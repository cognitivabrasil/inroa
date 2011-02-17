<%-- 
    Document   : listaMapeamentoPadraoSelecionado
    Created on : 17/02/2011, 18:11:06
    Author     : Marcos
--%>
<%@include file="conexaoBD.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<select name="tipo_mapeamento" id="mapeamento" onFocus="this.className='inputSelecionado'" onBlur="this.className=''">
                            <option value="" selected>Selecione
                                <%

                                            String sqlMap = "SELECT t.nome as tipo_map, t.descricao, t.id as id_map, m.padraometadados_id as id_padrao"
                                                    + "  FROM mapeamentos m, tipomapeamento t"
                                                    + "  WHERE m.tipo_mapeamento_id=t.id AND m.padraometadados_id=3"
                                                    + "  GROUP BY t.id, id_padrao, t.nome, t.descricao;";
                                           ResultSet res = stm.executeQuery(sqlMap);
                                            while (res.next()) {
                                                if (!res.getString("nome").equalsIgnoreCase("todos")) {
                                                    out.println("<option value=" + res.getString("id") + ">" + res.getString("nome").toUpperCase());
                                                }

                                            }
                                %>

                        </select>