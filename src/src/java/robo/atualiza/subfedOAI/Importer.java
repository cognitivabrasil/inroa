/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robo.atualiza.subfedOAI;

import OBAA.OaiOBAA;
import java.io.File;
import modelos.DocumentosDAO;
import modelos.SubFederacao;
import modelos.SubFederacaoDAO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Import the OBAA xml file to OBAA object and saves in database. Only for federation.
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
    @Autowired
    private SubFederacaoDAO subFedDao;
    Logger log = Logger.getLogger(this.getClass().getName());

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
     * Updates the repository from the XML input file.
     */
    public void update() throws Exception{
        assert (inputXmlFile != null);
        oai = OaiOBAA.fromFile(inputXmlFile);

        docDao.setFederation(subFed);

        for (int i = 0; i < oai.getSize(); i++) {
            
            try {
                log.debug("Saving object: " + oai.getHeader(i).getIdentifier());
                docDao.save(oai.getMetadata(i), oai.getHeader(i));
            } catch (NullPointerException e) {
                log.error("NullPointer ao tentar inserir elemento " + new Integer(i).toString(), e);
            }
        }


        // TODO: move the conversion to and from String of the date to OaiOBAA class
        log.debug(oai.getResponseDate());
        
//        try {
//            Date d = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'").parse(oai.getResponseDate());

            
            subFed.setDataXML(oai.getResponseDate());
            subFedDao.save(subFed);
//        } catch (ParseException e) {
//            // TODO Auto-generated catch block
//            System.err.println("Error in parsing date from OaiPMH, probably malformed XML.");
//        }



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
     * Don't use unless you know what you are doing, this variable will be
     * autowired by Spring.
     *
     * @param documentDao the DocumentDAO to set
     */
    void setDocDao(DocumentosDAO documentDao) {
        this.docDao = documentDao;
    }

    void setSubFedDao(SubFederacaoDAO subFedDao) {
        this.subFedDao = subFedDao;
    }
}
