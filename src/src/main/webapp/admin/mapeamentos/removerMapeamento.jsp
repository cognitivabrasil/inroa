<%-- 
    Document   : removerRepositorio
    Created on : 17/08/2009, 17:18:39
    Author     : Marcos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../../testaSessaoNovaJanela.jsp"%>
<%@page import="postgres.Excluir" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FEB - Ferramenta Administrativa</title>
        <link rel="StyleSheet" media="screen" href="../../css/padrao.css" type="text/css"/>
        <script type="text/javascript" src="../../scripts/funcoes.js"></script>
        <link href="../../imagens/favicon.ico" rel="shortcut icon" type="image/x-icon" />
    </head>
    <body>
        <div id="page">
            <div class="subTitulo-center">&nbsp;Ferramenta para remo&ccedil;&atilde;o de mapeamentos</div>
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

            if (formNull) {

                String idTipoMap = request.getParameter("idTipoMap");
                String idPadrao = request.getParameter("idPadrao");
            %>
            <form name="adicionarRepositorio" action="" method="post">
                <div class="LinhaEntrada">
                    <div class="Tab">
                        Deseja realmente remover este mapeamento?
                    </div>
                    <BR>
                </div>
                <input type="hidden" name="apagar" value="sim"/>
                <input type="hidden" name="idTipoMap" value="<%=idTipoMap%>">
                <input type="hidden" name="idPadrao" value="<%=idPadrao%>">
                <div class="LinhaEntrada">
                    <div class="Buttons">
                        <input type="submit" value="&nbsp;Sim&nbsp;" name="submit" />
                        <input id="cancelar" onclick="javascript:window.close();" value="&nbsp;N&atilde;o&nbsp;" type="button" class="CancelButton"/>
                    </div>
                </div>
            </form>
            <%

            } else {
                boolean resultado = false;
                Excluir excluir = new Excluir();
                out.println("<p>Apagando... </p>");
                //chama metodo responsavel por excluir o repositorio
                int tipoMapId = Integer.parseInt(request.getParameter("idTipoMap"));
                int padraoId = Integer.parseInt(request.getParameter("idPadrao"));
                resultado = excluir.removeMapeamento(tipoMapId, padraoId);
                if (resultado) {//se a exclusao tiver sucesso entra no if
                    //imprime mensagem positiva
                    out.print("<script type='text/javascript'>alert('Mapeamento removido com sucesso!');</script></p>" +
                            "<script type='text/javascript'>fechaRecarrega();</script>");
                } else {
                    //imprime mensagem negativa
                    out.print("<script type='text/javascript'>alert('Erro ao remover o mapeamento da base de bados! \\n Tente novamente mais tarde!');</script></p>" +
                            "<script type='text/javascript'>fechaRecarrega();</script>");
                }
            }
            
            %>
        </div>
        <%@include file="../../googleAnalytics"%>
    </body>
</html>