<%-- 
    Document   : removerFederacao
    Created on : 14/09/2009, 12:01:56
    Author     : Marcos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
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
		<div class="subTitulo-center">&nbsp;Ferramenta para
			remo&ccedil;&atilde;o de usuários</div>
		<div class="EspacoAntes">&nbsp;</div>

		<form name="removerFederacao" method="post">
			<div class="LinhaEntrada">
				<div class="Tab">
					Deseja realmente remover o usuário <b>${user.username}</b>
					?
				</div>
				<BR>
			</div>
			<div class="LinhaEntrada">
				<div class="Buttons">
					<input type="submit" value="&nbsp;Sim&nbsp;" name="submit" /> <input
						id="cancelar" onclick="javascript:window.close();"
						value="&nbsp;N&atilde;o&nbsp;" type="button" class="CancelButton" />
				</div>
			</div>
		</form>
	</div>
	<%@include file="../../googleAnalytics"%>
</body>
</html>