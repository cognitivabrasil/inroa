package com.cognitivabrasil.feb.spring;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

import javax.servlet.Filter;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.cognitivabrasil.feb.AppConfig;
import com.cognitivabrasil.feb.WebConfig;
import com.cognitivabrasil.feb.data.services.TagCloudService;
import com.cognitivabrasil.feb.ferramentaBusca.indexador.Indexador;
import com.cognitivabrasil.feb.spring.controllers.AdminController;
import com.cognitivabrasil.feb.spring.controllers.FEBController;

/**
 * http://spring.io/blog/2014/05/23/preview-spring-security-test-web-security
 *
 * @author Paulo Schreiner
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class, WebConfig.class})
@WebAppConfiguration
@ActiveProfiles("test")
@Ignore("Da merda nos testes se rodar todos, rodando individual passa")
public class SpringSecurityIT{
    @Autowired
    private WebApplicationContext context;
    
    @Autowired
    Md5PasswordEncoder md5Encoder;

    @Autowired
    private Filter springSecurityFilterChain;

    private MockMvc mvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        mvc = MockMvcBuilders.webAppContextSetup(context).addFilters(springSecurityFilterChain).build();
    }

    @Test
    public void loginCorretoAdminFunciona() throws Exception {
        mvc.perform(formLogin("/login").user("admin").password("teste"))
                .andExpect(authenticated());
    }

    @Test
    @Ignore("bug no spring-security test, testar de novo com a versão 4.0.0 quando sair")
    public void loginCorretoAdminPossuiRolesCorretos() throws Exception {
        mvc.perform(formLogin("/login").user("admin").password("teste"))
            .andExpect(authenticated().withRoles(
                  "CHANGE_DATABASE",
                  "UPDATE",
                  "MANAGE_MAPPINGS",
                  "MANAGE_STATISTICS",
                  "MANAGE_REP",
                  "MANAGE_METADATA",
                  "MANAGE_USERS"
             ));
    }

    @Test
    public void loginIncorretoAdminFalha() throws Exception {
        mvc.perform(formLogin("/login").user("admin").password("dfdf"))
                .andExpect(unauthenticated());
    }
    
    @Test 
    @Ignore
    public void md5EncoderTest() {
        assertThat(md5Encoder.encodePassword("teste", null), equalTo("bla"));
    }
}