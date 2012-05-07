<%-- 
    Document   : alterarSenha
    Created on : 09/09/2009, 17:10:44
    Author     : Marcos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>GT-FEB – Federa&ccedil;&atilde;o de Reposit&oacute;rios Educa Brasil</title>
        <link href="../imagens/favicon.ico" rel="shortcut icon" type="image/x-icon" />
        <link rel="StyleSheet" media="screen" href="../css/padrao.css" type="text/css"/>
        <script type="text/javascript" src="../scripts/validatejs.js"></script>
        <script type="text/javascript" src="../scripts/funcoes.js"></script>
        <!--script type="text/javascript">
            var myForm = new Validate();myForm.addRules({id:'atual',option:'required',error:'* Voc&ecirc; deve informar sua senha atual!'});
            myForm.addRules({id:'nova',option:'required',error:'* Voc&ecirc; deve informar a nova senha!'});
            myForm.addRules({id:'confirmacao',option:'required',error:'* Voc&ecirc; deve <b>confirmar</b> a senha no campo indicado!'});
            myForm.addRules({id:'nova', to:'confirmacao', option:'isEqual',error:'* As senhas digitadas n&atilde;o est&atilde;o iguais!'});
        </script-->
    </head>
    <body>

        <div id="page">
            <FORM name="login" action="gravaSenha" method="post" onsubmit="return myForm.Apply('MensagemErro')">
                <div class="subTitulo-center">&nbsp;Trocar Senha</div>
                <div class="TextoDivAlerta" id="MensagemErro"><!--Aqui o script colocara a mensagem de erro, se ocorrer--></div>
                <div class="EspacoAntes">&nbsp;</div>
                <div class="LinhaEntrada">
                    <label class="Label">Usu&aacute;rio:</label>
                    <div class="Value">
                        <input name="login" type="text" disabled="true" value="${login}" class="disabled">

                    </div>
                </div>

                <div class="LinhaEntrada">
                    <div class="ValueErro">${erroSenha}</div>
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
                    <div class="ValueErro">${erroSenhaConf}</div>
                    <label class="Label">Repita a nova senha: </label>
                    <div class="Value">
                        <input name="confimaSenha" type="password" id="confirmacao">
                    </div>
                </div>
                <div class="LinhaEntrada">
                    <div class="Buttons">
                        <input class="BOTAO" id="cancelar" onclick="javascript:window.close();" value="Cancelar" type="button" class="CancelButton">
                        <input class="BOTAO" name="trocarSenha" type="submit" value="Trocar a Senha">                        
                    </div>
                </div>

            </FORM>
        </div>
        <%@include file="../googleAnalytics"%>
    </body>
</html>
