import java.util.ArrayList;

public class CountQueue {

    ArrayList<Integer> counts;

    public CountQueue() {
        counts = new ArrayList();
    }

    /**
     * Add new value to the counts depending on previous value
     * @param newValue
     * @return
     */
    public boolean add(int newValue) {
        if (counts.size() > 0) {
            for (int i = counts.size() - 1; i >=0; i-- ) {
                if (newValue > counts.get(i)) {
//                    System.out.println("i : " + i);
//                    System.out.println("counts.get(i) : " + counts.get(i));
                    counts.remove(i);
//                    System.out.println("counts.get(i-1) : " + counts.get(i-1));
                }
            }
            if (counts.size() == 0 || newValue <= counts.get(counts.size() - 1)) {
                counts.add(newValue);
                return true;
            } else {
                return false;
            }
        } else {
            counts.add(newValue);
            return true;
        }
    }

    /**
     * Remove the given value from the counts
     * @return the next value if the removed value is the first value,
     * -1 if first value is not the given value,
     * 0 if queue is empty
     */
    public int remove(int value) {
        if (counts.size() > 0) {
            if(counts.get(0)==value){
                counts.remove(0);
                if(counts.isEmpty()){
                    return 0;
                } else {
                    return counts.get(0);
//                       return max(counts);
                }
            } else {
                return -1;
            }
        }
        return 0;
    }


//    public void print() {
//        for (int x : counts) {
//            System.out.print(x + ", ");
//        }
//        System.out.println();
//    }
//
//    int max(ArrayList<Integer> arr) {
//        int maxx = 0;
//        for (int x : arr) {
//            if (maxx < x) {
//                maxx = x;
//            }
//        }
//        return maxx;
//    }
//
//    int size() {
//        return counts.size();
//    }
}
