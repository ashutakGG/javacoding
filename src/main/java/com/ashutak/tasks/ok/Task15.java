package com.ashutak.tasks.ok;

public class Task15 {
    public static void main(String[] args) {
        long res = bitsToLong(args[0]);

        System.out.println(res);
    }

    static long bitsToLong(String input) {
        char[] inputArr = input.toCharArray();

        long res = 0;
        long power2 = 1;
        for (int i = inputArr.length - 1; i >=0; i--, power2 = power2 << 1) {
            char ch = inputArr[i];

            if (ch != '0' && ch != '1') {
                throw new IllegalArgumentException("Wrong input: " + input);
            }

            if (ch == '1') {
                res += power2;
            }
        }
        return res;
    }
}
