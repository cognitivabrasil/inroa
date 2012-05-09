<%-- 
    Document   : removerRepositorio
    Created on : 17/08/2009, 17:18:39
    Author     : Marcos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>FEB - Ferramenta Administrativa</title>
<c:url var="favicon" value="/imagens/favicon.ico" />
<c:url var="css" value="/css/padrao.css" />
<c:url var="validateJs" value="/scripts/validatejs.js" />
<c:url var="funcoesJs" value="/scripts/funcoes.js" />
<link rel="StyleSheet" media="screen" href="${css}"
	type="text/css" />
<script type="text/javascript" src="${funcoesJs}"></script>
<link href="${favicon}" rel="shortcut icon"
	type="image/x-icon" />
</head>
<body>
	<div id="page">
		<div class="subTitulo-center">&nbsp;Ferramenta para
			remo&ccedil;&atilde;o de reposit&oacute;rios</div>
		<div class="EspacoAntes">&nbsp;</div>

		<form method="POST">
			<div class="LinhaEntrada">
				<div class="Tab">
					Deseja realmente remover o reposit&oacute;rio <b>${rep.nome}</b> ?
					<div class="info">Atenção: Esta opera&ccedil;&atilde;o pode
						levar alguns minutos!</div>
				</div>
			</div>
			<input type="hidden" name="submitted" value="true" /> <input
				type="hidden" name="id" value="${rep.id}">
			<div class="LinhaEntrada">
				<div class="Buttons">
					<input type="submit" value="&nbsp;Sim&nbsp;" name="submit" /> <input
						id="cancelar" onclick="javascript:window.close();"
						value="&nbsp;N&atilde;o&nbsp;" type="button" class="CancelButton" />
				</div>
			</div>
		</form>
	</div>
	<%@include file="../googleAnalytics"%>
</body>
</html>