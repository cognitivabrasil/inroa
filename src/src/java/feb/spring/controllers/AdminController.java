package feb.spring.controllers;

import feb.data.entities.PadraoMetadados;
import feb.data.entities.Repositorio;
import feb.data.entities.Usuario;
import feb.data.interfaces.MapeamentoDAO;
import feb.data.interfaces.PadraoMetadadosDAO;
import feb.data.interfaces.RepositoryDAO;
import feb.data.interfaces.SubFederacaoDAO;
import feb.data.interfaces.UsuarioDAO;
import feb.ferramentaBusca.indexador.Indexador;
import feb.spring.FebConfig;
import feb.spring.ServerInfo;
import feb.spring.validador.InfoBDValidator;
import feb.spring.validador.PadraoValidator;
import feb.spring.validador.SubFederacaoValidador;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
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
    @Autowired
    private Indexador indexador;
    @Autowired
    private ServerInfo serverInfo;
    @Autowired
    private FebConfig conf;
    static Logger log = Logger.getLogger(AdminController.class);

    public AdminController() {
    }

    @RequestMapping("/")
    public String admin(Model model) {
        model.addAttribute("repositories", repDao.getAll());
        model.addAttribute("mapeamentos", mapDao.getAll());
        model.addAttribute("subDAO", subDao);
        model.addAttribute("padraoMetadadosDAO", padraoDao);
        model.addAttribute("users", userDao.getAll());
        model.addAttribute("versao", serverInfo.getFullVersion());
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

    @RequestMapping(value = "/alterDB", method = RequestMethod.GET)
    public String showAlterDB(Model model) {
        model.addAttribute("conf", conf);
        return "admin/alterDB";
    }

    @RequestMapping(value = "/alterDB", method = RequestMethod.POST)
    public String saveDB(
            @ModelAttribute("conf") FebConfig febConf,
            @RequestParam(required = false) String passConf,
            BindingResult result,
            Model model) {
        boolean senhaDiferentes = false;
        InfoBDValidator infoBDVal = new InfoBDValidator();
        infoBDVal.validate(febConf, result);
        try {
            if (!febConf.getPassword().equals(passConf)) { //testa se as senhas informadas sao iguais (confirmacao da senha).
                model.addAttribute("erro", "Senhas não correspondem.");
                senhaDiferentes = true;
            }

            if (result.hasErrors() || senhaDiferentes) {
                model.addAttribute("conf", febConf);
                return "admin/alterDB";
            } else {
                try{
                	conf.updateFrom(febConf);
                    conf.save();
                    return "redirect:fechaRecarrega";
                }catch(IOException i){
                    log.error("Erro ao alterar as informações da base de dados.", i);
                    model.addAttribute("conf", febConf);
                    model.addAttribute("erro", "Não foi possível escrever as configurações no arquivo: /etc/feb/feb.properties \n Possivelmente os usuário do tomcat não tem permissão de escrita nesse diretório." );
                    return "admin/alterDB";
                }
            }
        } catch (Exception e) {
            log.error("Erro ao alterar as informações da base de dados.", e);
            model.addAttribute("conf", febConf);
            model.addAttribute("erro", "Ocorreu um erro. Exception: " + e.toString());
            return "admin/alterDB";
        }
    }

    @RequestMapping("confirmaRecalcularIndice")
    public String ConfirmaRecalcularIndice(Model model) {

        return "admin/recalcularIndice";
    }

    @RequestMapping("efetuaRecalculoIndice")
    public String recalcularIndice(Model model) {
        log.info("Recalculando indice - Solicitação feita pela ferramenta administrativa.");
        indexador.indexarTodosRepositorios();
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
            model.addAttribute("errorPassConf", "Senhas não correspondem.");
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
                    //TODO: Quando passar para o feb.spring security modificar a forma de deslogado o usuario aqui
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
     *
     * @return admin/fechaRecarrega.jsp
     */
    @RequestMapping("fechaRecarrega")
    public String fechaRecarrega() {
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