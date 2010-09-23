<%-- 
    Document   : grava_usuario.jsp
    Created on : 09/09/2010, 13:01:46
    Author     : Marcos

OBS: O que tiver de saida (impressão na tela) aqui, será o retorno para o Ajax.
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<% // Importação de conexão com a base de dados %>
<%@include file="../conexaoBD.jsp"%>


<% // Variáveis da conexão com o banco de dados %>


<%




//Parâmetros recuperados do Ajax


            String tipo = request.getParameter("tipo");
            String valorAnterior = request.getParameter("valorAnterior");
            String idDivResultado = request.getParameter("idResultado");
            
            int idMapOuPadrao = 0;
            String idMapeamento = request.getParameter("idMapOuIdPadrao");
            if (!idMapeamento.isEmpty()) {
                idMapOuPadrao = Integer.valueOf(idMapeamento);
            }

            if (tipo.equalsIgnoreCase("comboBox")) {
                out.println("<select name='atributos' id='atrb"+idDivResultado+"' onFocus=\"this.className='inputSelecionado'\" onBlur=\"this.className=''\">");

                //consultar o mysql todos os atributos
                String sqlPadrao = "SELECT a.id, a.atributo " +
                        "FROM atributos a " +
                        "WHERE a.idPadrao = (SELECT p.id FROM padraometadados p, mapeamentos m, atributos a WHERE m.destino_id=p.id AND m.destino_id=a.id AND a.idPadrao=p.id GROUP BY p.id)" +
                        "ORDER BY a.atributo;";

                ResultSet res = stm.executeQuery(sqlPadrao);
                //percorre os resultados retornados pela consulta sql
                while (res.next()) {
                    out.println("<option value=" + res.getString("id") + ">" + res.getString("atributo"));
                }
                out.println("</select>");

                if(idMapOuPadrao >0){
                out.println("<input type=\"button\" name=\"salvar\" class=\"BotaoMapeamento\" id=\"salvar\"  value=\"Salvar\" onclick=\"salvarBase('" + idDivResultado + "', '" + idMapOuPadrao + "', 'atrb"+idDivResultado+"')\"/>");
                out.println("<input type=\"button\" name=\"cancelar\" class=\"BotaoMapeamento\" id=\"cancelar\"  value=\"Cancelar\" onclick=\"cancelar('" + idDivResultado + "', '" + valorAnterior + "')\"/>");
                }
            }

            else if (tipo.equalsIgnoreCase("cancelar")) { //retorna o atributo de destino que esta salvo na base de dados
                
                out.println(valorAnterior);
            }

            else if (tipo.equalsIgnoreCase("salvar")) { //se for para salvar na base..
                String novoValor = request.getParameter("novo");

                if (idMapOuPadrao > 0) { //se for pra salvar algum mapeamento



                    String sqlUpdate = "UPDATE mapeamentos SET destino_id=" + novoValor + " where id=" + idMapOuPadrao + ";";

                    stm.executeUpdate(sqlUpdate); //realiza no mysql oque esta na variavel sqlUpdate

                    out.println(consultaAtributoBase(idMapOuPadrao)); //retorna o valor que ficou salvo na base de dados
                }
                else { //se for pra salvar dados gerais
                    String id = request.getParameter("idTipMap");
                                    
                    if (idDivResultado.equalsIgnoreCase("descricao")) {

                        String sqlUpdate = "UPDATE tipomapeamento SET descricao='" + novoValor + "' where id=" + id + ";";

                        stm.executeUpdate(sqlUpdate); //realiza no mysql oque esta na variavel sqlUpdate

                        out.println(consultaTipoMapeamento("descricao", id)); //retorna o valor que ficou salvo na base de dados

                    } else if (idDivResultado.equalsIgnoreCase("tipoMap")) {
                        String sqlUpdate = "UPDATE tipomapeamento SET nome='" + novoValor + "' where id=" + id + ";";
                        stm.executeUpdate(sqlUpdate); //realiza no mysql oque esta na variavel sqlUpdate
                        
                        out.println(consultaTipoMapeamento("nome", id)); //retorna o valor que ficou salvo na base de dados

                    }
                }
            }
            else if (tipo.equalsIgnoreCase("text")) {

                String idTipoMapeamento = request.getParameter("idTipMap");
                out.println("<input type=\"text\" id=\"geral\" name=\"geral\" onFocus=\"this.className='inputSelecionado'\" onBlur=\"this.className=''\" />");
                out.println("<input type=\"button\" name=\"salvar\" class=\"BotaoMapeamento\" id=\"salvar\"  value=\"Salvar\" onclick=\"salvarBase('" + idDivResultado + "', '0', 'geral', '"+idTipoMapeamento+"')\"/>");
                out.println("<input type=\"button\" name=\"cancelar\" class=\"BotaoMapeamento\" id=\"cancelar\"  value=\"Cancelar\" onclick=\"cancelar('" + idDivResultado + "', '" + valorAnterior + "')\"/>");
                //inserir um text para salvar o valor depois




            }
            else if (tipo.equalsIgnoreCase("textComp")) {

                String idTipoMapeamento = request.getParameter("idTipMap");
                out.println("<input type=\"text\" id=\"geral\" name=\"geral\" onFocus=\"this.className='inputSelecionado'\" onBlur=\"this.className=''\" />");
                
                out.println("<input type=\"button\" name=\"cancelar\" class=\"BotaoMapeamento\" id=\"cancelar\"  value=\"Cancelar\" onclick=\"cancelarAddComposto('" + idDivResultado + "', '" + valorAnterior + "')\"/>");
                //inserir um text para salvar o valor depois




            }
            else if (tipo.equalsIgnoreCase("comboOrigem")) {
                out.println("<select name='atributosOrigem' id='atribOrigem' onFocus=\"this.className='inputSelecionado'\" onBlur=\"this.className=''\">");

                //consultar o mysql todos os atributos
                String sqlPadrao = "SELECT a.id, a.atributo " +
                        "FROM atributos a " +
                        "WHERE a.idPadrao =" +idMapOuPadrao+
                        " ORDER BY a.atributo;";

                ResultSet res = stm.executeQuery(sqlPadrao);
                //percorre os resultados retornados pela consulta sql
                while (res.next()) {
                    out.println("<option value=" + res.getString("id") + ">" + res.getString("atributo"));
                }
                out.println("</select>");

            }
            else //Este comando devolverá "Dados inseridos com Sucesso para" o Ajax.
            {
                out.println("Faltando informação para funcionar o Ajax");
            }

%>

<%! public String consultaAtributoBase(int id) throws SQLException{
    Conectar conect = new Conectar();
            //chama metodo que conecta no mysql
            Connection con = conect.conectaBD();
    Statement stm = con.createStatement();
    String sql = "SELECT a.atributo FROM mapeamentos m, atributos a WHERE m.destino_id=a.id AND m.id=" + id + ";";
                ResultSet rs = stm.executeQuery(sql);
                rs.next();
                return rs.getString("atributo");

}

public String consultaTipoMapeamento(String atributo, String id) throws SQLException{
    
    Conectar conect = new Conectar();
            //chama metodo que conecta no mysql
            Connection con = conect.conectaBD();
    Statement stm = con.createStatement();
    String sql = "";
    if(atributo.equalsIgnoreCase("descricao")){
        sql = "SELECT t.descricao FROM tipomapeamento t where id="+id+";";
    }else if(atributo.equalsIgnoreCase("nome")){
        sql = "SELECT t.nome FROM tipomapeamento t where id="+id+";";
    }
                ResultSet rs = stm.executeQuery(sql);
                rs.next();
                return rs.getString(1);
    
}
%>
