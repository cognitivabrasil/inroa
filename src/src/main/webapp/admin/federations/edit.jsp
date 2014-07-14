<%-- 
    Document   : editarFederacao
    Created on : 14/09/2009, 12:49:22
    Author     : Marcos
--%>

<%@page import="javax.persistence.Parameter"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>FEB - Ferramenta Administrativa</title>
        <c:url var="favicon" value="/imagens/favicon.ico" />
        <c:url var="css" value="/css/padrao.css" />
        <c:url var="validateJs" value="/scripts/validatejs.js" />
        <c:url var="root" value="/" />
        <script>rootUrl = ${root};</script>

        <c:url var="jquery" value="/scripts/vendor/jquery-1.7.2.js" />
        <script language="javascript" type="text/javascript" src='${jquery}'></script>
        <c:url var="validateOAI" value="/scripts/validateOAI.js" />
        <script type="text/javascript" src="${validateOAI}"></script>

        <link href="${favicon}" rel="shortcut icon" type="image/x-icon" />
        <link rel="StyleSheet" href="${css }" type="text/css" />
    </head>

    <body>
        <input type="hidden" id="federation" value="true" />
        <div id="page">

            <div class="subTitulo-center">&nbsp;Editanto federa&ccedil;&atilde;o ${subDAO.name}</div>
            <div class="subtitulo">Informa&ccedil;&otilde;es sobre a federa&ccedil;&atilde;o</div>
            <div class="EspacoAntes">&nbsp;</div>

            <form:form method="post" modelAttribute="federation" acceptCharset="utf-8">

                <div class="TextoDivAlerta" id="MensagemErro">
                    <!--Aqui o script colocara a mensagem de erro, se ocorrer-->
                </div>
                <div class="LinhaEntrada">
                    <form:errors path="url" cssClass="ValueErro" />
                    <div class="Comentario">Ex: http://feb.ufrgs.br/feb</div>
                    <div class="Label">URL:</div>
                    <div class="Value">
                        <form:input path="url" maxlength="200" />
                        &nbsp;
                        <div id="resultadoTesteOAI" class="linkCantoDireito"></div>
                    </div>
                </div>
                <div class="LinhaEntrada">
                    <form:errors path="name" cssClass="ValueErro" />
                    <div class="Label">Nome:</div>
                    <div class="Value">
                        <form:input path="name" maxlength="45" />
                    </div>
                </div>
                <div class="LinhaEntrada">
                    <form:errors path="descricao" cssClass="ValueErro" />
                    <div class="Label">Descri&ccedil;&atilde;o:</div>
                    <div class="Value">
                        <form:input path="descricao" maxlength="455"/>
                    </div>
                </div>
                <div class="LinhaEntrada">
                    <form:errors path="version" cssClass="ValueErro" />
                    <div class="Label">Versão:</div>
                    <div class="Value">
                        <form:input path="version" maxlength="455" />
                    </div>
                </div>
                
                <div class="LinhaEntrada">
                    <div class="Buttons">
                        <c:url var="back" value="/admin/federations/${federation.id}" />
                        <input type="button" value="&lArr; Voltar" onclick="window.location.href='${back}'" /> 
                        <input type="submit" value="Gravar &rArr;" name="submit" />
                    </div>
                </div>
            </form:form>

        </div>
        <%@include file="../../googleAnalytics"%>
    </body>
</html>
