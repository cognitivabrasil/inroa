package com.cognitivabrasil.feb.solr.camposObaa;

import java.util.ArrayList;
import java.util.List;

import cognitivabrasil.obaa.OBAA;

public class EducationalFields {

    private static final String cabecalho = "obaa.educational.";

    public static List<String> getInteractivitytype(OBAA o) {

        List<String> tipo = new ArrayList<String>();
        tipo.add(cabecalho + "interactivitytype");
        tipo.add(o.getEducational().getInteractivityType());
        return tipo;
    }

    public static List<String> getLearningresourcetype(OBAA o) {

        List<String> learning = new ArrayList<String>();
        learning.add(cabecalho + "learningresourcetype");
        learning.addAll(o.getEducational().getLearningResourceTypesString());
        return learning;
    }

    public static List<String> getIntendedenduserrole(OBAA o) {

        List<String> intended = new ArrayList<String>();
        intended.add(cabecalho + "intendedenduserrole");
        intended.addAll(o.getEducational().getIntendedEndUserRole());
        return intended;
    }

    public static List<String> getContext(OBAA o) {

        List<String> context = new ArrayList<String>();
        context.add(cabecalho + "context");
        context.addAll(o.getEducational().getContexts());
        return context;
    }

    public static List<String> getTypicalagerange(OBAA o) {

        List<String> typical = new ArrayList<String>();
        typical.add(cabecalho + "typicalagerange");
        typical.addAll(o.getEducational().getTypicalAgeRanges());
        return typical;
    }

    public static List<String> getDifficulty(OBAA o) {

        List<String> difficulty = new ArrayList<String>();
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

        List<List<String>> all = new ArrayList<List<String>>();

        all.add(getContext(o));
        all.add(getDifficulty(o));
        all.add(getIntendedenduserrole(o));
        all.add(getInteractivitytype(o));
        all.add(getLearningresourcetype(o));
        all.add(getTypicalagerange(o));
        return all;
    }
}
