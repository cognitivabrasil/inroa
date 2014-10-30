package com.cognitivabrasil.feb.data.entities;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

import org.junit.Test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Marcos Freitas Nunes <marcos@cognitivabrasil.com.br>
 */
public class ConsultaTest {

    @Test
    public void testIsEmpty() {
        Consulta c = new Consulta();
        assertThat(c.isEmpty(), equalTo(true));
    }

    @Test
    public void testEmpty2() {
        Consulta c = new Consulta();
        c.setLimit(10);
        c.setOffset(15);
        c.setRss(true);
        c.setSizeResult(1500);
        c.getFederacoes().add(1);
        c.getRepositorios().add(1);
        c.getRepSubfed().add(1);
        assertThat(c.isEmpty(), equalTo(true));
    }

    @Test
    public void testNotEmpty() {
        Consulta c = new Consulta();
        c.setSize("2");
        assertThat(c.isEmpty(), equalTo(true));
    }


    @Test
    public void testNotEmpty3() {
        Consulta c = new Consulta();
        c.setAutor("autor");
        assertThat(c.isEmpty(), equalTo(false));
    }

    @Test
    public void testNotEmpty4() {
        Consulta c = new Consulta();
        c.setConsulta("consulta");
        assertThat(c.isEmpty(), equalTo(false));
    }

    @Test
    public void testNotEmpty5() {
        Consulta c = new Consulta();
        c.addCost(true);
        assertThat(c.isEmpty(), equalTo(true));
    }

    @Test
    public void testNotEmpty6() {
        Consulta c = new Consulta();
        c.addFacetFilter("difficulty", "dificil");
        assertThat(c.isEmpty(), equalTo(true));
    }

    @Test
    public void testNotEmpty7() {
        Consulta c = new Consulta();
        c.addFormat(".pdf");
        assertThat(c.isEmpty(), equalTo(true));
    }

    @Test
    public void testNotEmpty8() {
        Consulta c = new Consulta();
        c.addHasAuditory(true);
        assertThat(c.isEmpty(), equalTo(true));
    }

    @Test
    public void testNotEmpty9() {
        Consulta c = new Consulta();
        c.addHasText(Boolean.FALSE);
        assertThat(c.isEmpty(), equalTo(true));
    }

    @Test
    public void testNotEmpty10() {
        Consulta c = new Consulta();
        c.addHasVisual(true);
        assertThat(c.isEmpty(), equalTo(true));
    }

    @Test
    public void testNotEmpty11() {
        Consulta c = new Consulta();
        c.setIdioma("pt-BR");
        assertThat(c.isEmpty(), equalTo(true));
    }

    @Test
    public void testNotEmpty12() {
        Consulta c = new Consulta();
        c.setAdultAge(Boolean.TRUE);
        assertThat(c.isEmpty(), equalTo(true));
    }
    
      
    @Test
    public void testGetUrlEncodedSimple() {
        Consulta c = new Consulta();
        c.setConsulta("matemática");
        
        assertThat(c.getUrlEncoded(), containsString("consulta=matem"));
    }
    
    @Test
    public void testHasAuditory() {
        Consulta c = new Consulta();
        c.setConsulta("bla");
        c.addHasAuditory(true);
        
        assertThat(c.getUrlEncoded(), containsString("hasAuditory=true"));
    }
    
    @Test
    public void testHasVisual() {
        Consulta c = new Consulta();
        c.setConsulta("bla");
        c.addHasVisual(false);
        
        assertThat(c.getUrlEncoded(), containsString("hasVisual=false"));
    }
    
    @Test
    public void testCost() {
        Consulta c = new Consulta();
        c.setConsulta("bla");
        c.addCost(true);
        
        assertThat(c.getUrlEncoded(), containsString("cost=true"));
    }
    
    @Test
    public void testAgeRange() {
        Consulta c = new Consulta();
        c.setConsulta("bla");
        c.addAgeRangeInt(12);
        
        assertThat(c.getUrlEncoded(), containsString("ageRangeInt=12"));
    }
    
    @Test
    public void testHasAuditoryNotSet() {
        Consulta c = new Consulta();
        c.setConsulta("bla");
        
        assertThat(c.getUrlEncoded(), not(containsString("hasAuditory")));
    }
    
    @Test
    public void testIsActive() {
        Consulta c = new Consulta();
        c.setConsulta("bla");
        
        c.addCost(true);
        c.addCost(false);
        c.addFormat("bogus");
        
        assertTrue(c.isActive("cost", "true"));
        assertTrue(c.isActive("cost", "false"));
        assertTrue(c.isActive("format", "bogus"));

        
        assertFalse(c.isActive("hasvisual", "true"));
        assertFalse(c.isActive("format", "bogus2"));
    }
    
    @Test
    public void removeFacetFilterTest() {
        Consulta c = new Consulta();
        c.setConsulta("bla");
        
        c.addCost(true);
        c.addCost(false);
        c.addFormat("bogus");
        
        c.removeFacetFilter("cost", "false");
        c.removeFacetFilter("format", "bogus");
        
        assertTrue(c.isActive("cost", "true"));
        
        assertFalse(c.isActive("cost", "false"));
        assertFalse(c.isActive("format", "bogus"));
    }
    
    @Test
    public void copyWorks() {
        Consulta c = new Consulta();
        c.setConsulta("bla");
        
        c.addCost(true);
        c.addCost(false);
        c.addFormat("bogus");
        
        Consulta c2 = new Consulta(c);
        
        c2.removeFacetFilter("format", "bogus");
        c2.addFacetFilter("format", "meuFormat");
        
        assertTrue(c.isActive("format", "bogus"));
        assertFalse(c.isActive("format", "meuFormat"));
    }
    
    @Test
    public void testAddRepository() {
        Consulta c = new Consulta();
        c.setConsulta("bla");

        c.add("repositorios", 1);
        
        assertThat(c.getUrlEncoded(), containsString("repositorios=1"));
    }
}
