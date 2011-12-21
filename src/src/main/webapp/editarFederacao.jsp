<%-- 
    Document   : editarFederacao
    Created on : 14/09/2009, 12:49:22
    Author     : Marcos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="testaSessaoNovaJanela.jsp"%>
<%@include file="conexaoBD.jsp"%>
<%
            request.setCharacterEncoding("UTF-8");
//response.setCharacterEncoding("UTF-8");
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>FEB - Ferramenta Administrativa</title>
        <link rel="StyleSheet" href="css/padrao.css" type="text/css"/>
        <script language="JavaScript" type="text/javascript" src="scripts/funcoes.js"></script>
        <script type="text/javascript" src="./scripts/validatejs.js"></script>
        <link href="imagens/favicon.ico" rel="shortcut icon" type="image/x-icon" />
        <script type="text/javascript">
            var myForm = new Validate();
            myForm.addRules({id:'nome',option:'required',error:'* Voc&ecirc; deve informar o nome do reposit&oacute;rio!'});
            myForm.addRules({id:'descricao',option:'required',error:'* Deve ser informarmada uma descri&ccedil;&atilde;o!'});
            myForm.addRules({id:'urlSF',option:'urlcomip',error:'* Deve ser informada a url da federa&ccedil;&atilde;o que est&aacute; sendo adicionada. Come&ccedil;ando por http://'});
        </script>
    </head>

    <body>
        <div id="page">
            <%
                        boolean formNull = true;
                        try {
                            String editar = request.getParameter("editar");
                            if (editar.equals("sim")) {
                                formNull = false;
                            }
                        } catch (Exception e) {
                            formNull = true;

                        }

                        String id = request.getParameter("id");
                        String sql = "SELECT nome, descricao, url FROM dados_subfederacoes l WHERE l.id=" + id;
                        Statement stm = con.createStatement();
                        ResultSet res = stm.executeQuery(sql);
                        res.next();

                        if (formNull) {

            %>
            <div class="subTitulo-center">&nbsp;Editanto reposit&oacute;rio <%=res.getString("nome")%></div>
            <div class="subtitulo">Informa&ccedil;&otilde;es sobre a subfedera&ccedil;&atilde;o</div>
            <div class="EspacoAntes">&nbsp;</div>
            <form name="editaSubfed" action="editarFederacao.jsp" method="post" onsubmit="return myForm.Apply('MensagemErro')">
                <div class="TextoDivAlerta" id="MensagemErro"><!--Aqui o script colocara a mensagem de erro, se ocorrer--></div>
                <div class="LinhaEntrada">
                    <div class="Label">
                        Nome:
                    </div>
                    <div class="Value">
                        <input type="text" id="name" name="nome" value="<%=res.getString("nome")%>" maxlength="45" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>
                </div>
                <div class="LinhaEntrada">
                    <div class="Label">
                        Descri&ccedil;&atilde;o:
                    </div>
                    <div class="Value">
                        <input name="descricao" id="descricao" type="text" value="<%=res.getString("descricao")%>" maxlength="455" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>
                </div>
                <div class="LinhaEntrada">
                    <div class="Comentario">Ex: http://feb.ufrgs.br/feb</div>
                    <div class="Label">
                        URL da federa&ccedil;&atilde;o:
                    </div>
                    <div class="Value">
                        <input name="urlSF" id="urlSF" value="<%=res.getString("url")%>" type="text" maxlength="200" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>
                </div>

                <input type="hidden" name="id" value="<%=id%>"/>
                <input type="hidden" name="editar" value="sim"/>
                <div class="LinhaEntrada">
                    <div class="Buttons">
                        <input type="button" value="&lArr; Voltar" onclick="javascript:history.go(-1);"/>
                        <input type="submit" value="Gravar &rArr;" name="submit" />

                    </div>
                </div>

            </form>
            <%


                        } //fim do if que testa se eh para aparecer o formulario ou se eh para editar
                        else { //se o formulario ja foi preenchido entra no else
                            out.println("<script language=\"JavaScript\" type=\"text/javascript\">"
                                    + "document.body.style.cursor=\"default\";"
                                    + "</script>");


                            String nome = request.getParameter("nome").trim();
                            String descricao = request.getParameter("descricao").trim();
                            String url = request.getParameter("urlSF").trim();


                            if (nome.isEmpty() || descricao.isEmpty() || url.isEmpty()) {
                                out.print("<script type='text/javascript'>alert('Todos os campos devem ser preenchidos!');</script>"
                                        + "<script type='text/javascript'>history.back(-1);</script>");
                                out.close();
                            }

                            int result = 0;

                            String sqlUp = "UPDATE dados_subfederacoes set nome='" + nome + "', descricao='" + descricao + "', url='" + url + "' where id=" + id;

                            result = stm.executeUpdate(sqlUp); //realiza no banco o que esta na variavel sqlUp

                            if (result > 0) { //se o insert funcionar entra no if
                                out.print("<script type='text/javascript'>alert('Os dados foram atualizados com sucesso!'); "
                                        + "opener.location.href=opener.location.href; "
                                        + "window.location=\"exibeFederacao.jsp?id=" + id + "\";</script>");
                            } else {
                                out.print("<p class=\"textoErro\">ERRO AO ATUALIZAR A BASE DE DADOS!</p>");
                            }



                        }
            %>


        </div>
        <%@include file="googleAnalytics"%>
    </body>
</html>
<%
            con.close(); //fechar conexao com o postgre
%>
