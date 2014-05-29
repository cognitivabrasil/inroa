package com.cognitivabrasil.feb.spring.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.cognitivabrasil.feb.data.entities.Consulta;
import com.cognitivabrasil.feb.data.entities.Document;
import com.cognitivabrasil.feb.ferramentaBusca.Recuperador;
 
@Controller
@RequestMapping("/rss")
public class RssController { 
	
	@RequestMapping(value="/feed", method = RequestMethod.GET)
	public ModelAndView getFeedInRss(@ModelAttribute("buscaModel") Consulta consulta) {
 
        Recuperador rec = new Recuperador();
        
        consulta.setRss(true);
        List<Document> items = rec.busca(consulta);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("rssViewer");
		mav.addObject("feedContent", items);
 
		return mav;
 
	}
 
}