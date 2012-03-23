<%-- 
    Document   : alterarSenha
    Created on : 09/09/2009, 17:10:44
    Author     : Marcos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="testaSessaoNovaJanela.jsp"%>
<%@include file="conexaoBD.jsp"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>GT-FEB – Federa&ccedil;&atilde;o de Reposit&oacute;rios Educa Brasil</title>
        <link href="../imagens/favicon.ico" rel="shortcut icon" type="image/x-icon" />
        <link rel="StyleSheet" media="screen" href="../css/padrao.css" type="text/css"/>
        <script type="text/javascript" src="../scripts/validatejs.js"></script>
        <script type="text/javascript" src="../scripts/funcoes.js"></script>
        <script type="text/javascript">
            var myForm = new Validate();myForm.addRules({id:'atual',option:'required',error:'* Voc&ecirc; deve informar sua senha atual!'});
            myForm.addRules({id:'nova',option:'required',error:'* Voc&ecirc; deve informar a nova senha!'});
            myForm.addRules({id:'confirmacao',option:'required',error:'* Voc&ecirc; deve <b>confirmar</b> a senha no campo indicado!'});
            myForm.addRules({id:'nova', to:'confirmacao', option:'isEqual',error:'* As senhas digitadas n&atilde;o est&atilde;o iguais!'});
        </script>
    </head>
    <body>
        <%
            Statement stm = con.createStatement();
            String login = (String) session.getAttribute("usuario");
            boolean formNull = true;
            try {
                String senhaAtual = request.getParameter("senhaAtual");
                if (!senhaAtual.isEmpty()) {
                    formNull = false;
                }
            } catch (Exception e) {
                formNull = true;

            }
            //postgres ok
            if (formNull) {
                String sql = "";
                sql += "SELECT * FROM usuarios ";
                sql += "WHERE login='" + login + "';";
                
                ResultSet res = stm.executeQuery(sql);
                if (res.next()) {

        %>

        <div id="page">
            <FORM name="login" action="" method="post" onsubmit="return myForm.Apply('MensagemErro')">
                <div class="subTitulo-center">&nbsp;Trocar Senha</div>
                <div class="TextoDivAlerta" id="MensagemErro"><!--Aqui o script colocara a mensagem de erro, se ocorrer--></div>
                <div class="EspacoAntes">&nbsp;</div>
                <div class="LinhaEntrada">
                    <label class="Label">Usu&aacute;rio:</label>
                    <div class="Value">
                        <b><%=res.getString("login")%></b>&nbsp;
                    </div>
                </div>
                <div class="LinhaEntrada">
                    <label class="Label">Digite a senha atual: </label>
                    <div class="Value">
                        <input name="senhaAtual" type="password" id="atual">
                    </div>
                </div>
                <div class="LinhaEntrada">
                    <label class="Label">Digite a nova senha: </label>
                    <div class="Value">
                        <input name="novaSenha" type="password" id="nova">
                    </div>
                </div>
                <div class="LinhaEntrada">
                    <label class="Label">Repita a nova senha: </label>
                    <div class="Value">
                        <input name="confirmacaoSenha" type="password" id="confirmacao">
                    </div>
                </div>
                <div class="LinhaEntrada">
                    <div class="Buttons">
                        <input class="BOTAO" name="trocarSenha" type="submit" value="Trocar a Senha">
                        <input class="BOTAO" id="cancelar" onclick="javascript:window.close();" value="Cancelar" type="button" class="CancelButton">
                    </div>
                </div>

            </FORM>
        </div>


        <%
                }
            } else { //se o formulario ja foi preenchido entra no else
                
                String senhaAtual = request.getParameter("senhaAtual");
                String senhaNova = request.getParameter("novaSenha");
                String confimaSenha = request.getParameter("confirmacaoSenha");

                if (!senhaNova.equals(confimaSenha)) {//testa se a nova senha e a confirmacao estao iguais
                    out.print("<script type='text/javascript'>alert('As senhas informadas não conferem. Digite novamente!');</script>" +
                            "<script type='text/javascript'>history.back(-1);</script>");
                }

                try {
                    //postgres ok
                    String sql = "SELECT nome FROM usuarios WHERE login='" + login + "' and senha=md5('" + senhaAtual + "');";
                    ResultSet res = stm.executeQuery(sql);
                    if (res.next()) {
                        int result = 0;
                        //potgres ok
                        String sqlUpdate = "UPDATE usuarios SET senha=md5('" + senhaNova + "') WHERE login='" + login + "'";
                        result = stm.executeUpdate(sqlUpdate); //executa o update da senha
                        if (result > 0) { //se result for maior que zero é pq atualizou a senha
                            session.removeAttribute("usuario");//remove o usuario da sessao para que tenha que digitar a nova senha
                            out.print("<script type='text/javascript'> alert('Senha alterada com sucesso!');" +
                                    "fechaRecarrega();</script>");//fecha a popup e recarrega a pagina principal
                        } else //se entrar no else é pq nao atualizou a senha
                        {
                            out.print("<script type='text/javascript'>alert(' Erro ao atualizar a senha!'); " +
                                    "history.back(-1);</script>");
                        }
                    } else {
                        out.print("<script type='text/javascript'>alert('A senha atual informada está incorreta!');</script>" +
                                "<script type='text/javascript'>history.back(-1);</script>");
                    }

                } catch (SQLException e) {
                    System.err.println("FEB: ERRO SQL. Erro ao alterar senha"+e);
                    out.print("<script type='text/javascript'>alert(' Erro ao atualizar a senha: " +
                            "history.back(-1);</script>");

                }
            }
        %>

        <%@include file="../googleAnalytics"%>
    </body>
</html>
<%
con.close(); //fechar conexao com o banco de dados
%>