package com.cognitivabrasil.feb.solr;

import static com.cognitivabrasil.feb.solr.SolrDocumentListBuilder.solrDocumentListBuilder;
import static com.cognitivabrasil.feb.solr.SolrQueryResponseBuilder.solrQueryResponseBuilder;
import static com.cognitivabrasil.feb.solr.SolrSpellCheckBuilder.solrSpellCheckBuilder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cognitivabrasil.feb.data.entities.Document;
import com.cognitivabrasil.feb.data.services.ObaaSearchAdapterImpl;
import com.cognitivabrasil.feb.ferramentaBusca.ConsultaFeb;
import com.cognitivabrasil.feb.solr.ObaaSearchServiceSolrImpl;
import com.cognitivabrasil.feb.solr.query.QuerySolr;
import com.cognitivabrasil.feb.solr.query.ResultadoBusca;

public class ObaaSearchServiceSolrImplTest {
    @Mock
    QuerySolr querySolr;

    @InjectMocks
    private ObaaSearchServiceSolrImpl recuperador;

    @Before
    public void setup() {
        recuperador = new ObaaSearchServiceSolrImpl(querySolr, new ObaaSearchAdapterImpl());
        MockitoAnnotations.initMocks(this);

    }
    
    @Test
    public void testBusca() throws SolrServerException {
        ConsultaFeb consulta = new ConsultaFeb();
        consulta.setLimit(2);
        consulta.setConsulta("bla");
        
        
        QueryResponse qr = solrQueryResponseBuilder().results(
                    solrDocumentListBuilder()
                        .add().field("obaa.idBaseDados", 1)
                        .add().field("obaa.idBaseDados", 2)
                        .add().field("obaa.idBaseDados", 3)
                        .build()
                ).build();
        
  
        when(querySolr.pesquisaCompleta(consulta, 0, 2)).thenReturn(qr);
        
        
        
        ResultadoBusca<Document> r = recuperador.busca(consulta);
        
        verify(querySolr).pesquisaCompleta(consulta, 0, 2);
        
        assertThat(r.getDocuments(), hasSize(2));
        assertThat(r.getDocuments().get(0).getId(), equalTo(1));
        
        assertThat(r.getResultSize(), equalTo(3));   
    }
    
    @Test
    public void testAutosuggestWithSolrServerError() {
        QueryResponse r = new QueryResponse();

        when(querySolr.autosuggest(any())).thenReturn(r);

        List<String> l = recuperador.autosuggest("auto");

        assertThat(l, empty());
    }

    @Test
    public void testAutosuggestWithNoSuggestion() {
        QueryResponse r = solrQueryResponseBuilder()
                    .spellCheckResponse(
                                solrSpellCheckBuilder().build()
                            ).build();

        when(querySolr.autosuggest(any())).thenReturn(r);

        List<String> l = recuperador.autosuggest("auto");

        assertThat(l, empty());
    }
    
    @Test
    public void testAutosuggestWithSuggestions() {
        QueryResponse r = solrQueryResponseBuilder().spellCheckResponse(
                        solrSpellCheckBuilder()
                        .alternative("autoSuggest1", "autoSuggest2")
                        .alternative("bla")
                        .build()
                    ).build();


        when(querySolr.autosuggest(any())).thenReturn(r);

        List<String> l = recuperador.autosuggest("auto");
       
        assertThat(l, hasItems("autoSuggest1", "autoSuggest2", "bla"));
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
        
        Document d = (Document) recuperador.getDocumentosReais(queryResponse, 1).get(0);
        
        assertThat(d.getTitles(), hasItems("Title 1", "Title 2"));
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
        
        Document d = (Document) recuperador.getDocumentosReais(queryResponse, 1).get(0);
        
        assertThat(d.getDescriptions(), hasItems("Desc 1"));
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
        
        Document d = (Document) recuperador.getDocumentosReais(queryResponse, 1).get(0);
        
        assertThat(d.getKeywords(), hasItems("Key 1"));
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
        
        Document d = (Document) recuperador.getDocumentosReais(queryResponse, 1).get(0);
        
        assertThat(d.getLocation().get(0).getText(), equalTo("http://location"));
    }

}
