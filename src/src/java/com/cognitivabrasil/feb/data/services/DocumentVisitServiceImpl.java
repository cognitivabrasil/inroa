package com.cognitivabrasil.feb.data.services;

import com.cognitivabrasil.feb.data.repositories.DocumentVisitRepository;
import com.cognitivabrasil.feb.data.entities.DocumentoReal;
import com.cognitivabrasil.feb.data.entities.DocumentosVisitas;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Luiz Henrique Longhi Rossi <lh.rossi@cognitivabrasil.com.br>
 */
@Service
public class DocumentVisitServiceImpl implements DocumentVisitService {

    @Autowired
    DocumentVisitRepository dvRep;

    @Override
    public List<DocumentosVisitas> getAll() {
        return dvRep.findAll();
    }

    @Override
    public void save(DocumentosVisitas v) {
        dvRep.save(v);
    }

    @Override
    public DocumentosVisitas get(int id) {
        return dvRep.findById(id);
    }

//    @Override
//    public List<DocumentoReal> getTop(int n) {
//
//        Session s = this.sessionFactory.getCurrentSession();
//        String sql = "SELECT d.* from documentos_visitas dv, documentos as d WHERE dv.documento=d.id AND d.deleted=false GROUP BY d.id, d.titulo, d.obaa_entry, d.resumo, d.data, d.localizacao, d.id_repositorio, d.timestamp, d.palavras_chave, d.id_rep_subfed, deleted, obaaxml ORDER BY COUNT(*) DESC LIMIT " + n;
//
//        return s.createSQLQuery(sql).addEntity(DocumentoReal.class).list();
//    }
    @Override
    public List<DocumentoReal> getTop(int n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
