package feb.solr.indexar;

import cognitivabrasil.obaa.Educational.TypicalAgeRange;
import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;

import cognitivabrasil.obaa.OBAA;
import feb.solr.converter.Converter;
import java.io.IOException;
import java.util.Collection;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrServerException;

public class IndexarDados {

    private static final String solrURL = "http://localhost:8983/solr/";
    private static final Logger log = Logger.getLogger(IndexarDados.class);

    private SolrServer serverteste;

    /**
     * Apaga todo o indice do Solr
     *
     * @return True se o indice foi apagado
     */
    public static boolean apagarIndice() {
        SolrServer server = new HttpSolrServer(solrURL);
        try {
            UpdateResponse response = server.deleteByQuery("*:*");
            if (response.getStatus() == 400) {
                log.error("A base de dados nao pode ser apagada (nao sei como esse erro poderia acontecer)");
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

    /**
     * Converte Strings passados para formato SOLR e envia eles para indexacao
     *
     * @param objeto Lista de Lista de Strings. Os String sao do formato
     * <Field:Metadados>
     * @return True se todos os objetos foram indexados
     */
    public static boolean indexarObjeto(List<List<String>> objeto) {
        SolrInputDocument doc = Converter.listToSolrInputDocument(objeto);
        SolrServer server = new HttpSolrServer(solrURL);
        try {
            UpdateResponse response = server.add(doc);
            if (response.getStatus() == 400) {
                log.error("Erro ao indexar os objetos (possivelmente falta de 'entry')");
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

    /**
     * Indexa um objeto OBAA no SOLR
     *
     * @param objeto Objeto a ser indexado
     * @return True se o objeto foi indexado com sucesso
     */
    public static boolean indexarOBAA(OBAA objeto) {
        return indexarObjeto(Converter.OBAAToList(objeto));
    }

    /**
     * Indexa uma colecao de objetos ja no formato SOLR
     *
     * @param docs Colecao de documentos no formato SOLR
     * @return True se todos os objetos foram indexados.
     */
    public static boolean indexarColecaoSolrInputDocument(Collection<SolrInputDocument> docs) {
        SolrServer server = new HttpSolrServer(solrURL);
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

    /**
     * Indexa um unico documentado SOLR
     *
     * @param docs Documento ja no formato SOLR a ser indexado
     * @return True se o documento foi indexado
     */
    public static boolean indexarSolrInputDocument(SolrInputDocument docs) {
        SolrServer server = new HttpSolrServer(solrURL);
        try {
            UpdateResponse response = server.add(docs);

            if (response.getStatus() == 400) {

                log.error("Erro ao indexar o objeto (possivelmente falta de 'entry')");
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

    //PARA TESTES DE VELOCIDADE. PODE SER APAGADO LIVREMENTE DEPOIS
    public IndexarDados(boolean t) {
        serverteste = new HttpSolrServer(solrURL);

    }

    public boolean indexarIndividualmente(SolrInputDocument docs) {
        try {
            UpdateResponse response = serverteste.add(docs);

            if (response.getStatus() == 400) {

                log.error("Erro ao indexar o objeto (possivelmente falta de 'entry')");
                return false;
            }
            return true;
        } catch (SolrServerException e) {
            log.error("Nao foi possivel se conectar ao servidor solr ou o documento não está configurado corretamente", e);
        } catch (IOException e) {
            log.error("Nao foi possivel se conectar ao servidor solr ou o documento não está configurado corretamente", e);
        }

        return false;
    }

    public void darCommmit() {
        try {
            serverteste.commit();
        } catch (SolrServerException ex) {
            java.util.logging.Logger.getLogger(IndexarDados.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(IndexarDados.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public boolean indexarColecaoSolrInputDocument2(Collection<SolrInputDocument> docs) {
        SolrServer server = new HttpSolrServer(solrURL);
        try {
            server.add(docs);

            server.commit();

            return true;
        } catch (SolrServerException e) {
            log.error("Nao foi possivel se conectar ao servidor solr ou o documento não está configurado corretamente", e);
        } catch (IOException e) {
            log.error("Nao foi possivel se conectar ao servidor solr ou o documento não está configurado corretamente", e);
        }

        return false;
    }

}
