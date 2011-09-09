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
<%@page import="operacoesPostgre.Editar" %>
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
            myForm.addRules({id:'ipDes',option:'isIP',error:'* O ip informado para a federa&ccedil;&atilde;o local n&atilde;o est&aacute; correto!'});
            myForm.addRules({id:'portaDes',option:'required',error:'* Deve ser informada a porta da base de dados (Padr&atilde;o PostgreSQL 2345)!'});
            myForm.addRules({id:'loginDes',option:'required',error:'* Deve ser informado o login do da base de dados da subfedera&ccedil;&atilde;o!'});
            myForm.addRules({id:'senhaDes',option:'required',error:'* Deve ser informada a senha do da base de dados da subfedera&ccedil;&atilde;o!'});
            myForm.addRules({id:'confSenhaDes',option:'required',error:'* A senha da base de dados da da subfedera&ccedil;&atilde;o deve ser repetida no campo indicado!'});
            myForm.addRules({id:'senhaDes', to:'confSenhaDes', option:'isEqual',error:'* As senhas digitadas n&atilde;o est&atilde;o iguais!'});

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
            String sql = "SELECT * FROM dados_subfederacoes l WHERE l.id=" + id;

            ResultSet res = stm.executeQuery(sql);
            res.next();
            String ipAntigo = res.getString("ip");

            if (formNull) {

            %>
            <div class="subTitulo-center">&nbsp;Editanto reposit&oacute;rio <%=res.getString("nome")%></div>
            <div class="subtitulo">Informa&ccedil;&otilde;es sobre a subfedera&ccedil;&atilde;o</div>
            <div class="EspacoAntes">&nbsp;</div>
            <form name="editaLDAP" action="editarFederacao.jsp" method="post" onsubmit="return myForm.Apply('MensagemErro')">
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
                    <div class="Label">
                        Endere&ccedil;o ip:
                    </div>
                    <div class="Value">
                        <input name="ip" id="ipDes" type="text" value="<%=res.getString("ip")%>" onkeypress ="return ( maskIP(event,this) );" maxlength="15" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>
                </div>
                <div class="LinhaEntrada">
                    <div class="Label">
                        Porta:
                    </div>
                    <div class="Value">
                        <input name="porta" value="<%=res.getInt("porta")%>" id="portaDes" type="text" maxlength="4" onkeypress ="return ( isNumber(event) );" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>
                </div>
                <div class="LinhaEntrada">
                    <div class="Label">
                        Login:
                    </div>
                    <div class="Value">
                        <input name="login" value="<%=res.getString("login")%>" id="loginDes" type="text" maxlength="100" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>
                </div>
                <div class="LinhaEntrada">
                    <div class="Label">
                        Senha:
                    </div>
                    <div class="Value">
                        <input name="senha" value="<%=res.getString("senha")%>" id="senhaDes" type="password" maxlength="100" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>
                </div>
                <div class="LinhaEntrada">
                    <div class="Label">
                        Repita a senha:
                    </div>
                    <div class="Value">
                        <input name="confSenha" value="<%=res.getString("senha")%>" id="confSenhaDes" type="password" maxlength="100" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>
                </div>
                <input type="hidden" name="id" value="<%=id%>"/>
                <input type="hidden" name="editar" value="sim"/>
                <div class="LinhaEntrada">
                    <div class="Buttons">
                        <input type="button" value="&lArr; Voltar" onclick="javascript:history.go(-1);"/>
                        <input type="submit" value="Gravar >" name="submit" />

                    </div>
                </div>

            </form>
            <%


            } //fim do if que testa se eh para aparecer o formulario ou se eh para editar
            else { //se o formulario ja foi preenchido entra no else
                out.println("<script language=\"JavaScript\" type=\"text/javascript\">" +
                        "document.body.style.cursor=\"default\";" +
                        "</script>");


                String nome = request.getParameter("nome").trim();
                String descricao = request.getParameter("descricao").trim();
                String ip = request.getParameter("ip").trim();
                String porta = request.getParameter("porta").trim();
                String loginLdap = request.getParameter("login").trim();
                String senhaLdap = request.getParameter("senha").trim();
                String confimaSenha = request.getParameter("confSenha").trim();

                if (nome.isEmpty() || descricao.isEmpty() || ip.isEmpty() || porta.isEmpty() || loginLdap.isEmpty() || senhaLdap.isEmpty() || confimaSenha.isEmpty()) {
                    out.print("<script type='text/javascript'>alert('Todos os campos devem ser preenchidos!');</script>" +
                            "<script type='text/javascript'>history.back(-1);</script>");
                    out.close();
                }
                //testa se a senha informada e a repeticao estao iguais
                if (!senhaLdap.equals(confimaSenha)) {
                    out.print("<script type='text/javascript'>alert('As senhas informadas para não conferem. Digite novamente!');</script>" +
                            "<script type='text/javascript'>history.back(-1);</script>");
                    out.close();
                }


                int result = 0;

                String sqlUp = "UPDATE dados_subfederacoes set nome='" + nome + "', descricao='" + descricao + "', login='" + loginLdap + "', senha='" + senhaLdap + "', porta=" + porta + ", ip='" + ip + "' where id=" + id;

                result = stm.executeUpdate(sqlUp); //realiza no banco o que esta na variavel sqlUp

                if (result > 0) { //se o insert funcionar entra no if
                      out.print("<script type='text/javascript'>alert('Os dados foram atualizados com sucesso!'); " +
                                "opener.location.href=opener.location.href; " +
                                "window.location=\"exibeFederacao.jsp?id=" + id + "\";</script>");
                        }else
                            out.print("ERRO AO ATUALIZAR A BASE DE DADOS!");
                     

                
            }
            %>


        </div>
        <%@include file="googleAnalytics"%>
    </body>
</html>
<%
con.close(); //fechar conexao com o postgre
%>
