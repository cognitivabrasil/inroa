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
 * Sign Language: Indicates that the described resource contains sign language 
 * alternatives, in the specified language, for text in the referenced primary 
 * resource.
 * 
 * Value Space: AmericanASL, AustralianAuslan, AustrianASQ, BrasilianLIBRAS, 
    BritishBSL,DanishDSL, FrenchLSF, GermanDGS, IrishISL, ItalianLIS, JapaneseJSL,
MalaysianMSL, MexicanLSM, NativeAmerican, NetherlandsNGT, NorwegianNSL, QuebecLSQ,
RussianRSL, SingaporeSLS, SpanishLSE, SwedishSWL or other
 *      
 * according to IMS GLOBAL v1.0 http://www.imsglobal.org/
 *</div>
 *
 * <div class="br">
 * 
 *
 * Adaptado de http://www.portalobaa.org
 *</div>
 * @author LuizRossi
 */
public class SignLanguage {
    
    private String signLanguage;
    private enum setOfTerms {AmericanASL, AustralianAuslan, AustrianASQ, BrasilianLIBRAS, 
    BritishBSL,DanishDSL, FrenchLSF, GermanDGS, IrishISL, ItalianLIS, JapaneseJSL,
MalaysianMSL, MexicanLSM, NativeAmerican, NetherlandsNGT, NorwegianNSL, QuebecLSQ,
RussianRSL, SingaporeSLS, SpanishLSE, SwedishSWL, other};

    public SignLanguage() {
        signLanguage = "";
    }

    public void SignLanguage(String signLanguage) throws IllegalArgumentException{
        
        try {
            setOfTerms.valueOf(signLanguage);
            this.signLanguage = signLanguage;

        } catch (IllegalArgumentException I) {
            throw new IllegalArgumentException("SignLanguage must be one of: AmericanASL, AustralianAuslan, AustrianASQ, BrasilianLIBRAS,BritishBSL,DanishDSL, FrenchLSF, GermanDGS, IrishISL, ItalianLIS, JapaneseJSL, MalaysianMSL, MexicanLSM, NativeAmerican, NetherlandsNGT, NorwegianNSL, QuebecLSQ, RussianRSL, SingaporeSLS, SpanishLSE, SwedishSWL or other");
        }
    }

    public String getSignLanguage() {
        return signLanguage;
    }
}
