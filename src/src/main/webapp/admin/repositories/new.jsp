<%--
    Document   : cadastraRepositorio
    Created on : 03/08/2009, 16:12:33
    Author     : Marcos

Primeira etapa do cadastro de um repositorio
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FEB - Ferramenta Administrativa</title>

        <c:url var="favicon" value="/imagens/favicon.ico" />
        <c:url var="css" value="/css/padrao.css" />
        <c:url var="validateJs" value="/scripts/validatejs.js" />
        <c:url var="funcoesJs" value="/scripts/funcoes.js" />

        <c:url var="jquery" value="/scripts/vendor/jquery-1.7.2.js" />
        <script language="javascript" type="text/javascript" src='${jquery}'></script>
        <c:url var="validateOAI" value="/scripts/validateOAI.js" />
        <script type="text/javascript" src="${validateOAI}"></script>


        <c:url var="funcoesMapeamentoJs" value="/scripts/funcoesMapeamento.js" />

        <link href="${favicon}" rel="shortcut icon" type="image/x-icon" />
        <link rel="StyleSheet" href="${css }" type="text/css" />
        <script type="text/javascript" src="${validateJs }"></script>
        <script type="text/javascript" src="${funcoesJs }"></script>

        <c:url var="root" value="/" />
        <script>setRootUrl(${root});</script>

        <script language="JavaScript" type="text/javascript"
                src="${funcoesMapeamentoJs}">
                    //funcoes javascript que chamam o ajax
        </script>
        <script type="text/javascript">
            var myForm = new Validate();
            myForm.addRules({id : 'name', option : 'required', error : '* Voc&ecirc; deve informar o nome do reposit&oacute;rio!'});
            myForm.addRules({id : 'descricao', option : 'required',	error : '* Deve ser informarmada uma descri&ccedil;&atilde;o!'});
            myForm.addRules({id : 'padraoMetadados', option : 'required', error : '* Deve ser informado o padr&atilde;o dos metadados do repositorio!'});
            myForm.addRules({id : 'periodicidadeAtualizacao', option : 'required', error : '* Deve ser informado a periodicidade de atualiza&ccedil;&atilde;o. Em dias!'});
            myForm.addRules({id : 'mapeamento.id', option : 'required', error : '* Deve ser selecionado um mapeamento!'});
            myForm.addRules({id : 'metadataPrefix', option : 'required', error : '* Deve ser informado o MetadataPrefix!' });
            myForm.addRules({id : 'url', option : 'required', error : '* Deve ser informada uma url que responda ao protocolo OAI-PMH!'});
        </script>

    </head>
    <body
        onload="verificaMapOnLoad('${padraoSelecionado}', '${mapSelecionado}', 'resultado');">

        <div id="page">

            <div class="subTitulo-center">&nbsp;Entre com as
                informa&ccedil;&otilde;es para cadastrar um novo reposit&oacute;rio</div>
            <div class="EspacoAntes">&nbsp;</div>
            <form:form method="post" modelAttribute="repModel"
                       acceptCharset="utf-8"
                       onsubmit="return myForm.Apply('MensagemErro')">

                <div class="TextoDivAlerta" id="MensagemErro">
                    <!--Aqui o script colocara a mensagem de erro, se ocorrer-->
                    <c:out value="${erro}" />
                </div>
                <div class="subtitle">Informa&ccedil;&otilde;es gerais sobre o
                    reposit&oacute;rio</div>
                <div class="LinhaEntrada">
                    <form:errors path="name" cssClass="ValueErro" />
                    <div class="Label">Nome/Sigla:</div>
                    <div class="Value">
                        <form:input path="name" maxlength="45"/>
                    </div>
                </div>
                <div class="LinhaEntrada">
                    <form:errors path="descricao" cssClass="ValueErro" />
                    <div class="Label">Descri&ccedil;&atilde;o:</div>
                    <div class="Value">
                        <form:input path="descricao" maxlength="455"/>
                    </div>
                </div>

                <div class="subtitle">Sincroniza&ccedil;&atilde;o dos metadados</div>
                <div class="LinhaEntrada">
                    <form:errors path="url" cssClass="ValueErro" />
                    <div class="Comentario">Ex: http://url.do.repositorio/request</div>
                    <div class="msgError"></div>
                    <div class="Label">URL que responde OAI-PMH:</div>
                    <div class="Value">
                        <form:input path="url" maxlength="200"  />
                        &nbsp;
                        <div id="resultadoTesteOAI" class="linkCantoDireito"></div>
                    </div>
                </div>

                <div class="LinhaEntrada">
                    <div class="Comentario">Se for mais de uma separar por ponto e
                        v&iacute;rgula.</div>
                    <div class="Comentario">Ex: com1;com2;com3</div>
                    <form:errors path="colecoes" cssClass="ValueErro" />
                    <div class="Label">Cole&ccedil;&otilde;es ou Comunidades
                        (opcional):</div>
                    <div class="Value">
                        <form:input path="colecoesString" maxlength="45"/>
                    </div>
                </div>

                <div class="LinhaEntrada">
                    <form:errors path="padraoMetadados.id" cssClass="ValueErro" />
                    <div class="Label">Padr&atilde;o de metadados utilizado:</div>
                    <div class="Value">
                        <select name="padraoMetadados.id" id="padraoMetadados"


                                onChange="selecionaMapeamento('resultado', this.value, 0);">

                            <c:if test="${empty padraoSelecionado || padraoSelecionado==0}">
                                <option value="0" selected>Selecione
                                </c:if>
                                <c:forEach var="padraoMet" items="${padraoMetadadosDAO.all}">

                                <option value="${padraoMet.id}"
                                        ${padraoMet.id==padraoSelecionado ? 'selected':''}>
                                    ${fn:toUpperCase(padraoMet.name)}
                                </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="LinhaEntrada">
                    <form:errors path="mapeamento.id" cssClass="ValueErro" />
                    <div class="Label">Mapeamento:</div>
                    <div id='resultado'>
                        <div class="Value">Selecione um padr&atilde;o</div>
                        <input type="hidden" id="mapeamento.id" name="mapeamento.id"
                               value=""> <input type="hidden" id="metadataPrefix"
                               name="metadataPrefix" value=""> <input type="hidden"
                               id="namespace" name="namespace" value="">
                    </div>


                    <div class="Espaco">&nbsp;</div>
                </div>


                <div class="LinhaEntrada">
                    <form:errors path="periodicidadeAtualizacao" cssClass="ValueErro" />
                    <div class="Label">Periodicidade de atualiza&ccedil;&atilde;o
                        (em dias):</div>
                    <div class="Value">
                        <form:input path="periodicidadeAtualizacao" maxlength="3"
                                    onkeypress="return ( isNumber(event) );"/>
                    </div>
                </div>

                <div class="LinhaEntrada">
                    <div class="Buttons">
                        <input type="reset" value="Limpar" class="CancelButton"
                               onclick="javascript:window.location.reload();" /> 
                        <input id="cancelar" onclick="javascript:window.close();"
                               value="Cancelar" type="button" class="CancelButton" /> 
                        <input type="submit" value="Gravar &rArr;" name="submit" />
                    </div>
                </div>

            </form:form>

        </div>
        <%@include file="../../googleAnalytics"%>
    </body>
</html>