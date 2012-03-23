<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>FEB - Ferramenta Administrativa</title>
        <link href="imagens/favicon.ico" rel="shortcut icon" type="image/x-icon" />
        <link rel="StyleSheet" href="css/padrao.css" type="text/css">
<script type="text/javascript" src="./scripts/validatejs.js"></script>

<script type="text/javascript">
    var myForm = new Validate();

    myForm.addRules({id:'user',option:'required',error:'* Voc&ecirc; deve informar o seu nome de usu&aacute;rio!'});
    myForm.addRules({id:'passwd',option:'required',error:'* Voc&ecirc; deve informar sua senha!'});
</script>


    </HEAD>

    <body id="bodyMenor">

        <div id="page">

            <div class="subTitulo-center">&nbsp;Digite seu usu&aacute;rio e sua senha para acessar o sistema</div>
            <div class="linkCantoEsquerdo"><a href="./">Ir para Ferramenta de Busca</a></div>
            <div class="clear"></div>
            <FORM name="login" action="login" method="post" onsubmit="return myForm.Apply('MensagemErro')">

                <div class="EspacoAntes">&nbsp;</div>
                <div class="TextoErro" id="MensagemErro">
                    <!--Aqui o script colocara a mensagem de erro, se ocorrer-->
                    <c:out value="${erro}" />
                </div>
                <div class="LinhaEntrada">

                    <label class="Label"><B>Usu&aacute;rio:&nbsp;</B></label>
                    <div class="value">
                        <input name="login" id="user" class="ImeDisabled" maxlength="20" type="text" onFocus="this.className='inputSelecionado'" onBlur="this.className=''">
                    </div>
                </div>
                <div class="LinhaEntrada">
                    <label class="Label"><B>Senha:&nbsp;</B></label>
                    <div class="value">
                        <input name="senha" id="passwd" type=password onFocus="this.className='inputSelecionado'" onBlur="this.className=''">
                    </div>
               
                    <div class="Buttons">
                        <input class="BOTAO" name="submeter" value="Entrar no sistema" type="submit"/>
                    </div>
                </div>

            </FORM>
        </div>
        <script type="text/javascript">
            var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
            document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
        </script>
        <script type="text/javascript">
            try {
                var pageTracker = _gat._getTracker("UA-15028081-2");
                pageTracker._trackPageview();
            } catch(err) {}
        </script>
    </BODY>
</HTML>
