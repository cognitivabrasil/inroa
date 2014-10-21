<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:url var="images" value="/imagens" />
<c:url var="index" value="/" />
<c:url var="adm" value="/admin" />
<c:url var="scripts" value="/scripts" />
<c:url var="css" value="/css/main.css" />
<c:url var="index" value="/css/busca.css" />

<html lang="pt-BR">

    <head>

        <jsp:include page="fragments/htmlHeader.jsp"/>

        <!-- index CSS -->
        <link rel="StyleSheet" href="${index}" type="text/css">

        <!--Para parecer um aplicativo standalone em dispositivos apple-->
        <meta name="apple-mobile-web-app-capable" content="yes">

    </head>

    <body>
        <jsp:include page="fragments/cabecalho.jsp"/>

        <div class="container">

            <div class="row">
                <div class="col-lg-12">
                    <c:if test="${!empty erro}">
                        <div class="alert alert-danger text-center">${erro}</div>
                    </c:if>
                    <form:form method="get" modelAttribute="buscaModel" action="resultado" acceptCharset="utf-8">

                        <form:errors path="consulta" element="div" cssClass="alert alert-danger text-center" />

                        <div class="searchBox shadow input-group">

                            <div class="form-group">
                                <label class="sr-only" for="consulta">Termo de busca</label>                                
                                <form:input path="consulta" type="text" cssClass="form-control" 
                                            placeholder="Termo de busca" value="" required="required" />
                            </div>

                            <span class="input-group-btn hidden-xs">
                                <button class="btn btn-success" type="submit">
                                    <i class="fa fa-search visible-lg"> Buscar</i>
                                    <i class="fa fa-search visible-md visible-sm"></i>
                                </button>
                            </span>
                        </div>
                        <!-- /.serachBox .input-group -->

                    </form:form>

                    <a id="lnkAvancada" class="lnkInTheHex" href="./buscaAvancada">Busca Avançada</a>
                </div>
                <!--/.col-lg-12-->
            </div>
            <!--/.row-->

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
                                <a href="http://feb.ufrgs.br">Site do projeto</a>
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

        <!--Custom JS-->
        <script language="javascript" type="text/javascript" src='${scripts}/toggleSearch.js'></script>

        
        <jsp:include page="fragments/scriptsBarraGoverno.jsp"/>
        
        <%@include file="googleAnalytics"%>
    </body>

</html>
