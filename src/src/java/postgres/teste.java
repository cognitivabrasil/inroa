/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author Marcos
 */
public class teste {

    public void testandoPreparedStatement() {
        Conectar conecta = new Conectar();
        Connection con = conecta.conectaBD();
        String sql = "INSERT INTO repositorios (nome, descricao) VALUES " +
                "(?, ?)";
        try {
            PreparedStatement addAuthor = con.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            addAuthor.setString(1, "teste");
            addAuthor.setString(2, "testando");


            int rows = addAuthor.executeUpdate();
            System.out.println("rows= " + rows);
            ResultSet rs = addAuthor.getGeneratedKeys();
            if (rs.next()) {
                ResultSetMetaData rsmd = rs.getMetaData();
                int colCount = rsmd.getColumnCount();
                do {
                    for (int i = 1; i <= colCount; i++) {
                        String key = rs.getString(i);
                        System.out.println("key " + i + "is " + key);
                    }
                } while (rs.next());
            } else {
                System.out.println("There are no generated keys.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void testando2() {
        Conectar conecta = new Conectar();
        Connection con = conecta.conectaBD();
        String sql = "INSERT INTO repositorios (nome, descricao) VALUES " +
                "(?, ?)";
        try {

            PreparedStatement stmt1 = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt1.setString(1, "teste2");
            stmt1.setString(2, "testando2");
            stmt1.execute();

            ResultSet rs = stmt1.getGeneratedKeys();
            rs.next();
            int key = rs.getInt(1);
            System.out.println("a chave é: " + key);

            stmt1.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void teste() {
        Conectar conecta = new Conectar();
        Connection con = conecta.conectaBD();
        String sql = "";
        try {
            int result = 0;

            // PrepareStatement sta = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //sta.executeUpdate();

            //ResultSet rsID = sta.getGeneratedKeys();

//if(rsID.next())

            int id = 0;
            // rsID.getInt("id");

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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

    public static void main(String[] args) {

        teste run = new teste();
        //run.testando2();
        int result = 0;
        try {
            result = run.gravaMapeamentosBase("1", "117", "117", "1", "", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(result);
    }
}
