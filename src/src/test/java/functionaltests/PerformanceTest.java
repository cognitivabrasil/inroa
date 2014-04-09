/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package functionaltests;

import feb.data.daos.AbstractDaoTest;
import feb.data.entities.RepositorioSubFed;
import feb.data.entities.SubFederacao;
import feb.data.interfaces.*;
import feb.robo.atualiza.subfedOAI.ParserListSets;
import feb.spring.controllers.FederationsController;
import feb.util.Operacoes;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import org.apache.log4j.Logger;
import static org.junit.Assert.assertEquals;

import org.hibernate.SessionFactory;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.util.StopWatch;
import org.xml.sax.SAXException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 *
 * @author marcos
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testApplicationContext.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class PerformanceTest extends AbstractTransactionalJUnit4SpringContextTests {
	Logger log = Logger.getLogger(PerformanceTest.class);

    @Autowired
    RepositoryDAO repDao;
    @Autowired
    DocumentosDAO docDao;
    @Autowired
    SubFederacaoDAO subDao;
    @Autowired
    PadraoMetadadosDAO padDao;
    @Autowired
    MapeamentoDAO mapDao;
    @Autowired SessionFactory sessionFactory;

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
            System.out.println("FEB: Lendo XML " + caminho.substring(caminho.lastIndexOf("/") + 1));

            feb.robo.atualiza.subfedOAI.Importer imp = new feb.robo.atualiza.subfedOAI.Importer();
            imp.setInputFile(arquivoXML);
            imp.setDocDao(docDao);
            imp.setSubFed(subFed);
            imp.update();
            
            stop.stop();
            System.out.println("XML " + stop.prettyPrint());
            

        }

        subDao.save(subFed);
        int docSizeAfterSubFed = docDao.getAll().size();
        int docsUpdated = docSizeAfterSubFed - before;
        System.out.println("docs updated: " + docsUpdated);
        Long fim = System.currentTimeMillis();
        Long tempoTotal = fim-inicio;
        System.out.println("Tempo executanto o perfomanceTest: " + Operacoes.formatTimeMillis(tempoTotal));
        
        sessionFactory.getCurrentSession().flush();
        sessionFactory.getCurrentSession().clear();


         Long deletei = System.currentTimeMillis();
         subFed = subDao.get(subFed.getId());
        subDao.delete(subFed);
        Long deletef = System.currentTimeMillis();
        System.out.println("Tempo para deletar a federacao: " + Operacoes.formatTimeMillis(deletef-deletei));
        assertEquals(before, docDao.getAll().size());
        assertEquals(null, subDao.get("performanceTest2"));

    }
    
    @Ignore
    @Test(timeout=30000)
    @Transactional
    public void jorjaoTest() throws ParserConfigurationException, SAXException, IOException, TransformerConfigurationException, TransformerException {
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
            System.out.println("FEB: Lendo XML " + caminho.substring(caminho.lastIndexOf("/") + 1));

            feb.robo.atualiza.subfedOAI.Importer imp = new feb.robo.atualiza.subfedOAI.Importer();
            imp.setInputFile(arquivoXML);
            imp.setDocDao(docDao);
            imp.setSubFed(subFed);
            imp.update();
            
            stop.stop();
            System.out.println("XML " + stop.prettyPrint());
            

        }

        subDao.save(subFed);
        int docSizeAfterSubFed = docDao.getAll().size();
        int docsUpdated = docSizeAfterSubFed - before;
        System.out.println("docs updated: " + docsUpdated);
        Long fim = System.currentTimeMillis();
        Long tempoTotal = fim-inicio;
        System.out.println("Tempo executanto o perfomanceTest: " + Operacoes.formatTimeMillis(tempoTotal));
        
        sessionFactory.getCurrentSession().flush();
        sessionFactory.getCurrentSession().clear();


         Long deletei = System.currentTimeMillis();
         subFed = subDao.get(subFed.getId());
        subDao.delete(subFed);
        Long deletef = System.currentTimeMillis();
        System.out.println("Tempo para deletar a federacao: " + Operacoes.formatTimeMillis(deletef-deletei));
        assertEquals(before, docDao.getAll().size());
        assertEquals(null, subDao.get("performanceTest2"));

    }
    
    

    @Test
    @Transactional
    @Ignore
    public void XMLdeleteDocsTest() throws IOException, ParserConfigurationException, SAXException, TransformerException{
          Long inicio = System.currentTimeMillis();

        ArrayList<String> caminhosXML = new ArrayList<String>();
//        caminhosXML.add("tmp/deleted1.xml");
        caminhosXML.add("tmp/FEB-ufrgs90.xml");


        SubFederacao subFed = subDao.get(100);
        subFed.setVersion("2.1");

        System.out.println("repositorios: "+ subFed.getRepositorios());

        int before = docDao.getAll().size();


        for (String caminho : caminhosXML) {
        	StopWatch stop = new StopWatch();
        	stop.start("XML " + caminho.substring(caminho.lastIndexOf("/") + 1));
            File arquivoXML = new File(caminho);
            System.out.println("FEB: Lendo XML " + caminho.substring(caminho.lastIndexOf("/") + 1));

            feb.robo.atualiza.subfedOAI.Importer imp = new feb.robo.atualiza.subfedOAI.Importer();
            imp.setInputFile(arquivoXML);
            imp.setDocDao(docDao);
            imp.setSubFed(subFed);
            imp.update();

            stop.stop();
            System.out.println("XML " + stop.prettyPrint());


        }

        subDao.save(subFed);
        int docSizeAfterSubFed = docDao.getAll().size();
        int docsUpdated = docSizeAfterSubFed - before;
        System.out.println("docs updated: " + docsUpdated);
        Long fim = System.currentTimeMillis();
        Long tempoTotal = fim-inicio;
        System.out.println("Tempo executanto o perfomanceTest: " + Operacoes.formatTimeMillis(tempoTotal));

    }
}
