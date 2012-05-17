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

<%
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");

%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>GT-FEB – Federa&ccedil;&atilde;o de Reposit&oacute;rios Educa Brasil</title>
        <link rel="StyleSheet" href="css/padrao.css" type="text/css">
        <link href="imagens/favicon.ico" rel="shortcut icon" type="image/x-icon" />
        <script language="JavaScript" type="text/javascript" src="scripts/rss.js"></script>


    </head>      

    <body id="bodyMenor">

        <div id="page">
            
            <div class="logoBusca"><img class="logo" src="imagens/Logo FEB_reduzido.png" alt="Logo FEB_reduzido"/></div>


            <div class="clear"> </div>

            <div class="EspacoPequeno">&nbsp;</div>
            <div class="subTituloBusca">&nbsp;Consulta de Objetos Educacionais</div>
            <div>
                <div class="linkCantoEsquerdo"> <a href="http://feb.ufrgs.br/feb"><img src="imagens/Logo FEB_reduzido_45x32.png" alt="Logo FEB_reduzido" title="Buscar na Confederação"/>  Confederação</a></div>
                <div class="linkCantoDireito"><a href="./admin/" title="Ferramenta Administrativa"><img src="imagens/ferramenta_32x32.png" alt="Ferramenta Administrativa"></a></div>
            </div>
            <div class="Espaco">&nbsp;</div>

            <form:form method="get" modelAttribute="buscaModel" action="consulta" acceptCharset="utf-8">   

                <div class="clear"> </div>
                <div class="EspacoAntes">&nbsp;</div>

                <c:if test="${!empty erro}">
                    <div class="DivErro" id="MensagemErro">${erro}</div>
                </c:if>

                <div class="LinhaEntrada">                    
                    <form:errors path="consulta" cssClass="ValueErro" />
                    <div class="Label">
                        Texto para a busca:
                    </div>
                    <div class="Value">
                        <form:input path="consulta" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>

                    <div class="clear"> </div>
                </div>
                <div class="LinhaEntrada">
                    <div class="btConsultar">
                        <input class="BOTAO" type="submit" value="Consultar"/>
                    </div>
                </div>
            </form:form>
            <div ALIGN="CENTER">
                <a href="./index2">Busca Avan&ccedil;ada</a>
            </div>

        </div>
        <div>
            <div class="copyRight">Desenvolvido em parceria com: UFRGS e RNP</div>
            <div  class="rss">
                <a class="linkRSS" onclick= "geraRss()" title="RSS da busca"><img src="imagens/rss_300x300.png" width="3%" alt="rsslogo" onclick= "geraRss()"/> </a>
            </div>
        </div>
        <%@include file="googleAnalytics"%>
    </body>
</html>
