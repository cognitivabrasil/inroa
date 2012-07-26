
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>



<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>FEB - Página de Erro</title>
<c:url var="css" value="/css/padrao.css" />
<c:url var="favicon" value="/imagens/favicon.ico" />
<c:url var="funcoes" value="/scripts/funcoes.js" />
<link rel="StyleSheet" href="${css}" type="text/css">
<link href="${favicon }" rel="shortcut icon" type="image/x-icon" />
<script language="JavaScript" type="text/javascript" src="${funcoes}"></script>

 


</head>

<body>

	<div id="page">

		<div class="subTitulo-center">Erro!</div>
		
		<div class="error">${message}</div>
		
		<div class="voltar"><a href="javascript: history.go(-1)">Voltar</a></div>

	</div>


</body>


</html>