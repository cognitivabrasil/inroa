/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitivabrasil.feb.data.entities;

import org.junit.*;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author paulo
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class UsuarioTest {

    /**
     * Test of getId method, of class Usuario.
     */
    @Test
    public void testSetPassword() {
        String password = "teste";
        Usuario user = new Usuario();
        user.setPassword(password);
        assertEquals("698dc19d489c4e4db73e28a713eab07b", user.getPasswordMd5());
    }

    /**
     * Test of authenticate method, of class Usuario.
     */
    @Test
    public void testAuthenticate() {
        Usuario u = new Usuario();
        u.setPasswordMd5("698dc19d489c4e4db73e28a713eab07b");
        assertEquals(true, u.authenticate("teste"));
        assertEquals(false, u.authenticate("Teste"));
    }

    @Test
    public void testAuthenticateNullPassword() {
        Usuario u = new Usuario();
        u.setPasswordMd5("698dc19d489c4e4db73e28a713eab07b");

        assertFalse(u.authenticate(null));
    }
}