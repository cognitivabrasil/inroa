<%--
Document   : index
Created on : 29/04/2009, 12:04:58
Author     : Marcos Nunes
--%>
<%@page import="sun.security.krb5.internal.crypto.crc32"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@include file="testaSessao.jsp"%>
<%@page import="robo.util.Operacoes" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FEB - Ferramenta Administrativa</title>
        <link rel="StyleSheet" href="<spring:url value="/css/padrao.css" htmlEscape="true" />" type="text/css">
        <link rel="shortcut icon" type="image/x-icon" href="<spring:url value="/imagens/favicon.ico" htmlEscape="true" />"  >
        <script language="JavaScript" type="text/javascript" src="<spring:url value="/scripts/funcoes.js" htmlEscape="true" />"></script>
        <script language="JavaScript" type="text/javascript" src="<spring:url value="/scripts/funcoesMapeamento.js" htmlEscape="true" />"></script>

    </head>
    <body style="cursor:wait">


        <jsp:useBean id="operacoesBean"
                     class="robo.util.Operacoes"
                     scope="page" />


        <table class="cabecalhoAdm">
            <tr>
                <td width =50% class="title">
                    Ferramenta Administrativa
                </td>
                <td width ="10%">
                    <a onclick="javascript:NewWindow('confirmaRecalcularIndice','','500','240','scrollbars=yes,menubar=no,resizable=yes,toolbar=no,location=no,status=no');">Recalcular o &Iacute;ndice</a>
                </td>
                <td width ="10%">
                    <a href="" onclick="javascript:NewWindow('alterarSenhaAdm.jsp','','700','400','scrollbars=yes,menubar=no,resizable=yes,toolbar=no,location=no,status=no');return false">Alterar Senha</a>
                </td>
                <td width ="13%">
                    <a href="" onclick="javascript:NewWindow('alterarSenhaBD','','650','500','scrollbars=yes,menubar=no,resizable=yes,toolbar=no,location=no,status=no');return false">Alterar Base de Dados</a>
                </td>
                <td class="sair" width =7%>
                    <a href="sair">Sair</a>
                </td>
            </tr>
        </table>
        <div class="mensagemAdm">${mensagem}</div>
        <div class="versao">Vers&atilde;o 3.0</div>
        <table class='repositorios-table' cellpadding=3>
            <tr>
                <th colspan=4>
                    <font size="3%" color=black>Lista de Reposit&oacute;rios Cadastrados na Federa&ccedil;&atilde;o</font>
                </th>
            </tr>

            <tr style="background-color: #AEC9E3">
                <th width="10%">Opera&ccedil;&otilde;es</th>

                <th width="30%">Nome</th>
                <th width="40%">Descri&ccedil;&atilde;o</th>
                <th width="20%">&Uacute;ltima atualiza&ccedil;&atilde;o</th>

            </tr>
            <c:forEach var="rep" items="${repDAO.all}" varStatus="status">  
                <tr class="${status.index % 2 == 0? 'price-yes' : 'price-no'}" >              

                    <td>
                        <input type="button" class="botaoExcluir" title="Excluir reposit&oacute;rio" name="excluir" id="excluirRep" onclick="NewWindow('removerRepositorio?id=${rep.id}','','500','240','scrollbars=yes,menubar=no,resizable=yes,toolbar=no,location=no,status=no');" >
                        &nbsp;
                        <input type="button" class="botaoEditar" title="Editar / Visualizar" name="editar" id="editarRep" onclick="NewWindow('exibeRepositorios?id=${rep.id}','','750','total','scrollbars=yes,menubar=no,resizable=yes,toolbar=no,location=no,status=no');" >

                    </td>
                    <td >&nbsp;${rep.nome}</td>
                    <td >&nbsp;${rep.descricao}</td>
                    <td >&nbsp;

                        <c:choose>
                            <c:when test="${operacoesBean.dataAnteriorAtual(rep.proximaAtualizacao)}">
                                <div id='textResult${param.id}' class="textoErro">&nbsp; 
                                    ${operacoesBean.ultimaAtualizacaoFrase(rep.ultimaAtualizacao)}
                                    &nbsp;
                                    <a title="Atualizar agora" onclick="javaScript:atualizaRepAjax(${rep.id}, this.parentNode);">
                                        <img src='../imagens/erro_sincronizar.png' border='0' width='24' height='24' alt='Atualizar' align='middle'>
                                    </a>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="Value" id="textResult${param.id}">&nbsp;
                                    ${operacoesBean.ultimaAtualizacaoFrase(rep.ultimaAtualizacao)}
                                    &nbsp;&nbsp;
                                    <a title='Atualizar agora' onclick="javaScript:atualizaRepAjax(${rep.id}, this.parentNode);">
                                        <img src='../imagens/sincronizar.png' border='0' width='24' height='24' alt='Atualizar' align='middle'> 
                                    </a> 
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>

            </c:forEach>


            <tr class='center'>
                <td>

                    <a title="Adicionar novo reposit&oacute;rio" onclick="NewWindow('cadastraRepositorio','Cadastro','750','total','scrollbars=yes,menubar=no,resizable=yes,toolbar=no,location=no,status=no');">
                        <img src="<spring:url value="/imagens/add-24x24.png" htmlEscape="true" />" border="0" width="24" height="24" alt="Visualizar" align="middle">
                    </a>

                </td>
                <td colspan="2" class="left bold" style="font-size:110%">
                    &nbsp;&nbsp;
                    <a onclick="NewWindow('cadastraRepositorio','Cadastro','750','total','scrollbars=yes,menubar=no,resizable=yes,toolbar=no,location=no,status=no');">
                        Adicionar novo reposit&oacute;rio
                    </a>

                </td>
                <td>
                    <div id="textResultTodos">
                        <a style="text-decoration:none" title="Atualizar todos" onclick="javaScript:atualizaRepAjax(0, document.getElementById('textResultTodos'));"><img src="<spring:url value="/imagens/sincronizar.png" htmlEscape="true" />" border="0" width="24" height="24" alt="Visualizar" align="middle"> Atualizar todos agora</a>
                    </div>
                </td>

            </tr>
        </table>
        <!--Insere codigo que lista os padroes de metadados-->


        <table class='repositorios-table' id='padroes' cellpadding=3>
            <tr>
                <th colspan=4>
                    <font size="3%" color=black>Lista de Padr&atilde;o de metadados</font>
                </th>
            </tr>

            <tr style="background-color: #AEC9E3">
                <th width="10%">Opera&ccedil;&otilde;es</th>

                <th width="20%">Nome</th>
                <th width="50%">Metadata Prefix</th>
                <th width="20%">Namespace</th>

            </tr>
            <c:forEach var="padraoMet" items="${padraoMetadadosDAO.all}" varStatus="status">  
                <tr class="${status.index % 2 == 0? 'price-yes' : 'price-no'}" >              

                    <td>
                        <input type="button" class="botaoExcluir" title="Excluir padr&atilde;o de metadados" name="excluir" id="excluirPadrao" onclick="confirmaDelPadrao(${padraoMet.id},'msgerro','padroes',this.parentNode.parentNode.rowIndex);"/>
                        &nbsp;
                        <input type="button" class="botaoEditar" title="Editar / Visualizar" name="editar" id="editarPadrao" onclick="NewWindow('./padraoMetadados/editaPadrao?id=${padraoMet.id}','editaPadrao','750','total','scrollbars=yes,menubar=no,resizable=yes,toolbar=no,location=no,status=no');"/>

                    </td>
                    <td>${padraoMet.nome}</td>
                    <td>${padraoMet.metadataPrefix}</td>
                    <td>${padraoMet.namespace}</td>

                </tr>
            </c:forEach>

            <tr class='center'>
                <td>

                    <a title="Adicionar novo padr&atilde;o de metadados" onclick="NewWindow('./padraoMetadados/addPadrao','addPadrao','750','650','scrollbars=yes,menubar=no,resizable=yes,toolbar=no,location=no,status=no');">
                        <img src="<spring:url value="/imagens/add-24x24.png" htmlEscape="true" />" border="0" width="24" height="24" alt="Visualizar" align="middle">
                    </a>

                </td>
                <td colspan="2" class="left bold" style="font-size:110%">
                    &nbsp;&nbsp;
                    <a onclick="NewWindow('./padraoMetadados/addPadrao','Cadastro','750','650','scrollbars=yes,menubar=no,resizable=yes,toolbar=no,location=no,status=no');">
                        Adicionar novo padr&atilde;o
                    </a>                  
                </td>
                <td><div id='msgerro' class='textoErro left'></div></td>



            </tr>
        </table>
        <!--Fim codigo que lista os padroes-->

        <!--Insere codigo que lista os mapeamentos-->
        <%--@include file="./mapeamentos/mapeamentos.jsp"--%>
        <!--Fim codigo que lista os mapeamentos-->


        <table class='repositorios-table' cellpadding=3>
            <tr>
                <th colspan=4>
                    <font size="3%" color=black>Lista de Subfedera&ccedil;&atilde;o cadastradas</font>
                </th>
            </tr>

            <tr style="background-color: #AEC9E3">
                <th width="10%">Opera&ccedil;&otilde;es</th>

                <th width="20%">Nome</th>
                <th width="50%">Descri&ccedil;&atilde;o</th>
                <th width="20%">&Uacute;ltima atualiza&ccedil;&atilde;o</th>
            </tr>

            <c:forEach var="subfed" items="${subDAO.all}" varStatus="status">  
                <tr class="${status.index % 2 == 0? 'price-yes' : 'price-no'}" >
                    <td>
                        <input type="button" class="botaoExcluir" title="Excluir Subfedera&ccedil;&atilde;o" name="excluir" id="excluirSubfed" onclick="NewWindow('removerFederacao?id=${subfed.id}','','500','200','scrollbars=yes,menubar=no,resizable=yes,toolbar=no,location=no,status=no');" >
                        &nbsp;
                        <input type="button" class="botaoEditar" title="Editar / Visualizar" name="editar" id="editarSubfed" onclick="NewWindow('exibeFederacao?id=${subfed.id}','','750','560','scrollbars=yes,menubar=no,resizable=yes,toolbar=no,location=no,status=no');" >

                    </td>
                    <td>&nbsp;${subfed.nome}</td>
                    <td>&nbsp;${subfed.descricao}</td>
                    <td>&nbsp;
                        <div id='textResultSF${subfed.id}'>
                            ${operacoesBean.ultimaAtualizacaoFrase(subfed.ultimaAtualizacao)}

                            <a title="Atualizar agora" onclick="javaScript:atualizaSubfedAjax(${subfed.id}, document.getElementById('textResultSF${subfed.id}'));">
                                <img src="<spring:url value="/imagens/sincronizar.png" htmlEscape="true" />" border="0" width="24" height="24" alt="Visualizar" align="middle">
                            </a>                       
                        </div>
                    </td>

                </tr>
            </c:forEach>

            <tr class='center'>
                <td>

                    <a title="Adicionar nova subfedera&ccedil;&atilde;o" onclick="NewWindow('cadastraFederacao','Cadastro','750','650','scrollbars=yes,menubar=no,resizable=yes,toolbar=no,location=no,status=no');">
                        <img src="<spring:url value="/imagens/add-24x24.png" htmlEscape="true" />" border="0" width="24" height="24" alt="Visualizar" align="middle">
                    </a>

                </td>
                <td colspan="2" class="left bold" style="font-size:110%">
                    &nbsp;&nbsp;
                    <a onclick="NewWindow('cadastraFederacao','Cadastro','750','650','scrollbars=yes,menubar=no,resizable=yes,toolbar=no,location=no,status=no');">
                        Adicionar nova subfedera&ccedil;&atilde;o
                    </a>
                </td>
                <td>
                    <div id="textResultSF">
                        <a style="text-decoration:none" title="Atualizar todas" onclick="javaScript:atualizaSubfedAjax(0, document.getElementById('textResultSF'));"><img src="<spring:url value="/imagens/sincronizar.png" htmlEscape="true" />" border="0" width="24" height="24" alt="Visualizar" align="middle"> Atualizar todas agora</a>
                    </div>
                </td>
            </tr>
        </table>



        <div id='infoIcones'>
            <table border="0">
                <tr>

                    <td><h1>&nbsp;&nbsp;</h1></td>
                    <td>
                        <img src="<spring:url value="/imagens/Lapiz-32x32.png" htmlEscape="true" />" border="0" width="32" height="32" alt="Laudar" align="middle">
                    </td>
                    <td>&nbsp;Visualizar / Editar</td>

                    <td><h1>&nbsp;&nbsp;</h1></td>

                    <td>
                        <img src="<spring:url value="/imagens/ico24_deletar.gif" htmlEscape="true" />" border="0" width="24" height="24" alt="Visualizar" align="middle">
                    </td>
                    <td>&nbsp;Remover</td>

                    <td><h1>&nbsp;&nbsp;</h1></td>

                    <td>
                        <img src="<spring:url value="/imagens/sincronizar.png" htmlEscape="true" />" border="0" width="24" height="24" alt="Visualizar" align="middle">
                    </td>
                    <td>&nbsp;Atualizar Reposit&oacute;rio/Federa&ccedil;&aacute;o</td>

                    <td><h1>&nbsp;&nbsp;</h1></td>

                    <td>
                        <img src="<spring:url value="/imagens/add-24x24.png" htmlEscape="true" />" border="0" width="24" height="24" alt="Visualizar" align="middle">
                    </td>
                    <td>&nbsp;Adicionar</td>

                </tr>
            </table>
        </div>
        <script language="JavaScript" type="text/javascript">
            document.body.style.cursor="default";
        </script>
        <%@include file="../googleAnalytics"%>
    </BODY>
</html>
