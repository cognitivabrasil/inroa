/**
 * Copyright 2006 OCLC Online Computer Library Center Licensed under the Apache
 * License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or
 * agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ORG.oclc.oai.server.crosswalk;

import java.util.Properties;
import ORG.oclc.oai.server.verb.CannotDisseminateFormatException;
import feb.Documento;
import feb.Objeto;

/**
 * Convert native "item" to oai_dc. In this case, the native "item"
 * is assumed to already be formatted as an OAI <record> element,
 * with the possible exception that multiple metadataFormats may
 * be present in the <metadata> element. The "crosswalk", merely
 * involves pulling out the one that is requested.
 */
public class XML2oai_obaa extends Crosswalk {
	
    /**
     * The constructor assigns the schemaLocation associated with this crosswalk. Since
     * the crosswalk is trivial in this case, no properties are utilized.
     *
     * @param properties properties that are needed to configure the crosswalk.
     */
    public XML2oai_obaa(Properties properties) {
	super("http://ltsc.ieee.org/xsd/LOM http://ltsc.ieee.org/xsd/lomv1.0/lom.xsd");
    }

    /**
     * Can this nativeItem be represented in DC format?
     * @param nativeItem a record in native format
     * @return true if DC format is possible, false otherwise.
     */
    public boolean isAvailableFor(Object nativeItem) {
	//String fullItem = (String)nativeItem;
	//if ((fullItem.indexOf(elementStart)) >= 0)
	 //   return true;
	//return false;
        return true;
    }
    
    private static String ifNotNull(String element, String value) {
        if(value == null) {
            return "";
        }
        else if (value.trim().equals("")) {
            return "";
        }
        else {
            return "<obaa:" + element + ">" + value + "</obaa:" + element + ">"; 
        }
    }
        private static String ifNotNull(String element, String value, String namespace) {
        if(value == null) {
            return "";
        }
        else if (value.trim().equals("")) {
            return "";
        }
        else {
            return "<obaa:" + element + " " + namespace + ">" + value + "</obaa:" + element + ">"; 
        }
    }

    /**
     * Perform the actual crosswalk.
     *
     * @param nativeItem the native "item". In this case, it is
     * already formatted as an OAI <record> element, with the
     * possible exception that multiple metadataFormats are
     * present in the <metadata> element.
     * @return a String containing the XML to be stored within the <metadata> element.
     * @exception CannotDisseminateFormatException nativeItem doesn't support this format.
     */
    public String createMetadata(Object nativeItem)
	throws CannotDisseminateFormatException {
	Documento m  = (Documento)nativeItem;

	StringBuffer s = new StringBuffer(200);
            s.append("<obaa:obaa xmlns:obaa=\"http://ltsc.ieee.org/xsd/LOM\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://ltsc.ieee.org/xsd/LOM http://ltsc.ieee.org/xsd/lomv1.0/lom.xsd\">");
                s.append("<obaa:general xmlns:obaa=\"http://ltsc.ieee.org/xsd/LOM\">");
                    s.append("<obaa:identifier>");
                        s.append("<obaa:entry>"  + m.getObaaEntry() + "</obaa:entry>");
                    s.append("</obaa:identifier>");
		    for(Objeto o : m.getObjetos()) {
			    s.append("<obaa:" + o.getAtributo() + ">" + o.getValor() + "</obaa:" + o.getAtributo() + ">");
		    }
//                    s.append(ifNotNull("title", m.getTitle()));
//                    s.append(ifNotNull("language", m.getLanguage()));
//                    s.append(ifNotNull("coverage", m.getCoverage()));
 //                   s.append(ifNotNull("keyword", m.getSubject()));
                s.append("</obaa:general>");
                //
 //               if((m.getCreator() != null) && !(m.getCreator().trim().equals(""))) {
  //                  s.append(ifNotNull("lifecycle", ifNotNull("contribute", 
   //                         ifNotNull("role", "author") + ifNotNull("entity", m.getCreator()) + 
   //                         ifNotNull("date", m.getDate().toString())), "xmlns:obaa=\"http://ltsc.ieee.org/xsd/LOM\""));
    //            }
    //            s.append(ifNotNull("rights", ifNotNull("description", m.getRights()),"xmlns:obaa=\"http://ltsc.ieee.org/xsd/LOM\""));
   //             s.append(ifNotNull("technical", 
   //                     ifNotNull("format", m.getFormat()) +
    //                    ifNotNull("location", m.getLocation()),"xmlns:obaa=\"http://ltsc.ieee.org/xsd/LOM\""));
     //           s.append(ifNotNull("educational", ifNotNull("learningresourcetype", m.getType()),"xmlns:obaa=\"http://ltsc.ieee.org/xsd/LOM\""));
                
     //           if((m.getSource() != null) && !(m.getSource().trim().equals(""))) {
      //              s.append(ifNotNull("relation", ifNotNull("kind", "Baseado em") +
       //                     ifNotNull("identifier", m.getSource() +
        //                    ifNotNull("description", m.getDescription())),"xmlns:obaa=\"http://ltsc.ieee.org/xsd/LOM\""));
       //         }
        //        else {
         //           s.append(ifNotNull("relation", ifNotNull("description", m.getDescription()),"xmlns:obaa=\"http://ltsc.ieee.org/xsd/LOM\""));
        //        }
                
                    //"<dc:date>" + m.getDate() + "</dc:date>" +
                    //"<dc:creator>" + m.getCreator() + "</dc:creator>" +
                    //"<dc:identifier>" + new Integer(m.getLocal_identifier()) + "</dc:identifier>" +
              s.append("</obaa:obaa>");
                     
	return s.toString();
    }
}
