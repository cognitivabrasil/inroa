package com.cognitivabrasil.feb.solr.indexar;

import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;

import cognitivabrasil.obaa.OBAA;

import com.cognitivabrasil.feb.solr.converter.Converter;
import com.cognitivabrasil.feb.spring.FebConfig;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrException;

@Service
public class IndexarDados {

    private static final Logger log = LoggerFactory.getLogger(IndexarDados.class);
    
    @Autowired
    private FebConfig config;

    /**
     * Apaga todo o indice do Solr
     *
     * @return True se o indice foi apagado
     */
    public boolean apagarIndice() {
        SolrServer server = new HttpSolrServer(config.getSolrUrl());
        try {
            UpdateResponse response = server.deleteByQuery("*:*");
            if (response.getStatus() == 400) {
                // Add exception para travar o programa
                log.error("A base de dados nao pode ser apagada (nao sei como esse erro poderia acontecer)");
                return false;
            }
            server.commit();
            return true;
        } catch (SolrServerException | IOException e) {
            log.error("Nao foi possivel se conectar ao servidor solr", e);
        }
        return true;

    }

    /**
     * Converte Strings passados para formato SOLR e envia eles para indexacao
     *
     * @param objeto Lista de Lista de Strings. Os String sao do formato
     * <Field:Metadados>
     * @return True se todos os objetos foram indexados
     */
    public boolean indexarObjeto(List<List<String>> objeto) {
        SolrInputDocument doc = Converter.listToSolrInputDocument(objeto);
        return indexarSolrInputDocument(doc);
    }

    /**
     * Indexa um objeto OBAA no SOLR
     *
     * @param objeto Objeto a ser indexado
     * @return True se o objeto foi indexado com sucesso
     */
    public boolean indexarOBAA(OBAA objeto) {
        return indexarObjeto(Converter.OBAAToList(objeto));
    }

    /**
     * Indexa uma colecao de objetos ja no formato SOLR
     *
     * @param docs Colecao de documentos no formato SOLR
     * @return True se todos os objetos foram indexados.
     */
    public boolean indexarColecaoSolrInputDocument(Collection<SolrInputDocument> docs) {
        SolrServer server = new HttpSolrServer(config.getSolrUrl());
        try {
            UpdateResponse response = server.add(docs);

            if (response.getStatus() == 400) {
                log.error("Não foi possivel indexar os documentos.");
                return false;
            }
            server.commit();

            return true;
        } catch (SolrServerException | IOException e) {
            log.error("Nao foi possivel se conectar ao servidor solr ou o documento não está configurado corretamente", e);
            throw new SolrException(SolrException.ErrorCode.SERVICE_UNAVAILABLE,
                    "Nao foi possivel se conectar ao servidor solr ou o documento não está configurado corretamente");
        }
    }

    /**
     * Indexa um unico documentado SOLR
     *
     * @param docs Documento ja no formato SOLR a ser indexado
     * @return True se o documento foi indexado
     */
    public boolean indexarSolrInputDocument(SolrInputDocument docs) {
        Set<SolrInputDocument> col = new HashSet<>();
        col.add(docs);
        return indexarColecaoSolrInputDocument(col);
    }

    public boolean indexarColecaoSolrInputDocument2(Collection<SolrInputDocument> docs) {
        SolrServer server = new HttpSolrServer(config.getSolrUrl());
        try {
            server.add(docs);

            server.commit();

            return true;
        } catch (SolrServerException | IOException e) {
            log.error("Nao foi possivel se conectar ao servidor solr ou o documento não está configurado corretamente", e);
            throw new SolrException(SolrException.ErrorCode.SERVICE_UNAVAILABLE,
                    "Nao foi possivel se conectar ao servidor solr ou o documento não está configurado corretamente");
        }
    }
}
