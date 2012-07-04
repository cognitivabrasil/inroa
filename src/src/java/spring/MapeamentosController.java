
package spring;

import java.security.Principal;

import modelos.Mapeamento;
import modelos.MapeamentoDAO;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Controller for users.
 * 
 * @author Paulo Schreiner <paulo@jorjao81.com>
 */
@Controller("mapeamentos")
@RequestMapping("/admin/mapeamentos/*")
public final class MapeamentosController {
	@Autowired 	private MapeamentoDAO mapDao;
	
	Logger log = Logger.getLogger(MapeamentosController.class);

	/**
	 * Instantiates a new users controller.
	 */
	public MapeamentosController() {
	}

	/**
	 * Shows details of the user.
	 *
	 * @param id the user id
	 */
	@RequestMapping("/{id}")
	public String userShow(@PathVariable Integer id, Model model) {
		model.addAttribute("mapeamento", mapDao.get(id));
		return "admin/mapeamentos/show";
	}

	/**
	 * Shows new user forme.
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String userNewShow(Model model) {
		model.addAttribute("userModel", new Mapeamento());
		model.addAttribute("roleList", UserDto.referenceData().get("roleList"));
		return "admin/mapeamentos/form";
	}
	
	@RequestMapping(value = "/passwd", method = RequestMethod.GET)
	public String passwdShow(Model model, Principal principal) {
		UserPasswordDto u = new UserPasswordDto();
		u.setUsername(principal.getName());
		model.addAttribute("user", u);
		
		return "admin/mapeamentos/passwd";
	}
	
	/**
	 * Shows edit user form.
	 *
	 * @param id the user id
	 */
	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public String userEditShow(@PathVariable Integer id, Model model) {

		model.addAttribute("mapeamento", mapDao.get(id));
//		model.addAttribute("metadataList", );

		return "admin/mapeamentos/form";
	}
	
}
