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
            var data = [
                
                [<% out.print("'UFRGS'");%>, 12],['MEC', 9], ['local', 14], 
                ['UFMA', 16],['Fiocruz', 7], ['UFES', 9], ['UFSCAR', 9], ['IFSul', 9]
            ];
            var plot1 = jQuery.jqplot ('chart1', [data], 
                { 
                    title: {
                            text: 'Quantidade de objetos: Federações',
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
    </body>
    
</html>