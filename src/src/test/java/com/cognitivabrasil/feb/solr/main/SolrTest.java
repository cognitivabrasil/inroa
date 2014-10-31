package com.cognitivabrasil.feb.solr.main;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.common.SolrInputDocument;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import cognitivabrasil.obaa.OBAA;
import cognitivabrasil.obaa.Technical.Technical;
import cognitivabrasil.obaa.builder.ObaaBuilder;

import com.cognitivabrasil.feb.data.entities.Document;
import com.cognitivabrasil.feb.data.entities.Repositorio;
import com.cognitivabrasil.feb.solr.indexar.IndexarDados;

public class SolrTest {
    @Mock
    private IndexarDados indexar;
    
    @InjectMocks
    private Solr solr;
    
    @Before
    public void setup() {
        solr = new Solr();
        
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void naoIndexaDocumentoSemObaaEntry() {
        List<Document> docs = new ArrayList<>();
        
        Document doc = new Document();
        OBAA obaa = ObaaBuilder.buildObaa().general().title("Bla").build();
        obaa.setTechnical(new Technical());
        obaa.getTechnical().addLocation("http://teste");
        doc.setMetadata(obaa);
        
        docs.add(doc);
        
        ArgumentCaptor<List> capture = ArgumentCaptor.forClass(List.class);
        
        solr.indexarBancoDeDados(docs);
        
        verify(indexar).indexarColecaoSolrInputDocument(capture.capture());
        
        List<SolrInputDocument> r = capture.getValue();
        
        assertThat(r, hasSize(0));        
    }
    
    @Test
    public void naoIndexaDocumentoComObaaEntryVazio() {
        List<Document> docs = new ArrayList<>();
        
        Document doc = new Document();
        OBAA obaa = ObaaBuilder.buildObaa().general().title("Bla").build();
        obaa.setTechnical(new Technical());
        obaa.getTechnical().addLocation("http://teste");
        doc.setMetadata(obaa);
        doc.setObaaEntry("");
        
        docs.add(doc);
        
        ArgumentCaptor<List> capture = ArgumentCaptor.forClass(List.class);
        
        solr.indexarBancoDeDados(docs);
        
        verify(indexar).indexarColecaoSolrInputDocument(capture.capture());
        
        List<SolrInputDocument> r = capture.getValue();
        
        assertThat(r, hasSize(0));        
    }
    
    @Test
    public void naoIndexaDocumentoSemLocation() {
        List<Document> docs = new ArrayList<>();
        
        Document doc = new Document();
        OBAA obaa = ObaaBuilder.buildObaa().general().title("Bla").build();
        obaa.setTechnical(new Technical());
        doc.setMetadata(obaa);
        doc.setObaaEntry("entry");
        
        docs.add(doc);

        
        ArgumentCaptor<List> capture = ArgumentCaptor.forClass(List.class);
        
        solr.indexarBancoDeDados(docs);
        
        verify(indexar).indexarColecaoSolrInputDocument(capture.capture());
        
        List<SolrInputDocument> r = capture.getValue();
        
        assertThat(r, hasSize(0));  
    }
    
    @Test
    public void naoIndexaDocumentoSemLocationComHttp() {
        List<Document> docs = new ArrayList<>();
        
        Document doc = new Document();
        OBAA obaa = ObaaBuilder.buildObaa().general().title("Bla").build();
        obaa.setTechnical(new Technical());
        obaa.getTechnical().addLocation("teste");
        doc.setMetadata(obaa);
        doc.setObaaEntry("entry");
        
        docs.add(doc);

        
        ArgumentCaptor<List> capture = ArgumentCaptor.forClass(List.class);
        
        solr.indexarBancoDeDados(docs);
        
        verify(indexar).indexarColecaoSolrInputDocument(capture.capture());
        
        List<SolrInputDocument> r = capture.getValue();
        
        assertThat(r, hasSize(0));  
    }
    
    @Test
    public void indexaDocumento() {
        List<Document> docs = new ArrayList<>();
        
        Document doc = new Document();
        OBAA obaa = ObaaBuilder.buildObaa().general().title("Bla").build();
        obaa.setTechnical(new Technical());
        obaa.getTechnical().addLocation("http://teste");
        doc.setMetadata(obaa);
        doc.setObaaEntry("entry");
        
        docs.add(doc);

        
        ArgumentCaptor<List> capture = ArgumentCaptor.forClass(List.class);
        
        solr.indexarBancoDeDados(docs);
        
        verify(indexar).indexarColecaoSolrInputDocument(capture.capture());
        
        List<SolrInputDocument> r = capture.getValue();
        
        assertThat(r, hasSize(1));  
        SolrInputDocument solrDoc = r.get(0);
        
        assertThat(solrDoc.getFieldValue("obaa.general.title"), equalTo("Bla"));
    }
    
    @Test
    public void indexaComParametros() {
        List<Document> docs = new ArrayList<>();
        
        Document doc = new Document();
        OBAA obaa = ObaaBuilder.buildObaa().general().title("Bla").build();
        obaa.setTechnical(new Technical());
        obaa.getTechnical().addLocation("http://teste");
        doc.setMetadata(obaa);
        doc.setObaaEntry("entry");
        
        doc.setId(2);
        Repositorio repositorio = new Repositorio();
        repositorio.setId(3);
        repositorio.setName("my Rep");
        doc.setRepositorio(repositorio);
        
        docs.add(doc);

        
        ArgumentCaptor<List> capture = ArgumentCaptor.forClass(List.class);
        
        solr.indexarBancoDeDados(docs);
        
        verify(indexar).indexarColecaoSolrInputDocument(capture.capture());
        
        List<SolrInputDocument> r = capture.getValue();
        
        assertThat(r, hasSize(1));  
        SolrInputDocument solrDoc = r.get(0);
        
        assertThat(solrDoc.getFieldValue("obaa.repositorio"), equalTo(3));
        assertThat(solrDoc.getFieldValue("obaa.federacao"), equalTo(-1));
        assertThat(solrDoc.getFieldValue("obaa.subFederacao"), equalTo(-1));
        assertThat(solrDoc.getFieldValue("obaa.repName"), equalTo("my Rep"));
        assertThat(solrDoc.getFieldValue("obaa.idBaseDados"), equalTo(2));


    }
}
