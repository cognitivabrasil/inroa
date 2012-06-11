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
 * according to OBAA http://www.portalobaa.org/
 * </div>
 * <div class="br">
 *
 * Lista de plataformas digitais para as quais o Objeto de Aprendizagem está 
 * previsto. Atualmente estão previstos três tipos básicos de plataformas 
 * digitais para disponibilização de OAs: Web, DTV e Mobile.
 * Este item não é obrigatório, para manter a compatibilidade com o LOM, mas é 
 * recomendado seu preenchimento.
 * 
 * Adaptado de http://www.portalobaa.org/
 * </div>
 *
 * @author LuizRossiNote
 */
public class SupportedPlatform {
    
    private String supportedPlatform;
    
     private enum setOfTerms {
       Mobile, DTV, Web
    }
    
    public SupportedPlatform() {
        supportedPlatform = "";
    }
    
    public void setSupportedPlatform(String supportedPlatform) {
         try {
            setOfTerms.valueOf(supportedPlatform);
            this.supportedPlatform = supportedPlatform;

        } catch (IllegalArgumentException I) {
            throw new IllegalArgumentException("Supported Platform must be one of: Mobile, DTV, Web");      
        }
    }
    
    public String getSupportedPlatform() {
        return (supportedPlatform);
    }
}
