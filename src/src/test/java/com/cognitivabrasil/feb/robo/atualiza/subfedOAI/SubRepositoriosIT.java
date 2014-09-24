/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitivabrasil.feb.robo.atualiza.subfedOAI;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.xml.sax.SAXException;

import com.cognitivabrasil.feb.AppConfig;

/**
 *
 * @author marcos
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class })
@ActiveProfiles("test")
public class SubRepositoriosIT  extends AbstractTransactionalJUnit4SpringContextTests {
    
    @Test
    public void testParserXML() throws ParserConfigurationException, IOException, SAXException{
        String fileXML = "src/test/resources/metadata/oai_subRepositorios.xml";
        File arquivoXML = new File(fileXML);
        ParserListSets parserListSets = new ParserListSets();
        Set<String> listaSubrep = parserListSets.parser(arquivoXML);
        
        assertEquals(2, listaSubrep.size());
        
        assert(listaSubrep.contains("LUME"));
        assert(listaSubrep.contains("CESTA"));
        
    }   
}
