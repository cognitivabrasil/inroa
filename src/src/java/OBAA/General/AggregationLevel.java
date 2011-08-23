package OBAA.General;

/*
 * <div class="en">
 * The functional granularity of this learning object.
 *
 * 1: the smallest level of aggregation, e.g., raw media data or fragments.
 * 2: a collection of level 1 learning objects, e.g., a lesson.
 * 3: a collection of level 2 learning objects, e.g., a course.
 * 4: the largest level of granularity, e.g., a set of courses that lead to a certificate.
 *
 * NOTE 1:--Level 4 objects can contain level 3 objects, or can recursively contain other level 4 objects.
 *
 * according to IEEE LOM http://ltsc.ieee.org/
 * </div>
 *
 * <div class="br">
 *
 *
 * </div>
 * @author LuizRossi
 */
public class AggregationLevel {

    private int aggregationLevel;

    public AggregationLevel() {
        aggregationLevel = 0;
    }

    /**
     *
     * @param aggregationLevel must be beetween 1 and 4
     * @throws IllegalArgumentException
     */
    public AggregationLevel(int aggregationLevel) throws IllegalArgumentException{
        setAggregationLevel(aggregationLevel);
    }

    /**
     *
     * @param aggregationLevel must be beetween 1 and 4
     * @throws IllegalArgumentException
     */
    public void setAggregationLevel(int aggregationLevel) throws IllegalArgumentException {

        if (aggregationLevel >= 5 || aggregationLevel <= 0){
            throw new IllegalArgumentException("Aggregation level must be beetween 1 and 4");
        }  else{
        this.aggregationLevel = aggregationLevel;
        }
    }

    public int getAggregationLevel() {
        return aggregationLevel;
    }

//    public static void main(String[] args) {
//        AggregationLevel a = new AggregationLevel (1);
//    }

}
