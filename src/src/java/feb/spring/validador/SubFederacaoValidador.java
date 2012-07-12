/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feb.spring.validador;

import java.net.MalformedURLException;
import java.net.URL;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import feb.data.entities.SubFederacao;

/**
 *
 * @author Marcos Nunes <marcosn@gmail.com>
 */
@Component
public class SubFederacaoValidador implements Validator {

    public boolean supports(Class clazz) {
        return SubFederacao.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {
        
        ValidationUtils.rejectIfEmpty(errors, "nome",
                "required.nome", "É necessário informar um nome.");
        ValidationUtils.rejectIfEmpty(errors, "descricao",
                "required.descricao", "É necessário informar uma descrição.");

        SubFederacao subfed = (SubFederacao) target;
        String url = subfed.getUrl();

        if (url != null) {
            try{
            new URL(url);
            }catch (MalformedURLException m){
                    errors.rejectValue("url","invalid.url", "Informe uma url válida.");
            }
              
        }
    }
}
