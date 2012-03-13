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

    @RequestMapping("/editarRepositorio")
    public String editaRep(Model model) {
        model.addAttribute("repDAO", repDao);
        model.addAttribute("padraoMetadadosDAO", padraoDao);
        return "editarRepositorio";
    }

    @RequestMapping("/salvarRepositorioGeral")
    public String salvaRepGeral(
            @RequestParam(value = "nome", required = true) String nome,
            @RequestParam(value = "descricao", required = true) String descricao,
            @RequestParam(value = "padrao_metadados", required = true) String padrao,
            @RequestParam(value = "tipo_map", required = true) String mapeamento,
            @RequestParam(value = "namespace", required = true) String nameSpace,
            @RequestParam(value = "metPrefix", required = true) String metadataPrefix,
            @RequestParam(value = "id", required = true) int id,
            Model model) {


//        int result = 0, result2 = 0;
//
//        String sql1 = "UPDATE repositorios set nome='" + nome + "', descricao='" + descricao + "' where id=" + id; //sql que possui o update
//
//        result = stm.executeUpdate(sql1); //submete o UPDATE ao banco de dados
//
//        if (result > 0) { //se o insert funcionar entra no if
//            String sql2 = "UPDATE info_repositorios set padrao_metadados=" + padrao_metadados + ", tipo_mapeamento_id=" + tipo_mapeamento + ", metadata_prefix='" + metadata_prefix + "', name_space='" + name_space + "' where id_repositorio=" + id;
//            result2 = stm.executeUpdate(sql2);
//            if (result2 > 0) {
//                out.print("<script type='text/javascript'>alert('Os dados foram atualizados com sucesso!'); "
//                        + "opener.location.href=opener.location.href; "
//                        + "window.location=\"exibeRepositorios?id=" + id + "\";</script>");
//            }
//        }
        return "editarRepositorio";
    }

    @RequestMapping("/salvarRepositorioOAI")
    public String salvaRepOAI(
            @RequestParam(value = "url", required = true) String url,
            @RequestParam(value = "periodicidade", required = true) String periodicidade,
            @RequestParam(value = "set", required = true) String set,
            @RequestParam(value = "id", required = true) int id,
            Model model) {


//        int periodicidadeHoras = Integer.parseInt(periodicidade) * 24;
//        String sql = "UPDATE info_repositorios SET url_or_ip='" + url + "', periodicidade_horas=" + periodicidadeHoras + ", set='" + set + "' WHERE id_repositorio=" + id;
//        result = stm.executeUpdate(sql);
//        if (result > 0) {
//            out.print("<script type='text/javascript'>alert('Os dados foram atualizados com sucesso!'); "
//                    + "window.location=\"exibeRepositorios?id=" + id + "\";</script>");
//        }
        
        return "editarRepositorio";
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
