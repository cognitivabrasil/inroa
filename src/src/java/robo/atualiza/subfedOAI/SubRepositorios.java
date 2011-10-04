/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robo.atualiza.subfedOAI;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import robo.atualiza.harvesterOAI.ListSets;

/**
 *
 * @author Marcos
 */
public class SubRepositorios {
    private ArrayList<String> listaSubRepositorios = new ArrayList<String>();

    public void setListaSubRepositorios(ArrayList<String> listaSubRepositorios) {
        this.listaSubRepositorios = listaSubRepositorios;
    }


    /**
     * Percorre o xml coleta o nome dos reposit&oacute;rios da subfedera&ccedil;&atilde;o e atualiza na base local
     * @param arquivoXML XML de resposta do OAI-PMH
     * @param idSubFed id da subfedera&ccedil;&atilde;o a ser atualizada.
     * @param con conex;&atilde;o com a base de dados
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     * @throws SQLException
     */
    public void AtualizaSubRepositorios(String caminhoArquivoXML, int idSubFed,Connection con) throws ParserConfigurationException, SAXException, IOException, SQLException{
        

        ParserListSets reader = new ParserListSets();
        File arquivoXML = new File(caminhoArquivoXML);
        if (arquivoXML.isFile() || arquivoXML.canRead()) {
                
                ArrayList listaSubrep =  reader.parser(arquivoXML);//efetua a leitura do xml e insere os objetos na base de dados
                atualizaRepSubfed(idSubFed, con, listaSubrep);
                
                //apaga arquivo XML
                arquivoXML.delete();

            } else {
                System.out.println("O arquivo informado não é um arquivo ou não pode ser lido. Caminho: " + caminhoArquivoXML);
            }
    }



    /**
     * M&eacute;todo que atualiza os reposit&oacute;rios da subfedera&ccedil;&atilde;o
     * @param idSubFed id da federa&ccedil;&atilde;ona base local
     * @param conLocal conex&atilde;o com a base de dados local
     * @param listaSubRep ArrayList de Strings contento o nome dos reposit&oacute;rios da subfedera&ccedil;&atilde;o
     * @throws SQLException
     */
    public void atualizaRepSubfed(int idSubFed, Connection conLocal, ArrayList<String> listaSubRep) throws SQLException {
        String selectLocal = "SELECT nome FROM repositorios_subfed WHERE id_subfed=" + idSubFed;

        Statement stmLocal = conLocal.createStatement();
        ResultSet rsLocal = stmLocal.executeQuery(selectLocal);

        
        ArrayList<String> listaLocal = new ArrayList<String>();
        while (rsLocal.next()) {
            listaLocal.add(rsLocal.getString(1));
        }
        rsLocal.close();

        for(int i=0; i<listaSubRep.size();i++){
            
            if (!listaLocal.contains(listaSubRep.get(i))) {
                String insert = "INSERT INTO repositorios_subfed (id_subfed, nome) VALUES (" + idSubFed + ", '" + listaSubRep.get(i) + "')";
                stmLocal.executeUpdate(insert);
            }
        }

        for (int i = 0; i < listaLocal.size(); i++) {
            if (!listaSubRep.contains(listaLocal.get(i))) {
                String delete = "DELETE FROM repositorios_subfed WHERE nome='" + listaLocal.get(i) + "'";
                stmLocal.executeUpdate(delete);
            }
        }

    }


    /**
     * Efetua a coleta do XML utilizando o verbo oai-pmh ListSets.
     * @param endereco URL que responde com o padrao OAI-PMH.
     * @param nomeSubfed nome da subfederacao.
     * @param dirXML diret&oacute;rio onde ser&aacute; salvo o xml.
     * @return String contendo o endere&ccedil;o para o xml salvo.
     * @throws Exception toda e qualquer exce&ccedil;&atilde;o gerada.
     */
    public String coletaXML_ListSets(String endereco, String nomeSubfed, String dirXML) throws Exception {


        String barra = System.getProperty("file.separator"); //barra do sistema operacional muda de win pra linux
        String caminhoAbsoluto = dirXML + barra + "FEB-" + nomeSubfed+ ".xml"; //endereco + nome do arquivo. Utilizado em mais de um local no codigo.


        //efetua por OAI-PMH o verbo lisRecord com a url, a data inicial, o set e o metadataPrefix recebidos como parametro
        ListSets listSets = new ListSets(endereco);

        Writer out = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(caminhoAbsoluto), "UTF8")); //escreve um arquivo xml em UTF8 no caminho setado no configuracao.java


        out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        listSets.getDocument(); //lista o registros retornados
        out.write(listSets.toString()); //imprime no arquivo os registros transformados para string
        out.close();//fecha o arquivo xml que estava sendo escrito


        return caminhoAbsoluto;
    }

}

