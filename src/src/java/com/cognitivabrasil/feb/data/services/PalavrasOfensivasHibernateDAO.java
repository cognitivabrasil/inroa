package com.cognitivabrasil.feb.data.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 *
 * @author luiz
 */
@Service
public class PalavrasOfensivasHibernateDAO {
/* TODO: update this for JPA */
//    @Autowired
//    private SessionFactory sessionFactory;

//    @Override
    public boolean contains(String word) {

//        Session s = sessionFactory.getCurrentSession();
//
//        word = word.replace("@", "a");
//        word = word.replace("$", "s");
//        word = word.replace("£", "e");
//        word = word.replace("3", "e");
//        word = word.replace("1", "l");
//        word = word.replace("4", "t");
//        word = word.replace("0", "o");
//        word = word.replace("¢", "c");
//        word = com.cognitivabrasil.feb.util.Operacoes.removeAcentuacao(word);
//        
//        String[] tokens = word.split(" ");
//
        boolean containsBadWord = false;
//        for (int i = 0; i < tokens.length; i++) {
//            
//            List<PalavrasOfensivas> p = s.createCriteria(PalavrasOfensivas.class).add(Restrictions.ilike("word", tokens[i])).list();
//            
//            if (!(p.isEmpty())) {
//                containsBadWord = true;
//            }
//        }
        
        return (containsBadWord);
    }
}
