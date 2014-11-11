package com.cognitivabrasil.feb.solr.query;

import java.util.List;

import org.apache.solr.client.solrj.response.SpellCheckResponse;

import com.cognitivabrasil.feb.ferramentaBusca.Suggestion;
import com.cognitivabrasil.feb.solr.camposObaa.ObaaDocument;

/**
 * Representa os resultados de uma busca.
 * 
 * @author Paulo Schreiner
 */
public class ResultadoBusca<T extends ObaaDocument> {
    private List<T> documents;
    private List<Facet> facets;
    private SpellCheckResponse spellCheckResponse;
    private Consulta consulta;
    private int resultSize;
    

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta2) {
        this.consulta = consulta2;
    }

    public List<T> getDocuments() {
        return documents;
    }

    public void setDocuments(List<T> documents) {
        this.documents = documents;
    }

    public void setFacets(List<Facet> facetsProcessed) {
       this.facets = facetsProcessed;
    }
    
    public List<Facet> getFacets() {
        return facets;
    }

    public void setSpellCheckResponse(SpellCheckResponse spellCheckResponse) {
        this.spellCheckResponse = spellCheckResponse;     
    }
    
    public Suggestion getSuggestion() {
        return new Suggestion(spellCheckResponse, consulta);
    }

    public void setResultSize(int numDocs) {
        this.resultSize = numDocs;
    }
    
    public int getResultSize() {
        return resultSize;
    }
    
}
