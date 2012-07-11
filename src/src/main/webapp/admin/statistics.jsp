<%-- 
    Document   : index
    Created on : 07/05/2012, 17:03:11
    Author     : cei
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
    <head>     
        <link rel="StyleSheet"
              href='<spring:url value="/css/jquery.jqplot.min.css" htmlEscape="true" />'
              type="text/css">

        <script language="javascript" type="text/javascript" src='<spring:url value="/scripts/jquery-1.7.2.js" htmlEscape="true" />'></script>
        <script language="javascript" type="text/javascript" src='<spring:url value="/scripts/jquery.jqplot.min.js" htmlEscape="true" />'></script>
        <script type="text/javascript" src='<spring:url value="/scripts/jqplot.pieRenderer.min.js" htmlEscape="true" />'></script>        
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
                alert (${visitasTotal})
                var plot2 = $.jqplot ('chart2', [${visitasTotal}], {                    
                    title: 'Número de Visitas',
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
            10 Consultas mais realizadas
        </table>
            
        <table>
            10 Objetos mais acessados
        </table>
        <p>
            Relação consultas/visita: 1
        </p>
        
    </body>

</html>