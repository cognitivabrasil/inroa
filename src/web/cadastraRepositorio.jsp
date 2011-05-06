<%--
    Document   : cadastraRepositorio
    Created on : 03/08/2009, 16:12:33
    Author     : Marcos

Primeira etapa do cadastro de um repositorio
--%>
<%@include file="testaSessaoNovaJanela.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="conexaoBD.jsp"%>
<%
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FEB - Ferramenta Administrativa</title>
        <link href="imagens/favicon.ico" rel="shortcut icon" type="image/x-icon" />
        <link rel="StyleSheet" href="./css/padrao.css" type="text/css"/>
        <script type="text/javascript" src="./scripts/validatejs.js"></script>
        <script type="text/javascript" src="./scripts/funcoes.js"></script>
        <script language="JavaScript" type="text/javascript" src="./mapeamentos/funcoesMapeamento.js">
            //funcoes javascript que chamam o ajax
        </script>
        <script type="text/javascript">
            var myForm = new Validate();
            myForm.addRules({id:'nameRep',option:'required',error:'* Voc&ecirc; deve informar o nome do reposit&oacute;rio!'});
            myForm.addRules({id:'descricao',option:'required',error:'* Deve ser informarmada uma descri&ccedil;&atilde;o!'});
            myForm.addRules({id:'padraoMet',option:'required',error:'* Deve ser informado o padr&atilde;o dos metadados do repositorio!'});
            myForm.addRules({id:'periodicidade',option:'required',error:'* Deve ser informado a periodicidade de atualiza&ccedil;&atilde;o. Em dias!'});
            myForm.addRules({id:'url',option:'urlcomip',error:'* Deve ser informada uma url <b>v&aacute;lida</b> que responda com protocolo OAI-PMH! Come&ccedil;ando por http://'});
            myForm.addRules({id:'rdMap',option:'required',error:'* Deve ser selecionado o tipo de mapeamento!'});
        </script>
    </head>
    <body id="bodyMenor">

        <div id="page">

            <div class="subTitulo-center">&nbsp;Entre com as informa&ccedil;&otilde;es para cadastrar um novo reposit&oacute;rio</div>
            <div class="EspacoAntes">&nbsp;</div>

            <form name="adicionarRepositorio" action="gravaRepositorioBase.jsp" method="post" onsubmit="return myForm.Apply('MensagemErro')">
                <div class="TextoDivAlerta" id="MensagemErro"><!--Aqui o script colocara a mensagem de erro, se ocorrer--></div>
                <div class="subtitle">Informa&ccedil;&otilde;es gerais sobre o reposit&oacute;rio</div>
                <div class="LinhaEntrada">
                    <div class="Label">
                        Nome:
                    </div>
                    <div class="Value">
                        <input type="text" id="nameRep" name="nomeRep" maxlength="45" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>
                </div>
                <div class="LinhaEntrada">
                    <div class="Label">
                        Descri&ccedil;&atilde;o:
                    </div>
                    <div class="Value">
                        <input name="descricao" id="descricao" type="text" maxlength="455" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>
                </div>
                <div class="LinhaEntrada">
                    <div class="Label">
                        Padr&atilde;o de metadados utilizado:
                    </div>
                    <div class="Value">
                        <select name="padrao_metadados" id="padraoMet" onFocus="this.className='inputSelecionado'" onBlur="this.className=''">
                            <option value="" selected onclick="selecionaMapeamento('resultado', 'padraoMet', 'cadastra')">Selecione
                                <%
                                            //Carrega do banco de dados os padroes de metadados cadastrados
                                            //postgres ok
                                            ResultSet res = stm.executeQuery("SELECT nome, id FROM padraometadados ORDER BY nome ASC");
                                            while (res.next()) {
                                                if (!res.getString("nome").equalsIgnoreCase("todos")) {
                                                    out.println("<option value='" + res.getString("id") + "' onclick=\"selecionaMapeamento('resultado', 'padraoMet', 'cadastra')\">" + res.getString("nome").toUpperCase());
                                                }

                                            }

                                %>

                        </select>
                    </div>
                    </div>
                    <div class="LinhaEntrada">
                    <div class="Label">
                        Tipo de mapeamento:
                    </div>
                                <div id='resultado'>
                                    <div class="Value">Selecione um padr&atilde;o</div>
                                    <input type="hidden" id="rdMap"  name="tipo_map" value=""> </div>
                            <div class="Espaco"> &nbsp;</div>
                    </div>
                

                <div class="subtitle">Informa&ccedil;&otilde;es sobre o configura&ccedil;&atilde;o da federa&ccedil;&atilde;o</div>
                <div class="LinhaEntrada">
                    <div class="Label">
                        Periodicidade de atualiza&ccedil;&atilde;o (em dias):
                    </div>
                    <div class="Value">
                        <input name="periodicidade" id="periodicidade" type="text" maxlength="3" onkeypress ="return ( isNumber(event) );" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>
                </div>

                <div class="subtitle">Sincroniza&ccedil;&atilde;o dos metadados</div>
                <div class="LinhaEntrada">
                    <div class="Label">
                        URL que responde OAI-PMH:
                    </div>
                    <div class="Value">
                        <input name="url" id="url" type="text" maxlength="455" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>
                </div>

                <div class="LinhaEntrada">
                    <div class="Buttons">
                        <input type="reset" value="Limpar" class="CancelButton" onclick="javascript:window.location.reload();"/>
                        <input id="cancelar" onclick="javascript:window.close();" value="Cancelar" type="button" class="CancelButton"/>
                        <input type="submit" value="Avan&ccedil;ar >" name="submit" />
                    </div>
                </div>

            </form>

        </div>
        <%@include file="googleAnalytics"%>
    </body>
</html>
<%
            con.close(); //fechar conexao
%>