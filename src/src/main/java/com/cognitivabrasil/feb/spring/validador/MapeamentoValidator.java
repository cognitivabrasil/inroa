package com.cognitivabrasil.feb.spring.validador;

import com.cognitivabrasil.feb.data.entities.Mapeamento;
import com.cognitivabrasil.feb.data.services.MappingService;
import com.cognitivabrasil.feb.data.services.MetadataRecordService;
import com.cognitivabrasil.feb.spring.dtos.MapeamentoDto;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import static org.apache.commons.lang3.StringUtils.isBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MapeamentoValidator implements Validator {

    @Autowired
    private MappingService mapDao;
    @Autowired
    private MetadataRecordService padraoDao;
    @PersistenceContext
    private EntityManager em;

    @Override
    public boolean supports(@SuppressWarnings("rawtypes") Class clazz) {
        return MapeamentoDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MapeamentoDto m = (MapeamentoDto) target;

        if (isBlank(m.getName())) {
            errors.rejectValue("name", "name.empty", "Informe um nome.");
        } else if (m.getId() != null && mapDao.exists(m.getName())) {
            Mapeamento real = mapDao.get(m.getId());
            if (!m.getName().equals(real.getName())) {
                errors.rejectValue("name", "description.exists",
                        "O nome já existe.");
            }
        }
        em.clear();

        if (isBlank(m.getDescription())) {
            errors.rejectValue("description", "description.empty",
                    "Informe uma descrição");
        }

        if (isBlank(m.getXslt())) {
            errors.rejectValue("xslt", "xslt.empty", "Informe um XSLT");
        }

        if (padraoDao != null
                && padraoDao.get(m.getPadraoMetadados()) == null) {
            errors.rejectValue("padraoMetadados", "padraoMetadados.empty",
                    "Informe uma padrão de metadados");
        }

        m.transform();

        if (m.isFailed()) {
            errors.rejectValue("xslt", "xslt.invalid", "O XSLT é inválido.");
        }

    }

}
