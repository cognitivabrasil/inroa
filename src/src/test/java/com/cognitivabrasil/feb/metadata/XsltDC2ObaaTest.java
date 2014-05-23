/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitivabrasil.feb.metadata;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import cognitivabrasil.obaa.OBAA;
import cognitivabrasil.obaa.OaiOBAA;

/**
 *
 * @author paulo
 */
public class XsltDC2ObaaTest {

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
        String foo_xml = "src/test/resources/metadata/lume1.xml"; //input xml
        String foo_xsl = "src/xslt/dc2obaa_full.xsl"; //input xsl

        try {
            String s = XSLTUtil.transformFilename(foo_xml, foo_xsl);
            PrintWriter out = new PrintWriter("dc2obaa.xml");
            out.print(s);
            out.close();
            oai = OaiOBAA.fromString(s);
        } catch (Exception ex) {
            fail("Shoudln't throw exception");
        }
    }

    /**
     * Test that location tag is correctly translated
     */
    @Test
    public void testDC2ObaaLocation() {
        OBAA l = oai.getMetadata(5);
        assert (!(l.getTechnical() == null));
        assert (!(l.getTechnical().getLocation() == null));
        assertThat(l.getTechnical().getLocation().get(0).getText(), equalTo("http://hdl.handle.net/10183/399"));

    }

    /**
     * Test that elements with no well formed URL return NULL for the location
     */
    @Test
    public void testDC2ObaaLocationNull() {
        OBAA l = oai.getMetadata(6);
        assert (!(l.getTechnical() == null));
        assertThat(l.getTechnical().getFirstHttpLocation(),
                nullValue());
    }

    @After
    public void tearDown() {
        File dc2obaa = new File("dc2obaa.xml");
        dc2obaa.delete();
    }

    @Test
    public void testCestaCompleteOutput() throws FileNotFoundException {

        try {
            oai.toString();
        } catch (Exception ex) {
            //XSLTUtil.handleException(ex);
            fail("Shoudln't throw exception");
        }
    }

}
