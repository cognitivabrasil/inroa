package robo.main;

import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPException;
import robo.importa_OAI_DC.InicioLeituraXML;
import robo.harvesterOAI.Principal;
import robo.importaLDAP.AtualizaLDAP;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import mysql.AtualizaBase;
import robo.util.Informacoes;
import ferramentaBusca.indexador.Indexador;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import operacoesLdap.Remover;
import mysql.Conectar;

/**
 * Ferramenta de Sincronismo (Robô)
 * @author Marcos
 */
public class Robo {

    Informacoes conf = new Informacoes();
    Principal importar = new Principal();
    InicioLeituraXML gravacao = new InicioLeituraXML();
    AtualizaLDAP atualizaLDAP = new AtualizaLDAP();
    Conectar conectar = new Conectar(); //instancia uma variavel da classe mysql.conectar

    /**
     * Principal m&eacute;todo do rob&ocirc;. Este m&eacute;todo efetua uma consulta na base de dados, procurando por reposit&oacute;rios que est&atilde;o desatualizados, quando encontra algum, efetua a sua atualiza&ccedil;&atilde;o.
     * @author Marcos Nunes
     */
    public void testaUltimaIportacao() {

        String caminhoDiretorioTemporario = conf.getCaminho();
        Indexador indexar = new Indexador();
        Connection con = null;
        boolean atualizou = false;

        String sql = "select r.nome, i.dataUltimaAtualizacao, r.id as idrep, l.ip as ipDestino, concat('ou=',i.nome_na_federacao,',',l.dn) as dnDestino, i.urlorip as url, i.tipoSincronizacao, " +
                "l.login as loginLdapDestino, l.senha as senhaLdapDestino, p.nome as padraoMetadados,l.porta as portaLdapDestino, " +
                "d.loginLdapOrigem, d.senhaLdapOrigem, d.portaLdapOrigem, d.dnOrigem, " +
                "DATE_FORMAT(i.dataUltimaAtualizacao, '%Y-%m-%dT%H:%i:%sZ') as ultimaAtualizacaoForm " +
                "FROM repositorios r, info_repositorios i, padraometadados p, dadosldap d, ldaps l " +
                "WHERE r.id=i.id_repositorio " +
                "AND r.id=d.id_repositorio " +
                "AND i.padraoMetadados=p.id " +
                "AND i.ldapDestino=l.id " +
                "AND r.nome!='todos' " +
                "AND r.nome!='obaa' " +
                "AND i.dataUltimaAtualizacao<DATE_SUB(now(), INTERVAL i.periodicidadeHoras HOUR);";


        con = conectar.conectaBD(); //chama o metodo conectaBD da classe mysql.conectar

        try {

            //Class.forName("com.mysql.jdbc.Driver");
            //con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + baseDeDados, usuario, this.senhaLocal);


            Statement stm = con.createStatement();
            //Statement stmUpdt = con.createStatement();
            ResultSet rs = stm.executeQuery(sql);

            while (rs.next()) {
                Date hora = new Date();
                System.out.println("- " + hora);

                String nome = rs.getString("nome"); //atribiu a variavel nome o nome do repositorio retornado pela consulta sql

                System.out.println("nome do repositorio: " + nome);//imprime o nome do repositorio

                String url = rs.getString("url"); //pega a url retornada pela consulta sql


                if (url.isEmpty()) { //testa se a string url esta vazia.
                    System.out.println("Não existe uma url associada ao repositório " + nome);

                } else {//repositorio possui url para atualizacao

                    String ultimaAtualizacao = rs.getString("ultimaAtualizacaoForm");
                    //String horaAtual = rs.getString("horaAtualForm");
                    String ipDestino = rs.getString("ipDestino");
                    String dnDestino = rs.getString("dnDestino");
                    int idRep = rs.getInt("idrep");
                    String loginDestino = rs.getString("loginLdapDestino");
                    String senhaDestino = rs.getString("senhaLdapDestino");
                    String padraoMetadados = rs.getString("padraoMetadados");
                    int portaLDAPDestino = rs.getInt("portaLdapDestino");
                    String loginOrigem = rs.getString("loginLdapOrigem");
                    String senhaOrigem = rs.getString("senhaLdapOrigem");
                    int portaLDAPOrigem = rs.getInt("portaLdapOrigem");

                    String dnOrigem = rs.getString("dnOrigem");
                    String tipoSinc = rs.getString("tipoSincronizacao");
                    Date dataUltimaAtualizacao = rs.getDate("dataUltimaAtualizacao");
                    ArrayList<String> caminhoXML = new ArrayList<String>(); //ArrayList que aramzanara os caminhos para os xmls



                    //se a data da ultima atualização for inferior a 01/01/1000 apaga todos as informacoes do repositorio o LDAP
                    if (testarDataAnterior(dataUltimaAtualizacao)) {
                        Remover deleta = new Remover();
                        System.out.println("Deletando toda a base de dados do repositório: " + nome.toUpperCase());
                        deleta.setDebugOut(false); //seta que nao e para imprimir mensagens de erro
                        try {

                                    LDAPConnection lc = new LDAPConnection();
                                    lc.connect(ipDestino, portaLDAPDestino); // conecta no servidor ldap
                                    lc.bind(LDAPConnection.LDAP_V3, loginDestino, senhaDestino.getBytes("UTF8")); // autentica no servidor

                        deleta.apagaTodosObjetos(dnDestino, lc); //apaga todos os objetos da base
                        lc.disconnect();
                        } catch (LDAPException e) {
                                    System.out.println("Error:  " + e.toString());
                                } catch (UnsupportedEncodingException e) {
                                    System.out.println("Error: " + e.toString());
                                }
                    }

                    //testar se a sincronicazao eh feita por OAI-PMH ou conexao direta no LDAP
                    if (tipoSinc.equalsIgnoreCase("OAI-PMH")) {


                        if (padraoMetadados.equalsIgnoreCase("Dublin Core")) { //testar em que padrao sera feita a sincrinizacao com o OAI-PMH
                            System.out.println("\nAtualizando repositorio: " + nome + "...\n Está no padrão dublin core e a sincrinização será por: " + tipoSinc);

                            System.out.println(" ultima atualizacao: " + ultimaAtualizacao);

                            File caminhoTeste = new File(caminhoDiretorioTemporario);
                            if (!caminhoTeste.isDirectory()) {//se o caminho informado nao for um diretorio
                                caminhoTeste.mkdirs();//cria o diretorio
                                }

                            if (caminhoTeste.isDirectory()) {
                                //conectar o ldap e mandar a conexao pronta
                                try {

                                    LDAPConnection lc = new LDAPConnection();
                                    lc.connect(ipDestino, portaLDAPDestino); // conecta no servidor ldap
                                    lc.bind(LDAPConnection.LDAP_V3, loginDestino, senhaDestino.getBytes("UTF8")); // autentica no servidor

                                    caminhoXML = importar.buscaXmlRepositorio(url, ultimaAtualizacao, "9999-12-31T00:00:00Z", nome, caminhoDiretorioTemporario); //chama o metodo que efetua o HarvesterVerb grava um xml em disco e retorna um arrayList com os caminhos para os XML
                                    gravacao.leXMLgravaLdapDCtoLom(dnDestino, caminhoXML, idRep, indexar, lc); //chama a classe que le o xml e grava os dados no ldap
                                    atualizou = true;
                                    lc.disconnect(); //desconecta do ldap
                                } catch (LDAPException e) {
                                    System.out.println("Error:  " + e.toString());
                                } catch (UnsupportedEncodingException e) {
                                    System.out.println("Error: " + e.toString());
                                }

                            } else {
                                System.out.println("O caminho informado não é um diretório. E não pode ser criado. " + caminhoDiretorioTemporario);
                            }

                        } else if (padraoMetadados.equalsIgnoreCase("lom")) {
                            System.out.println("\nAtualizando repositorio: " + nome + "...\n Está no padrão LOM e a sincrinização será por: " + tipoSinc);
                            System.out.println(" ultima atualizacao: " + ultimaAtualizacao);
                            System.err.println("Não tem nenhuma classe que faça a sincronização com o padrão LOM!");
                        }

                    } else if (tipoSinc.equalsIgnoreCase("LDAP")) { //se a sincronizacao for por LDAP
                        if (padraoMetadados.equalsIgnoreCase("Dublin Core")) { //se o padrao de metadados for dublincore
                            System.out.println("\nAtualizando repositorio: " + nome + "...\n Está no padrão dublin core e a sincrinização será por: " + tipoSinc);
                            System.out.println(" ultima atualizacao: " + ultimaAtualizacao);
                            System.err.println(" Não tem nenhuma classe que faça a sincronização com o padrão dublin core!");
                        } else if (padraoMetadados.equalsIgnoreCase("lom")) {
                            System.out.println("\nAtualizando repositorio: " + nome + "...\n Está no padrão LOM e a sincrinização será por: " + tipoSinc);
                            System.out.println(" ultima atualizacao: " + ultimaAtualizacao);
                            try {

                                LDAPConnection lc = new LDAPConnection();
                                lc.connect(ipDestino, portaLDAPDestino); // conecta no servidor ldap
                                lc.bind(LDAPConnection.LDAP_V3, loginDestino, senhaDestino.getBytes("UTF8")); // autentica no servidor

                                boolean result = atualizaLDAP.atualizaLDAP(url, dnOrigem, loginOrigem, senhaOrigem, portaLDAPOrigem, "lomIdentifier", dnDestino, lc, indexar);
                                //se o resultado for true atualiza a hora da base
                                if (result) {
                                    System.out.println("Repositorio " + nome + " atualizado com sucesso!");
                                    //atualizar hora da ultima atualização
                                    AtualizaBase atualiza = new AtualizaBase();
                                    //chama metodo que atualiza a hora da ultima atualizacao
                                    atualiza.atualizaHora(idRep);
                                    atualizou = true;
                                }
                                lc.disconnect(); //desconecta do ldap
                                } catch (LDAPException e) {
                                System.out.println("Error:  " + e.toString());
                            } catch (UnsupportedEncodingException e) {
                                System.out.println("Error: " + e.toString());
                            }

                        }
                    }

                }

            }

            if (atualizou) {
                System.out.println("recalculando o indice");
                indexar.populateR1(con);
            }

        } catch (SQLException e) {
            System.err.println("SQL Exception... Erro na consulta:");
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException erro) {
                System.err.println("Erro no fechamento");
                erro.printStackTrace();
            }
        }

    }

    /**
     *  Testa se a data recebida é anterior a 01/01/1000. Utilizado para testar se a base de dados deve ser sincronizada do zero ou não.
     * @param horaBase hora que será testada
     * @return true ou false. Se a data informada como parâmetro for menor retorna true se não false
     */
    public static boolean testarDataAnterior(Date horaBase) {

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

