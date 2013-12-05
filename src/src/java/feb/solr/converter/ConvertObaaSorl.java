package feb.solr.converter;

import org.apache.solr.common.SolrInputDocument;

import cognitivabrasil.obaa.OBAA;

public class ConvertObaaSorl {



	SolrInputDocument doc  ;
	public SolrInputDocument ConverterObjeto(OBAA o)
	{
		doc  = new SolrInputDocument();
		return doc;
	}
	
/*	public void addGeneral (OBAA o)
	{
		if (o.getGeneral()==null)
			return;
		
		String field = "obaa:general:";

		if (o.getGeneral().getAggregationLevel()!=null)
			addField(field+ "AggregationLevel", o.getGeneral().getAggregationLevel());
		
		if (o.getGeneral().getDescriptions()!=null)
			doc.addField(field+ "Descriptions", o.getGeneral().getDescriptions());
		
		if (o.getGeneral().getKeywords()!=null)
			doc.addField(field+ "Keywords", o.getGeneral().getKeywords());
		
		if (o.getGeneral().getLanguages()!=null)
			doc.addField(field+ "Languages", o.getGeneral().getLanguages());

		//FALTOU O ENTRY, QUE É O MAIS IMPORTATNE
	}
	*/
	
	public void addAccessibility (OBAA o)
	{
		if (o.getAccessibility()==null)
			return;
		
		String field = "obaa:accessibility:resourcedescription:primary:";
		
		if (o.getAccessibility().getResourceDescription().getPrimary().getHasAuditory()!=null)
			doc.addField(field + "hasauditory", o.getAccessibility().getResourceDescription().getPrimary().getHasAuditory());
		
		if (o.getAccessibility().getResourceDescription().getPrimary().getHasText()!=null)
			doc.addField(field + "hastext", o.getAccessibility().getResourceDescription().getPrimary().getHasText()); 
		
		if (o.getAccessibility().getResourceDescription().getPrimary().getHasTactile()!=null)
			doc.addField(field + "hastactile",o.getAccessibility().getResourceDescription().getPrimary().getHasTactile());
		
		if (o.getAccessibility().getResourceDescription().getPrimary().getHasVisual()!=null)
			doc.addField(field + "hasvisual",o.getAccessibility().getResourceDescription().getPrimary().getHasVisual());

	}
	
	public void addEducational (OBAA o)
	{
		if (o.getEducational()==null)
			return;
		
		String field = "obaa:educational:";

		if (o.getEducational().getInteractivityType()!=null)
			doc.addField(field + "interactivitytype",o.getEducational().getInteractivityType());

		if (o.getEducational().getLearningResourceTypes()!=null)
		{
			for (int i = 0 ; i<o.getEducational().getLearningResourceTypes().size(); i++)
			doc.addField(field + "learningresourcetype",o.getEducational().getLearningResourceTypesString().get(i));
		}
		
		if (o.getEducational().getIntendedEndUserRole()!=null)
		{
			for (int i = 0 ; i<o.getEducational().getIntendedEndUserRole().size(); i++)	
				doc.addField(field + "intendedenduserrole",o.getEducational().getIntendedEndUserRole().get(i));
		}
		
		if (o.getEducational().getContexts()!=null)
		{
			for (int i = 0 ; i<o.getEducational().getContexts().size(); i++)	
				doc.addField(field + "context",o.getEducational().getContexts().get(i));
		}
		
		
		//FALTA AINDA.
	}
	
	public void addLifeCycle (OBAA o)
	{
		
	}
	
	public void addRelations (OBAA o)
	{
		
	}
	
	public void addRights (OBAA o)
	{
		
	}
	
	public void addTechnical (OBAA o)
	{
		
	}
	
	public void addSegmentsInformationTable (OBAA o)
	{
		
	}
	
}

