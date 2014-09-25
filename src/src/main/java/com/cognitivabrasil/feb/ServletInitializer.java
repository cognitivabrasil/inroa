package com.cognitivabrasil.feb;

import javax.servlet.Filter;
import javax.servlet.ServletRegistration.Dynamic;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

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
    SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
}