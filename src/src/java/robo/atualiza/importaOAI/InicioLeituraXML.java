package robo.atualiza.importaOAI;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import ferramentaBusca.indexador.Indexador;
import java.sql.Connection;
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
     * @param con Conex&atilde;o com a base de dados relacional.
     *
     */
    public boolean leXMLgravaBase(
            ArrayList<String> caminhoXML,
            int id,
            Indexador indexar,
            Connection con) throws ParserConfigurationException, SAXException, IOException {
        boolean atualizou = false;

        XmlSaxReader reader = new XmlSaxReader();
        for (int i = 0; i < caminhoXML.size(); i++) {
            File arquivoXML = new File(caminhoXML.get(i));
            if (arquivoXML.isFile() || arquivoXML.canRead()) {

                reader.parser(caminhoXML.get(i), indexar, con, id);//efetua a leitura do xml e insere os objetos na base de dados
                atualizou = true;

                //apaga arquivo XML
                arquivoXML.delete();

            } else {
                System.out.println("O arquivo informado não é um arquivo ou não pode ser lido. Caminho: " + caminhoXML.get(i));
            }
        }
        return atualizou;
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
            Connection con) throws ParserConfigurationException, SAXException, IOException {


        XmlSaxReader reader = new XmlSaxReader();
        File arquivoXML = new File(caminhoXML);
        if (arquivoXML.isFile() || arquivoXML.canRead()) {

            reader.parser(caminhoXML, indexar, con, id);//efetua a leitura do xml e insere os objetos na base de dados

            //apaga arquivo XML
            arquivoXML.delete();

        } else {
            System.out.println("O arquivo informado não é um arquivo ou não pode ser lido. Caminho: " + caminhoXML);
        }

    }
    public static void main(String[] args) {
         XmlSaxReader reader = new XmlSaxReader();
         Indexador indexar = new Indexador();
         Conectar conecta = new Conectar();
         Connection con = conecta.conectaBD();
         try{
        reader.parser("c:/temp/request.xml", indexar, con, 328);//efetua a leitura do xml e insere os objetos na base de dados
        }catch (Exception e){
             System.out.println(e);
        }

    }
}
