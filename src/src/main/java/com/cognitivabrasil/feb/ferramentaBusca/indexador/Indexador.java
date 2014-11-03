/**
 * o pacote indexador correspoonde as classes que fazem a preparaç&atilde;o dos dados
 *
 */
package com.cognitivabrasil.feb.ferramentaBusca.indexador;

import com.cognitivabrasil.feb.data.entities.Document;
import com.cognitivabrasil.feb.data.services.DocumentService;
import com.cognitivabrasil.feb.services.ObaaIndexService;
import com.cognitivabrasil.feb.solr.ObaaIndexServiceSolrImpl;
import com.cognitivabrasil.feb.util.Operacoes;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Indexador é a classe que faz os processos de contruç&atilde;o da base de dados para preparaç&atilde;o da posterior
 * recuperaç&atilde;o de informações
 *
 * @author Luiz Rossi <lh.rossi@gmail.com>
 * @author Marcos Nunes <marcosn@gmail.com>
 */
@Service
public class Indexador {

    private static final Logger log = LoggerFactory.getLogger(Indexador.class);
    @Autowired
    private DocumentService docService;

    @Autowired
    private ObaaIndexService indexService;
    
    @PersistenceContext
    private EntityManager em;

    public Indexador() {
    }

    /**
     * Esse metodo deverá ser executado sempre depois da adiç&atilde;o de todos os documentos na base. Chama o indexador
     * do Solr para atualizar seu indice de busca.
     *
     */
    public void populateR1() {
        log.info("Recalculando o indice do Solr...");

        indexService.apagarIndice();
        Long inicio = System.currentTimeMillis();
        int numMaxDoc = 10000;

        Pageable limit = new PageRequest(0, numMaxDoc);
        Page<Document> docs = docService.getlAll(limit);
        indexService.indexarBancoDeDados(docs.getContent());

        while (docs.hasNext()) {
            docs = docService.getlAll(docs.nextPageable());
            indexService.indexarBancoDeDados(docs.getContent());
            em.clear();
        }

        log.info("FIM DA INDEXACAO");
        Long fim = System.currentTimeMillis();
        Long total = fim - inicio;
        log.info("Tempo total para o recalculo do indice: " + Operacoes.formatTimeMillis(total));
    }
}
