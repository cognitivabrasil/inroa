<%-- 
    Document   : logando
    Created on : 21/08/2009, 11:22:25
    Author     : Marcos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="login.Usuario"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FEB - Ferramenta Administrativa</title>
        <link rel="StyleSheet" href="css/padrao.css" type="text/css">
    </head>
    <body>
<%
//recupera login
            String login = request.getParameter("login").trim();
//recupera senha
            String pass = request.getParameter("senha").trim();
            
            Usuario teste = new Usuario();
            boolean result = teste.verificaUsuario(login, pass);
            if (result) {
                session.setAttribute("usuario", login); //armazena na sessao o login
                session.setMaxInactiveInterval(900); //seta o tempo de validade da session
                out.print("<script type='text/javascript'>window.location=\"adm.jsp\";</script>");
            } //gravar na sessao
            else {
                out.print("<script type='text/javascript'>alert('Usuário e/ou senha incorretos!');" +
                        "javascript:history.go(-1);</script>");
            }
%>


    </body>
</html>
