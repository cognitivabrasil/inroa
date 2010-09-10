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
            String destino = request.getParameter("destino");
            String tipo = request.getParameter("tipo");
            String idPadrao = request.getParameter("padrao");

            if (tipo.equalsIgnoreCase("comboBox") && !idPadrao.isEmpty()) {
                out.println("<select name='atributos' id='atributos' onFocus=\"this.className='inputSelecionado'\" onBlur=\"this.className=''\">");

                //consultar o mysql todos os atributos
                String sqlPadrao = "SELECT a.id, a.atributo FROM atributos a WHERE idPadrao=" + idPadrao + ";";
                ResultSet res = stm.executeQuery(sqlPadrao);
                //percorre os resultados retornados pela consulta sql
                while(res.next()){
                    out.println("<option value=" + res.getString("id") + ">" + res.getString("atributo"));
                }
                out.println("</select>");
                
                String idResultado = request.getParameter("idResultado");
                out.println("<input type=\"button\" name=\"salvar\" id=\"salvar\"  value=\"Salvar\" onclick=\"salvarBase('"+idResultado+"', '"+destino+"')\"/>");
                out.println("<input type=\"button\" name=\"cancelar\" id=\"cancelar\"  value=\"Cancelar\" onclick=\"cancelar('"+idResultado+"', '"+destino+"')\"/>");
                
            }

            else if(tipo.equalsIgnoreCase("cancelar") && !destino.isEmpty()){
                out.println(destino);
            }

            else if(tipo.equalsIgnoreCase("salvar")){ //se for para salvar na base..
                String novoValor = request.getParameter("novo");
                out.println(novoValor);

            } else //Este comando devolverá "Dados inseridos com Sucesso para" o Ajax.
            {
                out.println("Faltando informação para funcionar o Ajax");
            }

%>