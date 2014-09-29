package com.cognitivabrasil.feb.robo.main;

import com.cognitivabrasil.feb.data.services.SearchService;
import com.cognitivabrasil.feb.exceptions.FederationException;
import com.cognitivabrasil.feb.ferramentaBusca.indexador.Indexador;
import com.cognitivabrasil.feb.robo.atualiza.Repositorios;
import com.cognitivabrasil.feb.robo.atualiza.SubFederacaoOAI;
import com.cognitivabrasil.feb.util.Operacoes;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Ferramenta de Sincronismo (Robô)
 *
 * @author Marcos Nunes <marcosn@gmail.com>
 */
@Service
@EnableScheduling
public class Robo {

    @Autowired
    private Indexador indexar;
    @Autowired
    private Repositorios repositorio;
    @Autowired
    private SubFederacaoOAI subFed;
    @Autowired
    private SearchService searchesDao;

    private static final Logger log = Logger.getLogger(Robo.class);

    /**
     * Principal m&eacute;todo do rob&ocirc;. Este m&eacute;todo efetua uma consulta na base de dados, procurando por
     * reposit&oacute;rios que est&atilde;o desatualizados, quando encontra algum, chama o m&eacute;todo que atualiza o
     * repositório.
     *
     */
    @Scheduled(cron = "0 0 2 * * *")
    @Transactional
    public void run() {

        log.info(">>>");
        log.info(">>> Iniciando o Robô.");
        log.info(">>>");

        Long inicioRobo = System.currentTimeMillis();

        // atualiza SUBFEDERACAO
        try{
        subFed.atualizaFederacoes(false);
        }catch(FederationException f){
            log.error("Falha na atualização das federações.",f);
        }

        // atualiza REPOSITORIO
        repositorio.atualizaRepositorios();

        indexar.populateR1();

        log.info("Limpando consultas antigas...");
        searchesDao.cleanup();

        Long finalRobo = System.currentTimeMillis();
        Long tempoTotal = (finalRobo - inicioRobo);
        log.info("Levou " + Operacoes.formatTimeMillis(tempoTotal) + " todo o processo do Robô");
    }
}