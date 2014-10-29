package com.cognitivabrasil.feb.solr;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.solr.client.solrj.response.SpellCheckResponse;
import org.apache.solr.client.solrj.response.SpellCheckResponse.Suggestion;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

public class SolrSpellCheckBuilder {
    private SpellCheckResponse spellCheck;
    private List<Suggestion> suggestions;

    public SolrSpellCheckBuilder() {
        spellCheck = mock(SpellCheckResponse.class);
        
        suggestions = new ArrayList<>();
        when(spellCheck.getSuggestions()).thenReturn(suggestions);

    }

    public SolrSpellCheckBuilder alternative(String... strings) {
        Suggestion s1 = mock(Suggestion.class);
        when(s1.getAlternatives()).thenReturn(Arrays.asList(strings));
        
        suggestions.add(s1);
        
   
        return this;
    }
    
    public SpellCheckResponse build() {
        when(spellCheck.isCorrectlySpelled()).thenReturn(suggestions.isEmpty());

        
        return spellCheck;
    }
    
    public static SolrSpellCheckBuilder solrSpellCheckBuilder() {
        return new SolrSpellCheckBuilder();
    }

}
