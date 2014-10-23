<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<c:url var="images" value="/imagens" />

<html lang="pt-BR" xmlns:jsp="http://java.sun.com/JSP/Page"
      xmlns:c="http://java.sun.com/jsp/jstl/core">

    <jsp:directive.page contentType="text/html;charset=UTF-8" />
    <jsp:directive.page pageEncoding="UTF-8" />

    <c:url var="imagens" value="/imagens" />

    <head>
        <jsp:include page="fragments/htmlHeader.jsp"/>

        <c:url var="cssResult" value="/css/result.css" />
        <link href="${cssResult}" rel="stylesheet" />

    </head>

    <body>
        <jsp:include page="fragments/cabecalho.jsp"/>
        <div class="container">

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

            <div class="row">
                <div id="5" class="">
                    <c:if test="${empty documentos}">
                        <div class="resultadoConsulta text-center well text-info">
                            <h4>Nenhum resultado encontrato</h4>
                        </div>
                    </c:if>
                    <c:forEach var="doc" items="${documentos}" varStatus="status">
                        <c:url var="exibeMetadados" value="objetos/${doc.id}"/>
                        <div class="well shadow">
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

                            <div class="atributo">
                                Localização: 
                                <c:forEach var="localizacao" items="${doc.locationHttp}">
                                    <c:if test="${localizacao.value}">
                                        <br />
                                        <a class="verifyUrl breakWord" href="${localizacao.key}" target="_new">${localizacao.key}</a>
                                    </c:if>
                                </c:forEach>
                            </div>
                            <div class="atributo">
                                Repositório: ${doc.nomeRep}
                            </div>
                        </div>
                        <!--/.resultadoConsulta-->

                    </c:forEach>

                </div>
                <!--/#result-->
            </div>
            <!--/.row-->
            <c:choose>
                <c:when test="${avancada}">
                    <c:url var="docUrl" value="/resultadoav?${buscaModel.urlEncoded}"/>
                </c:when>
                <c:otherwise>
                    <c:url var="docUrl" value="/resultado?${buscaModel.urlEncoded}"/>
                </c:otherwise>
            </c:choose>
            
             <!-- Pagination Bar -->               
                <div class="row text-center">
                    <ul class="pagination shadow">
                      <c:if test="${pagination.hasPreviousPage()}">
                        <li>                        
                          <a href="${docUrl}&page=${pagination.previousPage}" title="Ir para página anterior">Anterior</a>
                          
                        </li>           
                      </c:if>                                                                        
                       
                      <c:forEach var="page" items="${pagination.pages}">
                          <c:choose>  
                            <c:when test="${page == pagination.currentPage}">
                                <li class="active">
                                     <a href="${docUrl}&page=${page}"><span text="${item.number}">${page+1}</span></a>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li>
                                     <a href="${docUrl}&page=${page}"><span text="${item.number}">${page+1}</span></a>
                                </li>
                            </c:otherwise>
                          </c:choose>
                      </c:forEach>
                                
                      <c:if test="${pagination.hasNextPage()}">
                        <li>                        
                          <a href="${docUrl}&page=${pagination.nextPage}" title="Ir para próxima página">Próxima</a>
                        </li>
                      </c:if>
                    </ul>                            
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

        <!-- jQuery Version 1.11.0 -->
        <c:url var="jquery" value="/scripts/vendor/jquery-1.7.2.js"/>
        <script language="javascript" type="text/javascript" src='${jquery}'></script>

        <!-- Bootstrap Core JavaScript -->
        <c:url var="bootstrap" value="/scripts/vendor/bootstrap-3.1.1-dist/js/bootstrap.min.js"/>
        <script language="javascript" type="text/javascript" src='${bootstrap}'></script>

        <c:url var="root" value="/" />
        <script>rootUrl = "${root}";</script>

        <c:url var="validateURL" value="/scripts/testUrlActive.js" />
        <script type="text/javascript" src="${validateURL}"></script>
        <%@include file="googleAnalytics"%>
        <jsp:include page="fragments/scriptsBarraGoverno.jsp"/>
    </body>

</html>
