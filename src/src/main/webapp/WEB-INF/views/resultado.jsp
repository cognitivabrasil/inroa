<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html lang="pt-BR" xmlns:jsp="http://java.sun.com/JSP/Page"
      xmlns:c="http://java.sun.com/jsp/jstl/core">

    <jsp:directive.page contentType="text/html;charset=UTF-8" />
    <jsp:directive.page pageEncoding="UTF-8" />


    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <meta name="description" content="" />
        <meta name="author" content="" />

        <title>Infraestrutura Nacional de Repositórios de Objetos de Aprendizagem</title>

        <!-- Bootstrap Core CSS -->
        <c:url var="bootstrap" value="scripts/vendor/bootstrap-3.1.1-dist/css/bootstrap.min.css" />
        <link rel="StyleSheet" href="${bootstrap}" type="text/css" />

        <!-- Custom CSS -->
        <c:url var="cssMain" value="/css/main.css" />
        <link href="${cssMain}" rel="stylesheet" />
        <c:url var="cssResult" value="/css/result.css" />
        <link href="${cssResult}" rel="stylesheet" />

        <!-- Custom Fonts -->
        <c:url var="cssFontawsome" value="/css/vendor/font-awesome-4.2.0/css/font-awesome.min.css" />
        <link href="${cssFontawsome}" rel="stylesheet" type="text/css" />


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
                    <a href="http://brasil.gov.br" 
                       style="font-family:sans,sans-serif; text-decoration:none; color:white;">Portal do Governo Brasileiro</a>
                </li> 

                <li>
                    <a style="font-family:sans,sans-serif; text-decoration:none; color:white;" 
                       href="http://epwg.governoeletronico.gov.br/barra/atualize.html">Atualize sua Barra de Governo</a>
                </li>
            </ul>
        </div>
        <!-- /#barra-brasil -->

        <div class="container text-center">

            <div class="row">
                <div class="col-lg-12">
                    <div class="intro-message">
                        <a href="index.html">
                            <img id="logo" src="imagens/logo.png" alt=""/>
                        </a>
                    </div>
                    <!--/.intro-message-->
                </div>
                <!--/.col-lg-12-->
            </div>
            <!--/.row-->
            <div id="preResult" class="row relative">

                <div class="col-lg-3">
                    <a href="index.html">
                        <button class="btn btn-lg btn-success" type="submit">Efetuar nova consulta</button>
                    </a>
                </div>
                <!--/.col-lg-3-->
                <div class="col-lg-9 text-right vbottom">
                    <c:url var="rsslink" value="rss/feed?${buscaModel.urlEncoded}"/>
                    <a id="rssBtn" class="btn btn-xs btn-success" href="${rsslink}"><i class="fa fa-rss"></i></a>
                    <span class="text"> 
                        Consulta efetuada: <strong><c:out value="${buscaModel.consulta}"/></strong> 
                        / Total de <strong>${buscaModel.sizeResult}</strong> objetos 
                    </span>
                </div>
                <!--/.col-lg-9-->
            </div>
            <!--/#preResult .row-->
            <!-- Pagination Bar -->               
            <div class="row pagination-centered">
                <ul class="pagination">
                    <c:if test="${documents.hasPreviousPage()}">
                        <li>                        
                            <a href="${docUrl}/page/${documents.number-1}" title="Ir para página anterior">Anterior</a>

                        </li>           
                    </c:if>                                                                        

                    <c:forEach var="paginasDisponiveis" items="${pages}">
                        <c:choose>  
                            <c:when test="${paginasDisponiveis == documents.number}">
                                <li class="active">
                                    <a href="${docUrl}/page/${paginasDisponiveis}"><span text="${item.number}">${paginasDisponiveis+1}</span></a>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li>
                                    <a href="${docUrl}/page/${paginasDisponiveis}"><span text="${item.number}">${paginasDisponiveis+1}</span></a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                    <c:if test="${documents.hasNextPage()}">
                        <li>                        
                            <a href="${docUrl}/page/${documents.number+1}" title="Ir para próxima página">Próxima</a>
                        </li>
                    </c:if>
                </ul>                            
            </div>
            <div class="row">
                <div id="result" class="shadow">
                    <c:forEach var="doc" items="${documentos}" varStatus="status">
                        <c:url var="exibeMetadados" value="objetos/${doc.id}"/>
                        <div class="resultadoConsulta">
                            <c:if test="${empty doc.titles}">
                                <div class="titulo"><a href='${exibeMetadados}'>T&iacute;tulo n&atilde;o informado.</a></div>
                            </c:if>
                            <c:forEach var="titulo" items="${doc.titles}">
                                <div class="titulo">
                                    <a href='${exibeMetadados}'>${titulo}</a>
                                </div>
                            </c:forEach>
                            <c:forEach var="resumo" items="${doc.shortDescriptions}">
                                <div class="atributo">${resumo}</div>
                            </c:forEach>   
                        </div> 
                        <div class="atributo">
                            Localização: 
                            <c:forEach var="localizacao" items="${doc.locationHttp}">
                                <c:if test="${localizacao.value}">
                                    <br />
                                    <a class="verifyUrl" href="${localizacao.key}" target="_new">${localizacao.key}</a>
                                </c:if>
                            </c:forEach>
                        </div>
                        <div class="atributo">
                            Repositório: ${doc.nomeRep}
                        </div>
                    </c:forEach>
                    <div class="resultadoConsulta">
                        <div class="titulo"> <a> Um estudo sobre polinômios e sua abordagem no ensino</a></div>
                        <div class="atributo">
                            Este trabalho tem como o objetivo de estudar Polinômios além do que foi estudado na Graduação, fazer um breve estudo de como se está ensinando Polinômios de acordo com alguns livros didáticos e, principalmente, propor algumas atividades que contribuam para o trabalho dos professores, despertando assim o interesse dos alunos pelo estudo da Algebra.
                            TCC (graduação) - Universidade Federal de Santa Catarina, Centro de Ciências Físicas e Matemáticas, Curso de Matemática.
                        </div>

                        <div class="atributo">
                            Localização: 
                            <br />
                            <a href="http://repositorio.ufsc.br/xmlui/handle/123456789/94973">
                                http://repositorio.ufsc.br/xmlui/handle/123456789/94973
                            </a>
                        </div>

                        <div class="atributo">
                            Repositório: Subfederação UFSC / Repositório UFSC
                        </div>
                    </div>
                    <!--/.resultadoConsulta-->

                    <div class="resultadoConsulta">
                        <div class="titulo"> <a> Um estudo sobre polinômios e sua abordagem no ensino</a></div>
                        <div class="atributo">
                            Este trabalho tem como o objetivo de estudar Polinômios além do que foi estudado na Graduação, fazer um breve estudo de como se está ensinando Polinômios de acordo com alguns livros didáticos e, principalmente, propor algumas atividades que contribuam para o trabalho dos professores, despertando assim o interesse dos alunos pelo estudo da Algebra.
                            TCC (graduação) - Universidade Federal de Santa Catarina, Centro de Ciências Físicas e Matemáticas, Curso de Matemática.
                        </div>

                        <div class="atributo">
                            Localização: 
                            <a href="http://repositorio.ufsc.br/xmlui/handle/123456789/94973">http://repositorio.ufsc.br/xmlui/handle/123456789/94973</a>
                        </div>

                        <div class="atributo">
                            Repositório: Subfederação UFSC / Repositório UFSC
                        </div>
                    </div>
                    <!--/.resultadoConsulta-->
                </div>
                <!--/#result-->
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
                        <li class="footer-menu-divider">|</li>
                        <li>
                            <a href="http://www.rnp.br/pesquisa-e-desenvolvimento/grupos-trabalho">GTs RNP</a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </footer>
    
    <c:url var="imgTrue" value="/imagens/check.png"/>
    <img id="imgUrlTrue" class='imgchecklink' src="${imgTrue}" border='0' alt='Link correto' title='link ativo'>
    
       <!-- jQuery Version 1.11.0 -->
       <c:url var="jquery" value="/scripts/vendor/jquery-1.7.2.js"/>
    <script language="javascript" type="text/javascript" src='${jquery}'></script>

    <!-- Bootstrap Core JavaScript -->
    <c:url var="bootstrap" value="/scripts/vendor/bootstrap-3.1.1-dist/js/bootstrap.min.js"/>
    <script language="javascript" type="text/javascript" src='${bootstrap}'></script>


    <!-- Barra do Governo Federal -->
    <script defer="defer" async="async" src="//barra.brasil.gov.br/barra.js" type="text/javascript"></script>
    
    <c:url var="root" value="/" />
    <script>rootUrl = "${root}";</script>

    <c:url var="validateURL" value="/scripts/testUrlActive.js" />
    <script type="text/javascript" src="${validateURL}"></script>
</body>

</html>
