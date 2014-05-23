/**
 * o pacote indexador correspoonde as classes que fazem a preparaç&atilde;o dos
 * dados
 *
 */
package com.cognitivabrasil.feb.ferramentaBusca.indexador;

import com.cognitivabrasil.feb.data.entities.DocumentoReal;
import com.cognitivabrasil.feb.data.services.DocumentService;
import com.cognitivabrasil.feb.solr.main.Solr;
import com.cognitivabrasil.feb.util.Operacoes;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * Indexador é a classe que faz os processos de contruç&atilde;o da base de
 * dados para preparaç&atilde;o da posterior recuperaç&atilde;o de informações
 *
 * @author Luiz Rossi <lh.rossi@gmail.com>
 * @author Marcos Nunes <marcosn@gmail.com>
 */
public class Indexador {

    private static final Logger log = Logger.getLogger(Indexador.class);
    @Autowired
    private DocumentService docService;
    @PersistenceContext
    private EntityManager em;

    public Indexador() {
    }

    /**
     * Esse metodo deverá ser executado sempre depois da adiç&atilde;o de todos
     * os documentos na base. Chama o indexador do Solr para atualizar seu
     * indice de busca.
     *
     */
    public void populateR1() {
        log.info("Recalculando o indice do Solr...");

         Solr.apagarIndice();
        Long inicio = System.currentTimeMillis();
        int numMaxDoc = 10000;
        Solr s = new Solr();

/**
        for (int i = 0; i < tamanhoDocDAO; i = i + numMaxDoc) {

            s.indexarBancoDeDados(docDao.getAll(numMaxDoc, i));
            
            // Esse comando abaixo serve para evitar estouro de memoria. 
            //Jorjao vai resolver isso porque eh um erro do hibernate
           docDao.getSession().clear();
*/
        Pageable limit = new PageRequest(0, numMaxDoc);
        Page<DocumentoReal> docs = docService.getlAll(limit);
        s.indexarBancoDeDados(docs.getContent());

        while (docs.hasNextPage()) {
            docs = docService.getlAll(docs.nextPageable());
            s.indexarBancoDeDados(docs.getContent());
            em.clear();
        }

        System.out.println("FIM DA INDEXACAO");
        Long fim = System.currentTimeMillis();
        Long total = fim - inicio;
        log.info("Tempo total para o recalculo do indice: " + Operacoes.formatTimeMillis(total));
    }
    /*
     public void populateR1(int vez) {
     log.info("Recalculando o indice do Solr...");

     //    Solr.apagarIndice();

     Long inicio = System.currentTimeMillis();
     int numMaxDoc = 10000;

     Solr s = new Solr();

     //ELE DA ERRO NO 115.000
     int prim = 0;
     if (vez == 1)
     prim = docDao.getSize()/2;
     int fin = docDao.getSize()/2;
     if (vez == 1)
     fin = docDao.getSize();
     for (int i = prim; i < fin; i = i + numMaxDoc) {

     System.out.println(i);
     s.memoryLeakTest(docDao.getAll(numMaxDoc, i));
     }

     System.out.println("FIM DA INDEACAO");
     Long fim = System.currentTimeMillis();
     Long total = fim - inicio;
     log.info("Tempo total para o recalculo do indice: " + Operacoes.formatTimeMillis(total));
     s = null;
     s = new Solr()
     ;
     try {
     Thread.sleep(10000);
     } catch (InterruptedException ex) {
     java.util.logging.Logger.getLogger(Indexador.class.getName()).log(Level.SEVERE, null, ex);
     }
     System.gc();
     Runtime.getRuntime().gc();
     }*/
}
