/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;
import org.dbunit.Assertion;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.SortedTable;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.*;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 *
 * @author paulo
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testApplicationContext.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class SubfederacaoHibernateDaoIT extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    SubFederacaoHibernateDAO instance;
    @Autowired
    DataSource dataSource;
    static IDatabaseConnection connection;
    boolean updated = false;

    public SubfederacaoHibernateDaoIT() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        connection.close();
    }

    @Before
    public void init() throws Exception {
        System.out.println("Before");
        // Insere os dados no banco de dados
        DatabaseOperation.CLEAN_INSERT.execute(getConnection(), getBeforeDataSet());
    }

    @After
    public void after() throws Exception {
        System.out.println("After");
        //Limpa a base de dados
        // DatabaseOperation.DELETE_ALL.execute(getConnection(), getBeforeDataSet());
    }

    private IDatabaseConnection getConnection() throws Exception {
        System.out.println("Get Connection");
        // Pega a conexão com o banco de dados
        if (connection == null) {
            Connection con = dataSource.getConnection();
            DatabaseMetaData databaseMetaData = con.getMetaData();
            connection = new DatabaseConnection(con);
            System.out.println("New connection");
        }

        return connection;

    }

    private IDataSet getAfterDataSet() throws Exception {
        System.out.println("get DataSet");
        // Pega o arquivo de para inserir
        File file = new File("src/test/resources/documentosDataAfter.xml");
        return new FlatXmlDataSet(file);
    }

    private IDataSet getBeforeDataSet() throws Exception {
        System.out.println("get DataSet");
        // Pega o arquivo de para inserir
        File file = new File("src/test/resources/documentosDataBefore.xml");
        return new FlatXmlDataSet(file);
    }

    /**
     * Test of delete method, of class RepositoryHibernateDAO.
     *
     * /**
     * Test of get method, of class RepositoryHibernateDAO.
     */
    @Test
    public void testGet() {
        System.out.println("get");
        int id = 1;
        SubFederacao ufrgs = instance.get(id);

        assertEquals("UFRGS", ufrgs.getNome());
    }

    /**
     * Test of getAll method, of class RepositoryHibernateDAO.
     */
    @Test
    public void testGetAll() {
        System.out.println("getAll");;
        List result = instance.getAll();
        assertEquals(3, result.size());
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    @Test
    @Transactional
    public void testRepository() {
        System.out.println("repository");;

        List<SubFederacao> l = instance.getAll();
        SubFederacao ufrgs = l.get(0);

        List<RepositorioSubFed> r = new ArrayList<RepositorioSubFed>(ufrgs.getRepositorios());

        RepositorioSubFed t = r.get(0);

        assertEquals("RepUfrgs1", t.getNome());
        assertEquals("Get from getDocumentos", 2, t.getDocumentos().size());
        assertEquals("Get from size()", 2, (int) t.getSize());


    }

    @Test
    public void testDelete() {
        SubFederacao cesta = instance.get(1);

        instance.delete(cesta);

        List<SubFederacao> l = instance.getAll();
        assertEquals(2, l.size());


    }

    @Test
    public void testGetByName() {
        SubFederacao ufrgs = instance.get("UFRGS");


        assertThat(ufrgs, is(notNullValue()));
        assertEquals("UFRGS", ufrgs.getNome());
        assertEquals(1, ufrgs.getId());
    }

    @Test
    public void testGetByNameNonExisting() {
        SubFederacao fake = instance.get("rgterter");

        assertThat(fake, is(nullValue()));

    }

    @Test
    public void testSaveAndUpdate() throws Exception {
        System.out.println("save");

        SubFederacao f = new SubFederacao();
        f.setNome("Nova");
        f.setUrl("http://nova");
        f.setDataXML(new Date());

        instance.save(f);

        SubFederacao fTeste = instance.get("Nova");
        assertThat(fTeste, is(notNullValue()));
        assertEquals("Nova", fTeste.getNome());

        assertEquals("Nr correto de Subfederacoes apos adicao", 4, instance.getAll().size());

        SubFederacao f2 = instance.get(3);
        f2.setNome("Jorjao");
        f2.setUrl("http://jorjao");

        instance.save(f2);

        updated = true;

    }

    /*
     * This is needed to get over AbstractTransactionalJUnit4SpringContextTests
     * limitations TODO: find a more elegant and generic solution to integrate
     * spring and DbUnit, maybe with annotations?
     */
    @AfterTransaction
    public void testSaveAndUpdate2() throws Exception {
        if (updated) {
            updated = false;
            String[] ignore = {"id", "data_xml", "data_ultima_atualizacao", "descricao"};
            String[] sort = {"nome"};
            Assertion.assertEqualsIgnoreCols(new SortedTable(getAfterDataSet().getTable("dados_subfederacoes"), sort), new SortedTable(getConnection().createDataSet().getTable("dados_subfederacoes"), sort), ignore);

        }
    }
}
