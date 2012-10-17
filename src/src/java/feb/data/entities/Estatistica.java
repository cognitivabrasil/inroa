package feb.data.entities;

import java.util.List;

/**
 *
 * @author cei
 */
public class Estatistica {

    /**
     *
     * @param list the List to be converted
     * @param tipo is size if you want the repositories number of objects is
     * visits if you want the repositories access
     * @return
     */
    public String convertNodoList(List<SubNodo> list, String tipo) {

        StringBuilder builder = new StringBuilder("[ ");

        if (list == null) {
            return "";
        }

        int size = list.size();

        for (int i = 0; i < size; i++) {
            SubNodo nodo = list.get(i);

            if (tipo.equals("size")) {
                if (i == size - 1) {
                    builder.append("[ '");
                    builder.append(nodo.getName());
                    builder.append("', ");
                    builder.append(nodo.getSize());
                    builder.append(" ]");
                    //jsList += "[ '" + nodo.getName() + "', " + nodo.getSize() + " ]";
                } else {
                    builder.append("[ '");
                    builder.append(nodo.getName());
                    builder.append("', ");
                    builder.append(nodo.getSize());
                    builder.append(" ], ");
                    
                   // jsList += "[ '" + nodo.getName() + "', " + nodo.getSize() + " ], ";
                }
            } else if (tipo.equals("visits")) {
                if (i == size - 1) {
                    builder.append("[ '");
                    builder.append(nodo.getName());
                    builder.append("', ");
                    builder.append(nodo.getVisits());
                    builder.append(" ]");
                    
                   // jsList += "[ '" + nodo.getName() + "', " + nodo.getVisits() + " ]";
                } else {
                    builder.append("[ '");
                    builder.append(nodo.getName());
                    builder.append("', ");
                    builder.append(nodo.getVisits());
                    builder.append(" ], ");
                    //jsList += "[ '" + nodo.getName() + "', " + nodo.getVisits() + " ], ";
                }
            }
        }

        builder.append(" ]");
        
        return (builder.toString());
    }

    public String convertIntList(List<Integer> list) {
        String jsList = "[";

        if (list == null) {
            return "";
        }

        int size = list.size();

        for (int i = 0; i < size; i++) {
            Integer n = list.get(i);

            if (i == size - 1) {
                jsList += n;
            } else {
                jsList += n + ", ";
            }
        }

        jsList += "]";

        return jsList;
    }
}
