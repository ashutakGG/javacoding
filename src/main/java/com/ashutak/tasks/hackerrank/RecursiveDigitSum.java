package com.ashutak.tasks.hackerrank;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class RecursiveDigitSum {
    static int superDigit(String n, int k) {
        int superDigitOfN = superDigitOfString(n, 0, n.length() -1 );
        System.out.println(superDigitOfN);
        return superDigitOfInt(superDigitOfN * k);
    }

    private static int superDigitOfString(String n, int start, int end) {
        if (start == end) {
            return new Integer(n.charAt(start) + "");
        }
        if (start > end) {
            return 0;
        }

        int mid = (start + end) / 2;
        int sum = superDigitOfString(n, start, mid) + superDigitOfString(n, mid + 1, end);

        return superDigitOfInt(sum);
    }

    private static final Map<Integer, Integer> digitCache = new HashMap<>();

    private static int superDigitOfInt(int x) {
        if (x < 10) {
            return x;
        }
        Integer cache = digitCache.get(x);
        if (cache != null) {
            return cache;
        }
        int sum = 0;
        int rest = x;
        while (rest != 0) {
            int digit = rest % 10;
            rest /= 10;
            sum += digit;
        }
        int res = superDigitOfInt(sum);
        digitCache.put(x, res);
        return res;
    }
}
