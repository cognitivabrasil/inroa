/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitivabrasil.feb.data.services;

import com.cognitivabrasil.feb.data.entities.Search;
import com.cognitivabrasil.feb.data.repositories.SearchesJdbcDao;
import com.cognitivabrasil.feb.data.repositories.SearchRepository;
import com.cognitivabrasil.feb.util.Operacoes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Marcos Freitas Nunes <marcos@cognitivabrasil.com.br>
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchesJdbcDao searchesJdbc;
    @Autowired
    private SearchRepository searchRep;

    @Override
    public void cleanup() {
        searchRep.deleteOlderThan(DateTime.now().minusMonths(1));
    }

    @Override
    public List<Search> getSearches(Integer limit, Date date) {
        return searchesJdbc.getSearches(limit, date);
    }

    @Override
    public void save(String string, Date date) {
        save(string, new DateTime(date));
    }
    
    @Override
    public void save(String string, DateTime date) {
        if (!Operacoes.isEmptyText(string)) {
            String[] l = string.trim().toLowerCase().split("\\s+");
            List<String> n = new ArrayList<>();
            n.addAll(Arrays.asList(l));
            String cons = StringUtils.join(n, " ");
            searchRep.save(new Search(cons, date));
        }
    }

    /**
     * Deletes all entries.
     */
    @Override
    public void deleteAll() {
        searchRep.deleteAll();
    }

    /**
     * Deletes a specific tag
     */
    @Override
    public void delete(String tag) {
        searchRep.deleteByText(tag);
    }
}
