package OBAA.LifeCycle;

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

    java.util.Date date;
    
    public Date() {
        date = new java.util.Date();

    }
    
    public Date(java.util.Date date){
        this.date = date;
    }
    
    public Date (String date) throws ParseException{
        this.setDate(date);
        
    }

    public void setDate(String date) throws ParseException {
        
        //TODO: verificar como fica no FEB e fazer todas as conversões necessárias
        
        SimpleDateFormat input = new SimpleDateFormat ("yyyy/MM/dd");
        this.date = input.parse(date);
        
    }
    
    
}
