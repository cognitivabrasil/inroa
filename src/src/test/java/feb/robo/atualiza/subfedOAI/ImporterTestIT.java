/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feb.robo.atualiza.subfedOAI;

import cognitivabrasil.obaa.OaiOBAA;
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

import com.cognitivabrasil.feb.data.services.AbstractDaoTest;
import com.cognitivabrasil.feb.data.entities.SubFederacao;
import com.cognitivabrasil.feb.data.services.DocumentService;
import com.cognitivabrasil.feb.data.services.FederationService;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 *
 * @author marcos
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class ImporterTestIT extends AbstractDaoTest {

    @Autowired
    private FederationService subFedDao;
    @Autowired
    private DocumentService docDao;
    
    @Test
    public void testUpdateFederation() throws Exception{        
        String fileXML = "./src/test/resources/oai_obaa.xml";
        
        SubFederacao subFed = subFedDao.get("UFRGS");
                
        Importer imp = new Importer();
        imp.setInputFile(fileXML);
        imp.setDocDao(docDao);
        imp.setSubFed(subFed);
        imp.update();
        
        assertThat(subFed.getDataXMLTemp(), equalTo("2012-05-15T22:15:22Z"));
        
        OaiOBAA oai = imp.getOaiObaa();
        
        assertEquals("2012-05-15T22:15:22Z", oai.getResponseDate());        
        assertEquals(3, oai.getSize());
        
        assertEquals("Ataque a o TCP - Mitnick", oai.getMetadata(0).getTitles().get(0));
        assertEquals("oai:cesta2.cinted.ufrgs.br:123456789/57", oai.getHeader(0).getIdentifier());        
    }
}