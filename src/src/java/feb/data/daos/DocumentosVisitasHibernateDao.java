package feb.data.daos;

import feb.data.entities.DocumentoReal;
import feb.data.entities.DocumentosVisitas;
import feb.data.entities.Visita;
import feb.data.interfaces.DocumentosVisitasDao;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Luiz Henrique Longhi Rossi <lh.rossi@cognitivabrasil.com.br>
 */
public class DocumentosVisitasHibernateDao extends AbstractHibernateDAO<DocumentosVisitas> implements DocumentosVisitasDao {

    
    public DocumentosVisitasHibernateDao() {
    	setClazz(DocumentosVisitas.class);
    }

    @Override
    public DocumentosVisitas get(int id) {

        Session s = this.sessionFactory.getCurrentSession();
        DocumentosVisitas v = (DocumentosVisitas) s.createCriteria(Visita.class).
                add(Restrictions.eq("id", id)).uniqueResult();

        return v;
    }

    @Override
    public List<DocumentoReal> getTop(int n) {

        Session s = this.sessionFactory.getCurrentSession();
        String sql = "SELECT d.* from documentos_visitas dv, documentos as d WHERE dv.documento=d.id GROUP BY d.id, d.titulo, d.obaa_entry, d.resumo, d.data, d.localizacao, d.id_repositorio, d.timestamp, d.palavras_chave, d.id_rep_subfed, deleted, obaaxml ORDER BY COUNT(*) DESC LIMIT " + n;

        return s.createSQLQuery(sql).addEntity(DocumentoReal.class).list();
    }
}
