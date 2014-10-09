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
<c:url var="bootstrap" value="scripts/vendor/bootstrap-3.1.1-dist/css/bootstrap.min.css" />
<c:url var="fontawsome" value="/css/vendor/font-awesome-4.2.0/css/font-awesome.min.css" />
<c:url var="fonts" value="/css/fonts.css" />

<html lang="pt-BR">

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
        <link rel="StyleSheet" href="${index}" type="text/css">

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
        <div id="wrap"></div>
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

                        <a href="index.html">
                            <img id="logo" src="${images}/logo.png" alt="" />
                        </a>
                    </div>
                    <!--/.intro-message-->
                </div>
                <!--./col-lg-12-->
            </div>
            <!--./row-->

            <div class="row">
                <div class="col-lg-12">
                    <form:form method="get" modelAttribute="buscaModel" action="consulta" acceptCharset="utf-8">

                        <div class="searchBox shadow input-group">

                            <div class="form-group">
                                <label class="sr-only" for="consulta">Termo de busca</label>
                                <form:input path="consulta" type="text" cssClass="form-control" placeholder="Termo de busca" value="" required="required" />
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
                    <div class="col-lg-12">
                        <ul class="list-inline">
                            <li>
                                <a href="http://feb.ufrgs.br">Página antiga do projeto</a>
                            </li>
                            <li class="footer-menu-divider">&sdot;</li>
                            <li>
                                <a href="http://www.rnp.br/pesquisa-e-desenvolvimento/grupos-trabalho">GTs RNP</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>

        <!-- jQuery Version 1.11.0 -->
        <script language="javascript" type="text/javascript" src='${scripts}/vendor/jquery-1.11.0.min.js'></script>

        <!-- Bootstrap Core JavaScript -->
        <script language="javascript" type="text/javascript" src='${scripts}/vendor/bootstrap-3.1.1-dist/js/bootstrap.min.js'></script>
        <!--Custom JS-->
        <script language="javascript" type="text/javascript" src='${scripts}/toggleSearch.js'></script>

        <!-- Barra do Governo Federal -->
        <script defer="defer" async="async" src="//barra.brasil.gov.br/barra.js" type="text/javascript"></script>

        <%@include file="googleAnalytics"%>
    </body>

</html>
