package com.cognitivabrasil.feb.solr.query;

import static org.apache.commons.lang3.StringUtils.isBlank;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.solr.client.solrj.SolrQuery;

/**
 *
 * @author Daniel Epstein
 */
public class CriaQuery {

    /**
     * Identifica todos os campos onde a busca deve acontecer.
     *
     * @param pesquisa Devolve o String de busca pronto para ser utilizado pelo SORL
     * @return Retorna uma query Solr com todos os campos preenchidos na classe {@link Consulta}.
     */
    public static SolrQuery criaQueryCompleta(Consulta pesquisa) {
        SolrQuery query = new SolrQuery();

        String resultado = "";

        if (!isBlank(pesquisa.getConsulta())) {
            resultado = pesquisa.getConsulta();
        }

        if (pesquisa.hasAuthor()) {
            // boost de 100 é extremamente alto para dar prioridade absoluta ao autor caso presente
            resultado += " obaa.lifecycle.entity:(" + pesquisa.getAutor() + ")^100.0";
        }
        query.setQuery(resultado);
        
        if (!isBlank(pesquisa.getIdioma())) {
            query.addFilterQuery("obaa.general.language:(" + pesquisa.getIdioma() + ")");
        }


        for(String name : pesquisa.getAll()) {
            List l = pesquisa.get(name);
            String fullName = pesquisa.getFullName(name);
            String tagName = pesquisa.getTagName(name);
            if(l != null) {       
                query.addFilterQuery("{!tag=" + tagName + "}" + 
                        orQueryQuoted(fullName, l));
            }
        }
        
        for(String name : pesquisa.getAll()) {
            List l = pesquisa.get(name);
            String fullName = pesquisa.getFullName(name);
            String tagName = pesquisa.getTagName(name);
            if(l != null) {       
                query.addFilterQuery("{!tag=" + tagName + "}" + 
                        orQueryQuoted(fullName, l));
            }
        }

        return query;
    }

    /**
     * Gera uma query para o Solr delimitada por OR
     * @param obaaField campo para o qual queremos a query
     * @param values valores aceitados no campo (qualquer um deles)
     * @return query pronta para ser adicionada a um QueryFilter do Solr
     */
    private static <T extends Object> String orQueryQuoted(String obaaField, List<T> values) {
        // query mais bonita para inteiros
        if(values.get(0).getClass().equals(Integer.class)) {
            return obaaField + ":("
                    + values.stream().map(i -> i.toString()).collect(Collectors.joining(" "))
                    + ")";
        }
        else {
            return values.stream().map(s -> obaaField + ":" + quote(s.toString())).collect(Collectors.joining(" OR "));
        }
    }
     
    
    /**
     * @param s String para ser quoted
     * @return a string delimitada por aspas
     */
    private static String quote(String s) {
        return "\"" + s + "\"";
    }

    private static <T extends Object> boolean isBlankList(List<T> format2) {
        return format2 == null || format2.isEmpty();
    }
}
