<%-- 
    Document   : cadastraFederacao
    Created on : 11/09/2009, 17:08:33
    Author     : Marcos
--%>
<%@include file="testaSessaoNovaJanela.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FEB - Ferramenta Administrativa</title>
        <link href="../imagens/favicon.ico" rel="shortcut icon" type="image/x-icon" />
        <link rel="StyleSheet" href="./css/padrao.css" type="text/css"/>
        <script type="text/javascript" src="../scripts/funcoes.js"></script>
        <script type="text/javascript" src="../scripts/validatejs.js"></script>

        <script type="text/javascript">
            var myForm = new Validate();
            myForm.addRules({id:'nome',option:'required',error:'* Voc&ecirc; deve informar o nome do reposit&oacute;rio!'});
            myForm.addRules({id:'descricao',option:'required',error:'* Deve ser informarmada uma descri&ccedil;&atilde;o!'});
            myForm.addRules({id:'url',option:'urlcomip',error:'* Deve ser informada a url da federa&ccedil;&atilde;o que est&aacute; sendo adicionada. Come&ccedil;ando por http://'});
        </script>
    </head>
    <body>
        <div id="page">
            
            <div class="subTitulo-center">&nbsp;Entre com as informa&ccedil;&otilde;es para cadastrar uma nova federa&ccedil;&atilde;o</div>
            <div class="EspacoAntes">&nbsp;</div>
            <div class="TextoDivAlerta" id="MensagemErro"><!--Aqui o script colocara a mensagem de erro, se ocorrer-->
                <c:out value="${erro}"/>
            </div>
            <form:form method="post" modelAttribute="subDAO" action="salvarNovaFederacao" acceptCharset="utf-8" onsubmit="return myForm.Apply('MensagemErro')">
                <form:errors path="*" cssClass="error" />
                <div class="TextoDivAlerta" id="MensagemErro"><!--Aqui o script colocara a mensagem de erro, se ocorrer--></div>
                <div class="LinhaEntrada">
                    <div class="Label">
                        Nome:
                    </div>
                    <div class="Value">
                        <form:errors path="nome" cssClass="error" />
                        <form:input path="nome" maxlength="45" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>
                </div>
                <div class="LinhaEntrada">
                    <div class="Label">
                        Descri&ccedil;&atilde;o:
                    </div>
                    <div class="Value">
                        <form:errors path="descricao" cssClass="error" />
                        <form:input path="descricao" maxlength="455" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>
                </div>
                <div class="LinhaEntrada">
                    <div class="Comentario">Ex: http://feb.ufrgs.br/feb</div>
                    <div class="Label">
                        URL da federa&ccedil;&atilde;o:
                    </div>
                    <div class="Value">
                        <form:errors path="url" cssClass="error"/>
                        <form:input path="url" maxlength="200" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />                                
                    </div>
                </div>
                
                <div class="LinhaEntrada">
                    <div class="Buttons">

                        <input class="BOTAO" type="reset" value="Limpar" class="CancelButton" onclick="javascript:window.location.reload();"/>
                        <input class="BOTAO" id="cancelar" onclick="javascript:window.close();" value="Cancelar" type="button" class="CancelButton"/>
                        <input class="BOTAO" type="submit" value="Gravar >" name="submit" />
                    </div>
                </div>
                
            </form:form>

            
        </div>
        <%@include file="../googleAnalytics"%>
    </body>
</html>