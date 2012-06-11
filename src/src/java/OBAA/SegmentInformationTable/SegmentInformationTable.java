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

/**
 * <div class="en">
 *
 * according to TV Anytime http://www.tv-anytime.org/
 *</div>
 *
 * <div class="br">
 * 
 * Grupo que conterá o conjunto de informações de segmentação dos objetos de 
 * aprendizagem e de grupos de segmentos dos objetos de aprendizagem.
 * 
 *
 * Adaptado de http://www.portalobaa.org
 *</div>
 * @author LuizRossi
 */
public class SegmentInformationTable {

    private SegmentList segmentList;
    private SegmentGroupList segmentGroupList;

    public void setSegmentGroupList(SegmentGroupList segmentGroupList) {
        this.segmentGroupList = segmentGroupList;
    }

    public SegmentGroupList getSegmentGroupList() {
        return segmentGroupList;
    }    
    

    public SegmentInformationTable() {
        segmentList = new SegmentList();
    }

    public void setSegmentList(SegmentList segmentList) {
        this.segmentList = segmentList;
    }

    public SegmentList getSegmentList() {
        return segmentList;
    }
}