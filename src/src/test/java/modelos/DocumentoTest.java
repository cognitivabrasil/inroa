package modelos;

import cognitivabrasil.obaa.OBAA;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import org.junit.Test;

public class DocumentoTest {

	@Test
	public void testXmlDeserialization() throws IOException {
		DocumentoReal d = new DocumentoReal();
		
		String obaaXml = FileUtils.readFileToString(new File("src/test/java/feb/metadata/obaa1.xml"));
		
		d.setObaaXml(obaaXml);
		
		OBAA m = d.getMetadata();
		
		assertThat(m.getTitles(), hasItem("Título 1"));

	}
	
	/**
	 * Test that calling getMetadata on an object that does not have XML metadata throws exception.
	 */
	@Test(expected=IllegalStateException.class)
	public void testThrowsException() {
		DocumentoReal d = new DocumentoReal();
		
		d.getMetadata();
	}


}
