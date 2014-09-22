package com.cognitivabrasil.feb.solr.camposObaa;

import java.util.ArrayList;
import java.util.List;

import cognitivabrasil.obaa.OBAA;

public class RightsFields {

    private static final String cabecalho = "obaa.rights.";

    public static List<String> getCost(OBAA o) {

        List<String> cost = new ArrayList<String>();
        cost.add(cabecalho + "cost");

        if (o.getRights().getCost() == null) {
            cost.add(null);
            return cost;
        }

        cost.add(o.getRights().getCost().toString());
        return cost;
    }

    public static List<String> getCopyrightandotherrestrictions(OBAA o) {

        List<String> copy = new ArrayList<String>();
        copy.add(cabecalho + "copyrightandotherrestrictions");
        if (o.getRights().getCopyright() == null) {
            copy.add(null);
            return copy;
        }

        copy.add(o.getRights().getCopyright().toString());
        return copy;
    }

    public static List<String> getDescription(OBAA o) {
        List<String> description = new ArrayList<String>();
        description.add(cabecalho + "description");
        description.add(o.getRights().getDescription());
        return description;
    }

    public static List<List<String>> getAll(OBAA o) {
        if (o.getRights() == null) {
            return null;
        }

        List<List<String>> all = new ArrayList<List<String>>();

        all.add(getCopyrightandotherrestrictions(o));
        all.add(getCost(o));
        all.add(getDescription(o));
        return all;
    }

}
