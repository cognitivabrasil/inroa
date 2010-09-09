<%-- 
    Document   : grava_usuario.jsp
    Created on : 09/09/2010, 13:01:46
    Author     : Marcos

OBS: O que tiver de saida (impressão na tela) aqui, será o retorno para o Ajax.
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<% // Importação de Classes Java %>



<% // Variáveis da conexão com o banco de dados %>


<%




//Parâmetros recuperados do Ajax
String origem = request.getParameter("origem");
String tipo = request.getParameter("tipo");
String idPadrao = request.getParameter("padrao");

if(tipo.equalsIgnoreCase("combo")){
    
    
    //consultar o mysql todos os atributos


    out.print("combo!! Padrao= "+idPadrao);
}else
    //Este comando devolverá "Dados inseridos com Sucesso para" o Ajax.
    out.println("Dados inseridos com Sucesso!!! Origem="+origem+" tipo="+tipo);

%>