package com.ashutak.tasks.cracking_coding_interview;

public class Task10_4 {
    static boolean isSet(byte[] index, short el) {
        byte b = index[el / 8];
        int bit = el % 8;
        return (b & (1 << bit)) != 0;
    }

    static void set(byte[] index, short el) {
        int bit = el % 8;
        index[el / 8] = (byte) (index[el / 8] | (1 << bit));
    }

    public static void main(String[] args) {
        byte[] index = new byte[Short.MAX_VALUE/8+1];
        System.out.println(isSet(index, (short) 0));
        System.out.println(isSet(index, (short) 3));
        System.out.println(isSet(index, (short) 10));
        System.out.println(isSet(index, Short.MAX_VALUE));
        set(index, (short) 0);
        set(index, (short) 3);
        set(index, (short) 10);
        set(index, (short) Short.MAX_VALUE);
        System.out.println(isSet(index, (short) 0));
        System.out.println(isSet(index, (short) 3));
        System.out.println(isSet(index, (short) 10));
        System.out.println(isSet(index, Short.MAX_VALUE));
    }
}
