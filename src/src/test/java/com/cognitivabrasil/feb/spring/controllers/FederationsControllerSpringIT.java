package com.cognitivabrasil.feb.spring.controllers;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cognitivabrasil.feb.AppConfig;
import com.cognitivabrasil.feb.data.entities.SubFederacao;
import com.cognitivabrasil.feb.spring.validador.SubFederacaoValidador;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class })
@ActiveProfiles("test")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@DirtiesContext
public class FederationsControllerSpringIT extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    @InjectMocks
    FederationsController controller;
    
    @Mock
    SubFederacaoValidador validator;

    private MockMvc mockMvc;

    @Before
    public void setup() {

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testPost() throws Exception {
        ArgumentCaptor<SubFederacao> subFed = ArgumentCaptor.forClass(SubFederacao.class);
        
        mockMvc.perform(post("/admin/federations/new")
                .param("name", "testName")
                .param("descricao", "Federação")
                .param("url", "http://blabla"));
        
        verify(validator).validate(subFed.capture(), any());

        assertThat(subFed.getValue().getDescricao(), equalTo("Federação"));
    }
}