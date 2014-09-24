<!-- 
    Document   : showDocument
    Created on : 03/04/2014, 18:35:14
    Author     : Marcos Freitas Nunes <marcos@cognitivabrasil.com.br>
-->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FEB – Federação de Repositórios Educa Brasil</title>
        <c:url var="defaultCss" value="/css/padrao.css"/>
        <link rel="StyleSheet" href="${defaultCss}" type="text/css">
        <c:url var="favicon" value="/imagens/favicon.ico"/>
        <link href="${favicon}" rel="shortcut icon" type="image/x-icon" />
        <c:url var="jstreeCss" value="/scripts/vendor/jsTree/dist/themes/default/style.min.css" />
        <link rel="StyleSheet" href="${jstreeCss}" type="text/css">
        
        <c:url var="root" value="/" />
        <script>rootUrl = ${root};
        obaaJson=${obaaJson};</script>
        <c:url var="jquery" value="/scripts/vendor/jquery-1.11.0.min.js" />
        <script language="javascript" type="text/javascript" src='${jquery}'></script>
        <c:url var="jstree" value="/scripts/vendor/jsTree/dist/jstree.min.js" />
        <script language="JavaScript" type="text/javascript" src="${jstree}"></script>
        <c:url var="validateURL" value="/scripts/testUrlActive.js" />
        <script type="text/javascript" src="${validateURL}"></script>        
        <c:url var="showMetadata" value="/scripts/showMetadata.js" />
        <script type="text/javascript" src="${showMetadata}"></script>

        <script type="text/javascript"
                src="https://apis.google.com/js/plusone.js">
            {
                lang: 'pt-BR';
            }
        </script>

    </head>
    <body>
        <div id="jstreeTest">d</div>
        ${obaaJson}
    </body>