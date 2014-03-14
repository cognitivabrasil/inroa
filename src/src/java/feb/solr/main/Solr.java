package feb.solr.main;

import feb.solr.indexar.IndexarDados;
import feb.solr.converter.Converter;
import org.apache.log4j.Logger;

import feb.data.entities.DocumentoReal;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.ArrayList;
import java.util.List;
import org.apache.solr.common.SolrInputDocument;

public class Solr {

    private static final Logger log = Logger.getLogger(Solr.class);
    private static final int maxDocs = 10000; // Numero maximo de documentos que seram indexados upload para o Solr

    public Solr() {
    }

    /**
     * Apaga todo o indice do Solr e todos os documentos contindo nele
     *
     * @return True se deu certo. False se houve alguma falha.
     */
    public static boolean apagarIndice() {
        return IndexarDados.apagarIndice();

    }

    /**
     * Recebe uma lista de documentos reais, converte eles para DocumentSolr e
     * envia eles para o sistema indexar Converte um a um os documentos reais
     * ate atingir maxDocs documentos. Apos, envia eles para o Sorl
     *
     * @param docs Lista de documentos reais a serem indexados
     */
    public static void indexarBancoDeDados(List<DocumentoReal> docs) {
        List<SolrInputDocument> docsSolr = new ArrayList<SolrInputDocument>();
        int numDocs = 0;
        log.debug("Convertendo obaaXML em SolrXML...");
        log.debug("Numero de objetos a serem convertidos: " + docs.size());
        for (DocumentoReal doc : docs) {
            numDocs++;
            String entry = "";
            try {
                entry = doc.getObaaEntry();
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

            int repositorio = doc.getRepositorio() != null ? doc.getRepositorio().getId() : -1;
            int subFeb = doc.getRepositorioSubFed() != null ? doc.getRepositorioSubFed().getId() : -1;
            int federacao = doc.getRepositorioSubFed() != null
                    ? doc.getRepositorioSubFed().getSubFederacao().getId() : -1;

            docsSolr.add(Converter.OBAAToSolrInputDocument(doc.getMetadata(), entry, doc.getId(), repositorio, subFeb, federacao));

            if (numDocs == maxDocs) {
                log.debug("Enviando para o Solrs a lista de documento. (Numero de documentos: " + docsSolr.size() + ")");
                //Tenta fazer o upload para o Solr. Se não conseguiu, faz upload de um por um
                try {
                    if (!IndexarDados.indexarColecaoSolrInputDocument(docsSolr)) {
                        log.error("Erro ao mandar a lista de documentos para o Solr, sera enviado um a um.");
                        for (int d = 0; d < docsSolr.size(); d++) {
                            IndexarDados.indexarSolrInputDocument(docsSolr.get(d));
                        }
                    }

                } catch (OutOfMemoryError e) {
                    log.error("Out of memory enquanto enviava os documentos para o SOLR!" + e);
                    List<SolrInputDocument> listaInicial = docsSolr.subList(0, docsSolr.size() / 2 - 1);
                    List<SolrInputDocument> listaFinal = docsSolr.subList(docsSolr.size() / 2 - 1, docsSolr.size());
    MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();

                        MemoryUsage heapUsage = memoryBean.getHeapMemoryUsage();
            long maxMemory = heapUsage.getMax() / (1024*1024);
            long usedMemory = heapUsage.getUsed() / (1024*1024);
            System.out.println( " : Memory Use :" + usedMemory + "M/" + maxMemory + "M");
       
            
                    if (!IndexarDados.indexarColecaoSolrInputDocument(listaInicial)) {
                        log.error("Erro ao mandar a lista de documentos para o Solr, sera enviado um a um.");
                        for (int d = 0; d < docsSolr.size(); d++) {
                            IndexarDados.indexarSolrInputDocument(docsSolr.get(d));
                        }
                    }
                    if (!IndexarDados.indexarColecaoSolrInputDocument(listaFinal)) {
                        log.error("Erro ao mandar a lista de documentos para o Solr, sera enviado um a um.");
                        for (int d = 0; d < docsSolr.size(); d++) {
                            IndexarDados.indexarSolrInputDocument(docsSolr.get(d));
                        }
                    }
                }
                numDocs = 0;
                docsSolr = new ArrayList<SolrInputDocument>();
            }
        }

    }
}
