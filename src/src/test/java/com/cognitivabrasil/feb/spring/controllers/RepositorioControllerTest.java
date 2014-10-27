/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitivabrasil.feb.spring.controllers;

import com.cognitivabrasil.feb.data.entities.Mapeamento;
import com.cognitivabrasil.feb.data.entities.PadraoMetadados;
import com.cognitivabrasil.feb.data.entities.Repositorio;
import com.cognitivabrasil.feb.data.services.MetadataRecordService;
import com.cognitivabrasil.feb.data.services.RepositoryService;
import java.util.ArrayList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

/**
 *
 * @author Marcos Freitas Nunes <marcos@cognitivabrasil.com.br>
 */
public class RepositorioControllerTest {

    private RepositoriesController controller;
    private Repositorio r;
    private ExtendedModelMap model;
    private MetadataRecordService padraoService;
    private RepositoryService repService;
    private BindingResult bindingResult;

    @Before
    public void setup() {
        controller = new RepositoriesController();
        r = new Repositorio();
        r.setName("rep do marcos");
        r.setDescricao("marcos");
        r.setMetadataPrefix("obaa");
        r.setUrl("http://marcos.nunes");
        Mapeamento m = new Mapeamento();
        m.setId(1);
        r.setMapeamento(m);
        PadraoMetadados p = new PadraoMetadados();
        p.setId(1);
        r.setPadraoMetadados(p);
        padraoService = mock(MetadataRecordService.class);
        repService = mock(RepositoryService.class);
        ReflectionTestUtils.setField(controller, "padraoServ", padraoService);
        ReflectionTestUtils.setField(controller, "repServ", repService);
        model = new ExtendedModelMap();
        bindingResult = new BeanPropertyBindingResult(r, "rep");
    }

    @Test
    public void testValidationSuccess() {
        when(repService.get(r.getName())).thenReturn(null);
        String result = controller.newDo(r, bindingResult, model);
        assertThat(result, equalTo("redirect:/admin/fechaRecarrega"));
        
        assertThat(bindingResult.hasErrors(), equalTo(false));
    }
    
    @Test
    public void testValidationWithoutUrlAndMetadata() {
        
        when(padraoService.getAll()).thenReturn(new ArrayList<>());
        r.setUrl("");
        r.setPadraoMetadados(null);
        String result = controller.newDo(r, bindingResult, model);
        assertThat(result, equalTo("admin/repositories/new"));
        
        assertThat(bindingResult.hasErrors(), equalTo(true));
        assertThat(bindingResult.getErrorCount(), equalTo(2));
        assertThat(bindingResult.getAllErrors().get(0).getDefaultMessage(), equalTo("Informe uma url válida"));
        assertThat(bindingResult.getAllErrors().get(1).getDefaultMessage(), equalTo("Informe um padrão de metadados"));
        
        assertThat(model.get("padraoMetadados"), notNullValue());
        assertThat(model.get("repModel"), notNullValue());
    }
    
    @Test
    public void testNewRepExistingName(){
        when(repService.get(r.getName())).thenReturn(r);
        
        String result = controller.newDo(r, bindingResult, model);
        assertThat(result, equalTo("admin/repositories/new"));
        
        assertThat(bindingResult.hasErrors(), equalTo(true));
        assertThat(bindingResult.getErrorCount(), equalTo(1));
        assertThat(bindingResult.getAllErrors().get(0).getDefaultMessage(), 
                equalTo("Já existe um repositório com esse nome"));
        
        assertThat(model.get("padraoMetadados"), notNullValue());
        assertThat(model.get("repModel"), notNullValue());
    }

}
