<%-- 
    Document   : alterarSenhaBD
    Created on : 08/12/2011, 17:30:21
    Author     : Marcos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="testaSessaoNovaJanela.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>GT-FEB – Federa&ccedil;&atilde;o de Reposit&oacute;rios Educa Brasil</title>
        <link href="../imagens/favicon.ico" rel="shortcut icon" type="image/x-icon" />
        <link rel="StyleSheet" media="screen" href="../css/padrao.css" type="text/css"/>
        <script type="text/javascript" src="../scripts/validatejs.js"></script>
        <script type="text/javascript" src="../scripts/funcoes.js"></script>
        <script type="text/javascript">
            var myForm = new Validate();
            myForm.addRules({id:'senhabd', to:'confsenhabd', option:'isEqual',error:'* As senhas digitadas n&atilde;o est&atilde;o iguais!'});
        </script>
    </head>
    <body>

        <div id="page">            
            <div class="subTitulo-center">&nbsp;Informa&ccedil;&otilde;es do Banco de Dados</div>
            <div class="TextoDivAlerta" id="MensagemErro"><!--Aqui o script colocara a mensagem de erro, se ocorrer--></div>
            <div class="EspacoAntes">&nbsp;</div>
            <div class="TextoDivAlerta" id="MensagemErro"><!--Aqui o script colocara a mensagem de erro, se ocorrer-->
                ${erro}
            </div>

            <form:form method="post" modelAttribute="conf" action="salvaSenhaBD" acceptCharset="utf-8" onsubmit="return myForm.Apply('MensagemErro')">

                <div class="LinhaEntrada">
                    <form:errors path="base" cssClass="ValueErro" />
                    <label class="Label">Base de dados: </label>                    
                    <div class="Value">
                        <form:input path="base" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>
                </div>
                <div class="LinhaEntrada">
                    <form:errors path="IP" cssClass="ValueErro" />
                    <label class="Label">IP: </label>
                    <div class="Value">
                        <form:input path="IP" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>
                </div>

                <div class="LinhaEntrada">
                    <form:errors path="porta" cssClass="ValueErro" />
                    <label class="Label">Porta: </label>
                    <div class="Value">
                        <form:input path="porta" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" onkeypress ="return ( isNumber(event) );" />
                    </div>
                </div>

                <div class="LinhaEntrada">
                    <form:errors path="usuario" cssClass="ValueErro" />
                    <label class="Label">Usu&aacute;rio:</label>
                    <div class="Value">
                        <form:input path="usuario" maxlength="45" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>
                </div>

                <div class="LinhaEntrada">
                    <div class="Label">Senha: </div>                    
                    <div class="Value" id="divSenha">
                        <input type="button" value="Alterar senha" onclick="javaScript:exibeDivSenha(document.getElementById('divSenha'), document.getElementById('divRepSenha'))">
                        <form:hidden path="senhaCriptografada" />
                    </div>
                </div>
                <div class="hidden" id="divRepSenha">
                    <div class="ValueErro">${erroSenhaConf}</div>
                    <div class="Label">Repita a senha: </div>
                    <div class="Value">
                        <input name="confirmacaoSenhaBD" type="password" id="confsenhabd" value="${conf.senhaCriptografada}">
                    </div>
                </div>


                <div class="LinhaEntrada">
                    <div class="Buttons">
                        <input class="BOTAO" id="cancelar" onclick="javascript:window.close();" value="Cancelar" type="button" class="CancelButton">
                        <input class="BOTAO" name="trocarSenha" type="submit" value="Salvar">                        
                    </div>
                </div>

            </form:form>
        </div>


        <%--                    } else { //se o formulario ja foi preenchido entra no else

                        String nomeBD = request.getParameter("nomeBD");
                        String senhaNova = request.getParameter("SenhaBD");
                        String confimaSenha = request.getParameter("confirmacaoSenhaBD");
                        String ip = request.getParameter("ipBD");
                        String usuario = request.getParameter("usuarioBD");
                        String porta = request.getParameter("portaBD");
                        if (nomeBD.isEmpty() || ip.isEmpty() || usuario.isEmpty() || porta.isEmpty()) {
                            out.print("<script type='text/javascript'>alert('Os campos: nome, ip, usuario e senha devem ser preenchidos!');</script>"
                                    + "<script type='text/javascript'>history.back(-1);</script>");
                        } else {



                            if (!senhaNova.equals(confimaSenha)) {//testa se a nova senha e a confirmacao estao iguais
                                out.print("<script type='text/javascript'>alert('As senhas informadas não conferem. Digite novamente!');</script>"
                                        + "<script type='text/javascript'>history.back(-1);</script>");
                            }

                            try {

                                conf.setBase(nomeBD);
                                conf.setIP(ip);
                                conf.setUsuario(usuario);
                                conf.setPorta(porta);
                                if (!senhaNova.isEmpty()) {
                                    conf.setSenhaCriptografada(senhaNova);
                                }
                                boolean resultado = conf.criaArquivo();
                                if (resultado) {
                                    out.print("<script type='text/javascript'> alert('Dados alterados com sucesso!');"
                                            + "fechaRecarrega();</script>");//fecha a popup e recarrega a pagina principal
                                } else {
                                    out.print("<script type='text/javascript'>alert('Erro ao alterar os dados.');</script>"
                                            + "<script type='text/javascript'>history.back(-1);</script>");
                                }


                            } catch (Exception e) {
                                System.err.println("FEB ERRO: erro ao alterar as informacoes da base de dados. " + this.getClass() + " Mensagem:" + e);
                                out.print("<script type='text/javascript'>alert('Erro ao alterar os dados.');</script>"
                                        + "<script type='text/javascript'>history.back(-1);</script>");
                            }

                        }
                    }

        --%>

        <%@include file="../googleAnalytics"%>
    </body>
</html>
