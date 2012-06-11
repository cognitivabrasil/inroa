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
 * A reference to file(s) (meta-data) within the primary resource for which the 
 * described resource is an equivalent (if applicable)
 * 
 * Recommended: URI of another object.
 * 
 * according to IMS GLOBAL v1.0 http://www.imsglobal.org/
 *</div>
 *
 * <div class="br">
 * 
 *
 * Adaptado de http://www.portalobaa.org
 *</div>
 * @author LuizRossi
 */
public class PrimaryFile {
private String uri;

    public PrimaryFile() {
        uri = "";
    }

    public void setPrimaryFile(String uri) {
        this.uri = uri;
    }

    public String getPrimaryFile() {
        return uri;
    }
}
