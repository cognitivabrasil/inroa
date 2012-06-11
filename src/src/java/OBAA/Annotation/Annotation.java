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

package OBAA.Annotation;

import java.util.Date;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * <div class="en">
 *
 * This category provides comments on the educational use of this learning 
 * object, and information on when and by whom the comments were created.
 * This category enables educators to share their assessments of learning 
 * objects, suggestions for use, etc.
 * 
 * according to IEEE LOM http://ltsc.ieee.org/
 *</div>
 *
 * <div class="br">
 * Grupo de metadados com comentários sobre o uso educacional do objeto.
 *
 * Adaptado de http://www.portalobaa.org
 *</div>
 * @author LuizRossi
 */
@Root
public class Annotation {
    @Element
    private String entity;
    @Element
    private Date date;
    @Element
    private String description;

    public Annotation() {
       entity = "";
       date = new Date();
       description = "";
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getEntity() {
        return entity;
    }
}
