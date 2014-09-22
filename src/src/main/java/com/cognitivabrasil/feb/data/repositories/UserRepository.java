/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitivabrasil.feb.data.repositories;

import com.cognitivabrasil.feb.data.entities.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Marcos Freitas Nunes <marcos@cognitivabrasil.com.br>
 */
@Repository
public interface UserRepository extends JpaRepository<Usuario,Integer>{
    @Override
    public List<Usuario> findAll();
    
    Usuario findByUsername(final String username);
    
}
