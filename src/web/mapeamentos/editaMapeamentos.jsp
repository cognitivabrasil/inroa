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

        /*String sql = "SELECT r.*, i.*, d.*, p.nome as nomePadrao " +
                    " FROM repositorios r, info_repositorios i, dadosldap d, padrao_metadados p " +
                    " WHERE r.id=" + id + " " +
                    "AND r.id=i.id_repositorio " +
                    "AND r.id=d.id_repositorio " +
                    "AND i.padrao_metadados=p.id " +
                    "ORDER BY r.nome ASC;";*/
        String sql = "SELECT r.*, i.*, d.*, p.nome as nome_padrao, l.nome as nome_ldap, l.descricao as descricao_ldap, (i.data_ultima_atualizacao + periodicidade_horas*('1 HOUR')::INTERVAL) as proxima_atualizacao"
+" FROM repositorios r, info_repositorios i, ldaps l, dadosldap d, padraometadados p"
+" WHERE r.id="+id+" AND r.id=i.id_repositorio AND r.id=d.id_repositorio AND i.padrao_metadados=p.id AND i.ldap_destino=l.id ORDER BY r.nome ASC;";

        ResultSet res = stm.executeQuery(sql);
        if(res.next()){

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

<!--Informações configuração-->
            <div class="subtitulo">Informa&ccedil;&otilde;es sobre a configura&ccedil;&atilde;o da federa&ccedil;&atilde;o</div>
            <div class="editar"><a href="./editarRepositorio.jsp?id=<%=id%>&campo=config">Editar</a></div>

            <div class="LinhaEntrada">
                <div class="Label">
                    Periodicidade de atualiza&ccedil;&atilde;o :
                </div>
                <div class="Value">&nbsp;<%=res.getInt("periodicidade_horas")%> (horas)</div>
            </div>
            <div class="LinhaEntrada">
                <div class="Label">
                    Nome na federa&ccedil;&atilde;o:
                </div>
                <div class="Value">&nbsp;<%=res.getString("nome_na_federacao")%></div>
            </div>

            <div class="LinhaEntrada">
                <div class="Label">
                    Tipo de Sincroniza&ccedil;&atilde;o:
                </div>
                <div class="Value">&nbsp;<%=res.getString("tipo_sincronizacao")%></div>
            </div>

<!--Ldap Local-->
            <div class="subtitulo">Informa&ccedil;&otilde;es sobre o Ldap local</div>
            <div class="editar"><a href="./editarRepositorio.jsp?id=<%=id%>&ldapLocal=<%=res.getString("nome_ldap")%>&campo=LdapLocal">Editar</a></div>

            <div class="LinhaEntrada">
                <div class="Label">
                    Nome:
                </div>
                <div class="Value">&nbsp;<%=res.getString("nome_ldap")%></div>
            </div>
            <div class="LinhaEntrada">
                <div class="Label">
                    Descri&ccedil;&atilde;o:
                </div>
                <div class="Value">&nbsp;<%=res.getString("descricao_ldap")%></div>
            </div>

            <%
        if (res.getString("tipo_sincronizacao").equalsIgnoreCase("OAI-PMH")) {
            %>

<!--Informação OAI-PMH-->
            <div class="subtitulo">Sincroniza&ccedil;&atilde;o dos metadados</div>
            <div class="editar"><a href="./editarRepositorio.jsp?id=<%=id%>&campo=OAI-PMH">Editar</a></div>

            <div class="LinhaEntrada">
                <div class="Label">
                    URL que responde OAI-PMH:
                </div>
                <div class="Value">&nbsp;<%=res.getString("url_or_ip")%></div>
            </div>

            <%            } else if (res.getString("tipo_sincronizacao").equalsIgnoreCase("LDAP"))//se for do tipo LDAP apresenta as informacoes a baixo
            {
            %>
            <input type="hidden" id="url" value="http://null.com.br">

<!--Informações Ldap Origem -->
            <div class="subtitulo">Dados sobre o Ldap de origem</div>
            <div class="editar"><a href="./editarRepositorio.jsp?id=<%=id%>&campo=LdapOrigem">Editar</a></div>

            <div class="LinhaEntrada">
                <div class="Label">
                    Endere&ccedil;o ip:
                </div>
                <div class="Value">&nbsp;<%=res.getString("url_or_ip")%></div>
            </div>
            <div class="LinhaEntrada">
                <div class="Label">
                    Porta:
                </div>
                <div class="Value">&nbsp;<%=res.getInt("porta_ldap_origem")%></div>
            </div>
            <div class="LinhaEntrada">
                <div class="Label">
                    DN:
                </div>
                <div class="Value">&nbsp;<%=res.getString("dn_origem")%></div>
            </div>
            <div class="LinhaEntrada">
                <div class="Label">
                    Login:
                </div>
                <div class="Value">&nbsp;<%=res.getString("login_ldap_origem")%></div>
            </div>
            <div class="LinhaEntrada">
                <div class="Label">
                    Senha:
                </div>
                <div class="Value">&nbsp;<font size="+1"><%=res.getString("senha_ldap_origem").replaceAll(".", "*")%></font></div>
            </div>
            <%            }
        }else
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
                if(dataProxAtualizacao.before(new Date())){//se a data da proxima atualizacao for inferior a data atual imprimir como erro
                    out.println("<div class=\"ValueErro\"><img src='./imagens/erro_sincronizar.png' border='0' width='24' height='24' alt='Apagar' align='top'>&nbsp;"+AtualizacaoRepositorio.ultimaAtualizacaoFrase(res.getTimestamp("proxima_atualizacao"), res.getString("url_or_ip"))+"</div>");
                }
                else{
                    out.println("<div class=\"Value\">&nbsp;"+AtualizacaoRepositorio.ultimaAtualizacaoFrase(res.getTimestamp("proxima_atualizacao"), res.getString("url_or_ip"))+"</div>");
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
con.close(); //fechar conexao com mysql
%>