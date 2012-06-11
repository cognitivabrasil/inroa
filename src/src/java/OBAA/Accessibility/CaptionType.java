/*
 * OBAA - Agent Based Leanring Objetcs
 *
 * This file is part of Obaa.
 * Obaa is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Obaa is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Obaa. If not, see <http://www.gnu.org/licenses/>.
 */

package OBAA.Accessibility;

/**
 * <div class="en">
 *
 * Indicates that the described resource contains a text caption for the 
 * referenced primary resource.
 * 
 * Verbatim: Indicates that the caption is a verbatim caption. Mutually 
 * exclusive with reducedReadingLevel
 * 
 * Enhanced Caption: Indicates that the caption is an enhanced caption.
 * 
 * according to IMS GLOBAL v1.0 http://www.imsglobal.org/
 * 
 *</div>
 *
 * <div class="br">
 * 
 *
 * Adaptado de http://www.portalobaa.org
 *</div>
 * @author LuizRossi
 */
public class CaptionType {

    private String language;
    private boolean verbatim;
    private boolean reducedReadingLevel;
    private ReducedSpeed reducedSpeed;
    private boolean enhancedCaption;

    public CaptionType() {

        language = "";
        verbatim = true;
        reducedReadingLevel = false;
    }

    /**
     * If is set verbatim true, the reducedReadingLevel is set false and viceverse, due to the muttualy exclusion of these two params;
     * @param verbatim 
     */
    
    public void setVerbatim(boolean verbatim) {
        
        this.reducedReadingLevel = !verbatim;            
        this.verbatim = verbatim;
    }

    /**
     * If is set Reduced Reading Level true, the verbatim is set false and viceverse, due to the muttualy exclusion of these two params;

     * @param reducedReadingLevel 
     */
    public void setReducedReadingLevel(boolean reducedReadingLevel) {
        this.verbatim = !reducedReadingLevel;
        this.reducedReadingLevel = reducedReadingLevel;
    }

    public void setEnhancedCaption(boolean enhancedCaption) {
        this.enhancedCaption = enhancedCaption;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setReducedSpeed(ReducedSpeed reducedSpeed) {
        this.reducedSpeed = reducedSpeed;
    }

    public String getLanguage() {
        return language;
    }

    public ReducedSpeed getReducedSpeed() {
        return reducedSpeed;
    }
    
    public boolean getVerbatim () {
        return this.verbatim;    
    }
    
    public boolean getReducedReadingLevel () {
        return this.reducedReadingLevel;    
    }
    
    public boolean getEnhancedCaption () {
        return this.enhancedCaption;    
    }

}
