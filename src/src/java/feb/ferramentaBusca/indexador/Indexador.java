/**
 * o pacote indexador correspoonde as classes que fazem a preparaç&atilde;o dos
 * dados
 *
 */
 //Daniel
// Nao quis apagar tudo e colocar esse, entao deixei esse com novo nome. Mas no futuro, só esse arquivo vai ser necessário...
package feb.ferramentaBusca.indexador;

import feb.data.entities.DocumentoReal;
import feb.data.interfaces.DocumentosDAO;
import feb.solr.main.Solr;
import feb.util.Operacoes;
import java.util.List;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

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
    private DocumentosDAO docDao;

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
        int numeroParticoes = docDao.getSize() / numMaxDoc + 1;
        
    //    System.out.println("numer de particoes " + numeroParticoes + ". Totoal de doc " + docDao.getSize());

        Solr s = new Solr();

        //ELE DA ERRO NO 115.000
        for (int i = 0; i < docDao.getSize(); i = i + numMaxDoc) {
       //     System.out.println(i);
            s.indexarBancoDeDados(docDao.getAll(numMaxDoc, i));
        }

        System.out.println("FIM DA INDEACAO");
        Long fim = System.currentTimeMillis();
        Long total = fim - inicio;
        log.info("Tempo total para o recalculo do indice: " + Operacoes.formatTimeMillis(total));
    }
}
