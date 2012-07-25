<%-- 
    Document   : tagCloud
    Created on : 20/07/2012, 15:48:11
    Author     : cei
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<script language="javascript" type="text/javascript" src='<spring:url value="/scripts/jquery-1.7.2.js" htmlEscape="true" />'></script>
<script language="javascript" type="text/javascript" src='<spring:url value="/scripts/jquery.tagcloud.js" htmlEscape="true" />'></script>
<script language="javascript" type="text/javascript" src='<spring:url value="/scripts/tagCloud.js" htmlEscape="true" />'></script>





<div id="tagcloud">
    <c:forEach var="consulta" items="${termos}" varStatus="key">
    <spring:url var="buscaConsulta" value="/consulta?consulta=${consulta.key}" htmlEscape="true"/>
        <a href="${buscaConsulta}" rel="${consulta.value}"><c:out value="${consulta.key}"/></a>

    </c:forEach>

</div>
