/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitivabrasil.feb.data.repositories;

import cognitivabrasil.obaa.OBAA;
import com.cognitivabrasil.feb.data.entities.Document;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import static org.hamcrest.Matchers.equalTo;
import org.joda.time.DateTime;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 *
 * @author Marcos Freitas Nunes <marcos@cognitivabrasil.com.br>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class DocumentRepositoryIT extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private DocumentRepository docRep;
    @PersistenceContext
    private EntityManager em;

    @Test
    public void testSaveCreated() {
        long docsBefore = docRep.count();
        Document doc = new Document();
        doc.setCreated(new DateTime(2014, 6, 29, 8, 52));
        doc.setMetadata(new OBAA());
        doc.setObaaEntry("marcos");

        docRep.save(doc);

        em.flush();
        em.clear();

        assertThat(docRep.count(), equalTo(docsBefore + 1));

        Document doc2 = docRep.findByObaaEntry("marcos");
        assertThat(doc2.getCreated(), equalTo(new DateTime(2014, 6, 29, 8, 52)));
    }
}
