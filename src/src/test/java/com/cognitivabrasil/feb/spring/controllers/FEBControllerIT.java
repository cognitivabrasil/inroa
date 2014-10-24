/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitivabrasil.feb.spring.controllers;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.ui.ExtendedModelMap;

import com.cognitivabrasil.feb.AppConfig;
import com.cognitivabrasil.feb.data.entities.Consulta;
import com.cognitivabrasil.feb.data.services.SearchService;
import com.cognitivabrasil.feb.data.services.TagCloudService;
import java.io.IOException;
import org.junit.Ignore;
import org.springframework.util.StopWatch;

/**
 *
 * @author Marcos Freitas Nunes <marcos@cognitivabrasil.com.br>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class })
@ActiveProfiles("test")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class FEBControllerIT extends AbstractTransactionalJUnit4SpringContextTests {
    
    @Autowired
    FEBController controller;
    
    @PersistenceContext
    EntityManager em;
    
    private MockHttpServletResponse response;
    private MockHttpServletRequest request;
    private ExtendedModelMap model;
    
    @Before
    public void setUp() {
        
        response = new MockHttpServletResponse();
        request = new MockHttpServletRequest();
        model = new ExtendedModelMap();
    }
    
    @Test
    public void testIndex(){
        
        String result = controller.index(model, response, request, "x");
        
        assertThat(result, equalTo("index"));
        
        Consulta c = (Consulta) model.get("buscaModel");
        assertThat(c, notNullValue());        
    }
    
    @Test
    @Ignore("Não deve rodar, pois usa a internet para testar o link. Apenas para algum links no futuro")
    public void testVerificaUrl() throws IOException{
        String url = "http://feb.ufrgs.br/repositorio/documents/230";
//        String url = "http://objetoseducacionais2.mec.gov.br/bitstream/handle/mec/17405/046.%20Jogos.mpg?sequence=3";
        StopWatch stop = new StopWatch();
        stop.start("url: "+url);
        Boolean result = controller.verifyUrl(url);
        assertThat(result, equalTo(true));
        stop.stop();
        System.out.println("XML " + stop.prettyPrint());
    }
}