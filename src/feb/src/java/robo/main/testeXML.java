/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robo.main;

import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPException;
import ferramentaBusca.indexador.Indexador;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import robo.importa_OAI.InicioLeituraXML;
/**
 *
 * @author Marcos
 */
public class testeXML {

    public void xmlTeste(){
        ArrayList<String> caminhoXML = new ArrayList<String>(); //ArrayList que aramzanara os caminhos para os xmls
        InicioLeituraXML gravacao = new InicioLeituraXML();
        try {
Indexador indexar = new Indexador();
                                LDAPConnection lc = new LDAPConnection();
                                lc.connect("143.54.95.74", 389); // conecta no servidor ldap
                                lc.bind(LDAPConnection.LDAP_V3, "cn=Manager,dc=ufrgs,dc=br", "secret".getBytes("UTF8")); // autentica no servidor

//                                caminhoXML = importar.buscaXmlRepositorio(url, ultimaAtualizacao, "9999-12-31T00:00:00Z", nome, caminhoDiretorioTemporario, "oai_obaa"); //chama o metodo que efetua o HarvesterVerb grava um xml em disco e retorna um arrayList com os caminhos para os XML
                                caminhoXML.add("c:/engeo.xml");
                                gravacao.leXMLgravaLdapObaatoObaa("ou=engeo,dc=ufrgs,dc=br", caminhoXML, 8, indexar, lc); //chama a classe que le o xml e grava os dados no ldap

                                lc.disconnect(); //desconecta do ldap
                                } catch (LDAPException e) {
                                System.out.println("Error:  " + e.toString());
                            } catch (UnsupportedEncodingException e) {
                                System.out.println("Error: " + e.toString());
                            }
    }
    public static void main(String[] args) {
        testeXML run = new testeXML();
        run.xmlTeste();
    }
}
