<%--
@author Marcos Nunes <marcosn@gmail.com>

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
                                                        <li>
<div class="nome">Tipo:</div><div class="valor">${xx}</div>
</li>
                                                        </c:if>
                                                        <c:if test="${!empty xx}">
                                                        <li>
<div class="nome">Tipo:</div><div class="valor">${xx}</div>
</li>
                                                        </c:if>
                                                        <c:if test="${!empty xx}">
                                                        <li>
<div class="nome">Tipo:</div><div class="valor">${xx}</div>
</li>
                                                        </c:if>
                                                        <c:if test="${!empty xx}">
                                                        <li>
<div class="nome">Tipo:</div><div class="valor">${xx}</div>
</li>
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
        <title>FEB – Federação de Repositórios Educa Brasil</title>
        <c:url value="/images/favicon.ico" var="favicon" />
        <link rel="shortcut icon" href="${favicon}" />
        
        <c:url value="/css/padrao.css" var="app_css_url" />
        <link rel="stylesheet" type="text/css" media="screen" href="${app_css_url}" />
        
        <c:url value="/scripts/vendor/jsTree/dist/themes/default/style.min.css" var="jstree_css_url" />
        <link rel="stylesheet" type="text/css" media="screen" href="${jstree_css_url}" />
        

        <c:url var="root" value="/" />
        <script>
            rootUrl = ${root};
            obaaJson= ${obaaJson};
        </script>
        <c:url var="jquery" value="/scripts/vendor/jquery-1.11.0.min.js" />
        <script language="javascript" type="text/javascript" src='${jquery}'></script>
        <c:url var="jsTree" value="/scripts/vendor/jsTree/dist/jstree.min.js" />
        <script language="javascript" type="text/javascript" src='${jsTree}'></script>
        <c:url var="validateURL" value="/scripts/testUrlActive.js" />
        <script type="text/javascript" src="${validateURL}"></script>
        <c:url var="showJs" value="/scripts/showMetadata.js" />
        <script type="text/javascript" src="${showJs}"></script>

        <script type="text/javascript"
                src="https://apis.google.com/js/plusone.js">
            {
                lang: 'pt-BR';
            }
        </script>
        <script>
            
            (function(d, s, id) {
                var js, fjs = d.getElementsByTagName(s)[0];
                if (d.getElementById(id))
                    return;
                js = d.createElement(s);
                js.id = id;
                js.src = "//connect.facebook.net/pt_BR/all.js#xfbml=1";
                fjs.parentNode.insertBefore(js, fjs);
            }(document, 'script', 'facebook-jssdk'));
        </script>

    </head>
    <body>
        <div id="fb-root"></div>
        

        <div id="page">
            <jsp:include page="cabecalho.jsp">
                <jsp:param value="Resultado da Pesquisa" name="titulo" />
                <jsp:param value="7%" name="tamanho" />
            </jsp:include>

            <input class="BOTAO" type="button" value="&lArr; Voltar"
                   onclick="javascript:history.back(-1);" />

            <div class="socialBookmarks">
                <div class="socialBookmark">
                    <div class="fb-share-button" data-type="button_count"></div>
                </div>


                <div class="socialBookmark">
                    <g:plusone size="medium"></g:plusone>
                </div>

                <div class="socialBookmark">
                    <a href="http://twitter.com/share" class="twitter-share-button"
                       data-count="horizontal">Tweet</a>
                    <script type="text/javascript"
                    src="http://platform.twitter.com/widgets.js"></script>
                </div>


            </div>
            <div class="clear"></div>
            <div class="tituloPrincipal">
                <div class="tituloObj">${title}</div>
                <div class="identificadorObj">Objeto ${obaaEntry}</div>

            </div>
                <div id="jstree_demo" class="metadataTree">d</div>
            <c:if test="${!empty metadata}">
                <ul class="infoDetalhada">
                    <c:if test="${!empty metadata.general}">
                        <li><span class="title">Informa&ccedil;&otilde;es
                                Gerais</span>
                            <ul>
                                <c:forEach var="title" items="${metadata.general.titles}">
                                    <c:if test="${!empty title}">
                                        <li>
                                            <div class="nome">Título:</div>
                                            <div class="valor">${title}</div>
                                        </li>
                                    </c:if>
                                </c:forEach>

                                <c:forEach var="language" items="${metadata.general.languages}">
                                    <c:if test="${!empty language}">
                                        <li>
                                            <div class="nome">Idioma:</div>
                                            <div class="valor">${language}</div>
                                        </li>
                                    </c:if>
                                </c:forEach>

                                <c:forEach var="description"
                                           items="${metadata.general.descriptions}">
                                    <c:if test="${!empty description}">
                                        <li>
                                            <div class="nome">Descrição:</div>
                                            <div class="valor">${description}</div>
                                        </li>
                                    </c:if>
                                </c:forEach>
                                <c:forEach var="keyword" items="${metadata.general.keywords}">
                                    <c:if test="${!empty keyword}">
                                        <li>
                                            <div class="nome">Palavra-chave:</div>
                                            <div class="valor">${keyword}</div>
                                        </li>
                                    </c:if>
                                </c:forEach>

                                <c:forEach var="coverage" items="${metadata.general.coverages}">
                                    <c:if test="${!empty coverage}">
                                        <li>
                                            <div class="nome">Cobertura:</div>
                                            <div class="valor">${coverage}</div>
                                        </li>
                                    </c:if>
                                </c:forEach>
                                <c:set var="structure">${metadata.general.structure}</c:set>
                                <c:if test="${!empty structure}">
                                    <li>
                                        <div class="nome">Estrutura:</div>
                                        <div class="valor">${structure}</div>
                                    </li>
                                </c:if>
                                <c:if test="${!empty metadata.general.aggregationLevel}">
                                    <li>
                                        <div class="nome">Nível de agregação:</div>
                                        <div class="valor">${metadata.general.aggregationLevel}</div>
                                    </li>
                                </c:if>
                            </ul></li>
                        </c:if>

                    <c:if test="${!empty metadata.lifeCycle}">
                        <li><span class="title">Ciclo de Vida</span>
                            <ul>
                                <c:if test="${!empty metadata.lifeCycle.version}">
                                    <li>
                                        <div class="nome">Versão:</div>
                                        <div class="valor">${metadata.lifeCycle.version}</div>
                                    </li>
                                </c:if>

                                <c:if test="${!empty metadata.lifeCycle.status}">
                                    <li>
                                        <div class="nome">Estado:</div>
                                        <div class="valor">${metadata.lifeCycle.status}</div>
                                    </li>
                                </c:if>

                                <c:forEach var="contribute"
                                           items="${metadata.lifeCycle.contribute}">
                                    <li><span class="title">Contribuinte</span>
                                        <ul>

                                            <c:if test="${!empty contribute.role}">
                                                <li>
                                                    <div class="nome">Papel:</div>
                                                    <div class="valor">${contribute.role}</div>
                                                </li>
                                            </c:if>
                                            <c:forEach var="entity" items="${contribute.entity}">
                                                <c:if test="${!empty entity}">
                                                    <li>
                                                        <div class="nome">Entidade:</div>
                                                        <div class="valor">${entity}</div>
                                                    </li>
                                                </c:if>
                                            </c:forEach>

                                            <c:if test="${!empty contribute.date}">
                                                <li>
                                                    <div class="nome">Data:</div>
                                                    <div class="valor">${contribute.date}</div>
                                                </li>
                                            </c:if>

                                        </ul></li>
                                    </c:forEach>

                            </ul> <!--/LIFE CYCLE--></li>
                        <!--/LIFE CYCLE-->
                    </c:if>
                    <!--/LIFE CYCLE-->

                    <c:if test="${!empty metadata.technical}">
                        <li><span class="title">Informa&ccedil;&otilde;es
                                t&eacute;cnicas</span>
                            <ul>
                                <c:forEach var="format" items="${metadata.technical.format}">
                                    <c:if test="${!empty format}">
                                        <li>
                                            <div class="nome">Formato:</div>
                                            <div class="valor">${format}</div>
                                        </li>
                                    </c:if>
                                </c:forEach>
                                <c:if test="${!empty metadata.technical.size}">
                                    <li>
                                        <div class="nome">Tamanho:</div>
                                        <div class="valor">${metadata.technical.size}</div>
                                    </li>
                                </c:if>

                                <c:forEach var="location"
                                           items="${metadata.technical.locationHttp}">
                                    <li>
                                        <div class="nome">Localiza&ccedil;&atilde;o:</div>
                                        <div class="valor">
                                            <c:choose>
                                                <c:when test="${location.value}">
                                                    <a class="verifyUrl" href="${location.key}" target="_blank">${location.key}</a>
                                                </c:when>
                                                <c:otherwise>${location.key}</c:otherwise>
                                            </c:choose>
                                        </div>
                                    </li>
                                </c:forEach>

                                <c:forEach var="requirement"
                                           items="${metadata.technical.requirement}">
                                    <c:if test="${!empty requirement}">
                                        <li><span class="title">Requisitos</span>
                                            <ul>
                                                <c:forEach var="orComposite"
                                                           items="${requirement.orComposite}" varStatus="i">
                                                    <c:if test="${!empty orComposite}">
                                                        <li><span class="title">op&ccedil;&atilde;o
                                                                ${i.index+1}</span>
                                                            <ul>
                                                                <c:if test="${!empty orComposite.type}">
                                                                    <li>
                                                                        <div class="nome">Tipo:</div>
                                                                        <div class="valor">${orComposite.type}</div>
                                                                    </li>
                                                                </c:if>
                                                                <c:if test="${!empty orComposite.name}">
                                                                    <li>
                                                                        <div class="nome">Nome:</div>
                                                                        <div class="valor">${orComposite.name}</div>
                                                                    </li>
                                                                </c:if>
                                                                <c:if test="${!empty orComposite.minimumVersion}">
                                                                    <li>
                                                                        <div class="nome">Vers&atilde;o M&iacute;nima:</div>
                                                                        <div class="valor">${orComposite.minimumVersion}</div>
                                                                    </li>
                                                                </c:if>
                                                                <c:if test="${!empty orComposite.maximumVersion}">
                                                                    <li>
                                                                        <div class="nome">Vers&atilde;o M&aacute;xima:</div>
                                                                        <div class="valor">${orComposite.maximumVersion}</div>
                                                                    </li>
                                                                </c:if>
                                                            </ul></li>
                                                        </c:if>
                                                    </c:forEach>
                                            </ul></li>
                                        </c:if>
                                    </c:forEach>

                                <c:if test="${!empty metadata.technical.installationRemarks}">
                                    <li>
                                        <div class="nome">Notas da instala&ccedil;&atilde;o:</div>
                                        <div class="valor">${metadata.technical.installationRemarks}</div>
                                    </li>
                                </c:if>
                                <c:if
                                    test="${!empty metadata.technical.otherPlatformRequirements}">
                                    <li>
                                        <div class="nome">Outros requisitos da plataforma:</div>
                                        <div class="valor">${metadata.technical.otherPlatformRequirements}</div>
                                    </li>
                                </c:if>
                                <c:if test="${!empty metadata.technical.duration}">
                                    <li>
                                        <div class="nome">Dura&ccedil;&atilde;o:</div>
                                        <div class="valor">${metadata.technical.duration}</div>
                                    </li>
                                </c:if>

                                <c:forEach var="var"
                                           items="${metadata.technical.supportedPlatforms}">
                                    <li>
                                        <div class="nome">Plataforma suportada:</div>
                                        <div class="valor">${var}</div>
                                    </li>
                                </c:forEach>

                                <c:forEach var="platformSpecificFeature"
                                           items="${metadata.technical.platformSpecificFeatures}">
                                    <li><span class="title">Caracter&iacute;stica
                                            espec&iacute;fica da plataforma</span>
                                        <ul>
                                            <c:if test="${!empty platformSpecificFeature.platformType}">
                                                <li>
                                                    <div class="nome">Tipo de plataforma:</div>
                                                    <div class="valor">${platformSpecificFeature.platformType}</div>
                                                </li>
                                            </c:if>
                                            <c:forEach var="format"
                                                       items="${platformSpecificFeature.specificFormats}">
                                                <li>
                                                    <div class="nome">Formato espec&iacute;fico:</div>
                                                    <div class="valor">${format}</div>
                                                </li>
                                            </c:forEach>
                                            <c:if test="${!empty platformSpecificFeature.specificSize}">
                                                <li>
                                                    <div class="nome">Tamanho espec&iacute;fico:</div>
                                                    <div class="valor">${platformSpecificFeature.specificSize}</div>
                                                </li>
                                            </c:if>
                                            <c:if
                                                test="${!empty platformSpecificFeature.specificLocation}">
                                                <li>
                                                    <div class="nome">Localiza&ccedil;&atilde;o
                                                        espec&iacute;fica:</div>
                                                    <div class="valor">${platformSpecificFeature.specificLocation}</div>
                                                </li>
                                            </c:if>

                                            <c:forEach var="specificRequirements"
                                                       items="${platformSpecificFeature.specificRequirements}">
                                                <li><span class="title">Requisitos
                                                        espec&iacute;ficos</span>
                                                    <ul>
                                                        <c:forEach var="specificOrComposite"
                                                                   items="${specificRequirements.specificOrComposites}">
                                                            <li><span class="title">Specific or Composite</span>
                                                                <ul>
                                                                    <c:if test="${!empty specificOrComposite.specificType}">
                                                                        <li>
                                                                            <div class="nome">Tipo:</div>
                                                                            <div class="valor">${specificOrComposite.specificType}</div>
                                                                        </li>
                                                                    </c:if>
                                                                    <c:if test="${!empty specificOrComposite.specificName}">
                                                                        <li>
                                                                            <div class="nome">Nome:</div>
                                                                            <div class="valor">${specificOrComposite.specificName}</div>
                                                                        </li>
                                                                    </c:if>
                                                                    <c:if
                                                                        test="${!empty specificOrComposite.specificMinimumVersion}">
                                                                        <li>
                                                                            <div class="nome">Vers&atilde;o M&iacute;nima:</div>
                                                                            <div class="valor">${specificOrComposite.specificMinimumVersion}</div>
                                                                        </li>
                                                                    </c:if>
                                                                    <c:if
                                                                        test="${!empty specificOrComposite.specificMaximumVersion}">
                                                                        <li>
                                                                            <div class="nome">Vers&atilde;o M&aacute;xima:</div>
                                                                            <div class="valor">${specificOrComposite.specificMaximumVersion}</div>
                                                                        </li>
                                                                    </c:if>
                                                                </ul></li>
                                                            </c:forEach>
                                                    </ul></li>
                                                </c:forEach>

                                            <c:if
                                                test="${!empty platformSpecificFeature.specificInstallationRemarks}">
                                                <li>
                                                    <div class="nome">Observa&ccedil;&otilde;es
                                                        espec&iacute;ficas de instala&ccedil;&atilde;o:</div>
                                                    <div class="valor">${platformSpecificFeature.specificInstallationRemarks}</div>
                                                </li>
                                            </c:if>
                                            <c:if
                                                test="${!empty platformSpecificFeature.specificOtherPlatformRequirements}">
                                                <li>
                                                    <div class="nome">Outros requisitos espec&iacute;ficos
                                                        de plataforma:</div>
                                                    <div class="valor">${platformSpecificFeature.specificOtherPlatformRequirements}</div>
                                                </li>
                                            </c:if>
                                        </ul></li>
                                    </c:forEach>

                                <c:forEach var="service" items="${metadata.technical.service}">
                                    <li><span class="title">Servi&ccedil;o</span>
                                        <ul>
                                            <c:if test="${!empty service.name}">
                                                <li>
                                                    <div class="nome">Nome:</div>
                                                    <div class="valor">${service.name}</div>
                                                </li>
                                            </c:if>
                                            <c:if test="${!empty service.type}">
                                                <li>
                                                    <div class="nome">Tipo:</div>
                                                    <div class="valor">${service.type}</div>
                                                </li>
                                            </c:if>
                                            <c:if test="${!empty service.provides}">
                                                <li>
                                                    <div class="nome">Fornece:</div>
                                                    <div class="valor">${service.provides}</div>
                                                </li>
                                            </c:if>
                                            <c:if test="${!empty service.essential}">
                                                <li>
                                                    <div class="nome">Essencial:</div>
                                                    <div class="valor">${service.essential}</div>
                                                </li>
                                            </c:if>

                                            <c:forEach var="protocol" items="${service.protocol}">
                                                <li>
                                                    <div class="nome">Protocolo:</div>
                                                    <div class="valor">${protocol}</div>
                                                </li>
                                            </c:forEach>

                                            <c:forEach var="language" items="${service.language}">
                                                <li>
                                                    <div class="nome">Linguagem:</div>
                                                    <div class="valor">${language}</div>
                                                </li>
                                            </c:forEach>

                                            <c:forEach var="details" items="${service.details}">
                                                <li><span class="title">Detalhes</span>
                                                    <ul>
                                                        <c:if test="${!empty details.details}">
                                                            <li>
                                                                <div class="nome">Descri&ccedil;&atilde;o:</div>
                                                                <div class="valor">${details.details}</div>
                                                            </li>
                                                        </c:if>
                                                        <c:if test="${!empty details.serviceLocation}">
                                                            <li>
                                                                <div class="nome">Localiza&ccedil;&atilde;o:</div>
                                                                <div class="valor">${details.serviceLocation}</div>
                                                            </li>
                                                        </c:if>
                                                    </ul></li>
                                                </c:forEach>


                                            <c:forEach var="ontology" items="${service.ontology}">
                                                <li><span class="title">Ontologia</span>
                                                    <ul>
                                                        <c:if test="${!empty ontology.name}">
                                                            <li>
                                                                <div class="nome">Nome:</div>
                                                                <div class="valor">${ontology.name}</div>
                                                            </li>
                                                        </c:if>
                                                        <c:if test="${!empty ontology.language}">
                                                            <li>
                                                                <div class="nome">Linguagem:</div>
                                                                <div class="valor">${ontology.language}</div>
                                                            </li>
                                                        </c:if>
                                                        <c:if test="${!empty ontology.location}">
                                                            <li>
                                                                <div class="nome">Localiza&ccedil;&atilde;o:</div>
                                                                <div class="valor">${ontology.location}</div>
                                                            </li>
                                                        </c:if>
                                                    </ul></li>
                                                </c:forEach>
                                        </ul></li>
                                    </c:forEach>


                            </ul> <!--/technical--></li>
                        <!--/technical-->
                    </c:if>
                    <!--/technical-->

                    <c:if test="${!empty metadata.educational}">
                        <li><span class="title">Educacional</span>
                            <ul>
                                <c:if test="${!empty metadata.educational.interactivityType}">
                                    <li>
                                        <div class="nome">Tipo de interação:</div>
                                        <div class="valor">${metadata.educational.interactivityType}</div>
                                    </li>
                                </c:if>

                                <c:forEach var="learningResourceType"
                                           items="${metadata.educational.learningResourceTypes}">
                                    <li>
                                        <div class="nome">Tipo de recurso:</div>
                                        <div class="valor">${learningResourceType}</div>
                                    </li>
                                </c:forEach>

                                <c:if test="${!empty metadata.educational.interactivityLevel}">
                                    <li>
                                        <div class="nome">Nível de interatividade:</div>
                                        <div class="valor">${metadata.educational.interactivityLevel}</div>
                                    </li>
                                </c:if>

                                <c:if test="${!empty metadata.educational.semanticDensity}">
                                    <li>
                                        <div class="nome">Densidade Semântica:</div>
                                        <div class="valor">${metadata.educational.semanticDensity}</div>
                                    </li>
                                </c:if>

                                <c:forEach var="intendedEndUserRole"
                                           items="${metadata.educational.intendedEndUserRoles}">
                                    <li>
                                        <div class="nome">Desenvolvido para:</div>
                                        <div class="valor">${intendedEndUserRole}</div>
                                    </li>
                                </c:forEach>

                                <c:forEach var="context" items="${metadata.educational.contexts}">
                                    <li>
                                        <div class="nome">Contexto:</div>
                                        <div class="valor">${context}</div>
                                    </li>
                                </c:forEach>

                                <c:forEach var="typicalAgeRange"
                                           items="${metadata.educational.typicalAgeRanges}">
                                    <li>
                                        <div class="nome">Faixa etária:</div>
                                        <div class="valor">${typicalAgeRange}</div>
                                    </li>
                                </c:forEach>

                                <c:if test="${!empty metadata.educational.difficulty}">
                                    <li>
                                        <div class="nome">Dificuldade:</div>
                                        <div class="valor">${metadata.educational.difficulty}</div>
                                    </li>
                                </c:if>

                                <c:if test="${!empty metadata.educational.typicalLearningTime}">
                                    <li>
                                        <div class="nome">Tempo típico de aprendizagem:</div>
                                        <div class="valor">${metadata.educational.typicalLearningTime}</div>
                                    </li>
                                </c:if>

                                <c:forEach var="description"
                                           items="${metadata.educational.descriptions}">
                                    <li>
                                        <div class="nome">Descrição:</div>
                                        <div class="valor">${description}</div>
                                    </li>
                                </c:forEach>

                                <c:forEach var="language"
                                           items="${metadata.educational.languages}">
                                    <li>
                                        <div class="nome">Idioma:</div>
                                        <div class="valor">${language}</div>
                                    </li>
                                </c:forEach>

                                <c:if test="${!empty metadata.educational.learningContentType}">
                                    <li>
                                        <div class="nome">Tipo do conte&uacute;do:</div>
                                        <div class="valor">${metadata.educational.learningContentType}</div>
                                    </li>
                                </c:if>
                                <c:if test="${!empty metadata.educational.interaction}">
                                    <li><span class="title">Intera&ccedil;&atilde;o</span>
                                        <ul>
                                            <c:if
                                                test="${!empty metadata.educational.interaction.interactionType}">
                                                <li>
                                                    <div class="nome">Tipo de intera&ccedil;&atilde;o:</div>
                                                    <div class="valor">${metadata.educational.interaction.interactionType}</div>
                                                </li>
                                            </c:if>
                                            <c:if
                                                test="${!empty metadata.educational.interaction.perception}">
                                                <li>
                                                    <div class="nome">Percep&ccedil;&atilde;o:</div>
                                                    <div class="valor">${metadata.educational.interaction.perception}</div>
                                                </li>
                                            </c:if>
                                            <c:if
                                                test="${!empty metadata.educational.interaction.synchronism}">
                                                <li>
                                                    <div class="nome">Sincronismo:</div>
                                                    <div class="valor">${metadata.educational.interaction.synchronism}</div>
                                                </li>
                                            </c:if>
                                            <c:if
                                                test="${!empty metadata.educational.interaction.coPresence}">
                                                <li>
                                                    <div class="nome">Co-presen&ccedil;a:</div>
                                                    <div class="valor">${metadata.educational.interaction.coPresence}</div>
                                                </li>
                                            </c:if>
                                            <c:if
                                                test="${!empty metadata.educational.interaction.reciprocity}">
                                                <li>
                                                    <div class="nome">Reciprocidade:</div>
                                                    <div class="valor">${metadata.educational.interaction.reciprocity}</div>
                                                </li>
                                            </c:if>
                                        </ul></li>
                                    </c:if>

                                <c:forEach var="didaticStrategy"
                                           items="${metadata.educational.didaticStrategy}">
                                    <li>
                                        <div class="nome">Estratégias Didáticas:</div>
                                        <div class="valor">${didaticStrategy}</div>
                                    </li>
                                </c:forEach>

                            </ul> <!--/educational--></li>
                        <!--/educational-->
                    </c:if>
                    <!--/educational-->

                    <c:if test="${!empty metadata.rights}">
                        <li><span class="title">Direitos</span>
                            <ul>
                                <c:if test="${!empty metadata.rights.cost}">
                                    <li>
                                        <div class="nome">Custo:</div>
                                        <div class="valor">${metadata.rights.cost}</div>
                                    </li>
                                </c:if>
                                <c:if test="${!empty metadata.rights.copyright}">
                                    <li>
                                        <div class="nome">Direito autoral:</div>
                                        <div class="valor">${metadata.rights.copyright}</div>
                                    </li>
                                </c:if>
                                <c:if test="${!empty metadata.rights.description}">
                                    <li>
                                        <div class="nome">Descrição:</div>
                                        <div class="valor">${metadata.rights.description}</div>
                                    </li>
                                </c:if>
                            </ul> <!--/rights--></li>
                        <!--/rights-->
                    </c:if>
                    <!--/rights-->
                    <c:forEach var="relations" items="${metadata.relations}">
                        <li><span class="title">Rela&ccedil;&atilde;o</span>
                            <ul>
                                <c:if test="${!empty relations.kind}">
                                    <li>
                                        <div class="nome">Tipo:</div>
                                        <div class="valor">${relations.kind}</div>
                                    </li>
                                </c:if>
                                <c:if
                                    test="${!empty relations.resource.description or !empty relations.resource.identifier}">
                                    <li><span class="title">Recurso</span>
                                        <ul>
                                            <c:forEach var="var" items="${relations.resource.description}">
                                                <li>
                                                    <div class="nome">Descri&ccedil;&atilde;o:</div>
                                                    <div class="valor">${var}</div>
                                                </li>
                                            </c:forEach>

                                            <c:forEach var="identifier"
                                                       items="${relations.resource.identifier}">
                                                <li><span class="title">Identificador</span>
                                                    <ul>
                                                        <c:if test="${!empty identifier.catalog}">
                                                            <li>
                                                                <div class="nome">Cat&aacute;logo:</div>
                                                                <div class="valor">${identifier.catalog}</div>
                                                            </li>
                                                        </c:if>
                                                        <c:if test="${!empty identifier.entry}">
                                                            <li>
                                                                <div class="nome">Identificador:</div>
                                                                <div class="valor">${identifier.entry}</div>
                                                            </li>
                                                        </c:if>
                                                    </ul></li>
                                                </c:forEach>
                                        </ul></li>
                                    </c:if>
                            </ul> <!--/relations--></li>
                        <!--/relations-->
                    </c:forEach>
                    <!--/relations-->

                    <c:forEach var="annotation" items="${metadata.annotations}">
                        <li><span class="title">Anota&ccedil;&otilde;es</span>
                            <ul>
                                <c:if test="${!empty annotation.entity}">
                                    <li>
                                        <div class="nome">Entidade:</div>
                                        <div class="valor">${annotation.entity}</div>
                                    </li>
                                </c:if>
                                <c:if test="${!empty annotation.date}">
                                    <li>
                                        <div class="nome">Data:</div>
                                        <div class="valor">${annotation.date}</div>
                                    </li>
                                </c:if>
                                <c:if test="${!empty annotation.description}">
                                    <li>
                                        <div class="nome">Descri&ccedil;&atilde;o:</div>
                                        <div class="valor">${annotation.description}</div>
                                    </li>
                                </c:if>
                            </ul> <!--/annotations--></li>
                        <!--/annotations-->
                    </c:forEach>
                    <!--/annotations-->

                    <c:forEach var="classification" items="${metadata.classifications}">
                        <li><span class="title">Classifica&ccedil;&atilde;o</span>
                            <ul>
                                <c:if test="${!empty classification.purpose}">
                                    <li>
                                        <div class="nome">Prop&oacute;sito:</div>
                                        <div class="valor">${classification.purpose}</div>
                                    </li>
                                </c:if>
                                <c:forEach var="var" items="${classification.taxonPath}">
                                    <c:if test="${!empty var.source}">
                                        <li>
                                            <div class="nome">Fonte:</div>
                                            <div class="valor">${var.source}</div>
                                        </li>
                                    </c:if>
                                    <c:forEach var="taxons" items="${var.taxons}">
                                        <li><span class="title">Taxonomia</span>
                                            <ul>
                                                <c:if test="${!empty taxons.id}">
                                                    <li>
                                                        <div class="nome">Id:</div>
                                                        <div class="valor">${taxons.id}</div>
                                                    </li>
                                                </c:if>
                                                <c:if test="${!empty taxons.entry}">
                                                    <li>
                                                        <div class="nome">Label:</div>
                                                        <div class="valor">${taxons.entry}</div>
                                                    </li>
                                                </c:if>
                                            </ul></li>
                                        </c:forEach>

                                </c:forEach>
                                <c:if test="${!empty classification.description}">
                                    <li>
                                        <div class="nome">Descri&ccedil;&atilde;o:</div>
                                        <div class="valor">${classification.description}</div>
                                    </li>
                                </c:if>
                                <c:forEach var="var" items="${classification.keywords}">
                                    <li>
                                        <div class="nome">Palavra-chave:</div>
                                        <div class="valor">${var}</div>
                                    </li>
                                </c:forEach>
                            </ul> <!--/Classification--></li>
                        <!--/Classification-->
                    </c:forEach>
                    <!--/Classification-->

                    <c:if test="${!empty metadata.accessibility.resourceDescription}">
                        <li><span class="title">Acessibilidade</span>
                            <ul>
                                <li><span class="title">Descrição dos recursos</span>
                                    <ul>
                                        <c:if
                                            test="${!empty metadata.accessibility.resourceDescription.primary}">
                                            <li><span class="title">Principal</span>
                                                <ul>
                                                    <c:if
                                                        test="${!empty metadata.accessibility.resourceDescription.primary.hasVisual}">
                                                        <li>
                                                            <div class="nome">&Eacute; visual:</div>
                                                            <div class="valor">${metadata.accessibility.resourceDescription.primary.hasVisual}</div>
                                                        </li>
                                                    </c:if>
                                                    <c:if
                                                        test="${!empty metadata.accessibility.resourceDescription.primary.hasAuditory}">
                                                        <li>
                                                            <div class="nome">&Eacute; auditivo:</div>
                                                            <div class="valor">${metadata.accessibility.resourceDescription.primary.hasAuditory}</div>
                                                        </li>
                                                    </c:if>
                                                    <c:if
                                                        test="${!empty metadata.accessibility.resourceDescription.primary.hasText}">
                                                        <li>
                                                            <div class="nome">&Eacute; textual:</div>
                                                            <div class="valor">${metadata.accessibility.resourceDescription.primary.hasText}</div>
                                                        </li>
                                                    </c:if>
                                                    <c:if
                                                        test="${!empty metadata.accessibility.resourceDescription.primary.hasTactile}">
                                                        <li>
                                                            <div class="nome">&Eacute; t&aacute;til:</div>
                                                            <div class="valor">${metadata.accessibility.resourceDescription.primary.hasTactile}</div>
                                                        </li>
                                                    </c:if>
                                                    <c:forEach var="earlStatement"
                                                               items="${metadata.accessibility.resourceDescription.primary.earlStatement}">
                                                        <li><span class="title">Declara&ccedil;&atilde;o
                                                                em EARL</span>
                                                            <ul>
                                                                <c:if
                                                                    test="${!empty earlStatement.displayTransformability}">
                                                                    <li>
                                                                        <div class="nome">Transformabilidade:</div>
                                                                        <div class="valor">${earlStatement.displayTransformability}</div>
                                                                    </li>
                                                                </c:if>
                                                                <c:if test="${!empty earlStatement.controlFlexibility}">
                                                                    <li>
                                                                        <div class="nome">Controle e flexibilidade:</div>
                                                                        <div class="valor">${earlStatement.controlFlexibility}</div>
                                                                    </li>
                                                                </c:if>
                                                            </ul></li>
                                                        </c:forEach>

                                                    <c:forEach var="equivalentResource"
                                                               items="${metadata.accessibility.resourceDescription.primary.equivalentResource}">
                                                        <li>
                                                            <div class="nome">Recurso equivalente:</div>
                                                            <div class="valor">${equivalentResource}</div>
                                                        </li>
                                                    </c:forEach>
                                                </ul></li>

                                        </c:if>

                                        <c:forEach var="equivalent"
                                                   items="${metadata.accessibility.resourceDescription.equivalent}">
                                            <li><span class="title">Equivalente</span>
                                                <ul>
                                                    <c:if test="${!empty equivalent.primaryResource}">
                                                        <li>
                                                            <div class="nome">Recurso principal:</div>
                                                            <div class="valor">${equivalent.primaryResource}</div>
                                                        </li>
                                                    </c:if>
                                                    <c:forEach var="primaryFile"
                                                               items="${equivalent.primaryFile}">
                                                        <li>
                                                            <div class="nome">Arquivo principal:</div>
                                                            <div class="valor">${primaryFile}</div>
                                                        </li>
                                                    </c:forEach>
                                                    <c:if test="${!empty equivalent.supplementaryTxt}">
                                                        <li>
                                                            <div class="nome">Suplementar:</div>
                                                            <div class="valor">${equivalent.supplementaryTxt}</div>
                                                        </li>
                                                    </c:if>
                                                    <c:if test="${!empty equivalent.content}">
                                                        <li><span class="title">Conteúdo</span>
                                                            <ul>
                                                                <c:if
                                                                    test="${!empty equivalent.content.alternativesToVisual}">
                                                                    <li><span class="title">Alternativa para o
                                                                            visual</span>
                                                                        <ul>
                                                                            <c:forEach var="audioDescription"
                                                                                       items="${equivalent.content.alternativesToVisual.audioDescription}">
                                                                                <li>
                                                                                    <div class="nome">Descri&ccedil;&atilde;o do
                                                                                        audio:</div>
                                                                                    <div class="valor">${audioDescription}</div>
                                                                                </li>
                                                                            </c:forEach>
                                                                            <c:if
                                                                                test="${!empty equivalent.content.alternativesToVisual.altTextLang}">
                                                                                <li>
                                                                                    <div class="nome">Idioma alternativo do texto:</div>
                                                                                    <div class="valor">${equivalent.content.alternativesToVisual.altTextLang}</div>
                                                                                </li>
                                                                            </c:if>
                                                                            <c:if
                                                                                test="${!empty equivalent.content.alternativesToVisual.longDescriptionLang}">
                                                                                <li>
                                                                                    <div class="nome">Idioma da
                                                                                        descri&ccedil;&atilde;o:</div>
                                                                                    <div class="valor">${equivalent.content.alternativesToVisual.longDescriptionLang}</div>
                                                                                </li>
                                                                            </c:if>
                                                                            <c:forEach var="colorAvoidance"
                                                                                       items="${equivalent.content.alternativesToVisual.colorAvoidance}">
                                                                                <li>
                                                                                    <div class="nome">Descri&ccedil;&atilde;o de
                                                                                        cores:</div>
                                                                                    <div class="valor">${colorAvoidance}</div>
                                                                                </li>
                                                                            </c:forEach>
                                                                        </ul></li>
                                                                    </c:if>

                                                                <c:if
                                                                    test="${!empty equivalent.content.alternativesToText}">
                                                                    <li><span class="title">Alternativa ao texto</span>
                                                                        <ul>
                                                                            <c:if
                                                                                test="${!empty equivalent.content.alternativesToText.graphicAlternative}">
                                                                                <li>
                                                                                    <div class="nome">Tem alternativa
                                                                                        gr&aacute;fica:</div>
                                                                                    <div class="valor">${equivalent.content.alternativesToText.graphicAlternative}</div>
                                                                                </li>
                                                                            </c:if>
                                                                            <c:forEach var="signLanguage"
                                                                                       items="${equivalent.content.alternativesToText.signLanguage}">
                                                                                <li>
                                                                                    <div class="nome">Linguagem de sinal:</div>
                                                                                    <div class="valor">${signLanguage}</div>
                                                                                </li>
                                                                            </c:forEach>
                                                                        </ul></li>
                                                                    </c:if>

                                                                <c:if
                                                                    test="${!empty equivalent.content.alternativesToAuditory}">
                                                                    <li><span class="title">Alternativa &agrave;
                                                                            audi&ccedil;&atilde;o</span>
                                                                        <ul>
                                                                            <c:forEach var="captionType"
                                                                                       items="${equivalent.content.alternativesToAuditory.captionType}">
                                                                                <li><span class="title">Legenda</span>
                                                                                    <ul>
                                                                                        <c:if test="${!empty captionType.language}">
                                                                                            <li>
                                                                                                <div class="nome">Idioma:</div>
                                                                                                <div class="valor">${captionType.language}</div>
                                                                                            </li>
                                                                                        </c:if>
                                                                                        <c:if test="${!empty captionType.verbatimTxt}">
                                                                                            <li>
                                                                                                <div class="nome">Texto original:</div>
                                                                                                <div class="valor">${captionType.verbatimTxt}</div>
                                                                                            </li>
                                                                                        </c:if>
                                                                                        <c:if
                                                                                            test="${!empty captionType.reducedReadingLevelTxt}">
                                                                                            <li>
                                                                                                <div class="nome">Texto reduzido:</div>
                                                                                                <div class="valor">${captionType.reducedReadingLevelTxt}</div>
                                                                                            </li>
                                                                                        </c:if>
                                                                                        <c:if test="${!empty captionType.reducedSpeed}">

                                                                                            <c:if
                                                                                                test="${!empty captionType.reducedSpeed.reducedSpeedTxt}">
                                                                                                <li>
                                                                                                    <div class="nome">Velocidade reduzida:</div>
                                                                                                    <div class="valor">${captionType.reducedSpeed.reducedSpeedTxt}</div>
                                                                                                </li>
                                                                                            </c:if>
                                                                                            <c:if
                                                                                                test="${!empty captionType.reducedSpeed.captionRate}">
                                                                                                <li>
                                                                                                    <div class="nome">Velocidade:</div>
                                                                                                    <div class="valor">${captionType.reducedSpeed.captionRate}</div>
                                                                                                </li>
                                                                                            </c:if>
                                                                                        </c:if>
                                                                                        <c:if
                                                                                            test="${!empty captionType.enhancedCaptionTxt}">
                                                                                            <li>
                                                                                                <div class="nome">Texto aumentado:</div>
                                                                                                <div class="valor">${captionType.enhancedCaptionTxt}</div>
                                                                                            </li>
                                                                                        </c:if>
                                                                                    </ul></li>
                                                                                </c:forEach>
                                                                                <c:forEach var="signLanguage"
                                                                                           items="${equivalent.content.alternativesToAuditory.signLanguage}">
                                                                                <li>
                                                                                    <div class="nome">Linguagem de sinal:</div>
                                                                                    <div class="valor">${signLanguage}</div>
                                                                                </li>
                                                                            </c:forEach>
                                                                        </ul></li>
                                                                    </c:if>

                                                                <c:forEach var="learnerScaffold"
                                                                           items="${equivalent.content.learnerScaffold}">
                                                                    <li>
                                                                        <div class="nome">Ferramenta learnerScaffold:</div>
                                                                        <div class="valor">${learnerScaffold}</div>
                                                                    </li>
                                                                </c:forEach>
                                                            </ul></li>
                                                        </c:if>


                                                </ul></li>
                                            </c:forEach>

                                    </ul></li>
                            </ul> <!--/Accessibility--></li>
                        <!--/Accessibility-->
                    </c:if>
                    <!--/Accessibility-->

                    <c:forEach var="segmentsInformationTable"
                               items="${metadata.segmentsInformationTable}">
                        <li><span class="title">Informa&ccedil;&otilde;es de
                                segmenta&ccedil;&atilde;o</span>
                            <ul>
                                <c:forEach var="segmentList"
                                           items="${segmentsInformationTable.segmentList}">
                                    <li><span class="title">Conjunto de
                                            informa&ccedil;&otilde;es de segmentos</span>
                                        <ul>
                                            <c:forEach var="segmentInformation"
                                                       items="${segmentList.segmentInformation}">
                                                <li><span class="title">Informa&ccedil;&otilde;es</span>
                                                    <ul>
                                                        <c:if test="${!empty segmentInformation.identifier}">
                                                            <li>
                                                                <div class="nome">Identificador:</div>
                                                                <div class="valor">${segmentInformation.identifier}</div>
                                                            </li>
                                                        </c:if>
                                                        <c:if test="${!empty segmentInformation.title}">
                                                            <li>
                                                                <div class="nome">T&iacute;tulo:</div>
                                                                <div class="valor">${segmentInformation.title}</div>
                                                            </li>
                                                        </c:if>
                                                        <c:if test="${!empty segmentInformation.description}">
                                                            <li>
                                                                <div class="nome">Descri&ccedil;&atilde;o:</div>
                                                                <div class="valor">${segmentInformation.description}</div>
                                                            </li>
                                                        </c:if>
                                                        <c:forEach var="keyword"
                                                                   items="${segmentInformation.keywords}">
                                                            <li>
                                                                <div class="nome">Palavra-chave:</div>
                                                                <div class="valor">${keyword}</div>
                                                            </li>
                                                        </c:forEach>
                                                        <c:if test="${!empty segmentInformation.segmentMediaType}">
                                                            <li>
                                                                <div class="nome">Tipo de m&iacute;dia:</div>
                                                                <div class="valor">${segmentInformation.segmentMediaType}</div>
                                                            </li>
                                                        </c:if>
                                                        <c:if test="${!empty segmentInformation.start}">
                                                            <li>
                                                                <div class="nome">In&iacute;cio:</div>
                                                                <div class="valor">${segmentInformation.start}</div>
                                                            </li>
                                                        </c:if>
                                                        <c:if test="${!empty segmentInformation.end}">
                                                            <li>
                                                                <div class="nome">Fim:</div>
                                                                <div class="valor">${segmentInformation.end}</div>
                                                            </li>
                                                        </c:if>
                                                    </ul></li>
                                                </c:forEach>
                                        </ul></li>
                                    </c:forEach>
                                    <c:if
                                        test="${!empty segmentsInformationTable.segmentGroupList.segmentGroupInformation}">
                                    <li><span class="title">Grupos de segmento</span>
                                        <ul>
                                            <c:forEach var="segmentGroupInformation"
                                                       items="${segmentsInformationTable.segmentGroupList.segmentGroupInformation}">
                                                <li><span class="title">Informa&ccedil;&otilde;es</span>
                                                    <ul>
                                                        <c:if test="${!empty segmentGroupInformation.identifier}">
                                                            <li>
                                                                <div class="nome">Identificador:</div>
                                                                <div class="valor">${segmentGroupInformation.identifier}</div>
                                                            </li>
                                                        </c:if>
                                                        <c:if test="${!empty segmentGroupInformation.groupType}">
                                                            <li>
                                                                <div class="nome">Tipo de agrupamento:</div>
                                                                <div class="valor">${segmentGroupInformation.groupType}</div>
                                                            </li>
                                                        </c:if>
                                                        <c:if test="${!empty segmentGroupInformation.title}">
                                                            <li>
                                                                <div class="nome">T&iacute;tulo do segmento:</div>
                                                                <div class="valor">${segmentGroupInformation.title}</div>
                                                            </li>
                                                        </c:if>
                                                        <c:if test="${!empty segmentGroupInformation.description}">
                                                            <li>
                                                                <div class="nome">Descri&ccedil;&atilde;o do
                                                                    conte&uacute;do do segmento:</div>
                                                                <div class="valor">${segmentGroupInformation.description}</div>
                                                            </li>
                                                        </c:if>
                                                        <c:forEach var="keyword"
                                                                   items="${segmentGroupInformation.keywords}">
                                                            <li>
                                                                <div class="nome">Palavra-chave:</div>
                                                                <div class="valor">${keyword}</div>
                                                            </li>
                                                        </c:forEach>
                                                        <c:if test="${!empty segmentGroupInformation.segments}">
                                                            <li><span class="title">Segmentos que fazem
                                                                    parte do grupo</span>
                                                                <ul>
                                                                    <c:forEach var="identifier"
                                                                               items="${segmentGroupInformation.segments.identifier}">
                                                                        <li>
                                                                            <div class="nome">C&oacute;digo identificador:</div>
                                                                            <div class="valor">${identifier}</div>
                                                                        </li>
                                                                    </c:forEach>
                                                                </ul></li>
                                                            </c:if>
                                                    </ul></li>
                                                </c:forEach>
                                        </ul></li>
                                    </c:if>
                            </ul> <!--/segmentsInformationTable--></li>
                        <!--/segmentsInformationTable-->
                    </c:forEach>
                    <!--/segmentsInformationTable-->

                </ul>
            </c:if>

            <input class="BOTAO" type="button" value="&lArr; Voltar"
                   onclick="javascript:history.back(-1);" />
        </div>

        <%@include file="googleAnalytics"%>
    </body>
</html>
