/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitivabrasil.feb.spring.controllers;

import com.cognitivabrasil.feb.data.entities.RepositorioSubFed;
import com.cognitivabrasil.feb.data.entities.SubFederacao;
import com.cognitivabrasil.feb.data.services.DocumentService;
import com.cognitivabrasil.feb.data.services.FederationService;
import com.cognitivabrasil.feb.exceptions.FederationException;
import com.cognitivabrasil.feb.ferramentaBusca.indexador.Indexador;
import com.cognitivabrasil.feb.robo.atualiza.SubFederacaoOAI;
import com.cognitivabrasil.feb.spring.validador.SubFederacaoValidador;
import java.net.ConnectException;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Controller para ferramenta administrativa
 *
 * @author Marcos Nunes <marcosn@gmail.com>
 * @author Paulo Schreiner <paulo@jorjao81.com>
 */
@Controller("federations")
@RequestMapping("/admin/federations/*")
public final class FederationsController {

    @Autowired
    private FederationService subDao;
    @Autowired
    private DocumentService docDao;
    @Autowired
    private Indexador indexar;
    @Autowired
    SubFederacaoOAI subFedOAI;

    private final SubFederacaoValidador subFedValidador;
    private static final Logger log = Logger.getLogger(FederationsController.class);

    public FederationsController() {
        subFedValidador = new SubFederacaoValidador();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String exibeFed(@PathVariable("id") Integer id, Model model,
            @RequestParam(required = false, value = "r") boolean recarregar) {
        SubFederacao fed = subDao.get(id);
        
        Map<String, Integer> repsSize = new HashMap<>();
        Integer fedSize = 0;
        //list rep whith size
        for(RepositorioSubFed sRep : fed.getRepositorios()){
            Integer sizeRep = docDao.countFromSubRep(sRep);
            fedSize+=sizeRep;
            repsSize.put(sRep.getName(), sizeRep);
        }
        
        model.addAttribute("fedSize", fedSize);
        model.addAttribute("mapRepSize", repsSize);
        model.addAttribute("federation", fed);
        model.addAttribute("recarregar", recarregar);
        
        return "admin/federations/show";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String cadastraFed(Model model) {
        SubFederacao subFed = new SubFederacao();
        subFed.setUrl("http://");
        model.addAttribute("federation", subFed);
        return "admin/federations/new";
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String salvaFed(
            @ModelAttribute("federation") SubFederacao subfed,
            Model model, BindingResult result) {
        subFedValidador.validate(subfed, result);
        if (result.hasErrors()) {
            model.addAttribute("federation", subfed);
            return "admin/federations/new";
        } else {
            try {
                if (subDao.get(subfed.getName()) != null) {
                    model.addAttribute("erro", "Já existe um federação cadastrada com esse nome!");
                    return "admin/federations/new";
                } else {
                    subDao.save(subfed); //Grava a subfederacao modificada no formulario
                    return "redirect:/admin/fechaRecarrega";
                }
            } catch (Exception e) {
                model.addAttribute("erro", "Exception: " + e);
                return "admin/federations/new";
            }
        }
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String editaFed(
            @PathVariable("id") Integer id, Model model) {
        model.addAttribute("federation", subDao.get(id));
        return "admin/federations/edit";
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.POST)
    public String edit(@PathVariable("id") Integer id, SubFederacao subfed, BindingResult result,
            ExtendedModelMap model) {
        try {
            //tive que colocar isso pois nao esta pegando sozinho o id da federacao.
            subfed.setId(id);
            subFedValidador.validate(subfed, result);
            if (result.hasErrors()) {
                model.addAttribute("federation", subfed);
                return "admin/federations/edit";
            } else {
                subDao.updateNotBlank(subfed); //Grava a subfederacao modificada no formulario
                return "redirect:/admin/federations/" + id + "?r=true";
            }
        } catch (Exception e) {
            model.addAttribute("erro", "Ocorreu um erro. Exception: " + e);
            model.addAttribute("federation", subfed);
            return "admin/federations/edit";
        }
    }

    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    public @ResponseBody
    String atualizaFedAjax(@PathVariable("id") Integer id,
            @RequestParam boolean apagar) {
        log.info("Solicitacao de atualizacao pela Ferramenta Administrativa...");
        long initNumberDocs = docDao.getSizeWithDeleted();
        try {
            if (id > 0) {
                subFedOAI.atualizaSubfedAdm(subDao.get(id), apagar);
            } else {
                subFedOAI.atualizaSubfedAdm(subDao.getAll(), apagar);
            }

            return "1";
        } catch (ConnectException c) {
            log.error("Erro ao coletar o xml por OAI-PMH", c);
            return "Erro ao coletar o xml por OAI-PMH. " + c.toString();
        } catch (FederationException f) {
            log.error("Ocorreu algum erro atualizar.", f);
            return f.toString();
        } catch (Exception e) {
            log.error("Erro ao atualizar uma federação", e);
            return "Ocorreu um erro ao atualizar. Exception: " + e.toString();
        } finally {
            long finalNumberDocs = docDao.getSizeWithDeleted();
            if (finalNumberDocs != initNumberDocs) {
                log.info("Foram atualizados: " + (finalNumberDocs - initNumberDocs) + " documentos");
                indexar.populateR1();
            }
        }
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    public @ResponseBody
    String delete(@PathVariable("id") Integer id, Model model) {
        SubFederacao obj = subDao.get(id);
        String fedName = obj.getName();
        log.info("Deletando " + obj.getClass().getName() + ": " + fedName);
        subDao.delete(obj);
        log.info("Federação " + fedName + " deletada com sucesso.");
        return "ok";
    }
}
