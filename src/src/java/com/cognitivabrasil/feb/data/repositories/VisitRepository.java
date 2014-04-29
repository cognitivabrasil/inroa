/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cognitivabrasil.feb.data.repositories;

import com.cognitivabrasil.feb.data.entities.Visita;
import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Marcos Freitas Nunes <marcos@cognitivabrasil.com.br>
 */

public interface VisitRepository extends JpaRepository<Visita, Integer> {
    
    @Query("SELECT count(*) FROM Visita d WHERE horario >= ?1 AND horario < ?2")
    public Integer countBetween(DateTime from, DateTime until);
    
}
