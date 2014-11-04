package com.cognitivabrasil.feb.solr.query;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItemInArray;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.apache.solr.client.solrj.SolrQuery;
import org.junit.Test;

import com.cognitivabrasil.feb.ferramentaBusca.ConsultaFeb;

public class CriaQueryTest {
    
    @Test
    public void usaFilterQueryParaTechnicalFormat() {
        ConsultaFeb c = new ConsultaFeb();
        c.addFormat("application/pdf");
        c.setConsulta("consulta");
        
        SolrQuery query = CriaQuery.criaQueryCompleta(c);
        
        assertThat(query.getFilterQueries(), hasItemInArray("{!tag=format}obaa.technical.format:\"application/pdf\""));
    }
    
    @Test
    public void filtrarPorMultiplosFormados() {
        ConsultaFeb c = new ConsultaFeb();
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
        ConsultaFeb c = new ConsultaFeb();
        c.setAutor("Paulo Schreiner");
        c.setConsulta("consulta");
        
        SolrQuery query = CriaQuery.criaQueryCompleta(c);

        
        assertThat(query.getQuery(), equalTo("consulta obaa.lifecycle.entity:(Paulo Schreiner)^100.0"));
    }
    
    @Test
    public void semAutor() {
        ConsultaFeb c = new ConsultaFeb();
        c.setConsulta("consulta");
        
        SolrQuery query = CriaQuery.criaQueryCompleta(c);

        
        assertThat(query.getQuery(), equalTo("consulta"));
    }
    
    @Test
    public void filtrarPorAuditory() {
        ConsultaFeb c = new ConsultaFeb();
        c.addHasAuditory(true);

        c.setConsulta("consulta");
        
        SolrQuery query = CriaQuery.criaQueryCompleta(c);
        
        assertThat(query.getFilterQueries(), 
                hasItemInArray("{!tag=hasauditory}obaa.accessibility.resourcedescription.primary.hasauditory:\"true\""));
    }
    
    @Test
    public void filtrarPorVisual() {
        ConsultaFeb c = new ConsultaFeb();
        c.addHasVisual(true);

        c.setConsulta("consulta");
        
        SolrQuery query = CriaQuery.criaQueryCompleta(c);
        
        assertThat(query.getFilterQueries(), 
                hasItemInArray("{!tag=hasvisual}obaa.accessibility.resourcedescription.primary.hasvisual:\"true\""));
    }
    
    @Test
    public void filtrarPorCostDuplo() {
        ConsultaFeb c = new ConsultaFeb();
        c.addCost(false);
        c.addCost(true);

        c.setConsulta("consulta");
        
        SolrQuery query = CriaQuery.criaQueryCompleta(c);
        
        assertThat(query.getFilterQueries(), 
                hasItemInArray("{!tag=cost}obaa.rights.cost:\"false\" OR obaa.rights.cost:\"true\""));
    }
    
    @Test
    public void filtrarPorDifficulty() {
        ConsultaFeb c = new ConsultaFeb();
        c.add("difficulty", "hard");

        c.setConsulta("consulta");
        
        SolrQuery query = CriaQuery.criaQueryCompleta(c);
        
        assertThat(query.getFilterQueries(), 
                hasItemInArray("{!tag=difficulty}obaa.educational.difficulty:\"hard\""));
    }
    
    @Test
    public void filtroPorFederacoesRepositorios() {
        ConsultaFeb c = new ConsultaFeb();
        c.setConsulta("consulta");
        
        c.setFederacoes(Arrays.asList(2, 3));
        c.setRepositorios(Arrays.asList(1));
        c.setRepSubfed(Arrays.asList(4,5));
        
        SolrQuery query = CriaQuery.criaQueryCompleta(c);
        
        System.out.println(query.getFilterQueries());
        
        assertThat(query.getFilterQueries(), hasItemInArray("{!tag=federacoes}obaa.federacao:(2 3)"));
        assertThat(query.getFilterQueries(), hasItemInArray("{!tag=repositorios}obaa.repositorio:(1)"));
        assertThat(query.getFilterQueries(), hasItemInArray("{!tag=repsubfed}obaa.subFederacao:(4 5)"));

    }
}
