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
 * Grouping of multiple requirements. The composite requirement is satisfied 
 * when one of the component requirements is satisfied, i.e., the logical 
 * connector is OR.
 * 
 * according to IEEE LOM http://ltsc.ieee.org/
 * </div>
 * 
 * 
 * <div class="br">
 *
 * Adaptado de http://www.portalobaa.org/
 * </div>
 *
 * @author LuizRossiNote
 */
public class OrComposite {

    private Type type;
    private Name name;
    private String minimumVersion;
    private String maximumVersion;
    
    public OrComposite() {
      type = new Type();
      name = new Name();
      minimumVersion = "";
      maximumVersion = "";
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setName(Name name) throws IllegalArgumentException{
        
        
        if (this.type.getType().equals("middleware")) {
            
            this.name = name;
            
        } else if (this.type.getType().equals("operatingSystem")){
            //pcDos, msWindows, macOS, unix, multiOs, none
            if (name.getName().equals("pcDos") || name.getName().equals("msWindows") || name.getName().equals("macOS") || name.getName().equals("unix") || name.getName().equals("multiOs") || name.getName().equals("none")){
                this.name = name;
            } else throw new IllegalArgumentException("The Name "+name.getName()+"is not valid, if the type is "+this.type.getType()+" the Name must be one of: pcDos, msWindows, macos, unix, multiOs or none");
            
        } else if (this.type.getType().equals("browser")){
            //any, netscapeComunicator, msInternetExplorer, opera, amaya, mozillaFirefox, appleSafari or googleChrome
            if (name.getName().equals("any") || name.getName().equals("netscapeComunicator") || name.getName().equals("msInternetExplorer") || name.getName().equals("opera") || name.getName().equals("amaya") || name.getName().equals("mozillaFirefox") || name.getName().equals("appleSafari") || name.getName().equals("googleChrome")){
                this.name = name;
            } else throw new IllegalArgumentException("The Name "+name.getName()+"is not valid, if the type is "+this.type.getType()+" the Name must be one of: pcDos, msWindows, macos, unix, multiOs or none");
            
        }
        
    }

    public void setMaximumVersion(String maximumVersion) {
        this.maximumVersion = maximumVersion;
    }

    public void setMinimumVersion(String minimumVersion) {
        this.minimumVersion = minimumVersion;
    }

    public String getMaximumVersion() {
        return maximumVersion;
    }

    public String getMinimumVersion() {
        return minimumVersion;
    }

    public Name getName() {
        return name;
    }

    public Type getType() {
        return type;
    }
   
    @Override
    public String toString () {
        return("Type: "+this.getType().getType()+"; Name: "+this.getName().getName()+" Minimum Version: "+this.minimumVersion+" Maximum Version: "+this.maximumVersion);
    }
}