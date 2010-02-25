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

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FEB - Ferramenta Administrativa</title>
        <link rel="StyleSheet" href="css/padrao.css" type="text/css">
    </head>

    <body>
        <div id="page">

            <div class="subTitulo-center">&nbsp;Edi&ccedil;&atilde;o / Visualiza&ccedil;&atilde;o de reposit&oacute;rios cadastrados</div>

            <%
        String id = request.getParameter("id");
       
        /*String sql = "SELECT r.*, i.*, d.*, p.nome as nomePadrao " +
                    " FROM repositorios r, info_repositorios i, dadosldap d, padraometadados p " +
                    " WHERE r.id=" + id + " " +
                    "AND r.id=i.id_repositorio " +
                    "AND r.id=d.id_repositorio " +
                    "AND i.padraoMetadados=p.id " +
                    "ORDER BY r.nome ASC;";*/
        String sql = "SELECT r.*, i.*, d.*, p.nome as nomePadrao, l.nome as nomeLdap, l.descricao as descricaoLdap " +
                "FROM repositorios r, info_repositorios i, ldaps l, dadosldap d, padraometadados p " +
                " WHERE r.id=" + id + " " +
                "AND r.id=i.id_repositorio " +
                "AND r.id=d.id_repositorio " +
                "AND i.padraoMetadados=p.id " +
                "AND i.ldapDestino=l.id " +
                "ORDER BY r.nome ASC;";

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
                <div class="Value">&nbsp;<%=res.getString("nomePadrao").toUpperCase()%></div>
            </div>

<!--Informações configuração-->
            <div class="subtitulo">Informa&ccedil;&otilde;es sobre a configura&ccedil;&atilde;o da federa&ccedil;&atilde;o</div>
            <div class="editar"><a href="./editarRepositorio.jsp?id=<%=id%>&campo=config">Editar</a></div>

            <div class="LinhaEntrada">
                <div class="Label">
                    Periodicidade de atualiza&ccedil;&atilde;o :
                </div>
                <div class="Value">&nbsp;<%=res.getInt("periodicidadeHoras")%> (horas)</div>
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
                <div class="Value">&nbsp;<%=res.getString("tipoSincronizacao")%></div>
            </div>

<!--Ldap Local-->
            <div class="subtitulo">Informa&ccedil;&otilde;es sobre o Ldap local</div>
            <div class="editar"><a href="./editarRepositorio.jsp?id=<%=id%>&ldapLocal=<%=res.getString("nomeLdap")%>&campo=LdapLocal">Editar</a></div>

            <div class="LinhaEntrada">
                <div class="Label">
                    Nome:
                </div>
                <div class="Value">&nbsp;<%=res.getString("nomeLdap")%></div>
            </div>
            <div class="LinhaEntrada">
                <div class="Label">
                    Descri&ccedil;&atilde;o:
                </div>
                <div class="Value">&nbsp;<%=res.getString("descricaoLdap")%></div>
            </div>
            
            <%
        if (res.getString("tipoSincronizacao").equalsIgnoreCase("OAI-PMH")) {
            %>

<!--Informação OAI-PMH-->
            <div class="subtitulo">Sincroniza&ccedil;&atilde;o dos metadados</div>
            <div class="editar"><a href="./editarRepositorio.jsp?id=<%=id%>&campo=OAI-PMH">Editar</a></div>

            <div class="LinhaEntrada">
                <div class="Label">
                    URL que responde OAI-PMH:
                </div>
                <div class="Value">&nbsp;<%=res.getString("URLorIP")%></div>
            </div>

            <%            } else if (res.getString("tipoSincronizacao").equalsIgnoreCase("LDAP"))//se for do tipo LDAP apresenta as informacoes a baixo
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
                <div class="Value">&nbsp;<%=res.getString("URLorIP")%></div>
            </div>
            <div class="LinhaEntrada">
                <div class="Label">
                    Porta:
                </div>
                <div class="Value">&nbsp;<%=res.getInt("portaLdapOrigem")%></div>
            </div>
            <div class="LinhaEntrada">
                <div class="Label">
                    DN:
                </div>
                <div class="Value">&nbsp;<%=res.getString("dnOrigem")%></div>
            </div>
            <div class="LinhaEntrada">
                <div class="Label">
                    Login:
                </div>
                <div class="Value">&nbsp;<%=res.getString("loginLdapOrigem")%></div>
            </div>
            <div class="LinhaEntrada">
                <div class="Label">
                    Senha:
                </div>
                <div class="Value">&nbsp;<font size="+1"><%=res.getString("senhaLdapOrigem").replaceAll(".", "*")%></font></div>
            </div>
            <%            }
        }else
            out.println("<p class='textoErro'>Ocorreu um erro ao consultar a base de dados.</p>");
            %>
            
        </div>
        
                    <div class="BotaoFechar">
                        <input class="color" id="cancelar" onclick="javascript:window.close();" value="&nbsp;&nbsp;Fechar janela&nbsp;&nbsp;" type="button" class="CancelButton"/>
                    </div>


    </body>
</html>