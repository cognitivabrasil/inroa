package com.cognitivabrasil.feb.solr;

import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

public class SolrDocumentBuilder {
    SolrDocumentList documentList;
    SolrDocument doc;
    SolrDocumentListBuilder documentListBuilder;

    public SolrDocumentBuilder(SolrDocumentListBuilder builder) {
        documentList = builder.getList();
        documentListBuilder = builder;
        
        doc = new SolrDocument();
        documentList.add(doc);
    }

    public SolrDocumentBuilder field(String string, Object value) {
        doc.addField(string, value);
        return this;
    }
    
    public SolrDocumentList build() {
        return documentListBuilder.build();
    }

    public SolrDocumentBuilder add() {
        return documentListBuilder.add();
    }

}
