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
<%@page import="operacoesPostgre.Editar" %>
<%
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>FEB - Ferramenta Administrativa</title>
        <link rel="StyleSheet" href="css/padrao.css" type="text/css"/>
        <script language="JavaScript" type="text/javascript" src="scripts/funcoes.js"></script>
        <script language="JavaScript" type="text/javascript" src="./mapeamentos/funcoesMapeamento.js"> //funcoes javascript que chamam o ajax</script>
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


                            String id = "";
                            String tipo = "";
                            boolean varParametro = false;
                            try {
                                id = request.getParameter("id");
                                tipo = request.getParameter("campo");
                                if (id.isEmpty() || tipo.isEmpty()) {
                                    varParametro = false;
                                } else {
                                    varParametro = true;
                                }

                            } catch (Exception e) {
                                varParametro = false;
                            }
                            if (varParametro) {


                                if (tipo.equalsIgnoreCase("geral")) {

                                    String sql = "SELECT r.nome, r.descricao, p.id as id_padrao, p.nome as nome_padrao, i.tipo_mapeamento_id"
                                            + " FROM repositorios r, info_repositorios i, padraometadados p"
                                            + " WHERE r.id=" + id
                                            + " AND r.id=i.id_repositorio"
                                            + " AND i.padrao_metadados=p.id;";

                                    ResultSet res = stm.executeQuery(sql);

                                    res.next();
                                    String nomePadrao = res.getString("nome_padrao");
                                    int idPadrao = res.getInt("id_padrao");
                                    int idTipoMap = res.getInt("tipo_mapeamento_id");


            %>
            <script type="text/javascript">
                var myForm = new Validate();
                myForm.addRules({id:'nameRep',option:'required',error:'* Voc&ecirc; deve informar o nome do reposit&oacute;rio!'});
                myForm.addRules({id:'descricao',option:'required',error:'* Deve ser informarmada uma descri&ccedil;&atilde;o!'});
                myForm.addRules({id:'padraoMet',option:'required',error:'* Deve ser informado o padr&atilde;o dos metadados do repositorio!'});
                myForm.addRules({id:'rdMap',option:'isNotEmpty',error:'* Deve ser selecionado o tipo de mapeamento!'});
            </script>

            <div class="subTitulo-center">&nbsp;Editanto reposit&oacute;rio <%=res.getString("nome")%></div>
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
                                                                        out.println("<option value=" + resPadrao.getString("id") + " selected onclick=\"selecionaMapeamento('resultado', 'padraoMet', 'edita')\">" + resPadrao.getString("nome").toUpperCase());
                                                                    } else {
                                                                        out.println("<option value=" + resPadrao.getString("id") + " onclick=\"selecionaMapeamento('resultado', 'padraoMet', 'edita')\">" + resPadrao.getString("nome").toUpperCase());
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
                    <div id="resultado">
                        <%
                                                            String sqlTM = "SELECT t.nome as tipo_map, t.descricao, t.id as id_map"
                                                                    + "  FROM mapeamentos m, tipomapeamento t"
                                                                    + "  WHERE m.tipo_mapeamento_id=t.id AND m.padraometadados_id=" + idPadrao
                                                                    + "  GROUP BY t.id, t.nome, t.descricao;";
                                                            ResultSet rsTM = stm.executeQuery(sqlTM);
                                                            while (rsTM.next()) {
                                                                int idtMap = rsTM.getInt("id_map");
                                                                if (idtMap == idTipoMap) {//se for o tipo que esta sendo usado deixa marcado o radio
                                                                    out.println("<div class=\"ValueIndex\"><input type=\"radio\" checked=true id=\"rdMap\" name=\"tipo_map\" value=" + rsTM.getString("id_map") + ">" + rsTM.getString("tipo_map") + " (" + rsTM.getString("descricao") + ")</div>");
                                                                } else {
                                                                    out.println("<div class=\"ValueIndex\"><input type=\"radio\" id=\"rdMap\" name=\"tipo_map\" value=" + rsTM.getString("id_map") + ">" + rsTM.getString("tipo_map") + " (" + rsTM.getString("descricao") + ")</div>");
                                                                }
                                                            }

                        %>
                        
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
                                            } else if (tipo.equalsIgnoreCase("OAI-PMH")) {
                                                String sql = "SELECT i.url_or_ip, r.nome, i.periodicidade_horas FROM info_repositorios i, repositorios r WHERE r.id=i.id_repositorio AND i.id_repositorio=" + id;
                                                ResultSet res = stm.executeQuery(sql);
                                                res.next();

            %>
            <script type="text/javascript">
                var myForm = new Validate();
                myForm.addRules({id:'url',option:'urlcomip',error:'* Deve ser informada uma url <b>v&aacute;lida</b> que responda com protocolo OAI-PMH! Come&ccedil;ando por http://'});
                myForm.addRules({id:'periodicidade',option:'required',error:'* Deve ser informado a periodicidade de atualiza&ccedil;&atilde;o. Em horas!'});
            </script>

            <div class="subTitulo-center">&nbsp;Editanto reposit&oacute;rio <%=res.getString("nome")%></div>
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
                <div class="LinhaEntrada">
                    <div class="Label">
                        Periodicidade de atualiza&ccedil;&atilde;o:
                    </div>
                    <div class="Value">
                        <input name="periodicidade" value="<%=res.getInt("periodicidade_horas")%>" id="periodicidade" type="text" maxlength="3" onkeypress ="return ( isNumber(event) );" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
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
                            }
                        } //fim do if que testa se eh para aparecer o formulario ou se eh para editar
                        else { //se o formulario ja foi preenchido entra no else
///////
                            out.println("<script language=\"JavaScript\" type=\"text/javascript\">"
                                    + "document.body.style.cursor=\"default\";"
                                    + "</script>");
                            String id = request.getParameter("id"); //armazena o valor do id passado pelo form
                            String campo = request.getParameter("campo"); //armazena o valor do campo passado pelo form. Geral ou config ou Ldaplocal...

                            if (campo.equalsIgnoreCase("geral")) { //se o campo a ser editador for o geral entra no if

                                String nome = "";
                                String descricao = "";
                                String padrao_metadados = "";
                                String tipo_mapeamento = "";
                                boolean nullpointer = true;
                                try {
                                    nome = request.getParameter("nomeRep").trim();
                                    descricao = request.getParameter("descricao").trim();
                                    padrao_metadados = request.getParameter("padrao_metadados").trim();
                                    tipo_mapeamento = request.getParameter("tipo_map").trim();
                                    nullpointer = false;
                                } catch (NullPointerException n) {
                                    nullpointer = true;
                                    out.print("<script type='text/javascript'>alert('Todos os campos devem ser preenchidos!');</script>"
                                            + "<script type='text/javascript'>history.back(-1);</script>");
                                }
                                

                                if(!nullpointer){
                                if ((nome.isEmpty() || descricao.isEmpty() || padrao_metadados.isEmpty() || tipo_mapeamento.isEmpty())) {
                                    out.print("<script type='text/javascript'>alert('Todos os campos devem ser preenchidos!');</script>"
                                            + "<script type='text/javascript'>history.back(-1);</script>");
                                } else {
                                    //out.println("<p>ome: " + nome + " descricao " + descricao + " padrao " + padrao_metadados + "</p>");
                                    int result = 0, result2 = 0;

                                    String sql1 = "UPDATE repositorios set nome='" + nome + "', descricao='" + descricao + "' where id=" + id; //sql que possui o update

                                    result = stm.executeUpdate(sql1); //submete o UPDATE ao banco de dados

                                    if (result > 0) { //se o insert funcionar entra no if
                                        String sql2 = "UPDATE info_repositorios set padrao_metadados=" + padrao_metadados + ", tipo_mapeamento_id=" + tipo_mapeamento + " where id_repositorio=" + id;
                                        result2 = stm.executeUpdate(sql2);
                                        if (result2 > 0) {
                                            out.print("<script type='text/javascript'>alert('Os dados foram atualizados com sucesso!'); "
                                                    + "opener.location.href=opener.location.href; "
                                                    + "window.location=\"exibeRepositorios.jsp?id=" + id + "\";</script>");
                                        }
                                    }
}
                                }
                            } else if (campo.equalsIgnoreCase("OAI-PMH")) { //se o campo a ser editador for o OAI-PMH entra no if
                                int result = 0;

                                String url = request.getParameter("url").trim();
                                String periodicidade = request.getParameter("periodicidade").trim();
                                if (url.isEmpty() || periodicidade.isEmpty()) {
                                    out.print("<script type='text/javascript'>alert('Os campos url e periodicidade devem estar devidamente preenchidos!');</script>"
                                            + "<script type='text/javascript'>history.back(-1);</script>");
                                    out.close();
                                } else {
                                    String sql = "UPDATE info_repositorios SET url_or_ip='" + url + "', periodicidade_horas=" + periodicidade + " WHERE id_repositorio=" + id;
                                    result = stm.executeUpdate(sql);
                                    if (result > 0) {
                                        out.print("<script type='text/javascript'>alert('Os dados foram atualizados com sucesso!'); "
                                                + "window.location=\"exibeRepositorios.jsp?id=" + id + "\";</script>");
                                    }
                                }
                            }


                        }
                        con.close(); //fechar conexao com o banco de dados
            %>


        </div>
        <%@include file="googleAnalytics"%>
    </body>
</html>
