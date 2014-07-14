package com.cognitivabrasil.feb.solr.camposObaa;

import java.util.ArrayList;
import java.util.List;

import cognitivabrasil.obaa.OBAA;
import static cognitivabrasil.util.HelperFunctions.toStringList;
import java.util.Map;

public class TechnicalFields {

    private static final String cabecalho = "obaa.technical.";

    public static List<String> getFormat(OBAA o) {
        List<String> format = new ArrayList<String>();
        format.add(cabecalho + "format");
        format.addAll(toStringList(o.getTechnical().getFormats()));
        return format;
    }

    public static List<String> getSize(OBAA o) {
        List<String> tamanho = new ArrayList<String>();
        tamanho.add(cabecalho + "size");
        tamanho.add(o.getTechnical().getSize());
        return tamanho;

    }

    public static List<String> getDuration(OBAA o) {
        List<String> duration = new ArrayList<String>();
        duration.add(cabecalho + "duration");
        duration.add(o.getTechnical().getDuration());
        return duration;
    }

    public static List<String> getSupportedplatforms(OBAA o) {
        List<String> support = new ArrayList<String>();
        support.add(cabecalho + "supportedplatforms");
        support.addAll(o.getTechnical().getSupportedPlatforms());
        return support;
    }

    public static List<String> getLocation(OBAA o) {
        List<String> location = new ArrayList<String>();
        location.add(cabecalho + "location");
        
        for (Map.Entry<String, Boolean> entry : o.getTechnical().getLocationHttp().entrySet())
        {
            if (entry.getValue())
                location.add(entry.getKey());
        }
        
        // Para mostrar apenas os primeiros 3 locations, removemos os demais
        if (location.size()>4)
            location = location.subList(0, 4);
        
        return location;
    }

    public static List<List<String>> getAll(OBAA o) {
        List<List<String>> all = new ArrayList<List<String>>();

        all.add(getDuration(o));
        all.add(getFormat(o));
        all.add(getSize(o));
        all.add(getSupportedplatforms(o));
        all.add(getLocation(o));
        return all;
    }

}
