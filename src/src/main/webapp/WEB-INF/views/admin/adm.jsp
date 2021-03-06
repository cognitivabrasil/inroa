<%--
Document   : index
Created on : 29/04/2009, 12:04:58
Author     : Marcos Nunes
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="feb.spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<%@page import="com.cognitivabrasil.feb.util.Operacoes"%>
<c:url var="root" value="/" />


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FEB - Ferramenta Administrativa</title>

        <c:url var="imgLapiz" value="/imagens/Lapiz-32x32.png" />
        <c:url var="imgAdd" value="/imagens/add-24x24.png" />
        <c:url var="imgDeletar" value="/imagens/ico24_deletar.gif"/>
        <c:url var="imgSincronizar" value="/imagens/sincronizar.png"/>
        <c:url var="imgErroSincronizar" value="/imagens/erro_sincronizar.png"/>

        <link rel="StyleSheet"
              href="<feb.spring:url value="/css/vendor/Theme/jquery-ui-1.8.22.custom.css" htmlEscape="true" />"
              type="text/css">
        <link rel="StyleSheet"
              href="<feb.spring:url value="/css/padrao.css" htmlEscape="true" />"
              type="text/css">

        <link rel="shortcut icon" type="image/x-icon"
              href="<feb.spring:url value="/imagens/favicon.ico" htmlEscape="true" />">

        <script language="JavaScript" type="text/javascript"
        src="<feb.spring:url value="/scripts/funcoes.js" htmlEscape="true" />"></script>
        <script language="javascript" type="text/javascript" src='<feb.spring:url value="/scripts/vendor/jquery-1.7.2.js" htmlEscape="true" />'></script>
        <script type="text/javascript" src='<feb.spring:url value="/scripts/vendor/jquery-ui-1.8.22.custom.min.js" htmlEscape="true" />'></script>
        <script language="JavaScript" type="text/javascript"
        src="<feb.spring:url value="/scripts/vendor/jquery.cookie.js" htmlEscape="true" />"></script>
        <script language="JavaScript" type="text/javascript"
        src="<feb.spring:url value="/scripts/tabsNavigator.js" htmlEscape="true" />"></script>

        <script>
            setRootUrl("${root}");
        </script>
        <script type="text/javascript" src='<feb.spring:url value="/scripts/admin/admin.js" htmlEscape="true" />'></script>
    </head>
    <body id="paginaAdministrativa">

        <jsp:include page="../cabecalho.jsp">
            <jsp:param value="Ferramenta Administrativa" name="titulo" />
            <jsp:param value="7%" name="tamanho" />
        </jsp:include>

        <c:url var="logoutUrl" value="/logout" />
        <div class="sair">
            <button type="button" id="sair" onclick="location.href = '${logoutUrl}'">Sair</button>
        </div>
        <div id="tabs">
            <ul>
                <li><a href="#tabs-1">Reposit&oacute;rios e Federa&ccedil;&otilde;es</a></li>
                <li><a href="#tabs-3">Metadados</a></li>
                <li><a href="#tabs-4">Ger&ecirc;ncia</a></li>
                    <c:url var="estatistics" value="/admin/statistics/" />
                <li><a href="${estatistics}">Estat&iacute;sticas</a></li>

            </ul>
            <div id="tabs-1">
                <table class='admin-table zebra' cellpadding=3>
                    <caption>Reposit&oacute;rios Cadastrado</caption>

                    <th width="10%">Opera&ccedil;&otilde;es</th>
                    <th width="30%">Nome</th>
                    <th width="40%">Descri&ccedil;&atilde;o</th>
                    <th width="20%">&Uacute;ltima atualiza&ccedil;&atilde;o</th>

                    <c:forEach var="rep" items="${repositories}" varStatus="status">
                        <tr>

                            <td>
                                <security:authorize access="hasRole('ROLE_MANAGE_REP')">
                                    <c:url var="excluirRepositorio" value="/admin/repositories/${rep.id}/delete" />
                                    <input type="button" class="botaoExcluir delete_link"
                                           title="excluir reposit&oacute;rio ${rep.name}" name="excluirRep"
                                           id="excluirRep" href="${excluirRepositorio}" />

                                </security:authorize> 
                                &nbsp; 
                                
                                <c:url var="repositoriesEdit" value="/admin/repositories/${rep.id}"/>
                                <input type="button" class="botaoEditar"
                                       title="Editar / Visualizar" name="editar" id="editarRep"
                                       onclick="NewWindow('${repositoriesEdit}', '', '850', 'total');">
                            </td>
                            <td>${rep.name}</td>
                            <td>${rep.descricao}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${rep.isOutdated}">
                                        <div id='textResult${param.id}' class="textoErro">
                                            &nbsp;
                                            ${rep.ultimaAtualizacaoFormatada}
                                            <security:authorize access="hasRole('ROLE_UPDATE')">
                                                &nbsp;
                                                <a title="Atualizar agora"
                                                   onclick="javaScript:atualizaRepAjax(${rep.id}, this.parentNode);">

                                                    <img src='${imgErroSincronizar}' border='0'
                                                         width='24' height='24' alt='Atualizar' align='middle' />
                                                </a>
                                            </security:authorize>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="Value" id="textResult${param.id}">
                                            &nbsp;
                                            ${rep.ultimaAtualizacaoFormatada}
                                            <security:authorize access="hasRole('ROLE_UPDATE')">
                                                &nbsp;&nbsp; 
                                                <a title='Atualizar agora'
                                                   onclick="javaScript:atualizaRepAjax(${rep.id}, this.parentNode);">
                                                    <img src='${imgSincronizar}' border='0' width='24'
                                                         height='24' alt='Atualizar' align='middle'>
                                                </a>
                                            </security:authorize>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>

                    <security:authorize
                        access="hasAnyRole('ROLE_UPDATE, ROLE_MANAGE_REP')">

                        <tr class='footer'>
                            <c:url var="newRepositoryUrl" value="/admin/repositories/new" />
                            <td><security:authorize access="hasRole('ROLE_MANAGE_REP')">

                                    <a title="Adicionar novo reposit&oacute;rio"
                                       onclick="NewWindow('${newRepositoryUrl}', 'Cadastro', '750', 'total');">
                                        <img src="${imgAdd}" border="0" width="24" height="24" alt="Visualizar" align="middle">
                                    </a>
                                </security:authorize></td>
                            <td colspan="2" class="left bold" style="font-size: 110%">
                                &nbsp;&nbsp; 
                                <security:authorize access="hasRole('ROLE_MANAGE_REP')">
                                    <a onclick="NewWindow('${newRepositoryUrl}', 'Cadastro', '750', 'total');">
                                        Adicionar novo reposit&oacute;rio 
                                    </a>
                                </security:authorize>

                            </td>
                            <td>
                                <div id="textResultTodos">

                                    <security:authorize access="hasRole('ROLE_UPDATE')">

                                        <a style="text-decoration: none" title="Atualizar todos"
                                           onclick="javaScript:atualizaRepAjax(0, document.getElementById('textResultTodos'));"><img
                                                src="${imgSincronizar}"
                                                border="0" width="24" height="24" alt="Visualizar"
                                                align="middle"> Atualizar todos agora
                                        </a>
                                    </security:authorize>
                                </div>
                            </td>
                        </tr>
                    </security:authorize>
                </table>

                <table class='admin-table zebra' cellpadding=3>
                    <caption>Federa&ccedil;&otilde;es Cadastradas</caption>

                    <th width="10%">Opera&ccedil;&otilde;es</th>
                    <th width="20%">Nome</th>
                    <th width="50%">Descri&ccedil;&atilde;o</th>
                    <th width="20%">&Uacute;ltima atualiza&ccedil;&atilde;o</th>

                    <c:forEach var="subfed" items="${subDAO.all}" varStatus="status">
                        <tr>
                            <td>
                                <security:authorize access="hasRole('ROLE_MANAGE_REP')">
                                    <c:url var="excluirFederacao" value="/admin/federations/${subfed.id}/delete" />
                                    <input type="button" class="botaoExcluir delete_link"
                                           title="excluir federa&ccedil;&atilde;o ${subfed.name}"
                                           name="excluirFed" id="excluirFed" href="${excluirFederacao}" />

                                </security:authorize> 
                                &nbsp; 
                                <c:url var="federationsEdit" value="/admin/federations/${subfed.id}"/>
                                <input type="button" id="editarSubfed" class="botaoEditar" 
                                       title="Editar / Visualizar" name="editar"                                           
                                       onclick="NewWindow('${federationsEdit}', '', '750', '560');">

                            </td>
                            <td>${subfed.name}</td>
                            <td>${subfed.descricao}</td>
                            <td><c:choose>
                                    <c:when test="${subfed.isOutdated}">
                                        <div id='textResultSF${subfed.id}' class='textoErro'>
                                            ${subfed.ultimaAtualizacaoFormatada}
                                            <security:authorize access="hasRole('ROLE_UPDATE')">

                                                <a title='Atualizar agora'
                                                   onclick="javaScript:atualizaSubfedAjax(${subfed.id}, this.parentNode);">
                                                    <img
                                                        src="${imgErroSincronizar}"
                                                        border="0" width="24" height="24" alt="Visualizar"
                                                        align="middle">
                                                </a>
                                            </security:authorize>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div id='textResultSF${subfed.id}'>
                                            ${subfed.ultimaAtualizacaoFormatada}
                                            <security:authorize access="hasRole('ROLE_UPDATE')">
                                                <a title='Atualizar agora'
                                                   onclick="javaScript:atualizaSubfedAjax(${subfed.id}, this.parentNode);">

                                                    <img
                                                        src="${imgSincronizar}"
                                                        border="0" width="24" height="24" alt="Visualizar"
                                                        align="middle">
                                                </a>
                                            </security:authorize>
                                        </div>
                                    </c:otherwise>
                                </c:choose></td>
                        </tr>
                    </c:forEach>

                    <security:authorize
                        access="hasAnyRole('ROLE_UPDATE, ROLE_MANAGE_REP')">

                        <tr class='footer'>
                            <c:url var="newFederationUrl" value="/admin/federations/new" />
                            <td><security:authorize access="hasRole('ROLE_MANAGE_REP')">

                                    <a title="Adicionar nova federa&ccedil;&atilde;o"
                                       onclick="NewWindow('${newFederationUrl}', 'Cadastro', '750', '650');">
                                        <img src="${imgAdd}" border="0" width="24" height="24" alt="Visualizar" align="middle">
                                    </a>
                                </security:authorize></td>
                            <td colspan="2" class="left bold" style="font-size: 110%">
                                &nbsp;&nbsp; 
                                <security:authorize access="hasRole('ROLE_MANAGE_REP')">

                                    <a onclick="NewWindow('${newFederationUrl}', 'Cadastro', '750', '650');">
                                        Adicionar nova federa&ccedil;&atilde;o 
                                    </a>
                                </security:authorize>
                            </td>
                            <td>
                                <security:authorize access="hasRole('ROLE_UPDATE')">

                                    <div id="textResultSF">
                                        <a style="text-decoration: none" title="Atualizar todas"
                                           onclick="javaScript:atualizaSubfedAjax(0, document.getElementById('textResultSF'));">
                                            <img src="${imgSincronizar}" border="0" width="24" height="24" align="middle"> 
                                            Atualizar todas agora
                                        </a>
                                    </div>
                                </security:authorize>
                            </td>
                        </tr>
                    </security:authorize>
                </table>

                <div id='infoIcones'>
                    <table border="0">
                        <tr>

                            <td><h1>&nbsp;&nbsp;</h1></td>
                            <td>
                                <img src="${imgLapiz}" border="0" width="32" height="32" alt="Laudar" align="middle">
                            </td>
                            <td>&nbsp;Visualizar / Editar</td>

                            <td><h1>&nbsp;&nbsp;</h1></td>

                            <td><img
                                    src="${imgDeletar}"
                                    border="0" width="24" height="24" alt="Visualizar" align="middle">
                            </td>
                            <td>&nbsp;Remover</td>

                            <td><h1>&nbsp;&nbsp;</h1></td>

                            <td><img
                                    src="${imgSincronizar}"
                                    border="0" width="24" height="24" alt="Visualizar" align="middle">
                            </td>
                            <td>&nbsp;Atualizar Reposit&oacute;rio/Federa&ccedil;&aacute;o</td>

                            <td><h1>&nbsp;&nbsp;</h1></td>

                            <td>
                                <img src="${imgAdd}" border="0" width="24" height="24" alt="Visualizar" align="middle">
                            </td>
                            <td>&nbsp;Adicionar</td>

                        </tr>
                    </table>
                </div>
            </div>

            <div id="tabs-3">
                <!--Insere codigo que lista os padroes de metadados-->
                <table class='admin-table zebra' id='padroes' cellpadding=3>
                    <caption>Padr&otilde;es de Metadados Cadastrados</caption>

                    <th width="10%">Opera&ccedil;&otilde;es</th>
                    <th width="20%">Nome</th>
                    <th width="50%">Metadata Prefix</th>
                    <th width="20%">Namespace</th>

                    <c:forEach var="padraoMet" items="${padraoMetadadosDAO.all}"
                               varStatus="status">
                        <tr>
                            <td>
                                <security:authorize access="hasRole('ROLE_MANAGE_METADATA')">
                                    <c:url var="excluirPadrao" value="/admin/metadataStandard/${padraoMet.id}/delete" />
                                    <input type="button" class="botaoExcluir delete_link"
                                           title="excluir padr&atilde;o de metadados ${padraoMet.name}" name="excluirPadrao"
                                           id="excluirPadrao" href="${excluirPadrao}" />

                                </security:authorize>
                                &nbsp;
                                <c:url var="exibePadrao" value="/admin/metadataStandard/${padraoMet.id}" />
                                <input type="button" class="botaoEditar"
                                       title="Editar / Visualizar" name="editar" id="editarPadrao"
                                       onclick="NewWindow('${exibePadrao}', 'editaPadrao', '650', '400');" />
                            </td>
                            <td>${padraoMet.name}</td>
                            <td>${padraoMet.metadataPrefix}</td>
                            <td>${padraoMet.namespace}</td>

                        </tr>
                    </c:forEach>

                    <security:authorize access="hasRole('ROLE_MANAGE_METADATA')">
                        <c:url var="newMetadata" value="/admin/metadataStandard/new" />
                        <tr class='footer'>
                            <td>
                                <a title="Adicionar novo padr&atilde;o de metadados"
                                   onclick="NewTab('${newMetadata}');">
                                    <img src="${imgAdd}" border="0" width="24" height="24" alt="Visualizar" align="middle">
                                </a>
                            </td>
                            <td colspan="2" class="left bold" style="font-size: 110%">
                                &nbsp;&nbsp; 
                                <a onclick="NewTab('${newMetadata}');">
                                    Adicionar novo padr&atilde;o 
                                </a>
                            </td>
                            <td><div id='msgerro' class='textoErro left'></div></td>
                        </tr>
                    </security:authorize>
                </table>
                <!--Fim codigo que lista os padroes-->

                <!--Insere codigo que lista os mapeamentos-->

                <table class='admin-table zebra' cellpadding=3>
                    <caption>Mapeamentos Cadastrados</caption>

                    <th width="10%">Opera&ccedil;&otilde;es</th>
                    <th width="20%">Nome</th>
                    <th width="50%">Descrição</th>
                    <th width="20%">Padrão Metadados</th>

                    <c:forEach var="mapeamento" items="${mapeamentos}"
                               varStatus="status">
                        <tr>
                            <td>
                                <security:authorize access="hasRole('ROLE_MANAGE_METADATA')">
                                    <c:url var="excluirMapeamento" value="/admin/mapeamentos/${mapeamento.id}/delete" />
                                    <input type="button" class="botaoExcluir delete_link"
                                           title="excluir o mapeamento ${mapeamento.name}"
                                           name="excluirMap" id="excluirMap"
                                           href="${excluirMapeamento}" />
                                </security:authorize>
                                &nbsp;
                                <c:url var="exibeMapeamento" value="/admin/mapeamentos/${mapeamento.id}" />
                                <input type="button" class="botaoEditar"
                                       title="Editar / Visualizar" name="editar" id="editarMap"
                                       onclick="NewTab('${exibeMapeamento}');" />
                            </td>
                            <td>${mapeamento.name}</td>
                            <td>${mapeamento.description}</td>
                            <td>${mapeamento.padraoMetadados.name}</td>

                        </tr>
                    </c:forEach>

                    <security:authorize access="hasRole('ROLE_MANAGE_METADATA')">

                        <tr class='footer'>
                            <c:url var="novoMapeamento" value="/admin/mapeamentos/new"/>
                            <td><a title="Adicionar novo padr&atilde;o de metadados"
                                   onclick="NewTab('${novoMapeamento}');">
                                    <img src="${imgAdd}" border="0" width="24" height="24" alt="Visualizar" align="middle">
                                </a></td>
                            <td colspan="2" class="left bold" style="font-size: 110%">
                                &nbsp;&nbsp; <a
                                    onclick="NewTab('${novoMapeamento}');">
                                    Adicionar novo mapeamento </a>
                            </td>
                            <td><div id='msgerro' class='textoErro left'></div></td>
                        </tr>
                    </security:authorize>
                </table>
                <!--Fim codigo que lista os mapeamentos-->

                <div id='infoIcones'>
                    <table border="0">
                        <tr>

                            <td><h1>&nbsp;&nbsp;</h1></td>
                            <td>
                                <img src="${imgLapiz}" border="0" width="32" height="32" alt="Laudar" align="middle">
                            </td>
                            <td>&nbsp;Visualizar / Editar</td>

                            <td><h1>&nbsp;&nbsp;</h1></td>

                            <td><img
                                    src="${imgDeletar}"
                                    border="0" width="24" height="24" alt="Visualizar" align="middle">
                            </td>
                            <td>&nbsp;Remover</td>

                            <td><h1>&nbsp;&nbsp;</h1></td>

                            <td>
                                <img src="${imgAdd}" border="0" width="24" height="24" alt="Visualizar" align="middle">
                            </td>
                            <td>&nbsp;Adicionar</td>

                        </tr>
                    </table>
                </div>
            </div>
            <div id="tabs-4">

                <div class="versao">Vers&atilde;o <b>${version}</b></div>
                <!--<div class="versao">Branch <b>${gitBranch}</b></div>-->
                <div class="versao">Commit <b>${gitCommitId} - ${gitCommitTime}</b></div>
                

                <security:authorize access="hasRole('ROLE_UPDATE')">
                    <c:url var="reindex" value="/admin/confirmaRecalcularIndice"/>
                    <button id="recalculo" onclick="javascript:NewWindow('${reindex}', '', '500', '300');
                            return false">
                        Recalcular o &Iacute;ndice</button>
                    </security:authorize>

                <c:url var="passwdUser" value="/admin/users/passwd"/>
                <button id="alterarSenha" onclick="javascript:NewWindow('${passwdUser}', '', '700', '400');
                        return false">
                    Alterar Senha </button>

                <security:authorize access="hasRole('ROLE_CHANGE_DATABASE')">
                    <c:url var="alterDb" value="/admin/alterDB"/>
                    <button id="alterarBase" onclick="javascript:NewWindow('${alterDb}', '', '650', '500');
                            return false">
                        Alterar Base de Dados
                    </button>
                </security:authorize>


                <table id='tbListUser' class='admin-table zebra' cellpadding=3>
                    <caption>Usu&aacute;rios</caption>

                    <th width="10%">Opera&ccedil;&otilde;es</th>
                    <th width="20%">Login</th>
                    <th width="50%">Nome</th>
                    <th width="20%">Perfil</th>

                    <c:forEach var="user" items="${users}" varStatus="status">
                        <tr>
                            <td>
                                <security:authorize access="hasRole('ROLE_MANAGE_USERS')">
                                    <c:url var="delUser" value="/admin/users/${user.id}/delete" />
                                    <input type="button" class="botaoExcluir delete_link"
                                           title="excluir usu&aacute;rio ${user.username}"
                                           name="excluirUsario" id="excluirUsario" href="${delUser}" />
                                </security:authorize> &nbsp; 
                                <c:if test="${userAdministrator || username == user.username}">
                                    <c:url var="showUser" value="/admin/users"/>
                                    <input type="button" class="botaoEditar"
                                           title="Editar / Visualizar" name="editar" id="editarSubfed"
                                           onclick="NewWindow('${showUser}/${user.id}', '', '750', '560');">
                                </c:if>

                            </td>
                            <td>${user.username}</td>
                            <td>${user.description}</td>
                            <td>${user.role}</td>

                        </tr>
                    </c:forEach>

                    <security:authorize access="hasRole('ROLE_MANAGE_USERS')">
                        <tr class='footer'>
                            <c:url var="newUser" value="/admin/users/new"/>
                            <td>
                                <a title="Adicionar novo usuário"
                                   onclick="NewWindow('${newUser}', 'Cadastro', '750', '650');">
                                    <img src="${imgAdd}" border="0" width="24" height="24" alt="Visualizar" align="middle">
                                </a>
                            </td>
                            <td colspan="2" class="left bold" style="font-size: 110%">
                                &nbsp;&nbsp; <a
                                    onclick="NewWindow('${newUser}', 'Cadastro', '750', '650');">
                                    Adicionar novo usuário </a>
                            </td>
                            <td></td>
                        </tr>
                    </security:authorize>
                </table>

                <div id='infoIcones'>
                    <table border="0">
                        <tr>

                            <td><h1>&nbsp;&nbsp;</h1></td>
                            <td>
                                <img src="${imgLapiz}" border="0" width="32" height="32" alt="Laudar" align="middle">
                            </td>
                            <td>&nbsp;Visualizar / Editar</td>

                            <td><h1>&nbsp;&nbsp;</h1></td>

                            <td><img
                                    src="${imgDeletar}"
                                    border="0" width="24" height="24" alt="Visualizar" align="middle">
                            </td>
                            <td>&nbsp;Remover</td>

                            <td><h1>&nbsp;&nbsp;</h1></td>

                            <td>
                                <img src="${imgAdd}" border="0" width="24" height="24" alt="Visualizar" align="middle">
                            </td>
                            <td>&nbsp;Adicionar</td>

                        </tr>
                    </table>
                </div>
            </div>
        </div>

        <div class="mensagemAdm">${mensagem}</div>

        <div class="dialog-confirm" title="Apagar?">
            <p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
                Deseja realmente <span id="msgApagar"></span>?</p>
        </div>
        <div class="ui-widget" id="dialog-error" title="Erro">
            <div class="ui-state-error ui-corner-all" style="padding: 0 .7em;">
                <p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
                    <strong>Erro:</strong> <span id="errorThrown"></span>.</p>
            </div>
        </div>

    </body>
</html>