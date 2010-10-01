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
        <div id="page">
        <%
                    String tipoMap = "";
                    boolean confereVariavelParametro = false;
                    boolean select = false;
                    try {
                        tipoMap = request.getParameter("tipoMap");
                        if (tipoMap.isEmpty()) {
                            select = false;
                        } else {
                            select = true;
                        }
                        confereVariavelParametro = true;
                    } catch (Exception e) {
                        out.print("<script type='text/javascript'>alert('O tipo do mapeamento deve ser informado');</script>" +
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
                            }

                        else{
                            nomeMap = "<input type=\"text\" id=\"nome\" name=\"nome\" maxlength=\"45\" onFocus=\"this.className='inputSelecionado'\" onBlur=\"this.className=''\" />";
                            descricao = "<input type=\"text\" id=\"descricao\" name=\"descricao\" maxlength=\"455\" onFocus=\"this.className='inputSelecionado'\" onBlur=\"this.className=''\" />";

                        }
                            %>
            
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

            </div>
        <%



                        }

        %>
        

        

        </div>
    </body>
</html>
