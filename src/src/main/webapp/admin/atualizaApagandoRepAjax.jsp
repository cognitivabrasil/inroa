<%-- 
    Document   : atualizaApagandoRepAjax
    Created on : 14/09/2011, 17:50:31
    Author     : Marcos
--%>

<%@page import="robo.atualiza.Repositorios"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="robo.main.Robo"%>

<%
String id = request.getParameter("id");
int idRep = Integer.parseInt(id);

Repositorios repositorio = new Repositorios();
repositorio.atualizaFerramentaAdm(idRep, true);
%>