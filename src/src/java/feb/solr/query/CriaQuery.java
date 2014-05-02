
package feb.solr.query;

import com.cognitivabrasil.feb.data.entities.Consulta;

/**
 *
 * @author Daniel Epstein
 */
public class CriaQuery {


    
    /**
     * Identifica todos os campos onde a busca deve acontecer
     *
     * @param pesquisa Devolve o String de busca pronto para ser utilizado pelo
     * SORL
     * @return
     */
    public static String criaQueryCompleta (Consulta pesquisa)
    {
           String resultado = "obaaQueryFields:" + pesquisa.getConsulta();

        if (pesquisa.hasAuthor()) {
            resultado += "AND obaa.general.author:" + pesquisa.getAutor();
        }

        if (pesquisa.getHasAuditory() != null) {
            resultado += "AND obaa.educational.resourcedescription.primary.hasauditory:" + pesquisa.getHasAuditory();
        }

        if (pesquisa.getHasVisual() != null) {
            resultado += "AND obaa.educational.resourcedescription.primary.hasvisual:" + pesquisa.getHasVisual();
        }

        if (pesquisa.getHasText() != null) {
            resultado += "AND obaa.educational.resourcedescription.primary.hastext:" + pesquisa.getHasText();
        }
        
        if (pesquisa.getHasTactile()) {
            resultado += "OR obaa.educational.resourcedescription.primary.hastactile:" + pesquisa.getHasTactile();
        }
        
        
        if (pesquisa.getCost() != null) {
            resultado += "AND obaa.rights.cost:" + pesquisa.getCost();
        }

        if (pesquisa.getIdioma()!= null) {
            resultado += "AND obaa.general.language:" + pesquisa.getIdioma();
        }
        
        
        if (pesquisa.getFormat()!= null) {
            resultado += "AND obaa.technical.format:" + pesquisa.getFormat();
        }
        
        
        if (pesquisa.getDifficult()!= null) {
            resultado += "AND obaa.educational.difficulty:" + pesquisa.getDifficult();
        }
        
        
        if (pesquisa.getSize()!= null) {
            resultado += "OR obaa.technical.size:" + pesquisa.getSize();
        }
        
        /**FEDERACOES, REPOSITORIOS E SUBFEDERACOE **/
        
        if (pesquisa.getFederacoes() != null)
        {
            resultado += "AND obaa.federacao:";
            for (int feds: pesquisa.getFederacoes())
                resultado += " "+feds;
        }
        
        
        if (pesquisa.getRepSubfed()!= null)
        {
            resultado += "AND obaa.subFederacao:";
            for (int subFeds: pesquisa.getRepSubfed())
                resultado += " "+subFeds;
        }
        
        
        if (pesquisa.getRepositorios()!= null)
        {
            resultado += "AND obaa.repositorio:";
            for (int repos: pesquisa.getRepositorios())
                resultado += " "+repos;
        }
        
        
        if (resultado.startsWith("OR ") ) {
            resultado = resultado.substring(3);
        }
        if (resultado.startsWith("AND ") ) {
            resultado = resultado.substring(4);
        }
        
        return resultado;
    
    }
}
