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
        boolean atualizou = false;
        Robo robo = new Robo();

        String sql = "SELECT r.nome, r.id as idrep" +
                " FROM repositorios r, info_repositorios i" +
                " WHERE r.id=i.id_repositorio" +
                " AND r.nome!='todos'" +
                " AND r.nome!='obaa'" +
                " AND i.data_ultima_atualizacao < (now() - i.periodicidade_horas*('1 HOUR')::INTERVAL);";



        con = conectar.conectaBD(); //chama o metodo conectaBD da classe conectar

        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(sql);

            while (rs.next()) {
                int idRep = rs.getInt("idrep");

                atualizou = robo.atualizaRepositorio(idRep, indexar); //chama o metodo que atualiza o repositorio

            }

            if (atualizou) {
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
//    public boolean atualizaRepositorio(int idRepositorio, Indexador indexar) {
//        boolean atualizou = false;
//        Connection con = null;
//        String caminhoDiretorioTemporario = conf.getCaminho();
//
//
//
//         String sql = "SELECT r.nome, i.data_ultima_atualizacao, l.ip AS ipDestino, ('ou='||i.nome_na_federacao||','||l.dn) as dnDestino, i.url_or_ip as url, i.tipo_sincronizacao,"+
//                " l.login as loginLdapDestino, l.senha as senhaLdapDestino, p.nome as padrao_metadados, p.metadata_prefix, p.name_space, l.porta as portaLdapDestino,"+
//                " d.login_ldap_origem, d.senha_ldap_origem, d.porta_ldap_origem, d.dn_origem,"+
//                " i.data_ultima_atualizacao as ultima_atualizacao_form"+
//                " FROM repositorios r, info_repositorios i, padraometadados p, dadosldap d, ldaps l"+
//                " WHERE r.id = i.id_repositorio"+
//                " AND r.id = d.id_repositorio"+
//                " AND i.padrao_metadados = p.id"+
//                " AND i.ldap_destino = l.id"+
//                " AND r.id = "+ idRepositorio + ";";
//
//        /*
//        String sql = "SELECT r.nome, i.data_ultima_atualizacao, l.ip AS ip_destino, ('ou='||i.nome_na_federacao||','||l.dn) as dn_destino, i.url_or_ip as url, i.tipo_sincronizacao," +
//                " l.login as login_ldap_destino, l.senha as senha_ldap_destino, p.nome as padrao_metadados, p.metadata_prefix, p.name_space, l.porta as porta_ldap_destino," +
//                " d.login_ldap_origem, d.senha_ldap_origem, d.porta_ldap_origem, d.dn_origem," +
//                " DATE_FORMAT(i.data_ultima_atualizacao, '%Y-%m-%dT%H:%i:%sZ') as ultima_atualizacao_form" +
//                " FROM repositorios r, info_repositorios i, padraometadados p, dadosldap d, ldaps l" +
//                " WHERE r.id=i.id_repositorio" +
//                " AND r.id=d.id_repositorio" +
//                " AND i.padrao_metadados=p.id" +
//                " AND i.ldap_destino=l.id" +
//                " AND r.id=" + idRepositorio + ";";
//*/
//        con = conectar.conectaBD(); //chama o metodo conectaBD da classe Conectar
//
//        try {
//            Statement stm = con.createStatement();
//            ResultSet rs = stm.executeQuery(sql);
//            rs.next();
//
//            Date hora = new Date();
//
//            String nome = rs.getString("nome"); //atribiu a variavel nome o nome do repositorio retornado pela consulta sql
//
//            System.out.println("-> " + hora);
//            System.out.println("Atualizando repositorio: " + nome);//imprime o nome do repositorio
//
//            String url = rs.getString("url"); //pega a url retornada pela consulta sql
//            String metadataPrefix = rs.getString("metadata_prefix");
//            String namespace = rs.getString("name_space");
//
//            if (url.isEmpty()) { //testa se a string url esta vazia.
//                System.out.println("Não existe uma url associada ao repositório " + nome);
//
//            } else {//repositorio possui url para atualizacao
//
//                String ultimaAtualizacao = rs.getString("ultima_atualizacao_form");
//                //String horaAtual = rs.getString("horaAtualForm");
//                String ipDestino = rs.getString("ip_destino");
//                String dnDestino = rs.getString("dn_destino");
//                int idRep = idRepositorio;
//                String loginDestino = rs.getString("login_ldap_destino");
//                String senhaDestino = rs.getString("senha_ldap_destino");
//                String padrao_metadados = rs.getString("padrao_metadados");
//                int portaLDAPDestino = rs.getInt("porta_ldap_destino");
//                String loginOrigem = rs.getString("login_ldap_origem");
//                String senhaOrigem = rs.getString("senha_ldap_origem");
//                int portaLDAPOrigem = rs.getInt("porta_ldap_origem");
//
//                String dnOrigem = rs.getString("dnOrigem");
//                String tipoSinc = rs.getString("tipo_sincronizacao");
//                Date data_ultima_atualizacao = rs.getDate("data_ultima_atualizacao");
//                ArrayList<String> caminhoXML = new ArrayList<String>(); //ArrayList que aramzanara os caminhos para os xmls
//
//                System.out.println("Ultima Atualização: " + ultimaAtualizacao + " nome do rep: " + nome);
//
//
//                //se a data da ultima atualização for inferior a 01/01/1000 apaga todos as informacoes do repositorio o LDAP
//                if (testarDataAnteriorMil(data_ultima_atualizacao)) {
//                    Remover deleta = new Remover();
//                    System.out.println("Deletando toda a base de dados do repositório: " + nome.toUpperCase());
//                    deleta.setDebugOut(false); //seta que nao e para imprimir mensagens de erro
//                    try {
//
//                        LDAPConnection lc = new LDAPConnection();
//                        lc.connect(ipDestino, portaLDAPDestino); // conecta no servidor ldap
//                        lc.bind(LDAPConnection.LDAP_V3, loginDestino, senhaDestino.getBytes("UTF8")); // autentica no servidor
//
//                        deleta.apagaTodosObjetos(dnDestino, lc); //apaga todos os objetos da base
//                        lc.disconnect();
//                    } catch (LDAPException e) {
//                        System.out.println("Error:  " + e.toString());
//                    } catch (UnsupportedEncodingException e) {
//                        System.out.println("Error: " + e.toString());
//                    }
//                }
//
//                //testar se a sincronicazao deve ser feita por OAI-PMH
//                if (tipoSinc.equalsIgnoreCase("OAI-PMH")) {
//
//                    System.out.println(" ultima atualizacao: " + ultimaAtualizacao);
//                    File caminhoTeste = new File(caminhoDiretorioTemporario);
//                    if (!caminhoTeste.isDirectory()) {//se o caminho informado nao for um diretorio
//                        caminhoTeste.mkdirs();//cria o diretorio
//                        }
//
//                    if (caminhoTeste.isDirectory()) {
//                        //conectar o ldap e mandar a conexao pronta
//                        try {
//                            LDAPConnection lc = new LDAPConnection();
//                            lc.connect(ipDestino, portaLDAPDestino); // conecta no servidor ldap
//                            lc.bind(LDAPConnection.LDAP_V3, loginDestino, senhaDestino.getBytes("UTF8")); // autentica no servidor
//
//                            caminhoXML = importar.buscaXmlRepositorio(url, ultimaAtualizacao, "9999-12-31T00:00:00Z", nome, caminhoDiretorioTemporario, metadataPrefix); //chama o metodo que efetua o HarvesterVerb grava um xml em disco e retorna um arrayList com os caminhos para os XML
//                            //leXMLgravaBase le do xml e armazena no ldap idependente de padrao de metadado
//
//                            //Primeira operação do robô com LDAP
//                            gravacao.leXMLgravaBase(dnDestino, caminhoXML, idRep, indexar, lc, con); //chama a classe que le o xml e grava os dados no ldap
//
//                            atualizou = true;
//                            lc.disconnect(); //desconecta do ldap
//                        } catch (LDAPException e) {
//                            System.out.println("Error:  " + e.toString());
//                        } catch (UnsupportedEncodingException e) {
//                            System.out.println("Error: " + e.toString());
//                        }
//
//                    } else {
//                        System.out.println("O caminho informado não é um diretório. E não pode ser criado. " + caminhoDiretorioTemporario);
//                    }
//
//                } else if (tipoSinc.equalsIgnoreCase("LDAP")) { //se a sincronizacao for por LDAP
//
//                    if (padrao_metadados.equalsIgnoreCase("lom")) {
//                        System.out.println("\nAtualizando repositorio: " + nome + "...\n Está no padrão LOM e a sincrinização será por: " + tipoSinc);
//                        System.out.println(" ultima atualizacao: " + ultimaAtualizacao);
//                        try {
//
//                            LDAPConnection lc = new LDAPConnection();
//                            lc.connect(ipDestino, portaLDAPDestino); // conecta no servidor ldap
//                            lc.bind(LDAPConnection.LDAP_V3, loginDestino, senhaDestino.getBytes("UTF8")); // autentica no servidor
//
//                            boolean result = atualizaLDAP.atualizaLDAP(url, dnOrigem, loginOrigem, senhaOrigem, portaLDAPOrigem, "lomIdentifier", dnDestino, lc, indexar, idRep);
//                            //se o resultado for true atualiza a hora da base
//                            if (result) {
//                                System.out.println("Repositorio " + nome + " atualizado com sucesso!");
//                                //atualizar hora da ultima atualização
//                                AtualizaBase atualiza = new AtualizaBase();
//                                //chama metodo que atualiza a hora da ultima atualizacao
//                                atualiza.atualizaHora(idRep);
//                                atualizou = true;
//                            }
//                            lc.disconnect(); //desconecta do ldap
//                        } catch (LDAPException e) {
//                            System.out.println("Error:  " + e.toString());
//                        } catch (UnsupportedEncodingException e) {
//                            System.out.println("Error: " + e.toString());
//                        }
//
//                    }
//                }
//
//            }
//
//
//        } catch (SQLException e) {
//            System.err.println("SQL Exception... Erro na consulta:");
//            e.printStackTrace();
//        } finally {
//            try {
//                con.close(); //fechar conexao mysql
//                } catch (SQLException e) {
//                System.out.println("Erro ao fechar a conexão: " + e.getMessage());
//            }
//            return atualizou;
//        }
//
//    }

        public boolean atualizaRepositorio(int idRepositorio, Indexador indexar) {
        boolean atualizou = false;
        Connection con = null;
        String caminhoDiretorioTemporario = conf.getCaminho();



         String sql = "SELECT l.base, r.nome, i.data_ultima_atualizacao, l.ip, i.url_or_ip as url, i.tipo_sincronizacao,"+
                " l.login, l.senha, p.metadata_prefix, l.porta as portaLdapDestino,"+
                " i.data_ultima_atualizacao as ultima_atualizacao_form"+
                " FROM repositorios r, info_repositorios i, padraometadados p, dados_subfederacoes l"+
                " WHERE r.id = i.id_repositorio"+
                " AND i.padrao_metadados = p.id"+
                " AND i.ldap_destino = l.id"+
                " AND r.id = "+ idRepositorio + ";";

        /*
        String sql = "SELECT r.nome, i.data_ultima_atualizacao, l.ip AS ip_destino, ('ou='||i.nome_na_federacao||','||l.dn) as dn_destino, i.url_or_ip as url, i.tipo_sincronizacao," +
                " l.login as login_ldap_destino, l.senha as senha_ldap_destino, p.nome as padrao_metadados, p.metadata_prefix, p.name_space, l.porta as porta_ldap_destino," +
                " d.login_ldap_origem, d.senha_ldap_origem, d.porta_ldap_origem, d.dn_origem," +
                " DATE_FORMAT(i.data_ultima_atualizacao, '%Y-%m-%dT%H:%i:%sZ') as ultima_atualizacao_form" +
                " FROM repositorios r, info_repositorios i, padraometadados p, dadosldap d, ldaps l" +
                " WHERE r.id=i.id_repositorio" +
                " AND r.id=d.id_repositorio" +
                " AND i.padrao_metadados=p.id" +
                " AND i.ldap_destino=l.id" +
                " AND r.id=" + idRepositorio + ";";
*/
        con = conectar.conectaBD(); //chama o metodo conectaBD da classe Conectar

        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            rs.next();

            Date hora = new Date();

            String nome = rs.getString("nome"); //atribiu a variavel nome o nome do repositorio retornado pela consulta sql

            System.out.println("-> " + hora);
            System.out.println("Atualizando repositorio: " + nome);//imprime o nome do repositorio

            String url = rs.getString("url"); //pega a url retornada pela consulta sql
            String metadataPrefix = rs.getString("metadata_prefix"); // para o OAI-PMH

            if (url.isEmpty()) { //testa se a string url esta vazia.
                System.out.println("Não existe uma url associada ao repositório " + nome);

            } else {//repositorio possui url para atualizacao

                String base = rs.getString("base");
                String ultimaAtualizacao = rs.getString("ultima_atualizacao_form");
                //String horaAtual = rs.getString("horaAtualForm");
                String ip = rs.getString("ip_destino");
                String login = rs.getString("login");
                String senha = rs.getString("senha");
                int porta = rs.getInt("porta");
                

                //Configuracao subFedconf = new Configuracao(base, login, senha, ip, porta);
                
                Date data_ultima_atualizacao = rs.getDate("data_ultima_atualizacao");
                ArrayList<String> caminhoXML = new ArrayList<String>(); //ArrayList que armazenara os caminhos para os xmls

                System.out.println("Ultima Atualização: " + ultimaAtualizacao + " nome do rep: " + nome);


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

                    System.out.println(" ultima atualizacao: " + ultimaAtualizacao);
                    File caminhoTeste = new File(caminhoDiretorioTemporario);
                    if (!caminhoTeste.isDirectory()) {//se o caminho informado nao for um diretorio
                        caminhoTeste.mkdirs();//cria o diretorio
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


    public boolean atualizarComIndice(int idRepositorio) {
        boolean resultado = false;
        return resultado;
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

    public static void main(String[] args) {
        Robo run = new Robo();
        run.testaUltimaIportacao();
    }
}

