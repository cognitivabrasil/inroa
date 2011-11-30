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
<%@page import="robo.util.Operacoes"%>
<%@page import="operacoesPostgre.Consultar"%>
<%@page import="java.util.Date" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FEB - Ferramenta Administrativa</title>
        <link rel="StyleSheet" href="css/padrao.css" type="text/css">
        <link href="imagens/favicon.ico" rel="shortcut icon" type="image/x-icon" />
        <script language="JavaScript" type="text/javascript" src="scripts/funcoes.js"></script>
    </head>

    <body>
        <div id="page">

            <div class="subTitulo-center">&nbsp;Edi&ccedil;&atilde;o / Visualiza&ccedil;&atilde;o de reposit&oacute;rios cadastrados</div>

            <%
                        String id = request.getParameter("id");

                        String sql = "SELECT r.nome, r.descricao, p.nome as nome_padrao, i.url_or_ip, i.periodicidade_horas, i.name_space, i.metadata_prefix, i.set, t.nome as nometipomap, t.descricao as descricaotm, i.data_ultima_atualizacao, (i.data_ultima_atualizacao + periodicidade_horas*('1 HOUR')::INTERVAL) as proxima_atualizacao "
                                + " FROM repositorios r, info_repositorios i, padraometadados p, tipomapeamento t "
                                + " WHERE r.id=" + id + " AND r.id=i.id_repositorio AND i.padrao_metadados=p.id AND i.tipo_mapeamento_id=t.id "
                                + " ORDER BY r.nome ASC";
                        
                        ResultSet res = stm.executeQuery(sql);
                        if (res.next()) {
                            String set = res.getString("set");
                            if(set==null || set.isEmpty()){
                                set = "Todas";
                            }
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
                    Nome do mapeamento:
                </div>
                <div class="Value">&nbsp;<%=res.getString("nometipomap")+" - "+res.getString("descricaotm")%></div>
            </div>
            <div class="LinhaEntrada">
                <div class="Label">
                    MetadataPrefix:
                </div>
                <div class="Value">&nbsp;<%=res.getString("metadata_prefix")%></div>
            </div>
            <div class="LinhaEntrada">
                <div class="Label">
                    NameSpace:
                </div>
                <div class="Value">&nbsp;<%=res.getString("name_space")%></div>
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
                    Cole&ccedil;&otilde;es ou Comunidades:
                </div>
                <div class="Value">&nbsp;<%=set%></div>
            </div>

            <div class="LinhaEntrada">
                <div class="Label">
                    Periodicidade de atualiza&ccedil;&atilde;o :
                </div>
                <div class="Value">&nbsp;<%=res.getInt("periodicidade_horas")/24%> dia(s)</div>
            </div>


            <%

                        } else
                            out.println("<p class='textoErro'>Ocorreu um erro ao consultar a base de dados.</p>");
            %>
            <div class="subtitulo">Atualiza&ccedil;&atilde;o</div>
            <div class="EspacoAntes">&nbsp;</div>
            <div class="LinhaEntrada">
                <div class="Label">
                    &Uacute;ltima Atualiza&ccedil;&atilde;o:
                </div>
                <div class="Value">&nbsp;<%=Operacoes.ultimaAtualizacaoFrase(res.getTimestamp("data_ultima_atualizacao"))%></div>
            </div>
            <div class="LinhaEntrada">
                <div class="Label">
                    Pr&oacute;xima Atualiza&ccedil;&atilde;o:
                </div>
                <%
                            Timestamp dataProxAtualizacao = res.getTimestamp("proxima_atualizacao");
                            if (dataProxAtualizacao.before(new Date())) {//se a data da proxima atualizacao for inferior a data atual imprimir como erro
                                out.println("<div id='textResult"+id+"' class=\"ValueErro\">&nbsp;" + Operacoes.ultimaAtualizacaoFrase(res.getTimestamp("proxima_atualizacao"), res.getString("url_or_ip"))
                                        + "&nbsp;&nbsp;<a title='Atualizar agora' onclick=\"javaScript:atualizaRepAjax("+id+", document.getElementById('textResult"+id+"'));\"><img src='./imagens/erro_sincronizar.png' border='0' width='24' height='24' alt='Atualizar' align='middle'> </a> </div>");
                            } else {
                                out.println("<div class=\"Value\">&nbsp;" + Operacoes.ultimaAtualizacaoFrase(res.getTimestamp("proxima_atualizacao"), res.getString("url_or_ip"))
                                        + "&nbsp;&nbsp;<a title='Atualizar agora' onclick=\"javaScript:atualizaRepAjax("+id+", document.getElementById('textResult"+id+"'));\"><img src='./imagens/sincronizar.png' border='0' width='24' height='24' alt='Atualizar' align='middle'> </a> </div>");
                            }
                %>
            </div>
            <div class="LinhaEntrada">
                <div class="Label">
                    N&uacute;mero de objetos:
                </div>
                <div class="Value">
                    <div>&nbsp;<%=Consultar.selectNumeroDocumentosRep(con,Integer.parseInt(id))%></div>
                    
                    <div id="removeAtualiza" class="ApagaObjetos">&nbsp;<input type="button" value="Formatar e restaurar" onclick="javascript:apagaAtaualizaRepAjax(<%=id%>, this.parentNode)"></div>
                    
                </div>               
            </div>
        </div>
        
        <%@include file="googleAnalytics"%>
    </body>

</html>
<%
            con.close(); //fechar conexao
%>