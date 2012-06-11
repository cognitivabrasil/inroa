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
 * <div class="br">
 *
 * Container para a especificação de serviços relacionados a este objeto.
 * 
 * Adaptado de http://www.portalobaa.org/
 * </div>
 *
 * @author LuizRossiNote
 */
public class Service {
    
    private String name; //O nome do serviço. Pode ser um conceito definido pela ontologia associada (metadado número 4.10.6).
    private String type; //O tipo do serviço. Também pode ser um conceito definido pela ontologia associada (metadado número 4.10.6).
    private boolean provides; //Define se o objeto fornece ou solicita o serviço. Este metadado define o uso do metadado location (4.10.8.1). True: o serviço é fornecido pelo objeto False: o serviço é solicitado pelo objeto
    private boolean essential; //Define se o serviço é obrigatório (essencial) ou opcional em relação à correta execução do objeto. True: o serviço é obrigatório para que o objeto funcione corretamente False: o serviço é opcional e o objeto funcionará mesmo sem sua disponibilidade
    private Collection<String> protocol; //O nome do protocolo utilizado para comunicação com o serviço. É uma informação dependente de cada serviço. O valor também pode ser associado a um conceito ou indivíduo presente na ontologia (4.10.6).
    private Collection<Ontology> ontology; //Ontologias associadas a este serviço. Geralmente este tipo de ontologia fornece uma especificação formal do contexto do serviço.
    private Collection<String> language; //A linguagem utilizada para a comunicação com o serviço.
    private Collection<String> details;
    
    
    public Service() {

        name = "";
        type = "";
        provides = true;
        essential = false;
        
    }

}
