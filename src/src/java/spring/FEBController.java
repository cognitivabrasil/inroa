/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spring;

import ferramentaBusca.Recuperador;
import javax.servlet.http.HttpSession;
import modelos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import spring.validador.BuscaValidator;

/**
 * Controller geral para o FEB
 *
 * @author Paulo Schreiner <paulo@jorjao81.com>
 */
@Controller("feb")
public final class FEBController {

    @Autowired
    private UsuarioDAO userDao;
    @Autowired
    private RepositoryDAO repDao;
    @Autowired
    private SubFederacaoDAO subDao;
    private BuscaValidator buscaValidator;
    
    @Autowired
    private PadraoMetadadosDAO padraoDao;

    public FEBController() {
        buscaValidator = new BuscaValidator();
    }

    @RequestMapping("/")
    public String inicio(Model model) {
        return index(model);
    }
    
    @RequestMapping("/index.*")
    public String indexJSP(Model model) {
        return index(model);
    }

    @RequestMapping("/index")
    public String index(Model model) {
        model.addAttribute("buscaModel", new Busca());
        return "index";
    }

    @RequestMapping("/index2")
    public String index2(Model model) {

        model.addAttribute("repDAO", repDao);
        model.addAttribute("subDAO", subDao);
        return "index2";
    }

    @RequestMapping("/consulta")
    public String consulta(
            @ModelAttribute("buscaModel") Busca consulta,
            BindingResult result,
            Model model) {

        buscaValidator.validate(consulta, result);
        if (result.hasErrors()) {
            model.addAttribute("BuscaModel", consulta);
            return "index";
        } else {
            return "consulta";
        }
    }
    
    @RequestMapping("/consultaAvancada")
    public String consultaAvancada(
            @ModelAttribute("buscaModel") Busca consulta,
            BindingResult result,
            Model model) {
        model.addAttribute("item", "--DEUU");
        buscaValidator.validate(consulta, result);
        if (result.hasErrors()) {
            model.addAttribute("BuscaModel", consulta);
            model.addAttribute("repDAO", repDao);
            model.addAttribute("subDAO", subDao);
            return "index2";
        } else {
                //Recuperador rec = new Recuperador();
                //rec.busca(consulta);
            return "consulta";
        }
    }

    /**
     * Método para realizar o login.
     *
     * @param login Passado por HTTP
     * @param password Passado por HTTP
     * @return Redirect para adm caso autentique, permanece nesta página com uma
     * mensagem de erro caso contrário
     */
    @RequestMapping("/login")
    public String logando(
            @RequestParam(value = "login", required = false) String login,
            @RequestParam(value = "senha", required = false) String password,
            HttpSession session, Model model) {

        if (userDao.authenticate(login, password) != null) {

            session.setAttribute("usuario", login); //armazena na sessao o login
            session.setMaxInactiveInterval(900); //seta o tempo de validade da session
            return "redirect:/admin/";

        } else {
            if (login != null) {
                model.addAttribute("erro", "Usuário ou senha incorretos!");
            }
            return "login";
        }
    }
}
