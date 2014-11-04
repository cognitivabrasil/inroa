package com.cognitivabrasil.feb.solr;

import java.util.Map;

import com.cognitivabrasil.feb.solr.camposObaa.ObaaDocument;

public interface ObaaSearchAdapter {

    void addCustomFields(ObaaDocument doc, Map<String, Object> map);

    ObaaDocument createObaaDocument();


}
