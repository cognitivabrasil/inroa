/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feb.data.interfaces;

import java.util.List;

import feb.data.entities.Mapeamento;

/**
 *
 * @author marcos
 */
public interface MapeamentoDAO {
    /**
     * 
     * @return todos os mapeamentos.
     */
    List<Mapeamento> getAll();
       
    /**
     * cria ou atualiza o mapeamento.
     * 
     * @param m mapeamento para ser criado ou atualizado.
     */
    void save(Mapeamento m);
       
       /**
        * Remove um mapeamento.
        * 
        * @param m mapeamento a ser removido
        */
       void delete(Mapeamento m);
       
       /**
        * Busca por um mapeamento espec&iacute;fico.
        * 
        * @param id id do mapeamento
        * @return Mapeamento
        */
       Mapeamento get(int id);
    
}
