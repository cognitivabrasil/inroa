/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cognitivabrasil.feb.data.services;

import com.cognitivabrasil.feb.data.repositories.FederationRepository;
import com.cognitivabrasil.feb.data.entities.SubFederacao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Marcos Freitas Nunes <marcos@cognitivabrasil.com.br>
 */
@Service
public class FederationServiceImpl implements FederationService{

    @Autowired
    FederationRepository rep;
    
    @Override
    public List<SubFederacao> getAll() {
        return rep.findAll();
    }

    @Override
    public SubFederacao get(int id) {
        return rep.findOne(id);
    }

    @Override
    public SubFederacao get(String name) {
        return rep.findByName(name);
    }

    @Override
    public void save(SubFederacao s) {
        rep.save(s);
    }

    @Override
    public void delete(SubFederacao s) {
        rep.delete(s);
    }

    @Override
    public void updateNotBlank(SubFederacao r2) {
        if (r2.getId() == null) {
            throw new IllegalArgumentException("Cant update a new SubFederation, save it instead");
        }
        SubFederacao r = get(r2.getId());
        r.merge(r2);
        rep.save(r);
    }
    
}
