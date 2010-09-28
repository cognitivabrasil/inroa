<%-- 
    Document   : salvarBaseAjax.jsp
    Created on : 27/09/2010, 18:59:39
    Author     : Marcos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<% // Importação de conexão com a base de dados %>
<%@include file="../conexaoBD.jsp"%>

<%




//Parâmetros recuperados do Ajax


    String idOrigem = request.getParameter("origem");
    String idDestino = request.getParameter("destino");
    String idOrigemComp = request.getParameter("origemcomplementar");
    String idDestinoComp = request.getParameter("destinocomplementar");

    out.println("origem: "+idOrigem+" destino: "+idDestino+" idOrigemComp: "+idOrigemComp+ "idDestinoComp: "+idDestinoComp);

%>
