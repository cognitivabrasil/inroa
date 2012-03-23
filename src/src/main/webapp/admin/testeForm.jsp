<%-- 
    Document   : testeForm
    Created on : 20/03/2012, 19:45:30
    Author     : marcos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
    <head>
        <title>Teste</title>
        <link rel="StyleSheet" href="../css/padrao.css" type="text/css">
    </head>
    <body>
    <form:form method="post" modelAttribute="subDAO" acceptCharset="utf-8">
        <form:errors path="*" cssClass="error" />
        <table>
            <tr>
                <td>Nome: </td>
                <td><form:input path="nome" /></td>
            <td><form:errors path="nome" cssClass="error" /></td>
            </tr>
            <tr>
                <td>Descrição: </td>
                <td><form:input path="descricao" /></td>
            <td><form:errors path="descricao" cssClass="error" /></td>
            </tr>
            <tr>
                <td>url: </td>
                <td><form:input path="url" /></td>
            <td><form:errors path="url" cssClass="error" /></td>
            </tr>
            <tr>
                <td colspan="3">
                    <input type="hidden" name="submitted" value="true"/>
                    <input type="submit" name="subimit" value="true" /></td>
            </tr>
        </table>            
    </form:form>
</body>
</html>
