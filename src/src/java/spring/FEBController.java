/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spring;

import ferramentaBusca.Recuperador;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import modelos.*;
import org.hibernate.SessionFactory;
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
 * @author Marcos Nunes <marcosn@gmail.com>
 */
@Controller("feb")
public final class FEBController {

    @Autowired
    private UsuarioDAO userDao;
    @Autowired
    private RepositoryDAO repDao;
    @Autowired
    private SubFederacaoDAO subDao;
    @Autowired
    private DocumentosDAO docDao;
    private BuscaValidator buscaValidator;

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
        model.addAttribute("buscaModel", new Consulta());
        return "index";
    }

    @RequestMapping("/index2")
    public String index2(Model model) {

        model.addAttribute("repDAO", repDao);
        model.addAttribute("subDAO", subDao);
        return "index2";
    }

    /**
     * This method renders the object specified by the ID.
     *
     * @param id the id
     * @param model the model
     * @return appropriate view
     */
    @RequestMapping("/objetos/{id}")
    public String infoDetalhada(@PathVariable Integer id, Model model) {
        DocumentoReal d = docDao.get(id);
        model.addAttribute("obaaEntry", d.getObaaEntry());
        model.addAttribute("title", d.getTitles().get(0));
        model.addAttribute("metadata", d.getMetadata());
        return "infoDetalhada";
    }

    @RequestMapping("/consulta")
    public String consulta(HttpServletRequest request,
            @ModelAttribute("buscaModel") Consulta consulta,
            BindingResult result, Model model,
            @RequestParam(value = "pager.offset", required = false) Integer offset) {
        model.addAttribute("BuscaModel", consulta);

        buscaValidator.validate(consulta, result);
        if (result.hasErrors()) {
            return "index";
        } else {
            try {
                if (offset != null) {
                    consulta.setOffset(offset);
                }

                Recuperador rec = new Recuperador();
                List<DocumentoReal> docs = rec.busca(consulta);
                model.addAttribute("documentos", docs);
                return "consulta";
            } catch (Exception e) {
                model.addAttribute("erro",
                        "Ocorreu um erro ao efetuar a consulta. Tente novamente mais tarde.");
                System.err.println("FEB ERRO: Erro ao efetuar a consula na base de dados. Exception: "
                        + e.toString());
                return "index";
            }
        }
    }

    @RequestMapping("/consultaAvancada")
    public String consultaAvancada(
            HttpServletRequest request,
            @ModelAttribute("buscaModel") Consulta consulta,
            BindingResult result, Model model) {
        model.addAttribute("BuscaModel", consulta);

        buscaValidator.validate(consulta, result);
        if (result.hasErrors()) {
            model.addAttribute("repDAO", repDao);
            model.addAttribute("subDAO", subDao);
            return "index2";
        } else {
            try {
                Recuperador rec = new Recuperador();
                List<DocumentoReal> docs = rec.busca(consulta);
                model.addAttribute("documentos", docs);
                return "consulta";
            } catch (Exception e) {
                model.addAttribute("erro",
                        "Ocorreu um erro ao efetuar a consulta. Tente novamente mais tarde.");
                System.err.println("FEB ERRO: Erro ao efetuar a consula na base de dados. Exception: "
                        + e.toString());
                return "index2";
            }
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

            session.setAttribute("usuario", login); // armazena na sessao o
            // login
            session.setMaxInactiveInterval(900); // seta o tempo de validade da
            // session
            return "redirect:/admin/";

        } else {
            if (login != null) {
                model.addAttribute("erro", "Usuário ou senha incorretos!");
            }
            return "login";
        }
    }

    @RequestMapping("obaaconsulta")
    public @ResponseBody
    String webservice(
            @RequestParam String query,
            @RequestParam int limit,
            @RequestParam int offset) {

        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        try{
        if (query == null || query.isEmpty()) {
                xml = "<FEB> "
                        + "  <error code=\"badQuery\">empty query</error> "
                        + "</FEB>";
            } else {
                String encodedQuery = URLDecoder.decode(query, "UTF-8");

                Recuperador rep = new Recuperador();
                Consulta c = new Consulta();

                c.setConsulta(encodedQuery);
                c.setLimit(limit);
                c.setOffset(offset);
                List<DocumentoReal> docs = rep.busca(c);
                if (docs.size() > 0) {
                    xml = "<ListRecords>";
                }
                for (DocumentoReal doc : docs) {
                    xml = "<record><metadata>"
                            + doc.getObaaXml()
                            + "</metadata></record>";
                }
            }

        return xml;
        
        }catch (UnsupportedEncodingException u){
            return "<error code=\"UnsupportedEncoding\">Unsupported Encoding to UTF-8. Error: "+u+"</error>";
        }
    }
}
