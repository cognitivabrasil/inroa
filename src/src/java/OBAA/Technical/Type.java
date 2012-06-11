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
package OBAA.Technical;

/**
 *
 * <div class="en">
 *
 * The technology required to use this learning
 * object, e.g., hardware, software, network, etc.
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
public class Type {
    
    private String type;
    private enum setOfTerms {
        middleware, browser, operatingSystem 
    };
    
    public Type() {
        type = "";
    }
    
    public void setType(String type) {
     try {
            setOfTerms.valueOf(type);
            this.type = type;

        } catch (IllegalArgumentException I) {
            throw new IllegalArgumentException("Type must be middleware, browser or operatingSystem ");
        }
    }
    
    public String getType() {
        return (this.type);
    }
}
