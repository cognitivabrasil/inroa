<%--
@author Marcos Nunes <marcosn@gmail.com>
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">


<html lang="pt-BR">
    <head>
        
        <jsp:include page="fragments/header.jsp"/>

        <c:url value="/css/showDocument.css" var="show_doc_css_url" />
        <link rel="stylesheet" type="text/css" media="screen" href="${show_doc_css_url}" /> 
        
        <c:url var="root" value="/" />
        <c:url var="linkJson" value="/objetos/${docId}/json" />
        <script>
            rootUrl = "${root}";
        </script>

    </head>
    <body>

        <jsp:include page="cabecalho.jsp"/>

        <div class="col-md-12">
            
            <div class="socialBookmarks">
                <div class="socialBookmark">
                    <div id="fb-root"></div>
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
            <div class="tituloPrincipal col-md-12">
                <div class="tituloObj row">${title}</div>
                <div class="identificadorObj row">Objeto ${obaaEntry}</div>

            </div>

            <div id="obaaTree" class="col-md-12" src="${linkJson}">
                <c:url var="imgLoader" value="/imagens/ajax-loader.gif"/>
                <div class="text-center"><img src='${imgLoader}' border='0' alt='Carregando' align='middle'> Aguarde, carregando...</div>
            </div>

            <div class="footerButton">
                <a class="btn btn-default" href="javascript:history.go(-1)">
                    <span class="glyphicon glyphicon-arrow-left"></span>
                    <span class='gliphycon-text'>Voltar</span>
                </a>
            </div>
        </div>
                
        <jsp:include page="fragments/scripts.jsp"/>
        
        
        <c:url var="showJs" value="/scripts/showMetadata.js" />
        <script type="text/javascript" src="${showJs}" ></script>

        <script type="text/javascript"
                src="https://apis.google.com/js/plusone.js">
            {
                lang: 'pt-BR';
            }
        </script>
        <script>
            (function (d, s, id) {
                var js, fjs = d.getElementsByTagName(s)[0];
                if (d.getElementById(id))
                    return;
                js = d.createElement(s);
                js.id = id;
                js.src = "//connect.facebook.net/pt_BR/all.js#xfbml=1";
                fjs.parentNode.insertBefore(js, fjs);
            }(document, 'script', 'facebook-jssdk'));
        </script>
        
        <%@include file="googleAnalytics"%>
    </body>
</html>
