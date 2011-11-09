<%-- 
    Document   : respostaAjaxPadrao
    Created on : 01/10/2010, 13:51:16
    Author     : Marcos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../conexaoBD.jsp"%>
<%
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");


            String tipo = request.getParameter("tipo");
            String idDivResultado = request.getParameter("idResultado");



            if (tipo.equalsIgnoreCase("exibeText")) {
                String valorAnterior = request.getParameter("valorAnterior");
                String idPadrao = request.getParameter("id");
                String atributo = request.getParameter("atributo").trim();

                out.println("<input type=\"text\" id=\"geral\" name=\"geral\" value='" + valorAnterior + "' onFocus=\"this.className='inputSelecionado'\" onBlur=\"this.className=''\" />");
                out.println("<input type=\"button\" name=\"salvar\" class=\"BotaoMapeamento\" id=\"salvar\"  value=\"Salvar\" onclick=\"salvarBase('" + idDivResultado + "' , '" + idPadrao + "', 'geral', '" + atributo + "')\"/>");
                out.println("<input type=\"button\" name=\"cancelar\" class=\"BotaoMapeamento\" id=\"cancelar\"  value=\"Cancelar\" onclick=\"cancelar('" + idDivResultado + "', '" + valorAnterior + "')\"/>");


            } else if (tipo.equalsIgnoreCase("exibeTextAtributo")) {
                String valorAnterior = request.getParameter("valorAnterior");
                String idAtributo = request.getParameter("id");

                out.println("<input type=\"text\" id=\"geral\" name=\"geral\" value='" + valorAnterior + "' onFocus=\"this.className='inputSelecionado'\" onBlur=\"this.className=''\" />");
                out.println("<input type=\"button\" name=\"salvar\" class=\"BotaoMapeamento\" id=\"salvar\"  value=\"Salvar\" onclick=\"salvarAtributo('" + idDivResultado + "' , 'geral', '" + idAtributo + "')\"/>");
                out.println("<input type=\"button\" name=\"cancelar\" class=\"BotaoMapeamento\" id=\"cancelar\"  value=\"Cancelar\" onclick=\"cancelar('" + idDivResultado + "', '" + valorAnterior + "')\"/>");

            } else if (tipo.equalsIgnoreCase("updatePadrao")) {
                String novoValor = request.getParameter("novo");
                String idPadrao = request.getParameter("id");
                String atributo = request.getParameter("atributo").trim();

                String sqlUpdate = "UPDATE padraometadados SET " + atributo + "='" + novoValor + "' where id=" + idPadrao + ";";

                stm.executeUpdate(sqlUpdate); //submete o que esta na variavel sqlUpdate

                out.println(consultaPadrao(idPadrao, atributo)); //retorna o valor que ficou salvo na base de dados

            } else if (tipo.equalsIgnoreCase("updateAtributo")) {
                String novoValor = request.getParameter("novo");
                String idAtributo = request.getParameter("id");

                String sqlUpdate = "UPDATE atributos SET atributo='" + novoValor + "' where id=" + idAtributo + ";";

                stm.executeUpdate(sqlUpdate); //submete o que esta na variavel sqlUpdate

                out.println(consultaAtributos(idAtributo)); //retorna o valor que ficou salvo na base de dados

            } else if (tipo.equalsIgnoreCase("novoAtributo")) {
                String novoValor = request.getParameter("novo");
                String idPadrao = request.getParameter("id");

                String sqlComp = "INSERT INTO atributos (id_padrao, atributo) VALUES (?, ?);";
                PreparedStatement stmt = con.prepareStatement(sqlComp, Statement.RETURN_GENERATED_KEYS);
                stmt.setInt(1, Integer.parseInt(idPadrao));
                stmt.setString(2, novoValor);
                stmt.execute();

                ResultSet rs = stmt.getGeneratedKeys();
                rs.next();
                int idGerado = rs.getInt(1);

                out.println(consultaAtributos(idGerado)); //retorna o valor que ficou salvo na base de dados

            } else if (tipo.equalsIgnoreCase("deletaAtributo")) {
                String idAtributo = "";
                try {
                    idAtributo = request.getParameter("id");

                    if (idAtributo != null) {

                        String sql = "DELETE FROM atributos WHERE id=" + idAtributo + ";";
                        PreparedStatement stmt = con.prepareStatement(sql);
                        int result = stmt.executeUpdate();
                        stmt.close();
                        out.println("Exclu&iacute;do com sucesso.");

                    } else {
                        out.print("<p class='textoErro'>Erro! O id do atributo informado consta como null ou est&aacute; incorreto.</p>");
                    }

                } catch (Exception e) {
                    out.print("<p class='textoErro'>Faltou informar o id do mapeamento a ser excluido</p>");
                    System.err.println("FEB: ERRO no respostaAjaxPadrao chamado pelo ajax. Mensagem: "+ e.getMessage());
                }

            }

%>

<%! public String consultaPadrao(String id, String atributo) throws SQLException {
        Conectar conect = new Conectar();
        //conecta na base
        Connection con = conect.conectaBD();
        Statement stm = con.createStatement();
        String sql = "SELECT " + atributo + " FROM padraometadados WHERE id=" + id + ";";
        ResultSet rs = stm.executeQuery(sql);
        rs.next();
        return rs.getString(1);
    }

    public String consultaAtributos(int id) throws SQLException {
        String idS = Integer.toString(id);
        return consultaAtributos(idS);
    }

    public String consultaAtributos(String id) throws SQLException {
        Conectar conect = new Conectar();
        //conecta na base
        Connection con = conect.conectaBD();
        Statement stm = con.createStatement();
        String sql = "SELECT atributo FROM atributos WHERE id=" + id + ";";
        ResultSet rs = stm.executeQuery(sql);
        rs.next();
        return rs.getString(1);
    }
%>