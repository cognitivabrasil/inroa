package com.cognitivabrasil.feb.spring.controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cognitivabrasil.feb.data.entities.Consulta;
import com.cognitivabrasil.feb.data.entities.Document;
import com.cognitivabrasil.feb.data.services.DocumentService;
import com.cognitivabrasil.feb.data.services.FederationService;
import com.cognitivabrasil.feb.data.services.RepositoryService;
import com.cognitivabrasil.feb.data.services.SearchService;
import com.cognitivabrasil.feb.data.services.UserService;
import com.cognitivabrasil.feb.ferramentaBusca.Recuperador;
import com.cognitivabrasil.feb.spring.FebConfig;
import com.cognitivabrasil.feb.spring.dtos.PaginationDto;
import com.cognitivabrasil.feb.spring.validador.BuscaValidator;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import org.apache.solr.client.solrj.SolrServerException;

/**
 * Controller geral para o FEB
 *
 * @author Paulo Schreiner <paulo@jorjao81.com>
 * @author Marcos Nunes <marcosn@gmail.com>
 */
@Controller("feb")
public final class FEBController implements ErrorController {

    @Autowired
    private UserService userDao;
    @Autowired
    private RepositoryService repDao;
    @Autowired
    private FederationService subDao;
    @Autowired
    private DocumentService docDao;
    private final BuscaValidator buscaValidator;
    private static final Logger log = LoggerFactory.getLogger(FEBController.class);
    @Autowired
    private SearchService searchesDao;

    @Autowired
    private Recuperador recuperador;

    @Autowired
    private FebConfig config;

    public FEBController() {
        buscaValidator = new BuscaValidator();
    }

    @ModelAttribute("analyticsId")
    public String populateOrigin() {
        return config.getAnalyticsId();
    }

    @RequestMapping("/error")
    public ModelAndView error(Model model, HttpServletResponse response, HttpServletRequest request) {

        ModelAndView mv = new ModelAndView("errors/error404");

        return mv;
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping("/")
    public String inicio(Model model, HttpServletResponse response, HttpServletRequest request,
            @CookieValue(value = "feb.cookie", required = false) String cookie) {
        return index(model, response, request, cookie);
    }

    @RequestMapping("/index")
    public String index(Model model, HttpServletResponse response, HttpServletRequest request,
            @CookieValue(value = "feb.cookie", required = false) String cookie) {

        model.addAttribute("buscaModel", new Consulta());

        if (StringUtils.isEmpty(cookie)) {
            addCookie(response, request);
        }
        return "index";
    }

    /**
     * Se o usuário tentar acessar /feb/objetos ele redireciona para o index
     *
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
    public String infoDetalhada(@PathVariable Integer id, HttpServletResponse response, HttpServletRequest request,
            Model model, @CookieValue(value = "feb.cookie", required = false) String cookie)
            throws IOException {
        Document d = docDao.get(id);
        if (d == null) {
            response.setStatus(404);
            log.warn("O id " + id + " solicitado não existe!");
            return "";
        } else if (d.isDeleted()) {
            response.setStatus(410);

            return "errors/error410";
        } else {
            d.getMetadata().setLocale("pt-BR");
            model.addAttribute("obaaEntry", d.getObaaEntry());
            List<String> titles = d.getTitles();
            String title = "Título não informado";
            if (!titles.isEmpty()) {
                title = titles.get(0);
            }

            model.addAttribute("title", title);
            model.addAttribute("docId", d.getId());
            return "infoDetalhada";
        }
    }

    @RequestMapping("/objetos/{id}/json")
    public @ResponseBody
    String getJson(@PathVariable Integer id) {
        Document d = docDao.get(id);
        d.getMetadata().setLocale("pt-BR");
        return d.getMetadata().getJson();
    }

    @RequestMapping("/resultado")
    public String consulta(HttpServletRequest request,
            @ModelAttribute("buscaModel") Consulta consulta,
            BindingResult result, Model model,
            @RequestParam(required = false) Integer page,
            @CookieValue(value = "feb.cookie", required = false) String cookie) {
        model.addAttribute("buscaModel", consulta);
        log.debug("");
        log.debug("IP: " + request.getRemoteAddr() + " / search: \"" + consulta.getConsulta() + "\" / Cookie: " + !StringUtils.isEmpty(cookie));
        log.debug("User-Agent: " + request.getHeader("User-Agent"));

        buscaValidator.validate(consulta, result);
        if (result.hasErrors()) {
            return "index";
        } else {
            try {
                if (page == null) {
                    page = 0;
                } else {
                    consulta.setOffsetByPage(page);
                }

                List<Document> docs = recuperador.busca(consulta);
                log.trace("Carregou " + docs.size() + " documentos.");
                model.addAttribute("documentos", docs);

                if (!StringUtils.isEmpty(cookie)) {
                    searchesDao.save(consulta.getConsulta(), new Date());
                }

                PaginationDto pagination = new PaginationDto(consulta.getLimit(), consulta.getSizeResult(), page);
                model.addAttribute("pagination", pagination);

                log.debug("Resultou em: " + consulta.getSizeResult() + " documentos. Offset: " + consulta.getOffset());
                return "resultado";
            } catch (SolrServerException e) {
                model.addAttribute("erro",
                        "Ocorreu um erro ao efetuar a consulta. Tente novamente mais tarde.");
                //TODO: erro GRAVE, deve enviar um aviso ao administrador.

                log.error("Erro ao efetuar a consulta no Solr, possivelmente o serviço está parado. ", e);
                return "index";
            } catch (Exception e) {
                model.addAttribute("erro",
                        "Ocorreu um erro ao efetuar a consulta. Tente novamente mais tarde.");
                log.error("FEB ERRO: Erro ao efetuar a consulta na base de dados. ", e);
                return "index";
            }
        }
    }

    @RequestMapping(value = "/buscaAvancada", method = RequestMethod.GET)
    public String buscaAvancada(Model model, HttpServletResponse response, HttpServletRequest request,
            @CookieValue(value = "feb.cookie", required = false) String cookie) {

        model.addAttribute("repositories", repDao.getAll());
        model.addAttribute("federations", subDao.getAll());
        model.addAttribute("buscaModel", new Consulta());

        if (StringUtils.isEmpty(cookie)) {
            addCookie(response, request);
        }
        return "buscaAvancada";
    }



    /**
     * Apenas encaminha para a tela de login.
     *
     * @return login.
     */
    @RequestMapping(method = RequestMethod.GET, value = "/login")
    public String login() {
        return "login";
    }

    /**
     * Webservice com a funcionalidade de utilizar o sistema de consulta do FEB retornando os documentos no formato XML.
     * Utiliza os mesmos métodos da ferramenta de busca principal do FEB. Retorna um xml FEB com os objetos no padrão de
     * metadados OBAA. Existe uma tag de erro que contém um code e uma mensagem: <error code="code">Message</error>
     * códigos existentes: "badQuery" - Caso a consulta não tenha sido informada "limitExceeded" - Caso o limite
     * informado seja superior a 100, que é o limite máximo permitido (este limite máximo existe para que as consultar
     * por webservice não comprometam o desempenho da base de dados). "UnsupportedEncoding" - Caso ocorra um erro ao
     * codificar a query em utf-8. "InternalServerError" - Caso ocorra um erro interno no servidor de busca (Solr).
     *
     * @param query String que será utilizada para buscar documentos.
     * @param autor String contendo o nome do autor que será utilizado na busca.
     * @param limit Número de resultados por resposta. O máximo é 100.
     * @param offset Utilizado em conjunto com o limit. Se o limit for 5 e o offset 0, retornará os 5 primeiros objetos,
     * alterando o offset para 1 retornará os próximos 5 resultados. Isto é utilizado para efetuar uma paginação.
     * @return XML FEB contendo os documentos, no padrão de metadados OBAA, referentes a consulta.
     */
    @RequestMapping(value = "obaaconsulta", method = RequestMethod.GET, produces = "text/xml;charset=UTF-8")
    public @ResponseBody
    String webservice(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String autor,
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) Integer offset) {

        log.info("consulta feita no webservice: '" + query + "' autor: '" + autor + "' limit: " + limit + " offset: " + offset);

        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<FEB>";
        try {
            if ((query == null || query.isEmpty()) && (autor == null || autor.isEmpty())) {
                xml += "<error code=\"badQuery\">empty query</error> ";
            } else if (limit != null && limit > 100) {
                xml += "<error code=\"limitExceeded\">The maximum is 100</error> ";
            } else {

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
                List<Document> docs = recuperador.busca(c);

                xml += "<ListRecords "
                        + "query=\"" + c.getConsulta() + "\" ";
                if (!c.getAutor().isEmpty()) {
                    xml += "autor=\"" + c.getAutor() + "\" ";
                }
                xml += "limit=\"" + c.getLimit() + "\" "
                        + "offset=\"" + c.getOffset() + "\" >";

                for (Document doc : docs) {
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
        } catch (SolrServerException e) {
            String error = "<FEB>"
                    + "<error code=\"InternalServerError\">There is an internal server searching error.</error></FEB>";
            return error;
        }
    }

    /**
     * Verificador de URL, recebe como entrada uma url e retorna um boolean informando se ela está ativa ou não. Usar o
     * {@link #verifyUrl(java.lang.String)}.
     *
     *
     * @param url URL a ser testada, deve iniciar com http://
     * @return true se a url estiver ativa e false caso contrário
     */
    @Deprecated
    @RequestMapping("verificaURLOld")
    public @ResponseBody
    String verifyURL(@RequestParam String url) {
        try {
            URL u = new URL(url);
            HttpURLConnection huc = (HttpURLConnection) u.openConnection();
            huc.setRequestMethod("HEAD");
            huc.connect();
            int code = huc.getResponseCode();
            return String.valueOf(code >= 200 && code < 400);
        } catch (IOException e) {
            return "false";
        }
    }

    /**
     * Verificador de URL, recebe como entrada uma url e retorna um boolean informando se ela está ativa ou não.
     *
     * @param url URL a ser testada, deve iniciar com http://
     * @return true se a url estiver ativa e false caso contrário
     */
    @RequestMapping("verificaUrl")
    public @ResponseBody
    boolean verifyUrl(@RequestParam String url) {
        try {
            log.debug("testando url: " + url);
            URL u = new URL(url);
            BufferedReader reader = new BufferedReader(new InputStreamReader(u.openStream()));
            boolean result = reader.ready();
            reader.close();
            return result;

        } catch (IOException e) {
            return false;
        }

    }

    private Cookie addCookie(HttpServletResponse response, HttpServletRequest request) {
        Cookie newCookie = null;
        Cookie[] testeCokies = request.getCookies();
        if (testeCokies != null && testeCokies.length > 0) {
            newCookie = new Cookie("feb.cookie", DateTime.now().toString());
            newCookie.setMaxAge(300);
            response.addCookie(newCookie);
        }
        return newCookie;
    }
}
