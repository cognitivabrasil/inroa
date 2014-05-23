/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cognitivabrasil.feb.solr;

import cognitivabrasil.obaa.OBAA;
import org.junit.Test;

/**
 *
 * @author Daniel Epstein
 */
public class SolrTest {
    
    @Test
    public void MemoryLeakTest ()
    {
        for (int i = 0 ; i < 1000000; i++)
        {
            
            OBAA o = new OBAA();
            
        }
    }
}
