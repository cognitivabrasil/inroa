/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 *
 * @author paulo
 */
public class RepositorioTest {
    
    public RepositorioTest() {
    }
    
    @Test
    public void testToString() {
        Repositorio instance = new Repositorio();
        instance.setNome("Teste");
        
        assertThat(instance.toString(), containsString("Teste"));
    }
    
    @Test
    public void testGetColecoesEmpty() {
        Repositorio instance = new Repositorio();
        Set result = instance.getColecoes();
        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetColecoesNotEmpty() {
        Repositorio instance = new Repositorio();
        instance.setColecoesInternal("Jorge;Feliz da vida;Teste 2");
        
        HashSet<String> exp = new HashSet<String>();
        exp.add("Jorge");
        exp.add("Teste 2");
        exp.add("Feliz da vida");
        
        assertEquals(exp, instance.getColecoes());
    }
    
    @Test
    public void testSetColecoes() {
        Repositorio instance = new Repositorio();
        
        HashSet<String> exp = new HashSet<String>();
        exp.add("Jorge");
        exp.add("Teste 2");
        exp.add("Feliz da vida");
        
        instance.setColecoes(exp);
        
        assertEquals(exp, instance.getColecoes());
    }
    
    @Test
    public void testSetColecoesEmpty() {
        Repositorio instance = new Repositorio();
        instance.setColecoes(new HashSet<String>());
        Set result = instance.getColecoes();
        
        assertEquals("", instance.getColecoesInternal());
        assertEquals(0, result.size());
    }
    
    @Test
    public void testAddColecao() {
        Repositorio instance = new Repositorio();
        
        instance.addColecao("Jorge");
        instance.addColecao("Teste 2");
        instance.addColecao("Feliz da vida");
        
        HashSet<String> exp = new HashSet<String>();
        exp.add("Jorge");
        exp.add("Teste 2");
        exp.add("Feliz da vida");
        
        assertEquals(exp, instance.getColecoes());
    }
    
    @Test
    public void testUpdateNotBlank() {
        Repositorio r1 = new Repositorio();
        
        r1.setId(1);
        
        r1.setNome("Jorge");
        r1.setUrl("bla");
        
        Repositorio r2 = new Repositorio();
        
        r2.setNome("Paulo");
        r2.setNamespace("obaa");
        
        r1.merge(r2);
        
        assertEquals("Paulo", r1.getNome());
        assertEquals("bla", r1.getUrl());
        assertEquals("obaa", r1.getNamespace());
    }
    
        @Test(expected=IllegalArgumentException.class)
      public void testUpdateDifferentIds() {
        Repositorio r1 = new Repositorio();
        
        r1.setId(1);
        
        r1.setNome("Jorge");
        r1.setUrl("bla");
        
        Repositorio r2 = new Repositorio();
        
        r2.setId(3);
        r2.setNome("Paulo");
        r2.setNamespace("obaa");
        
         r1.merge(r2);
        

    }
}
