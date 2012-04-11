/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spring;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import modelos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import postgres.SingletonConfig;
import spring.validador.PadraoValidator;
import spring.validador.RepositorioValidator;
import spring.validador.InfoBDValidator;
import spring.validador.SubFederacaoValidador;

/**
 * Controller para ferramenta administrativa
 *
 * @author Marcos Nunes <marcosn@gmail.com>
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
    @Autowired
    ServletContext servletContext;

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
            model.addAttribute("repModel", rep);
            model.addAttribute("padraoMetadadosDAO", padraoDao);
            return "admin/editarRepositorio";
        } else {
            repDao.save(rep);
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
            return "redirect:fechaRecarrega";
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
                return "redirect:fechaRecarrega";
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
            return "redirect:fechaRecarrega";
        } else {

            model.addAttribute("subDAO", subDao);
            return "admin/removerFederacao";
        }
    }

    @RequestMapping("padraoMetadados/addPadrao")
    public String cadastraPadrao(Model model) {
        model.addAttribute("padraoModel", new PadraoMetadados());
        return "admin/padraoMetadados/addPadrao";
    }

    @RequestMapping("padraoMetadados/salvaPadrao")
    public String salvaPadrao(
            @ModelAttribute("padraoModel") PadraoMetadados padrao,
            BindingResult result,
            Model model) {
        PadraoValidator padraoVal = new PadraoValidator();
        padraoVal.validate(padrao, result);
        if (result.hasErrors()) {
            model.addAttribute("padraoModel", padrao);
            return "admin/padraoMetadados/addPadrao";
        } else {
            if (padraoDao.get(padrao.getNome()) != null) {
                result.rejectValue("nome", "invalid.nome", "Já existe um padrão cadastrado com esse nome.");
                model.addAttribute("padraoModel", padrao);
                return "admin/padraoMetadados/addPadrao";
            }
            padraoDao.save(padrao);
            return "redirect:/admin/fechaRecarrega";
        }
    }

    @RequestMapping("padraoMetadados/editaPadrao")
    public String editaPadrao(
            @RequestParam int id,
            Model model) {
        model.addAttribute("padrao", padraoDao.get(id));

        return "admin/padraoMetadados/editaPadrao";
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

    @RequestMapping("excluirPadrao")
    public @ResponseBody
    String excluiPadrao(@RequestParam int id) {
        PadraoMetadados padrao = new PadraoMetadados();
        padrao.setId(id);
        padraoDao.delete(padrao);
        return "1";
    }

    @RequestMapping("/alterarSenhaBD")
    public String alteraSenhaDB(Model model) {

        // we should NOT connect to the DB here, just load the config
        SingletonConfig.initConfig(servletContext);
        SingletonConfig conf = SingletonConfig.getConfig();
        model.addAttribute("conf", conf);
        return "admin/alterarSenhaBD";
    }

    @RequestMapping("/salvaSenhaBD")
    public String salvaSenhaDB(
            @ModelAttribute("conf") SingletonConfig conf,
            @RequestParam(value = "confirmacaoSenhaBD", required = false) String confSenha,
            BindingResult result,
            Model model) {

        InfoBDValidator infoBDVal = new InfoBDValidator();
        infoBDVal.validate(conf, result);
        if (result.hasErrors()) {
            model.addAttribute("conf", conf);
            return "admin/alterarSenhaBD";
        } else {
            if (conf.criaArquivo()) {
                return "redirect:fechaRecarrega";
            } else {
                model.addAttribute("conf", conf);
                model.addAttribute("erro", "Erro ao alterar os dados.");
                return "admin/alterarSenhaBD";
            }
        }
    }

    @RequestMapping("/testeMarcos")
    public void verificaOAI() {
        System.out.println("entrou");

    }
}
