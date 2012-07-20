<%-- 
    Document   : tagCloud
    Created on : 20/07/2012, 15:48:11
    Author     : cei
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="feb.spring" uri="http://www.springframework.org/tags"%>


<script language="javascript" type="text/javascript" src='<feb.spring:url value="/scripts/jquery-1.7.2.js" htmlEscape="true" />'></script>
<script language="javascript" type="text/javascript" src='<feb.spring:url value="/scripts/jquery.tagcloud.js" htmlEscape="true" />'></script>
<script language="javascript" type="text/javascript" src='<feb.spring:url value="/scripts/tagCloud.js" htmlEscape="true" />'></script>
<c:url var="busca" value="/consulta" />




<div id="tagcloud">
    <c:forEach var="consulta" items="${termos}" varStatus="key">
        <a href="${busca}?consulta=${consulta.key}" rel="${consulta.value}">${consulta.key}</a>

    </c:forEach>

</div>
