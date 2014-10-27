package com.cognitivabrasil.feb.ferramentaBusca;

import java.util.List;

import org.apache.solr.client.solrj.response.SpellCheckResponse;

import com.cognitivabrasil.feb.data.entities.Consulta;
import com.cognitivabrasil.feb.data.entities.Document;
import com.cognitivabrasil.feb.solr.query.Facet;

/**
 * Representa os resultados de uma busca.
 * 
 * @author Paulo Schreiner
 */
public class ResultadoBusca {
    private List<Document> documents;
    private List<Facet> facets;
    private SpellCheckResponse spellCheckResponse;
    private Consulta consulta;
    

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
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
    
}
