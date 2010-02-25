package robo.importa_OAI_DC;

import com.novell.ldap.LDAPAttribute;
import com.novell.ldap.LDAPAttributeSet;
import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPEntry;
import com.novell.ldap.LDAPException;


/**
 *
 * @author Marcos
 */
public class InsereLdap {

    /**
     * Método que insere dados no LDAP, traduzindo do padrão Dublin Core para Obaa.
     *
     * @param oai_dc Referência para a classe OAI_DC que contém os objetos lidos do XML.
     * @param header Refêrencia para a classe Header que contém os dados da tag Header do XML.
     * @param dnRecebido dn do LDAP onde serão armazenados os objetos.
     * @param lc Conex&atild;o com o Ldap. Conex&atilde;o e bind.
     */
    public static void insereDCtoObaa(OAI_DC oai_dc, Header header, String dnRecebido, LDAPConnection lc) {
        
        String[] dcIdentifier = oai_dc.getIdentifier().split(";;");
        String[] dcTitle = oai_dc.getTitle().split(";;");
        String[] dcLanguage = oai_dc.getLanguage().split(";;");
        String[] dcDescription = oai_dc.getDescription().split(";;");

        String[] dcCoverage = oai_dc.getCoverage().split(";;");
        String[] dcType = oai_dc.getType().split(";;");
        String[] dcDate = oai_dc.getDate().split(";;");
        String[] dcCreator = oai_dc.getCreator().split(";;");
        String[] dcOtherContributor = oai_dc.getOtherContributor().split(";;");
        String[] dcPublisher = oai_dc.getPublisher().split(";;");
        String[] dcFormat = oai_dc.getFormat().split(";;");
        String[] dcRigths = oai_dc.getRigths().split(";;");
        String[] dcRelation = oai_dc.getRelation().split(";;");
        String[] dcSource = oai_dc.getSource().split(";;");
        String[] dcSubject = oai_dc.getSubject().split(";;");

//        int ldapPort = portaLDAP;
//        int ldapVersion = LDAPConnection.LDAP_V3;
        /*passa pro ldapHost o ip recebido como parametro retirando eventuais
         *espaços em branco no incio ou no fim da string*/
//        String ldapHost = ip.trim();


//        String loginDN = loginLDAP;
//        String password = senhaLDAP;

        String containerName = dnRecebido.trim();

//        LDAPConnection lc = new LDAPConnection();
        LDAPAttribute attribute = null;
        LDAPAttributeSet attributeSet = new LDAPAttributeSet();


        System.out.println(" =============================");
        System.out.println("  Inserindo objeto: " + header.getIdentifier());
        System.out.println(" =============================\n");

        attributeSet.add(new LDAPAttribute(
                "objectclass", new String[]{"top", "obaa"}));



        //System.out.println(numerador);



        if (!dcIdentifier[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaIdentifier", dcIdentifier));
        }

        if (!dcTitle[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaTitle", dcTitle));
        }

        if (!dcLanguage[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaLanguage", dcLanguage));
        }

        if (!dcDescription[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaDescription", dcDescription));
        }

        if (!dcSubject[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaKeyword", dcSubject));
        }

        if (!dcCoverage[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaCoverage", dcCoverage));
        }

        if (!dcType[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaLearningResourceType", dcType));
        }

        if (!dcDate[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaDate", dcDate));
        }
        //attributeSet.add( new LDAPAttribute("obaaRole","Publisher"));

        if (!dcCreator[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaEntity", dcCreator));
            attributeSet.add(new LDAPAttribute("obaaRole", "Autor"));
        }

        if (!dcOtherContributor[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaEntity", dcOtherContributor));
            attributeSet.add(new LDAPAttribute("obaaRole", "Nome do Contribuinte"));
        }

        if (!dcPublisher[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaEntity", dcPublisher));
            attributeSet.add(new LDAPAttribute("obaaRole", "Publisher"));
        }
        String temp = "";
        if (!dcFormat[0].isEmpty()) {
            for (int i = 0; i < dcFormat.length; i++) {

                if (dcFormat[i].substring(0, 1).matches("[0-9]")) {
                    attributeSet.add(new LDAPAttribute("obaaSize", dcFormat[i]));
                } else {
                    if (temp.isEmpty()) {
                        temp = dcFormat[i];
                    } else {
                        temp = temp.concat(";;" + dcFormat[i]);
                    }
                }
            }

            if (!temp.isEmpty()) {
                attributeSet.add(new LDAPAttribute("obaaFormat", temp.split(";;")));
            }
        }

        if (!dcRigths[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaRightsDescription", dcRigths));
        }

        if (!dcRelation[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaResourceDescription", dcRelation));
        }

        if (!dcSource[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaLocation", dcSource));
        }


        String dn = "obaaEntry=" + header.getIdentifier() + "," + containerName;

        LDAPEntry newEntry = new LDAPEntry(dn, attributeSet);

        //Insert LDAP

        try {

            // connect to the server
//            lc.connect(ldapHost, ldapPort);

            // authenticate to the server
//            lc.bind(ldapVersion, loginDN, password.getBytes("UTF8"));

            lc.add(newEntry);


            // disconnect with the server
//            lc.disconnect();
        } catch (LDAPException e) {
            System.out.println("Error:  " + e.toString());
        }

    }
}
