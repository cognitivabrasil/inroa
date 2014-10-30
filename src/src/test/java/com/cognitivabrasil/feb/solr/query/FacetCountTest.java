package com.cognitivabrasil.feb.solr.query;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.solr.client.solrj.response.FacetField.Count;
import org.junit.Test;

import com.cognitivabrasil.feb.data.entities.Consulta;


public class FacetCountTest {

    @Test
    public void test() {
        Count c = fakeCount(10L, "fakeName");
        
        FacetCount fc = new FacetCount(c, "obaa.technical.format", new Consulta());
        
        assertThat(fc.getCount(), equalTo(10L));
        assertThat(fc.getName(), equalTo("fakeName"));
    }
    
    @Test
    public void testShortName() {
        Count c = fakeCount(10L, "fakeName");
        
        Consulta cons = mock(Consulta.class);
        
        FacetCount fc = new FacetCount(c, "obaa.technical.format", cons);
        
        verify(cons).isActive("format", "fakeName");
    }
    
    
    @Test
    public void problemaComCamelCase() {
        Count c = fakeCount(10L, "true");
        
        Consulta cons = new Consulta();
        cons.addHasVisual(true);
        
        FacetCount fc = new FacetCount(c, "obaa.accessibility.resourcedescription.primary.hasvisual", cons);
        
        assertThat(fc.isActive(), equalTo(true));
    }
    
    @Test
    public void problemaComCamelCase2() {
        Count c = fakeCount(10L, "true");
        
        Consulta cons = new Consulta();
        
        FacetCount fc = new FacetCount(c, "obaa.accessibility.resourcedescription.primary.hasvisual", cons);
        
        assertThat(fc.isActive(), equalTo(false));
        assertThat(fc.getConsulta().getHasVisual(), hasItem(true));
    }
    
    
    @Test
    public void seEstaAtivoDesativa() {
        Count c = fakeCount(10L, "fakeFormat");
        
        Consulta cons = new Consulta();
        cons.addFormat("fakeFormat");
        
        FacetCount fc = new FacetCount(c, "obaa.technical.format", cons);
        
        assertThat(fc.isActive(), equalTo(true));
        assertThat(fc.getConsulta().getFormat(), not(hasItem("fakeFormat")));
    }
    
    @Test
    public void seNaoEstaAtivoAtiva() {
        Count c = fakeCount(10L, "fakeFormat");
        
        Consulta cons = new Consulta();
        
        FacetCount fc = new FacetCount(c, "obaa.technical.format", cons);
        
        assertThat(fc.isActive(), equalTo(false));
        assertThat(fc.getConsulta().getFormat(), hasItem("fakeFormat"));
    }
    

    private Count fakeCount(Long l, String name) {
        Count c = mock(Count.class);
        when(c.getCount()).thenReturn(l);
        when(c.getName()).thenReturn(name);
        return c;
    }
    
}
