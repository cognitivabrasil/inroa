package feb.solr.converter;

import java.util.List;

import org.apache.solr.common.SolrInputDocument;

import cognitivabrasil.obaa.OBAA;

public class Converter {

    /**
     * Converte uma Lista de lista de strings para formato SORL (cria um unico
     * documento com esse parametro)
     *
     * @param objeto Lista de lista de strings no formatp <field:texto>
     * @return O documento SOLR
     */
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

    /**
     * Converte um objeto do tipo OBAA para um documento SOLR
     *
     * @param o Objeto Obaa a ser convertido
     * @param entry String com o Entry do objeto (esse entry pode ser encontrado
     * no banco de dados e nao necessariamente eh o mesmo entry do XML do Obaa).
     * @param id identificador do Objeto referente ao seu numero no banco de
     * dados
     * @param rep Repositorio ao qual o objeto pertence (Se nao pertencer a
     * nenhum, passar o valor -1)
     * @param subFeb Subfederacao ao qual o objeto pertence (Se nao pertencer a
     * nenhum, passar o valor -1)
     * @param federacao Federacao ao qual o objeto pertence (Se nao pertencer a
     * nenhum, passar o valor -1)
     * @return Documento SOLR pronto para ser indexado
     */
    public SolrInputDocument OBAAToSolrInputDocument(OBAA o, String entry, int id, int rep, int subFeb, int federacao, String nomeRep) {
        SolrInputDocument doc = listToSolrInputDocument(feb.solr.camposObaa.AllFields.getAll(o));

        doc.addField("obaa.general.identifier.entry", entry);
        doc.addField("obaa.idBaseDados", id);
        doc.addField("obaa.repositorio", rep);
        doc.addField("obaa.subFederacao", subFeb);
        doc.addField("obaa.federacao", federacao);
        doc.addField("obaa.nomeRep", nomeRep);

        return doc;
    }
}
