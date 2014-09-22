/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitivabrasil.feb.spring.controllers;

import com.cognitivabrasil.feb.AppConfig;
import com.cognitivabrasil.feb.data.services.TagCloudService;
import com.cognitivabrasil.feb.util.Message;

import java.util.Map;
import java.util.Map.Entry;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ExtendedModelMap;

/**
 *
 * @author Marcos Freitas Nunes <marcos@cognitivabrasil.com.br>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class })
@ActiveProfiles("test")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class StatisticsControllerIT extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    StatisticsController controller;
    @Autowired
    private TagCloudService tagCloud;

    @Test
    public void testStatistics() {
        ExtendedModelMap uiModel = new ExtendedModelMap();

        String result = controller.statistics(uiModel);
        assertThat(result, equalTo("admin/statistics"));
        Long sizeObj = (Long) uiModel.get("totalObj");
        Map<String, Integer> repositories = (Map<String, Integer>) uiModel.get("repositories");
        Map<String, Integer> federations = (Map<String, Integer>) uiModel.get("federations");

        assertThat(sizeObj, equalTo(6L));
        assertThat(repositories, notNullValue());
        assertThat(federations, notNullValue());

        assertThat(repositories.size(), equalTo(4));
        assertThat(federations.size(), equalTo(4));

        for (Entry<String, Integer> map : repositories.entrySet()) {
            if(map.getKey().equals("Cesta")) {
                assertThat(map.getValue(), equalTo(4));
            } else {
                assertThat(map.getValue(), equalTo(0));
            }
        }
        
        for (Entry<String,Integer> map : federations.entrySet()) {
            if(map.getKey().equals("UFRGS")){
                assertThat(map.getValue(), equalTo(2));
            }else{
                assertThat(map.getValue(), equalTo(0));
            }
        }
    }
    
    @Ignore("Tem que implementar o searches em JPA para funcionar o teste")
    @Test
    public void testDeleteTag() throws Exception{
        Map<String, Integer> mapTagCloud = tagCloud.getTagCloud();
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(post("/admin/statistics/deletetag").param("tag", "teste"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("type", is(Message.SUCCESS)));
        
        assertThat(tagCloud.getTagCloud().size(), equalTo(mapTagCloud.size()-1));
    }
}