package robo.importa_OAI;

import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import postgres.AtualizaBase;
import ferramentaBusca.indexador.Indexador;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.util.HashMap;
import postgres.Conectar;

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
     * Método que chama o parser xml o qual insere na base de dados os registros contidos nos arquivos xml recebidos como parâmetro.
     * Recebe um ou mais arquivos xml para realizar o parser e inserir objetos.
     *
     * @param caminhoXML ArrayList de Strings contendo caminhos para os arquivos xml
     * @param id id do repositório na base de dados
     * @param indexar variavel do tipo Indexador
     * @param con Conexão com a base de dados relacional.
     *
     */
    public void leXMLgravaBase(
            ArrayList<String> caminhoXML,
            int id,
            Indexador indexar,
            Connection con) {

        try {
            //SAXReader reader = new SAXReader();
            XmlSaxReader reader = new XmlSaxReader();
            for (int i = 0; i < caminhoXML.size(); i++) {
                File arquivoXML = new File(caminhoXML.get(i));
                if (arquivoXML.isFile() || arquivoXML.canRead()) {
                    //efetua a leitura do xml e insere os objetos no ldap
                    reader.parser(caminhoXML.get(i), indexar, con, id);

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
     * Método que chama o parser xml o qual insere na base de dados os registros contidos nos arquivos xml recebidos como parâmetro.
     * Recebe um ou mais arquivos xml para realizar o parser e inserir objetos.
     *
     * @param caminhoXML String contendo o caminho para o arquivo xml
     * @param id id do repositório na base de dados
     * @param indexar variavel do tipo Indexador
     * @param con Conexão com a base de dados relacional.
     *
     */
    public void leXMLgravaBase(
           String caminhoXML,
            int id,
            Indexador indexar,
            Connection con) {

        try {
            XmlSaxReader reader = new XmlSaxReader();
                File arquivoXML = new File(caminhoXML);
                if (arquivoXML.isFile() || arquivoXML.canRead()) {
                    //efetua a leitura do xml e insere os objetos no ldap
                    reader.parser(caminhoXML, indexar, con, id);

                    //apaga arquivo XML
                    arquivoXML.delete();

                    //atualizar hora da ultima atualização
                    AtualizaBase atualiza = new AtualizaBase();
                    //chama metodo que atualiza a hora da ultima atualizacao
                    atualiza.atualizaHora(id);
                } else {
                    System.out.println("O arquivo informado não é um arquivo ou não pode ser lido. Caminho: " + caminhoXML);
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

                        Conectar conectar = new Conectar(); //instancia uma variavel da classe mysql.conectar
                        Connection con = conectar.conectaBD(); //chama o metodo conectaBD da classe mysql.conectar

                        reader.parser("C:/engeo.xml", indexar, con, 8);
                        con.close();
                    } catch (UnsupportedEncodingException e) {
                        System.out.println("Error: " + e.toString());
                    } catch (Exception e){
                        e.printStackTrace();
                    }
    }
}