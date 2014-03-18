package feb.ferramentaBusca;

import feb.data.entities.Consulta;
import feb.data.entities.DocumentoReal;
import feb.data.interfaces.DocumentosDAO;
import feb.solr.query.QuerySolr;
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
    private DocumentosDAO docDao;

    public Recuperador() {
    }

    /**
     * Envia para o Solr uma consulta
     *
     * @param consulta Consulta efetuada (obvio....)
     * @return Lista de documentos reais que correspondem ao resultado da busca
     */
    public List<DocumentoReal> busca(Consulta consulta) {

        List<DocumentoReal> resultadoConsulta;

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
    public List<DocumentoReal> buscaAvancada(Consulta consulta) {

        QuerySolr q = new QuerySolr();
        log.debug(consulta.getConsulta());
        q.pesquisaCompleta(consulta, consulta.getLimit(), consulta.getOffset());
        List<DocumentoReal> resultadoConsulta = q.getDocumentosReais(consulta.getOffset(), consulta.getLimit());
        log.debug("Numero de resultados a serem apresentados: " + resultadoConsulta.size());
        return resultadoConsulta;

    }

}
