<%--
@author Marcos Nunes <marcosn@gmail.com>
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>


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

        <jsp:include page="fragments/cabecalho.jsp"/>


        <div class="container">
            <div class="row">
                <div class="col-lg-12 text-right">
                    <span>

                        <div class="fb-share-button" data-type="button_count"></div>
                    </span>


                    <span>
                        <g:plusone size="medium"></g:plusone>
                    </span>

                    <span>
                        <a href="http://twitter.com/share" class="twitter-share-button"
                           data-count="horizontal">Tweet</a>
                        <script type="text/javascript"
                        src="http://platform.twitter.com/widgets.js"></script>
                    </span>
                </div>
            </div>

            <div class="row">
                <div class="col-lg-12">
                    <div class="tituloPrincipal well shadow">
                        <div class="tituloObj">${title}</div>
                        <div class="identificadorObj">Objeto ${obaaEntry}</div>
                    </div>
                </div>
            </div>

            <div class="row">
                <div id="obaaTree" class="col-lg-12" src="${linkJson}">
                    <c:url var="imgLoader" value="/imagens/ajax-loader.gif"/>
                    <div class="text-center"><img src='${imgLoader}' border='0' alt='Carregando' align='middle'> Aguarde, carregando...</div>
                </div>
            </div>

            <div class="row">
                <div class="col-lg-12">
                    <div class="footerButton">
                        <a class="btn btn-default shadow" href="javascript:history.go(-1)">
                            <span class="glyphicon glyphicon-arrow-left"></span>
                            <span class='gliphycon-text'>Voltar</span>
                        </a>
                    </div>
                </div>
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

        <div id="fb-root"></div>
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
