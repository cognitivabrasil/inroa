package functionaltests;

import java.io.File;
import java.io.IOException;


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

import feb.data.daos.AbstractDaoTest;
import feb.data.daos.DocumentosHibernateDAO;
import feb.data.entities.Mapeamento;
import feb.data.entities.Repositorio;
import feb.data.interfaces.MapeamentoDAO;
import feb.data.interfaces.PadraoMetadadosDAO;
import feb.data.interfaces.RepositoryDAO;
import feb.robo.atualiza.importaOAI.Importer;

/**
 * 
 * @author marcos
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testApplicationContext.xml")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class LumeXmlIT extends AbstractDaoTest {
	@Autowired
	DocumentosHibernateDAO docDao;
	@Autowired
	RepositoryDAO repDao;
	@Autowired
	PadraoMetadadosDAO padDao;
	@Autowired
	MapeamentoDAO mapDao;

	@Test
	public void importLume() throws IOException {
        String inputXmlFile = "src/test/java/feb/metadata/lume_erro_null_documento.xml";
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
        imp.setRepDao(repDao);
        imp.update();
	}
}
