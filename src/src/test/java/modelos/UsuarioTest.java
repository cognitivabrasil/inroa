/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author paulo
 */
public class UsuarioTest {
    
    public UsuarioTest() {
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
     * Test of getId method, of class Usuario.
     */
  
    @Test
    public void testSetPassword() {
        System.out.println("setPassword");
        String password = "teste";
        Usuario instance = new Usuario();
        instance.setPassword(password);
        assertEquals("698dc19d489c4e4db73e28a713eab07b", instance.getPasswordMd5());
    }

    /**
     * Test of authenticate method, of class Usuario.
     */
    @Test
    public void testAuthenticate() {
        System.out.println("authenticate");
        Usuario u = new Usuario();
        u.setPasswordMd5("698dc19d489c4e4db73e28a713eab07b");
        assertEquals(true, u.authenticate("teste"));
        assertEquals(false, u.authenticate("Teste"));
    }
}
