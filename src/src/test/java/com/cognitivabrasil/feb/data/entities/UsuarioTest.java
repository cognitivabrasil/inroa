package com.cognitivabrasil.feb.data.entities;

import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class UsuarioTest {

    
    @Test
    public void traduzNomeDosRolesDaVersao2ParaA3() {
        Usuario u = new Usuario();
        
        u.setPermissionsInternal("PERM_BLA,PERM_BLIBLOBLU");
        
        assertThat(u.getPermissions(), hasItems("ROLE_BLA", "ROLE_BLIBLOBLU"));
    }
}
