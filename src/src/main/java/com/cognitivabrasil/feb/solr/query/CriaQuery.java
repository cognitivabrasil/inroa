package com.cognitivabrasil.feb.solr.query;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.solr.client.solrj.SolrQuery;

import com.cognitivabrasil.feb.data.entities.Consulta;

import static org.apache.commons.lang3.StringUtils.isBlank;

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

        if (pesquisa.getHasAuditory() != null) {
            query.addFilterQuery("{!tag=hasauditory}" + 
            		orQueryQuoted("obaa.accessibility.resourcedescription.primary.hasauditory", pesquisa.getHasAuditory()));
        }
        if (pesquisa.getHasVisual() != null) {
            query.addFilterQuery("{!tag=hasvisual}" + 
            		orQueryQuoted("obaa.accessibility.resourcedescription.primary.hasvisual", pesquisa.getHasVisual()));
        }
        if (pesquisa.getHasText() != null) {
            query.addFilterQuery("{!tag=hastext}" + 
            		orQueryQuoted("obaa.accessibility.resourcedescription.primary.hastext", pesquisa.getHasText()));
        }

        if (pesquisa.getHasTactile() != null) {
            query.addFilterQuery("{!tag=hastactile}" + 
            		orQueryQuoted("obaa.accessibility.resourcedescription.primary.hastactile", pesquisa.getHasTactile()));
        }

        if (!isBlankList(pesquisa.getCost())) {
            query.addFilterQuery("{!tag=cost}" + 
            		orQueryQuoted("obaa.rights.cost", pesquisa.getCost()));
        }
        
        if (!isBlank(pesquisa.getIdioma())) {
            query.addFilterQuery("obaa.general.language:(" + pesquisa.getIdioma() + ")");
        }

        if (!isBlankList(pesquisa.getFormat())) {
            query.addFilterQuery("{!tag=format}" + orQueryQuoted("obaa.technical.format", pesquisa.getFormat()));
        }

        if (!isBlankList(pesquisa.getDifficulty())) {
            query.addFilterQuery("{!tag=difficulty}" + orQueryQuoted("obaa.educational.difficulty", pesquisa.getDifficulty()));
        }
        
        if (!isBlankList(pesquisa.getAgeRangeInt())) {
            query.addFilterQuery("{!tag=agerangeint}" + orQueryQuoted("obaa.educational.typicalagerangeint", pesquisa.getAgeRangeInt()));
        }

        
        

        /**
         * FEDERACOES, REPOSITORIOS E SUBFEDERACOE *
         */

        if (!pesquisa.getFederacoes().isEmpty()) {
            String q = "obaa.federacao:(";
            for (int feds : pesquisa.getFederacoes()) {
                q += " " + feds;
            }
            q += ")";
            query.addFilterQuery(q);
        }

        if (!pesquisa.getRepSubfed().isEmpty()) {
            String q = "obaa.subFederacao:(";
            for (int subFeds : pesquisa.getRepSubfed()) {
                q += " " + subFeds;
            }
            q += ")";
            query.addFilterQuery(q);
        }

        if (!pesquisa.getRepositorios().isEmpty()) {
            String q = " obaa.repositorio:(";
            for (int repos : pesquisa.getRepositorios()) {
                q += " " + repos;
            }
            q += ")";
            query.addFilterQuery(q);
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
        return values.stream().map(s -> obaaField + ":" + quote(s.toString())).collect(Collectors.joining(" OR "));
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
