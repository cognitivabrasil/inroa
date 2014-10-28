package com.cognitivabrasil.feb.ferramentaBusca;

import com.cognitivabrasil.feb.data.entities.Consulta;
import com.cognitivabrasil.feb.data.entities.Document;
import com.cognitivabrasil.feb.solr.query.Facet;
import com.cognitivabrasil.feb.solr.query.QuerySolr;
import com.cognitivabrasil.feb.spring.FebConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.SpellCheckResponse;
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
    public ResultadoBusca busca(Consulta consulta) throws SolrServerException {

        List<Document> resultadoConsulta;
        int limit;
        if (consulta.isRss()) {
            limit = rssSizeLimit;
        } else {
            limit = consulta.getLimit();
        }

        QuerySolr q = new QuerySolr(config);
        log.debug(consulta.getConsulta());
        QueryResponse response = q.pesquisaCompleta(consulta, consulta.getOffset(), limit);
        consulta.setSizeResult(q.getNumDocs());
        resultadoConsulta = q.getDocumentosReais(consulta.getOffset(), limit);
        log.debug("Numero de resultados a serem apresentados: " + resultadoConsulta.size());
        
        ResultadoBusca r = new ResultadoBusca();
        r.setDocuments(resultadoConsulta);
        
        List<FacetField> facets = response.getFacetFields();
        
        List<Facet> facetsProcessed = facets.stream().map(f -> new Facet(f, consulta)).collect(Collectors.toList());
        
        r.setSpellCheckResponse(response.getSpellCheckResponse());
           
        r.setFacets(facetsProcessed);
        
        r.setConsulta(consulta);
        
        r.setResultSize(q.getNumDocs());

        return r;

    }
    
    public List<String> autosuggest(String partial) {
        log.debug("Autosuggest for {}", partial);
        
        List<String> auto = new ArrayList<>();
        
        QuerySolr q = new QuerySolr(config);
        QueryResponse r = q.autosuggest(partial);
        
        SpellCheckResponse spellCheckResponse = r.getSpellCheckResponse();
        if (spellCheckResponse != null && (!spellCheckResponse.isCorrectlySpelled())) {
            for (org.apache.solr.client.solrj.response.SpellCheckResponse.Suggestion suggestion : r.getSpellCheckResponse().getSuggestions()) {
                log.debug("Got suggestions: {}", suggestion.getAlternatives());
                auto.addAll(suggestion.getAlternatives());
            }
        }

        
        return auto;
    }



}
