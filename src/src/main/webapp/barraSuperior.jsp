<%-- 
    Document   : barraSuperior
    Created on : 23/07/2012, 14:21:46
    Author     : marcos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url var="adm" value="/admin" />

<div class="barra-superior">
    <span><a href="http://feb.ufrgs.br/feb">Confederação</a></span>
    <span><a href="${adm}">Ferramenta Administrativa</a></span>
    <span><a href="http://feb.ufrgs.br">Portal do projeto</a></span>
</div>
<div class="copyRight">Desenvolvido em parceria com: UFRGS e RNP</div>