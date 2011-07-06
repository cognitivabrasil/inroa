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

        <script language="JavaScript" type="text/javascript">
            function geraRss() {
                var consulta = document.getElementById("key").value;
                var repositorio = document.getElementById("repositorio").value;

                window.location='rss.jsp?search='+consulta+'&idRep='+repositorio;

            }
        </script>

    </head>      

    <body id="bodyMenor">
        <!-- incluir um arquivo %@ include file="top.html" %> -->
        <div id="page">

                <div class="logoBusca"><img src="imagens/Logo FEB_reduzido.png" width="11%" height="11%" alt="Logo FEB_reduzido"/></div>


            <div class="clear"> </div>

            <div class="EspacoPequeno">&nbsp;</div>
            <div class="subTituloBusca">&nbsp;Consulta de Objetos Educacionais</div>
<div class="linkCantoDireito"><a href="./adm.jsp">Ferramenta Administrativa</a></div>
            <div class="Espaco">&nbsp;</div>
            <form name="consulta" action="consulta.jsp" method="POST">
<!--
                <div class="LinhaEntrada">
                    <div class="EspacoAntes">&nbsp;</div>
                    <div class="Label">
                        Servidor:
                    </div>
                    <div class="Value">
                        <select name="repositorio" id="repositorio" onFocus="this.className='inputSelecionado'" onBlur="this.className=''">
                            <option selected value="0">Federa&ccedil;&atilde;o
                                <%
                //Carrega do banco de dados os repositorios cadastrados
                ResultSet res = stm.executeQuery("SELECT nome, id FROM repositorios ORDER BY nome ASC");
                while (res.next()) {
                    if(res.isFirst()){
                        out.println("<option value='' disabled>- Reposit&oacute;rios");
                    }
                    if (!res.getString("nome").equalsIgnoreCase("todos")) {
                        out.println("<option value=rep;" + res.getInt("id") + ">" + res.getString("nome").toUpperCase());
                    }

                }
                ResultSet rsSub = stm.executeQuery("SELECT nome, id FROM dados_subfederacoes ORDER BY nome ASC");

                while (rsSub.next()) {
                    if(rsSub.isFirst()){
                        out.println("<option value='' disabled>- Subfedera&ccedil;&otilde;es");
                    }
                    if (!rsSub.getString("nome").equalsIgnoreCase("local")) {
                        out.println("<option value=subFed;" + rsSub.getInt("id") + ">" + rsSub.getString("nome").toUpperCase());
                    }
                }
                                %>

                        </select>
                    </div>
                </div> -->
<div class="clear"> </div>
                <div id="modificavel">
                    <div class="LinhaEntrada">
                        <div class="EspacoAntes">&nbsp;</div>
                        <div class="Label">
                            Texto para a busca:
                        </div>
                        <div class="Value">
                            <input type="text" name="key" id="key" value="" onFocus="this.className='inputSelecionado'" onBlur="this.className=''"/>
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
            <div ALIGN="CENTER">
                <a href="./Index3.jsp">Selecionar Repositórios</a>
            </div>
                    <div  ALIGN="RIGHT">
                        <a class="linkRSS" onclick= "geraRss()"><img src="imagens/rss_300x300.png" width="3%" height="3%" alt="rsslogo" onclick= "geraRss()"/> <b>Gerar RSS</b></a>
                        <!--<img src="imagens/gerarRss.png" width="17%" height="17%" alt="rsslogo" onclick= "geraRss()"/>-->
                    </div>
        </div>
        <%@include file="googleAnalytics"%>
    </body>
</html>
<%
            con.close(); //fechar conexao
%>