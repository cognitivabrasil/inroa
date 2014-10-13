<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!--Para html 5-->
<meta charset="utf-8">
<!--Para html 4.0.1-->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Infraestrutura Nacional de Repositórios de Objetos de Aprendizagem</title>

<!--favicon-->
<c:url value="/imagens/favicon.ico" var="favicon" />
<link rel="shortcut icon" type="image/x-icon" href="${favicon}" />

<!-- Tela de inicio para webclip apple -->
<link rel="apple-touch-startup-image" href="${images}/inroa.png" />
<!-- Favicon Apple-->
<link rel="apple-touch-icon" href="${images}/favicon.ico">

<!--bootstrap-->
<c:url value="/scripts/vendor/bootstrap-3.1.1-dist/css/bootstrap.min.css" var="bootstrap_css" />
<link rel="stylesheet" type="text/css" media="screen" href="${bootstrap_css}" />

<!-- Custom Fonts -->
<c:url var="fontawsome_css" value="/css/vendor/font-awesome-4.2.0/css/font-awesome.min.css" />
<link href="${fontawsome_css}" rel="stylesheet" type="text/css" />
<c:url var="fonts_css" value="/css/fonts.css" />
<link rel="StyleSheet" href="${fonts_css}" type="text/css"> 

<!--main css-->
<c:url value="/css/main.css" var="app_css_url" />
<link rel="stylesheet" type="text/css" media="screen" href="${app_css_url}" />


<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->