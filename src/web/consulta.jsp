<%--
    Document   : consulta
    Created on : 01/07/2009, 16:09:51
    Author     : Marcos
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="operacoesPostgre.Consultar"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@include file="conexaoBD.jsp"%>
<%@page import="ferramentaBusca.Recuperador"%>

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



            String idRepLocal[] = {""};
            String idSubfed[] = {""};
            String idSubRep[] = {""};


            String textoBusca = "";



            boolean testaConsulta = false;
            try {
                textoBusca = request.getParameter("key"); //recebe a consulta informada no formulario
                idRepLocal = request.getParameterValues("replocal");
                idSubfed = request.getParameterValues("subfed");
                idSubRep = request.getParameterValues("subrep");


                if (textoBusca.isEmpty()) {
                    out.print("<script type='text/javascript'>alert('Nenhuma consulta foi informada');</script>"
                            + "<script type='text/javascript'>history.back(-1);</script>");
                } else {
                    testaConsulta = true;
                }
            } catch (Exception e) {
                out.print("<script type='text/javascript'>alert('Nenhuma consulta foi informada');</script>"
                        + "<script type='text/javascript'>window.location=\"index.jsp\";</script>");
                e.printStackTrace();

            }
            if (testaConsulta) { //se nao foi informada a consulta nao entra no if

                int numObjetosEncontrados = 0; //inicializa int que tera o numero de resultados retornados
                ArrayList<Integer> resultadoBusca = new ArrayList<Integer>(); //ArrayList que recebera o resultado da busca

                //chama o metodo de busca inteligente
                Recuperador rep = new Recuperador();

                try {
                    resultadoBusca = rep.busca(textoBusca, con, idRepLocal, idSubfed, idSubRep, "relevancia");

                    numObjetosEncontrados = resultadoBusca.size(); //armazena o numero de objetos

                } catch (SQLException e) {
                    out.println("");
                    System.out.println("Erro na consulta: ");
                    e.printStackTrace(); //imprime o erro.
                    numObjetosEncontrados = 0;
                    out.print("<script type='text/javascript'>alert('Nao foi possivel efetuar a consulta na base de dados');</script>"
                            + "<script type='text/javascript'>history.back(-1);</script>");
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
        <div class="logoBusca"><img src="imagens/Logo FEB_reduzido.png" width="7%" alt="Logo FEB_reduzido"/></div>


        <div class="clear"> </div>

        <div class="EspacoPequeno">&nbsp;</div>
        <div class="subTituloBusca">&nbsp;Resultado da Pesquisa</div>

        <div id="page-sub-header">
            <a href="index.jsp">Efetuar nova consulta</a>
        </div>
        <div class="cabecalhoConsulta">
            <div class="esquerda">
                &nbsp;Consulta efetuada: <i>"<strong><%=textoBusca%></strong>"</i>
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

                                            String url = "";

                                            if (idRepLocal != null) {
                                                for (int i = 0; i < idRepLocal.length; i++) {
                                                    if (!idRepLocal[i].isEmpty()) {
                                                        url += "&replocal=" + idRepLocal[i];
                                                    }
                                                }
                                            }
                                            if (idSubfed != null) {
                                                for (int i = 0; i < idSubfed.length; i++) {
                                                    if (!idSubfed[i].isEmpty()) {
                                                        url += "&subfed=" + idSubfed[i];
                                                    }
                                                }
                                            }
                                            if (idSubRep != null) {
                                                for (int i = 0; i < idSubRep.length; i++) {
                                                    if (!idSubRep[i].isEmpty()) {
                                                        url += "&subrep=" + idSubRep[i];
                                                    }
                                                }
                                            }
                                            url += "&key=" + textoBusca;

            %>

            <form action="<%= request.getRequestURI()%>" method="get">
                <center>


                    <pg:pager
                    items="<%= numObjetosEncontrados%>"
                    index="<%= index%>"
                    maxPageItems="<%= maxPageItems%>"
                    maxIndexPages="<%= maxIndexPages%>"
                    isOffset="<%= true%>"
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
                                                                ArrayList resultHash = new ArrayList(); //ArrayList que recebera o resultado da busca


                                                                //fazer consulta na base de dados para pegar as informações necessárias
                                                                //postgres ok
                                                                //String resultadoSQL = "SELECT l.id as id_base, d.obaa_entry, d.titulo, d.resumo, d.data, d.localizacao, r.nome as repositorio FROM documentos d, repositorios r, dados_subfederacoes l, info_repositorios i where d.id=" + resultadoBusca.get(result) + " and d.id_repositorio=r.id and r.id=i.id_repositorio and i.id_federacao=l.id;";

                                                                ArrayList<String> titulo = new ArrayList<String>();
                                                                ArrayList<String> resumo = new ArrayList<String>();
                                                                ArrayList<String> data = new ArrayList<String>();
                                                                ArrayList<String> localizacao = new ArrayList<String>();

                                                                String identificador = "";
                                                                int repositorio = 0;
                                                                String nomeRepositorio = "";
                                                                String idRepSubfed = "";
                                                                int idSubfederacao = 0;


                                                                String resultadoSQL = "SELECT d.obaa_entry, o.atributo, o.valor, d.id_rep_subfed as repsubfed, d.id_repositorio as repositorio"
                                                                        + " FROM documentos d, objetos o"
                                                                        + " WHERE d.id=o.documento AND d.id=" + resultadoBusca.get(result)
                                                                        + "AND (o.atributo ~* '^obaadate$' OR o.atributo ~* '^obaaLocation$' OR o.atributo ~* '^obaaDescription$' OR o.atributo ~* '^obaaTitle$' OR o.atributo ~* '^nomeRepositorio')";
                                                                try {
                                                                    ResultSet rs = stm.executeQuery(resultadoSQL);
                                                                    //pega o proximo resultado retornado pela consulta sql

                                                                    while (rs.next()) {
                                                                        if (rs.isFirst()) {
                                                                            identificador = rs.getString("obaa_entry");
                                                                            repositorio = rs.getInt("repositorio");
                                                                            //nomeRepositorio = rs.getString("nomeRep");
                                                                            idRepSubfed = rs.getString("repsubfed");
                                                                        }
                                                                        String valor = rs.getString("valor");
                                                                        String atributo = rs.getString("atributo");
                                                                        if (atributo.equalsIgnoreCase("obaaTitle")) {
                                                                            titulo.add(valor);
                                                                        } else if (atributo.equalsIgnoreCase("obaaDate")) {
                                                                            data.add(valor);
                                                                        } else if (atributo.equalsIgnoreCase("obaaLocation")) {
                                                                            localizacao.add(valor);
                                                                        } else if (atributo.equalsIgnoreCase("obaaDescription")) {
                                                                            resumo.add(valor);
                                                                        }
                                                                    }
                                                                } catch (SQLException e) {
                                                                    out.print("<script type='text/javascript'>alert('Nao foi possivel recuperar as informacoes da base de dados');</script>"
                                                                            + "<script type='text/javascript'>history.back(-1);</script>");
                                                                }

////                                                    if(titulo.size()>0 ||data.size()>0|| localizacao.size()>0 || resumo.size()>0){
///                                                    }

                                                                if (idRepSubfed == null) {
                                                                    String sql = "SELECT r.nome as nomeRep from documentos d, repositorios r WHERE d.id_repositorio=r.id AND d.id=" + resultadoBusca.get(result);
                                                                    ResultSet rs = stm.executeQuery(sql);
                                                                    if (rs.next()) {
                                                                        nomeRepositorio = rs.getString("nomeRep");
                                                                    }
                                                                } else {
                                                                    String sql = "SELECT ds.id as idSubfed, ds.nome as nomeSubfed, rsf.nome as nomeRepSF from documentos d, dados_subfederacoes ds, repositorios_subfed rsf WHERE d.id_rep_subfed=rsf.id AND rsf.id_subfed=ds.id AND d.id=" + resultadoBusca.get(result);
                                                                    ResultSet rs = stm.executeQuery(sql);
                                                                    if (rs.next()) {
                                                                        idSubfederacao = rs.getInt("idSubFed");
                                                                        nomeRepositorio = "Subfedera&ccedil;&atilde;o " + rs.getString("nomeSubfed") + " / " + rs.getString("nomeRepSF");
                                                                    }
                                                                }

                            %>

                            <div class="resultadoConsulta">

                                <%
///inicio tratamento titulo
                                                                                                if (!titulo.isEmpty()) {
                                %>
                                <div class="titulo">
                                    <a href='infoDetalhada.jsp?id=<%=identificador%>&idBase=<%=idSubfederacao%>&repositorio=<%=repositorio%>'>
                                        <%
                                                                                                                                            for (int j = 0; j < titulo.size(); j++) { //percorrer todos os resultados separados por ;;
                                                                                                                                                if (j > 0) { //apos o primeiro elemento colocar um "<BR>"
                                                                                                                                                    out.print("<br>");
                                                                                                                                                }
                                                                                                                                                out.print("- " + titulo.get(j).trim());
                                                                                                                                            }
                                        %>
                                    </a>
                                </div>
                                <%
                                                                                                } else {//se nao existir titulo informa que nao tem titulo mas cria o link para o objeto
                                                                                                    out.println("<div class=\"titulo\"><a href='infoDetalhada.jsp?id=" + identificador + "&idBase=" + idSubfederacao + "&repositorio=" + repositorio + "'>T&iacute;tulo n&atilde;o informado.</a></div>");
                                                                                                }
//fim tratamento titulo

//inicio tratamento resumo
                                                                                                if (!resumo.isEmpty()) {
                                                                                                    out.println("<div class=\"atributo\">");

                                                                                                    for (int j = 0; j < resumo.size(); j++) {
                                                                                                        //apos o primeiro elemento colocar um "<BR>"
                                                                                                        if (j > 0) {
                                                                                                            out.print("<br>");
                                                                                                        }
                                                                                                        String resumoLimitado = resumo.get(j).trim();
                                                                                                        if (resumoLimitado.length() >= 500) {
                                                                                                            resumoLimitado = resumoLimitado.substring(0, 500);
                                                                                                            resumoLimitado += " <a style='text-decoration: none;' href='infoDetalhada.jsp?id=" + identificador + "&idBase=" + idSubfederacao + "&repositorio=" + repositorio + "'>(...)</a>";
                                                                                                        }
                                                                                                        out.print(resumoLimitado);
                                                                                                    }
                                                                                                    out.println("</div>");
                                                                                                }
//fim tratamento resumo

//inicio tratamento localizacao
                                                                                                if (!localizacao.isEmpty()) {
                                                                                                    out.println("<div class=\"atributo\">");
                                                                                                    out.println("Localiza&ccedil;&atilde;o:");


                                                                                                    for (int j = 0; j < localizacao.size(); j++) {

                                                                                                        out.println("<div class=\"valor\">"
                                                                                                                + "<a href=\"" + localizacao.get(j).trim() + "\" target=\"_new\">" + localizacao.get(j).trim() + "</a>"
                                                                                                                + "</div>");
                                                                                                    }
                                                                                                    out.println("</div>");
                                                                                                }
//fim tratamento localizacao
//inicio tratamento data
                                                                                                out.println("<div class=\"atributo\">");
                                                                                                out.println("Data:");

                                                                                                if (!data.isEmpty()) {

                                                                                                    for (int j = 0; j < data.size(); j++) {
                                                                                                        if (!data.get(j).equalsIgnoreCase("Ano-Mês-Dia")) {
                                                                                                            out.print("<div class=\"valor\">" + data.get(j).trim().replaceAll("[A-Z,a-z,ê,Ê]", " ").replaceAll(" -", "")
                                                                                                                    + "</div>");
                                                                                                        }
                                                                                                    } //fim for

                                                                                                } else {
                                                                                                    out.print("N&atilde;o documentado");
                                                                                                }
                                                                                                out.println("</div>");
//fim tratamento data

//inicio tratamento repositorio
                                                                                                if (!nomeRepositorio.isEmpty()) {

                                                                                                    out.println("<div class=\"atributo\">"
                                                                                                            + "Reposit&oacute;rio: " + nomeRepositorio
                                                                                                            + "</div>");
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
                con.close(); //fechar conexao
            }

%>