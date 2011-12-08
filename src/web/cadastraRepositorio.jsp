<%--
    Document   : cadastraRepositorio
    Created on : 03/08/2009, 16:12:33
    Author     : Marcos

Primeira etapa do cadastro de um repositorio
--%>

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
        <%@include file="testaSessaoNovaJanela.jsp"%>
        <link href="imagens/favicon.ico" rel="shortcut icon" type="image/x-icon" />
        <link rel="StyleSheet" href="./css/padrao.css" type="text/css"/>
        <script type="text/javascript" src="./scripts/validatejs.js"></script>
        
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
            myForm.addRules({id:'metPrefix',option:'required',error:'* Deve ser informado o MetadataPrefix!'});
            myForm.addRules({id:'namespace',option:'required',error:'* Deve ser informado o NameSpace!'});
            myForm.addRules({id:'confereLinkOAI',option:'required',error:'* A url informada n&atilde;o responde ao protocolo OAI-PMH!'});
        </script>
    </head>
    <body>

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
                        <select name="padrao_metadados" id="padraoMet" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" onChange="selecionaMapeamento('resultado', this.value, 'cadastra');">
                            <option value="" selected>Selecione
                                <%
                                            //Carrega do banco de dados os padroes de metadados cadastrados
                                            //postgres ok
                                            ResultSet res = stm.executeQuery("SELECT nome, id FROM padraometadados ORDER BY nome ASC");
                                            while (res.next()) {
                                                if (!res.getString("nome").equalsIgnoreCase("todos")) {
                                                    out.println("<option value='" + res.getString("id") + "'>" + res.getString("nome").toUpperCase());
                                                }

                                            }
                                           

                                %>

                        </select>
                                
                    </div>
                    </div>
                    <div class="LinhaEntrada">
                    <div class="Label">
                        Mapeamento:
                    </div>
                                <div id='resultado'>
                                    <div class="Value">Selecione um padr&atilde;o</div>
                                    <input type="hidden" id="rdMap"  name="tipo_map" value=""> 
                                    <input type="hidden" id="metPrefix"  name="metPrefix" value="">
                                    <input type="hidden" id="namespace"  name="namespace" value="">
                                </div>
                                     

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
                        <input name="url" id="url" type="text" maxlength="200" onFocus="this.className='inputSelecionado'" onBlur="this.className='';verificaLinkOAI(this.value, this, document.getElementById('resultadoTesteOAI'), document.getElementById('confereLinkOAI'))"/>&nbsp;<div id="resultadoTesteOAI" class="linkCantoDireito"></div>
                    </div>
                    <input type="hidden" id="confereLinkOAI" value="">
                </div>

                <div class="LinhaEntrada">
                    <div class="Label">
                        Cole&ccedil;&otilde;es ou Comunidades (opcional):
                    </div>
                    <div class="Comentario">
                        Se for mais de uma separar por ponto e v&iacute;rgula.
                    </div>
                    <div class="Comentario">
                        Ex: com1;com2;com3
                    </div>
                    <div class="Value">
                        <input name="set" value="" id="set" type="text" maxlength="45" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
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