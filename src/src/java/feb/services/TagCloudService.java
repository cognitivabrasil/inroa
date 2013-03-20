package feb.services;

import java.util.Map;


/**
 * Tag cloud interface.
 * 
 * Configuration is implementation dependant.
 * @author paulo
 *
 */
public interface TagCloudService {

	/**
	 * Calculates a tag cloud.
	 * 
	 * Generates a Map where the keys are the tags and the value is
	 * a integer representing the weight of this tag. The absolute value
	 * of the Integer is implementation dependent, the relative size is what matters.
	 * @return the map
	 */
	public Map<String, Integer> getTagCloud();
	
	/**
	 * Returns the sum of the weights that this tag cloud would have.
	 * @return sum of the weights
	 */
	public Double getTotalWeight();
	
	/**
	 * Clears the tagcloud backend, so that it is blank.
	 */
	public void clear();
        
        /**
         * Sets the limit of results
         * @param size number of results
         */
        void setMaxSize(int size);
        
        /**
         * Returns the limit of results in the tag cloud.
         * @return number of results
         */
        public int getMaxSize();
        
        public void delete(String tag);
	
}
