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
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * <div class="en">
 *
 * This category describes where this learning object falls within a particular 
 * classification system. To define multiple classifications, there may be 
 * multiple instances of this category.
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
@Root
public class Classification {
    @Element (name = "Purpose")
    private Purpose purpose;
    @ElementList (name = "TaxonPath")
    private Set <TaxonPath> taxonPath;
    @Element (name = "Description")
    private String description;
    @ElementList (name = "Keywords")
    private ArrayList<String> keywords;
    
    public Classification() {
        
        purpose = new Purpose();
        description = "";
        keywords = new ArrayList<String>();
        taxonPath = new TreeSet<TaxonPath>();
     
    }

    public void setPurpose(Purpose purpose) {
      this.purpose = purpose;
    }
    public void setPurpose(String purpose) throws IllegalArgumentException {        
        this.purpose.setPurpose(purpose);
    }
    public Purpose getPurpose() {
        return purpose;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTaxonPath(Set<TaxonPath> taxonPath) {
        this.taxonPath = taxonPath;
    }
    public Set<TaxonPath> getTaxon(){
        return taxonPath;
    
    }
    public void addTaxonPath (TaxonPath newTaxonPath){        
        this.taxonPath.add(newTaxonPath);
    }

    public void addKeyword(String keyword) {
        this.keywords.add(keyword);
    }

   
    
}
