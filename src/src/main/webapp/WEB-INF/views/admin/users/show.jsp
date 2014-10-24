<%-- 
    Document   : exibeFederacao
    Created on : 14/09/2009, 12:16:29
    Author     : Marcos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="security"
           uri="http://www.springframework.org/security/tags"%>



<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FEB - Ferramenta Administrativa</title>
        <c:url var="css" value="/css/padrao.css" />
        <c:url var="favicon" value="/imagens/favicon.ico" />
        <c:url var="funcoes" value="/scripts/funcoes.js" />
        <link rel="StyleSheet" href="${css}" type="text/css">
        <link href="${favicon }" rel="shortcut icon" type="image/x-icon" />
        <c:url var="jqueryUi" value="/css/vendor/Theme/jquery-ui-1.8.22.custom.css" />
        <link rel="StyleSheet" href="${jqueryUi}" type="text/css"/>
        <c:url var="jUi" value="/scripts/vendor/jquery-1.7.2.js" />
        <script language="javascript" type="text/javascript" src='${jUi}'></script>
        <c:url var="jQuery" value="/scripts/vendor/jquery-ui-1.8.22.custom.min.js" />
        <script type="text/javascript" src='${jQuery}'></script>

        <script language="JavaScript" type="text/javascript" src="${funcoes}"></script>
        <c:url var="root" value="/" />
        <script>
            setRootUrl("${root}");
            $(function() {
                $( "#alterarSenha" ).button({
                    
                    icons: {
                        primary: "ui-icon-key"
                    }
                });
            });
        </script>
    </head>

    <body>
        <jsp:useBean id="operacoesBean" class="com.cognitivabrasil.feb.util.Operacoes" scope="page" />

        <div id="page">

            <div class="subTitulo-center">Edição / Visualização de Usuários
                Cadastrados</div>


            <div class="subtitulo">Informações sobre o usuario
                ${user.username}</div>

            <security:authorize access="hasRole('ROLE_MANAGE_USERS')">

                <div class="editar">
                    <a href="./${user.id}/edit">Editar</a>
                </div>
            </security:authorize>

            <div class="LinhaEntrada">
                <div class="Label">Username:</div>
                <div class="Value">&nbsp;${user.username}</div>
            </div>
            <div class="LinhaEntrada">
                <div class="Label">Descri&ccedil;&atilde;o:</div>
                <div class="Value">&nbsp;${user.description}</div>
            </div>
            <div class="LinhaEntrada">
                <div class="Label">Perfil:</div>
                <div class="Value">&nbsp;${user.role}</div>
            </div>
                <c:url var="chPass" value="/admin/users/passwd"/>
            <a id="alterarSenha" href="${chPass}">
                Alterar Senha </a>
            
    </body>
</html>