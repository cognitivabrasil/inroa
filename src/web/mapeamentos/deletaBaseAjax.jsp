<%-- 
    Document   : deletaBaseAjax
    Created on : 30/09/2010, 11:49:21
    Author     : Marcos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% // Importação de conexão com a base de dados %>
<%@include file="../conexaoBD.jsp"%>
<%
            String idMap = "";

            boolean resultado = false;
            try {
                idMap = request.getParameter("idmap");

            if (idMap!=null) {

                String sql = "DELETE FROM mapeamentos WHERE id="+idMap+";";
                PreparedStatement stmt = con.prepareStatement(sql);
                int result = stmt.executeUpdate();
                stmt.close();
                out.println(result);

            }
                else{
                    out.print("<p class='textoErro'>Erro! O id informado consta como null</p>");
                }

                } catch (Exception e) {
                out.print("<p class='textoErro'>Faltou informar o id do mapeamento a ser excluido</p>");
                e.printStackTrace();
            }
%>