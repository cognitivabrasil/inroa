<%-- 
    Document   : statistics
    Created on : 25/07/2014, 14:11:46
    Author     : Marcos Freitas Nunes <marcos@cognitivabrasil.com.br>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FEB - Estatísticas</title>

        <c:url var="zebraScript" value="/scripts/zebraScript.js" />
        <c:url var="statistics" value="/scripts/statistics.js" />
        <c:url var="messagecss" value="/css/messages/messages.css" />

        <link rel="StyleSheet" href="${messagecss}" type="text/css" />
        <script language="javascript" type="text/javascript" src="${zebraScript}"></script>
        <script language="javascript" type="text/javascript" src="${statistics}"></script>

    </head>
    <body>
        <div class="ui-widget hidden" id="dialog-error" title="Erro">
            <div class="ui-state-error ui-corner-all">
                <p>
                    <span class="ui-icon ui-icon-alert"><jsp:text/></span>
                    <strong>Erro: </strong> <span id="errorThrown"><jsp:text/></span>.
                </p>
            </div>
        </div>

        <div id="estatisticas">
            <div class="caixaAzul">
                <span class="left bold">Número total de objetos:</span> <fmt:formatNumber value="${totalObj}" />

                <c:if test="${!empty repositories}">
                    <table class="admin-table zebraTable">
                        <caption class="estatisticasTitulo">Repositórios</caption>
                        <tr>
                            <th> Repositório</th><th>Documentos</th>
                        </tr>
                        <c:forEach var="map" items="${repositories}">
                            <tr>
                                <td>${map.key}</td>
                                <td class="numObj"><fmt:formatNumber value="${map.value}" /></td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:if>

                <c:if test="${!empty federations}">
                    <table class="admin-table zebraTable">
                        <caption class="estatisticasTitulo">Federações</caption>
                        <tr><th> Federa&ccedil;&atilde;o</th><th>Documentos</th></tr>
                                <c:forEach var="map" items="${federations}">
                            <tr>
                                <td>${map.key}</td>
                                <td class="numObj"><fmt:formatNumber value="${map.value}" /></td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:if>
            </div>

            <security:authorize access="hasRole('ROLE_MANAGE_STATISTICS')">
                <c:if test="${not empty termosTagCloud}">
                    <div class="caixaAzul">
                        <div id="msgTagCloud"></div>
                        <table class="admin-table zebraTable acessos">
                            <caption class="estatisticasTitulo">Termos da <i>tag cloud</i></caption>
                            <tr><th>Termo</th><th>Quantidade de buscas</th><th>Deletar</th></tr>
                                    <c:url var="deleteTag" value="/admin/statistics/deletetag?tag="/>
                                    <c:forEach var="termo" items="${termosTagCloud}">
                                <tr>
                                    <td>${termo.key}</td>
                                    <td class="center">${termo.value}</td>
                                    <td class="center">
                                        <a class="deleteTag btSemTexto" href="${deleteTag}${termo.key}">deletar</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                        <c:url var="deleteAllTags" value="/admin/statistics/deletealltags"/>
                        <a class="deleteTag" href="${deleteAllTags}">Apagar todos os termos</a>
                    </div>
                </c:if>
            </security:authorize>


            <%--
            <div class="fedsGrande caixaAzul">
                <div class="estatisticasTitulo">Federações</div>
                <table class="admin-table zebraTable">
                    <tr><th> Federa&ccedil;&atilde;o</th><th>N&uacute;mero de objetos</th></tr>
                            <c:forEach var="feds" items="${federacoes}" varStatus="status">
                        <tr><td>${feds.name}</td><td class="col2">${feds.size}</td></tr>
                        </c:forEach>
                </table>
                
            </div> --%>
        </div>
        <div class="modal"></div>
    </body>
</html>
