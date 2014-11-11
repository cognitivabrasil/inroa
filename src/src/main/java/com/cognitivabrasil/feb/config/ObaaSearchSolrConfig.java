package com.cognitivabrasil.feb.config;

import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cognitivabrasil.feb.spring.FebConfig;
import com.cognitivabrasil.obaa.search.ObaaIndexService;
import com.cognitivabrasil.obaa.search.ObaaIndexServiceSolrImpl;
import com.cognitivabrasil.obaa.search.ObaaSearchAdapter;
import com.cognitivabrasil.obaa.search.ObaaSearchService;
import com.cognitivabrasil.obaa.search.ObaaSearchServiceSolrImpl;

/**
 * Configuração dos Beans que fazem do ObaaSearch.
 * 
 * @author Paulo Schreiner
 */
@Configuration
public class ObaaSearchSolrConfig {
    @Autowired
    private FebConfig febConfig;
    
    @Autowired
    private ObaaSearchAdapter obaaSearchAdapter;
    
    /**
     * @return conexão ao servidor Solr.
     */
    @Bean
    public HttpSolrServer serverSolr() {
        HttpSolrServer server = new HttpSolrServer(febConfig.getSolrUrl());
        return server;
    }

    @Bean
    public ObaaIndexService obaaIndexServiceSolr() {
        return new ObaaIndexServiceSolrImpl(serverSolr());
    }

    @Bean
    public ObaaSearchService obaaSearchServiceSolr() {
        return new ObaaSearchServiceSolrImpl(serverSolr(), obaaSearchAdapter);
    }
}
