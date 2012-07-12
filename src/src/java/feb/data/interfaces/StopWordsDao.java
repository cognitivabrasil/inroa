/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feb.data.interfaces;

import java.util.List;

/**
 *
 * @author Marcos Nunes <marcosn@gmail.com>
 */
public interface StopWordsDao {

    /**
     * 
     * @param language language of the stopword list
     * @return All the stopWords of the language in parameter
     */
    public List<String> getStopWords(String language);
    

}
