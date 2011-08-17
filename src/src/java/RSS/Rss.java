/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RSS;

import ferramentaBusca.Recuperador;
import java.util.ArrayList;
import java.sql.*;
import postgres.Conectar;
import java.io.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

/**
 *
 * @author antonio
 */
public class Rss {

    private String link;
    static final String feedDescription = "Alimentador de objetos de aprendizagem. ";
    private String textoBusca;
    
    private int rssTamMax = 30;
    private String[] idRepLocal;
    private String[] idSubfed;
    private String[] idSubRep;


    /**
     * Construtor da classe Rss
     * @param search String a ser buscada nos reposit&oacute;rios
     * @param idBusca String com os id dos reposit&oacute;rios (inteiros) separados por v&iacute;rgulas
     * @param link String com a atual url. Como será
     **/
    public Rss(String search, String[] idRepLocal, String[] idSubfed, String[] idSubRep, String link) {
        if (search == null) {
            this.textoBusca = "";
        } else {
            this.textoBusca = search;
        }
        this.idRepLocal = idRepLocal;
        this.idSubfed = idSubfed;
        this.idSubRep = idSubRep;
        this.link = link.substring(0, link.indexOf("rss.jsp"));
    }

    /**
     * Faz a busca da string passada no construtor no banco de dados, utilizando
     * a mesma fun&ccedil;&atilde;o que a busca normal.
     * @return string correspondente ao xml do rss que ser&aacute; gerado
     **/
    public String generateFeed() {
        ArrayList<Integer> idArray = new ArrayList<Integer>();
        Conectar conectar = new Conectar(); //instancia uma variavel da classe conectar
        Connection con = conectar.conectaBD(); //chama o metodo conectaBD da classe conectar

        Recuperador rep = new Recuperador();
        String xml = "";

        try {
            //cria documento xml
            DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            //root do documento
            Element rss = doc.createElement("rss");
            rss.setAttribute("version", "1.0");
            doc.appendChild(rss);
            Element channel = doc.createElement("channel");
            rss.appendChild(channel);

            //titulo, descrição e link do rss
            Element rssTitle = doc.createElement("title");
            Text text = doc.createTextNode("FEB - " + this.textoBusca);
            rssTitle.appendChild(text);
            channel.appendChild(rssTitle);
            Element rssDescription = doc.createElement("description");
            text = doc.createTextNode(feedDescription);
            rssDescription.appendChild(text);
            channel.appendChild(rssDescription);
            Element rssLink = doc.createElement("link");
            text = doc.createTextNode(link);
            rssLink.appendChild(text);
            channel.appendChild(rssLink);


            //faz a busca da string search, com o id do repositorio escolhido na base de dados
            //idDoc é um array com os identificadores correspondentes à pesquisa
            try {
                idArray = rep.busca(this.textoBusca, con, this.idRepLocal, this.idSubfed, this.idSubRep, "data");
            } catch (SQLException e) {
                System.out.println("FEB: RSS - Problemas com a busca\n" + e);
            }
            
            //adiciona um item no rss para cada elemento encontrado
            for (int i = 0; i < idArray.size() && i < rssTamMax; i++) {
                //faz a busca pelo id
                    String titulo = "";
                    String resumo = "";
                    
                    Statement stm = con.createStatement();
//                    String resultadoSQL = "SELECT d.obaa_entry, o.atributo, o.valor, r.nome as repositorio, ds.id as id_base FROM documentos d, dados_subfederacoes ds, repositorios r, objetos o, info_repositorios i WHERE d.id=o.documento AND d.id_repositorio=r.id AND r.id = i.id_repositorio AND i.id_federacao=ds.id AND d.id=" + idArray.get(i);
                    String resultadoSQL = "SELECT d.obaa_entry, o.atributo, o.valor, dsf.id as id_subfed, d.id_repositorio as repositorio FROM documentos d, objetos o, repositorios_subfed rsf, dados_subfederacoes dsf WHERE d.id=o.documento AND d.id_rep_subfed=rsf.id AND rsf.id_subfed=dsf.id AND (o.atributo ~* '^obaaDescription$' OR o.atributo ~* '^obaaTitle$') AND d.id=" + idArray.get(i);
                    try {
                        ResultSet rs = stm.executeQuery(resultadoSQL);
                        //pega o proximo resultado retornado pela consulta sql


                        Element item = doc.createElement("item");
                        channel.appendChild(item);
                        while (rs.next()) {
                            if (rs.isFirst()) {

                                String repositorio = rs.getString("repositorio");
                                String subFed = rs.getString("id_subfed");
                                if(repositorio==null)
                                    repositorio = "0";
                                if(subFed==null)
                                    subFed = "null";

                                Element tag = doc.createElement("idBase");
                                text = doc.createTextNode(subFed);
                                tag.appendChild(text);
                                item.appendChild(tag);

                                tag = doc.createElement("link");
                                text = doc.createTextNode(link + "infoDetalhada.jsp?id=" + rs.getString("obaa_entry")
                                        + "&idBase=" + subFed
                                        + "&repositorio=" + repositorio);
                                tag.appendChild(text);
                                item.appendChild(tag);

                                tag = doc.createElement("repositorio");
                                text = doc.createTextNode(repositorio);
                                tag.appendChild(text);
                                item.appendChild(tag);
                                
                            }

                            String valor = rs.getString("valor");
                            String atributo = rs.getString("atributo");
                            
                            if (atributo.equalsIgnoreCase("obaaTitle")) {
                                titulo += valor;
                            } else if (atributo.equalsIgnoreCase("obaaDescription")) {
                                resumo += valor;
                            } else {
                                Element tag = doc.createElement(atributo);
                                Text tagText = doc.createTextNode(valor);
                                tag.appendChild(tagText);
                                item.appendChild(tag);
                            }
                        }
                        Element tag = doc.createElement("title");
                        text = doc.createTextNode(titulo);
                        tag.appendChild(text);
                        item.appendChild(tag);

                        tag = doc.createElement("description");
                        text = doc.createTextNode(resumo);
                        tag.appendChild(text);
                        item.appendChild(tag);

                    } catch (SQLException e) {
                        System.out.println("FEB: RSS - Nao foi possivel recuperar as informacoes da base de dados" + e);
                    }
                }

            TransformerFactory transfac = TransformerFactory.newInstance();
            Transformer trans = transfac.newTransformer();
            trans.setOutputProperty(OutputKeys.INDENT, "yes");
            trans.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
            trans.setOutputProperty(OutputKeys.VERSION, "1.0");

            //create string from xml tree
            StringWriter sw = new StringWriter();
            StreamResult result = new StreamResult(sw);
            DOMSource source = new DOMSource(doc);
            trans.transform(source, result);
            xml = sw.toString();

            con.close(); //fecha a conexao com a base de dados

        } catch (DOMException e) {
            System.out.print("FEB: Erro gerado pelo DOM para a geração de um RSS\n" + e);
        } catch (SQLException e) {
            System.out.print("FEB: Erro na busca SQL para a geração do RSS\n" + e);
        } catch (TransformerException e) {
            System.out.print("FEB: Erro com o Transformer para a geração do RSS\n" + e);
        } catch (Exception e) {
            System.out.print("FEB: Erro na geração do RSS:" + e);
            e.printStackTrace();
        }
        return xml;

    }


}
