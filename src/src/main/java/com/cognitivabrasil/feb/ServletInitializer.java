package com.cognitivabrasil.feb;

import javax.servlet.Filter;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.filter.HttpPutFormContentFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Classe de configuração do Servlet 3.0 (substitui o web.xml).
 * 
 * É usada somente quando é feito o deploy do WAR em um container.
 * Quando o programa é rodado diretamente, não utiliza essa classe.
 * 
 * @author Paulo Schreiner
 * @see Application
 */
public class ServletInitializer extends
    AbstractAnnotationConfigDispatcherServletInitializer {

  @Override
  protected Class<?>[] getRootConfigClasses() {
    return new Class[] { AppConfig.class };
  }

  @Override
  protected Class<?>[] getServletConfigClasses() {
    return new Class[] { WebConfig.class };
  }

  @Override
  protected String[] getServletMappings() {
    return new String[] { "/" };
  }

  @Override
  protected void customizeRegistration(Dynamic registration) {
    registration.setInitParameter("dispatchOptionsRequest", "true");
  }

  @Override
  protected Filter[] getServletFilters() {
    CharacterEncodingFilter charFilter = new CharacterEncodingFilter();
    charFilter.setEncoding("UTF-8");
    charFilter.setForceEncoding(true);
    return new Filter[] { new HiddenHttpMethodFilter(), charFilter,
        new HttpPutFormContentFilter() };
  }
  
}