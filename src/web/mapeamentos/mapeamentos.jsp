<%--
    Document   : mapeamentos
    Created on : 13/09/2010, 18:27:41
    Author     : Marcos
--%>

<%--

Esse código é inserido no adm.jsp. Não deve ser executado diretamente.

--%>



<table class='repositorios-table' cellpadding=3>
    <tr>
        <th colspan=4>
            <font size="3%" color=black>Lista de Mapeamentos</font>
        </th>
    </tr>

    <tr style="background-color: #AEC9E3">
        <th width="10%">Opera&ccedil;&otilde;es</th>

        <th width="20%">Padrão de Metadados</th>
        <th width="20%">Nome do mapeamento</th>
        <th width="50%">Descri&ccedil;&atilde;o</th>

    </tr>
    <%

        int linhaMap = 1;


        //funcionalidades
        //adicionar, adicionar a partir de um pronto, remover, visualizar/editar


        String sql = "SELECT p.nome as nome_padrao, t.nome as tipo_map, t.descricao, t.id as id_map, p.id as id_padrao"+
                    " FROM mapeamentos m, padraometadados p, tipomapeamento t"+
                    " WHERE m.tipo_mapeamento_id=t.id AND m.padraometadados_id=p.id"+
                    " GROUP BY t.id, p.id, t.nome, p.nome, t.descricao;";

        ResultSet rs = stm.executeQuery(sql);
        //pega o proximo resultado retornado pela consulta sql
        while (rs.next()) {
            if (linhaMap % 2 == 0) {
                yesnocolor = "price-yes";
            } else {
                yesnocolor = "price-no";
            }
            String nomePadrao = rs.getString("nome_padrao");
            String tipoMap = rs.getString("tipo_map");
            String descricaoMap = rs.getString("descricao");
            int idTipMap = rs.getInt("id_map");
            int idPadrao = rs.getInt("id_padrao");
    %>
    <tr  class='center'>
        <td class="<%=yesnocolor%>">
            <input type="button" title="Excluir Mapeamento" class="botaoExcluir" name="excluir" id="excluirMapeamento" onclick="NewWindow('./mapeamentos/removerMapeamento.jsp?idTipoMap=<%=idTipMap%>&idPadrao=<%=idPadrao%>','','500','200','scrollbars=yes,menubar=no,resizable=yes,toolbar=no,location=no,status=no');"/>
            &nbsp;
            <input type="button" class="botaoEditar" title="Editar / Visualizar" name="editar" id="excluirPadrao" onclick="NewWindow('./mapeamentos/editaMapeamentos.jsp?tipmap=<%=idTipMap%>&padrao=<%=idPadrao%>','','total','total','scrollbars=yes,menubar=no,resizable=yes,toolbar=no,location=no,status=no');"/>

        </td>
        <td class="<%=yesnocolor%>">&nbsp;<%=nomePadrao%></td>
        <td class="<%=yesnocolor%>">&nbsp;<%=tipoMap%></td>
        <td class="<%=yesnocolor%>">&nbsp;<%=descricaoMap%></td>
        <%
       linhaMap++;
   }
        %>
        <tr class='center'>
                <td>

                    <a title="Adicionar novo mapeamento" onclick="NewWindow('./mapeamentos/selecionaPadraoAddMap.jsp','Cadastro','750','total','scrollbars=yes,menubar=no,resizable=yes,toolbar=no,location=no,status=no');">
                        <img src="./imagens/add-24x24.png" border="0" width="24" height="24" alt="Visualizar" align="middle">
                    </a>

                </td>
                <td colspan="3"class="left bold" style="font-size:110%">
                    &nbsp;&nbsp;
                    <a onclick="NewWindow('./mapeamentos/selecionaPadraoAddMap.jsp','Cadastro','750','total','scrollbars=yes,menubar=no,resizable=yes,toolbar=no,location=no,status=no');">
                        Adicionar novo mapeamento
                    </a>

                </td>
            </tr>
</table>
