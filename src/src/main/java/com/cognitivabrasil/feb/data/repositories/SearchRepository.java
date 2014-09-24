/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitivabrasil.feb.data.repositories;

import com.cognitivabrasil.feb.data.entities.Search;
import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Marcos Freitas Nunes <marcos@cognitivabrasil.com.br>
 */
public interface SearchRepository extends JpaRepository<Search, Integer> {

    @Modifying
    @Transactional
    @Query("delete from Search where text = :text")
    int deleteByText(@Param("text") String text);

    @Modifying
    @Transactional
    @Query("delete from Search where created < ?1")
    int deleteOlderThan(DateTime date);
}
