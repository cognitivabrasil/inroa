<%-- 
    Document   : addMapeamento
    Created on : 01/10/2010, 16:45:17
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
        <script language="JavaScript" type="text/javascript" src="../scripts/funcoes.js">
            //necessario para usar o ajax
        </script>

    </head>
    <body>

        <%
            String idPadrao = "";
            boolean confereVariavelParametro = false;
            try {
                idPadrao = request.getParameter("padrao");
                if (idPadrao.isEmpty()) {
                    out.print("<script type='text/javascript'>alert('O padrao deve ser informado');</script>" +
                            "<script type='text/javascript'>fechaRecarrega();</script>");
                }
                confereVariavelParametro = true;
            } catch (Exception e) {
                out.print("<script type='text/javascript'>alert('O padrao deve ser informado');</script>" +
                        "<script type='text/javascript'>fechaRecarrega();</script>");
                System.err.println("ERRO AO ADIOCIONAR MAPEAMENTO: " + e);

            }
            if (confereVariavelParametro) { //se for informado o valor por parametro entra no codigo.


        %>

        <div id="page">
            <div class="subTitulo-center">&nbsp;Adicionar novo mapeamento</div>
            <div class="subtitulo">Informa&ccedil;&otilde;es gerais</div>
            <form name="adicionarRepositorio" action="addMapeamento.jsp" method="post">
                <div class="Mapeamento">
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
                    <input class="BOTAO" type="button" value="Adicionar novo" name="submit" onclick="javascript:window.location='addMapeamento.jsp?tipoMap=';" />
                    <input class="BOTAO" type="submit" value="Pr&oacute;ximo >" name="submit" />
                </div>
                </div>
            </form>

        </div>

        <%
            }
        %>
    </body>
</html>
