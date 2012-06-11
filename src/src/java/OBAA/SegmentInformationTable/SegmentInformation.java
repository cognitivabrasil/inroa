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

import java.util.Collection;

/**
 * <div class="en">
 *
 * according to TV Anytime http://www.tv-anytime.org/
 *</div>
 *
 * <div class="br">
 * 
 * Agrupamento das informações de um segmento (SegmentInformationType do TV-Anytime)
 *
 * Adaptado de http://www.portalobaa.org
 *</div>
 * @author LuizRossi
 */
public class SegmentInformation {

    private String identifier;
    private String title;
    private String description; 
    private Collection <String> keyword;
    private SegmentMediaType segmentMediaType;
    private String start; //TODO: implement the mediaTimeType MPEG-7
    private String end;

    public SegmentInformation() {
        this.identifier = "";
        this.title = "";
        this.description = "";
       
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void addKeyword(String newKeyword) {
        this.keyword.add(newKeyword);
    }

    public void setSegmentMediaType(SegmentMediaType segmentMediaType) {
        this.segmentMediaType = segmentMediaType;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public String getEnd() {
        return end;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Collection<String> getKeyword() {
        return keyword;
    }

    public SegmentMediaType getSegmentMediaType() {
        return segmentMediaType;
    }

    public String getStart() {
        return start;
    }

    public String getTitle() {
        return title;
    }
    
}