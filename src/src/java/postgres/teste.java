/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Marcos
 */
public class teste {

    public void testeGeneratedKeys() {
        Conectar conecta = new Conectar();
        Connection con = conecta.conectaBD();
        String obaaEntry = "teste";
        int idSubFed = 2;
        String nomeRep = "LUME";
        try {
            String insertSelect = "INSERT INTO documentos (obaa_entry, id_rep_subfed) "
                    + "SELECT '" + obaaEntry + "', id FROM repositorios_subfed WHERE id_subfed=" + idSubFed + " AND nome='" + nomeRep + "'";
            Statement stmLocal = con.createStatement();
            stmLocal.execute(insertSelect, Statement.RETURN_GENERATED_KEYS);
            ResultSet rsInsert = stmLocal.getGeneratedKeys();
            rsInsert.next();
            System.out.println(rsInsert.getInt(1));

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
        String sql = "INSERT INTO repositorios (nome, descricao) VALUES "
                + "(?, ?)";
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

    public void teste() throws SQLException {
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

    public boolean inserePadrao(String nome, String metadaPrefix, String nameSpace, String atributos) throws SQLException {
        Conectar conecta = new Conectar();
        Connection con = conecta.conectaBD();
        int idPadrao = 0;
        boolean result = false;
        String sqlInsert = "INSERT INTO padraometadados (nome, metadata_prefix, name_space) "
                + "VALUES (?, ?, ?);";
        PreparedStatement stmt = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, nome);
        stmt.setString(2, metadaPrefix);
        stmt.setString(3, nameSpace);
        stmt.execute();

        ResultSet rs = stmt.getGeneratedKeys();
        rs.next();
        idPadrao = rs.getInt(1);

        if (idPadrao > 0) {
            String[] vetAtributos = atributos.split(";");
            String sqlAtributos = "INSERT INTO atributos (id_padrao, atributo) VALUES";

            for (int i = 0; i < vetAtributos.length; i++) {
                if (i == vetAtributos.length - 1) {
                    sqlAtributos += " (" + idPadrao + ",'" + vetAtributos[i] + "');";
                } else {
                    sqlAtributos += " (" + idPadrao + ",'" + vetAtributos[i] + "'),";
                }
            }
            PreparedStatement stmtAtrib = con.prepareStatement(sqlAtributos);
            int resultSQL = stmtAtrib.executeUpdate();
            stmtAtrib.close();
            if (resultSQL > 0) {
                result = true;
            } else {
                String sqlDelete = "DELETE FROM padraometadados WHERE id=" + idPadrao;
                PreparedStatement stmtDel = con.prepareStatement(sqlDelete);
                stmtDel.executeUpdate();
                result = false;
            }
        }
        return result;
    }

   public static void testeObj(ArrayList<String> array, String i){
       i+="b";
       array.add("segundo");
   }

   public static void testeObjMain(){
       ArrayList<String> array = new ArrayList<String>();

        String count = "a";

        array.add("primeiro");


        testeObj(array,count);
        count+="c";
        System.out.println(array +" "+count);
   }

    public static void main(String[] args) {

        //Date hoje = new Date();

      Date ontem = new Date(new Date().getTime()-(1000*24*60*60));
        System.out.println(ontem);
      
        }
}
