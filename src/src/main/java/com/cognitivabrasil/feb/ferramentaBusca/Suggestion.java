package com.cognitivabrasil.feb.ferramentaBusca;

import org.apache.solr.client.solrj.response.SpellCheckResponse;

/**
 * Representa uma sugestão de SpellCheck.
 * 
 * Usada para gerar os links "Você quis dizer bla?" no resultado da busca.
 * 
 * @author Paulo Schreiner
 */
public class Suggestion {
    private final ConsultaFeb consulta;
    private final SpellCheckResponse spellCheckResponse;

    /**
     * @param spellCheckResponse resposta do Solr para o spellcheck
     * @param consulta que gerou esta resposta
     */
    public Suggestion(SpellCheckResponse spellCheckResponse, ConsultaFeb consulta) {
        this.spellCheckResponse = spellCheckResponse;
        this.consulta = new ConsultaFeb(consulta);
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
    
    /**
     * @return se há correções disponíveis
     */
    public boolean isMisspelled() {
        return getText() != null;
    }

}
