package operacoesLdap;


!!!! provocado para dar erros!!!!



import com.novell.ldap.*;
import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import postgres.Conectar;
import postgres.Excluir;
import java.sql.*;
import java.util.ArrayList;

/**
 * Classe com metodos responsáveis por remover dados de uma base Ldap
 *
 * @author Marcos Nunes
 */
public class Remover {

    private int numeroErros = 0;
    private int errosQuePodemOcorrer = 50;
    private static boolean debugOut = false;
    private static boolean debugErr = true;

    /**
     * Método responsável por apagar objetos de um base LDAP
     * @param nomeId Nome do atributo identificador do ojeto a ser apagado. Ex.: obaa_entry
     * @param valorId Valor do atributo identificador do objeto a ser apagado. Ex: obaa0001
     * @param dnRecebido DN da base onde se encontra o objeto
     * @param lc Conex&atilde;o com o ldap. Deve ter conexão e bind realizados.
     * @throws LDAPException Exceção do LDAP
     * @throws UnsupportedEncodingException Exceção de encoding
     */
    public void apagaObjeto(String nomeId, String valorId, String dnRecebido, LDAPConnection lc)
            throws LDAPException, UnsupportedEncodingException, SQLException {

        String identifier = nomeId+ "=" + valorId;
        boolean resultado = false;

        String containerName = dnRecebido.trim();

        String deleteDN = identifier.trim() + "," + containerName;

        // deleta a entrada do diretorio ldap
        lc.delete(deleteDN);


        resultado = Excluir.removerDocumentoIndice(valorId); //exclui o objeto do indice que esta no mysql

        if (resultado) {
            prtln("\nObjeto: " + deleteDN + " foi deletado.");
        } else {
            prtln("\nO objeto foi deletado do ldap mas não foi deletado do indice do mysql");
        }

    }

    /**
     * Método responsável por apagar todos objetos uma base LDAP.
     * @param dn DN da base onde se encontram os objetos
     * @param lc Conex&atilde;o com o ldap. Deve ter conexão e bind realizados.
     * @return Retorna true se não ocorreu nenhum erro ao apagar os objetos ou false se ocorreu algum erro.
     */
    public boolean apagaTodosObjetos(String dn, LDAPConnection lc) {
        boolean resultado = false;

        //SUB busca no metadiretorio
        //int searchScope = LDAPConnection.SCOPE_SUB;
        //busca apenas no mesmo nível
        int searchScope = LDAPConnection.SCOPE_ONE;



        //recebe como parametro a palavra chave ou frase que sera buscada
        String searchFilter = "(obaa_entry=*)";

//        LDAPConnection lc = new LDAPConnection();
        String nomeIdentificador = "";
        String valorIdentificador = "";
        try {
            // connect to the server
//            lc.connect(ldapHost, ldapPort);
            // bind to the server
//            lc.bind(ldapVersion, loginDN, password.getBytes("UTF8"));

            LDAPSearchResults searchResults =
                    lc.search(dn,
                    searchScope,
                    searchFilter,
                    null, // return all attributes
                    false);       // return attrs and values


            while (searchResults.hasMore()) {


                LDAPEntry nextEntry = null;

                nextEntry = searchResults.next();


                LDAPAttribute atributo = new LDAPAttribute(nextEntry.getAttribute("obaa_entry"));
                nomeIdentificador = atributo.getName();
                valorIdentificador = atributo.getStringValue();

                try {
                    apagaObjeto(nomeIdentificador, valorIdentificador, dn, lc); //apaga o objeto
                    resultado = true;
                } catch (LDAPException e) {
                    if (e.getResultCode() == LDAPException.NO_SUCH_OBJECT) {
                        prtlnErr("Error: Não foi encontrado o objeto: " + valorIdentificador.trim() + "," + dn);
                        resultado = true;
                    } else if (e.getResultCode() ==
                            LDAPException.INSUFFICIENT_ACCESS_RIGHTS) {
                        prtlnErr("Error: Insufficient rights");
                        resultado = false;
                    } else {
                        prtlnErr("Error: " + e.toString());
                        resultado = false;
                    }
                }


            }

            // disconnect with the server
//            lc.disconnect();
            resultado = true;
        } catch (LDAPException e) {

            if (e.getResultCode() == LDAPException.SIZE_LIMIT_EXCEEDED) {
                if (this.numeroErros < this.errosQuePodemOcorrer) {
                    this.numeroErros++;
                    apagaTodosObjetos(dn, lc);
                }
            } else if (e.getResultCode() == LDAPException.CONNECT_ERROR) {
                System.out.println("ERRO na conexao! O servidor nao esta acessivel.");
                resultado = false;
            } else {
                prtlnErr("Error LDAP: " + e.toString());
            }
        } catch (UnsupportedEncodingException e) {
            prtlnErr("Error: " + e.toString());
        } catch (SQLException e) {
            prtlnErr("Ocorreu um erro no SQL: " + e);
        }
        return resultado;
    }

    /**
     *  Imprime o texto com o System.out.println, se debug estiver setado com true.
     *
     * @param  s  A string que será impressa.
     */
    private final static void prtln(String s) {
        if (debugOut) {
            System.out.println(s);
        }
    }

    /**
     *  Imprime o texto com o prtlnErr, se debug estiver setado com true.
     *
     * @param  s  A string que será impressa.
     */
    private final static void prtlnErr(String s) {
        if (debugErr) {
            System.err.println(s);
        }
    }

    /**
     *  Define o boolean debugOut. Se for true ao executar o programa as mensagens System.out serão impressas e vice-versa.
     *
     * @param  db  O novo valor de debug
     */
    public void setDebugOut(boolean db) {
        debugOut = db;
    }

    /**
     *  Define o boolean debugErr. Se for true ao executar o programa as mensagens de erro System.err serão impressas e vice-versa.
     *
     * @param  db  O novo valor de debug
     */
    public void setDebugErr(boolean db) {
        debugErr = db;
    }

    /**
     * Exclui nodo da base Ldap. Recebe os dados sobre o Ldap e o nome do nodo a ser excluido.
     * @param nomeNodo nome do nodo que será excluido. Ex. "ou=lume"
     * @param ip ip da base Ldap que terá o nodo excluido
     * @param dn dn da base Ldap
     * @param loginLDAP login da base Ldap
     * @param senhaLDAP senha da base Ldap
     * @param portaLDAP porta da base Ldap
     * @return retorna true ou false, se o processo foi executado com sucesso ou não.
     */
    public boolean removeNodo(String nomeNodo, String ip, String dn, String loginLDAP, String senhaLDAP, int portaLDAP) {
        boolean resultado = false;
        boolean apagaTudo = false;
        String idNodo = "ou" ;
        
        String dnNodo = idNodo+"="+nomeNodo + "," + dn;//contatena o ou= antes do nome do nodo. Cria o dn do nodo, onde estão seus objetos
        //Apagar todos os filhos do nodo
        Remover run = new Remover();
        LDAPConnection lc = new LDAPConnection();
        try {
            // connect to the server
            lc.connect(ip, portaLDAP);
            // bind to the server
            lc.bind(LDAPConnection.LDAP_V3, loginLDAP, senhaLDAP.getBytes("UTF8"));

            apagaTudo = run.apagaTodosObjetos(dnNodo, lc); //apaga os filhos do nodo
            if (apagaTudo) {
                //Apagar o nodo
                run.apagaObjeto(idNodo,nomeNodo, dn, lc); //apaga o nodo
                resultado = true;
            }
        } catch (LDAPException e) {
            if (e.getResultCode() == LDAPException.NO_SUCH_OBJECT) {
                resultado = true;
                prtlnErr("Error: Não foi encontrado o objeto: " + nomeNodo.trim() + "," + dn);
            } else if (e.getResultCode() ==
                    LDAPException.INSUFFICIENT_ACCESS_RIGHTS) {
                prtlnErr("Error: Insufficient rights");
            } else {
                prtlnErr("Error: " + e.toString());
            }
        } catch (UnsupportedEncodingException e) {
            resultado = false;
            prtlnErr("Ocorreu um Erro ao apagar o nodo: " + e);
        } catch (SQLException e) {
            prtlnErr("Ocorreu um erro no SQL: " + e);
        }


        return resultado;
    }

    /**
     * Exclui nodo do Ldap. Os dados são capturados da base de dados mysql,
     * então é necessário passar o id do reposiório do qual o nodo será excluido
     * @param id id da tabela repositorio do mysql. Identificador do repositório que terá o nodo Ldap excluido
     * @param con conexao com o mysql
     * @return retorna true ou false, se o processo foi executado com sucesso ou não.
     */
    public boolean removeNodo(int id, Connection con) {
        boolean resultado = false;
       
        

        boolean apagou = false;

        try {
            Statement stm = con.createStatement();
            //executa a consulta que esta na variavel sql

            String sql2 = "SELECT l.ip, l.dn, l.login, l.senha, l.porta, i.nome_na_federacao as nome_nodo FROM ldaps l, info_repositorios i where i.ldap_destino=l.id AND i.id_repositorio=" + id;
            //pegar dados do mysql para informar ao metodo
            ResultSet infoLdap = stm.executeQuery(sql2);
            infoLdap.next();

            apagou = removeNodo(infoLdap.getString("nome_nodo"), infoLdap.getString("ip"), infoLdap.getString("dn"), infoLdap.getString("login"), infoLdap.getString("senha"), infoLdap.getInt("porta"));
            if (apagou) {
                resultado = true;
            }

            
        } catch (Exception e) {
            resultado = false;
        }
        return resultado;
    }

    /**
     * Exclui linha do arquivo slapd,conf referente a base ldap que está sendo excluida da federação.
     * @param ip Endereço ip do ldap a ser excluído
     * @param dn Dn no ldap a ser excluído
     * @return Retorna true se a exclusão foi realizada com sucesso ou false se o ocorreu algum erro. Possíveis erros: arquivo não existe ou não possui permissão de escrita.
     */
    public boolean excluiLdapSlapd(String ip, String dn) {
        boolean resultado = false;

        String caminhoArquivo = "/etc/openldap/slapd.conf";
        String line;
        ArrayList<String> temporario = new ArrayList<String>();
        File arquivo = new File(caminhoArquivo);

        if (arquivo.exists() || arquivo.canWrite()) {//so vai executar se o arquivo existir e tiver permissao de escrita

            try {
                BufferedReader in = new BufferedReader(new FileReader(caminhoArquivo)); //le o arquivo

                while ((line = in.readLine()) != null) {
                    if (line.contains(ip) && line.contains(dn)) {
                        //ignorar a linha
                    } else {
                        temporario.add(line + "\n");
                    }
                }
                in.close();

                BufferedWriter out = new BufferedWriter(new FileWriter(caminhoArquivo)); //escreve no arquivo
                for (int i = 0; i < temporario.size(); i++) {//percorre o arraylist
                    out.write(temporario.get(i));//escreve a linha no arquivo
                }
                out.close(); //fecha o arquivo de escrita

                resultado = true;
            } catch (IOException e) {
                System.err.println(e); //imprime o erro
            }

        }
        return resultado; //retorna o boolean resultado
    }

    public static void teste() {
        Remover deleta = new Remover();
        System.out.println("Deletando toda a base de dados do repositório: " + "cesta".toUpperCase());
        deleta.setDebugOut(true); //seta que nao e para imprimir mensagens de erro
        try {

            LDAPConnection lc = new LDAPConnection();
            lc.connect("143.54.95.74", 389); // conecta no servidor ldap
            lc.bind(LDAPConnection.LDAP_V3, "cn=Manager,dc=ufrgs,dc=br", "secret".getBytes("UTF8")); // autentica no servidor

            deleta.apagaTodosObjetos("ou=cesta,dc=ufrgs,dc=br", lc); //apaga todos os objetos da base
            lc.disconnect();
        } catch (LDAPException e) {
            System.out.println("Error:  " + e.toString());
        } catch (UnsupportedEncodingException e) {
            System.out.println("Error: " + e.toString());
        }
    }

    public static void main(String[] args) {
        teste();
    }
}
