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
    /**
     * Realiza a consulta na base de dados pelo termo termo 'query' utilizando a base passada pelo 'con'
     * @param query a string a ser consultada
     * @param con a conex&atilde;o com o banco de dados
     * @param idRep id do reposit&oacute;, se for zero busca em todos, se n&atilde busca apenas no selecionado.
     * @return uma lista de integer com o id de cada documento
     * @throws SQLException
     */
    public ArrayList<Integer> search2(String query, Connection con, int idRep)
            throws SQLException {


        Documento ret = new Documento(query);
        ArrayList<String> tokens = ret.getTokens();
//		ArrayList<String> Entry = new ArrayList<String>();
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
}
