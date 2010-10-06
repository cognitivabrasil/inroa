<%-- 
    Document   : removerFederacao
    Created on : 14/09/2009, 12:01:56
    Author     : Marcos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="testaSessaoNovaJanela.jsp"%>
<%@include file="conexaoBD.jsp"%>
<%--@page import="operacoesLdap.Remover" %>
<%@page import="operacoesLdap.ReiniciarServico" --%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FEB - Ferramenta Administrativa</title>
        <link rel="StyleSheet" media="screen" href="css/padrao.css" type="text/css"/>
        <script type="text/javascript" src="./scripts/funcoes.js"></script>
        <link href="imagens/favicon.ico" rel="shortcut icon" type="image/x-icon" />
    </head>
    <body>
        <div id="page">
            <div class="subTitulo-center">&nbsp;Ferramenta para remo&ccedil;&atilde;o de subfedera&ccedil;&otilde;es</div>
            <div class="EspacoAntes">&nbsp;</div>
            <%
            boolean formNull = true;
            try {
                String apagar = request.getParameter("apagar");
                if (apagar.equals("sim")) {
                    formNull = false;
                }
            } catch (Exception e) {
                formNull = true;

            }
            String id = request.getParameter("id");
            //Carrega do banco de dados os padroes de metadados cadastrados
            ResultSet res = stm.executeQuery("SELECT l.nome, l.ip FROM dados_subfederacoes l WHERE id=" + id + " ORDER BY nome ASC");
            res.next();
            String ip = res.getString("ip");

            if (formNull) {


            %>
            <form name="removerFederacao" action="removerFederacao.jsp" method="post">
                <div class="LinhaEntrada">
                    <div class="Tab">
                        Deseja realmente remover a rela&ccedil;&atilde;o com a federa&ccedil;&atilde;o <b><%=res.getString("nome")%></b> ?
                    </div>
                    <BR>
                </div>
                <input type="hidden" name="apagar" value="sim"/>
                <input type="hidden" name="id" value="<%=id%>">
                <div class="LinhaEntrada">
                    <div class="Buttons">
                        <input type="submit" value="&nbsp;Sim&nbsp;" name="submit" />
                        <input id="cancelar" onclick="javascript:window.close();" value="&nbsp;N&atilde;o&nbsp;" type="button" class="CancelButton"/>
                    </div>
                </div>
            </form>
            <%
            } else{
                
                    int result = 0;
                    String sql = "DELETE FROM dados_subfederacoes WHERE id=" + id;
                    
                    try {

                    result = stm.executeUpdate(sql); //executa o que tem na variavel sql
                    } catch (SQLFeatureNotSupportedException e) {
                    out.print("<p class='textoErro'>Erro:" + e);

                } catch (SQLException k) {
                    System.out.println("ERRO SQL: "+k);
                    out.print("<p class='textoErro'>Erro no sql: " + k);

                    out.print("<script type='text/javascript'>alert('Erro ao remover a relacao com a subfederacao! \\n Tente novamente mais tarde!');</script></p>" +
                            "<script type='text/javascript'>window.close();</script>");
                }
                    if (result > 0) {
                                out.print("<script type='text/javascript'>alert('Base removida com sucesso!');" +
                                        "fechaRecarrega();</script>");
                    }

                }
            %>
        </div>
        <%@include file="googleAnalytics"%>
    </body>
</html>
<%
con.close(); //fechar conexao
%>