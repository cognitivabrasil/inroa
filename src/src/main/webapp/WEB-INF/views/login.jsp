<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="security"
           uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:url var="login" value="/scripts/login.js" />

<html lang="pt-BR">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>FEB - Ferramenta Administrativa</title>
        <link href="imagens/favicon.ico" rel="shortcut icon" type="image/x-icon" />
        <link rel="StyleSheet" href="css/padrao.css" type="text/css">
        <script type="text/javascript" src="./scripts/validatejs.js"></script>
        <script type="text/javascript" src="${login}"></script>

        <script type="text/javascript">
            var myForm = new Validate();

            myForm.addRules({
                id: 'user',
                option: 'required',
                error: '* Voc&ecirc; deve informar o seu nome de usu&aacute;rio!'
            });
            myForm.addRules({
                id: 'passwd',
                option: 'required',
                error: '* Voc&ecirc; deve informar sua senha!'
            });
        </script>


    </HEAD>

    <body id="bodyMenor">

        <div id="page">
            <jsp:include page="cabecalho.jsp">
                <jsp:param value="Digite seu usu&aacute;rio e sua senha para acessar o sistema" name="titulo" />
            </jsp:include>

            <div class="linkCantoEsquerdo">
                <a href="index">Ferramenta de Busca</a>
            </div>
            <div class="clear"></div>

            <c:url var="postLoginUrl" value="/login" />
            <form:form name="login" action="${postLoginUrl }" method="post"
                       onsubmit="return myForm.Apply('MensagemErro')">

                <div class="EspacoAntes">&nbsp;</div>
                <div class="textoErro" id="MensagemErro">
                    <c:if test="${param.error!=null}">
                        <div th:if="${param.error}" class="alert alert-error">Usu�rio e senha inv�lidos. </div>
                    </c:if>

                </div>
                <div class="LinhaEntrada">

                    <label class="Label"><B>Usu&aacute;rio:&nbsp;</B></label>
                    <div class="Value">
                        <input name="username" id="user" maxlength="20" type="text"
                               onFocus="this.className = 'inputSelecionado'"
                               onBlur="this.className = ''">
                    </div>
                </div>
                <div class="LinhaEntrada">
                    <label class="Label"><B>Senha:&nbsp;</B></label>
                    <div class="Value">
                        <input name="password" id="passwd" type=password
                               onFocus="this.className = 'inputSelecionado'"
                               onBlur="this.className = ''">
                    </div>
                </div>

                <div class="center">
                    <input name="remember" type="checkbox" value=""> Permanecer logado
                </div>

                <div class="LinhaEntrada">
                    <div class="Buttons">
                        <input class="BOTAO" name="submeter"
                               value="Entrar no sistema" type="submit" />
                    </div>
                </div>

            </form:form>
        </div>
        <jsp:include page="fragments/googleAnalytics.jsp">             <jsp:param name="analyticsId" value="${analyticsId}" />         </jsp:include>
    </BODY>
</HTML>
