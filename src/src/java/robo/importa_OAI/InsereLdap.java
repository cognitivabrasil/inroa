package robo.importa_OAI;

import com.novell.ldap.LDAPAttribute;
import com.novell.ldap.LDAPAttributeSet;
import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPEntry;
import com.novell.ldap.LDAPException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author Marcos
 */
public class InsereLdap {

    /**
     * Método que insere dados no LDAP, recebendo metadados em OBAA e inserindo em OBAA.
     *
     * @param attributeSet Referência para a classe LDAPAttributeSet que contém as entradas a serem inseridas no LDAP.
     * @param header Refêrencia para a classe Header que contém os dados da tag Header do XML.
     * @param dnRecebido dn do LDAP onde serão armazenados os objetos.
     * @param lc Conex&atild;o com o Ldap. Conex&atilde;o e bind.
     */
    public static void insereObaa(DadosParaLdap dadosLdap, Header header, String dnRecebido, LDAPConnection lc) {
      
        String containerName = dnRecebido.trim();

        LDAPAttribute attribute = null;
        LDAPAttributeSet attributeSet = new LDAPAttributeSet();
        
        System.out.println(" =============================");
        System.out.println("  Inserindo objeto: " + header.getIdentifier());
        System.out.println(" =============================\n");

        attributeSet.add(new LDAPAttribute(
                "objectclass", new String[]{"top", "obaa"}));

        //adiciona os valores lidos do xml no atributo ldap
        HashMap<String, ArrayList<String>> atributos = dadosLdap.getAtributos();
        Set<String> chaves = atributos.keySet();
        //percorre todo o HashMap
         for (String chave : chaves) //enquanto tiver chaves chave recebe o conteudo de chaves
         {
             if(chave != null){ // testa se a string chave é diferente de nulo
                 ArrayList<String> lista = dadosLdap.getAtributos().get(chave); //adiciona no ArrayList todos os valores para a chave atual(do for)
                String[] valores = (String[]) lista.toArray (new String[lista.size()]); //transforma o arraylist em um vetor de string pq é só o que o LDAPAttribute aceita para multiplos valores
                attributeSet.add(new LDAPAttribute(chave, valores));
             }
         }
            


        String dn = "obaaEntry=" + header.getIdentifier() + "," + containerName;

        LDAPEntry newEntry = new LDAPEntry(dn, attributeSet);

        //Insert LDAP

        try {
            lc.add(newEntry);

        } catch (LDAPException e) {
            System.out.println("Error:  " + e.toString());
        }

    }
}
