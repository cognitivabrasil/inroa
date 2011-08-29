/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metadata.LOM;

import org.junit.Test;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


/**
 *
 * @author paulo
 */
public class LOMTest {
	LOM l = new LOM();

	
	public LOMTest() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}
	
	@Before
	public void setUp() {
		l = LOM.fromFilename("./src/test/java/metadata/lom1.xml");
	}
	
	@After
	public void tearDown() {
	}
	// TODO add test methods here.
	// The methods must be annotated with annotation @Test. For example:
	//
	// @Test
	// public void hello() {}
	
	@Test
	public void testTitle() {
		// TODO review the generated test code and remove the default call to fail.
		assert(!(l.getGeneral() == null));
		assertThat(l.getTitles(), hasItems("Título 1"));
	}
}
