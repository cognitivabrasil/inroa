<%--
    Document   : consulta
    Created on : 01/07/2009, 16:09:51
    updated on: 26/04/2012
    Author     : Marcos
--%>


<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="StyleSheet" href="css/padrao.css" type="text/css">
        <link href="imagens/favicon.ico" rel="shortcut icon" type="image/x-icon" />
        <title>FEB – Federa&ccedil;&atilde;o de Reposit&oacute;rios Educa Brasil</title>
    </head>
    <body id="body">
        <%
            //pegar valor do model pelo jsp
            //System.out.println(pageContext.getAttribute("item", pageContext.REQUEST_SCOPE));

            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");

        %>


        <jsp:include page="cabecalho.jsp">
                <jsp:param value="Resultado da Pesquisa" name="titulo" />
                <jsp:param value="7%" name="tamanho" />
            </jsp:include>
        

        <div id="page-sub-header">
            <a href="index">Efetuar nova consulta</a>
        </div>
        <div class="cabecalhoConsulta">
            <div class="esquerda">
                <c:if test="${!empty BuscaModel.consulta}"> &nbsp;Consulta efetuada <i>"<strong>${BuscaModel.consulta}</strong>"</i></c:if>
                <c:if test="${!empty BuscaModel.autor}"> &nbsp;Autor: <i>"<strong>${BuscaModel.autor}</strong>"</i></c:if>
            </div>
            <div class="direita">
                Total de <strong>${BuscaModel.sizeResult}</strong> objeto(s) encontrado(s)&nbsp;
            </div>

        </div>


        <div id="body-resultado">

            <c:choose>
                <c:when test="${documentos.isEmpty()}">
                    <p align="center">
                        <strong>
                            <font size="3" face="Verdana, Arial, Helvetica, sans-serif">
                                Nenhum objeto encontrado
                            </font>
                        </strong>
                    </p>
                </c:when>
                <c:otherwise>
                    <form action="<%= request.getRequestURI()%>" method="get">
                        <center>

                            <pg:pager
                            items="${BuscaModel.sizeResult}"
                                url="consulta"
                                index="center"
                                maxPageItems="${BuscaModel.limit}"
                                maxIndexPages="10"
                                isOffset="true"
                                export="offset,currentPageNumber=pageNumber"
                                scope="page"
                                >                           
                                <pg:param name="repositorios"/>
                                <pg:param name="federacoes"/>
                                <pg:param name="repSubfed"/>
                                <pg:param name="autor"/>
                                

                                <%-- keep track of preference --%>
                                <pg:param name="style"/>
                                <pg:param name="position"/>
                                <pg:param name="index"/>
                                <pg:param name="maxPageItems"/>
                                <pg:param name="maxIndexPages"/>

                                <%-- salva pager offset durante as mudancas do form --%>
                                <input type="hidden" name="pager.offset" value="<%= offset%>">

                                <%-- Inclui paginacao antes dos resultados--%>
                                <%@include file="WEB-INF/jsp/paginacaoPersonalizada.jsp" %>

                                <div id="body-resultado-interno">
                                    <c:forEach var="doc" items="${documentos}" varStatus="status">
                                        <div class="resultadoConsulta">
                                            <c:set var="infoDetalhada"
                                                   value="objetos/${doc.id}"
                                                   scope="page"/>

                                            <c:if test="${empty doc.titles}">
                                                <div class="titulo"><a href='${infoDetalhada}'>T&iacute;tulo n&atilde;o informado.</a></div>
                                            </c:if>
                                            <c:forEach var="titulo" items="${doc.titles}">
                                                <div class="titulo">
                                                    <a href='${infoDetalhada}'>
                                                        ${titulo}
                                                    </a>
                                                </div>
                                            </c:forEach>


                                            <c:forEach var="resumo" items="${doc.shortDescriptions}">
                                                <div class="atributo">${resumo}</div>
                                            </c:forEach>

                                            <c:if test="${!empty doc.location}">
                                                <div class="atributo"> Localiza&ccedil;&atilde;o:
                                                    <c:forEach var="localizacao" items="${doc.location}">
                                                        <div class="atributo"><a href="${localizacao}" target="_new">${localizacao}</a></div>
                                                    </c:forEach>
                                                </div>
                                            </c:if>

                                            <c:if test="${!empty doc.date}">
                                                <div class="atributo"> Data:
                                                    <c:forEach var="data" items="${doc.date}">
                                                        <div class="atributo">${data}</div>
                                                    </c:forEach>
                                                </div>
                                            </c:if>

                                            <c:if test="${!empty doc.nomeRep}">
                                                <div class="atributo"> Reposit&oacute;rio: ${doc.nomeRep}</div>
                                            </c:if>

                                        </div>
                                    </c:forEach>

                                </div>
                                <%-- Inclui paginacao depois dos resultados--%>
                                <%@include file="WEB-INF/jsp/paginacaoPersonalizada.jsp" %>

                            </pg:pager>
                        </center>
                    </form>
                </c:otherwise>
            </c:choose>
        </div>

        <div class="rodapeConsulta">&nbsp;</div>
        <%@include file="googleAnalytics"%>

    </body>
</html>
