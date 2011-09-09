<%-- 
    Document   : addTipoMap
    Created on : 09/10/2010, 17:26:33
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
            <%
            String nome = "";
            String descricao = "";
            boolean exibeForm = true;
            try {
                nome = request.getParameter("nome");
                if (nome.isEmpty()) {
                    exibeForm = true;
                } else {
                    exibeForm = false;
                }
                if (!exibeForm) {
                    descricao = request.getParameter("descricao");
                    if (descricao.isEmpty() && nome.isEmpty()) {
                        out.print("<script type='text/javascript'>alert('Todos os campos devem ser preenchidos!');</script>" +
                                "<script type='text/javascript'>history.back(-1);</script>");
                        exibeForm = true;
                    }
                }
            } catch (NullPointerException n) {
                exibeForm = true;
            }

            if (exibeForm) {
            %>

            <div class="subTitulo-center">&nbsp;Adicionar novo mapeamento</div>
            <div class="subtitulo">Selecione o padr&atilde;o de metadados que deseja mapear para o OBAA</div>
            <div class="EspacoAntes">&nbsp;</div>
            <form name="adicionarMap" action="" method="post">
                <div class="Mapeamento">
                <div class="TextoInformativo">
                Informe um nome e uma descri&ccedil;&atilde;o para o novo tipo de mapeamento.
                <p>Exemplo
                    <BR>&nbsp;Nome: &nbsp;&nbsp;&nbsp;&nbsp;"repOBAA" <BR>Destri&ccedil;&atilde;o: "mapeamento especifico para o reposit&oacute;rio OBAA.</p>
                </div>
                
                    <div class="Legenda">
                        <div> Nome:</div>
                    </div>

                    <div class="Valor" id="tipoMap">
                        <input type="text" id="nome" name="nome" maxlength="45" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>


                    <div class="Legenda">
                        Descrição:
                    </div>
                    <div class="Valor">
                        <input type="text" id="descricao" name="descricao" maxlength="1000" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>

                    <div class="Buttons">
                        <input type="button" value="&lArr; Voltar" onclick="javascript:history.go(-1);"/>
                        <input class="BOTAO" id="cancelar" onclick="javascript:window.close();" value="Cancelar" type="button" class="CancelButton"/>
                        <input class="BOTAO" type="submit" value="Adicionar >" name="submit" />
                    </div>




                </div>

            </form>
            <%            } else {
                String sql = "INSERT INTO tipomapeamento (nome, descricao) VALUES (?,?)";
                try {
                    PreparedStatement stmt = con.prepareStatement(sql);
                    stmt.setString(1, nome);
                    stmt.setString(2, descricao);
                    int res = stmt.executeUpdate();
                    if (res > 0) {
                        String redirectURL = "selecionaPadraoAddMap.jsp";
                        response.sendRedirect(redirectURL);
                    } else {
                        exibeForm = true;
                        out.print("<script type='text/javascript'>alert('Nao foi inserido nenhum valor na base de dados!');</script>" +
                                "<script type='text/javascript'>history.back(-1);</script>");

                    }
                } catch (SQLException s) {
                    out.println("ERRO ao adicionar as informa&ccdil;&otilde;es na base de dados: " + s.getMessage());
                    out.print("<script type='text/javascript'>javascript:window.location.reload();</script>");
                }
            }
            %>
        </div>
    </body>
</html>