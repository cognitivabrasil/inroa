package com.cognitivabrasil.feb.spring.controllers;

import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

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

import com.cognitivabrasil.feb.AppConfig;
import com.cognitivabrasil.feb.data.entities.Consulta;
import com.cognitivabrasil.feb.ferramentaBusca.Recuperador;

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
    Recuperador recuperador;

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
        ArgumentCaptor<Consulta> consulta = ArgumentCaptor.forClass(Consulta.class);
        
        mockMvc.perform(get("/resultado?consulta=teste&format=abc&format=xxx")
                );
        
        verify(recuperador).busca(consulta.capture());

        assertThat(consulta.getValue().getFormat(), hasItems("abc", "xxx"));
    }
}