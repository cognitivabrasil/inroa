package com.cognitivabrasil.feb.solr.main;

import cognitivabrasil.obaa.OBAA;
import com.cognitivabrasil.feb.data.entities.DocumentoReal;
import com.cognitivabrasil.feb.solr.indexar.IndexarDados;
import com.cognitivabrasil.feb.solr.converter.Converter;
import org.apache.log4j.Logger;
import java.util.ArrayList;
import java.util.List;
import org.apache.solr.common.SolrInputDocument;

public class Solr {

    private static final Logger log = Logger.getLogger(Solr.class);
    private static final int maxDocs = 9999; // Numero maximo de documentos que seram indexados upload para o Solr
    private Converter convert;
    private IndexarDados st;

    public Solr() {
        convert = new Converter();
    }

    /**
     * Apaga todo o indice do Solr e todos os documentos contindo nele
     *
     * @return True se deu certo. False se houve alguma falha.
     */
    public static boolean apagarIndice() {
        return IndexarDados.apagarIndice();

    }

    public void memoryLeakTest(List<DocumentoReal> docs) {
        for (DocumentoReal doc : docs) {

            OBAA o = doc.getMetadata();
            if (o.getGeneral() == null) {
                System.out.println("dfillsfdis");
            }
        }

    }

    /**
     * Recebe uma lista de documentos reais, converte eles para DocumentSolr e
     * envia eles para o sistema indexar Converte um a um os documentos reais
     * ate atingir maxDocs documentos. Apos, envia eles para o Sorl
     *
     * @param docs Lista de documentos reais a serem indexados
     */
    public void indexarBancoDeDados(List<DocumentoReal> docs) {
        List<SolrInputDocument> docsSolr = new ArrayList<SolrInputDocument>();
        
        log.debug("Convertendo obaaXML em SolrXML: Numero de objetos a serem convertidos: " + docs.size());
        for (DocumentoReal doc : docs) {
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

            String nomeRep = doc.getNomeRep();

            docsSolr.add(convert.OBAAToSolrInputDocument(doc.getMetadata(), entry, doc.getId(), repositorio, subFeb, federacao, nomeRep));

        }

        log.debug("Enviando para o Solrs a lista de documento. (Numero de documentos: " + docsSolr.size() + ")");

        //Tenta fazer o upload para o Solr. Se não conseguiu, faz upload de um por um
        if (!IndexarDados.indexarColecaoSolrInputDocument(docsSolr)) {
            log.error("Erro ao mandar a lista de documentos para o Solr, sera enviado um a um.");
            for (int d = 0; d < docsSolr.size(); d++) {
                IndexarDados.indexarSolrInputDocument(docsSolr.get(d));
            }
        }

    }
}
