<%-- 
    Document   : editarRepositorio
    Created on : 18/08/2009, 14:59:26
    Author     : Marcos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="testaSessaoNovaJanela.jsp"%>
<%@include file="conexaoBD.jsp"%>
<%@include file="tiposdeSincronizacaoSuportados.jsp"%>
<%@page import="operacoesLdap.Editar" %>
<%
            request.setCharacterEncoding("UTF-8");
//response.setCharacterEncoding("UTF-8");
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>FEB - Ferramenta Administrativa</title>
        <link rel="StyleSheet" href="css/padrao.css" type="text/css"/>
        <script language="JavaScript" type="text/javascript" src="scripts/funcoes.js"></script>
        <script type="text/javascript" src="./scripts/validatejs.js"></script>
        <link href="imagens/favicon.ico" rel="shortcut icon" type="image/x-icon" />
    </head>

    <body>
        <div id="page">
            <%
            boolean formNull = true;
            try {
                String apagar = request.getParameter("apagar");
                if (apagar.equals("sim")) {
                    formNull = false;
                }
            } catch (Exception e) {
                formNull = true;

            }

            if (formNull) {


                String id = request.getParameter("id");
                String tipo = request.getParameter("campo");

                if (tipo.equalsIgnoreCase("geral")) {

 //                   String sql = "SELECT r.nome, r.descricao, p.nome as nome_padrao " +
 //                           " FROM repositorios r, info_repositorios i, dadosldap d, padraometadados p " +
 //                           " WHERE r.id=" + id + " " +
 //                           "AND r.id=i.id_repositorio " +
 //                           "AND i.padrao_metadados=p.id;";

                    String sql = "SELECT r.nome, r.descricao, p.nome as nome_padrao" +
                             " FROM repositorios r, info_repositorios i, dadosldap d, padraometadados p"+
                             " WHERE r.id="+id+" AND r.id=i.id_repositorio"+
                             " AND i.padrao_metadados=p.id"+
                             " GROUP BY r.nome, r.descricao, p.nome;";

                    ResultSet res = stm.executeQuery(sql);

                    res.next();
                    String nomePadrao = res.getString("nome_padrao");
            %>
            <script type="text/javascript">
                var myForm = new Validate();
                myForm.addRules({id:'nameRep',option:'required',error:'* Voc&ecirc; deve informar o nome do reposit&oacute;rio!'});
                myForm.addRules({id:'descricao',option:'required',error:'* Deve ser informarmada uma descri&ccedil;&atilde;o!'});
                myForm.addRules({id:'padraoMet',option:'required',error:'* Deve ser informado o padr&atilde;o dos metadados do repositorio!'});

            </script>

            <div class="subTitulo-center">&nbsp;Editando reposit&oacute;rio <%=res.getString("nome")%></div>
            <div class="subtitulo">Informa&ccedil;&otilde;es gerais</div>
            <div class="EspacoAntes">&nbsp;</div>
            <form name="editaGeral" action="editarRepositorio.jsp" method="post" onsubmit="return myForm.Apply('MensagemErro')">
                <div class="TextoDivAlerta" id="MensagemErro"><!--Aqui o script colocara a mensagem de erro, se ocorrer--></div>
                <div class="LinhaEntrada">
                    <div class="Label">
                        Nome:
                    </div>
                    <div class="Value">
                        <input type="text" id="nameRep" name="nomeRep" value="<%=res.getString("nome")%>" maxlength="45" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>
                </div>
                <div class="LinhaEntrada">
                    <div class="Label">
                        Descri&ccedil;&atilde;o:
                    </div>
                    <div class="Value">
                        <input name="descricao" id="descricao" type="text" value="<%=res.getString("descricao")%>" maxlength="455" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>
                </div>
                <div class="LinhaEntrada">
                    <div class="Label">
                        Padr&atilde;o de metadados utilizado:
                    </div>
                    <div class="Value">
                        <select name="padrao_metadados" id="padraoMet" onFocus="this.className='inputSelecionado'" onBlur="this.className=''">
                            <%
                //Carrega do banco de dados os padroes de metadados cadastrados
                ResultSet resPadrao = stm.executeQuery("SELECT nome, id FROM padraometadados ORDER BY nome ASC");
                while (resPadrao.next()) {
                    String nomePadraoTemp = resPadrao.getString("nome");

                    if (nomePadraoTemp.equalsIgnoreCase(nomePadrao)) {
                        out.println("<option value=" + resPadrao.getString("id") + " selected>" + resPadrao.getString("nome").toUpperCase());
                    } else {
                        out.println("<option value=" + resPadrao.getString("id") + ">" + resPadrao.getString("nome").toUpperCase());
                    }


                }
                            %>
                        </select>
                    </div>
                </div>
                <input type="hidden" name="id" value="<%=id%>"/>
                <input type="hidden" name="campo" value="<%=tipo%>"/>
                <input type="hidden" name="apagar" value="sim"/>
                <div class="LinhaEntrada">
                    <div class="Buttons">
                        <input type="button" value="< Voltar" onclick="javascript:history.go(-1);"/>
                        <input type="submit" value="Gravar >" name="submit" />

                    </div>
                </div>

            </form>
            <%
            } else if (tipo.equalsIgnoreCase("config")) {

                String sql = "SELECT r.*, i.*, p.nome as nome_padrao " +
                        " FROM repositorios r, info_repositorios i, padraometadados p " +
                        " WHERE r.id=" + id + " " +
                        " AND r.id=i.id_repositorio" +
                        " AND i.padrao_metadados=p.id" +
                        " ORDER BY r.nome ASC";
                
                ResultSet res = stm.executeQuery(sql);

                res.next();                
            %>

            <script type="text/javascript">
                var myForm = new Validate();
                myForm.addRules({id:'periodicidade',option:'required',error:'* Deve ser informado a periodicidade de atualiza&ccedil;&atilde;o. Em horas!'});
                myForm.addRules({id:'tipoSinc',option:'required',error:'* Deve ser informado o tipo de sincroniza&ccedil;&atilde;o que o reposit&oacute;rio suporta!'});
            </script>

            <!--Informações configuração-->
            <div class="subTitulo-center">&nbsp;Editando reposit&oacute;rio <%=res.getString("nome")%></div>
            <form name="editaConfig" action="editarRepositorio.jsp" method="post" onsubmit="return myForm.Apply('MensagemErro')">
                <div class="TextoDivAlerta" id="MensagemErro"><!--Aqui o script colocara a mensagem de erro, se ocorrer--></div>
                <div class="subtitulo">Informa&ccedil;&otilde;es sobre o configura&ccedil;&atilde;o da federa&ccedil;&atilde;o</div>
                <div class="EspacoAntes">&nbsp;</div>
                <div class="LinhaEntrada">
                    <div class="Label">
                        Periodicidade de atualiza&ccedil;&atilde;o:
                    </div>
                    <div class="Value">
                        <input name="periodicidade" value="<%=res.getInt("periodicidade_horas")%>" id="periodicidade" type="text" maxlength="3" onkeypress ="return ( isNumber(event) );" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>
                </div>
                <div class="LinhaEntrada">
                    <div class="Label">
                        Nome na federa&ccedil;&atilde;o:
                    </div>
                    <div class="Value"><%=res.getString("nome")%></div>
                </div>

                <div class="LinhaEntrada">
                    <div class="Label">
                        Tipo de Sincroniza&ccedil;&atilde;o:
                    </div>

                </div>
                <input type="hidden" name="id" value="<%=id%>"/>
                <input type="hidden" name="campo" value="<%=tipo%>"/>
                <input type="hidden" name="apagar" value="sim"/>
                <div class="LinhaEntrada">
                    <div class="Buttons">
                        <input type="button" value="< Voltar" onclick="javascript:history.go(-1);"/>
                        <input type="submit" value="Gravar >" name="submit" />
                    </div>
                </div>
            </form>

            <%
                //Informações sobre a federação local
                } else if (tipo.equalsIgnoreCase("ldaplocal")) {

                //String sql = "SELECT d.*, r.nome FROM dadosldap d, repositorios r WHERE d.id_repositorio=" + id + " " +
                //        "AND r.id=d.id_repositorio";

                //ResultSet res = stm.executeQuery(sql);
                ResultSet res = stm.executeQuery("SELECT nome, id, descricao FROM dados_subfederacoes WHERE nome NOT LIKE '%Meta%Diretorio%' ORDER BY nome ASC;");
                //res.next();

                String nomeLdapLocal = request.getParameter("ldapLocal");

            %>

            <div class="subTitulo-center">&nbsp;Editando reposit&oacute;rio</div>
            <form name="editaGeral" action="editarRepositorio.jsp" method="post" onsubmit="return myForm.Apply('MensagemErro')">
                <div class="TextoDivAlerta" id="MensagemErro"><!--Aqui o script colocara a mensagem de erro, se ocorrer--></div>

                <div class="subtitle">Dados da Federa&ccedil;&atilde;o - Onde ser&atilde;o armazenados os metadados</div>
                
                <div class="TextoDivAlerta"><b>Aten&ccedil;&atilde;o:</b> Se o repositorio for alterado, seus metadados ser&atilde;o perdidos, ou n&atilde;o.</div>
                <div class="EspacoAntes">&nbsp;</div>
                <div class="LinhaEntrada">
                    <div class="Label">
                        Federa&ccedil;&atilde;o:
                    </div>
                    <div class="Value">
                        <select name="nome" id="ldapDesc" onFocus="this.className='inputSelecionado'" onBlur="this.className=''">
                            <%
            //Carrega do banco de dados os padroes de metadados cadastrados

            while (res.next()) {

                if (nomeLdapLocal.equalsIgnoreCase(res.getString("nome"))) {
                    out.println("<option value=" + res.getString("id") + " selected>" + res.getString("nome").toUpperCase());
                } else {
                    out.println("<option value=" + res.getString("id") + ">" + res.getString("nome").toUpperCase());
                }
                //descricaoLDAP=res.getString("descricao");
            }

                            %>

                        </select>

                    </div>
                </div>

                <input type="hidden" name="id" value="<%=id%>"/>
                <input type="hidden" name="campo" value="<%=tipo%>"/>
                <input type="hidden" name="apagar" value="sim"/>
                <div class="LinhaEntrada">
                    <div class="Buttons">
                        <input type="button" value="< Voltar" onclick="javascript:history.go(-1);"/>
                        <input type="submit" value="Gravar >" name="submit" />
                    </div>
                </div>

            </form>

            <%
                //Informações Federação de origem
                } else if (tipo.equalsIgnoreCase("ldaporigem")) {
                String sql = "SELECT r.nome as nome, d.*, i.url_or_ip FROM dados_subfederacoes d, repositorios r, info_repositorios i WHERE r.id= " + id + " " +
                        " AND i.id_federacao=d.id" +
                        " AND r.id=i.id_repositorio";

                ResultSet res = stm.executeQuery(sql);
                res.next();

                String ip = res.getString("url_or_ip");
                int portaLdapOrigem = res.getInt("porta");
                String portaOrigem = "";                
                String login_ldap_origem = res.getString("login");
                if (ip == null) {
                    ip = "";
                }
                if (portaLdapOrigem == 0) {
                    portaOrigem = "";
                } else {
                    portaOrigem = String.valueOf(portaLdapOrigem);
                }

                if (login_ldap_origem.equalsIgnoreCase("null")) {
                    login_ldap_origem = "";
                }

            %>
            <script type="text/javascript">
                var myForm = new Validate();

            myForm.addRules({id:'nome',option:'required',error:'* Voc&ecirc; deve informar o nome do reposit&oacute;rio!'});
            myForm.addRules({id:'descricao',option:'required',error:'* Deve ser informarmada uma descri&ccedil;&atilde;o!'});
            myForm.addRules({id:'ipDes',option:'isIP',error:'* O ip informado para a federa&ccedil;&atilde;o local n&atilde;o est&aacute; correto!'});
            myForm.addRules({id:'portaDes',option:'required',error:'* Deve ser informada a porta da base de dados (Padr&atilde;o PostgreSQL 2345)!'});
            myForm.addRules({id:'loginDes',option:'required',error:'* Deve ser informado o login do da base de dados da subfedera&ccedil;&atilde;o!'});
            myForm.addRules({id:'senhaDes',option:'required',error:'* Deve ser informada a senha do da base de dados da subfedera&ccedil;&atilde;o!'});
            myForm.addRules({id:'confSenhaDes',option:'required',error:'* A senha da base de dados da da subfedera&ccedil;&atilde;o deve ser repetida no campo indicado!'});
            myForm.addRules({id:'senhaDes', to:'confSenhaDes', option:'isEqual',error:'* As senhas digitadas n&atilde;o est&atilde;o iguais!'});

            </script>
            <div class="subTitulo-center">&nbsp;Editando reposit&oacute;rio <%=res.getString("nome")%></div>
            <form name="editaGeral" action="editarRepositorio.jsp" method="post" onsubmit="return myForm.Apply('MensagemErro')">
                <div class="TextoDivAlerta" id="MensagemErro"><!--Aqui o script colocara a mensagem de erro, se ocorrer--></div>

                <div class="subtitulo">Dados sobre a Federa&ccedil;&atilde; de origem</div>
                <div class="EspacoAntes">&nbsp;</div>
                <div class="LinhaEntrada">
                    <div class="Label">
                        Endere&ccedil;o ip:
                    </div>
                    <div class="Value">
                        <input name="ipOrigem" value="<%=ip%>" id="ipOr" type="text" onkeypress ="return ( maskIP(event,this) );" maxlength="15" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>
                </div>
                <div class="LinhaEntrada">
                    <div class="Label">
                        Porta:
                    </div>
                    <div class="Value">
                        <input name="portaOrigem" value="<%=portaOrigem%>" id="portaOr" type="text" maxlength="4" onkeypress ="return ( isNumber(event) );" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>
                </div>
                <div class="LinhaEntrada">
                    <div class="Label">
                        Login:
                    </div>
                    <div class="Value">
                        <input name="loginOrigem" value="<%=login_ldap_origem%>" id="loginOr" type="text" maxlength="100" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>
                </div>
                <div class="LinhaEntrada">
                    <div class="Label">
                        Senha:
                    </div>
                    <div class="Value">
                        <input name="senhaOrigem" value="<%=res.getString("senha")%>" id="senhaOr" type="password" maxlength="100" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>
                </div>
                <div class="LinhaEntrada">
                    <div class="Label">
                        Repita a senha:
                    </div>
                    <div class="Value">
                        <input name="confSenhaOrigem" value="<%=res.getString("senha")%>" id="confSenhaOr" type="password" maxlength="100" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>
                </div>
                <input type="hidden" name="id" value="<%=id%>"/>
                <input type="hidden" name="campo" value="<%=tipo%>"/>
                <input type="hidden" name="apagar" value="sim"/>
                <div class="LinhaEntrada">
                    <div class="Buttons">
                        <input type="button" value="< Voltar" onclick="javascript:history.go(-1);"/>
                        <input type="submit" value="Gravar >" name="submit" />
                    </div>
                </div>
            </form>
            <%

                //Informações OAI-PMH
                } else if (tipo.equalsIgnoreCase("OAI-PMH")) {
                String sql = "SELECT i.url_or_ip, r.nome FROM info_repositorios i, repositorios r WHERE r.id=i.id_repositorio AND i.id_repositorio=" + id;

                ResultSet res = stm.executeQuery(sql);
                res.next();

            %>
            <script type="text/javascript">
                var myForm = new Validate();
                myForm.addRules({id:'url',option:'urlcomip',error:'* Deve ser informada uma url <b>v&aacute;lida</b> que responda com protocolo OAI-PMH! Come&ccedil;ando por http://'});
            </script>

            <div class="subTitulo-center">&nbsp;Editando reposit&oacute;rio <%=res.getString("nome")%></div>
            <form name="editaGeral" action="editarRepositorio.jsp" method="post" onsubmit="return myForm.Apply('MensagemErro')">
                <div class="TextoDivAlerta" id="MensagemErro"><!--Aqui o script colocara a mensagem de erro, se ocorrer--></div>

                <div class="subtitulo">Sincroniza&ccedil;&atilde;o dos metadados</div>
                <div class="EspacoAntes">&nbsp;</div>
                <div class="LinhaEntrada">
                    <div class="Label">
                        URL que responde OAI-PMH:
                    </div>
                    <div class="Value">
                        <input name="url" value="<%=res.getString("url_or_ip")%>" id="url" type="text" maxlength="455" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>
                </div>
                <input type="hidden" name="id" value="<%=id%>"/>
                <input type="hidden" name="campo" value="<%=tipo%>"/>
                <input type="hidden" name="apagar" value="sim"/>
                <div class="LinhaEntrada">
                    <div class="Buttons">
                        <input type="button" value="< Voltar" onclick="javascript:history.go(-1);"/>
                        <input type="submit" value="Gravar >" name="submit" />
                    </div>
                </div>
            </form>
            <%
                }
            } //fim do if que testa se eh para aparecer o formulario ou se eh para editar
            else { //se o formulario ja foi preenchido entra no else
                out.println("<script language=\"JavaScript\" type=\"text/javascript\">" +
                        "document.body.style.cursor=\"default\";" +
                        "</script>");
                String id = request.getParameter("id"); //armazena o valor do id passado pelo form
                String campo = request.getParameter("campo"); //armazena o valor do campo passado pelo form. Geral ou config ou Ldaplocal...

                if (campo.equalsIgnoreCase("geral")) { //se o campo a ser editador for o geral entra no if

                    String nome = request.getParameter("nomeRep").trim();
                    String descricao = request.getParameter("descricao").trim();
                    String padrao_metadados = request.getParameter("padrao_metadados").trim();


                    //out.println("<p>ome: " + nome + " descricao " + descricao + " padrao " + padrao_metadados + "</p>");
                    int result = 0, result2 = 0;

                    String sql1 = "UPDATE repositorios set nome='" + nome + "', descricao='" + descricao + "' where id=" + id; //sql que possui o update

                    result = stm.executeUpdate(sql1); //executa a consluta

                    if (result > 0) { //se o insert funcionar entra no if
                        String sql2 = "UPDATE info_repositorios set padrao_metadados=" + padrao_metadados + " where id_repositorio=" + id;
                        result2 = stm.executeUpdate(sql2);
                        if (result2 > 0) {
                            out.print("<script type='text/javascript'>alert('Os dados foram atualizados com sucesso!'); " +
                                    "opener.location.href=opener.location.href; " +
                                    "window.location=\"exibeRepositorios.jsp?id=" + id + "\";</script>");
                        }
                    }


                } else if (campo.equalsIgnoreCase("config")) { //se o campo a ser editador for o config entra no if

                    String periodicidade = request.getParameter("periodicidade").trim();
                    //String nomeNaFederacao = request.getParameter("nomeNaFederacao").trim();
                    //para atualizar o nome na federacao. Tem que fazer um script que altere o arquivo de configuracao do ldap
                    String tipoSinc = request.getParameter("sincronizacao").trim();
                    String tipoSincOriginal = request.getParameter("tipoSincOriginal");


                    if (periodicidade.isEmpty() || tipoSinc.isEmpty()) {
                        out.print("<script type='text/javascript'>alert('O campo url deve estar devidamente preenchido!');</script>" +
                                "<script type='text/javascript'>history.back(-1);</script>");
                        out.close();
                    }

                    int result = 0;


                    String sql = "UPDATE info_repositorios set periodicidade_horas=" + periodicidade + ", tipo_sincronizacao='" + tipoSinc + "' where id_repositorio=" + id;
                    result = stm.executeUpdate(sql);
                    if (result > 0) {

                        out.print("<script type='text/javascript'>alert('Os dados foram atualizados com sucesso!');</script>");

                        if (!tipoSinc.equalsIgnoreCase(tipoSincOriginal)) { //se foi alterado o tipo de sincronizacao redirecionar para o tipo de sincronizacao

                            out.print("<script type='text/javascript'>window.location=\"exibeRepositorios.jsp?id=" + id + "&campo=" + tipoSinc + "\";</script>");
                        } else {
                            out.print("<script type='text/javascript'>window.location=\"exibeRepositorios.jsp?id=" + id + "\";</script>");
                        }
                    }



                } else if (campo.equalsIgnoreCase("ldaplocal")) { //se o campo a ser editador for o ldaplocal entra no if
                  
                  
                    int idLdapDestino = Integer.parseInt(request.getParameter("id_federacao").trim());

                    if (request.getParameter("id_federacao").trim().isEmpty()) {
                        out.print("<script type='text/javascript'>alert('Deve ser selecionada federacao!');</script>" +
                                "<script type='text/javascript'>history.back(-1);</script>");
                        out.close();
                    }
                    
                    boolean resultado = false;
                    Editar edit = new Editar();
                    resultado = edit.trocarBaseLdap(Integer.parseInt(id), idLdapDestino);
                    
                    if (resultado) {
                        out.print("<script type='text/javascript'>alert('Atualizado com sucesso!'); " +
                                "window.location=\"exibeRepositorios.jsp?id=" + id + "\";</script>");
                    }

                    /*
                    String sql = "UPDATE info_repositorios SET ldap_destino="+idLdapDestino+" WHERE id_repositorio=" + id;

                    int result = 0;
                    result = stm.executeUpdate(sql);
                    if (result > 0) {
                    out.print("<script type='text/javascript'>alert('A base LDAP foi atualizada com sucesso!'); " +
                    "window.location=\"exibeRepositorios.jsp?id=" + id + "\";</script>");
                    }
                     */

                } else if (campo.equalsIgnoreCase("ldaporigem")) { //se o campo a ser editador for o ldaporigem entra no if

                    String portaOrigem = request.getParameter("portaOrigem").trim();
                    String dnOrigem = request.getParameter("dnOrigem").trim();
                    String login_ldap_origem = request.getParameter("loginOrigem").trim();
                    String senhaLdapOrigem = request.getParameter("senhaOrigem").trim();
                    String confirmaSenhaOrigem = request.getParameter("confSenhaOrigem").trim();
                    String ipOrigem = request.getParameter("ipOrigem").trim();

                    //testa se os campos foram preenchidos
                    if (portaOrigem.isEmpty() || dnOrigem.isEmpty() || login_ldap_origem.isEmpty() || senhaLdapOrigem.isEmpty() || confirmaSenhaOrigem.isEmpty()) {
                        out.print("<script type='text/javascript'>alert('Todos os campos devem ser preenchidos!');</script>" +
                                "<script type='text/javascript'>history.back(-1);</script>");
                        out.close();
                    }
                    //testa se a senha do origem confere com a repeticao
                    if (!senhaLdapOrigem.equals(confirmaSenhaOrigem)) {
                        out.print("<script type='text/javascript'>alert('As senhas não conferem. Digite novamente!');</script>" +
                                "<script type='text/javascript'>history.back(-1);</script>");
                        out.close();
                    }

                    String sql = "UPDATE dadosldap set login_ldap_origem='" + login_ldap_origem + "', senha_ldap_origem='" + senhaLdapOrigem + "', porta_ldap_origem=" + portaOrigem + ", dn_origem='" + dnOrigem + "' where id_repositorio=" + id;
                    int result = 0, result2 = 0;
                    result = stm.executeUpdate(sql);
                    if (result > 0) {
                        String sql2 = "Update info_repositorios set url_or_ip='" + ipOrigem + "' where id_repositorio=" + id;
                        result2 = stm.executeUpdate(sql2);
                        if (result2 > 0) {
                            out.print("<script type='text/javascript'>alert('Os dados foram atualizados com sucesso!'); " +
                                    "window.location=\"exibeRepositorios.jsp?id=" + id + "\";</script>");
                        }
                    }



                } else if (campo.equalsIgnoreCase("OAI-PMH")) { //se o campo a ser editador for o OAI-PMH entra no if
                    int result = 0;

                    String url = request.getParameter("url").trim();
                    if (url.isEmpty()) {
                        out.print("<script type='text/javascript'>alert('O campo url deve estar devidamente preenchido!');</script>" +
                                "<script type='text/javascript'>history.back(-1);</script>");
                        out.close();
                    }
                    String sql = "Update info_repositorios set url_or_ip='" + url + "' where id_repositorio=" + id;
                    result = stm.executeUpdate(sql);
                    if (result > 0) {
                        out.print("<script type='text/javascript'>alert('Os dados foram atualizados com sucesso!'); " +
                                "window.location=\"exibeRepositorios.jsp?id=" + id + "\";</script>");
                    }
                }


            }
            con.close(); //fechar conexao
            %>


        </div>
        <%@include file="googleAnalytics"%>
    </body>
</html>
