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

/**
 * Classe para a busca de objetos armazenados pela classe {@link Indexador}
 * @author Luiz Rossi
 * 
 */
public class Recuperador {


    public Recuperador() {

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
//     * @return uma lista de Strings com o obaa_entry de cada objeto
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
    public ArrayList<Integer> search2(String query, Connection con, int idRep)
            throws SQLException {


        Documento ret = new Documento(query);
        ArrayList<String> tokens = ret.getTokens();
//		ArrayList<String> Entry = new ArrayList<String>();
        ArrayList<Integer> idDoc = new ArrayList<Integer>();

//////		String consult = "SELECT tid,SUM(weight) as weight FROM r1weights r1w WHERE r1w.token=";
        String consult = "";
        if (idRep > 0) {
            //consult = "SELECT tid,SUM(weight) as weight FROM r1weights r1w, repositorios r, documentos d " +
            consult = "SELECT tid FROM r1weights r1w, documentos d " +
                    " WHERE r1w.tid=d.id " +
                    " AND d.id_repositorio=" + idRep +
                    " AND (r1w.token=";
        } else {
            consult = "SELECT tid FROM r1weights r1w WHERE (r1w.token=";
        }
        //consult = "SELECT tid,SUM(weight) as weight FROM r1weights r1w WHERE r1w.token=";
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

//				String entryQ = "SELECT obaa_entry FROM documentos WHERE id=" + id
//						+ ";";
//				Statement stm = con.createStatement();
//				ResultSet rs2 = stm.executeQuery(entryQ);
//				rs2.next();
//				String entryA = rs2.getString("obaa_entry");
//
//				Entry.add(entryA);

        }
        return idDoc;
    }

    ///////////
    /**
     * M�todo para popular os relacionamentos tempor�rios de R2
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
}
