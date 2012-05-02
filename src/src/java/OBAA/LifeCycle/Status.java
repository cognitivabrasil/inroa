/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OBAA.LifeCycle;

import org.simpleframework.xml.Text;

/**
 *
 * <div class="en">
 *
 * The completion status or condition of this learning object.
 * 
 * Types: 
 * draft
 * final or finalized
 * revised
 * unavailable
 *   NOTE:--When the status is "unavailable" it means that the learning
 * object itself is not available.
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
public class Status {
    
    @Text(required=false)
    private String status;
    private enum setOfTerms {draft,finalized, revised,unavailable};
    
    public Status() {
    }
    
    public Status(String status) throws IllegalArgumentException {
        this.setStatus(status);
    }    



    public void setStatus(String status) throws IllegalArgumentException {
	status = status.toLowerCase();
        try {
            if (status.equals("final")) status = "finalized";
            setOfTerms.valueOf(status);
            this.status = status;

        } catch (IllegalArgumentException I) {
            throw new IllegalArgumentException("Satus must be one of: draft,final, revised or unavailable");

        }
    }
    public String getStatus() {
        return this.status;
    }
    
    public String toString() {
        return this.status;
    }

}
