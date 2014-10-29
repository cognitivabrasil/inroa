package com.cognitivabrasil.feb.solr;

import org.apache.solr.common.SolrDocumentList;

public class SolrDocumentListBuilder {
    private SolrDocumentList list;
    
    public SolrDocumentListBuilder() {
        list = new SolrDocumentList();
    }
    
    public static SolrDocumentListBuilder solrDocumentListBuilder() {
        return new SolrDocumentListBuilder();
    }
    
    public SolrDocumentBuilder add() {
        list.setNumFound(list.getNumFound() + 1);
        
        return new SolrDocumentBuilder(this);
    }
    
    public SolrDocumentList build() {
        return list;
    }

    public SolrDocumentList getList() {
        return list;
    }
    
}
