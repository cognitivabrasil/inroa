/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitivabrasil.feb.data.repositories;

import com.cognitivabrasil.feb.data.entities.DocumentoReal;

import java.util.List;
import org.joda.time.DateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * <b>ATENTION!</b>
 *
 * Beware the inconsistency between from and until ({@link #from(DateTime, Pageable)},
 * {@link #countFrom(DateTime)}, {@link #until()}, {@link #countUntil(DateTime)},
 * {@link #betweenInclusive(DateTime, DateTime, Pageable)},
 * {@link #countBetweenInclusive(DateTime, DateTime)} in this repository.
 *
 * From is inclusive, until is NOT inclusive. This is necessary for us to ignore
 * the milliseconds portion of the timestamp.
 *
 * @author Marcos Freitas Nunes <marcos@cognitivabrasil.com.br>
 * @author Paulo Schreiner <paulo@cognitivabrasil.com.br>
 */
public interface DocumentRepository extends JpaRepository<DocumentoReal, Integer> {

    public List<DocumentoReal> findByDeletedIsFalse();
    public Page<DocumentoReal> findByDeletedIsFalse(Pageable pageable);
    public DocumentoReal findByObaaEntry(String entry);
    public DocumentoReal findById(int id);
    
    @Query("SELECT count(*) FROM DocumentoReal d WHERE deleted is false")
    public long countDeletedFalse();
    
    //metodos para o OAI
    
    // Hack: we add one second to the date and use non-inclusive comparison for until, and 
    // use incluse queries for from, in this way we ignore fractions of seconds.
    @Query("SELECT d FROM DocumentoReal d WHERE created >= ?1")
    public Page<DocumentoReal> from(DateTime dateTime, Pageable p);
    
    @Query("SELECT count(*) FROM DocumentoReal d WHERE created >= ?1")
    public Integer countFrom(DateTime dateTime);
    
    @Query("SELECT d FROM DocumentoReal d WHERE created < ?1")
    public Page<DocumentoReal> until(DateTime dateTime, Pageable p);

    @Query("SELECT count(*) FROM DocumentoReal d WHERE created < ?1")
    public Integer countUntil(DateTime dateTime);

    @Query("SELECT d FROM DocumentoReal d WHERE created >= ?1 AND created < ?2")
    public Page<DocumentoReal> betweenInclusive(DateTime from, DateTime until, Pageable p);

    @Query("SELECT count(*) FROM DocumentoReal d WHERE created >= ?1 AND created < ?2")
    public Integer countBetweenInclusive(DateTime from, DateTime until);

    @Query("SELECT d FROM DocumentoReal d")
    public Page<DocumentoReal> all(Pageable pageable);

    @Query("SELECT count(*) FROM DocumentoReal d")
    public Integer countInt();
    
}
