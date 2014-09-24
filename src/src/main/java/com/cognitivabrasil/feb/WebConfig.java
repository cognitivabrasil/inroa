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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewInterceptor;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import ORG.oclc.oai.server.OAIHandler;

/**
 * 
 * JavaConfig com as configurações e beans diretamente relacionados ao Spring MVC.
 *
 * @author Paulo Schreiner
 */
@EnableWebMvc
@Configuration
@ComponentScan(basePackages = "com.cognitivabrasil.feb.spring")
public class WebConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private LocalContainerEntityManagerFactoryBean emf;

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
    }
    
//    @Bean
//    public BeanNameViewResolver beanNameViewResolver() {
//        return new BeanNameViewResolver();
//    }
    
    @Bean
    public InternalResourceViewResolver internalResourceViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        
        return resolver;
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
