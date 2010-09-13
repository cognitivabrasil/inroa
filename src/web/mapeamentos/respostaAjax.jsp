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
            int idPadrao = 0;
            String idPad = request.getParameter("idPadraoDestino");
            if (!idPad.isEmpty()) {
                idPadrao = Integer.valueOf(idPad);
            }
            
            int idMap = 0;
            String idMapeamento = request.getParameter("idMap");
            if (!idMapeamento.isEmpty()) {
                idMap = Integer.valueOf(idMapeamento);
            }

            if (tipo.equalsIgnoreCase("comboBox") && idPadrao > 0) {
                out.println("<select name='atributos' id='atributos' onFocus=\"this.className='inputSelecionado'\" onBlur=\"this.className=''\">");

                //consultar o mysql todos os atributos
                String sqlPadrao = "SELECT a.id, a.atributo FROM atributos a WHERE idPadrao=" + idPadrao + ";";
                ResultSet res = stm.executeQuery(sqlPadrao);
                //percorre os resultados retornados pela consulta sql
                while (res.next()) {
                    out.println("<option value=" + res.getString("id") + ">" + res.getString("atributo"));
                }
                out.println("</select>");

                String idResultado = request.getParameter("idResultado");
                out.println("<input type=\"button\" name=\"salvar\" id=\"salvar\"  value=\"Salvar\" onclick=\"salvarBase('" + idResultado + "', '" + idMap + "')\"/>");
                out.println("<input type=\"button\" name=\"cancelar\" id=\"cancelar\"  value=\"Cancelar\" onclick=\"cancelar('" + idResultado + "', '" + idMap + "', '" + idPadrao + "')\"/>");
            }


            else if (tipo.equalsIgnoreCase("cancelar") && idMap > 0) { //retorna o atributo de destino que esta salvo na base de dados
                out.println(consultaAtributoBase(idMap));
            }


            else if (tipo.equalsIgnoreCase("salvar") && idMap > 0) { //se for para salvar na base..
                String novoValor = request.getParameter("novo");
                int result = 0;

                String sqlUpdate = "UPDATE mapeamentos SET destino_id="+novoValor+" where id="+idMap+";";

                result = stm.executeUpdate(sqlUpdate); //realiza no mysql oque esta na variavel sqlUpdate

               out.println(consultaAtributoBase(idMap)); //retorna o valor que ficou salvo na base de dados

            } else //Este comando devolverá "Dados inseridos com Sucesso para" o Ajax.
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
%>