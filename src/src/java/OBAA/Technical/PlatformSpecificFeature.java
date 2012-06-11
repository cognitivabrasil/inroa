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

import java.util.Collection;

/**
 *
 * <div class="en">
 *
 * according to IEEE LOM http://ltsc.ieee.org/
 * </div>
 * 
 * 
 * <div class="br">
 *
 * Conjunto de características técnicas das mídias específicas desenvolvidas 
 * para cada plataforma para a qual o Objeto de Aprendizagem foi previsto.
 * Deverá ser criado um registro deste conjunto de metadados para cada plataforma 
 * suportada pelo OA e cujas informações técnicas diferem das informações
 * técnicas já descritas no item (Technical), ou seja, apenas quando mídias 
 * diferentes forem disponibilizadas para cada plataforma.
 * 
 * Adaptado de http://www.portalobaa.org/
 * </div>
 *
 * @author LuizRossiNote
 */
public class PlatformSpecificFeature {
    
    private Type platformType;
    private Collection<String> specificFormats;
    private String specificSize;
    private String specificLocation;
    private Collection<Requirement> specificRequirements;
    private Collection<OrComposite> specificOrComposites;
    private String specificInstallationRemarks;
    private String specificOtherPlatformRequirements;
    
    
    public PlatformSpecificFeature() {
        
        platformType = new Type();        
        specificSize = "";
        specificLocation = "";
        specificInstallationRemarks = "";
        specificOtherPlatformRequirements = "";     
    }

/**
 *
 * <div class="en">
 *
 * A string that is used to access this learning object. It may be a location 
 * (e.g., Universal Resource Locator), or a method that resolves to a location 
 * (e.g., Universal Resource Identifier). The first element of this list shall 
 * be the preferable location.
 * 
 * NOTE:--This is where the learning object described by this metadata instance 
 * is physically located.
 * 
 * Example: "http://host/id"
 * 
 * according to IEEE LOM http://ltsc.ieee.org/
 * </div>
 * <div class="br">
 *
 * Uma sequência de caracteres utilizada para acessar a mídia criada 
 * especialmente para utilização na plataforma especificada no item 4.9.1. Segue 
 * as mesmas definições e regras do item 4.3, porém aplicadas à mídia específica.
 * 
 * Adaptado de http://www.portalobaa.org/
 * </div>
 */
    public void setSpecificLocation(String specificLocation) {
        this.specificLocation = specificLocation;
    }

    public void setPlatformType(Type platformType) {
        this.platformType = platformType;
    }

    public void setSpecificInstallationRemarks(String specificInstallationRemarks) {
        this.specificInstallationRemarks = specificInstallationRemarks;
    }

    public void setSpecificOtherPlatformRequirements(String specificOtherPlatformRequirements) {
        this.specificOtherPlatformRequirements = specificOtherPlatformRequirements;
    }

    public void setSpecificSize(String specificSize) {
        this.specificSize = specificSize;
    }

    public void addSpecificFormats(String specificFormat) {
        this.specificFormats.add(specificFormat);
    }

    public void setSpecificOrComposites(OrComposite specificOrComposite) {
        this.specificOrComposites.add(specificOrComposite);
    }

    public void addSpecificRequirements(Requirement newSpecificRequirement) {
        this.specificRequirements.add(newSpecificRequirement);
    }

    public Type getPlatformType() {
        return platformType;
    }

    public Collection<String> getSpecificFormats() {
        return specificFormats;
    }

    public String getSpecificInstallationRemarks() {
        return specificInstallationRemarks;
    }

    public String getSpecificLocation() {
        return specificLocation;
    }

    public Collection<OrComposite> getSpecificOrComposites() {
        return specificOrComposites;
    }

    public String getSpecificOtherPlatformRequirements() {
        return specificOtherPlatformRequirements;
    }

    public Collection<Requirement> getSpecificRequirements() {
        return specificRequirements;
    }

    public String getSpecificSize() {
        return specificSize;
    }
    
}

