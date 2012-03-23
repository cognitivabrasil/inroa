<%-- 
    Document   : salvarBaseAjax.jsp
    Created on : 27/09/2010, 18:59:39
    Author     : Marcos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% // Importação de conexão com a base de dados %>
<%@include file="../../conexaoBD.jsp"%>

<%




//Parâmetros recuperados do Ajax
            String idOrigem = "";
            String idDestino = "";
            String idOrigemComp = "";
            String idDestinoComp = "";
            String padrao = "";
            String tipoMap = "";

            try{
            idOrigem = request.getParameter("origem");
            idDestino = request.getParameter("destino");
            idOrigemComp = request.getParameter("origemcomplementar");
            idDestinoComp = request.getParameter("destinocomplementar");
            padrao = request.getParameter("padrao");
            tipoMap = request.getParameter("tipoMap");
            }catch(Exception e){
                out.print("nao foram informados os parametros");
            }

            
            if (!idOrigem.isEmpty() || !idDestino.isEmpty() || !padrao.isEmpty() || !tipoMap.isEmpty()) {
                
//                out.println("INICIO padrao: -"+padrao+"- origem: -"+idOrigem+"- destino: -"+idDestino+"- tipomap: -"+tipoMap+"- origemComp: -"+idOrigemComp+"- destinoComp: -"+idDestinoComp +"- FIM ");
                
                try {
                    
                    out.println(gravaMapeamentosBase(padrao, idOrigem, idDestino, tipoMap, idOrigemComp, idDestinoComp));

                } catch (SQLException e) {
                    out.println("0");
                    e.printStackTrace();//log
                }
            } else {
                out.println("0");
            }
%>


<%!

public int gravaMapeamentosBase(String padrao, String origem, String destino, String tipoMap, String origemComp, String destinoComp) throws SQLException {

        Conectar conecta = new Conectar();
        Connection con = conecta.conectaBD();
        int retornoIdComp = 0;


        if (!origemComp.isEmpty() || !destinoComp.isEmpty()) {
            String sqlComp = "INSERT INTO mapeamentocomposto (valor, id_origem) VALUES (?, ?);";
            PreparedStatement stmt = con.prepareStatement(sqlComp, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, destinoComp);
            stmt.setInt(2, Integer.parseInt(origemComp));
            stmt.execute();

            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            retornoIdComp = rs.getInt(1);
        }


        String sql = "INSERT INTO mapeamentos (padraometadados_id, origem_id, destino_id, tipo_mapeamento_id, mapeamento_composto_id ) VALUES " +
                "(?, ?, ?, ?, ?)";
        if (retornoIdComp <= 0) {
            sql = "INSERT INTO mapeamentos (padraometadados_id, origem_id, destino_id, tipo_mapeamento_id) VALUES " +
                    "(?, ?, ?, ?)";
        }

        PreparedStatement stmt1 = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt1.setInt(1, Integer.parseInt(padrao));
        stmt1.setInt(2, Integer.parseInt(origem));
        stmt1.setInt(3, Integer.parseInt(destino));
        stmt1.setInt(4, Integer.parseInt(tipoMap));
        if (retornoIdComp > 0) {
            stmt1.setInt(5, retornoIdComp);
        }
        int resultado = stmt1.executeUpdate();
        ResultSet rs = stmt1.getGeneratedKeys();
            rs.next();
            return rs.getInt(1);

    }
%>