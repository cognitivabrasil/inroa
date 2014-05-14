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
<c:url var="logoReduzido" value="/imagens/Logo FEB_reduzido.png" />
<c:url var="index" value="/" />
<c:url var="scripts" value="/scripts" />
<c:url var="css" value="/css" />

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FEB – Federa&ccedil;&atilde;o de Reposit&oacute;rios Educa Brasil</title>
        <link rel="StyleSheet" href="${css}/padrao.css" type="text/css">
        <link rel="StyleSheet" href="${css}/buscaAvancada.css" type="text/css">
        <link rel="StyleSheet" href="${scripts}/vendor/jsTree/dist/themes/default/style.min.css" type="text/css">
        <link rel="StyleSheet" href="${scripts}/vendor/bootstrap-slider/css/slider.css" type="text/css">
        <c:url value="/scripts/vendor/bootstrap-3.1.1-dist/css/bootstrap-theme.min.css" var="bootstrap_theme_css" />
        <link rel="stylesheet" type="text/css" media="screen" href="${bootstrap_theme_css}" />
        <link href="imagens/favicon.ico" rel="shortcut icon" type="image/x-icon" />
        <script language="javascript" type="text/javascript" src='${scripts}/vendor/jquery-1.11.0.min.js'></script>
        <script language="JavaScript" type="text/javascript" src="${scripts}/vendor/jsTree/dist/jstree.min.js"></script>
        <c:url var="jsBootstrap" value="/scripts/vendor/bootstrap-3.1.1-dist/js/bootstrap.min.js" />
        <script language="javascript" type="text/javascript" src='${jsBootstrap}'></script>
        <script language="JavaScript" type="text/javascript" src="${scripts}/vendor/bootstrap-slider/js/bootstrap-slider.js"></script>
        <script language="JavaScript" type="text/javascript" src="${scripts}/buscaAvancada.js"></script>
    </head>

    <body>
        <jsp:include page="barraSuperior.jsp" />

        <div id="page-index">
            <c:if test="${!empty erro}">
                <div class="DivErro" id="MensagemErro">${erro}</div>
            </c:if>

            <form:form method="GET" modelAttribute="buscaModel" action="consultaAvancada" acceptCharset="utf-8">   
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
                            <div class="LabelLeft">
                                <b>Texto</b> para a busca
                            </div>
                            <div class="Value">
                                <form:input path="consulta"/>
                            </div>
                        </div>  

                        <div class="LinhaEntrada">
                            <div class="LabelLeft">
                                Pesquisar objetos <b>de autoria</b> de
                            </div>
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
                            
                        <div class="LinhaEntrada">
                            <div class="LabelLeft">
                                Faixa etária:
                            </div>
                            <div class="Value">
                                <form:input path="ageRange" class="span2" data-slider-min="0" data-slider-max="200" data-slider-step="1" data-slider-value="[0,100]"/>
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
                    </div>
                    <div class="EspacoAntes">&nbsp;</div>
                    <div>
                        <a href="${index}">Retornar a busca padr&atilde;o</a>
                    </div>
                </div>
            </form:form>
        </div>
        <%@include file="googleAnalytics"%>
    </body>
</html>
