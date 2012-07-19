<%-- 
    Document   : editaPadrao
    Created on : 01/10/2010, 10:52:12
    Author     : Marcos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title>FEB - Federa&ccedil;&atilde;o de Reposit&oacute;rios Educa Brasil</title>

        <link rel="StyleSheet" href="../../css/padrao.css" type="text/css">
        <link href="../../imagens/favicon.ico" rel="shortcut icon" type="image/x-icon" />

        <script language="JavaScript" type="text/javascript" src="../../scripts/funcoes.js"></script>


    </head>
    <body onUnLoad="recarrega()">
        <div id="page">

            <div class="subTitulo-center">&nbsp;Edi&ccedil;&atilde;o / Visualiza&ccedil;&atilde;o de padr&otilde;es cadastrados</div>
            <div class="subtitulo">Informa&ccedil;&otilde;es gerais</div>
            <form:form method="post" modelAttribute="padrao" acceptCharset="utf-8" onsubmit="return myForm.Apply('MensagemErro')">
                <div class="LinhaForm">
                    <div class="Editar">&nbsp;
                        <input type="button" class="botaoEditar" size="30" name="editarNome" id="editarNome" onclick=""/>
                    </div>
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

                </div>
            </form:form>


        </div>
    </body>
</html>