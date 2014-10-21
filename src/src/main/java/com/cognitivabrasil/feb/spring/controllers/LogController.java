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
@RequestMapping("/admin/log")
@Controller
public class LogController {
    @Autowired
    private FebConfig config;

    /**
     * Recebe o nome do log, busca no disco e retorna para o usuário. O nome deve ser "feb", "searches" ou "update", podendo também coletar os arquivos antigos como por exemplo, com a consulta "feb-1" retornará o log "feb.log.1".
     * @param fileName Nome do arquivos de log solicitado. "feb", "searches" ou "update" podendo colocar um "-" e o número do log, ex: "update-1".
     * @return O arquivo de log solicitado.
     */
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
        else if(fileName.matches("(feb|searches|update)-[1-9]")){
            String[] file = fileName.split("-");
            return new FileSystemResource(new File(config.getLogHome() + "/" + file[0] + ".log."+file[1]));
        }        
        else { return null; }
    }
}