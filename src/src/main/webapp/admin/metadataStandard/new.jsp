<%-- 
    Document   : Adiciona ou edita padrão de metadados
    Created on : 30/09/2010, 16:49:05
    Edited on  : 20/07/2012
    Author     : Marcos Nunes
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FEB - Federa&ccedil;&atilde;o de Reposit&oacute;rios Educa Brasil</title>
        <c:url var="css" value="/css/padrao.css" />
        <c:url var="favicon" value="/imagens/favicon.ico" />
        <link rel="StyleSheet" href="${css}" type="text/css">
        <link href="${favicon}" rel="shortcut icon" type="image/x-icon" />
    </head>
    <body>

        <div id="page">

            <form:form method="post" modelAttribute="padraoModel" acceptCharset="utf-8">
                <div class="subTitulo-center">Padr&atilde;o de metadados</div>
                <div class="subtitulo">Informa&ccedil;&otilde;es gerais</div>
                <div class="EspacoAntes">&nbsp;</div>
                <div class="TextoDivAlerta" id="MensagemErro"><!--Aqui o script colocara a mensagem de erro, se ocorrer--></div>

                
                
                <div class="LinhaForm">
                    <div class="ErroValidador"><form:errors path="name" /> </div>
                    <div class="Legenda">
                        Nome do Padr&atilde;o:
                    </div>
                    <div class="Valor">
                        <form:input path="name" maxlength="45" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>
                </div>
                <div class="LinhaForm">
                    <div class="ErroValidador"><form:errors path="metadataPrefix" /> </div>
                    <div class="Legenda">
                        MetadataPrefix:
                    </div>
                    <div class="Valor">
                        <form:input path="metadataPrefix" maxlength="45" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>
                </div>
                    
                <div class="LinhaForm">
                    <div class="ErroValidador"><form:errors path="namespace" /> </div>
                    <div class="Legenda">
                        Namespace:
                    </div>
                    <div class="Valor">
                        <form:input path="namespace" maxlength="45" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>
                </div>
                
                <div class="LinhaForm">
                    <div class="Buttons">                        
                        <input class="BOTAO" id="cancelar" onclick="javascript:window.close();" value="Cancelar" type="button" class="CancelButton"/>
                        <input class="BOTAO" type="submit" value="Gravar >" name="submit" />
                    </div>

                </div>
            </form:form>
        </div>
    </body>
</html>