/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Recuperador;

import ferramentaBusca.Recuperador;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import postgres.Conectar;

import static org.junit.Assert.assertEquals;
import org.springframework.test.context.TestExecutionListeners;
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
     *  Teste para verificar a String SQL sem consulta por autor
     */    
    
    @Test
    public void deleteMe() {
    }
//    @Test
//    public void testQueryWithoutAuthor() {
//
//        String sqlOrdenacao = "') GROUP BY r1w.tid ORDER BY SUM(weight) DESC;";
//        ArrayList<String> tokensConsulta = new ArrayList<String>();
//        
//        tokensConsulta.add("educa");
//                
//        assertEquals("SELECT tid FROM r1weights r1w, documentos d WHERE r1w.tid=d.id  AND (r1w.token='educa') GROUP BY r1w.tid ORDER BY SUM(weight) DESC;", r.buscaConfederacao(tokensConsulta, sqlOrdenacao, false));
//        
//       //Para testes posteriores com integração com o banco de dados 
//        //      ArrayList <Integer> resultCarlosHeitor = new ArrayList<Integer>();
//        
//  //      Collections.addAll(resultEduca, 21745, 9942, 18835, 5781, 10317, 23313, 16769, 18708, 12491, 11458);
//  //      Collections.addAll(resultCarlosHeitor, 301871, 301703, 301872, 320257, 307257, 312284, 326509, 306905, 322122, 322182, 322132, 322163, 322156, 322144, 322038, 322062, 322103, 322104, 322121, 322183, 318642, 301867, 309403, 301839, 320866, 301863, 301865, 301869, 301866, 301873, 301870, 316650, 314198, 313849);        
//        
////    Conectar conecta = new Conectar();
////    Connection conn = conecta.conectaBD();
//    
//   
//  //        String sqlOrdenacao = "') AND a.documento=d.id AND a.nome~@@'Carlos Heitor' GROUP BY r1w.tid ORDER BY SUM (weight) DESC;";
////          ArrayList<String> tokensConsulta = new ArrayList<String>();
////          tokensConsulta.add("educa");
////            
////          assertEquals(sqlLilia, r.buscaConfederacao(tokensConsulta, sqlOrdenacao, true));
////          
//          //ArrayList <Integer> resultEduca = new ArrayList<Integer>();
// //       resultEduca.equals(r.busca("educa", conn, null, null, null, "Relevancia"));
////        assertEquals(r.busca("educa", conn, null, null, null, "Relevancia"), resultEduca);
////        assertEquals(r.busca(null, "Carlos Heitor",conn, null, null, null, "Relevancia"), resultEduca);
//       // assertEquals(conecta, conn);
//
//    }
//    /**
//     *  Teste para verificar a String SQL sem consulta com autor sem query
//     */   
//    @Test
//    public void testQueryWithAuthorWithoutQuery(){
//        ArrayList<String> tokensConsulta = new ArrayList<String>();
//        String sqlAutorSemQuery = "a.documento=d.id AND a.nome~@@'Carlos Heitor' GROUP BY d.id, a.nome ORDER BY (qgram(a.nome, 'Carlos Heitor')) DESC;";        
//        
//        assertEquals("SELECT d.id FROM documentos d, autor a WHERE a.documento=d.id AND a.nome~@@'Carlos Heitor' GROUP BY d.id, a.nome ORDER BY (qgram(a.nome, 'Carlos Heitor')) DESC;", r.buscaConfederacao(tokensConsulta, sqlAutorSemQuery, true));
//    }
//    /**
//     *  Teste para verificar a String SQL com consulta por autor e com uma query
//     */   
//    @Test
//    public void testQueryWithAuthorWithQuery(){
//        ArrayList<String> tokensConsulta = new ArrayList<String>();
//        String sqlAutorComQuery = "') AND a.documento=d.id AND a.nome~@@'Lília Ferreira Lobo' GROUP BY r1w.tid ORDER BY SUM (weight) DESC;";
//        tokensConsulta.add("educa");
//
//        assertEquals("SELECT tid FROM r1weights r1w, documentos d, autor a WHERE r1w.tid=d.id  AND (r1w.token='educa') AND a.documento=d.id AND a.nome~@@'Lília Ferreira Lobo' GROUP BY r1w.tid ORDER BY SUM (weight) DESC;", r.buscaConfederacao(tokensConsulta, sqlAutorComQuery, true));
//        
//    }
}
