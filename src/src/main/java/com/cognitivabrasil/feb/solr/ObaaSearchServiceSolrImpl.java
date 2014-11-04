package com.cognitivabrasil.feb.solr;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.SpellCheckResponse;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cognitivabrasil.obaa.OBAA;
import cognitivabrasil.obaa.General.General;
import cognitivabrasil.obaa.Technical.Technical;

import com.cognitivabrasil.feb.data.entities.Consulta;
import com.cognitivabrasil.feb.data.entities.Document;
import com.cognitivabrasil.feb.ferramentaBusca.ResultadoBusca;
import com.cognitivabrasil.feb.services.ObaaSearchService;
import com.cognitivabrasil.feb.solr.camposObaa.ObaaDocument;
import com.cognitivabrasil.feb.solr.query.Facet;
import com.cognitivabrasil.feb.solr.query.QuerySolr;
import com.cognitivabrasil.feb.spring.FebConfig;

/**
 * Recuperador pelo SOLR
 *
 * @author Daniel Epstein
 * @author Paulo Schreiner
 */
@Service
public class ObaaSearchServiceSolrImpl implements ObaaSearchService {

    private static final Logger log = LoggerFactory.getLogger(ObaaSearchServiceSolrImpl.class);
    private static final int rssSizeLimit = 100;
    
    @Autowired
    private QuerySolr q;
    
    private ObaaSearchAdapter obaaSearchAdapter;
    
    @Autowired
    public ObaaSearchServiceSolrImpl(ObaaSearchAdapter obaaSearchAdapter) {
        this.obaaSearchAdapter = obaaSearchAdapter;
    }

    @Override
    public ResultadoBusca busca(Consulta consulta) throws SolrServerException {

        List<ObaaDocument> resultadoConsulta;
        int limit;
        if (consulta.isRss()) {
            limit = rssSizeLimit;
        } else {
            limit = consulta.getLimit();
        }

        log.debug(consulta.getConsulta());
        QueryResponse response = q.pesquisaCompleta(consulta, consulta.getOffset(), limit);
        consulta.setSizeResult(QuerySolr.getNumDocs(response));
        resultadoConsulta = getDocumentosReais(response, limit);
        log.debug("Numero de resultados a serem apresentados: " + resultadoConsulta.size());
        
        ResultadoBusca r = new ResultadoBusca();
        r.setDocuments(resultadoConsulta);
        
        List<FacetField> facets = response.getFacetFields();
        
        List<Facet> facetsProcessed = facets.stream().map(f -> new Facet(f, consulta)).collect(Collectors.toList());
        
        r.setSpellCheckResponse(response.getSpellCheckResponse());
           
        r.setFacets(facetsProcessed);
        
        r.setConsulta(consulta);
        
        r.setResultSize(QuerySolr.getNumDocs(response));

        return r;

    }
    
    @Override
    public List<String> autosuggest(String partial) {
        log.debug("Autosuggest for {}", partial);
        
        List<String> auto = new ArrayList<>();
        
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


    /**
     * Transforma a resposta fornecida pelo SOLR em Documentos Reais, com hit higlighting e snippets.
     *
     * @param queryResponse resultado da busca a ser transformado em documentos reais
     * @param numberOfDocs Numero de resultados da busca a ser transformado em Document
     *
     * @return Lista de DocumentosReais construida a partir da busca.
     */
    public List<ObaaDocument> getDocumentosReais(QueryResponse queryResponse, int numberOfDocs) {

        List<ObaaDocument> retorno = new ArrayList<>();
        SolrDocumentList list = queryResponse.getResults();

        for (int i = 0; i < numberOfDocs && i < list.size(); i++) {

            //O valor de numDoc eh o documento a ser apresentado
            int numDoc = i;

            ObaaDocument doc = obaaSearchAdapter.createObaaDocument();
            OBAA obaa = new OBAA();
            doc.setMetadata(obaa);

            obaa.setGeneral(new General());

            obaa.setTechnical(new Technical());

            Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
            if (list.get(numDoc).getFieldValues("obaa.general.title") != null) {
                // id is the uniqueKey field
                String id = (String) list.get(numDoc).getFieldValue("obaa.general.identifier.entry");

                if (highlighting.get(id) != null && highlighting.get(id).get("obaa.general.title") != null) {
                    for (String snippet : highlighting.get(id).get("obaa.general.title")) {
                        obaa.getGeneral().addTitle(snippet);
                    }
                }
            }

            if (list.get(numDoc).getFieldValues("obaa.general.description") != null) {
                String id = (String) list.get(numDoc).getFieldValue("obaa.general.identifier.entry"); 

                if (highlighting.get(id) != null && highlighting.get(id).get("obaa.general.description") != null) {
                    for (String snippet : highlighting.get(id).get("obaa.general.description")) {
                        obaa.getGeneral().addDescription(snippet.replaceFirst("^\\.", ""));
                    }
                }
            }

            if (list.get(numDoc).getFieldValues("obaa.general.keyword") != null) {
                String id = (String) list.get(numDoc).getFieldValue("obaa.general.identifier.entry"); // id is the
                // uniqueKey field

                if (highlighting.get(id) != null && highlighting.get(id).get("obaa.general.keyword") != null) {
                    for (String snippet : highlighting.get(id).get("obaa.general.keyword")) {
                        obaa.getGeneral().addKeyword(snippet);
                    }
                }

            }

            if (list.get(numDoc).getFieldValues("obaa.technical.location") != null) {
                for (Object o : list.get(numDoc).getFieldValues("obaa.technical.location")) {
                    log.trace((String) o);
                    obaa.getTechnical().addLocation((String) o);
                }
            }


            /* adicionar campos específicos deste projeto */
            obaaSearchAdapter.addCustomFields(doc, list.get(numDoc).getFieldValueMap());

            retorno.add(doc);
        }

        return retorno;
    }

}
