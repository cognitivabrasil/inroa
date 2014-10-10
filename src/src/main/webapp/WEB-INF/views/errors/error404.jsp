<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:url var="images" value="/imagens" />
<c:url var="index" value="/" />


<html lang="pt-BR">
    <head>

        <jsp:include page="../fragments/header.jsp"/>

    </head>

    <body>
        <jsp:include page="../cabecalho.jsp"/>
        <div class="container">
            <div class="well">
                <h1>Ooops, a página solicitada não foi encontrada.</h1>
                Clique <a href="${index}">aqui</a> e volte para a ferramenta de busca.
            </div>
        </div>


        <jsp:include page="../fragments/scripts.jsp"/>            
    </body>

</html>