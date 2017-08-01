//import com.clearspring.analytics.stream.cardinality.*;
//import com.clearspring.analytics.stream.cardinality.HyperLogLog;

/**
 * Created by chamod on 7/27/17.
 */
public class Main {
    public static void main(String[] args) {

        final int NO_OF_UNIQUE_NUMBERS = 200000;

        HyperLogLog<Integer> integerHyperLogLog = new HyperLogLog<Integer>(0.09);

        for(int i = 0; i < NO_OF_UNIQUE_NUMBERS; i++){
            integerHyperLogLog.addItem(i);
        }

//        for(int i = 0; i < NO_OF_UNIQUE_NUMBERS; i++){
//            integerHyperLogLog.addItem(i);
//        }
//        for(int i = 0; i < NO_OF_UNIQUE_NUMBERS; i++){
//            integerHyperLogLog.addItem(i);
//        }
//        for(int i = 0; i < NO_OF_UNIQUE_NUMBERS; i++){
//            integerHyperLogLog.addItem(i);
//        }

        System.out.println("cardinality : " + integerHyperLogLog.getCardinality());
        System.out.println("accuracy : " + integerHyperLogLog.getAccuracy());

        long cardinality  = integerHyperLogLog.getCardinality();
        double accuracy = integerHyperLogLog.getAccuracy();
        System.out.println(String.format("[%s, %s]", cardinality - cardinality * accuracy,
                cardinality + cardinality * accuracy));



        //............................library................................
//
//        int log2m = 30;
//
//        double accuracy = 1.04 / Math.sqrt(1 << log2m);
//
//
//        com.clearspring.analytics.stream.cardinality.HyperLogLog hyperLogLog = new HyperLogLog(log2m);
//
//        for(int i = 0; i < NO_OF_UNIQUE_NUMBERS; i++){
//            hyperLogLog.offer(i);
//        }
//
//        long cardinality  = hyperLogLog.cardinality();
//        System.out.println("cardinality : " + hyperLogLog.cardinality());
////        System.out.println("accuracy : " + integerHyperLogLog.getAccuracy());
//        System.out.println(String.format("[%s, %s]", cardinality - cardinality * accuracy, cardinality + cardinality * accuracy));
    }
}
