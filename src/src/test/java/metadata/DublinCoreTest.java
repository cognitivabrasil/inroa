/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metadata;

import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 *
 * @author paulo
 */
public class DublinCoreTest {
	DublinCore dc;
	File file;
	
	public DublinCoreTest() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}
	
	@Before
	public void setUp() {
		dc = DublinCore.fromFilename("./src/test/java/metadata/teste1.xml");
	}
	
	@After
	public void tearDown() {
	}

	@Test
	public void testTitle() {
		// TODO review the generated test code and remove the default call to fail.
		String dc_xml = "";
		assertEquals(dc.getTitle(),  "Taquaraço: 9 anos de glórias");
		assertThat(dc.getTitles(), hasItems("Taquaraço: 9 anos de glórias", "Taquaraço: 9 years of glory"));
	}

	@Test
	public void testDescriptions() {
		// TODO review the generated test code and remove the default call to fail.
		assertThat(dc.getDescriptions(), hasItems("Descrição 1", "Descrição 2"));
	}

	
	@Test 
	public void testXml() {
		// TODO review the generated test code and remove the default call to fail.
		String dc_xml = "";

		dc = new DublinCore();
		dc.addTitle("Title 1");
		dc.addTitle("Title 2");
		dc.addDescription("D1");
		dc.addDescription("D2");
		try {
			dc_xml = dc.toXml();
			System.out.println(dc_xml);
		}
		catch(Exception e) {
			fail("Exception");
		}
			
		assertEquals("<oai_dc:dc " +
			 "xsi:schemaLocation=\"http://www.openarchives.org/OAI/2.0/oai_dc/ http://www.openarchives.org/OAI/2.0/oai_dc.xsd\"" + 
			" xmlns:oai_dc=\"http://www.openarchives.org/OAI/2.0/oai_dc/\" xmlns:dc=\"http://purl.org/dc/elements/1.1/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n"+
 "   <dc:title>Title 1</dc:title>\n" +
"   <dc:title>Title 2</dc:title>\n" +
 "   <dc:description>D1</dc:description>\n" +
"   <dc:description>D2</dc:description>\n" +
"</oai_dc:dc>", dc_xml);


	}

}
