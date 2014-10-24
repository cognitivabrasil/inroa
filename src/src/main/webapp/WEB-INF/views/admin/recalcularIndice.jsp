<%-- 
    Document   : recalculaIndice
    Author     : Marcos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FEB - Ferramenta Administrativa</title>
        <c:url var="funcoesJs" value="/scripts/funcoes.js" />
        <link rel="StyleSheet" media="screen" href="../css/padrao.css" type="text/css"/>
        <script type="text/javascript" src="${funcoesJs}"></script>
        <c:url var="root" value="/" />
        <script>setRootUrl("${root}");</script>
        <link href="../imagens/favicon.ico" rel="shortcut icon" type="image/x-icon" />
    </head>
    <body>
        <div id="page">
            <div class="subTitulo-center">&nbsp;Rec&aacute;lculo do &iacute;ndice</div>
            <div class="EspacoAntes">&nbsp;</div>

            <c:choose>
                <c:when test="${empty fim}">
                    <div id="mensagem" class="hidden">
                        <c:url var="ajaxLoader" value="/imagens/ajax-loader.gif" />
                        <center><img src="${ajaxLoader}" border='0' alt='Atualizando' align='middle'> O &iacute;ndice est&aacute; sendo recalculado, por favor aguarde! </center>
                    </div>
                    <div id="form">
                        <form action="efetuaRecalculoIndice" method="post">
                            <div class="LinhaEntrada">
                                <div class="Tab">
                                    Deseja realmente efetuar o rec&aacute;lculo do &iacute;ndice?
                                    <div class="info">Atenção: Esta opera&ccedil;&atilde;o pode levar alguns minutos!</div>
                                </div>
                            </div>
                            <input type="hidden" name="submitted" value="true"/>
                            <div class="LinhaEntrada">
                                <div class="Buttons">
                                    <input type="submit" value="&nbsp;Sim&nbsp;" name="submit" onclick="recalcularIndice(document.getElementById('mensagem'), this.parentNode.parentNode.parentNode)" />
                                    <input id="cancelar" onclick="javascript:window.close();" value="&nbsp;N&atilde;o&nbsp;" type="button" class="CancelButton"/>
                                </div>
                            </div>
                        </form>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="mensagemSucesso">${fim}
                    </div>
                    <div class="EspacoAntes">&nbsp;</div>
                    <center>
                        <input type="button" onclick="javascript:window.close();" value="Fechar" class="BOTAO"/>
                    </center>
                </c:otherwise>
            </c:choose>


        </div>
    </body>
</html>