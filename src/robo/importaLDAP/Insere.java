/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 *
 * Recupera todos os dados de um repositorio LDAP, apaga todos os dados do ldap de destino, e insere todos os dados recebidos do origem
 */
package robo.importaLDAP;

/**
 *
 * @author Marcos
 */
import java.util.HashMap;
import com.novell.ldap.LDAPAttribute;
import com.novell.ldap.LDAPAttributeSet;
import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPEntry;
import com.novell.ldap.LDAPException;
import ferramentaBusca.indexador.Documento;
import ferramentaBusca.indexador.Indexador;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Set;
import mysql.Conectar;

public class Insere {

    private static boolean debugOut = true;
    private static boolean debugErr = true;

    /**
     * Método que recebe um HashMaps contendo metadados no padrão LOM,
     * os traduz para o padrão Obaa e os armazena no LDAP.
     * @param objetos HashMap que contem os metadados a serem inseridos no LDAP. Em key deve conter os nomes dos atributos e em value os valores dos atributos.
     * @param dnRecebido dn do LDAP onde serão armazenados os objetos.
     * @param lc Conex&atild;o com o Ldap. Conex&atilde;o e bind.
     * @param indexar vari&aacute;vel da classe Indexador
     * @return true se o objeto foi inserido com sucesso ou false se ocorreu algum erro
     */
    public boolean insereLomtoObaa(HashMap objetos, String dnRecebido, LDAPConnection lc, Indexador indexar) {
        boolean resultado = false;

        Documento doc = new Documento();

//        int ldapPort = portaLDAP;
//        int ldapVersion = LDAPConnection.LDAP_V3;
        /*passa pro ldapHost o ip recebido como parametro retirando eventuais
         *espaços em branco no incio ou no fim da string*/
//        String ldapHost = ip.trim();


//        String loginDN = loginLDAP;
//        String password = senhaLDAP;

        String containerName = dnRecebido.trim();

//        LDAPConnection lc = new LDAPConnection();
//        LDAPAttribute attribute = null;
        LDAPAttributeSet attributeSet = new LDAPAttributeSet();


        prtln("=============================");
        prtln("Inserindo objeto: " + objetos.get("lomIdentifier").toString());
        prtln("=============================\n");

        attributeSet.add(new LDAPAttribute(
                "objectclass", new String[]{"top", "obaa"}));



        /*funcao recursiva que adiciona todos os atributos contidos no hashmap*/
        //adiciona a atributos as chaves do hashmap objetos
        Set atributos = objetos.keySet();
        Iterator iter = atributos.iterator();
        while (iter.hasNext()) {
            Object o = iter.next();
            //atributoLom lom recebe uma string com o nome do atributo lom
            String atributoLom = o.toString();
            //nao adicionar no ldap quando o key for obaaIdentifierRaiz e quando o valor do atributo estiver em branco.
            if (!atributoLom.contains("obaaIdentifierRaiz") && !objetos.get(atributoLom).toString().isEmpty()) {
                //atributoObaa lom recebe uma string com o nome do atributo substituindo lom por obaa.
                String atributoObaa = o.toString().replaceFirst("lom", "obaa");
                //ignora atributos que estão fora do padrão lom.
                if (!atributoObaa.contains("obaaCaracUsuario") && !atributoObaa.contains("obaaCaracDesc") && !atributoObaa.contains("obaaCaracAmbiente") && !atributoObaa.contains("obaaContEnt") && !atributoObaa.contains("obaaCaracTipo") && !atributoObaa.contains("obaaCaracFaixa")) {

                    String[] valorTemporario = objetos.get(atributoLom).toString().split(";; ");
                    attributeSet.add(new LDAPAttribute(atributoObaa, valorTemporario));

                    //if's que inserem os dados no indice
                    if (atributoObaa.equalsIgnoreCase("obaaTitle")) {
                        for (int i = 0; i < valorTemporario.length; i++) {
                            doc.setTitulo(valorTemporario[i]);
                        }
                    }//fim if titulo
                    else if (atributoObaa.equalsIgnoreCase("obaaIdentifier")) {
                        for (int i = 0; i < valorTemporario.length; i++) {
                            doc.setObaaEntry(valorTemporario[i]);
                        }
                    }//fim if obaaEntry
                    else if (atributoObaa.equalsIgnoreCase("obaaDescription")) {
                        for (int i = 0; i < valorTemporario.length; i++) {
                            doc.setDescricao(valorTemporario[i]);
                        }
                    }//fim if Description
                    else if (atributoObaa.equalsIgnoreCase("obaaEntity")) {
                        for (int i = 0; i < valorTemporario.length; i++) {
                            doc.setEntidade(valorTemporario[i]);
                        }
                    }//fim if Entity
                    else if (atributoObaa.equalsIgnoreCase("obaaKeyword")) {
                        for (int i = 0; i < valorTemporario.length; i++) {
                            doc.setPalavrasChave(valorTemporario[i]);
                        }
                    }//fim if Keyword
                }//fim if ignorar alguns campos
            }
        }
        /*fim da funcao recursiva*/


        String dn = "obaaEntry=" + objetos.get("lomIdentifier") + "," + containerName;

        LDAPEntry newEntry = new LDAPEntry(dn, attributeSet);

        //Insert LDAP

        try {

            lc.add(newEntry);

            prtln("\nObjecto: " + dn + " inserido com successo.");
            resultado=true;

            // disconnect with the server
//            lc.disconnect();

            Conectar conecta = new Conectar();
            Connection con=conecta.conectaBD();
            indexar.addDoc(doc, con);
            con.close();
        } catch (LDAPException e) {
            prtlnErr("Error:  " + e.toString());
        } catch (SQLException e){
            prtlnErr("Erro ao inserir documento no indice: "+e);
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
}
