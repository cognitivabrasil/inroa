/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robo.atualiza.subfedOAI;

import ferramentaBusca.indexador.Indexador;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import postgres.Conectar;
import robo.atualiza.harvesterOAI.Principal;
import robo.util.Informacoes;
import robo.util.Operacoes;

/**
 *
 * @author Marcos
 */
public class Objetos {

    public void atualizaObjetosSubFed(String endereco, String dataInicial, String nomeSubfed, String metadataPrefix, String set, Connection con, Indexador indexar) throws Exception {
        ArrayList<String> caminhosXML;
        Informacoes conf = new Informacoes();
        String caminhoDiretorioTemporario = conf.getCaminho();


        File caminhoTeste = Operacoes.testaDiretorioTemp(caminhoDiretorioTemporario);
        if (caminhoTeste.isDirectory()) {

            //efetua o Harvester e grava os xmls na pasta temporaria
            Principal harvesterOAI = new Principal();
            caminhosXML = harvesterOAI.coletaXML_ListRecords(endereco, dataInicial, nomeSubfed, caminhoDiretorioTemporario, metadataPrefix, set);
            
            //efetua o parser do xml e insere os documentos na base
            parserObjetos(caminhosXML, indexar, con);
        }

    }

    private HashMap<String, Integer> recuperaDadosSubRep(Connection con) throws SQLException {
        HashMap<String, Integer> dadosSubRep = new HashMap<String, Integer>();
        String sql = "SELECT nome, id FROM repositorios_subfed;";
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery(sql);
        while (rs.next()) {
            dadosSubRep.put(rs.getString(1), rs.getInt(2));
        }
        return dadosSubRep;
    }

    /**
     * Método que chama o parser xml o qual insere na base de dados os registros da subfedera&ccecul&atilde;o contidos nos arquivos xml recebidos como parâmetro.
     * Recebe um ou mais arquivos xml para realizar o parser e inserir objetos.
     *
     * @param caminhoXML ArrayList de Strings contendo caminhos para os arquivos xml
     * @param indexar variavel do tipo Indexador
     * @param con Conex&atilde;o com a base de dados relacional.
     * 
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     * @throws SQLException
     */
    public boolean parserObjetos(
            ArrayList<String> caminhoXML,
            Indexador indexar,
            Connection con) throws ParserConfigurationException, SAXException, IOException, SQLException {
        boolean atualizou = false;

         ParserObjetos parserObj = new ParserObjetos();
         HashMap<String, Integer> dadosSubRep = recuperaDadosSubRep(con);

        for (int i = 0; i < caminhoXML.size(); i++) {
            File arquivoXML = new File(caminhoXML.get(i));
            if (arquivoXML.isFile() || arquivoXML.canRead()) {
                parserObj.parser(caminhoXML.get(i), indexar, con, dadosSubRep);//efetua a leitura do xml e insere os objetos na base de dados
                atualizou = true;

                //apaga arquivo XML
                arquivoXML.delete();

            } else {
                System.out.println("O arquivo informado não é um arquivo ou não pode ser lido. Caminho: " + caminhoXML.get(i));
            }
        }
        return atualizou;
    }

    public static void main(String[] args) {
        
        Conectar conecta = new Conectar();
        Connection con = conecta.conectaBD();
        Indexador indexar = new Indexador();
        Informacoes info = new Informacoes();
        String arquivo = info.getCaminho()+info.getBarra()+"oi.xml";
        ArrayList<String> arq = new ArrayList<String>();
        arq.add(arquivo);
        Objetos obj = new Objetos();
        try {
            obj.parserObjetos(arq, indexar, con);
            con.close();
        } catch (Exception s) {
            System.out.println(s);
        }

    }
}
