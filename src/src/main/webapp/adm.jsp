<%--
Document   : index
Created on : 29/04/2009, 12:04:58
Author     : Marcos Nunes
--%>
<%@page import="sun.security.krb5.internal.crypto.crc32"%>
<%@page import="modelos.SubFederacaoDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="conexaoBD.jsp"%>
<%@include file="testaSessao.jsp"%>
<%@page import="modelos.RepositoryDAO"%>
<%@page import="robo.util.Operacoes" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.Iterator" %>
<%@page import="modelos.Repositorio" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FEB - Ferramenta Administrativa</title>
        <link rel="StyleSheet" href="css/padrao.css" type="text/css">
        <!--<link rel="StyleSheet" media="handheld" href="css/mobile.css" type="text/css">-->
        <link href="imagens/favicon.ico" rel="shortcut icon" type="image/x-icon" >
        <script language="JavaScript" type="text/javascript" src="scripts/funcoes.js"></script>
        <script language="JavaScript" type="text/javascript" src="scripts/funcoesMapeamento.js"></script>

    </head>
    <body style="cursor:wait">
                

        <%

            if (con == null) {
                out.print("<p class='textoErro'>ERRO ao conectar na base de dados! "
                        + "<BR><BR>Verifique as informa&ccedil&otilde;es da base de dados no arquivo feb.properties"
                        + "<BR>Verifique o log para ver o erro gerado.</p>");
            } else {

                org.springframework.context.ApplicationContext ctx = spring.ApplicationContextProvider.getApplicationContext();
                RepositoryDAO repDao = ctx.getBean(RepositoryDAO.class);
                SubFederacaoDAO subDao = ctx.getBean(SubFederacaoDAO.class);
                
                session.setAttribute("repDAO", repDao);
                session.setAttribute("subDAO", subDao);
        %>
        <jsp:useBean id="operacoesBean"
                     class="robo.util.Operacoes"
                     scope="session" />


        <table width=100% border=1 bgcolor="#AEC9E3">
            <tr>
                <td width =50% class="adm-title">
                    Ferramenta Administrativa
                </td>
                <td width ="10%" class="center middle">
                    <a href="" onclick="javascript:NewWindow('recalcularIndice.jsp','','700','400','scrollbars=yes,menubar=no,resizable=yes,toolbar=no,location=no,status=no');">Recalcular o Indice</a>
                </td>
                <td width ="10%" class="center middle">
                    <a href="" onclick="javascript:NewWindow('alterarSenhaAdm.jsp','','700','400','scrollbars=yes,menubar=no,resizable=yes,toolbar=no,location=no,status=no');return false">Alterar Senha</a>
                </td>
                <td width ="13%" class="center middle">
                    <a href="" onclick="javascript:NewWindow('alterarSenhaBD.jsp','','650','500','scrollbars=yes,menubar=no,resizable=yes,toolbar=no,location=no,status=no');return false">Alterar Base de Dados</a>
                </td>
                <th class="center middle" width =7%>
                    <a href="sair.jsp">Sair</a>
                </th>
            </tr>
        </table>
        <div align="right">Vers&atilde;o 3.0</div>
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
                        <input type="button" class="botaoExcluir" title="Excluir reposit&oacute;rio" name="excluir" id="excluirRep" onclick="NewWindow('removerRepositorio.jsp?id=${rep.id}','','500','200','scrollbars=yes,menubar=no,resizable=yes,toolbar=no,location=no,status=no');" >
                        &nbsp;
                        <input type="button" class="botaoEditar" title="Editar / Visualizar" name="editar" id="editarRep" onclick="NewWindow('exibeRepositorios.jsp?id=${rep.id}','','750','total','scrollbars=yes,menubar=no,resizable=yes,toolbar=no,location=no,status=no');" >

                    </td>
                    <td >&nbsp;${rep.nome}</td>
                    <td >&nbsp;${rep.descricao}</td>
                    <td >&nbsp;
                        <div id="textResult${rep.id}">
                            ${operacoesBean.ultimaAtualizacaoFrase(rep.ultimaAtualizacao)}
                            
                            <a title="Atualizar agora" onclick="javaScript:atualizaRepAjax(${rep.id}, document.getElementById('textResult'+${rep.id}));">
                                <img src="./imagens/sincronizar.png" border="0" width="24" height="24" alt="Visualizar" align="middle">
                            </a>
                        </div>
                    </td>
                </tr>

            </c:forEach>


            <tr class='center'>
                <td>

                    <a title="Adicionar novo reposit&oacute;rio" onclick="NewWindow('cadastraRepositorio.jsp','Cadastro','750','total','scrollbars=yes,menubar=no,resizable=yes,toolbar=no,location=no,status=no');">
                        <img src="./imagens/add-24x24.png" border="0" width="24" height="24" alt="Visualizar" align="middle">
                    </a>

                </td>
                <td colspan="2" class="left bold" style="font-size:110%">
                    &nbsp;&nbsp;
                    <a onclick="NewWindow('cadastraRepositorio.jsp','Cadastro','750','total','scrollbars=yes,menubar=no,resizable=yes,toolbar=no,location=no,status=no');">
                        Adicionar novo reposit&oacute;rio
                    </a>

                </td>
                <td>
                    <div id="textResultTodos">
                        <a style="text-decoration:none" title="Atualizar todos" onclick="javaScript:atualizaRepAjax(0, document.getElementById('textResultTodos'));"><img src="./imagens/sincronizar.png" border="0" width="24" height="24" alt="Visualizar" align="middle"> Atualizar todos agora</a>
                    </div>
                </td>
                <%-- linha++;--%>

            </tr>
        </table>
        <!--Insere codigo que lista os padroes de metadados-->
        <%--@include file="./padraoMetadados/padraoMetadados.jsp"%>
        <!--Fim codigo que lista os padroes-->
        <!--Insere codigo que lista os mapeamentos-->
        <%@include file="./mapeamentos/mapeamentos.jsp"--%>
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

<c:forEach var="rep" items="${subDAO.all}" varStatus="status">  
                <tr class="${status.index % 2 == 0? 'price-yes' : 'price-no'}" >              

                    <td>
                        <input type="button" class="botaoExcluir" title="Excluir reposit&oacute;rio" name="excluir" id="excluirRep" onclick="NewWindow('removerRepositorio.jsp?id=${rep.id}','','500','200','scrollbars=yes,menubar=no,resizable=yes,toolbar=no,location=no,status=no');" >
                        &nbsp;
                        <input type="button" class="botaoEditar" title="Editar / Visualizar" name="editar" id="editarRep" onclick="NewWindow('exibeRepositorios.jsp?id=${rep.id}','','750','total','scrollbars=yes,menubar=no,resizable=yes,toolbar=no,location=no,status=no');" >

                    </td>
                    <td >&nbsp;${rep.nome}</td>
                    <td >&nbsp;${rep.descricao}</td>
                    <td >&nbsp;
                        <div id="textResult${rep.id}">
                            ${operacoesBean.ultimaAtualizacaoFrase(rep.ultimaAtualizacao)}
                            
                            <a title="Atualizar agora" onclick="javaScript:atualizaRepAjax(${rep.id}, document.getElementById('textResult'+${rep.id}));">
                                <img src="./imagens/sincronizar.png" border="0" width="24" height="24" alt="Visualizar" align="middle">
                            </a>
                        </div>
                    </td>
                </tr>

            </c:forEach>
            
            
            <tr class='center'>
                <td>

                    <a title="Adicionar nova subfedera&ccedil;&atilde;o" onclick="NewWindow('cadastraFederacao.jsp','Cadastro','750','650','scrollbars=yes,menubar=no,resizable=yes,toolbar=no,location=no,status=no');">
                        <img src="./imagens/add-24x24.png" border="0" width="24" height="24" alt="Visualizar" align="middle">
                    </a>

                </td>
                <td colspan="2" class="left bold" style="font-size:110%">
                    &nbsp;&nbsp;
                    <a onclick="NewWindow('cadastraFederacao.jsp','Cadastro','750','650','scrollbars=yes,menubar=no,resizable=yes,toolbar=no,location=no,status=no');">
                        Adicionar nova subfedera&ccedil;&atilde;o
                    </a>

                </td>
                <td>
                    <div id="textResultSF">
                        <a style="text-decoration:none" title="Atualizar todas" onclick="javaScript:atualizaSubfedAjax(0, document.getElementById('textResultSF'));"><img src="./imagens/sincronizar.png" border="0" width="24" height="24" alt="Visualizar" align="middle"> Atualizar todas agora</a>
                    </div>
                </td>

     
            </tr>
        </table>



        <div id='infoIcones'>
            <table border="0">
                <tr>

                    <td><h1>&nbsp;&nbsp;</h1></td>
                    <td>
                        <img src="./imagens/Lapiz-32x32.png" border="0" width="32" height="32" alt="Laudar" align="middle">
                    </td>
                    <td>&nbsp;Visualizar / Editar</td>

                    <td><h1>&nbsp;&nbsp;</h1></td>

                    <td>
                        <img src="./imagens/ico24_deletar.gif" border="0" width="24" height="24" alt="Visualizar" align="middle">
                    </td>
                    <td>&nbsp;Remover</td>

                    <td><h1>&nbsp;&nbsp;</h1></td>

                    <td>
                        <img src="./imagens/sincronizar.png" border="0" width="24" height="24" alt="Visualizar" align="middle">
                    </td>
                    <td>&nbsp;Atualizar reposit&oacute;rio</td>

                    <td><h1>&nbsp;&nbsp;</h1></td>

                    <td>
                        <img src="./imagens/add-24x24.png" border="0" width="24" height="24" alt="Visualizar" align="middle">
                    </td>
                    <td>&nbsp;Adicionar</td>

                </tr>
            </table>
        </div>
        <script language="JavaScript" type="text/javascript">
            document.body.style.cursor="default";
        </script>
        <%@include file="googleAnalytics"%>
        <%
                con.close(); //fechar conexao o banco de dados
            }
        %>
    </BODY>
</html>
