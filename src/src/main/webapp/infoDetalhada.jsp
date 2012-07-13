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
                    <div class="nome">Atributo:</div>
                    <div class="valor">valor1</div>
                    <div class="valor">valor2</div>
                </div>
            </div>
        </div>
    </div>
</div>

<c:forEach var="xx" items="${xx}">
                                    <li><span class="title">xx</span>
                                        <ul>
                                            <c:forEach var="xx" items="${xx}">
                                                <li><span class="title">orComposite</span>
                                                    <ul>
                                                        <c:if test="${!empty xx}">
                                                        <li><div class="nome">Tipo:</div><div class="valor">${xx}</div></li>
                                                        </c:if>
                                                        <c:if test="${!empty xx}">
                                                        <li><div class="nome">Tipo:</div><div class="valor">${xx}</div></li>
                                                        </c:if>
                                                        <c:if test="${!empty xx}">
                                                        <li><div class="nome">Tipo:</div><div class="valor">${xx}</div></li>
                                                        </c:if>
                                                        <c:if test="${!empty xx}">
                                                        <li><div class="nome">Tipo:</div><div class="valor">${xx}</div></li>
                                                        </c:if>
                                                        xxfor

                                                    </ul>
                                                </li>
                                            </c:forEach>

--%>

<%@page import="org.apache.bcel.generic.AALOAD"%>
<%@page import="cognitivabrasil.obaa.OBAA"%>
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
            <c:if test="${!empty metadata}">
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
                                    <li><div class="nome">Título:</div><div class="valor">${title}</div></li>
                                </c:forEach>

                                <c:forEach var="language" items="${metadata.general.languages}">
                                    <li><div class="nome">Idioma:</div><div class="valor">${language}</div></li>
                                </c:forEach>

                                <c:forEach var="description" items="${metadata.general.descriptions}">
                                    <li><div class="nome">Descrição:</div><div class="valor">${description}</div></li>
                                </c:forEach>

                                <c:forEach var="keyword" items="${metadata.general.keywords}">
                                    <li><div class="nome">Palavra-chave:</div><div class="valor">${keyword}</div></li>
                                </c:forEach>

                                <c:forEach var="coverage" items="${metadata.general.coverages}">
                                    <li><div class="nome">Cobertura:</div><div class="valor">${coverage}</div></li>
                                </c:forEach>

                                <c:if test="${!empty metadata.general.structure}">
                                    <li>
                                        <div class="nome">Estrutura:</div><div class="valor">${metadata.general.structure}</div>
                                    </li>
                                </c:if>
                                <c:if test="${!empty metadata.general.aggregationLevel}">
                                    <li>
                                        <div class="nome">Nível de agregação:</div><div class="valor">${metadata.general.aggregationLevel}</div>
                                    </li>
                                </c:if>
                            </ul>
                        </li>
                    </c:if>

                    <c:if test="${!empty metadata.lifeCycle}">
                        <li><span class="title">Ciclo de Vida</span>
                            <ul>
                                <c:if test="${!empty metadata.lifeCycle.version}">
                                    <li><div class="nome">Versão:</div><div class="valor">${metadata.lifeCycle.version}</div></li>
                                </c:if>

                                <c:if test="${!empty metadata.lifeCycle.status}">
                                    <li><div class="nome">Estado:</div><div class="valor">${metadata.lifeCycle.status}</div></li>
                                </c:if>

                                <c:forEach var="contribute" items="${metadata.lifeCycle.contribute}">
                                    <li><span class="title">Contribuinte</span>
                                        <ul>
                                            <c:if test="${!empty contribute.role}">
                                                <li><div class="nome">Papel:</div><div class="valor">${contribute.role}</div></li>
                                            </c:if>

                                            <c:forEach var="entity" items="${contribute.entity}">
                                                <li><div class="nome">Entidade:</div><div class="valor">${entity}</div></li>
                                            </c:forEach>

                                            <c:if test="${!empty contribute.date}">
                                                <li><div class="nome">Data:</div><div class="valor">${contribute.date}</div></li>
                                            </c:if>

                                        </ul>
                                    </li>
                                </c:forEach>

                            </ul> <!--/LIFE CYCLE-->
                        </li> <!--/LIFE CYCLE-->
                    </c:if> <!--/LIFE CYCLE-->

                    <c:if test="${!empty metadata.technical}">
                        <li><span class="title">Informa&ccedil;&otilde;es t&eacute;cnicas</span>
                            <ul>
                                <c:forEach var="format" items="${metadata.technical.format}">
                                    <li><div class="nome">Formato:</div><div class="valor">${format}</div></li>
                                </c:forEach>

                                <li><div class="nome">Tamanho:</div><div class="valor">${metadata.technical.size}</div></li>

                                <c:forEach var="location" items="${metadata.technical.location}">
                                    <li><div class="nome">Localiza&ccedil;&atilde;o:</div><div class="valor"><a href="${location}" target="_blank">${location}</a></div></li>
                                </c:forEach>

                                <c:forEach var="requirement" items="${metadata.technical.requirement}">
                                    <li><span class="title">Requisitos</span>
                                        <ul>
                                            <c:forEach var="orComposite" items="${requirement.orComposite}" varStatus="i">
                                                <li><span class="title">op&ccedil;&atilde;o ${i.index+1}</span>
                                                    <ul>
                                                        <c:if test="${!empty orComposite.type}">
                                                            <li><div class="nome">Tipo:</div><div class="valor">${orComposite.type}</div></li>
                                                        </c:if>
                                                        <c:if test="${!empty orComposite.name}">
                                                            <li><div class="nome">Nome:</div><div class="valor">${orComposite.name}</div></li>
                                                        </c:if>
                                                        <c:if test="${!empty orComposite.minimumVersion}">
                                                            <li><div class="nome">Vers&atilde;o M&iacute;nima:</div><div class="valor">${orComposite.minimumVersion}</div></li>
                                                        </c:if>
                                                        <c:if test="${!empty orComposite.maximumVersion}">
                                                            <li><div class="nome">Vers&atilde;o M&aacute;xima:</div><div class="valor">${orComposite.maximumVersion}</div></li>
                                                        </c:if>
                                                    </ul>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </li>
                                </c:forEach>

                                <c:if test="${!empty metadata.technical.installationRemarks}">
                                    <li><div class="nome">Notas da instala&ccedil;&atilde;o:</div><div class="valor">${metadata.technical.installationRemarks}</div></li>
                                </c:if>
                                <c:if test="${!empty metadata.technical.otherPlatformRequirements}">
                                    <li><div class="nome">Outros requisitos da plataforma:</div><div class="valor">${metadata.technical.otherPlatformRequirements}</div></li>
                                </c:if>
                                <c:if test="${!empty metadata.technical.duration.text}">
                                    <li><div class="nome">Dura&ccedil;&atilde;o:</div><div class="valor">${metadata.technical.duration}</div></li>
                                </c:if>

                                <c:forEach var="var" items="${metadata.technical.supportedPlatforms}">
                                    <li><div class="nome">Plataforma suportada:</div><div class="valor">${var}</div></li>
                                </c:forEach>

                                <c:forEach var="platformSpecificFeature" items="${metadata.technical.platformSpecificFeature}">
                                    <li><span class="title">Caracter&iacute;stica espec&iacute;fica da plataforma</span>
                                        <ul>
                                            <c:if test="${!empty platformSpecificFeature.platformType}">
                                                <li><div class="nome">Tipo de plataforma:</div><div class="valor">${platformSpecificFeature.platformType}</div></li>
                                            </c:if>
                                            <c:forEach var="format" items="${platformSpecificFeature.SpecificFormats}">
                                                <li><div class="nome">Formato espec&iacute;fico:</div><div class="valor">${format}</div></li>
                                            </c:forEach>
                                            <c:if test="${!empty platformSpecificFeature.specificSize}">
                                                <li><div class="nome">Tamanho espec&iacute;fico:</div><div class="valor">${platformSpecificFeature.specificSize}</div></li>
                                            </c:if>
                                            <c:if test="${!empty platformSpecificFeature.specificLocation}">
                                                <li><div class="nome">Localiza&ccedil;&atilde;o espec&iacute;fica:</div><div class="valor">${platformSpecificFeature.specificLocation}</div></li>
                                            </c:if>

                                            <c:forEach var="specificRequirements" items="${platformSpecificFeature.specificRequirements}">
                                                <li><span class="title">Requisitos espec&iacute;ficos</span>
                                                    <ul>
                                                        <c:forEach var="specificOrComposite" items="${specificRequirements.specificOrComposite}">
                                                            <li><span class="title">Specific or Composite</span>
                                                                <ul>
                                                                    <c:if test="${!empty specificOrComposite.specificType}">
                                                                        <li><div class="nome">Tipo:</div><div class="valor">${specificOrComposite.specificType}</div></li>
                                                                    </c:if>
                                                                    <c:if test="${!empty specificOrComposite.name}">
                                                                        <li><div class="nome">Nome:</div><div class="valor">${specificOrComposite.specificName}</div></li>
                                                                    </c:if>
                                                                    <c:if test="${!empty specificOrComposite.specificMinimumVersion}">
                                                                        <li><div class="nome">Vers&atilde;o M&iacute;nima:</div><div class="valor">${specificOrComposite.specificMinimumVersion}</div></li>
                                                                    </c:if>
                                                                    <c:if test="${!empty specificOrComposite.specificMaximumVersion}">
                                                                        <li><div class="nome">Vers&atilde;o M&aacute;xima:</div><div class="valor">${specificOrComposite.specificMaximumVersion}</div></li>
                                                                    </c:if>
                                                                </ul>
                                                            </li>
                                                        </c:forEach>
                                                    </ul>
                                                </li>
                                            </c:forEach>

                                            <c:if test="${!empty platformSpecificFeature.specificInstallationRemarks}">
                                                <li><div class="nome">Observa&ccedil;&otilde;es espec&iacute;ficas de instala&ccedil;&atilde;o:</div><div class="valor">${platformSpecificFeature.specificInstallationRemarks}</div></li>
                                            </c:if>
                                            <c:if test="${!empty platformSpecificFeature.specificOtherPlatformRequirements}">
                                                <li><div class="nome">Outros requisitos espec&iacute;ficos de plataforma:</div><div class="valor">${platformSpecificFeature.specificOtherPlatformRequirements}</div></li>
                                            </c:if>
                                        </ul>
                                    </li>
                                </c:forEach>

                                <c:forEach var="service" items="${metadata.technical.service}">
                                    <li><span class="title">Servi&ccedil;o</span>
                                        <ul>
                                            <c:if test="${!empty service.name}">
                                                <li><div class="nome">Nome:</div><div class="valor">${service.name}</div></li>
                                            </c:if>
                                            <c:if test="${!empty service.type}">
                                                <li><div class="nome">Tipo:</div><div class="valor">${service.type}</div></li>
                                            </c:if>
                                            <c:if test="${!empty service.provides}">
                                                <li><div class="nome">Fornece:</div><div class="valor">${service.provides}</div></li>
                                            </c:if>
                                            <c:if test="${!empty service.essential}">
                                                <li><div class="nome">Essencial:</div><div class="valor">${service.essential}</div></li>
                                            </c:if>

                                            <c:forEach var="protocol" items="${service.protocol}">
                                                <li><div class="nome">Protocolo:</div><div class="valor">${service.protocol}</div></li>
                                            </c:forEach>

                                            <c:forEach var="ontology" items="${service.ontology}">
                                                <li><span class="title">Ontologia</span>
                                                    <ul>
                                                        <c:if test="${!empty ontology.language}">
                                                            <li><div class="nome">Linguagem:</div><div class="valor">${ontology.language}</div></li>
                                                        </c:if>
                                                        <c:if test="${!empty ontology.location}">
                                                            <li><div class="nome">Localiza&ccedil;&atilde;o:</div><div class="valor">${ontology.location}</div></li>
                                                        </c:if>
                                                    </ul>
                                                </li>
                                            </c:forEach>

                                            <c:forEach var="language" items="${service.language}">
                                                <li><div class="nome">Linguagem:</div><div class="valor">${service.language}</div></li>
                                            </c:forEach>

                                            <c:forEach var="details" items="${service.details}">
                                                <li><span class="title">Detalhes</span>
                                                    <ul>
                                                        <c:if test="${!empty details.details}">
                                                            <li><div class="nome">Descri&ccedil;&atilde;o:</div><div class="valor">${details.details}</div></li>
                                                        </c:if>
                                                        <c:if test="${!empty details.serviceLocation}">
                                                            <li><div class="nome">Localiza&ccedil;&atilde;o do servi&ccedil;o:</div><div class="valor">${details.serviceLocation}</div></li>
                                                        </c:if>
                                                    </ul>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </li>
                                </c:forEach>


                            </ul> <!--/technical-->
                        </li><!--/technical-->
                    </c:if> <!--/technical-->

                    <c:if test="${!empty metadata.educational}">
                        <li><span class="title">Educacional</span>
                            <ul>
                                <c:if test="${!empty metadata.educational.interactivityType}">
                                    <li><div class="nome">Tipo de interação:</div><div class="valor">${metadata.educational.interactivityType}</div></li>
                                </c:if>

                                <c:forEach var="learningResourceType" items="${metadata.educational.learningResourceTypes}">
                                    <li><div class="nome">Tipo de recurso:</div><div class="valor">${learningResourceType}</div></li>
                                </c:forEach>

                                <c:if test="${!empty metadata.educational.interactivityLevel}">
                                    <li><div class="nome">Nível de interatividade:</div><div class="valor">${metadata.educational.interactivityLevel}</div></li>
                                </c:if>

                                <c:if test="${!empty metadata.educational.semanticDensity}">
                                    <li><div class="nome">Densidade Semântica:</div><div class="valor">${metadata.educational.semanticDensity}</div></li>
                                </c:if>

                                <c:forEach var="intendedEndUserRole" items="${metadata.educational.intendedEndUserRoles}">
                                    <li><div class="nome">Desenvolvido para:</div><div class="valor">${intendedEndUserRole}</div></li>
                                </c:forEach>

                                <c:forEach var="context" items="${metadata.educational.contexts}">
                                    <li><div class="nome">Contexto:</div><div class="valor">${context}</div></li>
                                </c:forEach>

                                <c:forEach var="typicalAgeRange" items="${metadata.educational.typicalAgeRanges}">
                                    <li><div class="nome">Faixa etária:</div><div class="valor">${typicalAgeRange}</div></li>
                                </c:forEach>

                                <c:if test="${!empty metadata.educational.difficulty}">
                                    <li><div class="nome">Dificuldade:</div><div class="valor">${metadata.educational.difficulty}</div></li>
                                </c:if>

                                <c:if test="${!empty metadata.educational.typicalLearningTime}">
                                    <li><div class="nome">Tempo típico de aprendizagem:</div><div class="valor">${metadata.educational.typicalLearningTime}</div></li>
                                </c:if>

                                <c:forEach var="description" items="${metadata.educational.descriptions}">
                                    <li><div class="nome">Descrição:</div><div class="valor">${description}</div></li>
                                </c:forEach>

                                <c:forEach var="language" items="${metadata.educational.languages}">
                                    <li><div class="nome">Idioma:</div><div class="valor">${language}</div></li>
                                </c:forEach>

                                <c:if test="${!empty metadata.educational.learningContentType.text}">
                                    <li><div class="nome">Tipo do conte&uacute;do:</div><div class="valor">${metadata.educational.learningContentType}</div></li>
                                </c:if>

                                <c:if test="${!empty metadata.educational.interaction}">
                                    <li>
                                        <span class="title">Intera&ccedil;&atilde;o</span>
                                        <ul>
                                            <c:if test="${!empty metadata.educational.interaction.interactionType.text}">
                                                <li><div class="nome">Tipo de intera&ccedil;&atilde;o:</div><div class="valor">${metadata.educational.interaction.interactionType}</div></li>
                                            </c:if>
                                            <c:if test="${!empty metadata.educational.interaction.perception.text}">
                                                <li><div class="nome">Percep&ccedil;&atilde;o:</div><div class="valor">${metadata.educational.interaction.perception}</div></li>
                                            </c:if>
                                            <c:if test="${!empty metadata.educational.interaction.synchronism.text}">
                                                <li><div class="nome">Sincronismo:</div><div class="valor">${metadata.educational.interaction.synchronism}</div></li>
                                            </c:if>
                                            <c:if test="${!empty metadata.educational.interaction.coPresence.text}">
                                                <li><div class="nome">Co-presen&ccedil;a:</div><div class="valor">${metadata.educational.interaction.coPresence}</div></li>
                                            </c:if>
                                            <c:if test="${!empty metadata.educational.interaction.reciprocity.text}">
                                                <li><div class="nome">Reciprocidade:</div><div class="valor">${metadata.educational.interaction.reciprocity}</div></li>
                                            </c:if>
                                        </ul>
                                    </li>
                                </c:if>

                                <c:forEach var="didaticStrategy" items="${metadata.educational.didaticStrategy}">
                                    <li><div class="nome">Estratégias Didáticas:</div><div class="valor">${didaticStrategy}</div></li>
                                </c:forEach>

                            </ul> <!--/educational-->
                        </li> <!--/educational-->
                    </c:if> <!--/educational-->

                    <c:if test="${!empty metadata.rights}">
                        <li><span class="title">Direitos</span>
                            <ul>
                                <c:if test="${!empty metadata.rights.cost}">
                                    <li><div class="nome">Custo:</div><div class="valor">${metadata.rights.cost}</div></li>
                                </c:if>
                                <c:if test="${!empty metadata.rights.copyright}">
                                    <li><div class="nome">Direito autoral:</div><div class="valor">${metadata.rights.copyright}</div></li>
                                </c:if>
                                <c:if test="${!empty metadata.rights.description}">
                                    <li><div class="nome">Descrição:</div><div class="valor">${metadata.rights.description}</div></li>
                                </c:if>
                            </ul> <!--/RIGHTS-->
                        </li> <!--/RIGHTS-->
                    </c:if> <!--/RIGHTS-->
                    <c:forEach var="relations" items="${metadata.relations}">
                        <li>
                            <span class="title">Rela&ccedil;&atilde;o</span>
                            <ul>
                                <c:if test="${!empty relations.kind}">
                                    <li><div class="nome">Tipo:</div><div class="valor">${relations.kind}</div></li>
                                </c:if>

                                <c:if test="${!empty relations.resource}">
                                    <li>
                                        <span class="title">Recurso</span>
                                        <ul>
                                            <c:forEach var="identifier" items="${relations.resource.identifier}">
                                                <li>
                                                    <span class="title">Identificador</span>
                                                    <ul>
                                                        <c:if test="${!empty identifier.entry}">
                                                            <li><div class="nome">Identificador:</div><div class="valor">${identifier.entry}</div></li>
                                                        </c:if>
                                                        <c:if test="${!empty identifier.catalog}">
                                                            <li><div class="nome">Cat&aacute;logo:</div><div class="valor">${identifier.catalog}</div></li>
                                                        </c:if>
                                                    </ul>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </li>
                                </c:if>
                            </ul> <!--/RELATION-->
                        </li> <!--/RELATION-->
                    </c:forEach> <!--/RELATION-->

                </ul>
            </c:if>

            <input class="BOTAO" type="button" value="&lArr; Voltar"
                   onclick="javascript:history.back(-1);" />
        </div>

        <%@include file="googleAnalytics"%>
    </body>
</html>
