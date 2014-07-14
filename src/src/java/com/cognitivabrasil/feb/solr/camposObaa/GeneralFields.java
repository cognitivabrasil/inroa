//******************COMPLETO **************////////
package com.cognitivabrasil.feb.solr.camposObaa;

import java.util.ArrayList;
import java.util.List;

import cognitivabrasil.obaa.OBAA;

public class GeneralFields {

    private static final String cabecalho = "obaa.general.";

    public static List<String> getTitle(OBAA o) {
        List<String> title = new ArrayList<String>();
        title.add(cabecalho + "title");
        title.addAll(o.getGeneral().getTitles());
        return title;
    }

    public static List<String> getLanguage(OBAA o) {
        List<String> language = new ArrayList<String>();
        language.add(cabecalho + "language");
        language.addAll(o.getGeneral().getLanguages());
        return language;
    }

    public static List<String> getDescription(OBAA o) {
        List<String> description = new ArrayList<String>();
        description.add(cabecalho + "description");
        for (int i = 0; i < o.getGeneral().getDescriptions().size(); i++) {
            description.add(o.getGeneral().getDescriptions().get(i));
        }

        return description;

    }

    public static List<String> getKeyword(OBAA o) {
        List<String> keys = new ArrayList<String>();
        keys.add(cabecalho + "keyword");

        for (int i = 0; i < o.getGeneral().getKeywords().size(); i++) {
            keys.add(o.getGeneral().getKeywords().get(i));
        }

        return keys;
    }

    public static List<String> getStructureCountry(OBAA o) {
        List<String> structure = new ArrayList<String>();
        structure.add(cabecalho + "structure.country");
        if (o.getGeneral().getStructure() != null) {
            structure.add(o.getGeneral().getStructure().getCountry());
        }
        return structure;
    }

    public static List<String> getStructureLanguage(OBAA o) {
        List<String> structure = new ArrayList<String>();
        structure.add(cabecalho + "structure.language");
        if (o.getGeneral().getStructure() != null) {
            structure.add(o.getGeneral().getStructure().getLanguage());
        }
        return structure;
    }

    public static List<String> getStructureText(OBAA o) {
        List<String> structure = new ArrayList<String>();
        structure.add(cabecalho + "structure.text");
        if (o.getGeneral().getStructure() != null) {
            structure.add(o.getGeneral().getStructure().getText());
        }
        return structure;
    }

    public static List<String> getStructureTranslated(OBAA o) {
        List<String> structure = new ArrayList<String>();
        structure.add(cabecalho + "sctructure.translated.");
        if (o.getGeneral().getStructure() != null) {
            structure.add(o.getGeneral().getStructure().getTranslated());
        }
        return structure;
    }

    public static List<String> getAggregationCountry(OBAA o) {
        List<String> aggregation_level = new ArrayList<String>();
        aggregation_level.add(cabecalho + "aggregation_level.country");
        if (o.getGeneral().getAggregationLevel() != null) {
            aggregation_level.add(o.getGeneral().getAggregationLevel().getCountry());
        }
        return aggregation_level;
    }

    public static List<String> getAggregationLanguage(OBAA o) {
        List<String> aggregation_level = new ArrayList<String>();
        aggregation_level.add(cabecalho + "aggregation_level.language");
        if (o.getGeneral().getAggregationLevel() != null) {
            aggregation_level.add(o.getGeneral().getAggregationLevel().getLanguage());
        }
        return aggregation_level;
    }

    public static List<String> getAggregationText(OBAA o) {
        List<String> aggregation_level = new ArrayList<String>();
        aggregation_level.add(cabecalho + "aggregation_level.text");
        if (o.getGeneral().getAggregationLevel() != null) {
            aggregation_level.add(o.getGeneral().getAggregationLevel().getText());
        }
        return aggregation_level;
    }

    public static List<String> getAggregationTranslated(OBAA o) {
        List<String> aggregation_level = new ArrayList<String>();
        aggregation_level.add(cabecalho + "aggregation_level.translated.");
        if (o.getGeneral().getAggregationLevel() != null) {
            aggregation_level.add(o.getGeneral().getAggregationLevel().getTranslated());
        }
        return aggregation_level;
    }

    public static List<List<String>> getAll(OBAA o) {
        List<List<String>> all = new ArrayList<List<String>>();

        all.add(getDescription(o));
        all.add(getKeyword(o));
        all.add(getLanguage(o));
        all.add(getTitle(o));

        all.add(getStructureCountry(o));
        all.add(getStructureLanguage(o));
        all.add(getStructureText(o));
        all.add(getStructureTranslated(o));

        all.add(getAggregationCountry(o));
        all.add(getAggregationLanguage(o));
        all.add(getAggregationText(o));
        all.add(getAggregationTranslated(o));

        return all;
    }

}
