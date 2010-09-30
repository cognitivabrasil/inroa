<%-- 
    Document   : deletaBaseAjax
    Created on : 30/09/2010, 11:49:21
    Author     : Marcos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% // Importação de conexão com a base de dados %>

<%@page import="java.sql.*"%>
<%@page import="postgres.Conectar"%>
<%
Conectar conect = new Conectar();
            //chama metodo que conecta no mysql
            Connection con = conect.conectaBD();

            Statement stm = con.createStatement();

            String idMap = "";
            String tabela ="";
      
            try {
                idMap = request.getParameter("idmap");
                tabela = request.getParameter("tabela");

            if (idMap!=null) {

                String sql = "DELETE FROM "+tabela+" WHERE id="+idMap+";";
                PreparedStatement stmt = con.prepareStatement(sql);
                int result = stmt.executeUpdate();
                stmt.close();
                out.print(result);

            }
                else{
                    out.print("<p class='textoErro'>Erro! O id informado consta como null</p>");
                }

                } catch (Exception e) {
                out.print("<p class='textoErro'>Faltou informar o id do mapeamento a ser excluido</p>");
                e.printStackTrace();
            }
 
%>