/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OBAA.Technical;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 *
 * <div class="en">
 * 
 * Time a continuous learning object takes when played at intended speed.
 * 
 * NOTE:--This data element is especially useful for sounds, movies or animations.
 *
 * according to IEEE LOM http://ltsc.ieee.org/
 * </div>
 * 
 * 
 * <div class="br">
 *
 * Adaptado de http://www.portalobaa.org/
 * </div>
 *
 * @author LuizRossiNote
 */
public class Duration {
    
    private Date duration;
    private DateFormat dateFormat;
    private String format;
    
    public Duration() {
        duration = new Date();
        format = "'PT'h'H'm'M's'S'";
        dateFormat = new SimpleDateFormat(format);        
    }
    
    public void setDuration(String duration) throws IllegalArgumentException {
        
        try {
            this.duration = dateFormat.parse(duration);
            

        } catch (ParseException I) {
            throw new IllegalArgumentException("The Data format must be PThHmMsS where 'h' is the hours, 'm' is the minues and 's' is the seconds.");
        }
    }
    
    public Date getDuration() {
        return duration;

    }
    
    //TODO: remover comentário de testes
//    public static void main(String[] args) {
//        Duration d = new Duration();
//        d.setDuration("PT1H0M12S");
//        
//        Date d1 = d.getDuration();
//        DateFormat df3;
//        String f3 = "hh:mm:ss";
//        df3 = new SimpleDateFormat(f3);
//        System.out.println(df3.format(d1));
//    }
}
