/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cognitivabrasil.feb.data.services;

import com.cognitivabrasil.feb.data.repositories.RepositoryRepository;
import com.cognitivabrasil.feb.data.entities.Repositorio;
import java.util.List;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Marcos Freitas Nunes <marcos@cognitivabrasil.com.br>
 */
@Service
public class RepositoryServiceImpl implements RepositoryService{
    @Autowired
    RepositoryRepository rep;

    @Override
    public List<Repositorio> getAll() {
        return rep.findAll();
    }

    @Override
    public void save(Repositorio r) {
        rep.save(r);
    }

    @Override
    public void delete(Repositorio r) {
        rep.delete(r);
    }

    @Override
    public Repositorio get(int id) {
        return rep.findOne(id);
    }

    @Override
    public Repositorio get(String name) {
        return rep.findByNameIgnoreCase(name);
    }

    @Override
    public void updateNotBlank(Repositorio r2) {
        if(r2.getId() == null) {
            throw new IllegalArgumentException("Cant update a new repository, save it instead");
        }
        Repositorio r = get(r2.getId());
        r.merge(r2);
        rep.save(r);
    }

    @Override
    public List<Repositorio> getOutDated() {
        DateTime date = DateTime.now().minusDays(1).minusHours(4);
        
        return rep.findByUltimaAtualizacaoLessThanOrUltimaAtualizacaoIsNull(date);
    }
    
}
