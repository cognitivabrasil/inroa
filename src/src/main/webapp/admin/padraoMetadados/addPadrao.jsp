<%-- 
    Document   : addPadrao
    Created on : 30/09/2010, 16:49:05
    Author     : Marcos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../testaSessaoNovaJanela.jsp"%>
<%@include file="../conexaoBD.jsp"%>
<%
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            Statement stm = con.createStatement();
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FEB - Federa&ccedil;&atilde;o de Reposit&oacute;rios Educa Brasil</title>
        <link rel="StyleSheet" href="../../css/padrao.css" type="text/css">
        <link href="../../imagens/favicon.ico" rel="shortcut icon" type="image/x-icon" />
        <script language="JavaScript" type="text/javascript" src="../../scripts/funcoes.js">
            //necessario para usar o ajax
        </script>
        <script type="text/javascript" src="../../scripts/validatejs.js"></script>
        <script type="text/javascript">
            var myForm = new Validate();
            myForm.addRules({id:'nome',option:'required',error:'* Voc&ecirc; deve informar o nome do padr&atilde;o de metadados!'});
            myForm.addRules({id:'atributos',option:'required',error:'* Voc&ecirc; deve informar os atributos do padr&atilde;o separados por ; (ponto e virgula)!'});
            myForm.addRules({id:'metPrefix',option:'required',error:'* Voc&ecirc; deve informar o MetadataPrefix. Ex.: oai_obaa. &Eacute; utilizado no OAI-PMH.'});
            myForm.addRules({id:'namespace',option:'required',error:'* Voc&ecirc; deve informar o namespace!'});
        </script>

    </head>
    <body>

        <div id="page">
            <%
            boolean formNull = true;
            try {
                String nome = request.getParameter("nome");
                if (!nome.isEmpty()) {
                    formNull = false;
                }
            } catch (Exception e) {
                formNull = true;

            }
            if (formNull) {
            %>
            <form name="adicionarPadrao" action="" method="post" onsubmit="return myForm.Apply('MensagemErro')">
                <div class="subTitulo-center">&nbsp;Edi&ccedil;&atilde;o / Visualiza&ccedil;&atilde;o de mapeamentos cadastrados</div>
                <div class="subtitulo">Informa&ccedil;&otilde;es gerais</div>
                <div class="EspacoAntes">&nbsp;</div>
                <div class="TextoDivAlerta" id="MensagemErro"><!--Aqui o script colocara a mensagem de erro, se ocorrer--></div>
                <div class="Mapeamento">
                    <div class="Legenda">
                        Nome do Padr&atilde;o:
                    </div>
                    <div class="Valor">
                        <input type="text" id="nome" name="nome" maxlength="45" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>

                    <div class="Legenda">
                        MetadataPrefix:
                    </div>
                    <div class="Valor">
                        <input type="text" id="metPrefix" name="metPrefix" maxlength="45" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>

                    <div class="Legenda">
                        NameSpace:
                    </div>
                    <div class="Valor">
                        <input type="text" id="namespace" name="namespace" maxlength="45" onFocus="this.className='inputSelecionado'" onBlur="this.className=''" />
                    </div>
                    <div class="Valor">
                        <font class="textoErro">
                            Insira todos os atributos do padr&atilde;o separados por ponto e virgula  ";"
                        </font>
                    </div>
                    
                    <div class="Legenda">
                        Atributos:
                    </div>
                    <div class="Valor" id="descricao">
                        <textarea name="atributos" id="atributos" rows="10" cols="50" onFocus="this.className='inputSelecionado'" onBlur="this.className=''"></textarea>
                    </div>

                    <div class="Buttons">

                        <input class="BOTAO" type="reset" value="Limpar" class="CancelButton" onclick="javascript:window.location.reload();"/>
                        <input class="BOTAO" id="cancelar" onclick="javascript:window.close();" value="Cancelar" type="button" class="CancelButton"/>
                        <input class="BOTAO" type="submit" value="Gravar >" name="submit" />
                    </div>

                </div>
            </form>
            <%            } else {
                String nome = request.getParameter("nome").trim();
                String atributos = request.getParameter("atributos").trim();
                String metPrefix = request.getParameter("metPrefix").trim();
                String nameSpace = request.getParameter("namespace").trim();

                

                //testa se os atributos estão preenchidos
                if (nome.isEmpty() || atributos.isEmpty() || metPrefix.isEmpty() || nameSpace.isEmpty()) {
                    out.print("<script type='text/javascript'>alert('Todos os campos devem ser preenchidos!');</script>" +
                            "<script type='text/javascript'>history.back(-1);</script>");
                }
                String sqlTeste = "SELECT nome FROM padraometadados WHERE nome='" + nome + "'";
                ResultSet testeExiste = stm.executeQuery(sqlTeste);

                if (testeExiste.next()) {
                    //se o navegador tiver o javascrit desativado ira apresentar a mensagem abaixo
                    out.print("<p class='textoErro'>J&aacute; existe um padr&atilde;o cadastrado com esse nome!</p>");
                    out.print("<script type='text/javascript'>alert('Já existe um padrao cadastrado com esse nome!');</script>");
                    out.print("<script type='text/javascript'>history.go(-1);</script>");

                } else {
                    boolean result = false;
                    
                    try {
                        result = inserePadrao(nome, metPrefix, nameSpace, atributos); //realiza no Portgre o insert do novo padrao
                    } catch (SQLException k) {
                        out.print("<p class='textoErro'>Erro ao inserir o padr&atilde;o na base de dados: </p>" + k);
                        System.err.println("ERRO NO SQL ao inserir o novo padr&atilde;o na base de dados: " + k);
                    }
                    if (result) {
                        out.println("Novo padr&atilde;o salvo com sucesso.");
                        out.print("<script type='text/javascript'>alert('Novo padrão salvo com sucesso'); " +
                                "fechaRecarrega();</script>");
                    } else {
                        out.println("N&atilde;o foi poss&iacute;vel salvar o padr&atilde;o na base de dados");
                        out.print("<script type='text/javascript'>alert('Não foi possível salvar o padrão na base de dados'); " +
                                "fechaRecarrega();</script>");
                    }
                }

            }
            %>
        </div>
    </body>
</html>


<%!
    public boolean inserePadrao(String nome, String metadaPrefix, String nameSpace, String atributos) throws SQLException {
        Conectar conecta = new Conectar();
        Connection con = conecta.conectaBD();
        int idPadrao = 0;
        boolean result = false;
        String sqlInsert = "INSERT INTO padraometadados (nome, metadata_prefix, name_space) " +
                "VALUES (?, ?, ?);";
        PreparedStatement stmt = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, nome);
        stmt.setString(2, metadaPrefix);
        stmt.setString(3, nameSpace);
        stmt.execute();

        ResultSet rs = stmt.getGeneratedKeys();
        rs.next();
        idPadrao = rs.getInt(1);

        if (idPadrao > 0) {
            String[] vetAtributos = atributos.split(";");
            String sqlAtributos = "INSERT INTO atributos (id_padrao, atributo) VALUES";

            for (int i = 0; i < vetAtributos.length; i++) {
                if (i == vetAtributos.length - 1) {
                    sqlAtributos += " (" + idPadrao + ",'" + vetAtributos[i] + "');";
                } else {
                    sqlAtributos += " (" + idPadrao + ",'" + vetAtributos[i] + "'),";
                }
            }
            PreparedStatement stmtAtrib = con.prepareStatement(sqlAtributos);
                    int resultSQL = stmtAtrib.executeUpdate();
                    stmtAtrib.close();
                    if(resultSQL >0){
                        result = true;
                    }
                    else{
                        String sqlDelete = "DELETE FROM padraometadados WHERE id="+idPadrao;
                        PreparedStatement stmtDel = con.prepareStatement(sqlDelete);
                        stmtDel.executeUpdate();
                        result = false;
                    }
        }
        return result;
    }
%>