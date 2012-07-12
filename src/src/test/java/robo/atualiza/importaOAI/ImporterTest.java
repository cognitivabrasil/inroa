/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robo.atualiza.importaOAI;

import cognitivabrasil.obaa.OBAA;
import cognitivabrasil.obaa.OaiOBAA;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import modelos.DocumentosDAO;
import modelos.RepositoryDAO;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertEquals;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import feb.data.entities.Mapeamento;
import feb.data.entities.Repositorio;

/**
 *
 * @author paulo
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testApplicationContext.xml")
@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
public class ImporterTest {
	/**
	 * Tests that the importer is converting the file and calling the
	 * correct number of save() in the document DAO, from a sample DC input.
	 * @throws IOException 
	 */
	@Test
	public void testImportDC() throws IOException {
		String inputXmlFile = "src/test/java/feb/metadata/oai_dc.xml"; // input xml
		String inputXsltFile = "src/xslt/dc2obaa_full.xsl"; // input xsl
		String xslt = FileUtils.readFileToString(new File(inputXsltFile));
		
		Mapeamento m = new Mapeamento();
		m.setXslt(xslt);
		
		Repositorio r = new Repositorio();
		r.setMapeamento(m);
		
		DocumentosDAO docDao = mock(DocumentosDAO.class);
		RepositoryDAO repDao = mock(RepositoryDAO.class);
				
		Importer imp = new Importer();
		imp.setInputFile(new File(inputXmlFile));
		imp.setRepositorio(r);
		imp.setDocDao(docDao);
		imp.setRepDao(repDao);
		imp.update();
		
		OaiOBAA oai = imp.getOaiObaa();
		
		assertEquals(2, oai.getSize());
		assertEquals("2011-09-12T12:25:51Z", oai.getResponseDate());
		
		OBAA obaa = oai.getMetadata(0);
		
		assertThat(obaa.getTitles(), hasItems("Taquaraço: 9 anos de glórias", "Taquaraço: 9 years of glory"));
		
		// Verify that save() was called twice on mocked docDao
		verify(docDao, times(2)).save(isA(OBAA.class), isA(metadata.Header.class));	

	}
    
}

