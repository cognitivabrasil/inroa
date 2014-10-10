/*******************************************************************************
 * Copyright (c) 2014 Cognitiva Brasil Tecnologias Educacionais
 * http://www.cognitivabrasil.com.br - contato@cognitivabrasil.com.br
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 * In no event shall the author be liable for any claim or damages.
 *
 * Todos os direitos reservados.
 *******************************************************************************/
package com.cognitivabrasil.feb;

import java.util.List;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewInterceptor;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import ORG.oclc.oai.server.OAIHandler;

import com.cognitivabrasil.feb.config.LoggingHandlerExceptionResolver;
import com.cognitivabrasil.feb.spring.controllers.RssController;

/**
 * 
 * JavaConfig com as configurações e beans diretamente relacionados ao Spring MVC.
 *
 * @author Paulo Schreiner
 */
@EnableWebMvc
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private LocalContainerEntityManagerFactoryBean emf;
    
    @Autowired
    private LoggingHandlerExceptionResolver exceptionResolver;

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    /* (non-Javadoc)
     * Adiciona interecptor parar evitar o erro de LazyBean nos views.
     * 
     * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry)
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        OpenEntityManagerInViewInterceptor interceptor = new OpenEntityManagerInViewInterceptor();
        interceptor.setEntityManagerFactory(emf.getObject());
        registry.addWebRequestInterceptor(interceptor);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/WEB-INF/resources/");
        registry.addResourceHandler("/css/**").addResourceLocations("/css/");
        registry.addResourceHandler("/imagens/**").addResourceLocations("/imagens/");
        registry.addResourceHandler("/scripts/**").addResourceLocations("/scripts/");
    }
    
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add(exceptionResolver);
    }
    
    /**
     * Configura os view resolvers do Spring MVC.
     * 
     * Cria um view resolvers:
      <ol>
      <li>baseado em nomes de bean , que escanea o projeto por beans que implementem {@link View}, torna eles
      disponíveis como views nos controllers (usado pelo {@link RssController})</li>
      <li>baseado em .jsps</li>
      </ol>
     * 
     * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#configureViewResolvers(org.springframework.web.servlet.config.annotation.ViewResolverRegistry)
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.beanName();
        registry.jsp("/WEB-INF/views/", ".jsp");
    };
    

    @Bean
    public Filter characterEncodingFilter() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return characterEncodingFilter;
    } 
     
    /**
     * Registra servlet para o Oaicat.
     * 
     * @return servlet do oaicat.
     */
    @Bean
    public ServletRegistrationBean servletRegistrationBean(){
        OAIHandler h = new OAIHandler();
 
        ServletRegistrationBean s = new ServletRegistrationBean(h,"/oai/*");
        
        s.addInitParameter("properties", "dummy.properties");
        
        return s;
    }

}
