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
            <font size="3%" color=black>Lista de Mapeamentos Cadastrados na Federa&ccedil;&atilde;o</font>
        </th>
    </tr>

    <tr style="background-color: #AEC9E3">
        <th width="10%">Opera&ccedil;&otilde;es</th>

        <th width="20%">Padrão de Metadados</th>
        <th width="20%">Tipo de mapeamento</th>
        <th width="50%">Descri&ccedil;&atilde;o</th>

    </tr>
    <%

        int linhaMap = 1;
        

        //funcionalidades
        //adicionar, adicionar a partir de um pronto, remover, visualizar/editar
        String sql = "SELECT p.nome as 'nomePadrao', t.nome as 'tipoTipMap', t.descricao, t.id as 'idMap', p.id as 'idPadrao'" +
                "FROM mapeamentos m, padraometadados p, tipomapeamento t " +
                "WHERE m.tipoMapeamento_id=t.id AND m.padraometadados_id=p.id " +
                "GROUP BY t.id, p.id;";
        ResultSet rs = stm.executeQuery(sql);
        //pega o proximo resultado retornado pela consulta sql
        while (rs.next()) {
            if (linhaMap % 2 == 0) {
                yesnocolor = "price-yes";
            } else {
                yesnocolor = "price-no";
            }
            String nomePadrao = rs.getString("nomePadrao");
            String tipoMap = rs.getString("tipoTipMap");
            String descricaoMap = rs.getString("descricao");
            int idTipMap = rs.getInt("idMap");
            int idPadrao = rs.getInt("idPadrao");
    %>
    <tr  class='center'>
        <td class="<%=yesnocolor%>">
            <a title="Excluir" onclick="NewWindow('removerMapeamento.jsp?id=<%=idTipMap%>','','500','200','scrollbars=yes,menubar=no,resizable=yes,toolbar=no,location=no,status=no');">
                <img src="./imagens/ico24_deletar.gif" border="0" width="24" height="24" alt="Excluir" align="middle">
            </a>
            &nbsp;
            <a title="Editar / Visualizar" onclick="NewWindow('./mapeamentos/editaMapeamentos.jsp?tipmap=<%=idTipMap%>&padrao=<%=idPadrao%>','','total','total','scrollbars=yes,menubar=no,resizable=yes,toolbar=no,location=no,status=no');">
                <img src="./imagens/Lapiz-32x32.png" border="0" width="24" height="24" alt="Visualizar" align="middle">
            </a>

        </td>
        <td class="<%=yesnocolor%>">&nbsp;<%=nomePadrao%></td>
        <td class="<%=yesnocolor%>">&nbsp;<%=tipoMap%></td>
        <td class="<%=yesnocolor%>">&nbsp;<%=descricaoMap%></td>
        <%
       linhaMap++;
   }
        %>
</table>
