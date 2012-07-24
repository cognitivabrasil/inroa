/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package functionaltests;

import feb.data.daos.AbstractDaoTest;
import feb.data.entities.Mapeamento;
import feb.data.entities.Repositorio;
import feb.data.entities.RepositorioSubFed;
import feb.data.entities.SubFederacao;
import feb.data.interfaces.*;
import feb.robo.atualiza.importaOAI.Importer;
import feb.robo.atualiza.subfedOAI.ParserListSets;
import feb.util.Operacoes;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
import org.xml.sax.SAXException;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author marcos
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testApplicationContext.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class PerformanceTest extends AbstractDaoTest {

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

    @Test
    public void perfomanceTest() throws IOException, ParserConfigurationException, SAXException, TransformerException {
        Long inicio = System.currentTimeMillis();

        String xmlFedSets = "src/test/resources/Fiocruz-listSets.xml";
        ArrayList<String> caminhosXML = new ArrayList<String>();
        caminhosXML.add("src/test/resources/FEB-fiocruz2.xml");
        caminhosXML.add("src/test/resources/FEB-fiocruz3.xml");
        caminhosXML.add("src/test/resources/FEB-fiocruz4.xml");
        caminhosXML.add("src/test/resources/FEB-fiocruz5.xml");
        caminhosXML.add("src/test/resources/FEB-fiocruz6.xml");

        SubFederacao subFed = subDao.get("marcos");
        subFed.setVersion("2.1");
        int before = docDao.getAll().size();

        for (RepositorioSubFed r : subFed.getRepositorios()) {
            r.dellAllDocs();
        }

        ParserListSets parserListSets = new ParserListSets();
        File xmlFedSetsFile = new File(xmlFedSets);

        Set<String> listaSubrep = parserListSets.parser(xmlFedSetsFile);//efetua a leitura do xml e insere os objetos na base de dados


        subFed.atualizaListaSubRepositorios(listaSubrep);
        assertEquals("Size of repSubFed after.", 1, subFed.getRepositorios().size());

        subDao.save(subFed);

        for (String caminho : caminhosXML) {

            File arquivoXML = new File(caminho);
            System.out.println("FEB: Lendo XML " + caminho.substring(caminho.lastIndexOf("/") + 1));

            feb.robo.atualiza.subfedOAI.Importer imp = new feb.robo.atualiza.subfedOAI.Importer();
            imp.setInputFile(arquivoXML);
            imp.setDocDao(docDao);
            imp.setSubFed(subFed);
            imp.update();

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
