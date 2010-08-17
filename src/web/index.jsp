<%--
    Document   : index
    Created on : 25/06/2009, 18:26:51
    Author     : Marcos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.sql.*"%>
<%@include file="conexaoBD.jsp"%>
<%
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>GT-FEB – Federa&ccedil;&atilde;o de Reposit&oacute;rios Educa Brasil</title>
        <link rel="StyleSheet" href="css/padrao.css" type="text/css">
        <link href="imagens/favicon.ico" rel="shortcut icon" type="image/x-icon" />

    </head>

    <body id="bodyMenor">
        <!-- incluir um arquivo %@ include file="top.html" %> -->
        <div id="page">
            <div class="linkCantoDireito"><a href="./adm.jsp">Ferramenta Administrativa</a></div>
            <div class="EspacoAntes">&nbsp;</div>
            <div class="subTitulo-center">&nbsp;CONSULTA DE OBJETOS EDUCACIONAIS</div>

            <div class="logoBusca"><img src="imagens/Logo FEB_reduzido.png" width="11%" height="10%" alt="Logo FEB_reduzido"/></div>
            
            <div class="EspacoPequeno">&nbsp;</div>
            <div class="subTituloBusca">&nbsp;Consulta de Objetos Educacionais</div>
            <div class="linkCantoDireito"><a href="./adm.jsp">Ferramenta Administrativa</a></div>
            <div class="Espaco">&nbsp;</div>
            <form name="consulta" action="consulta.jsp" method="post">

                <div class="LinhaEntrada">
                    <div class="EspacoAntes">&nbsp;</div>
                    <div class="Label">
                        Servidor:
                    </div>
                    <div class="Value">
                        <select name="repositorio" onFocus="this.className='inputSelecionado'" onBlur="this.className=''">
                            <option selected value="0">Federa&ccedil;&atilde;o
                                <%
            //Carrega do banco de dados os repositorios cadastrados
            ResultSet res = stm.executeQuery("SELECT nome, id FROM repositorios ORDER BY nome ASC");
            while (res.next()) {
                if (!res.getString("nome").equalsIgnoreCase("todos")) {
                    out.println("<option value=" + res.getInt("id") + ">" + res.getString("nome").toUpperCase());
                }

            }
                                %>

                        </select>
                    </div>
                </div>
                <div class="clear"> </div>
                <div id="modificavel">
                    <div class="LinhaEntrada">
                        <div class="Label">
                            Texto para a busca:
                        </div>
                        <div class="Value">
                            <input type="text" name="key" value="" onFocus="this.className='inputSelecionado'" onBlur="this.className=''"/>
                        </div>
                    </div>
                </div>
                <div class="clear"> </div>
                <div class="LinhaEntrada">
                    <div class="Buttons">
                        <input class="BOTAO" type="submit" value="Consultar"/>
                    </div>
                </div>
            </form>
        </div>
        <%@include file="googleAnalytics"%>
    </body>
</html>
<%
            con.close(); //fechar conexao com mysql
%>