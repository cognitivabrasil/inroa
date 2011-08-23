package OBAA.General;

/**
 *
 * Underlying organizational structure of this learning object.
 *
 * atomic: an object that is indivisible (in this context).
 * collection: a set of objects with no specified relationship between them.
 * networked: a set of objects with relationships that are unspecified.
 * hierarchical: a set of objects whose relationships can be represented by a tree structure.
 * linear: a set of objects that are fully ordered.
 *   Example: A set of objects that are connected by "previous" and "next" relationships
 *
 *  NOTE:A learning object with Structure="atomic" will typically have
 * 1.8:General.AggregationLevel=1. A learning object with Structure="collection", "linear", "hierarchical"
 * or "networked" will typically have 1.8:General.AggregationLevel=2, 3 or 4.
 *
 * @author LuizRossi
 */
public class Structure{

    private String structure;
    private enum setOfTerms {atomic, collection, networked,hierarchical,linear};

    public Structure() {
        structure = "";
    }

    public Structure(String structure) throws IllegalArgumentException {
            setStructure(structure);
        
    }

    public void setStructure(String structure) throws IllegalArgumentException {

        try {
            setOfTerms.valueOf(structure);
            this.structure = structure;

        } catch (IllegalArgumentException I) {
            throw new IllegalArgumentException("Structure must be one of: atomic, collection, networked, hierarchical or linear");

        }
    }

    public String getStructure() {
        return structure;
    }


    public static void main (String args[]){

        String a = "atomico";

        Structure s = new Structure();

        s.setStructure(a);

        System.out.println("OK");
    }
}
