/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spring;

import ferramentaBusca.IndexadorBusca;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import modelos.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import postgres.SingletonConfig;
import spring.validador.PadraoValidator;
import spring.validador.RepositorioValidator;
import spring.validador.InfoBDValidator;
import spring.validador.SubFederacaoValidador;
import robo.atualiza.Repositorios;
import robo.atualiza.SubFederacaoOAI;

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
    @Autowired
    ServletContext servletContext;
    @Autowired
    private UsuarioDAO userDao;

    public AdminController() {
    }

    @RequestMapping("/")
    public String admin(Model model) {
        model.addAttribute("repositories", repDao.getAll());
        model.addAttribute("subDAO", subDao);
        model.addAttribute("padraoMetadadosDAO", padraoDao);
        model.addAttribute("users", userDao.getAll());
        model.addAttribute("versao", "2.5-alpha");
        return "admin/adm";
    }

    @RequestMapping("/adm")
    public String admin2(Model model) {
        return admin(model);
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

    @RequestMapping("/salvaSenhaBD")
    public String salvaSenhaDB(
            @ModelAttribute("conf") SingletonConfig conf,
            @RequestParam(value = "confirmacaoSenhaBD", required = false) String confSenha,
            BindingResult result,
            Model model) {
        boolean senhaDiferentes = false;
        InfoBDValidator infoBDVal = new InfoBDValidator();
        infoBDVal.validate(conf, result);
        try {
            if (!conf.descriptografa(conf.getSenhaCriptografada()).equals(confSenha)) { //testa se as senhas informadas sao iguais (confirmacao da senha).
                model.addAttribute("erro", "Senhas não correspondem.");
                senhaDiferentes = true;
            }

            if (result.hasErrors() || senhaDiferentes) {
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
        } catch (Exception e) {
            model.addAttribute("conf", conf);
            model.addAttribute("erro", "Ocorreu um erro. Exception: " + e.toString());
            return "admin/alterarSenhaBD";
        }
    }

    @RequestMapping("confirmaRecalcularIndice")
    public String ConfirmaRecalcularIndice(Model model) {

        return "admin/recalcularIndice";
    }

    @RequestMapping("efetuaRecalculoIndice")
    public String recalcularIndice(Model model) {
        IndexadorBusca run = new IndexadorBusca();
        run.indexarTodosRepositorios();
        model.addAttribute("fim", "Índice recalculado com sucesso!");
        return "admin/recalcularIndice";
    }

    @RequestMapping("alterarSenhaAdm")
    public String alterarSenhaUser(Model model, HttpSession session) {
        String user = (String) session.getAttribute("usuario");
        model.addAttribute("login", user);
        return "admin/alterarSenhaAdm";
    }

    @RequestMapping("gravaSenha")
    public String gravaSenhaUser(
            @RequestParam String senhaAtual,
            @RequestParam String novaSenha,
            @RequestParam String confimaSenha,
            Model model,
            HttpSession session) {
        String user = (String) session.getAttribute("usuario"); //pega o nome do usuario da sessao

        if (!novaSenha.equals(confimaSenha)) {//testa se a nova senha e a confirmacao estao iguais
            model.addAttribute("erroSenhaConf", "Senhas não correspondem.");
            model.addAttribute("login", user);
            return "admin/alterarSenhaAdm";
        } else {
            Usuario userModel = userDao.authenticate(user, senhaAtual);
            if (userModel == null) {
                model.addAttribute("erroSenha", "A senha fornecida estava incorreta.");
                model.addAttribute("login", user);
                return "admin/alterarSenhaAdm";
            } else {
                try {
                    userModel.setPassword(novaSenha);
                    userDao.save(userModel);
                    //TODO: Quando passar para o spring security modificar a forma de deslogado o usuario aqui
                    session.removeAttribute("usuario");//remove o usuario da sessao para que tenha que digitar a nova senha
                    return "redirect:fechaRecarrega";
                } catch (Exception e) {
                    model.addAttribute("erro", "Desculpe, não foi possível alterar a senha. Exception: " + e.toString());
                    model.addAttribute("login", user);
                    return "admin/alterarSenhaAdm";
                }
            }
        }
    }
    
    /**
     * Fecha a pop-up e recarrega a janela principal
     * @return admin/fechaRecarrega.jsp
     */
    @RequestMapping("fechaRecarrega")
    public String fechaRecarrega(){
        return "admin/fechaRecarrega";
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
        try {
            padrao.setId(id);
            padraoDao.delete(padrao);
            return "1";
        } catch (Exception e) {
            return "Ocorreu um erro ao excluir. Exception:" + e.toString();
        }
    }

    //------FIM FUNCOES PARA AJAX------------
}
