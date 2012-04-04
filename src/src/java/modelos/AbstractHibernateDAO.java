/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.lang.Class;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author paulo
 */
public abstract class AbstractHibernateDAO<T> {

    @Autowired
    SessionFactory sessionFactory;
    private Class<T> type;

    public AbstractHibernateDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public T get(int id) {
        return (T) this.sessionFactory.getCurrentSession().get(type, id);
    }

    public void delete(T item) {
        this.sessionFactory.getCurrentSession().delete(item);
    }

    public void save(T item) {
        System.out.println("Saving...");
        System.out.println(item.toString());

        this.sessionFactory.getCurrentSession().saveOrUpdate(item);

    }
    
    public List<T> getAll() {
        return this.sessionFactory.getCurrentSession().createCriteria(type).list();
    }
}
