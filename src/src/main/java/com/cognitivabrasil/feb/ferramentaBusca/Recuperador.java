package com.cognitivabrasil.feb.ferramentaBusca;

import com.cognitivabrasil.feb.data.entities.Consulta;
import com.cognitivabrasil.feb.data.entities.Document;
import com.cognitivabrasil.feb.solr.query.QuerySolr;

import java.util.List;
import org.apache.log4j.Logger;

/**
 * Recuperador pelo SOLR
 *
 * @author Daniel Epstein
 */
public class Recuperador {

    private static final Logger log = Logger.getLogger(Recuperador.class);
    private static final int rssSizeLimit = 100;

    public Recuperador() {
    }

    /**
     * Envia para o Solr uma consulta
     *
     * @param consulta Consulta efetuada
     * @return Lista de documentos reais que correspondem ao resultado da busca
     */
    public List<Document> busca(Consulta consulta) {

        List<Document> resultadoConsulta;
        int limit;
        if (consulta.isRss()) {
            limit = rssSizeLimit;
        } else {
            limit = consulta.getLimit();
        }

        QuerySolr q = new QuerySolr();
        log.debug(consulta.getConsulta());
        q.pesquisaSimples(consulta.getConsulta(), consulta.getOffset(), limit);
        consulta.setSizeResult(q.getNumDocs());
        resultadoConsulta = q.getDocumentosReais(consulta.getOffset(), limit);
        log.debug("Numero de resultados a serem apresentados: " + resultadoConsulta.size());

        return resultadoConsulta;

    }

    /**
     * Busca avancada que sera enviada para o SOLR
     *
     * @param consulta Consulta realizada
     * @return Lista de documentos reais que correspondem ao resultado da busca
     */
    public List<Document> buscaAvancada(Consulta consulta) {

        List<Document> resultadoConsulta;

        int limit;
        if (consulta.isRss()) {
            limit = rssSizeLimit;
        } else {
            limit = consulta.getLimit();
        }

        QuerySolr q = new QuerySolr();
        log.debug(consulta.getConsulta());
        q.pesquisaCompleta(consulta, consulta.getOffset(), limit);
        consulta.setSizeResult(q.getNumDocs());
        resultadoConsulta = q.getDocumentosReais(consulta.getOffset(), limit);
        log.debug("Numero de resultados a serem apresentados: " + resultadoConsulta.size());

        return resultadoConsulta;

    }
}
