package functionaltests;

import java.io.File;
import java.io.IOException;

import modelos.AbstractDaoTest;
import modelos.DocumentosHibernateDAO;
import modelos.Mapeamento;
import modelos.MapeamentoDAO;
import modelos.PadraoMetadadosDAO;
import modelos.Repositorio;
import modelos.RepositoryDAO;

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

import robo.atualiza.importaOAI.Importer;

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
