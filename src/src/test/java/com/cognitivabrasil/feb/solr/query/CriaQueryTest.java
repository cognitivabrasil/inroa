package com.cognitivabrasil.feb.solr.query;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItemInArray;
import static org.junit.Assert.assertThat;

import org.apache.solr.client.solrj.SolrQuery;
import org.junit.Test;

import com.cognitivabrasil.feb.data.entities.Consulta;

public class CriaQueryTest {
    
    @Test
    public void usaFilterQueryParaTechnicalFormat() {
        Consulta c = new Consulta();
        c.setFormat("application/pdf");
        c.setConsulta("consulta");
        
        SolrQuery query = CriaQuery.criaQueryCompleta(c);
        
        assertThat(query.getFilterQueries(), hasItemInArray("obaa.technical.format:\"application/pdf\""));
    }
    
    @Test
    public void comAutor() {
        Consulta c = new Consulta();
        c.setAutor("Paulo Schreiner");
        c.setConsulta("consulta");
        
        SolrQuery query = CriaQuery.criaQueryCompleta(c);

        
        assertThat(query.getQuery(), equalTo("consulta obaa.lifecycle.entity:(Paulo Schreiner)^100.0"));
    }
    
    @Test
    public void semAutor() {
        Consulta c = new Consulta();
        c.setConsulta("consulta");
        
        SolrQuery query = CriaQuery.criaQueryCompleta(c);

        
        assertThat(query.getQuery(), equalTo("consulta"));
    }
}
