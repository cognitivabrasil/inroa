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
import java.util.Iterator;
import java.util.List;
import modelos.Consulta;
import modelos.DocumentoReal;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import postgres.Conectar;
import spring.ApplicationContextProvider;

/**
 * Disponibiliza realizar Recuperador por similaridade
 *
 * @author Marcos, Luiz
 */
public class Recuperador {

    public Recuperador() {
    }

    /**
     *
     * @param consulta
     * @param indexIni ordem do primeiro objeto a ser retornado
     * @param offset quantidade de objetos a serem retornados
     * @return
     */
    
    
    public List<DocumentoReal> busca(Consulta consulta){
        
        ApplicationContext context = ApplicationContextProvider.getApplicationContext();
        SessionFactory sf = context.getBean(SessionFactory.class);
        Session s = sf.getCurrentSession();
        
        Conectar c = new Conectar();
        Connection con = c.conectaBD();
        
        StopWordTAD stWd = new StopWordTAD(con);
        Documento docConsulta = new Documento(consulta.getConsulta(), stWd); //Cria tad Documento informando a consulta
        ArrayList<String> tokensConsulta = docConsulta.getTokens(); //tokeniza as palavras da consulta e adiciona no ArrayList
        List<DocumentoReal> docs;  //lista dos documentos retornados da consulta

        boolean confederacao = false;
        boolean LRU = false;        

        LRU cache = new LRU(tokensConsulta, con);

        String consultaSql = ""; //para cada caso de combinacoes dos parametros a consulta sql eh gerada em um dos metodos privados        
        String sqlOrdenacao = ""; //eh preenchido pelo if que testa qual o tipo de ordenacao

        
        if (consulta.isRss()) {
            //TODO: Fazer RSS para autor?
            sqlOrdenacao = "') GROUP BY d.id, timestamp HAVING SUM(r1w.weight)>= 0.2*" + tokensConsulta.size() + " ORDER BY timestamp DESC;";
        } else {

            if (consulta.hasAuthor()) {

                if (consulta.getConsulta().isEmpty()) {
                    //COM autor, SEM termo de busca
                    sqlOrdenacao = " a.documento=d.id AND a.nome~##lower('" + consulta.getAutor() + "') GROUP BY d.id, a.nome ORDER BY (qgram(a.nome, lower('" + consulta.getAutor() + "'))) DESC;";
                } else {
                    //COM autor, COM termo de busca
                    sqlOrdenacao = "') AND a.documento=d.id AND a.nome~##lower('" + consulta.getAutor() + "') GROUP BY d.id, a.nome ORDER BY (qgram(a.nome, lower('" + consulta.getAutor() + "'))) DESC, SUM (weight) DESC;";
                }
            } else {

                sqlOrdenacao = "') GROUP BY d.id ORDER BY SUM(weight) DESC;";
            }
        }

        if (consulta.getRepositorios().isEmpty()) {
            if (consulta.getFederacoes().isEmpty()) {
                if (consulta.getRepSubfed().isEmpty()) {
                    try {
                        LRU = cache.verificaConsulta();
                    } catch (SQLException e) {
                        System.out.println("FEB: Não foi possível verificar se a consulta estava nos LRU "+e);
                        LRU = false;
                    }

                    if (LRU || consulta.hasAuthor()) {
                                              
                        consultaSql = buscaConfederacao(tokensConsulta, sqlOrdenacao, consulta.hasAuthor());
                    } else {                        
                        //busca na confederacao
                        confederacao = true;
                        consultaSql = buscaConfederacao(tokensConsulta, sqlOrdenacao, consulta.hasAuthor());
                    }
                } else {
                    consultaSql = busca_subRep(tokensConsulta, consulta, sqlOrdenacao); //busca no subrepositorio
                }
            } else { //subfed != vazio e repLocal = vazio
                if (consulta.getRepSubfed().isEmpty()) {
                    consultaSql = busca_subfed(tokensConsulta, consulta, sqlOrdenacao);//busca na subfederacao
                } else {
                    consultaSql = busca_subfed_subrep(tokensConsulta, consulta, sqlOrdenacao); //busca na subfederacao e no subrepositorio
                }
            }
        } else { //replocal != vazio
            if (consulta.getFederacoes().isEmpty()) { //replocal != vazio e subfed = vazio
                if (consulta.getRepSubfed().isEmpty()) {
                    consultaSql = busca_repLocal(tokensConsulta, consulta, sqlOrdenacao); //busca no repositorio local
                } else {
                    consultaSql = busca_repLocal_subrep(tokensConsulta, consulta, sqlOrdenacao); //busca no reposiotio local e no subrepositorio
                }
            } else { //replocal != vazio e subfed != vazio
                if (consulta.getRepSubfed().isEmpty()) {
                    consultaSql = busca_repLocal_subfed(tokensConsulta, consulta, sqlOrdenacao);//busca na subfederacao
                } else {
                    consultaSql = busca_repLocal_subfed_subrep(tokensConsulta, consulta, sqlOrdenacao); //busca na subfederacao e no subrepositorio
                }
            }
        }


        if (LRU && !consulta.hasAuthor()) { //se a consulta ja esta no banco de dados e não for por autor
            //resultados = cache.getResultado();
        } else { //se a consulta nao tiver no banco
            
            //PreparedStatement stmt = con.prepareStatement(consultaSql);

  //          docs = s.createSQLQuery(consultaSql).addEntity(DocumentoReal.class).list();
            
            //ResultSet rs = stmt.executeQuery();
            if (confederacao && !consulta.hasAuthor()) { //se for confederacao e nao tiver no banco a consulta
                //while (docs.) {
                    //TODO: adicionar 
                    
                  //  resultados.add(rs.getInt("tid"));
                  //  cache.setResultado(rs.getString("tid"));
                //}
                //cache.gravaResultado(); //armazena o resultado na tabela consultas (LRU)

            } else { //se nao for na confederacao
            //    while (rs.next()) {
             //       idsResultados.add(rs.getInt("tid"));
                }
            }

    //}
        //docs = s.createSQLQuery("SELECT * from documentos WHERE id=1006").addEntity(DocumentoReal.class).list();
        docs = s.createSQLQuery(consultaSql).addEntity(DocumentoReal.class).list();
            
        
        return docs;
        //return null;
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

/*
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
        boolean autorb = true;
        
        LRU cache = new LRU(tokensConsulta, con);

        String consultaSql = ""; //para cada caso de combinacoes dos parametros a consulta sql eh gerada em um dos metodos privados        
        String sqlOrdenacao = ""; //eh preenchido pelo if que testa qual o tipo de ordenacao

        if (autor == null || autor.isEmpty()) autorb = false;
        
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
                    sqlOrdenacao = "') AND a.documento=d.id AND a.nome~@@'"+autor+"' GROUP BY r1w.tid, a.nome ORDER BY (qgram(a.nome, '"+autor+"')) DESC, SUM (weight) DESC;";
                }
            } else {
                
                sqlOrdenacao = "') GROUP BY r1w.tid ORDER BY SUM(weight) DESC;";
            }
        }

        if (idRepLocal.length == 1 && idRepLocal[0].isEmpty()) {
            if (idSubfed.length == 1 && idSubfed[0].isEmpty()) {
                if (idSubRep.length == 1 && idSubRep[0].isEmpty()) {
                    LRU = cache.verificaConsulta();
                    
                    if (!LRU && !autorb) {
                        confederacao = true;
                        //busca na confederacao
                        consultaSql = buscaConfederacao(tokensConsulta, sqlOrdenacao, autorb);
                    } else { 
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
        

        if (LRU && !autorb){ //se a consulta ja esta no banco de dados e não for por autor
            //TODO: removi pq tava dando erro
            //idsResultados = cache.getResultado();
        }
        
        else { //se a consulta nao tiver no banco
            
            System.out.println("SQL:\n"+consultaSql);
            PreparedStatement stmt = con.prepareStatement(consultaSql);

            ResultSet rs = stmt.executeQuery();
            if (confederacao && !autorb) { //se for confederacao e nao tiver no banco a consulta
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
//*/
    //confederacao
    String buscaConfederacao(ArrayList<String> tokensConsulta, String finalSQL, boolean autor) {
        
        if (autor) {                        // if is a author request
            if (tokensConsulta.isEmpty()) {   // see if have a query                
                String consultaSql = "SELECT d.* FROM documentos d, autores a WHERE "+finalSQL;
                return consultaSql;
                
            } else {
                String consultaSql = "SELECT d.* FROM r1weights r1w, documentos d, autores a"
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
            String consultaSql = "SELECT d.* FROM r1weights r1w, documentos d"
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
    String busca_repLocal(ArrayList<String> tokensConsulta, Consulta c, String finalSQL) {
        
        String consultaSql = "";
        if (c.hasAuthor()) {                     // if is a author request
            if (tokensConsulta.isEmpty()) {
                consultaSql = "SELECT d.* FROM documentos d, autores a WHERE ";
            } else {
                consultaSql = "SELECT d.* FROM r1weights r1w, documentos d, autores a"
                        + " WHERE r1w.tid=d.id "
                        + " AND (";
            }
        } else {
            consultaSql = "SELECT d.* FROM r1weights r1w, documentos d "
                    + " WHERE r1w.tid=d.id "
                    + " AND (";
        }
        
        Iterator<Integer> repositorios = c.getRepositorios().iterator();
        boolean addOr = false;
        
        while (repositorios.hasNext()){
            
            if (!addOr){
                consultaSql += " d.id_repositorio=" + repositorios.next();                
                
            } else {
                consultaSql += " OR d.id_repositorio=" + repositorios.next();
            }            
            addOr = true;            
        }

        if (!tokensConsulta.isEmpty())
            consultaSql += ") AND (r1w.token=";
        else
            consultaSql += ")";

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
    String busca_subfed(ArrayList<String> tokensConsulta, Consulta c, String finalSQL) {
        String consultaSql = "";
        if (c.hasAuthor()) {                    // if is a author request
            if (tokensConsulta.isEmpty()) {
                consultaSql = "SELECT d.* FROM documentos d, autores a WHERE ";
            } else {
                consultaSql = "SELECT d.* FROM r1weights r1w, documentos d, repositorios_subfed rsf, autores a"
                        + " WHERE r1w.tid=d.id AND d.id_rep_subfed = rsf.id"
                        + " AND (";
            }
        } else {
                consultaSql = "SELECT d.* FROM r1weights r1w, documentos d, repositorios_subfed rsf"
                + " WHERE r1w.tid=d.id AND d.id_rep_subfed = rsf.id "
                + " AND (";
        }

        
        Iterator<Integer> idsSubfed = c.getFederacoes().iterator();
        boolean addOr = false;
        
        while (idsSubfed.hasNext()){
            
            if (!addOr){
                consultaSql += "rsf.id_subfed=" + idsSubfed.next();
                
            } else {
                consultaSql += " OR rsf.id_subfed=" + idsSubfed.next();
            }
            addOr = true;            
        }
        

        if (!tokensConsulta.isEmpty())
            consultaSql += ") AND (r1w.token=";
        else
            consultaSql += ")";

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
    String busca_subRep(ArrayList<String> tokensConsulta, Consulta c,String finalSQL) {
        
        String consultaSql = "";
        if (c.hasAuthor()) {                     // if is a author request
            if (tokensConsulta.isEmpty()) {
                consultaSql = "SELECT d.* FROM documentos d, autores a WHERE ";
            } else {
                consultaSql = "SELECT d.* FROM r1weights r1w, documentos d, autores a"
                        + " WHERE r1w.tid=d.id "
                        + " AND (";
            }
        } else {
                consultaSql = "SELECT d.* FROM r1weights r1w, documentos d"
                + " WHERE r1w.tid=d.id "
                + " AND (";
        }

        
        Iterator<Integer> idsRepSubfed = c.getRepSubfed().iterator();
        boolean addOr = false;
        
        while (idsRepSubfed.hasNext()){
            
            if (!addOr){
                consultaSql += "d.id_rep_subfed=" + idsRepSubfed.next();
                
            } else {
                consultaSql += " OR d.id_rep_subfed=" + idsRepSubfed.next();
            }
            addOr = true;            
        }
       

        if (!tokensConsulta.isEmpty())
            consultaSql += ") AND (r1w.token=";
        else
            consultaSql += ")";

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
    String busca_repLocal_subfed(ArrayList<String> tokensConsulta, Consulta c, String finalSQL) {

        String consultaSql = "";
        if (c.hasAuthor()) {                     // if is a author request
            if (tokensConsulta.isEmpty()) {
                consultaSql = "SELECT d.* FROM documentos d, autores a WHERE ";
            } else {
                consultaSql = "SELECT d.* FROM r1weights r1w, documentos d, repositorios_subfed rsf, autores a"
                + " WHERE r1w.tid=d.id AND ("
                + " (d.id_rep_subfed = rsf.id AND (";
            }
        } else {
                consultaSql = "SELECT d.* FROM r1weights r1w, documentos d, repositorios_subfed rsf"
                + " WHERE r1w.tid=d.id AND ("
                + " (d.id_rep_subfed = rsf.id AND (";
        }
        
        
        Iterator<Integer> idsSubfed = c.getFederacoes().iterator();
        boolean addOr = false;
        
        while (idsSubfed.hasNext()){
            
            if (!addOr){
                consultaSql += "rsf.id_subfed=" + idsSubfed.next();
                
            } else {
                consultaSql += " OR rsf.id_subfed=" + idsSubfed.next();
            }
            addOr = true;            
        }
        
        Iterator<Integer> idsRepositorios = c.getRepositorios().iterator();
        addOr = false;
        
        consultaSql += ")) OR (";
        
        while (idsRepositorios.hasNext()){
            
            if (!addOr){
                consultaSql += " d.id_repositorio=" + idsRepositorios.next();
                
            } else {
                consultaSql += " OR d.id_repositorio=" + idsRepositorios.next();
            }
            addOr = true;            
        }
       

        if (!tokensConsulta.isEmpty())
            consultaSql += ")) AND (r1w.token=";
        else
            consultaSql += ")";

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
    String busca_repLocal_subrep(ArrayList<String> tokensConsulta, Consulta c, String finalSQL) {
        
        String consultaSql = "";
        if (c.hasAuthor()) {                    // if is a author request
            if (tokensConsulta.isEmpty()) {
                consultaSql = "SELECT d.* FROM documentos d, autores a WHERE ";
            } else {
                consultaSql = "SELECT d.* FROM r1weights r1w, documentos d, autores a"
                + " WHERE r1w.tid=d.id"
                + " AND (";
            }
        } else {
                consultaSql = "SELECT d.* FROM r1weights r1w, documentos d"
                + " WHERE r1w.tid=d.id"
                + " AND (";
        }     

        Iterator<Integer> idsRepositorios = c.getRepositorios().iterator();
        boolean addOr = false;
      
        while (idsRepositorios.hasNext()){
            
            if (!addOr){
                consultaSql += " d.id_repositorio=" + idsRepositorios.next();
                
            } else {
                consultaSql += " OR d.id_repositorio=" + idsRepositorios.next();
            }
            addOr = true;            
        }
        
        Iterator<Integer> idsRepositoriosSubFed = c.getRepSubfed().iterator();
                
        while (idsRepositoriosSubFed.hasNext()){        
            consultaSql += " OR d.id_rep_subfed=" + idsRepositoriosSubFed.next();                        
        }
        
        if (!tokensConsulta.isEmpty())
            consultaSql += ") AND (r1w.token=";
        else
            consultaSql += ")";

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
    String busca_subfed_subrep(ArrayList<String> tokensConsulta, Consulta c, String finalSQL) {

        String consultaSql = "";
        if (c.hasAuthor()) {                   // if is a author request
            if (tokensConsulta.isEmpty()) {
                consultaSql = "SELECT d.* FROM documentos d, autores a WHERE ";
            } else {
                consultaSql = "SELECT d.* FROM r1weights r1w, documentos d, repositorios_subfed rsf, autores a"
                + " WHERE r1w.tid=d.id AND d.id_rep_subfed = rsf.id"
                + " AND (";
            }
        } else {
                consultaSql = "SELECT d.* FROM r1weights r1w, documentos d, repositorios_subfed rsf"
                + " WHERE r1w.tid=d.id AND d.id_rep_subfed = rsf.id"
                + " AND (";
        }       
        
        Iterator<Integer> idsSubFed = c.getFederacoes().iterator();
        boolean addOr = false;
        
        while (idsSubFed.hasNext()){
            
            if (!addOr){
                consultaSql += " rsf.id_subfed=" + idsSubFed.next();
                
            } else {
                consultaSql += " OR rsf.id_subfed=" + idsSubFed.next();
            }
            addOr = true;            
        }

        Iterator<Integer> idsRepositoriosSubfed = c.getRepSubfed().iterator();
                
        while (idsRepositoriosSubfed.hasNext()){
            
           consultaSql += " OR rsf.id=" + idsRepositoriosSubfed.next();       
        }
        
        
        if (!tokensConsulta.isEmpty())
            consultaSql += ") AND (r1w.token=";
        else
            consultaSql += ")";

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
    String busca_repLocal_subfed_subrep(ArrayList<String> tokensConsulta, Consulta c, String finalSQL) {
       
        String consultaSql = "";
        if (c.hasAuthor()) {                   // if is a author request
            if (tokensConsulta.isEmpty()) {
                consultaSql = "SELECT d.* FROM documentos d, autores a WHERE ";
            } else {
                consultaSql = "SELECT d.* FROM r1weights r1w, documentos d, repositorios_subfed rsf, autores a"
                + " WHERE r1w.tid=d.id AND ("
                + " (d.id_rep_subfed = rsf.id AND (";
            }
        } else {
                consultaSql = "SELECT d.* FROM r1weights r1w, documentos d, repositorios_subfed rsf"
                + " WHERE r1w.tid=d.id AND ("
                + " (d.id_rep_subfed = rsf.id AND (";
        } 

        Iterator<Integer> idsSubFed = c.getFederacoes().iterator();
        boolean addOr = false;
        
        while (idsSubFed.hasNext()){
            
            if (!addOr){
                consultaSql += " rsf.id_subfed=" + idsSubFed.next();
                
            } else {
                consultaSql += " OR rsf.id_subfed=" + idsSubFed.next();
            }
            addOr = true;            
        }
        
        Iterator<Integer> idsRepositorios = c.getRepositorios().iterator();
        addOr = false;
        
        consultaSql += ")) OR (";
        
        while (idsRepositorios.hasNext()){
            
            if (!addOr){
                consultaSql += " d.id_repositorio=" + idsRepositorios.next();            
            } else {
                consultaSql += " OR d.id_repositorio=" + idsRepositorios.next();
            }
            addOr = true;            
        }
        
        Iterator<Integer> idsRepositoriosSubfed = c.getRepSubfed().iterator();
                
        while (idsRepositoriosSubfed.hasNext()){
            
           consultaSql += " OR rsf.id=" + idsRepositoriosSubfed.next();
        }

        if (!tokensConsulta.isEmpty())
            consultaSql += ")) AND (r1w.token=";
        else
            consultaSql += ")";

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