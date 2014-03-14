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
<c:url var="images" value="/imagens" />
<c:url var="logoReduzido" value="/imagens/Logo FEB_reduzido.png" />
<c:url var="index" value="/" />
<c:url var="scripts" value="/scripts" />


<%
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");

%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FEB – Federa&ccedil;&atilde;o de Reposit&oacute;rios Educa Brasil</title>
        <link rel="StyleSheet" href="css/padrao.css" type="text/css">
        <link href="imagens/favicon.ico" rel="shortcut icon" type="image/x-icon" />
        <script language="javascript" type="text/javascript" src='${scripts}/vendor/jquery-1.7.2.js'></script>
        <script language="JavaScript" type="text/javascript" src="${scripts}/buscaAvancada.js"></script>
        
    </head>

    <body>
        <jsp:include page="barraSuperior.jsp" />

        <div id="page-index">

            <c:if test="${!empty erro}">
                <div class="DivErro" id="MensagemErro">${erro}</div>
            </c:if>

            <form:form method="get" modelAttribute="buscaModel" action="consultaAvancada" acceptCharset="utf-8">    
                <div id="index">
                    <a href="${index}">
                        <img src="${logoReduzido}" alt="Logo FEB_reduzido" class="logo"/>
                    </a>

                    <div class="clear"> </div>
                    <div class="EspacoAntes">&nbsp;</div>


                    <div id="buscaAvancada">

                        <div class="LinhaEntrada">
                            <form:errors path="consulta" cssClass="ValueErro" />
                            <div class="LabelLeft">
                                <b>Texto</b> para a busca
                            </div>
                            <div class="Value">
                                <input type="text" name="consulta" id="consulta" value="" onFocus="this.className='inputSelecionado'" onBlur="this.className=''"/>
                            </div>
                        </div>
                        <div class="LinhaEntrada">
                            <div class="LabelLeft">
                                Pesquisar objetos <b>de autoria</b> de
                            </div>
                            <div class="Value"><form:input path="autor" 
                                
                                <input type="text" name="autor" id="autor" value="" onFocus="this.className='inputSelecionado'" onBlur="this.className=''"/>
                            </div>
                        </div>
                        
                        <!--    
                        <div class="LinhaEntrada">
                            <div class="LabelLeft">
                                Local para a busca
                            </div>

                            <c:forEach var="repDAO" items="${repDAO.all}" varStatus="i">

                                <c:if test="${i.index==0}"><div class='ValueTree'>- Reposit&oacute;rios</div></c:if>

                                <div class='ValueTree'>&nbsp;&nbsp;&nbsp;
                                    <input value='${rep.id}' type=checkbox id="${rep.id}" name="repositorios"> ${fn:toUpperCase(rep.nome)}
                                </div>

                            </c:forEach>

                            <c:forEach var="subFed" items="${subDAO.all}" varStatus="i">

                                <c:if test="${i.index==0}"><div class='ValueTree'>- Subfedera&ccedil;&otilde;es</div></c:if>
                                <div class='ValueTree'>&nbsp;&nbsp;&nbsp;
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
                        -->

                        <div class="LinhaEntrada">
                            <div class="Buttons">
                                <input class="BOTAO" type="submit" value="Consultar"/>
                            </div>
                        </div>
                    </div>

                    <div>
                        <a href="${index}">Retornar a busca padr&atilde;o</a>
                    </div>
                </div>
            </form:form>

        </div>
        <%@include file="googleAnalytics"%>
    </body>
</html>
