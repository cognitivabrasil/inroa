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
     * Realiza a consulta na base de dados do termo 'query' na base tranferida no 'con'
     * @param query String a ser consultada
     * @param con Conex&atilde;o com o banco de dados
     * @param idRep ArrayList de reposit&oacute;rios onde ser&aacute; feita a busca
     * @return Um ArrayList de inteiros contendo os ids de cada documento
     * @throws SQLException
     */
    public ArrayList<Integer> search2(String query, Connection con, String idRep)
            throws SQLException {
        return search2(query, con, 0, "relevancia");
    }

    public ArrayList<Integer> search2(String query, Connection con, String[] ids)
            throws SQLException {
        
            ArrayList<Integer> repId = new ArrayList<Integer>();
            ArrayList<Integer> subfedId = new ArrayList<Integer>();
            for (int i = 0; i < ids.length; i++) {
                if (ids[i].contains("rep;")) {
                    repId.add(Integer.parseInt(ids[i].replace("rep;", "")));
                } else if (ids[i].contains("subFed;")) {
                    subfedId.add(Integer.parseInt(ids[i].replace("subFed;", "")));
                }
                //COLOCAR AQUI PARA INSERIR OS REPOSITORIOS DAS SUBFEDERACOES
            }

            return search2(query, con, repId, subfedId, "relevancia");
        
    }

    public ArrayList<Integer> search2(String query, Connection con, String idRep, String ordenar)
            throws SQLException {
        if (idRep.equals("0")) {
            return search2(query, con, 0, ordenar);
        } else {
            ArrayList<Integer> repId = new ArrayList<Integer>();
            ArrayList<Integer> subfedId = new ArrayList<Integer>();
            String ids[] = idRep.split(",");
            for (int i = 0; i < ids.length; i++) {
                if (ids[i].contains("rep;")) {
                    repId.add(Integer.parseInt(ids[i].replace("rep;", "")));
                } else if (ids[i].contains("subFed;")) {
                    subfedId.add(Integer.parseInt(ids[i].replace("subFed;", "")));
                }
            }

            return search2(query, con, repId, subfedId, ordenar);
        }
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
     **/
    public ArrayList<Integer> search2(String query, Connection con, ArrayList<Integer> idRep, ArrayList<Integer> idSubfed)
            throws SQLException {
        return search2(query, con, idRep, idSubfed, "relevancia");
    }

    /**
     * Realiza a consulta na base de dados do termo 'query' na base tranferida no 'con'
     * @param query
     *            a string a ser consultada
     * @param con
     *            a conex&atilde;o com o banco de dados
     * @param idRep ArrayList de reposit&oacute;rios onde ser&aacute; feita a busca
     * @param ordenar String que define como ordenar os resultados da consulta.
     * Deve ser "relevancia", para ordenar por relevancia, ou "data", para ordenar por data
     * @return uma lista de Integer com o id de cada documento
     * @throws SQLException
     **/
    public ArrayList<Integer> search2(String query, Connection con, ArrayList<Integer> idRep, ArrayList<Integer> idSubfed, String ordenar)
            throws SQLException {
        Documento ret = new Documento(query);
        int nElementos = ret.getDescricao().size();
        ArrayList<String> tokens = ret.getTokens();
        ArrayList<Integer> idDoc = new ArrayList<Integer>();
        String consult = "";

        consult = "SELECT tid FROM r1weights r1w, documentos d "
                + " WHERE r1w.tid=d.id "
                + " AND (";

        for (int i = 0; i < idRep.size(); i++) {
            if (i == 0) {
                consult += " d.id_repositorio=" + idRep.get(i);
            } else {
                consult += " OR d.id_repositorio=" + idRep.get(i);
            }
        }

        for (int f = 0; f < idSubfed.size(); f++) {
            if (f == 0 && idRep.size() > 0) {
                consult += " OR d.id_subfed=" + idSubfed.get(f);
            } else if (f == 0) {
                consult += "d.id_subfed=" + idSubfed.get(f);
            } else {
                consult += " OR d.id_subfed=" + idSubfed.get(f);
            }
        }

        consult += ") AND (r1w.token=";
        //System.out.println(consult);

        for (int i = 0; i < tokens.size(); i++) {

            String token = tokens.get(i);
            if (i == tokens.size() - 1) {
                if (ordenar.equalsIgnoreCase("data")){
                    //Aqui se usou uma constante arbitrária 0.2 para restringir os resultados, pois uma busca classificada por data pode trazer resultados irrelevantes
                    consult += "'" + token + "') GROUP BY r1w.tid, timestamp HAVING SUM(r1w.weight)>= 0.2*" + nElementos + " ORDER BY timestamp DESC;";
                } else{
                    consult += "'" + token + "') GROUP BY r1w.tid ORDER BY SUM(weight) DESC;";
                }
            } else {
                consult += "'" + token + "' OR r1w.token=";
            }

        }
        int id;
        
        PreparedStatement stmt = con.prepareStatement(consult);
        System.out.println("consulta: "+consult);
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
     * @param ordenar String que define como ordenar os resultados da consulta.
     * Deve ser "relevancia", para ordenar por relevancia, ou "data", para ordenar por data
     * @return uma lista de integer com o id de cada documento
     * @throws SQLException
     */
    public ArrayList<Integer> search2(String query, Connection con, int idRep, String ordenar)
            throws SQLException {

        Documento ret = new Documento(query);
        int nElementos = ret.getDescricao().size();
        ArrayList<String> tokens = ret.getTokens();
        ArrayList<Integer> idDoc = new ArrayList<Integer>();

        String consult = "";
        if (idRep > 0) {
            consult = "SELECT tid FROM r1weights r1w, documentos d "
                    + " WHERE r1w.tid=d.id "
                    + " AND d.id_repositorio=" + idRep
                    + " AND (r1w.token=";
        } else {
            consult = "SELECT tid FROM r1weights r1w, documentos d WHERE r1w.tid=d.id "
                    + " AND (r1w.token=";
        }

        for (int i = 0; i < tokens.size(); i++) {

            String token = tokens.get(i);
            if (i == tokens.size() - 1) {
                if (ordenar.equalsIgnoreCase("data")){
                    //Aqui se usou uma constante arbitrária 0.2 para restringir os resultados, pois uma busca classificada por data pode trazer resultados irrelevantes
                    consult += "'" + token + "') GROUP BY r1w.tid, timestamp HAVING SUM(r1w.weight)>= 0.2*" + nElementos + " ORDER BY timestamp DESC;";
                } else {
                    consult += "'" + token + "') GROUP BY r1w.tid ORDER BY SUM(weight) DESC;";
                }
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

    /**
     * M&eacute;todo para exluir os relacionamentos auxiliares de R2
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

        ArrayList<Integer> resultado = new ArrayList<Integer>();
        ArrayList<Integer> rep = new ArrayList<Integer>();
        ArrayList<Integer> subFed = new ArrayList<Integer>();
        rep.add(36);
        subFed.add(2);

        try {
//            resultado = run.search2("educa", con, idRep, idSubfed);
            //paralelepipedo idRep: [36] idSubfed: [2]odernar: relevancia
            resultado = run.search2("paralelepipedo",con,rep,subFed,"relevancia");
            System.out.println(resultado.size());
            
        } catch (SQLException s) {
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
