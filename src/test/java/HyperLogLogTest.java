//import com.clearspring.analytics.stream.cardinality.*;
//import com.clearspring.analytics.stream.cardinality.HyperLogLog;
import org.junit.Test;

/**
 * Created by chamod on 8/2/17.
 */
public class HyperLogLogTest {
    final int NO_OF_UNIQUE_NUMBERS = 1000000000;
    final double accurracy = 0.001;

    @Test
    public void testImplementedCardinality(){

        HyperLogLog<Integer> integerHyperLogLog = new HyperLogLog<Integer>(accurracy);

        for(int i = 0; i < NO_OF_UNIQUE_NUMBERS; i++){
            integerHyperLogLog.addItem(i);
        }

        long cardinality  = integerHyperLogLog.getCardinality();
        System.out.println("cardinality : " + cardinality);
        System.out.println(String.format("[%s, %s]", cardinality - cardinality * accurracy,
                cardinality + cardinality * accurracy));
    }

//    @Test
//    public void testLibraryCardinality(){
//        int m = (int) Math.pow(1.04 / accurracy, 2);
//        int log2m = (int) Math.ceil(Math.log(m) / Math.log(2));
//
//        com.clearspring.analytics.stream.cardinality.HyperLogLog hyperLogLog = new HyperLogLog(log2m);
//
//        for(int i = 0; i < NO_OF_UNIQUE_NUMBERS; i++){
//            hyperLogLog.offer(i);
//        }
//
//        long cardinality  = hyperLogLog.cardinality();
//        System.out.println("cardinality : " + cardinality);
//        System.out.println(String.format("[%s, %s]", cardinality - cardinality * accurracy,
//                cardinality + cardinality * accurracy));
//    }


}
