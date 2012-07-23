<%--
    Document   : index
    Created on : 25/06/2009, 18:26:51
    Author     : Marcos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");

%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>GT-FEB – Federa&ccedil;&atilde;o de Reposit&oacute;rios Educa Brasil</title>
        <link rel="StyleSheet" href="css/padrao.css" type="text/css">
        <link href="imagens/favicon.ico" rel="shortcut icon" type="image/x-icon" />
        <script language="JavaScript" type="text/javascript" src="scripts/index.js"></script>

    </head>
    <body id="bodyMenor">

        <div id="page">

            <jsp:include page="cabecalho.jsp">
                <jsp:param value="Consulta de Objetos Educacionais / Busca Avan&ccedil;ada" name="titulo" />
            </jsp:include>

            <div class="linkCantoDireito"><a href="./admin/"><img src="imagens/ferramenta_32x32.png" alt="Ferramenta Administrativa"></a></div>
            <div class="Espaco">&nbsp;</div>


            <form:form method="get" modelAttribute="buscaModel" action="consultaAvancada" acceptCharset="utf-8">    
                <div class="EspacoAntes">&nbsp;</div>
                <c:if test="${!empty erro}">
                    <div class="DivErro" id="MensagemErro">${erro}</div>
                </c:if>

                <div class="ondeBuscar">

                    <c:forEach var="rep" items="${repDAO.all}" varStatus="i">

                        <c:if test="${i.index==0}"><div class='ValueIndex'>- Reposit&oacute;rios</div></c:if>

                        <div class='repositorios'>&nbsp;&nbsp;&nbsp;
                            <input value='${rep.id}' type=checkbox id="${rep.id}" name="repositorios"> ${fn:toUpperCase(rep.nome)}
                        </div>                           

                    </c:forEach>

                    <c:forEach var="subFed" items="${subDAO.all}" varStatus="i">

                        <c:if test="${i.index==0}"><div class='ValueIndex'>- Subfedera&ccedil;&otilde;es</div></c:if>
                        <div class='federacoes'>&nbsp;&nbsp;&nbsp;
                            <a id='link${subFed.id}' class='linkRepSubfeb' onclick='tornarVisivel("link${subFed.id}","listaRep${subFed.id}", "Interno");'>+</a>
                            <input value='${subFed.id}' type=checkbox id="${subFed.id}" name="federacoes"> ${fn:toUpperCase(subFed.nome)}
                            <div id='listaRep${subFed.id}' class='hidden'>

                                <c:forEach var="repSubFed" items="${subFed.repositorios}" varStatus="i">
                                    <div class='Int'>&nbsp;&nbsp;&nbsp;
                                        <input value='${repSubFed.id}' type=checkbox id="${repSubFed.id}" name="repSubfed">
                                        ${fn:toUpperCase(repSubFed.name)}
                                    </div>
                                </c:forEach>                        
                            </div>
                        </div>

                    </c:forEach>



                </div>


                <div class="clear"> </div>
                <div id="modificavel" class="margin-top">
                    <div class="LinhaEntrada">
                        <form:errors path="consulta" cssClass="ValueErro" />
                        <div class="Label">
                            Texto para a busca:
                        </div>
                        <div class="Value">
                            <input type="text" name="consulta" id="consulta" value="" onFocus="this.className='inputSelecionado'" onBlur="this.className=''"/>
                        </div>
                    </div>
                    <div class="LinhaEntrada">
                        <div class="Label">
                            Autor:
                        </div>
                        <div class="Value">
                            <input type="text" name="autor" id="autor" value="" onFocus="this.className='inputSelecionado'" onBlur="this.className=''"/>
                        </div>
                    </div>
                </div>
                <div class="clear"> </div>
                <div class="LinhaEntrada">
                    <div class="Buttons">
                        <input class="BOTAO" type="submit" value="Consultar" ALIGN="CENTER"/>
                    </div>
                </div>
            </form:form>
            <div ALIGN="CENTER">
                <a href="index">Retornar a busca padr&atilde;o</a>
            </div>

        </div>
        <div>
            <div class="copyRight">Desenvolvido em parceria com: UFRGS e RNP</div>
        </div>
        <%@include file="googleAnalytics"%>
    </body>
</html>