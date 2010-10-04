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
                ResultSet rs = stm.executeQuery(sql);
                out.println("<select name='padrao' id='padrao' onFocus=\"this.className='inputSelecionado'\" onBlur=\"this.className=''\">");
                while (rs.next()) {
                    out.println("<option value=" + rs.getInt("id") + ">" + rs.getString("nome"));
                }
                out.println("</select>");

                    %>

                </div>

                <div class="Legenda">
                        Tipo do mapeamento:
                    </div>
                    <div class="Valor" id="tipoMap">
                        <select name='tipoMap' id='tipoMap' onFocus="this.className='inputSelecionado'" onBlur="this.className=''">
                            <option value="" selected onclick="insereValorDiv('divDesc','Selecione um tipo de mapeamento acima')">Selecione
                                <%
                            String sqlTipMap = "SELECT t.id, t.nome, t.descricao FROM tipomapeamento t;";
                            ResultSet rsTipo = stm.executeQuery(sqlTipMap);

                            out.println("");

                            //pega o proximo resultado retornado pela consulta sql
                            while (rsTipo.next()) {
                                out.println("<option value=" + rsTipo.getInt("id") + " onclick=\"insereValorDiv('divDesc','" + rsTipo.getString("descricao") + "')\">" + rsTipo.getString("nome"));
                            }
                                %>
                            
                        </select>
                    </div>
                    <div class="Legenda">
                        Descrição:
                    </div>
                    <div class="Valor">

                        <div id="divDesc">Selecione o tipo de mapeamento e veja aqui sua descrição.</div>
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