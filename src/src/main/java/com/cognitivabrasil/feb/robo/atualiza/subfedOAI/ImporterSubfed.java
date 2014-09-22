/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitivabrasil.feb.robo.atualiza.subfedOAI;

import cognitivabrasil.obaa.OBAA;
import cognitivabrasil.obaa.OaiOBAA;

import com.cognitivabrasil.feb.data.entities.SubFederacao;
import com.cognitivabrasil.feb.data.services.DocumentService;
import com.cognitivabrasil.feb.metadata.XSLTUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import metadata.Header;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Import the OBAA xml file to OBAA object and saves in database. Only for
 * federation.
 *
 * @author Marcos Nunes <marcosn@gmail.com>
 * @author Paulo Schreiner <paulo@jorjao81.com>
 */
@Component
@Scope("prototype")
public class ImporterSubfed {

    private SubFederacao subFed;
    private File inputXmlFile;
    private OaiOBAA oai;
    @Autowired
    private DocumentService docDao;
    
    @PersistenceContext
    private EntityManager em;
    
    private final Logger log = Logger.getLogger(ImporterSubfed.class);

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
     * Sets the federation.
     *
     * @param sf the federarion
     */
    public void setSubFed(SubFederacao sf) {
        this.subFed = sf;
    }

    /**
     * Updates the repository from the XML input file. This method dont'n save
     * the federation.
     *
     * @throws FileNotFoundException
     * @throws TransformerException
     * @throws TransformerConfigurationException
     */
    @Transactional
    public void update() throws FileNotFoundException,
            TransformerConfigurationException, TransformerException {
        assert (inputXmlFile != null);

        // if version 2.1, convert XML
        if ("2.1".equals(subFed.getVersion())) {
            log.info("Version 2.1, going to convert XML...");
            InputStream xsl = this.getClass().getResourceAsStream(
                    "/feb2to3.xsl"); // input xsl
            String transformed = XSLTUtil.transform(new FileInputStream(
                    inputXmlFile), xsl);
            oai = OaiOBAA.fromString(transformed);
        } else {
            oai = OaiOBAA.fromFile(inputXmlFile);
        }

        for (int i = 0; i < oai.getSize(); i++) {
            Header header = oai.getHeader(i);
            OBAA obaa = oai.getMetadata(i);
            log.debug("Saving object: " + header.getIdentifier());

            header.setDatestamp(new Date());
            if ((!header.isDeleted()) && obaa == null) {
                log.error("Não foi possível carregar metadados do objeto " + i
                        + ", provavelmente o XML está mal formado");
            } else {
                docDao.save(obaa, header, subFed);
            }

            if (i % 10 == 0) {
                em.flush();
                em.clear();
            }
        }
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
     * @param documentDao the DocumentDAO to set
     */
    public void setDocDao(DocumentService documentDao) {
        this.docDao = documentDao;
    }
}
