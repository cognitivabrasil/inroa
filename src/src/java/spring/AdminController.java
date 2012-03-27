/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spring;

import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import modelos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import spring.validador.SubFederacaoValidador;

/**
 * Controller para ferramenta administrativa
 *
 * @author Marcos Nunes
 */
@Controller("admin")
@RequestMapping("/admin/*")
public final class AdminController {

    @Autowired
    private RepositoryDAO repDao;
    @Autowired
    private SubFederacaoDAO subDao;
    @Autowired
    private PadraoMetadadosDAO padraoDao;
    private SubFederacaoValidador subFedValidador = new SubFederacaoValidador();

    public AdminController() {
    }

    /**
     * Fallback: caso a URL não dê match em nenhum metodos, bate nesse
     *
     * @return Retorna o nove do view que foi passado na URL
     */
    @RequestMapping("{viewName}")
    public String fallback(@PathVariable String viewName, Model model) {
        model.addAttribute("repDAO", repDao);
        model.addAttribute("subDAO", subDao);
        model.addAttribute("padraoMetadadosDAO", padraoDao);
        System.out.println("request for: " + viewName);
        return viewName;
    }

    @RequestMapping("/")
    public String admin(Model model) {
        model.addAttribute("repDAO", repDao);
        model.addAttribute("subDAO", subDao);
        model.addAttribute("padraoMetadadosDAO", padraoDao);
        return "admin/adm";
    }

    @RequestMapping("/exibeRepositorios")
    public String exibeRep(Model model) {
        model.addAttribute("repDAO", repDao);
        return "admin/exibeRepositorios";
    }

    @RequestMapping("/cadastraRepositorio")
    public String cadastraRep(Model model) {

        //TODO: alterar o jsp para coletar o padrao e o tipo do mapeamento atraves do repModel   
        model.addAttribute("repModel", new Repositorio());
        model.addAttribute("padraoMetadadosDAO", padraoDao);
        return "admin/cadastraRepositorio";
    }

    @RequestMapping("/salvarNovoRepositorio")
    public String salvaNovoRep(
            @ModelAttribute("repModel") Repositorio rep,
            Model model) {
//TODO: testar se os campos foram preenchidos

        if (repDao.get(rep.getNome()) == null) {
            model.addAttribute("erro", "Já existe um repositório cadastrado com esse nome!");
            return "admin/cadastraRepositorio";
        } else {
            repDao.save(rep); //salva o novo repositorio
            return "redirect:admin/fechaRecarrega";
        }

    }

    @RequestMapping("/editarRepositorio")
    public String editaRep(
            @RequestParam(value = "id", required = true) int id,
            Model model) {

        //TODO: alterar o jsp para coletar o padrao e o tipo do mapeamento atraves do repModel   
        model.addAttribute("repModel", repDao.get(id));
        model.addAttribute("padraoMetadadosDAO", padraoDao);
        return "admin/editarRepositorio";
    }

    @RequestMapping("/salvarRepositorio")
    public String salvaRep(
            @ModelAttribute("repModel") Repositorio rep,
            @RequestParam(value = "id", required = true) int id,
            BindingResult result,
            SessionStatus status,
            Model model) {
//TODO: testar se os campos foram preenchidos
        repDao.save(rep);

        return "redirect:admin/exibeRepositorios?id=" + id;
    }

    @RequestMapping("/removerRepositorio")
    public String apagaRep(
            @RequestParam(value = "submitted", required = false) boolean submitted,
            @RequestParam(value = "id", required = true) int id,
            Model model) {
        if (submitted) {
            Repositorio rep = new Repositorio();
            rep.setId(id);
            repDao.delete(rep);
            return "redirect:admin/fechaRecarrega";
        } else {

            model.addAttribute("repDAO", repDao);
            return "admin/removerRepositorio";
        }
    }

    @RequestMapping("/cadastraFederacao")
    public String cadastraFed(Model model) {
        SubFederacao subFed = new SubFederacao();
        model.addAttribute("subDAO", subFed);
        return "admin/cadastraFederacao";
    }

    @RequestMapping("/salvarNovaFederacao")
    public String salvaFed(
            @ModelAttribute("subDAO") SubFederacao subfed,
            Model model, BindingResult result) {
        subFedValidador.validate(subfed, result);
        if (result.hasErrors()) {
            model.addAttribute("subDAO", subfed);
            return "admin/cadastraFederacao";
        } else {

            if (subDao.get(subfed.getNome()) == null) {
                model.addAttribute("erro", "Já existe um federação cadastrada com esse nome!");
                return "admin/cadastraFederacao";
            } else {
                subDao.save(subfed); //Grava a subfederacao modificada no formulario
                return "redirect:admin/fechaRecarrega";
            }
        }
    }

    @RequestMapping("/exibeFederacao")
    public String exibeFed(@RequestParam(value = "id", required = true) int id, Model model) {
        String nome = (new ArrayList<RepositorioSubFed>(subDao.get(id).getRepositorios())).get(0).getNome();
        
        model.addAttribute("nomeRepositorio", nome);
        model.addAttribute("subDAO", subDao);
        return "admin/exibeFederacao";
    }

    @RequestMapping("/editarFederacao")
    public String editaFed(
            @RequestParam(value = "id", required = true) int id,
            Model model) {
        model.addAttribute("subDAO", subDao.get(id));
        return "admin/editarFederacao";
    }

    @RequestMapping("/salvarFederacao")
    public String salvaFed(
            @RequestParam(value = "id", required = true) int id,
            @ModelAttribute("subDAO") SubFederacao subfed,
            Model model,
            BindingResult result) {

        subFedValidador.validate(subfed, result);
        if (result.hasErrors()) {
            model.addAttribute("subDAO", subfed);
            return "admin/editarFederacao";
        } else {

            subDao.save(subfed); //Grava a subfederacao modificada no formulario

            model.addAttribute("subDAO", subDao);
            return "redirect:admin/exibeFederacao?id=" + id;
        }
    }

    @RequestMapping("/removerFederacao")
    public String apagaFed(
            @RequestParam(value = "submitted", required = false) boolean submitted,
            @RequestParam(value = "id", required = true) int id,
            Model model) {
        if (submitted) {
            SubFederacao subFed = new SubFederacao();
            subFed.setId(id);
            subDao.delete(subFed);
            return "redirect:admin/fechaRecarrega";
        } else {

            model.addAttribute("subDAO", subDao);
            return "admin/removerFederacao";
        }
    }

    @RequestMapping(value = "/addPadrao", method = RequestMethod.GET)
    public String addPadrao(Model model) {
        PadraoMetadados padrao = new PadraoMetadados();
        model.addAttribute("padrao", padrao);
        return "admin/addPadrao";
    }

    @RequestMapping("/testeForm")
    public String teste(
            @RequestParam(value = "submitted", required = false) boolean submitted,
            @ModelAttribute("subDAO") SubFederacao subfed,
            Model model) {
        if (!submitted) {
//            SubFederacao fed = new SubFederacao();
//            fed.setNome("marcos");
            model.addAttribute("subDAO", subDao.get(9));

            return "admin/testeForm";

        } else {
            Teste.gravaSubFed(subfed);
            return "redirect:index";
        }

    }

    @RequestMapping("/sair")
    public String sair(Model model, HttpSession session) {
        session.removeAttribute("usuario");
        return "redirect:/";
    }
}
