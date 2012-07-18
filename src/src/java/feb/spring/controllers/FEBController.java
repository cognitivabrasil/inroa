package feb.spring.controllers;

import feb.data.entities.Consulta;
import feb.data.entities.DocumentoReal;
import feb.data.entities.Visita;
import feb.data.interfaces.DocumentosDAO;
import feb.data.interfaces.RepositoryDAO;
import feb.data.interfaces.SubFederacaoDAO;
import feb.data.interfaces.UsuarioDAO;
import feb.data.interfaces.VisitasDao;
import feb.ferramentaBusca.Recuperador;
import feb.robo.atualiza.importaOAI.XMLtoDB;
import feb.spring.ServerInfo;
import feb.spring.validador.BuscaValidator;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Controller geral para o FEB
 *
 * @author Paulo Schreiner <paulo@jorjao81.com>
 * @author Marcos Nunes <marcosn@gmail.com>
 */
@Controller("feb")
public final class FEBController {

    @Autowired
    ServerInfo serverInfo;
    @Autowired
    private UsuarioDAO userDao;
    @Autowired
    private RepositoryDAO repDao;
    @Autowired
    private SubFederacaoDAO subDao;
    @Autowired
    private DocumentosDAO docDao;
    @Autowired
    private VisitasDao visDao;
    private BuscaValidator buscaValidator;
    Logger log = Logger.getLogger(FEBController.class);

    public FEBController() {
        buscaValidator = new BuscaValidator();
    }

    @RequestMapping("/")
    public String inicio(Model model, HttpServletResponse response, @CookieValue(value = "feb.cookie", required = false) String cookie) {
        return index(model, response, cookie);
    }

    @RequestMapping("/index.*")
    public String indexJSP(Model model, HttpServletResponse response, @CookieValue(value = "feb.cookie", required = false) String cookie) {
        return index(model, response, cookie);
    }

    @RequestMapping("/index")
    public String index(Model model, HttpServletResponse response, @CookieValue(value = "feb.cookie", required = false) String cookie) {

        model.addAttribute("buscaModel", new Consulta());

        if (cookie == null) {
            Visita newVisitor = new Visita();
            visDao.save(newVisitor);
            Integer id = newVisitor.getId();
            Cookie cookieNew = new Cookie("feb.cookie", id.toString());
            response.addCookie(cookieNew);
        }

        return "index";
    }

    @RequestMapping("/index2")
    public String index2(Model model) {

        model.addAttribute("repDAO", repDao);
        model.addAttribute("subDAO", subDao);
        return "index2";
    }

    /**
     * Se o usuário tentar a cessar /feb/objetos ele redireciona para o index
     * @return redireciona para o /
     */
    @RequestMapping("/objetos")
    public String objetos() {
        return "redirect:/";
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
        List<String> titles = d.getTitles();
        String title = "Título não informado";
        if (!titles.isEmpty()) {
            title = titles.get(0);
        }

        model.addAttribute("title", title);
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

    @RequestMapping("/version")
    public @ResponseBody
    String version() {
        return serverInfo.getFullVersion();
    }

    @RequestMapping(value = "teste", method = RequestMethod.GET)
    public void testeLume() {
        XMLtoDB test = new XMLtoDB();
        test.testeLume(docDao, repDao);
    }

    /**
     * Webservice com a funcionalidade de utilizar o sistema de consulta do FEB
     * retornando os documentos no formato XML. Utiliza os mesmos métodos da
     * ferramenta de busca principal do FEB. Retorna um xml FEB com os objetos
     * no padrão de metadados OBAA. Existe uma tag de erro que contém um code e
     * uma mensagem: <error code="code">Message</error> codigos existentes: +
     * "badQuery" - Caso a consulta não tenha sido informada + "limitExceeded" -
     * Caso o limit informado seja superior a 100, que é o limite máximo
     * permitido (este limite máximo existe para que as consultar por webservice
     * não comprometam o desempenho da base de dados). + "UnsupportedEncoding" -
     * Caso ocorra um erro ao codificar a query em utf-8
     *
     * @param query String que será utilizada para buscar documentos.
     * @param autor String contendo o nome do autor que será utilizado na busca.
     * @param limit Número de resultados por resposta. O máximo é 100.
     * @param offset Utilizado em conjunto com o limit. Se o limit for 5 e o
     * offset 0, retornará os 5 primeiros objetos, alterando o offset para 1
     * retornará os próximos 5 resultados. Isto é utilizado para efetuar uma
     * paginação.
     * @return XML FEB contendo os documentos, no padrão de metadados OBAA,
     * referentes a consulta.
     */
    @RequestMapping(value = "obaaconsulta", method = RequestMethod.GET, produces = "text/xml;charset=UTF-8")
    public @ResponseBody
    String webservice(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String autor,
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) Integer offset) {


        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<FEB>";
        try {
            if ((query == null || query.isEmpty()) && (autor == null || autor.isEmpty())) {
                xml += "<error code=\"badQuery\">empty query</error> ";
            } else if (limit != null && limit > 100) {
                xml += "<error code=\"limitExceeded\">The maximum is 100</error> ";
            } else {

                Recuperador rep = new Recuperador();
                Consulta c = new Consulta();
                if (query != null) {
                    String encodedQuery = URLDecoder.decode(query, "UTF-8");
                    c.setConsulta(encodedQuery);
                }

                if (limit != null) {
                    c.setLimit(limit);
                }
                if (offset != null) {
                    c.setOffset(offset);
                }
                if (autor != null) {
                    c.setAutor(autor);
                }
                List<DocumentoReal> docs = rep.busca(c);

                xml += "<ListRecords "
                        + "query=\"" + c.getConsulta() + "\" ";
                if (!c.getAutor().isEmpty()) {
                    xml += "autor=\"" + c.getAutor() + "\" ";
                }
                xml += "limit=\"" + c.getLimit() + "\" "
                        + "offset=\"" + c.getOffset() + "\" >";

                for (DocumentoReal doc : docs) {
                    xml += "<document><metadata>"
                            + doc.getObaaXml()
                            + "</metadata></document>";
                }
                xml += "</ListRecords> ";
            }
            xml += "</FEB>";
            log.debug("Uso do webservice: consulta: " + query + " Autor: " + autor + " limit: " + limit + " offset: " + offset);
            return xml;

        } catch (UnsupportedEncodingException u) {
            String error = "<FEB>"
                    + "<error code=\"UnsupportedEncoding\">Unsupported Encoding to UTF-8. Error: " + u + "</error></FEB>";
            return error;
        }
    }
}
