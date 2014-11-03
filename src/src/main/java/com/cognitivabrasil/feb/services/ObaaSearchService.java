package com.cognitivabrasil.feb.services;

import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;

import com.cognitivabrasil.feb.data.entities.Consulta;
import com.cognitivabrasil.feb.ferramentaBusca.ResultadoBusca;

/**
 * Serviço para realizar todas as buscar por documentos.
 * 
 * @author Paulo Schreiner
 */
public interface ObaaSearchService {

    /**
     * Retorna uma lista de sugestões para o autocomplete.
     * @param query busca a ser feita
     * @return lista de sugestões 
     */
    List<String> autosuggest(String query);

    /**
     * Envia para o Solr uma consulta
     *
     * @param consulta Consulta efetuada
     * @return Lista de documentos reais que correspondem ao resultado da busca
     * @throws SolrServerException - Não foi possível fazer a pesquisa (server offline?) 
     */
    ResultadoBusca busca(Consulta consulta) throws SolrServerException;

}
