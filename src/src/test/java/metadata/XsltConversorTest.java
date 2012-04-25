package metadata;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.junit.Test;

import OBAA.OBAA;
import OBAA.OaiOBAA;

// TODO: Auto-generated Javadoc
/**
 * The Class XsltConversorTest.
 * 
 * Test the basic XsltConversor class. No need to do a lot of tests here, 
 * since XsltConversor uses XSLTTransformer from Saxon, and that is tested
 * in XsltDC2ObaaTest and others. Tests that verify the XSLT transforms
 * themselves should not be put here.
 */
public class XsltConversorTest {
	
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
	 * Test to obaa.
	 * 
	 * Simple test that just verifies the class is working and returns sensible results.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void testToObaa() throws IOException {
		String inputXmlFile = "src/test/java/metadata/oai_dc.xml"; // input xml
		String inputXsltFile = "src/xslt/dc2obaa_full.xsl"; // input xsl
		String xslt = readFileAsString(inputXsltFile);
		String xml = readFileAsString(inputXmlFile);

		XsltConversor conv = new XsltConversor(xslt);

		OaiOBAA oai = OaiOBAA.fromString(conv.toObaa(xml));

		OBAA l = oai.getMetadata(0);
		assert (!(l.getGeneral() == null));
		assert (!(l.getGeneral().getIdentifier() == null));
		assertThat(l.getGeneral().getIdentifier().getEntry(),
				equalTo("http://hdl.handle.net/10183/394"));

	}

}
