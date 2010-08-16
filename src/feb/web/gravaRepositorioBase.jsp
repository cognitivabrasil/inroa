<%-- 
    Document   : gravaRepositorioBase
    Created on : 05/08/2009, 11:41:37
    Author     : Marcos Nunes
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="conexaoBD.jsp"%>
<%@page import="operacoesLdap.*" %>

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


            out.print("<p>Gravando dados na base...</p>");
            //inicializa variaveis do LDAP origem, pois se o tipo de sincronizacao for OAI, elas ficaram com null.
            String portaOrigem = null;
            String dnOrigem = null;
            String loginOrigem = null;
            String senhaOrigem = null;
            String confirmaSenhaOrigem = null;
            String url = null;
            Long key = new Long(0);



            String nome = session.getAttribute("nomeRep").toString();

            if (nome == "") {
                out.print("<script type='text/javascript'>history.back(-1);</script>");
            }

            boolean gravadoSql1 = false;
            boolean gravadoSql2 = false;
            boolean gravadoSql3 = false;
            boolean resultadoCadastraNodo=false;

            //armazena em variaveis os dados armazenados na sessao
            String descricao = session.getAttribute("descricao").toString();
            String padraoMetadados = session.getAttribute("padraoMetadados").toString();
            String periodicidade = session.getAttribute("periodicidade").toString();
            //String nomeNaFederacao = session.getAttribute("nomeNaFederacao").toString();
            String nomeNaFederacao = nome.toLowerCase().replaceAll(" ", "_");
            String tipoSinc = session.getAttribute("sincronizacao").toString();


            //armazena em variaveis os dados preenchidos no formulario
             String idLdapDestino = request.getParameter("ldapDestino").trim();

            //se for do tipo de sincronizacao. Se for LDAP entra no primeiro if
            if (tipoSinc.equalsIgnoreCase("LDAP")) {
                //armazena os dados referente ao LDAP Origem
                portaOrigem = request.getParameter("portaOrigem").trim();
                dnOrigem = request.getParameter("dnOrigem").trim();
                loginOrigem = request.getParameter("loginOrigem").trim();
                senhaOrigem = request.getParameter("senhaOrigem").trim();
                confirmaSenhaOrigem = request.getParameter("confSenhaOrigem").trim();
                url = request.getParameter("ipOrigem").trim();

                //testa se os campos foram preenchidos
                if (portaOrigem.isEmpty() || dnOrigem.isEmpty() || loginOrigem.isEmpty() || senhaOrigem.isEmpty() || confirmaSenhaOrigem.isEmpty()) {
                    out.print("<script type='text/javascript'>alert('Todos os campos devem ser preenchidos!');</script>" +
                            "<script type='text/javascript'>history.back(-1);</script>");
                    out.close();
                }
                //testa se a senha do origem confere com a repeticao
                if (!senhaOrigem.equals(confirmaSenhaOrigem)) {
                    out.print("<script type='text/javascript'>alert('As senhas informadas para o Ldap Origem não conferem. Digite novamente!');</script>" +
                            "<script type='text/javascript'>history.back(-1);</script>");
                }

                //out.println("LDAP Origem: \nporta: " + portaOrigem + "\ndnOrigem: " + dnOrigem + "\nloginOrigem: " + loginOrigem + "\n senha: " + senhaOrigem + "\nconfirma Senha: " + confirmaSenhaOrigem);


            } else if (tipoSinc.equalsIgnoreCase("OAI-PMH")) {
                url = request.getParameter("url").trim();
            }


            //testa se os campos em comum dos dois tipos de sincronizacao foram preenchidos
            if (idLdapDestino.isEmpty() || url.isEmpty()) {
                out.print("<script type='text/javascript'>alert('Todos os campos devem ser preenchidos!');</script>" +
                        "<script type='text/javascript'>history.back(-1);</script>");
            }
            //testa se a senha informada e a repeticao estao iguais


            //testar se ja existe um repositorio cadastrado com o mesmo nome
            ResultSet testeExiste = stm.executeQuery("select r.nome from repositorios r where r.nome='" + nome + "';");

            if (testeExiste.next()) {//se existir imprime o alerta
                out.print("<script type='text/javascript'>alert('Já existe um repositório cadastrado com esse nome (" + nome + ")!');</script>");
                out.print("<script type='text/javascript'>history.go(-2);</script>");
                //se o navegador tiver o javascrit desativado ira apresentar a mensagem abaixo
                out.print("<p class='textoErro'>Já existe um repositório cadastrado com esse nome (" + nome + ")!</p>");

            } else {
                //testa se ja existe um repositorio identificado na federacao pelo nomeNaFederacao informado
                ResultSet testeNomeFederacao = stm.executeQuery("select id_repositorio from info_repositorios i where i.nome_na_federacao='" + nomeNaFederacao + "';");
                if (testeNomeFederacao.next()) {
                    //se ja existir exibe um alerta e retorna para o formulario
                    out.print("<script type='text/javascript'>alert('Já existe na federacao um repositorio identificado por: " + nomeNaFederacao + "!\\nPor favor informe outro nome!');</script></p>");
                    out.print("<script type='text/javascript'>history.back(-2);</script>");
                    //se o navegador tiver o javascrit desativado ira apresentar a mensagem abaixo
                    out.print("<p class='textoErro'>J&aacute; existe um reposit&oacute;rio um repositorio identificado por: " + nomeNaFederacao + "!\\nPor favor informe outro nome!</p>");
                } else { //se nao existir segue o codigo

                    try {
                        int result = 0, result2 = 0, result3 = 0;


                        String sql1 = "insert into repositorios(nome, descricao) " +
                                "values ('" + nome + "', '" + descricao + "');"; //sql que possui um insert com os dados da tabela repositorio


                        result = stm.executeUpdate(sql1, Statement.RETURN_GENERATED_KEYS); //realiza no mysql o insert que esta na variavel sql1, pedindo para retornar a key gerada automaticamente

                        if (result > 0) { //se o insert funcionar entra no if
                            gravadoSql1 = true;
                            ResultSet rs = stm.getGeneratedKeys();
                            rs.next();
                            key = rs.getLong(1);
                            String sql2 = "INSERT INTO info_repositorios (id_repositorio, dataUltimaAtualizacao, periodicidadeHoras, nome_na_federacao, URLorIP, tipoSincronizacao, padraoMetadados, ldapDestino) " +
                                    "VALUES (" + key + ", '0001-01-01 00:00:00', " + periodicidade + ", '" + nomeNaFederacao + "', '" + url + "', '" + tipoSinc + "', '" + padraoMetadados + "', " + idLdapDestino + ");";


                            result2 = stm.executeUpdate(sql2); //executa o que tem na variavel slq2 no mysql
                            //se o insert funcionou seta pra true o boolean
                            if (result2 > 0) {
                                gravadoSql2 = true; //informa que o segundo insert foi realizado

                                //string que contem um insert com os dados da tabela dadosldap
                                String sql3 = "INSERT INTO dadosldap (id_repositorio, LoginLdapOrigem, senhaLdapOrigem, portaLdapOrigem, dnOrigem) " +
                                        "VALUES (" + key + ", '" + loginOrigem + "', '" + senhaOrigem + "', " + portaOrigem + ", '" + dnOrigem + "');";
                                result3 = stm.executeUpdate(sql3); //executa o que tem na variavel slq3 no mysql

                                if (result3 > 0) {//se o insert funcionou seta pra true o boolean
                                    gravadoSql3 = true; //informa que o terceiro insert foi realizado
////////////////////////////////////chamar metodo que insere nodo no LDAP
                                    Inserir cadastraRep = new Inserir();
                                    //consulta sql que retorna os dados necessário para criar o novo nodo
                                    ResultSet infoLdap = stm.executeQuery("SELECT ip, dn, login, senha, porta from ldaps where id="+idLdapDestino+";");
                                    infoLdap.next();
                                    resultadoCadastraNodo=cadastraRep.insereNodo(nomeNaFederacao, infoLdap.getString("ip"), infoLdap.getString("dn"), infoLdap.getString("login"), infoLdap.getString("senha"), infoLdap.getInt("porta"));
                                }
                            }
                            if (resultadoCadastraNodo) { //se todos os insert e a insersao do nodo no ldap foram realizados
                                out.print("<script type='text/javascript'>alert('Informações do repositório " + nome.toUpperCase() + " gravadas com sucesso!');</script></p>");
                                out.print("<script type='text/javascript'>fechaRecarrega();</script>");
                            } else {
                                stm.executeUpdate("delete from repositorios where id=" +key);
                                out.print("<script type='text/javascript'>alert('Erro ao inserir as informações na base de bados! Ocorreu erro ao adiocionar nodo no LDAP!');</script>");
                                out.print("<script type='text/javascript'>history.go(-1);</script>");
                                out.print("<BR><p class='textoErro'>Erro ao inserir as informa&ccedil;&otilde;es na base de bados! Ocorreu erro ao adiocionar nodo no LDAP!");
                            }

                        } else {
                            out.print("<script type='text/javascript'>alert('Erro ao inserir as informações na base de bados!');</script>");
                            out.print("<script type='text/javascript'>history.go(-1);</script>");
                            out.print("<BR><p class='textoErro'>Erro ao inserir as informa&ccedil;&otilde;es na base de bados!");
                        }

                    } catch (SQLFeatureNotSupportedException e) {
                        out.print("<p class='textoErro'>Erro:" + e);

                        if (gravadoSql1) { //se o primeiro insert foi realizado, aqui ele sera apagado
                            if (key > 0) {
                                stm.executeUpdate("delete from repositorios where id='" + key + "'");
                            } else {
                                stm.executeUpdate("delete from repositorios where nome='" + nome + "'");
                            }
                        }
                        if (gravadoSql2) //se o segundo insert foi realizado, aqui ele sera apagado
                        {
                            stm.executeUpdate("delete from repositorios where id_repositorio='" + key + "'");
                        }

                    } catch (SQLException k) {
                        out.print("<p class='textoErro'>Erro no sql: " + k);
                        if (gravadoSql1) { //se o primeiro insert foi realizado, aqui ele sera apagado
                            if (key > 0) {
                                stm.executeUpdate("delete from repositorios where id='" + key + "'");
                            } else {
                                stm.executeUpdate("delete from repositorios where nome='" + nome + "'");
                            }
                        }
                        if (gravadoSql2) //se o segundo insert foi realizado, aqui ele sera apagado
                        {
                            stm.executeUpdate("delete from repositorios where id='" + key + "'");
                        }
                        

                        out.print("<script type='text/javascript'>alert('Erro ao inserir as informações na base de bados! \\n Tente novamente mais tarde!');</script></p>" +
                                "<script type='text/javascript'>window.close();</script>");
                    }
                }
            }

        %>


        <%@include file="googleAnalytics"%>
    </body>
</html>
<%
con.close(); //fechar conexao com mysql
%>
