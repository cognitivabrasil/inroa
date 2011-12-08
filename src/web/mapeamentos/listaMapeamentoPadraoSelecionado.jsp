<%-- 
    Document   : listaMapeamentoPadraoSelecionado
    Created on : 17/02/2011, 18:11:06
    Author     : Marcos
--%>
<%@include file="../conexaoBD.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>



<%
            Statement stm = con.createStatement();
            int idPadrao;
            String acao = "";

            try {
                idPadrao = Integer.parseInt(request.getParameter("idpadrao"));
                acao = request.getParameter("acao");
            } catch (Exception e) {
                idPadrao = 0;
            }

            if (idPadrao <= 0) {
                out.println("<div class=\"Value\">Selecione um padr&atilde;o</div> "
                        + "<input type='hidden' id='rdMap' name=\"tipo_map\" value=''>"
                        + "<input type=\"hidden\" id=\"metPrefix\"  name=\"metPrefix\" value=''> "
                        + "<input type=\"hidden\" id=\"namespace\" name=\"namespace\" value=''>");
            } else {
                String sqlMap = "SELECT t.nome as tipo_map, t.descricao, t.id as id_map"
                        + "  FROM mapeamentos m, tipomapeamento t"
                        + "  WHERE m.tipo_mapeamento_id=t.id AND m.padraometadados_id=" + idPadrao
                        + "  GROUP BY t.id, t.nome, t.descricao;";

                ResultSet res = stm.executeQuery(sqlMap);
                //out.println("<div class=\"Value\">Tipo de mapeamento - Descri&ccedil;&atilde;o</div>");
                while (res.next()) {
                    String tipoMap = res.getString("tipo_map");
                    if ((tipoMap.equalsIgnoreCase("padrão") || tipoMap.equalsIgnoreCase("padrao")) && acao.equalsIgnoreCase("cadastra")) {
                        out.println("<div class=\"ValueIndex\"><input type=\"radio\" id='rdMap' name=\"tipo_map\" checked=true value=" + res.getString("id_map") + ">" + res.getString("tipo_map") + " (" + res.getString("descricao") + ")</div>");
                    } else {
                        out.println("<div class=\"ValueIndex\"><input type=\"radio\" id='rdMap' name=\"tipo_map\" value=" + res.getString("id_map") + ">" + res.getString("tipo_map") + " (" + res.getString("descricao") + ")</div>");
                    }


                }
                if (acao.equalsIgnoreCase("cadastra")) {

                    String sqlPadrao = "SELECT metadata_prefix, name_space FROM padraometadados p WHERE p.id=" + idPadrao;
                    ResultSet rsPadrao = stm.executeQuery(sqlPadrao);
                    while (rsPadrao.next()) {
                        String nameSpace = rsPadrao.getString("name_space");
                        String metadataPrefix = rsPadrao.getString("metadata_prefix");
                        out.println("<div> &nbsp;</div>"
                                + "<div class=\"Label\">MetadataPrefix:</div>"
                                + "<div class=\"Value\">  <input type=\"text\" value=\"" + metadataPrefix + "\" id=\"metPrefix\" name=\"metPrefix\" maxlength=\"45\" onFocus=\"this.className='inputSelecionado'\" onBlur=\"this.className=''\" /></div>  "
                                + "<div class=\"Label\">NameSpace:</div> "
                                + "<div class=\"Value\"> <input type=\"text\" value=\"" + nameSpace + "\" id=\"namespace\" name=\"namespace\" maxlength=\"45\" onFocus=\"this.className='inputSelecionado'\" onBlur=\"this.className=''\" /> </div>");
                    }
                }


            }
%>