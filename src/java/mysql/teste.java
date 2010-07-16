/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mysql;


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
        String sql = "SELECT r.*, i.*, d.*, p.nome as nomePadrao, l.nome as nomeLdap, l.descricao as descricaoLdap, DATE_ADD(i.dataUltimaAtualizacao, INTERVAL i.periodicidadeHoras HOUR) as proximaAtualizacao " +
                "FROM repositorios r, info_repositorios i, ldaps l, dadosldap d, padraometadados p " +
                " WHERE r.id=" + 2 + " " +
                "AND r.id=i.id_repositorio " +
                "AND r.id=d.id_repositorio " +
                "AND i.padraoMetadados=p.id " +
                "AND i.ldapDestino=l.id " +
                "ORDER BY r.nome ASC;";
        try {
            Conectar conecta = new Conectar();
            Connection con = conecta.conectaBD();
            Statement stm = con.createStatement();
            ResultSet res = stm.executeQuery(sql);
            if (res.next()) {
                Timestamp minhaData = res.getTimestamp("proximaAtualizacao");
                System.out.println(minhaData);
                if(minhaData.before(new Date())){
                    System.out.println("Anterior");
                }
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        teste run = new teste();
        run.teste();
    }
}
