/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitivabrasil.feb.data.services;

import java.util.List;

import com.cognitivabrasil.feb.data.entities.Mapeamento;

/**
 * 
 * Marcos Freitas Nunes <marcos@cognitivabrasil.com.br>
 */
public interface MappingService {
	/**
	 * 
	 * @return All mapping.
	 */
	List<Mapeamento> getAll();

	/**
	 * Save or update the mapping
	 * 
	 * @param m mapping to be saved or updated
	 */
	void save(Mapeamento m);

	/**
	 * Delete the mapping.
	 * 
	 * @param m mapping to be deleted
	 */
	void delete(Mapeamento m);

	/**
	 * Get the mapping
	 * 
	 * @param id id number
	 * @return mapping with the specified id
	 */
	Mapeamento get(int id);

	/**
	 * Checks if a mapping with the given name exists.
	 * 
	 * @param name the name
	 * @return true, if it exists
	 */
	boolean exists(String name);

}
