<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Infraestrutura Nacional de Repositórios de Objetos de Aprendizagem</title>

<!--favicon-->
<c:url value="/imagens/favicon.ico" var="favicon" />
<link rel="shortcut icon" href="${favicon}" />

<!--bootstrap-->
<c:url value="/scripts/vendor/bootstrap-3.1.1-dist/css/bootstrap.min.css" var="bootstrap_css" />
<link rel="stylesheet" type="text/css" media="screen" href="${bootstrap_css}" />

<!--main css-->
<c:url value="/css/main.css" var="app_css_url" />
<link rel="stylesheet" type="text/css" media="screen" href="${app_css_url}" />

<!-- Custom Fonts -->
<c:url var="cssFontawsome" value="/css/vendor/font-awesome-4.2.0/css/font-awesome.min.css" />
<link href="${cssFontawsome}" rel="stylesheet" type="text/css" />