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
 * The presentation or display of most resources can be transformed if 
 * appropriate formats, mark-up or software development practices are used to 
 * create the resources. This requires that the content and content-structure 
 * are independent of the presentation of the content. The means to achieve this 
 * is to keep presentational and structural mark-up separate (see W3C guidelines) 
 * Display transformability specifies how the display or presentation of a 
 * resource (e.g., font colour, font size, background colour, layout, image size) 
 * is amenable to transformation. It can be determined using a number of 
 * available Web content evaluation and repair tools. E.g. CSS, XSLT, ...
 * 
 * according to IMS GLOBAL http://www.imsglobal.org/specificationdownload.cfm
 *</div>
 *
 * <div class="br">
 * 
 *
 * Adaptado de http://www.portalobaa.org
 *</div>
 * @author LuizRossi
 */
public class DisplayTransformability {
    
    private String displayTranformabulity;

    public DisplayTransformability() {
        displayTranformabulity = "";
    }

       public void setControlFlexibility(String displayTranformabulity) {
        this.displayTranformabulity = displayTranformabulity;
    }


    public String getDisplayTransformability() {
        return displayTranformabulity;
    }
}
