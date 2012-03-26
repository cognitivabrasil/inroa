<%--
    Document   : cadastraRepositorio
    Created on : 03/08/2009, 16:12:33
    Author     : Marcos

Primeira etapa do cadastro de um repositorio
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="conexaoBD.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FEB - Ferramenta Administrativa</title>
        <%@include file="testaSessaoNovaJanela.jsp"%>
        <link href="../imagens/favicon.ico" rel="shortcut icon" type="image/x-icon" />
        <link rel="StyleSheet" href="../css/padrao.css" type="text/css"/>
        <script type="text/javascript" src="../scripts/validatejs.js"></script>

        <script language="JavaScript" type="text/javascript" src="../scripts/funcoesMapeamento.js">
            //funcoes javascript que chamam o ajax
        </script>
        <script type="text/javascript">
            var myForm = new Validate();
            myForm.addRules({id:'nome',option:'required',error:'* Voc&ecirc; deve informar o nome do reposit&oacute;rio!'});
            myForm.addRules({id:'descricao',option:'required',error:'* Deve ser informarmada uma descri&ccedil;&atilde;o!'});
            myForm.addRules({id:'idPadraoMetadados',option:'required',error:'* Deve ser informado o padr&atilde;o dos metadados do repositorio!'});
            myForm.addRules({id:'periodicidadeAtualizacao',option:'required',error:'* Deve ser informado a periodicidade de atualiza&ccedil;&atilde;o. Em dias!'});
            myForm.addRules({id:'url',option:'urlcomip',error:'* Deve ser informada uma url <b>v&aacute;lida</b> que responda com protocolo OAI-PMH! Come&ccedil;ando por http://'});
            myForm.addRules({id:'rdMap',option:'required',error:'* Deve ser selecionado o tipo de mapeamento!'});
            myForm.addRules({id:'metadataPrefix',option:'required',error:'* Deve ser informado o MetadataPrefix!'});
            myForm.addRules({id:'namespace',option:'required',error:'* Deve ser informado o NameSpace!'});
            myForm.addRules({id:'confereLinkOAI',option:'required',error:'* A url informada n&atilde;o responde ao protocolo OAI-PMH!'});
        </script>

    </head>
    <body>

        <div id="page">

            <div class="subTitulo-center">&nbsp;Entre com as informa&ccedil;&otilde;es para cadastrar um novo reposit&oacute;rio</div>
            <div class="EspacoAntes">&nbsp;</div>
            <form:form method="post" modelAttribute="repModel" action="salvarNovoRepositorio" acceptCharset="utf-8" onsubmit="return myForm.Apply('MensagemErro')">
                <form:errors path="*" cssClass="error" />

                <div class="TextoDivAlerta" id="MensagemErro"><!--Aqui o script colocara a mensagem de erro, se ocorrer-->
                    <c:out value="${erro}"/>
                </div>
                <div class="subtitle">Informa&ccedil;&otilde;es gerais sobre o reposit&oacute;rio</div>
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
                    <div class="Label">
                        Padr&atilde;o de metadados utilizado:
                    </div>
                    <div class="Value">
                        <select name="idPadraoMetadados" id="idPadraoMetadados" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" onChange="selecionaMapeamento('resultado', this.value, 'cadastra');">
                            <option value="" selected>Selecione
                                <c:forEach var="padraoMet" items="${padraoMetadadosDAO.all}" >
                                <option value="${padraoMet.id}"> ${fn:toUpperCase(padraoMet.nome)}
                                </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="LinhaEntrada">
                    <div class="Label">
                        Mapeamento:
                    </div>
                    <div id='resultado'>
                        <div class="Value">Selecione um padr&atilde;o</div>
                        <input type="hidden" id="rdMap"  name="tipo_map" value=""> 
                        <input type="hidden" id="metadataPrefix"  name="metadataPrefix" value="">
                        <input type="hidden" id="namespace"  name="namespace" value="">
                    </div>


                    <div class="Espaco"> &nbsp;</div>
                </div>


                <div class="subtitle">Informa&ccedil;&otilde;es sobre o configura&ccedil;&atilde;o da federa&ccedil;&atilde;o</div>
                <div class="LinhaEntrada">
                    <div class="Label">
                        Periodicidade de atualiza&ccedil;&atilde;o (em dias):
                    </div>
                    <div class="Value">
                        <form:errors path="periodicidadeAtualizacao" cssClass="error" />
                        <form:input path="periodicidadeAtualizacao" maxlength="3" onkeypress ="return ( isNumber(event) );" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>
                </div>

                <div class="subtitle">Sincroniza&ccedil;&atilde;o dos metadados</div>
                <div class="LinhaEntrada">
                    <div class="Label">
                        URL que responde OAI-PMH:
                    </div>
                    <div class="Value">
                        <form:errors path="url" cssClass="error" />
                        <form:input path="url" maxlength="200" onFocus="this.className='inputSelecionado'" onBlur="this.className='';verificaLinkOAI(this.value, this, document.getElementById('resultadoTesteOAI'), document.getElementById('confereLinkOAI'))"/>&nbsp;<div id="resultadoTesteOAI" class="linkCantoDireito"></div>
                    </div>
                    <input type="hidden" id="confereLinkOAI" value="">
                </div>

                <div class="LinhaEntrada">
                    <div class="Label">
                        Cole&ccedil;&otilde;es ou Comunidades (opcional):
                    </div>
                    <div class="Comentario">
                        Se for mais de uma separar por ponto e v&iacute;rgula.
                    </div>
                    <div class="Comentario">
                        Ex: com1;com2;com3
                    </div>
                    <div class="Value">
                        <form:errors path="colecoes" cssClass="error" />
                        <form:input path="colecoes" maxlength="45" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>
                </div>

                <div class="LinhaEntrada">
                    <div class="Buttons">
                        <input type="reset" value="Limpar" class="CancelButton" onclick="javascript:window.location.reload();"/>
                        <input id="cancelar" onclick="javascript:window.close();" value="Cancelar" type="button" class="CancelButton"/>
                        <input type="submit" value="Avan&ccedil;ar >" name="submit" />
                    </div>
                </div>

            </form:form>

        </div>
        <%@include file="../googleAnalytics"%>
    </body>
</html>
<%
    con.close(); //fechar conexao
%>