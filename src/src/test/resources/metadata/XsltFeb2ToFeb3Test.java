/*
 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitivabrasil.feb.metadata;

import java.io.InputStream;

import cognitivabrasil.obaa.OBAA;
import cognitivabrasil.obaa.OaiOBAA;
import cognitivabrasil.obaa.LifeCycle.Contribute;

import cognitivabrasil.obaa.LifeCycle.Role;
import cognitivabrasil.obaa.Technical.Format;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 *
 * @author paulo
 */
public class XsltFeb2ToFeb3Test {

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
	public void setUp() {
		
		InputStream xml = this.getClass().getResourceAsStream("/oai-feb2.1.xml");
		InputStream xsl = this.getClass().getResourceAsStream("/feb2to3.xsl"); //input xsl

		try {
        		String s = XSLTUtil.transform(xml, xsl);
//        		//System.out.println(s);
			oai = OaiOBAA.fromString(s);
 		} catch (Exception ex) {      			
			throw new RuntimeException(ex);
 		}
	}
	
	@After
	public void tearDown() {
	}

	@Test
	public void title() {
		OBAA l = oai.getMetadata(0);
		assert(!(l.getGeneral() == null));
		assertThat(l.getGeneral().getTitles(), hasItems("Título 1"));
	}
	
	@Test
	public void description() {
		OBAA l = oai.getMetadata(0);
		assertThat(l.getGeneral().getDescriptions(), hasItems("Description 1"));
	}
	
	@Test
	public void keywords() {
		OBAA l = oai.getMetadata(0);
		assertThat(l.getGeneral().getKeywords(), hasItems("Complex multiplication"));
	}
	
	@Test
	public void author() {
		OBAA l = oai.getMetadata(0);
		boolean found = false;
		for(Contribute c : l.getLifeCycle().getContribute()) {
			if("Araujo Neto, Afonso Comba de".equals(c.getFirstEntity())) {
				found = true;
			}
		}
		assert(found);
	}
	
	@Test
	public void location() {
		OBAA l = oai.getMetadata(0);
		assertThat(l.getTechnical().getFirstHttpLocation(), equalTo("http://hdl.handle.net/10183/394"));
	}
	
	@Test
	public void learningResourceType() {
		OBAA l = oai.getMetadata(0);
		assertThat(l.getEducational().getLearningResourceTypesString(), hasItems("Dissertação"));
	}
	
	@Test 
	public void format() {
		OBAA l = oai.getMetadata(0);
		boolean found = false;
		for(Format f : l.getTechnical().getFormats()) {
			if("application/pdf".equals(f.toString())) {
				found = true;
			}
		}
		assert(found);
	}
	
	@Test
	public void resourceDescription() {
		OBAA l = oai.getMetadata(1);
		boolean found = false;
		for(Contribute c : l.getLifeCycle().getContribute()) {
                    Role r = c.getRole();
                    r.setCountry("BR");
                    r.setLanguage("BR");
			if("Caderno de farmácia".equals(c.getFirstEntity()) && "Publicador".equals(r.getTranslated())) {
				found = true;
			}
		}
		assert(found);
	}
	
}
