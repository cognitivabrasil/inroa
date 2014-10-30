package com.cognitivabrasil.feb.solr.query;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItemInArray;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.apache.solr.client.solrj.SolrQuery;
import org.junit.Test;

import com.cognitivabrasil.feb.data.entities.Consulta;

public class CriaQueryTest {
    
    @Test
    public void usaFilterQueryParaTechnicalFormat() {
        Consulta c = new Consulta();
        c.addFormat("application/pdf");
        c.setConsulta("consulta");
        
        SolrQuery query = CriaQuery.criaQueryCompleta(c);
        
        assertThat(query.getFilterQueries(), hasItemInArray("{!tag=format}obaa.technical.format:\"application/pdf\""));
    }
    
    @Test
    public void filtrarPorMultiplosFormados() {
        Consulta c = new Consulta();
        c.addFormat("application/pdf");
        c.addFormat("application/zip");

        c.setConsulta("consulta");
        
        SolrQuery query = CriaQuery.criaQueryCompleta(c);
        
        assertThat(query.getFilterQueries(), 
                hasItemInArray("{!tag=format}obaa.technical.format:\"application/pdf\" OR " 
                        + "obaa.technical.format:\"application/zip\""));
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
    
    @Test
    public void filtrarPorAuditory() {
        Consulta c = new Consulta();
        c.addHasAuditory(true);

        c.setConsulta("consulta");
        
        SolrQuery query = CriaQuery.criaQueryCompleta(c);
        
        assertThat(query.getFilterQueries(), 
                hasItemInArray("{!tag=hasauditory}obaa.accessibility.resourcedescription.primary.hasauditory:\"true\""));
    }
    
    @Test
    public void filtrarPorVisual() {
        Consulta c = new Consulta();
        c.addHasVisual(true);

        c.setConsulta("consulta");
        
        SolrQuery query = CriaQuery.criaQueryCompleta(c);
        
        assertThat(query.getFilterQueries(), 
                hasItemInArray("{!tag=hasvisual}obaa.accessibility.resourcedescription.primary.hasvisual:\"true\""));
    }
    
    @Test
    public void filtrarPorCostDuplo() {
        Consulta c = new Consulta();
        c.addCost(false);
        c.addCost(true);

        c.setConsulta("consulta");
        
        SolrQuery query = CriaQuery.criaQueryCompleta(c);
        
        assertThat(query.getFilterQueries(), 
                hasItemInArray("{!tag=cost}obaa.rights.cost:\"false\" OR obaa.rights.cost:\"true\""));
    }
    
    @Test
    public void filtrarPorDifficulty() {
        Consulta c = new Consulta();
        c.add("difficulty", "hard");

        c.setConsulta("consulta");
        
        SolrQuery query = CriaQuery.criaQueryCompleta(c);
        
        assertThat(query.getFilterQueries(), 
                hasItemInArray("{!tag=difficulty}obaa.educational.difficulty:\"hard\""));
    }
    
    @Test
    public void filtroPorFederacoesRepositorios() {
        Consulta c = new Consulta();
        c.setConsulta("consulta");
        
        c.setFederacoes(Arrays.asList(2, 3));
        c.setRepositorios(Arrays.asList(1));
        c.setRepSubfed(Arrays.asList(4,5));
        
        SolrQuery query = CriaQuery.criaQueryCompleta(c);
        
        assertThat(query.getFilterQueries(), hasItemInArray("obaa.federacao:(2 3)"));
        assertThat(query.getFilterQueries(), hasItemInArray("obaa.repositorio:(1)"));
        assertThat(query.getFilterQueries(), hasItemInArray("obaa.subFederacao:(4 5)"));

    }
}
