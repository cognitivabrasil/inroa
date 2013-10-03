package feb.ferramentaAdministrativa;

import static org.junit.Assert.*;

import org.junit.Test;

import feb.ferramentaAdministrativa.validarOAI.VerificaLinkOAI;

public class VerificaLinkOAITest {

	@Test
	public void testVerificaLinkOAIPMH() {
		String link = "http://ares.unasus.gov.br/oai/request";
		assertTrue(VerificaLinkOAI.verificaLinkOAIPMH(link));
	}

}
