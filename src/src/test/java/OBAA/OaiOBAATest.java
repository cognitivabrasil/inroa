package OBAA;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;

import org.junit.Test;

public class OaiOBAATest {
	/**
	 * Test that trying to load a an invalid OAI-PMH url throws the correct Exception.
	 *
	 * @throws FileNotFoundException the file not found exception
	 */
	@Test
	public void testDetectOAIError() throws FileNotFoundException {
		OaiOBAA l;
		try {
			l = OaiOBAA.fromFilename("./src/test/java/metadata/oai_error.xml");
		}
		catch(OaiException e) {
			assertEquals("badArgument", e.getName());
			return;
		}
		fail("Should not reach this point, should have thrown OaiException");
	}
	
	/**
	 * Test that trying to load a an invalid OAI-PMH url throws the correct Exception.
	 *
	 * @throws FileNotFoundException the file not found exception
	 */
	@Test
	public void testDetectOAICannotDisseminate() throws FileNotFoundException {
		OaiOBAA l;
		try {
			l = OaiOBAA.fromFilename("./src/test/java/metadata/oai_cannot_disseminate.xml");
		}
		catch(OaiCannotDisseminateFormatException e) {
			assert("cannotDisseminateFormat".equals(e.getName()));
			return;
		}
		fail("Should not reach this point, should have thrown OaiException");
	}
	
	/**
	 * Teste that it works with no records.
	 * 
	 * Should return an empty list.
	 *
	 * @throws FileNotFoundException the file not found exception
	 */
	@Test
	public void testDetectOAINoRecords() throws FileNotFoundException {
	
			OaiOBAA l = OaiOBAA.fromFilename("./src/test/java/metadata/oai_norecords.xml");
			assertEquals(l.getSize(), 0);			
	
	}
}
