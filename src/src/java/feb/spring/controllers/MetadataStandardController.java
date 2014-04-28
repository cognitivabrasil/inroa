/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feb.spring.controllers;

import com.cognitivabrasil.feb.data.entities.PadraoMetadados;
import com.cognitivabrasil.feb.data.services.MetadataRecordService;
import feb.spring.validador.PadraoValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Controller para padrões de metadados
 *
 * @author Marcos Nunes <marcosn@gmail.com>
 */
@Controller("metadataStandard")
@RequestMapping("/admin/metadataStandard/*")
public final class MetadataStandardController{// extends AbstractDeletable<PadraoMetadados, PadraoMetadadosHibernateDAO> {
//TODO: IMPLEMENTAR O METODO DELETE
    @Autowired
    private MetadataRecordService padraoDao;
    Logger log = Logger.getLogger(MetadataStandardController.class);

    public MetadataStandardController() {
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String cadastraPadrao(Model model) {
        model.addAttribute("padraoModel", new PadraoMetadados());
        return "admin/metadataStandard/new";
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String salvaPadrao(
            @ModelAttribute("padraoModel") PadraoMetadados padrao,
            BindingResult result,
            Model model) {
        PadraoValidator padraoVal = new PadraoValidator();
        padraoVal.validate(padrao, result);
        if (result.hasErrors()) {
            model.addAttribute("padraoModel", padrao);
            return "admin/metadataStandard/new";
        } else {
            if (padraoDao.get(padrao.getName()) != null) {
                result.rejectValue("name", "invalid.name", "Já existe um padrão cadastrado com esse nome.");
                model.addAttribute("padraoModel", padrao);
                return "admin/metadataStandard/new";
            }
            padraoDao.save(padrao);
            return "redirect:/admin/mapeamentos/new?padrao=" + padrao.getId();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String exibePadrao(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("padrao", padraoDao.get(id));
        return "admin/metadataStandard/show";
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String editaPadrao(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("padraoModel", padraoDao.get(id));
        return "admin/metadataStandard/new";
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.POST)
    public String salvaPadrao(
            @PathVariable("id") Integer id,
            @ModelAttribute("padraoModel") PadraoMetadados padrao,
            BindingResult result,
            Model model) {
        PadraoValidator padraoVal = new PadraoValidator();
        padraoVal.validate(padrao, result);
        if (result.hasErrors()) {
            model.addAttribute("padraoModel", padrao);
            return "admin/metadataStandard/new";
        } else {
            padraoDao.save(padrao);
            return "redirect:/admin/metadataStandard/" + id;
        }
    }


}
