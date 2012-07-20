<%--
    Document   : cadastraRepositorio
    Created on : 03/08/2009, 16:12:33
    Author     : Marcos

Primeira etapa do cadastro de um repositorio
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>FEB - Ferramenta Administrativa</title>

<c:url var="favicon" value="/imagens/favicon.ico" />
<c:url var="css" value="/css/padrao.css" />
<c:url var="validateJs" value="/scripts/validatejs.js" />
<c:url var="funcoesJs" value="/scripts/funcoes.js" />

<c:url var="funcoesMapeamentoJs" value="/scripts/funcoesMapeamento.js" />

<link href="${favicon}" rel="shortcut icon" type="image/x-icon" />
<link rel="StyleSheet" href="${css }" type="text/css" />
<script type="text/javascript" src="${validateJs }"></script>
<script type="text/javascript" src="${funcoesJs }"></script>


</head>
<body>

	<div id="page">

		<div class="subTitulo-center">&nbsp;Entre com as
			informa&ccedil;&otilde;es para cadastrar um novo reposit&oacute;rio</div>
		<div class="EspacoAntes">&nbsp;</div>
		<form:form method="post" modelAttribute="userModel"
			acceptCharset="utf-8" onsubmit="return myForm.Apply('MensagemErro')">

			<div class="TextoDivAlerta" id="MensagemErro">
				<!--Aqui o script colocara a mensagem de erro, se ocorrer-->
				<c:out value="${erro}" />
			</div>
			<div class="subtitle">Informa&ccedil;&otilde;es gerais sobre o
				reposit&oacute;rio</div>
			<div class="LinhaEntrada">
				<form:errors path="username" cssClass="ValueErro" />
				<div class="Label">Nome:</div>
				<div class="Value">
					<form:input path="username" maxlength="45"
						onFocus="this.className='inputSelecionado'"
						onBlur="this.className=''" />
				</div>
			</div>
			<div class="LinhaEntrada">
				<form:errors path="description" cssClass="ValueErro" />
				<div class="Label">Descri&ccedil;&atilde;o:</div>
				<div class="Value">
					<form:input path="description" maxlength="55"
						onFocus="this.className='inputSelecionado'"
						onBlur="this.className=''" />
				</div>
			</div>

			<div class="LinhaEntrada">
				<form:errors path="role" cssClass="ValueErro" />
				<div class="Label">Tipo de usuário:</div>
				<div class="Value">
					<form:select path="role">
   <form:option value="NONE" label="--- Selecione ---"/>
   <form:options items="${roleList}" />
</form:select>
				</div>

				<div class="Espaco">&nbsp;</div>
			</div>

			<div class="LinhaEntrada">
				<form:errors path="password" cssClass="ValueErro" />
				<div class="Label">Senha:</div>
				<div class="Value">
					<form:password path="password" maxlength="20" />
				</div>

				<div class="Espaco">&nbsp;</div>
			</div>
			<div class="LinhaEntrada">
				<form:errors path="password" cssClass="ValueErro" />
				<div class="Label">Confirmar Senha:</div>
				<div class="Value">
					<form:password path="confirmPassword" maxlength="20" />
				</div>

				<div class="Espaco">&nbsp;</div>
			</div>

			<div class="LinhaEntrada">
				<div class="Buttons">
					<input type="reset" value="Limpar" class="CancelButton"
						onclick="javascript:window.location.reload();" /> <input
						id="cancelar" onclick="javascript:window.close();"
						value="Cancelar" type="button" class="CancelButton" /> <input
						type="submit" value="Avan&ccedil;ar >" name="submit" />
				</div>
			</div>

		</form:form>

	</div>
	<%@include file="../../googleAnalytics"%>
</body>
</html>