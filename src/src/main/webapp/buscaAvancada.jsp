<%--
    Document   : index
    Created on : 25/06/2009, 18:26:51
    Author     : Marcos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:url var="images" value="/imagens" />
<c:url var="logoReduzido" value="/imagens/Logo FEB_reduzido.png" />
<c:url var="index" value="/" />
<c:url var="adm" value="/admin" />

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
        <script language="JavaScript" type="text/javascript" src="scripts/index.js"></script>
    </head>

    <body>
        <jsp:include page="barraSuperior.jsp" />
        
        <div id="page-index">

            <c:if test="${!empty erro}">
                <div class="DivErro" id="MensagemErro">${erro}</div>
            </c:if>

            <form:form method="get" modelAttribute="buscaModel" action="consultaAvancada" acceptCharset="utf-8">    
                <div id="index">
                    <a href="${index}">
                        <img src="${logoReduzido}" alt="Logo FEB_reduzido" class="logo"/>
                    </a>

                    <div class="clear"> </div>
                    <div class="EspacoAntes">&nbsp;</div>

                    <div class="busca">
                        <div><form:errors path="consulta" cssClass="ValueErro" /></div>
                        <input type="text" name="consulta" id="consulta" value="" onFocus="this.className='inputSelecionado'" onBlur="this.className=''"/>
                        <input class="botaoLupa" type="submit" value=""/>
                    </div>
                        <div class="autor">
                        <span>Autor:</span><span><input type="text" name="autor" id="autor" value="" onFocus="this.className='inputSelecionado'" onBlur="this.className=''"/></span>
                    </div>

                    <div class="ondeBuscar">

                        <c:forEach var="rep" items="${repDAO.all}" varStatus="i">
                            <c:if test="${i.index==0}"><div class='ValueIndex'>- Reposit&oacute;rios</div></c:if>

                            <div class='repositorios'>&nbsp;&nbsp;&nbsp;
                                <input value='${rep.id}' type=checkbox id="${rep.id}" name="repositorios"> ${fn:toUpperCase(rep.nome)}
                            </div>
                        </c:forEach>

                        <c:forEach var="subFed" items="${subDAO.all}" varStatus="i">
                            <c:if test="${i.index==0}"><div class='ValueIndex'>- Subfedera&ccedil;&otilde;es</div></c:if>
                            <div class='federacoes'>&nbsp;&nbsp;&nbsp;
                                <a id='link${subFed.id}' class='linkRepSubfeb' onclick='tornarVisivel("link${subFed.id}","listaRep${subFed.id}", "Interno");'>+</a>
                                <input value='${subFed.id}' type=checkbox id="${subFed.id}" name="federacoes"> ${fn:toUpperCase(subFed.nome)}
                                <div id='listaRep${subFed.id}' class='hidden'>

                                    <c:forEach var="repSubFed" items="${subFed.repositorios}" varStatus="i">
                                        <div class='Int'>&nbsp;&nbsp;&nbsp;
                                            <input value='${repSubFed.id}' type=checkbox id="${repSubFed.id}" name="repSubfed">
                                            ${fn:toUpperCase(repSubFed.name)}
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                        </c:forEach>
                    </div>


                    <div class="LinhaEntrada">
                        <div class="Buttons">
                            <input class="BOTAO" type="submit" value="Consultar" ALIGN="CENTER"/>
                        </div>
                    </div>

                    <div ALIGN="CENTER">
                        <a href="index">Retornar a busca padr&atilde;o</a>
                    </div>
                </div>
            </form:form>

        </div>
        <%@include file="googleAnalytics"%>
    </body>
</html>