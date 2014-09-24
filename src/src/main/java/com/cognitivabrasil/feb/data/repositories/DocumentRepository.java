/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitivabrasil.feb.data.repositories;

import com.cognitivabrasil.feb.data.entities.Document;
import com.cognitivabrasil.feb.data.entities.Repositorio;
import com.cognitivabrasil.feb.data.entities.RepositorioSubFed;

import java.util.List;
import org.joda.time.DateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * <b>ATENTION!</b>
 *
 * Beware the inconsistency between from and until ({@link #from(DateTime, Pageable)},
 * {@link #countFrom(DateTime)}, {@link #until(DateTime, Pageable) }, {@link #countUntil(DateTime)},
 * {@link #betweenInclusive(DateTime, DateTime, Pageable)},
 * {@link #countBetweenInclusive(DateTime, DateTime)} in this repository.
 *
 * From is inclusive, until is NOT inclusive. This is necessary for us to ignore
 * the milliseconds portion of the timestamp.
 *
 * @author Marcos Freitas Nunes <marcos@cognitivabrasil.com.br>
 * @author Paulo Schreiner <paulo@cognitivabrasil.com.br>
 */
public interface DocumentRepository extends JpaRepository<Document, Integer> {

    public List<Document> findByDeletedIsFalse();

    public Page<Document> findByDeletedIsFalse(Pageable pageable);

    public Document findByObaaEntry(String entry);

    public Document findById(int id);

    @Modifying
    @Transactional
    @Query("delete from Document d where d.repositorio = :repositorio")
    int deleteByRepositorio(@Param("repositorio") Repositorio repositorio);
    
    @Modifying
    @Transactional
    @Query("delete from Document d where d.repositorioSubFed = :repositorio")
    int deleteBySubRepository(@Param("repositorio") RepositorioSubFed repositorio);

    @Query("SELECT count(*) FROM Document d WHERE deleted is false")
    public long countDeletedFalse();
    
    @Query("SELECT count(*) FROM Document d WHERE deleted is :deleted AND d.repositorio = :repositorio")
    public Integer countFromRepository(@Param("repositorio") Repositorio repositorio,@Param("deleted") boolean deleted);
    
    @Query("select count(*) from Document doc WHERE doc.repositorioSubFed = :repositorio AND doc.deleted = :deleted")
    public Integer countFromSubRepository(@Param("repositorio") RepositorioSubFed repositorio, @Param("deleted") boolean deleted);

    //metodos para o OAI
    // Hack: we add one second to the date and use non-inclusive comparison for until, and 
    // use incluse queries for from, in this way we ignore fractions of seconds.
    @Query("SELECT d FROM Document d WHERE created >= ?1")
    public Page<Document> from(DateTime dateTime, Pageable p);

    @Query("SELECT count(*) FROM Document d WHERE created >= ?1")
    public Integer countFrom(DateTime dateTime);

    @Query("SELECT d FROM Document d WHERE created < ?1")
    public Page<Document> until(DateTime dateTime, Pageable p);

    @Query("SELECT count(*) FROM Document d WHERE created < ?1")
    public Integer countUntil(DateTime dateTime);

    @Query("SELECT d FROM Document d WHERE created >= ?1 AND created < ?2")
    public Page<Document> betweenInclusive(DateTime from, DateTime until, Pageable p);

    @Query("SELECT count(*) FROM Document d WHERE created >= ?1 AND created < ?2")
    public Integer countBetweenInclusive(DateTime from, DateTime until);

    @Query("SELECT d FROM Document d")
    public Page<Document> all(Pageable pageable);

    @Query("SELECT count(*) FROM Document d")
    public Integer countInt();

}
