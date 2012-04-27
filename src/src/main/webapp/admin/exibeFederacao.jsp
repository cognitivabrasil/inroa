<%-- 
    Document   : exibeFederacao
    Created on : 14/09/2009, 12:16:29
    Author     : Marcos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="testaSessaoNovaJanela.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>



<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FEB - Ferramenta Administrativa</title>
        <link rel="StyleSheet" href="../css/padrao.css" type="text/css">
        <link href="../imagens/favicon.ico" rel="shortcut icon" type="image/x-icon" />
    </head>

    <body>
        <jsp:useBean id="operacoesBean" class="robo.util.Operacoes" scope="page" />

        <div id="page">

            <div class="subTitulo-center">&nbsp;Edi&ccedil;&atilde;o / Visualiza&ccedil;&atilde;o de federa&ccedil;&otilde;es cadastradas</div>


            <div class="subtitulo">Informa&ccedil;&otilde;es sobre as subfedera&ccedil;&otilde;es ${subFed.nome}</div>
            <div class="editar"><a href="./editarFederacao?id=${subFed.id}">Editar</a></div>

            <div class="LinhaEntrada">
                <div class="Label">
                    Nome:
                </div>
                <div class="Value">&nbsp;${subFed.nome}</div>
            </div>
            <div class="LinhaEntrada">
                <div class="Label">
                    Descri&ccedil;&atilde;o:
                </div>
                <div class="Value">&nbsp;${subFed.descricao}</div>
            </div>
            <div class="LinhaEntrada">
                <div class="Label">
                    URL da federa&ccedil;&atilde;o:
                </div>
                <div class="Value">&nbsp;${subFed.url}</div>
            </div>

            <div class="LinhaEntrada">
                <div class="Label">
                    &Uacute;ltima Atualiza&ccedil;&atilde;o:
                </div>
                <c:choose>
                    <c:when test="${subFed.isOutdated()}">
                        <div id="textResultSF${param.id}" class='Value textoErro'>&nbsp;
                            ${operacoesBean.ultimaAtualizacaoFrase(subFed.ultimaAtualizacao, subFed.url)}
                            <a title='Atualizar agora' onclick="javaScript:atualizaSubfedAjax(${param.id}, this.parentNode);">
                                <img src='../imagens/erro_sincronizar.png' border='0' width='24' height='24' alt='Atualizar' align='middle'> 
                            </a> 
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div id="textResultSF${param.id}" class="Value">&nbsp;
                            ${operacoesBean.ultimaAtualizacaoFrase(subFed.ultimaAtualizacao, subFed.url)}
                            <a title='Atualizar agora' onclick="javaScript:atualizaSubfedAjax(${param.id}, this.parentNode);">
                                <img src='../imagens/sincronizar.png' border='0' width='24' height='24' alt='Atualizar' align='middle'> 
                            </a>
                        </div>
                    </c:otherwise>
                </c:choose>               
            </div>


            <table  width=100% class="tableSubfed">
                <th width="70%">Reposit&oacute;rio</th><th width="30%">N&uacute;mero de objetos</th>
                <c:forEach var="rep" items="${subFed.repositorios}" varStatus="status">
                    <tr class="${status.index % 2 == 0? 'price-yes' : 'price-no'}">
                        <td>${rep.nome}</td>
                        <td align='center'>${rep.size} </td>
                    </tr>
                </c:forEach>                    

                <tr class="bold">
                    <th align="right">TOTAL</th> <th align='center'>${subFed.sizeDoc}</th>
                </tr>
            </table>
        </div>

        <%@include file="../googleAnalytics"%>
    </body>
</html>