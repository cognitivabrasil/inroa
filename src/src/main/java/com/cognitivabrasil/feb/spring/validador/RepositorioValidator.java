/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitivabrasil.feb.spring.validador;

import com.cognitivabrasil.feb.data.entities.Repositorio;
import java.net.MalformedURLException;
import java.net.URL;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Respons&aacute;vel por validar os dados submetidos para um Repositorio
 *
 * @author Marcos Nunes <marcosn@gmail.com>
 */
@Component
public class RepositorioValidator implements Validator {

    @Override
    public boolean supports(Class clazz) {
        return Repositorio.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ValidationUtils.rejectIfEmpty(errors, "name",
                "required.name", "É necessário informar um nome");
        ValidationUtils.rejectIfEmpty(errors, "descricao",
                "required.descricao", "É necessário informar uma descrição");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mapeamento.id",
                "required.mapeamento.id", "É necessário informar um mapeamento");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "metadataPrefix",
                "required.metadataPrefix", "É necessário informar um metadataPrefix");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "url",
                "required.url", "Informe uma url válida");

        Repositorio rep = (Repositorio) target;
        String url = rep.getUrl();
        String name = rep.getName();

        if (name != null && name.length() > Repositorio.LIMIT_NAME) {
            errors.rejectValue("name", "invalid.name", "O nome excede " + Repositorio.LIMIT_NAME + " caracteres");
        }

        if (isNotBlank(url)) {
            if (url.replace("http://", "").isEmpty()) {
                errors.rejectValue("url", "invalid.url", "Informe uma url válida");
            } else {
                try {
                    new URL(url);
                } catch (MalformedURLException m) {
                    errors.rejectValue("url", "invalid.url", "Informe uma url válida");
                }
            }
        }

        if (rep.getPadraoMetadados() == null || rep.getPadraoMetadados().getId() <= 0) {
            errors.rejectValue("padraoMetadados.id", "invalid.padraoMetadados.id", "Informe um padrão de metadados");
        }
    }
}
