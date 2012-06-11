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
public class Primary {

    private boolean hasVisual;
    private boolean hasAudititory;
    private boolean hasText;
    private boolean hasTacticle;
    private EarlStatement earlStatement;
    private EquivalentResource equivalentResource;
    

    public Primary() {
        hasVisual = false;
        hasAudititory = false;
        hasText = false;
        hasTacticle = false;
        earlStatement = new EarlStatement();
        equivalentResource = new EquivalentResource();
       }

    public void setEarlStatement(EarlStatement earlStatement) {
        this.earlStatement = earlStatement;
    }

    public void setEquivalentResource(EquivalentResource equivalentResource) {
        this.equivalentResource = equivalentResource;
    }

    public void setHasAudititory(boolean hasAudititory) {
        this.hasAudititory = hasAudititory;
    }

    public void setHasTacticle(boolean hasTacticle) {
        this.hasTacticle = hasTacticle;
    }

    public void setHasText(boolean hasText) {
        this.hasText = hasText;
    }

    public void setHasVisual(boolean hasVisual) {
        this.hasVisual = hasVisual;
    }

    public EarlStatement getEarlStatement() {
        return earlStatement;
    }

    public EquivalentResource getEquivalentResource() {
        return equivalentResource;
    }
    
}
