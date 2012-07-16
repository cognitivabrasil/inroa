package feb.spring.controllers;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import feb.data.entities.Mapeamento;
import feb.data.entities.PadraoMetadados;
import feb.data.interfaces.MapeamentoDAO;
import feb.data.interfaces.PadraoMetadadosDAO;
import feb.spring.dtos.MapeamentoDto;
import feb.spring.validador.MapeamentoValidator;

/**
 * Controller for users.
 * 
 * @author Paulo Schreiner <paulo@jorjao81.com>
 */
@Controller("mapeamentos")
@RequestMapping("/admin/mapeamentos/*")
public final class MapeamentosController {
	@Autowired
	private MapeamentoDAO mapDao;
	@Autowired
	private PadraoMetadadosDAO padraoDao;
	@Autowired private MapeamentoValidator validator;

	private static Logger log = Logger.getLogger(MapeamentosController.class);

	/**
	 * Instantiates a new users controller.
	 */
	public MapeamentosController() {
	}

	/**
	 * Shows details of the user.
	 * 
	 * @param id
	 *            the user id
	 */
	@RequestMapping("/{id}")
	public String mapeamentoShow(@PathVariable Integer id, Model model) {
		model.addAttribute("mapeamento", mapDao.get(id));
		return "admin/mapeamentos/show";
	}

	/**
	 * Returns a map ready to be used on a SpringForm select.
	 * 
	 * @param m
	 * @return the map
	 */
	private Map<Integer, String> getMetadataListForSelect(
			PadraoMetadadosDAO pDao) {
		Map<Integer, String> hash = new LinkedHashMap<Integer, String>();

		for (PadraoMetadados m : pDao.getAll()) {
			hash.put(m.getId(), m.getName());
		}

		return hash;
	}

	/**
	 * Shows new user forme.
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String mapeamentoNewShow(Model model) {
		model.addAttribute("mapeamento", new MapeamentoDto(new Mapeamento()));
		model.addAttribute("metadataList", getMetadataListForSelect(padraoDao));
		return "admin/mapeamentos/form";
	}

	/**
	 * Recebe os dados do formulário e mostra um XML transformado, ou salva o mapeamento.
	 */
	@RequestMapping(value = { "/new", "/{id}/edit" }, method = RequestMethod.POST)
	public String mapeamentoEdit(Model model,
			@ModelAttribute("mapeamento") MapeamentoDto map,
			BindingResult result) {
		
		validator.validate(map, result);
		

		if (result.hasErrors()) {
			model.addAttribute("mapeamento", map);
			model.addAttribute("metadataList",
					getMetadataListForSelect(padraoDao));
			return "admin/mapeamentos/form";
		} else {
			map.transform();
			Mapeamento m = map.createMapeamento(padraoDao);
			mapDao.save(m);
			return "redirect:/admin/fechaRecarrega";

		}
	}


	/**
	 * Shows edit user form.
	 * 
	 * @param id
	 *            the user id
	 */
	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public String userEditShow(@PathVariable Integer id, Model model) {

		model.addAttribute("mapeamento", new MapeamentoDto(mapDao.get(id)));
		model.addAttribute("metadataList", getMetadataListForSelect(padraoDao));

		return "admin/mapeamentos/form";
	}
	

    @RequestMapping(value="/{id}/delete", method = RequestMethod.POST)
    public String deleteDo(
    		@PathVariable Integer id,
    		Model model) {
            Mapeamento map = new Mapeamento();
            map.setId(id);
            mapDao.delete(map);
            return "redirect:/admin/fechaRecarrega";
    }
    
    @RequestMapping(value="/{id}/delete", method = RequestMethod.GET)
    public String deleteShow(
    		@PathVariable Integer id,
            Model model) {
            model.addAttribute("mapeamento", mapDao.get(id));
            return "admin/mapeamentos/confirmDelete";
    }


}
