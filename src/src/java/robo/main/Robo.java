package robo.main;

import robo.importa_OAI.InicioLeituraXML;
import robo.harvesterOAI.Principal;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import robo.util.Informacoes;
import ferramentaBusca.indexador.Indexador;
import java.io.File;
import java.util.Date;
import operacoesLdap.Remover;
import postgres.Conectar;

/**
 * Ferramenta de Sincronismo (Robô)
 * @author Marcos
 */
public class Robo {

    Informacoes conf = new Informacoes();
    Principal importar = new Principal();
    InicioLeituraXML gravacao = new InicioLeituraXML();
    Conectar conectar = new Conectar(); //instancia uma variavel da classe Conectar

    /**
     * Principal m&eacute;todo do rob&ocirc;. Este m&eacute;todo efetua uma consulta na base de dados, procurando por reposit&oacute;rios que est&atilde;o desatualizados, quando encontra algum, chama o m&eacute;todo que atualiza o repositório.
     * @author Marcos Nunes
     */
    public void testaUltimaIportacao() {

        Indexador indexar = new Indexador();
        Connection con = null;
//        boolean atualizou = false;
        Robo robo = new Robo();

        String sql = "SELECT r.nome, r.id as idrep" + " FROM repositorios r, info_repositorios i " +
                " WHERE r.id=i.id_repositorio" +
                " AND r.nome!='todos'" +
                " AND r.nome!='OBAA'" +
                " AND i.data_ultima_atualizacao < (now() - i.periodicidade_horas*('1 HOUR')::INTERVAL);";



        con = conectar.conectaBD(); //chama o metodo conectaBD da classe conectar

        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            boolean repAtualizado = false;
            while (rs.next()) {
                int idRep = rs.getInt("idrep");
                robo.atualizaRepositorio(idRep, indexar); //chama o metodo que atualiza o repositorio
                repAtualizado = true;
            }

            if (repAtualizado) {
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

    /**
     * Atualiza o reposit&oacute;rio solicitado.
     * @param idRepositorio id do reposit&oacute;rio que deve ser atualizado.
     * @param indexar Variavel do tipo Indexador. &Eacute; utilizada para passar os dados para o indice durante a atualiza&ccidil;&atilde;o dos metadados
     * @return true ou false indicando se o reposit&aacute;rio foi atualizado ou n&atilde;
     */
    public boolean atualizaRepositorio(int idRepositorio, Indexador indexar) {
        boolean atualizou = false;
        Connection con = null;
        String caminhoDiretorioTemporario = conf.getCaminho();



        String sql = "SELECT l.base, r.nome, i.data_ultima_atualizacao, l.ip, i.url_or_ip as url, i.tipo_sincronizacao," + " l.login, l.senha, p.metadata_prefix, l.porta as portaLdapDestino," + " to_char(i.data_ultima_atualizacao, 'YYYY-MM-DD\"T\"HH:MI:SSZ') as ultima_atualizacao_form" + " FROM repositorios r, info_repositorios i, padraometadados p, dados_subfederacoes l" + " WHERE r.id = i.id_repositorio" + " AND i.padrao_metadados = p.id" + " AND i.ldap_destino = l.id" + " AND r.id = " + idRepositorio + ";";

        con = conectar.conectaBD(); //chama o metodo conectaBD da classe Conectar

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

                    String base = rs.getString("base");
                    String ultimaAtualizacao = rs.getString("ultima_atualizacao_form");
                    //String horaAtual = rs.getString("horaAtualForm");
////                String ip = rs.getString("ip_destino");
////                String login = rs.getString("login");
////                String senha = rs.getString("senha");
////                int porta = rs.getInt("porta");


                    //Configuracao subFedconf = new Configuracao(base, login, senha, ip, porta);

                    Date data_ultima_atualizacao = rs.getDate("data_ultima_atualizacao");
                    ArrayList<String> caminhoXML = new ArrayList<String>(); //ArrayList que armazenara os caminhos para os xmls

                    System.out.println("\t Ultima Atualização: " + ultimaAtualizacao + " nome do rep: " + nome);


                    //se a data da ultima atualização for inferior a 01/01/1000 apaga todos as informacoes do repositorio o LDAP
                    if (testarDataAnteriorMil(data_ultima_atualizacao)) {
                        Remover deleta = new Remover();
                        System.out.println("Deletando toda a base de dados do repositório: " + nome.toUpperCase());
                        deleta.setDebugOut(false); //seta que nao e para imprimir mensagens de erro
                        try {
                            deleta.apagaTodosObjetos(idRepositorio, con);

                        } catch (Exception e) {
                            System.out.println("Error: " + e.toString());
                        }
                    }


                    //sincronicazao feita por OAI-PMH

                    File caminhoTeste = new File(caminhoDiretorioTemporario);
                    if (!caminhoTeste.isDirectory()) {//se o caminho informado nao for um diretorio
                        caminhoTeste.mkdirs();//cria o diretorio
                    } else {
                        /**************************
                         * APAGAR TODOS ARQUIVOS XML DA PASTA
                         **************************/
                    }

                    if (caminhoTeste.isDirectory()) {


                        caminhoXML = importar.buscaXmlRepositorio(url, ultimaAtualizacao, "9999-12-31T00:00:00Z", nome, caminhoDiretorioTemporario, metadataPrefix); //chama o metodo que efetua o HarvesterVerb grava um xml em disco e retorna um arrayList com os caminhos para os XML
                        //leXMLgravaBase le do xml e armazena no ldap idependente de padrao de metadado

                        //Primeira operação do robô com LDAP
                        gravacao.leXMLgravaBase(caminhoXML, idRepositorio, indexar, con); //chama a classe que le o xml e grava os dados no ldap
                        atualizou = true;


                    } else {
                        System.out.println("O caminho informado não é um diretório. E não pode ser criado. " + caminhoDiretorioTemporario);
                    }

                }
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
            return atualizou;
        }

    }

    /**
     *  Testa se a data recebida é anterior a 01/01/1000. Utilizado para testar se a base de dados deve ser sincronizada do zero ou não.
     * @param horaBase hora que será testada
     * @return true ou false. Se a data informada como parâmetro for menor retorna true se não false
     */
    public static boolean testarDataAnteriorMil(Date horaBase) {

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        Date dataTeste = null;
        try {
            dataTeste = format.parse("01/01/1000");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (dataTeste.after(horaBase)) {
            return true;
        } else {
            return false;
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
//        boolean atualizou = false;
        Robo robo = new Robo();
        boolean recalcularIndice = false;
        try {
            con = conectar.conectaBD(); //chama o metodo conectaBD da classe conectar
            System.out.println("id= " + idRep);
            if (idRep > 0) {
                recalcularIndice = robo.atualizaRepositorio(idRep, indexar);
            } else {
                String sql = "SELECT r.id as idrep" + " FROM repositorios r" + " WHERE r.nome!='todos';";

                Statement stm = con.createStatement();
                ResultSet rs = stm.executeQuery(sql);

                while (rs.next()) {
                    int id = rs.getInt("idrep");
                    recalcularIndice = robo.atualizaRepositorio(id, indexar); //chama o metodo que atualiza o repositorio
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

    public static void main(String[] args) {
        Robo run = new Robo();
        run.testaUltimaIportacao();
    }
}
