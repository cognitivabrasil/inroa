<%-- 
    Document   : alterarSenha
    Created on : 09/09/2009, 17:10:44
    Author     : Marcos
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
<c:url var="favicon" value="/imagens/favicon.ico" />
<c:url var="css" value="/css/padrao.css" />
<c:url var="validateJs" value="/scripts/validatejs.js" />

<c:url var="funcoesMapeamentoJs" value="/scripts/funcoesMapeamento.js" />

<link href="${favicon}" rel="shortcut icon" type="image/x-icon" />
<link rel="StyleSheet" href="${css }" type="text/css" />
<script type="text/javascript" src="${validateJs }"></script>

    </head>
    <body>

        <div id="page">
           <form:form method="post" modelAttribute="user"
			acceptCharset="utf-8" onsubmit="return myForm.Apply('MensagemErro')">
                <div class="subTitulo-center">&nbsp;Trocar Senha</div>
                <div class="TextoDivAlerta" id="MensagemErro"><!--Aqui o script colocara a mensagem de erro, se ocorrer--></div>
                <div class="EspacoAntes">&nbsp;</div>
                <div class="LinhaEntrada">
                    <label class="Label">Usu&aacute;rio:</label>
                    <div class="Value">
                        <form:input path="username" type="text" disabled="true" class="disabled"/>

                    </div>
                </div>

                <div class="LinhaEntrada">
    				<form:errors path="oldPassword" cssClass="ValueErro" />
				<div class="Label">Digite a senha atual:</div>
				<div class="Value">
					<form:password path="oldPassword" maxlength="45" />
				</div>
                </div>
                <div class="LinhaEntrada">
                <form:errors path="password" cssClass="ValueErro" />
                    <label class="Label">Digite a nova senha: </label>
                    <div class="Value">
                        <form:password path="password"/>
                    </div>
                </div>

                <div class="LinhaEntrada">
                <form:errors path="confirmPassword" cssClass="ValueErro" />
                    <label class="Label">Repita a nova senha: </label>
                    <div class="Value">
                        <form:password path="confirmPassword"/>
                    </div>
                </div>
                <div class="LinhaEntrada">
                    <div class="Buttons">
                        <input class="BOTAO" id="cancelar" onclick="javascript:window.close();" value="Cancelar" type="button" class="CancelButton">
                        <input class="BOTAO" name="trocarSenha" type="submit" value="Trocar a Senha">                        
                    </div>
                </div>

            </form:form>
        </div>
    </body>
</html>
