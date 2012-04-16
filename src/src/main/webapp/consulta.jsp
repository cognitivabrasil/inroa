<%--
    Document   : consulta
    Created on : 01/07/2009, 16:09:51
    Author     : Marcos
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="operacoesPostgre.Consultar"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="ferramentaBusca.Recuperador"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="StyleSheet" href="css/padrao.css" type="text/css">
        <link href="imagens/favicon.ico" rel="shortcut icon" type="image/x-icon" />
        <title>FEB – Federa&ccedil;&atilde;o de Reposit&oacute;rios Educa Brasil</title>
    </head>
    <body id="body">
        <%
            //pegar valor do model pelo jsp
            //System.out.println(pageContext.getAttribute("item", pageContext.REQUEST_SCOPE));

            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");

            //TODO: ver como resolver isso
            int numObjetosEncontrados = 10;
        %>


        <div class="logoBusca"><img src="imagens/Logo FEB_reduzido.png" width="7%" alt="Logo FEB_reduzido"/></div>


        <div class="clear"> </div>

        <div class="EspacoPequeno">&nbsp;</div>
        <div class="subTituloBusca">&nbsp;Resultado da Pesquisa</div>

        <div id="page-sub-header">
            <a href="index">Efetuar nova consulta</a>
        </div>
        <div class="cabecalhoConsulta">
            <div class="esquerda">
                &nbsp;Consulta efetuada: <i>"<strong>${BuscaModel.consulta}</strong>"</i>
            </div>
            <div class="direita">
                Total de <strong><%=numObjetosEncontrados%></strong> objeto(s) encontrado(s)&nbsp;
            </div>

        </div>


        <div id="body-resultado">

            <%
                if (numObjetosEncontrados == 0) { //se nao retorno nenhum objeto
%>
            <p align="center">
                <strong>
                    <font size="3" face="Verdana, Arial, Helvetica, sans-serif">
                        Nenhum objeto encontrado
                    </font>
                </strong>
            </p>


            <%//se retornou objetos
            } else {

                String url = "";
                //ver como passar o objeto consulta

            %>

            <form action="<%= request.getRequestURI()%>" method="get">
                <center>
                    <p>offset='${offset}'</p>

                    <pg:pager
                    items="<%= numObjetosEncontrados%>"
                        index="center"
                        maxPageItems="5"
                        maxIndexPages="10"
                        isOffset="true"
                        export="offset,currentPageNumber=pageNumber"
                        scope="page"
                        >

                        <%-- keep track of preference --%>
                        <pg:param name="style"/>
                        <pg:param name="position"/>
                        <pg:param name="index"/>
                        <pg:param name="maxPageItems"/>
                        <pg:param name="maxIndexPages"/>

                        <%-- salva pager offset durante as mudancas do form --%>
                        <input type="hidden" name="pager.offset" value="<%= offset%>">

                        <%-- Inclui paginacao antes dos resultados--%>
                        <%@include file="WEB-INF/jsp/paginacaoPersonalizada.jsp" %>

                        <div id="body-resultado-interno">
                            <c:forEach var="doc" items="${documentos}" varStatus="status">
                                <div class="resultadoConsulta">
                                    <c:set var="infoDetalhada"
                                           value="infoDetalhada.jsp?id=${doc.id}"
                                           scope="page"/>

                                    <c:if test="${empty docs.titles}">
                                        <div class=\"titulo\"><a href='${infoDetalhada}'>T&iacute;tulo n&atilde;o informado.</a></div>
                                    </c:if>
                                    <c:forEach var="titulo" items="${doc.titles}">
                                        <div class="titulo">
                                            <a href='${infoDetalhada}'>
                                                ${titulo}
                                            </a>
                                        </div>
                                    </c:forEach>


                                    <c:forEach var="resumo" items="${doc.shortDescriptions}">
                                        <div class="atributo">${resumo}</div>
                                    </c:forEach>

                                    <c:if test="${!empty doc.location}">
                                        <div class="atributo"> Localiza&ccedil;&atilde;o:
                                            <c:forEach var="localizacao" items="${doc.location}">
                                                <div class="atributo"><a href="${localizacao}" target="_new">${localizacao}</a></div>
                                            </c:forEach>
                                        </div>
                                    </c:if>

                                    <c:if test="${!empty doc.date}">
                                        <div class="atributo"> Data:
                                            <c:forEach var="data" items="${doc.date}">
                                                <div class="atributo">${data}</div>
                                            </c:forEach>
                                        </div>
                                    </c:if>

                                    <c:if test="${!empty doc.nomeRep}">
                                        <div class="atributo"> Reposit&oacute;rio: ${doc.nomeRep}</div>
                                    </c:if>



                                </div>
                            </c:forEach>

                        </div>
                        <%-- Inclui paginacao depois dos resultados--%>
                        <%@include file="WEB-INF/jsp/paginacaoPersonalizada.jsp" %>

                    </pg:pager>
                </center>
            </form>
        </div>

        <div class="rodapeConsulta">&nbsp;</div>
        <%@include file="googleAnalytics"%>

    </body>
</html>
