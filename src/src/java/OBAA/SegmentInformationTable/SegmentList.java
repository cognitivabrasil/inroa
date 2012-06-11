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

package OBAA.SegmentInformationTable;

import java.util.ArrayList;

/**
 * <div class="en">
 *
 * according to TV Anytime http://www.tv-anytime.org/
 *</div>
 *
 * <div class="br">
 * 
 * Conjunto de informações de segmentos.
 *
 * Adaptado de http://www.portalobaa.org
 *</div>
 * @author LuizRossi
 */
public class SegmentList {
    
    private ArrayList <SegmentInformation> segment;

    public SegmentList() {
        segment = new ArrayList<SegmentInformation>();
    }
    
    public void addSegmentList(SegmentInformation newSegment) {
        segment.add(newSegment);
    }

    public ArrayList <SegmentInformation> getSegmentList() {
        return this.getSegmentList();
    }
}