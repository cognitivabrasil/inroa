package robo.atualiza.importaOAI;

import org.springframework.beans.factory.annotation.Autowired;

import OBAA.OaiOBAA;
import metadata.XsltConversor;
import modelos.DocumentosDAO;
import modelos.Repositorio;

// TODO: Auto-generated Javadoc
public class Importer {
	XsltConversor conversor;
	Repositorio rep;
	String inputXmlFile;
	OaiOBAA oai;
	
	@Autowired
	private DocumentosDAO docDao;

	/**
	 * Sets the input file.
	 *
	 * @param inputFile the new input file
	 */
	public void setInputFile(String inputFile) {
		this.inputXmlFile = inputFile; // input xml	
	}

	/**
	 * Sets the repositorio.
	 *
	 * @param r the new repositorio
	 */
	public void setRepositorio(Repositorio r) {
		rep = r;		
		conversor = new XsltConversor(r.getMapeamento().getXslt());
	}

	/**
	 * Updates the repository from the XML input file.
	 */
	public void update() {
		assert(inputXmlFile != null);
		oai = OaiOBAA.fromString(conversor.toObaaFromFile(inputXmlFile));
		
		for(int i = 0; i < oai.getSize(); i++) {
			docDao.save(oai.getMetadata(i), oai.getHeader(i));
		}
		
		docDao.flush();
	}
	
	/**
	 * Gets the oai obaa.
	 * 
	 * IMPORTANT! This is an internal method, to help unit testing.
	 *
	 * @return the oai obaa
	 */
	protected OaiOBAA getOaiObaa() {
		return oai;
	}


	/**
	 * Don't use unless you know what you are doing, this variable will
	 * be autowired by Spring.
	 * 
	 * @param documentDao the DocumentDAO to set
	 */
	protected void setDocDao(DocumentosDAO documentDao) {
		this.docDao = documentDao;
	}



}
