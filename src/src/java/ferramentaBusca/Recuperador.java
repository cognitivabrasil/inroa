/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ferramentaBusca;

import ferramentaBusca.indexador.Documento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import postgres.Conectar;

/**
 * Disponibiliza realizar Recuperador por similaridade
 * @author Marcos
 */
public class Recuperador {

    public Recuperador() {
    }

    /**
     * M&eacute;todo para busca de objetos na base de dados. Se n&atilde;o for passado nenhum dos 3 &uacute;ltimos par&acirc;metros a busca ser&aacute; realizada em toda a confedera&ccedil;&atilde;o, deve ser especificado apenas os ids de onde deseja buscar, os demais par&acirc;metros de id devem ser passados null.
     * @param consulta Texto que consultado.
     * @param con Conex&atilde;o com a base de dados da confedera&ccedil;&atilde;o.
     * @param idRepLocal id(s) do(s) reposit&oacute;rio(s) da confedera&ccedil;&atilde;o.
     * @param idSubfed id(s) da(s) subfedera&ccedil;&atilde;o
     * @param idSubRep id(s) do(s) reposit&otirio(s) da(s) subfedera&ccedil;&atilde;o
     * @return ArrayList de inteiros contendo os ids dos documentos retornados na busca. Em ordem de relev&acirc;ncia.
     * @exception SQLException
     */
    public ArrayList<Integer> busca(String consulta, Connection con, String[] idRepLocal, String[] idSubfed, String[] idSubRep) throws SQLException {


        if (idRepLocal == null) {  //tratamento de consistencia, para variaveis nulas
            idRepLocal = new String[1];
            idRepLocal[0] = "";
        }
        if (idSubfed == null) {
            idSubfed = new String[1];
            idSubfed[0] = "";
        }
        if (idSubRep == null) {
            idSubRep = new String[1];
            idSubRep[0] = "";
        }
        Documento docConsulta = new Documento(consulta); //Cria tad Documento informando a consulta
        ArrayList<String> tokensConsulta = docConsulta.getTokens(); //tokeniza as palavras da consulta e adiciona no ArrayList
        ArrayList<Integer> idsResultados = new ArrayList<Integer>(); //lista dos ids dos documentos retornados da consulta

        String consultaSql = ""; //para cada caso de combinacoes dos parametros a consulta sql eh gerada em um dos metodos privados

        if (idRepLocal.length == 1 && idRepLocal[0].isEmpty()) {
            if (idSubfed.length == 1 && idSubfed[0].isEmpty()) {
                if (idSubRep.length == 1 && idSubRep[0].isEmpty()) {
                    //busca na confederacao
                    consultaSql = buscaConfederacao(tokensConsulta);
                } else {
                    consultaSql = busca_subRep(tokensConsulta, idSubRep); //busca no subrepositorio
                }
            } else { //subfed != vazio e repLocal = vazio
                if (idSubRep.length == 1 && idSubRep[0].isEmpty()) {
                    consultaSql = busca_subfed(tokensConsulta, idSubfed);//busca na subfederacao
                } else {
                    consultaSql = busca_subfed_subrep(tokensConsulta, idSubfed, idSubRep); //busca na subfederacao e no subrepositorio
                }
            }
        } else { //replocal != vazio
            if (idSubfed.length == 1 && idSubfed[0].isEmpty()) { //replocal != vazio e subfed = vazio
                if (idSubRep.length == 1 && idSubRep[0].isEmpty()) {
                    consultaSql = busca_repLocal(tokensConsulta, idRepLocal); //busca no repositorio local
                } else {
                    consultaSql = busca_repLocal_subrep(tokensConsulta, idRepLocal, idSubRep); //busca no reposiotio local e no subrepositorio
                }
            } else { //replocal != vazio e subfed != vazio
                if (idSubRep.length == 1 && idSubRep[0].isEmpty()) {
                    consultaSql = busca_repLocal_subfed(tokensConsulta, idRepLocal, idSubfed);//busca na subfederacao
                } else {
                    consultaSql = busca_repLocal_subfed_subrep(tokensConsulta, idRepLocal, idSubfed, idSubRep); //busca na subfederacao e no subrepositorio
                }
            }
        }

        PreparedStatement stmt = con.prepareStatement(consultaSql);

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            idsResultados.add(rs.getInt("tid"));
        }

        return idsResultados;
    }

    //confederacao
    private String buscaConfederacao(ArrayList<String> tokensConsulta) {
        String consultaSql = "SELECT tid FROM r1weights r1w, documentos d "
                + " WHERE r1w.tid=d.id "
                + " AND (r1w.token=";

        for (int i = 0; i < tokensConsulta.size(); i++) {
            String token = tokensConsulta.get(i);
            if (i == tokensConsulta.size() - 1) {
                consultaSql += "'" + token + "') GROUP BY r1w.tid ORDER BY SUM(weight) DESC;";
            } else {
                consultaSql += "'" + token + "' OR r1w.token=";
            }

        }
        return consultaSql;
    }

    //replocal
    private String busca_repLocal(ArrayList<String> tokensConsulta, String[] idRepLocal) {
        String consultaSql = "SELECT tid FROM r1weights r1w, documentos d "
                + " WHERE r1w.tid=d.id "
                + " AND (";

        for (int i = 0; i < idRepLocal.length; i++) {
            if (i == 0) {
                consultaSql += " d.id_repositorio=" + idRepLocal[i];
            } else {
                consultaSql += " OR d.id_repositorio=" + idRepLocal[i];
            }
        }

        consultaSql += ") AND (r1w.token=";

        for (int i = 0; i < tokensConsulta.size(); i++) {
            String token = tokensConsulta.get(i);
            if (i == tokensConsulta.size() - 1) {
                consultaSql += "'" + token + "') GROUP BY r1w.tid ORDER BY SUM(weight) DESC;";
            } else {
                consultaSql += "'" + token + "' OR r1w.token=";
            }
        }
        return consultaSql;
    }

    //subfed
    private String busca_subfed(ArrayList<String> tokensConsulta, String[] idSubfed) {
        String consultaSql = "SELECT tid FROM r1weights r1w, documentos d, repositorios_subfed rsf"
                + " WHERE r1w.tid=d.id AND d.id_rep_subfed = rsf.id "
                + " AND (";

        for (int i = 0; i < idSubfed.length; i++) {
            if (i == 0) {
                consultaSql += "rsf.id_subfed=" + idSubfed[i];
            } else {
                consultaSql += " OR rsf.id_subfed=" + idSubfed[i];
            }
        }

        consultaSql += ") AND (r1w.token=";

        for (int i = 0; i < tokensConsulta.size(); i++) {
            String token = tokensConsulta.get(i);
            if (i == tokensConsulta.size() - 1) {
                consultaSql += "'" + token + "') GROUP BY r1w.tid ORDER BY SUM(weight) DESC;";
            } else {
                consultaSql += "'" + token + "' OR r1w.token=";
            }
        }
        
        return consultaSql;
    }

    //repsubfed
    private String busca_subRep(ArrayList<String> tokensConsulta, String[] idSubRep) {
        String consultaSql = "SELECT tid FROM r1weights r1w, documentos d"
                + " WHERE r1w.tid=d.id "
                + " AND (";

        for (int i = 0; i < idSubRep.length; i++) {
            if (i == 0) {
                consultaSql += "d.id_rep_subfed=" + idSubRep[i];
            } else {
                consultaSql += " OR d.id_rep_subfed=" + idSubRep[i];
            }
        }

        consultaSql += ") AND (r1w.token=";

        for (int i = 0; i < tokensConsulta.size(); i++) {
            String token = tokensConsulta.get(i);
            if (i == tokensConsulta.size() - 1) {
                consultaSql += "'" + token + "') GROUP BY r1w.tid ORDER BY SUM(weight) DESC;";
            } else {
                consultaSql += "'" + token + "' OR r1w.token=";
            }
        }

        return consultaSql;
    }

    //replocal + subfed
    private String busca_repLocal_subfed(ArrayList<String> tokensConsulta, String[] idRepLocal, String[] idSubfed) {

        String consultaSql = "SELECT tid FROM r1weights r1w, documentos d, repositorios_subfed rsf"
                + " WHERE r1w.tid=d.id AND ("
                + " (d.id_rep_subfed = rsf.id AND (";

        for (int i = 0; i < idSubfed.length; i++) {
            if (i == 0) {
                consultaSql += "rsf.id_subfed=" + idSubfed[i];
            } else {
                consultaSql += " OR rsf.id_subfed=" + idSubfed[i];
            }
        }

        consultaSql += ")) OR (";

        for (int i = 0; i < idRepLocal.length; i++) {
            if (i == 0) {
                consultaSql += " d.id_repositorio=" + idRepLocal[i];
            } else {
                consultaSql += " OR d.id_repositorio=" + idRepLocal[i];
            }
        }

        consultaSql += ")) AND (r1w.token=";

        for (int i = 0; i < tokensConsulta.size(); i++) {
            String token = tokensConsulta.get(i);
            if (i == tokensConsulta.size() - 1) {
                consultaSql += "'" + token + "') GROUP BY r1w.tid ORDER BY SUM(weight) DESC;";
            } else {
                consultaSql += "'" + token + "' OR r1w.token=";
            }
        }
        

        return consultaSql;
    }

    //replocal + repsubfed
    private String busca_repLocal_subrep(ArrayList<String> tokensConsulta, String[] idRepLocal, String[] idSubRep) {
        String consultaSql = "SELECT tid FROM r1weights r1w, documentos d"
                + " WHERE r1w.tid=d.id"
                + " AND (";

        for (int i = 0; i < idRepLocal.length; i++) {
            if (i == 0) {
                consultaSql += " d.id_repositorio=" + idRepLocal[i];
            } else {
                consultaSql += " OR d.id_repositorio=" + idRepLocal[i];
            }
        }

        for (int i = 0; i < idSubRep.length; i++) {
            consultaSql += " OR d.id_rep_subfed=" + idSubRep[i];
        }

        consultaSql += ") AND (r1w.token=";

        for (int i = 0; i < tokensConsulta.size(); i++) {
            String token = tokensConsulta.get(i);
            if (i == tokensConsulta.size() - 1) {
                consultaSql += "'" + token + "') GROUP BY r1w.tid ORDER BY SUM(weight) DESC;";
            } else {
                consultaSql += "'" + token + "' OR r1w.token=";
            }
        }
        

        return consultaSql;
    }

    //subfed + repsubfed
    private String busca_subfed_subrep(ArrayList<String> tokensConsulta, String[] idSubfed, String[] idSubRep) {

        String consultaSql = "SELECT tid FROM r1weights r1w, documentos d, repositorios_subfed rsf"
                + " WHERE r1w.tid=d.id AND d.id_rep_subfed = rsf.id"
                + " AND (";
        //for subfed
        for (int i = 0; i < idSubfed.length; i++) {
            if (i == 0) {
                consultaSql += "rsf.id_subfed=" + idSubfed[i];
            } else {
                consultaSql += " OR rsf.id_subfed=" + idSubfed[i];
            }
        }
        //for subrep
        for (int i = 0; i < idSubRep.length; i++) {
            consultaSql += " OR rsf.id=" + idSubRep[i];
        }
        
        consultaSql += ") AND (r1w.token=";

        for (int i = 0; i < tokensConsulta.size(); i++) {
            String token = tokensConsulta.get(i);
            if (i == tokensConsulta.size() - 1) {
                consultaSql += "'" + token + "') GROUP BY r1w.tid ORDER BY SUM(weight) DESC;";
            } else {
                consultaSql += "'" + token + "' OR r1w.token=";
            }
        }
         
       return consultaSql;
    }

    //replocal + subfed + repsubfed
    private String busca_repLocal_subfed_subrep(ArrayList<String> tokensConsulta, String[] idRepLocal, String[] idSubfed, String[] idSubRep) {
        String consultaSql = "SELECT tid FROM r1weights r1w, documentos d, repositorios_subfed rsf"
                + " WHERE r1w.tid=d.id AND ("
                + " (d.id_rep_subfed = rsf.id AND (";

        for (int i = 0; i < idSubfed.length; i++) {
            if (i == 0) {
                consultaSql += "rsf.id_subfed=" + idSubfed[i];
            } else {
                consultaSql += " OR rsf.id_subfed=" + idSubfed[i];
            }
        }

        consultaSql += ")) OR (";

        for (int i = 0; i < idRepLocal.length; i++) {
            if (i == 0) {
                consultaSql += " d.id_repositorio=" + idRepLocal[i];
            } else {
                consultaSql += " OR d.id_repositorio=" + idRepLocal[i];
            }
        }
        for (int i = 0; i < idSubRep.length; i++) {
            consultaSql += " OR d.id_rep_subfed=" + idSubRep[i];
        }

        consultaSql += ")) AND (r1w.token=";

        for (int i = 0; i < tokensConsulta.size(); i++) {
            String token = tokensConsulta.get(i);
            if (i == tokensConsulta.size() - 1) {
                consultaSql += "'" + token + "') GROUP BY r1w.tid ORDER BY SUM(weight) DESC;";
            } else {
                consultaSql += "'" + token + "' OR r1w.token=";
            }
        }

        return consultaSql;
    }

}