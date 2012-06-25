/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ferramentaBusca;

import ferramentaBusca.indexador.Documento;
import ferramentaBusca.indexador.StopWordTAD;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import modelos.Consulta;
import modelos.DocumentoReal;

import org.apache.log4j.Logger;
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
	static Logger log = Logger.getLogger(Recuperador.class.getName());


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
//TODO: ver como vai ficar essa consulta
//                sqlOrdenacao = "') GROUP BY d.id ORDER BY SUM(weight) DESC;";
                  sqlOrdenacao = "') GROUP BY d.id, d.obaa_entry, d.id_repositorio, d.timestamp, d.id_rep_subfed, d.deleted, d.obaaxml, d.titulo, d.resumo, d.data, d.localizacao, d.palavras_chave ORDER BY SUM(weight) DESC LIMIT "+consulta.getLimit()+" OFFSET "+consulta.getOffset()+";";

            }
        }

        if (consulta.getRepositorios().isEmpty()) {
            if (consulta.getFederacoes().isEmpty()) {
                if (consulta.getRepSubfed().isEmpty()) {
                    try {
                        LRU = cache.verificaConsulta();
                    } catch (SQLException e) {
                        log.debug("FEB: Não foi possível verificar se a consulta estava nos LRU ", e);
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
        log.debug(consultaSql);
        docs = s.createSQLQuery(consultaSql).addEntity(DocumentoReal.class).list();       
        
        
        consultaSql = consultaSql.replace("SELECT d.*", "SELECT COUNT(DISTINCT(d.id))");
        
        consultaSql = consultaSql.substring(0, consultaSql.indexOf("GROUP"));
        System.out.println(consultaSql);
        BigInteger nResult = (BigInteger) s.createSQLQuery(consultaSql).uniqueResult();
        
        //TODO: colocar o valor certo no sizeResult
        consulta.setSizeResult(nResult.intValue());
        
        try{
        con.close();
        }catch(SQLException se){
            System.out.println("Erro ao fechar a conexao com a base. Classe: "+se.getClass() +" Exception: "+se);
        }
        
        return docs;
        //return null;
    }

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