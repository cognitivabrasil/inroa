<%-- 
    Document   : adicionaMapBranco
    Created on : 04/10/2010, 16:49:16
    Author     : Marcos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../testaSessaoNovaJanela.jsp"%>
<%@include file="../conexaoBD.jsp"%>
<%

            int idTipoMap = 0;
            int idPadrao = 0;
            boolean confereAtributos = false;
            try {
                String tipoMap = request.getParameter("tipoMap");
                idTipoMap = Integer.parseInt(tipoMap);
                if (tipoMap.isEmpty() || idTipoMap <= 0) {
                    out.print("<script type='text/javascript'>alert('O id do tipo de mapeamento deve ser informado');</script>" +
                            "<script type='text/javascript'>fechaRecarrega();</script>");
                    ;
                }
                String padrao = request.getParameter("padrao");
                idPadrao = Integer.parseInt(padrao);
                if (padrao.isEmpty() || idPadrao <= 0) {
                    out.print("<script type='text/javascript'>alert('O id do padrao deve ser informado');</script>" +
                            "<script type='text/javascript'>history.go(-1);</script>");
                }
                confereAtributos = true;
            } catch (Exception e) {
                out.print("<script type='text/javascript'>alert('O tipo do mapeamento e o id do padrao devem ser informados');</script>" +
                        "<script type='text/javascript'>history.go(-1);</script>");
                System.err.println("Erro recebendo os dados ao adicionar mapeamento " + e);

            }

            if (confereAtributos) {
                String sqlConfere = "select * from mapeamentos where padraometadados_id=" + idPadrao + " and tipo_mapeamento_id=" + idTipoMap + ";";
                ResultSet rsConfere = stm.executeQuery(sqlConfere);
                if (rsConfere.next()) { //se ja existir na base de dados
                    out.print("<script type='text/javascript'>alert('Ja existe um mapeamento para esse padrao com esse tipo de mapeamento. Selecione outro tipo de mapeamento.');</script>" +
                        "<script type='text/javascript'>history.go(-1);</script>");
                } else {
                    String sqlInsert = "INSERT INTO mapeamentos (origem_id, padraometadados_id, destino_id, tipo_mapeamento_id, mapeamento_composto_id) " +
                            "SELECT id as origem_id, id_padrao, 0 as destino_id, "+idTipoMap+" as tipo_mapeamento_id, null as mapeamento_composto_id FROM atributos WHERE id_padrao="+idPadrao+";";
                    int resultInsert = stm.executeUpdate(sqlInsert);
                    if (resultInsert>0){
                        String compURL = "editaMapeamentos.jsp?tipmap="+idTipoMap+"&padrao="+idPadrao;
                        response.sendRedirect(compURL);
                    }

                }
            }
%>