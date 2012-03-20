<%-- 
    Document   : testeForm
    Created on : 20/03/2012, 19:45:30
    Author     : marcos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
    <head>
        <title>Reservation Form</title>
        <style>
            .error {
                color: #ff0000;
                font-weight: bold;
            }
        </style>
    </head>
    <body>
    <form:form method="post" modelAttribute="subDAO" acceptCharset="utf-8">
        <form:errors path="*" cssClass="error" />
        <table>
            <tr>
                <td>Court Name</td>
                <td><form:input path="nome" /></td>
            <td><form:errors path="nome" cssClass="error" /></td>
            </tr>
            <tr>
                <td>Date</td>
                <td><form:input path="descricao" /></td>
            <td><form:errors path="descricao" cssClass="error" /></td>
            </tr>
            <tr>
                <td>Hour</td>
                <td><form:input path="url" /></td>
            <td><form:errors path="url" cssClass="error" /></td>
            </tr>
            <tr>
                <td colspan="3">
                    <input type="hidden" name="submitted" value="true"/>
                    <input type="submit" /></td>
            </tr>
        </table>            
    </form:form>
</body>
</html>
