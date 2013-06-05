/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feb.spring.controllers;

import feb.data.daos.AbstractHibernateDAO;
import feb.data.interfaces.FebDomainObject;
import org.apache.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author marcos
 */
public abstract class AbstractDeletable<T extends FebDomainObject, H extends AbstractHibernateDAO<T>>{
    
    private Logger  log = Logger.getLogger(AbstractDeletable.class);
    
    @RequestMapping(value = "/teste", method = RequestMethod.GET)
    public @ResponseBody
    String teste() {
            return "testado "+getDAO().getClass().getName();
            
    }
    
    public abstract H getDAO();
    
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    public @ResponseBody
    String delete(@PathVariable("id") Integer id, Model model) {
            T obj = getDAO().get(id);
            log.info("Deletando "+obj.getClass().getName()+": "+obj.getName());
            getDAO().delete(obj);
            log.info(obj.getClass().getName()+" deletado(a) com sucesso.");
            return "ok";
    }
}
