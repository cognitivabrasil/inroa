package feb.solr.camposObaa;

import java.util.ArrayList;
import java.util.List;

import cognitivabrasil.obaa.OBAA;

public class AllFields {
	

	public static List<List<String>> getAll (OBAA o)
	{
		List<List<String>> all = new ArrayList<List<String>>();
		
                all.addAll(GeneralFields.getAll(o));				
                
                if (o.getEducational() != null)
                    all.addAll(EducationalFields.getAll(o));
		
                if (o.getRights()!= null)
                    all.addAll(RightsFields.getAll(o));
                
                if (o.getLifeCycle()!=null)
                    all.addAll(LifeCycleFields.getAll(o));
                
		if (o.getSegmentsInformationTable()!= null)
                    all.addAll(SegmentsInformationTableFields.getAll(o));
		
                if (o.getTechnical()!= null)
                    all.addAll(TechnicalFields.getAll(o));
		
                
		return all;
	}
}
