/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cognitivabrasil.feb.data.services;

import com.cognitivabrasil.feb.data.repositories.MetadataRecordRepository;
import com.cognitivabrasil.feb.data.entities.PadraoMetadados;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Marcos Freitas Nunes <marcos@cognitivabrasil.com.br>
 */
@Service
public class MetadataRecordServiceImpl implements MetadataRecordService{
    @Autowired
    private MetadataRecordRepository rep;

    @Override
    public List<PadraoMetadados> getAll() {
        return rep.findAll();
    }

    @Override
    public void save(PadraoMetadados r) {
        rep.save(r);
    }

    @Override
    public void delete(PadraoMetadados r) {
        rep.delete(r);
    }

    @Override
    public PadraoMetadados get(int id) {
        return rep.findOne(id);
    }

    @Override
    public PadraoMetadados get(String name) {
        return rep.findByName(name);
    }   
    
}
