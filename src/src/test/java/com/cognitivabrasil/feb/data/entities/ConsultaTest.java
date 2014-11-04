package com.cognitivabrasil.feb.data.entities;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

import org.junit.Test;

import com.cognitivabrasil.feb.ferramentaBusca.ConsultaFeb;

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
        ConsultaFeb c = new ConsultaFeb();
        assertThat(c.isEmpty(), equalTo(true));
    }

    @Test
    public void testEmpty2() {
        ConsultaFeb c = new ConsultaFeb();
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
        ConsultaFeb c = new ConsultaFeb();
        c.setSize("2");
        assertThat(c.isEmpty(), equalTo(true));
    }


    @Test
    public void testNotEmpty3() {
        ConsultaFeb c = new ConsultaFeb();
        c.setAutor("autor");
        assertThat(c.isEmpty(), equalTo(false));
    }

    @Test
    public void testNotEmpty4() {
        ConsultaFeb c = new ConsultaFeb();
        c.setConsulta("consulta");
        assertThat(c.isEmpty(), equalTo(false));
    }

    @Test
    public void testNotEmpty5() {
        ConsultaFeb c = new ConsultaFeb();
        c.addCost(true);
        assertThat(c.isEmpty(), equalTo(true));
    }

    @Test
    public void testNotEmpty6() {
        ConsultaFeb c = new ConsultaFeb();
        c.addFacetFilter("difficulty", "dificil");
        assertThat(c.isEmpty(), equalTo(true));
    }

    @Test
    public void testNotEmpty7() {
        ConsultaFeb c = new ConsultaFeb();
        c.addFormat(".pdf");
        assertThat(c.isEmpty(), equalTo(true));
    }

    @Test
    public void testNotEmpty8() {
        ConsultaFeb c = new ConsultaFeb();
        c.addHasAuditory(true);
        assertThat(c.isEmpty(), equalTo(true));
    }

    @Test
    public void testNotEmpty9() {
        ConsultaFeb c = new ConsultaFeb();
        c.addHasText(Boolean.FALSE);
        assertThat(c.isEmpty(), equalTo(true));
    }

    @Test
    public void testNotEmpty10() {
        ConsultaFeb c = new ConsultaFeb();
        c.addHasVisual(true);
        assertThat(c.isEmpty(), equalTo(true));
    }

    @Test
    public void testNotEmpty11() {
        ConsultaFeb c = new ConsultaFeb();
        c.setIdioma("pt-BR");
        assertThat(c.isEmpty(), equalTo(true));
    }

    @Test
    public void testNotEmpty12() {
        ConsultaFeb c = new ConsultaFeb();
        c.setAdultAge(Boolean.TRUE);
        assertThat(c.isEmpty(), equalTo(true));
    }
    
      
    @Test
    public void testGetUrlEncodedSimple() {
        ConsultaFeb c = new ConsultaFeb();
        c.setConsulta("matemática");
        
        assertThat(c.getUrlEncoded(), containsString("consulta=matem"));
    }
    
    @Test
    public void testHasAuditory() {
        ConsultaFeb c = new ConsultaFeb();
        c.setConsulta("bla");
        c.addHasAuditory(true);
        
        assertThat(c.getUrlEncoded(), containsString("hasAuditory=true"));
    }
    
    @Test
    public void testHasVisual() {
        ConsultaFeb c = new ConsultaFeb();
        c.setConsulta("bla");
        c.addHasVisual(false);
        
        assertThat(c.getUrlEncoded(), containsString("hasVisual=false"));
    }
    
    @Test
    public void testCost() {
        ConsultaFeb c = new ConsultaFeb();
        c.setConsulta("bla");
        c.addCost(true);
        
        assertThat(c.getUrlEncoded(), containsString("cost=true"));
    }
    
    @Test
    public void testAgeRange() {
        ConsultaFeb c = new ConsultaFeb();
        c.setConsulta("bla");
        c.addAgeRangeInt(12);
        
        assertThat(c.getUrlEncoded(), containsString("ageRangeInt=12"));
    }
    
    @Test
    public void testHasAuditoryNotSet() {
        ConsultaFeb c = new ConsultaFeb();
        c.setConsulta("bla");
        
        assertThat(c.getUrlEncoded(), not(containsString("hasAuditory")));
    }
    
    @Test
    public void testIsActive() {
        ConsultaFeb c = new ConsultaFeb();
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
        ConsultaFeb c = new ConsultaFeb();
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
        ConsultaFeb c = new ConsultaFeb();
        c.setConsulta("bla");
        
        c.addCost(true);
        c.addCost(false);
        c.addFormat("bogus");
        
        ConsultaFeb c2 = new ConsultaFeb(c);
        
        c2.removeFacetFilter("format", "bogus");
        c2.addFacetFilter("format", "meuFormat");
        
        assertTrue(c.isActive("format", "bogus"));
        assertFalse(c.isActive("format", "meuFormat"));
    }
    
    @Test
    public void testAddRepository() {
        ConsultaFeb c = new ConsultaFeb();
        c.setConsulta("bla");

        c.add("repositorios", 1);
        
        assertThat(c.getUrlEncoded(), containsString("repositorios=1"));
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testOnlyAcceptsValidArguments() {
        ConsultaFeb c = new ConsultaFeb();
        c.add("hasaudio", true);
    }
}
