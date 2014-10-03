<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:url var="images" value="/imagens" />
<c:url var="logoReduzido" value="/imagens/Logo FEB_reduzido.png" />
<c:url var="index" value="/" />
<c:url var="adm" value="/admin" />
<c:url var="scripts" value="/scripts" />
<c:url var="css" value="/css/padrao.css" />

<%
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");
%>
<html>
    
    <head>
        <style>
            body {margin: 50px;}
        </style>
    </head>

    <body>
        <c:url var="logo" value="/imagens/Logo FEB_reduzido.png" />
        <a href="${index}">
            <img src="${logo}" alt="logo" width="15%"/>
        </a>

        <h1>O documento solicitado foi deletado.</h1>
        Clique <a href="${index}">aqui</a> e vá para a ferramenta de busca.
    </body>

</html>