<%-- 
    Document   : gravaRepositorioBase
    Created on : 05/08/2009, 11:41:37
    Author     : Marcos Nunes
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="conexaoBD.jsp"%>
<%@page import="operacoesPostgre.*" %>
<%
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="StyleSheet" href="css/padrao.css" type="text/css">
        <script type="text/javascript" src="./scripts/funcoes.js"></script>
        <link href="imagens/favicon.ico" rel="shortcut icon" type="image/x-icon" />
        <title>FEB - Cadastrar novo repositório</title>
    </head>
    <body>

        <%
   
        Long key = new Long(0);
        boolean testaParametros = false;
        String url = "";
        String nome = "";
        String descricao = "";
        String padrao_metadados = "";
        String periodicidadeDias = "";
        String tipo_mapeamento = "";
        String metadataPrefix = "";
        String nameSpace = "";
        String set = "";

        try {
            //armazena em variaveis os dados preenchidos no formulario
            url = request.getParameter("url").trim();
            nome = request.getParameter("nomeRep").trim().toUpperCase();
            descricao = request.getParameter("descricao").trim();
            padrao_metadados = request.getParameter("padrao_metadados").trim();
            periodicidadeDias = request.getParameter("periodicidade").trim();
            tipo_mapeamento = request.getParameter("tipo_map").trim();
            metadataPrefix = request.getParameter("metPrefix").trim();
            nameSpace = request.getParameter("namespace").trim();
            set = request.getParameter("set");

            if (url.isEmpty() || nome.isEmpty() || descricao.isEmpty() || padrao_metadados.isEmpty() || periodicidadeDias.isEmpty() || tipo_mapeamento.isEmpty() || metadataPrefix.isEmpty() || nameSpace.isEmpty()) {
                out.print("<script type='text/javascript'>alert('Todos os campos devem ser preenchidos!');</script>" +
                        "<script type='text/javascript'>history.back(-1);</script>");
            } else {
                testaParametros = true;
            }

        } catch (NullPointerException n) {
            out.print("<script type='text/javascript'>alert('Todos os campos devem ser preenchidos!');</script>" +
                    "<script type='text/javascript'>history.back(-1);</script>");
        }


        if (testaParametros) {

            boolean gravadoSql1 = false;
            boolean gravadoSql2 = false;

            String nomeNaFederacao = nome.toLowerCase().replaceAll(" ", "_");


            out.print("<p>Gravando dados na base...</p>");


            //teste se ja existe um repositorio cadastrado com o mesmo nome
            ResultSet testeExiste = stm.executeQuery("SELECT r.nome FROM repositorios r WHERE r.nome='" + nome + "';");

            if (testeExiste.next()) {//se existir imprime o alerta
                out.print("<script type='text/javascript'>alert('Já existe um repositório cadastrado com esse nome (" + nome + ")!');</script>");
                out.print("<script type='text/javascript'>history.go(-1);</script>");
                //se o navegador tiver o javascrit desativado ira apresentar a mensagem abaixo
                out.print("<p class='textoErro'>Já existe um repositório cadastrado com esse nome (" + nome + ")!</p>");
            //fim teste
            } else {

                try {
                    int result = 0, result2 = 0;

                    String sql1 = "INSERT INTO repositorios(nome, descricao) " +
                            "VALUES ('" + nome + "', '" + descricao + "');"; //sql que possui um insert com os dados da tabela repositorio


                    result = stm.executeUpdate(sql1, Statement.RETURN_GENERATED_KEYS); //realiza na base o insert que esta na variavel sql1, pedindo para retornar a key gerada automaticamente

                    if (result > 0) { //se o insert funcionar entra no if
                        gravadoSql1 = true;
                        ResultSet rs = stm.getGeneratedKeys();
                        rs.next();
                        key = rs.getLong(1);
                        int periodicidadeHoras = Integer.parseInt(periodicidadeDias)*24; //mudar dias para horas
                        String sql2 = "INSERT INTO info_repositorios (id_repositorio, data_ultima_atualizacao, periodicidade_horas, nome_na_federacao, url_or_ip, padrao_metadados, tipo_mapeamento_id, metadata_prefix, name_space, set) " +
                                "VALUES (" + key + ", '0001-01-01 00:00:00', " + periodicidadeHoras + ", '" + nomeNaFederacao + "', '" + url + "', '" + padrao_metadados + "', "+tipo_mapeamento+", '"+metadataPrefix+"','"+nameSpace+"','"+set+"');";


                        result2 = stm.executeUpdate(sql2); //executa o que tem na variavel slq2
                        //se o insert funcionou seta pra true o boolean
                        if (result2 > 0) { //se todos os insert foram realizados
                            gravadoSql2 = true;
                            out.print("<script type='text/javascript'>alert('Informações do repositório " + nome.toUpperCase() + " gravadas com sucesso!');</script></p>");
                            out.print("<script type='text/javascript'>fechaRecarrega();</script>");
                        } else {
                            out.print("<script type='text/javascript'>alert('Erro ao inserir as informações na base de bados!');</script>");
                            stm.executeUpdate("delete from repositorios where id=" + key);
                            out.print("<script type='text/javascript'>history.go(-1);</script>");
                            out.print("<BR><p class='textoErro'>Erro ao inserir as informa&ccedil;&otilde;es na base de bados!");
                        }
                    }

                } catch (SQLFeatureNotSupportedException e) {
                    out.print("<p class='textoErro'>Erro:" + e);

                    if (gravadoSql1) { //se o primeiro insert foi realizado, aqui ele sera apagado
                        if (key > 0) {
                            stm.executeUpdate("delete from repositorios where id=" + key);
                        }
                    }
                    if (gravadoSql2) //se o segundo insert foi realizado, aqui ele sera apagado
                    {
                        stm.executeUpdate("delete from repositorios where id=" + key);
                    }

                } catch (SQLException k) {
                    System.out.println("ERRO SQL: " + k);
                    out.print("<p class='textoErro'>Erro no sql: " + k);
                    if (gravadoSql1) { //se o primeiro insert foi realizado, aqui ele sera apagado
                        if (key > 0) {
                            stm.executeUpdate("delete from repositorios where id=" + key);
                        }
                    }
                    if (gravadoSql2) //se o segundo insert foi realizado, aqui ele sera apagado
                    {
                        stm.executeUpdate("delete from repositorios where id=" + key);
                    }


                    out.print("<script type='text/javascript'>alert('Erro ao inserir as informações na base de bados! \\n Tente novamente mais tarde!');</script></p>" +
                            "<script type='text/javascript'>window.close();</script>");
                }
            }
        }
    
        %>

        <%@include file="googleAnalytics"%>
    
<%
con.close (); //fechar conexao
%>
</body>
</html>