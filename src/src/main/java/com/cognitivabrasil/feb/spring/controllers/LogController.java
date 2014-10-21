package com.cognitivabrasil.feb.spring.controllers;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cognitivabrasil.feb.spring.FebConfig;

/**
 * Controller para baixar logs.
 *
 * @author Paulo Schreiner
 */
@RequestMapping("/log")
@Controller
public class LogController {
    @Autowired
    private FebConfig config;

    @RequestMapping(value = "{file_name}", method = RequestMethod.GET)
    @ResponseBody
    public FileSystemResource getFile(@PathVariable("file_name") String fileName) {
        /* só permite baixar os 3 arquivos de log, evita ataques de passar .. para tentar
         * ler qualquer arquivo do filesystem.
         */
        if(fileName.equals("feb") 
                || fileName.equals("seaches")
                || fileName.equals("update")) {
        
            return new FileSystemResource(new File(config.getLogHome() + "/" + fileName + ".log"));
        }
        
        else { return null; }
    }
}