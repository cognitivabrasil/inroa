package com.cognitivabrasil.feb.solr.query;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.solr.client.solrj.response.FacetField;

import com.cognitivabrasil.feb.ferramentaBusca.ConsultaFeb;

/**
 * Representa uma campo facetado.
 * 
 * @see FacetField
 * @author Paulo Schreiner
 */
public class Facet {
    private final int valueCount;
    private final List<FacetCount> values;
    private final String name;

    /**
     * @param facetField obtido do Solr
     * @param consulta a consulta atual, que gerou estas facetas
     */
    public Facet(FacetField facetField, ConsultaFeb consulta) {
        name = facetField.getName();
        valueCount = facetField.getValueCount();
        
        values = facetField.getValues().stream().map(v -> new FacetCount(v, name, consulta)).collect(Collectors.toList());
    }

    /**
     * @return quantidade de categorias distintas no facet
     */
    public int getValueCount() {
        return valueCount;
    }

    /**
     * @return categorias individuais
     */
    public List<FacetCount> getValues() {
        return values;
    }

    /**
     * @return nome do campo que está sendo facetado
     */
    public String getName() {
        return name;
    }
    
    public String getVarName() {
        return name.replaceAll("\\.", "_");
    }
    
    public boolean isActive() {
        return getValues().stream().anyMatch(f -> f.isActive());
    }

}
