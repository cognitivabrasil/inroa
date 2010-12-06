/**
 *
 */
package ferramentaBusca.recuperador;

import ferramentaBusca.indexador.Documento;
import ferramentaBusca.indexador.Indexador;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import postgres.Conectar;

/**
 * Classe para a busca de objetos armazenados pela classe {@link Indexador}
 * @author Luiz Rossi
 *
 */
public class Recuperador {


    public Recuperador() {

    }

    /**
     * Transforma um string de inteiros em um ArrayList de inteiros
     * @param s String de inteiros separados por v&iacute;rgulas
     * @return ArrayList de inteiros correspondente &agrave; string
     **/
    private ArrayList <Integer> stringToIntegerArrayList (String s){
        int posfim = 0, posini = 0;
        ArrayList <Integer> array = new ArrayList<Integer>();
        while (!s.isEmpty()) {
            if (s.contains(","))
                posfim = s.indexOf(',');
            else
                posfim = s.length();
            try {
                array.add(Integer.parseInt(s.substring(posini, posfim)));
            } catch (NumberFormatException e) {
                System.out.println("Id de repositorio invalido " + e);
            } catch (Exception e) {
                System.out.println(e);
            }
            if (s.contains(","))
                posfim += 1;
            s = s.substring(posfim);
        }
        return array;
    }
        /**
     * Realiza a consulta na base de dados do termo 'query' na base tranferida no 'con'
     * @param query
     *            a string a ser consultada
     * @param con
     *            a conex&atilde;o com o banco de dados
     * @param idRep ArrayList de reposit&oacute;rios onde ser&aacute; feita a busca
     * @return uma lista de Integer com o id de cada documento
     * @throws SQLException
     */
    public ArrayList<Integer> search2(String query, Connection con, String idRep)
            throws SQLException {
        if (idRep.contains("0"))
            return search2(query, con, 0);
        else
            return search2(query, con, stringToIntegerArrayList(idRep));
    }

    /**
     * Realiza a consulta na base de dados do termo 'query' na base tranferida no 'con'
     * @param query
     *            a string a ser consultada
     * @param con
     *            a conex&atilde;o com o banco de dados
     * @param idRep ArrayList de reposit&oacute;rios onde ser&aacute; feita a busca
     * @return uma lista de Integer com o id de cada documento
     * @throws SQLException
     */
    public ArrayList<Integer> search2(String query, Connection con, ArrayList<Integer> idRep)
            throws SQLException {


        Documento ret = new Documento(query);
        ArrayList<String> tokens = ret.getTokens();
        ArrayList<Integer> idDoc = new ArrayList<Integer>();
        String consult = "";

        consult = "SELECT tid FROM r1weights r1w, documentos d "
                + " WHERE r1w.tid=d.id "
                + " AND (d.id_repositorio=" + idRep.get(0);

        for (int i = 1; i < idRep.size(); i++) {
            consult += " OR d.id_repositorio=" + idRep.get(i);
        }
        consult += ") AND (r1w.token=";
        //System.out.println(consult);

        for (int i = 0; i < tokens.size(); i++) {

            String token = tokens.get(i);
            if (i == tokens.size() - 1) {
                consult += "'" + token + "') GROUP BY r1w.tid ORDER BY SUM(weight) DESC;";
            } else {
                consult += "'" + token + "' OR r1w.token=";
            }

        }
        int id;

        PreparedStatement stmt = con.prepareStatement(consult);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            id = rs.getInt("tid");
            idDoc.add(id);

        }
        return idDoc;
    }

    /**
     * Realiza a consulta na base de dados do termo 'query' na base tranferida no 'con'
     * @param query
     *            a string a ser consultada
     * @param con
     *            a conex&atilde;o com o banco de dados
     * @param idRep identificador do reposit&oacute;rio onde ser&aacute; feita a busca. Usar 0 caso se queira buscar todos em todos os reposit&oacute;rios
     * @return uma lista de integer com o id de cada documento
     * @throws SQLException
     */
    public ArrayList<Integer> search2(String query, Connection con, int idRep)
            throws SQLException {


        Documento ret = new Documento(query);
        ArrayList<String> tokens = ret.getTokens();
//        ArrayList<String> Entry = new ArrayList<String>();
        ArrayList<Integer> idDoc = new ArrayList<Integer>();


        String consult = "";
        if (idRep > 0) {
            consult = "SELECT tid FROM r1weights r1w, documentos d " +
                    " WHERE r1w.tid=d.id " +
                    " AND d.id_repositorio=" + idRep +
                    " AND (r1w.token=";
        } else {
            consult = "SELECT tid FROM r1weights r1w WHERE (r1w.token=";
        }

        for (int i = 0; i < tokens.size(); i++) {

            String token = tokens.get(i);
            if (i == tokens.size() - 1) {
                consult += "'" + token + "') GROUP BY r1w.tid ORDER BY SUM(weight) DESC;";
            } else {
                consult += "'" + token + "' OR r1w.token=";
            }

        }


        int id;

        PreparedStatement stmt = con.prepareStatement(consult);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            id = rs.getInt("tid");
            idDoc.add(id);

//                String entryQ = "SELECT obaa_entry FROM documentos WHERE id=" + id
//                        + ";";
//                Statement stm = con.createStatement();
//                ResultSet rs2 = stm.executeQuery(entryQ);
//                rs2.next();
//                String entryA = rs2.getString("obaa_entry");
//
//                Entry.add(entryA);

        }
        return idDoc;
    }


    ///////////
    /**
     * M&eacute;todo para popular os relacionamentos tempor&aacute;rios de R2
     * @param con
     * @throws SQLException
     * @deprecated
     */
    private static void populateR2(Connection con) throws SQLException {

        PreparedStatement R2Weights = con.prepareStatement("INSERT INTO R2Weights(tid, token, weight) SELECT T.id, T.token, 1 FROM R2Tokens T;");
        R2Weights.execute();
        R2Weights.close();

        PreparedStatement R2Sum = con.prepareStatement("INSERT INTO R2Sum(token, total) SELECT R.token, SUM(R.weight) FROM R2Weights R GROUP BY R.token;");
        R2Sum.execute();
        R2Sum.close();
    }

    /**
     * M�todo para exluir os relacionamentos auxiliares de R2
     * @param con
     * @throws SQLException
     */
    private static void deleteR2(Connection con) throws SQLException {

        String submit = "DELETE FROM R2TOKENS;";
        PreparedStatement stmt = con.prepareStatement(submit);
        stmt.execute();
        stmt.close();

        String submit2 = "DELETE FROM R2WEIGHTS;";

        PreparedStatement delweights = con.prepareStatement(submit2);
        delweights.execute();
        delweights.close();

        String submit3 = "DELETE FROM R2SUM;";

        PreparedStatement delsum = con.prepareStatement(submit3);
        delsum.execute();
        delsum.close();
    }

    public static void main(String[] args) {
        Conectar conecta = new Conectar();
        Connection con = conecta.conectaBD();
        Recuperador run = new Recuperador();
        try{
            System.out.println(run.search2("gremio", con, "0"));
        }catch(SQLException s){
            System.out.println(s);
        }
    }


    //sem lista de stop words

//    public Recuperador() {
//        stWd = null;
//        ret = new Documento(null);
//    }

//    /**
//     * Realiza a consulta na base de dados do termo 'query' na base tranferida no 'con'
//     * @param query
//     *            a string a ser consultada
//     * @param con
//     *            a conex&atilde;o com o banco de dados
//     * @return uma lista de integer com o id de cada documento
//     * @throws SQLException
//     * @deprecated
//     */
//    public ArrayList<String> search(String query, Connection con)
//            throws SQLException {
//
//        ret = new Documento(query, stWd);
//        ArrayList<String> tokens = ret.getTokens();
//
//        for (int i = 0; i < tokens.size(); i++) {
//
//            String token = tokens.get(i);
//            String add = "insert into R2TOKENS (token) VALUES ('" + token + "')";
//            PreparedStatement stmt = con.prepareStatement(add);
//            stmt.execute();
//            stmt.close();
//        }
//        populateR2(con);
//
//        String consult;
//
//        consult = "SELECT r1w.tid AS tid1 FROM R1Weights r1w, R2Weights r2w WHERE r1w.token = r2w.token GROUP BY r1w.tid, r2w.tid HAVING SUM(r1w.weight*r2w.weight)>=0.003 ORDER BY SUM(r1w.weight*r2w.weight) DESC;";
//        PreparedStatement stmt = con.prepareStatement(consult);
//        ResultSet rs = stmt.executeQuery();
//        rs = stmt.executeQuery();
//        int id;
//
//        ArrayList<String> Entry = new ArrayList<String>();
//        while (rs.next()) {
//            id = rs.getInt("tid1");
//            System.out.println("id: " + id);
//            String entryQ = "SELECT obaa_entry FROM documentos WHERE id=" + id + ";";
//            Statement stm = con.createStatement();
//            ResultSet rs2 = stm.executeQuery(entryQ);
//            rs2.next();
//            String entryA = rs2.getString("obaa_entry");
//            System.out.println("entry: " + entryA);
//            Entry.add(entryA);
//
//        }
//        deleteR2(con);
//
//        return Entry;
//    }
    ////////////
}
