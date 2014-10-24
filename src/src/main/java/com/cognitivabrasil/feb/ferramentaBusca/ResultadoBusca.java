package com.cognitivabrasil.feb.ferramentaBusca;

import java.util.List;

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
    
}
