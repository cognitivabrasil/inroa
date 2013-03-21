package feb.spring.controllers;

import feb.data.entities.*;
import feb.data.interfaces.DocumentosDAO;
import feb.data.interfaces.DocumentosVisitasDao;
import feb.data.interfaces.RepositoryDAO;
import feb.data.interfaces.SubFederacaoDAO;
import feb.data.interfaces.VisitasDao;
import feb.services.TagCloudService;
import feb.util.Message;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author cei
 */
@Controller("statistics")
@RequestMapping("/admin/statistics/*")
public final class StatisticsController {

    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private RepositoryDAO repDao;
    @Autowired
    private VisitasDao visitasDao;
    @Autowired
    private DocumentosVisitasDao docVisDao;
    @Autowired
    private SubFederacaoDAO fedDao;
    @Autowired
    private DocumentosDAO docDao;
    @Autowired
    private TagCloudService tagCloud;
    private static Logger log = Logger.getLogger(StatisticsController.class);

    @RequestMapping("/")
    public String statistics(Model model) {

        model.addAttribute("totalObj", docDao.getSize());

        List repList = repDao.getAll();
        Estatistica e = new Estatistica();

        model.addAttribute("repAcessos", e.convertNodoList(repList, "visits"));
        model.addAttribute("repObjects", e.convertNodoList(repList, "size"));
        model.addAttribute("repositorios", repList);


        Calendar c = Calendar.getInstance();
        List visitsList = visitasDao.visitsUpToAMonth(c.get(Calendar.MONTH), c.get(Calendar.YEAR));
        model.addAttribute("visitasTotal", e.convertIntList(visitsList));

        List<DocumentoReal> docsMaisAcessados = docVisDao.getTop(10);
        model.addAttribute("docsMaisAcessados", docsMaisAcessados);



        List fedList = fedDao.getAll();
        model.addAttribute("fedAcessos", e.convertNodoList(fedList, "visits"));
        model.addAttribute("fedObjects", e.convertNodoList(fedList, "size"));
        model.addAttribute("federacoes", fedList);

        Map<String, Integer> termos = tagCloud.getTagCloud();
        model.addAttribute("termosTagCloud", termos);

        return "admin/statistics";
    }

    @RequestMapping(value = "/objUser", method = RequestMethod.POST)
    public void insertDocumentUser(
            @RequestParam(required = true) int id,
            @CookieValue(value = "feb.cookie", required = true) String cookie) {


        DocumentosVisitas docVis = new DocumentosVisitas();
        docVis.setDocumento((DocumentoReal) getSession().load(DocumentoReal.class, id));
        docVis.setVisita((Visita) getSession().load(Visita.class, Integer.parseInt(cookie)));

        docVisDao.save(docVis);

    }

    @RequestMapping(value = "/deletetag/{tag}", method = RequestMethod.POST)
    @ResponseBody
    public Message delete(@PathVariable("tag") String tag, RedirectAttributes redirectAttributes) {

        try {
            tagCloud.delete(tag);
            return new Message(Message.SUCCESS, "Termo: '" + tag + "' removido com sucesso da tag cloud");
        } catch (Exception e) {
            log.error("Erro ao excluir a tag cloud: '" + tag + "'", e);
            return new Message(Message.ERROR, "Erro ao excluir o termo: '" + tag + "' da tag cloud");
        }
    }

    @RequestMapping(value = "/deletealltags", method = RequestMethod.POST)
    @ResponseBody
    public Message deleteAll(RedirectAttributes redirectAttributes) {
        try {
            tagCloud.clear();
            return new Message(Message.SUCCESS, "Todos os termos da tag cloud foram removidos com sucesso");
        } catch (Exception e) {
            log.error("Erro ao excluir todos os termos da tag cloud.", e);
            return new Message(Message.ERROR, "Erro ao excluir todos os termos da tag cloud");
        }
    }

    /**
     * Gets the session.
     *
     * @return the session
     */
    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }
}
