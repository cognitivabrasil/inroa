/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feb.spring.validador;

import com.cognitivabrasil.feb.data.entities.PadraoMetadados;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author Marcos Nunes <marcosn@gmail.com>
 */
@Component
public class PadraoValidator implements Validator {


    @Override
    public boolean supports(Class clazz) {
        return PadraoMetadados.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ValidationUtils.rejectIfEmpty(errors, "name",
                "required.name", "É necessário informar um nome.");
        ValidationUtils.rejectIfEmpty(errors, "metadataPrefix",
                "required.metadataPrefix", "É necessário informar um metadataPrefix.");

    }
}
