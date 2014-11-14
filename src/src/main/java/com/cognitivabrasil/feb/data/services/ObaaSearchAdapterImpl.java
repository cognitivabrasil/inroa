package com.cognitivabrasil.feb.data.services;

import org.springframework.stereotype.Service;

import com.cognitivabrasil.feb.data.entities.Document;
import com.cognitivabrasil.obaa.search.ObaaDocument;
import com.cognitivabrasil.obaa.search.ObaaSearchAdapter;

@Service
public class ObaaSearchAdapterImpl implements ObaaSearchAdapter {
    
    @Override
    public ObaaDocument createObaaDocument() {
        return new Document();
    }

}
