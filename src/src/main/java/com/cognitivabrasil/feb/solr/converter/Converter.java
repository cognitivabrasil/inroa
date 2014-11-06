package com.cognitivabrasil.feb.solr.converter;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.solr.common.SolrInputDocument;

import com.cognitivabrasil.feb.solr.camposObaa.ObaaDocument;

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
     * Converte um documento obaa para Solr.
     *
     * @return Documento SOLR pronto para ser indexado
     */
    public static SolrInputDocument obaaToSolr(ObaaDocument obaaDoc) {
        OBAA o = obaaDoc.getMetadata();
        String entry = obaaDoc.getObaaEntry();
        int id = obaaDoc.getId();
        
        SolrInputDocument doc = listToSolrInputDocument(com.cognitivabrasil.feb.solr.camposObaa.AllFields.getAll(o));

        doc.addField("obaa.general.identifier.entry", entry);
        doc.addField("obaa.idBaseDados", id);
        
        Map<String,Object> extraArgs = obaaDoc.getExtraSearchFields();
        
        for(Entry<String,Object> e : extraArgs.entrySet()) {
            doc.addField(e.getKey(), e.getValue());
        }

        return doc;
    }
}
