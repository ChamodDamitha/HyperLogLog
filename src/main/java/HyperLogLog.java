import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by chamod on 7/27/17.
 */
public class HyperLogLog<E> {

    private double accuracy;
    private int noOfBuckets;
    private int lengthOfBucketId;
    private int[] countArray;

    private final double ESTIMATION_FACTOR = 0.5;

    private MessageDigest messageDigest;

    /**
     * Create a new HyperLogLog by specifying the accuracy
     * Based on the accuracy the array size is calculated
     * @param accuracy is a number in the range (0, 1)
     */
    public HyperLogLog(double accuracy) {
        this.accuracy = accuracy;

//      accuracy = 1.04 / sqrt(noOfBuckets) = > noOfBuckets = (1.04 / accuracy) ^ 2
        noOfBuckets = (int)Math.ceil(Math.pow(1.04 / accuracy, 2));
        System.out.println("array size I : " + noOfBuckets); //TODO : added for testing

        lengthOfBucketId = (int)Math.ceil(Math.log(noOfBuckets) / Math.log(2));
        System.out.println("bucket size : " + lengthOfBucketId); //TODO : added for testing

        noOfBuckets = (1 << lengthOfBucketId);
        System.out.println("array size II : " + noOfBuckets); //TODO : added for testing

        countArray = new int[noOfBuckets];

//        setting MD5 digest function to generate hashes
        try {
            this.messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }

    /**
     * Compute the accuracy using the count array size
     * @return
     */
    public double getAccuracy(){
        return (1.04 / Math.sqrt(noOfBuckets));
    }


    /**
     * Adds a new item to the array by hashing and setting the count of relevant bucckets
     * @param item
     */
    public void addItem(E item){
        int hash = getHashValue(item);
        System.out.println("hash : " + Integer.toBinaryString(hash)); //TODO : added for testing

//      Shift all the bits to right till only the bucket ID is left
        int bucketId = hash >>> (Integer.SIZE - lengthOfBucketId);
        System.out.println("bucketID : " + Integer.toBinaryString(bucketId)); //TODO : added for testing

//      Shift all the bits to left till the bucket id is removed
        int remainingValue = hash << lengthOfBucketId;
        System.out.println("Remaining value : " + Integer.toBinaryString(remainingValue)); //TODO : added for testing

        int noOfLeadingZeros = Integer.numberOfLeadingZeros(remainingValue);
        System.out.println("no of leading zeros : " + noOfLeadingZeros); //TODO : added for testing

        updateBucket(bucketId, noOfLeadingZeros);
    }

    /**
     * Compute an integer hash value for a given value
     * @param value to be hashed
     * @return
     */
    public int getHashValue(E value){
        byte[] bytes = messageDigest.digest(getBytes(value));
        return (bytes[0] & 0xff) + ((bytes[1] & 0xff) << 8) + ((bytes[2] & 0xff) << 16) + ((bytes[3] & 0xff) << 24);
    }

    private void updateBucket(int index, int leadingZeroCount){

    }
//
//    private int zeroBitCount(){
//
//    }

    /**
     * return a byte array for input data of type E
     * @param data
     * @return a byte array
     */
    private byte[] getBytes(E data){
        return data.toString().getBytes();
    }

}
