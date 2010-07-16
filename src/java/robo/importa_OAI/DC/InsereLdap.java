package robo.importa_OAI.DC;

import robo.importa_OAI.Header;
import com.novell.ldap.LDAPAttribute;
import com.novell.ldap.LDAPAttributeSet;
import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPEntry;
import com.novell.ldap.LDAPException;
import ferramentaBusca.indexador.Documento;
import ferramentaBusca.indexador.Indexador;
import ferramentaBusca.indexador.StopWordTAD;
import java.sql.Connection;
import java.sql.SQLException;
import mysql.Conectar;
import robo.importa_OAI.OAI_Interface;

/**
 *
 * @author Marcos
 */
public class InsereLdap {

    /**
     * Método que insere dados no LDAP, traduzindo do padrão Dublin Core para Obaa.
     *
     * @param oai_dc Referência para a classe OAI_Interface que contém os objetos lidos do XML.
     * @param header Refêrencia para a classe Header que contém os dados da tag Header do XML.
     * @param dnRecebido dn do LDAP onde serão armazenados os objetos.
     * @param lc Conex&atild;o com o Ldap. Conex&atilde;o e bind.
     */
    public static void insereDCtoObaa(OAI_Interface oai_dc, Header header, String dnRecebido, LDAPConnection lc, Indexador indexar, int idRep) {

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

        Conectar conectar = new Conectar(); //instancia uma variavel da classe mysql.conectar
        Connection con = conectar.conectaBD(); //chama o metodo conectaBD da classe mysql.conectar
        StopWordTAD stWd = new StopWordTAD();
        stWd.load(con);

        Documento doc = new Documento(stWd);

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

        doc.setObaaEntry(header.getIdentifier()); //envia o obaaIdentifier para o indice
        doc.setServidor(idRep); //adiciona no indice o id do repositorio

        if (!dcIdentifier[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaIdentifier", dcIdentifier));
        }

        if (!dcTitle[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaTitle", dcTitle));
            doc.setTitulo(oai_dc.getTitle()); //envia o titulo para o indice
        }

        if (!dcLanguage[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaLanguage", dcLanguage));
        }

        if (!dcDescription[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaDescription", dcDescription));
            doc.setDescricao(oai_dc.getDescription()); //envia descricao para o indice
        }

        if (!dcSubject[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaKeyword", dcSubject));
            doc.setPalavrasChave(oai_dc.getSubject()); //envia palavras-chaves para o indice
        }

        if (!dcCoverage[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaCoverage", dcCoverage));
        }

        if (!dcType[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaLearningResourceType", dcType));
        }

        if (!dcDate[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaDate", dcDate));
            doc.setData(oai_dc.getDate()); //envia data para o indice
        }
        //attributeSet.add( new LDAPAttribute("obaaRole","Publisher"));

        if (!dcCreator[0].isEmpty()) {
            attributeSet.add(new LDAPAttribute("obaaEntity", dcCreator));
            attributeSet.add(new LDAPAttribute("obaaRole", "Autor"));
            doc.setEntidade(oai_dc.getCreator()); //envia o entity para o indice
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
            doc.setLocalizacao(oai_dc.getSource()); //adiciona a localizacao no indice
        } else if (!dcIdentifier[0].isEmpty()) { //em alguns casos o location está no identifier
            doc.setLocalizacao(oai_dc.getIdentifier()); // se nao tiver location envia para o indice o identifier
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

        
        try {
            System.out.println("adicionando documento ao indice");
            indexar.addDoc(doc, con); //adciona o documento no indice mysql
            } catch (SQLException e) {
            System.err.println("Erro ao inserir o documento no indice: " + e.getMessage());
        }
            try {
                con.close(); //fechar conexao mysql
                } catch (SQLException e) {
                System.out.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        
    }
}
