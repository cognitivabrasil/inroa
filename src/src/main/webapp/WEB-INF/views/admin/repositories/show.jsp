<%--
    Document   : exibeRepositorios
    Created on : 03/08/2009, 16:14:12
    Author     : Marcos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="security"
           uri="http://www.springframework.org/security/tags"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FEB - Ferramenta Administrativa</title>
        <c:url var="css" value="/css/padrao.css" />
        <c:url var="favicon" value="/imagens/favicon.ico" />
        <c:url var="funcoes" value="/scripts/funcoes.js" />
        <link rel="StyleSheet" href="${css}" type="text/css">
        <link href="${favicon }" rel="shortcut icon" type="image/x-icon" />
        <script language="JavaScript" type="text/javascript" src="${funcoes}"></script>
        <c:url var="root" value="/" />
        <script>setRootUrl("${root}");</script>
    </head>

    <body>

        <div id="page">
            <c:if test="${recarregar}">
                <script language="JavaScript" type="text/javascript">recarrega();</script>
            </c:if>
            <div class="subTitulo-center">&nbsp;Edi&ccedil;&atilde;o /
                Visualiza&ccedil;&atilde;o de reposit&oacute;rios cadastrados</div>

            <!--Informações Gerais-->
            <div class="subtitulo">Informa&ccedil;&otilde;es gerais</div>
            <security:authorize access="hasRole('ROLE_MANAGE_REP')">

                <div class="editar">
                    <c:url var="editar" value="/admin/repositories/${repId}/edit" />
                    <input type="button" class="botaoEditar" title="Editar"
                           name="editar" id="editarRep" onclick="location.href='${editar}'">
                    <a href="${editar}">Editar</a>
                </div>
            </security:authorize>

            <div class="LinhaEntrada">
                <div class="Label">Nome/Sigla:</div>
                <div class="Value">&nbsp;${rep.name}</div>
            </div>
            <div class="LinhaEntrada">
                <div class="Label">Descri&ccedil;&atilde;o:</div>
                <div class="Value">&nbsp;${rep.descricao}</div>
            </div>
            <div class="LinhaEntrada">
                <div class="Label">Padr&atilde;o de metadados utilizado:</div>
                <div class="Value">&nbsp;${fn:toUpperCase(rep.padraoMetadados.name)}</div>
            </div>
            <div class="LinhaEntrada">
                <div class="Label">Nome do mapeamento:</div>
                <div class="Value">&nbsp;${rep.mapeamento.name} -
                    ${rep.mapeamento.description}</div>
            </div>
            <div class="LinhaEntrada">
                <div class="Label">MetadataPrefix:</div>
                <div class="Value">&nbsp;${rep.metadataPrefix}</div>
            </div>
            <div class="LinhaEntrada">
                <div class="Label">NameSpace:</div>
                <div class="Value">&nbsp;${rep.namespace}</div>
            </div>

            <div class="subtitulo">Sincroniza&ccedil;&atilde;o dos metadados</div>


            <div class="LinhaEntrada">
                <div class="Label">URL que responde OAI-PMH:</div>
                <div class="Value">&nbsp;${rep.url}</div>
            </div>

            <div class="LinhaEntrada">
                <div class="Label">Cole&ccedil;&otilde;es ou Comunidades:</div>
                <div class="Value">
                    &nbsp;
                    <c:choose>
                        <c:when test="${empty rep.colecoesString}">
                            Todas
                        </c:when>
                        <c:otherwise>
                            ${rep.colecoesString}
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

            <div class="subtitulo">Atualiza&ccedil;&atilde;o</div>
            <div class="EspacoAntes">&nbsp;</div>
            <div class="LinhaEntrada">
                <div class="Label">&Uacute;ltima Atualiza&ccedil;&atilde;o:</div>
                <div class="Value">${rep.ultimaAtualizacaoFormatada}</div>
            </div>
            <div class="LinhaEntrada">
                <div class="Label">Pr&oacute;xima Atualiza&ccedil;&atilde;o:</div>

                <c:choose>
                    <c:when test="${rep.isOutdated}">
                        <div id='textResult${repId}' class="ValueErro">
                            <span>
                                ${rep.ultimaAtualizacaoFormatada} &nbsp;&nbsp;
                                <security:authorize access="hasRole('ROLE_UPDATE')">

                                    <a title="Atualizar agora"
                                       onclick="javaScript:atualizaRepAjax(${repId}, this.parentNode);">
                                        <c:url var="erro_sincronizar"
                                               value="/imagens/erro_sincronizar.png" /> <img
                                            src="${erro_sincronizar }" border='0' width='24' height='24'
                                            alt='Atualizar' align='middle'>
                                    </a>
                                </security:authorize>
                            </span>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="Value" id="textResult${repId}">
                            <span>
                                ${rep.proximaAtualizacaoFormatada} &nbsp;&nbsp;
                                <security:authorize access="hasRole('ROLE_UPDATE')">

                                    <a title='Atualizar agora'
                                       onclick="javaScript:atualizaRepAjax(${repId}, this.parentNode);">
                                        <c:url var="sync" value="/imagens/sincronizar.png" /> <img
                                            src="${sync}" border='0' width='24' height='24' alt='Atualizar'
                                            align='middle'>
                                    </a>
                                </security:authorize>
                            </span>
                        </div>
                    </c:otherwise>
                </c:choose>

            </div>

            <div class="LinhaEntrada">
                <div class="Label">N&uacute;mero de objetos:</div>
                <div class="Value">
                    <div>&nbsp; ${repSize}</div>
                </div>
            </div>
            <security:authorize access="hasRole('ROLE_MANAGE_REP')">

                <div id="removeAtualiza" class="ApagaObjetos">
                    <span>
                        <input type="button" value="Formatar e restaurar"
                               onclick="javascript:apagaAtualizaRepAjax(${repId}, this.parentNode)">
                    </span>
                </div>
            </security:authorize>
        </div>

        
    </body>

</html>