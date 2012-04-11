/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spring.validador;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import postgres.SingletonConfig;

/**
 *
 * @author marcos
 */
@Component
public class InfoBDValidator implements Validator {
    public boolean supports(Class clazz) {
        return SingletonConfig.class.isAssignableFrom(clazz);
    }
    
    public void validate(Object target, Errors errors) {
        
        ValidationUtils.rejectIfEmpty(errors, "usuario",
                "required.usuario", "É necessário informar um usuário.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "base",
                "required.descricao", "É necessário informar o nome da base.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "IP",
                "required.IP", "É necessário informar o ip.");

        SingletonConfig subfed = (SingletonConfig) target;
        
        if(subfed.getPorta() == null || subfed.getPorta() <=0){
            errors.rejectValue("porta","invalid.porta", "Deve ser informada uma porta válida.");
        }
        

    }
    
}
