/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitivabrasil.feb.data.services;

import ORG.oclc.oai.server.catalog.OaiDocumentService;
import cognitivabrasil.obaa.OBAA;
import com.cognitivabrasil.feb.data.entities.Document;
import com.cognitivabrasil.feb.data.entities.Repositorio;
import com.cognitivabrasil.feb.data.entities.RepositorioSubFed;
import com.cognitivabrasil.feb.data.entities.SubFederacao;
import com.cognitivabrasil.feb.data.repositories.DocumentRepository;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import metadata.Header;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 * @author Marcos Freitas Nunes <marcos@cognitivabrasil.com.br>
 */
@Service
public class DocumentServiceImpl implements DocumentService, OaiDocumentService {

    @Autowired
    private DocumentRepository docRep;
    
    private static final Logger log = Logger.getLogger(DocumentServiceImpl.class);

    @Override
    public Document get(String e) {
        return docRep.findByObaaEntry(e);
    }

    @Override
    public Document get(int i) {
        return docRep.findById(i);
    }
    
    @Override
    public void delete(Document d) {
        docRep.delete(d);
    }
    
     @Override
    public void save(OBAA obaa, Header h, SubFederacao federation)
            throws IllegalStateException {
        Document doc = new Document();
        log.trace("Going to create documento " + h.getIdentifier());

        log.debug("Armazenando objeto do tipo RepositorioSubFed");
        RepositorioSubFed repSubFed = federation.getRepositoryByName(h.getSetSpec().get(0));
        if (repSubFed == null) {
            throw new IllegalStateException("The repository '"
                    + h.getSetSpec().get(0)
                    + "' doesn't exists in the federation '"
                    + federation.getName() + "'");
        }

        doc.setRepositorioSubFed(repSubFed); 

        save(obaa, h, doc);

    }

    @Override
    public void save(OBAA obaa, Header h, Repositorio r) {
        Document doc = new Document();
        log.trace("Going to create documento " + h.getIdentifier());

        doc.setRepositorio(r);

        save(obaa, h, doc);
    }

    @Override
    public List<Document> getAll() {
        return docRep.findByDeletedIsFalse();
    }

    @Override
    public Page<Document> getlAll(Pageable pageable) {
        return docRep.findByDeletedIsFalse(pageable);
    }

    @Override
    public long getSize() {
        return docRep.countDeletedFalse();
    }
    
    @Override
    public long getSizeWithDeleted() {
        return docRep.count();
    }

    /**
     * Saves (updates) a document.
     *
     * @param obaa OBAA object
     * @param h OAI-PMH header
     * @param doc a newly initialized Document, should have either a
 Repsitory or a RepSubFed set.
     */
    private void save(OBAA obaa, Header h, Document doc) {
        doc.setDeleted(false);
        doc.setObaaEntry(h.getIdentifier());

        Document real = getWithoutId(doc);
        DateTime hTimestamp = new DateTime(h.getTimestamp());
        real.setCreated(hTimestamp);

        try {
            if (h.isDeleted()) {
                real.setDeleted(true);
                real.setMetadata(null);

                docRep.save(real);

            } else {
                real.setMetadata(obaa);
                docRep.save(real);
                //TODO: talvez colocar aqui a indexacao do soler.
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private Document getWithoutId(Document doc) {

        if (!doc.getObaaEntry().isEmpty()) {
            Document d = docRep.findByObaaEntry(doc.getObaaEntry());
            if (d != null) {
                return d;
            }
        }
        return doc;
    }
    
    //oaicat
    @Override
    public Iterator find(Date from, Date until, int oldCount, int maxListSize) {
        PageRequest pr = new PageRequest(oldCount / maxListSize, maxListSize, Sort.Direction.ASC, "created");

        if (from != null && until != null) {
            return docRep.betweenInclusive(new DateTime(from), add1Second(new DateTime(until)), pr).iterator();
        } else if (from != null && until == null) {
            return docRep.from(new DateTime(from), pr).iterator();
        } else if (from == null && until != null) {
            return docRep.until(add1Second(new DateTime(until)), pr).iterator();

        } else {
            return docRep.all(pr).iterator();
        }
    }
    
    @Override
    public int count(Date from, Date until) {
        if (from != null && until != null) {
            return docRep.countBetweenInclusive(new DateTime(from), add1Second(new DateTime(until)));
        } else if (from != null && until == null) {
            return docRep.countFrom(new DateTime(from));
        } else if (from == null && until != null) {
            return docRep.countUntil(add1Second(new DateTime(until)));

        } else {
            return docRep.countInt();
        }

    }
    
    private DateTime add1Second(DateTime dateTime) {
        return dateTime.plusSeconds(1);
    }

    @Override
    public int deleteAllFromRep(Repositorio r) {
        return docRep.deleteByRepositorio(r);
    }

    
}
