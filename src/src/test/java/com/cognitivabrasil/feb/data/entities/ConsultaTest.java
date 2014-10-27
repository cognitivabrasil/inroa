package com.cognitivabrasil.feb.data.entities;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

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
        assertThat(c.isEmpty(), equalTo(false));
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
        assertThat(c.isEmpty(), equalTo(false));
    }

    @Test
    public void testNotEmpty6() {
        Consulta c = new Consulta();
        c.addFacetFilter("difficulty", "dificil");
        assertThat(c.isEmpty(), equalTo(false));
    }

    @Test
    public void testNotEmpty7() {
        Consulta c = new Consulta();
        c.addFormat(".pdf");
        assertThat(c.isEmpty(), equalTo(false));
    }

    @Test
    public void testNotEmpty8() {
        Consulta c = new Consulta();
        c.addHasAuditory(true);
        assertThat(c.isEmpty(), equalTo(false));
    }

    @Test
    public void testNotEmpty9() {
        Consulta c = new Consulta();
        c.addHasText(Boolean.FALSE);
        assertThat(c.isEmpty(), equalTo(false));
    }

    @Test
    public void testNotEmpty10() {
        Consulta c = new Consulta();
        c.addHasVisual(true);
        assertThat(c.isEmpty(), equalTo(false));
    }

    @Test
    public void testNotEmpty11() {
        Consulta c = new Consulta();
        c.setIdioma("pt-BR");
        assertThat(c.isEmpty(), equalTo(false));
    }

    @Test
    public void testNotEmpty12() {
        Consulta c = new Consulta();
        c.setAdultAge(Boolean.TRUE);
        assertThat(c.isEmpty(), equalTo(false));
    }
    
      
    @Test
    public void testGetUrlEncodedSimple() {
        Consulta c = new Consulta();
        c.setConsulta("matemática");
        
        assertThat(c.getUrlEncoded(), containsString("consulta=matem"));
    }
}
