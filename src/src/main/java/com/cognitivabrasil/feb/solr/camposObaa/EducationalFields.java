package com.cognitivabrasil.feb.solr.camposObaa;

import java.util.ArrayList;
import java.util.List;

import cognitivabrasil.obaa.OBAA;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EducationalFields {

    private static final String cabecalho = "obaa.educational.";
    private static final Logger log = LoggerFactory.getLogger(EducationalFields.class);

    public static List<String> getInteractivitytype(OBAA o) {

        List<String> tipo = new ArrayList<>();
        tipo.add(cabecalho + "interactivitytype");
        tipo.add(o.getEducational().getInteractivityType());
        return tipo;
    }

    public static List<String> getLearningresourcetype(OBAA o) {

        List<String> learning = new ArrayList<>();
        learning.add(cabecalho + "learningresourcetype");
        learning.addAll(o.getEducational().getLearningResourceTypesString());
        return learning;
    }

    public static List<String> getIntendedenduserrole(OBAA o) {

        List<String> intended = new ArrayList<>();
        intended.add(cabecalho + "intendedenduserrole");
        intended.addAll(o.getEducational().getIntendedEndUserRole());
        return intended;
    }

    public static List<String> getContext(OBAA o) {

        List<String> context = new ArrayList<>();
        context.add(cabecalho + "context");
        context.addAll(o.getEducational().getContexts());
        return context;
    }

    public static List<String> getTypicalagerange(OBAA o) {

        List<String> typical = new ArrayList<>();
        typical.add(cabecalho + "typicalagerange");
        typical.addAll(o.getEducational().getTypicalAgeRanges());

        return typical;
    }
    
    /*
    
    public static List<String> getTypicalagerange(OBAA o) {

        List<String> typical = new ArrayList<String>();
        typical.add(cabecalho + "typicalagerange");
        typical.addAll(o.getEducational().getTypicalAgeRanges());
        if (o.getEducational().getTypicalAgeRanges().size()>0)
        {
            log.debug(o.getEducational().getTypicalAgeRanges());
        }
        return typical;
    }
    */

    public static int getMintypicalagerange(OBAA o) {

        int min = -1;

        if (o.getEducational().getTypicalAgeRanges().size()>0
                && o.getEducational() != null &&
                o.getEducational().getTypicalAgeRanges() != null) {
                        log.debug("IdadeMinima");

            String[] idades = o.getEducational().getTypicalAgeRanges().get(0).split("-");
            //Adicionamos e removemos o espaco em branco que tem depois do digito de idade
            min = Integer.parseInt(idades[0].substring(0, idades[0].length() - 1));
        }

        return min;
    }

    public static int getMaxtypicalagerange(OBAA o) {

        int max = -1;

        if (o.getEducational() != null &&
                o.getEducational().getTypicalAgeRanges() != null
                && !o.getEducational().getTypicalAgeRanges().isEmpty()) {
            String[] idades = o.getEducational().getTypicalAgeRanges().get(0).split("-");
            //Adicionamos e removemos o espaco em branco que tem antes e depois do digito de idade
            idades[1] = idades[1].substring(1);
            max = Integer.parseInt(idades[1].substring(0, idades[1].indexOf(" ")));
        }

        return max;
    }

    public static List<String> getDifficulty(OBAA o) {

        List<String> difficulty = new ArrayList<>();
        difficulty.add(cabecalho + "difficulty");
        if (o.getEducational().getDifficulty() == null) {
            difficulty.add(null);
            return difficulty;
        }

        difficulty.add(o.getEducational().getDifficulty().getText());
        return difficulty;
    }

    public static List<List<String>> getAll(OBAA o) {

        if (o.getEducational() == null) {
            return null;
        }

        List<List<String>> all = new ArrayList<>();

        all.add(getContext(o));
        all.add(getDifficulty(o));
        all.add(getIntendedenduserrole(o));
        all.add(getInteractivitytype(o));
        all.add(getLearningresourcetype(o));
        all.add(getTypicalagerange(o));
        return all;
    }
}
