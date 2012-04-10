<%-- 
    Document   : addPadrao
    Created on : 30/09/2010, 16:49:05
    Author     : Marcos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../testaSessaoNovaJanela.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FEB - Federa&ccedil;&atilde;o de Reposit&oacute;rios Educa Brasil</title>
        <link rel="StyleSheet" href="../../css/padrao.css" type="text/css">
        <link href="../../imagens/favicon.ico" rel="shortcut icon" type="image/x-icon" />
    </head>
    <body>

        <div id="page">

            <form:form method="post" modelAttribute="padraoModel" action="salvaPadrao">
                <div class="subTitulo-center">&nbsp;Edi&ccedil;&atilde;o / Visualiza&ccedil;&atilde;o de mapeamentos cadastrados</div>
                <div class="subtitulo">Informa&ccedil;&otilde;es gerais</div>
                <div class="EspacoAntes">&nbsp;</div>
                <div class="TextoDivAlerta" id="MensagemErro"><!--Aqui o script colocara a mensagem de erro, se ocorrer--></div>

                
                
                <div class="LinhaForm">
                    <div class="ErroValidador"><form:errors path="nome" /> </div>
                    <div class="Legenda">
                        Nome do Padr&atilde;o:
                    </div>
                    <div class="Valor">
                        <form:input path="nome" maxlength="45" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
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
                    <div class="Valor">
                        <font class="textoErro">
                            Insira todos os atributos do padr&atilde;o separados por ponto e virgula  ";"
                        </font>
                    </div>

                    <div class="ErroValidador"><form:errors path="atributos" /></div>
                    <div class="Legenda">
                        Atributos:
                    </div>
                    <div class="Valor" id="descricao">
                        <form:textarea path="atributos" rows="10" cols="50" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
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