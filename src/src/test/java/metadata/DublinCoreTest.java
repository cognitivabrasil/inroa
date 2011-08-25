/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metadata;

import com.sun.org.apache.xml.internal.security.c14n.Canonicalizer;
import java.io.File;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author paulo
 */
public class DublinCoreTest {
	DublinCore dc;
	Serializer serializer;
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
		System.out.println(dc.getTitle());
		assertEquals(dc.getTitle(),  "Taquaraço: 9 anos de glórias");
	}
	
	//@Test TODO: Make it pass
	public void testXml() {
		// TODO review the generated test code and remove the default call to fail.
		Canonicalizer canon;
		String dc_xml = "";

		dc = new DublinCore();
		dc.addTitle("Title 1");
		dc.addTitle("Title 2");
		try {
			dc_xml = dc.toXml();
		System.out.println(dc_xml);
		}
		catch(Exception e) {
			fail("Exception");
		}
			
		assertEquals(dc_xml,"<oai_dc:dc xmlns:oai_dc=\"http://www.openarchives.org/OAI/2.0/oai_dc/\" xmlns:dc=\"http://purl.org/dc/elements/1.1/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.openarchives.org/OAI/2.0/oai_dc/ http://www.openarchives.org/OAI/2.0/oai_dc.xsd\">\n" +
 "   <dc:title>Taquaraço: 9 anos de glórias</dc:title>\n" +
"   <dc:title>Taquaraço: 9 years of glory</dc:title>\n" +
"</oai_dc:dc>");


	}

}
