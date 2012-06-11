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
 * Control flexibility describes how the resource supports a choice of methods 
 * of controlling the resource functions. It is anticipated that this will be 
 * determined using accessibility-checking tools.
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
public class ControlFlexibility {
private String controlFlexibility;

    public ControlFlexibility() {
        controlFlexibility = "";
    }

    public void setControlFlexibility(String controlFlexibility) {
        this.controlFlexibility = controlFlexibility;
    }

    public String getControlFlexibility() {
        return controlFlexibility;
    }
}
