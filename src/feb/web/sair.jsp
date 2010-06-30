<%-- 
    Document   : sair
    Created on : 09/09/2009, 16:52:07
    Author     : Marcos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%
//session.invalidate();
session.removeAttribute("usuario");
out.print("<script type='text/javascript'>window.location=\"index.jsp\";</script>");
%>