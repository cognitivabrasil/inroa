package feb.solr.main;

import feb.solr.indexar.IndexarDados;
import feb.solr.converter.Converter;
import feb.solr.bd.AcessarBD;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;

import cognitivabrasil.obaa.OBAA;
import feb.data.entities.DocumentoReal;
import java.util.ArrayList;
import java.util.List;
import org.apache.solr.common.SolrInputDocument;

public class Solr {

    private AcessarBD bd;
    private static final Logger log = Logger.getLogger(Solr.class);
    private static final int maxDocs = 10000;

    public Solr() {
        bd = new AcessarBD();
    }

    public static void indexarBancoDeDados(List<DocumentoReal> docs) {
        List<SolrInputDocument> docsSolr = new ArrayList<SolrInputDocument>();
        int numDocs = 0;
        log.debug("Convertendo obaaXML em SolrXML...");

        for (DocumentoReal doc : docs) {
            numDocs++;
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

            int repositorio = doc.getRepositorio() != null ? doc.getRepositorio().getId() : -1;
            int subFeb = doc.getRepositorioSubFed() != null ? doc.getRepositorioSubFed().getId() : -1;
            int federacao = doc.getRepositorioSubFed() != null
                    ? doc.getRepositorioSubFed().getSubFederacao().getId() : -1;

            docsSolr.add(Converter.OBAAToSolrInputDocument(doc.getMetadata(), doc.getId(), repositorio, subFeb, federacao));

            if (numDocs == maxDocs) {
                log.debug("Enviando para o Solrs a lista de documento.");
                //Tenta fazer o upload para o Solr. Se não conseguiu, faz upload de um por um
                if (!IndexarDados.indexarColecaoSolrInputDocument(docsSolr)) {
                    log.error("Erro ao mandar a lista de documentos para o Solr, sera enviado um a um.");
                    for (int d = 0; d < docsSolr.size(); d++) {
                        IndexarDados.indexarSolrInputDocument(docsSolr.get(d));
                    }
                }
                numDocs = 0;
                docsSolr = new ArrayList<SolrInputDocument>();
            }
        }

    }
    /*
     public boolean indexarBancoDeDados() throws SQLException {

     // Testa se esta conectado ao BD e, se nao estiver, tenta conectar.
     if (!bd.estaConectado()) {
     bd = new AcessarBD();
     if (!bd.estaConectado()) {
     System.out.println("Erro de conexao. Não foi possivel conectar no BD!");
     return false;
     }
     }

     // Fazer uploads de cerca de 10.000 arquivos por vez.
     int numeroRows = bd.numRowsDocumentos() / bd.PAGE + 1;

     for (int a = 0; a < numeroRows; a++) {
     ResultSet rs = bd.BuscarObjetos(a);
     List<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
     while (rs.next()) {

     if (rs.getString(12).isEmpty()) {
     continue;
     }

     OBAA o = OBAA.fromString(rs.getString(12));
     if (o.getGeneral().getIdentifiers() != null && o.getGeneral().getIdentifiers().size() > 0) {
     o.getGeneral().getIdentifiers().get(0).setEntry(rs.getString(3));
                    
     //      docs.add(Converter.OBAAToSolrInputDocument(o, rs.getInt(1)));

     } else {
     //Nem ideia de como fazer isso....
     }
     }

     //Tenta fazer o upload para o Solr. Se não conseguiu, faz upload de um por um
     if (!IndexarDados.indexarColecaoSolrInputDocument(docs)) {
     for (int d = 0; d < docs.size(); d++) {
     IndexarDados.indexarSolrInputDocument(docs.get(d));
     }
     }

     }

     return true;

     }

     public static boolean indexarSolr() throws SQLException {
     Solr s = new Solr();
     if (!s.bd.estaConectado()) {
     System.out.println("Erro de conexao. Não foi possivel conectar no BD!");
     return false;
     }
     s.indexarBancoDeDados();
     return true;
     }
    
     */
}
