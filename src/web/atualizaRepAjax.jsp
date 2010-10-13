<%-- 
    Document   : atualizaRepAjax
    Created on : 13/10/2010, 11:47:40
    Author     : Marcos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="robo.main.Robo"%>

<%
String id = request.getParameter("id");
int idRep = Integer.parseInt(id);
Robo robo = new Robo();
robo.atualizaFerramentaAdm(idRep);
%>