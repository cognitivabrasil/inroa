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
<%@ taglib prefix="feb.spring" uri="http://www.springframework.org/tags"%>

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

            <ul class="infoDetalhada">
                <c:if test="${!empty metadata.general}">
                    <li>
                        <span class="title">Informa&ccedil;&otilde;es Gerais</span>
                        <ul>
                            <li><div class="nome">URL:</div>
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
                            </li>
                            <c:forEach var="title" items="${metadata.general.titles}">
                                <li><div class="nome">Título: </div><div class="valor">${title}</div></li>
                            </c:forEach>

                            <c:forEach var="language" items="${metadata.general.languages}">
                                <li><div class="nome">Idioma: </div><div class="valor">${language}</div></li>
                            </c:forEach>

                            <c:forEach var="description" items="${metadata.general.descriptions}">
                                <li><div class="nome">Descrição: </div><div class="valor">${description}</div></li>
                            </c:forEach>

                            <c:forEach var="keyword" items="${metadata.general.keywords}">
                                <li><div class="nome">Palavra-chave: </div><div class="valor">${keyword}</div></li>
                            </c:forEach>

                            <c:forEach var="coverage" items="${metadata.general.coverages}">
                                <li><div class="nome">Cobertura: </div><div class="valor">${coverage}</div></li>
                            </c:forEach>

                            <c:if test="${!empty metadata.general.structure}">
                                <li>
                                    <div class="nome">Estrutura: </div><div class="valor">${metadata.general.structure}</div>
                                </li> 
                            </c:if>
                            <c:if test="${!empty metadata.general.aggregationLevel}">
                                <li>
                                    <div class="nome">Nível de agregação: </div><div class="valor">${metadata.general.aggregationLevel}</div>
                                </li>
                            </c:if>
                        </ul>
                    </li>
                </c:if>

                <c:if test="${!empty metadata.lifeCycle}">
                    <li><span class="title">Ciclo de Vida</span>
                        <ul>

                            <c:if test="${!empty metadata.lifeCycle.version}">
                                <li><div class="nome">Versão: </div><div class="valor">${metadata.lifeCycle.version}</div></li>
                            </c:if>

                            <c:if test="${!empty metadata.lifeCycle.status}">
                                <li><div class="nome">Estado: </div><div class="valor">${metadata.lifeCycle.status}</div></li>
                            </c:if>

                            <c:forEach var="contribute" items="${metadata.lifeCycle.contribute}">
                                <li><span class="title">Contribuinte</span>
                                    <ul>
                                        <c:if test="${!empty contribute.role}">
                                            <li><div class="nome">Papel: </div><div class="valor">${contribute.role}</div></li>
                                        </c:if>

                                        <c:forEach var="entity" items="${contribute.entity}">
                                            <li><div class="nome">Entidade: </div><div class="valor">${entity}</div></li>
                                        </c:forEach>

                                        <c:if test="${!empty contribute.date}">
                                            <li><div class="nome">Data: </div><div class="valor">${contribute.date}</div></li>
                                        </c:if>

                                    </ul>
                                </li>
                            </c:forEach>

                        </ul> <!--/LIFE CYCLE-->
                    </li> <!--/LIFE CYCLE-->
                </c:if> <!--/LIFE CYCLE-->

                <c:if test="${!empty metadata.rights}">
                    <li><span class="title">Direitos</span>
                        <ul>
                            <c:if test="${!empty metadata.rights.cost}">
                                <li><div class="nome">Custo: </div><div class="valor">${metadata.rights.cost}</div></li>
                            </c:if>

                            <c:if test="${!empty metadata.rights.copyright}">
                                <li><div class="nome">Direito autoral: </div><div class="valor">${metadata.rights.copyright}</div></li>
                            </c:if>

                            <c:if test="${!empty metadata.rights.description}">
                                <li><div class="nome">Descrição: </div><div class="valor">${metadata.rights.description}</div></li>
                            </c:if>
                        </ul> <!--/RIGHTS-->
                    </li> <!--/RIGHTS-->
                </c:if> <!--/RIGHTS-->

                <c:if test="${!empty metadata.educational}">
                    <li><span class="title">Educacional</span>
                        <ul>
                            <c:if test="${!empty metadata.educational.interactivityType}">
                                <li><div class="nome">Tipo de interação: </div><div class="valor">${metadata.educational.interactivityType}</div></li>
                            </c:if>

                            <c:if test="${!empty metadata.educational.interactivityLevel}">
                                <li><div class="nome">Nível de interatividade: </div><div class="valor">${metadata.educational.interactivityLevel}</div></li>
                            </c:if>

                            <c:if test="${!empty metadata.educational.semanticDensity}">
                                <li><div class="nome">Densidade Semântica: </div><div class="valor">${metadata.educational.semanticDensity}</div></li>
                            </c:if>

                            <c:if test="${!empty metadata.educational.difficulty}">
                                <li><div class="nome">Dificuldade: </div><div class="valor">${metadata.educational.difficulty}</div></li>
                            </c:if>

                            <c:if test="${!empty metadata.educational.typicalLearningTime}">
                                <li><div class="nome">Tempo típico de aprendizagem: </div><div class="valor">${metadata.educational.typicalLearningTime}</div></li>
                            </c:if>

                            <c:forEach var="intendedEndUserRole" items="${metadata.educational.intendedEndUserRoles}">
                                <li><div class="nome">Desenvolvido para: </div><div class="valor">${intendedEndUserRole}</div></li>
                            </c:forEach>

                            <c:forEach var="learningResourceType" items="${metadata.educational.learningResourceTypes}">
                                <li><div class="nome">Tipo de recurso: </div><div class="valor">${learningResourceType}</div></li>
                            </c:forEach>

                            <c:forEach var="typicalAgeRange" items="${metadata.educational.typicalAgeRanges}">
                                <li><div class="nome">Faixa etária: </div><div class="valor">${typicalAgeRange}</div></li>
                            </c:forEach>

                            <c:forEach var="description" items="${metadata.educational.descriptions}">
                                <li><div class="nome">Descrição: </div><div class="valor">${description}</div></li>
                            </c:forEach>

                            <c:forEach var="language" items="${metadata.educational.languages}">
                                <li><div class="nome">Idioma: </div><div class="valor">${language}</div></li>
                            </c:forEach>

                            <c:forEach var="context" items="${metadata.educational.contexts}">
                                <li><div class="nome">Contexto: </div><div class="valor">${context}</div></li>
                            </c:forEach>
                        </ul> <!--/educational-->
                    </li> <!--/educational-->
                </c:if> <!--/educational-->

                <c:if test="${!empty metadata.technical}">
                    <li><span class="title">Informa&ccedil;&otilde;es t&eacute;cnicas</span>
                        <ul>
                            <c:forEach var="location" items="${metadata.technical.location}">
                                <li><div class="nome">Localização: </div><div class="valor">
                                        <a href="${location}" target="_blank">${location}</a>
                                    </div></li>
                                </c:forEach>

                        </ul> <!--/technical-->
                    </li><!--/technical-->
                </c:if> <!--/technical-->
            </ul>

            <input class="BOTAO" type="button" value="&lArr; Voltar"
                   onclick="javascript:history.back(-1);" />
        </div>        

        <%@include file="googleAnalytics"%>
    </body>
</html>
