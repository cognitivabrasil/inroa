/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feb.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import feb.data.entities.Repositorio;
import feb.data.interfaces.PadraoMetadadosDAO;
import feb.data.interfaces.RepositoryDAO;
import feb.robo.atualiza.Repositorios;
import feb.spring.validador.RepositorioValidator;

/**
 * Controller for the repositories.
 *
 * @author Marcos Nunes <marcosn@gmail.com>
 * @author Paulo Schreiner <paulo@jorjao81.com>
 */
@Controller("repositories")
@RequestMapping("/admin/repositories/*")
public final class RepositoriesController {

    @Autowired private RepositoryDAO repDao;
    @Autowired
    private PadraoMetadadosDAO padraoDao;
    private RepositorioValidator repValidator = new RepositorioValidator();
   
    /**
     * Shows the details of the repository.
     *
     * @param id the repository id
     */
    @RequestMapping("/{id}")
    public String show(@PathVariable Integer id,
            Model model) {
        model.addAttribute("rep", repDao.get(id));
        model.addAttribute("repId", id);
        return "admin/repositories/show";
    }

    /**
     * Shows the form for creation of a new repository.
     */
    @RequestMapping(value = "/new", method=RequestMethod.GET)
    public String newShow(Model model) {

        //TODO: alterar o jsp para coletar o padrao e o tipo do mapeamento atraves do repModel   
        model.addAttribute("repModel", new Repositorio());
        model.addAttribute("padraoMetadadosDAO", padraoDao);
        return "admin/repositories/new";
    }

    /**
     * Actually saves the repository.
     *
     * @param rep the rep
     * @return to the admin page in case of success, to repository
     * creation page otherwise
     */
    @RequestMapping(value="/new", method=RequestMethod.POST)
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
            if (repDao.get(rep.getName()) != null) { //se retornar algo é porque já existe um repositorio com esse nome
                result.rejectValue("nome", "invalid.nome", "Já existe um repositório com esse nome."); //nao esta dentro da classe validator pq só executa isso quando for um repositorio novo, quando editar não.

                model.addAttribute("mapSelecionado", rep.getMapeamento().getId());
                model.addAttribute("repModel", rep);
                model.addAttribute("padraoMetadadosDAO", padraoDao);
                model.addAttribute("padraoSelecionado", rep.getPadraoMetadados().getId());
                return "admin/repositories/new";
            } else { //se retornar null é porque nao tem nenhum repositorio com esse nome
                repDao.save(rep); //salva o novo repositorio return
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
            return "redirect:/admin/repositories/" + id;
        }
    }
    
    /**
     * Ajax method to update (harvest new items) a repository.
     *
     * @param id the repository id
     * @param apagar if true, delete everything and starts from sratch, if 
     * false harvests only new items
     * @return "1" in case of success, error string otherwise
     */
    @RequestMapping(value="/{id}/update", method = RequestMethod.POST)
    @ResponseBody public String update(
    		@PathVariable Integer id,
            @RequestParam boolean apagar) {

        Repositorios repositorio = new Repositorios();
        try {
            repositorio.atualizaFerramentaAdm(id, apagar);
            return "1";
        } catch (Exception e) {
            return "Ocorreu um erro ao atualizar o repositorio. Exception: " + e.toString();
        }
    }


    /**
     * Deletes a repository.
     *
     * @param id the repository id
     */
    @RequestMapping(value="/{id}/delete", method = RequestMethod.POST)
    public String deleteDo(
    		@PathVariable Integer id,
    		Model model) {
            Repositorio rep = repDao.get(id);
            repDao.delete(rep);
            return "redirect:/admin/fechaRecarrega";
    }
    
    /**
     * Shows a confirmation popup for the deletion.
     *
     * @param id the repository id
     */
    @RequestMapping(value="/{id}/delete", method = RequestMethod.GET)
    public String deleteShow(
    		@PathVariable Integer id,
            Model model) {
            model.addAttribute("rep", repDao.get(id));
            return "admin/repositories/confirmDelete";
    }

  }
