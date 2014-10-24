package com.cognitivabrasil.feb.spring.controllers;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import cognitivabrasil.obaa.OBAA;
import cognitivabrasil.obaa.General.General;

import com.cognitivabrasil.feb.AppConfig;
import com.cognitivabrasil.feb.WebConfig;
import com.cognitivabrasil.feb.data.entities.Document;
import com.cognitivabrasil.feb.ferramentaBusca.Recuperador;
import org.apache.solr.client.solrj.SolrServerException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class, WebConfig.class })
@WebAppConfiguration
@ActiveProfiles("test")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@DirtiesContext
public class RssControllerTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Mock
    Recuperador recuperador;
    
    @Autowired
    @InjectMocks
    RssController controller;
    
    @Autowired
    WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setup() throws SolrServerException {
        MockitoAnnotations.initMocks(this);
        
        List<Document> items = new ArrayList<>();
        
        Document d = new Document();
        OBAA metadata = new OBAA();
        General general = new General();
        general.addTitle("teste RSS");
        metadata.setGeneral(general);
        d.setMetadata(metadata);
        
        items.add(d);
        
        when(recuperador.busca(any())).thenReturn(items);
        
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void consultaSimplesRetornaUmFeedRssBemFormado() throws Exception {
        mockMvc.perform(get("/rss/feed")
                .param("consulta", "teste")
             )
             .andExpect(status().isOk())
             .andExpect(content().contentType("application/rss+xml"))
             .andExpect(content().string(containsString("<title>teste RSS</title>")));
    }
}