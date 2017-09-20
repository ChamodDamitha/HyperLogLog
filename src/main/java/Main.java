//import com.clearspring.analytics.stream.cardinality.*;
//import com.clearspring.analytics.stream.cardinality.HyperLogLog;

import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;

/**
 * Created by chamod on 7/27/17.
 */
public class Main {
    public static void main(String[] args) {

        final int NO_OF_UNIQUE_NUMBERS = 100;

        HyperLogLog<Integer> integerHyperLogLog = new HyperLogLog<Integer>(0.1);

        Random random1 = new Random(123);
        Random random2 = new Random(123);

        long[] cardinalities = new long[NO_OF_UNIQUE_NUMBERS];

        System.out.println("cardinality : " + integerHyperLogLog.getCardinality());

        for (int i = 0; i < NO_OF_UNIQUE_NUMBERS; i++) {
            integerHyperLogLog.addItem(random1.nextInt());
            System.out.println("cardinality : " + integerHyperLogLog.getCardinality());
//            cardinalities[i] = integerHyperLogLog.getCardinality();
        }

        System.out.println("-----------------------------------------------------------");
        for (int i = 0; i < NO_OF_UNIQUE_NUMBERS; i++) {
            integerHyperLogLog.removeItem(random2.nextInt());
            System.out.println("cardinality : " + integerHyperLogLog.getCardinality());
//            if(integerHyperLogLog.getCardinality()!=cardinalities)
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

//        System.out.println("cardinality : " + integerHyperLogLog.getCardinality());
//        System.out.println("accuracy : " + integerHyperLogLog.getAccuracy());
//        printLongArray(integerHyperLogLog.getConfidenceInterval());


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


    static void printLongArray(long[] arr) {
        System.out.print("{");
        for (long l : arr) {
            System.out.print(l + ",");
        }
        System.out.println("}");
    }
}
