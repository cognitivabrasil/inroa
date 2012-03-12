/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spring;

import javax.servlet.http.HttpSession;
import modelos.PadraoMetadadosDAO;
import modelos.RepositoryDAO;
import modelos.SubFederacaoDAO;
import modelos.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller geral para o FEB
 * 
 * TODO: Separar em controller para busca e para Admin
 * 
 * @author Paulo Schreiner <paulo@jorjao81.com>
 */
@Controller("feb")
@RequestMapping("/")
public final class FEBController {

    @Autowired
    private RepositoryDAO repDao;
    @Autowired
    private SubFederacaoDAO subDao;
    @Autowired
    private PadraoMetadadosDAO padraoDao;
    @Autowired
    private UsuarioDAO userDao;

    public FEBController() {
    }

    @RequestMapping("/")
    public String index(Model model) {

        return "index";
    }

    /**
     * Fallback: caso a URL não dê match em nenhum metodos, bate nesse
     * @return Retorna o nove do view que foi passado na URL 
     */
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
   
   @RequestMapping("/exibeRepositorios")
    public String exibeRep(Model model) {
        model.addAttribute("repDAO", repDao);
        return "exibeRepositorios";
    }

    /**
     * Método para realizar o login.
     * 
     * @param login Passado por HTTP
     * @param password Passado por HTTP
     * @return Redirect para adm caso autentique, permanece nesta página com uma mensagem de erro
     * caso contrário
     */
    @RequestMapping("/login")
    public String logando(
            @RequestParam(value="login", required=false) String login,
            @RequestParam(value="senha", required=false) String password,
            HttpSession session, Model model) {

        if (userDao.authenticate(login, password) != null) {

            session.setAttribute("usuario", login); //armazena na sessao o login
            session.setMaxInactiveInterval(900); //seta o tempo de validade da session
            return "redirect:adm";

        } else {
            if(login != null) {
            model.addAttribute("erro", true);
            }
            return "login";
        }
    }
}
