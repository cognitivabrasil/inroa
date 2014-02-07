<%--
Document   : index
Created on : 29/04/2009, 12:04:58
Author     : Marcos Nunes
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="feb.spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<%@page import="feb.util.Operacoes"%>
<c:url var="root" value="/" />


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FEB - Ferramenta Administrativa</title>

        <link rel="StyleSheet"
              href="<feb.spring:url value="/css/Theme/jquery-ui-1.8.22.custom.css" htmlEscape="true" />"
              type="text/css">
        <link rel="StyleSheet"
              href="<feb.spring:url value="/css/padrao.css" htmlEscape="true" />"
              type="text/css">

        <link rel="shortcut icon" type="image/x-icon"
              href="<feb.spring:url value="/imagens/favicon.ico" htmlEscape="true" />">

        <script language="JavaScript" type="text/javascript"
        src="<feb.spring:url value="/scripts/funcoes.js" htmlEscape="true" />"></script>
        <script language="javascript" type="text/javascript" src='<feb.spring:url value="/scripts/jquery-1.7.2.min.js" htmlEscape="true" />'></script>
        <script type="text/javascript" src='<feb.spring:url value="/scripts/jquery-ui-1.8.22.custom.min.js" htmlEscape="true" />'></script>
        <script language="JavaScript" type="text/javascript"
        src="<feb.spring:url value="/scripts/jquery.cookie.js" htmlEscape="true" />"></script>
        <script language="JavaScript" type="text/javascript"
        src="<feb.spring:url value="/scripts/tabsNavigator.js" htmlEscape="true" />"></script>

        <script>
            setRootUrl(${root});
            $(function() {
                $( "#recalculo" ).button({
                    
                    icons: {
                        primary: "ui-icon-arrowrefresh-1-n"
                    }
                })
                
                $( "#alterarSenha" ).button({
                    
                    icons: {
                        primary: "ui-icon-key"
                    }
                })
                
                $( "#alterarBase" ).button({
                    
                    icons: {
                        primary: "ui-icon-suitcase"
                    }
                })
                
                $( "#sair" ).button({
                    
                    icons: {
                        primary: "ui-icon-close"
                    }
                })
            });
        </script>
        <script type="text/javascript" src='<feb.spring:url value="/scripts/admin.js" htmlEscape="true" />'></script>

    </head>
    <body id="paginaAdministrativa">

        
        <jsp:include page="../cabecalho.jsp">
            <jsp:param value="Ferramenta Administrativa" name="titulo" />
            <jsp:param value="7%" name="tamanho" />
        </jsp:include>

        <c:url var="logoutUrl" value="/j_spring_security_logout" />
        <div class="sair">
            <button type="button" id="sair" onclick="location.href='${logoutUrl}'">Sair</button>
        </div>
        <div id="tabs">
            <ul>
                <li><a href="#tabs-1">Reposit&oacute;rios e Federa&ccedil;&otilde;es</a></li>
                <li><a href="#tabs-3">Metadados</a></li>
                <li><a href="#tabs-4">Ger&ecirc;ncia</a></li>
                <c:url var="estatistics" value="/admin/statistics/" />
                <li id="estatisticas"><a href="${estatistics}">Estat&iacute;sticas</a></li>


            </ul>
            <div id="tabs-1">
                <table class='repositorios-table' cellpadding=3>
                    <tr>
                    <caption>Reposit&oacute;rios Cadastrados na Federa&ccedil;&atilde;o</caption>
                    </tr>

                    <tr style="background-color: #AEC9E3">
                        <th width="10%">Opera&ccedil;&otilde;es</th>

                        <th width="30%">Nome</th>
                        <th width="40%">Descri&ccedil;&atilde;o</th>
                        <th width="20%">&Uacute;ltima atualiza&ccedil;&atilde;o</th>

                    </tr>
                    <c:forEach var="rep" items="${repositories}" varStatus="status">
                        <tr class="${status.index % 2 == 0? 'price-yes' : 'price-no'}">

                            <td><security:authorize access="hasRole('PERM_MANAGE_REP')">
                                    <c:url var="excluirRepositorio" value="/admin/repositories/${rep.id}/delete" />
                                    <input type="button" class="botaoExcluir delete_link"
                                           title="excluir reposit&oacute;rio ${rep.name}" name="excluirRep"
                                           id="excluirRep" href="${excluirRepositorio}" />

                                </security:authorize> &nbsp; <input type="button" class="botaoEditar"
                                              title="Editar / Visualizar" name="editar" id="editarRep"
                                              onclick="NewWindow('repositories/${rep.id}','','850','total');">
                            </td>
                            <td>${rep.name}</td>
                            <td>${rep.descricao}</td>
                            <td><c:choose>
                                    <c:when test="${rep.isOutdated}">
                                        <div id='textResult${param.id}' class="textoErro">
                                            &nbsp;
                                            ${rep.ultimaAtualizacaoFormatada}
                                            <security:authorize access="hasRole('PERM_UPDATE')">
                                                &nbsp;
                                                <a title="Atualizar agora"
                                                   onclick="javaScript:atualizaRepAjax(${rep.id}, this.parentNode);">

                                                    <img src='../imagens/erro_sincronizar.png' border='0'
                                                         width='24' height='24' alt='Atualizar' align='middle' />
                                                </a>
                                            </security:authorize>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="Value" id="textResult${param.id}">
                                            &nbsp;
                                            ${rep.ultimaAtualizacaoFormatada}
                                            <security:authorize access="hasRole('PERM_UPDATE')">
                                                &nbsp;&nbsp; <a title='Atualizar agora'
                                                                onclick="javaScript:atualizaRepAjax(${rep.id}, this.parentNode);">
                                                    <img src='../imagens/sincronizar.png' border='0' width='24'
                                                         height='24' alt='Atualizar' align='middle'>
                                                </a>
                                            </security:authorize>
                                        </div>
                                    </c:otherwise>
                                </c:choose></td>
                        </tr>
                    </c:forEach>

                    <security:authorize
                        access="hasAnyRole('PERM_UPDATE, PERM_MANAGE_REP')">

                        <tr class='center'>
                            <c:url var="newRepositoryUrl" value="/admin/repositories/new" />
                            <td><security:authorize access="hasRole('PERM_MANAGE_REP')">

                                    <a title="Adicionar novo reposit&oacute;rio"
                                       onclick="NewWindow('${newRepositoryUrl}','Cadastro','750','total');">
                                        <img
                                            src="<feb.spring:url value="/imagens/add-24x24.png" htmlEscape="true" />"
                                            border="0" width="24" height="24" alt="Visualizar" align="middle">
                                    </a>
                                </security:authorize></td>
                            <td colspan="2" class="left bold" style="font-size: 110%">
                                &nbsp;&nbsp; <security:authorize
                                    access="hasRole('PERM_MANAGE_REP')">

                                    <a
                                        onclick="NewWindow('${newRepositoryUrl}','Cadastro','750','total');">
                                        Adicionar novo reposit&oacute;rio </a>
                                    </security:authorize>

                            </td>
                            <td>
                                <div id="textResultTodos">

                                    <security:authorize access="hasRole('PERM_UPDATE')">

                                        <a style="text-decoration: none" title="Atualizar todos"
                                           onclick="javaScript:atualizaRepAjax(0, document.getElementById('textResultTodos'));"><img
                                                src="<feb.spring:url value="/imagens/sincronizar.png" htmlEscape="true" />"
                                                border="0" width="24" height="24" alt="Visualizar"
                                                align="middle"> Atualizar todos agora</a>
                                        </security:authorize>
                                </div>
                            </td>
                        </tr>
                    </security:authorize>
                </table>

                <table class='repositorios-table' cellpadding=3>
                    <tr>
                    <caption>Federa&ccedil;&otilde;es Cadastradas</caption>
                    </tr>

                    <tr style="background-color: #AEC9E3">
                        <th width="10%">Opera&ccedil;&otilde;es</th>

                        <th width="20%">Nome</th>
                        <th width="50%">Descri&ccedil;&atilde;o</th>
                        <th width="20%">&Uacute;ltima atualiza&ccedil;&atilde;o</th>
                    </tr>

                    <c:forEach var="subfed" items="${subDAO.all}" varStatus="status">
                        <tr class="${status.index % 2 == 0? 'price-yes' : 'price-no'}">
                            <td><security:authorize access="hasRole('PERM_MANAGE_REP')">
                                    <c:url var="excluirFederacao" value="/admin/federations/${subfed.id}/delete" />
                                    <input type="button" class="botaoExcluir delete_link"
                                           title="excluir federa&ccedil;&atilde;o ${subfed.name}"
                                           name="excluirFed" id="excluirFed" href="${excluirFederacao}" />

                                </security:authorize> &nbsp; <input type="button" class="botaoEditar"
                                              title="Editar / Visualizar" name="editar" id="editarSubfed"
                                              onclick="NewWindow('./federations/${subfed.id}','','750','560');">

                            </td>
                            <td>${subfed.name}</td>
                            <td>${subfed.descricao}</td>
                            <td><c:choose>
                                    <c:when test="${subfed.isOutdated}">
                                        <div id='textResultSF${subfed.id}' class='textoErro'>
                                            ${subfed.ultimaAtualizacaoFormatada}
                                            <security:authorize access="hasRole('PERM_UPDATE')">

                                                <a title='Atualizar agora'
                                                   onclick="javaScript:atualizaSubfedAjax(${subfed.id}, this.parentNode);">
                                                    <img
                                                        src="<feb.spring:url value="/imagens/erro_sincronizar.png" htmlEscape="true" />"
                                                        border="0" width="24" height="24" alt="Visualizar"
                                                        align="middle">
                                                </a>
                                            </security:authorize>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div id='textResultSF${subfed.id}'>
                                            ${subfed.ultimaAtualizacaoFormatada}
                                            <security:authorize access="hasRole('PERM_UPDATE')">
                                                <a title='Atualizar agora'
                                                   onclick="javaScript:atualizaSubfedAjax(${subfed.id}, this.parentNode);">

                                                    <img
                                                        src="<feb.spring:url value="/imagens/sincronizar.png" htmlEscape="true" />"
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
                        access="hasAnyRole('PERM_UPDATE, PERM_MANAGE_REP')">

                        <tr class='center'>
                            <c:url var="newFederationUrl" value="/admin/federations/new" />
                            <td><security:authorize access="hasRole('PERM_MANAGE_REP')">

                                    <a title="Adicionar nova federa&ccedil;&atilde;o"
                                       onclick="NewWindow('${newFederationUrl}','Cadastro','750','650');">
                                        <img
                                            src="<feb.spring:url value="/imagens/add-24x24.png" htmlEscape="true" />"
                                            border="0" width="24" height="24" alt="Visualizar" align="middle">
                                    </a>
                                </security:authorize></td>
                            <td colspan="2" class="left bold" style="font-size: 110%">
                                &nbsp;&nbsp; <security:authorize
                                    access="hasRole('PERM_MANAGE_REP')">

                                    <a
                                        onclick="NewWindow('${newFederationUrl}','Cadastro','750','650');">
                                        Adicionar nova federa&ccedil;&atilde;o </a>
                                    </security:authorize>
                            </td>
                            <td><security:authorize access="hasRole('PERM_UPDATE')">

                                    <div id="textResultSF">
                                        <a style="text-decoration: none" title="Atualizar todas"
                                           onclick="javaScript:atualizaSubfedAjax(0, document.getElementById('textResultSF'));"><img
                                                src="<feb.spring:url value="/imagens/sincronizar.png" htmlEscape="true" />"
                                                border="0" width="24" height="24" alt="Visualizar"
                                                align="middle"> Atualizar todas agora</a>
                                    </div>
                                </security:authorize></td>
                        </tr>
                    </security:authorize>
                </table>

                <div id='infoIcones'>
                    <table border="0">
                        <tr>

                            <td><h1>&nbsp;&nbsp;</h1></td>
                            <td><img
                                    src="<feb.spring:url value="/imagens/Lapiz-32x32.png" htmlEscape="true" />"
                                    border="0" width="32" height="32" alt="Laudar" align="middle">
                            </td>
                            <td>&nbsp;Visualizar / Editar</td>

                            <td><h1>&nbsp;&nbsp;</h1></td>

                            <td><img
                                    src="<feb.spring:url value="/imagens/ico24_deletar.gif" htmlEscape="true" />"
                                    border="0" width="24" height="24" alt="Visualizar" align="middle">
                            </td>
                            <td>&nbsp;Remover</td>

                            <td><h1>&nbsp;&nbsp;</h1></td>

                            <td><img
                                    src="<feb.spring:url value="/imagens/sincronizar.png" htmlEscape="true" />"
                                    border="0" width="24" height="24" alt="Visualizar" align="middle">
                            </td>
                            <td>&nbsp;Atualizar Reposit&oacute;rio/Federa&ccedil;&aacute;o</td>

                            <td><h1>&nbsp;&nbsp;</h1></td>

                            <td><img
                                    src="<feb.spring:url value="/imagens/add-24x24.png" htmlEscape="true" />"
                                    border="0" width="24" height="24" alt="Visualizar" align="middle">
                            </td>
                            <td>&nbsp;Adicionar</td>

                        </tr>
                    </table>
                </div>
            </div>

            <div id="tabs-3">
                <!--Insere codigo que lista os padroes de metadados-->
                <table class='repositorios-table' id='padroes' cellpadding=3>
                    <tr>
                    <caption>Padr&otilde;es de Metadados Cadastrados</caption>
                    </tr>

                    <tr style="background-color: #AEC9E3">
                        <th width="10%">Opera&ccedil;&otilde;es</th>


                        <th width="20%">Nome</th>
                        <th width="50%">Metadata Prefix</th>
                        <th width="20%">Namespace</th>

                    </tr>
                    <c:forEach var="padraoMet" items="${padraoMetadadosDAO.all}"
                               varStatus="status">
                        <tr class="${status.index % 2 == 0? 'price-yes' : 'price-no'}">


                            <td>
                                <security:authorize access="hasRole('PERM_MANAGE_METADATA')">
                                    <c:url var="excluirPadrao" value="/admin/metadataStandard/${padraoMet.id}/delete" />
                                    <input type="button" class="botaoExcluir delete_link"
                                           title="excluir padr&atilde;o de metadados ${padraoMet.name}" name="excluirPadrao"
                                           id="excluirPadrao" href="${excluirPadrao}" />

                                </security:authorize>
                                &nbsp;
                                <input type="button" class="botaoEditar"
                                       title="Editar / Visualizar" name="editar" id="editarPadrao"
                                       onclick="NewWindow('./metadataStandard/${padraoMet.id}','editaPadrao','650','400');" />
                            </td>
                            <td>${padraoMet.name}</td>
                            <td>${padraoMet.metadataPrefix}</td>
                            <td>${padraoMet.namespace}</td>

                        </tr>
                    </c:forEach>

                    <security:authorize access="hasRole('PERM_MANAGE_METADATA')">

                        <tr class='center'>
                            <td><a title="Adicionar novo padr&atilde;o de metadados"
                                   onclick="NewTab('./metadataStandard/new');">
                                    <img
                                        src="<feb.spring:url value="/imagens/add-24x24.png" htmlEscape="true" />"
                                        border="0" width="24" height="24" alt="Visualizar" align="middle">
                                </a></td>
                            <td colspan="2" class="left bold" style="font-size: 110%">
                                &nbsp;&nbsp; <a
                                    onclick="NewTab('./metadataStandard/new');">
                                    Adicionar novo padr&atilde;o </a>
                            </td>
                            <td><div id='msgerro' class='textoErro left'></div></td>
                        </tr>
                    </security:authorize>
                </table>
                <!--Fim codigo que lista os padroes-->

                <!--Insere codigo que lista os mapeamentos-->

                <table class='repositorios-table' id='padroes' cellpadding=3>
                    <tr>
                    <caption>Mapeamentos Cadastrados</caption>
                    </tr>

                    <tr style="background-color: #AEC9E3">
                        <th width="10%">Opera&ccedil;&otilde;es</th>


                        <th width="20%">Nome</th>
                        <th width="50%">Descrição</th>
                        <th width="20%">Padrão Metadados</th>

                    </tr>
                    <c:forEach var="mapeamento" items="${mapeamentos}"
                               varStatus="status">
                        <tr class="${status.index % 2 == 0? 'price-yes' : 'price-no'}">


                            <td>
                                <security:authorize access="hasRole('PERM_MANAGE_METADATA')">
                                    <c:url var="excluirMapeamento" value="/admin/mapeamentos/${mapeamento.id}/delete" />
                                    <input type="button" class="botaoExcluir delete_link"
                                           title="excluir o mapeamento ${mapeamento.name}"
                                           name="excluirMap" id="excluirMap"
                                           href="${excluirMapeamento}" />
                                </security:authorize>
                                &nbsp; <input type="button" class="botaoEditar"
                                              title="Editar / Visualizar" name="editar" id="editarMap"
                                              onclick="NewTab('./mapeamentos/${mapeamento.id}');" />
                            </td>
                            <td>${mapeamento.name}</td>
                            <td>${mapeamento.description}</td>
                            <td>${mapeamento.padraoMetadados.name}</td>

                        </tr>
                    </c:forEach>

                    <security:authorize access="hasRole('PERM_MANAGE_METADATA')">

                        <tr class='center'>
                            <td><a title="Adicionar novo padr&atilde;o de metadados"
                                   onclick="NewTab('./mapeamentos/new');">
                                    <img
                                        src="<feb.spring:url value="/imagens/add-24x24.png" htmlEscape="true" />"
                                        border="0" width="24" height="24" alt="Visualizar" align="middle">
                                </a></td>
                            <td colspan="2" class="left bold" style="font-size: 110%">
                                &nbsp;&nbsp; <a
                                    onclick="NewTab('./mapeamentos/new');">
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
                            <td><img
                                    src="<feb.spring:url value="/imagens/Lapiz-32x32.png" htmlEscape="true" />"
                                    border="0" width="32" height="32" alt="Laudar" align="middle">
                            </td>
                            <td>&nbsp;Visualizar / Editar</td>

                            <td><h1>&nbsp;&nbsp;</h1></td>

                            <td><img
                                    src="<feb.spring:url value="/imagens/ico24_deletar.gif" htmlEscape="true" />"
                                    border="0" width="24" height="24" alt="Visualizar" align="middle">
                            </td>
                            <td>&nbsp;Remover</td>

                            <td><h1>&nbsp;&nbsp;</h1></td>

                            <td><img
                                    src="<feb.spring:url value="/imagens/add-24x24.png" htmlEscape="true" />"
                                    border="0" width="24" height="24" alt="Visualizar" align="middle">
                            </td>
                            <td>&nbsp;Adicionar</td>

                        </tr>
                    </table>
                </div>
            </div>
            <div id="tabs-4">

                <div class="versao">Vers&atilde;o ${versao}</div>

                <security:authorize access="hasRole('PERM_UPDATE')">

                    <button id="recalculo" onclick="javascript:NewWindow('confirmaRecalcularIndice','','500','240');">
                        Recalcular o &Iacute;ndice</button>
                    </security:authorize>

                <button id="alterarSenha" onclick="javascript:NewWindow('./users/passwd','','700','400');return false">
                    Alterar Senha </button>

                <security:authorize access="hasRole('PERM_CHANGE_DATABASE')">
                    <button id="alterarBase" onclick="javascript:NewWindow('alterDB','','650','500');return false">
                        Alterar Base de Dados</button>
                    </security:authorize>


                <table class='repositorios-table' cellpadding=3>
                    <tr>
                    <caption>Usu&aacute;rios</caption>
                    </tr>

                    <tr style="background-color: #AEC9E3">
                        <th width="10%">Opera&ccedil;&otilde;es</th>

                        <th width="20%">Login</th>
                        <th width="50%">Nome</th>
                        <th width="20%">Perfil</th>
                    </tr>

                    <c:forEach var="user" items="${users}" varStatus="status">
                        <tr class="${status.index % 2 == 0? 'price-yes' : 'price-no'}">
                            <td><security:authorize access="hasRole('PERM_MANAGE_USERS')">
                                    <c:url var="delUser" value="/admin/users/${user.id}/delete" />
                                    <input type="button" class="botaoExcluir delete_link"
                                           title="excluir usu&aacute;rio ${user.username}"
                                           name="excluirUsario" id="excluirUsario" href="${delUser}" />
                                </security:authorize> &nbsp; <input type="button" class="botaoEditar"
                                              title="Editar / Visualizar" name="editar" id="editarSubfed"
                                              onclick="NewWindow('./users/${user.id}','','750','560');">

                            </td>
                            <td>${user.username}</td>
                            <td>${user.description}</td>
                            <td>${user.role}</td>

                        </tr>
                    </c:forEach>

                    <security:authorize access="hasRole('PERM_MANAGE_USERS')">
                        <tr class='center'>
                            <td><a title="Adicionar novo usuário"
                                   onclick="NewWindow('./users/new','Cadastro','750','650');">
                                    <img
                                        src="<feb.spring:url value="/imagens/add-24x24.png" htmlEscape="true" />"
                                        border="0" width="24" height="24" alt="Visualizar" align="middle">
                                </a></td>
                            <td colspan="2" class="left bold" style="font-size: 110%">
                                &nbsp;&nbsp; <a
                                    onclick="NewWindow('./users/new','Cadastro','750','650');">
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
                            <td><img
                                    src="<feb.spring:url value="/imagens/Lapiz-32x32.png" htmlEscape="true" />"
                                    border="0" width="32" height="32" alt="Laudar" align="middle">
                            </td>
                            <td>&nbsp;Visualizar / Editar</td>

                            <td><h1>&nbsp;&nbsp;</h1></td>

                            <td><img
                                    src="<feb.spring:url value="/imagens/ico24_deletar.gif" htmlEscape="true" />"
                                    border="0" width="24" height="24" alt="Visualizar" align="middle">
                            </td>
                            <td>&nbsp;Remover</td>

                            <td><h1>&nbsp;&nbsp;</h1></td>

                            <td><img
                                    src="<feb.spring:url value="/imagens/add-24x24.png" htmlEscape="true" />"
                                    border="0" width="24" height="24" alt="Visualizar" align="middle">
                            </td>
                            <td>&nbsp;Adicionar</td>

                        </tr>
                    </table>
                </div>
            </div>
            <div class='center hidden' id='loading'> <img src='/feb/imagens/ajax-loader.gif' border='0' alt='Atualizando' align='middle'> <p class='textoErro center'>Aguarde, carregando...</p> </div>
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
        
        <%@include file="../googleAnalytics"%>
    </BODY>
</html>
