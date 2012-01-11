<%--
    Document   : editaMapeamentos
    Created on : 19/08/2010, 16:22:05
    Author     : Marcos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../conexaoBD.jsp"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FEB - Federa&ccedil;&atilde;o de Reposit&oacute;rios Educa Brasil</title>
        <link rel="StyleSheet" href="../css/padrao.css" type="text/css">
        <link href="../imagens/favicon.ico" rel="shortcut icon" type="image/x-icon" />
        <script language="JavaScript" type="text/javascript" src="../scripts/funcoes.js">
            //necessario para usar o ajax
        </script>
        <script language="JavaScript" type="text/javascript" src="../scripts/funcoesMapeamento.js">
             //funcoes javascript que chamam o ajax
        </script>

    </head>

    <%
//testa sessao..

            String usr = (String) session.getAttribute("usuario");
            if (usr == null) {
                out.print("<script type='text/javascript'>" +
                        "alert('Seu tempo logado expirou. Efetua o login novamente.');" +
                        "fechaRecarrega();" +
                        "</script>");
            }
    %>

    <body onUnLoad="apagarMapeamentoBranco(); recarrega();">

        <%
            int tipoMapeamento = 0;
            int idPadrao = 0;
            int linha = 1;
            int linhaCompleta = 1; //conta inclusive os compostos
            String yesnocolor = "";
            String atributoComplementar = "";
            String valorComplementar = "";
            Statement stm = con.createStatement();
            try {
                String tipoMap = request.getParameter("tipmap");

                String padrao = request.getParameter("padrao");
                if (tipoMap.isEmpty() || padrao.isEmpty()) { //se nao foi informado nenhum valor.
                    new Exception();
                }
                tipoMapeamento = Integer.parseInt(tipoMap);
                idPadrao = Integer.parseInt(padrao);
                if (tipoMapeamento < 1 || idPadrao < 1) {//se for informado um valor invalido
                    new Exception();
                }
            } catch (Exception e) {
                out.print("<script type='text/javascript'>alert('Informe o tipo do mapeamento e o padrao de metadados');</script>" +
                        "<script type='text/javascript'>history.back(-1);</script>");
            }

            //postgres ok
            String sqlInfoMapeamento = "SELECT t.nome, t.descricao FROM tipomapeamento t where id=" + tipoMapeamento + ";";
            ResultSet rsInfoMap = stm.executeQuery(sqlInfoMapeamento);
            //pega o proximo resultado retornado pela consulta sql
            rsInfoMap.next();
            String nomeMap = rsInfoMap.getString("nome");
            String descricao = rsInfoMap.getString("descricao");

        %>
        <div id="page">
            <div class="subTitulo-center">&nbsp;Edi&ccedil;&atilde;o / Visualiza&ccedil;&atilde;o de mapeamentos cadastrados</div>
            <div class="subtitulo">Informa&ccedil;&otilde;es gerais</div>

            <div class="Mapeamento">
                <div class="Legenda">
                    Nome do Mapeamento:
                </div>
                <div class="Editar">&nbsp;
                    <input type="button" class="botaoEditar" size="30" name="editar" id="editarMapeamento" onclick="exibeText('tipoMap', <%=tipoMapeamento%>, this)"/>
                </div>
                <div class="Valor" id="tipoMap"><%=nomeMap%></div>


                <div class="Legenda">
                    Descri&ccedil;&atilde;o:
                </div>
                <div class="Editar">&nbsp;
                    <input type="button" class="botaoEditar" size="30" name="editar" id="editarDescricao"  onclick="exibeText('descricao', <%=tipoMapeamento%>, this)"/>
                </div>
                <div class="Valor" id="descricao"><%=descricao%></div>

            </div>


            <%--Tabela que exibe o mapeamento--%>

            <table class='mapeamentos-table' id="tabela" cellpadding=5%>

                <tr style="background-color: #AEC9E3">
                    <th width="30%">Origem</th>

                    <th width="50%">Destino (OBAA)</th>
                    <th width="10%">Complementar</th>
                    <th width="10%">&nbsp;</th>

                </tr>

                <%

//consulta os mapeamentos na base de dados
            //potgres ok
            String sqlPadrao = "SELECT nome FROM padraometadados p WHERE p.id=" + idPadrao + ";";

            //potgres ok
            String sqlMap = "SELECT m.id as id_map, a1.atributo as origem, a2.atributo as destino, a2.id_padrao as id_padrao_destino, m.mapeamento_composto_id" +
                    " FROM atributos a1, mapeamentos m, atributos a2" +
                    " WHERE a1.id=m.origem_id and a2.id=m.destino_id and m.tipo_mapeamento_id=" + tipoMapeamento + " AND m.padraometadados_id=" + idPadrao + "ORDER BY origem;";

            ResultSet rs1 = stm.executeQuery(sqlPadrao);
            //pega o proximo resultado retornado pela consulta sql
            rs1.next();
            String nomePadrao = rs1.getString("nome");
            //rs1.close();
            out.print("<div class=\"subtitulo\">" + nomePadrao + " / <b>OBAA</b></div>");
            out.println("<div id='msgerro' class='textoErro center'></div>");
            out.println("<div class='textoErro center'><b>Aten&ccedil;&atilde;o!</b> Tenha cuidado ao editar este mapeamento, pois pode estar sendo utilizado por mais de um reposit&oacute;rio.</div>");
            ResultSet rs2 = stm.executeQuery(sqlMap);
            //pega o proximo resultado retornado pela consulta sql
            while (rs2.next()) {
                String origem = rs2.getString("origem");
                String destino = rs2.getString("destino");
                int idComplementar = rs2.getInt("mapeamento_composto_id");
                int idMapeamento = rs2.getInt("id_map");
                int idPadraoDestino = rs2.getInt("id_padrao_destino");

                if (linha % 2 == 0) {
                    yesnocolor = "price-yes";
                } else {
                    yesnocolor = "price-no";
                }
                %>

                <tr class='center'>
                    <td class="<%=yesnocolor%>">&nbsp;<%=origem%></td>
                    <td class="<%=yesnocolor%>">
                        <div id='<%="result" + linha%>'><%=destino%></div>
                    </td>



                    <%

                atributoComplementar = "";
                valorComplementar = "";
                if (idComplementar > 0) { //se tiver mapeamento complementar

                    //rodou no postgres, nao consegui fazer o teste pois nao ha mapeamentos compostos na base
                    String sql = "SELECT a.atributo as destino, m.valor FROM mapeamentocomposto m, atributos a WHERE m.id_origem=a.id AND m.id= " + idComplementar + ";";
                    ResultSet rs;
                    Statement stm2 = con.createStatement();
                    rs = stm2.executeQuery(sql);
                    rs.next();//procura a prÃ³xima ocorrencia
                    valorComplementar = rs.getString("valor");
                    atributoComplementar = rs.getString("destino");

                    stm2.close();
                    rs.close();
                    out.println("<td class=\"" + yesnocolor + "\">&nbsp;Sim</td>");

                } else {
                    out.println("<td class=\"" + yesnocolor + "\">&nbsp;-</td>");
                }
                    %>
                    <td class="<%=yesnocolor%>">
                        <input type="button" class="botaoEditar" size="30" name="editar" id="editar"  onclick="exibeSelect('<%="result" + linha%>', '<%=idMapeamento%>', this)"/>

                        <a title="Excluir" onclick="confirmaExclusao(<%=idMapeamento%>,'mapeamentos','msgerro', 'tabela', this.parentNode.parentNode.rowIndex)">

                            <img src="../imagens/ico24_deletar.gif" border="0" width="24" height="24" alt="Excluir" align="middle">
                        </a>
                    </td>
                </tr>
                <%
                if (!atributoComplementar.isEmpty() && !valorComplementar.isEmpty()) {
                %>
                <tr class='center'>
                    <td class="<%=yesnocolor%>"><%=atributoComplementar%></td>
                    <td class="<%=yesnocolor%>"><%=valorComplementar%></td>
                    <td class="<%=yesnocolor%>"></td>
                    <td class="<%=yesnocolor%>"></td>
                </tr>
                <%
                    linhaCompleta++; //se tiver complementar incrementar tambem
                }
                linha++; //incrementar no final do loop
                linhaCompleta++; //incrementar no final do loop
            }


                %>

            </table>
            <script type="text/javascript">
                setLinha(<%=linha%>);
            </script>
            <table class='mapeamentos-table-add' id="tblAdicionar" cellpadding=5%>
                <tr class='left'>
                    
                    <td width="10%">&nbsp;</td>
                    <td width="60%">

                        <a title="Adicionar Mapeamento" onclick="adicionaMap(<%=linhaCompleta%>, <%=idPadrao%>, <%=tipoMapeamento%>);">
                            <img src="../imagens/add-24x24.png" border="0" width="24" height="24" alt="Visualizar" align="middle">
                        </a>


                        &nbsp;&nbsp;
                        <a onclick="adicionaMap(<%=linhaCompleta%>, <%=idPadrao%>, <%=tipoMapeamento%>);">
                            Adicionar Mapeamento
                        </a>

                    </td>
                    <td width="30%" class="right">
                        <input class="BOTAO" id="salvar" onclick="javascript:apagarMapeamentoBranco();" value="Salvar Mapeamentos" type="button" class="CancelButton"/>
                    </td>


                </tr>
            </table>
        </div>
    </body>
</html>
<%
            con.close();

%>
