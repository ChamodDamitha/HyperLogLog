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

    private final double ESTIMATION_FACTOR = 0.7;

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
//        System.out.println("array size I : " + noOfBuckets); //TODO : added for testing

        lengthOfBucketId = (int)Math.ceil(Math.log(noOfBuckets) / Math.log(2));
//        System.out.println("bucket size : " + lengthOfBucketId); //TODO : added for testing

        noOfBuckets = (1 << lengthOfBucketId);
//        System.out.println("array size II : " + noOfBuckets); //TODO : added for testing

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
     * Calculate the cardinality(number of unique items in a set)
     * by calculating the harmonic mean of the counts in the buckets
     * Check for the upper and lower bounds to modify the estimation
     * @return
     */
    public int getCardinality(){
        int harmonicCountSum = 0;
        int harmonicCountMean;
        int count;
        int noOfZeroBuckets = 0;
        int estimatedCardinality;

//      calculate harmonic mean of the bucket values
        for(int i = 0; i < noOfBuckets; i++){
            count = countArray[i];
            harmonicCountSum += (1.0 / (1 << count));
            if(count == 0){
                noOfZeroBuckets++;
            }
        }

        harmonicCountMean = noOfBuckets / harmonicCountSum;

//      calculate the estimated cardinality
        estimatedCardinality = (int)Math.ceil(noOfBuckets * ESTIMATION_FACTOR * harmonicCountMean);

//      if the estimate E is less than 2.5 * 32 and there are buckets with max-leading-zero count of zero,
//      then instead return −32⋅log(V/32), where V is the number of buckets with max-leading-zero count = 0.
        if(estimatedCardinality < 2.5 * noOfBuckets && noOfZeroBuckets > 0){       //threshold of 2.5x comes from the recommended load factor for Linear Counting
            return (int)Math.ceil(-noOfBuckets * Math.log((double) noOfZeroBuckets / noOfBuckets));
        }
//       if E > 2 ^ (32) / 30 : return −2 ^ (32) * log(1 − E / 2 ^ (32))
        else if(estimatedCardinality > ((1 << 32) / 30.0)){
            return (int)Math.ceil(-(1 << 32) * Math.log(1 - (estimatedCardinality / (1 << 32))));
        }
        else{
            return estimatedCardinality;
        }
    }



    /**
     * Adds a new item to the array by hashing and setting the count of relevant bucckets
     * @param item
     */
    public void addItem(E item){
        int hash = getHashValue(item);
//        System.out.println("hash : " + Integer.toBinaryString(hash)); //TODO : added for testing

//      Shift all the bits to right till only the bucket ID is left
        int bucketId = hash >>> (Integer.SIZE - lengthOfBucketId);
//        System.out.println("bucketID : " + Integer.toBinaryString(bucketId)); //TODO : added for testing

//      Shift all the bits to left till the bucket id is removed
        int remainingValue = hash << lengthOfBucketId;
//        System.out.println("Remaining value : " + Integer.toBinaryString(remainingValue)); //TODO : added for testing

        int noOfLeadingZeros = Integer.numberOfLeadingZeros(remainingValue);
//        System.out.println("no of leading zeros : " + noOfLeadingZeros); //TODO : added for testing

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

    /**
     * Update the zero count value in the relevant bucket if the given value is larger than the existing value
     * @param index is the bucket ID of the relevant bucket
     * @param leadingZeroCount is the new zero count
     */
    private void updateBucket(int index, int leadingZeroCount){
        int currentZeroCount = countArray[index];
        if(currentZeroCount < leadingZeroCount){
            countArray[index] = leadingZeroCount;
        }
    }

    /**
     * return a byte array for input data of type E
     * @param data
     * @return a byte array
     */
    private byte[] getBytes(E data){
        return data.toString().getBytes();
    }

}
