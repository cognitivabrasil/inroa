/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package functionaltests;

import com.cognitivabrasil.feb.data.services.FederationService;
import com.cognitivabrasil.feb.data.services.RepositoryService;
import com.cognitivabrasil.feb.data.services.MetadataRecordService;
import com.cognitivabrasil.feb.data.services.MappingService;
import com.cognitivabrasil.feb.data.services.DocumentService;
import feb.data.daos.AbstractDaoTest;
import com.cognitivabrasil.feb.data.entities.Mapeamento;
import com.cognitivabrasil.feb.data.entities.Repositorio;
import com.cognitivabrasil.feb.data.entities.RepositorioSubFed;
import com.cognitivabrasil.feb.data.entities.SubFederacao;
import feb.data.interfaces.*;
import feb.robo.atualiza.importaOAI.Importer;
import feb.robo.atualiza.importaOAI.XMLtoDB;
import feb.robo.atualiza.subfedOAI.ParserListSets;
import java.io.File;
import java.io.IOException;
import java.util.Set;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.Assert.assertEquals;
import org.junit.Ignore;
import org.xml.sax.SAXException;

/**
 *
 * @author marcos
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testApplicationContext.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class UpdateRepAndFedIt extends AbstractDaoTest {

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

    @Test
    public void testRepAndSubFedWrhithSameEntry() throws IOException, ParserConfigurationException, SAXException, TransformerException {
        String xmlRep = "src/test/resources/repIFRS-records.xml";
        String xmlFedSets = "src/test/resources/fedIFRS-listSets.xml";
        String xmlFedRecords = "src/test/resources/fedIFRS-records.xml";
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

        rep.dellAllDocs();
        int docSize = docDao.getAll().size();
        Importer imp = new Importer();
        imp.setInputFile(xmlRep);
        imp.setRepositorio(rep);
        imp.setDocDao(docDao);
        imp.setRepDao(repDao);
        int updated = imp.update();
        assert (updated > 0);
        repDao.save(rep);
        int docSizeAfterRep = docDao.getAll().size();
        assertEquals("Size of Documents after updated Rep", docSize + 2, docSizeAfterRep);
        SubFederacao subFed = subDao.get("marcos");

        for (RepositorioSubFed r : subFed.getRepositorios()) {
            r.dellAllDocs();
        }

        ParserListSets parserListSets = new ParserListSets();
        File xmlFedSetsFile = new File(xmlFedSets);

        Set<String> listaSubrep = parserListSets.parser(xmlFedSetsFile);//efetua a leitura do xml e insere os objetos na base de dados
        assert (listaSubrep.size() == 1);

        subFed.atualizaListaSubRepositorios(listaSubrep);
        assertEquals("Size of repSubFed after.", 1, subFed.getRepositorios().size());

        subDao.save(subFed);

        feb.robo.atualiza.subfedOAI.Importer impSF = new feb.robo.atualiza.subfedOAI.Importer();
        impSF.setInputFile(xmlFedRecords);
        impSF.setDocDao(docDao);
        impSF.setSubFed(subFed);
        impSF.update();

        subDao.save(subFed);
        int docSizeAfterSubFed = docDao.getAll().size();
        assertEquals("Size of Documents after updated Federation", docSizeAfterRep + 2, docSizeAfterSubFed);

    }
}
