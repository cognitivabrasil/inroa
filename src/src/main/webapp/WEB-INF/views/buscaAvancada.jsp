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
                <div class="DivErro" id="MensagemErro">${erro}</div>
            </c:if>

            <form:form method="POST" modelAttribute="buscaModel" acceptCharset="utf-8"> 

                <div class="row">
                    <div class="col-md-1"></div>
                    <div class="col-md-10">
                        <form:errors path="consulta" element="div" cssClass="alert alert-danger text-center" />
                    </div>
                    <div class="col-md-1"></div>
                </div>
                
                <div class="row field">              


                    <div class="col-md-4">
                        <form:label cssClass="textWhite" path="consulta" cssErrorClass="">
                            Texto para a busca
                        </form:label>
                    </div>
                    <!--/.col-md-4-->


                    <div class="col-md-8">
                        <form:input cssClass="form-control shadow" path="consulta" cssErrorClass="form-control shadow"/>
                    </div>
                    <!--/.col-md-8-->
                </div>
                <!--/.row-->

                <div class="row field">
                    <div class="col-md-4">
                        <span class="textWhite">Autor</span>
                    </div>
                    <!--/.col-md-6-->

                    <div class="col-md-8">
                        <form:input class="form-control shadow" path="autor"/>
                    </div>
                    <!--/.col-md-6-->
                </div>
                <!--/.row-->

                <div class="row field">
                    <div class="col-md-4">
                        <span class="textWhite">Local</span>
                    </div>
                    <!--/.col-md-6-->

                    <div class="col-md-8">

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
                    <!--/.col-md-6-->

                </div>
                <!--/.row-->
            </form:form> 
            <div>
                <a class="lnkInTheHex text-right" href="${index}">Retornar para a busca padrão</a>
            </div>
        </div>
        <!-- /.container -->

        <jsp:include page="fragments/scripts.jsp"/>
 
        <!-- JS Tree -->
        <script language="JavaScript" type="text/javascript" src="${scripts}/vendor/jsTree/dist/jstree.min.js"></script>

        <!-- CUSTOM JS -->
        <script language="JavaScript" type="text/javascript" src="${scripts}/buscaAvancada.js"></script>

        <!-- Barra do Governo Federal -->       
        <script defer="defer" async="async" src="//barra.brasil.gov.br/barra.js" type="text/javascript"></script>
        <%@include file="googleAnalytics"%>
    </body>
</html>
