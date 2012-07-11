/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robo.atualiza.subfedOAI;

import cognitivabrasil.obaa.OaiOBAA;
import modelos.AbstractDaoTest;
import modelos.DocumentosDAO;
import modelos.SubFederacao;
import modelos.SubFederacaoDAO;
import org.hibernate.SessionFactory;
import static org.junit.Assert.assertEquals;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 *
 * @author marcos
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testApplicationContext.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Ignore
public class ImporterTestIT extends AbstractDaoTest {

    @Autowired
    private SubFederacaoDAO subFedDao;
    @Autowired
    private DocumentosDAO docDao;

    public ImporterTestIT() {
        
    }
    
    
    
    @Test
    public void testUpdateFederation() throws Exception{
        
        String fileXML = "./src/test/java/metadata/oai_obaa.xml";
        
        SubFederacao subFed = subFedDao.get("UFRGS");
        
        
        Importer imp = new Importer();
        imp.setInputFile(fileXML);
        imp.setDocDao(docDao);
        imp.setSubFed(subFed);
        imp.update();
        
        OaiOBAA oai = imp.getOaiObaa();
        
        assertEquals("2012-05-15T22:15:22Z", oai.getResponseDate());
        assertEquals(3, oai.getSize());
        
        assertEquals("Ataque a o TCP - Mitnick", oai.getMetadata(0).getTitles().get(0));
        assertEquals("oai:cesta2.cinted.ufrgs.br:123456789/57", oai.getHeader(0).getIdentifier());
        
    }
}
