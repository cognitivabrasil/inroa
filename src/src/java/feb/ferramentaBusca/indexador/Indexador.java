/**
 * o pacote indexador correspoonde as classes que fazem a preparaç&atilde;o dos
 * dados
 *
 */
 //Daniel
// Nao quis apagar tudo e colocar esse, entao deixei esse com novo nome. Mas no futuro, só esse arquivo vai ser necessário...
package feb.ferramentaBusca.indexador;

import com.cognitivabrasil.feb.data.services.DocumentService;
import com.cognitivabrasil.feb.data.entities.DocumentoReal;
import feb.solr.main.Solr;
import feb.util.Operacoes;
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

    static Logger log = Logger.getLogger(Indexador.class);
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

        //ELE DA ERRO NO 115.000
        Pageable limit = new PageRequest(0, numMaxDoc);
        Page<DocumentoReal> docs = docService.getlAll(limit);
        s.memoryLeakTest(docs);

        while (docs.hasNextPage()) {
            docs = docService.getlAll(docs.nextPageable());
            s.memoryLeakTest(docs);
            em.clear();
        }

        System.out.println("FIM DA INDEXACAO");
        Long fim = System.currentTimeMillis();
        Long total = fim - inicio;
        log.info("Tempo total para o recalculo do indice: " + Operacoes.formatTimeMillis(total));
    }
}
