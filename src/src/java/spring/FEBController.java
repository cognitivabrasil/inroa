/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spring;

import javax.servlet.http.HttpSession;
import modelos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Controller geral para o FEB
 *
 * TODO: Separar em controller para busca e para Admin
 *
 * @author Paulo Schreiner <paulo@jorjao81.com>
 */
@Controller("feb")

public final class FEBController {

    @Autowired
    private UsuarioDAO userDao;

    public FEBController() {
    }

    @RequestMapping("/")
    public String index(Model model) {

        return "index";
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
