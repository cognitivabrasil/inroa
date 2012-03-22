/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.util.List;

/**
 *
 * @author Marcos
 */
public interface SubFederacaoDAO {
    
    /**
     * 
     * @return Todas Subfedera&ccedil;&otilde;es
     */
    List<SubFederacao> getAll();
    
    /**
     * Busca Subfedera&ccedil;&atilde;o espec&iacute;fica
     * @param id id da Subfedera&ccedil;&atilde;o
     * @return SubFederacao
     */
    SubFederacao get(int id);
    
    /**
     * Busca Subfedera&ccedil;&atilde;o espec&iacute;fica
     * @param nome  nome da Subfedera&ccedil;&atilde;o
     * @return SubFederacao
     */
    SubFederacao get(String nome);
    
    
    
        /**
     * Cria ou atualiza uma subfedera&ccedil;&atilde;o.
     * 
     * @param s subfedera&ccedil;&atilde;o que ser&aacute; criada ou atualizada.
     */
    void save(SubFederacao s);
       
       /**
        * Deletes a repository.
        * 
        * @param r repository to be deleted
        */
       void delete(SubFederacao s);
       
       //TODO: Tem que ter um metodo que teste se a subfederacao com o nome informado ja existe na base.
    
}
