<%--
    Document   : exibeRepositorios
    Created on : 03/08/2009, 16:14:12
    Author     : Marcos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="testaSessaoNovaJanela.jsp"%>
<%@include file="conexaoBD.jsp"%>
<%@page import="postgres.AtualizacaoRepositorio"%>
<%@page import="java.util.Date" %>
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

//        String sql = "SELECT r.*, i.*, p.nome as nome_padrao, l.nome as nome_federacao, l.descricao as descricao_federacao, l.porta, l.login, l.senha, (i.data_ultima_atualizacao + periodicidade_horas*('1 HOUR')::INTERVAL) as proxima_atualizacao"
//+" FROM repositorios r, info_repositorios i, dados_subfederacoes l, padraometadados p"
//+" WHERE r.id="+id+" AND r.id=i.id_repositorio AND i.padrao_metadados=p.id AND i.id_federacao=l.id ORDER BY r.nome ASC";
                        String sql = "SELECT r.nome, r.descricao, p.nome as nome_padrao, i.url_or_ip, i.periodicidade_horas, t.nome as nometipomap, t.descricao as descricaotm, i.data_ultima_atualizacao, (i.data_ultima_atualizacao + periodicidade_horas*('1 HOUR')::INTERVAL) as proxima_atualizacao "
                                + " FROM repositorios r, info_repositorios i, padraometadados p, tipomapeamento t "
                                + " WHERE r.id=" + id + " AND r.id=i.id_repositorio AND i.padrao_metadados=p.id AND i.tipo_mapeamento_id=t.id "
                                + " ORDER BY r.nome ASC";
                        
                        ResultSet res = stm.executeQuery(sql);
                        if (res.next()) {

            %>

            <!--Informações Gerais-->
            <div class="subtitulo">Informa&ccedil;&otilde;es gerais</div>
            <div class="editar"><a href="./editarRepositorio.jsp?id=<%=id%>&campo=geral">Editar</a></div>

            <div class="LinhaEntrada">
                <div class="Label">
                    Nome do reposit&oacute;rio:
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
                    Padr&atilde;o de metadados utilizado:
                </div>
                <div class="Value">&nbsp;<%=res.getString("nome_padrao").toUpperCase()%></div>
            </div>
            <div class="LinhaEntrada">
                <div class="Label">
                    Tipo de mapeamento:
                </div>
                <div class="Value">&nbsp;<%out.println(res.getString("nometipomap")+" - "+res.getString("descricaotm"));%></div>
            </div>

            <!--Informações configuração
                        <div class="subtitulo">Informa&ccedil;&otilde;es sobre a configura&ccedil;&atilde;o da federa&ccedil;&atilde;o</div>
                        <div class="editar"><a href="./editarRepositorio.jsp?id=<%=id%>&campo=config">Editar</a></div>


            -->

            <div class="subtitulo">Sincroniza&ccedil;&atilde;o dos metadados</div>
            <div class="editar"><a href="./editarRepositorio.jsp?id=<%=id%>&campo=OAI-PMH">Editar</a></div>

            <div class="LinhaEntrada">
                <div class="Label">
                    URL que responde OAI-PMH:
                </div>
                <div class="Value">&nbsp;<%=res.getString("url_or_ip")%></div>
            </div>

            <div class="LinhaEntrada">
                <div class="Label">
                    Periodicidade de atualiza&ccedil;&atilde;o :
                </div>
                <div class="Value">&nbsp;<%=res.getInt("periodicidade_horas")%> (horas)</div>
            </div>


            <%

                        } else
                            out.println("<p class='textoErro'>Ocorreu um erro ao consultar a base de dados.</p>");
            %>
            <div class="subtitulo">Atualiza&ccedil;&atilde;o</div>
            <div class="editar"><a href="">Atualizar agora</a></div>
            <div class="LinhaEntrada">
                <div class="Label">
                    &Uacute;ltima Atualiza&ccedil;&atilde;o:
                </div>
                <div class="Value">&nbsp;<%=AtualizacaoRepositorio.ultimaAtualizacaoFrase(res.getTimestamp("data_ultima_atualizacao"))%></div>
            </div>
            <div class="LinhaEntrada">
                <div class="Label">
                    Pr&oacute;xima Atualiza&ccedil;&atilde;o:
                </div>
                <%
                            Timestamp dataProxAtualizacao = res.getTimestamp("proxima_atualizacao");
                            if (dataProxAtualizacao.before(new Date())) {//se a data da proxima atualizacao for inferior a data atual imprimir como erro
                                out.println("<div class=\"ValueErro\"><img src='./imagens/erro_sincronizar.png' border='0' width='24' height='24' alt='Apagar' align='top'>&nbsp;" + AtualizacaoRepositorio.ultimaAtualizacaoFrase(res.getTimestamp("proxima_atualizacao"), res.getString("url_or_ip")) + "</div>");
                            } else {
                                out.println("<div class=\"Value\">&nbsp;" + AtualizacaoRepositorio.ultimaAtualizacaoFrase(res.getTimestamp("proxima_atualizacao"), res.getString("url_or_ip")) + "</div>");
                            }
                %>
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