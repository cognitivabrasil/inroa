
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url var="logoReduzido" value="/imagens/Logo FEB_reduzido.png" />
<c:url var="index" value="/" />
<c:url value="/css/header.css" var="app_css_url" />
<link rel="stylesheet" type="text/css" media="screen" href="${app_css_url}" />
<div class="logoBusca">
    <a href="${index}">
        <img src="${logoReduzido}" alt="Logo FEB_reduzido" 
             <c:choose>
                 <c:when test="${empty param.tamanho}">class="logo"</c:when>
                 <c:otherwise>width="${param.tamanho}"</c:otherwise>
             </c:choose> 
             />
    </a>
</div>    
<div class="clear"> </div>

<div class="espacoPequeno">&nbsp;</div>
<div class="subTituloBusca">&nbsp;${param.titulo}</div>
