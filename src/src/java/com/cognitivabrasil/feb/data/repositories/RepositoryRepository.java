/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitivabrasil.feb.data.repositories;

import com.cognitivabrasil.feb.data.entities.Repositorio;
import java.util.List;
import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Marcos Freitas Nunes <marcos@cognitivabrasil.com.br>
 */
public interface RepositoryRepository extends JpaRepository<Repositorio, Integer> {

    public Repositorio findByNameIgnoreCase(String name);

    public List<Repositorio> findByUltimaAtualizacaoLessThanOrUltimaAtualizacaoIsNull(DateTime d);
}
