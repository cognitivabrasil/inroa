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
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Date"%>
<%@page import="operacoesPostgre.Consultar"%>
<%@page import="robo.util.Operacoes"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FEB - Ferramenta Administrativa</title>
        <link rel="StyleSheet" href="css/padrao.css" type="text/css">
        <link href="imagens/favicon.ico" rel="shortcut icon" type="image/x-icon" />
    </head>

    <body>
        <div id="page">

            <div class="subTitulo-center">&nbsp;Edi&ccedil;&atilde;o / Visualiza&ccedil;&atilde;o de federa&ccedil;&otilde;es cadastradas</div>

            <%
                        String id = request.getParameter("id");

                        String sql = "SELECT id, nome, descricao, url, data_ultima_atualizacao "
                                + " FROM dados_subfederacoes l"
                                + " WHERE l.id=" + id
                                + " ORDER BY l.nome ASC;";

                        Statement stm = con.createStatement();
                        ResultSet res = stm.executeQuery(sql);
                        if(res.next()){

                        HashMap<String, Integer> repositorios = Consultar.selectNumeroDocumentosSubrep(con, Integer.parseInt(id));
                        Timestamp ultimaAtualizacao = res.getTimestamp("data_ultima_atualizacao");
                        Date ontem = new Date(new Date().getTime()-(1000*24*60*60));


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
                    URL da federa&ccedil;&atilde;o:
                </div>
                <div class="Value">&nbsp;<%=res.getString("url")%></div>
            </div>

            <div class="LinhaEntrada">
                <div class="Label">
                    &Uacute;ltima Atualiza&ccedil;&atilde;o:
                </div>
                <div id="textResultSF<%=res.getString("id")%>" class="Value">
                    <%
                        if(ultimaAtualizacao.before(ontem)){
                             out.println("<div class='textoErro'>&nbsp;"+Operacoes.ultimaAtualizacaoFrase(res.getTimestamp("data_ultima_atualizacao"))+
                                     " <a title='Atualizar agora' onclick=\"javaScript:atualizaSubfedAjax("+res.getString("id")+", document.getElementById('textResultSF"+res.getString("id")+"'));\"><img src='./imagens/erro_sincronizar.png' border='0' width='24' height='24' alt='Atualizar' align='middle'> </a> </div>");
                        } else {
                            out.println("&nbsp;" + Operacoes.ultimaAtualizacaoFrase(res.getTimestamp("data_ultima_atualizacao"))+
                                    " <a title='Atualizar agora' onclick=\"javaScript:atualizaSubfedAjax("+res.getString("id")+", document.getElementById('textResultSF"+res.getString("id")+"'));\"><img src='./imagens/sincronizar.png' border='0' width='24' height='24' alt='Atualizar' align='middle'> </a>");
                            }  %>
                    
                    
                </div>
            </div>

            <table  align="center" width=100% class="tableSubfed">
                <th class='coluna' width="70%">Reposit&oacute;rio</th><th class='coluna' width="30%">N&uacute;mero de objetos</th>


                    <%
                    int total = 0;
                    for(String nomes : repositorios.keySet()){
                        total+=repositorios.get(nomes);
                        out.println("<tr>");
                        out.println("<td class='coluna'>"+nomes+"</td> <td class='coluna' align='center'>"+repositorios.get(nomes)+"</td>");
                        out.println("</tr>");
                    }

                    %>
                    <tr class="bold"><td align="right" class='coluna'>TOTAL</td><td class='coluna' align='center'><%=total%></td></tr>
                    </table>

                    <%}%>
        </div>

        <%@include file="googleAnalytics"%>
    </body>
</html>
<%
            con.close(); //fechar conexao
%>