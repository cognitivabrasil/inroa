
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="security"
           uri="http://www.springframework.org/security/tags"%>



<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FEB - Ferramenta Administrativa</title>
        <c:url var="css" value="/css/padrao.css" />
        <c:url var="favicon" value="/imagens/favicon.ico" />
        <c:url var="funcoes" value="/scripts/funcoes.js" />
        <link rel="StyleSheet" href="${css}" type="text/css">
        <link href="${favicon }" rel="shortcut icon" type="image/x-icon" />
        <script language="JavaScript" type="text/javascript" src="${funcoes}"></script>
        <c:url var="root" value="/" />
        <script>setRootUrl(${root});</script>

        <!-- Include required JS files -->
        <c:url var="shCoreJs" value="/scripts/vendor/shCore.js" />

        <script type="text/javascript" src="${shCoreJs }"></script>

        <!--
            At least one brush, here we choose JS. You need to include a brush for every
            language you want to highlight
        -->
        <c:url var="shBrushXml" value="/scripts/vendor/shBrushXml.js" />

        <script type="text/javascript" src="${shBrushXml}"></script>

        <!-- Include *at least* the core style and default theme -->
        <c:url var="shCore" value="/css/shCore.css" />
        <link href="${shCore}" rel="stylesheet" type="text/css" />
        <c:url var="shTheme" value="/css/shThemeDefault.css" />

        <link href="${shTheme}" rel="stylesheet" type="text/css" />



    </head>

    <body>
        <jsp:useBean id="operacoesBean" class="com.cognitivabrasil.feb.util.Operacoes"
                     scope="page" />

        <div id="page">

            <div class="subTitulo-center">Edição / Visualização de Usuários
                Cadastrados</div>


            <div class="subtitulo">Informações sobre o mapeamento
                ${mapeamento.name}</div>

            <security:authorize access="hasRole('ROLE_MANAGE_METADATA')">

                <div class="editar">
                    <c:choose>
                        <c:when test="${mapeamento.editable}">
                            <a href="./${mapeamento.id}/edit">Editar</a>
                        </c:when>
                            <c:otherwise><span class="textoErro">N&atilde;o pode ser editado.</span></c:otherwise>
                    </c:choose>
                    
                </div>
            </security:authorize>

            <div class="formLeft">
                <div class="Label">Nome do mapeamento:</div>
                <div class="Value">&nbsp;${mapeamento.name}</div>
            </div>
            <div class="formLeft">
                <div class="Label">Descri&ccedil;&atilde;o:</div>
                <div class="Value">&nbsp;${mapeamento.description}</div>
            </div>
            <div class="subtitulo">XSLT</div>


            <pre class="brush :xml"><c:out value="${mapeamento.xslt}"/></pre>

    </body>
    <!-- Finally, to actually run the highlighter, you need to include this JS on your page -->
    <script type="text/javascript">
        SyntaxHighlighter.all()
    </script>
</html>