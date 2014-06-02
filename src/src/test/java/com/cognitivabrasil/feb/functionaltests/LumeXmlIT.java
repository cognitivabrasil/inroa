package com.cognitivabrasil.feb.functionaltests;

import com.cognitivabrasil.feb.data.entities.Mapeamento;
import com.cognitivabrasil.feb.data.entities.Repositorio;
import com.cognitivabrasil.feb.data.services.DocumentService;
import com.cognitivabrasil.feb.data.services.MappingService;
import com.cognitivabrasil.feb.data.services.MetadataRecordService;
import com.cognitivabrasil.feb.data.services.RepositoryService;
import com.cognitivabrasil.feb.robo.atualiza.importaOAI.Importer;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author marcos
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class LumeXmlIT extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    DocumentService docDao;
    @Autowired
    RepositoryService repDao;
    @Autowired
    MetadataRecordService padDao;
    @Autowired
    MappingService mapDao;

    @Test
    public void importLume() throws IOException {
        String inputXmlFile = "src/test/resources/metadata/lume_erro_null_documento.xml";
        String inputXsltFile = "src/xslt/dc2obaa_full.xsl"; // input xsl
        String xslt = FileUtils.readFileToString(new File(inputXsltFile));

        Mapeamento m = new Mapeamento();
        m.setXslt(xslt);
        m.setDescription("bla");
        m.setName("teste");
        m.setPadraoMetadados(padDao.get(1));
        mapDao.save(m);

        Repositorio r = repDao.get(1);
        r.setMapeamento(m);

        Importer imp = new Importer();
        imp.setInputFile(new File(inputXmlFile));
        imp.setRepositorio(r);
        imp.setDocDao(docDao);
        imp.update();
    }
}