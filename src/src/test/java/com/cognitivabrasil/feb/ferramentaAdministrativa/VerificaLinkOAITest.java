package com.cognitivabrasil.feb.ferramentaAdministrativa;

import static org.junit.Assert.*;

import org.junit.Test;

import com.cognitivabrasil.feb.ferramentaAdministrativa.validarOAI.VerificaLinkOAI;
import org.junit.Ignore;

public class VerificaLinkOAITest {

    @Ignore("Testes não devem depender de rede e disponibilidade de servidores de terceiros")
    @Test
    public void testVerificaLinkOAIPMH() {
        String link = "http://ares.unasus.gov.br/oai/request";
        assertTrue(VerificaLinkOAI.verificaLinkOAIPMH(link));
    }

}
