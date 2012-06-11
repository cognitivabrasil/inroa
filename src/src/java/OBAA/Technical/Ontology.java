/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OBAA.Technical;

/**
 *
 * <div class="en">
 *
 * </div>
 * <div class="br">
 *
 * Ontologias associadas a este serviço. Geralmente este tipo de ontologia fornece uma especificação formal do contexto do serviço.
 * 
 * Adaptado de http://www.portalobaa.org/
 * </div>
 *
 * @author LuizRossiNote
 */
public class Ontology {
    
    private String name; //Ex. “OBAA Ontology”, “WordNet”,“GUMO”
    private String language;
    private String location;
    
    public Ontology() {
        name = "";
        language = "";
        location = "";
    }

    public void setName(String name) {
        this.name = name;
    }
      
    public void setLanguage(String language) {
        this.language = language;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }
    
    public String getLanguage() {
        return (this.language);
    }

    public String getLocation() {
        return location;
    }
    
}
