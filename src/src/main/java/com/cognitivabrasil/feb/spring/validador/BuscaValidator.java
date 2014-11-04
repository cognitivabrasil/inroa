/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitivabrasil.feb.spring.validador;

import com.cognitivabrasil.feb.ferramentaBusca.ConsultaFeb;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Respons&aacute;vel por validar os dados submetidos para uma busca
 *
 * @author Marcos Nunes <marcosn@gmail.com>
 */
@Component
public class BuscaValidator implements Validator {

    @Override
    public boolean supports(Class clazz) {
        return ConsultaFeb.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ConsultaFeb busca = (ConsultaFeb) target;

        if (busca.isEmpty()) {
            errors.rejectValue("consulta", "invalid.consulta", "Nenhuma consulta foi informada.");
        }
    }
}
