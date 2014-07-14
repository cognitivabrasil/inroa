package com.cognitivabrasil.feb.robo.atualiza.importaOAI;

import cognitivabrasil.obaa.OBAA;
import cognitivabrasil.obaa.OaiOBAA;
import com.cognitivabrasil.feb.data.entities.Repositorio;
import com.cognitivabrasil.feb.data.services.DocumentService;
import java.io.File;
import java.util.Date;
import metadata.Header;
import metadata.XsltConversor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

// TODO: Auto-generated Javadoc
public class Importer {

    Logger logger = Logger.getLogger(Importer.class);
    private XsltConversor conversor;
    private Repositorio rep;
    private File inputXmlFile;
    private OaiOBAA oai;
    @Autowired
    private DocumentService docDao;

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
        assert (inputXmlFile != null);
        oai = OaiOBAA.fromString(conversor.toObaaFromFile(inputXmlFile));

        for (int i = 0; i < oai.getSize(); i++) {
            logger.debug("Trying to get: " + i);

            try {
                Header header = oai.getHeader(i);
                logger.debug(header);
                // set date to current date (instead of the repository date
                header.setDatestamp(new Date()); 
                OBAA metadata = oai.getMetadata(i);

                if ((!header.isDeleted()) && metadata == null) {
                    logger.error("Não foi possível carregar metadados do objeto " + i + ", provavelmente o XML está mal formado");
                } else {
                    docDao.save(metadata, header, rep);
                }
            } catch (NullPointerException e) {
                logger.error("NullPointer ao tentar inserir elemento " + i, e);
            }

        }

        // TODO: move the conversion to and from String of the date to OaiOBAA class

        rep.setDataOrigemTemp(oai.getResponseDate());

        return oai.getSize();

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
