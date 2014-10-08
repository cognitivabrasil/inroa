package com.cognitivabrasil.feb.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

//@Component
public class LoggingHandlerExceptionResolver implements HandlerExceptionResolver, Ordered {
    private static final Logger log = LoggerFactory.getLogger(LoggingHandlerExceptionResolver.class);
    
    @Override
    public int getOrder() {
        return Integer.MIN_VALUE; // we're first in line, yay!
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest aReq, HttpServletResponse aRes, Object aHandler,
            Exception e) {
        log.info("Tratando erro: " + e.toString());
        
               
        ModelAndView mv = new ModelAndView("errors/errorGeneric");
        mv.addObject("stacktrace", e);
        
        return mv;
    }
}
