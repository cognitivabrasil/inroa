/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitivabrasil.feb.functionaltests;

import cognitivabrasil.obaa.OBAA;
import cognitivabrasil.obaa.OaiOBAA;
import com.cognitivabrasil.feb.AppConfig;
import com.cognitivabrasil.feb.data.entities.Mapeamento;
import com.cognitivabrasil.feb.data.entities.Repositorio;
import com.cognitivabrasil.feb.data.services.DocumentService;
import com.cognitivabrasil.feb.data.services.MappingService;
import com.cognitivabrasil.feb.data.services.MetadataRecordService;
import com.cognitivabrasil.feb.data.services.RepositoryService;
import com.cognitivabrasil.feb.metadata.XSLTUtil;
import com.cognitivabrasil.feb.robo.atualiza.importaOAI.ImporterRep;
import java.io.File;
import java.io.IOException;
import javax.xml.transform.TransformerException;
import org.apache.commons.io.FileUtils;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 *
 * @author preto
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class })
@ActiveProfiles("test")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@DirtiesContext
public class UabXmlIT extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    DocumentService docDao;
    @Autowired
    RepositoryService repDao;
    @Autowired
    MetadataRecordService padDao;
    @Autowired
    MappingService mapDao;
    
    @Autowired
    private ImporterRep imp;

    @Test
    public void importUabCapes() throws IOException, TransformerException {
        String inputXmlFile = "src/test/resources/metadata/FEB-capes___dspace10.xml";
        String inputXsltFile = "src/xslt/uab.xsl"; // input xsl
        
        OaiOBAA oas;
        
        String s = XSLTUtil.transformFilename(inputXmlFile, inputXsltFile);
        oas = OaiOBAA.fromString(s);
        
        OBAA l = oas.getMetadata(0);
        assert (l.getGeneral() != null);
        assertThat(l.getGeneral().getTitles(), hasItems("História da Educação Brasileira I"));
        
    }
}