package feb.robo.main;

import feb.data.interfaces.SearchesDao;
import feb.ferramentaBusca.indexador.Indexador;
import feb.robo.atualiza.Repositorios;
import feb.robo.atualiza.SubFederacaoOAI;

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

    @Autowired
    Indexador indexar;
    
    @Autowired private SearchesDao searchesDao;

    /**
     * Principal m&eacute;todo do rob&ocirc;. Este m&eacute;todo efetua uma
     * consulta na base de dados, procurando por reposit&oacute;rios que
     * est&atilde;o desatualizados, quando encontra algum, chama o m&eacute;todo
     * que atualiza o repositório.
     *
     * @author Marcos Nunes
     */
    @Transactional
    public void testaUltimaImportacao() {
        Logger log = Logger.getLogger(Robo.class.getName());
        SimpleDateFormat dataFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        log.info(">>>");
        log.info(">>> FEB: iniciando o Robo. " + dataFormat.format(new Date()));
        log.info(">>>");

        Long inicioRobo = System.currentTimeMillis();


        boolean repAtualizado = false;
        boolean subFedAtualizada = false;

//TESTA/ATUALIZA SUBFEDERACAO
        SubFederacaoOAI subFed = new SubFederacaoOAI();
        subFedAtualizada = subFed.pre_AtualizaSubFedOAI(indexar);

//TESTA REPOSITORIO
        Repositorios repositorio = new Repositorios();
        repAtualizado = repositorio.testa_atualizar_repositorio(indexar);


//TESTA SE PRECISA RECALCULA O INDICE

//           if ((subFedAtualizada || repAtualizado) && nDocumentos!=selectNumeroDocumentos(con)) {
        if (subFedAtualizada || repAtualizado) {
            indexar.populateR1();
        } else {
            log.info("FEB: NAO existe atualizaçoes para os repositorios! " + dataFormat.format(new Date()));
        }
        
        log.info("FEB: limpando visitas antigas...");
        searchesDao.cleanup();
        
        Long finalRobo = System.currentTimeMillis();
        Long tempoTotal = (finalRobo - inicioRobo) / 1000;
        log.debug("Levou " + tempoTotal + " segundos todo o processo do Robo");
    }
}
