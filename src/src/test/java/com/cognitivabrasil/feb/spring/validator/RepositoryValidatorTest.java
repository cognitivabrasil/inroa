/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitivabrasil.feb.spring.validator;

import com.cognitivabrasil.feb.data.entities.Mapeamento;
import com.cognitivabrasil.feb.data.entities.PadraoMetadados;
import com.cognitivabrasil.feb.data.entities.Repositorio;
import com.cognitivabrasil.feb.spring.validador.RepositorioValidator;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

/**
 *
 * @author Marcos Freitas Nunes <marcos@cognitivabrasil.com.br>
 */
public class RepositoryValidatorTest {

    private Repositorio r;
    private RepositorioValidator validator;
    
    @Before
    public void setup() {
        validator = new RepositorioValidator();
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
        
    }

    @Test
    public void testValidationSuccess() {
        BindingResult bindingResult = new BeanPropertyBindingResult(r, "rep");
        validator.validate(r, bindingResult);
        assertThat(bindingResult.hasErrors(), equalTo(false));
    }

    @Test
    public void testValidationNameLegth() {
        r.setName("nome com mais de 30 caracteres.");
        assertThat(r.getName().length() > 30, equalTo(true));
        
        BindingResult bindingResult = new BeanPropertyBindingResult(r, "rep");
        validator.validate(r, bindingResult);
        
        assertThat(bindingResult.hasErrors(), equalTo(true));
        assertThat(bindingResult.getErrorCount(), equalTo(1));
        assertThat(bindingResult.getAllErrors().get(0).getDefaultMessage(),
                equalTo("O nome excede " + Repositorio.LIMIT_NAME + " caracteres"));
    }
    
    @Test
    public void testValidationName(){
        r.setName("");
        BindingResult bindingResult = new BeanPropertyBindingResult(r, "rep");
        validator.validate(r, bindingResult);
        assertThat(bindingResult.getErrorCount(), equalTo(1));
        assertThat(bindingResult.getAllErrors().get(0).getDefaultMessage(),
                equalTo("É necessário informar um nome"));
    }
    
    @Test
    public void testAllErrors(){

        r = new Repositorio();
        
        BindingResult b = new BeanPropertyBindingResult(r, "rep");
        validator.validate(r, b);
        
        assertThat(b.getErrorCount(), equalTo(6));
        assertThat(b.getAllErrors().get(0).getDefaultMessage(),
                equalTo("É necessário informar um nome"));
        assertThat(b.getAllErrors().get(1).getDefaultMessage(),
                equalTo("É necessário informar uma descrição"));
        assertThat(b.getAllErrors().get(2).getDefaultMessage(),
                equalTo("É necessário informar um mapeamento"));
        assertThat(b.getAllErrors().get(3).getDefaultMessage(),
                equalTo("É necessário informar um metadataPrefix"));
        assertThat(b.getAllErrors().get(4).getDefaultMessage(),
                equalTo("Informe uma url válida"));
        assertThat(b.getAllErrors().get(5).getDefaultMessage(),
                equalTo("Informe um padrão de metadados"));
    }
    
    @Test
    public void testUrl(){
        r.setUrl("http://");
        
        BindingResult bindingResult = new BeanPropertyBindingResult(r, "rep");
        validator.validate(r, bindingResult);
        assertThat(bindingResult.getErrorCount(), equalTo(1));
        assertThat(bindingResult.getAllErrors().get(0).getDefaultMessage(), equalTo("Informe uma url válida"));
    }
}
