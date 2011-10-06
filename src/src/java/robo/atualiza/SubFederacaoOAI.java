/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robo.atualiza;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import javax.xml.parsers.ParserConfigurationException;
import operacoesPostgre.Remover;
import org.xml.sax.SAXException;
import postgres.Conectar;
import robo.atualiza.subfedOAI.Objetos;
import robo.atualiza.subfedOAI.SubRepositorios;
import robo.util.Informacoes;
import robo.util.Operacoes;

/**
 *
 * @author Marcos
 */
public class SubFederacaoOAI {

    /**
     * Testa se alguma subfedera&ccedil;&atilde;o precisa ser atualizada, se sim chama o m&etodo respons&aacute;vel por isso.
     * @param con Conex&atilde;o com a base de dados local.
     * @return true ou false indicando se alguma subfedera&ccedil;&atilde;o foi atualizada ou n&atilde;
     */
    public boolean testaAtualizaSubFedOAI(Connection con) {
        boolean atualizou = false;

        String sql = "SELECT id, url, nome, data_ultima_atualizacao, to_char(data_ultima_atualizacao, 'YYYY-MM-DD\"T\"HH:MI:SSZ') as data_formatada FROM dados_subfederacoes WHERE data_ultima_atualizacao <= (now() - ('24 HOUR')::INTERVAL);";

        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id");
                boolean atualizadoTemp = false;
                String nome = rs.getString("nome");
                String url = rs.getString("url");

                testaSubfedAnterioraMil(rs.getDate("data_ultima_atualizacao"), id, con, nome);//deleta toda a base se o ano da ultima atualizacao for menor que 1000

                if (url.isEmpty()) { //testa se a string url esta vazia.
                    System.err.println("FEB ERRO:Nao existe uma url associada ao repositorio " + nome);
                } else {
                    Informacoes info = new Informacoes();

                    if (url.endsWith("/")) { //se a url terminar com / concatena o endereço do oai-pmh sem a barra
                        url += info.getOaiPMH();
                    } else {
                        url += "/" + info.getOaiPMH();
                    }

                    atualizadoTemp = atualizaSubFedOAI(id, url, rs.getString("data_formatada"), nome, con);
                }

                if (atualizadoTemp) { //se alguma subfederacao foi atualizada entao seta o atualizou como true
                    atualizou = true;
                }
            }
        } catch (SQLException e) {
            System.err.println("FEB ERRO: Erro na consulta SQL no metodo testaAtualizaSubFedOAI. Mensagem:" + e);
        }

        return atualizou;
    }


    private boolean atualizaSubFedOAI(int idSubfed, String url, String ultimaAtualizacao, String nome, Connection con) {
        boolean atualizou = false;

        System.out.println("FEB: Atualizando subfederacao: " + nome);//imprime o nome do repositorio

        try {
            //atualizar repositorios da subfederacao
            SubRepositorios subRep = new SubRepositorios();
            subRep.atualizaSubRepositorios(url, nome, idSubfed, con);

            //atualizar objetos da subfederacao
            ArrayList<String> caminhoXML = new ArrayList<String>(); //ArrayList que armazenara os caminhos para os xmls
            Objetos obj = new Objetos();


            atualizaTimestampSubFed(con, idSubfed); //atualiza a hora da ultima atualizacao
        } catch (UnknownHostException u) {
            System.err.println("Nao foi possivel encontrar o servidor oai-pmh informado, erro: " + u);
        } catch (SQLException e) {
            System.err.println("SQL Exception... Erro na consulta sql na classe Repositorios:" + e.getMessage());
        } catch (ParserConfigurationException e) {
            System.err.println("O parser nao foi configurado corretamente. " + e);
        } catch (SAXException e) {
            String msg = e.getMessage();
            String msgOAI = "\nFEB: ERRO no parser do OAI-PMH, mensagem: ";
            if (msg.equalsIgnoreCase("badArgument")) {
                System.err.println(msgOAI + msg + " - The request includes illegal arguments, is missing required arguments, includes a repeated argument, or values for arguments have an illegal syntax.\n");
            } else if (msg.equalsIgnoreCase("badResumptionToken")) {
                System.err.println(msgOAI + msg + " - The value of the resumptionToken argument is invalid or expired.\n");
            } else if (msg.equalsIgnoreCase("badVerb")) {
                System.err.println(msgOAI + msg + " - Value of the verb argument is not a legal OAI-PMH verb, the verb argument is missing, or the verb argument is repeated. \n");
            } else if (msg.equalsIgnoreCase("cannotDisseminateFormat")) {
                System.err.println(msgOAI + msg + " -  The metadata format identified by the value given for the metadataPrefix argument is not supported by the item or by the repository.\n");
            } else if (msg.equalsIgnoreCase("idDoesNotExist")) {
                System.err.println(msgOAI + msg + " - The value of the identifier argument is unknown or illegal in this repository.\n");
            } else if (msg.equalsIgnoreCase("noRecordsMatch")) {
                System.out.println("FEB: " + msg + " - The combination of the values of the from, until, set and metadataPrefix arguments results in an empty list.\n");
                atualizaTimestampSubFed(con, idSubfed); //atualiza a hora da ultima atualizacao
            } else if (msg.equalsIgnoreCase("noMetadataFormats")) {
                System.err.println(msgOAI + msg + " - There are no metadata formats available for the specified item.\n");
            } else if (msg.equalsIgnoreCase("noSetHierarchy")) {
                System.err.println(msgOAI + msg + " - The repository does not support sets.\n");
            } else {
                System.err.println("\nFEB ERRO: Problema ao fazer o parse do arquivo. " + e);
            }
        } catch (FileNotFoundException e) {
            System.err.println("\nFEB ERRO: nao foi possivel coletar os dados de: " + e + "\n");
        } catch (IOException e) {
            System.err.println("\nFEB ERRO: O arquivo nao pode ser escrito ou lido: " + e + "\n");
        } catch (Exception e) {
            System.err.println("\nFEB ERRO ao efetuar o Harvester " + e + "\n");
        } finally {
            return atualizou;
        }
    }

    private static void testaSubfedAnterioraMil(Date data, int id, Connection con, String nome) {
        if (Operacoes.testarDataAnteriorMil(data)) {
            Remover deleta = new Remover();
            System.out.println("FEB: Deletando toda a base de dados da Subfederação: " + nome.toUpperCase());
            deleta.setDebugOut(false); //seta que nao e para imprimir mensagens de erro
            try {
                deleta.apagaObjetosSubfederacao(id, con);
                System.out.println("FEB: Base deletada!");
            } catch (Exception e) {
                System.out.println("FEB: Erro: " + e.toString());
            }
        }
    }

    /**
     * M&eacute;todo que atualiza a data da &uacute;ltima atualiza&ccedil;&atilde;o para a hora atual
     * @param con conex&atilde;o com a base de dados local
     * @param idSubFed id da subfedera&ccedil;&atilde;o que foi atualizada
     * @throws SQLException
     */
    private void atualizaTimestampSubFed(Connection con, int idSubFed) throws SQLException {
        String sql = "UPDATE dados_subfederacoes set data_ultima_atualizacao = now() WHERE id=" + idSubFed;
        Statement stm = con.createStatement();
        stm.executeUpdate(sql);
    }

    public static void main(String[] args) {
        SubFederacaoOAI run = new SubFederacaoOAI();
        Conectar conecta = new Conectar();
        Connection con = conecta.conectaBD();
        run.testaAtualizaSubFedOAI(con);
    }
}
