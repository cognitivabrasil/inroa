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

<c:url var="codeMirrorJs" value="/scripts/codemirror/lib/codemirror.js" />
<script type="text/javascript" src="${codeMirrorJs}"></script>

<c:url var="codeMirrorCss"
	value="/scripts/codemirror/lib/codemirror.css" />
<link rel="stylesheet" href="${codeMirrorCss }">

<c:url var="codeMirrorXml" value="/scripts/codemirror/mode/xml/xml.js" />
<script type="text/javascript" src="${codeMirrorXml }"></script>


</head>
<body>

	<div id="page">

		<div class="subTitulo-center">Cadastrar/Editar mapeamento</div>
		<div class="EspacoAntes">&nbsp;</div>
		<form:form method="post" modelAttribute="mapeamento"
			acceptCharset="utf-8" onsubmit="return myForm.Apply('MensagemErro')">

			<div class="TextoDivAlerta" id="MensagemErro">
				<!--Aqui o script colocara a mensagem de erro, se ocorrer-->
				<c:out value="${erro}" />
			</div>
			<div class="subtitle">Informa&ccedil;&otilde;es gerais sobre o
				reposit&oacute;rio</div>
			<div class="LinhaEntrada">
				<form:errors path="name" cssClass="ValueErro" />
				<div class="Label">Nome:</div>
				<div class="Value">
					<form:input path="name" maxlength="45"
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
				<form:errors path="padraoMetadados" cssClass="ValueErro" />
				<div class="Label">Padrão de metadados:</div>
				<div class="Value">
					<form:select path="padraoMetadados">
						<form:option value="-1" label="--- Selecione ---" />
						<form:options items="${metadataList}" />
					</form:select>
				</div>

				<div class="Espaco">&nbsp;</div>
			</div>

			<div class="subtitle">XSLT</div>

			<form:textarea id="code" path="xslt" />
			<script>
				var editor = CodeMirror.fromTextArea(document
						.getElementById("code"), {
					mode : {
						name : "xml",
						alignCDATA : true
					},
					lineNumbers : true
				});
			</script>

			<div class="subtitle">Xml de Exemplo</div>
			<form:textarea id="xml" path="xml" />
			<script>
				var editor = CodeMirror.fromTextArea(document
						.getElementById("xml"), {
					mode : {
						name : "xml",
						alignCDATA : true
					},
					lineNumbers : true
				});
			</script>
			
			<div class="subtitle">Xml OBAA transformado</div>
			<form:textarea id="xmlObaa" path="xmlObaa" />
			<script>
				var editor = CodeMirror.fromTextArea(document
						.getElementById("xmlObaa"), {
					mode : {
						name : "xml",
						alignCDATA : true
					},
					lineNumbers : true
				});
			</script>



			<div class="LinhaEntrada">
				<div class="Buttons">
					<input type="reset" value="Limpar" class="CancelButton"
						onclick="javascript:window.location.reload();" /> <input
						id="cancelar" onclick="javascript:window.close();"
						value="Cancelar" type="button" class="CancelButton" /> <input
						type="submit" value="Visualizar" name="preview" />
						 <input
						type="submit" value="Salvar" name="submit" />
				</div>
			</div>

		</form:form>

	</div>
	<%@include file="../../googleAnalytics"%>
</body>

</html>