/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robo.atualiza;

import ferramentaBusca.indexador.Indexador;
import java.io.File;
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
import postgres.AtualizaBase;
import postgres.Conectar;
import robo.atualiza.harvesterOAI.Principal;
import robo.util.Informacoes;
import robo.atualiza.importa_OAI.InicioLeituraXML;
import robo.util.Operacoes;

/**
 *
 * @author Marcos
 */
public class Repositorios {

    /**
     * Testa se algum reposit&oacute;rios precisa ser atualizado, se sim chama o m&etodo respons&aacute;vel por isso.
     * @param con Conex&atilde;o com a base de dados.
     * @param indexar Variavel do tipo Indexador. &Eacute; utilizada para passar os dados para o indice durante a atualiza&ccidil;&atilde;o dos metadados
     * @return true ou false indicando se algum reposit&aacute;rio foi atualizado ou n&atilde;
     */
    public boolean testa_atualizar_repositorio(Connection con, Indexador indexar) {
        boolean atualizou = false;

        String sql = "SELECT r.nome, r.id as idrep" + " FROM repositorios r, info_repositorios i "
                + " WHERE r.id=i.id_repositorio"
                + " AND r.nome!='todos'"
                + " AND r.nome!='OBAA'"
                + " AND i.data_ultima_atualizacao < (now() - i.periodicidade_horas*('1 HOUR')::INTERVAL);";


        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(sql);

            while (rs.next()) {
                int idRep = rs.getInt("idrep");
                boolean atualizadoTemp = false;
                //ATUALIZA REP
                atualizadoTemp = atualizaRepositorio(idRep, indexar, con); //chama o metodo que atualiza o repositorio

                if (atualizadoTemp) { //se algum repositorio foi atualizado entao recalcula o indice
                    atualizou = true;
                }
            }

        } catch (SQLException e) {
            System.err.println("SQL Exception... Erro na consulta:");
            e.printStackTrace();
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
    private boolean atualizaRepositorio(int idRepositorio, Indexador indexar, Connection con) {
        boolean atualizou = false;
        Principal importar = new Principal();
        Informacoes conf = new Informacoes();
        InicioLeituraXML gravacao = new InicioLeituraXML();

        String caminhoDiretorioTemporario = conf.getCaminho();

        String sql = "SELECT r.nome, i.data_ultima_atualizacao, i.url_or_ip as url, i.tipo_sincronizacao, i.metadata_prefix, to_char(i.data_ultima_atualizacao, 'YYYY-MM-DD\"T\"HH:MI:SSZ') as ultima_atualizacao_form"
                + " FROM repositorios r, info_repositorios i"
                + " WHERE r.id = i.id_repositorio"
                + " AND r.id = " + idRepositorio + ";";


        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            if (rs.next()) {

                Date hora = new Date();

                String nome = rs.getString("nome"); //atribiu a variavel nome o nome do repositorio retornado pela consulta sql

                System.out.println("-> " + hora);
                System.out.println("Atualizando repositorio: " + nome);//imprime o nome do repositorio

                String url = rs.getString("url"); //pega a url retornada pela consulta sql
                String metadataPrefix = rs.getString("metadata_prefix"); // para o OAI-PMH

                if (url.isEmpty()) { //testa se a string url esta vazia.
                    System.out.println("Não existe uma url associada ao repositório " + nome);
                    atualizou = false;

                } else {//repositorio possui url para atualizacao

                    String ultimaAtualizacao = rs.getString("ultima_atualizacao_form");
                    Date data_ultima_atualizacao = rs.getDate("data_ultima_atualizacao");
                    ArrayList<String> caminhoXML = new ArrayList<String>(); //ArrayList que armazenara os caminhos para os xmls

                    System.out.println("\t Ultima Atualização: " + ultimaAtualizacao + " nome do rep: " + nome);


                    //se a data da ultima atualização for inferior a 01/01/1000 apaga todos as informacoes do repositorio
                    if (Operacoes.testarDataAnteriorMil(data_ultima_atualizacao)) {
                        ultimaAtualizacao = null; //se passar null para o metodo de harvester ele busca desde o inicio do rep
                        Remover deleta = new Remover();
                        System.out.println("Deletando toda a base de dados do repositório: " + nome.toUpperCase());
                        deleta.setDebugOut(false); //seta que nao e para imprimir mensagens de erro
                        try {
                            deleta.apagaObjetosRepositorio(idRepositorio, con);

                        } catch (Exception e) {
                            System.out.println("Error: " + e.toString());
                        }
                    }

                    //se o diretorio nao existir, cria se já existir apaga todos os .xml
                    File caminhoTeste = new File(caminhoDiretorioTemporario);
                    if (!caminhoTeste.isDirectory()) {//se o caminho informado nao for um diretorio
                        caminhoTeste.mkdirs();//cria o diretorio
                    } else { //APAGA TODOS ARQUIVOS XML DA PASTA
                        File[] arquivos = caminhoTeste.listFiles();
                        for (File toDelete : arquivos) {
                            if (toDelete.getName().startsWith("FEB-") && toDelete.getName().endsWith(".xml")) {
                                toDelete.delete();
                            }
                        }
                    }

                    if (caminhoTeste.isDirectory()) {
                        //efetua o Harvester e grava os xmls na pasta temporaria
                        caminhoXML = importar.buscaXmlRepositorio(url, ultimaAtualizacao, nome, caminhoDiretorioTemporario, metadataPrefix); //chama o metodo que efetua o HarvesterVerb grava um xml em disco e retorna um arrayList com os caminhos para os XML

                        //leXMLgravaBase: le do xml traduz para o padrao OBAA e armazena na base de dados
                        gravacao.leXMLgravaBase(caminhoXML, idRepositorio, indexar, con);
                        atualizou = true;

                    } else {
                        System.out.println("O caminho informado não é um diretório. E não pode ser criado em: '" + caminhoDiretorioTemporario + "'");
                    }

                }
            }
        } catch (UnknownHostException u) {
            System.err.println("Nao foi possivel encontrar o servidor oai-pmh informado, erro: " + u);
        } catch (SQLException e) {
            System.err.println("SQL Exception... Erro na consulta:");
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            System.err.println("O parser nao foi configurado corretamente. " + e);
            e.printStackTrace();
        } catch (SAXException e) {
            System.err.println("Problema ao fazer o parse do arquivo. " + e);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("O arquivo nao pode ser escrito ou lido: " + e);
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Erro efetuar o Harvester " + e);
        } finally {
//ATUALIZA DATA ultima atualizacao
            AtualizaBase atualiza = new AtualizaBase();
            //chama metodo que atualiza a hora da ultima atualizacao
            atualiza.atualizaHora(idRepositorio);
            return atualizou;
        }

    }

    /**
     * M&eacute;todo utilizado pela ferramenta administrativa para atualizar o reposit&oacute;rio em tempo real. Este m&eacute;todo recebe um id, se esse if for zero ele atualiza todos os reposit&aacute;rios existentes. Se for um valor maior que zero ele atualiza apenas o escolhido.
     * @param idRep id do reposit&oacute;rio a ser atualizado. Se informar zero atualizar&aacute; todos
     * @author Marcos Nunes
     */
    public void atualizaFerramentaAdm(int idRep) {

        Indexador indexar = new Indexador();
        Connection con = null;
        boolean recalcularIndice = false;
        try {
            Conectar conectar = new Conectar(); //instancia uma variavel da classe Conectar
            con = conectar.conectaBD(); //chama o metodo conectaBD da classe conectar

            if (idRep > 0) {
                recalcularIndice = atualizaRepositorio(idRep, indexar, con);
            } else {
                String sql = "SELECT r.id as idrep" + " FROM repositorios r" + " WHERE r.nome!='todos';";

                Statement stm = con.createStatement();
                ResultSet rs = stm.executeQuery(sql);

                while (rs.next()) {
                    int id = rs.getInt("idrep");
                    recalcularIndice = atualizaRepositorio(id, indexar, con); //chama o metodo que atualiza o repositorio
                }
            }
            if (recalcularIndice) {
                System.out.println("recalculando o indice " + new Date());
                indexar.populateR1(con);
                System.out.println("indice recalculado! " + new Date());
            }

        } catch (SQLException e) {
            System.err.println("SQL Exception... Erro na consulta:");
            e.printStackTrace();
        } finally {
            try {
                con.close(); //fechar conexao
            } catch (SQLException e) {
                System.out.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }


    }
}
