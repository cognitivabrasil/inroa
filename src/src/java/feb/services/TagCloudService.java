package feb.services;

import java.util.Map;


public interface TagCloudService {

	/**
	 * Calculates a tag cloud.
	 * 
	 * Generates a Map where the keys are the tags and the value is
	 * a float representing the weight of this tag. The absolute value
	 * of the Float is implementation dependent, the relative size is what matters.
	 * @return the map
	 */
	public Map<String, Double> getTagCloud();
	
	/**
	 * Returns the sum of the weights that this tag cloud would have.
	 * @return sum of the weights
	 */
	public Float getTotalWeight();
	
	/**
	 * Utility method to generate the Map as a JavaScript object.
	 * 
	 * See {@link getTagCloud()}
	 * @return a String that can be instantiated as a JavaScript object (map).
	 */
	public String getTagCloudAsJs();
	
}
