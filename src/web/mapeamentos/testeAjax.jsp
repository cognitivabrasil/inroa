<%-- 
    Document   : testeAjax
    Created on : 09/09/2010, 12:30:17
    Author     : Marcos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<!dsdsdsdsds>

<html>
    <head>
        <title>Teste</title>
        <script language="JavaScript" type="text/javascript" src="../scripts/funcoes.js">
            //necessario para usar o ajax
        </script>
        <script type="text/javascript">

            /**
             * Função utilizada pelo mapeamento dinamico com ajax.
             * Quando chamada, ela repassa os dados, utilizando ajax, para o arquivo jsp que rodará sem que a pagina principal seja recarregada.
             */
            function salvar(idResultado)
            {
                var nome = document.getElementById('nome').value; //Note que as variáveis são resgatadas pela função getElementById.


                var exibeResultado = document.getElementById(idResultado);

                var ajax = openAjax(); // Inicia o Ajax.
                ajax.open("GET", "respostaAjax.jsp?nome=" + nome, true); // Envia o termo da busca como uma querystring, nos possibilitando o filtro na busca.
                ajax.onreadystatechange = function()
                {
                    if(ajax.readyState == 1) // Quando estiver carregando, exibe: carregando...
                    {
                        exibeResultado.innerHTML = "Aguarde...";
                    }
                    if(ajax.readyState == 4) // Quando estiver tudo pronto.
                    {
                        if(ajax.status == 200)
                        {
                            var resultado = ajax.responseText;
                            exibeResultado.innerHTML = resultado;
                        }
                        else
                        {
                            exibeResultado.innerHTML = "Erro nas funções do Ajax";
                        }
                    }
                }
                ajax.send(null); // submete
                document.getElementById("nome").value= "";//limpa os campos
                document.getElementById("nome").setFocus=true;

            }
            
        </script>



        <link rel="stylesheet" type="text/css" href="tabelacss.css"/>
    </head>

    <body>
<%
String idResultado="exibeResultado";
%>

        <input type="hidden" id="nome" value="marcos"/>
        <input type="button" size="30" name="gravar" id="gravar" value="Gravar" onclick="salvar('<%=idResultado%>')">
        <div id="exibeResultado" align="center"><%="em espera"%></div>
        <div id="res1" align="center"><%="em espera"%></div>

    </body>
</html>