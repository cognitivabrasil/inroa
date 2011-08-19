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
                var repositorios = document.getElementsByName("replocal");
                var idSubfed1 = document.getElementsByName("subfed");
                var idSubRep1 = document.getElementsByName("subrep");

                var idRepLocal = "";
                var idSubfed = "";
                var idSubRep = "";                

                //Contatenar resultados dos repositorios locais
                for (var i=0;i<repositorios.length;i = i+1){
                    if (repositorios[i].checked == true){
                        idRepLocal +="&replocal="+repositorios[i].value;
                    }
                }
                //concatenar resultados das subfederacoes
                for (var i=0;i<idSubfed1.length;i = i+1){
                    if (idSubfed1[i].checked == true){
                        idSubfed +="&subfed="+idSubfed1[i].value;
                    }
                }
                //concatenar resultados dos repositorios das subfederacoes
                for (var i=0;i<idSubRep1.length;i = i+1){
                    if (idSubRep1[i].checked == true){
                        idSubRep +="&subrep="+idSubRep1[i].value;
                    }
                }
                
                window.location='rss.jsp?key='+consulta+idRepLocal+idSubfed+idSubRep;
            }
            function ocultar(id, idLink){
                var valor = document.getElementById(idLink);
                valor.innerHTML="+";
                valor.onclick=function(){tornarVisivel(idLink,"listaRep", "Interno")};
                var obj = document.getElementById(id);
                obj.className='hidden';
            }
            function tornarVisivel(idLink, id, css){
                var valor = document.getElementById(idLink);
                valor.innerHTML="-";
                valor.onclick=function(){ocultar("listaRep", idLink)};
                var obj = document.getElementById(id);
                obj.className=css;
            }

        </script>

    </head>

    <body id="bodyMenor">
        <!-- incluir um arquivo %@ include file="top.html" %> -->
        <div id="page">

            <div class="logoBusca"><img src="imagens/Logo FEB_reduzido.png" width="11%" height="10%" alt="Logo FEB_reduzido"/></div>


            <div class="clear"> </div>

            <div class="EspacoPequeno">&nbsp;</div>
            <div class="subTituloBusca">&nbsp;Consulta de Objetos Educacionais</div>
            <div class="linkCantoDireito"><a href="./adm.jsp"><img src="imagens/ferramenta_32x32.png" width="90%" alt="Ferramenta Administrativa"></a></div>
            <div class="Espaco">&nbsp;</div>
            <form name="consulta" action="consulta.jsp" method="GET">

                <div class="LinhaEntrada">
                    <div class="EspacoAntes">&nbsp;</div>
                    <div class="Label">
                        Servidor:
                    </div>

                    <%
                                Statement stm2 = con.createStatement();
                                //Carrega do banco de dados os repositorios cadastrados
                                ResultSet res = stm.executeQuery("SELECT nome, id FROM repositorios ORDER BY nome ASC");
                                //int i = 0;
                                while (res.next()) {
                                    if (!res.getString("nome").equalsIgnoreCase("todos")) {
                                        if (res.isFirst()) {
                                            out.println("<div class='ValueIndex'>- Reposit&oacute;rios</div>");
                                        }

                                        out.println("<div class='ValueIndex'>&nbsp;&nbsp;&nbsp;"
                                                + "<input value='" + res.getString("id") + "' type=checkbox id=\"" + res.getString("id") + "\""
                                                + " name=\"replocal\""
                                                + ">" + res.getString("nome").toUpperCase()
                                                + "</div>");
                                    }
                                    // i++;

                                }

                                ResultSet rsSub = stm.executeQuery("SELECT nome, id FROM dados_subfederacoes ORDER BY nome ASC");
                                boolean testeSubfederacao = true;
                                while (rsSub.next()) {
                                    if (testeSubfederacao) {
                                        if (!rsSub.getString("nome").equalsIgnoreCase("local")) {
                                            out.println("<div class='ValueIndex'>- Subfedera&ccedil;&otilde;es</div>");
                                            testeSubfederacao = false;
                                        }
                                    }
                                    if (!rsSub.getString("nome").equalsIgnoreCase("local")) {

                                        out.println("<div class='ValueIndex'>&nbsp;&nbsp;&nbsp;<a id='link' class='linkRepSubfeb' onclick='tornarVisivel(\"link\",\"listaRep\", \"Interno\");'>+</a><input value='" + rsSub.getString("id") + "' type=checkbox id=\"" + rsSub.getString("id") + "\""
                                                + " name=\"subfed\""
                                                + ">" + rsSub.getString("nome").toUpperCase());
                                        out.print("<div id='listaRep' class='hidden'>");
                                        String buscaRepSubfed = "SELECT rsf.nome, rsf.id FROM repositorios_subfed rsf WHERE id_subfed=" + rsSub.getString("id");
                                        ResultSet rsRepSub = stm2.executeQuery(buscaRepSubfed);
                                        while (rsRepSub.next()) {
                                            out.println("<div class='Int'>&nbsp;&nbsp;&nbsp;<input value='" + rsRepSub.getString("id") + "' type=checkbox id=\"" + rsRepSub.getString("id") + "\""
                                                    + " name=\"subrep\""
                                                    + ">" + rsRepSub.getString("nome").toUpperCase()
                                                    + "</div>");
                                        }
                                        out.println("</div></div>");
                                    }
                                }
                    %>

                </div>

                <div class="clear"> </div>
                <div id="modificavel">
                    <div class="LinhaEntrada">
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
                        <input class="BOTAO" type="submit" value="Consultar" ALIGN="CENTER"/>
                    </div>
                </div>
            </form>
            <div ALIGN="CENTER">
                <a href="./index.jsp">Ocultar Repositórios</a>
            </div>
        </div>
        <div>
            <div class="copyRight">Desenvolvido em parceria com: UFRGS e RNP</div>
            <div  class="rss">
                <a class="linkRSS" onclick= "geraRss()" title="RSS da busca"><img src="imagens/rss_300x300.png" width="3%" alt="rsslogo" onclick= "geraRss()"/> </a>
            </div>
        </div>
        <%@include file="googleAnalytics"%>
    </body>
</html>
<%    con.close(); //fechar conexao com a base de dados
%>