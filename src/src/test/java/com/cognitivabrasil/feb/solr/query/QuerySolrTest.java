package com.cognitivabrasil.feb.solr.query;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cognitivabrasil.feb.data.entities.Consulta;
import com.cognitivabrasil.feb.data.entities.Document;
import com.cognitivabrasil.feb.solr.SolrDocumentListBuilder;

import static com.cognitivabrasil.feb.solr.SolrQueryResponseBuilder.solrQueryResponseBuilder;

public class QuerySolrTest {
    @Mock
    private HttpSolrServer solr;
    
    @InjectMocks
    private QuerySolr querySolr;
    
    @Before
    public void setup() {
        querySolr = new QuerySolr();
        
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void getDocumentosReaisGetTitle() {
        QueryResponse queryResponse = solrQueryResponseBuilder()
                .results(
                        SolrDocumentListBuilder.solrDocumentListBuilder()
                            .add()
                                .field("obaa.general.title", "Title 1")
                                .field("obaa.general.title", "Title 2")
                                .field("obaa.general.identifier.entry", "id1")
                                .field("obaa.idBaseDados", 1)
                                .build()
                        ).addHighlighting().build();
        
        List<Document> l = QuerySolr.getDocumentosReais(queryResponse, 0, 1);
        
        assertThat(l.get(0).getTitles(), hasItems("Title 1", "Title 2"));
    }
    
    @Test
    public void getDocumentosReaisGetDescription() {
        QueryResponse queryResponse = solrQueryResponseBuilder()
                .results(
                        SolrDocumentListBuilder.solrDocumentListBuilder()
                            .add()
                                .field("obaa.general.description", "Desc 1")
                                .field("obaa.general.identifier.entry", "id1")
                                .field("obaa.idBaseDados", 1)
                                .build()
                        ).addHighlighting().build();
        
        List<Document> l = QuerySolr.getDocumentosReais(queryResponse, 0, 1);
        
        assertThat(l.get(0).getDescriptions(), hasItems("Desc 1"));
    }
    
    @Test
    public void getDocumentosReaisGetKeyword() {
        QueryResponse queryResponse = solrQueryResponseBuilder()
                .results(
                        SolrDocumentListBuilder.solrDocumentListBuilder()
                            .add()
                                .field("obaa.general.keyword", "Key 1")
                                .field("obaa.general.identifier.entry", "id1")
                                .field("obaa.idBaseDados", 1)
                                .build()
                        ).addHighlighting().build();
        
        List<Document> l = QuerySolr.getDocumentosReais(queryResponse, 0, 1);
        
        assertThat(l.get(0).getKeywords(), hasItems("Key 1"));
    }
    
    @Test
    public void getDocumentosReaisGetLocation() {
        QueryResponse queryResponse = solrQueryResponseBuilder()
                .results(
                        SolrDocumentListBuilder.solrDocumentListBuilder()
                            .add()
                                .field("obaa.technical.location", "http://location")
                                .field("obaa.general.identifier.entry", "id1")
                                .field("obaa.idBaseDados", 1)
                                .build()
                        ).addHighlighting().build();
        
        List<Document> l = QuerySolr.getDocumentosReais(queryResponse, 0, 1);
        
        assertThat(l.get(0).getLocation().get(0).getText(), equalTo("http://location"));
    }
    
    @Test
    public void test() throws SolrServerException {
        Consulta c = new Consulta();
        
        SolrQuery query = CriaQuery.criaQueryCompleta(c);
        query.setRequestHandler("/feb");
        
        querySolr.pesquisaCompleta(c, 0, 10);
        
        verify(solr).query(argThat(solrQuery(query)));
    }
    
    @Test
    public void autosuggestTest() throws SolrServerException {
        QueryResponse qr = new QueryResponse();
        
        SolrQuery query = new SolrQuery();
        query.setRequestHandler("/suggest");
        query.setQuery("mate");
        
        when(solr.query(argThat(solrQuery(query)))).thenReturn(qr);
        
        QueryResponse actual = querySolr.autosuggest("mate");
        
        assert(qr == actual);
    }
    
    @Test
    public void autosuggestExceptionTest() throws SolrServerException {      
             
        when(solr.query(any())).thenThrow(new SolrServerException("teste"));
        
        QueryResponse actual = querySolr.autosuggest("mate");
        
        assert(actual == null);
    }
    
    private Matcher<SolrQuery> solrQuery(SolrQuery query) {
        return new BaseMatcher<SolrQuery>() {
            private SolrQuery foo;
           @Override
           public boolean matches(final Object item) {
               foo = (SolrQuery) item;
              
              return query.getRequestHandler().equals(foo.getRequestHandler())
                      && query.getQuery().equals(foo.getQuery());
           }
           @Override
           public void describeTo(final Description description) {
               if(!query.getRequestHandler().equals(foo.getRequestHandler())) {
                   description.appendText("expected SolrQuery with request handler ")
                       .appendValue(query.getRequestHandler())
                       .appendText(" but was").appendValue(foo.getRequestHandler());
               }
               if(!query.getQuery().equals(foo.getQuery())) {
                   description.appendText("expected SolrQuery with query ")
                       .appendValue(query.getQuery())
                       .appendText(" but was").appendValue(foo.getQuery());
               }
               
           }
        };
     }
    
}


