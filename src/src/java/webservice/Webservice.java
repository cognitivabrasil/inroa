/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import ferramentaBusca.Recuperador;
import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.xml.parsers.*;
import org.w3c.dom.Document;
import org.xml.sax.*;
import postgres.Conectar;


public class Webservice extends HttpServlet
{
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String url = "http://www.inf.ufrgs.br/~agsoares/report.php?";
        String encodedQuery = request.getParameter("query");
        String query = URLDecoder.decode(request.getParameter("query"), "UTF-8");
        String start = request.getParameter("start");
        String limit = request.getParameter("limit");
        ArrayList<Integer> id = new ArrayList<Integer>();
        try
        {
            if (start == null || "".equals(start) )
            {
                start = "0";
            }

            if (limit == null || "".equals(limit) )
            {
                limit = "30";
            }

            Conectar conecta = new Conectar();
            Connection conn = conecta.conectaBD();
            Document doc = parseXMLfromString(readURL(url+"method=query&query="+encodedQuery));
            if("true".equals(doc.getElementsByTagName("exist").item(0).getTextContent()))
            {
                String[] ids = doc.getElementsByTagName("ids").item(0).getTextContent().split(",");

                for(String s : ids)
                {
                    id.add(Integer.parseInt(s));
                }
            }
            else
            {
                Recuperador rep = new Recuperador();
                id = rep.busca(query.substring(6), conn, null, null, null, "Relevancia");
                String updateIds = "";
                for(int i=0;i<id.size();i++)
                {
                    if(i==0)
                    {
                        updateIds = updateIds + id.get(i).toString();
                    }
                    else
                    {
                        updateIds = updateIds + "," + id.get(i).toString();
                    }
                }
                readURL(url+"method=update&query="+encodedQuery+"ids="+updateIds);
            }
            String xml = geraXML(id, conn, url, Integer.parseInt(start), Integer.parseInt(limit));
            out.println(xml);

            conn.close();

        }
        catch(Exception e)
        {
            System.out.println(e);
        }

        finally
        {
            out.close();
        }
    }

    public String geraXML(ArrayList id, Connection conn, String url, int start, int limit) throws SQLException, Exception
    {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

        HashMap<String, Integer> relatorio = new HashMap<String, Integer>();

        Statement stmt = conn.createStatement();

        if(start < id.size())
        {
            for(int i = start; i < id.size() && i <= start + limit; i++)
            {
                ResultSet rs = stmt.executeQuery("SELECT d.obaa_entry, o.atributo, o.valor, d.id_rep_subfed as repsubfed, d.id_repositorio as repositorio FROM documentos d, objetos o WHERE d.id=o.documento AND d.id="+id.get(i));

                xml = xml + "<OBJ>";

                while(rs.next())
                {
                   xml = xml + "<"+rs.getString(2)+">"+rs.getString(3)+"</"+rs.getString(2)+">";
                   if(relatorio.containsKey(rs.getString(2)))
                   {
                       relatorio.put(rs.getString(2), relatorio.get(rs.getString(2))+1 );
                   }
                   else
                   {
                       relatorio.put(rs.getString(2), 1);
                   }
                }
                if(relatorio.containsKey("Objects"))
                {
                    relatorio.put("Objects", relatorio.get("Objects")+1);
                }
                else
                {
                    relatorio.put("Objects", 1);
                }
                xml = xml + "</OBJ>";


            }
        }
        String updateReport = "";
        Set<String> keys = relatorio.keySet();
        for(Iterator<String> E = keys.iterator();E.hasNext();)
        {
            String Key = E.next();
            updateReport = updateReport + "&" + Key + "=" + relatorio.get(Key);
        }
        readURL(url+"method=report"+updateReport);
        return xml;
    }

    private String readURL(String url) throws MalformedURLException, IOException
    {
        String xml = "";
        String line;
        BufferedReader in = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
        while ((line = in.readLine()) != null)
        {
                xml = xml + line;
        }
        return xml;
    }

    private Document parseXMLfromString(String str) throws ParserConfigurationException, SAXException, IOException
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = factory.newDocumentBuilder();
        InputSource inStream = new InputSource();

        inStream.setCharacterStream(new StringReader(str));
        Document doc = db.parse(inStream);

        return doc;
    }


    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo()
    {
        return "Short description";
    }// </editor-fold>
}
