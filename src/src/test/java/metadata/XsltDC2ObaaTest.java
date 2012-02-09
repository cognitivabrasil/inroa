/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metadata;

import java.io.FileWriter;
import java.io.FileNotFoundException;
import OBAA.OBAA;
import OBAA.OaiOBAA;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.fail;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 *
 * @author paulo
 */
public class XsltDC2ObaaTest {

	OaiOBAA oai;
	
	@BeforeClass
	public static void setUpClass() throws Exception {
				 System.setProperty("javax.xml.transform.TransformerFactory",
 "net.sf.saxon.TransformerFactoryImpl");
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}
	
	@Before
	public void setUp() {
 		String foo_xml = "src/test/java/metadata/lume1.xml"; //input xml
		String foo_xsl = "src/xslt/dc2obaa_full.xsl"; //input xsl

		try {
        		String s = XSLTUtil.transform(foo_xml, foo_xsl);
			oai = OaiOBAA.fromString(s);
 		} catch (Exception ex) {
      			XSLTUtil.handleException(ex);
			fail("Shoudln't throw exception");
 		}
	}
	
	@After
	public void tearDown() {
	}

	@Test
	public void testCestaCompleteOutput() throws FileNotFoundException {

		try {
			
			System.out.println("===============================");
			System.out.println(oai.toString());
 		} catch (Exception ex) {
      			//XSLTUtil.handleException(ex);
			fail("Shoudln't throw exception");
 		}
	}


}
