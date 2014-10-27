<%-- 
    Document   : index
    Created on : 25/06/2009, 18:26:51
    Author     : Marcos Nunes
    Updated on : 19/03/2014, 14:05
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:url var="index" value="/" />
<c:url var="adm" value="/admin" />
<c:url var="scripts" value="/scripts" />
<c:url var="images" value="/imagens" />

<html>
    <head>
        <!--jsTree css-->
        <link rel="StyleSheet" href="${scripts}/vendor/jsTree/dist/themes/default/style.min.css" type="text/css">

        <jsp:include page="fragments/htmlHeader.jsp"/>

        <!--css especifico da busca avançada-->
        <c:url var="avancada" value="/css/buscaAvancada.css" />
        <link rel="StyleSheet" href="${avancada}" type="text/css">
    </head>

    <body>
        <jsp:include page="fragments/cabecalho.jsp"/>

        <div class="container">

            <c:if test="${!empty erro}">
                <div class="alert alert-danger text-center">${erro}</div>
            </c:if>

            <form:form method="POST" action="resultadoav" modelAttribute="buscaModel" class="form-horizontal"
                       role="form" acceptCharset="utf-8">

                <div class="row">
                    <div class="col-md-offset-3 col-md-9">
                        <form:errors path="consulta" element="div" cssClass="alert alert-danger text-center" />
                    </div>
                </div>

                <!--Texto para busca-->
                <div class="form-group">
                    <form:label cssClass="textWhite col-md-3 control-label" path="consulta" cssErrorClass="">
                        Texto para a busca
                    </form:label>

                    <div class="col-md-9">
                        <form:input cssClass="form-control shadow" path="consulta" cssErrorClass="form-control shadow"/>
                    </div>

                </div>

                <div class="form-group">
                    <form:label cssClass="textWhite col-md-3 control-label" path="autor" cssErrorClass="">
                        Autor
                    </form:label>

                    <div class="col-md-9">
                        <form:input class="form-control shadow" path="autor"/>
                    </div>
                </div>

                <div class="form-group">
                    <form:label cssClass="textWhite col-md-3 control-label" path="repositorios" cssErrorClass="">
                        Local
                    </form:label>

                    <div class="col-md-9">

                        <div class="row locais shadow">
                            <div class="col-md-5">
                                <h5 class="miniTitulo">Repositórios</h5>
                                <div id="tree_repositories">
                                    <ul>
                                        <form:checkboxes itemValue="id" path="repositorios" cssClass="hidden" 
                                                         element="li" items="${repositories}"/>
                                    </ul>
                                </div>
                            </div>

                            <c:if test="${!empty federations}">
                                <div class="col-md-7 overflowHidden">
                                    <h5 class="miniTitulo">Federações</h5>

                                    <div id="tree_federations">
                                        <ul>
                                            <c:forEach var="subFed" items="${federations}">
                                                <c:if test="${not empty subFed.repositorios}">
                                                    <li>
                                                        ${fn:toUpperCase(subFed.name)}
                                                        <ul>
                                                            <form:checkboxes itemValue="id" itemLabel="name" 
                                                                             path="repSubfed" cssClass="hidden" element="li" 
                                                                             items="${subFed.repositorios}"/>
                                                        </ul>
                                                    </li>
                                                </c:if>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                </div>
                            </c:if>

                            <div id="submitButton" class="row">
                                <div class="col-md-12 text-right">
                                    <button class="btn btn-success" type="submit">
                                        <i class="fa fa-search"> Buscar</i>
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!--/.col-md-8-->
                </div>


            </form:form>
            <div>
                <a class="lnkInTheHex text-right" href="${index}">Retornar para a busca padrão</a>
            </div>
        </div>
        <!-- /.container -->

        <!-- Footer -->
        <div class="footer">
            <div class="container">
                <div class="row">
                    <div class="col-xs-2">
                        <a href="http://capes.gov.br/">
                            <img src="${images}/logo-capes-rodape.png" alt="logotipo capes"/> 
                        </a>
                    </div>

                    <div class="col-xs-offset-2 col-xs-8">
                        <ul class="list-inline list-footer">
                            <li>
                                <a href="http://siteinroa.capes.gov.br/">Site do projeto</a>
                            </li>
                            <li>
                                <a href="http://www.rnp.br/pesquisa-e-desenvolvimento/grupos-trabalho">GTs RNP</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>


        <jsp:include page="fragments/scripts.jsp"/>

        <!-- JS Tree -->
        <script language="JavaScript" type="text/javascript" src="${scripts}/vendor/jsTree/dist/jstree.min.js"></script>

        <!-- CUSTOM JS -->
        <script language="JavaScript" type="text/javascript" src="${scripts}/buscaAvancada.js"></script>

        <jsp:include page="fragments/scriptsBarraGoverno.jsp"/>
        <jsp:include page="fragments/googleAnalytics.jsp">
            <jsp:param name="analyticsId" value="${analyticsId}" />
        </jsp:include>
    </body>
</html>
