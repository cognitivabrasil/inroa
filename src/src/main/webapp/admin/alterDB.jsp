<%-- 
    Document   : alterDB
    Created on : 08/12/2011, 17:30:21
    Author     : Marcos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
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

            <form:form method="POST" modelAttribute="conf" action="alterDB" acceptCharset="utf-8" onsubmit="return myForm.Apply('MensagemErro')">

                <div class="LinhaEntrada">
                    <form:errors path="database" cssClass="ValueErro" />
                    <label class="Label">Base de dados: </label>                    
                    <div class="Value">
                        <form:input path="database" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>
                </div>
                <div class="LinhaEntrada">
                    <form:errors path="host" cssClass="ValueErro" />
                    <label class="Label">Host: </label>
                    <div class="Value">
                        <form:input path="host" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>
                </div>

                <div class="LinhaEntrada">
                    <form:errors path="port" cssClass="ValueErro" />
                    <label class="Label">Porta: </label>
                    <div class="Value">
                        <form:input path="port" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" onkeypress ="return ( isNumber(event) );" />
                    </div>
                </div>

                <div class="LinhaEntrada">
                    <form:errors path="username" cssClass="ValueErro" />
                    <label class="Label">Usu&aacute;rio:</label>
                    <div class="Value">
                        <form:input path="username" maxlength="45" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>
                </div>

                <div class="LinhaEntrada" id="divSenha">
                    <form:errors path="password" cssClass="ValueErro" />
                    <div class="Label">Senha: </div>                    
                    <div class="Value">
                        <input type="button" value="Alterar senha" onclick="javaScript:exibeDivSenha(document.getElementById('divSenha'), document.getElementById('divRepSenha'))">
                    </div>
                </div>
                <div class="hidden" id="divRepSenha">
                    <div class="Label">Senha: </div>
                    <div class="Value">
                        <form:password path="password" maxlength="20" />
                    </div>
                    <div class="ValueErro">${errorPassConf}</div>
                    <div class="Label">Repita a senha: </div>
                    <div class="Value">
                        <input name="passConf" type="password" id="confsenhabd">
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

        <%@include file="../googleAnalytics"%>
    </body>
</html>
