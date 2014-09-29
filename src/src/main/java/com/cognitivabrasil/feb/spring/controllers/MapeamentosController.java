package com.cognitivabrasil.feb.spring.controllers;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cognitivabrasil.feb.data.entities.Mapeamento;
import com.cognitivabrasil.feb.data.entities.PadraoMetadados;
import com.cognitivabrasil.feb.data.services.MappingService;
import com.cognitivabrasil.feb.data.services.MetadataRecordService;
import com.cognitivabrasil.feb.spring.dtos.MapeamentoDto;
import com.cognitivabrasil.feb.spring.validador.MapeamentoValidator;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.*;

/**
 * Controller for users.
 *
 * @author Paulo Schreiner <paulo@jorjao81.com>
 */
@Controller("mapeamentos")
@RequestMapping("/admin/mapeamentos/*")
public final class MapeamentosController {

    @Autowired
    private MappingService mapDao;
    @Autowired
    private MetadataRecordService padraoDao;
    @Autowired
    private MapeamentoValidator validator;
    private static final Logger log = LoggerFactory.getLogger(MapeamentosController.class);

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
            MetadataRecordService pDao) {
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
    public String mapeamentoNewShow(
            Model model,
            @RequestParam(required = false) Integer padrao) {
        MapeamentoDto mapDto = new MapeamentoDto(new Mapeamento());
        if (padrao != null) {
            mapDto.setPadraoMetadados(padrao);
        }
        mapDto.setXmlObaa("Insira o XSLT e o XML de exemplo e clique no botão \"Testar o mapeamento\"!");
        model.addAttribute("mapeamento", mapDto);
        model.addAttribute("metadataList", getMetadataListForSelect(padraoDao));
        return "admin/mapeamentos/form";
    }

    /**
     * Recebe os dados do formulário e mostra um XML transformado, ou salva o
     * mapeamento.
     */
    @RequestMapping(value = {"/new", "/{id}/edit"}, method = RequestMethod.POST)
    public String mapeamentoEdit(Model model,
            @ModelAttribute("mapeamento") MapeamentoDto map,
            BindingResult result) {
    	
    	if (map == null || map.getPadraoMetadados() == null) {
			model.addAttribute("message", "Ocorreu um erro com a sua requisição." +
							"Uma causa possível é o XML de exemplo ser muito grande. " +
							"Tente reduzir o número de elementos dentro do ListRecords.");
			return "error";
			
		}

        validator.validate(map, result);


        if (result.hasErrors() || map.getSubmit()==null) {
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
     * @param id the user id
     */
    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String userEditShow(@PathVariable Integer id, Model model, HttpServletResponse response) {
        Mapeamento map = mapDao.get(id);
        if(!map.isEditable()){
            response.setStatus(401);
            log.warn("Algum usuário tentou forçar a edição de um mapeamento interno.");
            return "";
        }
        model.addAttribute("mapeamento", new MapeamentoDto(map));
        model.addAttribute("metadataList", getMetadataListForSelect(padraoDao));

        return "admin/mapeamentos/form";
    }
    
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    public @ResponseBody
    String delete(@PathVariable("id") Integer id, Model model) {
            Mapeamento obj = mapDao.get(id);
            String name = obj.getName();
            log.info("Deletando o mapeamento: "+name);
            mapDao.delete(obj);
            log.info("Mapeamento "+name+" deletado com sucesso.");
            return "ok";
    }
}
