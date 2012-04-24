/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OBAA;

import OBAA.LifeCycle.LifeCycle;
import metadata.Request;
import metadata.Header;
import java.io.FileNotFoundException;
import java.io.FileReader;
import org.junit.Test;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.fail;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;


/**
 *
 * @author paulo
 */
public class OBAATest {
	OBAA l = new OBAA();

	
	public OBAATest() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}
	
	@Before
	public void setUp() throws FileNotFoundException{
		l = OBAA.fromFilename("./src/test/java/metadata/obaa1.xml");
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
	
	@Test
	public void testTitleFromReader() throws FileNotFoundException {
		// TODO review the generated test code and remove the default call to fail.

		l = OBAA.fromReader(new FileReader("./src/test/java/metadata/obaa1.xml"));
		assert(!(l.getGeneral() == null));
		assertThat(l.getTitles(), hasItems("Título 1"));
	}
	@Test
	public void testOBAA_General_AggregationLevel() throws FileNotFoundException {
		assert(!(l.getGeneral() == null));
		assertThat(l.getGeneral().getAggregationLevel(), equalTo(1));
	}
	
	@Test
	public void testOBAA_General_Coverages() throws FileNotFoundException {
		assert(!(l.getGeneral() == null));
		assertThat(l.getGeneral().getCoverages(), hasItems("cov1", "cov2"));
	}
	
	@Test
	public void testOBAA_General_Descriptions() throws FileNotFoundException {
		assert(!(l.getGeneral() == null));
		assertThat(l.getGeneral().getDescriptions(), hasItems("Desc1", "Desc2"));
	}
	
	@Test
	public void testOBAA_General_Identifier_Catalog() throws FileNotFoundException {
		assert(!(l.getGeneral() == null));
		assert(!(l.getGeneral().getIdentifier() == null));
		assertThat(l.getGeneral().getIdentifier().getCatalog(), equalTo("teste"));
	}
	
	@Test
	public void testOBAA_General_Identifier_Entry() throws FileNotFoundException {
		assert(!(l.getGeneral() == null));
		assert(!(l.getGeneral().getIdentifier() == null));
		assertThat(l.getGeneral().getIdentifier().getEntry(), equalTo("123"));
	}
	
	@Test
	public void testOBAA_General_Keywords() throws FileNotFoundException {
		assert(!(l.getGeneral() == null));
		assertThat(l.getGeneral().getKeywords(), hasItems("TCP", "segurança", "ataque Mitnick"));
	}
	
	@Test
	public void testOBAA_General_Language() throws FileNotFoundException {
		assert(!(l.getGeneral() == null));
		assertThat(l.getGeneral().getLanguage(), equalTo("pt_BR"));
	}
	
	@Test
	public void testOBAA_General_Structure() throws FileNotFoundException {
		assert(!(l.getGeneral() == null));
		assertThat(l.getGeneral().getStructure(), equalTo("atomic"));
	}
	
	@Test
	public void testOaiOBAALifeCycleStatus() throws FileNotFoundException {
		assert(!(l.getLifeCycle() == null));
		assertThat(l.getLifeCycle().getStatus(), equalTo("Final"));
	}
	
	@Test
	public void testOaiOBAALifeCycleStatusException() throws FileNotFoundException {
		assert(!(l.getLifeCycle() == null));
		try { 
			l.getLifeCycle().setStatus("bla"); 
			fail("Should have thrown exception");
		}
		catch(IllegalArgumentException e) { 
			// thats what we expected
		}

	}

	@Test
	public void testOBAALifeCycleVersion() throws FileNotFoundException {
		assert(!(l.getLifeCycle() == null));
		assertThat(l.getLifeCycle().getVersion(), equalTo("1"));
	}

	@Test
	public void testOaiOBAALifeCycleContributeEntity() throws FileNotFoundException {
		assert(!(l.getLifeCycle() == null));
		assert(!(l.getLifeCycle().getContribute() == null));
		assertThat(l.getLifeCycle().getContribute().get(0).getFirstEntity(), equalTo("Tarouco, Liane"));
	}
	
	@Test
	public void testOBAA_LifeCycle_Contribute_Date() throws FileNotFoundException {
		assert(!(l.getLifeCycle() == null));
		assert(!(l.getLifeCycle().getContribute() == null));
		assertThat(l.getLifeCycle().getContribute().get(0).getDate(), equalTo("2011-09-09T21:08:38Z"));
	}
	
	@Test
	public void testOBAA_LifeCycle_Contribute_Role() throws FileNotFoundException {
		assert(!(l.getLifeCycle() == null));
		assert(!(l.getLifeCycle().getContribute() == null));
		assertThat(l.getLifeCycle().getContribute().get(0).getRole(), equalTo("author"));
	}
	
	@Test
	public void testOBAA_LifeCycle_Contribute_RoleException() throws FileNotFoundException {
		assert(!(l.getLifeCycle() == null));
		assert(!(l.getLifeCycle().getContribute() == null));
		try { 
			l.getLifeCycle().getContribute().get(0).setRole("other"); 
			fail("Should have thrown exception");
		}
		catch(IllegalArgumentException e) { 
			// thats what we expected
		}

	}
	
	@Test
	public void testOBAA_Rights_Cost() throws FileNotFoundException {
		assert(!(l.getRights() == null));
		assertThat(l.getRights().getCost(), equalTo("Nao"));
	}
	
	@Test
	public void testOBAA_Rights_Copyright() throws FileNotFoundException {
		assert(!(l.getRights() == null));
		assertThat(l.getRights().getCopyright(), equalTo("Nao"));
	}
	
	@Test
	public void testOBAA_Rights_Description() throws FileNotFoundException {
		assert(!(l.getRights() == null));
		assertThat(l.getRights().getDescription(), equalTo("http://creativecommons.org/licenses/by-sa/3.0/br/"));
	}
	
	@Test
	public void testOBAA_Educational_InteractivityType() throws FileNotFoundException {
		assert(!(l.getEducational() == null));
		assertThat(l.getEducational().getInteractivityType(), equalTo("Expositivo"));
	}
	
	@Test
	public void testOBAA_Educational_InteractivityLevel() throws FileNotFoundException {
		assert(!(l.getEducational() == null));
		assertThat(l.getEducational().getInteractivityLevel(), equalTo("Muito baixo"));
	}
	
	@Test
	public void testOBAA_Educational_SemanticDensity() throws FileNotFoundException {
		assert(!(l.getEducational() == null));
		assertThat(l.getEducational().getSemanticDensity(), equalTo("low"));
	}
	
	@Test
	public void testOBAA_Educational_Difficulty() throws FileNotFoundException {
		assert(!(l.getEducational() == null));
		assertThat(l.getEducational().getDifficulty(), equalTo("easy"));
	}
	
	@Test
	public void testOBAA_Educational_TypicalLearningTime() throws FileNotFoundException {
		assert(!(l.getEducational() == null));
		assertThat(l.getEducational().getTypicalLearningTime(), equalTo("15 min"));
	}
	
	@Test
	public void testOBAA_Educational_TypicalAgeRange() throws FileNotFoundException {
		assert(!(l.getEducational() == null));
		assertThat(l.getEducational().getTypicalAgeRange(), equalTo("adult"));
	}
	
	@Test
	public void testOBAA_Educational_IntendedEndUserRoles_Role() throws FileNotFoundException {
		assert(!(l.getEducational() == null));
		assert(!(l.getEducational().getIntendedEndUserRoles() == null));
		assertThat(l.getEducational().getIntendedEndUserRoles().get(0), equalTo("teacher"));
		assertThat(l.getEducational().getIntendedEndUserRoles().get(1), equalTo("manager"));
	}
	
	@Test
	public void testOBAA_Educational_LearningResourceTypes() throws FileNotFoundException {
		assert(!(l.getEducational() == null));
		assert(!(l.getEducational().getLearningResourceTypes() == null));
		assertThat(l.getEducational().getLearningResourceTypes(), hasItems("simulation", "experiment"));
	}
	
	@Test
	public void testOBAA_Educational_Descriptions() throws FileNotFoundException {
		assert(!(l.getEducational() == null));
		assert(!(l.getEducational().getDescriptions() == null));
		assertThat(l.getEducational().getDescriptions(), hasItems("demonstração", "bla bla bla"));
	}
	
	@Test
	public void testOBAA_Educational_Languages() throws FileNotFoundException {
		assert(!(l.getEducational() == null));
		assert(!(l.getEducational().getLanguages() == null));
		assertThat(l.getEducational().getLanguages(), hasItems("pt_BR", "en"));
	}
	
	@Test
	public void testOBAA_Educational_Contexts() throws FileNotFoundException {
		assert(!(l.getEducational() == null));
		assert(!(l.getEducational().getContexts() == null));
		assertThat(l.getEducational().getContexts(), hasItems("Educacao superior", "Profissionalizante"));
	}
	
	/*
	//@Test
	public void testOaiOBAA() throws FileNotFoundException {
		OaiOBAA oai = OaiOBAA.fromFilename("./src/test/java/metadata/oai_obaa.xml");
		l = oai.getMetadata(0);
		assert(!(l.getGeneral() == null));
		assertThat(l.getTitles(), hasItems("Título 1"));
	}
	
	//@Test
	public void testOaiOBAAKeywords() throws FileNotFoundException {
		OaiOBAA oai = OaiOBAA.fromFilename("./src/test/java/metadata/oai_obaa.xml");
		l = oai.getMetadata(0);
		assert(!(l.getGeneral() == null));
		assertThat(l.getKeywords(), hasItems("TCP", "segurança", "ataque Mitnick"));
	}
	
	//@Test
	public void testOaiOBAAGeneralAggregationLevel() throws FileNotFoundException {
		OaiOBAA oai = OaiOBAA.fromFilename("./src/test/java/metadata/oai_obaa.xml");
		l = oai.getMetadata(0);
		assert(!(l.getGeneral() == null));
		assertThat(l.getGeneral().getAggregationLevel(), equalTo(1));
	}
	
	//@Test
	public void testOaiOBAALifeCycleStatus() throws FileNotFoundException {
		OaiOBAA oai = OaiOBAA.fromFilename("./src/test/java/metadata/oai_obaa.xml");
		l = oai.getMetadata(0);
		assert(!(l.getLifeCycle() == null));
		assertThat(l.getLifeCycle().getStatus(), equalTo("Final"));
	}
	
	//@Test
	public void testOaiOBAALifeCycleStatusException() throws FileNotFoundException {
		OaiOBAA oai = OaiOBAA.fromFilename("./src/test/java/metadata/oai_obaa.xml");
		l = oai.getMetadata(0);
		assert(!(l.getLifeCycle() == null));
		try { 
			l.getLifeCycle().setStatus("bla"); 
			fail("Should have thrown exception");
		}
		catch(IllegalArgumentException e) { 
			// thats what we expected
		}

	}

	//@Test
	public void testOaiOBAALifeCycleVersion() throws FileNotFoundException {
		OaiOBAA oai = OaiOBAA.fromFilename("./src/test/java/metadata/oai_obaa.xml");
		l = oai.getMetadata(0);
		assert(!(l.getLifeCycle() == null));
		assertThat(l.getLifeCycle().getVersion(), equalTo("1"));
	}
	
	//@Test
	public void testOaiOBAAHeader() throws FileNotFoundException {
		OaiOBAA oai = OaiOBAA.fromFilename("./src/test/java/metadata/oai_obaa.xml");
		Header h = oai.getHeader(0);
		
		
		assertThat(h.getDatestamp(), equalTo("2010-12-07T17:52:43Z"));
		assertThat(h.getIdentifier(), equalTo("oai:cesta2.cinted.ufrgs.br:123456789/57"));
		assertThat(h.getSetSpec(), equalTo("hdl_123456789_13"));
	}

	//@Test
	public void testOaiOBAARequest() throws FileNotFoundException {
		OaiOBAA oai = OaiOBAA.fromFilename("./src/test/java/metadata/oai_obaa.xml");
		Request r = oai.getRequest();
		
		assertThat(r.getURI(), equalTo("http://cesta2.cinted.ufrgs.br/oai/request"));
		assertThat(r.getMetadataPrefix(), equalTo("obaa"));
		assertThat(r.getVerb(), equalTo("ListRecords"));
	}
	
	//@Test
	public void testOaiOBAADate() throws FileNotFoundException {
		OaiOBAA oai = OaiOBAA.fromFilename("./src/test/java/metadata/oai_obaa.xml");
		
		assertThat(oai.getResponseDate(), equalTo("2011-09-09T21:08:38Z"));
	}
	 * 
	 */
}
