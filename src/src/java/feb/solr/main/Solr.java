package feb.solr.main;

import feb.solr.indexar.IndexarDados;
import feb.solr.converter.Converter;
import feb.solr.bd.AcessarBD;
import feb.data.entities.DocumentoReal;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.solr.common.SolrInputDocument;

public class Solr {

    private AcessarBD bd;
    private static final Logger log = Logger.getLogger(Solr.class);

    public Solr() {
        bd = new AcessarBD();
    }

    public static void indexarBancoDeDados(List<DocumentoReal> docs) {
        List<SolrInputDocument> docsSolr = new ArrayList<SolrInputDocument>();
        log.debug("Convertendo obaaXML em SolrXML...");
        for (DocumentoReal doc : docs) {
            try {
                String entry = doc.getMetadata().getGeneral().getIdentifiers().get(0).getEntry();
                if (entry.isEmpty()) {
                    log.error("Encontrado documento sem obaaEntry. Id: " + doc.getId());
                    continue;
                }
            } catch (NullPointerException n) {
                log.error("Encontrado documento sem obaaEntry. Id: " + doc.getId());
                continue;
            } catch (IndexOutOfBoundsException i) {
                log.error("Encontrado documento sem obaaEntry. Id: " + doc.getId());
                continue;
            }
            
            docsSolr.add(Converter.OBAAToSolrInputDocument(doc.getMetadata(), doc.getId()));

        }
        log.debug("Enviando para o Solrs a lista de documento.");
        //Tenta fazer o upload para o Solr. Se não conseguiu, faz upload de um por um
        if (!IndexarDados.indexarColecaoSolrInputDocument(docsSolr)) {
            log.error("Erro ao mandar a lista de documentos para o Solr, sera enviado um a um.");
            for (int d = 0; d < docsSolr.size(); d++) {
                IndexarDados.indexarSolrInputDocument(docsSolr.get(d));
            }
        }
    }
}
