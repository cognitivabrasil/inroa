/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import ferramentaBusca.Recuperador;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import postgres.Conectar;


public class Webservice extends HttpServlet
{
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/xml;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String url = "http://www.inf.ufrgs.br/~agsoares/report.php?";
        String encodedQuery = request.getParameter("query");
        String page = request.getParameter("page");
        int limit = 20;
         
        try 
        {
            if (encodedQuery == null || "".equals(encodedQuery) )
            {
                out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?><OBJS></OBJS>");
            }
            else
            {
                String query = URLDecoder.decode(encodedQuery, "UTF-8");
                if (page == null || "".equals(page) ) 
                {
                    page = "1";
                } 

                Conectar conecta = new Conectar();
                Connection conn = conecta.conectaBD();
                Recuperador rep = new Recuperador();
                ArrayList<Integer> id = rep.busca(query, conn, null, null, null, "Relevancia");
                
                String xml = geraXML(id, conn, url, Integer.parseInt(page), limit);
                out.println(xml);

                conn.close();    
            }  
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

    public String geraXML(ArrayList id, Connection conn, String url, int page, int limit) throws SQLException, Exception
    {
        String xml = "<?xml version=\"1.0\"?>";
        xml = xml + "<OBJS page=\""+page+"\" from=\""+(int)Math.ceil(id.size()/limit)+"\">";
        HashMap<String, Integer> relatorio = new HashMap<String, Integer>();       
        
        Statement stmt = conn.createStatement();
       
        if((page-1)*limit < id.size())
        {    
            for(int i = (page-1)*limit; i < id.size() && i < (page-1)*limit + limit; i++)
            {            
                ResultSet rs = stmt.executeQuery("SELECT d.obaa_entry, o.atributo, o.valor, d.id_rep_subfed as repsubfed, d.id_repositorio as repositorio FROM documentos d, objetos o WHERE d.id=o.documento AND d.id="+id.get(i));
                xml = xml + "<OBJ id=\""+id.get(i)+"\">";

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
        readURL(url+updateReport);
        xml = xml + "</OBJS>";
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
