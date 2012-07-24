<%-- 
    Document   : removerFederacao
    Created on : 14/09/2009, 12:01:56
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
<c:url var="favicon" value="/imagens/favicon.ico" />
<c:url var="css" value="/css/padrao.css" />


<link href="${favicon}" rel="shortcut icon" type="image/x-icon" />
<link rel="StyleSheet" href="${css}" type="text/css" />

    </head>
    <body>
        <div id="page">
            <div class="subTitulo-center">&nbsp;Ferramenta para remo&ccedil;&atilde;o de subfedera&ccedil;&otilde;es</div>
            <div class="EspacoAntes">&nbsp;</div>

            <form name="removerFederacao" method="post">
                <div class="LinhaEntrada">
                    <div class="Tab">
                        Deseja realmente remover a Subfedera&ccedil;&atilde;o <b>${federation.name}</b> ?
                    </div>
                    <BR>
                </div>
                <input type="hidden" name="submitted" value="true"/>
                <input type="hidden" name="id" value="${federation.id}">
                <div class="LinhaEntrada">
                    <div class="Buttons">
                        <input type="submit" value="&nbsp;Sim&nbsp;" name="submit" />
                        <input id="cancelar" onclick="javascript:window.close();" value="&nbsp;N&atilde;o&nbsp;" type="button" class="CancelButton"/>
                    </div>
                </div>
            </form>
        </div>
        <%@include file="../../googleAnalytics"%>
    </body>
</html>