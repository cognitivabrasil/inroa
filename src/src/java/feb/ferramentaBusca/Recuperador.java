package feb.ferramentaBusca;

import feb.data.entities.Consulta;
import feb.data.entities.DocumentoReal;
import feb.spring.ApplicationContextProvider;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StopWatch;

/**
 * Recuperador por similaridade
 *
 * @author Marcos, Luiz
 */
public class Recuperador {

    static Logger log = Logger.getLogger(Recuperador.class.getName());
    private static int rssSizeLimit = 100;

    public Recuperador() {
    }

    /**
     * Efetua uma busca no indice de acordo com a consulta informada, retornando
     * uma lista de DocumentosReal que atendem a consulta informada.
     *
     * @param consulta Classe Consulta contendo os dados para efetuar a busca na
     * base de dados.
     * @return Lista de documentos que atenderam a consulta
     */
    public List<DocumentoReal> busca(Consulta consulta) {

        ApplicationContext context = ApplicationContextProvider.getApplicationContext();
        SessionFactory sf = context.getBean(SessionFactory.class);
        Session s = sf.getCurrentSession();


        ArrayList<String> tokensConsulta = (ArrayList) consulta.getConsultaTokenizada(); //tokeniza as palavras da consulta e adiciona no ArrayList
        List<DocumentoReal> docs;  //lista dos documentos retornados da consulta


        String consultaSql; //para cada caso de combinacoes dos parametros a consulta sql eh gerada em um dos metodos privados        
        String sqlOrdenacao; //eh preenchido pelo if que testa qual o tipo de ordenacao


        if (consulta.isRss()) {

            if (consulta.hasAuthor()) {

                if (consulta.getConsulta().isEmpty()) {
                    //COM autor, SEM termo de busca
                    sqlOrdenacao = " a.documento=d.id AND a.nome~##lower('" + consulta.getAutor() + "') GROUP BY d.id, d.titulo, d.obaa_entry, d.resumo, d.data, d.localizacao, d.id_repositorio, d.timestamp, d.palavra_chave, d.id_rep_subfed, d.deleted, d.obaaxml, a.nome ORDER BY timestamp DESC LIMIT " + rssSizeLimit;
                } else {
                    //COM autor, COM termo de busca
                    sqlOrdenacao = "') AND a.documento=d.id AND a.nome~##lower('" + consulta.getAutor() + "') GROUP BY d.id, d.titulo, d.obaa_entry, d.resumo, d.data, d.localizacao, d.id_repositorio, d.timestamp, d.palavra_chave, d.id_rep_subfed, d.deleted, d.obaaxml, a.nome HAVING SUM(r1w.weight)>= 0.1*" + tokensConsulta.size() + " ORDER BY timestamp DESC LIMIT " + rssSizeLimit;
                }
            } else {
                sqlOrdenacao = "') GROUP BY d.id, d.titulo, d.obaa_entry, d.resumo, d.data, d.localizacao, d.id_repositorio, d.timestamp, d.palavras_chave, d.id_rep_subfed, deleted, obaaxml HAVING SUM(r1w.weight)>= 0.1*" + tokensConsulta.size() + " ORDER BY timestamp DESC LIMIT " + rssSizeLimit;
            }

        } else {

            if (consulta.hasAuthor()) {

                if (consulta.getConsulta().isEmpty()) {
                    //COM autor, SEM termo de busca
                    sqlOrdenacao = " a.documento=d.id AND a.nome~##lower('" + consulta.getAutor() + "') GROUP BY d.id, d.obaa_entry, d.id_repositorio, d.timestamp, d.id_rep_subfed, d.deleted, d.obaaxml, d.titulo, d.resumo, d.data, d.localizacao, d.palavras_chave, a.nome ORDER BY (qgram(a.nome, lower('" + consulta.getAutor() + "'))) DESC LIMIT " + consulta.getLimit() + " OFFSET " + consulta.getOffset() + ";";
                } else {
                    //COM autor, COM termo de busca
                    sqlOrdenacao = "') AND a.documento=d.id AND a.nome~##lower('" + consulta.getAutor() + "') GROUP BY d.id, d.obaa_entry, d.id_repositorio, d.timestamp, d.id_rep_subfed, d.deleted, d.obaaxml, d.titulo, d.resumo, d.data, d.localizacao, d.palavras_chave, a.nome ORDER BY (qgram(a.nome, lower('" + consulta.getAutor() + "'))) DESC, SUM (weight) DESC LIMIT " + consulta.getLimit() + " OFFSET " + consulta.getOffset() + ";";
                }
            } else {
                sqlOrdenacao = "') GROUP BY d.id, d.obaa_entry, d.id_repositorio, d.timestamp, d.id_rep_subfed, d.deleted, d.obaaxml, d.titulo, d.resumo, d.data, d.localizacao, d.palavras_chave ORDER BY SUM(weight) DESC LIMIT " + consulta.getLimit() + " OFFSET " + consulta.getOffset() + ";";

            }
        }

        if (consulta.getRepositorios().isEmpty()) {
            if (consulta.getFederacoes().isEmpty()) {
                if (consulta.getRepSubfed().isEmpty()) {
                    consultaSql = buscaConfederacao(tokensConsulta, sqlOrdenacao, consulta.hasAuthor());
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
        StopWatch t = new StopWatch();

        t.start("QueryDocumentos");
        log.debug(consultaSql);
        docs = s.createSQLQuery(consultaSql).addEntity(DocumentoReal.class).list();
        t.stop();


        consultaSql = consultaSql.replace("SELECT d.*", "SELECT COUNT(DISTINCT(d.id))");

        t.start("QuerySize");
        consultaSql = consultaSql.substring(0, consultaSql.indexOf("GROUP"));        
        BigInteger nResult = (BigInteger) s.createSQLQuery(consultaSql).uniqueResult();
        t.stop();
        
        log.debug(t.prettyPrint());

        consulta.setSizeResult(nResult.intValue());

        return docs;
    }

    //confederacao
    protected String buscaConfederacao(ArrayList<String> tokensConsulta, String finalSQL, boolean autor) {

        if (autor) {                        // if is a author request
            if (tokensConsulta.isEmpty()) {   // see if have a query                
                String consultaSql = "SELECT d.* FROM documentos d, autores a WHERE " + finalSQL;
                return consultaSql;

            } else {
                String consultaSql = "SELECT d.* FROM r1weights r1w, documentos d, autores a"
                        + " WHERE r1w.documento_id=d.id "
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
                    + " WHERE r1w.documento_id=d.id "
                    + " AND (r1w.token=";


            if (tokensConsulta.isEmpty()) {
                consultaSql += "'" + finalSQL;
            }

            for (int i = 0; i < tokensConsulta.size(); i++) {
                String token = tokensConsulta.get(i);
                if (i == tokensConsulta.size() - 1) {
                    consultaSql += "'" + token;
                } else {
                    consultaSql += "'" + token + "' OR r1w.token=";
                }

            }
            return consultaSql + finalSQL;
        }
    }

    //replocal
    protected String busca_repLocal(ArrayList<String> tokensConsulta, Consulta c, String finalSQL) {

        String consultaSql = "";
        if (c.hasAuthor()) {                     // if is a author request
            if (tokensConsulta.isEmpty()) {
                consultaSql = "SELECT d.* FROM documentos d, autores a WHERE (";
            } else {
                consultaSql = "SELECT d.* FROM r1weights r1w, documentos d, autores a"
                        + " WHERE r1w.documento_id=d.id "
                        + " AND (";
            }
        } else {
            consultaSql = "SELECT d.* FROM r1weights r1w, documentos d "
                    + " WHERE r1w.documento_id=d.id "
                    + " AND (";
        }

        Iterator<Integer> repositorios = c.getRepositorios().iterator();
        boolean addOr = false;

        while (repositorios.hasNext()) {

            if (!addOr) {
                consultaSql += " d.id_repositorio=" + repositorios.next();

            } else {
                consultaSql += " OR d.id_repositorio=" + repositorios.next();
            }
            addOr = true;
        }

        if (!tokensConsulta.isEmpty()) {
            consultaSql += ") AND (r1w.token=";
        } else {
            consultaSql += ") AND ";
        }

        for (int i = 0; i < tokensConsulta.size(); i++) {
            String token = tokensConsulta.get(i);
            if (i == tokensConsulta.size() - 1) {
                consultaSql += "'" + token;
            } else {
                consultaSql += "'" + token + "' OR r1w.token=";
            }
        }

        return (consultaSql + finalSQL);

    }

    //subfed
    protected String busca_subfed(ArrayList<String> tokensConsulta, Consulta c, String finalSQL) {
        String consultaSql;
        if (c.hasAuthor()) {                    // if is a author request
            if (tokensConsulta.isEmpty()) {
                consultaSql = "SELECT d.* FROM documentos d, autores a, repositorios_subfed rsf WHERE rsf.id=d.id_rep_subfed AND (";
            } else {
                consultaSql = "SELECT d.* FROM r1weights r1w, documentos d, repositorios_subfed rsf, autores a"
                        + " WHERE r1w.documento_id=d.id AND d.id_rep_subfed = rsf.id"
                        + " AND (";
            }
        } else {
            consultaSql = "SELECT d.* FROM r1weights r1w, documentos d, repositorios_subfed rsf"
                    + " WHERE r1w.documento_id=d.id AND d.id_rep_subfed = rsf.id "
                    + " AND (";
        }


        Iterator<Integer> idsSubfed = c.getFederacoes().iterator();
        boolean addOr = false;

        while (idsSubfed.hasNext()) {

            if (!addOr) {
                consultaSql += "rsf.id_subfed=" + idsSubfed.next();

            } else {
                consultaSql += " OR rsf.id_subfed=" + idsSubfed.next();
            }
            addOr = true;
        }


        if (!tokensConsulta.isEmpty()) {
            consultaSql += ") AND (r1w.token=";
        } else {
            consultaSql += ") AND ";
        }

        for (int i = 0; i < tokensConsulta.size(); i++) {
            String token = tokensConsulta.get(i);
            if (i == tokensConsulta.size() - 1) {
                consultaSql += "'" + token;
            } else {
                consultaSql += "'" + token + "' OR r1w.token=";
            }
        }

        return consultaSql + finalSQL;
    }

    //repsubfed
    protected String busca_subRep(ArrayList<String> tokensConsulta, Consulta c, String finalSQL) {

        String consultaSql = "";
        if (c.hasAuthor()) {                     // if is a author request
            if (tokensConsulta.isEmpty()) {
                consultaSql = "SELECT d.* FROM documentos d, autores a WHERE (";
            } else {
                consultaSql = "SELECT d.* FROM r1weights r1w, documentos d, autores a"
                        + " WHERE r1w.documento_id=d.id "
                        + " AND (";
            }
        } else {
            consultaSql = "SELECT d.* FROM r1weights r1w, documentos d"
                    + " WHERE r1w.documento_id=d.id "
                    + " AND (";
        }


        Iterator<Integer> idsRepSubfed = c.getRepSubfed().iterator();
        boolean addOr = false;

        while (idsRepSubfed.hasNext()) {

            if (!addOr) {
                consultaSql += "d.id_rep_subfed=" + idsRepSubfed.next();

            } else {
                consultaSql += " OR d.id_rep_subfed=" + idsRepSubfed.next();
            }
            addOr = true;
        }


        if (!tokensConsulta.isEmpty()) {
            consultaSql += ") AND (r1w.token=";
        } else {
            consultaSql += ") AND ";
        }

        for (int i = 0; i < tokensConsulta.size(); i++) {
            String token = tokensConsulta.get(i);
            if (i == tokensConsulta.size() - 1) {
                consultaSql += "'" + token;
            } else {
                consultaSql += "'" + token + "' OR r1w.token=";
            }
        }

        return consultaSql + finalSQL;
    }

    //replocal + subfed
    protected String busca_repLocal_subfed(ArrayList<String> tokensConsulta, Consulta c, String finalSQL) {

        String consultaSql;
        if (c.hasAuthor()) {                     // if is a author request
            if (tokensConsulta.isEmpty()) {
                consultaSql = "SELECT d.* FROM documentos d, autores a, repositorios_subfed rsf WHERE (";
            } else {
                consultaSql = "SELECT d.* FROM r1weights r1w, documentos d, repositorios_subfed rsf, autores a"
                        + " WHERE ("
                        + " (d.id_rep_subfed = rsf.id AND (";
            }
        } else {
            consultaSql = "SELECT d.* FROM r1weights r1w, documentos d, repositorios_subfed rsf"
                    + " WHERE r1w.documento_id=d.id AND ("
                    + " (";
        }


        Iterator<Integer> idsSubfed = c.getFederacoes().iterator();
        boolean addOr = false;

        while (idsSubfed.hasNext()) {

            if (!addOr) {
                consultaSql += "rsf.id_subfed=" + idsSubfed.next();

            } else {
                consultaSql += " OR rsf.id_subfed=" + idsSubfed.next();
            }
            addOr = true;
        }

        Iterator<Integer> idsRepositorios = c.getRepositorios().iterator();
        addOr = false;

        consultaSql += " AND rsf.id=d.id_rep_subfed OR ";

        while (idsRepositorios.hasNext()) {

            if (!addOr) {
                consultaSql += " d.id_repositorio=" + idsRepositorios.next();

            } else {
                consultaSql += " OR d.id_repositorio=" + idsRepositorios.next();
            }
            addOr = true;
        }


        if (!tokensConsulta.isEmpty()) {
            consultaSql += ")) AND (r1w.token=";
        } else {
            consultaSql += ") AND ";
        }

        for (int i = 0; i < tokensConsulta.size(); i++) {
            String token = tokensConsulta.get(i);
            if (i == tokensConsulta.size() - 1) {
                consultaSql += "'" + token;
            } else {
                consultaSql += "'" + token + "' OR r1w.token=";
            }
        }


        return consultaSql + finalSQL;
    }

    //replocal + repsubfed
    protected String busca_repLocal_subrep(ArrayList<String> tokensConsulta, Consulta c, String finalSQL) {

        String consultaSql;
        if (c.hasAuthor()) {                    // if is an author request
            if (tokensConsulta.isEmpty()) {
                consultaSql = "SELECT d.* FROM documentos d, autores a WHERE (";
            } else {
                consultaSql = "SELECT d.* FROM r1weights r1w, documentos d, autores a"
                        + " WHERE r1w.documento_id=d.id"
                        + " AND (";
            }
        } else {
            consultaSql = "SELECT d.* FROM r1weights r1w, documentos d"
                    + " WHERE r1w.documento_id=d.id"
                    + " AND (";
        }

        Iterator<Integer> idsRepositorios = c.getRepositorios().iterator();
        boolean addOr = false;

        while (idsRepositorios.hasNext()) {

            if (!addOr) {
                consultaSql += " d.id_repositorio=" + idsRepositorios.next();

            } else {
                consultaSql += " OR d.id_repositorio=" + idsRepositorios.next();
            }
            addOr = true;
        }

        Iterator<Integer> idsRepositoriosSubFed = c.getRepSubfed().iterator();

        while (idsRepositoriosSubFed.hasNext()) {
            consultaSql += " OR d.id_rep_subfed=" + idsRepositoriosSubFed.next();
        }

        if (!tokensConsulta.isEmpty()) {
            consultaSql += ") AND (r1w.token=";
        } else {
            consultaSql += ") AND ";
        }

        for (int i = 0; i < tokensConsulta.size(); i++) {
            String token = tokensConsulta.get(i);
            if (i == tokensConsulta.size() - 1) {
                consultaSql += "'" + token;
            } else {
                consultaSql += "'" + token + "' OR r1w.token=";
            }
        }


        return consultaSql + finalSQL;
    }

    //subfed + repsubfed
    protected String busca_subfed_subrep(ArrayList<String> tokensConsulta, Consulta c, String finalSQL) {

        String consultaSql = "";
        if (c.hasAuthor()) {                   // if is a author request
            if (tokensConsulta.isEmpty()) {
                consultaSql = "SELECT d.* FROM documentos d, autores a WHERE (";
            } else {
                consultaSql = "SELECT d.* FROM r1weights r1w, documentos d, repositorios_subfed rsf, autores a"
                        + " WHERE r1w.documento_id=d.id AND d.id_rep_subfed = rsf.id"
                        + " AND (";
            }
        } else {
            consultaSql = "SELECT d.* FROM r1weights r1w, documentos d, repositorios_subfed rsf"
                    + " WHERE r1w.documento_id=d.id AND d.id_rep_subfed = rsf.id"
                    + " AND (";
        }

        Iterator<Integer> idsSubFed = c.getFederacoes().iterator();
        boolean addOr = false;

        while (idsSubFed.hasNext()) {

            if (!addOr) {
                consultaSql += " rsf.id_subfed=" + idsSubFed.next();

            } else {
                consultaSql += " OR rsf.id_subfed=" + idsSubFed.next();
            }
            addOr = true;
        }

        Iterator<Integer> idsRepositoriosSubfed = c.getRepSubfed().iterator();

        while (idsRepositoriosSubfed.hasNext()) {

            consultaSql += " OR rsf.id=" + idsRepositoriosSubfed.next();
        }

        if (!tokensConsulta.isEmpty()) {
            consultaSql += ") AND (r1w.token=";
        } else {
            consultaSql += ") AND ";
        }

        for (int i = 0; i < tokensConsulta.size(); i++) {
            String token = tokensConsulta.get(i);
            if (i == tokensConsulta.size() - 1) {
                consultaSql += "'" + token;
            } else {
                consultaSql += "'" + token + "' OR r1w.token=";
            }
        }

        return consultaSql + finalSQL;
    }

    //replocal + subfed + repsubfed
    protected String busca_repLocal_subfed_subrep(ArrayList<String> tokensConsulta, Consulta c, String finalSQL) {

        String consultaSql = "";
        if (c.hasAuthor()) {                   // if is a author request
            if (tokensConsulta.isEmpty()) {
                consultaSql = "SELECT d.* FROM documentos d, autores a WHERE (";
            } else {
                consultaSql = "SELECT d.* FROM r1weights r1w, documentos d, repositorios_subfed rsf, autores a"
                        + " WHERE r1w.documento_id=d.id AND ("
                        + " (d.id_rep_subfed = rsf.id AND (";
            }
        } else {
            consultaSql = "SELECT d.* FROM r1weights r1w, documentos d, repositorios_subfed rsf"
                    + " WHERE r1w.documento_id=d.id AND ("
                    + " (d.id_rep_subfed = rsf.id AND (";
        }

        Iterator<Integer> idsSubFed = c.getFederacoes().iterator();
        boolean addOr = false;

        while (idsSubFed.hasNext()) {

            if (!addOr) {
                consultaSql += " rsf.id_subfed=" + idsSubFed.next();

            } else {
                consultaSql += " OR rsf.id_subfed=" + idsSubFed.next();
            }
            addOr = true;
        }

        Iterator<Integer> idsRepositorios = c.getRepositorios().iterator();
        addOr = false;

        consultaSql += ")) OR (";

        while (idsRepositorios.hasNext()) {

            if (!addOr) {
                consultaSql += " d.id_repositorio=" + idsRepositorios.next();
            } else {
                consultaSql += " OR d.id_repositorio=" + idsRepositorios.next();
            }
            addOr = true;
        }

        Iterator<Integer> idsRepositoriosSubfed = c.getRepSubfed().iterator();

        while (idsRepositoriosSubfed.hasNext()) {

            consultaSql += " OR rsf.id=" + idsRepositoriosSubfed.next();
        }

        if (!tokensConsulta.isEmpty()) {
            consultaSql += ")) AND (r1w.token=";
        } else {
            consultaSql += ") AND ";
        }

        for (int i = 0; i < tokensConsulta.size(); i++) {
            String token = tokensConsulta.get(i);
            if (i == tokensConsulta.size() - 1) {
                consultaSql += "'" + token;
            } else {
                consultaSql += "'" + token + "' OR r1w.token=";
            }
        }

        return consultaSql + finalSQL;
    }
}