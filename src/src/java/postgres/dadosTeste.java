package postgres;

import ferramentaBusca.recuperador.Recuperador;
import java.beans.Statement;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.xml.sax.helpers.DefaultHandler;
import org.w3c.dom.*;

/**
 * Classe que faz o parser/leitura de um arquivo XML
 *
 * @author Marcos Nunes
 *
 */
public class dadosTeste extends DefaultHandler {

    public String generateFeed() {
        ArrayList<Integer> idArray = new ArrayList<Integer>();
        Conectar conectar = new Conectar(); //instancia uma variavel da classe mysql.conectar
        Connection con = conectar.conectaBD(); //chama o metodo conectaBD da classe mysql.conectar
        Recuperador rec = new Recuperador();
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
            //Text text = doc.createTextNode("FEB - " + this.search);
            //rssTitle.appendChild(text);
            channel.appendChild(rssTitle);
            Element rssDescription = doc.createElement("description");
            //text = doc.createTextNode(feedDescription);
            //rssDescription.appendChild(text);
            channel.appendChild(rssDescription);
            Element rssLink = doc.createElement("link");
            //text = doc.createTextNode(feedLink);
            //rssLink.appendChild(text);
            channel.appendChild(rssLink);


            //faz a busca da string search, com o id do repositorio escolhido na base de dados
            //idDoc é um array com os identificadores correspondentes à pesquisa
            try {
                if (idRepArray.isEmpty()) {
                    System.out.println("int:" + this.search + " " + this.idRep);
                    idArray = rec.search2(this.search, con, this.idRep);
                } else {
                    idArray = rec.search2(this.search, con, this.idRepArray);
                }
            } catch (SQLException e) {
                System.out.println("Problemas com a busca\n" + e);
            }

            //adiciona um item no rss para cada elemento encontrado
            for (int i = 0; i < idArray.size(); i++) {
                //faz a busca pelo id
                    String titulo = "";
                    String resumo = "";

                    Statement stm = con.createStatement();
                    String resultadoSQL = "SELECT d.obaa_entry, o.atributo, o.valor, r.nome as repositorio, ds.id as id_base FROM documentos d, dados_subfederacoes ds, repositorios r, objetos o, info_repositorios i WHERE d.id=o.documento AND d.id_repositorio=r.id AND r.id = i.id_repositorio AND i.id_federacao=ds.id AND d.id=" + idArray.get(i);

                    try {
                        ResultSet rs = stm.executeQuery(resultadoSQL);
                        //pega o proximo resultado retornado pela consulta sql


                        Element item = doc.createElement("item");
                        channel.appendChild(item);
                        while (rs.next()) {
                            if (rs.isFirst()) {

                                Element tag = doc.createElement("idBase");
                                text = doc.createTextNode(rs.getString("id_base"));
                                tag.appendChild(text);
                                item.appendChild(tag);

                                tag = doc.createElement("link");
                                text = doc.createTextNode("http://localhost:8084/feb/infoDetalhada.jsp?id=" + rs.getString("obaa_entry")
                                        + "&idBase=" + rs.getString("id_base")
                                        + "&repositorio=" + rs.getString("repositorio"));
                                tag.appendChild(text);
                                item.appendChild(tag);

                                tag = doc.createElement("repositorio");
                                text = doc.createTextNode(rs.getString("repositorio"));
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
                        System.out.println("Nao foi possivel recuperar as informacoes da base de dados" + e);
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

            con.close(); //fecha a conexao com o mysql

        } catch (DOMException e) {
            System.out.print("Erro gerado pelo DOM para a geração de um RSS\n" + e);
        } catch (SQLException e) {
            System.out.print("Erro na busca SQL para a geração do RSS\n" + e);
        } catch (TransformerException e) {
            System.out.print("Erro com o Transformer para a geração do RSS\n" + e);
        } catch (Exception e) {
            System.out.print("Erro na geração do RSS\n" + e);
        }
        return xml;

    }

    public static void main(String[] args) {
        dadosTeste vodka = new dadosTeste();

        try{
        vodka.parser("src\\java\\postgres\\febPropriedades.xml");
        } catch (Exception E){
            System.out.println("err");
        }
    }
}

