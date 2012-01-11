<%-- 
    Document   : addMapeamento
    Created on : 01/10/2010, 17:47:20
    Author     : Marcos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../testaSessaoNovaJanela.jsp"%>
<%@include file="../conexaoBD.jsp"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Set"%>
<%
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
%>

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
            //necessario para usar o funcionamento
        </script>

    </head>
    <body>
        <div id="page">
            <%
            String tipoMap = "";
            boolean confereVariavelParametro = false;
            boolean select = false;
            int idPadrao = 0;
            try {
                tipoMap = request.getParameter("tipoMap");
                int idTipoMap = Integer.parseInt(tipoMap);
                if (tipoMap.isEmpty() || idTipoMap <= 0) {
                    select = false;
                } else {
                    select = true;
                }
                String padrao = request.getParameter("padrao");
                idPadrao = Integer.parseInt(padrao);
                if (padrao.isEmpty() || idPadrao <= 0) {
                    out.print("<script type='text/javascript'>alert('O id do padrao deve ser informado');</script>" +
                            "<script type='text/javascript'>fechaRecarrega();</script>");
                }
                confereVariavelParametro = true;
            } catch (Exception e) {
                out.print("<script type='text/javascript'>alert('O tipo do mapeamento e o id do padrao devem ser informados');</script>" +
                        "<script type='text/javascript'>fechaRecarrega();</script>");
                System.err.println("ERRO AO ADIOCIONAR MAPEAMENTO: " + e);

            }

            if (confereVariavelParametro) { //se for informado o valor por parametro entra no codigo.
                String nomeMap = "&nbsp;";
                String descricao = "&nbsp;";
                if (select) {
                    String sqlInfoMapeamento = "SELECT t.nome, t.descricao FROM tipomapeamento t where id=" + tipoMap + ";";
                    ResultSet rsInfoMap = stm.executeQuery(sqlInfoMapeamento);
                    //pega o proximo resultado retornado pela consulta sql
                    rsInfoMap.next();
                    nomeMap = rsInfoMap.getString("nome");
                    descricao = rsInfoMap.getString("descricao");
                } else {
                    nomeMap = "<input type=\"text\" id=\"nome\" name=\"nome\" maxlength=\"45\" onFocus=\"this.className='inputSelecionado'\" onBlur=\"this.className=''\" />";
                    descricao = "<input type=\"text\" id=\"descricao\" name=\"descricao\" maxlength=\"455\" onFocus=\"this.className='inputSelecionado'\" onBlur=\"this.className=''\" />";

                }
                int tipoMapeamento = 0;

                int linha = 1;
                int linhaCompleta = 1; //conta inclusive os compostos
                String yesnocolor = "";
                String atributoComplementar = "";
                String valorComplementar = "";


            %>
            <div id="mapeamento">
                <div class="subTitulo-center">&nbsp;Edi&ccedil;&atilde;o / Visualiza&ccedil;&atilde;o de mapeamentos cadastrados</div>
                <div class="subtitulo">Informa&ccedil;&otilde;es gerais</div>

                <div class="Mapeamento">
                    <div class="Legenda">
                        Nome do Mapeamento:
                    </div>
                    <div class="Editar">&nbsp;
                        <input type="button" class="botaoEditar" size="30" name="editar" id="editarMapeamento" onclick="exibeText('tipoMap', <%=tipoMap%>, this)"/>
                    </div>
                    <div class="Valor" id="tipoMap"><%=nomeMap%></div>


                    <div class="Legenda">
                        Descri&ccedil;&atilde;o:
                    </div>
                    <div class="Editar">&nbsp;
                        <input type="button" class="botaoEditar" size="30" name="editar" id="editarDescricao"  onclick="exibeText('descricao', <%=tipoMap%>, this)"/>
                    </div>
                    <div class="Valor" id="descricao"><%=descricao%></div>
                    <div class="Buttons">

                        <input type="button" value="< Voltar" onclick="javascript:history.go(-1);"/>
                        <input class="BOTAO" type="button" value="Pr&oacute;ximo >" name="salvaTipoMap" onclick="salvaTipoMap()" />
                    </div>
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

                    //if (idPadrao > 0 && tipoMapeamento > 0) { //se for para editar, consulta o mapeamento existente
                    String sqlPadrao = "SELECT nome FROM padraometadados p WHERE p.id=" + idPadrao + ";";
                    String sqlMap = "SELECT atributo as origem FROM atributos WHERE id_padrao=" + idPadrao + ";";


                    //arrumar as funcoes que salvam
                    //fazer uma funcao para salvar o nome e descrição.
                    //passar por parametro se é novo map ou edicao




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

                        if (linha % 2 == 0) {
                            yesnocolor = "price-yes";
                        } else {
                            yesnocolor = "price-no";
                        }
                    %>

                    <tr class='center'>
                        <td class="<%=yesnocolor%>">&nbsp;<%=origem%></td>
                        <td class="<%=yesnocolor%>">
                            <div id='<%="result" + linha%>'>&nbsp;
                            </div>
                        </td>


                        <td class="<%=yesnocolor%>">+</td>

                        <td class="<%=yesnocolor%>">
                            <input type="button" class="botaoAdicionar" size="30" name="editar" id="editar"  onclick="exibeSelect('<%="result" + linha%>', '0', this)"/>
                            <a title="Excluir" onclick="confirmaExclusao(0,'mapeamentos','msgerro')">

                                <img src="../imagens/ico24_deletar.gif" border="0" width="24" height="24" alt="Excluir" align="middle">
                            </a>
                        </td>
                    </tr>


                    <%

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
                        <td width="10%">&nbsp;</td>
                        <td width="80%">

                            <a title="Adicionar Mapeamento" onclick="">
                                <img src="../imagens/add-24x24.png" border="0" width="24" height="24" alt="Visualizar" align="middle">
                            </a>


                            &nbsp;&nbsp;
                            <a onclick="adicionaMap(<%=linhaCompleta%>, <%=idPadrao%>, <%=tipoMapeamento%>);">
                                Adicionar Mapeamento
                            </a>

                        </td>


                    </tr>
                </table>




                <%



                }

                %>


            </div>

        </div>
    </body>
</html>
