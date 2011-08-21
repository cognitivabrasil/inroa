/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metadata;

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
		System.out.println(System.getProperty("user.dir"));
		File xml = new File("./src/test/java/metadata/teste1.xml");
		Serializer serializer = new Persister();
	
		try {	
			dc = serializer.read(DublinCore.class, xml);
		}
		catch(java.lang.Exception e) {
			e.printStackTrace();
		}
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
}
