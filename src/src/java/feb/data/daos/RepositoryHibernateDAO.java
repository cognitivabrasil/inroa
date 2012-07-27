/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feb.data.daos;


import feb.data.entities.Repositorio;
import feb.data.interfaces.RepositoryDAO;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class RepositoryHibernateDAO.
 *
 * @author paulo
 */
public class RepositoryHibernateDAO extends AbstractNamedHibernateDAO<Repositorio> implements RepositoryDAO {
 
    public RepositoryHibernateDAO() {
    	setClazz(Repositorio.class);
    }
    

    @Override
    public void updateNotBlank(Repositorio r2) {
        if(r2.getId() == null) {
            throw new IllegalArgumentException("Cant update a new repository, save it instead");
        }
        Repositorio r = get(r2.getId());
        r.merge(r2);
        save(r);
    }

    @Override
    public List<Repositorio> getOutDated() {
        // TODO: Jorjão ve se tu consegue fazer isso com linguagem do hibernate.
        // o incremento de 4 horas é para descontar da periodicidade da ultima
        // atualizacao. Pq se o robo rodou ontem as 02h e atualizou o
        // repositorio as 02:10h hoje quando rodar novamente nao vai atualiza pq
        // nao faz 24h ainda.
        return sessionFactory.getCurrentSession().createSQLQuery(
                "SELECT * FROM repositorios r WHERE (r.data_ultima_atualizacao <= ((now() - r.periodicidade_horas * INTERVAL '1 DAY') + INTERVAL '4 HOUR') or r.data_ultima_atualizacao is null)").addEntity(Repositorio.class).list();
    }
    
}
