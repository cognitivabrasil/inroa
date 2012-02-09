/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metadata.conversor;

import metadata.conversor.Conversor;
import metadata.conversor.MockOne;
import metadata.conversor.MockTwo;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Paulo Schreiner 
 * 
 *
 */
public class ConversorTest {
	Conversor c;
	
	public ConversorTest() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}
	
	@Before
	public void setUp() {
		c = new Conversor();
		c.add(new Rule("Title", "Titulo"));
		c.add(new Rule("Creators", "Authors"));
	}
	
	@After
	public void tearDown() {
	}

	@Test
	public void testSimpleConversion() {
		MockOne m1 = new MockOne();
		MockTwo m2 = new MockTwo();

		m1.setTitle("title1");
		m1.addCreator("Jorjão");
		m1.addCreator("Pretto");

		c.convert(m1, m2);

		assertEquals(m1.getTitle(), m2.getTitulo());

		assert(m2.getAuthors().contains("Jorjão"));
		assert(m2.getAuthors().contains("Pretto"));
		
	}
}
