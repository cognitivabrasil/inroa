/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitivabrasil.feb.spring.controllers;

import com.cognitivabrasil.feb.AppConfig;
import com.cognitivabrasil.feb.data.entities.Consulta;
import com.cognitivabrasil.feb.data.services.SearchService;
import java.util.Map;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import org.joda.time.DateTime;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.ui.ExtendedModelMap;

/**
 *
 * @author Marcos Freitas Nunes <marcos@cognitivabrasil.com.br>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class })
@ActiveProfiles("test")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class FEBControllerIT extends AbstractTransactionalJUnit4SpringContextTests {
    
    @Autowired
    FEBController controller;
    @Autowired
    SearchService searchService;
    
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
        searchService.save("marcos", DateTime.now());
        searchService.save("marcos", DateTime.now());
        searchService.save("marcos", DateTime.now());
        searchService.save("nunes", DateTime.now());
        searchService.save("nunes", DateTime.now());
        String result = controller.index(model, response, request, "x");
        
        assertThat(result, equalTo("index"));
        
        Consulta c = (Consulta) model.get("buscaModel");
        assertThat(c, notNullValue());
                
        Map<String, Integer> tagCloud = (Map<String, Integer>) model.get("termos");
        assertThat(tagCloud, notNullValue());
        assertThat(tagCloud.size(), equalTo(2));
    }
}