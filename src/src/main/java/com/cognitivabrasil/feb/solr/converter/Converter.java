package com.cognitivabrasil.feb.solr.converter;

import java.util.List;

import org.apache.solr.common.SolrInputDocument;

import cognitivabrasil.obaa.OBAA;

/**
 * Métodos utilitários para converter objetos OBAA em documentos que possam ser enviados
 * para o Solr.
 * 
 * @author Daniel Esptein
 * @author Paulo Schreiner
 */
public class Converter {
    
    private Converter() {
        
    }

    /**
     * Converte uma Lista de lista de strings para formato SORL (cria um unico
     * documento com esse parametro).
     *
     * @param objeto Lista de lista de strings no formatp <field:texto>
     * @return O documento SOLR
     */
    public static SolrInputDocument listToSolrInputDocument(List<List<String>> objeto) {
        SolrInputDocument documento = new SolrInputDocument();
        
        for(List<String> field : objeto) {
            for (int j = 1; j < field.size(); j++) {
                documento.addField(field.get(0), field.get(j));
            }
        }

        return documento;
    }


    /**
     * Converte um objeto do tipo OBAA para um documento SOLR.
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
     * @param nomeRep nome do repositório
     * @return Documento SOLR pronto para ser indexado
     */
    public static SolrInputDocument obaaToSolr(OBAA o, String entry, int id, int rep, int subFeb, int federacao, String nomeRep) {
        SolrInputDocument doc = listToSolrInputDocument(com.cognitivabrasil.feb.solr.camposObaa.AllFields.getAll(o));

        doc.addField("obaa.general.identifier.entry", entry);
        doc.addField("obaa.idBaseDados", id);
        doc.addField("obaa.repositorio", rep);
        doc.addField("obaa.subFederacao", subFeb);
        doc.addField("obaa.federacao", federacao);
        doc.addField("obaa.repName", nomeRep);

        return doc;
    }
}
