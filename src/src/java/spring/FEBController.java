/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spring;

import modelos.PadraoMetadadosDAO;
import modelos.RepositoryDAO;
import modelos.SubFederacaoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller("feb")
@RequestMapping("/")
public final class FEBController {

    @Autowired
    private RepositoryDAO repDao;
    @Autowired
    private SubFederacaoDAO subDao;
    @Autowired
    private PadraoMetadadosDAO padraoDao;

    public FEBController() {
    }

    @RequestMapping("/")
    public String index(Model model) {

        return "index";
    }

    @RequestMapping("{viewName}")
    public String fallback(@PathVariable String viewName, Model model) {
        model.addAttribute("repDAO", repDao);
        model.addAttribute("subDAO", subDao);
        model.addAttribute("padraoMetadadosDAO", padraoDao);
        System.out.println("Request for " + viewName);
        return viewName;
    }

    @RequestMapping("/adm")
    public String admin(Model model) {
        System.out.println("ADMIN");
        model.addAttribute("repDAO", repDao);
        model.addAttribute("subDAO", subDao);
        model.addAttribute("padraoMetadadosDAO", padraoDao);
        return "adm";
    }
}
