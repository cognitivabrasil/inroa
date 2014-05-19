package feb.spring.controllers;

import com.cognitivabrasil.feb.data.entities.Usuario;
import com.cognitivabrasil.feb.data.services.UserService;
import java.io.IOException;
import java.security.Principal;
import java.util.*;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

class UserPasswordDto {

    private String username;
    private String password;
    private String confirmPassword;
    private String oldPassword;

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
}

/**
 * The Class UserValidator, validates a UserPasswordDto.
 */
@Component
class UserPasswordValidator implements Validator {

    private Usuario user;
    Logger log = Logger.getLogger(UserPasswordValidator.class);

    @Override
    public boolean supports(Class clazz) {
        return UserPasswordDto.class.isAssignableFrom(clazz);
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserPasswordDto u = (UserPasswordDto) target;

        log.info(user);
        if (!user.authenticate(u.getOldPassword())) {
            errors.rejectValue("oldPassword", "password.incorrect", "Senha informada incorreta.");
        }

        if (u.getPassword() != null || u.getConfirmPassword() != null) {
            if (!u.getPassword().equals(u.getConfirmPassword())) {
                errors.rejectValue("password", "notmatch.password",
                        "As senhas devem se iguais");
            } else if (u.getPassword().length() < 5) {
                errors.rejectValue("password", "too_short.password", "A senha deve conter no mínimo 5 letras");
            }
        }

    }
}

/**
 * The Class UserDto.
 *
 * This class represents the User Data Transfer Object, i.e, the backend of the
 * User form. It can be populated with a {@link Usuario} object, and return an
 * updated one.
 */
class UserDto {

    private Integer id;
    private String username;
    private String role;
    private String password;
    private String confirmPassword;
    private String description;

    /**
     * Instantiates an empty UserDto.
     */
    public UserDto() {
    }

    /**
     * Instantiates a userDto based on the Usuario object.
     *
     * @param u the {@link Usuario}
     */
    public UserDto(Usuario u) {
        setUsername(u.getUsername());
        setId(u.getId());
        setRole(u.getRole());
        setDescription(u.getDescription());
    }

    /**
     * Gets the Usuario object corresponding to the Dto.
     *
     *
     * @param u the Usuario to be update or null if it is a new Usuario
     * @return the updated or new Usuario, ready to be saved
     */
    Usuario getUsuario(Usuario u) {
        if (u == null) {
            u = new Usuario();
        }
        if (password != null && (!password.isEmpty())) {
            u.setPassword(password);
        }
        u.setUsername(username);
        u.setRole(role);
        u.setPermissions(getPermissions(role));
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

    /**
     * Reference data for the roles.
     *
     * @return the map
     */
    protected static Map<String, Map<String, String>> referenceData() {

        Map<String, Map<String, String>> referenceData = new HashMap<>();

        Map<String, String> roles = new LinkedHashMap<>();
        roles.put("root", "Superusuário");
        roles.put("admin", "Administrador de federações");
        roles.put("update", "Visualizar e atualizar");
        roles.put("view", "Somente visualizar");
        referenceData.put("roleList", roles);

        return referenceData;
    }

    /**
     * Gets the permissions by the role.
     *
     * @param role the role
     * @return the permissions
     */
    private Set<String> getPermissions(String role) {
        Map<String, List<String>> roles = new LinkedHashMap<>();
        roles.put(
                "root",
                Arrays.asList(StringUtils
                        .split("PERM_MANAGE_USERS,PERM_UPDATE,PERM_MANAGE_REP,PERM_MANAGE_METADATA,PERM_MANAGE_MAPPINGS,PERM_CHANGE_DATABASE",
                                ',')));
        roles.put(
                "admin",
                Arrays.asList(StringUtils
                        .split("PERM_UPDATE,PERM_MANAGE_REP,PERM_MANAGE_METADATA,PERM_MANAGE_MAPPINGS",
                                ',')));
        roles.put("update", Arrays.asList(StringUtils.split(
                "PERM_UPDATE", ',')));
        roles.put("view",
                Arrays.asList(StringUtils.split("PERM_VIEW", ',')));
        return new HashSet<>(roles.get(role));
    }
}

/**
 * The Class UserValidator, validates a UserDto.
 */
@Component
class UserValidator implements Validator {

    @Autowired
    UserService userDao;

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
        // verifies that the role exists
        if (UserDto.referenceData().get("roleList").get(u.getRole()) == null) {
            errors.rejectValue("role", "required.role",
                    "É necessário informar um perfil");
        }

    }
}

/**
 * Controller for users.
 *
 * @author Paulo Schreiner <paulo@jorjao81.com>
 */
@Controller("users")
@RequestMapping("/admin/users/*")
public final class UsersController {

    @Autowired
    ServletContext servletContext;
    @Autowired
    private UserService userDao;
    @Autowired
    private UserValidator userValidator;
    private static Logger log = Logger.getLogger(UsersController.class);

    /**
     * Instantiates a new users controller.
     */
    public UsersController() {
    }

    /**
     * Shows details of the user.
     *
     * @param id the user id
     */
    @RequestMapping("/{id}")
    public String userShow(@PathVariable Integer id, Model model) {
        model.addAttribute("user", userDao.get(id));
        return "admin/users/show";
    }

    /**
     * Shows new user forme.
     */
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String userNewShow(Model model) {
        model.addAttribute("userModel", new UserDto());
        model.addAttribute("roleList", UserDto.referenceData().get("roleList"));
        return "admin/users/form";
    }

    @RequestMapping(value = "/passwd", method = RequestMethod.GET)
    public String passwdShow(Model model, Principal principal) {
        UserPasswordDto u = new UserPasswordDto();
        u.setUsername(principal.getName());
        model.addAttribute("user", u);

        return "admin/users/passwd";
    }

    @RequestMapping(value = "/passwd", method = RequestMethod.POST)
    public String passwdChange(Model model, Principal principal,
            @ModelAttribute("user") UserPasswordDto u,
            BindingResult result) {
        Usuario user = userDao.get(principal.getName());

        UserPasswordValidator userPasswordValidator = new UserPasswordValidator();
        userPasswordValidator.setUser(user);

        assert (userPasswordValidator != null);
        userPasswordValidator.validate(u, result);
        if (result.hasErrors()) {
            u.setUsername(user.getUsername());
            model.addAttribute("user", u);
            return "admin/users/passwd";
        } else {
            log.debug("Changing password from user " + user.getUsername());
            user.setPassword(u.getPassword());
            userDao.save(user);
            return "redirect:/admin/fechaRecarrega";
        }

    }

    /**
     * Shows edit user form.
     *
     * @param id the user id
     */
    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String userEditShow(@PathVariable Integer id, Model model) {

        model.addAttribute("userModel", new UserDto(userDao.get(id)));
        model.addAttribute("roleList", UserDto.referenceData().get("roleList"));

        return "admin/users/form";
    }

    /**
     * Actually changes the user.
     *
     * @param u the UserDto, as filled by the form
     * @return edit page reloaded with error descriptions in case of error,
     * returns to main admin page otherwise
     */
    @RequestMapping(value = "/{id}/edit", method = RequestMethod.POST)
    public String userEditDo(@PathVariable Integer id,
            @ModelAttribute("userModel") UserDto u, BindingResult result,
            Model model) {

        userValidator.validate(u, result);
        if (result.hasErrors()) {
            model.addAttribute("userModel", u);
            model.addAttribute("roleList",
                    UserDto.referenceData().get("roleList"));

            return "admin/users/form";
        } else {
            userDao.save(u.getUsuario(userDao.get(id)));
            return "redirect:/admin/fechaRecarrega";
        }

    }

    /**
     * Actually creates the new user.
     *
     * @param u the UserDto, as filled by the form
     * @return edit page reloaded with error descriptions in case of error,
     * returns to main admin page otherwise
     */
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String userNewDo(@ModelAttribute("userModel") UserDto u,
            BindingResult result, Model model) {
        userValidator.validate(u, result);
        if (result.hasErrors()) {
            model.addAttribute("userModel", u);
            model.addAttribute("roleList",
                    UserDto.referenceData().get("roleList"));

            return "admin/users/form";
        } else {
            userDao.save(u.getUsuario(null));
            return "redirect:/admin/fechaRecarrega";
        }

    }

    /**
     * Actually deletes the user, unless its the last one.
     *
     * @param id the user id
     * @throws RuntimeException if the user is the last one
     */
    @RequestMapping(value = "/{id}/delete")
    public @ResponseBody
    String userDeleteDo(@PathVariable Integer id, Model model, HttpServletResponse response) throws IOException {
        if (userDao.getAll().size() == 1) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Não é permidito deletar o último usuário.");

            return "erro";
        }
        Usuario u = userDao.get(id);
        userDao.delete(u);
        if (u.getId().equals(getCurrentUser().getId())) {
            SecurityContextHolder.getContext().setAuthentication(null);
        }

        return "ok";
    }

    private static Usuario getCurrentUser() {
        Usuario currentUser;
        try {
            currentUser = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (NullPointerException e) {
            log.error("Não foi possível recuperar o usuário que está utilizando o sistema.", e);
            currentUser = null;
        }
        return currentUser;
    }
}
