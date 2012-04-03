/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ferramentaBusca;

import ferramentaBusca.indexador.Documento;
import ferramentaBusca.indexador.StopWordTAD;
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
     * @param ordenacao pelo que ser&aacute; ordenado o resultado, pela data ou por relev&atilde;ncia
     * @return ArrayList de inteiros contendo os ids dos documentos retornados na busca. Em ordem de relev&acirc;ncia.
     * @exception SQLException
     */
    public ArrayList<Integer> busca(String consulta,Connection con, String[] idRepLocal, String[] idSubfed, String[] idSubRep, String ordenacao) throws SQLException {
        
        return (busca(consulta, null, con, idRepLocal, idSubfed, idSubRep, ordenacao));
    }
    
    public ArrayList<Integer> busca(String consulta, String autor, Connection con, String[] idRepLocal, String[] idSubfed, String[] idSubRep, String ordenacao) throws SQLException {


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


        StopWordTAD stWd = new StopWordTAD(con);
        Documento docConsulta = new Documento(consulta, stWd); //Cria tad Documento informando a consulta
        ArrayList<String> tokensConsulta = docConsulta.getTokens(); //tokeniza as palavras da consulta e adiciona no ArrayList
        ArrayList<Integer> idsResultados = new ArrayList<Integer>(); //lista dos ids dos documentos retornados da consulta

        boolean confederacao = false;
        boolean LRU = false;
        boolean autorb = false;
        
        LRU cache = new LRU(tokensConsulta, con);

        String consultaSql = ""; //para cada caso de combinacoes dos parametros a consulta sql eh gerada em um dos metodos privados        
        String sqlOrdenacao = ""; //eh preenchido pelo if que testa qual o tipo de ordenacao

        if (autor != null) autorb = true;
        
        if (ordenacao.equals("data")) {
            //TODO: Fazer RSS para autor?
            sqlOrdenacao = "') GROUP BY r1w.tid, timestamp HAVING SUM(r1w.weight)>= 0.2*" + tokensConsulta.size() + " ORDER BY timestamp DESC;";
        } else {
            
            if (autorb){
                if (consulta.isEmpty()){
                    //COM autor, SEM termo de busca
                    sqlOrdenacao = " a.documento=d.id AND a.nome~@@'"+autor+"' GROUP BY d.id, a.nome ORDER BY (qgram(a.nome, '"+autor+"')) DESC;";
                } else {
                    //COM autor, COM termo de busca
                    sqlOrdenacao = "') AND a.documento=d.id AND a.nome~@@'"+autor+"' GROUP BY r1w.tid ORDER BY SUM (weight) DESC;";
                }
            } else {
                
                sqlOrdenacao = "') GROUP BY r1w.tid ORDER BY SUM(weight) DESC;";
            }
        }

        if (idRepLocal.length == 1 && idRepLocal[0].isEmpty()) {
            if (idSubfed.length == 1 && idSubfed[0].isEmpty()) {
                if (idSubRep.length == 1 && idSubRep[0].isEmpty()) {
                    LRU = cache.verificaConsulta();
                    if (!LRU) {
                        confederacao = true;
                        //busca na confederacao
                        consultaSql = buscaConfederacao(tokensConsulta, sqlOrdenacao, autorb);
                    }
                } else {
                    consultaSql = busca_subRep(tokensConsulta, idSubRep, sqlOrdenacao, autorb); //busca no subrepositorio
                }
            } else { //subfed != vazio e repLocal = vazio
                if (idSubRep.length == 1 && idSubRep[0].isEmpty()) {
                    consultaSql = busca_subfed(tokensConsulta, idSubfed, sqlOrdenacao, autorb);//busca na subfederacao
                } else {
                    consultaSql = busca_subfed_subrep(tokensConsulta, idSubfed, idSubRep, sqlOrdenacao, autorb); //busca na subfederacao e no subrepositorio
                }
            }
        } else { //replocal != vazio
            if (idSubfed.length == 1 && idSubfed[0].isEmpty()) { //replocal != vazio e subfed = vazio
                if (idSubRep.length == 1 && idSubRep[0].isEmpty()) {
                    consultaSql = busca_repLocal(tokensConsulta, idRepLocal, sqlOrdenacao, autorb); //busca no repositorio local
                } else {
                    consultaSql = busca_repLocal_subrep(tokensConsulta, idRepLocal, idSubRep, sqlOrdenacao, autorb); //busca no reposiotio local e no subrepositorio
                }
            } else { //replocal != vazio e subfed != vazio
                if (idSubRep.length == 1 && idSubRep[0].isEmpty()) {
                    consultaSql = busca_repLocal_subfed(tokensConsulta, idRepLocal, idSubfed, sqlOrdenacao, autorb);//busca na subfederacao
                } else {
                    consultaSql = busca_repLocal_subfed_subrep(tokensConsulta, idRepLocal, idSubfed, idSubRep, sqlOrdenacao, autorb); //busca na subfederacao e no subrepositorio
                }
            }
        }

        if (LRU){ //se a consulta ja esta no banco de dados
            idsResultados = cache.getResultado();
        }
        else { //se a consulta nao tiver no banco
            PreparedStatement stmt = con.prepareStatement(consultaSql);

            ResultSet rs = stmt.executeQuery();
            if (confederacao) { //se for confederacao e nao tiver no banco a consulta
                while (rs.next()) {
                    idsResultados.add(rs.getInt("tid"));
                    cache.setResultado(rs.getString("tid"));
                }
                cache.gravaResultado(); //armazena o resultado na tabela consultas (LRU)
                
            } else { //se nao for na confederacao
                while (rs.next()) {
                    idsResultados.add(rs.getInt("tid"));
                }
            }

        }
        return idsResultados;
    }

    //confederacao
    String buscaConfederacao(ArrayList<String> tokensConsulta, String finalSQL, boolean autor) {
        
        if (autor) {                        // if is a author request
            if (tokensConsulta.isEmpty()) {   // see if have a query                
                String consultaSql = "SELECT d.id FROM documentos d, autores a WHERE "+finalSQL;
                return consultaSql;
                
            } else {
                String consultaSql = "SELECT tid FROM r1weights r1w, documentos d, autores a"
                        + " WHERE r1w.tid=d.id "
                        + " AND (r1w.token=";

                for (int i = 0; i < tokensConsulta.size(); i++) {
                    String token = tokensConsulta.get(i);
                    if (i == tokensConsulta.size() - 1) {
                        consultaSql += "'" + token;
                    } else {
                        consultaSql += "'" + token + "' OR r1w.token=";
                    }

                }
                consultaSql += finalSQL;
                return consultaSql;
            }
        } else {
            String consultaSql = "SELECT tid FROM r1weights r1w, documentos d"
                    + " WHERE r1w.tid=d.id "
                    + " AND (r1w.token=";

            for (int i = 0; i < tokensConsulta.size(); i++) {
                String token = tokensConsulta.get(i);
                if (i == tokensConsulta.size() - 1) {
                    consultaSql += "'" + token + finalSQL;
                } else {
                    consultaSql += "'" + token + "' OR r1w.token=";
                }

            }
            return consultaSql;
        }
    }

    //replocal
    String busca_repLocal(ArrayList<String> tokensConsulta, String[] idRepLocal, String finalSQL, boolean autor) {
        
        String consultaSql = "";
        if (autor) {                     // if is a author request
            if (tokensConsulta.isEmpty()) {
                consultaSql = "SELECT d.id FROM documentos d, autores a WHERE ";
            } else {
                consultaSql = "SELECT tid FROM r1weights r1w, documentos d, autores a"
                        + " WHERE r1w.tid=d.id "
                        + " AND (";
            }
        } else {
            consultaSql = "SELECT tid FROM r1weights r1w, documentos d "
                    + " WHERE r1w.tid=d.id "
                    + " AND (";
        }
        
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
                consultaSql += "'" + token + finalSQL;
            } else {
                consultaSql += "'" + token + "' OR r1w.token=";
            }
        }
        return consultaSql;
        
    }

    //subfed
    String busca_subfed(ArrayList<String> tokensConsulta, String[] idSubfed, String finalSQL, boolean autor) {
        String consultaSql = "";
        if (autor) {                    // if is a author request
            if (tokensConsulta.isEmpty()) {
                consultaSql = "SELECT d.id FROM documentos d, autores a WHERE ";
            } else {
                consultaSql = "SELECT tid FROM r1weights r1w, documentos d, repositorios_subfed rsf, autores a"
                        + " WHERE r1w.tid=d.id AND d.id_rep_subfed = rsf.id"
                        + " AND (";
            }
        } else {
                consultaSql = "SELECT tid FROM r1weights r1w, documentos d, repositorios_subfed rsf"
                + " WHERE r1w.tid=d.id AND d.id_rep_subfed = rsf.id "
                + " AND (";
        }

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
                consultaSql += "'" + token + finalSQL;
            } else {
                consultaSql += "'" + token + "' OR r1w.token=";
            }
        }

        return consultaSql;
    }

    //repsubfed
    String busca_subRep(ArrayList<String> tokensConsulta, String[] idSubRep, String finalSQL, boolean autor) {
        
        String consultaSql = "";
        if (autor) {                     // if is a author request
            if (tokensConsulta.isEmpty()) {
                consultaSql = "SELECT d.id FROM documentos d, autores a WHERE ";
            } else {
                consultaSql = "SELECT tid FROM r1weights r1w, documentos d, autores a"
                        + " WHERE r1w.tid=d.id "
                        + " AND (";
            }
        } else {
                consultaSql = "SELECT tid FROM r1weights r1w, documentos d"
                + " WHERE r1w.tid=d.id "
                + " AND (";
        }

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
                consultaSql += "'" + token + finalSQL;
            } else {
                consultaSql += "'" + token + "' OR r1w.token=";
            }
        }

        return consultaSql;
    }

    //replocal + subfed
    String busca_repLocal_subfed(ArrayList<String> tokensConsulta, String[] idRepLocal, String[] idSubfed, String finalSQL, boolean autor) {

        String consultaSql = "";
        if (autor) {                     // if is a author request
            if (tokensConsulta.isEmpty()) {
                consultaSql = "SELECT d.id FROM documentos d, autores a WHERE ";
            } else {
                consultaSql = "SELECT tid FROM r1weights r1w, documentos d, repositorios_subfed rsf, autores a"
                + " WHERE r1w.tid=d.id AND ("
                + " (d.id_rep_subfed = rsf.id AND (";
            }
        } else {
                consultaSql = "SELECT tid FROM r1weights r1w, documentos d, repositorios_subfed rsf"
                + " WHERE r1w.tid=d.id AND ("
                + " (d.id_rep_subfed = rsf.id AND (";
        }
        

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
                consultaSql += "'" + token + finalSQL;
            } else {
                consultaSql += "'" + token + "' OR r1w.token=";
            }
        }


        return consultaSql;
    }

    //replocal + repsubfed
    String busca_repLocal_subrep(ArrayList<String> tokensConsulta, String[] idRepLocal, String[] idSubRep, String finalSQL, boolean autor) {
        
        String consultaSql = "";
        if (autor) {                     // if is a author request
            if (tokensConsulta.isEmpty()) {
                consultaSql = "SELECT d.id FROM documentos d, autores a WHERE ";
            } else {
                consultaSql = "SELECT tid FROM r1weights r1w, documentos d, autores a"
                + " WHERE r1w.tid=d.id"
                + " AND (";
            }
        } else {
                consultaSql = "SELECT tid FROM r1weights r1w, documentos d"
                + " WHERE r1w.tid=d.id"
                + " AND (";
        }     

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
                consultaSql += "'" + token + finalSQL;
            } else {
                consultaSql += "'" + token + "' OR r1w.token=";
            }
        }


        return consultaSql;
    }

    //subfed + repsubfed
    String busca_subfed_subrep(ArrayList<String> tokensConsulta, String[] idSubfed, String[] idSubRep, String finalSQL, boolean autor) {

        String consultaSql = "";
        if (autor) {                   // if is a author request
            if (tokensConsulta.isEmpty()) {
                consultaSql = "SELECT d.id FROM documentos d, autores a WHERE ";
            } else {
                consultaSql = "SELECT tid FROM r1weights r1w, documentos d, repositorios_subfed rsf, autores a"
                + " WHERE r1w.tid=d.id AND d.id_rep_subfed = rsf.id"
                + " AND (";
            }
        } else {
                consultaSql = "SELECT tid FROM r1weights r1w, documentos d, repositorios_subfed rsf"
                + " WHERE r1w.tid=d.id AND d.id_rep_subfed = rsf.id"
                + " AND (";
        }         
        
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
                consultaSql += "'" + token + finalSQL;
            } else {
                consultaSql += "'" + token + "' OR r1w.token=";
            }
        }

        return consultaSql;
    }

    //replocal + subfed + repsubfed
    String busca_repLocal_subfed_subrep(ArrayList<String> tokensConsulta, String[] idRepLocal, String[] idSubfed, String[] idSubRep, String finalSQL, boolean autor) {
       
        String consultaSql = "";
        if (autor) {                   // if is a author request
            if (tokensConsulta.isEmpty()) {
                consultaSql = "SELECT d.id FROM documentos d, autores a WHERE ";
            } else {
                consultaSql = "SELECT tid FROM r1weights r1w, documentos d, repositorios_subfed rsf, autores a"
                + " WHERE r1w.tid=d.id AND ("
                + " (d.id_rep_subfed = rsf.id AND (";
            }
        } else {
                consultaSql = "SELECT tid FROM r1weights r1w, documentos d, repositorios_subfed rsf"
                + " WHERE r1w.tid=d.id AND ("
                + " (d.id_rep_subfed = rsf.id AND (";
        } 

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
                consultaSql += "'" + token + finalSQL;
            } else {
                consultaSql += "'" + token + "' OR r1w.token=";
            }
        }

        return consultaSql;
    }            
}