/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robo.atualiza;

import ferramentaBusca.indexador.Indexador;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import javax.xml.parsers.ParserConfigurationException;
import modelos.SubFederacao;
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
     * Atualiza todas as subfedera&ccedil;&otilde;es. Coleta da base os dados
     * das subfedera&ccedil;&otilde;es e chama o m&etodo que efetua a
     * atualia&ccedil;&atilde;o.
     *
     * @param con Conex&atilde;o com a base de dados local.
     * @param indexar Variavel do tipo Indexador. &Eacute; utilizada para passar
     * os dados para o indice durante a atualiza&ccidil;&atilde;o dos metadados.
     * @return true ou false indicando se alguma subfedera&ccedil;&atilde;o foi
     * atualizada ou n&atilde;o.
     */
    public boolean pre_AtualizaSubFedOAI(Connection con, Indexador indexar) {
        boolean atualizou = false;

        String sql = "SELECT id, url, nome, data_ultima_atualizacao, to_char(data_xml, 'YYYY-MM-DD\"T\"HH24:MI:SSZ') as data_formatada FROM dados_subfederacoes;";

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
                    System.err.println("FEB ERRO: Nao existe uma url associada ao repositorio " + nome);
                } else {
                    Informacoes info = new Informacoes();

                    if (url.endsWith("/")) { //se a url terminar com / concatena o endereço do oai-pmh sem a barra
                        url += info.getOaiPMH();
                    } else {
                        url += "/" + info.getOaiPMH();
                    }
                    Long inicio = System.currentTimeMillis();
                    try {
                        atualizadoTemp = atualizaSubFedOAI(id, url, rs.getString("data_formatada"), nome, con, indexar);
                    } catch (Exception e) {
                        /*
                         * ATENÇÃO: esse catch está vazio porque já é feito o
                         * tratamento de exceção dentro do metodo
                         * atualizaSubFedOAI mas é preciso subir a exceção
                         * porque se atualizar uma federação só pela ferramenta
                         * administrativa tem que saber se deu erro.
                         */
                    }
                    Long fim = System.currentTimeMillis();
                    System.out.println("FEB: Levou: " + (fim - inicio) / 1000 + " segundos para atualizar a subfederacao: " + nome);
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

    /**
     * Chama o m&eacute;todo respons&aacute;vel por atualizar os
     * reposit&oacute;rios da subfedera&ccedil;&atilde;o e o m&eacute;todo
     * respons&aacute;vel por atualizar os metadados dos objetos da
     * subfedera&ccedil;&atilde;o. Este m&eacute;todo efetua todo o tratamento
     * de exce&ccedil;&otilde;es do processo de harverter, parser e
     * inser&ccedil;&atilde;o na base.
     *
     * @param idSubfed Id da subfedera&ccedil;&atilde;o.
     * @param url Endere&ccedil;o http da subfedera&ccedil;&atilde;o que
     * responde por OAI-PMH. Ex: http://feb.ufrgs.br/feb/OAIHander
     * @param ultimaAtualizacao Data em que a subfedera&ccedil;&atilde;o sofreu
     * a &uacute;ltima atualiza&ccedil;&atilde;o.
     * @param nome Nome da subfedera&ccedil;&atilde;o.
     * @param con Conex&atilde;o com a base de dados local.
     * @param indexar Variavel do tipo Indexador. &Eacute; utilizada para passar
     * os dados para o indice durante a atualiza&ccidil;&atilde;o dos metadados.
     * @return true ou false indicando se alguma subfedera&ccedil;&atilde;o foi
     * atualizada ou n&atilde;o.
     */
    private boolean atualizaSubFedOAI(int idSubfed, String url, String ultimaAtualizacao, String nome, Connection con, Indexador indexar) throws Exception {
        boolean atualizou = false;

        System.out.println("FEB: Atualizando subfederacao: " + nome);//imprime o nome do repositorio

        try {
            //atualizar repositorios da subfederacao
            SubRepositorios subRep = new SubRepositorios();
            subRep.atualizaSubRepositorios(url, nome, idSubfed, con);

            //atualizar objetos da subfederacao
            Objetos obj = new Objetos();
            obj.atualizaObjetosSubFed(url, ultimaAtualizacao, nome, "obaa", null, con, indexar);
            atualizaTimestampSubFed(con, idSubfed, indexar.getDataXML()); //atualiza a hora da ultima atualizacao
            atualizou = true;

        } catch (UnknownHostException u) {
            System.err.println("FEB ERRO - Metodo atualizaSubFedOAI: Nao foi possivel encontrar o servidor oai-pmh informado, erro: " + u);
            throw u;
        } catch (SQLException e) {
            System.err.println("FEB ERRO - Metodo atualizaSubFedOAI: SQL Exception... Erro na consulta sql na classe SubFederacaoOAI:" + e.getMessage());
            throw e;
        } catch (ParserConfigurationException e) {
            System.err.println("FEB ERRO - Metodo atualizaSubFedOAI: O parser nao foi configurado corretamente. " + e);
            throw e;
        } catch (SAXException e) {            
            String msg = e.getMessage();
            String msgOAI = "\nFEB ERRO - Metodo atualizaSubFedOAI: erro no parser do OAI-PMH, mensagem: ";
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
                atualizaTimestampSubFed(con, idSubfed, indexar.getDataXML()); //atualiza a hora da ultima atualizacao
            } else if (msg.equalsIgnoreCase("noMetadataFormats")) {
                System.err.println(msgOAI + msg + " - There are no metadata formats available for the specified item.\n");
            } else if (msg.equalsIgnoreCase("noSetHierarchy")) {
                System.err.println(msgOAI + msg + " - The repository does not support sets.\n");
            } else {
                System.err.println("\nFEB ERRO: Problema ao fazer o parse do arquivo. " + e);
            }
            throw e;
        } catch (FileNotFoundException e) {
            System.err.println("\nFEB ERRO - " + this.getClass() + ": nao foi possivel coletar os dados de: " + e + "\n");
            throw e;
        } catch (IOException e) {
            System.err.println("\nFEB ERRO - Nao foi possivel coletar ou ler o XML em " + this.getClass() + ": " + e + "\n");
            throw e;
        } catch (Exception e) {
            System.err.println("\nFEB ERRO - " + this.getClass() + ": erro ao efetuar o Harvester " + e + "\n");
            throw e;
        } finally {
            return atualizou;
        }
    }

    /**
     * Testa se a data informada &eacute anterior ao ano 1000, se positivo,
     * deleta todos os objetos da Subfedera&ccedil;&atilde;o
     *
     * @param data Data a ser testada.
     * @param id Id da Subfedera&ccedil;&atilde;o
     * @param con Conex&atilde;o com a base de dados local.
     * @param nome Nome da Subfedera&ccedil;&atilde;o.
     */
    private static void testaSubfedAnterioraMil(Date data, int id, Connection con, String nome) {
        if (Operacoes.testarDataAnterior1970(data)) {
            Remover deleta = new Remover();
            System.out.println("FEB: Deletando toda a base de dados da Subfederação: " + nome.toUpperCase());
            deleta.setDebugOut(false); //seta que nao e para imprimir mensagens de erro
            try {
                deleta.apagaObjetosSubfederacao(id, con);
                System.out.println("FEB: Base deletada!");
            } catch (Exception e) {
                System.out.println("FEB ERRO - Metodo atualizaSubFedOAI: " + e.toString());
            }
        }
    }

    /**
     * M&eacute;todo que atualiza a data da &uacute;ltima
     * atualiza&ccedil;&atilde;o para a hora atual
     *
     * @param con Conex&atilde;o com a base de dados local
     * @param idSubFed Id da subfedera&ccedil;&atilde;o que foi atualizada
     * @throws SQLException
     */
    private void atualizaTimestampSubFed(Connection con, int idSubFed, String dataXML) throws SQLException {
        if (!dataXML.equals("0")) {
            String sql = "UPDATE dados_subfederacoes set data_ultima_atualizacao = now(), data_xml='" + dataXML + "' WHERE id=" + idSubFed;
            Statement stm = con.createStatement();
            stm.executeUpdate(sql);
        }
    }

    /**
     * Atualiza a subfedera&ccedil;&atilde;o informada e recalcula o
     * &iacute;ndice.
     *
     * @param idSub Id da subfedera&ccedil;&atilde;o na ser atualizada.
     * @return true se atualizou e false caso contr&aacute;rio.
     */
    public void atualizaSubfedAdm(SubFederacao subFed) throws Exception {

        Conectar conectar = new Conectar(); //instancia uma variavel da classe Conectar
        Connection con = conectar.conectaBD(); //chama o metodo conectaBD da classe conectar
        Indexador indexar = new Indexador();
        testaSubfedAnterioraMil(subFed.getUltimaAtualizacao(), subFed.getId(), con, subFed.getNome());//deleta toda a base se o ano da ultima atualizacao for menor que 1000
        String url = subFed.getUrl();
        if (url.isEmpty()) { //testa se a string url esta vazia.
            System.err.println("FEB ERRO: Nao existe uma url associada ao repositorio " + subFed.getNome());
        } else {
            Informacoes info = new Informacoes();

            if (url.endsWith("/")) { //se a url terminar com / concatena o endereço do oai-pmh sem a barra
                url += info.getOaiPMH();
            } else {
                url += "/" + info.getOaiPMH();
            }
            Long inicio = System.currentTimeMillis();
            atualizaSubFedOAI(subFed.getId(), url, Operacoes.formatDateOAIPMH(subFed.getDataXML()), subFed.getNome(), con, indexar);
            Long fim = System.currentTimeMillis();
            System.out.println("FEB: Levou: " + (fim - inicio) / 1000 + " segundos para atualizar a subfederacao: " + subFed.getNome());
        }

        System.out.println("FEB: recalculando o indice.");
        Long inicio = System.currentTimeMillis();
        indexar.populateR1(con);
        Long fim = System.currentTimeMillis();
        System.out.println("FEB: indice recalculado em " + (fim - inicio) / 1000 + " segundos.");

        try {
            con.close(); //fechar conexao
        } catch (SQLException e) {
            System.out.println("FEB ERRO - Metodo atualizaSubFedOAI: Erro ao fechar a conexão no metodo atualizaSubfedAdm: " + e.getMessage());
        }

    }
}
