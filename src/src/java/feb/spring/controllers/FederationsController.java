/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feb.spring.controllers;

import javax.servlet.ServletContext;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import feb.data.entities.SubFederacao;
import feb.data.interfaces.MapeamentoDAO;
import feb.data.interfaces.PadraoMetadadosDAO;
import feb.data.interfaces.RepositoryDAO;
import feb.data.interfaces.SubFederacaoDAO;
import feb.data.interfaces.UsuarioDAO;
import feb.robo.atualiza.SubFederacaoOAI;
import feb.spring.validador.SubFederacaoValidador;

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
    private RepositoryDAO repDao;
    @Autowired
    private SubFederacaoDAO subDao;
    @Autowired
    private PadraoMetadadosDAO padraoDao;
    @Autowired
    private MapeamentoDAO mapDao;
    private SubFederacaoValidador subFedValidador = new SubFederacaoValidador();
    @Autowired
    ServletContext servletContext;
    @Autowired
    private UsuarioDAO userDao;
    Logger  log = Logger.getLogger(FederationsController.class);


    public FederationsController() {
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String exibeFed(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("federation", subDao.get(id));
        return "admin/federations/show";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String cadastraFed(Model model) {
        SubFederacao subFed = new SubFederacao();
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
            @PathVariable("id") Integer id,
            Model model) {
        model.addAttribute("federation", subDao.get(id));
        return "admin/federations/edit";
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.POST)
    public String salvaFed(
            @RequestParam Integer id,
            @ModelAttribute("federation") SubFederacao subfed,
            Model model,
            BindingResult result) {
        try {
            subFedValidador.validate(subfed, result);
            if (result.hasErrors()) {
                model.addAttribute("federation", subfed);
                return "admin/federations/edit";
            } else {
                subDao.updateNotBlank(subfed); //Grava a subfederacao modificada no formulario
                return "redirect:/admin/federations/" + id;
            }
        } catch (Exception e) {
            model.addAttribute("erro", "Ocorreu um erro. Exception: " + e);
            model.addAttribute("federation", subfed);
            return "admin/federations/edit";
        }
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String apagaFed(
            @PathVariable("id") Integer id,
            Model model) {


            model.addAttribute("federation", subDao.get(id));
            return "admin/federations/confirmDelete";
     
    }
    
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    public String deleteDo(
            @PathVariable("id") Integer id,
            Model model) {
            SubFederacao subFed = new SubFederacao();
            subFed.setId(id);
            subDao.delete(subFed);
            return "redirect:/admin/fechaRecarrega";
    }

   


    @RequestMapping(value="/{id}/update", method = RequestMethod.POST)
    public @ResponseBody
    String atualizaFedAjax(@PathVariable("id") Integer id,
            @RequestParam boolean apagar) {
        log.info("FEB: Solicitacao de atualizacao pela Ferramenta Administrativa...");
        SubFederacaoOAI subFed = new SubFederacaoOAI();
        
        try {
            if (id > 0) {
                subFed.atualizaSubfedAdm(subDao.get(id), apagar);
            } else {
                for (SubFederacao subFederacao : subDao.getAll()) {
                    subFed.atualizaSubfedAdm(subFederacao, apagar);
                }
            }

            return "1";
        } catch (Exception e) {
            log.error("Erro ao atualizar uma subfederação",e);
            return "Ocorreu um erro ao atualizar. Exception: " + e.toString();
        }
    }
    //------FIM FUNCOES PARA AJAX------------
}
