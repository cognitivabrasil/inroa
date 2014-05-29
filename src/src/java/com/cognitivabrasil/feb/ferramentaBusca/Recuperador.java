package com.cognitivabrasil.feb.ferramentaBusca;

import com.cognitivabrasil.feb.data.entities.Consulta;
import com.cognitivabrasil.feb.data.entities.Document;
import com.cognitivabrasil.feb.data.services.DocumentService;
import com.cognitivabrasil.feb.solr.query.QuerySolr;
import java.sql.SQLException;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Recuperador pelo SOLR
 *
 * @author Daniel Epstein
 */
public class Recuperador {

    static Logger log = Logger.getLogger(Recuperador.class.getName());
    private static int rssSizeLimit = 100;
    @Autowired
    private DocumentService docDao;

    public Recuperador() {
    }

    /**
     * Envia para o Solr uma consulta
     *
     * @param consulta Consulta efetuada (obvio....)
     * @return Lista de documentos reais que correspondem ao resultado da busca
     */
    public List<Document> busca(Consulta consulta) {

        List<Document> resultadoConsulta;

        if (consulta.isRss()) {

            QuerySolr q = new QuerySolr();
            log.debug(consulta.getConsulta());
            q.pesquisaSimples(consulta.getConsulta(), consulta.getOffset(), rssSizeLimit);
            consulta.setSizeResult(q.getNumDocs());
            resultadoConsulta = q.getDocumentosReais(consulta.getOffset(), rssSizeLimit);
            log.debug("Numero de resultados a serem apresentados: " + resultadoConsulta.size());
        } else {

            QuerySolr q = new QuerySolr();
            log.debug(consulta.getConsulta());
            q.pesquisaSimples(consulta.getConsulta(), consulta.getOffset(), consulta.getLimit());
            consulta.setSizeResult(q.getNumDocs());
            resultadoConsulta = q.getDocumentosReais(consulta.getOffset(), consulta.getLimit());
            log.debug("Numero de resultados a serem apresentados: " + resultadoConsulta.size());

        }
        return resultadoConsulta;

    }

    /**
     * Busca avancada que sera enviada para o SOLR
     *
     * @param consulta Consulta realizada
     * @return Lista de documentos reais que correspondem ao resultado da busca
     */
    public List<Document> buscaAvancada(Consulta consulta) {

        List<Document> resultadoConsulta;

        if (consulta.isRss()) {

            QuerySolr q = new QuerySolr();
            log.debug(consulta.getConsulta());
            q.pesquisaCompleta(consulta, consulta.getOffset(), rssSizeLimit);
            consulta.setSizeResult(q.getNumDocs());
            resultadoConsulta = q.getDocumentosReais(consulta.getOffset(), rssSizeLimit);
            log.debug("Numero de resultados a serem apresentados: " + resultadoConsulta.size());
        } else {
            QuerySolr q = new QuerySolr();
            log.debug(consulta.getConsulta());
            q.pesquisaCompleta(consulta, consulta.getOffset(), consulta.getLimit());
            consulta.setSizeResult(q.getNumDocs());
            resultadoConsulta = q.getDocumentosReais(consulta.getOffset(), consulta.getLimit());
            log.debug("Numero de resultados a serem apresentados: " + resultadoConsulta.size());
        }
        return resultadoConsulta;

    }

}
