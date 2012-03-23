<%-- 
    Document   : editaPadrao
    Created on : 01/10/2010, 10:52:12
    Author     : Marcos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../../testaSessaoNovaJanela.jsp"%>
<%@include file="../../conexaoBD.jsp"%>
<%
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title>FEB - Federa&ccedil;&atilde;o de Reposit&oacute;rios Educa Brasil</title>

        <link rel="StyleSheet" href="../../css/padrao.css" type="text/css">
        <link href="../../imagens/favicon.ico" rel="shortcut icon" type="image/x-icon" />

        <script language="JavaScript" type="text/javascript" src="../../scripts/funcoes.js"></script>
        <script language="JavaScript" type="text/javascript" src="funcoesPadrao.js"></script>

        <script type="text/javascript" src="../../scripts/validatejs.js"></script>
        <script type="text/javascript">
            var myForm = new Validate();
            myForm.addRules({id:'nome',option:'required',error:'* Voc&ecirc; deve informar o nome do padr&atilde;o de metadados!'});
            myForm.addRules({id:'atributos',option:'required',error:'* Voc&ecirc; deve informar os atributos do padr&atilde;o separados por ; (ponto e virgula)!'});
            myForm.addRules({id:'metPrefix',option:'required',error:'* Voc&ecirc; deve informar o MetadataPrefix. Ex.: oai_obaa. &Eacute; utilizado no OAI-PMH.'});
            myForm.addRules({id:'namespace',option:'required',error:'* Voc&ecirc; deve informar o namespace!'});
        </script>

    </head>
    <body onUnLoad="recarrega()">
        <div id="page">

            <%
            String idPadrao = "";
            boolean confereVariavelParametro = false;
            Statement stm = con.createStatement();
            try {
                idPadrao = request.getParameter("id");
                if (idPadrao.isEmpty()) {
                    out.print("<script type='text/javascript'>alert('Informe o id do padrao de metadados');</script>" +
                            "<script type='text/javascript'>fechaRecarrega();</script>");
                }
                confereVariavelParametro = true;

            } catch (NullPointerException n) {
                out.print("<p class='textoErro'>Informe o id do padr&atilde;o de metadados</p>");
                out.print("<script type='text/javascript'>alert('Informe o id do padrao de metadados');</script>" +
                        "<script type='text/javascript'>fechaRecarrega();</script>");

            }

            if (confereVariavelParametro) { //se for informado o id por parametro entra no codigo.

                String sql = "SELECT nome, metadata_prefix, name_space FROM padraometadados WHERE id=" + idPadrao;
                ResultSet rs = stm.executeQuery(sql);
                //pega o proximo resultado retornado pela consulta sql
                rs.next();
                String atributoNome = "nome";
                String atributoMetadata = "metadata_prefix";
                String atributoNameSpace = "name_space";

                String nome = rs.getString(atributoNome);
                String metadata_prefix = rs.getString(atributoMetadata);
                String nameSpace = rs.getString(atributoNameSpace);
                
            %>

            <div class="subTitulo-center">&nbsp;Edi&ccedil;&atilde;o / Visualiza&ccedil;&atilde;o de padr&otilde;es cadastrados</div>
            <div class="subtitulo">Informa&ccedil;&otilde;es gerais</div>

            <div class="Mapeamento">
                <div class="Legenda">
                    Nome do Padr&atilde;o:
                </div>
                <div class="Editar">&nbsp;
                    <input type="button" class="botaoEditar" size="30" name="editarNome" id="editarNome" onclick="exibeText('nome', <%=idPadrao%>, '<%=atributoNome%>', this)"/>
                </div>
                <div class="Valor" id="nome"><%=nome%></div>


                <div class="Legenda">
                    metadataPrefix:
                </div>
                <div class="Editar">&nbsp;
                    <input type="button" class="botaoEditar" size="30" name="editarMetadataPrefix" id="editarMetadataPrefix"  onclick="exibeText('metadataprefix', <%=idPadrao%>, '<%=atributoMetadata%>', this)"/>
                </div>
                <div class="Valor" id="metadataprefix"><%=metadata_prefix%></div>

                <div class="Legenda">
                    nameSpace:
                </div>
                <div class="Editar">&nbsp;
                    <input type="button" class="botaoEditar" size="30" name="editarNameSpace" id="editarNameSpace"  onclick="exibeText('namespace',<%=idPadrao%>, '<%=atributoNameSpace%>', this)"/>
                </div>
                <div class="Valor" id="namespace"><%=nameSpace%></div>

            </div>

            <div class="subtitulo">Atributos do padr&atilde;o</div>
            <div id='msgerro' class='textoErro center'></div>
       <!--Tabela que apresenta os atributos-->
            <table class='mapeamentos-table' id="tabela" cellpadding=5%>

                <tr style="background-color: #AEC9E3">
                    <th width="30%">Atributo</th>
                    <th width="10%">Opera&ccedil;&otilde;es</th>
                </tr>

            <%
            int linha = 1;
            String yesnocolor = "";

             String sqlAtributos = "SELECT id, atributo FROM atributos WHERE id_padrao="+idPadrao+" ORDER BY atributo";
             ResultSet rs2 = stm.executeQuery(sqlAtributos);
            //pega o proximo resultado retornado pela consulta sql
            while (rs2.next()) {
                int idAtributo = rs2.getInt("id");
                String atributo = rs2.getString("atributo");

                if (linha % 2 == 0) { //para ficar uma linha da tabela de cada cor
                    yesnocolor = "price-yes";
                } else {
                    yesnocolor = "price-no";
                }

                %>
                <tr class='center'>
                         <td class="<%=yesnocolor%>">
                             <div id="atributo<%=linha%>"><%=atributo%></div>
                         </td>
                         <td class="<%=yesnocolor%>">
                        <input type="button" class="botaoEditar" size="30" name="editar" id="editar"  onclick="exibeTextAtributo('atributo<%=linha%>', '<%=idAtributo%>', this)"/>
                        <a title="Excluir" onclick="confirmaExclusao('<%=idAtributo%>', 'msgerro', this.parentNode.parentNode.rowIndex)">
                            <img src="../../imagens/ico24_deletar.gif" border="0" width="24" height="24" alt="Excluir" align="middle">
                        </a>
                        
                    </td>
                </tr>
            <%

                linha++; //incrementar no final do loop
            }
            %>



                
            </table>
<!--funcao que seta a linha-->
<script type="text/javascript">
                setLinha(<%=linha%>);
            </script>

 <table class='mapeamentos-table-add' id="tblAdicionar" cellpadding=5%>
                <tr class='center'>
                    
                    <td width="10%">&nbsp;</td>
                    <td width="80%">

                        <a title="Adicionar Atributo" onclick="">
                            <img src="../../imagens/add-24x24.png" border="0" width="24" height="24" alt="Visualizar" align="middle">
                        </a>


                        &nbsp;&nbsp;
                        <a onclick="adicionaAtributo(<%=idPadrao%>, this)">
                            Adicionar Atributo
                        </a>

                    </td>


                </tr>
            </table>
            <%
            } //fecha if que testa se o id foi informado por parametro
            %>
        </div>
    </body>
</html>