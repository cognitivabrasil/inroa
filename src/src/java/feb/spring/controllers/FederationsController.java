/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feb.spring.controllers;

import feb.data.daos.SubFederacaoHibernateDAO;
import feb.data.entities.SubFederacao;
import feb.data.interfaces.DocumentosDAO;
import feb.data.interfaces.SubFederacaoDAO;
import feb.exceptions.FederationException;
import feb.ferramentaBusca.indexador.Indexador;
import feb.robo.atualiza.SubFederacaoOAI;
import feb.spring.validador.SubFederacaoValidador;
import java.net.ConnectException;
import javax.servlet.ServletContext;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
public final class FederationsController extends AbstractDeletable<SubFederacao, SubFederacaoHibernateDAO> {

    @Autowired
    private SubFederacaoDAO subDao;
    @Autowired
    private DocumentosDAO docDao;
    @Autowired
    private Indexador indexar;
    @Autowired
    ServletContext servletContext;
    @Autowired SubFederacaoOAI subFedOAI;
    
    private SubFederacaoValidador subFedValidador = new SubFederacaoValidador();
    Logger log = Logger.getLogger(FederationsController.class);


    public FederationsController() {
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String exibeFed(@PathVariable("id") Integer id, Model model,
    @RequestParam(required=false, value="r") boolean recarregar) {
        model.addAttribute("federation", subDao.get(id));
        model.addAttribute("recarregar",recarregar);
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
                return "redirect:/admin/federations/" + id+"?r=true";
            }
        } catch (Exception e) {
            model.addAttribute("erro", "Ocorreu um erro. Exception: " + e);
            model.addAttribute("federation", subfed);
            return "admin/federations/edit";
        }
    }

    @RequestMapping(value="/{id}/update", method = RequestMethod.POST)
    public @ResponseBody
    String atualizaFedAjax(@PathVariable("id") Integer id,
            @RequestParam boolean apagar) {
        log.info("Solicitacao de atualizacao pela Ferramenta Administrativa...");
        Integer initNumberDocs = docDao.getSizeWithDeleted();
        try {
            if (id > 0) {
                subFedOAI.atualizaSubfedAdm(subDao.get(id), indexar, apagar);
            } else {
                subFedOAI.atualizaSubfedAdm(subDao.getAll(), indexar, apagar);
            }

            return "1";
        } catch (ConnectException c) {
            log.error("Erro ao coletar o xml por OAI-PMH", c);
            return "Erro ao coletar o xml por OAI-PMH. " + c.toString();
        } catch(FederationException f){
            log.error("Ocorreu algum erro atualizar.",f);
            return f.toString();
        } catch (Exception e) {
            log.error("Erro ao atualizar uma federação",e);
            return "Ocorreu um erro ao atualizar. Exception: " + e.toString();
        }finally{
            Integer finalNumberDocs = docDao.getSizeWithDeleted();
            if(finalNumberDocs != initNumberDocs){
                log.info("Foram atualizados: "+(finalNumberDocs-initNumberDocs) +" documentos");
                indexar.populateR1();
            }
        }
    }

    @Override
    public SubFederacaoHibernateDAO getDAO() {
        return (SubFederacaoHibernateDAO) subDao;
    }
    
}
