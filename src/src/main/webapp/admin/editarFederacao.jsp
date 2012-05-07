<%-- 
    Document   : editarFederacao
    Created on : 14/09/2009, 12:49:22
    Author     : Marcos
--%>

<%@page import="javax.persistence.Parameter"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>FEB - Ferramenta Administrativa</title>
        <link rel="StyleSheet" href="../css/padrao.css" type="text/css"/>
        <script language="JavaScript" type="text/javascript" src="../scripts/funcoes.js"></script>
        <script type="text/javascript" src="../scripts/validatejs.js"></script>
        <link href="../imagens/favicon.ico" rel="shortcut icon" type="image/x-icon" />
        <script type="text/javascript">
            var myForm = new Validate();
            myForm.addRules({id:'nome',option:'required',error:'* Voc&ecirc; deve informar o nome do reposit&oacute;rio!'});
            myForm.addRules({id:'descricao',option:'required',error:'* Deve ser informarmada uma descri&ccedil;&atilde;o!'});
            myForm.addRules({id:'url',option:'urlcomip',error:'* Deve ser informada a url da federa&ccedil;&atilde;o que est&aacute; sendo adicionada. Come&ccedil;ando por http://'});
        </script>
    </head>

    <body>
        <div id="page">

            <div class="subTitulo-center">&nbsp;Editanto subfedera&ccedil;&atilde;o ${subDAO.nome}</div>
            <div class="subtitulo">Informa&ccedil;&otilde;es sobre a subfedera&ccedil;&atilde;o</div>
            <div class="EspacoAntes">&nbsp;</div>

            <form:form method="post" modelAttribute="subDAO" action="salvarFederacao" acceptCharset="utf-8" onsubmit="return myForm.Apply('MensagemErro')">
                
                <div class="TextoDivAlerta" id="MensagemErro"><!--Aqui o script colocara a mensagem de erro, se ocorrer--></div>
                <div class="LinhaEntrada">
                    <form:errors path="nome" cssClass="ValueErro" />
                    <div class="Label">
                        Nome:
                    </div>
                    <div class="Value">
                        <form:input path="nome" maxlength="45" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>
                </div>
                <div class="LinhaEntrada">
                    <form:errors path="descricao" cssClass="ValueErro" />
                    <div class="Label">
                        Descri&ccedil;&atilde;o:
                    </div>
                    <div class="Value">
                        <form:input path="descricao" maxlength="455" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>
                </div>
                <div class="LinhaEntrada">
                    <form:errors path="url" cssClass="ValueErro"/>
                    <div class="Comentario">Ex: http://feb.ufrgs.br/feb</div>
                    <div class="Label">
                        URL da federa&ccedil;&atilde;o:
                    </div>
                    <div class="Value">                        
                        <form:input path="url" maxlength="200" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />                                
                    </div>
                </div>
                <input type="hidden" name="id" value="${param.id}"/>
                <div class="LinhaEntrada">
                    <div class="Buttons">
                        <input type="button" value="&lArr; Voltar" onclick="javascript:history.go(-1);"/>
                        <input type="submit" value="Gravar &rArr;" name="submit" />

                    </div>
                </div>
            </form:form>
              
        </div>
        <%@include file="../googleAnalytics"%>
    </body>
</html>
