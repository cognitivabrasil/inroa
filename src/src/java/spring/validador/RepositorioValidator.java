/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spring.validador;

import java.net.MalformedURLException;
import java.net.URL;
import modelos.Repositorio;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Respons&aacute;vel por validar os dados submetidos para um Repositorio
 * @author Marcos Nunes <marcosn@gmail.com>
 */
@Component
public class RepositorioValidator implements Validator  {
    
    
    public boolean supports(Class clazz) {
        return Repositorio.class.isAssignableFrom(clazz);
    }
    
    public void validate(Object target, Errors errors) {
        
        ValidationUtils.rejectIfEmpty(errors, "nome",
                "required.nome", "É necessário informar um nome.");
        ValidationUtils.rejectIfEmpty(errors, "descricao",
                "required.descricao", "É necessário informar uma descrição.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mapeamento.id",
                "required.mapeamento.id", "É necessário informar um mapeamento.");
        
        Repositorio rep = (Repositorio) target;
        String url = rep.getUrl();
        int idPadraoMetadados = rep.getPadraoMetadados().getId();
        int periodicidade = rep.getPeriodicidadeAtualizacao();


        if (url != null) {
            try{
            new URL(url);
            }catch (MalformedURLException m){
                    errors.rejectValue("url","invalid.url", "Informe uma url válida.");
            }              
        }
        
        if(idPadraoMetadados <= 0){
            errors.rejectValue("padraoMetadados.id","invalid.padraoMetadados.id", "Informe um padrão de metadados.");
        }
        
        if(periodicidade <= 0){
            errors.rejectValue("periodicidadeAtualizacao","invalid.periodicidadeAtualizacao", "Informe uma periodicidade de atualização.");            
        }
        
    }
    
}
