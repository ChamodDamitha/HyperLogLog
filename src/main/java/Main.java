/**
 * Created by chamod on 7/27/17.
 */
public class Main {
    public static void main(String[] args) {

        HyperLogLog<Integer> integerHyperLogLog = new HyperLogLog<Integer>(0.05);

        for(int i = 0; i < 20; i++){
            integerHyperLogLog.addItem(i);
        }

        System.out.println("cardinality : " + integerHyperLogLog.getCardinality());
    }
}
