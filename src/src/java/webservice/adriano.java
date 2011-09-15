/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import ferramentaBusca.Recuperador;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import postgres.Conectar;

/**
 *
 * @author Adriano
 */

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
            
            String xml = geraXML(id, conn);
            out.println(xml);
            
            conn.close();                   
        } 
        
        catch(SQLException ex)
        {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        
        catch(Exception e)
        {
            System.out.println("Problemas ao tentar conectar com o banco de dados: " + e);
        } 
        
        finally 
        {            
            out.close();
        }
    }

    public String geraXML(ArrayList id, Connection conn) throws SQLException
    {
        String tabela = "---Inicio---\n\n\n";
        Statement stmt = conn.createStatement();
        
        for(int i=0;i<id.size();i++)
        {
            ResultSet rs = stmt.executeQuery("SELECT d.obaa_entry, o.atributo, o.valor, d.id_rep_subfed as repsubfed, d.id_repositorio as repositorio FROM documentos d, objetos o WHERE d.id=o.documento AND d.id="+id.get(i));
            
            tabela = tabela + "---OBJ Inicio---\n\n";
            
            while(rs.next())
            {
               tabela = tabela + rs.getString(1) +"\t"+ rs.getString(2) +"\t"+ rs.getString(3) +"\t"+ rs.getString(4) +"\t"+ rs.getString(5) +"\n";
            }
            
            tabela = tabela + "\n---OBJ Fim---\n\n";
            
        }
        tabela = tabela + "\n---Fim---";
        return tabela; 
        
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
