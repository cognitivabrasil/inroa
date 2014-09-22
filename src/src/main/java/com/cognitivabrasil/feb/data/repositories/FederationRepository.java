/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cognitivabrasil.feb.data.repositories;

import com.cognitivabrasil.feb.data.entities.SubFederacao;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Marcos Freitas Nunes <marcos@cognitivabrasil.com.br>
 */
public interface FederationRepository extends JpaRepository<SubFederacao, Integer> {
    public SubFederacao findByNameIgnoreCase(String name);
}
