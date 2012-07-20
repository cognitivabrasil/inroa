<%-- 
    Document   : index
    Created on : 07/05/2012, 17:03:11
    Author     : cei
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="feb.spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>  
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FEB - Estatísticas de Uso</title>
        <link rel="StyleSheet"
              href="<feb.spring:url value="/css/padrao.css" htmlEscape="true" />"
              type="text/css">
        <link rel="shortcut icon" type="image/x-icon"
              href="<feb.spring:url value="/imagens/favicon.ico" htmlEscape="true" />">
        <link rel="StyleSheet"
              href='<feb.spring:url value="/css/jquery.jqplot.min.css" htmlEscape="true" />'
              type="text/css">

        <script language="javascript" type="text/javascript" src='<feb.spring:url value="/scripts/jquery-1.7.2.js" htmlEscape="true" />'></script>
        <script language="javascript" type="text/javascript" src='<feb.spring:url value="/scripts/jquery.jqplot.min.js" htmlEscape="true" />'></script>
        <script type="text/javascript" src='<feb.spring:url value="/scripts/jqplot.pieRenderer.min.js" htmlEscape="true" />'></script>
        <script type="text/javascript" src='<feb.spring:url value="/scripts/zebraScript.js" htmlEscape="true" />'></script>

        <script>
            $(document).ready(function(){                
                
                
                if(${empty repositorios}) { 
                   
                    $('.repsGrande').hide();                                        
                    
                } else {
                    var plot1 = jQuery.jqplot ('chart1', [${repObjects}], 
                    { 
                        title: {
                            text: 'Quantidade de objetos: Reposit&oacute;rios',
                            show: true
                        },                
                        seriesDefaults: {
                            // Make this a pie chart.
                            renderer: jQuery.jqplot.PieRenderer, 
                            rendererOptions: {
                                // Put data labels on the pie slices.
                                // By default, labels show the percentage of the slice.
                                showDataLabels: true,
                                dataLabels: 'value'
                            }
                        }, 
                        legend: { show:true, location: 'e' }
                    }
                );
                }                
                     
                if(${empty federacoes}) {  
                    
                    $('.fedsGrande').hide();                    
                    
                } else {
                    var plot5 = jQuery.jqplot ('chart5', [${fedObjects}], 
                    { 
                    
                        title: {
                            text: 'Quantidade de objetos',
                            show: true
                        },                
                        seriesDefaults: {
                            // Make this a pie chart.
                            renderer: jQuery.jqplot.PieRenderer, 
                            rendererOptions: {
                                // Put data labels on the pie slices.
                                // By default, labels show the percentage of the slice.
                                showDataLabels: true,
                                dataLabels: 'value'
                            }
                        }, 
                        legend: { show:true, location: 'e' }
                    }
                );
                }
                
                var plot2 = $.jqplot ('chart2', [${visitasTotal}], {                    
                    title: 'N&uacute;mero de Visitantes',
                    axesDefaults: {
                        labelRenderer: $.jqplot.CanvasAxisLabelRenderer
                    },
                    axes: {                    
                        xaxis: {
                            label: "Mês",
                            pad: 0
                        },
                        yaxis: {
                            label: "Visitantes"
                        }
                    }
                });
                
                var plot3 = jQuery.jqplot ('chart3', [${repAcessos}], 
                { 
                    title: {
                        text: 'Quantidade de acessos: Reposit&oacute;rios',
                        show: true
                    },                
                    seriesDefaults: {
                        // Make this a pie chart.
                        renderer: jQuery.jqplot.PieRenderer, 
                        rendererOptions: {
                            // Put data labels on the pie slices.
                            // By default, labels show the percentage of the slice.
                            showDataLabels: true,
                            dataLabels: 'value'
                        }
                    }, 
                    legend: { show:true, location: 'e' }
                }
            );
                
                var plot4 = jQuery.jqplot ('chart4', [${fedAcessos}], 
                { 
                    title: {
                        text: 'Quantidade de acessos: Federa&ccedil;&otilde;es',
                        show: true
                    },                
                    seriesDefaults: {
                        // Make this a pie chart.
                        renderer: jQuery.jqplot.PieRenderer, 
                        rendererOptions: {
                            // Put data labels on the pie slices.
                            // By default, labels show the percentage of the slice.
                            showDataLabels: true,
                            dataLabels: 'value'
                        }
                    }, 
                    legend: { show:true, location: 'e' }
                }
            );                
            }); //$(document).ready(function()            
        </script>
    </head>


    <body>  

        <jsp:include page="../cabecalho.jsp">
            <jsp:param value="Ferramenta Administrativa" name="titulo" />
            <jsp:param value="7%" name="tamanho" />
        </jsp:include>

        <div id="estatisticas">
            <div class="acessos">
                <table class="repositorios-table zebraTable acessos">
                    <caption class="estatisticasTitulo">10 Objetos mais acessados</caption>            
                    <tr><th> T&iacute;tulo do Objeto</th><th>N&uacute;mero de acessos</th><th>Reposit&oacute;rio</th></tr>
                    <c:forEach var="objs" items="${docsMaisAcessados}" varStatus="status">
                        <tr><td>${objs.firstTitle}</td><td>${objs.acessos}</td><td>${objs.repositorio}</td></tr>                
                    </c:forEach>
                </table>
                <div id="chart2" widith="100%" style="height:350px;"></div>
            </div>

            <div class="repsGrande">
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




            <div class="fedsGrande">
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

    </body>

</html>