<%-- 
    Document   : cadastraFederacao
    Created on : 11/09/2009, 17:08:33
    Author     : Marcos
--%>
<%@include file="testaSessaoNovaJanela.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="conexaoBD.jsp"%>

<%
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FEB - Ferramenta Administrativa</title>
        <link href="imagens/favicon.ico" rel="shortcut icon" type="image/x-icon" />
        <link rel="StyleSheet" href="./css/padrao.css" type="text/css"/>
        <script type="text/javascript" src="./scripts/funcoes.js"></script>
        <script type="text/javascript" src="./scripts/validatejs.js"></script>

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
                String nome = request.getParameter("nome");
                if (!nome.isEmpty()) {
                    formNull = false;
                }
            } catch (Exception e) {
                formNull = true;

            }
            if (formNull) {
            %>

            <div class="subTitulo-center">&nbsp;Entre com as informa&ccedil;&otilde;es para cadastrar uma nova federa&ccedil;&atilde;o</div>
            <div class="EspacoAntes">&nbsp;</div>
            <div class="TextoDivAlerta" id="MensagemErro"><!--Aqui o script colocara a mensagem de erro, se ocorrer--></div>
            <form name="adicionarFederacao" action="" method="post" onsubmit="return myForm.Apply('MensagemErro')">


                <div class="LinhaEntrada">
                    <div class="Label">
                        Nome:
                    </div>
                    <div class="Value">
                        <input type="text" id="nome" name="nome" maxlength="45" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>
                </div>

                <div class="LinhaEntrada">
                    <div class="Label">
                        Descri&ccedil;&atilde;o:
                    </div>
                    <div class="Value">
                        <input name="descricao" id="descricao" type="text" maxlength="455" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>
                </div>

                <div class="LinhaEntrada">
                    <div class="Comentario">Ex: http://feb.ufrgs.br/feb</div>
                    <div class="Label">
                        URL da federa&ccedil;&atilde;o:
                    </div>
                    <div class="Value">
                        <input name="urlSF" id="urlSF" type="text" maxlength="200" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>
                </div>

                
                <div class="LinhaEntrada">
                    <div class="Buttons">

                        <input class="BOTAO" type="reset" value="Limpar" class="CancelButton" onclick="javascript:window.location.reload();"/>
                        <input class="BOTAO" id="cancelar" onclick="javascript:window.close();" value="Cancelar" type="button" class="CancelButton"/>
                        <input class="BOTAO" type="submit" value="Gravar >" name="submit" />
                    </div>
                </div>

            </form>
            <%            } else {
                Statement stm = con.createStatement();
                String url = request.getParameter("urlSF").trim();
                String nome = request.getParameter("nome").trim();
                String descricao = request.getParameter("descricao").trim();

                //testa se os campos em comum dos dois tipos de sincronizacao foram preenchidos
                if (url.isEmpty() || nome.isEmpty() || descricao.isEmpty()) {
                    out.print("<script type='text/javascript'>alert('Todos os campos devem ser preenchidos!');</script>" +
                            "<script type='text/javascript'>history.back(-1);</script>");
                }


                //testar se ja existe uma subfederação cadastrada com o mesmo nome
                ResultSet testeExiste = stm.executeQuery("SELECT l.nome, l.descricao, l.url FROM dados_subfederacoes l WHERE l.nome='" + nome + "';");

                if (testeExiste.next()) {
                    out.print("<script type='text/javascript'>alert('Já existe um federa&ccedil;&atilde;o cadastrada com esse nome!\\nO endere&ccedil;o é: " + testeExiste.getString("url") + "\\nSeu nome é: " + testeExiste.getString("nome") + "\\nSua descrição é: " + testeExiste.getString("descricao") + "');</script>");
                    out.print("<script type='text/javascript'>history.go(-1);</script>");
                    //se o navegador tiver o javascript desativado ira apresentar a mensagem abaixo
                    out.print("<p class='textoErro'>J&aacute; existe uma federa&ccedil;&atilde;o cadastrada com esse nome ou ip! O endere&ccedil;o é: " + testeExiste.getString("url") + " Seu nome &eacute;: " + testeExiste.getString("nome") + " Sua descrição &eacute;: " + testeExiste.getString("descricao") + "</p>");

                } else {
                    int result = 0;
                    String sql = "INSERT INTO dados_subfederacoes (url, descricao, nome) " +
                            "VALUES ('" + url + "', '" + descricao + "', '" + nome + "');";
                    try {
                        result = stm.executeUpdate(sql); //realiza na base o insert que esta na variavel sql
                    } catch (SQLFeatureNotSupportedException e) {
                        out.print("<p class='textoErro'>Erro:" + e);


                    } catch (SQLException k) {
                        out.print("<p class='textoErro'>Erro no sql: " + k);
                        System.out.println("ERRO NO SQL: "+k);
                    }
                    if (result > 0) { //se o insert funcionar entra no if
                                out.println("Informações de " + nome.toUpperCase() + " gravadas com sucesso!");
                        out.print("<script type='text/javascript'>alert('Informações de " + nome.toUpperCase() + " gravadas com sucesso!'); " +
                                "fechaRecarrega();</script>");
                        }
                        
                    }


            }//fim else
            %>
        </div>
        <%@include file="googleAnalytics"%>
    </body>
</html>
<%
con.close(); //fechar conexao
%>