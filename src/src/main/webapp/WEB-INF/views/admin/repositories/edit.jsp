<%--
    Document   : editarRepositorio
    Created on : 18/08/2009, 14:59:26
    Author     : Marcos
--%>

<%@page import="org.springframework.ui.Model"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>FEB - Ferramenta Administrativa</title>

        <c:url var="favicon" value="/imagens/favicon.ico" />
        <c:url var="css" value="/css/padrao.css" />
        <c:url var="validateJs" value="/scripts/validatejs.js" />
        <c:url var="funcoesJs" value="/scripts/funcoes.js" />

        <c:url var="jquery" value="/scripts/vendor/jquery-1.7.2.js" />
        <script language="javascript" type="text/javascript" src='${jquery}'></script>

        <link rel="StyleSheet" href="${css }" type="text/css"/>
        <script language="JavaScript" type="text/javascript" src="${funcoesJs }"></script>
        <c:url var="root" value="/" />
        <script>setRootUrl("${root}");</script>
        <c:url var="newRepJs" value="/scripts/admin/repositories/new.js" />
        <script language="JavaScript" type="text/javascript" src="${newRepJs}"></script>
        <script type="text/javascript" src="${validateJs }"></script>
        <link href="${favicon }" rel="shortcut icon" type="image/x-icon" />
        <c:url var="validateOAI" value="/scripts/validateOAI.js" />
        <script type="text/javascript" src="${validateOAI}"></script>
    </head>

    <body>
        <div id="page">
            
            <c:choose>
                <c:when test="${empty repModel.id}">
                    <c:set var="titulo" value="Entre com as informa&ccedil;&otilde;es para cadastrar um novo 
                           reposit&oacute;rio"/>
                </c:when>
                <c:otherwise>
                    <c:set var="titulo" value="Editanto reposit&oacute;rio ${repModel.name}"/>
                </c:otherwise>
            </c:choose>
            <div class="subTitulo-center">${titulo}</div>
            <div class="subtitulo">Informa&ccedil;&otilde;es gerais</div>

            <form:form method="post" modelAttribute="repModel" acceptCharset="utf-8" 
                       onsubmit="return myForm.Apply('MensagemErro')">

                <div class="TextoDivAlerta" id="MensagemErro">
                    <!--Aqui o script colocara a mensagem de erro, se ocorrer-->
                    <c:out value="${erro}"/>
                </div>


                <div class="LinhaEntrada">
                    <form:errors path="name" cssClass="ValueErro" />
                    <form:label path="name" cssErrorClass="error">Nome/Sigla:</form:label>
                        <div class="Value">
                        <form:input path="name" maxlength="${repModel.maxSizeName}" cssErrorClass="error" />
                    </div>
                </div>

                <div class="LinhaEntrada">
                    <form:errors path="descricao" cssClass="ValueErro" />
                    <form:label path="descricao" cssErrorClass="error">Descri&ccedil;&atilde;o:</form:label>
                        <div class="Value">
                        <form:input path="descricao" maxlength="455" cssErrorClass="error" />
                    </div>
                </div>

                <div class="subtitulo">Sincroniza&ccedil;&atilde;o dos metadados</div>
                <div class="EspacoAntes">&nbsp;</div>


                <div class="LinhaEntrada">
                    <form:errors path="url" cssClass="ValueErro" />
                    <form:label path="url" cssErrorClass="error">URL que responde OAI-PMH:</form:label>
                        <div class="Value">
                        <form:input path="url" maxlength="200" onFocus="this.className='inputSelecionado'" 
                                    cssErrorClass="error"/>
                        &nbsp;<div id="resultadoTesteOAI" class="linkCantoDireito"></div>
                    </div>
                </div>

                <div class="LinhaEntrada">
                    <form:errors path="colecoesString" cssClass="ValueErro" />
                    <div class="Comentario">
                        Se for mais de uma separar por ponto e v&iacute;rgula.
                    </div>
                    <div class="Comentario">
                        Ex: com1;com2;com3
                    </div>
                    <form:label path="colecoesString" cssErrorClass="error">Cole&ccedil;&otilde;es ou Comunidades:</form:label>                    
                        <div class="Value">
                        <form:input path="colecoesString" maxlength="45" />
                    </div>
                </div>

                <div class="LinhaEntrada">
                    <form:errors path="padraoMetadados.id" cssClass="ValueErro" />
                    <form:label path="padraoMetadados.id" cssErrorClass="error">Padr&atilde;o de metadados utilizado:</form:label>
                        <div class="Value">
                        <c:url var="mapeamentoUrl" value="/admin/mapeamentos/listaMapeamentoPadraoSelecionado?mapSelecionado=${repModel.mapeamento.id}&idpadrao="/>
                        <form:select id="padraoMetadados" path="padraoMetadados.id" items="${padraoMetadados}" 
                                     itemValue="id" itemLabel="name" cssErrorClass="error" url="${mapeamentoUrl}"/>

                    </div>
                </div>

                <div class="LinhaEntrada">
                    <form:errors path="mapeamento.id" cssClass="ValueErro" />
                    <form:label path="mapeamento.id" cssErrorClass="error">Mapeamento:</form:label>
                        <div id="resultadoMap">

                        <c:forEach var="map" items="${repModel.padraoMetadados.mapeamentos}">
                            <div class="ValueIndex">
                                <input id="mapeamento.id" type="radio" name="mapeamento.id" value="${map.id}"
                                       ${map.id == repModel.mapeamento.id ? 'checked=true':''} />
                                ${map.name} (${map.description})
                            </div>
                        </c:forEach>
                    </div>
                </div>

                <div class="LinhaEntrada">
                    <div> &nbsp;</div>
                    <form:errors path="metadataPrefix" cssClass="ValueErro" />
                    <form:label path="metadataPrefix" cssErrorClass="error">MetadataPrefix:</form:label>
                    <div class="Value">
                        <form:input path="metadataPrefix" maxlength="45" />
                    </div>
                    <form:errors path="namespace" cssClass="ValueErro" />
                    <form:label path="namespace" cssErrorClass="error">NameSpace:</form:label>
                    <div class="Value">
                        <form:input path="namespace" maxlength="45" />
                    </div>
                </div>

                <input type="hidden" name="id" value="${idRep}"/>
                <div class="LinhaEntrada">
                    <div class="Buttons">
                        <input type="button" value="&lArr; Voltar" onclick="javascript:history.go(-1);" />
                        <input type="submit" value="Gravar &rArr;" name="submit" />

                    </div>
                </div>

            </form:form>

        </div>
    </body>
</html>
