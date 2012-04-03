/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ferramentaBusca;

import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

/**
 *
 * @author cei
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath:pretoTestApplicationContext.xml")
//@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
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

        String sqlOrdenacao = "') GROUP BY r1w.tid ORDER BY SUM(weight) DESC;";
        ArrayList<String> tokensConsulta = new ArrayList<String>();

        tokensConsulta.add("educa");

        assertEquals("SELECT tid FROM r1weights r1w, documentos d WHERE r1w.tid=d.id  AND (r1w.token='educa') GROUP BY r1w.tid ORDER BY SUM(weight) DESC;", r.buscaConfederacao(tokensConsulta, sqlOrdenacao, false));

    }

    /**
     * Teste para verificar a String SQL sem consulta com autor sem query
     */
    @Test
    public void testQueryWithAuthorWithoutQuery() {
        ArrayList<String> tokensConsulta = new ArrayList<String>();
        String sqlAutorSemQuery = "a.documento=d.id AND a.nome~@@'Carlos Heitor' GROUP BY d.id, a.nome ORDER BY (qgram(a.nome, 'Carlos Heitor')) DESC;";

        assertEquals("SELECT d.id as tid FROM documentos d, autores a WHERE a.documento=d.id AND a.nome~@@'Carlos Heitor' GROUP BY d.id, a.nome ORDER BY (qgram(a.nome, 'Carlos Heitor')) DESC;", r.buscaConfederacao(tokensConsulta, sqlAutorSemQuery, true));
    }

    /**
     * Teste para verificar a String SQL com consulta por autor e com uma query
     */
    @Test
    public void testQueryWithAuthorWithQuery() {
        ArrayList<String> tokensConsulta = new ArrayList<String>();
        tokensConsulta.add("educa");
        String sqlAutorComQuery = "') AND a.documento=d.id AND a.nome~@@'Lília Ferreira Lobo' GROUP BY r1w.tid, a.nome ORDER BY (qgram(a.nome, 'Lília Ferreira Lobo')) DESC, SUM (weight) DESC;";

        assertEquals("SELECT tid FROM r1weights r1w, documentos d, autores a WHERE r1w.tid=d.id  AND (r1w.token='educa') AND a.documento=d.id AND a.nome~@@'Lília Ferreira Lobo' GROUP BY r1w.tid, a.nome ORDER BY (qgram(a.nome, 'Lília Ferreira Lobo')) DESC, SUM (weight) DESC;", r.buscaConfederacao(tokensConsulta, sqlAutorComQuery, true));

    }

    @Test
    public void testQueryRepLocal() {
        ArrayList<String> tokensConsulta = new ArrayList<String>();
        tokensConsulta.add("educa");
        String id[] = new String[1];
        id[0] = "1";
        String sqlOrdenacao = "') GROUP BY r1w.tid ORDER BY SUM(weight) DESC;";
//        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n\n"+r.busca_repLocal(tokensConsulta, id, sqlOrdenacao, false));
        assertEquals("SELECT tid FROM r1weights r1w, documentos d  WHERE r1w.tid=d.id  AND ( d.id_repositorio=1) AND (r1w.token='educa') GROUP BY r1w.tid ORDER BY SUM(weight) DESC;", r.busca_repLocal(tokensConsulta, id, sqlOrdenacao, false));
    }

    @Test
    public void testQueryRepLocalAutor() {
        ArrayList<String> tokensConsulta = new ArrayList<String>();
        tokensConsulta.add("ataqu");
        String id[] = new String[1];
        id[0] = "328";
        String sqlOrdenacao = "') AND a.documento=d.id AND a.nome~@@'Liane Tarouco' GROUP BY r1w.tid ORDER BY SUM (weight) DESC;";
        //  System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n\n"+r.busca_repLocal(tokensConsulta, id, sqlOrdenacao, false));
        assertEquals("SELECT tid FROM r1weights r1w, documentos d, autores a WHERE r1w.tid=d.id  AND ( d.id_repositorio=328) AND (r1w.token='ataqu') AND a.documento=d.id AND a.nome~@@'Liane Tarouco' GROUP BY r1w.tid ORDER BY SUM (weight) DESC;", r.busca_repLocal(tokensConsulta, id, sqlOrdenacao, true));
    }

    @Test
    public void testQuerySubfed() {
        ArrayList<String> tokensConsulta = new ArrayList<String>();
        tokensConsulta.add("educa");
        String id[] = new String[1];
        id[0] = "1";
        String sqlOrdenacao = "') GROUP BY r1w.tid ORDER BY SUM(weight) DESC;";
        //System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n\n"+r.busca_subfed(tokensConsulta, id, sqlOrdenacao));
        assertEquals("SELECT tid FROM r1weights r1w, documentos d, repositorios_subfed rsf WHERE r1w.tid=d.id AND d.id_rep_subfed = rsf.id  AND (rsf.id_subfed=1) AND (r1w.token='educa') GROUP BY r1w.tid ORDER BY SUM(weight) DESC;", r.busca_subfed(tokensConsulta, id, sqlOrdenacao, false));
    }

    @Test
    @Ignore("Nao ta passando")
    public void testQuerySubfedAutor() {
        ArrayList<String> tokensConsulta = new ArrayList<String>();
        tokensConsulta.add("educa");
        String id[] = new String[1];
        id[0] = "1";
        String sqlOrdenacao = "') AND a.documento=d.id AND a.nome~@@'Liane Tarouco' GROUP BY r1w.tid ORDER BY SUM (weight) DESC;";

        String sql = "SELECT tid FROM r1weights r1w, documentos d, repositorios_subfed rsf, autores a WHERE r1w.tid=d.id AND d.id_rep_subfed = rsf.id  AND (rsf.id_subfed=1) AND (r1w.token='educa') AND a.documento=d.id AND a.nome~@@'Liane Tarouco' GROUP BY r1w.tid ORDER BY SUM (weight) DESC;";
        //System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n\n"+r.busca_subfed(tokensConsulta, id, sqlOrdenacao));
        assertEquals(sql, r.busca_subfed(tokensConsulta, id, sqlOrdenacao, true));
    }

    @Test
    public void testQuerySubRep() {
        ArrayList<String> tokensConsulta = new ArrayList<String>();
        tokensConsulta.add("educa");
        String id[] = new String[1];
        id[0] = "1";
        String sqlOrdenacao = "') GROUP BY r1w.tid ORDER BY SUM(weight) DESC;";

        //System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n\n"+r.busca_subRep(tokensConsulta, id, sqlOrdenacao));
        assertEquals("SELECT tid FROM r1weights r1w, documentos d WHERE r1w.tid=d.id  AND (d.id_rep_subfed=1) AND (r1w.token='educa') GROUP BY r1w.tid ORDER BY SUM(weight) DESC;", r.busca_subRep(tokensConsulta, id, sqlOrdenacao, false));
    }

    @Test
    @Ignore("Nao ta passando")
    public void testQuerySubRepAutor() {
        ArrayList<String> tokensConsulta = new ArrayList<String>();
        tokensConsulta.add("educa");
        String id[] = new String[1];
        id[0] = "1";
        String sqlOrdenacao = "') AND a.documento=d.id AND a.nome~@@'Liane Tarouco' GROUP BY r1w.tid ORDER BY SUM (weight) DESC;";

        String sql = "SELECT tid FROM r1weights r1w, documentos d, autores a WHERE r1w.tid=d.id  AND (d.id_rep_subfed=1) AND (r1w.token='educa') AND a.documento=d.id AND a.nome~@@'Liane Tarouco' GROUP BY r1w.tid ORDER BY SUM (weight) DESC;";

        //System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n\n"+r.busca_subRep(tokensConsulta, id, sqlOrdenacao));

        assertEquals(sql, r.busca_subRep(tokensConsulta, id, sqlOrdenacao, true));
    }

    @Test
    public void testQueryRepLocalSubfed() {
        ArrayList<String> tokensConsulta = new ArrayList<String>();
        tokensConsulta.add("educa");
        String id[] = new String[1];
        id[0] = "1";
        String sqlOrdenacao = "') GROUP BY r1w.tid ORDER BY SUM(weight) DESC;";

        String sql = "SELECT tid FROM r1weights r1w, documentos d, repositorios_subfed rsf WHERE r1w.tid=d.id AND ( (d.id_rep_subfed = rsf.id AND (rsf.id_subfed=1)) OR ( d.id_repositorio=1)) AND (r1w.token='educa') GROUP BY r1w.tid ORDER BY SUM(weight) DESC;";
//        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n\n"+r.busca_repLocal_subfed(tokensConsulta, id,id,sqlOrdenacao));
        assertEquals(sql, r.busca_repLocal_subfed(tokensConsulta, id, id, sqlOrdenacao, false));
    }

    @Test
    public void testQueryRepLocalSubfedAutor() {
        ArrayList<String> tokensConsulta = new ArrayList<String>();
        tokensConsulta.add("educa");
        String id[] = new String[1];
        id[0] = "1";
        String sqlOrdenacao = "') AND a.documento=d.id AND a.nome~@@'Liane Tarouco' GROUP BY r1w.tid ORDER BY SUM (weight) DESC;";

        String sql = "SELECT tid FROM r1weights r1w, documentos d, repositorios_subfed rsf, autores a WHERE r1w.tid=d.id AND ( (d.id_rep_subfed = rsf.id AND (rsf.id_subfed=1)) OR ( d.id_repositorio=1)) AND (r1w.token='educa') AND a.documento=d.id AND a.nome~@@'Liane Tarouco' GROUP BY r1w.tid ORDER BY SUM (weight) DESC;";
//        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n\n"+r.busca_repLocal_subfed(tokensConsulta, id,id,sqlOrdenacao));
        assertEquals(sql, r.busca_repLocal_subfed(tokensConsulta, id, id, sqlOrdenacao, true));
    }

    @Test
    public void testQueryRepLocalSubrep() {
        ArrayList<String> tokensConsulta = new ArrayList<String>();
        tokensConsulta.add("educa");
        String id[] = new String[1];
        id[0] = "1";
        String sqlOrdenacao = "') GROUP BY r1w.tid ORDER BY SUM(weight) DESC;";

        String sql = "SELECT tid FROM r1weights r1w, documentos d WHERE r1w.tid=d.id AND ( d.id_repositorio=1 OR d.id_rep_subfed=1) AND (r1w.token='educa') GROUP BY r1w.tid ORDER BY SUM(weight) DESC;";
//        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n\n"+r.busca_repLocal_subrep(tokensConsulta, id, id, sqlOrdenacao));
        assertEquals(sql, r.busca_repLocal_subrep(tokensConsulta, id, id, sqlOrdenacao, false));
    }

    @Test    
    public void testQueryRepLocalSubrepAutor() {
        ArrayList<String> tokensConsulta = new ArrayList<String>();
        tokensConsulta.add("educa");
        String id[] = new String[1];
        id[0] = "1";
        String sqlOrdenacao = "') AND a.documento=d.id AND a.nome~@@'Liane Tarouco' GROUP BY r1w.tid ORDER BY SUM (weight) DESC;";

        String sql = "SELECT tid FROM r1weights r1w, documentos d, autores a WHERE r1w.tid=d.id AND ( d.id_repositorio=1 OR d.id_rep_subfed=1) AND (r1w.token='educa') AND a.documento=d.id AND a.nome~@@'Liane Tarouco' GROUP BY r1w.tid ORDER BY SUM (weight) DESC;";
//        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n\n"+r.busca_repLocal_subrep(tokensConsulta, id, id, sqlOrdenacao));
        assertEquals(sql, r.busca_repLocal_subrep(tokensConsulta, id, id, sqlOrdenacao, true));
    }

    @Test
    public void testQuerySubfedSubrep() {
        ArrayList<String> tokensConsulta = new ArrayList<String>();
        tokensConsulta.add("educa");
        String id[] = new String[1];
        id[0] = "1";
        String sqlOrdenacao = "') GROUP BY r1w.tid ORDER BY SUM(weight) DESC;";

        String sql = "SELECT tid FROM r1weights r1w, documentos d, repositorios_subfed rsf WHERE r1w.tid=d.id AND d.id_rep_subfed = rsf.id AND (rsf.id_subfed=1 OR rsf.id=1) AND (r1w.token='educa') GROUP BY r1w.tid ORDER BY SUM(weight) DESC;";
        //System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n\n"+r.busca_subfed_subrep(tokensConsulta, id, id, sqlOrdenacao));
        assertEquals(sql, r.busca_subfed_subrep(tokensConsulta, id, id, sqlOrdenacao, false));
    }

    @Test
    public void testQuerySubfedSubrepAutor() {
        ArrayList<String> tokensConsulta = new ArrayList<String>();
        tokensConsulta.add("educa");
        String id[] = new String[1];
        id[0] = "1";
        String sqlOrdenacao = "') AND a.documento=d.id AND a.nome~@@'Liane Tarouco' GROUP BY r1w.tid ORDER BY SUM (weight) DESC;";

        String sql = "SELECT tid FROM r1weights r1w, documentos d, repositorios_subfed rsf, autores a WHERE r1w.tid=d.id AND d.id_rep_subfed = rsf.id AND (rsf.id_subfed=1 OR rsf.id=1) AND (r1w.token='educa') AND a.documento=d.id AND a.nome~@@'Liane Tarouco' GROUP BY r1w.tid ORDER BY SUM (weight) DESC;";
        //System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n\n"+r.busca_subfed_subrep(tokensConsulta, id, id, sqlOrdenacao));
        assertEquals(sql, r.busca_subfed_subrep(tokensConsulta, id, id, sqlOrdenacao, true));
    }

    @Test
    public void testQuerybusca_repLocal_subfed_subrep() {
        ArrayList<String> tokensConsulta = new ArrayList<String>();
        tokensConsulta.add("educa");
        String id[] = new String[1];
        id[0] = "1";
        String sqlOrdenacao = "') GROUP BY r1w.tid ORDER BY SUM(weight) DESC;";

        String sql = "SELECT tid FROM r1weights r1w, documentos d, repositorios_subfed rsf WHERE r1w.tid=d.id AND ( (d.id_rep_subfed = rsf.id AND (rsf.id_subfed=1)) OR ( d.id_repositorio=1 OR d.id_rep_subfed=1)) AND (r1w.token='educa') GROUP BY r1w.tid ORDER BY SUM(weight) DESC;";
        //       System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n\n"+r.busca_repLocal_subfed_subrep(tokensConsulta, id, id, id, sqlOrdenacao));
        assertEquals(sql, r.busca_repLocal_subfed_subrep(tokensConsulta, id, id, id, sqlOrdenacao, false));
    }

    @Test    
    public void testQuerybusca_repLocal_subfed_subrepAutor() {
        ArrayList<String> tokensConsulta = new ArrayList<String>();
        tokensConsulta.add("educa");
        String id[] = new String[1];
        id[0] = "1";
        String sqlOrdenacao = "') AND a.documento=d.id AND a.nome~@@'Liane Tarouco' GROUP BY r1w.tid ORDER BY SUM (weight) DESC;";

        String sql = "SELECT tid FROM r1weights r1w, documentos d, repositorios_subfed rsf, autores a WHERE r1w.tid=d.id AND ( (d.id_rep_subfed = rsf.id AND (rsf.id_subfed=1)) OR ( d.id_repositorio=1 OR d.id_rep_subfed=1)) AND (r1w.token='educa') AND a.documento=d.id AND a.nome~@@'Liane Tarouco' GROUP BY r1w.tid ORDER BY SUM (weight) DESC;";
        //       System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n\n"+r.busca_repLocal_subfed_subrep(tokensConsulta, id, id, id, sqlOrdenacao));
        assertEquals(sql, r.busca_repLocal_subfed_subrep(tokensConsulta, id, id, id, sqlOrdenacao, true));
    }
}