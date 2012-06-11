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
import java.util.Collection;

/**
 * <div class="en">
 *
 * according to TV Anytime http://www.tv-anytime.org/
 *</div>
 *
 * <div class="br">
 * 
 * Conjunto de informações do grupo de segmentos (SegmentGroupInformationType do TV-Anytime).
 *
 * Adaptado de http://www.portalobaa.org
 *</div>
 * @author LuizRossi
 */
public class SegmentGroupInformation {
    
    private String identifier;
    private GroupType groupType;
    private String title;
    private String description;
    private Collection<String> keywords;
    private Segments segments;

    public SegmentGroupInformation() {
        identifier = "";
        groupType = new GroupType();
        title = "";
        description = "";
        segments = new Segments();
    }

    public void addKeyword(String newKeyword) {
        this.keywords.add(newKeyword);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGroupType(GroupType groupType) {
        this.groupType = groupType;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setKeywords(Collection<String> keywords) {
        this.keywords = keywords;
    }

    public void setSegments(Segments segments) {
        this.segments = segments;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public GroupType getGroupType() {
        return groupType;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Collection<String> getKeywords() {
        return keywords;
    }

    public Segments getSegments() {
        return segments;
    }

    public String getTitle() {
        return title;
    }
}