package com.cognitivabrasil.feb.data.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.joda.time.DateTime;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
 
// TODO: Auto-generated Javadoc
/**
 * The Class SubFederacaoTest.
 *
 * @author paulo, Marcos
 */
public class SubFederacaoTest {

    /**
     * Instantiates a new sub federacao test.
     */
    public SubFederacaoTest() {
    }

    /**
	 * Test to string.
	 */
    @Test
    public void testToString() {
        SubFederacao s = new SubFederacao();
        s.setName("paulo");

        assertThat(s.toString(), containsString("paulo"));
    }

    /**
     * Test get proxima atualizacao.
     */
    @Test
    public void testGetProximaAtualizacao() {
        SubFederacao s = new SubFederacao();
        s.setUltimaAtualizacao(DateTime.now());
        assertThat(s.getProximaAtualizacao(), is(notNullValue()));
    }

    /**
     * Test get size doc.
     */
    @Test
    public void testGetSizeDoc() {
        RepositorioSubFed r1 = mock(RepositorioSubFed.class);
        RepositorioSubFed r2 = mock(RepositorioSubFed.class);


        when(r1.getSize()).thenReturn(3);
        when(r2.getSize()).thenReturn(4);

        Set<RepositorioSubFed> sr = new HashSet<RepositorioSubFed>();
        sr.add(r1);
        sr.add(r2);


        SubFederacao instance = new SubFederacao();

        instance.setRepositorios(sr);
        int result = instance.getSizeDoc();
        assertEquals(7, result);
    }

    /**
     * Test update not blank.
     */
    @Test
    public void testUpdateNotBlank() {
        SubFederacao r1 = new SubFederacao();

        r1.setId(1);

        r1.setName("Jorge");
        r1.setUrl("bla");

        SubFederacao r2 = new SubFederacao();

        r2.setName("Paulo");

        r1.merge(r2);

        assertEquals("Paulo", r1.getName());
        assertEquals("bla", r1.getUrl());
    }
    
    /**
     * Test update the list of repositories.
     */
    @Test
    public void testUpdateListRepositories() throws Exception{
        
        Set<RepositorioSubFed> listSubRep = new HashSet<>();
        RepositorioSubFed rsf = new RepositorioSubFed();
        rsf.setName("marcos");
        listSubRep.add(rsf);
        
        rsf = new RepositorioSubFed();
        rsf.setName("jorge");
        listSubRep.add(rsf);
        
        rsf = new RepositorioSubFed();
        rsf.setName("nunes");
        listSubRep.add(rsf);
        
        SubFederacao r1 = new SubFederacao();
        r1.setRepositorios(listSubRep);
        
        Set<String> listaNova = new HashSet<>();
        listaNova.add("marcos");
        listaNova.add("jorge");
        listaNova.add("preto");
        
        r1.atualizaListaSubRepositorios(listaNova);
       Set<RepositorioSubFed> correto = new HashSet<>();
       
        rsf = new RepositorioSubFed();
        rsf.setName("marcos");
        correto.add(rsf);
        
        rsf = new RepositorioSubFed();
        rsf.setName("jorge");
        correto.add(rsf);
        
        rsf = new RepositorioSubFed();
        rsf.setName("preto");
        correto.add(rsf);
        
        assertEquals(correto, r1.getRepositorios());
        
    }
    
    @Test
    public void testUpdateDate(){
        SubFederacao fed = new SubFederacao();
        DateTime date = DateTime.parse("1984-08-21T07:35:00Z");
        fed.setDataXMLTemp("1984-08-21T05:35:00Z");
        fed.setUltimaAtualizacao(date);
        
        assertThat(fed.getDataXML(), equalTo("1984-08-21T05:35:00Z"));        
    }
}