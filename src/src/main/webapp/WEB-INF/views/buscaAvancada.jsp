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
<c:url var="fontawsome" value="/css/font-awesome-4.2.0/css/font-awesome.min.css" />
<c:url var="fontawsome" value="/css/fonts.css" />


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

                        <a href="index.html">
                            <img id="logo" src="imagens/logo.png" alt=""/>
                        </a>
                    </div>
                    <!--/.intro-message-->
                </div>
                <!--./col-lg-12-->
            </div>
            <!--./row-->
            <div class="row field">
                <div class="col-lg-6">
                    <span class="textWhite">Texto para a busca</span>
                </div>
                <!--/.col-lg-6-->

                <div class="col-lg-6">
                    <input class="form-control shadow" />
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
                    <input class="form-control shadow" />
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
                            <div class="checkboxList Value">
                                - Repositórios:
                                <div id="tree_repositories">
                                    <ul>
                                        <form:checkboxes itemValue="id" path="repositorios" cssClass="hidden" element="li" items="${repositories}"/>
                                    </ul>
                                </div>
                            </div>
                        </div>

                        <div class="col-lg-6">
                            asdfff
                        </div>
                    </div>
                </div>
                <!--/.col-lg-6-->
            </div>
            <!--/.row-->

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


        <div id="page-index">
            <c:if test="${!empty erro}">
                <div class="DivErro" id="MensagemErro">${erro}</div>
            </c:if>

            <form:form method="POST" modelAttribute="buscaModel" acceptCharset="utf-8">   
                <div id="index">
                    <a href="${index}">
                        <img src="${logoReduzido}" alt="Logo FEB_reduzido" class="logo"/>
                    </a>

                    <div class="clear"> </div>
                    <div class="EspacoAntes">&nbsp;</div>
                    <%--<form:errors path="*" cssClass="ValueErro" />--%>

                    <div id="buscaAvancada">
                        <div class="LinhaEntrada">
                            <form:errors path="consulta" cssClass="ValueErro" />
                            <label class="LabelLeft" for="consulta" >
                                <strong>Texto</strong> para a busca
                            </label>
                            <div class="Value">
                                <form:input path="consulta"/>
                            </div>
                        </div>  

                        <div class="LinhaEntrada">
                            <label class="LabelLeft" for="autor" >
                                Pesquisar objetos <strong>de autoria</strong> de
                            </label>
                            <div class="Value">
                                <form:input path="autor"/>
                            </div>
                        </div>

                        <div class="LinhaEntrada">
                            <div class="LabelLeft">
                                Pesquisar <b>tamanho</b> de objetos
                            </div>
                            <div class="Value">
                                <form:input path="size"/>
                            </div>
                        </div>

                        <div class="LinhaEntrada">
                            <div class="LabelLeft">
                                Pesquisar <b>formato</b> de objetos
                            </div>
                            <div class="Value">
                                <form:select path="format" items="${buscaModel.mimeTypes}"/>
                            </div>
                        </div>

                        <div class="LinhaEntrada">
                            <form:errors path="idioma" cssClass="ValueErro" />
                            <div class="LabelLeft">
                                <b>Idioma</b>
                            </div>
                            <div class="Value">
                                <form:select path="idioma" items="${buscaModel.languages}"/>
                            </div>
                        </div> 

                        <div class="LinhaEntrada">
                            <div class="LabelLeft">
                                <b>Custo</b>
                            </div>
                            <div class="Value">
                                <form:radiobutton path="cost" value="false"/> Grátis
                                <form:radiobutton path="cost" value="true"/> Pagos
                                <form:radiobutton path="cost" value=""/> Ambos
                            </div>
                        </div> 


                        <div class="LinhaEntrada">
                            <div class="LabelLeft"> 
                                Objetos com <b>visual</b>
                            </div>
                            <div class="Value">
                                <form:radiobutton path="hasVisual" value="true"/> Sim
                                <form:radiobutton path="hasVisual" value="false"/> Não
                                <form:radiobutton path="hasVisual" value=""/> Ambos
                            </div>
                        </div> 

                        <div class="LinhaEntrada">
                            <div class="LabelLeft">
                                Objetos <b>auditivos</b>
                            </div>
                            <div class="Value">
                                <form:radiobutton path="hasAuditory" value="true"/> Sim
                                <form:radiobutton path="hasAuditory" value="false"/> Não
                                <form:radiobutton path="hasAuditory" value=""/> Ambos
                            </div>
                        </div>

                        <div class="LinhaEntrada">
                            <div class="LabelLeft">
                                Objetos <b>textuais</b>
                            </div>
                            <div class="Value">
                                <form:radiobutton path="hasText" value="true"/> Sim
                                <form:radiobutton path="hasText" value="false"/> Não
                                <form:radiobutton path="hasText" value=""/> Ambos
                            </div>
                        </div>

                        <div class="LinhaEntrada">
                            <div class="LabelLeft">
                                Objetos <b>táteis</b>
                            </div>
                            <div class="Value">
                                <form:radiobutton path="hasTactile" value="true"/> Sim
                                <form:radiobutton path="hasTactile" value="false"/> Não
                                <form:radiobutton path="hasTactile" value=""/> Ambos
                            </div>
                        </div>


                        <div class="LinhaEntrada">
                            <div class="LabelLeft">
                                Faixa etária:
                            </div>
                            <div class="Value slider-margin">
                                <form:input path="ageRange" class="span2" data-slider-min="0" data-slider-max="20" data-slider-step="1" data-slider-value="[0,20]"/>
                                <input id="adultAge" name="adultAge" type="checkbox" value="true"/> 19 ou +
                            </div>
                        </div>
                        <div class="LinhaEntrada">
                            <div class="LabelLeft">
                                Local para busca
                            </div>
                            <div class="checkboxList Value">
                                - Repositórios:
                                <div id="tree_repositories">
                                    <ul>
                                        <form:checkboxes itemValue="id" path="repositorios" cssClass="hidden" element="li" items="${repositories}"/>
                                    </ul>
                                </div>
                            </div>
                            <div class='checkboxList Value'>
                                <c:if test="${!empty federations}">
                                    - Federa&ccedil;&otilde;es
                                    <div id="tree_federations">
                                        <ul>
                                            <c:forEach var="subFed" items="${federations}">

                                                <li>
                                                    ${fn:toUpperCase(subFed.name)}
                                                    <ul>
                                                        <form:checkboxes itemValue="id" itemLabel="name" path="repSubfed" cssClass="hidden" element="li" items="${subFed.repositorios}"/>
                                                    </ul>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                </c:if>
                            </div>

                        </div>
                        <div class="LinhaEntrada">
                            <div class="Buttons">
                                <input class="BOTAO" type="submit" value="Consultar"/>
                            </div>
                        </div>
                        <div class="EspacoAntes">&nbsp;</div>
                        <div>
                            <a href="${index}">Retornar a busca padr&atilde;o</a>
                        </div>
                    </div>
                </form:form>
            </div>

            <!-- jQuery Version 1.11.0 -->
            <!--<script src="scripts/jquery-1.11.0.js"></script>-->
            <script language="javascript" type="text/javascript" src='${scripts}/vendor/jquery-1.7.2.js'></script>

            <!-- Bootstrap Core JavaScript -->
            <script language="javascript" type="text/javascript" src='${scripts}/vendor/bootstrap-3.1.1-dist/js/bootstrap.min.js'></script>
            <script src="scripts/bootstrap.min.js"></script>

            <!-- Barra do Governo Federal -->
            <script defer="defer" async="async" src="//barra.brasil.gov.br/barra.js" type="text/javascript"></script>
    </body>
</html>
