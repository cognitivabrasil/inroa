package com.cognitivabrasil.feb.data.repositories;

import java.util.Date;
import java.util.List;

import com.cognitivabrasil.feb.data.entities.Search;

public interface SearchesJdbcDao {

    List<Search> getSearches(Integer i, Date a);

}
