/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitivabrasil.feb.data.services;

import com.cognitivabrasil.feb.data.entities.SubFederacao;
import java.util.List;

/**
 *
 * @author Marcos
 */
public interface FederationService {

    /**
     *
     * @return Todas Subfedera&ccedil;&otilde;es
     */
    List<SubFederacao> getAll();

    /**
     * Busca Subfedera&ccedil;&atilde;o espec&iacute;fica
     *
     * @param id id da Subfedera&ccedil;&atilde;o
     * @return SubFederacao
     */
    SubFederacao get(int id);

    /**
     * Busca Subfedera&ccedil;&atilde;o espec&iacute;fica
     *
     * @param nome nome da Subfedera&ccedil;&atilde;o
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
     * Deletes a federation.
     *
     * @param s federation to be deleted
     */
    void delete(SubFederacao s);

    //TODO: Tem que ter um metodo que teste se a subfederacao com o nome informado ja existe na base.
    /**
     * Updates the federation with the same id as f2 safely, ignoring null and
     * blank values
     *
     * It does NOT merge the associated Documents.
     *
     * @param f2 A federation that we want to update.
     * @throws IllegalArgumentException when r2 is null
     */
    void updateNotBlank(SubFederacao f2);
    
    void deleteAllDocs(SubFederacao fed);
}
