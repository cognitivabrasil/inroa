package modelos;

import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.springframework.test.annotation.ExpectedException;

import cognitivabrasil.obaa.OBAA;

public class DocumentoTest {

	@Test
	public void testXmlDeserialization() throws IOException {
		DocumentoReal d = new DocumentoReal();
		
		String obaaXml = FileUtils.readFileToString(new File("src/test/java/metadata/obaa1.xml"));
		
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
