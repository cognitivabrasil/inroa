package feb.spring.validador;

import static org.apache.commons.lang3.StringUtils.isBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import feb.data.interfaces.MapeamentoDAO;
import feb.data.interfaces.PadraoMetadadosDAO;
import feb.spring.dtos.MapeamentoDto;

@Component
public class MapeamentoValidator implements Validator {
	@Autowired
	private MapeamentoDAO mapDao;
	@Autowired
	private PadraoMetadadosDAO padraoDao;

	@Override
	public boolean supports(@SuppressWarnings("rawtypes") Class clazz) {
		return MapeamentoDto.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		MapeamentoDto m = (MapeamentoDto) target;


		if (isBlank(m.getName())) {
			errors.rejectValue("name", "name.empty", "Informe um nome.");
		} else if (mapDao.exists(m.getName())) {
			errors.rejectValue("name", "description.exists",
					"O nome já existe.");
		}

		if (isBlank(m.getDescription())) {
			errors.rejectValue("description", "description.empty",
					"Informe uma descrição");
		}

		if (isBlank(m.getXslt())) {
			errors.rejectValue("xslt", "xslt.empty", "Informe um XSLT");
		}

		if (m != null && padraoDao != null && padraoDao.get(m.getPadraoMetadados()) == null) {
			errors.rejectValue("padraoMetadados", "padraoMetadados.empty",
					"Informe uma padrão de metadados");
		}


		m.transform();

		if (m.isFailed()) {
			errors.rejectValue("xslt", "xslt.invalid", "O XSLT é inválido.");
		}

	}

}