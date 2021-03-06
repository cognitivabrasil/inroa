<%-- 
    Document   : exibeFederacao
    Created on : 14/09/2009, 12:16:29
    Author     : Marcos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="security"
           uri="http://www.springframework.org/security/tags"%>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FEB - Ferramenta Administrativa</title>
        <c:url var="favicon" value="/imagens/favicon.ico" />
        <c:url var="css" value="/css/padrao.css" />
        <c:url var="funcoesJs" value="/scripts/funcoes.js" />

        <c:url var="funcoesMapeamentoJs" value="/scripts/funcoesMapeamento.js" />

        <link href="${favicon}" rel="shortcut icon" type="image/x-icon" />
        <link rel="StyleSheet" href="${css }" type="text/css" />
        <script type="text/javascript" src="${funcoesJs }"></script>
        <c:url var="root" value="/" />
        <script>setRootUrl("${root}");</script>
    </head>

    <body>

        <div id="page">
            <c:if test="${recarregar}">
                <%--quando edita, ele retorna para ca e avisa para recarregar a pagina principal--%>
                <script language="JavaScript" type="text/javascript">recarrega();</script>
            </c:if>
            <div class="subTitulo-center">&nbsp;Edi&ccedil;&atilde;o /
                Visualiza&ccedil;&atilde;o de federa&ccedil;&otilde;es cadastradas</div>


            <div class="subtitulo">Informa&ccedil;&otilde;es sobre as
                federa&ccedil;&otilde;es ${federation.name}</div>

            <security:authorize access="hasRole('ROLE_MANAGE_REP')">
                <div class="editar">
                    <a href="./${federation.id}/edit">Editar</a>
                </div>
            </security:authorize>

            <div class="LinhaEntrada">
                <div class="Label">Nome:</div>
                <div class="Value">&nbsp;${federation.name}</div>
            </div>
            <div class="LinhaEntrada">
                <div class="Label">Descri&ccedil;&atilde;o:</div>
                <div class="Value">&nbsp;${federation.descricao}</div>
            </div>
            <div class="LinhaEntrada">
                <div class="Label">Versão:</div>
                <div class="Value">&nbsp;${federation.version}</div>
            </div>
            <div class="LinhaEntrada">
                <div class="Label">URL da federa&ccedil;&atilde;o:</div>
                <div class="Value">&nbsp;${federation.url}</div>
            </div>

            <div class="LinhaEntrada">
                <div class="Label">&Uacute;ltima Atualiza&ccedil;&atilde;o:</div>
                <c:choose>
                    <c:when test="${federation.isOutdated}">
                        <div id="textResultSF${param.id}" class='Value textoErro'>
                            <span> ${federation.ultimaAtualizacaoFormatada} 
                                <security:authorize access="hasRole('ROLE_UPDATE')">

                                    <a title='Atualizar agora'
                                       onclick="javaScript:atualizaSubfedAjax(${federation.id}, this.parentNode);">
                                        <img src='../../imagens/erro_sincronizar.png' border='0'
                                             width='24' height='24' alt='Atualizar' align='middle'>
                                    </a>
                                </security:authorize>
                            </span>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div id="textResultSF${federation.id}" class="Value">
                            <span> ${federation.ultimaAtualizacaoFormatada} 
                                <security:authorize access="hasRole('ROLE_UPDATE')">

                                    <a title='Atualizar agora'
                                       onclick="javaScript:atualizaSubfedAjax(${federation.id}, this.parentNode);">
                                        <img src='../../imagens/sincronizar.png' border='0' width='24'
                                             height='24' alt='Atualizar' align='middle'>
                                    </a>
                                </security:authorize>
                            </span>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>

            <table width=100% class="zebra tableSubfed">
                <th width="70%">Reposit&oacute;rio</th>
                <th width="30%">N&uacute;mero de objetos</th>
                    <c:forEach var="rep" items="${mapRepSize}">
                    <tr>
                        <td>${rep.key}</td>
                        <td>${rep.value}</td>
                    </tr>
                </c:forEach>
                <tr class="bold">
                    <th align="right">TOTAL</th>
                    <th align='center'>${fedSize}</th>
                </tr>
            </table>

            <security:authorize access="hasRole('ROLE_MANAGE_REP')">

                <div id="removeAtualiza" class="ApagaObjetos">
                    <span> <input type="button" value="Formatar e restaurar"
                                  onclick="javascript:deleteAndUpdateSubFedAjax(${federation.id}, this.parentNode)">
                    </span>
                </div>
            </security:authorize>
        </div>
    </body>
</html>