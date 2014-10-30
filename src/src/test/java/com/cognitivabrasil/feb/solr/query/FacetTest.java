package com.cognitivabrasil.feb.solr.query;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


import java.util.ArrayList;

import org.apache.solr.client.solrj.response.FacetField;
import org.junit.Test;

import com.cognitivabrasil.feb.data.entities.Consulta;

public class FacetTest {
    @Test
    public void test() {
        Consulta c = new Consulta();
        c.setConsulta("teste");
        
        FacetField facetField = mock(FacetField.class);
            
        when(facetField.getName()).thenReturn("facetName");
        when(facetField.getValueCount()).thenReturn(10);
        
        when(facetField.getValues()).thenReturn(new ArrayList<>());
        
        Facet f = new Facet(facetField, c);
        
        assertThat(f.getName(), equalTo("facetName"));  
        assertThat(f.getValueCount(), equalTo(10));
    }
    
    @Test
    public void getVarNameTest() {
        Consulta c = new Consulta();
        c.setConsulta("teste");
        
        FacetField facetField = mock(FacetField.class);
            
        when(facetField.getName()).thenReturn("obaa.blabla.blibli");
        
        Facet f = new Facet(facetField, c);

        assertThat(f.getVarName(), equalTo("obaa_blabla_blibli"));
    }
  

}
