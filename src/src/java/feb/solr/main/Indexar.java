package feb.solr.main;

import feb.solr.converter.DocumentoSolr;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;

public class Indexar {

    SolrServer server;
    SolrInputDocument doc;

    public Indexar() {
        server = new HttpSolrServer("http://localhost:8080/solr");
        doc = new SolrInputDocument();
    }

    public Indexar(String url) {
        server = new HttpSolrServer(url);
        doc = new SolrInputDocument();
    }

    public boolean addFile(DocumentoSolr documento) {
        doc = new SolrInputDocument();
        for (int i = 0; i < documento.size(); i++) {
            doc.addField(documento.getField(i), documento.getDados(i));
        }

			// Add fields. The field names should match fields defined in schema.xml
        //		doc.addField("id", "552199");
        //	doc.addField("name", "Gouda cheese wheel");
        //doc.addField("price", "49.99");
        try {

            UpdateResponse response = server.add(doc);
            if (response.getStatus() == 400) {
                return false;
            }

            server.commit();

            return true;

        } catch (Exception e) {
            //TODO: Nao eh legal tratar Exception. Tem que ver qual exception 
            //quer tratar e fazer alguma coisa nem que seja imprimir o erro no log
        }

        return false;

    }

    public boolean addFile(SolrInputDocument documento) {

        try {

            UpdateResponse response = server.add(documento);
            if (response.getStatus() == 400) {
                return false;
            }

            server.commit();

            return true;

        } catch (Exception e) {
            //TODO: Nao eh legal tratar Exception.
        }

        return false;

    }

}
