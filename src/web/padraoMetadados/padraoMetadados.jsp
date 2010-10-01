<%--

Esse código é inserido no adm.jsp. Não deve ser executado diretamente.

--%>
<%-- 
    Document   : padraoMetadados
    Created on : 30/09/2010, 14:25:09
    Author     : Marcos
--%>

<script language="JavaScript" type="text/javascript" src="../mapeamentos/funcoesMapeamento.js">
    //necessario para usar o funcionamento
</script>


<table class='repositorios-table' cellpadding=3>
    <tr>
        <th colspan=4>
            <font size="3%" color=black>Lista de Padr&atilde;o de metadados</font>
        </th>
    </tr>

    <tr style="background-color: #AEC9E3">
        <th width="10%">Opera&ccedil;&otilde;es</th>

        <th width="20%">Nome</th>
        <th width="50%">Metadata Prefix</th>
        <th width="20%">nameSpace</th>

    </tr>


    <%
    int linhaPadrao = 1;
    yesnocolor = "";
    String sqlPadrao = "SELECT id, nome, metadata_prefix, name_space from padraometadados ORDER BY nome;";

    ResultSet rsPadrao = stm.executeQuery(sqlPadrao);
    while (rsPadrao.next()) {
        String nome = rsPadrao.getString("nome");
        String metadataPrefix = rsPadrao.getString("metadata_prefix");
        String nameSpace = rsPadrao.getString("name_space");
        int idPadrao = rsPadrao.getInt("id");
        if (linhaPadrao % 2 == 0) {
            yesnocolor = "price-yes";
        } else {
            yesnocolor = "price-no";
        }
    %>


    <tr  class='center'>
        <td class="<%=yesnocolor%>">
            <input type="button" class="botaoExcluir" name="excluir" id="excluirPadrao" onclick="confirmaExclusao(<%=idPadrao%>,'padraometadados','msgerro');"/>
            &nbsp;
            <a title="Editar / Visualizar" onclick="NewWindow('./padraoMetadados/editaPadrao.jsp?id=<%=idPadrao%>','editaPadrao','750','total','scrollbars=yes,menubar=no,resizable=yes,toolbar=no,location=no,status=no');">
                <img src="./imagens/Lapiz-24x24.png" border="0" width="24" height="24" alt="Visualizar" align="middle">
            </a>

        </td>
        <td class="<%=yesnocolor%>">&nbsp;<%=nome%></td>
        <td class="<%=yesnocolor%>">&nbsp;<%=metadataPrefix%></td>
        <td class="<%=yesnocolor%>">&nbsp;<%=nameSpace%></td>

        <% linhaPadrao++;%>

    </tr>
    <%

    } //fim while%>


    <tr class='center'>
        <td>

            <a title="Adicionar novo padr&atilde;o" onclick="NewWindow('addPadrao.jsp','addPadrao','750','650','scrollbars=yes,menubar=no,resizable=yes,toolbar=no,location=no,status=no');">
                <img src="./imagens/add-24x24.png" border="0" width="24" height="24" alt="Visualizar" align="middle">
            </a>

        </td>
        <td colspan="2"class="left bold" style="font-size:110%">
            &nbsp;&nbsp;
            <a onclick="NewWindow('./padraoMetadados/addPadrao.jsp','Cadastro','750','650','scrollbars=yes,menubar=no,resizable=yes,toolbar=no,location=no,status=no');">
                Adicionar novo padr&atilde;o
            </a>
            <div id='msgerro' class='textoErro center'></div>

        </td>

        <%
    linhaPadrao++;
        %>

    </tr>
</table>