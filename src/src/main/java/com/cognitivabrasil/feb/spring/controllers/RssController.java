package com.cognitivabrasil.feb.spring.controllers;

import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.cognitivabrasil.feb.data.entities.Document;
import com.cognitivabrasil.feb.ferramentaBusca.ConsultaFeb;
import com.cognitivabrasil.feb.solr.ObaaSearchServiceSolrImpl;

import org.apache.solr.client.solrj.SolrServerException;

@Controller
@RequestMapping("/rss")
public class RssController {
    private static final Logger log = LoggerFactory.getLogger(RssController.class);
    
    @Autowired
    private ObaaSearchServiceSolrImpl recuperador;

    @RequestMapping(value = "/feed", method = RequestMethod.GET)
    public ModelAndView getFeedInRss(@ModelAttribute("buscaModel") ConsultaFeb consulta) throws SolrServerException {
        log.debug("trying to get feed: {}", consulta);

        consulta.setRss(true);
        List<Document> items = recuperador.busca(consulta).getDocuments();

        ModelAndView mav = new ModelAndView();
        mav.setViewName("rssViewer");
        mav.addObject("feedContent", items);
        
        return mav;        
    }
}