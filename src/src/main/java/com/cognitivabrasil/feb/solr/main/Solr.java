package com.cognitivabrasil.feb.solr.main;

import com.cognitivabrasil.feb.data.entities.Document;
import com.cognitivabrasil.feb.solr.indexar.IndexarDados;
import com.cognitivabrasil.feb.solr.converter.Converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.common.SolrInputDocument;

@Service
public class Solr {

    private static final Logger log = LoggerFactory.getLogger(Solr.class);
    
    @Autowired
    private IndexarDados indexarDados;
    
    public Solr() {
    }

    /**
     * Apaga todo o indice do Solr e todos os documentos contindo nele
     *
     * @return True se deu certo. False se houve alguma falha.
     */
    public boolean apagarIndice() {
        return indexarDados.apagarIndice();
    }

    /**
     * Recebe uma lista de documentos reais, converte eles para DocumentSolr e envia eles para o sistema indexar
     * Converte um a um os documentos reais ate atingir maxDocs documentos. Apos, envia eles para o Sorl
     *
     * @param docs Lista de documentos reais a serem indexados
     */
    public void indexarBancoDeDados(List<Document> docs) {
        List<SolrInputDocument> docsSolr = new ArrayList<>();

        log.debug("Convertendo obaaXML em SolrXML: Numero de objetos a serem convertidos: " + docs.size());
        for (Document doc : docs) {
            String entry = "";
            try {
                entry = doc.getObaaEntry();
                if (entry.isEmpty()) {
                    log.error("Encontrado documento sem obaaEntry. Id: " + doc.getId());
                    continue;
                }
            } catch (NullPointerException | IndexOutOfBoundsException n) {
                log.error("Encontrado documento sem obaaEntry. Id: " + doc.getId());
                continue;
            }

            int repositorio = doc.getRepositorio() != null ? doc.getRepositorio().getId() : -1;
            int subFeb = doc.getRepositorioSubFed() != null ? doc.getRepositorioSubFed().getId() : -1;
            int federacao = doc.getRepositorioSubFed() != null
                    ? doc.getRepositorioSubFed().getSubFederacao().getId() : -1;

            String nomeRep = doc.getNomeRep();

            //Foi requerido que objetos sem link não fossem indexados ou apresentados ao usuário (FEB-472)
            if (doc.getMetadata().getTechnical().getLocationHttp().isEmpty()) {
                continue;
            }

            docsSolr.add(Converter.obaaToSolr(
                    doc.getMetadata(), entry, doc.getId(), repositorio, subFeb, federacao, nomeRep));

        }

        log.debug("Enviando para o Solr a lista de documento. (Número de documentos: " + docsSolr.size() + ")");

        //Tenta fazer o upload para o Solr. Se não conseguiu, faz upload de um por um
        if (!indexarDados.indexarColecaoSolrInputDocument(docsSolr)) {
            log.error("Erro ao mandar a lista de documentos para o Solr, será enviado um a um.");
            for (int d = 0; d < docsSolr.size(); d++) {
                indexarDados.indexarSolrInputDocument(docsSolr.get(d));
            }
        }
    }
}