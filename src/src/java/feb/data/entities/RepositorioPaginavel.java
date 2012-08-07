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
import org.springframework.util.StopWatch;

import feb.data.interfaces.Paginavel;

class RepositorioPaginator implements Iterator<List<DocumentoReal>> {
	int current = 0;
	int maxResults = 1000;
	int total = 0;
	Repositorio rep;
	Session session;
	
	public RepositorioPaginator(Repositorio r) {

		rep = r;
		session = r.getSessionFactory().getCurrentSession();
		Criteria c = session.createCriteria(DocumentoReal.class).add(Restrictions.eq("repositorio", r)).
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
		StopWatch t = new StopWatch();
		t.start("Pegando uma página");
		
		Criteria c = session.createCriteria(DocumentoReal.class).add(Restrictions.eq("repositorio", rep)).
				add(Restrictions.eq("deleted", false)).
				setFetchMode("tokens", FetchMode.JOIN).
				setFetchMode("objetos", FetchMode.JOIN).
				addOrder(Order.asc("id")).
				setFirstResult(current).setMaxResults(maxResults);
		current = current + maxResults;
		
		List<DocumentoReal> l = c.list();
		t.stop();
		System.out.println(t.prettyPrint());
		
		return l;
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}
	
}

public class RepositorioPaginavel implements Paginavel<DocumentoReal> {
	Iterator<List<DocumentoReal>> iterator;
	int total = 0;
	
	public RepositorioPaginavel(Repositorio r) {
		iterator = new RepositorioPaginator(r);
		Session session = r.getSessionFactory().getCurrentSession();
		Criteria c = session.createCriteria(DocumentoReal.class).add(Restrictions.eq("repositorio", r)).
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
