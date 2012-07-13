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
     * @return
     */
    public String convertNodoList(List<SubNodo> list) {

        String jsList = "[ ";

        if (list == null) {
            return "";
        }

        int size = list.size();

        for (int i = 0; i < size; i++) {
            SubNodo nodo = list.get(i);

            if (i == size - 1) {
                jsList += "[ '" + nodo.getName() + "', " + nodo.getSize() + " ]";
            } else {
                jsList += "[ '" + nodo.getName() + "', " + nodo.getSize() + " ], ";
            }
        }

        jsList += " ]";

        return jsList;
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
