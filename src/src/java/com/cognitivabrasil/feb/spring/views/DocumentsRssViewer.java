package com.cognitivabrasil.feb.spring.views;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.view.feed.AbstractRssFeedView;

import com.sun.syndication.feed.rss.Channel;
import com.sun.syndication.feed.rss.Content;
import com.sun.syndication.feed.rss.Image;
import com.sun.syndication.feed.rss.Item;

import com.cognitivabrasil.feb.data.entities.Autor;
import com.cognitivabrasil.feb.data.entities.Document;

public class DocumentsRssViewer extends AbstractRssFeedView {
	
	private static String getBaseUrl(HttpServletRequest req) {
	 	String scheme = req.getScheme();             // http
	    String serverName = req.getServerName();     // hostname.com
	    int serverPort = req.getServerPort();        // 80
	    String contextPath = req.getContextPath();   // /mywebapp
	    StringBuffer url =  new StringBuffer();
	    url.append(scheme).append("://").append(serverName);

	    if ((serverPort != 80) && (serverPort != 443)) {
	        url.append(":").append(serverPort);
	    }

	    url.append(contextPath);  
	    return url.toString();
	}
	
	private static String getDocLink(Document d, HttpServletRequest req) {		    
		    return getBaseUrl(req) + "/objetos/" + d.getId();
	}

	@Override
	public void buildFeedMetadata(Map<String, Object> model, Channel feed,
			HttpServletRequest request) {

		feed.setTitle("Feb Feed");
		feed.setDescription("Objetos de Aprendizagem");
		feed.setLink("http://feb.rnp.br");
		Image img = new Image();
		img.setUrl(getBaseUrl(request) + "/imagens/favicon.ico");
		img.setTitle("FEB Logo");
		feed.setImage(img);

		super.buildFeedMetadata(model, feed, request);
	}

	@Override
	public List<Item> buildFeedItems(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		@SuppressWarnings("unchecked")
		List<Document> listContent = (List<Document>) model
				.get("feedContent");
		List<Item> items = new ArrayList<Item>(listContent.size());

		for (Document doc : listContent) {

			Item item = new Item();

			Content content = new Content();
			
			String contentText = "";
			for(String d : doc.getDescriptions()) {
				contentText += "<p><strong>Descrição:</strong> " + d + "</p>";
			}
			
			if(doc.getKeywords().size() > 0) {
				contentText += "<p><strong>Palavras chave: </strong>";
				contentText += StringUtils.join(doc.getKeywords(), "; ");
				contentText += "</p>";
			}
			if(doc.getAutores().size() > 0) {
				contentText += "<p><strong>Autor(es): </strong>";
				List<String> aut = new ArrayList<String>();
				for(Autor a : doc.getAutores()) {
					aut.add(a.getNome());
				}
				contentText += StringUtils.join(aut, "; ");
				contentText += "</p>";
			}
			
			content.setValue(contentText);
			item.setContent(content);

			item.setTitle(doc.getTitles().get(0));
			
			item.setLink(getDocLink(doc, request));
			item.setPubDate(doc.getTimestamp());

			items.add(item);
		}

		return items;
	}

}
