package com.cognitivabrasil.feb.robo.main;

import com.cognitivabrasil.feb.data.services.DocumentService;
import com.cognitivabrasil.feb.data.services.SearchService;
import com.cognitivabrasil.feb.ferramentaBusca.indexador.Indexador;
import com.cognitivabrasil.feb.robo.atualiza.Repositorios;
import com.cognitivabrasil.feb.robo.atualiza.SubFederacaoOAI;
import com.cognitivabrasil.feb.util.Operacoes;
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
    private static final Logger log = Logger.getLogger(Robo.class);

    /**
     * Principal m&eacute;todo do rob&ocirc;. Este m&eacute;todo efetua uma
     * consulta na base de dados, procurando por reposit&oacute;rios que
     * est&atilde;o desatualizados, quando encontra algum, chama o m&eacute;todo
     * que atualiza o repositório.
     *
     */
    @Transactional
    public void testaUltimaImportacao() {
        
        log.info(">>>");
        log.info(">>> Iniciando o Robô.");
        log.info(">>>");

        Long inicioRobo = System.currentTimeMillis();
        long initNumberDocs = docDao.getSizeWithDeleted();
        // testa/atualiza SUBFEDERACAO
        boolean subFedAtualizada = subFed.pre_AtualizaSubFedOAI();

        // testa/atualiza REPOSITORIO
        boolean repAtualizado = repositorio.testa_atualizar_repositorio();

        long finalNumberDocs = docDao.getSizeWithDeleted();

        //testa se precisa recalcular o indice.
        if ((subFedAtualizada || repAtualizado) || (finalNumberDocs != initNumberDocs)) {
            indexar.populateR1();
        } else {
            log.info("Não existe atualizaçoes para os repositorios! ");
        }

        log.info("Limpando consultas antigas...");
        searchesDao.cleanup();

        Long finalRobo = System.currentTimeMillis();
        Long tempoTotal = (finalRobo - inicioRobo);
        log.info("Levou " + Operacoes.formatTimeMillis(tempoTotal) + " todo o processo do Robô");
    }
}