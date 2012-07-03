/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marcos Nunes <marcosn@gmail.com>
 * @author Luiz Rossi <lh.rossi@gmail.com>
 */
public class StopWords {

    private List<String> stopWords;
    private String language;

    public StopWords() {
        stopWords = new ArrayList<String>();
    }


    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<String> getStopWords() {
        return stopWords;
    }

    public void setStopWords(List<String> stopWords) {
        this.stopWords = stopWords;
    }



}
