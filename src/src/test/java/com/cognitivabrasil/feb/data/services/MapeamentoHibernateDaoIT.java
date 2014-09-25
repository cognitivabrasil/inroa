/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitivabrasil.feb.data.services;

import com.cognitivabrasil.feb.AppConfig;
import com.cognitivabrasil.feb.WebConfig;
import com.cognitivabrasil.feb.data.entities.Mapeamento;
import com.cognitivabrasil.feb.data.entities.PadraoMetadados;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 *
 * @author paulo
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class, WebConfig.class })
@WebAppConfiguration
@ActiveProfiles("test")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class MapeamentoHibernateDaoIT extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    MappingService instance;

    @Test
    public void testGet() {
        Mapeamento m = instance.get(1);

        assertEquals("Padrao", m.getName());
        assertEquals("xslt", m.getXslt());
    }

    @Test
    public void testGetAll() {
        assertEquals(2, instance.getAll().size());
    }

    @Test
    public void exists() {
        assertThat(instance.exists("Padrao2"), is(true));
        assertThat(instance.exists("lom8"), is(false));
    }

    @Test
    public void testDelete() {
        Mapeamento m = instance.get(2);

        instance.delete(m);

        assertEquals(1, instance.getAll().size());
    }

    @Test
    public void testSave() {
        Mapeamento m = new Mapeamento();

        m.setName("Jorjao");
        m.setDescription("Blabla");
        m.setXslt("xslt");

        PadraoMetadados p = new PadraoMetadados();
        p.setId(1);

        m.setPadraoMetadados(p);
        instance.save(m);

        Mapeamento m2 = instance.get(1);

        m2.setName("Updated");
        m2.setXslt("blabla");

        instance.save(m2);

        List<Mapeamento> m3 = instance.getAll();
        assertEquals(3, m3.size());
    }
}