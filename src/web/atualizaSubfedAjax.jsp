<%-- 
    Document   : atualizaSubfedAjax
    Created on : 01/09/2011, 11:00:27
    Author     : Marcos
--%>

<%@page import="robo.atualiza.SubFederacao"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%
System.out.println("FEB: Solicitacao de atualizacao pela Ferramenta Administrativa...");
String id = request.getParameter("id");
int idSub = Integer.parseInt(id);
boolean resultado = false;
SubFederacao Sf = new SubFederacao();
resultado = Sf.atualizaSubfedAdm(idSub);
if (resultado)
    out.print("1");
else
    out.print("0");
%>