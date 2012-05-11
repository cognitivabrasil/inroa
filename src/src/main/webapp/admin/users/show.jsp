<%-- 
    Document   : exibeFederacao
    Created on : 14/09/2009, 12:16:29
    Author     : Marcos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>



<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FEB - Ferramenta Administrativa</title>
        <c:url var="css" value="/css/padrao.css"/>
        <c:url var="favicon" value="/imagens/favicon.ico"/>
        <c:url var="funcoes" value="/scripts/funcoes.js"/>
        <link rel="StyleSheet" href="${css}" type="text/css">
        <link href="${favicon }" rel="shortcut icon" type="image/x-icon" />
        <script language="JavaScript" type="text/javascript" src="${funcoes}"></script>
    </head>

    <body>
        <jsp:useBean id="operacoesBean" class="robo.util.Operacoes" scope="page" />

        <div id="page">

            <div class="subTitulo-center">Edição / Visualização de Usuários Cadastrados</div>


            <div class="subtitulo">Informações sobre o usuario ${user.username}</div>
            <div class="editar"><a href="./${user.id}/edit">Editar</a></div>

            <div class="LinhaEntrada">
                <div class="Label">
                    Username:
                </div>
                <div class="Value">&nbsp;${user.username}</div>
            </div>
            <div class="LinhaEntrada">
                <div class="Label">
                    Descri&ccedil;&atilde;o:
                </div>
                <div class="Value">&nbsp;${user.description}</div>
            </div>
            <div class="LinhaEntrada">
                <div class="Label">
                    Perfil: 
                </div>
                <div class="Value">&nbsp;${user.role}</div>
            </div>

        <%@include file="../../googleAnalytics"%>
    </body>
</html>