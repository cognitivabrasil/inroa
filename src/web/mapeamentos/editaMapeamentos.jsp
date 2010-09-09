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
        <title>FEB – Federa&ccedil;&atilde;o de Reposit&oacute;rios Educa Brasil</title>
        <link rel="StyleSheet" href="../css/padrao.css" type="text/css">
        <link href="../imagens/favicon.ico" rel="shortcut icon" type="image/x-icon" />
    </head>
    <body>
        <div id="page">
            <div class="subTitulo-center">&nbsp;Edi&ccedil;&atilde;o / Visualiza&ccedil;&atilde;o de mapeamentos cadastrados</div>
            <table class='mapeamentos-table' cellpadding=5%>
            
            <tr style="background-color: #AEC9E3">
                <th width="30%">Origem</th>

                <th width="30%">Destino</th>
                <th width="10%">Complementar</th>
                <th width="30%">&nbsp;</th>

            </tr>
            
            <%
                int tipoMapeamento = 0;
                int idPadrao = 0;
                int linha = 1;
                String yesnocolor = "";
                String atributoComplementar="";
                String valorComplementar="";
                try {
                    String tipoMap = request.getParameter("tipmap");
                    String padrao = request.getParameter("padrao");
                    if (tipoMap.isEmpty() || padrao.isEmpty()) { //se não foi informado nenhum valor.
                        new Exception();
                    }
                    tipoMapeamento = Integer.parseInt(tipoMap);
                    idPadrao = Integer.parseInt(padrao);
                    if (tipoMapeamento < 1 || idPadrao < 1) {//se for informado um valor invalido
                        new Exception();
                    }
                } catch (Exception e) {
                    out.println("Informe o tipo do mapeamento e o padrão de metadados.");
                    //out.print("<script type='text/javascript'>alert('Nenhuma consulta foi informada');</script>" +
                    //                   "<script type='text/javascript'>history.back(-1);</script>");
                    }

                String sqlPadrao = "SELECT nome FROM padraometadados p WHERE p.id=" + idPadrao + ";";
                String sqlMap = "SELECT a1.atributo as origem, a2.atributo as destino, m.mapeamentoComposto_id" +
                        " FROM atributos a1, mapeamentos m, atributos a2" +
                        " WHERE a1.id=m.origem_id and a2.id=m.destino_id and m.tipoMapeamento_id=" + tipoMapeamento + " AND m.padraometadados_id=" + idPadrao + ";";

                ResultSet rs1 = stm.executeQuery(sqlPadrao);
                //pega o proximo resultado retornado pela consulta sql
                rs1.next();
                String nomePadrao = rs1.getString("nome");
                //rs1.close();
                out.print("<div class=\"subtitulo\">" + nomePadrao + " / <b>OBAA</b></div>");
                
                ResultSet rs2 = stm.executeQuery(sqlMap);
                //pega o proximo resultado retornado pela consulta sql
                while (rs2.next()) {
                    String origem = rs2.getString("origem");
                    String destino = rs2.getString("destino");
                    int idComplementar = rs2.getInt("mapeamentoComposto_id");

                    if (linha % 2 == 0) {
                        yesnocolor = "price-yes";
                    } else {
                        yesnocolor = "price-no";
                    }
            %>
            
            <tr class='center'>
                <td class="<%=yesnocolor%>">&nbsp;<%=origem%></td>
                <td class="<%=yesnocolor%>">&nbsp;<%=destino%></td>
                
            
            
                <%

                    atributoComplementar="";
                    valorComplementar="";
                    if (idComplementar > 0) { //se tiver mapeamento complementar

                        String sql = "SELECT a.atributo as destino, m.valor " + "FROM mapeamentoComposto m, atributos a " + "WHERE m.id_origem=a.id " + "AND m.id=" + idComplementar + ";";
                        ResultSet rs;
                        Statement stm2 = con.createStatement();
                        rs = stm2.executeQuery(sql);
                        rs.next();//procura a próxima ocorrencia
                        valorComplementar = rs.getString("valor");
                        atributoComplementar = rs.getString("destino");
                        out.print("<BR>" + rs.getString("destino") + "=" + rs.getString("valor")); //adiciona o mapeamento complementar
                        stm2.close();
                        rs.close();
                        out.println("<td class=\""+yesnocolor+"\">&nbsp;Sim</td>");

                    }
                    else
                        out.println("<td class=\""+yesnocolor+"\">&nbsp;-</td>");
                %>
                <td class="<%=yesnocolor%>">Editar</td>
            </tr>
            <%
            if(!atributoComplementar.isEmpty() && !valorComplementar.isEmpty()){
                %>
                <tr class='center'>
                    <td class="<%=yesnocolor%>"><%=atributoComplementar%></td>
                    <td class="<%=yesnocolor%>"><%=valorComplementar%></td>
                    <td class="<%=yesnocolor%>"></td>
                    <td class="<%=yesnocolor%>"></td>
                </tr>
            <%
                
            }
             linha++;
                      }


            %>
            
            </table>
        </div>
    </body>
</html>
<%
            con.close();
%>
