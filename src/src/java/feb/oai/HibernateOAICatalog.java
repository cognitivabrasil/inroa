/**
 * Copyright 2006 OCLC Online Computer Library Center Licensed under the Apache
 * License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or
 * agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package feb.oai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;

import ORG.oclc.oai.models.HibernateOaiDocument;
import ORG.oclc.oai.server.catalog.AbstractHibernateOAICatalog;
import feb.data.entities.Repositorio;
import feb.spring.ApplicationContextProvider;


/**
 * DummyOAICatalog is an example of how to implement the AbstractCatalog
 * interface. Pattern an implementation of the AbstractCatalog interface after
 * this class to have OAICat work with your database. Your effort may be
 * minimized by confining your changes to areas identified by
 * "YOUR CODE GOES HERE" comments. In truth, though, you can do things however
 * you want, as long as the non-private methods return what they're supposed to.
 * 
 * @author Jeffrey A. Young, OCLC Online Computer Library Center
 */
public class HibernateOAICatalog extends AbstractHibernateOAICatalog {
	static Logger log = Logger.getLogger(HibernateOAICatalog.class.getName());

	SessionFactory sessionFactory;

	public HibernateOAICatalog(Properties properties) {
		super(properties);
	}

	/**
	 * Retrieve a list of sets that satisfy the specified criteria
	 * 
	 * @return a Map object containing "sets" Iterator object (contains
	 *         <setSpec/> XML Strings) as well as an optional resumptionMap Map.
	 * @exception OAIBadRequestException
	 *                signals an http status code 400 problem
	 */
	public Map listSets() {
		purge(); // clean out old resumptionTokens
		Map listSetsMap = new HashMap();
		/**********************************************************************
		 * YOUR CODE GOES HERE */
		
		Session session = sessionFactory.getCurrentSession();

		Criteria criteria2 = session.createCriteria(Repositorio.class);
		List<Repositorio> fromdb = criteria2.list();
		List sets = new ArrayList();

		for (Repositorio r : fromdb) {
			StringBuffer s = new StringBuffer(200);
			s.append("<set>\n");
			s.append("<setSpec>");
			s.append(r.getName());
			s.append("</setSpec>\n");
			s.append("<setName>");
			s.append(r.getName());
			s.append("</setName>\n");
			s.append("</set>\n");
			sets.add(s.toString());
		}

		listSetsMap.put("sets", sets.iterator());
		return listSetsMap;
	}

	@Override
	public Class<? extends HibernateOaiDocument> getHibernateClass() {
		// TODO Auto-generated method stub
		return feb.data.entities.DocumentoReal.class;
	}

	@Override
	public SessionFactory getSessionFactory() {
		if(sessionFactory == null) {
			ApplicationContext ctx = ApplicationContextProvider
					.getApplicationContext();
			sessionFactory = ctx.getBean(SessionFactory.class);
		}
		return sessionFactory;
	}
	
}
