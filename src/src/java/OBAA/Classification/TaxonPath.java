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

package OBAA.Classification;

import java.util.ArrayList;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

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
public class TaxonPath {
    @Element
    private String source;
    @ElementList
    private ArrayList<Taxon> taxons;

    public TaxonPath() {
        source = "";
        taxons = new ArrayList<Taxon>();
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void addTaxons(Taxon newTaxon) {
        this.taxons.add(newTaxon);
    }

    public String getSource() {
        return source;
    }

    public ArrayList<Taxon> getTaxons() {
        return taxons;
    }

       
    
}
