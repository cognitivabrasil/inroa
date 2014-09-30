/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitivabrasil.feb.data.services;

import com.cognitivabrasil.feb.data.entities.Usuario;
import com.cognitivabrasil.feb.data.repositories.MappingRepository;
import com.cognitivabrasil.feb.data.repositories.UserRepository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Marcos Freitas Nunes <marcos@cognitivabrasil.com.br>
 */
@Service("UserService")
public class UserServiceImpl implements UserService, UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserRepository userRep;
    
    @Autowired
    private MappingRepository mappingRep;

    @Override
    public Usuario authenticate(String login, String password) {

        if (login == null) {
            return null;
        }

        Usuario u = get(login);
        if (u != null && u.authenticate(password)) {
            return u;
        }
        return null;
    }

    @Override
    public Usuario get(String login) {
        return userRep.findByUsername(login);
    }

    @Override
    public Usuario get(int id) {
        return userRep.findOne(id);
    }

    @Override
    public List<Usuario> getAll() {
        return userRep.findAll();
    }

    @Override
    public void save(Usuario u) {
        userRep.save(u);
    }

    @Override
    public void delete(Usuario u) {
        if (u == null) {
            throw new DataAccessException("This user can not be null") {
            };
        }
        userRep.delete(u);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String login)
            throws UsernameNotFoundException {
        log.debug("Trying to get user \"" + login + "\"");
        
        List<Usuario> l = getAll();
        
        log.debug("All: " + l);   
       
        Usuario d = get(login);

        if (d == null) {
            log.debug("No such user " + login);
            throw new UsernameNotFoundException("No such user: " + login);
        }
        return d;
    }

}
