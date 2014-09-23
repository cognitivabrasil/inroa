package com.cognitivabrasil.feb.spring;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

import java.util.Arrays;

import javax.servlet.Filter;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.cognitivabrasil.feb.AppConfig;
import com.cognitivabrasil.feb.WebConfig;
import com.cognitivabrasil.feb.ferramentaBusca.indexador.Indexador;
import com.cognitivabrasil.feb.spring.controllers.AdminController;

/**
 * http://spring.io/blog/2014/05/23/preview-spring-security-test-web-security
 * 
 * @author Paulo Schreiner
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class, WebConfig.class })
@WebAppConfiguration
@ActiveProfiles("test")
public class SpringSecurityTest {
    @Mock
    private Indexador indexador;
    
    @Autowired
    private WebApplicationContext context;
    
    @Autowired
    @InjectMocks
    private AdminController adminController;

    @Autowired
    private Filter springSecurityFilterChain;

    private MockMvc mvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        
        mvc = MockMvcBuilders.webAppContextSetup(context).addFilters(springSecurityFilterChain).build();
    }

    @Test
    public void porDefaultNaoDeixaAccessarPaginaDeAdmin() throws Exception {
        mvc.perform(get("/admin/"))
            .andExpect(unauthenticated())
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    public void usuarioLogadoConsegueVerPaginaDeAdmin() throws Exception {
        mvc.perform(get("/admin").with(user("testuser").roles("ANY"))).andExpect(authenticated())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void usuarioSemPermissaoChangeDatabaseNaoConsegueAlterar() throws Exception {
        mvc.perform(post("/admin/alterDB").with(csrf()).with(user("testuser").roles("ANY"))).andExpect(authenticated())
                .andExpect(status().isForbidden());
    }
    
    @Test
    public void usuarioComPermissaoChangeDatabaseConsegueAlterar() throws Exception {
        mvc.perform(post("/admin/alterDB").with(csrf()).with(user("myuser").roles("PERM_CHANGE_DATABASE")))
            .andExpect(authenticated())
            .andExpect(status().isOk());
    }
    
    @Test
    public void usuarioSemPermissaoNaoConsegueAtualizarRepositorios() throws Exception {
        mvc.perform(post("/admin/repositories/1/update")
                .with(csrf())
                .with(user("testuser").roles("PERM_CHANGE_DATABASE"))
        )
        .andExpect(status().isForbidden());
    }
    
    @Test
    public void usuarioComPermissaoConsegueAtualizarRepositorios() throws Exception {
        mvc.perform(post("/admin/repositories/1/update")
                .with(csrf())
                .with(user("testuser").roles("PERM_UPDATE"))
                .param("apagar", "false")
        )
        .andExpect(status().isOk());
    }
    
    @Test
    public void usuarioSemPermissaoNaoConsegueAtualizarFederacao() throws Exception {
        mvc.perform(post("/admin/federations/1/update")
                .with(csrf())
                .with(user("testuser").roles("PERM_CHANGE_DATABASE"))
        )
        .andExpect(status().isForbidden());
    }
    
    @Test
    public void usuarioComPermissaoConsegueAtualizarFederacao() throws Exception {
        mvc.perform(post("/admin/federations/1/update")
                .with(csrf())
                .with(user("testuser").roles("PERM_UPDATE"))
                .param("apagar", "false")
        )
        .andExpect(status().isOk());
    }
    
    @Test
    public void usuarioSemPermissaoNaoConsegueRecalcularIndice() throws Exception {
        mvc.perform(post("/admin/efetuaRecalculoIndice")
                .with(csrf())
                .with(user("testuser").roles("PERM_CHANGE_DATABASE"))
        )
        .andExpect(status().isForbidden());
    }
    
    @Test
    public void usuarioComPermissaoConsegueRecalcularIndice() throws Exception {
        mvc.perform(post("/admin/efetuaRecalculoIndice")
                .with(csrf())
                .with(user("testuser").roles("PERM_UPDATE"))
                .param("apagar", "false")
        )
        .andExpect(status().isOk());
    }
    
    @Test
    public void usuarioComPermissaoConsegueCriarRepositorio() throws Exception {
        mvc.perform(post("/admin/repositories/new")
                .with(csrf())
                .with(user("testuser").roles("PERM_MANAGE_REP"))
                .param("padraoMetadados.id", "1")

        )
        .andExpect(status().isOk());    
    }
    
    @Test
    public void usuarioSemPermissaoNaoConsegueCriarRepositorio() throws Exception {
        mvc.perform(post("/admin/repositories/new")
                .with(csrf())
                .with(user("testuser").roles("PERM_MANAGE_USERS"))
                .param("padraoMetadados.id", "1")

        )
        .andExpect(status().isForbidden());    
    }
    
    @Test
    public void usuarioComPermissaoConsegueCriarFederacao() throws Exception {
        mvc.perform(post("/admin/federations/new")
                .with(csrf())
                .with(user("testuser").roles("PERM_MANAGE_REP"))
        )
        .andExpect(status().isOk());    
    }
    
    @Test
    public void usuarioSemPermissaoNaoConsegueCriarFederacao() throws Exception {
        mvc.perform(post("/admin/federations/new")
                .with(csrf())
                .with(user("testuser").roles("PERM_MANAGE_USERS"))
        )
        .andExpect(status().isForbidden());    
    }
    
    @Test
    public void usuarioComPermissaoConsegueCriarMapeamento() throws Exception {
        mvc.perform(post("/admin/mapeamentos/new")
                .with(csrf())
                .with(user("testuser").roles("PERM_MANAGE_MAPPINGS"))
        )
        .andExpect(status().isOk());    
    }
    
    @Test
    public void usuarioSemPermissaoNaoConsegueCriarMapeamento() throws Exception {
        mvc.perform(post("/admin/mapeamentos/new")
                .with(csrf())
                .with(user("testuser").roles("PERM_MANAGE_USERS"))
        )
        .andExpect(status().isForbidden());    
    }
    
    @Test
    public void usuarioComPermissaoConsegueCriarMetadados() throws Exception {
        mvc.perform(post("/admin/metadataStandard/new")
                .with(csrf())
                .with(user("testuser").roles("PERM_MANAGE_METADATA"))
        )
        .andExpect(status().isOk());    
    }
    
    @Test
    public void usuarioSemPermissaoNaoConsegueCriarMetadados() throws Exception {
        mvc.perform(post("/admin/metadataStandard/new")
                .with(csrf())
                .with(user("testuser").roles("PERM_MANAGE_USERS"))
        )
        .andExpect(status().isForbidden());    
    }    
    
    @Test
    public void usuarioComPermissaoConsegueApagarTag() throws Exception {
        mvc.perform(post("/admin/statistics/deletetag")
                .with(csrf())
                .with(user("testuser").roles("PERM_MANAGE_STATISTICS"))
                .param("tag", "toDelete")
        )
        .andExpect(status().isOk());    
    }
    
    @Test
    public void usuarioSemPermissaoNaoConsegueApagarTag() throws Exception {
        mvc.perform(post("/admin/statistics/deletetag")
                .with(csrf())
                .with(user("testuser").roles("PERM_MANAGE_USERS"))
                .param("tag", "toDelete")
        )
        .andExpect(status().isForbidden());    
    } 
    
    @Test
    public void usuarioComPermissaoConsegueCriarUsuario() throws Exception {
        mvc.perform(post("/admin/users/new")
                .with(csrf())
                .with(user("testuser").roles("PERM_MANAGE_USERS"))
        )
        .andExpect(status().isOk());    
    }
    
    @Test
    public void usuarioSemPermissaoNaoConsegueCriarUsuario() throws Exception {
        mvc.perform(post("/admin/users/new")
                .with(csrf())
                .with(user("testuser").roles("PERM_MANAGE_METADATA"))
        )
        .andExpect(status().isForbidden());    
    }   
    
    @Test
    @Ignore("TODO")
    public void usuarioConsegueMudarPropriaSenha() {

    }

    @Test
    public void porDefaultDeixaAcessarTelaInicial() throws Exception {
        mvc.perform(get("/")).andExpect(status().is2xxSuccessful());
    }
    
    @Test
    public void loginCorretoAdminFunciona() throws Exception {
        mvc.perform(formLogin("/login").user("admin").password("teste"))
            .andExpect(authenticated());
    }
    
    @Test
    @Ignore("TODO")
    public void loginCorretoAdminPossuiRolesCorretos() throws Exception {

    }
    
    @Test
    public void loginIncorretoAdminFalha() throws Exception {
        mvc.perform(formLogin("/login").user("admin").password("dfdf"))
            .andExpect(unauthenticated());
    }

}
