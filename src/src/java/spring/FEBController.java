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
     *
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

    @RequestMapping("/editarRepositorio")
    public String editaRep(Model model) {
        model.addAttribute("repDAO", repDao);
        model.addAttribute("padraoMetadadosDAO", padraoDao);
        return "editarRepositorio";
    }

    @RequestMapping("/removerRepositorio")
    public String apagaRep(
            @RequestParam(value = "submitted", required = false) boolean submitted,
            @RequestParam(value = "id", required = true) int id,
            Model model) {
        if (submitted) {
            Repositorio rep = new Repositorio();
            rep.setId(id);
            repDao.delete(rep);
            return "fechaRecarrega";
        } else {

            model.addAttribute("repDAO", repDao);
            return "removerRepositorio";
        }
    }

    @RequestMapping("/salvarRepositorioGeral")
    public String salvaRepGeral(
            @RequestParam(value = "nomeRep", required = true) String nome,
            @RequestParam(value = "descricao", required = true) String descricao,
            @RequestParam(value = "padrao_metadados", required = true) int padrao,
            @RequestParam(value = "tipo_map", required = true) int mapeamento,
            @RequestParam(value = "namespace", required = true) String nameSpace,
            @RequestParam(value = "metPrefix", required = true) String metadataPrefix,
            @RequestParam(value = "id", required = true) int id,
            Model model) {

        Repositorio rep = new Repositorio();
        rep.setId(id);
        rep.setNome(nome);
        rep.setDescricao(descricao);
        rep.setIdPadraoMetadados(padrao);
        rep.setIdTipoMapeamento(mapeamento);
        rep.setNamespace(nameSpace);
        rep.setMetadataPrefix(metadataPrefix);

        repDao.save(rep);


        return "exibeRepositorios";
    }

    @RequestMapping("/salvarRepositorioOAI")
    public String salvaRepOAI(
            @RequestParam(value = "url", required = true) String url,
            @RequestParam(value = "periodicidade", required = true) int periodicidade,
            @RequestParam(value = "set", required = true) String set,
            @RequestParam(value = "id", required = true) int id,
            Model model) {
//TODO: como testar se todos os campos foram preenchidos? E como avisar se nao foi?

        Repositorio rep = new Repositorio();
        rep.setId(id);
        rep.setUrl(url);
        rep.setPeriodicidadeAtualizacao(periodicidade);
        rep.setColecoes(set);

        repDao.save(rep);

        return "exibeRepositorios";
    }

    @RequestMapping("/exibeFederacao")
    public String exibeFed(Model model) {
        model.addAttribute("subDAO", subDao);
        return "exibeFederacao";
    }

    @RequestMapping("/editarFederacao")
    public String editaFed(Model model) {
        model.addAttribute("subDAO", subDao);
        return "editarFederacao";
    }

    @RequestMapping("/salvarFederacao")
    public String salvaFed(
            @RequestParam(value = "nome", required = true) String nome,
            @RequestParam(value = "descricao", required = true) String descricao,
            @RequestParam(value = "url", required = true) String url,
            @RequestParam(value = "id", required = true) int id,
            Model model) {
        //TODO: como testar se todos os campos vieram preenchidos e se nao tiver retornar uma alerta? Tipo o codigo abaixo
        /*
         * if (nome.isEmpty() || descricao.isEmpty() || url.isEmpty()) {
         * out.print("<script type='text/javascript'>alert('Todos os campos
         * devem ser preenchidos!');</script>" + "<script
         * type='text/javascript'>history.back(-1);</script>"); }
         */

        SubFederacao subfed = new SubFederacao();

        subfed.setId(id);
        subfed.setNome(nome);
        subfed.setDescricao(descricao);
        subfed.setUrl(url);

        subDao.save(subfed);

        model.addAttribute("subDAO", subDao);
        return "exibeFederacao";
    }
    
    @RequestMapping("/removerFederacao")
    public String apagaFed(
            @RequestParam(value = "submitted", required = false) boolean submitted,
            @RequestParam(value = "id", required = true) int id,
            Model model) {
        if (submitted) {
            SubFederacao subFed = new SubFederacao();
            subFed.setId(id);
            subDao.delete(subFed);
            return "fechaRecarrega";
        } else {

            model.addAttribute("subDAO", subDao);
            return "removerFederacao";
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
            return "redirect:adm";

        } else {
            if (login != null) {
                model.addAttribute("erro", true);
            }
            return "login";
        }
    }
}
