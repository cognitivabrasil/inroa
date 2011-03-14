<%-- 
    Document   : testeaddmap
    Created on : 23/02/2011, 18:02:49
    Author     : Marcos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="StyleSheet" href="../css/padrao.css" type="text/css">
        <script language="JavaScript" type="text/javascript" src="./funcoesMapeamento.js">
            //funcoes javascript que chamam o ajax
        </script>
        <script language="JavaScript" type="text/javascript" src="../scripts/funcoes.js">
            //necessario para usar o ajax
        </script>
    </head>
    <body>
        <select name="padrao_metadados" id="padraoMet" onFocus="this.className='inputSelecionado'" onBlur="this.className=''"">
                            <option value="" selected>Selecione
                             <option value="1" onclick="selecionaMapeamento('resultado', 'padraoMet')">um
                                 <option value="2" onclick="selecionaMapeamento('resultado', 'padraoMet')">dois
        </select>
        <input type="button" value="Ajax" size="30" name="editar" id="editar"  onclick="selecionaMapeamento('resultado', 'padraoMet')"/>
        <div id='resultado'>resultado</div>
    </body>
</html>
