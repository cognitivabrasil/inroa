package feb.solr.camposObaa;

import java.util.ArrayList;
import java.util.List;

import cognitivabrasil.obaa.OBAA;

public class GeneralFields {

	private static final String cabecalho = "obaa.general.";

	public static List<String> getEntry(OBAA o)
	{
		List<String> entry = new ArrayList<String>();
		entry.add(cabecalho+"identifier.entry");
		for (int i = 0 ; i < o.getGeneral().getIdentifiers().size(); i++)
			entry.add(o.getGeneral().getIdentifiers().get(i).getEntry());
		
		return entry;
	}

	public static List<String> getTitle(OBAA o)
	{
		List<String> title = new ArrayList<String>();
		title.add(cabecalho+"title");
		title.addAll(o.getGeneral().getTitles());
		return title;
	}

	public static List<String> getLanguage(OBAA o)
	{
		List<String> language = new ArrayList<String>();
		language.add(cabecalho+"language");
		language.addAll(o.getGeneral().getLanguages());
		return language;
	}

	public static List<String> getDescription(OBAA o)
	{
		List<String> description = new ArrayList<String>();
		description.add(cabecalho+"description");                
		for (int i = 0; i < o.getGeneral().getDescriptions().size(); i++)
		description.add(o.getGeneral().getDescriptions().get(i));
		
		return description;
		
	}

	public static List<String> getKeyword(OBAA o)
	{
		List<String> keys = new ArrayList<String>();
		keys.add(cabecalho+"keyword");
		
		for (int i = 0 ; i < o.getGeneral().getKeywords().size(); i++)
			keys.add(o.getGeneral().getKeywords().get(i));
		
		return keys;
	}

/*
	public static List<String> getStructure(OBAA o)
	{
		List<String> retorno = new ArrayList<String>();
		retorno.add(cabecalho+"status");

	}

	public static List<String> getAggregationLeval(OBAA o)
	{
		List<String> retorno = new ArrayList<String>();
		retorno.add(cabecalho+"status");

	}
*/
	public static List<List<String>> getAll (OBAA o)
	{
		List<List<String>> all = new ArrayList<List<String>>();
		
		all.add(getDescription(o));
		all.add(getEntry(o));
		all.add(getKeyword(o));
		all.add(getLanguage(o));
		all.add(getTitle(o));
//		all.add(getAggregationLeval(o));
//		all.add(getStructure(o));
		
		return all;
	}

}
