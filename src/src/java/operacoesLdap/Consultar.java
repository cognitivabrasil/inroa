package operacoesLdap;

import com.novell.ldap.LDAPAttribute;
import com.novell.ldap.LDAPAttributeSet;
import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPEntry;
import com.novell.ldap.LDAPException;
import com.novell.ldap.LDAPSearchConstraints;
import com.novell.ldap.LDAPSearchResults;
import java.util.Enumeration;
import java.util.Iterator;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Disponibiliza método para efetuar consultas no Ldap
 * @author Marcos Nunes
 */
public class Consultar {

    ArrayList<HashMap> resultado = new ArrayList<HashMap>();

    /**
     * Contrutor responsável por efetuar consulta na base de dados LDAP.
     * @param ipServer Endereço ip do ldap a ser consultado
     * @param consulta Consulta a ser realizada no ldap, deve ser no padrão ldap. Por exemplo: "(obaaTitle=tituloaserprocurado)"
     * @param searchBase DN do ldap a ser consultado
     * @param login Login do ldap
     * @param senha Senha do ldap
     * @param porta porta do ldap
     */
    public Consultar(String ipServer, String consulta, String searchBase, String login, String senha, int porta, String[] atributos) {
        
        
        int ldapPort = porta;
        //SUB busca no metadiretorio
        int searchScope = LDAPConnection.SCOPE_SUB;

        //int searchScope = LDAPConnection.SCOPE_ONE;
        int ldapVersion = LDAPConnection.LDAP_V3;

        String ldapHost = ipServer;
        String loginDN;
        String password;
        //se for informado null no campo login o loginDN recebera ""
        if (login == null) {
            loginDN = "";
        } else {
            loginDN = login;
        }
        //se for informado null no campo senha o password recebera ""
        if (senha == null) {
            password = "";
        } else {
            password = senha;
        }




        //recebe como parametro a palavra chave ou frase que sera buscada
        String searchFilter = consulta;

        LDAPConnection lc = new LDAPConnection();

        try {

            
            // connect to the server
            lc.connect(ldapHost, ldapPort);

            LDAPSearchConstraints cons = lc.getSearchConstraints();
            cons.setMaxResults(0);//seta o limite de resultados. zero significa sem limites
            cons.setServerTimeLimit(60); //seta o tempo limite para 60 segundos

            // bind to the server
            lc.bind(ldapVersion, loginDN, password.getBytes("UTF8"));
           

            LDAPSearchResults searchResults =
                    lc.search(searchBase,
                    searchScope,
                    searchFilter,
                    atributos, // retorna os atributos selecionados
                    false, // return attrs and values
                    cons);
              
            
                    searchResults.hasMore();
           
              

            while (searchResults.hasMore()) {
              
                HashMap resultInterno = new HashMap();

                LDAPEntry nextEntry = null;
                try {
                    nextEntry = searchResults.next();
                } catch (LDAPException e) {
                    System.out.println("Error: " + e.toString());
                    // Exception is thrown, go for next entry
                    if (e.getResultCode() == LDAPException.LDAP_TIMEOUT || e.getResultCode() == LDAPException.CONNECT_ERROR) {
                        break;
                    } else {
                        continue;
                    }
                }

//                System.out.println("\n" + nextEntry.getDN());
//                System.out.println("  Attributes: ");
                resultInterno.put("obaaIdentifierRaiz", nextEntry.getDN());

                LDAPAttributeSet attributeSet = nextEntry.getAttributeSet();
                Iterator allAttributes = attributeSet.iterator();

                while (allAttributes.hasNext()) {
                
                    LDAPAttribute attribute =
                            (LDAPAttribute) allAttributes.next();
                    String attributeName = attribute.getName();
                    //System.out.println("    " + attributeName);

                    Enumeration allValues = attribute.getStringValues();

                    if (allValues != null) {
                        while (allValues.hasMoreElements()) {
                 
                            String Value = (String) allValues.nextElement();

                            if (resultInterno.get(attributeName) == null) {
                                resultInterno.put(attributeName, Value.trim());
                            } else {
                                String temporaria = resultInterno.get(attributeName).toString();
                                resultInterno.put(attributeName, temporaria.concat(";; " + Value.trim()));
                            }

                        }

                    }

                }
                resultado.add(resultInterno);

            }
            // disconnect with the server
            lc.disconnect();
        } catch (LDAPException e) {
            System.out.println("Error: " + e.toString());
            System.err.println("Nao foi possivel efetuar a consulta");
        } catch (UnsupportedEncodingException e) {
            System.out.println("Error: " + e.toString());
            System.err.println("Nao foi possivel efetuar a consulta");
        }

    }

    /**
     * Retorna os resultados da busca efetuada pelo contrutor.
     * @return Retorna um ArrayList de HashMap
     */
    public ArrayList<HashMap> getResultado() {
        return resultado;
    }


    public static void main(String[] args) {
        String[] arg = {"obaaTitle", "obaaEntry"};
        Consultar run = new Consultar("143.54.95.74", "obaaKeyword=couro", "dc=br", "cn=Manager,dc=ufrgs,dc=br", "secret", 389, arg);
        System.out.println(run.getResultado());
    }
}
