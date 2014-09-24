/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitivabrasil.feb.spring.validador;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author marcos
 */
@Component
public class InfoBDValidator implements Validator {
    public boolean supports(Class clazz) {
        return false;
    }
    
    @Override
    public void validate(Object target, Errors errors) {
        
        ValidationUtils.rejectIfEmpty(errors, "username",
                "required.usuario", "É necessário informar um usuário.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "database",
                "required.descricao", "É necessário informar o nome da base.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "host",
                "required.IP", "É necessário informar o ip.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "port",
                "required.IP", "É necessário informar a porta.");
        

    }
    
}
