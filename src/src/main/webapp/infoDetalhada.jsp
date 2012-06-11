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
                                        <a href="${metadata.technical.location}" target="_blank">${metadata.technical.location}</a>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="valor">Este objeto n&atilde;o possui URL associada!</div>
                                </c:otherwise>
                            </c:choose>
                        </div>

                        <c:if test="${!empty metadata.general.language}">
                            <div class="atributo">
                                <div class="nome">Idioma:</div>
                                <div class="valor">&nbsp; ${metadata.general.language}</div>
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

                <div id="sidetree">
            <div class="treeheader">&nbsp;</div>
            <div id="sidetreecontrol"><a href="?#">Contrair Todos</a> | <a href="?#">Expandir Todos</a></div>

            <c:choose>

                <c:when test="${empty metadata.general.identifiers}">
                    <h3>N&atilde;o h&aacute; metadados cadastrados para esse objeto!</h3>
                </c:when>
                <c:otherwise>
                    <ul id="tree" class="treeview-famfamfam">
                        <c:if test="${!empty metadata.general}">
                            <li><span class="title">General</span>
                                <ul>
                                    <c:forEach var="identifier" items="${metadata.general.identifiers}">
                                        <li><span class="title">Identificador</span>
                                            <ul>
                                                <c:if test="${!empty identifier.entry}">
                                                    <li><strong>Entry: </strong>${identifier.entry}</li>
                                                </c:if>
                                                <c:if test="${!empty identifier.catalog}">
                                                    <li><strong>Catálogo: </strong>${identifier.catalog}</li>
                                                </c:if>
                                            </ul>
                                        </li>
                                    </c:forEach>

                                    <c:forEach var="title" items="${metadata.general.titles}">
                                        <li><strong>Título: </strong>${title}</li>
                                    </c:forEach>

                                    <c:forEach var="language" items="${metadata.general.languages}">
                                        <li><strong>Idioma: </strong>${language}</li>
                                    </c:forEach>

                                    <c:forEach var="description" items="${metadata.general.descriptions}">
                                        <li><strong>Descrição: </strong>${description}</li>
                                    </c:forEach>

                                    <c:forEach var="keyword" items="${metadata.general.keywords}">
                                        <li><strong>Palavra-chave: </strong>${keyword}</li>
                                    </c:forEach>

                                    <c:forEach var="coverage" items="${metadata.general.coverages}">
                                        <li><strong>Cobertura: </strong>${coverage}</li>
                                    </c:forEach>

                                    <c:if test="${!empty metadata.general.structure}">
                                        <li><strong>Estrutura: </strong>${metadata.general.structure}</li>
                                    </c:if>
                                    <c:if test="${!empty metadata.general.aggregationLevel}">
                                        <li><strong>Nível de agregação: </strong>${metadata.general.aggregationLevel}</li>
                                    </c:if>
                                </ul> <!--/GENERAL-->
                            </li> <!--/GENERAL-->
                        </c:if> <!--/GENERAL-->

                        <c:if test="${!empty metadata.lifeCycle}">
                            <li><span class="title">Ciclo de Vida</span>
                                <ul>
                                    <li>
                                        <c:if test="${!empty metadata.lifeCycle.version}">
                                        <li><strong>Versão: </strong>${metadata.lifeCycle.version}</li>
                                    </c:if>

                                    <c:if test="${!empty metadata.lifeCycle.status}">
                                        <li><strong>Estado: </strong>${metadata.lifeCycle.status}</li>
                                    </c:if>

                                    <c:forEach var="contribute" items="${metadata.lifeCycle.contribute}">
                                        <li><span class="title">Contribuinte</span>
                                            <ul>
                                                <c:if test="${!empty contribute.role}">
                                                    <li><strong>Papel: </strong>${contribute.role}</li>
                                                </c:if>

                                                <c:forEach var="entity" items="${contribute.entity}">
                                                    <li><strong>Entidade: </strong>${entity}</li>
                                                </c:forEach>

                                                <c:if test="${!empty contribute.date}">
                                                    <li><strong>Data: </strong>${contribute.date}</li>
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
                                        <li><strong>Custo: </strong>${metadata.rights.cost}</li>
                                    </c:if>

                                    <c:if test="${!empty metadata.rights.copyright}">
                                        <li><strong>Direito autoral: </strong>${metadata.rights.copyright}</li>
                                    </c:if>

                                    <c:if test="${!empty metadata.rights.description}">
                                        <li><strong>Descrição: </strong>${metadata.rights.description}</li>
                                    </c:if>
                                </ul> <!--/RIGHTS-->
                            </li> <!--/RIGHTS-->
                        </c:if> <!--/RIGHTS-->

                        <c:if test="${!empty metadata.educational}">
                            <li><span class="title">Educacional</span>
                                <ul>
                                    <c:if test="${!empty metadata.educational.interactivityType}">
                                        <li><strong>Tipo de interação: </strong>${metadata.educational.interactivityType}</li>
                                    </c:if>

                                    <c:if test="${!empty metadata.educational.interactivityLevel}">
                                        <li><strong>Nível de interatividade: </strong>${metadata.educational.interactivityLevel}</li>
                                    </c:if>

                                    <c:if test="${!empty metadata.educational.semanticDensity}">
                                        <li><strong>Densidade Semântica: </strong>${metadata.educational.semanticDensity}</li>
                                    </c:if>

                                    <c:if test="${!empty metadata.educational.difficulty}">
                                        <li><strong>Dificuldade: </strong>${metadata.educational.difficulty}</li>
                                    </c:if>

                                    <c:if test="${!empty metadata.educational.typicalLearningTime}">
                                        <li><strong>Tempo típico de aprendizagem: </strong>${metadata.educational.typicalLearningTime}</li>
                                    </c:if>

                                    <c:forEach var="intendedEndUserRole" items="${metadata.educational.intendedEndUserRoles}">
                                        <li><strong>Desenvolvido para: </strong>${intendedEndUserRole}</li>
                                    </c:forEach>

                                    <c:forEach var="learningResourceType" items="${metadata.educational.learningResourceTypes}">
                                        <li><strong>Tipo de recurso: </strong>${learningResourceType}</li>
                                    </c:forEach>

                                    <c:forEach var="typicalAgeRange" items="${metadata.educational.typicalAgeRanges}">
                                        <li><strong>Faixa etária: </strong>${typicalAgeRange}</li>
                                    </c:forEach>

                                    <c:forEach var="description" items="${metadata.educational.descriptions}">
                                        <li><strong>Descrição: </strong>${description}</li>
                                    </c:forEach>

                                    <c:forEach var="language" items="${metadata.educational.languages}">
                                        <li><strong>Idioma: </strong>${language}</li>
                                    </c:forEach>

                                    <c:forEach var="context" items="${metadata.educational.contexts}">
                                        <li><strong>Contexto: </strong>${context}</li>
                                    </c:forEach>
                                </ul> <!--/educational-->
                            </li> <!--/educational-->
                        </c:if> <!--/educational-->

                        <c:if test="${!empty metadata.technical}">
                            <li><span class="title">Technical</span>
                                <ul>
                                    <c:forEach var="location" items="${metadata.technical.location}">
                                        <li><strong>Localização: </strong>${location}</li>
                                    </c:forEach>


                                </ul> <!--/technical-->
                            </li><!--/technical-->
                        </c:if> <!--/technical-->
                    </ul>
                </c:otherwise>

            </c:choose>
        </div>


        <%@include file="googleAnalytics"%>
    </body>
</html>
