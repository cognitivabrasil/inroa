/**
 * o pacote indexador correspoonde as classes que fazem a preparaç&atilde;o dos
 * dados
 *
 */
 //Daniel
// Nao quis apagar tudo e colocar esse, entao deixei esse com novo nome. Mas no futuro, só esse arquivo vai ser necessário...
package feb.ferramentaBusca.indexador;

import cognitivabrasil.obaa.OBAA;
import feb.data.daos.DocumentosHibernateDAO;
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
        int tamanhoDocDAO = docDao.getSize();
        Solr s = new Solr();

        for (int i = 0; i < tamanhoDocDAO; i = i + numMaxDoc) {

            System.out.println(i);
            s.indexarBancoDeDados(docDao.getAll(numMaxDoc, i));
            
            // Esse comando abaixo serve para evitar estouro de memoria. 
            //Jorjao vai resolver isso porque eh um erro do hibernate
           docDao.getSession().clear();
        }

        System.out.println("FIM DA INDEACAO");
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
