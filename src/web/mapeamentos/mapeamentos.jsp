<%-- 
    Document   : mapeamentos
    Created on : 13/09/2010, 18:27:41
    Author     : Marcos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../conexaoBD.jsp"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FEB – Federa&ccedil;&atilde;o de Reposit&oacute;rios Educa Brasil</title>
        <link rel="StyleSheet" href="../css/padrao.css" type="text/css">
        <link href="../imagens/favicon.ico" rel="shortcut icon" type="image/x-icon" />
    </head>
    <body>
        <%
             //listar os mapeamentos existentes


             //funcionalidades
             //adicionar, adicionar a partir de um pronto, remover, visualizar/editar
        String sql = "";
        ResultSet rs2 = stm.executeQuery(sql);
             //pega o proximo resultado retornado pela consulta sql
             while (rs2.next()) {
             }
        %>
    </body>
</html>
