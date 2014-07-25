package com.cognitivabrasil.feb.spring.controllers;

import com.cognitivabrasil.feb.data.entities.Repositorio;
import com.cognitivabrasil.feb.data.entities.SubFederacao;
import com.cognitivabrasil.feb.data.services.DocumentService;
import com.cognitivabrasil.feb.data.services.FederationService;
import com.cognitivabrasil.feb.data.services.RepositoryService;
import com.cognitivabrasil.feb.data.services.TagCloudService;

import com.cognitivabrasil.feb.util.Message;

import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Marcos Freitas Nunes <marcos@cognitivabrasil.com.br>
 */
@Controller("statistics")
@RequestMapping("/admin/statistics/*")
public final class StatisticsController {

    @Autowired
    private RepositoryService repDao;
    @Autowired
    private DocumentService docDao;
    @Autowired
    private FederationService fedDao;
    @Autowired
    private TagCloudService tagCloud;
    private static final Logger log = Logger.getLogger(StatisticsController.class);

    @RequestMapping("/")
    public String statistics(Model model) {
        model.addAttribute("totalObj", docDao.getSize());
        List<Repositorio> repList = repDao.getAll();
        model.addAttribute("repositorios", repList);
        List<SubFederacao> fedList = fedDao.getAll();
        model.addAttribute("federacoes", fedList);

        Map<String, Integer> termos = tagCloud.getTagCloud();
        model.addAttribute("termosTagCloud", termos);
        return "admin/statistics";
    }

    @RequestMapping(value = "/deletetag/{tag}", method = RequestMethod.POST)
    @ResponseBody
    public Message deleteTag(@PathVariable("tag") String tag, RedirectAttributes redirectAttributes) {
        try {
            tagCloud.delete(tag);
            return new Message(Message.SUCCESS, "Termo: '" + tag + "' removido com sucesso da tag cloud");
        } catch (Exception e) {
            log.error("Erro ao excluir a tag cloud: '" + tag + "'", e);
            return new Message(Message.ERROR, "Erro ao excluir o termo: '" + tag + "' da tag cloud");
        }
    }

    @RequestMapping(value = "/deletealltags", method = RequestMethod.POST)
    @ResponseBody
    public Message deleteAllTags(RedirectAttributes redirectAttributes) {
        try {
            tagCloud.clear();
            return new Message(Message.SUCCESS, "Todos os termos da tag cloud foram removidos com sucesso");
        } catch (Exception e) {
            log.error("Erro ao excluir todos os termos da tag cloud.", e);
            return new Message(Message.ERROR, "Erro ao excluir todos os termos da tag cloud");
        }
    }
}
