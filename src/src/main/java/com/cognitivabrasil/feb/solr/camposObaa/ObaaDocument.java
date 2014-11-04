package com.cognitivabrasil.feb.solr.camposObaa;

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
}
