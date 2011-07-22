<%-- 
    Document   : testaSessao
    Created on : 09/09/2009, 15:55:28
    Author     : Marcos
--%>

<script type="text/javascript" src="/feb/scripts/funcoes.js"></script>
<%
    String usr = (String)session.getAttribute("usuario");
    if (usr==null){
        out.print("<script type='text/javascript'>fechaRecarrega();</script>");
        out.print("<script type='text/javascript'>window.location=\"/feb/login.html\";</script>");
    }
%>