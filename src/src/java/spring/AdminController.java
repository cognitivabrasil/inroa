/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spring;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;
import modelos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import spring.validador.RepositorioValidator;
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
    @Autowired
    private MapeamentoDAO mapDao;
    private SubFederacaoValidador subFedValidador = new SubFederacaoValidador();
    private RepositorioValidator repValidator = new RepositorioValidator();
    
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
        System.out.println("request for in AdminController: " + viewName);
        return "admin/" + viewName;
    }

    @RequestMapping("/")
    public String admin(Model model) {
        model.addAttribute("repDAO", repDao);
        model.addAttribute("subDAO", subDao);
        model.addAttribute("padraoMetadadosDAO", padraoDao);
        return "admin/adm";
    }

    @RequestMapping("/exibeRepositorios")
    public String exibeRep(@RequestParam(value = "id", required = true) int id,
            Model model) {
        model.addAttribute("rep", repDao.get(id));
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
            BindingResult result,
            Model model) {
        
        repValidator.validate(rep, result);
        if (result.hasErrors()) {
            model.addAttribute("repModel", rep);
            model.addAttribute("padraoMetadadosDAO", padraoDao);
            model.addAttribute("padraoSelecionado", rep.getPadraoMetadados().getId());
            return "admin/cadastraRepositorio";
        } else {
            if (repDao.get(rep.getNome()) != null) { //se retornar algo é porque já existe um repositorio com esse nome
                result.rejectValue("nome", "invalid.nome", "Já existe um repositório com esse nome."); //nao esta dentro da classe validator pq só executa isso quando for um repositorio novo, quando editar não.
                
                model.addAttribute("mapSelecionado", rep.getMapeamento().getId());
                model.addAttribute("repModel", rep);
                model.addAttribute("padraoMetadadosDAO", padraoDao);
                model.addAttribute("padraoSelecionado", rep.getPadraoMetadados().getId());
                return "admin/cadastraRepositorio";
            } else { //se retornar null é porque nao tem nenhum repositorio com esse nome
                repDao.save(rep); //salva o novo repositorio return
                return "redirect:fechaRecarrega";
            }
        }
    }

    @RequestMapping("/editarRepositorio")
    public String editaRep(
            @RequestParam(value = "id", required = true) int id,
            Model model) {

        model.addAttribute("repModel", repDao.get(id));
        model.addAttribute("padraoMetadadosDAO", padraoDao);
        return "admin/editarRepositorio";
    }

    @RequestMapping("/salvarRepositorio")
    public String salvaRep(
            @ModelAttribute("repModel") Repositorio rep,
            @RequestParam(value = "id", required = true) int id,
            BindingResult result,
            Model model) {
        repValidator.validate(rep, result);
        if (result.hasErrors()) {
            model.addAttribute("repModel", repDao.get(id));
            model.addAttribute("padraoMetadadosDAO", padraoDao);
            return "admin/editarRepositorio";
        } else {
            //repDao.save(rep);
            return "redirect:/admin/exibeRepositorios?id=" + id;
        }
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
            return "redirect:/fechaRecarrega";
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

            if (subDao.get(subfed.getNome()) != null) {
                model.addAttribute("erro", "Já existe um federação cadastrada com esse nome!");
                return "admin/cadastraFederacao";
            } else {
                subDao.save(subfed); //Grava a subfederacao modificada no formulario
                return "redirect:/fechaRecarrega";
            }
        }
    }

    @RequestMapping("/exibeFederacao")
    public String exibeFed(@RequestParam(value = "id", required = true) int id, Model model) {

        //model.addAttribute("nomeRepositorio", nome);
        model.addAttribute("subFeb", subDao.get(id));
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
            return "redirect:/fechaRecarrega";
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
            return "redirect:index";
        }

    }

    @RequestMapping("/sair")
    public String sair(Model model, HttpSession session) {
        session.removeAttribute("usuario");
        return "redirect:/";
    }

    //------FUNCOES PARA AJAX------------
    @RequestMapping("/mapeamentos/listaMapeamentoPadraoSelecionado")
    public String listaMapSelecionadoAjax(
            @RequestParam(value = "idpadrao", required = true) int idPadrao,
            @RequestParam(value = "mapSelecionado", required = false) int mapSelecionado,
            Model model) {
        if (idPadrao <= 0) {
            model.addAttribute("idZero", true);
        } else {
            model.addAttribute("padraoMet", padraoDao.get(idPadrao));
            if (mapSelecionado > 0) {
                model.addAttribute("mapSelecionado", mapSelecionado);
            } else {
                model.addAttribute("repModel", new Repositorio());
                model.addAttribute("novoRep", true);
            }
        }

        return "admin/mapeamentos/listaMapeamentoPadraoSelecionado";
    }

    @RequestMapping("/testeMarcos")
    public void verificaOAI() {
        System.out.println("entrou");

    }
}
