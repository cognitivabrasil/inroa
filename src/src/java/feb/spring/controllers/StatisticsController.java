package feb.spring.controllers;

import com.cognitivabrasil.feb.data.entities.DocumentoReal;
import com.cognitivabrasil.feb.data.entities.Estatistica;
import com.cognitivabrasil.feb.data.entities.DocumentosVisitas;
import com.cognitivabrasil.feb.data.entities.Visita;
import com.cognitivabrasil.feb.data.services.DocumentService;
import com.cognitivabrasil.feb.data.services.DocumentVisitService;
import com.cognitivabrasil.feb.data.services.RepositoryService;
import com.cognitivabrasil.feb.data.services.FederationService;
import com.cognitivabrasil.feb.data.services.VisitService;
import feb.services.TagCloudService;
import feb.util.Message;
import feb.util.Operacoes;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
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
    private RepositoryService repDao;
    @Autowired
    private VisitService visitasDao;
    @Autowired
    private DocumentVisitService docVisDao;
    @Autowired
    private FederationService fedDao;
    @Autowired
    private DocumentService docDao;
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

        int defaultMonth = 6;
        Calendar c = Calendar.getInstance();

        List visitsList = visitasDao.visitsUpToAMonth(c.get(Calendar.MONTH), c.get(Calendar.YEAR), defaultMonth);
        model.addAttribute("visitasTotal", visitsList.toString());

        SimpleDateFormat sdformat = new SimpleDateFormat("dd/MM/yyyy");
        model.addAttribute("finalDate", sdformat.format(c.getTime()));
        c.add(Calendar.MONTH, -defaultMonth);
        model.addAttribute("initialDate", sdformat.format(c.getTime()));

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

    @RequestMapping(value = "/updatevisits/{from}/{until}", method = RequestMethod.POST)
    @ResponseBody
    public Message updateVisits(@PathVariable("from") String from, @PathVariable("until") String until, RedirectAttributes redirectAttributes) {
        Date fromDate;
        Date untilDate;
        List<Integer> visitsList;
        Estatistica e = new Estatistica();

        try {
            fromDate = Operacoes.stringToDate(from);
            untilDate = Operacoes.stringToDate(until);
        } catch (ParseException p) {
            return new Message(Message.ERROR, "As datas informadas não estão no padrão dia/mes/ano");
        }

        try {            
            visitsList = visitasDao.visitsToBetweenDates(fromDate, untilDate);
        } catch (Exception ex) {
            log.error("Erro ao consultar as visitas entre datas na base de dados", ex);
            return new Message(Message.ERROR, "Ocorreu um erro ao efetuar a consulta das visitas entre as datas selecionadas");
        }
        
        return new Message(Message.SUCCESS, visitsList.toString());
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
    public Message deleteTag(@PathVariable("tag") String tag, RedirectAttributes redirectAttributes) {

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
    public Message deleteAllTags(RedirectAttributes redirectAttributes) {
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
