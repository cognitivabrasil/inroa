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
    

    public static List<String> getTypicalAgeRangeInt(OBAA o) {
        List<String> typical = new ArrayList<>();
        typical.add(cabecalho + "typicalagerangeint");
        
        int min = getMintypicalagerange(o);
        int max = getMaxtypicalagerange(o);
        
        if(min > max || min < 0 || max < 0) {
            return typical;
        }
        
        for(int age = min; age <= max; age++) {
            typical.add(String.valueOf(age));
        }

        return typical;
    }

    /**
     * @param o
     * @return idade mínima no range, ou -1 caso não haja.
     */
    public static int getMintypicalagerange(OBAA o) {
        int min = -1;

        if (o.getEducational() != null && o.getEducational().getTypicalAgeRanges().size()>0
                && 
                o.getEducational().getTypicalAgeRanges() != null) {
                        log.debug("IdadeMinima");

                        String ageRange = o.getEducational().getTypicalAgeRanges().get(0);
                        
                        ageRange = ageRange.replaceAll("[^\\d-]", "");
                        String[] idades = ageRange.split("-");

            //Adicionamos e removemos o espaco em branco que tem depois do digito de idade
            min = Integer.parseInt(idades[0]);
        }

        return min;
    }

    /**
     * @param o
     * @return max age in range, but never more than 20, or -1 if not available
     */
    public static int getMaxtypicalagerange(OBAA o) {
        int max = -1;

        if (o.getEducational() != null &&
                o.getEducational().getTypicalAgeRanges() != null
                && !o.getEducational().getTypicalAgeRanges().isEmpty()) {
            String ageRange = o.getEducational().getTypicalAgeRanges().get(0);
            
            ageRange = ageRange.replaceAll("[^\\d-]", "");
            
            String[] idades = ageRange.split("-");
            //Adicionamos e removemos o espaco em branco que tem antes e depois do digito de idade
            max = Integer.parseInt(idades[1]);
        }

        if(max > 20) {
            return 20;
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
        all.add(getTypicalAgeRangeInt(o));
        
        return all;
    }
}
