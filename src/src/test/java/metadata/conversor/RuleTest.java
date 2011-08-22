/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metadata.conversor;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author paulo
 */
public class RuleTest {


	MockOne m1;
	MockTwo m2;
	
	public RuleTest() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}
	
	@Before
	public void setUp() {
		m1 = new MockOne();
		m2 = new MockTwo();
	}
	
	@After
	public void tearDown() {
	}

	@Test
	public void testConvertTitle() {
		Rule r = new Rule("Title", "Titulo");
		
		m1.addTitle("Teste");

		r.apply(m1, m2);

		assertEquals(m1.getTitle(), m2.getTitulo());
		
	}

	@Test(expected=IllegalArgumentException.class)
	public void badArgumentThrowsException1() {
		Rule r = new Rule("Title", "Title");
		
		m1.addTitle("Teste");

		r.apply(m1, m2);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void badArgumentThrowsException2() {
		Rule r = new Rule("Title", "Title");
		
		m2.addTitulo("Teste");

		r.apply(m1, m2);
	}
}
