/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feb.ferramentaBusca;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import static org.junit.Assert.assertEquals;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import feb.data.entities.Consulta;
import feb.ferramentaBusca.Recuperador;

/**
 *
 * @author cei
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testApplicationContext.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
public class RecuperadorTest {

    Recuperador r;

    public RecuperadorTest() {
        r = new Recuperador();
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Teste para verificar a String SQL sem consulta por autor
     */
    @Test
        
    public void testQuery() {

        String sqlOrdenacao = "') GROUP BY d.id ORDER BY SUM(weight) DESC;";
        ArrayList<String> tokensConsulta = new ArrayList<String>();

        tokensConsulta.add("gremi");

        assertEquals("SELECT d.* FROM r1weights r1w, documentos d WHERE r1w.documento_id=d.id  AND (r1w.token='gremi') GROUP BY d.id ORDER BY SUM(weight) DESC;", r.buscaConfederacao(tokensConsulta, sqlOrdenacao, false));

    }

    /**
     * Teste para verificar a String SQL sem consulta com autor sem query
     */
    @Test
    public void testQueryWithAuthorWithoutQuery() {
        ArrayList<String> tokensConsulta = new ArrayList<String>();
        String sqlAutorSemQuery = "a.documento=d.id AND a.nome~@@'Carlos Heitor' GROUP BY d.id, a.nome ORDER BY (qgram(a.nome, 'Carlos Heitor')) DESC;";

        assertEquals("SELECT d.* FROM documentos d, autores a WHERE a.documento=d.id AND a.nome~@@'Carlos Heitor' GROUP BY d.id, a.nome ORDER BY (qgram(a.nome, 'Carlos Heitor')) DESC;", r.buscaConfederacao(tokensConsulta, sqlAutorSemQuery, true));
    }

    /**
     * Teste para verificar a String SQL com consulta por autor e com uma query
     */
    @Test
    public void testQueryWithAuthorWithQuery() {
        ArrayList<String> tokensConsulta = new ArrayList<String>();
        tokensConsulta.add("educa");
        String sqlAutorComQuery = "') AND a.documento=d.id AND a.nome~@@'Lília Ferreira Lobo' GROUP BY d.id, a.nome ORDER BY (qgram(a.nome, 'Lília Ferreira Lobo')) DESC, SUM (weight) DESC;";

        assertEquals("SELECT d.* FROM r1weights r1w, documentos d, autores a WHERE r1w.documento_id=d.id  AND (r1w.token='educa') AND a.documento=d.id AND a.nome~@@'Lília Ferreira Lobo' GROUP BY d.id, a.nome ORDER BY (qgram(a.nome, 'Lília Ferreira Lobo')) DESC, SUM (weight) DESC;", r.buscaConfederacao(tokensConsulta, sqlAutorComQuery, true));

    }

    @Test
    public void testQueryRepLocal() {
        ArrayList<String> tokensConsulta = new ArrayList<String>();
        Consulta c = new Consulta();        
        tokensConsulta.add("educa");

        Set <Integer> repositorios = new HashSet<Integer>();
        repositorios.add(new Integer(1));
        c.setRepositorios(repositorios);
        
        String sqlOrdenacao = "') GROUP BY d.id ORDER BY SUM(weight) DESC;";
        
        String output = r.busca_repLocal(tokensConsulta, c, sqlOrdenacao);
        //System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n\n"+r.busca_repLocal(tokensConsulta, c, sqlOrdenacao));
        assertEquals("SELECT d.* FROM r1weights r1w, documentos d  WHERE r1w.documento_id=d.id  AND ( d.id_repositorio=1) AND (r1w.token='educa') GROUP BY d.id ORDER BY SUM(weight) DESC;", output);
    }

    @Test
    public void testQueryRepLocalMaisDeUm() {
        ArrayList<String> tokensConsulta = new ArrayList<String>();
        Consulta c = new Consulta();        
        tokensConsulta.add("educa");

        Set <Integer> repositorios = new TreeSet<Integer>();
        repositorios.add(new Integer(1));
        repositorios.add(new Integer(2));
        c.setRepositorios(repositorios);
        
        String sqlOrdenacao = "') GROUP BY d.id ORDER BY SUM(weight) DESC;";
        //System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n\n"+r.busca_repLocal(tokensConsulta, c, sqlOrdenacao));
        assertEquals("SELECT d.* FROM r1weights r1w, documentos d  WHERE r1w.documento_id=d.id  AND ( d.id_repositorio=1 OR d.id_repositorio=2) AND (r1w.token='educa') GROUP BY d.id ORDER BY SUM(weight) DESC;", r.busca_repLocal(tokensConsulta, c, sqlOrdenacao));
    }
        
    @Test
    public void testQueryRepLocalAutor() {
        ArrayList<String> tokensConsulta = new ArrayList<String>();
        tokensConsulta.add("ataqu");
        Consulta c = new Consulta();
        
        Set <Integer> repositorios = new TreeSet<Integer>();
        repositorios.add(new Integer(328));
        c.setRepositorios(repositorios);
        c.setAutor("Liane Tarouco");
        
        String sqlOrdenacao = "') AND a.documento=d.id AND a.nome~@@'Liane Tarouco' GROUP BY d.id ORDER BY SUM (weight) DESC;";
        //  System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n\n"+r.busca_repLocal(tokensConsulta, id, sqlOrdenacao, false));
        assertEquals("SELECT d.* FROM r1weights r1w, documentos d, autores a WHERE r1w.documento_id=d.id  AND ( d.id_repositorio=328) AND (r1w.token='ataqu') AND a.documento=d.id AND a.nome~@@'Liane Tarouco' GROUP BY d.id ORDER BY SUM (weight) DESC;", r.busca_repLocal(tokensConsulta, c, sqlOrdenacao));
    }

    @Test
    public void testQuerySubfed() {
        ArrayList<String> tokensConsulta = new ArrayList<String>();
        tokensConsulta.add("educa");
        Consulta c = new Consulta();
        
        Set <Integer> federacoes = new TreeSet<Integer>();
        federacoes.add(new Integer(1));
        c.setFederacoes(federacoes);        
        
        String sqlOrdenacao = "') GROUP BY d.id ORDER BY SUM(weight) DESC;";
        //System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n\n"+r.busca_subfed(tokensConsulta, id, sqlOrdenacao));
        assertEquals("SELECT d.* FROM r1weights r1w, documentos d, repositorios_subfed rsf WHERE r1w.documento_id=d.id AND d.id_rep_subfed = rsf.id  AND (rsf.id_subfed=1) AND (r1w.token='educa') GROUP BY d.id ORDER BY SUM(weight) DESC;", r.busca_subfed(tokensConsulta, c, sqlOrdenacao));
    }

    @Test
    public void testQuerySubfedAutor() {
        ArrayList<String> tokensConsulta = new ArrayList<String>();
        tokensConsulta.add("educa");
        Consulta c = new Consulta();
        
        Set <Integer> federacoes = new TreeSet<Integer>();
        federacoes.add(new Integer(1));
        c.setFederacoes(federacoes); 
        c.setAutor("Liane Tarouco");
        
        String sqlOrdenacao = "') AND a.documento=d.id AND a.nome~@@'Liane Tarouco' GROUP BY d.id ORDER BY SUM (weight) DESC;";

        String sql = "SELECT d.* FROM r1weights r1w, documentos d, repositorios_subfed rsf, autores a WHERE r1w.documento_id=d.id AND d.id_rep_subfed = rsf.id AND (rsf.id_subfed=1) AND (r1w.token='educa') AND a.documento=d.id AND a.nome~@@'Liane Tarouco' GROUP BY d.id ORDER BY SUM (weight) DESC;";
        //System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n\n"+r.busca_subfed(tokensConsulta, id, sqlOrdenacao));
        assertEquals(sql, r.busca_subfed(tokensConsulta, c, sqlOrdenacao));
    }

    @Test
    public void testQuerySubRep() {
        ArrayList<String> tokensConsulta = new ArrayList<String>();
        tokensConsulta.add("educa");
        Consulta c = new Consulta();

        Set <Integer> repSubfed = new TreeSet<Integer>();
        repSubfed.add(new Integer(1));
        c.setRepSubfed(repSubfed);         
        
        String sqlOrdenacao = "') GROUP BY d.id ORDER BY SUM(weight) DESC;";

        //System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n\n"+r.busca_subRep(tokensConsulta, id, sqlOrdenacao));
        assertEquals("SELECT d.* FROM r1weights r1w, documentos d WHERE r1w.documento_id=d.id  AND (d.id_rep_subfed=1) AND (r1w.token='educa') GROUP BY d.id ORDER BY SUM(weight) DESC;", r.busca_subRep(tokensConsulta, c, sqlOrdenacao));
    }

    @Test
    public void testQuerySubRepAutor() {
        ArrayList<String> tokensConsulta = new ArrayList<String>();
        tokensConsulta.add("educa");
        Consulta c = new Consulta();
        
        Set <Integer> repSubfed = new TreeSet<Integer>();
        repSubfed.add(new Integer(1));
        c.setRepSubfed(repSubfed);          
        c.setAutor("Liane Tarouco");
        
        String sqlOrdenacao = "') AND a.documento=d.id AND a.nome~@@'Liane Tarouco' GROUP BY d.id ORDER BY SUM (weight) DESC;";

        String sql = "SELECT d.* FROM r1weights r1w, documentos d, autores a WHERE r1w.documento_id=d.id  AND (d.id_rep_subfed=1) AND (r1w.token='educa') AND a.documento=d.id AND a.nome~@@'Liane Tarouco' GROUP BY d.id ORDER BY SUM (weight) DESC;";

        //System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n\n"+r.busca_subRep(tokensConsulta, id, sqlOrdenacao));

        assertEquals(sql, r.busca_subRep(tokensConsulta, c, sqlOrdenacao));
    }

    @Test
    public void testQueryRepLocalSubfed() {
        ArrayList<String> tokensConsulta = new ArrayList<String>();
        tokensConsulta.add("educa");
        Consulta c = new Consulta();
        
        Set <Integer> rep = new TreeSet<Integer>();
        rep.add(new Integer(1));
        c.setRepositorios(rep);
        
        Set <Integer> subfed = new TreeSet<Integer>();
        subfed.add(new Integer(1));
        c.setFederacoes(subfed);
        
        String sqlOrdenacao = "') GROUP BY d.id ORDER BY SUM(weight) DESC;";

        String sql = "SELECT d.* FROM r1weights r1w, documentos d, repositorios_subfed rsf WHERE r1w.documento_id=d.id AND ( (d.id_rep_subfed = rsf.id AND (rsf.id_subfed=1)) OR ( d.id_repositorio=1)) AND (r1w.token='educa') GROUP BY d.id ORDER BY SUM(weight) DESC;";

       // System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n\n"+r.busca_repLocal_subfed(tokensConsulta, c,sqlOrdenacao));
        
        assertEquals(sql, r.busca_repLocal_subfed(tokensConsulta, c, sqlOrdenacao));
    }

    @Test
    public void testQueryRepLocalSubfedAutor() {
        ArrayList<String> tokensConsulta = new ArrayList<String>();
        tokensConsulta.add("educa");
        Consulta c = new Consulta();
        
        
        Set <Integer> rep = new TreeSet<Integer>();
        rep.add(new Integer(1));
        c.setRepositorios(rep);
        
        Set <Integer> subfed = new TreeSet<Integer>();
        subfed.add(new Integer(1));
        c.setFederacoes(subfed);
        
        c.setAutor("Liane Tarouco");
        
        String sqlOrdenacao = "') AND a.documento=d.id AND a.nome~@@'Liane Tarouco' GROUP BY d.id ORDER BY SUM (weight) DESC;";

        String sql = "SELECT d.* FROM r1weights r1w, documentos d, repositorios_subfed rsf, autores a WHERE r1w.documento_id=d.id AND ( (d.id_rep_subfed = rsf.id AND (rsf.id_subfed=1)) OR ( d.id_repositorio=1)) AND (r1w.token='educa') AND a.documento=d.id AND a.nome~@@'Liane Tarouco' GROUP BY d.id ORDER BY SUM (weight) DESC;";
//        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n\n"+r.busca_repLocal_subfed(tokensConsulta, id,id,sqlOrdenacao));
        assertEquals(sql, r.busca_repLocal_subfed(tokensConsulta, c, sqlOrdenacao));
    }

    @Test
    public void testQueryRepLocalSubrep() {
        ArrayList<String> tokensConsulta = new ArrayList<String>();
        tokensConsulta.add("educa");

        Consulta c = new Consulta();
                
        Set <Integer> rep = new TreeSet<Integer>();
        rep.add(new Integer(1));
        c.setRepositorios(rep);
        
        Set <Integer> repSubfed = new TreeSet<Integer>();
        repSubfed.add(new Integer(1));
        c.setRepSubfed(repSubfed);
        
        String sqlOrdenacao = "') GROUP BY d.id ORDER BY SUM(weight) DESC;";

        String sql = "SELECT d.* FROM r1weights r1w, documentos d WHERE r1w.documento_id=d.id AND ( d.id_repositorio=1 OR d.id_rep_subfed=1) AND (r1w.token='educa') GROUP BY d.id ORDER BY SUM(weight) DESC;";
//        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n\n"+r.busca_repLocal_subrep(tokensConsulta, id, id, sqlOrdenacao));
        assertEquals(sql, r.busca_repLocal_subrep(tokensConsulta, c, sqlOrdenacao));
    }

    @Test  
    public void testQueryRepLocalSubrepAutor() {
        ArrayList<String> tokensConsulta = new ArrayList<String>();
        tokensConsulta.add("educa");

        Consulta c = new Consulta();
        
        Set <Integer> rep = new TreeSet<Integer>();
        rep.add(new Integer(1));
        c.setRepositorios(rep);
        
        Set <Integer> repSubfed = new TreeSet<Integer>();
        repSubfed.add(new Integer(1));
        c.setRepSubfed(repSubfed);
        
        c.setAutor("Liane Tarouco");
        
        String sqlOrdenacao = "') AND a.documento=d.id AND a.nome~@@'Liane Tarouco' GROUP BY d.id ORDER BY SUM (weight) DESC;";

        String sql = "SELECT d.* FROM r1weights r1w, documentos d, autores a WHERE r1w.documento_id=d.id AND ( d.id_repositorio=1 OR d.id_rep_subfed=1) AND (r1w.token='educa') AND a.documento=d.id AND a.nome~@@'Liane Tarouco' GROUP BY d.id ORDER BY SUM (weight) DESC;";
//        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n\n"+r.busca_repLocal_subrep(tokensConsulta, id, id, sqlOrdenacao));
        assertEquals(sql, r.busca_repLocal_subrep(tokensConsulta, c, sqlOrdenacao));
    }

    @Test
    public void testQuerySubfedSubrep() {
        ArrayList<String> tokensConsulta = new ArrayList<String>();
        tokensConsulta.add("educa");
        Consulta c = new Consulta();
        
        Set <Integer> fed = new TreeSet<Integer>();
        fed.add(new Integer(1));
        c.setFederacoes(fed);
        
        Set <Integer> repSubfed = new TreeSet<Integer>();
        repSubfed.add(new Integer(1));
        c.setRepSubfed(repSubfed);
        
        String sqlOrdenacao = "') GROUP BY d.id ORDER BY SUM(weight) DESC;";

        String sql = "SELECT d.* FROM r1weights r1w, documentos d, repositorios_subfed rsf WHERE r1w.documento_id=d.id AND d.id_rep_subfed = rsf.id AND ( rsf.id_subfed=1 OR rsf.id=1) AND (r1w.token='educa') GROUP BY d.id ORDER BY SUM(weight) DESC;";
        //System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n\n"+r.busca_subfed_subrep(tokensConsulta, id, id, sqlOrdenacao));
        assertEquals(sql, r.busca_subfed_subrep(tokensConsulta, c, sqlOrdenacao));
    }

    @Test
    public void testQuerySubfedSubrepAutor() {
        ArrayList<String> tokensConsulta = new ArrayList<String>();
        tokensConsulta.add("educa");
        Consulta c = new Consulta();
        
        Set <Integer> fed = new TreeSet<Integer>();
        fed.add(new Integer(1));
        c.setFederacoes(fed);
        
        Set <Integer> repSubfed = new TreeSet<Integer>();
        repSubfed.add(new Integer(1));
        c.setRepSubfed(repSubfed);        
        
        c.setAutor("Liane Tarouco");
        
        String sqlOrdenacao = "') AND a.documento=d.id AND a.nome~@@'Liane Tarouco' GROUP BY d.id ORDER BY SUM (weight) DESC;";

        String sql = "SELECT d.* FROM r1weights r1w, documentos d, repositorios_subfed rsf, autores a WHERE r1w.documento_id=d.id AND d.id_rep_subfed = rsf.id AND ( rsf.id_subfed=1 OR rsf.id=1) AND (r1w.token='educa') AND a.documento=d.id AND a.nome~@@'Liane Tarouco' GROUP BY d.id ORDER BY SUM (weight) DESC;";
       // System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n\n"+r.busca_subfed_subrep(tokensConsulta, c, sqlOrdenacao));
        assertEquals(sql, r.busca_subfed_subrep(tokensConsulta, c, sqlOrdenacao));
    }

    @Test
    public void testQuerybusca_repLocal_subfed_subrep() {
        ArrayList<String> tokensConsulta = new ArrayList<String>();
        tokensConsulta.add("educa");
        Consulta c = new Consulta();
        
        Set <Integer> rep = new TreeSet<Integer>();
        rep.add(new Integer(1));
        c.setRepositorios(rep);
        
        Set <Integer> fed = new TreeSet<Integer>();
        fed.add(new Integer(1));
        c.setFederacoes(fed);
        
        Set <Integer> repSubfed = new TreeSet<Integer>();
        repSubfed.add(new Integer(1));
        c.setRepSubfed(repSubfed);        
        
        String sqlOrdenacao = "') GROUP BY d.id ORDER BY SUM(weight) DESC;";

        String sql = "SELECT d.* FROM r1weights r1w, documentos d, repositorios_subfed rsf WHERE r1w.documento_id=d.id AND ( (d.id_rep_subfed = rsf.id AND ( rsf.id_subfed=1)) OR ( d.id_repositorio=1 OR rsf.id=1)) AND (r1w.token='educa') GROUP BY d.id ORDER BY SUM(weight) DESC;";
             // System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n\n"+r.busca_repLocal_subfed_subrep(tokensConsulta, c, sqlOrdenacao));
        assertEquals(sql, r.busca_repLocal_subfed_subrep(tokensConsulta, c, sqlOrdenacao));
    }

    @Test   
    public void testQuerybusca_repLocal_subfed_subrepAutor() {
        ArrayList<String> tokensConsulta = new ArrayList<String>();
        tokensConsulta.add("educa");
        Consulta c = new Consulta();
        
        Set <Integer> rep = new TreeSet<Integer>();
        rep.add(new Integer(1));
        c.setRepositorios(rep);
        
        Set <Integer> fed = new TreeSet<Integer>();
        fed.add(new Integer(1));
        c.setFederacoes(fed);
        
        Set <Integer> repSubfed = new TreeSet<Integer>();
        repSubfed.add(new Integer(1));
        c.setRepSubfed(repSubfed);  
        
        c.setAutor("Liane Tarouco");
        
        String sqlOrdenacao = "') AND a.documento=d.id AND a.nome~@@'Liane Tarouco' GROUP BY d.id ORDER BY SUM (weight) DESC;";

        String sql = "SELECT d.* FROM r1weights r1w, documentos d, repositorios_subfed rsf, autores a WHERE r1w.documento_id=d.id AND ( (d.id_rep_subfed = rsf.id AND ( rsf.id_subfed=1)) OR ( d.id_repositorio=1 OR rsf.id=1)) AND (r1w.token='educa') AND a.documento=d.id AND a.nome~@@'Liane Tarouco' GROUP BY d.id ORDER BY SUM (weight) DESC;";
        //       System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n\n"+r.busca_repLocal_subfed_subrep(tokensConsulta, id, id, id, sqlOrdenacao));
        assertEquals(sql, r.busca_repLocal_subfed_subrep(tokensConsulta, c, sqlOrdenacao));
    }
        @Test   
    public void testQuerybusca_2repLocal_subfed_subrepAutor() {
        ArrayList<String> tokensConsulta = new ArrayList<String>();
        tokensConsulta.add("educa");
        Consulta c = new Consulta();
        
        Set <Integer> rep = new TreeSet<Integer>();
        rep.add(new Integer(1));
        rep.add(new Integer(2));
        c.setRepositorios(rep);
        
        Set <Integer> fed = new TreeSet<Integer>();
        fed.add(new Integer(1));
        c.setFederacoes(fed);
        
        Set <Integer> repSubfed = new TreeSet<Integer>();
        repSubfed.add(new Integer(1));
        c.setRepSubfed(repSubfed);  
        
        c.setAutor("Liane Tarouco");
        
        String sqlOrdenacao = "') AND a.documento=d.id AND a.nome~@@'Liane Tarouco' GROUP BY d.id ORDER BY SUM (weight) DESC;";

        String sql = "SELECT d.* FROM r1weights r1w, documentos d, repositorios_subfed rsf, autores a WHERE r1w.documento_id=d.id AND ( (d.id_rep_subfed = rsf.id AND ( rsf.id_subfed=1)) OR ( d.id_repositorio=1 OR d.id_repositorio=2 OR rsf.id=1)) AND (r1w.token='educa') AND a.documento=d.id AND a.nome~@@'Liane Tarouco' GROUP BY d.id ORDER BY SUM (weight) DESC;";
        //       System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n\n"+r.busca_repLocal_subfed_subrep(tokensConsulta, id, id, id, sqlOrdenacao));
        assertEquals(sql, r.busca_repLocal_subfed_subrep(tokensConsulta, c, sqlOrdenacao));
    }
}
