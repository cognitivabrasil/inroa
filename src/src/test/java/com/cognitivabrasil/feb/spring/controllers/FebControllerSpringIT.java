package com.cognitivabrasil.feb.spring.controllers;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.solr.client.solrj.SolrServerException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import cognitivabrasil.obaa.OBAA;
import cognitivabrasil.obaa.builder.ObaaBuilder;

import com.cognitivabrasil.feb.AppConfig;
import com.cognitivabrasil.feb.data.entities.Document;
import com.cognitivabrasil.feb.data.services.DocumentService;
import com.cognitivabrasil.feb.ferramentaBusca.ConsultaFeb;
import com.cognitivabrasil.feb.spring.dtos.PaginationDto;
import com.cognitivabrasil.obaa.search.query.ObaaSearchService;
import com.cognitivabrasil.obaa.search.query.ResultadoBusca;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class })
@ActiveProfiles("test")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@DirtiesContext
public class FebControllerSpringIT extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    @InjectMocks
    FEBController controller;
    
    
    @Mock
    ObaaSearchService recuperador;
    
    @Mock
    private DocumentService docService;
    
    private MockMvc mockMvc;
    

    @Before
    public void setup() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");


        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).setViewResolvers(viewResolver).build();
    }

    @Test
    public void testMultipleFormats() throws Exception {
        ResultadoBusca r = new ResultadoBusca();
        r.setDocuments(new ArrayList<>());
        when(recuperador.busca(any())).thenReturn(r);
        
        ArgumentCaptor<ConsultaFeb> consulta = ArgumentCaptor.forClass(ConsultaFeb.class);
        
        mockMvc.perform(get("/resultado?consulta=teste&format=abc&format=xxx")
                );
        
        verify(recuperador).busca(consulta.capture());

        assertThat(consulta.getValue().getFormat(), hasItems("abc", "xxx"));
    }
    
    @Test
    public void objetoNaoExiste() throws Exception {
        when(docService.get(2)).thenReturn(null);
        
        mockMvc.perform(get("/objetos/{id}", 2))
            .andExpect(status().isNotFound());
    }
    
    @Test
    public void objetoApagade() throws Exception {
        Document d = new Document();
        d.setDeleted(true);
        when(docService.get(2)).thenReturn(d);
        
        mockMvc.perform(get("/objetos/{id}", 2))
            .andExpect(status().isGone());
    }
    
    @Test
    public void getObjeto() throws Exception {
        Document d = new Document();
        OBAA obaa = ObaaBuilder.buildObaa().general().title("Título").build();
        d.setId(2);
        d.setMetadata(obaa);
        d.setObaaEntry("entry");
        
        when(docService.get(2)).thenReturn(d);
        
        mockMvc.perform(get("/objetos/{id}", 2))
            .andExpect(status().is2xxSuccessful())
            .andExpect(model().attribute("title", equalTo("Título")))
            .andExpect(model().attribute("docId", equalTo(2)))
            .andExpect(model().attribute("obaaEntry", equalTo("entry")));
    }
    
    @Test
    public void suggestions() throws Exception {
        when(recuperador.autosuggest("bla")).thenReturn(Arrays.asList("blabla", "bleble"));
        
        mockMvc.perform(get("/suggestion").param("query", "bla"))
            .andExpect(jsonPath("$[0].value", equalTo("blabla")))
            .andExpect(jsonPath("$[1].value", equalTo("bleble")));
    }
    
    @Test
    public void json() throws Exception {
        Document d = new Document();
        OBAA obaa = ObaaBuilder.buildObaa().general().title("Objeto teste").build();
        d.setId(2);
        d.setMetadata(obaa);
        d.setObaaEntry("entry");
        
        when(docService.get(2)).thenReturn(d);        
               
        mockMvc.perform(get("/objetos/{id}/json", 2))
            .andExpect(status().is2xxSuccessful())
            .andExpect(jsonPath("$[0].label", equalTo("Informações Gerais")))
            .andExpect(jsonPath("$[0].children[0].label", equalTo("Título")))
            .andExpect(jsonPath("$[0].children[0].value", equalTo("Objeto teste")));
    }
    
    @Test
    public void jsonGone() throws Exception {
        Document d = new Document();
        d.setId(2);
        d.setObaaEntry("entry");
        d.setDeleted(true);
        
        when(docService.get(2)).thenReturn(d);        
               
        mockMvc.perform(get("/objetos/{id}/json", 2))
            .andExpect(status().isGone());

    }
    
    @Test
    public void json404() throws Exception {
        when(docService.get(2)).thenReturn(null);        
               
        mockMvc.perform(get("/objetos/{id}/json", 2))
            .andExpect(status().isNotFound());
    }
    
    @Test
    public void resultado() throws Exception {
        ResultadoBusca r = new ResultadoBusca();
        r.setDocuments(new ArrayList<>());
        
        when(recuperador.busca(any())).thenReturn(r);
        
         mockMvc.perform(get("/resultado?consulta=teste"))
             
             .andExpect(model().attribute("results", equalTo(r)));
    }
    
    @Test
    public void resultadoPaginacao() throws Exception {
        ResultadoBusca r = new ResultadoBusca();
        r.setDocuments(new ArrayList<>());
        r.setResultSize(12);
        
        when(recuperador.busca(any())).thenReturn(r);
        
        PaginationDto pag = new PaginationDto(5, 12, 0);
        
         mockMvc.perform(get("/resultado?consulta=teste&limit=5"))
             
             .andExpect(model().attribute("pagination", equalTo(pag)));
    }
    
    
    @Test
    public void resultadoSolrException() throws Exception {
        when(recuperador.busca(any())).thenThrow(new SolrServerException("teste"));
        
        mockMvc.perform(get("/resultado?consulta=teste"))
                .andExpect(model().attributeExists("erro"));
    }
    
}