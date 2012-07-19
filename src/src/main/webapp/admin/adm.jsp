<%--
Document   : index
Created on : 29/04/2009, 12:04:58
Author     : Marcos Nunes
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="feb.spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<%@page import="feb.util.Operacoes"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>FEB - Ferramenta Administrativa</title>
<link rel="StyleSheet"
	href="<feb.spring:url value="/css/padrao.css" htmlEscape="true" />"
	type="text/css">
<link rel="shortcut icon" type="image/x-icon"
	href="<feb.spring:url value="/imagens/favicon.ico" htmlEscape="true" />">
<script language="JavaScript" type="text/javascript"
	src="<feb.spring:url value="/scripts/funcoes.js" htmlEscape="true" />"></script>
<script language="JavaScript" type="text/javascript"
	src="<feb.spring:url value="/scripts/funcoesMapeamento.js" htmlEscape="true" />"></script>

</head>
<body id="paginaAdministrativa">


	<jsp:include page="../cabecalho.jsp">
		<jsp:param value="Ferramenta Administrativa" name="titulo" />
		<jsp:param value="7%" name="tamanho" />
	</jsp:include>


	<table class="cabecalhoAdm">
		<tr>
			<security:authorize access="hasRole('PERM_UPDATE')">

				<td width="10%"><a
					onclick="javascript:NewWindow('confirmaRecalcularIndice','','500','240','scrollbars=yes,menubar=no,resizable=yes,toolbar=no,location=no,status=no');">Recalcular
						o &Iacute;ndice</a></td>
			</security:authorize>

			<td width="10%"><a href=""
				onclick="javascript:NewWindow('./users/passwd','','700','400','scrollbars=yes,menubar=no,resizable=yes,toolbar=no,location=no,status=no');return false">Alterar
					Senha</a></td>

			<security:authorize access="hasRole('PERM_CHANGE_DATABASE')">
				<td width="13%"><a href=""
					onclick="javascript:NewWindow('alterDB','','650','500','scrollbars=yes,menubar=no,resizable=yes,toolbar=no,location=no,status=no');return false">Alterar
						Base de Dados</a></td>
			</security:authorize>

			<c:url var="logoutUrl" value="/j_spring_security_logout" />

			<td class="sair" width=7%><a href="${logoutUrl}">Sair</a></td>
		</tr>
	</table>
	<div class="mensagemAdm">${mensagem}</div>
	<div class="versao">Vers&atilde;o ${versao}</div>
	<table class='repositorios-table' cellpadding=3>
		<tr>
			<caption>Lista de Reposit&oacute;rios Cadastrados na Federa&ccedil;&atilde;o</caption>
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

						<input type="button" class="botaoExcluir"
							title="Excluir reposit&oacute;rio" name="excluir" id="excluirRep"
							onclick="NewWindow('repositories/${rep.id}/delete','','500','240','scrollbars=yes,menubar=no,resizable=yes,toolbar=no,location=no,status=no');">
					</security:authorize> &nbsp; <input type="button" class="botaoEditar"
					title="Editar / Visualizar" name="editar" id="editarRep"
					onclick="NewWindow('repositories/${rep.id}','','750','total','scrollbars=yes,menubar=no,resizable=yes,toolbar=no,location=no,status=no');">
				</td>
				<td>${rep.nome}</td>
				<td>${rep.descricao}</td>
				<td><c:choose>
						<c:when test="${rep.isOutdated}">
							<div id='textResult${param.id}' class="textoErro">
								&nbsp;
								${rep.ultimaAtualizacaoFormatada}
								&nbsp;
								<security:authorize access="hasRole('PERM_UPDATE')">

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
								&nbsp;&nbsp; <a title='Atualizar agora'
									onclick="javaScript:atualizaRepAjax(${rep.id}, this.parentNode);">
									<img src='../imagens/sincronizar.png' border='0' width='24'
									height='24' alt='Atualizar' align='middle'>
								</a>
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
							onclick="NewWindow('${newRepositoryUrl}','Cadastro','750','total','scrollbars=yes,menubar=no,resizable=yes,toolbar=no,location=no,status=no');">
							<img
							src="<feb.spring:url value="/imagens/add-24x24.png" htmlEscape="true" />"
							border="0" width="24" height="24" alt="Visualizar" align="middle">
						</a>
					</security:authorize></td>
				<td colspan="2" class="left bold" style="font-size: 110%">
					&nbsp;&nbsp; <security:authorize
						access="hasRole('PERM_MANAGE_REP')">

						<a
							onclick="NewWindow('${newRepositoryUrl}','Cadastro','750','total','scrollbars=yes,menubar=no,resizable=yes,toolbar=no,location=no,status=no');">
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
	<!--Insere codigo que lista os padroes de metadados-->


	<table class='repositorios-table' id='padroes' cellpadding=3>
		<tr>
			<caption>Lista de Padr&atilde;o de Metadados</caption>
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
                                    <security:authorize	access="hasRole('PERM_MANAGE_METADATA')">
						<input type="button" class="botaoExcluir"
							title="Excluir padr&atilde;o de metadados" name="excluir"
							id="excluirPadrao"
							onclick="confirmaDelPadrao(${padraoMet.id},'msgerro','padroes',this.parentNode.parentNode.rowIndex);" />
					</security:authorize> 
                                                &nbsp;
                                        <input type="button" class="botaoEditar"
					title="Editar / Visualizar" name="editar" id="editarPadrao"
					onclick="NewWindow('./metadataStandard/${padraoMet.id}','editaPadrao','750','400','scrollbars=yes,menubar=no,resizable=yes,toolbar=no,location=no,status=no');" />
				</td>
				<td>${padraoMet.nome}</td>
				<td>${padraoMet.metadataPrefix}</td>
				<td>${padraoMet.namespace}</td>

			</tr>
		</c:forEach>

		<security:authorize access="hasRole('PERM_MANAGE_METADATA')">

			<tr class='center'>
				<td><a title="Adicionar novo padr&atilde;o de metadados"
					onclick="NewWindow('./padraoMetadados/addPadrao','addPadrao','750','650','scrollbars=yes,menubar=no,resizable=yes,toolbar=no,location=no,status=no');">
						<img
						src="<feb.spring:url value="/imagens/add-24x24.png" htmlEscape="true" />"
						border="0" width="24" height="24" alt="Visualizar" align="middle">
				</a></td>
				<td colspan="2" class="left bold" style="font-size: 110%">
					&nbsp;&nbsp; <a
					onclick="NewWindow('./padraoMetadados/addPadrao','Cadastro','750','650','scrollbars=yes,menubar=no,resizable=yes,toolbar=no,location=no,status=no');">
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
			<caption>Lista de Mapeamentos</caption>
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


				<td><security:authorize
						access="hasRole('PERM_MANAGE_METADATA')">
						<input type="button" class="botaoExcluir"
							title="Excluir Subfedera&ccedil;&atilde;o" name="excluir"
							id="excluirSubfed"
							onclick="NewWindow('./mapeamentos/${mapeamento.id}/delete','','500','200','scrollbars=yes,menubar=no,resizable=yes,toolbar=no,location=no,status=no');">
					</security:authorize> &nbsp; <input type="button" class="botaoEditar"
					title="Editar / Visualizar" name="editar" id="editarPadrao"
					onclick="NewWindow('./mapeamentos/${mapeamento.id}','editaMapeamento','750','total','scrollbars=yes,menubar=no,resizable=yes,toolbar=no,location=no,status=no');" />
				</td>
				<td>${mapeamento.name}</td>
				<td>${mapeamento.description}</td>
				<td>${mapeamento.padraoMetadados.name}</td>

			</tr>
		</c:forEach>

		<security:authorize access="hasRole('PERM_MANAGE_METADATA')">

			<tr class='center'>
				<td><a title="Adicionar novo padr&atilde;o de metadados"
					onclick="NewWindow('./mapeamentos/new','addPadrao','750','650','scrollbars=yes,menubar=no,resizable=yes,toolbar=no,location=no,status=no');">
						<img
						src="<feb.spring:url value="/imagens/add-24x24.png" htmlEscape="true" />"
						border="0" width="24" height="24" alt="Visualizar" align="middle">
				</a></td>
				<td colspan="2" class="left bold" style="font-size: 110%">
					&nbsp;&nbsp; <a
					onclick="NewWindow('./mapeamentos/new','Cadastro','750','650','scrollbars=yes,menubar=no,resizable=yes,toolbar=no,location=no,status=no');">
						Adicionar novo mapeamento </a>
				</td>
				<td><div id='msgerro' class='textoErro left'></div></td>
			</tr>
		</security:authorize>
	</table>
<!--Fim codigo que lista os mapeamentos-->


	<table class='repositorios-table' cellpadding=3>
		<tr>
			<caption>Lista de Subfedera&ccedil;&otilde;es cadastradas</caption>
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

						<input type="button" class="botaoExcluir"
							title="Excluir Subfedera&ccedil;&atilde;o" name="excluir"
							id="excluirSubfed"
							onclick="NewWindow('./federations/${subfed.id}/delete','','500','200','scrollbars=yes,menubar=no,resizable=yes,toolbar=no,location=no,status=no');">
					</security:authorize> &nbsp; <input type="button" class="botaoEditar"
					title="Editar / Visualizar" name="editar" id="editarSubfed"
					onclick="NewWindow('./federations/${subfed.id}','','750','560','scrollbars=yes,menubar=no,resizable=yes,toolbar=no,location=no,status=no');">

				</td>
				<td>${subfed.nome}</td>
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

						<a title="Adicionar nova subfedera&ccedil;&atilde;o"
							onclick="NewWindow('${newFederationUrl}','Cadastro','750','650','scrollbars=yes,menubar=no,resizable=yes,toolbar=no,location=no,status=no');">
							<img
							src="<feb.spring:url value="/imagens/add-24x24.png" htmlEscape="true" />"
							border="0" width="24" height="24" alt="Visualizar" align="middle">
						</a>
					</security:authorize></td>
				<td colspan="2" class="left bold" style="font-size: 110%">
					&nbsp;&nbsp; <security:authorize
						access="hasRole('PERM_MANAGE_REP')">

						<a
							onclick="NewWindow('${newFederationUrl}','Cadastro','750','650','scrollbars=yes,menubar=no,resizable=yes,toolbar=no,location=no,status=no');">
							Adicionar nova subfedera&ccedil;&atilde;o </a>
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


	<table class='repositorios-table' cellpadding=3>
		<tr>
			<caption>Lista de Usuários</caption>
		</tr>

		<tr style="background-color: #AEC9E3">
			<th width="10%">Opera&ccedil;&otilde;es</th>

			<th width="20%">Nome</th>
			<th width="50%">Descri&ccedil;&atilde;o</th>
			<th width="20%">Perfil</th>
		</tr>

		<c:forEach var="user" items="${users}" varStatus="status">
			<tr class="${status.index % 2 == 0? 'price-yes' : 'price-no'}">
				<td><security:authorize access="hasRole('PERM_MANAGE_USERS')">
						<input type="button" class="botaoExcluir"
							title="Excluir Subfedera&ccedil;&atilde;o" name="excluir"
							id="excluirSubfed"
							onclick="NewWindow('./users/${user.id}/delete','','500','200','scrollbars=yes,menubar=no,resizable=yes,toolbar=no,location=no,status=no');">
					</security:authorize> &nbsp; <input type="button" class="botaoEditar"
					title="Editar / Visualizar" name="editar" id="editarSubfed"
					onclick="NewWindow('./users/${user.id}','','750','560','scrollbars=yes,menubar=no,resizable=yes,toolbar=no,location=no,status=no');">

				</td>
				<td>${user.username}</td>
				<td>${user.description}</td>
				<td>${user.role}</td>

			</tr>
		</c:forEach>

		<security:authorize access="hasRole('PERM_MANAGE_USERS')">
			<tr class='center'>
				<td><a title="Adicionar novo usuário"
					onclick="NewWindow('./users/new','Cadastro','750','650','scrollbars=yes,menubar=no,resizable=yes,toolbar=no,location=no,status=no');">
						<img
						src="<feb.spring:url value="/imagens/add-24x24.png" htmlEscape="true" />"
						border="0" width="24" height="24" alt="Visualizar" align="middle">
				</a></td>
				<td colspan="2" class="left bold" style="font-size: 110%">
					&nbsp;&nbsp; <a
					onclick="NewWindow('./users/new','Cadastro','750','650','scrollbars=yes,menubar=no,resizable=yes,toolbar=no,location=no,status=no');">
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

	<%@include file="../googleAnalytics"%>
</BODY>
</html>
