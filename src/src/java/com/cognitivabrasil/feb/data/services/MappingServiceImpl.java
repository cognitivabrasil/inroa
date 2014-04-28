/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cognitivabrasil.feb.data.services;

import com.cognitivabrasil.feb.data.repositories.MappingRepository;
import com.cognitivabrasil.feb.data.entities.Mapeamento;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Marcos Freitas Nunes <marcos@cognitivabrasil.com.br>
 */
@Service
public class MappingServiceImpl implements MappingService{
    @Autowired
    private MappingRepository mapRep;

    @Override
    public List<Mapeamento> getAll() {
        return mapRep.findAll();
    }

    @Override
    public void save(Mapeamento m) {
        mapRep.save(m);
    }

    @Override
    public void delete(Mapeamento m) {
        mapRep.delete(m);
    }

    @Override
    public Mapeamento get(int id) {
        return mapRep.findOne(id);
    }

    @Override
    public boolean exists(String name) {
        return mapRep.findByName(name);
    }    
}