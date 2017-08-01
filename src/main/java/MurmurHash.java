/*
* Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
* WSO2 Inc. licenses this file to you under the Apache License,
* Version 2.0 (the "License"); you may not use this file except
* in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied. See the License for the
* specific language governing permissions and limitations
* under the License.
*/

/**
 * Calculate hash values
 */
public final class MurmurHash {
    private MurmurHash() {}

    public static int hash(Object o) {
        return o == null?0:(o instanceof Long?hashLong(((Long)o).longValue()):
                (o instanceof Integer?hashLong((long)((Integer)o).intValue()):
                        (o instanceof Double?hashLong(Double.doubleToRawLongBits(((Double)o).doubleValue())):
                                (o instanceof Float?hashLong((long)Float.floatToRawIntBits(((Float)o).floatValue())):
                                        (o instanceof String?hash(((String)o).getBytes()):
                                                (o instanceof byte[]?hash((byte[])((byte[])o)):
                                                        hash(o.toString())))))));
    }

    /**
     * Generates 32 bit hash from byte array of the given length and
     * seed.
     *
     * @param data byte array to hash
     * @param length length of the array to hash
     * @param seed initial seed value
     * @return 32 bit hash of the given array
     */
    public static int hash32(final byte[] data, int length, int seed) {
        // 'm' and 'r' are mixing constants generated offline.
        // They're not really 'magic', they just happen to work well.
        final int m = 0x5bd1e995;
        final int r = 24;

        // Initialize the hash to a random value
        int h = seed ^ length;
        int length4 = length / 4;

        for (int i = 0; i < length4; i++) {
            final int i4 = i * 4;
            int k = (data[i4 + 0] & 0xff) + ((data[i4 + 1] & 0xff) << 8)
                    + ((data[i4 + 2] & 0xff) << 16) + ((data[i4 + 3] & 0xff) << 24);
            k *= m;
            k ^= k >>> r;
            k *= m;
            h *= m;
            h ^= k;
        }

        // Handle the last few bytes of the input array
        switch (length % 4) {
            case 3:
                h ^= (data[(length & ~3) + 2] & 0xff) << 16;
                break;
            case 2:
                h ^= (data[(length & ~3) + 1] & 0xff) << 8;
                break;
            case 1:
                h ^= (data[length & ~3] & 0xff);
                break;
        }
        h *= m;

        h ^= h >>> 13;
        h *= m;
        h ^= h >>> 15;

        return h;
    }

    /**
     * Generates 32 bit hash from byte array with default seed value.
     *
     * @param data byte array to hash
     * @param length length of the array to hash
     * @return 32 bit hash of the given array
     */
    public static int hash32(final byte[] data, int length) {
        return hash32(data, length, 0x9747b28c);
    }

    /**
     * Generates 32 bit hash from a string.
     *
     * @param text string to hash
     * @return 32 bit hash of the given string
     */
    public static int hash32(final String text) {
        final byte[] bytes = text.getBytes();
        return hash32(bytes, bytes.length);
    }

    /**
     * Generates 32 bit hash from a substring.
     *
     * @param text string to hash
     * @param from starting index
     * @param length length of the substring to hash
     * @return 32 bit hash of the given string
     */
    public static int hash32(final String text, int from, int length) {
        return hash32(text.substring(from, from + length));
    }

    /**
     * Generates 64 bit hash from byte array of the given length and seed.
     *
     * @param data byte array to hash
     * @param length length of the array to hash
     * @param seed initial seed value
     * @return 64 bit hash of the given array
     */
    public static long hash64(final byte[] data, int length, int seed) {
        final long m = 0xc6a4a7935bd1e995L;
        final int r = 47;

        long h = (seed & 0xffffffffL) ^ (length * m);

        int length8 = length / 8;

        for (int i = 0; i < length8; i++) {
            final int i8 = i * 8;
            long k =  ((long) data[i8 + 0] & 0xff) + (((long) data[i8 + 1] & 0xff) << 8)
                    + (((long) data[i8 + 2] & 0xff) << 16) + (((long) data[i8 + 3] & 0xff) << 24)
                    + (((long) data[i8 + 4] & 0xff) << 32) + (((long) data[i8 + 5] & 0xff) << 40)
                    + (((long) data[i8 + 6] & 0xff) << 48) + (((long) data[i8 + 7] & 0xff) << 56);

            k *= m;
            k ^= k >>> r;
            k *= m;

            h ^= k;
            h *= m;
        }

        switch (length % 8) {
            case 7:
                h ^= (long) (data[(length & ~7) + 6] & 0xff) << 48;
                break;
            case 6:
                h ^= (long) (data[(length & ~7) + 5] & 0xff) << 40;
                break;
            case 5:
                h ^= (long) (data[(length & ~7) + 4] & 0xff) << 32;
                break;
            case 4:
                h ^= (long) (data[(length & ~7) + 3] & 0xff) << 24;
                break;
            case 3:
                h ^= (long) (data[(length & ~7) + 2] & 0xff) << 16;
                break;
            case 2:
                h ^= (long) (data[(length & ~7) + 1] & 0xff) << 8;
                break;
            case 1:
                h ^= (long) (data[length & ~7] & 0xff);
                break;
        }
        h *= m;

        h ^= h >>> r;
        h *= m;
        h ^= h >>> r;

        return h;
    }

    /**
     * Generates 64 bit hash from byte array with default seed value.
     *
     * @param data byte array to hash
     * @param length length of the array to hash
     * @return 64 bit hash of the given string
     */
    public static long hash64(final byte[] data, int length) {
        return hash64(data, length, 0xe17a1465);
    }

    /**
     * Generates 64 bit hash from a string.
     *
     * @param text string to hash
     * @return 64 bit hash of the given string
     */
    public static long hash64(final String text) {
        final byte[] bytes = text.getBytes();
        return hash64(bytes, bytes.length);
    }

    /**
     * Generates 64 bit hash from a substring.
     *
     * @param text string to hash
     * @param from starting index
     * @param length length of the substring to hash
     * @return 64 bit hash of the given array
     */
    public static long hash64(final String text, int from, int length) {
        return hash64(text.substring(from, from + length));
    }

    //////////////////////////////////////////////////////////
    public static int hashLong(long data) {
        int m = 1540483477;
        int r = 24;
        int h = 0;
        int k = (int)data * m;
        k ^= k >>> r;
        h = h ^ k * m;
        k = (int)(data >> 32) * m;
        k ^= k >>> r;
        h *= m;
        h ^= k * m;
        h ^= h >>> 13;
        h *= m;
        h ^= h >>> 15;
        return h;
    }

}