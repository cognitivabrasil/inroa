/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feb.spring.validador;

import java.net.MalformedURLException;
import java.net.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import feb.data.entities.PadraoMetadados;
import feb.data.interfaces.PadraoMetadadosDAO;

/**
 *
 * @author Marcos Nunes <marcosn@gmail.com>
 */
@Component
public class PadraoValidator implements Validator {


    public boolean supports(Class clazz) {
        return PadraoMetadados.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {

        ValidationUtils.rejectIfEmpty(errors, "nome",
                "required.nome", "É necessário informar um nome.");
        ValidationUtils.rejectIfEmpty(errors, "metadataPrefix",
                "required.metadataPrefix", "É necessário informar um metadataPrefix.");
        ValidationUtils.rejectIfEmpty(errors, "namespace",
                "required.namespace", "É necessário informar um namespace.");
        ValidationUtils.rejectIfEmpty(errors, "atributos",
                "required.atributos", "É necessário informar os atributos.");

    }
}