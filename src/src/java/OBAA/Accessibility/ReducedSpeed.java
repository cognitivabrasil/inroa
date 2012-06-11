/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OBAA.Accessibility;

/**
 *
 * <div class="en">
 * 
 * Indicates that the caption is a reduced rate level caption.
 *
 * according to IMS GLOBAL v1.0 http://www.imsglobal.org/
 * </div>
 * <div class="br">
 *
 * Adaptado de http://www.portalobaa.org/
 * </div>
 *
 * @author LuizRossiNote
 */
public class ReducedSpeed {
    
    private boolean reducedSpeed;
    int captionRate;
    
    public ReducedSpeed() {
        reducedSpeed = false;
        captionRate = 120;
        
    }
    
    public void setReducedSpeedRate(int captionRate)  throws IllegalArgumentException{
        this.reducedSpeed = true;
        
        if (captionRate<1 || captionRate>300)
            throw new IllegalArgumentException("Caption rate must be an integer beetween 1 and 300");
        
        
        this.captionRate = captionRate;
    }
    
    public int getReducedSpeedRate() {
        return (this.captionRate);
    }
    
    public boolean HasReducedSpeed (){
        return reducedSpeed;
    }
}
