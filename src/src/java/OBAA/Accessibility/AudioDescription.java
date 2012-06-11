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
 * Indicates that the described resource is an audio description for the 
 * referenced primary resource.
 * 
 * Value Space: Standard or expanded
 * 
 * according to IEEE LOM http://ltsc.ieee.org/
 *</div>
 *
 * <div class="br">
 * 
 *
 * Adaptado de http://www.portalobaa.org
 *</div>
 * @author LuizRossi
 */
public class AudioDescription {
    
    private String audioDescriptionType;
    private enum setOfTerms {standard, expanded};
    private String language;
    

    public AudioDescription() {
        audioDescriptionType = "standard";
        language = "";
    }


    public void setAudioDescription(String audioDescriptionType) throws IllegalArgumentException{
        
        try {
            setOfTerms.valueOf(audioDescriptionType);
            this.audioDescriptionType = audioDescriptionType;

        } catch (IllegalArgumentException I) {
            throw new IllegalArgumentException("AudioDescriptionType must be standard or expanded.");
        }
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    
    public String getAudioDescriptionType() {
        return audioDescriptionType;
    }

    public String getLanguage() {
        return language;
    }
    
}
