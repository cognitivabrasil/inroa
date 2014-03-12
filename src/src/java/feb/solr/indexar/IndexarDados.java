package feb.solr.indexar;

import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;

import cognitivabrasil.obaa.OBAA;
import feb.solr.converter.Converter;
import java.io.IOException;
import java.util.Collection;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrServerException;

public class IndexarDados {

    private static final Logger log = Logger.getLogger(IndexarDados.class);

    public static boolean apagarIndice() {
        SolrServer server = new HttpSolrServer("http://localhost:8983/solr/");
        try {
            UpdateResponse response = server.deleteByQuery("*:*");
            if (response.getStatus() == 400) {
                return false;
            }
            server.commit();
            return true;
        } catch (SolrServerException e) {
            log.error("Nao foi possivel se conectar ao servidor solr", e);
        } catch (IOException e) {
            log.error("Nao foi possivel se conectar ao servidor solr", e);
        }
        return true;

    }

    public static boolean indexarObjeto(List<List<String>> objeto) {
        SolrInputDocument doc = Converter.listToSolrInputDocument(objeto);
        SolrServer server = new HttpSolrServer("http://localhost:8983/solr/");
        try {
            UpdateResponse response = server.add(doc);
            if (response.getStatus() == 400) {
                return false;
            }
            server.commit();
            return true;
        } catch (SolrServerException e) {
            log.error("Nao foi possivel se conectar ao servidor solr", e);
        } catch (IOException e) {
            log.error("Nao foi possivel se conectar ao servidor solr", e);
        }

        return false;
    }

    public static boolean indexarOBAA(OBAA objeto) {
        return indexarObjeto(Converter.OBAAToList(objeto));
    }

    public static boolean indexarColecaoSolrInputDocument(Collection<SolrInputDocument> docs) {
        SolrServer server = new HttpSolrServer("http://localhost:8983/solr/");
        try {
            UpdateResponse response = server.add(docs);

            if (response.getStatus() == 400) {
                log.error("Não foi possivel indexar os documentos.");
                return false;
            }
            server.commit();
            return true;
        } catch (SolrServerException e) {
            log.error("Nao foi possivel se conectar ao servidor solr ou o documento não está configurado corretamente", e);
        } catch (IOException e) {
            log.error("Nao foi possivel se conectar ao servidor solr ou o documento não está configurado corretamente", e);
        }

        return false;
    }

    public static boolean indexarSolrInputDocument(SolrInputDocument docs) {
        SolrServer server = new HttpSolrServer("http://localhost:8983/solr/");
        try {
            UpdateResponse response = server.add(docs);

            if (response.getStatus() == 400) {
                return false;
            }
            server.commit();
            return true;
        } catch (Exception e) {
            //     System.out.println(e+"\n Nao foi possivel se conectar ao servidor solr");
        }

        return false;
    }
}
