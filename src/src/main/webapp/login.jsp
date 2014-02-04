<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="security"
           uri="http://www.springframework.org/security/tags"%>

<c:url var="login" value="/scripts/login.js" />

<html>
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
                id : 'user',
                option : 'required',
                error : '* Voc&ecirc; deve informar o seu nome de usu&aacute;rio!'
            });
            myForm.addRules({
                id : 'passwd',
                option : 'required',
                error : '* Voc&ecirc; deve informar sua senha!'
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

            <c:url var="postLoginUrl" value="/j_spring_security_check" />
            <FORM name="login" action="${postLoginUrl }" method="post"
                  onsubmit="return myForm.Apply('MensagemErro')">

                <div class="EspacoAntes">&nbsp;</div>
                <div class="textoErro" id="MensagemErro">
                    <!--Aqui o script colocara a mensagem de erro, se ocorrer-->
                    <c:out value="${erro}" />
                    <c:if test="${param.error == true }">
                        Erro ao efetuar o login.
                    </c:if>
                </div>
                <div class="LinhaEntrada">

                    <label class="Label"><B>Usu&aacute;rio:&nbsp;</B></label>
                    <div class="Value">
                        <input name="j_username" id="user" maxlength="20" type="text"
                               onFocus="this.className='inputSelecionado'"
                               onBlur="this.className=''">
                    </div>
                </div>
                <div class="LinhaEntrada">
                    <label class="Label"><B>Senha:&nbsp;</B></label>
                    <div class="Value">
                        <input name="j_password" id="passwd" type=password
                               onFocus="this.className='inputSelecionado'"
                               onBlur="this.className=''">
                    </div>
                </div>

                <div class="center">
                    <input name="_spring_security_remember_me" type="checkbox"> Permanecer logado
                </div>

                <div class="LinhaEntrada">
                    <div class="Buttons">
                        <input class="BOTAO" name="submeter"
                               value="Entrar no sistema" type="submit" />
                    </div>
                </div>

            </FORM>
        </div>
        <%@include file="googleAnalytics"%>
    </BODY>
</HTML>
