package feb.spring.controllers;

import feb.data.entities.*;
import feb.data.interfaces.DocumentosVisitasDao;
import feb.data.interfaces.RepositoryDAO;
import feb.data.interfaces.SubFederacaoDAO;
import feb.data.interfaces.VisitasDao;
import java.util.Calendar;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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

    @RequestMapping("/")
    public String statistics(Model model) {

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

    /**
     * Gets the session.
     *
     * @return the session
     */
    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }
}
