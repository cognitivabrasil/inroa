package feb.data.daos;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;


/**
 * This class is for all entities that have a "name" property and want to
 * be order by it.
 * 
 * @author paulo
 *
 * @param <T>
 */
public abstract class AbstractNamedHibernateDAO<T> extends AbstractHibernateDAO<T> {
	/**
	 * Gets all entities of type T, ordered by name. Use with care for big collections.
	 *
	 * @return list of all the entities of type T
	 */
	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		return this.sessionFactory.getCurrentSession().createCriteria(type).addOrder(Order.asc("name").ignoreCase())
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY) // this removes duplicate entities generate by fetch=join
				.list();
	}
	
    /**
     * Get entity by name.
     * @param name
     * @return 
     */
    @SuppressWarnings("unchecked")
	public T get(String name) {
        Session s = this.sessionFactory.getCurrentSession();
        return (T)s.createCriteria(type).
        		add(Restrictions.eq("name", name).ignoreCase()).uniqueResult();
    }
    
}
