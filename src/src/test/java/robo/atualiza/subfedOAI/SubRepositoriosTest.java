/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robo.atualiza.subfedOAI;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.xml.sax.SAXException;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author marcos
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testApplicationContext.xml")
@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
public class SubRepositoriosTest {
    
    @Test
    public void testParserXML() throws ParserConfigurationException, IOException, SAXException{
        String fileXML = "src/test/java/metadata/oai_subRepositorios.xml";
        File arquivoXML = new File(fileXML);
        ParserListSets parserListSets = new ParserListSets();
        Set<String> listaSubrep = parserListSets.parser(arquivoXML);
        
        assertEquals(2, listaSubrep.size());
        
        assert(listaSubrep.contains("LUME"));
        assert(listaSubrep.contains("CESTA"));
        
    }
    
}
