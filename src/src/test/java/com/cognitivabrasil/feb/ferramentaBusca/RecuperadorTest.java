package com.cognitivabrasil.feb.ferramentaBusca;

import static com.cognitivabrasil.feb.solr.SolrDocumentListBuilder.solrDocumentListBuilder;
import static com.cognitivabrasil.feb.solr.SolrQueryResponseBuilder.solrQueryResponseBuilder;
import static com.cognitivabrasil.feb.solr.SolrSpellCheckBuilder.solrSpellCheckBuilder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cognitivabrasil.feb.data.entities.Consulta;
import com.cognitivabrasil.feb.solr.ObaaSearchServiceSolrImpl;
import com.cognitivabrasil.feb.solr.query.QuerySolr;

public class RecuperadorTest {
    @Mock
    QuerySolr querySolr;

    @InjectMocks
    private ObaaSearchServiceSolrImpl recuperador;

    @Before
    public void setup() {
        recuperador = new ObaaSearchServiceSolrImpl();
        MockitoAnnotations.initMocks(this);

    }
    
    @Test
    public void testBusca() throws SolrServerException {
        Consulta consulta = new Consulta();
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
        
        ResultadoBusca r = recuperador.busca(consulta);
        
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

}
