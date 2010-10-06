<%-- 
    Document   : exibeFederacao
    Created on : 14/09/2009, 12:16:29
    Author     : Marcos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="testaSessaoNovaJanela.jsp"%>
<%@include file="conexaoBD.jsp"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FEB - Ferramenta Administrativa</title>
        <link rel="StyleSheet" href="css/padrao.css" type="text/css">
        <link href="imagens/favicon.ico" rel="shortcut icon" type="image/x-icon" />
    </head>

    <body>
        <div id="page">

            <div class="subTitulo-center">&nbsp;Edi&ccedil;&atilde;o / Visualiza&ccedil;&atilde;o de reposit&oacute;rios cadastrados</div>

            <%
        String id = request.getParameter("id");

        String sql = "SELECT * " +
                    " FROM dados_subfederacoes l" +
                    " WHERE l.id=" + id + " " +
                    "ORDER BY l.nome ASC;";

        ResultSet res = stm.executeQuery(sql);
        res.next();
            %>

<!--Informações Gerais-->
            <div class="subtitulo">Informa&ccedil;&otilde;es sobre as subfedera&ccedil;&otilde;es <%=res.getString("nome")%></div>
            <div class="editar"><a href="./editarFederacao.jsp?id=<%=id%>">Editar</a></div>

            <div class="LinhaEntrada">
                <div class="Label">
                    Nome:
                </div>
                <div class="Value">&nbsp;<%=res.getString("nome")%></div>
            </div>
            <div class="LinhaEntrada">
                <div class="Label">
                    Descri&ccedil;&atilde;o:
                </div>
                <div class="Value">&nbsp;<%=res.getString("descricao")%></div>
            </div>
            <div class="LinhaEntrada">
                <div class="Label">
                    Endere&ccedil;o ip:
                </div>
                <div class="Value">&nbsp;<%=res.getString("ip")%></div>
            </div>
            <div class="LinhaEntrada">
                <div class="Label">
                    Porta:
                </div>
                <div class="Value">&nbsp;<%=res.getInt("porta")%></div>
            </div>
            <div class="LinhaEntrada">
                <div class="Label">
                    Login:
                </div>
                <div class="Value">&nbsp;<%=res.getString("login")%></div>
            </div>
            <div class="LinhaEntrada">
                <div class="Label">
                    Senha:
                </div>
                <div class="Value">&nbsp;<font size="+1"><%=res.getString("senha").replaceAll(".", "*")%></font></div>
            </div>

</div>
                    <div class="BotaoFechar">
                        <input class="color" id="cancelar" onclick="javascript:window.close();" value="&nbsp;&nbsp;Fechar janela&nbsp;&nbsp;" type="button" class="CancelButton"/>
                    </div>

    <%@include file="googleAnalytics"%>
    </body>
</html>
<%
con.close(); //fechar conexao
%>