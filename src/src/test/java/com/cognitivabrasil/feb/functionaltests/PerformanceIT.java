/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitivabrasil.feb.functionaltests;

import com.cognitivabrasil.feb.AppConfig;
import com.cognitivabrasil.feb.data.entities.SubFederacao;
import com.cognitivabrasil.feb.data.services.DocumentService;
import com.cognitivabrasil.feb.data.services.FederationService;
import com.cognitivabrasil.feb.data.services.MappingService;
import com.cognitivabrasil.feb.data.services.MetadataRecordService;
import com.cognitivabrasil.feb.data.services.RepositoryService;
import com.cognitivabrasil.feb.robo.atualiza.subfedOAI.ImporterSubfed;
import com.cognitivabrasil.feb.robo.atualiza.subfedOAI.ParserListSets;
import com.cognitivabrasil.feb.spring.controllers.FEBController;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;
import org.xml.sax.SAXException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class })
@ActiveProfiles("test")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class PerformanceIT extends AbstractTransactionalJUnit4SpringContextTests {

    Logger log = LoggerFactory.getLogger(PerformanceIT.class);

    @Autowired
    RepositoryService repDao;
    @Autowired
    DocumentService docDao;
    @Autowired
    FederationService subDao;
    @Autowired
    MetadataRecordService padDao;
    @Autowired
    MappingService mapDao;

    @PersistenceContext
    private EntityManager em;

//    @Autowired SessionFactory sessionFactory;
    @Test
    @Ignore
    public void testUpdateRepFromXML() throws IOException, ParserConfigurationException, SAXException, TransformerException {
        ArrayList<String> caminhosXML = new ArrayList<>();
        caminhosXML.add("FEB-ufrgs1.xml");
        caminhosXML.add("FEB-ufrgs2.xml");
        caminhosXML.add("FEB-ufrgs3.xml");
        caminhosXML.add("FEB-ufrgs4.xml");
        caminhosXML.add("FEB-ufrgs4.xml");
        caminhosXML.add("FEB-ufrgs6.xml");
        caminhosXML.add("FEB-ufrgs7.xml");
        caminhosXML.add("FEB-ufrgs8.xml");
        caminhosXML.add("FEB-ufrgs9.xml");
        caminhosXML.add("FEB-ufrgs10.xml");
        caminhosXML.add("FEB-ufrgs11.xml");
        caminhosXML.add("FEB-ufrgs12.xml");
        caminhosXML.add("FEB-ufrgs13.xml");
        caminhosXML.add("FEB-ufrgs14.xml");
        caminhosXML.add("FEB-ufrgs15.xml");
        caminhosXML.add("FEB-ufrgs16.xml");
        caminhosXML.add("FEB-ufrgs17.xml");
        caminhosXML.add("FEB-ufrgs18.xml");
        caminhosXML.add("FEB-ufrgs19.xml");
        caminhosXML.add("FEB-ufrgs20.xml");
        caminhosXML.add("FEB-ufrgs21.xml");
        caminhosXML.add("FEB-ufrgs22.xml");
        caminhosXML.add("FEB-ufrgs23.xml");
        caminhosXML.add("FEB-ufrgs24.xml");
        caminhosXML.add("FEB-ufrgs25.xml");
        caminhosXML.add("FEB-ufrgs26.xml");
        caminhosXML.add("FEB-ufrgs27.xml");
        caminhosXML.add("FEB-ufrgs28.xml");
        caminhosXML.add("FEB-ufrgs29.xml");
        caminhosXML.add("FEB-ufrgs30.xml");
        caminhosXML.add("FEB-ufrgs31.xml");
        caminhosXML.add("FEB-ufrgs32.xml");
        caminhosXML.add("FEB-ufrgs33.xml");
        caminhosXML.add("FEB-ufrgs34.xml");
        caminhosXML.add("FEB-ufrgs35.xml");
        caminhosXML.add("FEB-ufrgs36.xml");
        caminhosXML.add("FEB-ufrgs37.xml");
        caminhosXML.add("FEB-ufrgs38.xml");
        caminhosXML.add("FEB-ufrgs39.xml");
        caminhosXML.add("FEB-ufrgs40.xml");
        caminhosXML.add("FEB-ufrgs41.xml");
        caminhosXML.add("FEB-ufrgs42.xml");
        caminhosXML.add("FEB-ufrgs43.xml");
        caminhosXML.add("FEB-ufrgs44.xml");
        caminhosXML.add("FEB-ufrgs45.xml");
        caminhosXML.add("FEB-ufrgs46.xml");
        caminhosXML.add("FEB-ufrgs47.xml");
        caminhosXML.add("FEB-ufrgs48.xml");
        caminhosXML.add("FEB-ufrgs49.xml");
        caminhosXML.add("FEB-ufrgs50.xml");
        caminhosXML.add("FEB-ufrgs51.xml");
        caminhosXML.add("FEB-ufrgs52.xml");
        caminhosXML.add("FEB-ufrgs53.xml");
        caminhosXML.add("FEB-ufrgs54.xml");
        caminhosXML.add("FEB-ufrgs55.xml");
        caminhosXML.add("FEB-ufrgs56.xml");
        caminhosXML.add("FEB-ufrgs57.xml");
        caminhosXML.add("FEB-ufrgs58.xml");
        caminhosXML.add("FEB-ufrgs59.xml");
        caminhosXML.add("FEB-ufrgs60.xml");
        caminhosXML.add("FEB-ufrgs61.xml");
        caminhosXML.add("FEB-ufrgs62.xml");
        caminhosXML.add("FEB-ufrgs63.xml");
        caminhosXML.add("FEB-ufrgs64.xml");
        caminhosXML.add("FEB-ufrgs65.xml");
        caminhosXML.add("FEB-ufrgs66.xml");
        caminhosXML.add("FEB-ufrgs67.xml");
        caminhosXML.add("FEB-ufrgs68.xml");
        caminhosXML.add("FEB-ufrgs69.xml");
        caminhosXML.add("FEB-ufrgs70.xml");
        caminhosXML.add("FEB-ufrgs71.xml");
        caminhosXML.add("FEB-ufrgs72.xml");
        caminhosXML.add("FEB-ufrgs73.xml");
        caminhosXML.add("FEB-ufrgs74.xml");
        caminhosXML.add("FEB-ufrgs75.xml");
        caminhosXML.add("FEB-ufrgs76.xml");
        caminhosXML.add("FEB-ufrgs77.xml");
        caminhosXML.add("FEB-ufrgs78.xml");
        caminhosXML.add("FEB-ufrgs79.xml");
        caminhosXML.add("FEB-ufrgs80.xml");
        caminhosXML.add("FEB-ufrgs81.xml");
        caminhosXML.add("FEB-ufrgs82.xml");
        caminhosXML.add("FEB-ufrgs83.xml");
        caminhosXML.add("FEB-ufrgs84.xml");
        caminhosXML.add("FEB-ufrgs85.xml");
        caminhosXML.add("FEB-ufrgs86.xml");
        caminhosXML.add("FEB-ufrgs87.xml");
        caminhosXML.add("FEB-ufrgs88.xml");
        caminhosXML.add("FEB-ufrgs89.xml");
        caminhosXML.add("FEB-ufrgs90.xml");

        Collections.reverse(caminhosXML);

        String inputXsltFile = "src/xslt/obaa2obaa.xsl"; // input xsl
        String xslt = FileUtils.readFileToString(new File(inputXsltFile));
        SubFederacao subFed = subDao.get("marcos");

        for (String caminho : caminhosXML) {
            StopWatch stop = new StopWatch();
            stop.start("XML " + caminho.substring(caminho.lastIndexOf("/") + 1));
            File arquivoXML = new File(caminho);
            System.out.println("FEB: Lendo XML " + caminho.substring(caminho.lastIndexOf("/") + 1));

            ImporterSubfed imp = new ImporterSubfed();
            imp.setInputFile(arquivoXML);
            imp.setSubFed(subFed);
            imp.setDocDao(docDao);
            imp.update();

            //assertThat(updated, equalTo(2));
            subDao.save(subFed);

            stop.stop();
            System.out.println("XML " + stop.prettyPrint());

        }

    }

    @Test
    @Transactional
    @Ignore
    public void perfomanceTest() throws IOException, ParserConfigurationException, SAXException, TransformerException {
        Long inicio = System.currentTimeMillis();

        String xmlFedSets = "src/test/resources/Fiocruz-listSets.xml";
        ArrayList<String> caminhosXML = new ArrayList<String>();
//        caminhosXML.add("src/test/resources/FEB-fiocruz2.xml");
//        caminhosXML.add("src/test/resources/FEB-fiocruz3.xml");
//        caminhosXML.add("src/test/resources/FEB-fiocruz4.xml");
//        caminhosXML.add("src/test/resources/FEB-fiocruz5.xml");
        caminhosXML.add("src/test/resources/FEB-fiocruz6.xml");

        SubFederacao subFed = new SubFederacao();
        subFed.setVersion("2.1");
        subFed.setName("performanceTest2");
        subFed.setDescricao("xx");
        subFed.setUrl("http://");
        int before = docDao.getAll().size();

        ParserListSets parserListSets = new ParserListSets();
        File xmlFedSetsFile = new File(xmlFedSets);

        Set<String> listaSubrep = parserListSets.parser(xmlFedSetsFile);//efetua a leitura do xml e insere os objetos na base de dados

        subFed.atualizaListaSubRepositorios(listaSubrep);
        assertEquals("Size of repSubFed after.", 1, subFed.getRepositorios().size());

        subDao.save(subFed);

        for (String caminho : caminhosXML) {
            StopWatch stop = new StopWatch();
            stop.start("XML " + caminho.substring(caminho.lastIndexOf("/") + 1));
            File arquivoXML = new File(caminho);
            //System.out.println("FEB: Lendo XML " + caminho.substring(caminho.lastIndexOf("/") + 1));

            com.cognitivabrasil.feb.robo.atualiza.subfedOAI.ImporterSubfed imp = new com.cognitivabrasil.feb.robo.atualiza.subfedOAI.ImporterSubfed();
            imp.setInputFile(arquivoXML);
            imp.setDocDao(docDao);
            imp.setSubFed(subFed);
            imp.update();

            stop.stop();
            //System.out.println("XML " + stop.prettyPrint());

        }

        subDao.save(subFed);
        int docSizeAfterSubFed = docDao.getAll().size();
        int docsUpdated = docSizeAfterSubFed - before;
        //System.out.println("docs updated: " + docsUpdated);
        Long fim = System.currentTimeMillis();
        Long tempoTotal = fim - inicio;
        //System.out.println("Tempo executanto o perfomanceTest: " + Operacoes.formatTimeMillis(tempoTotal));

        /*
         sessionFactory.getCurrentSession().flush();
         sessionFactory.getCurrentSession().clear();
         */
        Long deletei = System.currentTimeMillis();
        subFed = subDao.get(subFed.getId());
        subDao.delete(subFed);
        Long deletef = System.currentTimeMillis();
        //System.out.println("Tempo para deletar a federacao: " + Operacoes.formatTimeMillis(deletef-deletei));
        assertEquals(before, docDao.getAll().size());
        assertEquals(null, subDao.get("performanceTest2"));

    }

    @Test(timeout = 30000)
    @Transactional
    @Ignore
    public void jorjaoTest() throws ParserConfigurationException, SAXException, IOException, TransformerConfigurationException, TransformerException {

        String xmlFedSets = "src/test/resources/Fiocruz-listSets.xml";
        ArrayList<String> caminhosXML = new ArrayList<>();
//        caminhosXML.add("src/test/resources/FEB-fiocruz2.xml");
//        caminhosXML.add("src/test/resources/FEB-fiocruz3.xml");
//        caminhosXML.add("src/test/resources/FEB-fiocruz4.xml");
//        caminhosXML.add("src/test/resources/FEB-fiocruz5.xml");
        caminhosXML.add("src/test/resources/FEB-fiocruz6.xml");

        SubFederacao subFed = new SubFederacao();
        subFed.setVersion("2.1");
        subFed.setName("performanceTest2");
        subFed.setDescricao("xx");
        subFed.setUrl("http://");
        int before = docDao.getAll().size();

        ParserListSets parserListSets = new ParserListSets();
        File xmlFedSetsFile = new File(xmlFedSets);

        Set<String> listaSubrep = parserListSets.parser(xmlFedSetsFile);//efetua a leitura do xml e insere os objetos na base de dados

        subFed.atualizaListaSubRepositorios(listaSubrep);
        assertEquals("Size of repSubFed after.", 1, subFed.getRepositorios().size());

        subDao.save(subFed);

        for (String caminho : caminhosXML) {
            StopWatch stop = new StopWatch();
            stop.start("XML " + caminho.substring(caminho.lastIndexOf("/") + 1));
            File arquivoXML = new File(caminho);
            //System.out.println("FEB: Lendo XML " + caminho.substring(caminho.lastIndexOf("/") + 1));

            com.cognitivabrasil.feb.robo.atualiza.subfedOAI.ImporterSubfed imp = new com.cognitivabrasil.feb.robo.atualiza.subfedOAI.ImporterSubfed();
            imp.setInputFile(arquivoXML);
            imp.setDocDao(docDao);
            imp.setSubFed(subFed);
            imp.update();

            stop.stop();
            //System.out.println("XML " + stop.prettyPrint());

        }

        subDao.save(subFed);

        /*
         sessionFactory.getCurrentSession().flush();
         sessionFactory.getCurrentSession().clear();
         */
        Long deletei = System.currentTimeMillis();
        subFed = subDao.get(subFed.getId());
        subDao.delete(subFed);
        Long deletef = System.currentTimeMillis();
        //System.out.println("Tempo para deletar a federacao: " + Operacoes.formatTimeMillis(deletef-deletei));
        assertEquals(before, docDao.getAll().size());
        assertEquals(null, subDao.get("performanceTest2"));

    }

    @Test
    @Transactional
    @Ignore
    public void XMLdeleteDocsTest() throws IOException, ParserConfigurationException, SAXException, TransformerException {
        Long inicio = System.currentTimeMillis();

        ArrayList<String> caminhosXML = new ArrayList<String>();
//        caminhosXML.add("tmp/deleted1.xml");
        caminhosXML.add("tmp/FEB-ufrgs90.xml");

        SubFederacao subFed = subDao.get(100);
        subFed.setVersion("2.1");

        //System.out.println("repositorios: "+ subFed.getRepositorios());
        int before = docDao.getAll().size();

        for (String caminho : caminhosXML) {
            StopWatch stop = new StopWatch();
            stop.start("XML " + caminho.substring(caminho.lastIndexOf("/") + 1));
            File arquivoXML = new File(caminho);
            //System.out.println("FEB: Lendo XML " + caminho.substring(caminho.lastIndexOf("/") + 1));

            com.cognitivabrasil.feb.robo.atualiza.subfedOAI.ImporterSubfed imp = new com.cognitivabrasil.feb.robo.atualiza.subfedOAI.ImporterSubfed();
            imp.setInputFile(arquivoXML);
            imp.setDocDao(docDao);
            imp.setSubFed(subFed);
            imp.update();

            stop.stop();
            //System.out.println("XML " + stop.prettyPrint());

        }

        subDao.save(subFed);
        int docSizeAfterSubFed = docDao.getAll().size();
        int docsUpdated = docSizeAfterSubFed - before;
        //System.out.println("docs updated: " + docsUpdated);
        Long fim = System.currentTimeMillis();
        Long tempoTotal = fim - inicio;
        //System.out.println("Tempo executanto o perfomanceTest: " + Operacoes.formatTimeMillis(tempoTotal));

    }
}
