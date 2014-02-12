/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feb.data.daos;


import feb.data.entities.PadraoMetadados;
import feb.data.interfaces.PadraoMetadadosDAO;


/**
 *
 * @author paulo
 */
public class PadraoMetadadosHibernateDAO extends AbstractNamedHibernateDAO<PadraoMetadados>  implements PadraoMetadadosDAO {


    public PadraoMetadadosHibernateDAO() {
    	setClazz(PadraoMetadados.class);
    }
    
}
