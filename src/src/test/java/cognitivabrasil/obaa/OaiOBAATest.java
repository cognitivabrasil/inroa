package cognitivabrasil.obaa;

import java.io.FileNotFoundException;
import metadata.Header;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Test;


public class OaiOBAATest {

	@Test
	public void testSetSpec() throws FileNotFoundException {
		OaiOBAA l = OaiOBAA
				.fromFilename("./src/test/java/feb/metadata/oai_obaa.xml");

		Header h = l.getHeader(0);

		assertEquals("oai:cesta2.cinted.ufrgs.br:123456789/57",
				h.getIdentifier());

		assert (h.getSetSpec() != null);
		assertEquals("RepUfrgs1", h.getSetSpec().get(0));
	}

	/**
	 * Test that trying to load a an invalid OAI-PMH url throws the correct
	 * Exception.
	 * 
	 * @throws FileNotFoundException
	 *             the file not found exception
	 */
	@Test
	public void testDetectOAIError() throws FileNotFoundException {
		OaiOBAA l;
		try {
			l = OaiOBAA.fromFilename("./src/test/java/feb/metadata/oai_error.xml");
		} catch (OaiException e) {
			assertEquals("badArgument", e.getName());
			return;
		}
		fail("Should not reach this point, should have thrown OaiException");
	}

	/**
	 * Test that trying to load a an invalid OAI-PMH url throws the correct
	 * Exception.
	 * 
	 * @throws FileNotFoundException
	 *             the file not found exception
	 */
	@Test
	public void testDetectOAICannotDisseminate() throws FileNotFoundException {
		OaiOBAA l;
		try {
			l = OaiOBAA
					.fromFilename("./src/test/java/feb/metadata/oai_cannot_disseminate.xml");
		} catch (OaiCannotDisseminateFormatException e) {
			assert ("cannotDisseminateFormat".equals(e.getName()));
			return;
		}
		fail("Should not reach this point, should have thrown OaiException");
	}

	/**
	 * Teste that it works with no records.
	 * 
	 * Should return an empty list.
	 * 
	 * @throws FileNotFoundException
	 *             the file not found exception
	 */
	@Test
	public void testDetectOAINoRecords() throws FileNotFoundException {

		OaiOBAA l = OaiOBAA
				.fromFilename("./src/test/java/feb/metadata/oai_norecords.xml");
		assertEquals(l.getSize(), 0);

	}

	/**
	 * Test for Regression reported in FEB-113
	 * 
	 * @throws FileNotFoundException
	 *             the file not found exception
	 */
	@Test
        @Ignore
	public void testDetectOAINoRecords2() throws FileNotFoundException {
		try {
			OaiOBAA l = OaiOBAA
					.fromFilename("./src/test/java/feb/metadata/oai_norecords2.xml");
			assertEquals(l.getSize(), 0);
		} catch (OaiParseErrorException e) {
			return;
		}
		fail("Should have thrown OaiParseErrorException");
	}
}
