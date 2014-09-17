package com.cognitivabrasil.feb.data.repositories;

import com.cognitivabrasil.feb.data.entities.Search;
import java.sql.Types;
import java.util.Date;
import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * @author Paulo Schreiner <paulo@cognitivabrasil.com.br>
 *
 */
public class SearchesJdbcDao extends JdbcDaoSupport {

    /**
     * Retorna todas as consultas feitas posteriores a data informada, agrupando pela consulta, então retorna uma lista
     * de {@link Search}, onde o texto da consulta é setado em {@link Search#text} e o número de vezes que ela aparece
     * em {@link Search#count}.
     * 
     * Esta consulta é feita em sql nativo para ficar mais rápido e evitar a complexidade de desenvolver com QueryDLS.
     *
     * @param i Limite de resultados.
     * @param a Data que será utilizada para filtrar os resultados.
     * @return Todas as consultas realizadas depois da data informada no limite informado.
     */
    public List<Search> getSearches(Integer i, Date a) {
        String sql = "SELECT text, COUNT(*) as count from searches WHERE created > ? GROUP BY text HAVING COUNT(*) > 1 ORDER BY count DESC,text LIMIT ?";
        List<Search> customers;
        customers = getJdbcTemplate().query(sql,
                new Object[]{a, i},
                new int[]{Types.TIMESTAMP, Types.BIGINT},
                new BeanPropertyRowMapper<>(Search.class));

        return customers;
    }

}
