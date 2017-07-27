/**
 * Created by chamod on 7/27/17.
 */
public class Main {
    public static void main(String[] args) {

        HyperLogLog<Integer> integerHyperLogLog = new HyperLogLog<Integer>(0.01);

//        byte[] bytes = integerHyperLogLog.getHashValue(12);
//
//        int i = bytes[0] & 0xff;

//        System.out.print("Accuracy :" +  integerHyperLogLog.getAccuracy());

        for(int i = 0; i < 100; i++){
            integerHyperLogLog.addItem(i);
        }

        System.out.println("cardinality : " + integerHyperLogLog.getCardinality());
    }
}
