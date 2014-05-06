package feb.robo.main;

import com.cognitivabrasil.feb.data.services.DocumentService;
import com.cognitivabrasil.feb.data.services.SearchService;
import feb.ferramentaBusca.indexador.Indexador;
import feb.robo.atualiza.Repositorios;
import feb.robo.atualiza.SubFederacaoOAI;
import feb.util.Operacoes;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Ferramenta de Sincronismo (Robô)
 *
 * @author Marcos Nunes <marcosn@gmail.com>
 */
public class Robo {

    @Autowired
    private Indexador indexar;
    @Autowired
    private Repositorios repositorio;
    @Autowired
    private SubFederacaoOAI subFed;
    @Autowired
    private SearchService searchesDao;
    @Autowired
    private DocumentService docDao;

    /**
     * Principal m&eacute;todo do rob&ocirc;. Este m&eacute;todo efetua uma
     * consulta na base de dados, procurando por reposit&oacute;rios que
     * est&atilde;o desatualizados, quando encontra algum, chama o m&eacute;todo
     * que atualiza o repositório.
     *
     */
    @Transactional
    public void testaUltimaImportacao() {
        Logger log = Logger.getLogger(Robo.class.getName());

        log.info(">>>");
        log.info(">>> Iniciando o Robo.");
        log.info(">>>");

        Long inicioRobo = System.currentTimeMillis();
        long initNumberDocs = docDao.getSizeWithDeleted();
//TESTA/ATUALIZA SUBFEDERACAO
        boolean subFedAtualizada = subFed.pre_AtualizaSubFedOAI(indexar);

//TESTA REPOSITORIO
        boolean repAtualizado = repositorio.testa_atualizar_repositorio(indexar);

        long finalNumberDocs = docDao.getSizeWithDeleted();

//TESTA SE PRECISA RECALCULAR O INDICE

        if ((subFedAtualizada || repAtualizado) || (finalNumberDocs != initNumberDocs)) {
            indexar.populateR1();
        } else {
            log.info("NAO existe atualizaçoes para os repositorios! ");
        }

        log.info("Limpando visitas antigas...");
        searchesDao.cleanup();

        Long finalRobo = System.currentTimeMillis();
        Long tempoTotal = (finalRobo - inicioRobo);
        log.info("Levou " + Operacoes.formatTimeMillis(tempoTotal) + " todo o processo do Robo");
    }
}