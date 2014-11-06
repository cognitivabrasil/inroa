package com.cognitivabrasil.feb.solr;

import com.cognitivabrasil.feb.services.ObaaIndexService;
import com.cognitivabrasil.feb.solr.indexar.IndexarDados;
import com.cognitivabrasil.feb.solr.camposObaa.ObaaDocument;
import com.cognitivabrasil.feb.solr.converter.Converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.common.SolrInputDocument;

@Service
public class ObaaIndexServiceSolrImpl implements ObaaIndexService {

    private static final Logger log = LoggerFactory.getLogger(ObaaIndexServiceSolrImpl.class);
    
    @Autowired
    private IndexarDados indexarDados;
    
    public ObaaIndexServiceSolrImpl() {
    }

    @Override
    public boolean apagarIndice() {
        return indexarDados.apagarIndice();
    }

    @Override
    public void indexarBancoDeDados(List<? extends ObaaDocument> docs) {
        List<SolrInputDocument> docsSolr = new ArrayList<>();

        log.debug("Convertendo obaaXML em SolrXML: Numero de objetos a serem convertidos: " + docs.size());
        for (ObaaDocument doc : docs) {
            String entry = "";
            entry = doc.getObaaEntry();
            if(entry == null || entry.isEmpty()) {
                    log.error("Encontrado documento sem obaaEntry. Id: " + doc.getId());
                    continue;
            }

            //Foi requerido que objetos sem link não fossem indexados ou apresentados ao usuário (FEB-472)
            if (doc.getMetadata().getTechnical().getLocationHttp().isEmpty()
                    || doc.getMetadata().getTechnical().getLocationHttp().values().stream()
                        .allMatch(v -> !v)
                    )
            {
                continue;
            }

            
            docsSolr.add(Converter.obaaToSolr(doc));

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