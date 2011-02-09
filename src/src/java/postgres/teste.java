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
import ptstemmer.*;
import ptstemmer.Stemmer.StemmerType;
import ptstemmer.exceptions.PTStemmerException;
import ptstemmer.support.PTStemmerUtilities;

/**
 *
 * @author Marcos
 */
public class teste {

    public void testandoPreparedStatement() {
        Conectar conecta = new Conectar();
        Connection con = conecta.conectaBD();
        String sql = "INSERT INTO repositorios (nome, descricao) VALUES "
                + "(?, ?)";
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

    public void testeStemmer() {
        try{
        Stemmer stemmer = Stemmer.StemmerFactory(StemmerType.ORENGO);
        stemmer.enableCaching(1000);
        stemmer.ignore(PTStemmerUtilities.fileToSet("data/stopwords.txt"));
//        stemmer.ignore(PTStemmerUtilities.fileToSet("data/namedEntities.txt"));
        String[] stem = stemmer.getPhraseStems("ciências a para bacana");
        for(int i=0;i<stem.length; i++)
        System.out.println(PTStemmerUtilities.removeDiacritics(stem[i]));
        }
        catch(PTStemmerException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//
        teste run = new teste();
//        //run.testando2();
//        int result = 0;
//        try {
////            run.inserePadrao("nome", "metadata", "namespace", "titulo;marcos;freitas;nunes;abvx");
//            run.teste();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        System.out.println(result);
        run.testeStemmer();
    }
}
