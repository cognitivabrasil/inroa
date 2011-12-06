package LifeCycle;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 * <div class="en">
 *
 * The date of the contribution.
 * 
 * Example: "2001-08-23"
 * 
 * according to IEEE LOM http://ltsc.ieee.org/
 * </div>
 * <div class="br">
 *
 * Adaptado de http://www.portalobaa.org/
 * </div>
 *
 * @author LuizRossiNote
 */
public class Date {
    
    String dateUnvalidate;
    java.util.Date date;
    
    public Date() {
        dateUnvalidate="";
        date = new java.util.Date();
    }
    
    public Date(java.util.Date date){
        dateUnvalidate = date.toString();
        this.date = date;
    }
    
    public Date (String date){
        this.dateUnvalidate = date;
    }

    /**
     * 
     * @param dateUnstandarized any string, without any valitation
     */
    public void setDate(String dateUnstandarized){
        
        dateUnvalidate = dateUnstandarized;
        
    }

    public String getDateUnvalidate() {
        return dateUnvalidate;
    }
    
    
    public void validate () throws ParseException {
        //TODO: parser da string com expressao regular
        
        SimpleDateFormat input = new SimpleDateFormat ("yyyy/MM/dd");
        this.date = input.parse(dateUnvalidate);
    }
    
    
}
