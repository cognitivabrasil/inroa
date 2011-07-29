package robo.atualiza.importa_OAI;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import ferramentaBusca.indexador.Indexador;
import java.sql.Connection;

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

//    public static void main(String[] args) {
//        XmlSaxReader reader = new XmlSaxReader();
//        Indexador indexar = new Indexador();
//        //efetua a leitura do xml e insere os objetos na base de dados
//        try {
//
//            Conectar conectar = new Conectar(); //instancia uma variavel da classe portgres.conectar
//            Connection con = conectar.conectaBD(); //chama o metodo conectaBD da classe portgres.conectar
//
//            reader.parser("C:/request.xml", indexar, con, 14);
//            con.close();
//        } catch (UnsupportedEncodingException e) {
//            System.out.println("Error: " + e.toString());
//        } catch (SAXException e) {
//           String erro = Repositorios.trataErroOAIPMH(e.getMessage());
//            if (erro.isEmpty()) {
//                System.err.println("Problema ao fazer o parse do arquivo. " + e);
//            } else {
//                System.err.println("FEB: Erro no parser do OAI-PMH, mensagem: "+erro);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
