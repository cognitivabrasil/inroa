package com.cognitivabrasil.feb.solr.camposObaa;

import java.util.HashMap;
import java.util.Map;

import cognitivabrasil.obaa.OBAA;

/**
 * Generic object type that contains a OBAA document.
 * 
 * The 
 * 
 * @author Paulo Schreiner
 *
 */
public interface ObaaDocument {
    OBAA getMetadata();

    void setMetadata(OBAA obaa);

    String getObaaEntry();

    int getId();

    /**
     * Override to add extra indexed fields do the search app. Default implementation is
     * to return a blank Map, so you only have to implement it if you actually have extra non-standard
     * fields.
     * @return extra fields to be indexed by solr. The key is the name of the field, and
     * the value is the value of the field. 
     */
    default Map<String, Object> getExtraSearchFields() {
        return new HashMap<>();
    }
}
