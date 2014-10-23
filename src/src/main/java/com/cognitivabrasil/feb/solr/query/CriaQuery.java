package com.cognitivabrasil.feb.solr.query;

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
     * @param pesquisa Devolve o String de busca pronto para ser utilizado pelo
     * SORL
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
            query.addFilterQuery("obaa.accessibility.resourcedescription.primary.hasauditory:(" + pesquisa.getHasAuditory() + ")");
        }
        if (pesquisa.getHasVisual() != null) {
            query.addFilterQuery("obaa.accessibility.resourcedescription.primary.hasvisual:(" + pesquisa.getHasVisual() + ")");
        }
        if (pesquisa.getHasText() != null) {
            query.addFilterQuery("obaa.accessibility.resourcedescription.primary.hastext:(" + pesquisa.getHasText() + ")");
        }

        if (pesquisa.getHasTactile() != null) {
            query.addFilterQuery("obaa.accessibility.resourcedescription.primary.hastactile:(" + pesquisa.getHasTactile() + ")");
        }

        if (pesquisa.getCost() != null) {
            query.addFilterQuery("obaa.rights.cost:(" + pesquisa.getCost() + ")");
        }
        if (!isBlank(pesquisa.getIdioma())) {
            query.addFilterQuery("obaa.general.language:(" + pesquisa.getIdioma() + ")");
        }

        if (!isBlank(pesquisa.getFormat())) {
            query.addFilterQuery("obaa.technical.format:\"" + pesquisa.getFormat() + "\"");
        }

        if (!isBlank(pesquisa.getDifficult())) {
            query.addFilterQuery("obaa.educational.difficulty:(" + pesquisa.getDifficult() + ")");
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
}
