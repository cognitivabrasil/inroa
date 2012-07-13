/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feb.robo.atualiza.subfedOAI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import cognitivabrasil.obaa.OaiOBAA;
import feb.data.entities.SubFederacao;
import feb.data.interfaces.DocumentosDAO;
import feb.data.interfaces.SubFederacaoDAO;
import feb.metadata.XSLTUtil;

/**
 * Import the OBAA xml file to OBAA object and saves in database. Only for
 * federation.
 * 
 * @author Marcos Nunes <marcosn@gmail.com>
 * @author Paulo Schreiner <paulo@jorjao81.com>
 */
public class Importer {

	SubFederacao subFed;
	File inputXmlFile;
	OaiOBAA oai;
	@Autowired
	private DocumentosDAO docDao;

	Logger log = Logger.getLogger(this.getClass().getName());

	/**
	 * Sets the input file.
	 * 
	 * @param inputFile
	 *            the new input file
	 */
	public void setInputFile(String inputFile) {
		setInputFile(new File(inputFile));
	}

	public void setInputFile(File arquivoXML) {
		inputXmlFile = arquivoXML;

	}

	/**
	 * Sets the federation.
	 * 
	 * @param sf
	 *            the federarion
	 */
	public void setSubFed(SubFederacao sf) {
		this.subFed = sf;
	}

	/**
	 * Updates the repository from the XML input file. This method dont'n save
	 * the federation.
	 * @throws FileNotFoundException 
	 * @throws TransformerException 
	 * @throws TransformerConfigurationException 
	 */
	public void update() throws FileNotFoundException, TransformerConfigurationException, TransformerException {
		assert (inputXmlFile != null);
		
		// if version 2.1, convert XML
		if("2.1".equals(subFed.getVersion())) {
			log.info("Version 2.1, going to convert XML...");
			InputStream xsl = this.getClass().getResourceAsStream("/feb2to3.xsl"); //input xsl
			String transformed = XSLTUtil.transform(new FileInputStream(inputXmlFile), xsl);
			oai = OaiOBAA.fromString(transformed);
		}
		else {
			oai = OaiOBAA.fromFile(inputXmlFile);
		}
		
		docDao.setFederation(subFed);

		for (int i = 0; i < oai.getSize(); i++) {
			try {
				log.debug("Saving object: " + oai.getHeader(i).getIdentifier());

				oai.getHeader(i).setDatestamp(new Date());
				docDao.save(oai.getMetadata(i), oai.getHeader(i));
			} catch (NullPointerException e) {
				log.error("NullPointer ao tentar inserir elemento "
						+ new Integer(i).toString(), e);
			}
		}

		docDao.flush();
		subFed.setDataXMLTemp(oai.getResponseDate());

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
	 * Don't use unless you know what you are doing, this variable will be
	 * autowired by Spring.
	 * 
	 * @param documentDao
	 *            the DocumentDAO to set
	 */
	void setDocDao(DocumentosDAO documentDao) {
		this.docDao = documentDao;
	}

}