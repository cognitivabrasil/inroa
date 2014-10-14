<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:url var="images" value="/imagens" />
<c:url var="index" value="/" />
<c:url var="error_css" value="/css/error.css" />


<html lang="pt-BR">
    <head>

        <jsp:include page="../fragments/htmlHeader.jsp"/>
        <link rel="stylesheet" type="text/css" media="screen" href="${error_css}" />

    </head>

    <body>
        <jsp:include page="../fragments/cabecalho.jsp"/>
        <div class="container">
            <div class="well text-center txtError">
                <h1>Ooops, ocorreu um erro no sistema.</h1>
                Tente novamente. Se o problema persistir, contate o administrador do sistema.
            </div>
        </div>
        
        <!-- Para aparecer no codigo fonte o erro ocorrido-->
        <!-- ${stacktrace} -->

        <jsp:include page="../fragments/scripts.jsp"/>
        <!-- Barra do Governo Federal -->
        <script defer="defer" async="async" src="//barra.brasil.gov.br/barra.js" type="text/javascript"></script>
    </body>

</html>