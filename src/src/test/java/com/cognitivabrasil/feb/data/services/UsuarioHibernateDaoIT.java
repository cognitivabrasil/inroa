/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitivabrasil.feb.data.services;

import com.cognitivabrasil.feb.data.entities.Usuario;
import java.util.List;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests of the UsuarioHibernateDao
 *
 * @author Paulo Schreiner <paulo@jorjao81.com>
 *
 */
//@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:spring-security.xml"})
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class UsuarioHibernateDaoIT extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    UserService instance;

    /**
     * Test of authenticate method, of class UsuarioHibernateDAO.
     */
    @Test
    public void testAuthenticate() {

        Usuario u1 = instance.authenticate("admin", "teste1");
        assertEquals(null, u1);

        Usuario u2 = instance.authenticate("admin", "teste");
        assertEquals("admin", u2.getLogin());
    }

    @Test
    public void testAuthenticateNoSuchUser() {
        Usuario u1 = instance.authenticate(null, "random");
        assertThat(u1, is(nullValue()));

        u1 = instance.authenticate("nosuchuser", "random");
        assertThat(u1, is(nullValue()));
    }

    /**
     * Test of delete method, of class UsuarioHibernateDAO.
     */
    @Test
    public void testDelete() {
        //System.out.println("delete");
        Usuario r = instance.get(2);
        instance.delete(r);

        assertEquals(1, instance.getAll().size());
    }

    /**
     * Test of get method, of class UsuarioHibernateDAO.
     */
    @Test
    public void testGet() {
        int id = 1;
        Usuario u = instance.get(id);
        assertEquals("admin", u.getLogin());
        assertEquals("698dc19d489c4e4db73e28a713eab07b", u.getPasswordMd5());
        assertEquals("Administrador da federacao", u.getDescription());
    }

    @Test
    public void testGetByLogin() {
        Usuario u = instance.get("admin");
        assertEquals("admin", u.getLogin());
        assertEquals("698dc19d489c4e4db73e28a713eab07b", u.getPasswordMd5());
        assertEquals("Administrador da federacao", u.getDescription());
    }

    @Test
    public void testGetPermissions() {
        Usuario u = instance.get("admin");

        assertThat(u.getPermissions(), hasItems("PERM_MANAGE_USERS", "PERM_MANAGE_MAPPING"));
    }

    /**
     * Test of getAll method, of class UsuarioHibernateDAO.
     */
    @Test
    public void testGetAll() {
        List result = instance.getAll();
        assertEquals(2, result.size());
    }

    /**
     * Test of save method, of class UsuarioHibernateDAO.
     */
    @Test
//    @Rollback(false) //TODO: nao sei qual o objetivo de nao fazer rollback, estavam falhando os testes, tirei isso e voltou ao normal (Marcos)
    @Transactional
    public void testSaveAndUpdate() throws Exception {

        Usuario r = instance.get(1);
        r.setUsername("jorjao");
        instance.save(r);

        Usuario r2 = new Usuario();
        r2.setUsername("paulo");
        r2.setPasswordMd5("bla");
        r2.setDescription("teste");

        instance.save(r2);
    }
}