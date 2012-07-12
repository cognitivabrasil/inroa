/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RSS;

import feb.ferramentaBusca.Recuperador;

import java.sql.*;
import java.io.*;
import java.util.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import modelos.Consulta;
import modelos.DocumentoReal;

/**
 *
 * @author antonio
 */
public class Rss {

    private String link;
    static final String feedDescription = "Alimentador de objetos de aprendizagem. ";    
    private Consulta c;
    private int rssTamMax = 30;

    /**
     * Construtor da classe Rss
     * @param search String a ser buscada nos reposit&oacute;rios
     * @param idBusca String com os id dos reposit&oacute;rios (inteiros) separados por v&iacute;rgulas
     * @param link String com a atual url. Como será
     **/
    public Rss(String search, String[] idRepLocal, String[] idSubfed, String[] idSubRep, String link) {
        if (search == null) {
            c.setConsulta("");
        } else {
            c.setConsulta(search);
        }
        
        Set repositorios = new TreeSet();
        
        for (int i=0;i>idRepLocal.length;i++){
            repositorios.add(idRepLocal[i]);
        }
        
        c.setRepositorios(repositorios);
        
        Set subFed = new TreeSet();
        
        for (int i=0;i>idSubfed.length;i++){
            subFed.add(idSubfed[i]);
        }
        
        c.setFederacoes(repositorios);
        
        Set repSubfed = new TreeSet();
        
        for (int i=0;i>idSubRep.length;i++){
            repSubfed.add(idSubRep[i]);
        }
        
        c.setRepSubfed(repositorios);
        
        
        this.link = link.substring(0, link.indexOf("rss.jsp"));
    }

    /**
     * Faz a busca da string passada no construtor no banco de dados, utilizando
     * a mesma fun&ccedil;&atilde;o que a busca normal.
     * @return string correspondente ao xml do rss que ser&aacute; gerado
     **/
    public String generateFeed() {
        //ArrayList<Integer> idArray = new ArrayList<Integer>();
        Connection con = null; //TODO: tirei a conexao com o postgres, tem que refatorar para usar o hibernate.
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
            Text text = doc.createTextNode("FEB - " + this.c.getConsulta());
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


            c.setRss(true);
            //faz a busca da string search, com o id do repositorio escolhido na base de dados
            //idDoc é um array com os identificadores correspondentes à pesquisa
           
           List<DocumentoReal> docs = rep.busca(c);
           
           Iterator<DocumentoReal> idocs = docs.iterator();
            //adiciona um item no rss para cada elemento encontrado
           int i=0;
            while (idocs.hasNext() && i<rssTamMax) {
                i++;
                //faz a busca pelo id
                    String titulo = "";
                    String resumo = "";
                    
                    Statement stm = con.createStatement();
                    String resultadoSQL = "SELECT d.obaa_entry, o.atributo, o.valor, d.id_rep_subfed as id_subfed, d.id_repositorio as repositorio FROM documentos d, objetos o WHERE d.id=o.documento AND (o.atributo ~* '^obaaDescription$' OR o.atributo ~* '^obaaTitle$') AND d.id=" + idocs.next().getId();
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
