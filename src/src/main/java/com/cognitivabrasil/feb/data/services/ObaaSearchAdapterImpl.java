package com.cognitivabrasil.feb.data.services;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.cognitivabrasil.feb.data.entities.Document;
import com.cognitivabrasil.feb.data.entities.Repositorio;
import com.cognitivabrasil.feb.solr.ObaaSearchAdapter;
import com.cognitivabrasil.feb.solr.camposObaa.ObaaDocument;

@Service
public class ObaaSearchAdapterImpl implements ObaaSearchAdapter {
    
    @Override
    public ObaaDocument createObaaDocument() {
        return new Document();
    }


    @Override
    public void addCustomFields(ObaaDocument oDoc, Map<String, Object> map) {
        Document doc = (Document) oDoc;
        
        Repositorio rep = new Repositorio();
        rep.setName((String)map.get("obaa.repName"));
        doc.setRepositorio(rep);

        doc.setId((Integer)map.get("obaa.idBaseDados"));                
    }

}
