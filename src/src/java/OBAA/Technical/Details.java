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
 * according to IEEE LOM http://ltsc.ieee.org/
 * </div>
 * <div class="br">
 *
 * Adaptado de http://www.portalobaa.org/
 * </div>
 *
 * @author LuizRossiNote
 */
public class Details {
    
    private String details; //Especificação de detalhes do serviço fora do escopo de metadados (ex.: parâmetros).
    private String location; //A URL, URI ou qualquer outra especificação para acessar o serviço. (relacionado ao item 4.3 do IEEE-LOM).
    
    public Details() {
        details = "";
        location = "";
    }

    public void setDetails(String details) {
        this.details = details;
    }
    
    public String getDetails() {
        return (this.details);
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }
    
    
}
