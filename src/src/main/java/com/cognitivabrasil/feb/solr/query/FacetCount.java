package com.cognitivabrasil.feb.solr.query;

import org.apache.solr.client.solrj.response.FacetField.Count;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cognitivabrasil.feb.data.entities.Consulta;

/**
 * Essa classe representa um valor de facet específico.
 * 
 * Possui informações sobre o facet e funcionalidade para facilitar o uso da
 * facet no .jsp.
 * 
 * @see Count
 * @author Paulo Schreiner
 */
public class FacetCount {
    private static final Logger log = LoggerFactory.getLogger(FacetCount.class);
    
    private Consulta consulta;
    private final String name;
    private final String fieldName;
    private final Long count;

    private final boolean isActive;
    
    /**
     * @param v Obtido do SOLR
     * @param consulta a consulta atual
     */
    public FacetCount(Count v, String fieldName, Consulta consulta) {
        name = v.getName();
        this.fieldName = getShortName(fieldName);
        count = v.getCount();
        
                
        
        isActive = consulta.isActive(this.fieldName, name);
                
        setNewConsulta(new Consulta(consulta));
    }
    
    /**
     * @param fieldName2
     * @return o nome "curto" do campo, pe, obaa.technical.format retorna "format"
     */
    private String getShortName(String fieldName2) {
        String[] r = fieldName2.split("\\.");
        return r[r.length-1];
    }

    /**
     * Gera uma nova consulta.
     * @param c consulta atual
     */
    private void setNewConsulta(Consulta c) {
        if(isActive) {
            c.removeFacetFilter(fieldName, name);
        }
        else {
            c.addFacetFilter(fieldName, name);
        }
        consulta = c;
    }

    /**
     * @return consulta que deve ser executada para ativar a facet.
     */
    public Consulta getConsulta() {
        return consulta;
    }


    /**
     * @return nome da facet (NÃO é o nome do campo, é o nome da categoria)
     */
    public String getName() {
        return name;
    }


    /**
     * @return número de documentos que contém a facet.
     */
    public Long getCount() {
        return count;
    }

   
    /**
     * @return true caso o facet esteja ativado, falso caso contrário.
     */
    public boolean isActive() {
        return isActive;

    }

}
