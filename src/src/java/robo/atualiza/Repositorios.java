/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robo.atualiza;

import ferramentaBusca.indexador.Indexador;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.xml.parsers.ParserConfigurationException;
import operacoesPostgre.Remover;
import org.xml.sax.SAXException;
import postgres.AtualizaBase;
import postgres.Conectar;
import robo.atualiza.harvesterOAI.Principal;
import robo.util.Informacoes;
import robo.atualiza.importaOAI.InicioLeituraXML;
import robo.util.Operacoes;

/**
 *
 * @author Marcos
 */
public class Repositorios {

    private SimpleDateFormat dataFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    /**
     * Testa se algum reposit&oacute;rios precisa ser atualizado, se sim chama o m&etodo respons&aacute;vel por isso.
     * @param con Conex&atilde;o com a base de dados.
     * @param indexar Variavel do tipo Indexador. &Eacute; utilizada para passar os dados para o indice durante a atualiza&ccidil;&atilde;o dos metadados
     * @return true ou false indicando se algum reposit&aacute;rio foi atualizado ou n&atilde;
     */
    public boolean testa_atualizar_repositorio(Connection con, Indexador indexar) {
        boolean atualizou = false;
        int tempoSeguranca = 4; //tempo para descontar da periodicidade da ultima atualizacao. Pq se o robo rodou ontem as 02h e atualizou o repositorio as 02:10h hoje quando rodar novamente nao vai atualiza pq nao faz 24h ainda.

        String sql = "SELECT r.nome, r.id as idrep" + " FROM repositorios r"
                + " WHERE r.nome!='todos'"
                + " AND r.nome!='OBAA'"
                + " AND r.data_ultima_atualizacao < (now() - (r.periodicidade_horas-" + tempoSeguranca + ")*('1 HOUR')::INTERVAL);";


        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(sql);

            while (rs.next()) {
                int idRep = rs.getInt("idrep");
                boolean atualizadoTemp = false;
                //ATUALIZA REP
                try{
                atualizadoTemp = atualizaRepositorio(idRep, indexar, con); //chama o metodo que atualiza o repositorio
                }catch(Exception e){
                    /*
                     * ATENÇÃO: esse catch está vazio porque já é feito o tratamento de exceção dentro do metodo atualizaRepositorio mas é preciso subir a exceção porque se atualizar um repositorio só pela ferramenta administrativa tem que saber se deu erro.
                     */
                }

                if (atualizadoTemp) { //se algum repositorio foi atualizado entao recalcula o indice
                    atualizou = true;
                }
            }

        } catch (SQLException e) {
            System.err.println("FEB ERRO: Erro na consulta SQL no metodo testa_atualizar_repositorio. Mensagem:" + e.toString());
        }

        return atualizou;
    }

    /**
     * Atualiza o reposit&oacute;rio solicitado.
     * @param idRepositorio id do reposit&oacute;rio que deve ser atualizado.
     * @param indexar Variavel do tipo Indexador. &Eacute; utilizada para passar os dados para o indice durante a atualiza&ccidil;&atilde;o dos metadados
     * @param con Conex&atilde;o com a base de dados.
     * @return true ou false indicando se o reposit&aacute;rio foi atualizado ou n&atilde;
     */
    private boolean atualizaRepositorio(int idRepositorio, Indexador indexar, Connection con) throws Exception{
        boolean atualizou = false;
        Principal importar = new Principal();
        Informacoes conf = new Informacoes();
        InicioLeituraXML gravacao = new InicioLeituraXML();


        String caminhoDiretorioTemporario = conf.getCaminho();

        String sql = "SELECT r.nome, r.data_ultima_atualizacao, r.url_or_ip as url, r.metadata_prefix, r.set, to_char(r.data_xml, 'YYYY-MM-DD\"T\"HH24:MI:SSZ') as ultima_atualizacao_form"
                + " FROM repositorios r"
                + " WHERE r.id = " + idRepositorio + ";";


        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            if (rs.next()) {


                String nome = rs.getString("nome"); //atribiu a variavel nome o nome do repositorio retornado pela consulta sql

                System.out.println("FEB: (" + dataFormat.format(new Date())+ ") Atualizando repositorio: " + nome);//imprime o nome do repositorio

                String url = rs.getString("url"); //pega a url retornada pela consulta sql
                String metadataPrefix = rs.getString("metadata_prefix"); // para o OAI-PMH
                String set = rs.getString("set");
                if (set == null || set.isEmpty()) {
                    set = null;
                }
                if (url.isEmpty()) { //testa se a string url esta vazia.
                    System.err.println("FEB: Nao existe uma url associada ao repositorio " + nome);
                    atualizou = false;

                } else {//repositorio possui url para atualizacao

                    String ultimaAtualizacao = rs.getString("ultima_atualizacao_form");
                    Date data_ultima_atualizacao = rs.getDate("data_ultima_atualizacao");
                    
                    System.out.println("\t FEB: Ultima Atualizacao: " + ultimaAtualizacao + " nome do rep: " + nome);


                    //se a data da ultima atualização for inferior a 01/01/1000 apaga todos as informacoes do repositorio
                    if (Operacoes.testarDataAnterior1970(data_ultima_atualizacao)) {
                        ultimaAtualizacao = null; //se passar null para o metodo de harvester ele busca desde o inicio do rep
                        Remover deleta = new Remover();
                        System.out.println("Deletando toda a base de dados do repositório: " + nome.toUpperCase());
                        deleta.setDebugOut(false); //seta que nao e para imprimir mensagens de erro
                        try {
                            deleta.apagaObjetosRepositorio(idRepositorio, con);

                        } catch (Exception e) {
                            System.out.println("Erro FEB: " + e.toString());
                        }
                    }

                    //se o diretorio nao existir, cria se já existir apaga todos os .xml
                    File caminhoTeste = new File(caminhoDiretorioTemporario);
                    if (!caminhoTeste.isDirectory()) {//se o caminho informado nao for um diretorio
                        caminhoTeste.mkdirs();//cria o diretorio
                    } else { //APAGA TODOS ARQUIVOS XML do FEB DA PASTA
                        File[] arquivos = caminhoTeste.listFiles();
                        for (File toDelete : arquivos) {
                            if (toDelete.getName().startsWith("FEB-") && toDelete.getName().endsWith(".xml")) {
                                toDelete.delete();
                            }
                        }
                    }

                    if (caminhoTeste.isDirectory()) {
                        //efetua o Harvester e grava os xmls na pasta temporaria
                        
                        //coletando xmls
                        ArrayList<String> caminhoXML = importar.coletaXML_ListRecords(url, ultimaAtualizacao, nome, caminhoDiretorioTemporario, metadataPrefix, set); //chama o metodo que efetua o HarvesterVerb grava um xml em disco e retorna um arrayList com os caminhos para os XML

                        //leXMLgravaBase: le do xml traduz para o padrao OBAA e armazena na base de dados
                        gravacao.leXMLgravaBase(caminhoXML, idRepositorio, indexar, con);

                        atualizou = true;
                    } else {
                        System.err.println("FEB ERRO: O caminho informado nao eh um diretorio. E nao pode ser criado em: '" + caminhoDiretorioTemporario + "'");
                    }

                }
            }


            //chama metodo que atualiza a hora da ultima atualizacao
            AtualizaBase.atualizaHora(idRepositorio, con, indexar.getDataXML());

        } catch (UnknownHostException u) {
            System.err.println("FEB ERRO: Nao foi possivel encontrar o servidor oai-pmh informado, erro: " + u.toString());
            throw u;
        } catch (SQLException e) {
            System.err.println("FEB ERRO: SQL Exception... Erro na consulta sql na classe Repositorios:" + e.toString());
            throw e;
        } catch (ParserConfigurationException e) {
            System.err.println("FEB ERRO: O parser nao foi configurado corretamente. Mensagem: " + e.toString());
            throw e;
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
                AtualizaBase.atualizaHora(idRepositorio, con, indexar.getDataXML());
            } else if (msg.equalsIgnoreCase("noMetadataFormats")) {
                System.err.println(msgOAI + msg + " - There are no metadata formats available for the specified item.\n");
            } else if (msg.equalsIgnoreCase("noSetHierarchy")) {
                System.err.println(msgOAI + msg + " - The repository does not support sets.\n");
            } else {
                System.err.println("\nFEB ERRO: Problema ao fazer o parse do arquivo. " + e.toString());
            }
            throw e;
        } catch (FileNotFoundException e) {
            System.err.println("\nFEB ERRO: nao foi possivel coletar os dados de: " + e.toString() + "\n");
            throw e;
        } catch (IOException e) {
            System.err.println("\nFEB ERRO: Nao foi possivel coletar ou ler o XML em: " + this.getClass() + ": " + e.toString() + "\n");
            throw e;
        } catch (Exception e) {
            System.err.println("\nFEB ERRO ao efetuar o Harvester " + e.toString() + "\n");
            throw e;
        } finally {

            return atualizou;
        }

    }

    /**
     * M&eacute;todo utilizado pela ferramenta administrativa para atualizar o reposit&oacute;rio em tempo real. Este m&eacute;todo recebe um id, se esse if for zero ele atualiza todos os reposit&aacute;rios existentes. Se for um valor maior que zero ele atualiza apenas o escolhido.
     * @param idRep id do reposit&oacute;rio a ser atualizado. Se informar zero atualizar&aacute; todos
     * @param apagar informar se deseja apagar toda a base. true apaga e false apenas atualiza.
     * @author Marcos Nunes
     */
    public void atualizaFerramentaAdm(int idRep, boolean apagar) throws Exception{

        Indexador indexar = new Indexador();
        Connection con = null;
        boolean recalcularIndice = false;
        Conectar conectar = new Conectar(); //instancia uma variavel da classe Conectar

        try {
            con = conectar.conectaBD(); //chama o metodo conectaBD da classe conectar
            Statement stm = con.createStatement();

            if (apagar) {
                zeraDataRepositorio(idRep, stm); //se informado true seta a data da ultima atualizacao para zero
            }

            if (idRep > 0) { //atualizar um repositorio especifico ou todos. 0 = todos
                recalcularIndice = atualizaRepositorio(idRep, indexar, con);
            } else {
                String sql = "SELECT r.id as idrep" + " FROM repositorios r" + " WHERE r.nome!='todos';";


                ResultSet rs = stm.executeQuery(sql);

                while (rs.next()) {
                    int id = rs.getInt("idrep");
                    boolean resultadoAtualiza = false;
                    resultadoAtualiza = atualizaRepositorio(id, indexar, con); //chama o metodo que atualiza o repositorio
                    if (resultadoAtualiza) { //se algum dos repositorios foi atualizado vai recalcular o indice
                        recalcularIndice = resultadoAtualiza;
                    }
                }
            }
            if (recalcularIndice) {
                System.out.println("FEB: recalculando o indice " + dataFormat.format(new Date()));
                indexar.populateR1(con);
                System.out.println("FEB: indice recalculado! " + dataFormat.format(new Date()));
            }

        } catch (SQLException e) {
            System.err.println("FEB ERRO: SQL Exception... Erro na consulta SQL no metodo atualizaFerramentaAdm: " + e);
            throw e;
        } finally {
            try {
                con.close(); //fechar conexao
            } catch (SQLException e) {
                System.out.println("FEB ERRO: Erro ao fechar a conexão: " + e.getMessage());
            }
        }


    }

    private void zeraDataRepositorio(int idRep, Statement stm) throws SQLException {
        String sql = "UPDATE repositorios set data_ultima_atualizacao='0001-01-01 00:00:00' WHERE id=" + idRep;
        stm.executeUpdate(sql);

    }
}
