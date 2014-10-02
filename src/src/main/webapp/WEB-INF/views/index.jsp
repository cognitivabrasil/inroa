<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<html lang="pt-BR">

    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Infraestrutura Nacional de Repositórios de Objetos de Aprendizagem</title>

        <!-- Bootstrap Core CSS -->
        <link href="css/bootstrap.min.css" rel="stylesheet">

        <!-- Custom CSS -->
        <link href="css/main.css" rel="stylesheet">

        <!-- Custom Fonts -->
        <link href="font-awesome-4.2.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">
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

        <div class="container text-center">

            <div class="row">
                <div class="col-lg-12">
                    <div class="intro-message">

                        <img id="logo" src="imagens/logo.png" alt="" />

                        <div id="searchBox" class="input-group">

                            <input type="text" class="form-control" placeholder="Termo de busca" value="" />
                            <span class="input-group-btn">
                                <button class="btn btn-success" type="submit">
                                    <i class="fa fa-search"> Buscar</i></button>
                            </span>
                        </div>
                        <!-- /.input-group -->

                        <a id="lnkAvancada" href="./buscaAvancada">Busca Avançada</a>
                    </div>
                </div>
            </div>
        </div>
        <!-- /.container -->

        <!-- Footer -->
        <footer>
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
        </footer>

        <!-- jQuery Version 1.11.0 -->
        <script src="scripts/jquery-1.11.0.js"></script>

        <!-- Bootstrap Core JavaScript -->
        <script src="scripts/bootstrap.min.js"></script>

        <!-- Barra do Governo Federal -->
        <script defer="defer" async="async" src="//barra.brasil.gov.br/barra.js" type="text/javascript"></script>
    </body>

</html>
