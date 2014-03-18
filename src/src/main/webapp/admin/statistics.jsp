<%-- 
    Document   : index
    Created on : 07/05/2012, 17:03:11
    Author     : cei
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
    <head>  
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FEB - Estatísticas de Uso</title>
        <c:url var="favicon" value="/imagens/favicon.ico" />
        <c:url var="css" value="/css/padrao.css" />
        <c:url var="jquery" value="/scripts/vendor/jquery-1.7.2.js" />
        <c:url var="jqplotcss" value="/css/jquery.jqplot.min.css" />
        <c:url var="jqplotjs" value="/scripts/vendor/jquery.jqplot.min.js" />
        <c:url var="pieRendererjs" value="/scripts/vendor/jqplot.pieRenderer.min.js" />
        <c:url var="zebraScript" value="/scripts/zebraScript.js" />
        <c:url var="statistics" value="/scripts/statistics.js" />
        <c:url var="uicss" value="/css/Theme/jquery-ui-1.8.22.custom.css" />
        <c:url var="jqueryUi" value="/scripts/vendor/jquery-ui-1.8.22.custom.min.js" />
        <c:url var="formDate" value="/scripts/formDate.js" />
        <c:url var="messagecss" value="/css/messages/messages.css" />
        <c:url var="jquery_mask" value="/scripts/vendor/jquery.maskedinput-1.3.min.js" />


        <link rel="shortcut icon" type="image/x-icon" href="${favicon}"  />

        <link rel="StyleSheet" media="screen" href="${css}" type="text/css"/>
        <link rel="StyleSheet" href="${jqplotcss}"  type="text/css">
        <link rel="StyleSheet" href="${uicss}" type="text/css" />
        <link rel="StyleSheet" href="${messagecss}" type="text/css" />

        <script language="javascript" type="text/javascript" src='${jquery}'></script>

        <script language="javascript" type="text/javascript" src="${jqplotjs}"></script>
        <script language="javascript" type="text/javascript" src="${pieRendererjs}"></script>
        <script language="javascript" type="text/javascript" src="${zebraScript}"></script>
        <script language="javascript" type="text/javascript" src="${statistics}"></script>
        <script type="text/javascript" src='${jqueryUi}'></script>
        <script language="javascript" type="text/javascript" src="${formDate}"></script>
        <script language="javascript" type="text/javascript"src="${jquery_mask}"></script>
        <c:url value="/admin/statistics" var="statisticsUrlRoot" />

        <script>
            $(function() {
                setStatisticsUrl('${statisticsUrlRoot}');
                
                if(${empty repositorios}) {                   
                    $('.repsGrande').hide();                    
                } else {
                    graficoPizza('chart1', [${repObjects}], "Quantidade de objetos: Reposit&oacute;rios");
                }                
                     
                if(${empty federacoes}) {                    
                    $('.fedsGrande').hide();                    
                } else {
                    graficoPizza('chart5', [${fedObjects}], "Quantidade de objetos");
                }
                
                graficoLinha('chart2', [${visitasTotal}], 'N&uacute;mero de Visitantes', 'Mês', 'Visitantes');
                graficoPizza('chart3', [${repAcessos}], "Quantidade de acessos: Reposit&oacute;rios");
                graficoPizza('chart4', [${fedAcessos}], "Quantidade de acessos: Federa&ccedil;&otilde;es");


                $('#loading').addClass("hidden");
                // code here
            }); 
        </script>
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
            </div>
            <security:authorize access="hasRole('PERM_MANAGE_STATISTICS')">
                <c:if test="${not empty termosTagCloud}">


                    <div class="caixaAzul">
                        <div id="msgTagCloud"></div>

                        <table class="repositorios-table zebraTable acessos">
                            <caption class="estatisticasTitulo">Termos da <i>tag cloud</i></caption>            
                            <tr><th>Termo</th><th>Quantidade de buscas</th><th>Deletar</th></tr>
                            <c:url var="deleteTag" value="/admin/statistics/deletetag"/>
                            <c:forEach var="termo" items="${termosTagCloud}">
                                <tr>

                                    <td>${termo.key}</td>
                                    <td>${termo.value}</td>
                                    <td>
                                        <a class="deleteTag btSemTexto" href="${deleteTag}/${termo.key}">deletar</a>
                                    </td>
                                </tr>                
                            </c:forEach>
                        </table>
                        <c:url var="deleteAllTags" value="/admin/statistics/deletealltags"/>
                        <a class="deleteTag" href="${deleteAllTags}">Apagar todos os termos</a>

                    </div>
                </c:if>
            </security:authorize>

            <div class="caixaAzul">
                <table class="repositorios-table zebraTable acessos">
                    <caption class="estatisticasTitulo">10 Objetos mais acessados</caption>            
                    <tr><th> T&iacute;tulo do Objeto</th><th>N&uacute;mero de acessos</th><th>Federa&ccedil;&atilde;o / Reposit&oacute;rio</th></tr>
                    <c:forEach var="objs" items="${docsMaisAcessados}" varStatus="status">
                        <tr><td>${objs.firstTitle}</td><td>${objs.acessos}</td><td>${objs.nomeRep}</td></tr>                
                    </c:forEach>
                </table>
                <div id="msgVisitas" class="error hidden"></div>
                <div id="dateRange">
                    <label>De: </label><input id="fromDate" value="${initialDate}" class="dataMask ui-widget-content ui-corner-all datepickerFrom" />
                    <label>Até: </label><input id="untilDate" value="${finalDate}" class="dataMask ui-widget-content ui-corner-all datepickerTo" />
                    <button id="updateGraph" class="btSemTexto">Atualizar</button>
                </div>
                <div id="chart2" widith="100%" style="height:350px;"></div>
            </div>

            <div class="repsGrande caixaAzul">
                <div class="estatisticasTitulo">Repositórios</div>
                <table class="repositorios-table zebraTable">
                    <tr><th> Repositório</th><th>N&uacute;mero de objetos</th></tr>            
                    <c:forEach var="reps" items="${repositorios}" varStatus="status">
                        <tr><td>${reps.name}</td><td class="col2">${reps.size}</td></tr>                
                    </c:forEach>
                </table>
                <div id="chart1" style="height:350px;"></div>
                <div id="chart3" style="height:350px;"></div>
            </div>


            <div class="fedsGrande caixaAzul">
                <div class="estatisticasTitulo">Federações</div>
                <table class="repositorios-table zebraTable">
                    <tr><th> Federa&ccedil;&atilde;o</th><th>N&uacute;mero de objetos</th></tr>            
                    <c:forEach var="feds" items="${federacoes}" varStatus="status">
                        <tr><td>${feds.name}</td><td class="col2">${feds.size}</td></tr>                
                    </c:forEach>
                </table>
                <div id="chart5" style="height:350px;"></div>
                <div id="chart4" style="height:350px;"></div>
            </div>

        </div>
        <div class="modal"></div>
    </body>
</html>