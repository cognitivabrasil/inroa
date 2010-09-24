<%-- 
    Document   : listaStowords
    Created on : 31/03/2010, 12:37:50
    Author     : Marcos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="testaSessaoNovaJanela.jsp"%>
<%@include file="conexaoBD.jsp"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FEB - Ferramenta Administrativa</title>
        <link rel="StyleSheet" href="css/padrao.css" type="text/css">
        <link href="imagens/favicon.ico" rel="shortcut icon" type="image/x-icon" />
    </head>

    <body>
        <div id="page">
            <form name="editaGeral" action="editarRepositorio.jsp" method="post" onsubmit="return myForm.Apply('MensagemErro')">
                <div class="TextoDivAlerta" id="MensagemErro"><!--Aqui o script colocara a mensagem de erro, se ocorrer--></div>
                <div class="LinhaEntrada">
                    <div class="Label">
                        Nova StopWord:
                    </div>
                    <div class="Value">
                        <input type="text" id="nameRep" name="nomeRep" value="" maxlength="45" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>
                </div>
                <input type="hidden" name="apagar" value="sim"/>
                <div class="LinhaEntrada">
                    <div class="Buttons">
                        <input type="button" value="< Voltar" onclick="javascript:history.go(-1);"/>
                        <input type="submit" value="Gravar" name="submit" />

                    </div>
                </div>

            </form>
            <%
            String sql = "SELECT id, stopword FROM stopwords";

        ResultSet res = stm.executeQuery(sql);
        while(res.next()){

/*            out.print("<div class='LinhaEntrada'>" +
                    "<div class='Label'><a title='Remover' onclick=''>" +
                    " <img src='./imagens/ico24_deletar.gif' border='0' width='20' height='20' alt='Apagar' align='middle'>" +
                    "</a></div>" +
        "<div class='Value'>"+res.getString("stopword")+"</div>" +
                    "</div>");*/
            
        %>
        <input type="checkbox" name="<%=res.getString("id")%>" value="<%=res.getString("id")%>"><%=res.getString("stopword")%>
            <%
        }
            %>
            
        </div>
    

    </body>
    <%
            con.close(); //fechar conexao com mysql
    %>
</html>
