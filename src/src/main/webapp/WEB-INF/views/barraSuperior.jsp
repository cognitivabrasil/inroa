<%-- 
    Document   : barraSuperior
    Created on : 23/07/2012, 14:21:46
    Author     : marcos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url var="adm" value="/admin/" />
<c:url var="confederacao" value="http://feb.ufrgs.br/feb/" />
<c:url var="barraScript" value="/scripts/barraSuperior.js" />

<script language="javascript" type="text/javascript" src="${barraScript}"></script>

<div class="barra-superior">
    <span id="confederacao"><a href="${confederacao}">Confedera&ccedil;&atilde;o</a></span>
    <span><a href="${adm}">Ferramenta Administrativa</a></span>
    <span><a href="http://feb.ufrgs.br">Portal do projeto</a></span>
</div>
<div class="copyRight">
    Suporte técnico <a href="http://cognitivabrasil.com.br/">Cognitiva Brasil</a>: <a href="mailto:contato@cognitivabrasil.com.br">contato@cognitivabrasil.com.br</a>
    |
    Desenvolvido em parceria com: UFRGS e RNP
    
</div>