<%--

modelo de tópico:

<div class="metadados">
    <div class="subnivel">
        <div class="titulo">nivel 1</div>
        <div class="subnivel">
            <div class="titulo">nivel 2</div>
            <div class="subnivel">
                <div class="titulo">nivel3</div> 
                <div class="atributo">
                    <div class="nome">Atributo: </div>
                    <div class="valor">valor1</div>
                    <div class="valor">valor2</div>
                </div>
            </div>
        </div>
    </div>
</div>

--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>GT-FEB – Federação de Repositórios Educa Brasil</title>
        <link rel="StyleSheet" href="../css/padrao.css" type="text/css">
        <link href="../imagens/favicon.ico" rel="shortcut icon"
              type="image/x-icon" />
        <script type="text/javascript"
                src="https://apis.google.com/js/plusone.js">
                    {
                        lang: 'pt-BR'
                    }
        </script>

    </head>
    <body id="bodyLetraMaior">


        <div id="page">
            <jsp:include page="cabecalho.jsp">
                <jsp:param value="Resultado da Pesquisa" name="titulo" />
                <jsp:param value="7%" name="tamanho" />
            </jsp:include>

            <input class="BOTAO" type="button" value="&lArr; Voltar"
                   onclick="javascript:history.back(-1);" />

            <div class="socialBookmarks">
                <div class="socialBookmark">
                    <g:plusone size="medium"></g:plusone>
                </div>

                <div class="socialBookmark">
                    <a href="http://twitter.com/share" class="twitter-share-button"
                       data-count="horizontal">Tweet</a>
                    <script type="text/javascript"
                    src="http://platform.twitter.com/widgets.js"></script>
                </div>
                <div id="fb-root" class="socialBookmark">
                    <script
                    src="http://connect.facebook.net/pt_BR/all.js#appId=109445635823915&amp;xfbml=1"></script>
                    <fb:like href="" send="false" width="80" show_faces="false" layout="button_type" colorscheme="light"
                             action="recommend" font=""></fb:like>

                </div>
            </div>
            <div class="clear"> </div>
            <div class="tituloPrincipal">
                <div class="tituloObj">
                    &diams; ${title}


                </div>
                <div class="identificadorObj">Objeto ${obaaEntry}</div>

            </div>

            <div class="metadados">

                <c:if test="${metadata.general != null}">
                    <!--Informacoes gerais-->
                    <div class="subnivel">

                        <div class="titulo">Informa&ccedil;&otilde;es Gerais</div>

                        <div class="atributo">

                            <div class="nome">URL:</div>
                            <c:choose>
                                <c:when
                                    test="${metadata.technical != null && metadata.technical.location != null}">
                                    <div class="valor">
                                        <a href="${metadata.technical.firstHttpLocation}" target="_blank">${metadata.technical.firstHttpLocation}</a>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="valor">Este objeto n&atilde;o possui URL associada!</div>
                                </c:otherwise>
                            </c:choose>
                        </div>

                        <c:if test="${!empty metadata.general.languages}">
                            <div class="atributo">
                                <div class="nome">Idioma:</div>
                                <c:forEach var="language" items="${metadata.general.languages }">
                                <div class="valor">&nbsp; ${language}</div>
                                </c:forEach>
                            </div>
                        </c:if>

                        <div class="atributo">
                            <div class="nome">Descrição:</div>
                            <c:forEach var="descricao" items="${metadata.general.descriptions}">
                                <div class="valor">${descricao}</div>
                            </c:forEach>
                        </div>

                        <div class="atributo">
                            <div class="nome">Palavra-chave:</div>
                            <c:forEach var="keyword" items="${metadata.general.keywords}">
                                <div class="valor">${keyword}</div>
                            </c:forEach>
                        </div>

                    </div>
                </c:if>



                <c:if test="${metadata.lifeCycle != null}">
                    <div class="subnivel">
                        <div class="titulo">Ciclo de vida</div>

                        <c:if test="${metadata.lifeCycle.version != null}">
                            <div class="atributo">
                                <div class="nome">Vers&atilde;o:</div>
                                <div class="valor">${metadata.lifeCycle.version}</div>
                            </div>                        
                        </c:if>
                        <c:if test="${metadata.lifeCycle.status != null}">
                            <div class="atributo">
                                <div class="nome">Status:</div>
                                <div class="valor">${metadata.lifeCycle.status}</div>
                            </div>                        
                        </c:if>


                        <c:if test="${metadata.lifeCycle.contribute != null}">
                            <div class="subnivel">
                                <div class="titulo">Contribute</div>

                                <c:forEach var="contribute" items="${metadata.lifeCycle.contribute}">
                                    <c:if test="${!empty contribute.role}">
                                        <div class="atributo">
                                            <div class="nome">Papel:</div>
                                            <div class="valor">${contribute.role}</div>
                                        </div>
                                    </c:if>
                                    <c:if test="${!empty contribute.date}">
                                        <div class="atributo">
                                            <div class="nome">Data:</div>
                                            <div class="valor">${contribute.date}</div>
                                        </div>
                                    </c:if>
                                    <c:if test="${!empty contribute.entity}">
                                        <div class="atributo">
                                            <div class="nome">Entidades:</div>
                                            <c:forEach var="entity" items="${contribute.entity}">
                                                <div class="valor">${entity}</div>
                                            </c:forEach>
                                        </div>
                                    </c:if>
                                </c:forEach>
                            </div>
                        </c:if>
                    </div>
                </c:if>

            </div>

            <input class="BOTAO" type="button" value="&lArr; Voltar"
                   onclick="javascript:history.back(-1);" />

        </div>

        <%@include file="googleAnalytics"%>
    </body>
</html>
