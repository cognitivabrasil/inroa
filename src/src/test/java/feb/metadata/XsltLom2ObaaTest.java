/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feb.metadata;

import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

import cognitivabrasil.obaa.OBAA;
import cognitivabrasil.obaa.OaiOBAA;
import java.io.File;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

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
public class XsltLom2ObaaTest {

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
	public void setUp() throws TransformerConfigurationException,
			TransformerException, FileNotFoundException {
		String foo_xml = "src/test/java/feb/metadata/oai_lom.xml"; // input xml
		String foo_xsl = "src/xslt/lom2obaa_full.xsl"; // input xsl

		String s = XSLTUtil.transformFilename(foo_xml, foo_xsl);
		oai = OaiOBAA.fromString(s);
	}

	@After
	public void tearDown() {
	}

	@Test
	public void testDC2OBAA_title() {
		OBAA l = oai.getMetadata(0);
		assert (!(l.getGeneral() == null));
		assertThat(l.getTitles(), hasItems("Título 1"));
	}

	@Test
	public void testDC2OBAA_identifier() {
		OBAA l = oai.getMetadata(0);
		assert (!(l.getGeneral() == null));
		assert (!(l.getGeneral().getIdentifier() == null));
		assertThat(l.getGeneral().getIdentifier().getEntry(),
				equalTo("baskjikM"));
	}

	@Test
	public void testDC2OBAA_Contribute() {
		OBAA l = oai.getMetadata(0);
		assert (!(l.getLifeCycle() == null));
		assert (!(l.getLifeCycle().getContribute() == null));
		assertThat(l.getLifeCycle().getContribute().get(0).getRole(),
				equalTo("Autor"));
		assertThat(l.getLifeCycle().getContribute().get(0).getFirstEntity(),
				equalTo("Tarouco, Liane"));
		// TODO: se é o n-esimo contribuinte, pegar a n-esima data
		// assertThat(l.getLifeCycle().getContribute().get(0).getDate(),
		// equalTo("2006"));
		assertThat(l.getLifeCycle().getContribute().get(1).getRole(),
				equalTo("Other"));
		assertThat(l.getLifeCycle().getContribute().get(1).getFirstEntity(),
				equalTo("CINTED/UFRGS"));
	}

	@Test
	public void testDC2OBAA_General() {
		OBAA l = oai.getMetadata(0);
		assert (!(l.getGeneral() == null));
		assertThat(
				l.getGeneral().getDescriptions(),
				hasItems("Animação ilustrando a troca de mensagens que ocorre quando um intruso bloqueia um computador medianteo envio de mensagens de hadshake repetidas e depois impersona esta máquina enquanto ela está imobilizada."));
		assertThat(l.getGeneral().getKeywords(),
				hasItems("TCP", "segurança", "ataque Mitnick"));

	}

	@Test
	public void testDC2OBAA_Rights() {
		OBAA l = oai.getMetadata(0);
		assert (!(l.getRights() == null));
		assertThat(l.getRights().getDescription(),
				equalTo("ihttp://creativecommons.org/licenses/by-sa/3.0/br/"));

	}

	/**
	 * Test that location tag is correctly translated
	 */
	@Test
	public void testLom2ObaaLocation() {
		OBAA l = oai.getMetadata(0);
		assert (!(l.getTechnical() == null));
		assert (!(l.getTechnical().getLocation() == null));
		assertThat(
				l.getTechnical().getFirstHttpLocation(),
				equalTo("http://cesta2.cinted.ufrgs.br/xmlui/handle/123456789/57"));

	}

	@Test
	public void testCestaCompleteOutput() throws FileNotFoundException {
		OaiOBAA cesta;
		String foo_xml = "src/test/java/feb/metadata/cesta_complete.xml"; // input
																			// xml
		String foo_xsl = "src/xslt/lom2obaa_full.xsl"; // input xsl

		try {
			String s = XSLTUtil.transformFilename(foo_xml, foo_xsl);
			FileWriter f = new FileWriter("out.xml");
			f.append(s);
			f.close();
			cesta = OaiOBAA.fromString(s);
			System.out.println("===============================");
			// System.out.println(cesta.toString());
		} catch (Exception ex) {
			// XSLTUtil.handleException(ex);
			fail("Shoudln't throw exception");
		} finally {
			File f = new File("out.xml");
			f.delete();
		}
	}

	@Test
	public void froac() throws TransformerConfigurationException, TransformerException, IOException {
		OaiOBAA fr;
		System.out.println("ddfsfd");

		String foo_xml = "src/test/java/feb/metadata/FEB-froac1.xml"; // input
		// xml
		String foo_xsl = "src/xslt/lom2obaa_full.xsl"; // input xsl

			String s = XSLTUtil.transformFilename(foo_xml, foo_xsl);
			fr = OaiOBAA.fromString(s);
	}

}
