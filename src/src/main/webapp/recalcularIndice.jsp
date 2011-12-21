<%-- 
    Document   : recalcularIndice
    Created on : 16/10/2010, 01:59:45
    Author     : Marcos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="conexaoBD.jsp"%>
<%@page import="ferramentaBusca.IndexadorBusca"%>
<%

out.print("Recalculando o indice.");
IndexadorBusca run = new IndexadorBusca();
run.indexarTodosRepositorios(con);
out.print("Indice recalculado");
%>
