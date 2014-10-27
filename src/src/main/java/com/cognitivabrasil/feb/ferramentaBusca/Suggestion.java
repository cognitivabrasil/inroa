package com.cognitivabrasil.feb.ferramentaBusca;

import org.apache.solr.client.solrj.response.SpellCheckResponse;

import com.cognitivabrasil.feb.data.entities.Consulta;

/**
 * Representa uma sugestão de SpellCheck.
 * 
 * Usada para gerar os links "Você quis dizer bla?" no resultado da busca.
 * 
 * @author Paulo Schreiner
 */
public class Suggestion {
    private final Consulta consulta;
    private final SpellCheckResponse spellCheckResponse;

    public Suggestion(SpellCheckResponse spellCheckResponse, Consulta consulta) {
        this.spellCheckResponse = spellCheckResponse;
        this.consulta = new Consulta(consulta);
        this.consulta.setConsulta(getText());
    }
    
    /**
     * Gera lista de parâmetros para criar um link para esta sugestão.
     * @return parameteos codificados para URL
     */
    public String getUrlEncoded() {
        return consulta.getUrlEncoded();
    }
    
    /**
     * @return texto da sugestão
     */
    public String getText() {
        if(spellCheckResponse == null) {
            return null;
        }
        return spellCheckResponse.getCollatedResult();
    }
    
    public boolean isMisspelled() {
        return getText() != null;
    }

}
