<%-- 
    Document   : cadastraFederacao
    Created on : 11/09/2009, 17:08:33
    Author     : Marcos
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FEB - Ferramenta Administrativa</title>

        <c:url var="jquery" value="/scripts/vendor/jquery-1.7.2.js" />
        <script language="javascript" type="text/javascript" src='${jquery}'></script>
        <c:url var="validateOAI" value="/scripts/validateOAI.js" />
        <script type="text/javascript" src="${validateOAI}"></script>

        <c:url var="favicon" value="/imagens/favicon.ico" />
        <c:url var="css" value="/css/padrao.css" />
        <c:url var="validateJs" value="/scripts/validatejs.js" />
        <c:url var="funcoesJs" value="/scripts/funcoes.js" />

        <c:url var="funcoesMapeamentoJs" value="/scripts/funcoesMapeamento.js" />
        
        <link href="${favicon}" rel="shortcut icon" type="image/x-icon" />
        <link rel="StyleSheet" href="${css }" type="text/css" />
        <script type="text/javascript" src="${validateJs }"></script>
        <script type="text/javascript" src="${funcoesJs }"></script>
        <c:url var="root" value="/" />
        <script>setRootUrl("${root}");</script>

        <script type="text/javascript">
            var myForm = new Validate();
            myForm.addRules({
                id : 'name',
                option : 'required',
                error : '* Voc&ecirc; deve informar o nome da federa&ccedil;&atilde;o!'
            });
            myForm.addRules({
                id : 'descricao',
                option : 'required',
                error : '* Deve ser informarmada uma descri&ccedil;&atilde;o!'
            });
            myForm
            .addRules({
                id : 'url',
                option : 'urlcomip',
                error : '* Deve ser informada a url da federa&ccedil;&atilde;o que est&aacute; sendo adicionada. Come&ccedil;ando por http://'
            });
        </script>
    </head>
    <body>
        <input type="hidden" id="federation" value="true" />
        <div id="page">

            <div class="subTitulo-center">&nbsp;Entre com as
                informa&ccedil;&otilde;es para cadastrar uma nova
                federa&ccedil;&atilde;o</div>
            <div class="EspacoAntes">&nbsp;</div>
            <div class="TextoDivAlerta" id="MensagemErro">
                <!--Aqui o script colocara a mensagem de erro, se ocorrer-->
            </div>
            <c:if test="${!empty erro}">
                <div class="DivErro">${erro}</div>
            </c:if>
            <form:form method="post" modelAttribute="federation"
                       acceptCharset="utf-8" onsubmit="return myForm.Apply('MensagemErro')">

                <div class="LinhaEntrada">
                    <form:errors path="name" cssClass="ValueErro" />
                    <div class="Label">Nome:</div>
                    <div class="Value">
                        <form:input path="name" maxlength="45"
                                    onFocus="this.className='inputSelecionado'"
                                    onBlur="this.className=''" />
                    </div>
                </div>
                <div class="LinhaEntrada">
                    <form:errors path="descricao" cssClass="ValueErro" />
                    <div class="Label">Descri&ccedil;&atilde;o:</div>
                    <div class="Value">
                        <form:input path="descricao" maxlength="455"
                                    onFocus="this.className='inputSelecionado'"
                                    onBlur="this.className=''" />
                    </div>
                </div>
                
                <div class="LinhaEntrada">
                    <form:errors path="url" cssClass="ValueErro" />
                    <div class="Comentario">Ex: http://feb.ufrgs.br/feb</div>
                    <div class="msgError"></div>
                    <div class="Label">URL da federa&ccedil;&atilde;o:</div>
                    <div class="Value">
                        <form:input path="url" maxlength="200"
                                    onFocus="this.className='inputSelecionado'"
                                    onBlur="this.className=''" />
                        &nbsp;
                        <div id="resultadoTesteOAI" class="linkCantoDireito"></div>
                    </div>
                </div>

                <div class="LinhaEntrada">
                    <div class="Buttons">

                        <input class="BOTAO" type="reset" value="Limpar"
                               class="CancelButton"
                               onclick="javascript:window.location.reload();" /> <input
                               class="BOTAO" id="cancelar" onclick="javascript:window.close();"
                               value="Cancelar" type="button" class="CancelButton" /> <input
                               class="BOTAO" type="submit" value="Gravar >" name="submit" />
                    </div>
                </div>

            </form:form>


        </div>
    </body>
</html>