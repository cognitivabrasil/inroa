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

package Educational;

/**
 * <div class="en">
 * Specify the educational interaction proposed by this learning object
 *
 * Adapted from http://www.portalobaa.org
 *</div>
 *
 * <div class="br">
 * Especifica a interação educacional proposta por este objeto de aprendizagem
 * e seu(s) usuários.
 *
 * Adaptado de http://www.portalobaa.org
 *</div>
 * @author LuizRossi
 */
public class Interaction {
private String interaction;

    public Interaction() {
        interaction = "";
    }

    public Interaction(String interaction) {
        this.interaction = interaction;
    }

    public void setInteraction(String interaction) {
        this.interaction = interaction;
    }

    public String getInteraction() {
        return interaction;
    }
}
