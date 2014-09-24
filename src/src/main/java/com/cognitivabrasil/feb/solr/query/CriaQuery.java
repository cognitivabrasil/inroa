package com.cognitivabrasil.feb.solr.query;

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
    public static String criaQueryCompleta(Consulta pesquisa) {
        String resultado = "";

        if (!isBlank(pesquisa.getConsulta())) {
            resultado = "obaaQueryFields:(" + pesquisa.getConsulta() + ")";
        }

        if (pesquisa.hasAuthor()) {
            resultado += " AND obaa.lifecycle.entity:(" + pesquisa.getAutor() + ")";
        }
        if (pesquisa.getHasAuditory() != null) {
            resultado += " AND obaa.accessibility.resourcedescription.primary.hasauditory:(" + pesquisa.getHasAuditory() + ")";
        }
        if (pesquisa.getHasVisual() != null) {
            resultado += " AND obaa.accessibility.resourcedescription.primary.hasvisual:(" + pesquisa.getHasVisual() + ")";
        }
        if (pesquisa.getHasText() != null) {
            resultado += " AND obaa.accessibility.resourcedescription.primary.hastext:(" + pesquisa.getHasText() + ")";
        }

        if (pesquisa.getHasTactile() != null) {
            resultado += " AND obaa.accessibility.resourcedescription.primary.hastactile:(" + pesquisa.getHasTactile() + ")";
        }

        if (pesquisa.getCost() != null) {
            resultado += " AND obaa.rights.cost:(" + pesquisa.getCost() + ")";
        }
        if (!isBlank(pesquisa.getIdioma())) {
            resultado += " AND obaa.general.language:(" + pesquisa.getIdioma() + ")";
        }

        if (!isBlank(pesquisa.getFormat())) {
            resultado += " AND obaa.technical.format:(" + pesquisa.getFormat() + ")";
        }

        if (!isBlank(pesquisa.getDifficult())) {
            resultado += " AND obaa.educational.difficulty:(" + pesquisa.getDifficult() + ")";
        }

        if (!isBlank(pesquisa.getSize())) {
            resultado += " OR obaa.technical.size:(" + pesquisa.getSize() + ")";
        }
        /**
         * FEDERACOES, REPOSITORIOS E SUBFEDERACOE *
         */

        if (!pesquisa.getFederacoes().isEmpty()) {
            resultado += " AND obaa.federacao:(";
            for (int feds : pesquisa.getFederacoes()) {
                resultado += " " + feds;
            }
            resultado += ")";
        }

        if (!pesquisa.getRepSubfed().isEmpty()) {
            resultado += " AND obaa.subFederacao:(";
            for (int subFeds : pesquisa.getRepSubfed()) {
                resultado += " " + subFeds;
            }
            resultado += ")";
        }

        if (!pesquisa.getRepositorios().isEmpty()) {
            resultado += " AND obaa.repositorio:(";
            for (int repos : pesquisa.getRepositorios()) {
                resultado += " " + repos;
            }
            resultado += ")";
        }

        if (resultado.startsWith(" OR ")) {
            resultado = resultado.substring(3);
        }
        if (resultado.startsWith(" AND ")) {
            resultado = resultado.substring(4);
        }
        
        return resultado;
    }
}
