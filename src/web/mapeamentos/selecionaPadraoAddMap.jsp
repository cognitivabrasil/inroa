<%-- 
    Document   : addMapeamento
    Created on : 01/10/2010, 15:46:40
    Author     : Marcos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../testaSessaoNovaJanela.jsp"%>
<%@include file="../conexaoBD.jsp"%>
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
        <script language="JavaScript" type="text/javascript" src="./funcoesMapeamento.js">
            //necessario para usar o funcionamento
        </script>
    </head>
    <body>
        <div id="page">

            <div class="subTitulo-center">&nbsp;Adicionar novo mapeamento</div>
            <div class="subtitulo">Selecione o padr&atilde;o de metadados que deseja mapear para o OBAA</div>
            <div class="EspacoAntes">&nbsp;</div>
            <form name="adicionarMap" action="adicionaMapBranco.jsp" method="post">
            <div class="Mapeamento">
                <div class="Legenda">
                    Padr&otilde;es cadastrados:
                </div>

                <div class="Valor" id="padroes">
                    <%
                String sql = "SELECT id, nome FROM padraometadados ORDER BY nome;";
                Statement stm = con.createStatement();
                ResultSet rs = stm.executeQuery(sql);
                out.println("<select name='padrao' id='padrao' onFocus=\"this.className='inputSelecionado'\" onBlur=\"this.className=''\">");
                while (rs.next()) {
                    out.println("<option value=" + rs.getInt("id") + ">" + rs.getString("nome"));
                }
                out.println("</select>");

                    %>

                </div>

                <div class="Legenda">
                        Nome do mapeamento:
                    </div>
                    <div class="Valor" id="tipoMap">
                        <select name='tipoMap' id='tipoMap' onFocus="this.className='inputSelecionado'" onBlur="this.className=''" onchange="preencheDescricaoTM(this.value, 'divDesc')">
                            <option value="" selected >Selecione
                                <%
                            String sqlTipMap = "SELECT t.id, t.nome, t.descricao FROM tipomapeamento t;";
                            ResultSet rsTipo = stm.executeQuery(sqlTipMap);

                            //pega o proximo resultado retornado pela consulta sql
                            while (rsTipo.next()) {
                                out.println("<option value=" + rsTipo.getInt("id") + ">" + rsTipo.getString("nome"));
                            }
                                %>
                            
                        </select>
                                <a onclick="javascript:window.location='addTipoMap.jsp'">
                                    Adicionar nome</a>
                    </div>
                    <div class="Legenda">
                        Descrição:
                    </div>
                    <div class="Valor">

                        <div id="divDesc">Selecione o nome do mapeamento e veja aqui sua descri&ccedil;&atilde;o.</div>
                    </div>

                    <div class="Legenda">
                    Criar a partir de um existente:
                </div>

                <div class="Valor" id="padroes">
                    <%
                String sql2 = "SELECT p.nome as nome_padrao, t.nome as tipo_map, t.id as id_map, p.id as id_padrao"
                        + " FROM mapeamentos m, padraometadados p, tipomapeamento t"
                        + " WHERE m.tipo_mapeamento_id = t.id AND m.padraometadados_id = p.id"
                        + " GROUP BY t.id, p.id, t.nome, p.nome;";
                ResultSet rs2 = stm.executeQuery(sql2);
                out.println("<select name='existente' id='existente' onFocus=\"this.className='inputSelecionado'\" onBlur=\"this.className=''\">");
                out.println("<option selected value=''> N&atilde;o");
                while (rs2.next()) {
                    if(rs2.isFirst())
                        out.println("<option value='' disabled>- Padr&atilde;o / Nome do mapeamento");
                    out.println("<option value="+rs2.getString("id_padrao")+";;"+rs2.getString("id_map")+">"
                            + rs2.getString("nome_padrao")+" / "+ rs2.getString("tipo_map"));
                }
                out.println("</select>");

                    %>

                </div>


                <div class="Buttons">

                    <input class="BOTAO" id="cancelar" onclick="javascript:window.close();" value="Cancelar" type="button" class="CancelButton"/>
                    <input class="BOTAO" type="submit" value="Pr&oacute;ximo >" name="submit" />
                </div>
                



            </div>
                            
            </form>
           
        </div>
    </body>
</html>