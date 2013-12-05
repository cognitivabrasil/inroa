package feb.solr.camposObaa;

import java.util.ArrayList;
import java.util.List;

import cognitivabrasil.obaa.OBAA;

public class LifeCycleFields {
	
	private static final String cabecalho = "obaa.lifecyclefield.";
	
	public static List<String> getStatus(OBAA o)
	{
		List<String> retorno = new ArrayList<String>();
		retorno.add(cabecalho+"status");
		retorno.add(o.getLifeCycle().getStatus());
		return retorno;
	}
	
	public static List<String> getEntity(OBAA o)
	{
		List<String> entities = new ArrayList<String>();
		entities.add(cabecalho+"entitiy");
		
		for (int i = 0 ; i < o.getLifeCycle().getContribute().size(); i++)
			for (int j = 0 ; j < o.getLifeCycle().getContribute().get(i).getEntities().size(); j++)
				entities.add(o.getLifeCycle().getContribute().get(i).getEntities().get(j));
		
		return entities;
	}
	
	public static List<String> getRole (OBAA o)
	{
		List<String> roles = new ArrayList<String>();
		roles.add(cabecalho+"role");
		for (int i = 0 ; i < o.getLifeCycle().getContribute().size(); i++)
			roles.add(o.getLifeCycle().getContribute().get(i).getRole().getText());
		
		return roles;
	}
	
	public static List<List<String>> getAll (OBAA o)
	{
		List<List<String>> all = new ArrayList<List<String>>();
			all.add(getEntity(o));
			all.add(getRole(o));
			all.add(getStatus(o));
			return all;
		
	}
	
}
