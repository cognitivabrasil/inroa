package com.cognitivabrasil.feb.solr.query;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Daniel Epstein
 * @author Paulo Schreiner
 */
public class QuerySolr {
    private static final Logger log = LoggerFactory.getLogger(QuerySolr.class);


    private HttpSolrServer serverSolr;
    
    public QuerySolr(HttpSolrServer serverSolr) {
        this.serverSolr = serverSolr;
    }
    
    /**
     * Realiza a busca avancada. A funcao verifica quais campos foram escolhidos e realiza a busca neles PERGUNTA:
     * REALIZA A BUSCA APENAS NELES OU NOS PRINCIPAIS TAMBEM? E A BUSCA PODE SER DIFERENTE PARA CADA CAMPO?
     *
     * @param pesquisa Um Objeto consulta com todas as informacoes da busca (String, campos, etc)
     * @param offset Posicao do primeiro resultado a aparecer
     * @param limit Numero de resultados desejados
     * @return 
     * @throws SolrServerException - Não foi possível fazer a pesquisa (server offline?)
     */
    public QueryResponse pesquisaCompleta(Consulta pesquisa, int offset, int limit) throws SolrServerException {
        SolrQuery query = CriaQuery.criaQueryCompleta(pesquisa);

        query.setStart(offset);
        query.setRows(limit);

        query.setRequestHandler("/feb");

        return serverSolr.query(query);
    }
    
    /**
     * Faz uma busca no handler de auto-suggest do Solr.
     * @param auto string de busca (o que o usuário digitou no field)
     * @return resultado da consulta, com as sugestões do autosuggest.
     */
    public QueryResponse autosuggest(String auto) {
        SolrQuery query = new SolrQuery(); 
        
        query.setRequestHandler("/suggest");
        query.setQuery(auto);
        
        try {
            return serverSolr.query(query);
        }
        catch (SolrServerException e) {
            log.error("Ocorreu um erro durante o autosuggest", e);
            return null;
        }
        
    }

   
    
    /**
         * @return Numero de documentos retornados da busca
         * @deprecated não há por que usar
         */
        @Deprecated
        public static int getNumDocs(QueryResponse queryResponse) {
            return (int) queryResponse.getResults().getNumFound();
        }

}
