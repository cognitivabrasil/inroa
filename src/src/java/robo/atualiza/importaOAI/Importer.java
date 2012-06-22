package robo.atualiza.importaOAI;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import metadata.XsltConversor;
import modelos.DocumentosDAO;
import modelos.Repositorio;
import modelos.RepositoryDAO;
import org.springframework.beans.factory.annotation.Autowired;

import cognitivabrasil.obaa.OaiOBAA;

// TODO: Auto-generated Javadoc
public class Importer {
	XsltConversor conversor;
	Repositorio rep;
	File inputXmlFile;
	OaiOBAA oai;
	
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
	 */
	public void update() {
		assert(inputXmlFile != null);
		oai = OaiOBAA.fromString(conversor.toObaaFromFile(inputXmlFile));
		
		docDao.setRepository(rep);
		for(int i = 0; i < oai.getSize(); i++) {
			System.out.print("Trying to get: ");
			System.out.println(i);
			
//			try {
//				oai.getMetadata(i).getGeneral().getIdentifier().getEntry();
//			}
//			catch(NullPointerException e) {
//				System.err.println("Elemento " + new Integer(i).toString()
//						+ " gera nullPointer ao tentar pegar o OBAAEntry,"
//						+ "provavelmente erro no repositório remoto ou no mapeamento");
//				continue;
//			}
			
			try {
				
				docDao.save(oai.getMetadata(i), oai.getHeader(i));
			}
			catch(NullPointerException e) {
				System.err.println("NullPointer ao tentar inserir elemento " + new Integer(i).toString()
						+ "");
			}
		}
		
		
		// TODO: move the conversion to and from String of the date to OaiOBAA class
		System.out.println(oai.getResponseDate());
		Date d;
		try {
			d = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'").parse(oai.getResponseDate());
			
			System.out.println("Date: " + d.toString());
			rep.setDataOrigem(d);
			repDao.save(rep);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.err.println("Error in parsing date from OaiPMH, probably malformed XML.");
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
	void setDocDao(DocumentosDAO documentDao) {
		this.docDao = documentDao;
	}
	
	void setRepDao(RepositoryDAO repositoryDao) {
		this.repDao = repositoryDao;
	}

}
