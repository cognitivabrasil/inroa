<%-- 
    Document   : Mostra informações do padrão de metadados e da opção para editar
    Created on : 01/10/2010, 10:52:12
    Edited on  : 20/07/2012
    Author     : Marcos Nunes
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title>FEB - Federa&ccedil;&atilde;o de Reposit&oacute;rios Educa Brasil</title>
        <c:url var="css" value="/css/padrao.css" />
        <c:url var="favicon" value="/imagens/favicon.ico" />
        <c:url var="funcoes" value="/scripts/funcoes.js" />
        <link rel="StyleSheet" href="${css}" type="text/css">
        <link href="${favicon}" rel="shortcut icon" type="image/x-icon" />
        <script language="JavaScript" type="text/javascript" src="${funcoes}"></script>
        <c:url var="root" value="/" />
        <script>setRootUrl(${root});</script>


    </head>
    <body>
        <div id="page">

            <div class="subTitulo-center">&nbsp;Edi&ccedil;&atilde;o / Visualiza&ccedil;&atilde;o de padr&otilde;es cadastrados</div>
            <div class="subtitulo">Informa&ccedil;&otilde;es gerais</div>
            <form:form method="post" modelAttribute="padrao" acceptCharset="utf-8" onsubmit="return myForm.Apply('MensagemErro')">
                <div class="LinhaForm">
                    <security:authorize access="hasRole('PERM_MANAGE_METADATA')">
                        <div class="Editar">
                            <c:url var="editar" value="/admin/metadataStandard/${padrao.id}/edit" />
                            <input type="button" class="botaoEditar" title="Editar"
                                   name="editar" id="editarMetadata" onclick="location.href='${editar}'">
                            <a href="${editar}">Editar</a>
                        </div>
                    </security:authorize>

                    <div class="Legenda">
                        Nome do Padr&atilde;o:
                    </div>
                    <div class="Valor" id="metadataprefix">${padrao.name}</div>
                    <div class="Legenda">
                        metadataPrefix:
                    </div>
                    <div class="Valor" id="metadataprefix">${padrao.metadataPrefix}</div>

                    <div class="Legenda">
                        nameSpace:
                    </div>
                    <div class="Valor" id="namespace">${padrao.namespace}</div>
                    <div class="clear"></div>
                </div>
            </form:form>


        </div>
    </body>
</html>