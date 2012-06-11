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
 * Some resources can only be controlled using a mouse or mouse equivalent. This 
 * means users who do not have a mouse or cannot control a mouse cannot control 
 * such resources. If all of the functions controlled by the mouse can also be 
 * controlled using keyboard commands, users will have access to the same 
 * functionality using a keyboard or any number of other keyboard-emulating 
 * devices (e.g., scanning systems, coding systems, enlarged keyboards, etc.).
 * Some interfaces require many sequential actions to navigate to a desired 
 * control, such as a button. This can be difficult for some users. Interfaces 
 * that allow reconfiguration of the actions required to access specific 
 * controls, buttons, links or input fields enable the optimization of the 
 * control method.
 * Control flexibility describes how the resource supports a choice of methods 
 * of controlling the resource functions. It is anticipated that this will be 
 * determined using accessibility-checking tools.
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
public class EarlStatement {

    private DisplayTransformability displayTransformability;
    private ControlFlexibility controlFlexibility;


    public EarlStatement() {
       
    }

}
