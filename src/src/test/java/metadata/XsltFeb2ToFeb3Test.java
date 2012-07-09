/*
 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metadata;

import java.io.FileWriter;
import java.io.FileNotFoundException;
import cognitivabrasil.obaa.OBAA;
import cognitivabrasil.obaa.OaiOBAA;
import java.io.File;
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
public class XsltFeb2ToFeb3Test {

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
 		String foo_xml = "src/test/resources/oai-feb2.1.xml"; //input xml
		String foo_xsl = "src/main/resources/feb2to3.xsl"; //input xsl

		try {
        		String s = XSLTUtil.transform(foo_xml, foo_xsl);
        		System.out.println(s);
			oai = OaiOBAA.fromString(s);
 		} catch (Exception ex) {
      			XSLTUtil.handleException(ex);
			throw new RuntimeException(ex);
 		}
	}
	
	@After
	public void tearDown() {
	}

	@Test
	public void title() {
		OBAA l = oai.getMetadata(0);
		assert(!(l.getGeneral() == null));
		assertThat(l.getGeneral().getTitles(), hasItems("Título 1"));
	}
	
//	@Test
//	public void testDC2OBAA_identifier() {
//		OBAA l = oai.getMetadata(0);
//		assert (!(l.getGeneral() == null));
//		assert (!(l.getGeneral().getIdentifier() == null));
//		assertThat(l.getGeneral().getIdentifier().getEntry(),
//				equalTo("baskjikM"));
//	}
//
//	@Test
//	public void testDC2OBAA_Contribute() {
//		OBAA l = oai.getMetadata(0);
//		assert (!(l.getLifeCycle() == null));
//		assert (!(l.getLifeCycle().getContribute() == null));
//		assertThat(l.getLifeCycle().getContribute().get(0).getRole(),
//				equalTo("Autor"));
//		assertThat(l.getLifeCycle().getContribute().get(0).getFirstEntity(),
//				equalTo("Tarouco, Liane"));
//		// TODO: se é o n-esimo contribuinte, pegar a n-esima data
//		// assertThat(l.getLifeCycle().getContribute().get(0).getDate(),
//		// equalTo("2006"));
//		assertThat(l.getLifeCycle().getContribute().get(1).getRole(),
//				equalTo("Other"));
//		assertThat(l.getLifeCycle().getContribute().get(1).getFirstEntity(),
//				equalTo("CINTED/UFRGS"));
//	}
//
//	@Test
//	public void testDC2OBAA_General() {
//		OBAA l = oai.getMetadata(0);
//		assert (!(l.getGeneral() == null));
//		assertThat(
//				l.getGeneral().getDescriptions(),
//				hasItems("Animação ilustrando a troca de mensagens que ocorre quando um intruso bloqueia um computador medianteo envio de mensagens de hadshake repetidas e depois impersona esta máquina enquanto ela está imobilizada."));
//		assertThat(l.getGeneral().getKeywords(),
//				hasItems("TCP", "segurança", "ataque Mitnick"));
//
//	}
//
//	@Test
//	public void testDC2OBAA_Rights() {
//		OBAA l = oai.getMetadata(0);
//		assert (!(l.getRights() == null));
//		assertThat(l.getRights().getDescription(),
//				equalTo("ihttp://creativecommons.org/licenses/by-sa/3.0/br/"));
//
//	}
//
//	/**
//	 * Test that location tag is correctly translated
//	 */
//	@Test
//	public void testLom2ObaaLocation() {
//		OBAA l = oai.getMetadata(0);
//		assert (!(l.getTechnical() == null));
//		assert (!(l.getTechnical().getLocation() == null));
//		assertThat(
//				l.getTechnical().getFirstHttpLocation(),
//				equalTo("http://cesta2.cinted.ufrgs.br/xmlui/handle/123456789/57"));
//
//	}
//
}
