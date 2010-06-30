package operacoesLdap;

import com.novell.ldap.LDAPAttribute;
import com.novell.ldap.LDAPAttributeSet;
import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPEntry;
import com.novell.ldap.LDAPException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Classe que disponibiliza operações de inserção no LDAP.
 * @author Marcos Nunes
 */
public class Inserir {

    /**
     * Método que adiciona um novo nodo na base de dados LDAP informada.
     * Recebe os dados do LDAP e dentro do DN adiciona o novo nodo com o nome informado
     * @param nomeNodo Nome do nodo a ser adicionado. Sem o ou= apenas o nome
     * @param ip ip da base LDAP onde será adicionado o novo nodo
     * @param dnRecebido DN da base LDAP
     * @param loginLDAP Login de acesso a base LDAP
     * @param senhaLDAP Senha da base LDAP
     * @param portaLDAP Porta da base LDAP
     */
    public boolean insereNodo(String nomeNodo, String ip, String dnRecebido, String loginLDAP, String senhaLDAP, int portaLDAP) {


        boolean resultado = false;
        int ldapPort = portaLDAP;
        int ldapVersion = LDAPConnection.LDAP_V3;
        /*passa pro ldapHost o ip recebido como parametro retirando eventuais
         *espaços em branco no incio ou no fim da string*/
        String ldapHost = ip.trim();


        String loginDN = loginLDAP;
        String password = senhaLDAP;

        String containerName = dnRecebido.trim();

        LDAPConnection lc = new LDAPConnection();
        //LDAPAttribute attribute = null;
        LDAPAttributeSet attributeSet = new LDAPAttributeSet();



        attributeSet.add(new LDAPAttribute(
                "objectclass", new String[]{"organizationalUnit"}));


        attributeSet.add(new LDAPAttribute("ou", nomeNodo));

        String dn = "ou=" + nomeNodo + "," + containerName;

        LDAPEntry newEntry = new LDAPEntry(dn, attributeSet);

        //Insert LDAP

        try {

            // connect to the server
            lc.connect(ldapHost, ldapPort);

            // authenticate to the server
            lc.bind(ldapVersion, loginDN, password.getBytes("UTF8"));

            lc.add(newEntry);


            // disconnect with the server
            lc.disconnect();
            resultado = true;
        } catch (LDAPException e) {
            System.out.println("Error:  " + e.toString());
            resultado = false;
        } catch (UnsupportedEncodingException e) {
            System.out.println("Error: " + e.toString());
            resultado = false;
        }
        return resultado;
    }

    /**
     * Edita o arquivo slapd.conf, adicionando uma nova linha que aponta para um novo repositório na federação. Recebe como entrada o ip e o dn do novo repositório.
     * @param ip Endereço ip do ldap que contém os metadados do novo repositório
     * @param dn DN do ldap que contém os metadados do novo repositório
     * @return retorna true se a edição foi realizada com sucesso ou false se o ocorreu um erro. Possíveis erros: arquivo não existe ou não possui permissão de escrita.
     */
    public boolean insereRepositorioSldap(String ip, String dn) {
        String caminhoArquivo = "/etc/openldap/slapd.conf";
        File arquivo = new File(caminhoArquivo);
        boolean resultado = false;
        String texto = "uri        \"ldap://" + ip + "/" + dn + "\"";
        if (arquivo.exists() || arquivo.canWrite()) {//so vai executar se o arquivo existir e tiver permissao de escrita

            try {
                BufferedWriter out = new BufferedWriter(new FileWriter(caminhoArquivo, true));
                out.write("\n" + texto); //adiciona uma nova linha e escreve o texto informado
                out.close(); //fecha o BufferedWriter
                resultado = true;
            } catch (IOException e) {
            }
        }
        return resultado;
    }

}

