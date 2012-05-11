/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spring;

import java.net.MalformedURLException;
import java.net.URL;

import ferramentaBusca.IndexadorBusca;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import modelos.*;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

import postgres.SingletonConfig;
import spring.validador.PadraoValidator;
import spring.validador.RepositorioValidator;
import spring.validador.InfoBDValidator;
import spring.validador.SubFederacaoValidador;
import robo.atualiza.Repositorios;
import robo.atualiza.SubFederacaoOAI;

class UserDto {
	private Integer id;
	private String username;
	private String role;
	private String password;
	private String confirmPassword;
	private String description;

	public UserDto() {
	};

	public UserDto(Usuario u) {
		setUsername(u.getUsername());
		setId(u.getId());
		setRole(u.getRole());
		setDescription(u.getDescription());
	}

	Usuario getUsuario(Usuario u) {
		if (u == null) {
			u = new Usuario();
		}
		if (password != null && (!password.isEmpty())) {
			u.setPassword(password);
		}
		u.setUsername(username);
		u.setRole(role);
		u.setDescription(description);
		return u;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}

@Component
class UserValidator implements Validator {
	@Autowired
	UsuarioDAO userDao;

	@Override
	public boolean supports(Class clazz) {
		return UserDto.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		ValidationUtils.rejectIfEmpty(errors, "username", "required.username",
				"É necessário informar um nome de usuário.");
		ValidationUtils.rejectIfEmpty(errors, "description",
				"required.description", "É necessário informar uma descrição.");

		UserDto u = (UserDto) target;

		if (u.getId() == null) {
			// is a NEW user, check that password is not blank
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password",
					"required.password", "É necessário informar uma senha.");
			if (userDao.get(u.getUsername()) != null) { 
				errors.rejectValue("username", "invalid.username",
						"Já existe um usuário com esse nome."); 
			}
		}

		if (u.getPassword() != null || u.getConfirmPassword() != null) {
			if (!u.getPassword().equals(u.getConfirmPassword())) {
				errors.rejectValue("password", "notmatch.password",
						"As senhas devem se iguais");
			}
		}

		if (!u.getRole().equals("admin")) {
			errors.rejectValue("role", "ivalid.role", "Role inválido");
		}

	}

}

/**
 * Controller for users
 * 
 * @author Paulo Schreiner <paulo@jorjao81.com>
 */
@Controller("users")
@RequestMapping("/admin/users/*")
public final class UsersController {
	@Autowired
	ServletContext servletContext;
	@Autowired
	private UsuarioDAO userDao;

	@Autowired
	private RepositoryDAO repDao;
	@Autowired
	private SubFederacaoDAO subDao;
	@Autowired
	private PadraoMetadadosDAO padraoDao;
	@Autowired
	private MapeamentoDAO mapDao;

	@Autowired
	private UserValidator userValidator;

	public UsersController() {
	}

	@RequestMapping("/{id}")
	public String exibeRep(@PathVariable Integer id, Model model) {
		model.addAttribute("user", userDao.get(id));
		return "admin/users/show";
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String cadastraRep(Model model) {

		// TODO: alterar o jsp para coletar o padrao e o tipo do mapeamento
		// atraves do repModel
		model.addAttribute("userModel", new UserDto());
		return "admin/users/form";
	}

	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public String userEditShow(@PathVariable Integer id, Model model) {

		model.addAttribute("userModel", new UserDto(userDao.get(id)));
		return "admin/users/form";
	}

	@RequestMapping(value = "/{id}/edit", method = RequestMethod.POST)
	public String userEditDo(@PathVariable Integer id,
			@ModelAttribute("userModel") UserDto u, BindingResult result,
			Model model) {

		userValidator.validate(u, result);
		if (result.hasErrors()) {
			model.addAttribute("userModel", u);
			return "admin/users/form";
		} else { // se retornar null é porque nao tem nenhum repositorio com
					// esse nome
			userDao.save(u.getUsuario(userDao.get(id))); // salva o novo
															// repositorio
															// return
			return "redirect:/admin/fechaRecarrega";
		}

	}

	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public String salvaNovoRep(@ModelAttribute("userModel") UserDto u,
			BindingResult result, Model model) {
		userValidator.validate(u, result);
		if (result.hasErrors()) {
			model.addAttribute("userModel", u);
			return "admin/users/form";
		} else { // se retornar null é porque nao tem nenhum repositorio com
					// esse nome
			userDao.save(u.getUsuario(null)); // salva o novo repositorio return
			return "redirect:/admin/fechaRecarrega";
		}

	}

	@RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
	public String userDeleteDo(@PathVariable Integer id, Model model) {
		if(userDao.getAll().size() == 1) {
			throw new RuntimeException("Cannot delete last user");
		}
		userDao.delete(userDao.get(id));
		return "redirect:/admin/fechaRecarrega";
	}

	@RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
	public String userDeleteShow(@PathVariable Integer id,
			Model model) {
		model.addAttribute("user", userDao.get(id));
		return "admin/users/confirmDelete";
	}
}
