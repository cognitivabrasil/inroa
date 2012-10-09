package feb.robo.main;

import feb.data.interfaces.DocumentosDAO;
import feb.data.interfaces.SearchesDao;
import feb.ferramentaBusca.indexador.Indexador;
import feb.robo.atualiza.Repositorios;
import feb.robo.atualiza.SubFederacaoOAI;
import feb.util.Operacoes;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Ferramenta de Sincronismo (Robô)
 *
 * @author Marcos Nunes <marcosn@gmail.com>
 */
public class Robo {

    @Autowired private Indexador indexar;
    
    @Autowired private Repositorios repositorio;
    @Autowired private SubFederacaoOAI subFed;
    
    @Autowired private SearchesDao searchesDao;
    @Autowired private DocumentosDAO docDao;

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
        log.info(">>> FEB: iniciando o Robo.");
        log.info(">>>");

        Long inicioRobo = System.currentTimeMillis();        
        Integer initNumberDocs = docDao.getSizeWithDeleted();
//TESTA/ATUALIZA SUBFEDERACAO
        boolean subFedAtualizada = subFed.pre_AtualizaSubFedOAI(indexar);

//TESTA REPOSITORIO
        boolean repAtualizado = repositorio.testa_atualizar_repositorio(indexar);

        Integer finalNumberDocs = docDao.getSizeWithDeleted();

//TESTA SE PRECISA RECALCULAR O INDICE

        if ((subFedAtualizada || repAtualizado) || (finalNumberDocs != initNumberDocs)) {
            indexar.populateR1();
        } else {
            log.info("FEB: NAO existe atualizaçoes para os repositorios! ");
        }
        
        log.info("FEB: limpando visitas antigas...");
        searchesDao.cleanup();
        
        Long finalRobo = System.currentTimeMillis();
        Long tempoTotal = (finalRobo - inicioRobo);
        log.info("Levou " + Operacoes.formatTimeMillis(tempoTotal) + " todo o processo do Robo");
    }
}
