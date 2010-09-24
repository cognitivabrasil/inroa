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

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="StyleSheet" href="css/padrao.css" type="text/css">
        <title>FEB – Federa&ccedil;&atilde;o de Reposit&oacute;rios Educa Brasil</title>
    </head>
    <body>


        <%
        //potgres ok
            String sql = "SELECT r.nome, CASE r.nome WHEN 'todos' THEN l.dn ELSE ('ou='||i.nome_na_federacao||','||l.dn) END AS dn, l.ip, l.login, l.senha, l.porta, i.nome_na_federacao " +
                    " FROM repositorios r, info_repositorios i, ldaps l " +
                    " WHERE r.id=" + request.getParameter("repositorio") +
                    " AND i.ldap_destino=l.id " +
                    " AND r.id=i.id_repositorio";


            ResultSet res = stm.executeQuery(sql);
            //pega o proximo resultado retornado pela consulta sql
            res.next();

            String ipServidor = res.getString("ip"); //pega da base de dados o ip do repositorio
            String searchBase = res.getString("dn"); //recebe o dn determinado na base de dados
            String login = res.getString("login");
            String senha = res.getString("senha");
            int porta = res.getInt("porta");


            String atributoBusca = request.getParameter("atributo"); //recebe o atributo que sera efetuada a busca, informado no formulario
            String palavraChave = request.getParameter("key"); //recebe a consulta informada no formulario
            //Se a busca nao for preenchida, volta a pagina para ser preenchida
            if (palavraChave.isEmpty()) {
                out.print("<script type='text/javascript'>alert('Nenhuma consulta foi informada');</script>" +
                        "<script type='text/javascript'>history.back(-1);</script>");
            }


            int numObjetosEncontrados = 0; //inicializa int que tera o numero de resultados retornados
            ArrayList<String> resultadoBusca = new ArrayList<String>(); //ArrayList que recebera o resultado da busca MySQL

            //chama o metodo de busca inteligente
            Recuperador recuperador = new Recuperador();
            try { 
                resultadoBusca = recuperador.search(palavraChave, con);//efetua a busca com o metodo de recuperacao de informacoes
                //resultadoBusca.add("obaa0001");
                //resultadoBusca.add("obaa0002");
                //resultadoBusca.add("obaa0003");
                numObjetosEncontrados = resultadoBusca.size(); //armazena o numero de objetos
            } catch (SQLException e) {
                System.out.println("ERRO NO SQL: "+e);
                numObjetosEncontrados = 0;
                }
            
        %>

        <table width="100%" border="0">
            <tr>
                <td>
                    <div align="center">
                        <strong>
                            <font size="4">
                                Resultado da Pesquisa
                            </font>
                        </strong>
                    </div>
                </td>
            </tr>
            <tr>
                <td>
                    <table width="100%" border="0" cellpadding="0" cellspacing="0">
                        <tr>
                            <td width="20%">
                                <div align="left">
                                    <font size="2">
                                        &nbsp;<a href="index.jsp">Efetuar nova consulta</a>
                                    </font>
                                </div>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td>
                    <table width="100%">
                        <tr>
                            <td class="barra-escura">
                                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                    <tr>
                                        <td width="70%" height="25">
                                            <font size="2">
                                                &nbsp;Pesquisar por <strong><%=palavraChave%></strong> no reposit&oacute;rio
                                            </font>
                                        </td>
                                        <td width="30%">
                                            <div align="right">
                                                <font size="2">
                                                    Total de <strong><%=numObjetosEncontrados%></strong> objeto(s) encontrado(s)&nbsp;
                                                </font>
                                            </div>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td height="21">
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
                if (numObjetosEncontrados > 50) {//limitar em 50 objetos a busca
                    numObjetosEncontrados = 50;
                }
                for (int result = 0; result < numObjetosEncontrados; result++) {//percorre o resultado da ferramenta de RI(recuperacao de informacao)

                    ArrayList resultHash = new ArrayList(); //ArrayList que recebera o resultado da busca LDAP
                    String consulta = "obaa_entry=" + resultadoBusca.get(result);//concatena o entry retornado pela busca com obaa_entry=
                    System.out.println(consulta);
                    String[] attributos = {"obaaTitle","obaa_entry","obaaEducationalDescription","obaaLocation","obaaResourceEntry","obaaIdentifier","obaaDate"};
                    Consultar busca = new Consultar(ipServidor, consulta, searchBase, login, senha, porta, attributos);
                    resultHash = busca.getResultado(); //solicita o resultado da consulta
                    
                    for (int count = 0; count < resultHash.size(); count++) {//percorre o resultado do ldap

                        HashMap internoHash = new HashMap();
                        internoHash = (HashMap) resultHash.get(count);
                        String dn = null;
                        String ipResultado = null;
                        String nomeServidorResultado = null;


                        //se nao foi escolhido a busca na federacao entra no if
                        if (res.getString("nome").equalsIgnoreCase("todos")) {
                            //armazena na variavel o identificador com o caminho da arvore
                            String idRaiz = internoHash.get("obaaIdentifierRaiz").toString();
                            //cria um vetor com os dados do dn
                            String[] idRaizVet = idRaiz.split(",");
                            //string que recebera o caminho da arvore ldap sem o identificador e separa pelo =
                            idRaizVet = idRaizVet[1].split("=");

                            //estancia uma variavel da classe testaNaBase passando o nome do repositorio, ex. pgie2
                            ConsultaNomeFederacao nomeBase = new ConsultaNomeFederacao(idRaizVet[1]);
                            //recebe o nome do repositio onde o objeto foi encontrado
                            nomeServidorResultado = nomeBase.getNomeEncontrado();

                            //recebe o ip do repositorio
                            ipResultado = nomeBase.getIpEncontrado();
                            //recebe o dn do repositorio
                            dn = nomeBase.getDnEncontrado();

                            //se a pesquisa nao foi na federacao pega os dados diretamente
                        } else {
                            nomeServidorResultado = res.getString("nome").toUpperCase();
                            ipResultado = ipServidor;
                            dn = searchBase;
                        }

                                    %>

                                    <table width="90%" border="0" align="center">

                                        <tr>
                                            <td>
                                                <%
                        //String[] id = internoHash.get("obaaIdentifier").toString().trim().split(";; ");

                        String title = internoHash.get("obaaTitle").toString().trim();
                        String identificador = internoHash.get("obaa_entry").toString().trim();


                                                %>
                                                <font size="2.7" face="Verdana, Arial, Helvetica, sans-serif">
                                                    <strong>
                                                        <a href='infoDetalhada.jsp?id=<%=identificador%>&ip=<%=ipResultado%>&dn=<%=dn%>'>
                                                            <%

                        try {
                            //out.println("<font size=\"3\" face=\"Verdana, Arial, Helvetica, sans-serif\"><strong><a href=infoDetalhada.jsp?id=" + identificador + "&ip=" + ipResultado + "&dn=" + dn.replace(" ", "") + ">" + title + "</font></strong>");
                            //se tiver mais de um titulo imprimir em linhas separadas
                            if (internoHash.containsKey("obaaTitle")) {
                                String[] tempObaa = internoHash.get("obaaTitle").toString().split(";; ");
                                for (int kk = 0; kk < tempObaa.length; kk++) {
                                    //apos o primeiro elemento colocar um "<BR>"
                                    if (kk > 0) {
                                        out.print("<br>");
                                    }

                                    out.print("- " + tempObaa[kk].trim());
                                }
                            }

                        } catch (Exception e) {
                            out.print("<p>" + e + "</p>");
                        }
                                                            %>
                                                        </a></strong></font>
                                            </td>
                                        </tr>


                                        <tr>
                                            <td>
                                                &nbsp;

                                                <br>
                                            </td>
                                        </tr>


                                        <% if (internoHash.containsKey("obaaEducationalDescription")) {%>
                                        <tr>
                                            <td colspan="2">
                                                <font size="2" face="Verdana, Arial, Helvetica, sans-serif">
                                                    <%=internoHash.get("obaaEducationalDescription")%>
                                                </font>
                                            </td>
                                        </tr>
                                        <%
                        }
                                        %>
                                        <tr>

                                            <td>
                                                <font size="2" face="Verdana, Arial, Helvetica, sans-serif">Localiza&ccedil;&atilde;o:</font>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td>
                                                <font size="2" face="Verdana, Arial, Helvetica, sans-serif">

                                                    <%
                        if (internoHash.containsKey("obaaLocation") || internoHash.containsKey("obaaResourceEntry") || internoHash.containsKey("obaaIdentifier")) {
                                                    %>

                                                    <%
                            String obaaTemp = "";
                            if (internoHash.containsKey("obaaLocation")) {
                                obaaTemp = internoHash.get("obaaLocation").toString().trim();
                            } else if (internoHash.containsKey("obaaResourceEntry")) {
                                obaaTemp = internoHash.get("obaaResourceEntry").toString().trim();
                            } else if (internoHash.containsKey("obaaIdentifier")) {
                                String temp[] = internoHash.get("obaaIdentifier").toString().split(";; ");
                                obaaTemp = "";

                                for (int i = 0; i < temp.length; i++) {
                                    if (temp[i].contains("http")) {
                                        if (i == 0) {
                                            obaaTemp = temp[i];
                                        } else {
                                            obaaTemp = obaaTemp + ";; " + obaaTemp;
                                        }
                                    }
                                }
                            }

                            String temp[] = obaaTemp.split(";; ");

                            for (int kk = 0; kk < temp.length; kk++) {
                                //colocar aqui as tag que mostraram o link e de estrutura da pagina
                                                    %>

                                                    <a href="<%=temp[kk].trim()%>" target="_new"><%=temp[kk].trim()%></a>

                                                    <%
                            } //fecha for

                        } else {
                                                    %>
                                                    N&#227;o documentado
                                                    <%            }
                                                    %>


                                                </font>
                                            </td>
                                        </tr>
                                        <%

                        if (internoHash.get("obaaDate") != null) {
                                        %>

                                        <tr>
                                            <td>
                                                <font size="2" face="Verdana, Arial, Helvetica, sans-serif">
                                                    Data:
                                                    <%
                            if (!internoHash.get("obaaDate").toString().equalsIgnoreCase("Ano-Mês-Dia")) {

                                String[] tempObaa = internoHash.get("obaaDate").toString().split(";; ");
                                for (int kk = 0; kk < tempObaa.length; kk++) {
                                    //apos o primeiro elemento colocar um "<BR>"
                                    if (kk > 0) {
                                        out.print("<BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                    }
                                    out.print(tempObaa[kk].replaceAll("[A-Z,a-z,ê,Ê]", " ").replaceAll(" -", ""));
                                } //fim for


                                //out.print(internoHash.get("obaaDate").toString().replaceAll("[A-Z,a-z,ê,Ê]", " ").replaceAll(" -", ""));
                            } else {
                                out.print("N&atilde;o documentado");
                            }
                                                    %>
                                                </font>
                                            </td>

                                        </tr>
                                        <tr>
                                            <%}%>
                                            <td>
                                                <font size="2" face="Verdana, Arial, Helvetica, sans-serif">
                                                    Servidor:
                                                    <%=nomeServidorResultado%>

                                                </font></td>
                                        </tr>


                                        <tr><td>&nbsp;</td></tr>
                                        <tr><td>&nbsp;</td></tr>

                                    </table>

                                    <%
                    }
                }
            }

                                    %>
                                    <p>&nbsp;</p>
                                </div>
                            </td>

                        </tr>
                        <tr>
                            <td height="21" bgcolor="#AEC9E3">&nbsp;</td><!--Rodape-->
                        </tr>
                    </table></td>
            </tr>
        </table>
        <%@include file="googleAnalytics"%>
    </body>
</html>