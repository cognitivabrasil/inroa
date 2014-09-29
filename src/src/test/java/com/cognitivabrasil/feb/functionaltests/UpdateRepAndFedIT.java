/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitivabrasil.feb.functionaltests;

import com.cognitivabrasil.feb.AppConfig;
import com.cognitivabrasil.feb.data.entities.Mapeamento;
import com.cognitivabrasil.feb.data.entities.Repositorio;
import com.cognitivabrasil.feb.data.entities.SubFederacao;
import com.cognitivabrasil.feb.data.services.DocumentService;
import com.cognitivabrasil.feb.data.services.FederationService;
import com.cognitivabrasil.feb.data.services.MappingService;
import com.cognitivabrasil.feb.data.services.MetadataRecordService;
import com.cognitivabrasil.feb.data.services.RepositoryService;
import com.cognitivabrasil.feb.robo.atualiza.importaOAI.ImporterRep;
import com.cognitivabrasil.feb.robo.atualiza.subfedOAI.ImporterSubfed;
import com.cognitivabrasil.feb.robo.atualiza.subfedOAI.ParserListSets;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.commons.io.FileUtils;

import static org.hamcrest.Matchers.equalTo;

import org.joda.time.DateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXException;

/**
 *
 * @author marcos
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class })
@ActiveProfiles("test")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@DirtiesContext
public class UpdateRepAndFedIT extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private RepositoryService repDao;
    @Autowired
    private DocumentService docDao;
    @Autowired
    private FederationService fedService;
    @Autowired
    private MetadataRecordService padDao;
    @Autowired
    private MappingService mapDao;
    @Autowired
    private ImporterSubfed impSf;
    
    @PersistenceContext
    private EntityManager em;

    @Test
    public void testUpdateRepFromXML() throws IOException, ParserConfigurationException, SAXException, TransformerException {
        String xmlRep = "src/test/resources/repIFRS-records.xml";
        String inputXsltFile = "src/xslt/obaa2obaa.xsl"; // input xsl
        String xslt = FileUtils.readFileToString(new File(inputXsltFile));
        Repositorio rep = repDao.get("marcos");

        Mapeamento m = new Mapeamento();
        m.setXslt(xslt);
        m.setDescription("obaa");
        m.setName("obaa");
        m.setPadraoMetadados(padDao.get(1));
        mapDao.save(m);

        rep.setMapeamento(m);

        int docSizeBefore = docDao.getAll().size();
        ImporterRep imp = new ImporterRep();
        imp.setInputFile(xmlRep);
        imp.setRepositorio(rep);
        imp.setDocDao(docDao);
        int updated = imp.update();
        assertThat(updated, equalTo(2));
        
        DateTime updateTime = DateTime.now();
        rep.setUltimaAtualizacao(updateTime);

        repDao.save(rep);
        em.flush();
        em.clear();
        int docSizeAfter = docDao.getAll().size();
        
        rep = repDao.get("marcos");
        assertThat(rep.getUltimaAtualizacao(), equalTo(updateTime));
        assertThat(rep.getDataXml(), equalTo("2012-07-20T17:09:42Z"));

        assertEquals("Size of Documents after updated Rep", docSizeBefore + 2, docSizeAfter);
    }

    @Test
    public void testUpdateFedFromXML() throws IOException, ParserConfigurationException, SAXException, TransformerException {
        String xmlFedSets = "src/test/resources/fedIFRS-listSets.xml";
        String xmlFedRecords = "src/test/resources/fedIFRS-records.xml";

        SubFederacao subFed = fedService.get("marcos");

        fedService.deleteAllDocs(subFed);

        ParserListSets parserListSets = new ParserListSets();
        File xmlFedSetsFile = new File(xmlFedSets);

        //efetua a leitura do xml e insere os objetos na base de dados
        Set<String> listaSubrep = parserListSets.parser(xmlFedSetsFile);
        assert (listaSubrep.size() == 1);

        subFed.atualizaListaSubRepositorios(listaSubrep);
        assertEquals("Size of repSubFed after.", 1, subFed.getRepositorios().size());

        int docSizeAfter = docDao.getAll().size();

        fedService.save(subFed);

        impSf.setInputFile(xmlFedRecords);
        impSf.setDocDao(docDao);
        impSf.setSubFed(subFed);
        impSf.update();

        DateTime updateTime = DateTime.now();
        String dataXmlTemp = subFed.getDataXmlTemp();
        subFed = fedService.get("marcos");
        subFed.setDataXmlTemp(dataXmlTemp);
        subFed.setUltimaAtualizacao(updateTime);
        fedService.save(subFed);
        
        
        em.flush();
        em.clear();
        int docSizeAfterSubFed = docDao.getAll().size();
        assertThat("Size of Documents after updated Federation", docSizeAfterSubFed, equalTo(docSizeAfter + 2));
        subFed = fedService.get("marcos");
        assertThat(subFed.getUltimaAtualizacao(), equalTo(updateTime));
        assertThat(subFed.getDataXml(), equalTo("2012-07-20T17:16:14Z"));
    }
}
