package robo.importa_OAI;

import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import mysql.AtualizaBase;
import ferramentaBusca.indexador.Indexador;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.util.HashMap;
import mysql.Conectar;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Marcos
 */
public class InicioLeituraXML {

    /**
     * Método que chama o parser xml o qual insere no LDAP os registros contidos no arquivo xml recebido como parâmetro.
     *
     * Recebe apenas um arquivo xml para realizar o parser e adicionar no LDAP.
     *
     * @param ip String contendo o endereço ip do repositório onde serão armazenados os objetos contidos no(s) XML
     * @param dn DN do repositório que receberá os objetos. Ex.: "ou=lom,ou=pgie,dc=ufrgs,dc=br"
     * @param caminhoXML String contento o caminho para um arquivo xml
     * @param id id que identifica o repositório na base de dados mysql
     * @param loginLDAP login do LDAP local que receberá os objetos
     * @param senhaLDAP senha do LDAP local que receberá os objetos
     * @param portaLDAP porta do LDAP local que receberá os objetos
     * @param indexar variavel do tipo Indexador
     */
//    public void leXMLgravaLdapDCtoObaa(String ip,
//            String dn,
//            String caminhoXML,
//            int id,
//            String loginLDAP,
//            String senhaLDAP,
//            int portaLDAP,
//            Indexador indexar,
//            LDAPConnection lc) {
//        try {
//            //SAXReader reader = new SAXReader();
//            XmlSaxReader reader = new XmlSaxReader();
//            //efetua a leitura do xml e insere os objetos no ldap
//            reader.parser(dn, caminhoXML, indexar, lc, id);
//            //apaga arquivo XML
//            File apagar = new File(caminhoXML);
//            apagar.delete();
//            //atualizar hora da ultima atualização
//            AtualizaBase atualiza = new AtualizaBase();
//            //chama metodo que atualiza a hora da ultima atualizacao
//            atualiza.atualizaHora(id);
//
//
//        } catch (ParserConfigurationException e) {
//            System.err.println("O parser nao foi configurado corretamente. " + e);
//            e.printStackTrace();
//        } catch (SAXException e) {
//            System.err.println("Problema ao fazer o parse do arquivo. " + e);
//            e.printStackTrace();
//        } catch (IOException e) {
//            System.err.println("O arquivo nao pode ser lido. " + e);
//            e.printStackTrace();
//        }
//    }

    /**
     * Método que chama o parser xml o qual insere no LDAP os registros contidos nos arquivos xml recebidos como parâmetro.
     * Recebe um ou mais arquivos xml para realizar o parser e inserir objetos no LDAP local.
     *
     * @param dn dn do repositório que receberá os objetos. Ex.: "ou=lom,ou=pgie,dc=ufrgs,dc=br"
     * @param caminhoXML ArrayList de Strings contendo caminhos para os arquivos xml
     * @param id id do repositório na base de dados mysql
     * @param indexar variavel do tipo Indexador
     * @param lc Conex&atilde;o com o ldap. Deve ter conexão e bind realizados.
     * @param con Conexão com a base de dados relacional.
     *
     */
    public void leXMLgravaLdap(
            String dn,
            ArrayList<String> caminhoXML,
            int id,
            Indexador indexar,
            LDAPConnection lc,
            Connection con) {

        try {
            //SAXReader reader = new SAXReader();
            XmlSaxReader reader = new XmlSaxReader();
            for (int i = 0; i < caminhoXML.size(); i++) {
                File arquivoXML = new File(caminhoXML.get(i));
                if (arquivoXML.isFile() || arquivoXML.canRead()) {
                    //efetua a leitura do xml e insere os objetos no ldap
                    reader.parser(dn, caminhoXML.get(i), indexar, lc, con, id);

                    //apaga arquivo XML
                    arquivoXML.delete();

                    //atualizar hora da ultima atualização
                    AtualizaBase atualiza = new AtualizaBase();
                    //chama metodo que atualiza a hora da ultima atualizacao
                    atualiza.atualizaHora(id);
                } else {
                    System.out.println("O arquivo informado não é um arquivo ou não pode ser lido. Caminho: " + caminhoXML.get(i));
                }
            }

        } catch (ParserConfigurationException e) {
            System.err.println("O parser nao foi configurado corretamente. " + e);
            e.printStackTrace();
        } catch (SAXException e) {
            System.err.println("Problema ao fazer o parse do arquivo. " + e);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("O arquivo nao pode ser lido. " + e);
            e.printStackTrace();
        }
    }


    /**
     * Método que chama o parser xml o qual insere no LDAP os registros contidos nos arquivos xml recebidos como parâmetro.
     * Recebe um ou mais arquivos xml para realizar o parser e inserir objetos no LDAP local.
     *
     * @param dn dn do repositório que receberá os objetos. Ex.: "ou=lom,ou=pgie,dc=ufrgs,dc=br"
     * @param caminhoXML ArrayList de Strings contendo caminhos para os arquivos xml
     * @param id id do repositório na base de dados mysql
     * @param indexar variavel do tipo Indexador
     * @param lc Conex&atilde;o com o ldap. Deve ter conexão e bind realizados.
     *
     */
    public void leXMLgravaLdapDCtoObaa(
            String dn,
            ArrayList<String> caminhoXML,
            int id,
            Indexador indexar,
            LDAPConnection lc) {

        try {
            //SAXReader reader = new SAXReader();
            robo.importa_OAI.DC.XmlSaxReader reader = new robo.importa_OAI.DC.XmlSaxReader();
            for (int i = 0; i < caminhoXML.size(); i++) {
                File arquivoXML = new File(caminhoXML.get(i));
                if (arquivoXML.isFile() || arquivoXML.canRead()) {
                    //efetua a leitura do xml e insere os objetos no ldap
                    reader.parser(dn, caminhoXML.get(i), indexar, lc, id);
                    
                    //apaga arquivo XML
                    arquivoXML.delete();


                    //atualizar hora da ultima atualização
                    AtualizaBase atualiza = new AtualizaBase();
                    //chama metodo que atualiza a hora da ultima atualizacao
                    atualiza.atualizaHora(id);
                } else {
                    System.out.println("O arquivo informado não é um arquivo ou não pode ser lido. Caminho: " + caminhoXML.get(i));
                }
            }


        } catch (ParserConfigurationException e) {
            System.err.println("O parser nao foi configurado corretamente. " + e);
            e.printStackTrace();
        } catch (SAXException e) {
            System.err.println("Problema ao fazer o parse do arquivo. " + e);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("O arquivo nao pode ser lido. " + e);
            e.printStackTrace();
        }
    }

        /**
     * Método que chama o parser xml do padr&atilde;o OBAA o qual insere no LDAP os registros contidos nos arquivos xml recebidos como parâmetro.
     * Recebe um ou mais arquivos xml para realizar o parser e inserir objetos no LDAP local.
     *
     * @param dn dn do repositório que receberá os objetos. Ex.: "ou=lom,ou=pgie,dc=ufrgs,dc=br"
     * @param caminhoXML ArrayList de Strings contendo caminhos para os arquivos xml
     * @param id id do repositório na base de dados mysql
     * @param indexar variavel do tipo Indexador
     * @param lc Conex&atilde;o com o ldap. Deve ter conexão e bind realizados.
     *
     */
    public void leXMLgravaLdapObaatoObaa(
            String dn,
            ArrayList<String> caminhoXML,
            int id,
            Indexador indexar,
            LDAPConnection lc) {

        robo.importa_OAI.OBAA.XmlSaxReader reader = new robo.importa_OAI.OBAA.XmlSaxReader();

        try {
            for (int i = 0; i < caminhoXML.size(); i++) {
                File arquivoXML = new File(caminhoXML.get(i));
                if (arquivoXML.isFile() || arquivoXML.canRead()) {
                    //efetua a leitura do xml e insere os objetos no ldap
                    reader.parser(dn, caminhoXML.get(i), indexar, lc, id);

                    //apaga arquivo XML
                    arquivoXML.delete();


                    //atualizar hora da ultima atualização
                    AtualizaBase atualiza = new AtualizaBase();
                    //chama metodo que atualiza a hora da ultima atualizacao
                    atualiza.atualizaHora(id);
                } else {
                    System.out.println("O arquivo informado não é um arquivo ou não pode ser lido. Caminho: " + caminhoXML.get(i));
                }
            }


        } catch (ParserConfigurationException e) {
            System.err.println("O parser nao foi configurado corretamente. " + e);
            e.printStackTrace();
        } catch (SAXException e) {
            System.err.println("Problema ao fazer o parse do arquivo. " + e);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("O arquivo nao pode ser lido. " + e);
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        XmlSaxReader reader = new XmlSaxReader();
        Indexador indexar = new Indexador();
            //efetua a leitura do xml e insere os objetos no ldap
        try {

                        LDAPConnection lc = new LDAPConnection();
                        lc.connect("143.54.95.74", 389); // conecta no servidor ldap
                        lc.bind(LDAPConnection.LDAP_V3, "cn=Manager,dc=ufrgs,dc=br", "secret".getBytes("UTF8")); // autentica no servidor
                        Conectar conectar = new Conectar(); //instancia uma variavel da classe mysql.conectar
                        Connection con = conectar.conectaBD(); //chama o metodo conectaBD da classe mysql.conectar

                        reader.parser("ou=teste,dc=ufrgs,dc=br", "C:/engeo.xml", indexar, lc, con, 9);
                        lc.disconnect();
                    } catch (LDAPException e) {
                        System.out.println("Error:  " + e.toString());
                    } catch (UnsupportedEncodingException e) {
                        System.out.println("Error: " + e.toString());
                    } catch (Exception e){
                        e.printStackTrace();
                    }


    }

}
