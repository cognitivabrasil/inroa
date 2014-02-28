package feb.solr.converter;

import java.util.List;

import org.apache.solr.common.SolrInputDocument;

import cognitivabrasil.obaa.OBAA;

public class Converter {

    public static SolrInputDocument listToSolrInputDocument(List<List<String>> objeto) {
        SolrInputDocument documento = new SolrInputDocument();

        for (int i = 0; i < objeto.size(); i++) {
            for (int j = 1; j < objeto.get(i).size(); j++) {
                documento.addField(objeto.get(i).get(0), objeto.get(i).get(j));
            }
        }
        return documento;
    }

    public static SolrInputDocument stringToSolrInputDocument(String objeto) {
        SolrInputDocument documento = new SolrInputDocument();

        return documento;
    }

    public static List<List<String>> OBAAToList(OBAA o) {
        return feb.solr.camposObaa.AllFields.getAll(o);
    }

    public static SolrInputDocument OBAAToSolrInputDocument(OBAA o) {
        return listToSolrInputDocument(feb.solr.camposObaa.AllFields.getAll(o));
    }

    public static SolrInputDocument OBAAToSolrInputDocument(OBAA o, int id, int rep, int subFeb, int federacao) {
        SolrInputDocument doc = listToSolrInputDocument(feb.solr.camposObaa.AllFields.getAll(o));
        doc.addField("obaa.idBaseDados", id);
        doc.addField("obaa.repositorio", rep);
        doc.addField("obaa.subFederacao", subFeb);
        doc.addField("obaa.federacao", federacao);
        return doc;
    }
}
