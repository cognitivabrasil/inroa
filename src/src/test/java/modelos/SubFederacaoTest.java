/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 *
 * @author paulo
 */
public class SubFederacaoTest {

    public SubFederacaoTest() {
    }

    @Test
    public void testToString() {
        System.out.println("toString");
        SubFederacao s = new SubFederacao();
        s.setNome("paulo");

        assertThat(s.toString(), containsString("paulo"));
    }

    @Test
    public void testGetProximaAtualizacao() {
        SubFederacao s = new SubFederacao();
        s.setUltimaAtualizacao(new Date());
        assertThat(s.getProximaAtualizacao(), is(notNullValue()));
    }

    @Test
    public void testGetSizeDoc() {
        RepositorioSubFed r1 = mock(RepositorioSubFed.class);
        RepositorioSubFed r2 = mock(RepositorioSubFed.class);


        when(r1.getSize()).thenReturn(3);
        when(r2.getSize()).thenReturn(4);

        Set<RepositorioSubFed> sr = new HashSet<RepositorioSubFed>();
        sr.add(r1);
        sr.add(r2);


        SubFederacao instance = new SubFederacao();

        instance.setRepositorios(sr);
        int result = instance.getSizeDoc();
        assertEquals(7, result);
    }
}
