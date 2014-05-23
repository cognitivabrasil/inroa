/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitivabrasil.feb.data.services;

import com.cognitivabrasil.feb.data.entities.Search;
import com.cognitivabrasil.feb.data.repositories.SearchesJdbcDao;
import com.cognitivabrasil.feb.util.Operacoes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Marcos Freitas Nunes <marcos@cognitivabrasil.com.br>
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchesJdbcDao searchesRep;

    @Override
    public void cleanup() {
        searchesRep.cleanup();
    }

    @Override
    public List<Search> getSearches(int limit) {
        return searchesRep.getSearches(limit);
    }

    @Override
    public List<Search> getSearches(Date a) {
        return searchesRep.getSearches(a);
    }

    @Override
    public List<Search> getSearches(Integer limit, Date date) {
        return searchesRep.getSearches(limit, date);
    }

    @Override
    public void save(String string, Date date) {
        if (!Operacoes.isEmptyText(string)) {
            String[] l = string.trim().toLowerCase().split("\\s+");
            List<String> n = new ArrayList<>();
            n.addAll(Arrays.asList(l));
            String cons = StringUtils.join(n, " ");
            searchesRep.save(cons, date);
        }
    }

    /**
     * Deletes all entries.
     */
    @Override
    public void deleteAll() {
        searchesRep.deleteAll();
    }

    /**
     * Deletes a specific tag
     */
    @Override
    public void delete(String tag) {
        searchesRep.delete(tag);
    }
}
