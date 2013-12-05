package feb.solr.converter;

import java.util.ArrayList;

import cognitivabrasil.obaa.OBAA;

//Vai ser um arrayList<arraylist><String>.
//Metodos do tipo add(string, string) que vao adicionar ao ArrayList<ArrayList<Sitnrg,>.
public class DocumentoSolr {

	private ArrayList<ArrayList<String>> documento; 
	
	
	public DocumentoSolr()
	{
		documento  = new ArrayList<ArrayList<String>>();
		
	}
	
	public DocumentoSolr(String xml)
	{
		documento  = new ArrayList<ArrayList<String>>();
		documento = docFromXML(xml);
		
	}
	
	public DocumentoSolr (OBAA o)
	{
		
	}
	
	public void add (String field, String dado)
	{
		ArrayList<String> adicionar = new ArrayList<String>();
		adicionar.add(field);
		adicionar.add(dado);
		documento.add(adicionar);
	}
	
	public String getField (int i)
	{return documento.get(i).get(0);}
	
	public String getDados(int i)
	{return documento.get(i).get(1);}
	
	
	public String getDados(String field)
	{
		for (int i = 0 ; i < documento.size(); i++)
			if (documento.get(i).get(0).equalsIgnoreCase(field))
				return documento.get(i).get(1);
		
		return "O field nao esta presente no documento";
	}
	
	public int getField(String field)
	{
		for (int i = 0 ; i < documento.size(); i++)
			if (documento.get(i).get(0).equalsIgnoreCase(field))
				return i;
		
		return -1;
	}
	
	public DocumentoSolr add (String xml)
	{
		DocumentoSolr novoDoc = new DocumentoSolr();
		novoDoc.documento = docFromXML(xml);
		return novoDoc;
	}
	
	private ArrayList<ArrayList<String>> docFromXML(String xml)
	{
		ArrayList<ArrayList<String>> docConvertido = new ArrayList<ArrayList<String>>();
		
	//	for (int i = 0 ; i < xml.lines(); i++)
	//		docConvertido.add(add(xml1, xml2));
		
		return docConvertido;
	}
	
	
	public int contain (DocumentoSolr doc, String texto)
	{
		for (int i = 0 ; i< doc.size(); i++)
		{
			if (doc.documento.contains(texto))
				return i;
		}
		
		return -1;
	}
	
	
	public int size()
	{return documento.size();}
}
