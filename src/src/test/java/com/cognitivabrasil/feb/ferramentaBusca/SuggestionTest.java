package com.cognitivabrasil.feb.ferramentaBusca;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.solr.client.solrj.response.SpellCheckResponse;
import org.junit.Test;

public class SuggestionTest {
    @Test
    public void nullSpellCheckResponseReturns() {
        ConsultaFeb c = new ConsultaFeb();
        
        Suggestion s = new Suggestion(null, c);
        
        assertThat(s.getText(), nullValue());
        assertThat(s.isMisspelled(), equalTo(false));
    }
    
    @Test
    public void getUrlEncodedDelegaParaConsulta() {
        ConsultaFeb c = new ConsultaFeb();
        
        SpellCheckResponse sp = mock(SpellCheckResponse.class);
        when(sp.getCollatedResult()).thenReturn("sugestao");
        
        Suggestion s = new Suggestion(sp, c);
        
        String urlEncoded = s.getUrlEncoded();
        c.setConsulta("sugestao");
        
        assertThat(urlEncoded, equalTo(c.getUrlEncoded()));
    }
    
    @Test
    public void getUrlText() {
        ConsultaFeb c = new ConsultaFeb();
        
        SpellCheckResponse sp = mock(SpellCheckResponse.class);
        when(sp.getCollatedResult()).thenReturn("sugestao");
        
        Suggestion s = new Suggestion(sp, c);
        
        assertThat(s.getText(), equalTo("sugestao"));
        assertThat(s.isMisspelled(), equalTo(true));
    }
    
    @Test
    public void ifNoSuggestionsIsMisspeledReturnsNull() {
        ConsultaFeb c = new ConsultaFeb();
        
        Suggestion s = new Suggestion(mock(SpellCheckResponse.class), c);
        
        assertThat(s.getText(), nullValue());
        assertThat(s.isMisspelled(), equalTo(false));
    }

}
