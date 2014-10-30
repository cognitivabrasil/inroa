package com.cognitivabrasil.feb.solr;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.SpellCheckResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;


public class SolrQueryResponseBuilder {
    private QueryResponse queryResponse;
    private Map<String, Map<String, List<String>>> highlight;
    private SolrDocumentList solrDocumentList;
    
    public SolrQueryResponseBuilder() {
        queryResponse = mock(QueryResponse.class);
    }
    
    public SolrQueryResponseBuilder results(SolrDocumentList solrDocumentList) {
        this.solrDocumentList = solrDocumentList;
        
        when(queryResponse.getResults()).thenReturn(solrDocumentList);
        
        return this;
    }
    
    public SolrQueryResponseBuilder spellCheckResponse(SpellCheckResponse spellCheck) {
        when(queryResponse.getSpellCheckResponse()).thenReturn(spellCheck);
        
        return this;
    }

    
    public QueryResponse build() {
        return queryResponse;
    }
    
    public static SolrQueryResponseBuilder solrQueryResponseBuilder() {
        return new SolrQueryResponseBuilder();
    }

    /**
     * Adds highlighting snippets based on the documents.
     * 
     * Will just return the verbatim fields. This HAS to be called after the documents were added.
     * @return 
     */
    public SolrQueryResponseBuilder addHighlighting() {
        highlight = new HashMap<>();
        
        for (SolrDocument d : solrDocumentList.stream().collect(Collectors.toList())) {
            String id = d.getFieldValue("obaa.general.identifier.entry").toString();
            if (!highlight.containsKey(id)) {
                highlight.put(id, new HashMap<>());
            }
            for (String fieldName : d.getFieldNames()) {
                if (!highlight.get(id).containsKey(fieldName)) {
                    highlight.get(id).put(fieldName, new ArrayList<>());
                }
                for (Object o : d.getFieldValues(fieldName)) {
                    highlight.get(id).get(fieldName).add(o.toString());
                }
            }
        }
        
        when(queryResponse.getHighlighting()).thenReturn(highlight);
        
        return this;
    }
}
