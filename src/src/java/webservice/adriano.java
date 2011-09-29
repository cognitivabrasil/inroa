/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import ferramentaBusca.Recuperador;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.sql.*;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import postgres.Conectar;


public class adriano extends HttpServlet
{

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String query = request.getQueryString();
        
        try 
        {
            Conectar conecta = new Conectar();
            Connection conn = conecta.conectaBD();
            Recuperador rep = new Recuperador();
            ArrayList<Integer> id = rep.busca(query.substring(6), conn, null, null, null, "Relevancia");
            
            String xml = geraXML(query, id, conn);
            out.println(xml);
            
            conn.close();    
               
        } 
        
        catch(SQLException ex)
        {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

////colocar o fechamento da conexao no finally
        
        catch(Exception e)
        {
            System.out.println(e);
        } 
        
        finally 
        {            
            out.close();
        }
    }

    public String geraXML(String query, ArrayList id, Connection conn) throws SQLException, Exception
    {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
       
        int cTitle = 0, cLanguage = 0, cRightsDescription = 0, cLearningResourceType = 0;
        int cEntry = 0, cIdentifier = 0, cDescription = 0, cKeyword = 0, cLocation = 0;
        int cDate = 0, cRole = 0, cEntity = 0, cResourceDescription = 0;
        
        String title, language, rightsDescription, learningResourceType, entry, identifier;
        
        ArrayList<String> description = new ArrayList<String>();
        ArrayList<String> keyword = new ArrayList<String>();
        ArrayList<String> location = new ArrayList<String>();
        ArrayList<String> date = new ArrayList<String>();
        ArrayList<String> role = new ArrayList<String>();
        ArrayList<String> entity = new ArrayList<String>();
        ArrayList<String> resourceDescription = new ArrayList<String>();

        
        for(int i=0;i<id.size();i++)
        {            
            
            title = "";
            language = "";
            rightsDescription = "";
            learningResourceType = "";
            entry = "";
            identifier = "";            
            
            description.clear();
            keyword.clear();
            location.clear();
            date.clear();
            role.clear();
            entity.clear();
            resourceDescription.clear();
            
            ResultSet rs = stmt.executeQuery("SELECT d.obaa_entry, o.atributo, o.valor, d.id_rep_subfed as repsubfed, d.id_repositorio as repositorio FROM documentos d, objetos o WHERE d.id=o.documento AND d.id="+id.get(i));
            
            while(rs.next())
            {
                if("obaaDescription".equals(rs.getString(2)))
                {
                    if(description.isEmpty())
                        cDescription++;
                    description.add(rs.getString(3));

                }
                if("obaaKeyword".equals(rs.getString(2)))
                {
                    if(keyword.isEmpty())
                        cKeyword++;
                    keyword.add(rs.getString(3));                    
                }   
                if("obaaLocation".equals(rs.getString(2)))
                {
                    if(location.isEmpty())
                        cLocation++;
                    location.add(rs.getString(3));
                }
                if("obaaDate".equals(rs.getString(2)))
                {
                    if(date.isEmpty())
                        cDate++;
                    date.add(rs.getString(3));
                }
                if("obaaRole".equals(rs.getString(2)))
                {
                    if(role.isEmpty())
                        cRole++;
                    role.add(rs.getString(3));
                }  
                if("obaaEntity".equals(rs.getString(2)))
                {
                    if(entity.isEmpty())
                        cEntity++;
                    entity.add(rs.getString(3));
                }
                if("obaaResourceDescription".equals(rs.getString(2)))
                {
                    if(resourceDescription.isEmpty())
                        cResourceDescription++;
                    resourceDescription.add(rs.getString(3));
                }
                if("obaaTitle".equals(rs.getString(2)))
                {
                    title = rs.getString(3);
                    cTitle++;
                }
                if("obaaLanguage".equals(rs.getString(2)))
                {
                    language = rs.getString(3);
                    cLanguage++;
                }
                if("obaaRightsDescription".equals(rs.getString(2)))
                {
                    rightsDescription = rs.getString(3);
                    cRightsDescription++;
                }
                if("obaaLearningResourceType".equals(rs.getString(2)))
                {
                    learningResourceType = rs.getString(3);
                    cLearningResourceType++;
                }
                if("obaaIdentifier".equals(rs.getString(2)))
                {
                    identifier = rs.getString(3);
                    cIdentifier++;
                }
            }

////pra que ir pro inicio?
            rs.first();
            entry = rs.getString(1);
            cEntry++;
            
            if(location.isEmpty())
            {
///o que faz isso?
                if("1".equals(rs.getString(4)))
                {
                    //// -29 ??
                    location.add("http://hdl.handle.net/" + entry.substring(28, entry.length()-29 ));
                }
                else
                {
                    location.add("http://hdl.handle.net/" + entry.substring(33, entry.length()-34 ));
                }
            }
            
            
            
            
            xml = xml + "<OBJ>";
            
            
            xml = xml + "<General>";
            xml = xml + "<Identifier>";
            xml = xml + "<Catalog>"+identifier+"</Catalog>";
            xml = xml + "<Entry>"+entry+"</Entry>";
            xml = xml + "</Identifier>";
            xml = xml + "<Title>"+title+"</Title>";
            xml = xml + "<Language>"+language+"</Language>";
            xml = xml + "<Descriptions>";
            if(!description.isEmpty())
            {            
                for(i=0;i<description.size();i++)
                {
                    xml = xml + "<Description>"+description.get(i)+"</Description>";
                }
            }
            xml = xml + "</Descriptions>";
            xml = xml + "<Keywords>";
            if(!keyword.isEmpty())
            {            
                for(i=0;i<keyword.size();i++)
                {
                    xml = xml + "<Keyword>"+keyword.get(i)+"</Keyword>";
                }
            }
            xml = xml + "</Keywords>";
            xml = xml + "</General>";           
            
            xml = xml + "<LyfeCycle>";
            xml = xml + "<Contribute>";
            xml = xml + "<Roles>";
            if(!role.isEmpty())
            {            
                for(i=0;i<role.size();i++)
                {
                    xml = xml + "<Role>"+role.get(i)+"</Role>";
                }
            }            
            xml = xml + "</Roles>"; 
            xml = xml + "<Entities>";
            if(!entity.isEmpty())
            {            
                for(i=0;i<entity.size();i++)
                {
                    xml = xml + "<Entity>"+entity.get(i)+"</Entity>";
                }
            }            
            xml = xml + "</Entities>"; 
            xml = xml + "<Dates>";
            if(!date.isEmpty())
            {            
                for(i=0;i<date.size();i++)
                {
                    xml = xml + "<Date>"+date.get(i)+"</Date>";
                }
            }            
            xml = xml + "</Dates>";             
            xml = xml + "</Contribute>";            
            xml = xml + "</LyfeCycle>";
            
            xml = xml + "<Meta-metadata>";
            xml = xml + "<Identifier>";
            xml = xml + "<Catalog>"+identifier+"</Catalog>";
            xml = xml + "<Entry>"+entry+"</Entry>";
            xml = xml + "</Identifier>";
            xml = xml + "<Contribute>";
            xml = xml + "<Roles>";
            if(!role.isEmpty())
            {            
                for(i=0;i<role.size();i++)
                {
                    xml = xml + "<Role>"+role.get(i)+"</Role>";
                }
            }            
            xml = xml + "</Roles>"; 
            xml = xml + "<Entities>";
            if(!entity.isEmpty())
            {            
                for(i=0;i<entity.size();i++)
                {
                    xml = xml + "<Entity>"+entity.get(i)+"</Entity>";
                }
            }            
            xml = xml + "</Entities>"; 
            xml = xml + "<Dates>";
            if(!date.isEmpty())
            {            
                for(i=0;i<date.size();i++)
                {
                    xml = xml + "<Date>"+date.get(i)+"</Date>";
                }
            }            
            xml = xml + "</Dates>";             
            xml = xml + "</Contribute>";
            xml = xml + "<Language>"+language+"</Language>";            
            xml = xml + "</Meta-metadata>";
            
            xml = xml + "<Technical>";
            xml = xml + "<Locations>";
            if(!location.isEmpty())
            {            
                for(i=0;i<location.size();i++)
                {
                    xml = xml + "<Location>"+location.get(i)+"</Location>";
                }
            }            
            xml = xml + "</Locations>"; 
            xml = xml + "</Technical>";
            
            xml = xml + "<Educational>";
            xml = xml + "<LearningResourceType>"+learningResourceType+"</LearningResourceType>";
            xml = xml + "</Educational>";
            
            xml = xml + "<Rights>";
            xml = xml + "<Description>"+rightsDescription+"</Description>";
            xml = xml + "</Rights>";
            
            xml = xml + "<Relation>";            
            xml = xml + "<Resource>";
            xml = xml + "<Identifier>";
            xml = xml + "<Catalog>"+identifier+"</Catalog>";
            xml = xml + "<Entry>"+entry+"</Entry>";
            xml = xml + "</Identifier>";
            if(!resourceDescription.isEmpty())
            {            
                for(i=0;i<resourceDescription.size();i++)
                {
                    xml = xml + "<Description>"+resourceDescription.get(i)+"</Description>";
                }
            }
            xml = xml + "</Descriptions>";
            xml = xml + "</Resource>";
            xml = xml + "</Relation>";
            
            
            xml = xml + "</OBJ>";            
            
        }
//pra que isso?
        String queryString = "query="+URLEncoder.encode(query)+"&objects="+id.size()+"&title="+cTitle+"&language="+cLanguage+"&rightsdescription="+cRightsDescription+"&learningresourcetype="+cLearningResourceType+"&entry="+cEntry
                +"&identifier="+cIdentifier+"&description="+cDescription+"&keyword="+cKeyword+"&location="+cLocation+"&date="+cDate+"&role="+cRole+"&entity="+cEntity+"&resourcedescription="+cResourceDescription;
///???
        URL relatorio = new URL("http://apps.soqueaocontrario.com.br/relatorio.php?"+queryString);//+"numero="+URLEncoder.encode(query)+"&lol=1"
        URLConnection urlConnection = relatorio.openConnection();
///isso nao eh usado pra nada!
        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        
        return xml;
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
