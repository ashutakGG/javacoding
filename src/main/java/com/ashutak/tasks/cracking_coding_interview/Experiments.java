package com.ashutak.tasks.cracking_coding_interview;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Experiments {

    public static void main(String[] args) {
        var m = new HashMap<Integer, String>();

        var v = new Map.Entry<Integer, String>();
        int res = Integer.M;
        Arrays.binarySearch()

        Object[] objects = m
                .entrySet()
                .stream()
                .sorted(Comparator.comparingInt(Map.Entry::getKey))
                .map(Map.Entry::getKey)
                .toArray();
    }

    static boolean isDigitLog(String log) {
        String[] words = log.split(" ");
        String word = words[1];
        for (char ch: word.toCharArray()) {
            if (ch < '0' || ch > '9')
                return false;
        }
        return true;
    }

    static boolean isLetterLog(String log) {
        return !isDigitLog(log);
    }

    static void tmp () {
        int[] arr = {1,2,3,4};

        var l = List.of(1,2,3,4);

        int[] arr2 = l.stream()
                .mapToInt(Integer::intValue)
                .toArray();

        System.out.println(Arrays.toString(arr2));
    }


    private static void removeChar(Map<Character, Integer> map, Character ch) {
        map.compute(ch, (k,v) -> {
            v--;
            if (v == 0) {
                return null;
            }
            return v;
        });
    }
}
