<%--
    Document   : testaSessao
    Created on : 09/09/2009, 15:55:28
    Author     : Marcos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%
    String usr = (String)session.getAttribute("usuario");
    if (usr==null){
        out.print("<script type='text/javascript'>window.location=\"/feb/login.html\";</script>");
    }
%>