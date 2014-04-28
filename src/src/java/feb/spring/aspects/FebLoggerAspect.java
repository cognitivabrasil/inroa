package feb.spring.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.cognitivabrasil.feb.data.entities.Repositorio;
import com.cognitivabrasil.feb.data.entities.SubFederacao;
import com.cognitivabrasil.feb.data.entities.Usuario;
import feb.data.interfaces.FebDomainObject;
import com.cognitivabrasil.feb.data.services.RepositoryService;
import feb.services.UserActionLogger;
import feb.spring.FebConfig;

@Aspect
public class FebLoggerAspect {
	private UserActionLogger userActionLogger;
	
	@Autowired
	RepositoryService repDao;
	
	
	@Autowired
	public void setUserActionLogger(UserActionLogger logger) {
		userActionLogger = logger;
	}
	
	private Usuario getCurrentUser() {
		Usuario currentUser;
		try {
			currentUser = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		catch(NullPointerException e) {
			currentUser = new Usuario();
			currentUser.setUsername("anonymous");
		}
		return currentUser;
	}

	@Pointcut(value="execution(public * save(..)) && args(febDom) && within(feb.data.daos.*)")
	public void anySaveMethod( FebDomainObject febDom) {}
	
	@Pointcut(value="execution(public * delete(..)) && args(febDom) && within(feb.data.daos.*)")
	public void anyDeleteMethod( FebDomainObject febDom) {}
	
	@Pointcut(value="execution(public * feb.spring.FebConfig.save()) && target(callee)")
	public void saveFebConfig(FebConfig callee) {}
	
	@Pointcut(value="execution(public * feb.robo.atualiza.Repositorios.atualizaFerramentaAdm(..)) && args(id, apagar)")
	public void atualizar(int id, boolean apagar) {}
	
	@Pointcut(value="execution(public * feb.robo.atualiza.SubFederacaoOAI.atualizaSubfedAdm(..)) && args(subFed, apagar)")
	public void atualizarFed(SubFederacao subFed, boolean apagar) {}
	
	@AfterReturning("atualizar(id, apagar)")
	public void logAtualizaAction(JoinPoint pjp, int id, boolean apagar) throws Throwable {
		String r = (id == 0 ? "TODOS" : repDao.get(id).toString());
		
		if(apagar) {
			userActionLogger.log(getCurrentUser(), "limpar e recoletar repositório " + r);
		}
		else {
			userActionLogger.log(getCurrentUser(), "recoletar repositório " + r);
		}
	}
	
	@AfterReturning("atualizarFed(subFed, apagar)")
	public void logAtualizaFedAction(JoinPoint pjp, SubFederacao subFed, boolean apagar) throws Throwable {

		if(apagar) {
			userActionLogger.log(getCurrentUser(), "limpar e recoletar federação " + subFed);
		}
		else {
			userActionLogger.log(getCurrentUser(), "recoletar federação " + subFed);
		}
	}
	
	@Before("saveFebConfig(febConfig)")
	public void logFebConfigAction(JoinPoint pjp, FebConfig febConfig) throws Throwable {
		userActionLogger.log(getCurrentUser(), "salvar configurações" + febConfig);
	}

	
	@Before("anySaveMethod(febDom)")
	public void logAction(JoinPoint pjp, FebDomainObject febDom) throws Throwable {
		userActionLogger.log(getCurrentUser(), febDom, "salvar ou editar");
 	}


	
	@Before("anyDeleteMethod(febDom)")
	public void logDeleteAction(JoinPoint pjp, FebDomainObject febDom) throws Throwable {
		userActionLogger.log(getCurrentUser(), febDom, "apagar");

	}
	
}
