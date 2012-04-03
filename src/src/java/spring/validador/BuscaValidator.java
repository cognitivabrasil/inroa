/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spring.validador;

import modelos.Busca;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Respons&aacute;vel por validar os dados submetidos para uma busca
 *
 * @author Marcos Nunes <marcosn@gmail.com>
 */
@Component
public class BuscaValidator implements Validator {

    public boolean supports(Class clazz) {
        return Busca.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "consulta",
                "required.consulta", "É necessário informar uma consulta.");
    }
}
