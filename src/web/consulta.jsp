<%--
    Document   : consulta
    Created on : 01/07/2009, 16:09:51
    Author     : Marcos

o dnRaiz deve ter essa ordem: obaaIdentifier=obaa000000,ou=obaa,dc=ufrgs,dc=br
  utilizamos um split pelo , pega a posicao 1 da um split de novo por = e pega a posicao 1 que eh o nome (obaa)
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="operacoesLdap.Consultar"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="postgres.ConsultaNomeFederacao" %>
<%@include file="conexaoBD.jsp"%>
<%@page import="ferramentaBusca.recuperador.Recuperador"%>

<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%

            int numeroMaximoDeObjetosPorPagina = 5;

            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");


            //variaveis para paginacao
            String style = "altavista";
            String position = "both";
            String index = "center";
            int maxPageItems = numeroMaximoDeObjetosPorPagina;
            int maxIndexPages = 10;
            //fim variaveis para paginacao



            
            String idRepositorio = "";
            String palavraChave = "";
            
            idRepositorio = request.getParameter("repositorio");
            palavraChave = request.getParameter("key"); //recebe a consulta informada no formulario
            try {
                if (idRepositorio.isEmpty() || palavraChave.isEmpty()) {
                    out.print("<script type='text/javascript'>alert('Nenhuma consulta foi informada');</script>" +
                            "<script type='text/javascript'>history.back(-1);</script>");
                }
            } catch (Exception e) {
                out.print("<script type='text/javascript'>alert('Nenhuma consulta foi informada');</script>" +
                        "<script type='text/javascript'>window.location=\"index.jsp\";</script>");
                e.printStackTrace();

            }



            //Se a busca nao for preenchida, volta a pagina para ser preenchida
            if (palavraChave.isEmpty()) {
                out.print("<script type='text/javascript'>alert('Nenhuma consulta foi informada');</script>" +
                        "<script type='text/javascript'>history.back(-1);</script>");
            }


            int numObjetosEncontrados = 0; //inicializa int que tera o numero de resultados retornados

            ArrayList<Integer> resultadoBusca = new ArrayList<Integer>(); //ArrayList que recebera o resultado da busca MySQL

            //chama o metodo de busca inteligente
            Recuperador rep = new Recuperador();


            try {
                resultadoBusca = rep.search2(palavraChave, con, Integer.valueOf(idRepositorio));//efetua a busca com o metodo de recuperacao de informacoes

                numObjetosEncontrados = resultadoBusca.size(); //armazena o numero de objetos

            } catch (SQLException e) {
                out.println("");
                System.out.println("Erro na consulta: ");
                e.printStackTrace(); //imprime o erro.
                numObjetosEncontrados = 0;
            }
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="StyleSheet" href="css/padrao.css" type="text/css">
        <link href="imagens/favicon.ico" rel="shortcut icon" type="image/x-icon" />
        <title>FEB – Federa&ccedil;&atilde;o de Reposit&oacute;rios Educa Brasil</title>
    </head>
    <body id="body">
        <div id="page-header">Resultado da Pesquisa</div>
        <div id="page-sub-header">
            <a href="index.jsp">Efetuar nova consulta</a>
        </div>
        <div class="cabecalhoConsulta">
            <div class="esquerda">
                &nbsp;Consulta efetuada: <i>"<strong><%=palavraChave%></strong>"</i>
            </div>
            <div class="direita">
                Total de <strong><%=numObjetosEncontrados%></strong> objeto(s) encontrado(s)&nbsp;
            </div>

        </div>


        <div id="body-resultado">

            <%
            if (numObjetosEncontrados == 0) { //se nao retorno nenhum objeto
            %>
            <p align="center">
                <strong>
                    <font size="3" face="Verdana, Arial, Helvetica, sans-serif">
                        Nenhum objeto encontrado
                    </font>
                </strong>
            </p>


            <%//se retornou objetos
                } else {

                String url = "&repositorio=" + idRepositorio + "&key=" + palavraChave;

            %>

            <form action="<%= request.getRequestURI()%>" method="get">
                <center>


                    <pg:pager
                        items="<%= numObjetosEncontrados %>"
                        index="<%= index %>"
                        maxPageItems="<%= maxPageItems %>"
                        maxIndexPages="<%= maxIndexPages %>"
                        isOffset="<%= true %>"
                        export="offset,currentPageNumber=pageNumber"
                        scope="page"
                        >

                        <%-- keep track of preference --%>
                        <pg:param name="style"/>
                        <pg:param name="position"/>
                        <pg:param name="index"/>
                        <pg:param name="maxPageItems"/>
                        <pg:param name="maxIndexPages"/>

                        <%-- salva pager offset durante as mudancas do form --%>
                        <input type="hidden" name="pager.offset" value="<%= offset%>">

                        <%-- Inclui paginacao antes dos resultados--%>
                        <%@include file="WEB-INF/jsp/paginacaoPersonalizada.jsp" %>

                        <div id="body-resultado-interno">


                            <%


                for (int result = offset.intValue(), l = Math.min(result + maxPageItems, numObjetosEncontrados); result < l; result++) {//percorre o resultado da ferramenta de RI(recuperacao de informacao)
//colocar aqui para imprimir as consultas. Ele vai entrar aqui uma vez para cada resultado do hashmap
                    ArrayList resultHash = new ArrayList(); //ArrayList que recebera o resultado da busca LDAP


                    //fazer consulta no mysql para pegar as informações necessárias
                    String resultadoSQL = "SELECT d.obaa_entry, d.titulo, d.resumo, d.data, d.localizacao, r.nome as servidor, l.ip, CASE r.nome WHEN 'todos' THEN l.dn ELSE ('ou='||i.nome_na_federacao||','||l.dn) END AS dn from documentos d, repositorios r, ldaps l, info_repositorios i where d.id=" + resultadoBusca.get(result) + " and d.id_repositorio=r.id and r.id=i.id_repositorio and i.ldap_destino=l.id;";

                    ResultSet rs = stm.executeQuery(resultadoSQL);
                    //pega o proximo resultado retornado pela consulta sql
                    rs.next();
                    String identificador = rs.getString("obaa_entry");
                    String titulo = rs.getString("titulo");
                    String resumo = rs.getString("resumo");
                    String data = rs.getString("data");
                    String localizacao = rs.getString("localizacao");
                    String Servidor = rs.getString("servidor");
                    String ipResultado = rs.getString("ip");
                    String dn = rs.getString("dn");


                    // out.println("<p>" + identificador + "<br>" + titulo + "<br>" + resumo + "<br>" + localizacao + "<br>" + data + "<br>" + Servidor + "<br>" + ipResultado + "<br>" + dn + "</p>");

                            %>

                            <div class="resultadoConsulta">

                                <%
///inicio tratamento titulo
                    if (!titulo.isEmpty()) {
                                %>
                                <div class="titulo">
                                    <a href='infoDetalhada.jsp?id=<%=identificador%>&ip=<%=ipResultado%>&dn=<%=dn%>'>
                                        <%
                                                    String[] tempObaa = titulo.split(";; ");
                                                    for (int kk = 0; kk < tempObaa.length; kk++) { //percorrer todos os resultados separados por ;;

                                                        if (kk > 0) { //apos o primeiro elemento colocar um "<BR>"
                                                            out.print("<br>");
                                                        }

                                                        out.print("- " + tempObaa[kk].trim());
                                                    }
                                        %>
                                    </a>
                                </div>
                                <%
                    } else {//se nao existir titulo informa que nao tem titulo mas cria o link para o objeto
                        out.println("<div class=\"titulo\"><a href='infoDetalhada.jsp?id=" + identificador + "&ip=" + ipResultado + "&dn=" + dn + "'>T&iacute;tulo n&atilde;o informado.</a></div>");
                    }
//fim tratamento titulo
//inicio tratamento resumo
                    if (!resumo.isEmpty()) {
                        out.println("<div class=\"atributo\">");
                        String[] tempObaa = resumo.split(";; ");
                        for (int kk = 0; kk < tempObaa.length; kk++) {
                            //apos o primeiro elemento colocar um "<BR>"
                            if (kk > 0) {
                                out.print("<br>");
                            }

                            out.print(tempObaa[kk].trim());
                        }
                        out.println("</div>");
                    }
//fim tratamento resumo
//inicio tratamento localizacao
                    if (!localizacao.isEmpty()) {
                        out.println("<div class=\"atributo\">");
                        out.println("Localiza&ccedil;&atilde;o:");

                        String[] tempObaa = localizacao.split(";;");
                        for (int kk = 0; kk < tempObaa.length; kk++) {                         

                            out.println("<div class=\"valor\">" +
                                    "<a href=\"" + tempObaa[kk].trim() + "\" target=\"_new\">" + tempObaa[kk].trim() + "</a>" +
                                    "</div>");
                        }
                        out.println("</div>");
                    }
//fim tratamento localizacao
//inicio tratamento data
                    out.println("<div class=\"atributo\">");
                    out.println("Data:");

                    if (!data.isEmpty() && !data.equalsIgnoreCase("Ano-Mês-Dia")) {
                        String[] tempObaa = data.split(";;");
                        for (int kk = 0; kk < tempObaa.length; kk++) {                            
                            out.print("<div class=\"valor\">"
                                    + tempObaa[kk].trim().replaceAll("[A-Z,a-z,ê,Ê]", " ").replaceAll(" -", "")+
                                    "</div>");
                        } //fim for

                    } else {
                        out.print("N&atilde;o documentado");
                    }
                    out.println("</div>");
//fim tratamento data
//inicio tratamento servidor
                    if (!Servidor.isEmpty()) {

                        out.println("<div class=\"atributo\">" +
                                "Reposit&oacute;rio: " + Servidor +
                                "</div>");
                    }

                                %></div><%


                }
                            %>


                        </div>
                        <%-- Inclui paginacao depois dos resultados--%>
                        <%@include file="WEB-INF/jsp/paginacaoPersonalizada.jsp" %>

                    </pg:pager>
                </center>
            </form>

            <%
            }
            %>
        </div>

        <div class="rodapeConsulta">&nbsp;</div>
        <%@include file="googleAnalytics"%>
    </body>
</html>
<%
            con.close(); //fechar conexao com mysql

%>