<%-- 
    Document   : cadastraLdap
    Created on : 11/09/2009, 17:08:33
    Author     : Marcos
--%>
<%@include file="testaSessaoNovaJanela.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="conexaoBD.jsp"%>
<%@page import="operacoesLdap.Inserir" %>
<%@page import="operacoesLdap.ReiniciarServico" %>
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
            myForm.addRules({id:'ipDes',option:'isIP',error:'* O ip informado para o Ldap local n&atilde;o est&aacute; correto!'});
            myForm.addRules({id:'portaDes',option:'required',error:'* Deve ser informada a porta do Ldap destino (Ldap local)!'});
            myForm.addRules({id:'dnDes',option:'required',error:'* Deve ser informado o DN do Ldap destino (Ldap local)!'});
            myForm.addRules({id:'loginDes',option:'required',error:'* Deve ser informado o login do Ldap destino (Ldap local)!'});
            myForm.addRules({id:'senhaDes',option:'required',error:'* Deve ser informada a senha do Ldap destino (Ldap local)!'});
            myForm.addRules({id:'confSenhaDes',option:'required',error:'* A senha deve ser repetida do Ldap destino no campo indicado!'});
            myForm.addRules({id:'senhaDes', to:'confSenhaDes', option:'isEqual',error:'* As senhas digitadas para o Ldap destino n&atilde;o est&atilde;o iguais!'});

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

            <div class="subTitulo-center">&nbsp;Entre com as informa&ccedil;&otilde;es para cadastrar um novo reposit&oacute;rio</div>
            <div class="EspacoAntes">&nbsp;</div>
            <div class="TextoDivAlerta" id="MensagemErro"><!--Aqui o script colocara a mensagem de erro, se ocorrer--></div>
            <form name="adicionarLDAP" action="" method="post" onsubmit="return myForm.Apply('MensagemErro')">


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
                    <div class="Label">
                        Endere&ccedil;o ip:
                    </div>
                    <div class="Value">
                        <input name="ip" id="ipDes" type="text" value="" onkeypress ="return ( maskIP(event,this) );" maxlength="15" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>
                </div>
                <div class="LinhaEntrada">
                    <div class="Label">
                        Porta:
                    </div>
                    <div class="Value">
                        <input name="porta" id="portaDes" type="text" maxlength="4" onkeypress ="return ( isNumber(event) );" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>
                </div>
                <div class="LinhaEntrada">
                    <div class="Label">
                        DN:
                    </div>
                    <div class="Value">
                        <input name="dn" id="dnDes" type="text" maxlength="100" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>
                </div>
                <div class="LinhaEntrada">
                    <div class="Label">
                        Login:
                    </div>
                    <div class="Value">
                        <input name="loginDestino" id="loginDes" type="text" maxlength="100" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>
                </div>
                <div class="LinhaEntrada">
                    <div class="Label">
                        Senha:
                    </div>
                    <div class="Value">
                        <input name="senhaDestino" id="senhaDes" type="password" maxlength="100" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>
                </div>
                <div class="LinhaEntrada">
                    <div class="Label">
                        Repita a senha:
                    </div>
                    <div class="Value">
                        <input name="confSenhaDestino" id="confSenhaDes" type="password" maxlength="100" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
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
                
                String ip = request.getParameter("ip").trim();
                String porta = request.getParameter("porta").trim();
                String dn = request.getParameter("dn").trim();
                String login = request.getParameter("loginDestino").trim();
                String senhaLdap = request.getParameter("senhaDestino").trim();
                String confimaSenha = request.getParameter("confSenhaDestino").trim();
                String nome = request.getParameter("nome").trim();
                String descricao = request.getParameter("descricao").trim();

                //testa se os campos em comum dos dois tipos de sincronizacao foram preenchidos
                if (ip.isEmpty() || porta.isEmpty() || dn.isEmpty() || login.isEmpty() || senhaLdap.isEmpty() || confimaSenha.isEmpty() || nome.isEmpty() || descricao.isEmpty()) {
                    out.print("<script type='text/javascript'>alert('Todos os campos devem ser preenchidos!');</script>" +
                            "<script type='text/javascript'>history.back(-1);</script>");
                }

                //testa se a senha informada e a repeticao estao iguais
                if (!senhaLdap.equals(confimaSenha)) {
                    out.print("<script type='text/javascript'>alert('As senhas informadas para o Ldap destino não conferem. Digite novamente!');</script>" +
                            "<script type='text/javascript'>history.back(-1);</script>");
                }

                //testar se ja existe um LDAP cadastrado com o mesmo nome
                ResultSet testeExiste = stm.executeQuery("SELECT l.nome, l.descricao, l.ip FROM ldaps l WHERE l.nome='" + nome + "';");

                if (testeExiste.next()) {
                    out.print("<script type='text/javascript'>alert('Já existe um LDAP cadastrado com esse nome!\\nO seu ip é: " + testeExiste.getString("ip") + "\\nSeu nome é: " + testeExiste.getString("nome") + "\\nSua descrição é: " + testeExiste.getString("descricao") + "');</script>");
                    out.print("<script type='text/javascript'>history.go(-1);</script>");
                    //se o navegador tiver o javascrit desativado ira apresentar a mensagem abaixo
                    out.print("<p class='textoErro'>J&aacute; existe um LDAP cadastrado com esse nome ou ip! O seu ip &eacute;: " + testeExiste.getString("ip") + " Seu nome &eacute;: " + testeExiste.getString("nome") + " Sua descrição &eacute;: " + testeExiste.getString("descricao") + "</p>");

                } else {
                    int result = 0;
                    String sql = "insert into ldaps (login, senha, ip, porta, dn, descricao, nome) " +
                            "VALUES ('" + login + "', '" + senhaLdap + "', '" + ip + "', " + porta + ", '" + dn + "', '" + descricao + "', '" + nome + "');";
                    try {
                        result = stm.executeUpdate(sql); //realiza no mysql o insert que esta na variavel sql
                    } catch (SQLFeatureNotSupportedException e) {
                        out.print("<p class='textoErro'>Erro:" + e);


                    } catch (SQLException k) {
                        out.print("<p class='textoErro'>Erro no sql: " + k);
                        System.out.println("ERRO NO SQL: "+k);
                    }
                    if (result > 0) { //se o insert funcionar entra no if
                        boolean inseriuLdap=false;
                        Inserir insereNovoLdap = new Inserir();
                        inseriuLdap = insereNovoLdap.insereRepositorioSldap(ip, dn); //insere o novo repositorio no arquivo slapd.conf
                        if(inseriuLdap){
                            boolean restartLdap=false;
                            ReiniciarServico restart = new ReiniciarServico();
                            if (restart.getResultado()) {
                            
                                out.println("Informações do LDAP " + nome.toUpperCase() + " gravadas com sucesso!");
                        out.print("<script type='text/javascript'>alert('Informações do LDAP " + nome.toUpperCase() + " gravadas com sucesso!'); " +
                                "fechaRecarrega();</script>");
                            }else{
                                out.println("Informações do LDAP " + nome.toUpperCase() + " foram gravadas! <BR>Porém o serviço ldap não foi reiniciado.<BR>Execute como root o comando /etc/init.d/ldap restart para entrar em vigor o novo arquivo de configuração");
                        out.print("<script type='text/javascript'>alert('Informações do LDAP " + nome.toUpperCase() + " foram gravadas! \\nPorém o serviço ldap não foi reiniciado.\\nExecute como root o comando /etc/init.d/ldap restart para entrar em vigor o novo arquivo de configuração'); " +
                                "fechaRecarrega();</script>");
                            }
                        }else{
                        out.println("Informa&ccedil;&otilde;es do LDAP " + nome.toUpperCase() + " foram gravadas no mysql, porém não foi possivel editar o arquivo /etc/openldap/sldap.conf!");
                        out.print("<script type='text/javascript'>alert('Informações do LDAP " + nome.toUpperCase() + " gravadas no mysql, porém não foi possivel editar o arquivo /etc/openldap/sldap.conf!'); " +
                                "fechaRecarrega();</script>");
                        }
                    }


                }

            }//fim else
            %>
        </div>
        <%@include file="googleAnalytics"%>
    </body>
</html>
<%
con.close(); //fechar conexao com mysql
%>