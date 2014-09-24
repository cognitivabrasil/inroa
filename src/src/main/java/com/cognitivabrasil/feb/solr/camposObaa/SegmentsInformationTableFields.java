package com.cognitivabrasil.feb.solr.camposObaa;

import java.util.ArrayList;
import java.util.List;

import cognitivabrasil.obaa.OBAA;

public class SegmentsInformationTableFields {

    private static final String cabecalho = "obaa.segmentinformationtable.";

    public static List<String> getSegmentlistTitle(OBAA o) {
        List<String> title = new ArrayList<String>();
        title.add(cabecalho + "segmentlist.segmentinformation.title");
        for (int i = 0; i < o.getSegmentsInformationTable().getSegmentList().size(); i++) {
            for (int j = 0; j < o.getSegmentsInformationTable().getSegmentList().get(i).getSegmentInformation().size(); j++) {
                title.add(o.getSegmentsInformationTable().getSegmentList().get(i).getSegmentInformation().get(j).getTitle());
            }
        }

        return title;
    }

    public static List<String> getSegmentlistDescription(OBAA o) {
        List<String> description = new ArrayList<String>();
        description.add(cabecalho + "segmentlist.segmentinformation.description");
        for (int i = 0; i < o.getSegmentsInformationTable().getSegmentList().size(); i++) {
            for (int j = 0; j < o.getSegmentsInformationTable().getSegmentList().get(i).getSegmentInformation().size(); j++) {
                description.add(o.getSegmentsInformationTable().getSegmentList().get(i).getSegmentInformation().get(j).getDescription());
            }
        }

        return description;

    }

    public static List<String> getSegmentlistKeywords(OBAA o) {
        List<String> keywords = new ArrayList<String>();
        keywords.add(cabecalho + "segmentlist.segmentinformation.keyword");
        for (int i = 0; i < o.getSegmentsInformationTable().getSegmentList().size(); i++) {
            for (int j = 0; j < o.getSegmentsInformationTable().getSegmentList().get(i).getSegmentInformation().size(); j++) {
                keywords.addAll(o.getSegmentsInformationTable().getSegmentList().get(i).getSegmentInformation().get(j).getKeywords());
            }
        }

        return keywords;

    }

    public static List<String> getSegmentgrouplistTitle(OBAA o) {
        List<String> title = new ArrayList<String>();
        title.add(cabecalho + "segmentgrouplist.segmentgroupinformation.title");
        for (int i = 0; i < o.getSegmentsInformationTable().getSegmentGroupList().getSegmentGroupInformation().size(); i++) {
            title.add(o.getSegmentsInformationTable().getSegmentGroupList().getSegmentGroupInformation().get(i).getTitle());
        }

        return title;
    }

    public static List<String> getSegmentgrouplistDescription(OBAA o) {
        List<String> description = new ArrayList<String>();
        description.add(cabecalho + "segmentgrouplist.segmentgroupinformation.description");
        for (int i = 0; i < o.getSegmentsInformationTable().getSegmentGroupList().getSegmentGroupInformation().size(); i++) {
            description.add(o.getSegmentsInformationTable().getSegmentGroupList().getSegmentGroupInformation().get(i).getDescription());
        }

        return description;

    }

    public static List<List<String>> getAll(OBAA o) {
        List<List<String>> all = new ArrayList<List<String>>();
        all.add(getSegmentgrouplistDescription(o));
        all.add(getSegmentgrouplistTitle(o));
        all.add(getSegmentlistDescription(o));
        all.add(getSegmentlistKeywords(o));
        all.add(getSegmentlistTitle(o));
        return all;

    }
}
