/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitivabrasil.feb.robo.atualiza.importaOAI;

import com.cognitivabrasil.feb.robo.atualiza.importaOAI.Importer;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import cognitivabrasil.obaa.OBAA;
import cognitivabrasil.obaa.OaiOBAA;
import com.cognitivabrasil.feb.data.entities.Mapeamento;
import com.cognitivabrasil.feb.data.entities.Repositorio;
import com.cognitivabrasil.feb.data.services.DocumentService;
import static org.hamcrest.Matchers.equalTo;
import org.junit.Ignore;

/**
 *
 * @author paulo
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
public class ImporterIT {

    /**
     * Tests that the importer is converting the file and calling the correct
     * number of save() in the document DAO, from a sample DC input.
     *
     * @throws IOException
     */
    @Test
    public void testImportDC() throws IOException {
        String inputXmlFile = "src/test/resources/metadata/oai_dc.xml"; // input xml
        String inputXsltFile = "src/xslt/dc2obaa_full.xsl"; // input xsl
        String xslt = FileUtils.readFileToString(new File(inputXsltFile));

        Mapeamento m = new Mapeamento();
        m.setXslt(xslt);

        Repositorio r = new Repositorio();
        r.setMapeamento(m);

        DocumentService docDao = mock(DocumentService.class);

        Importer imp = new Importer();
        imp.setInputFile(new File(inputXmlFile));
        imp.setRepositorio(r);
        imp.setDocDao(docDao);
        imp.update();

        assertThat(r.getDataOrigemTemp(), equalTo("2011-09-12T12:25:51Z"));

        OaiOBAA oai = imp.getOaiObaa();

        assertEquals(2, oai.getSize());
        assertEquals("2011-09-12T12:25:51Z", oai.getResponseDate());

        OBAA obaa = oai.getMetadata(0);

        assertThat(obaa.getGeneral().getTitles(), hasItems("Taquaraço: 9 anos de glórias", "Taquaraço: 9 years of glory"));

        // Verify that save() was called twice on mocked docDao
        verify(docDao, times(2)).save(isA(OBAA.class), isA(metadata.Header.class), isA(Repositorio.class));

    }

    @Test
    public void testImportLOM() throws IOException {
        String inputXmlFile = "src/test/resources/metadata/oai_lom.xml"; // input xml
        String inputXsltFile = "src/xslt/lom2obaa_full.xsl"; // input xsl
        String xslt = FileUtils.readFileToString(new File(inputXsltFile));

        Mapeamento m = new Mapeamento();
        m.setXslt(xslt);

        Repositorio r = new Repositorio();
        r.setMapeamento(m);

        DocumentService docDao = mock(DocumentService.class);

        Importer imp = new Importer();
        imp.setInputFile(new File(inputXmlFile));
        imp.setRepositorio(r);
        imp.setDocDao(docDao);
        imp.update();

        OaiOBAA oai = imp.getOaiObaa();


        assertEquals(1, oai.getSize());

        assertEquals("2011-09-09T21:08:38Z", oai.getResponseDate());

        OBAA obaa = oai.getMetadata(0);

        assertThat(obaa.getGeneral().getTitles(), hasItems("Título 1"));

        // Verify that save() was called twice on mocked docDao
        verify(docDao, times(1)).save(isA(OBAA.class), isA(metadata.Header.class), isA(Repositorio.class));

    }
}
