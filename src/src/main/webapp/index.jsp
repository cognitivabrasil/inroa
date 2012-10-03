<%--
    Document   : index
    Created on : 25/06/2009, 18:26:51
    Author     : Marcos
--%>

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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="robots" content="nofollow">
        <title>GT-FEB – Federa&ccedil;&atilde;o de Reposit&oacute;rios Educa Brasil</title>
        <link rel="StyleSheet" href="${css}" type="text/css">
        <link href="${images}/favicon.ico" rel="shortcut icon" type="image/x-icon" />
        <script language="JavaScript" type="text/javascript" src="${scripts}/rss.js"></script>

    </head>      

    <body>
        <jsp:include page="barraSuperior.jsp" />

        <div id="page-index">

            
            <c:if test="${!empty erro}">
                <div class="DivErro" id="MensagemErro">${erro}</div>
            </c:if>

            <form:form method="get" modelAttribute="buscaModel" action="consulta" acceptCharset="utf-8">
                <div id="index">

                    <a href="${index}">
                        <img src="${logoReduzido}" alt="Logo FEB_reduzido" class="logo"/>
                    </a>

                    <div class="clear"> </div>
                    <div class="EspacoAntes">&nbsp;</div>


                    <div class="busca">
                        <div><form:errors path="consulta" cssClass="ValueErro" /></div>
                        <form:input path="consulta" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                        <input class="botaoLupa" type="submit" value=""/>
                    </div>
                </div>
            </form:form>
            <div ALIGN="CENTER">
                <a href="./buscaAvancada">Busca Avan&ccedil;ada</a>
            </div>

            <div>
                <jsp:include page="tagCloud.jsp">
                    <jsp:param value="${termos}" name="termos" />
                </jsp:include>
            </div> 

        </div>


        <%@include file="googleAnalytics"%>
    </body>
</html>
