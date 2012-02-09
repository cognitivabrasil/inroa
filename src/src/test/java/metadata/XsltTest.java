/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metadata;

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
public class XsltTest {

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
 		String foo_xml = "src/test/java/metadata/oai_dc.xml"; //input xml
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
	public void testDC2OBAA_title() {
		OBAA l = oai.getMetadata(0);
		assert(!(l.getGeneral() == null));
		assertThat(l.getTitles(), hasItems("Taquaraço: 9 anos de glórias"));
	}
	
	@Test
	public void testDC2OBAA_identifier() {
		OBAA l = oai.getMetadata(0);
		assert(!(l.getGeneral() == null));
		assert(!(l.getGeneral().getIdentifier() == null));
		assertThat(l.getGeneral().getIdentifier().getEntry(), equalTo("http://hdl.handle.net/10183/394"));
	}
	
	@Test
	public void testDC2OBAA_Contribute() {
		OBAA l = oai.getMetadata(0);
		assert(!(l.getLifeCycle() == null));
		assert(!(l.getLifeCycle().getContribute() == null));
		assertThat(l.getLifeCycle().getContribute().get(0).getRole(), equalTo("author"));
		assertThat(l.getLifeCycle().getContribute().get(0).getEntity(), equalTo("Jorjão"));
		//TODO: se é o n-esimo contribuinte, pegar a n-esima data
		//assertThat(l.getLifeCycle().getContribute().get(0).getDate(), equalTo("2006"));
		assertThat(l.getLifeCycle().getContribute().get(0).getDate(), equalTo("2007-06-04T11:33:20Z"));
		
		assertThat(l.getLifeCycle().getContribute().get(1).getRole(), equalTo("unknown"));
		assertThat(l.getLifeCycle().getContribute().get(1).getEntity(), equalTo("Sílton"));
		
		assertThat(l.getLifeCycle().getContribute().get(2).getRole(), equalTo("publisher"));
		assertThat(l.getLifeCycle().getContribute().get(2).getEntity(), equalTo("Pub 1"));
	}
	
	@Test
	public void testDC2OBAA_General() {
		OBAA l = oai.getMetadata(0);
		assert(!(l.getGeneral() == null));
		assertThat(l.getGeneral().getDescriptions(), hasItems("Descrição 1", "Descrição 2"));
		assertThat(l.getGeneral().getCoverages(), hasItems("Coverage 1", "Coverage 2"));
		assertThat(l.getGeneral().getKeywords(), hasItems("Futebol", "Campeonato do Dacomp"));
		
	}
	
	@Test
	public void testDC2OBAA_Rights() {
		OBAA l = oai.getMetadata(0);
		assert(!(l.getRights() == null));
		assertThat(l.getRights().getDescription(), equalTo("Right 1 Right 2"));
		
	}


}
