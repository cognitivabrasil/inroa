/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitivabrasil.feb.spring.controllers;

import com.cognitivabrasil.feb.data.entities.Repositorio;
import com.cognitivabrasil.feb.data.services.MetadataRecordService;
import com.cognitivabrasil.feb.data.services.RepositoryService;
import com.cognitivabrasil.feb.robo.atualiza.Repositorios;
import com.cognitivabrasil.feb.spring.validador.RepositorioValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for the repositories.
 *
 * @author Marcos Nunes <marcosn@gmail.com>
 * @author Paulo Schreiner <paulo@jorjao81.com>
 */
@Controller("repositories")
@RequestMapping("/admin/repositories/*")
public final class RepositoriesController {

    @Autowired
    private RepositoryService repDao;
    @Autowired
    private MetadataRecordService padraoDao;
    private final RepositorioValidator repValidator;
    @Autowired
    private Repositorios atualizadorRep;

    private static final Logger log = Logger.getLogger(RepositoriesController.class);

    public RepositoriesController() {
        repValidator = new RepositorioValidator();
    }

    /**
     * Shows the details of the repository.
     *
     * @param id the repository id
     */
    @RequestMapping("/{id}")
    public String show(@PathVariable Integer id,
            Model model,
            @RequestParam(required = false, value = "r") boolean recarregar) {
        Repositorio rep = repDao.get(id);
        Integer size = repDao.size(rep);
        model.addAttribute("rep", rep);
        model.addAttribute("repId", id);
        model.addAttribute("repSize", size);
        model.addAttribute("recarregar", recarregar);
        
        return "admin/repositories/show";
    }

    /**
     * Shows the form for creation of a new repository.
     */
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newShow(Model model) {

        //TODO: alterar o jsp para coletar o padrao e o tipo do mapeamento atraves do repModel   
        Repositorio rep = new Repositorio();
        rep.setUrl("http://");
        model.addAttribute("repModel", rep);
        model.addAttribute("padraoMetadadosDAO", padraoDao);
        return "admin/repositories/new";
    }

    /**
     * Actually saves the repository.
     *
     * @param rep the rep
     * @return to the admin page in case of success, to repository creation page
     * otherwise
     */
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String newDo(
            @ModelAttribute("repModel") Repositorio rep,
            BindingResult result,
            Model model) {

        repValidator.validate(rep, result);
        if (result.hasErrors()) {
            model.addAttribute("repModel", rep);
            model.addAttribute("padraoMetadadosDAO", padraoDao);
            model.addAttribute("padraoSelecionado", rep.getPadraoMetadados().getId());
            return "admin/repositories/new";
        } else {
            if (repDao.get(rep.getName()) != null) { //se retornar algo é porque já existe um atualizadorRep com esse nome
                result.rejectValue("nome", "invalid.nome", "Já existe um repositório com esse nome."); //nao esta dentro da classe validator pq só executa isso quando for um atualizadorRep novo, quando editar não.

                model.addAttribute("mapSelecionado", rep.getMapeamento().getId());
                model.addAttribute("repModel", rep);
                model.addAttribute("padraoMetadadosDAO", padraoDao);
                model.addAttribute("padraoSelecionado", rep.getPadraoMetadados().getId());
                return "admin/repositories/new";
            } else { //se retornar null é porque nao tem nenhum atualizadorRep com esse nome
                repDao.save(rep); //salva o novo atualizadorRep return
                return "redirect:/admin/fechaRecarrega";
            }
        }
    }

    /**
     * Shows the edit page of the repository.
     *
     * @param id the repository id
     */
    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String editShow(
            @PathVariable Integer id,
            Model model) {

        model.addAttribute("repModel", repDao.get(id));
        model.addAttribute("padraoMetadadosDAO", padraoDao);
        model.addAttribute("idRep", id);
        return "admin/repositories/edit";
    }

    /**
     * Actually performs the change in the repository.
     *
     * @param rep the repository, as changed by the form
     * @param id the id of the repository
     * @return to admin page in case of success, to edit page otherwise
     */
    @RequestMapping(value = "/{id}/edit", method = RequestMethod.POST)
    public String editDo(
            @ModelAttribute("repModel") Repositorio rep,
            @PathVariable Integer id,
            BindingResult result,
            Model model) {

        repValidator.validate(rep, result);
        if (result.hasErrors()) {
            model.addAttribute("repModel", rep);
            model.addAttribute("padraoMetadadosDAO", padraoDao);
            model.addAttribute("idRep", id);
            return "admin/repositories/edit";
        } else {
            repDao.updateNotBlank(rep);
            return "redirect:/admin/repositories/" + id + "?r=true";
        }
    }

    /**
     * Ajax method to update (harvest new items) a repository.
     *
     * @param id the repository id
     * @param apagar if true, delete everything and starts from sratch, if false
     * harvests only new items
     * @return "1" in case of success, error string otherwise
     */
    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    @ResponseBody
    public String update(
            @PathVariable Integer id,
            @RequestParam boolean apagar) {

        try {
            atualizadorRep.atualizaFerramentaAdm(id, apagar);
            return "1";
        } catch (Exception e) {
            return "Ocorreu um erro ao atualizar. Exception: " + e.toString();
        }
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    public @ResponseBody
    String delete(@PathVariable("id") Integer id, Model model) {
        Repositorio obj = repDao.get(id);
        String name = obj.getName();
        log.info("Deletando o repositório: " + name);
        repDao.delete(obj);
        log.info("Repositório "+name+" deletado com sucesso.");
        return "ok";
    }

}
