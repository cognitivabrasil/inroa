/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feb.spring.validador;

import feb.data.entities.Consulta;
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
        return Consulta.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        //ValidationUtils.rejectIfEmpty(errors, "consulta", "required.consulta", "É necessário informar uma consulta.");
        
        Consulta busca = (Consulta) target;
        if(busca.getConsulta().trim().isEmpty() && busca.getAutor().trim().isEmpty()){
            errors.rejectValue("consulta","invalid.consulta", "Nenhuma consulta foi informada.");
        }
    }
}
