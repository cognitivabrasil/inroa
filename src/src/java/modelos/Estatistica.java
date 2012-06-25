package modelos;

import java.util.List;

/**
 *
 * @author cei
 */
public class Estatistica {

    public String fromListToJsList(List <SubNodo> list) {

        
        String jsList = "[ ";        

        if (list == null) {
            return "";
        }

        int size = list.size();

        for (int i = 0; i < size; i++) {
            SubNodo nodo = list.get(i);
                        
            if (i==size-1){
                jsList += "[ '"+nodo.getNome()+"', "+nodo.getSize()+" ]";                                
            } else {
                jsList += "[ '"+nodo.getNome()+"', "+nodo.getSize()+" ], ";
            }
        }
                        
        jsList += " ]";

        return jsList;
    }
}
