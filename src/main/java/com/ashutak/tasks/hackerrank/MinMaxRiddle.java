package com.ashutak.tasks.hackerrank;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class MinMaxRiddle {
    static class PairRiddle {
        final int pos;
        final long value;

        public PairRiddle(int pos, long value) {
            this.pos = pos;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Pair{" +
                    "pos=" + pos +
                    ", value=" + value +
                    '}';
        }
    }


    static long[] riddle(long[] arr) {
        Map<Long, Integer> number2MaxWindowSize = buildMap(arr);
        Map<Integer, Long> invertedMap = invertMap(number2MaxWindowSize);

        Long lastMaxMin = null;
        long[] res = new long[arr.length];
        for (int windowSize = arr.length; windowSize > 0; windowSize--) {
//            int windowSize = arr.length - i;
            Long maxMin = invertedMap.get(windowSize);
            if (maxMin != null) {
                lastMaxMin = maxMin;
            }
            if (lastMaxMin == null)
                throw new IllegalStateException(invertedMap.toString());

            res[windowSize - 1] = lastMaxMin;
        }
        return res;
    }

    private static Map<Integer, Long> invertMap(Map<Long, Integer> number2MaxWindowSize) {
        Map<Integer, Long> res = new HashMap<>();
        for (Map.Entry<Long, Integer> e: number2MaxWindowSize.entrySet()) {
            res.compute(e.getValue(), (windowSize, number) -> {
                if (number == null) {
                    return e.getKey();
                }
                return Math.max(e.getKey(), windowSize);
            });
        }
        return res;
    }

    private static Map<Long, Integer> buildMap(long[] arr) {
        Map<Long, Integer> res = new HashMap<>();

        int[] startIndexes = new int[arr.length];
        Deque<PairRiddle> stack = new ArrayDeque<>();
        stack.addLast(new PairRiddle (-1, -1));
        for (int i = 0; i < arr.length; i++) {
            long cur = arr[i];
            while (stack.getLast().value >= cur) {
                stack.removeLast();
            }
            startIndexes[i] = stack.getLast().pos;
            stack.addLast(new PairRiddle(i, cur));
        }

        int[] endIndexes = new int[arr.length];
        stack.clear();
        stack.addLast(new PairRiddle (arr.length, -1));
        for (int i = arr.length - 1; i >= 0; i--) {
            long cur = arr[i];
            while (stack.getLast().value >= cur) {
                stack.removeLast();
            }
            endIndexes[i] = stack.getLast().pos;
            stack.addLast(new PairRiddle(i, cur));
        }

        for (int i = 0; i < arr.length; i++) {
            int end = endIndexes[i];
            int start = startIndexes[i];
            res.compute(arr[i], (num, windowSize) -> {
                int newWindowSize = end - start - 1;
                if (windowSize == null) {
                    return newWindowSize;
                }
                return Math.max(newWindowSize, windowSize);
            });
        }

        return res;
    }
}
