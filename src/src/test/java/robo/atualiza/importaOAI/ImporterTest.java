/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robo.atualiza.importaOAI;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import modelos.DocumentosDAO;
import modelos.Mapeamento;
import modelos.Repositorio;
import modelos.RepositoryDAO;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import cognitivabrasil.obaa.OBAA;
import cognitivabrasil.obaa.OaiOBAA;

/**
 *
 * @author paulo
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testApplicationContext.xml")
@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
public class ImporterTest {
	/**
	 * Read file as string.
	 *
	 * @param filePath the file path
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private static String readFileAsString(String filePath) throws IOException {
		byte[] buffer = new byte[(int) new File(filePath).length()];
		FileInputStream f = null;
		f = new FileInputStream(filePath);
		f.read(buffer);
		return new String(buffer);
	}

	/**
	 * Tests that the importer is converting the file and calling the
	 * correct number of save() in the document DAO, from a sample DC input.
	 * @throws IOException 
	 */
	@Test
	public void testImportDC() throws IOException {
		String inputXmlFile = "src/test/java/metadata/oai_dc.xml"; // input xml
		String inputXsltFile = "src/xslt/dc2obaa_full.xsl"; // input xsl
		String xslt = readFileAsString(inputXsltFile);
		
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

