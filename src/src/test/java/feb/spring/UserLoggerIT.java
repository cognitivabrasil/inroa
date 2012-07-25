package feb.spring;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import feb.data.daos.AbstractDaoTest;
import feb.data.entities.Mapeamento;
import feb.data.entities.Repositorio;
import feb.data.entities.Usuario;
import feb.data.interfaces.FebDomainObject;
import feb.data.interfaces.MapeamentoDAO;
import feb.data.interfaces.PadraoMetadadosDAO;
import feb.data.interfaces.RepositoryDAO;
import feb.services.UserActionLogger;
import feb.spring.FebConfig;
import feb.spring.aspects.FebLoggerAspect;
import static org.mockito.Mockito.*;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testApplicationContext.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class UserLoggerIT extends AbstractDaoTest {

	@Autowired
	MapeamentoDAO mapDao;
	
	@Autowired
	FebConfig febConfig;
	
	@Autowired
	PadraoMetadadosDAO padraoDao;
	
	@Autowired
	RepositoryDAO repDao;
	
	@Autowired SessionFactory sessionFactory;
	Session session;
	
	private UserActionLogger userActionLogger;
	
	@Autowired
	private FebLoggerAspect febLoggerAspect;

	@Before
	public void before() {
		userActionLogger = mock(UserActionLogger.class);
		febLoggerAspect.setUserActionLogger(userActionLogger);
	}
	

	private Mapeamento getTestMapeamento() {
		Mapeamento m = new Mapeamento();
		m.setName("Bla");
		m.setPadraoMetadados(padraoDao.get(1));
		m.setXslt("ggg");
		m.setDescription("desc");
		return m;
	}
	
	@Test
	@Transactional
	public void mapeamentos() {
		Mapeamento m = getTestMapeamento();
		
		mapDao.save(m);
		
		verify(userActionLogger, times(1)).log(any(Usuario.class), any(FebDomainObject.class), argThat(startsWith("salvar")));
				
	}

	
	@Test
	@Transactional
	public void delete() {
		Mapeamento m = getTestMapeamento();
		
		mapDao.save(m);
		
		mapDao.delete(m);
		
		Repositorio r = repDao.get(1);
		repDao.delete(r);
		
		verify(userActionLogger, times(2)).log(any(Usuario.class), any(FebDomainObject.class), argThat(startsWith("apaga")));
		verify(userActionLogger, times(1)).log(any(Usuario.class), any(FebDomainObject.class), argThat(startsWith("salvar")));
		
	}
	
	@Test
	@Transactional
	public void febConfig() throws IOException {
		febConfig.setDatabase("host");
		febConfig.save();
		
		verify(userActionLogger, times(1)).log(any(Usuario.class), argThat(startsWith("salvar")));
				
	}
	
	
}