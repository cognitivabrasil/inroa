package feb.data.entities;

import java.util.Iterator;

import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import feb.data.interfaces.Paginavel;

class SubRepPaginator implements Iterator<List<DocumentoReal>> {
	int current = 0;
	int maxResults = 1000;
	int total = 0;
	RepositorioSubFed rep;
	Session session;
	
	public SubRepPaginator(RepositorioSubFed r) {
		rep = r;
		session = r.getSessionFactory().getCurrentSession();
		Criteria c = session.createCriteria(DocumentoReal.class).add(Restrictions.eq("repositorioSubFed", r)).
				add(Restrictions.eq("deleted", false)).

				setProjection(Projections.rowCount());
		
		total = ((Long) c.uniqueResult()).intValue();
	}

	@Override
	public boolean hasNext() {
		return current < total;
	}

	@Override
	public List<DocumentoReal> next() {
		Criteria c = session.createCriteria(DocumentoReal.class).add(Restrictions.eq("repositorioSubFed", rep)).
				add(Restrictions.eq("deleted", false)).
				addOrder(Order.asc("id")).
				setFetchMode("tokens", FetchMode.JOIN).
				setFetchMode("objetos", FetchMode.JOIN).
				setFirstResult(current).setMaxResults(maxResults);
		current = current + maxResults;
		return c.list();
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}
	
}

public class SubRepPaginavel implements Paginavel<DocumentoReal> {
	Iterator<List<DocumentoReal>> iterator;
	int total = 0;
	
	public SubRepPaginavel(RepositorioSubFed r) {
		iterator = new SubRepPaginator(r);
		Session session = r.getSessionFactory().getCurrentSession();
		Criteria c = session.createCriteria(DocumentoReal.class).add(Restrictions.eq("repositorioSubFed", r)).
				add(Restrictions.eq("deleted", false)).
				setProjection(Projections.rowCount());
		total =  ((Long)c.uniqueResult()).intValue();
	}

	@Override
	public Iterator<List<DocumentoReal>> iterator() {
		return iterator;
	}

	@Override
	public int size() {
		return total;
	}

}