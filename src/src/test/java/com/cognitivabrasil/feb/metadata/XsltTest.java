/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitivabrasil.feb.metadata;

import cognitivabrasil.obaa.OBAA;
import cognitivabrasil.obaa.OaiOBAA;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.fail;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.Ignore;

/**
 *
 * @author paulo
 */
public class XsltTest {

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
        String foo_xml = "src/test/resources/metadata/oai_dc.xml"; //input xml
        String foo_xsl = "src/xslt/dc2obaa_full.xsl"; //input xsl

        try {
            String s = XSLTUtil.transformFilename(foo_xml, foo_xsl);
            oai = OaiOBAA.fromString(s);
        } catch (Exception ex) {
            fail("Shoudln't throw exception");
        }
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testDC2OBAA_title() {
        OBAA l = oai.getMetadata(0);
        assert (!(l.getGeneral() == null));
        assertThat(l.getTitles(), hasItems("Taquaraço: 9 anos de glórias"));
    }

    @Test
    public void testDC2OBAA_identifier() {
        OBAA l = oai.getMetadata(0);
        assert (!(l.getGeneral() == null));
        assert (!(l.getGeneral().getIdentifier() == null));
        assertThat(l.getGeneral().getIdentifier().getEntry(), equalTo("http://hdl.handle.net/10183/394"));
    }

    @Test
    public void testDC2OBAA_General() {
        OBAA l = oai.getMetadata(0);
        assert (!(l.getGeneral() == null));
        assertThat(l.getGeneral().getDescriptions(), hasItems("Descrição 1", "Descrição 2"));
        assertThat(l.getGeneral().getCoverages(), hasItems("Coverage 1", "Coverage 2"));
        assertThat(l.getGeneral().getKeywords(), hasItems("Futebol", "Campeonato do Dacomp"));

    }

    @Test
    public void testDC2OBAA_Rights() {
        OBAA l = oai.getMetadata(0);
        assert (!(l.getRights() == null));
        assertThat(l.getRights().getDescription(), equalTo("Right 1 Right 2"));

    }
}