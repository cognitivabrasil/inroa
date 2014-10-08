<%-- 
    Document   : index
    Created on : 25/06/2009, 18:26:51
    Author     : Marcos Nunes
    Updated on : 19/03/2014, 14:05
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:url var="images" value="/imagens" />
<c:url var="index" value="/" />
<c:url var="adm" value="/admin" />
<c:url var="scripts" value="/scripts" />
<c:url var="css" value="/css/main.css" />
<c:url var="avancada" value="/css/buscaAvancada.css" />
<c:url var="bootstrap" value="scripts/vendor/bootstrap-3.1.1-dist/css/bootstrap.min.css" />
<c:url var="fontawsome" value="/css/vendor/font-awesome-4.2.0/css/font-awesome.min.css" />
<c:url var="fonts" value="/css/fonts.css" />
<c:url var="cssBusca" value="/css/busca.css" />


<html>
    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Infraestrutura Nacional de Repositórios de Objetos de Aprendizagem</title>

        <!-- Bootstrap Core CSS -->
        <link rel="StyleSheet" href="${bootstrap}" type="text/css">

        <!-- Custom CSS -->
        <link rel="StyleSheet" href="${css}" type="text/css">
        <link rel="StyleSheet" href="${avancada}" type="text/css">
        <link rel="StyleSheet" href="${cssBusca}" type="text/css">

        <!-- Custom Fonts -->
        <link rel="StyleSheet" href="${fontawsome}" type="text/css">
        <link rel="StyleSheet" href="${fonts}" type="text/css">

        <!--conferir as fontes do designer-->
        <!--<link href="http://fonts.googleapis.com/css?family=Lato:300,400,700,300italic,400italic,700italic" rel="stylesheet" type="text/css">-->

        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
            <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
            <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->


    </head>

    <body>
        <div id="barra-brasil" style="background:#7F7F7F; height: 20px; padding:0 0 0 10px;display:block;"> 
            <ul id="menu-barra-temp" style="list-style:none;">
                <li style="display:inline; float:left;padding-right:10px; margin-right:10px; border-right:1px solid #EDEDED">
                    <a href="http://brasil.gov.br" style="font-family:sans,sans-serif; text-decoration:none; color:white;">Portal do Governo Brasileiro</a>
                </li> 

                <li>
                    <a style="font-family:sans,sans-serif; text-decoration:none; color:white;" href="http://epwg.governoeletronico.gov.br/barra/atualize.html">Atualize sua Barra de Governo</a>
                </li>
            </ul>
        </div>
        <!-- /#barra-brasil -->

        <div class="container">

            <div class="row text-center">
                <div class="col-lg-12">
                    <div class="intro-message">

                        <a href="${index}">
                            <img id="logo" src="imagens/logo.png" alt=""/>
                        </a>
                    </div>
                    <!--/.intro-message-->
                </div>
                <!--./col-lg-12-->
            </div>
            <!--./row-->

            <c:if test="${!empty erro}">
                <div class="DivErro" id="MensagemErro">${erro}</div>
            </c:if>

            <form:form method="POST" modelAttribute="buscaModel" acceptCharset="utf-8"> 


                <div class="row field">
                    <!--                    <div class="col-lg-12">
                    <%--<form:errors path="consulta" cssClass="error" />--%>
                </div>-->

                    <div class="col-lg-6">
                        <span class="textWhite">Texto para a busca</span>
                    </div>
                    <!--/.col-lg-6-->

                    <div class="col-lg-6">                   

                        <form:input class="form-control shadow" path="consulta" cssErrorClass="form-control shadow error"/>
                    </div>
                    <!--/.col-lg-6-->
                </div>
                <!--/.row-->

                <div class="row field">
                    <div class="col-lg-6">
                        <span class="textWhite">Autor</span>
                    </div>
                    <!--/.col-lg-6-->

                    <div class="col-lg-6">
                        <form:input class="form-control shadow" path="autor"/>
                    </div>
                    <!--/.col-lg-6-->
                </div>
                <!--/.row-->

                <div class="row field">
                    <div class="col-lg-6">
                        <span class="textWhite">Local</span>
                    </div>
                    <!--/.col-lg-6-->

                    <div class="col-lg-6">

                        <div class="row locais shadow">
                            <div class="col-lg-6">
                                <h5 class="miniTitulo">Repositórios</h5>
                                <div class="checkboxList Value">

                                    <div id="tree_repositories">
                                        <ul>
                                            <form:checkboxes itemValue="id" path="repositorios" cssClass="hidden" element="li" items="${repositories}"/>
                                        </ul>
                                    </div>
                                </div>
                            </div>

                            <div class="col-lg-6">
                                <h5 class="miniTitulo">Federações</h5>
                                <div class="checkboxList Value">

                                    <div id="tree_repositories">
                                        <ul>
                                            <form:checkboxes itemValue="id" path="repositorios" cssClass="hidden" element="li" items="${repositories}"/>
                                        </ul>
                                    </div>
                                </div>
                            </div>

                            <div id="submitButton" class="row">
                                <div class="col-lg-12 text-right">
                                    <button class="btn btn-success" type="submit">
                                        <i class="fa fa-search"> Buscar</i>
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!--/.col-lg-6-->

                </div>
                <!--/.row-->
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
                    <div class="col-lg-12">
                        <ul class="list-inline">
                            <li>
                                <a href="http://feb.ufrgs.br">Página antiga do projeto</a>
                            </li>
                            <li class="footer-menu-divider">|</li>
                            <li>
                                <a href="http://www.rnp.br/pesquisa-e-desenvolvimento/grupos-trabalho">GTs RNP</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>


        <!-- jQuery Version 1.11.0 -->
        <!--<script src="scripts/jquery-1.11.0.js"></script>-->
        <script language="javascript" type="text/javascript" src='${scripts}/vendor/jquery-1.7.2.js'></script>

        <!-- Bootstrap Core JavaScript -->
        <script language="javascript" type="text/javascript" src='${scripts}/vendor/bootstrap-3.1.1-dist/js/bootstrap.min.js'></script>

        <!-- Barra do Governo Federal -->
        <script defer="defer" async="async" src="//barra.brasil.gov.br/barra.js" type="text/javascript"></script>
    </body>
</html>
