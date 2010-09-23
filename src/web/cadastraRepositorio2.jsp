<%--
    Document   : cadastraRepositorio2
    Created on : 13/08/2009, 12:33:05
    Author     : Marcos

Segunda etapa do cadastro de um repositorio
--%>
<%@include file="testaSessaoNovaJanela.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
%>
<%@include file="conexaoBD.jsp"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FEB - Ferramenta Administrativa</title>
        <link href="imagens/favicon.ico" rel="shortcut icon" type="image/x-icon" />
        <link rel="StyleSheet" href="./css/padrao.css" type="text/css"/>
        <script language="JavaScript" type="text/javascript" src="scripts/funcoes.js"></script>
        <script type="text/javascript" src="./scripts/validatejs.js"></script>
        <script type="text/javascript">
            var myForm = new Validate();

            myForm.addRules({id:'ldapDesc',option:'required',error:'* Deve ser selecionado uma base LDAP para armazenar os metadados!'});


            myForm.addRules({id:'ipOr',option:'isIP',error:'* O ip informado para o Ldap de origem n&atilde;o est&aacute; correto!'});
            myForm.addRules({id:'portaOr',option:'required',error:'* Deve ser informada a porta do Ldap de origem!'});
            myForm.addRules({id:'dnOr',option:'required',error:'* Deve ser informado o DN do Ldap de origem!'});
            myForm.addRules({id:'loginOr',option:'required',error:'* Deve ser informado o login do Ldap de origem!'});
            myForm.addRules({id:'senhaOr',option:'required',error:'* Deve ser informada a senha do Ldap de origem!'});
            myForm.addRules({id:'confSenhaOr',option:'required',error:'* A senha do LDAP de origem deve ser repetida no campo indicado!'});
            myForm.addRules({id:'senhaOr', to:'confSenhaOr', option:'isEqual',error:'* As senhas digitadas para o LDAP origem n&atilde;o est&atilde;o iguais!'});
            myForm.addRules({id:'url',option:'urlcomip',error:'* Deve ser informada uma url <b>v&aacute;lida</b> que responda com protocolo OAI-PMH! Come&ccedil;ando por http://'});
            //


        </script>
    </head>
    <body id="bodyMenor">
        <div id="page">

            <%
            //armazena em variaveis os dados preenchidos no formulario
            String nome = request.getParameter("nomeRep").trim();
            String descricao = request.getParameter("descricao").trim();
            String padrao_metadados = request.getParameter("padrao_metadados").trim();

            String periodicidade = request.getParameter("periodicidade").trim();
            //String nomeNaFederacao = request.getParameter("nomeNaFederacao").trim();
            String tipoSinc = request.getParameter("sincronizacao").trim();


            //request.setAttribute("nomeRep", nome);


            //se algum campo estiver em branco exibe alerta e volta ao formulario
            if (nome.isEmpty() || descricao.isEmpty() || periodicidade.isEmpty() ||
                    tipoSinc.isEmpty() || padrao_metadados.isEmpty()) {
                //nomeNaFederacao.isEmpty() ||
                out.print("<script type='text/javascript'>alert('Todos os campos devem ser preenchidos!');</script>" +
                        "<script type='text/javascript'>history.back(-1);</script>");
            }

            //armazena os valores na sessao
            session.setAttribute("nomeRep", nome);
            session.setAttribute("descricao", descricao);
            session.setAttribute("padrao_metadados", padrao_metadados);
            session.setAttribute("periodicidade", periodicidade);
            //session.setAttribute("nomeNaFederacao", nomeNaFederacao);
            session.setAttribute("sincronizacao", tipoSinc);

            String descricaoLDAP="";

            %>

            <div class="subTitulo-center">&nbsp;Complete as informa&ccedil;&otilde;es sobre o reposit&oacute;rio <%=nome%> </div>
            <form name="adicionarRepositorio" action="gravaRepositorioBase.jsp" method="post" onsubmit="return myForm.Apply('MensagemErro')">
                <div class="TextoDivAlerta" id="MensagemErro"><!--Aqui o script colocara a mensagem de erro, se ocorrer--></div>



                <div class="subtitle">Ldap local - Onde ser&atilde;o armazenados os metadados</div>
                <div class="LinhaEntrada">
                    <div class="Label">
                        Base LDAP:
                    </div>
                    <div class="Value">
                        <select name="ldap_destino" id="ldapDesc" onFocus="this.className='inputSelecionado'" onBlur="this.className=''">
                            <option value="" selected onclick="insereValorDiv('divDesc','Selecione um LDAP')">Selecione
                                <%
                //Carrega do banco de dados os padroes de metadados cadastrados
                ResultSet res = stm.executeQuery("SELECT nome, id, descricao FROM ldaps WHERE nome NOT LIKE '%Meta%Diretorio%' ORDER BY nome ASC;");
                while (res.next()) {
                    descricaoLDAP=res.getString("descricao");
                    
                        out.println("<option value=" + res.getString("id") + " onclick=\"insereValorDiv('divDesc', '"+descricaoLDAP+"')\">" + res.getString("nome").toUpperCase());
                        //descricaoLDAP=res.getString("descricao");
                }

                                %>

                        </select>
                                
                    </div>
                </div>

                <div class="LinhaEntrada">
                    <div class="Label">
                        Descrição:
                    </div>
                    <div class="Value">
                        
                        <div id="divDesc">Selecione a base LDAP e veja aqui sua descrição.</div>
                    </div>
                </div>
                                <div class="LinhaEntrada">
                    <div class="Label">
                        &nbsp;<a href="./cadastraLDAP.jsp">Cadastrar novo LDAP</a>
                    </div>
                    <div class="Value">
                        &nbsp;
                    </div>
                </div>
                
                <%
            if (tipoSinc.equalsIgnoreCase("OAI-PMH")) {
                %>
                <div class="subtitle">Sincroniza&ccedil;&atilde;o dos metadados</div>
                <div class="LinhaEntrada">
                    <div class="Label">
                        URL que responde OAI-PMH:
                    </div>
                    <div class="Value">
                        <input name="url" id="url" type="text" maxlength="455" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>
                </div>

                <input type="hidden" id="ipOr" value="100.100.100.100">
                <input type="hidden" id="portaOr" value="null">
                <input type="hidden" id="dnOr" value="null">
                <input type="hidden" id="loginOr" value="null">
                <input type="hidden" id="senhaOr" value="null">
                <input type="hidden" id="confSenhaOr" value="null">
                <input type="hidden" id="senhaOr" value="null">
                <%                } else if (tipoSinc.equalsIgnoreCase("LDAP")) {
                %>
                <input type="hidden" id="url" value="http://null.com.br">
                <div class="subtitle">Sincroniza&ccedil;&atilde;o dos metadados
                    <p>- Dados sobre o Ldap de origem</div>
                <div class="LinhaEntrada">
                    <div class="Label">
                        Endere&ccedil;o ip:
                    </div>
                    <div class="Value">
                        <input name="ipOrigem" id="ipOr" type="text" onkeypress ="return ( maskIP(event,this) );" maxlength="15" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>
                </div>
                <div class="LinhaEntrada">
                    <div class="Label">
                        Porta:
                    </div>
                    <div class="Value">
                        <input name="portaOrigem" id="portaOr" type="text" maxlength="4" onkeypress ="return ( isNumber(event) );" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>
                </div>
                <div class="LinhaEntrada">
                    <div class="Label">
                        DN:
                    </div>
                    <div class="Value">
                        <input name="dnOrigem" id="dnOr" type="text" maxlength="100" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>
                </div>
                <div class="LinhaEntrada">
                    <div class="Label">
                        Login:
                    </div>
                    <div class="Value">
                        <input name="loginOrigem" id="loginOr" type="text" maxlength="100" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>
                </div>
                <div class="LinhaEntrada">
                    <div class="Label">
                        Senha:
                    </div>
                    <div class="Value">
                        <input name="senhaOrigem" id="senhaOr" type="password" maxlength="100" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>
                </div>
                <div class="LinhaEntrada">
                    <div class="Label">
                        Repita a senha:
                    </div>
                    <div class="Value">
                        <input name="confSenhaOrigem" id="confSenhaOr" type="password" maxlength="100" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>
                </div>

                <%                }
                %>
                <div class="LinhaEntrada">
                    <div class="Buttons">
                        <input type="button" value="< Voltar" onclick="javascript:history.back(-1);"/>
                        <input type="reset" value="Limpar" class="CancelButton" onclick="javascript:window.location.reload();"/>
                        <input id="cancelar" onclick="javascript:window.close();" value="Cancelar" type="button" class="CancelButton"/>
                        <input type="submit" value="Gravar >" name="submit" />
                    </div>
                </div>
            </form>
        </div>
                <%@include file="googleAnalytics"%>
    </body>
</html>
<%
con.close(); //fechar conexao com mysql
%>