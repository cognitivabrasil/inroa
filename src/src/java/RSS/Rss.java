/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RSS;

import ferramentaBusca.recuperador.Recuperador;
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

    static final String feedLink = "http://feb.ufrgs.br";
    static final String feedDescription = "Alimentador de objetos de aprendizagem. ";
    private String search;
    private int idRep;
    private ArrayList <Integer>idRepArray = new ArrayList();

    /**
     * Transforma um string de inteiros em um ArrayList de inteiros
     * @param s String de inteiros separados por v&iacute;rgulas
     * @return ArrayList de inteiros correspondente &agrave; string
     **/
    private ArrayList <Integer> stringToIntegerArrayList (String s){
        int posfim = 0, posini = 0;
        ArrayList <Integer> array = new ArrayList<Integer>();
        while (!s.isEmpty()) {
            if (s.contains(","))
                posfim = s.indexOf(',');
            else
                posfim = s.length();
            try {
                array.add(Integer.parseInt(s.substring(posini, posfim)));
            } catch (NumberFormatException e) {
                System.out.println("Id de repositorio invalido " + e);
            } catch (Exception e) {
                System.out.println(e);
            }
            if (s.contains(","))
                posfim += 1;
            s = s.substring(posfim);
        }
        return array;
    }

    /**
     * Construtor da classe Rss
     * @param search string a ser buscada nos reposit&oacute;rios
     * @param idRep identificador do reposit&oacute;rio a ser buscado. Caso se queira buscar em todos os reposit&oacute;rios, utilizar 0.
     **/
    public Rss(String search, int idRep) {
        if (search == null) {
            this.search = "";
        } else {
            this.search = search;
        }
        this.idRep = idRep;
    }

    /**
     * Construtor da classe Rss
     * @param search string a ser buscada nos reposit&oacute;rios
     * @param idRepArray ArrayList dos identificadores dos reposit&oacute;rios a serem usados na busca
     **/
    public Rss(String search, ArrayList <Integer>idRepArray) {
        if (search == null) {
            this.search = "";
        } else {
            this.search = search;
        }
        if (idRepArray.contains(0))
        {
            this.idRep = 0;
            this.idRepArray.clear();
        } else {
            this.idRepArray = idRepArray;
            this.idRep = 0;
        }
    }

    /**
     * Construtor da classe Rss
     * @param search string a ser buscada nos reposit&oacute;rios
     * @param s  string com os id dos reposit&oacute;rios (inteiros) separados por v&iacute;rgulas
     **/
    public Rss(String search, String s) {
        if (search == null) {
            this.search = "";
        } else {
            this.search = search;
        }
        if (s.contains("0"))
        {
            this.idRep = 0;
            this.idRepArray.clear();
        } else {
            this.idRepArray = stringToIntegerArrayList(s);
            this.idRep = 0;
        }
    }

    /**
     * Faz a busca da string passada no construtor no banco de dados, utilizando
     * a mesma fun&ccedil;&atilde;o que a busca normal.
     * @return string correspondente ao xml do rss que será gerado
     **/
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
            Text text = doc.createTextNode("FEB - " + this.search);
            rssTitle.appendChild(text);
            channel.appendChild(rssTitle);
            Element rssDescription = doc.createElement("description");
            text = doc.createTextNode(feedDescription);
            rssDescription.appendChild(text);
            channel.appendChild(rssDescription);
            Element rssLink = doc.createElement("link");
            text = doc.createTextNode(feedLink);
            rssLink.appendChild(text);
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
        Rss rss = new Rss("educa", "3");
        System.out.println(rss.generateFeed());
    }
}
