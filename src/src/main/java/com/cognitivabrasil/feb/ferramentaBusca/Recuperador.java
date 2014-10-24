package com.cognitivabrasil.feb.ferramentaBusca;

import com.cognitivabrasil.feb.data.entities.Consulta;
import com.cognitivabrasil.feb.data.entities.Document;
import com.cognitivabrasil.feb.solr.query.QuerySolr;
import com.cognitivabrasil.feb.spring.FebConfig;

import java.util.List;
import org.apache.solr.client.solrj.SolrServerException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Recuperador pelo SOLR
 *
 * @author Daniel Epstein
 */
@Service
public class Recuperador {

    private static final Logger log = LoggerFactory.getLogger(Recuperador.class);
    private static final int rssSizeLimit = 100;

    @Autowired
    private FebConfig config;
    

    /**
     * Envia para o Solr uma consulta
     *
     * @param consulta Consulta efetuada
     * @return Lista de documentos reais que correspondem ao resultado da busca
     * @throws SolrServerException - Não foi possível fazer a pesquisa (server offline?) 
     */
    public List<Document> busca(Consulta consulta) throws SolrServerException {

        List<Document> resultadoConsulta;
        int limit;
        if (consulta.isRss()) {
            limit = rssSizeLimit;
        } else {
            limit = consulta.getLimit();
        }

        QuerySolr q = new QuerySolr(config);
        log.debug(consulta.getConsulta());
        q.pesquisaCompleta(consulta, consulta.getOffset(), limit);
        consulta.setSizeResult(q.getNumDocs());
        resultadoConsulta = q.getDocumentosReais(consulta.getOffset(), limit);
        log.debug("Numero de resultados a serem apresentados: " + resultadoConsulta.size());

        return resultadoConsulta;

    }



}
