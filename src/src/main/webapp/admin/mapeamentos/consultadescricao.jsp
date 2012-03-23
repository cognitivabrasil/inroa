<%-- 
    Document   : consultadescricao
    Created on : 21/07/2011, 23:57:21
    Author     : Marcos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="operacoesPostgre.Consultar"%>
<%
            String id = request.getParameter("id");
            if (id.isEmpty()) {
                out.println("Selecione o nome do mapeamento e veja aqui sua descrição.");
            } else {
                int idMap = Integer.parseInt(id);
                out.println(Consultar.consultaDescricaoMapeamento(idMap));
            }
%>