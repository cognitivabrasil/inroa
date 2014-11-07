package com.cognitivabrasil.feb.solr.indexar;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrException;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class IndexarDadosTest {
    @Mock
    SolrServer server;
    
    @InjectMocks
    IndexarDados indexarDados;
    
    @Before
    public void setup() {
        indexarDados = new IndexarDados(server);
        
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void deleteIndice() throws SolrServerException, IOException {
        UpdateResponse response = mock(UpdateResponse.class);
        
        when(response.getStatus()).thenReturn(200);
        
        when(server.deleteByQuery("*:*")).thenReturn(response);
        
        boolean status = indexarDados.apagarIndice();
        
        assertTrue(status);
        
        verify(server).commit();
    }
    
    @Test
    public void deleteIndiceError() throws SolrServerException, IOException {
        UpdateResponse response = mock(UpdateResponse.class);
        
        when(response.getStatus()).thenReturn(400);
        
        when(server.deleteByQuery("*:*")).thenReturn(response);
        
        boolean status = indexarDados.apagarIndice();
        
        assertFalse(status);
        
        verify(server, never()).commit();
    }
    
    @Test
    public void deleteIndiceException() throws SolrServerException, IOException {
        UpdateResponse response = mock(UpdateResponse.class);
        
        when(response.getStatus()).thenReturn(400);
        
        when(server.deleteByQuery("*:*")).thenThrow(new SolrServerException("teste"));
        
        boolean status = indexarDados.apagarIndice();
        
        assertFalse(status);
        
        verify(server, never()).commit();
    }
    
    @Test
    public void testIndex() throws SolrServerException, IOException {
        UpdateResponse response = mock(UpdateResponse.class);
        
        when(response.getStatus()).thenReturn(200);
        
        Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
        when(server.add(docs)).thenReturn(response);
        
        boolean status = indexarDados.indexarColecaoSolrInputDocument(docs);
        
        assertTrue(status);
        
        verify(server).commit();
    }
    
    @Test
    public void testIndexError() throws SolrServerException, IOException {
        UpdateResponse response = mock(UpdateResponse.class);
        
        when(response.getStatus()).thenReturn(400);
        
        Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
        when(server.add(docs)).thenReturn(response);
        
        boolean status = indexarDados.indexarColecaoSolrInputDocument(docs);
        
        assertFalse(status);
        
        verify(server, never()).commit();
    }
    
    @Test(expected=SolrException.class)
    public void testIndexException() throws SolrServerException, IOException {
        UpdateResponse response = mock(UpdateResponse.class);     
       
        Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
        when(server.add(docs)).thenThrow(new SolrServerException("teste"));
        
        indexarDados.indexarColecaoSolrInputDocument(docs);

    }
}
