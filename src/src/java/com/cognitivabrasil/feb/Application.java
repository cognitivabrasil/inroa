package com.cognitivabrasil.feb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Classe principal para o Spring Boot.
 *
 * @author Paulo Schreiner
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application {

    /**
     * Inicia aplicação do Spring.
     * 
     * @param args argumentos de linha de comando
     */
    public static void main(String[] args) {    
        SpringApplication.run(Application.class, args);
    }
}