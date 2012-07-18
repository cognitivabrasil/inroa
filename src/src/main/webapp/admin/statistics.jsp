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
        <link rel="shortcut icon" type="image/x-icon"
              href="<feb.spring:url value="/imagens/favicon.ico" htmlEscape="true" />">
        <link rel="StyleSheet"
              href='<feb.spring:url value="/css/jquery.jqplot.min.css" htmlEscape="true" />'
              type="text/css">

        <script language="javascript" type="text/javascript" src='<feb.spring:url value="/scripts/jquery-1.7.2.js" htmlEscape="true" />'></script>
        <script language="javascript" type="text/javascript" src='<feb.spring:url value="/scripts/jquery.jqplot.min.js" htmlEscape="true" />'></script>
        <script type="text/javascript" src='<feb.spring:url value="/scripts/jqplot.pieRenderer.min.js" htmlEscape="true" />'></script>        
        <script class="code" type="text/javascript">

            $(document).ready(function(){

                var plot1 = jQuery.jqplot ('chart1', [${numObjects}], 
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
            });
        </script>      

        <script>      
            $(document).ready(function(){                
                var plot2 = $.jqplot ('chart2', [${visitasTotal}], {                    
                    title: 'Número de Visitantes',
                    axesDefaults: {
                        labelRenderer: $.jqplot.CanvasAxisLabelRenderer
                    },
                    axes: {                    
                        xaxis: {
                            label: "Mês",
                            pad: 0
                        },
                        yaxis: {
                            label: "Visitas"
                        }
                    }
                });
            });
        </script>   

        <script>
            $(document).ready(function(){
                var data = [
                
                    [<% out.print("'UFRGS'");%>, 18],['MEC', 12], ['local', 8], 
                    ['UFMA', 23],['Fiocruz', 3], ['UFES', 14], ['UFSCAR', 8], ['IFSul', 4]
                ];
                var plot3 = jQuery.jqplot ('chart3', [data], 
                { 
                    title: {
                        text: 'Quantidade de acessos: Federações',
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
            });   
            
        </script>

    </head>


    <body>   
        <div id="chart1" style="height:350px; width:500px;"></div>  

        <div id="chart2" style="height:350px; width:500px;"></div>

        <div id="chart3" style="height:350px; width:500px;"></div>


        <table>
            <caption>10 Objetos mais acessados</caption>            
            <tr><th> Titulo do Objeto</th><th>N&uacute;mero de acessos</th></tr>
            <c:forEach var="objs" items="${docsMaisAcessados}" varStatus="status">
                <tr><td>${objs.firstTitle}</td><td>${objs.acessos}</td></tr>                
            </c:forEach>
        </table>

    </body>

</html>