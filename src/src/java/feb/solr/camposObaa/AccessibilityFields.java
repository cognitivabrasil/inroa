package feb.solr.camposObaa;

import java.util.ArrayList;
import java.util.List;

import cognitivabrasil.obaa.OBAA;

public class AccessibilityFields {

	private static final String cabecalho = "obaa.educational.resourcedescription.primary.";


	public static List<String> getHasVisual(OBAA o)
	{
		List<String> visual = new ArrayList<String>();
		visual.add(cabecalho+"hasvisual");
		visual.add(o.getAccessibility().getResourceDescription().getPrimary().getHasVisual());
		return visual;
	}


	public static List<String> getHasAuditory(OBAA o)
	{
		List<String> audio = new ArrayList<String>();
		audio.add(cabecalho+"hasauditory");
		audio.add(o.getAccessibility().getResourceDescription().getPrimary().getHasAuditory());
		return audio;
	}


	public static List<String> getHasTactile(OBAA o)
	{
		List<String> tatil = new ArrayList<String>();
		tatil.add(cabecalho+"hastactile");
		tatil.add(o.getAccessibility().getResourceDescription().getPrimary().getHasTactile());
		return tatil;
	}


	public static List<String> getHasText(OBAA o)
	{
		List<String> text = new ArrayList<String>();
		text.add(cabecalho+"hastext");
		text.add(o.getAccessibility().getResourceDescription().getPrimary().getHasText());
		return text;
	}

	public static List<List<String>> getAll (OBAA o)
	{
		List<List<String>> all = new ArrayList<List<String>>();

		all.add(getHasAuditory(o));
		all.add(getHasTactile(o));
		all.add(getHasText(o));
		all.add(getHasVisual(o));
		return all;
	}
}
