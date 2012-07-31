package feb.robo.atualiza.importaOAI;

import cognitivabrasil.obaa.OaiOBAA;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import metadata.XsltConversor;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import feb.data.entities.Repositorio;
import feb.data.interfaces.DocumentosDAO;
import feb.data.interfaces.RepositoryDAO;

// TODO: Auto-generated Javadoc
public class Importer {
	Logger logger = Logger.getLogger(Importer.class);
	
	private XsltConversor conversor;
	private Repositorio rep;
	private File inputXmlFile;
	private OaiOBAA oai;
	
	@Autowired
	private DocumentosDAO docDao;
	
	@Autowired
	private RepositoryDAO repDao;

	/**
	 * Sets the input file.
	 *
	 * @param inputFile the new input file
	 */
	public void setInputFile(String inputFile) {
		setInputFile(new File(inputFile));	
	}
	
	public void setInputFile(File arquivoXML) {
		inputXmlFile = arquivoXML;
		
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
	 * 
	 * @return number of updated objects
	 */
	public int update() {
		assert(inputXmlFile != null);
		oai = OaiOBAA.fromString(conversor.toObaaFromFile(inputXmlFile));
		
		Session session = getSession();
		
		for(int i = 0; i < oai.getSize(); i++) {
			logger.debug("Trying to get: " + i);
			

			try {
				logger.debug(oai.getHeader(i));
				oai.getHeader(i).setDatestamp(new Date()); // set date to current date (instead of the
									// repository date
				
				docDao.save(oai.getMetadata(i), oai.getHeader(i), rep);
			}
			catch(NullPointerException e) {
				logger.error("NullPointer ao tentar inserir elemento " + i, e);
			}
			
			if(i % 20 == 0) {
				session.flush();
				session.clear();
			}
			
		}
		
		
		// TODO: move the conversion to and from String of the date to OaiOBAA class
		Date d;
		try {
			d = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'").parse(oai.getResponseDate());
			rep.setDataOrigemTemp(d);
			repDao.save(rep);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			logger.error("Error in parsing date from OaiPMH, probably malformed XML.");
		}

		
		
		docDao.flush();
		
		return oai.getSize();

	}
	
	private Session getSession() {
		return docDao.getSession();
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
	public void setDocDao(DocumentosDAO documentDao) {
		this.docDao = documentDao;
	}
	
	public void setRepDao(RepositoryDAO repositoryDao) {
		this.repDao = repositoryDao;
	}

}
