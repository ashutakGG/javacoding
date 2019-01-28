package com.ashutak.tasks.hackerrank;

import java.util.*;

public class SolutionTrie {

    public static final int BIT_POS = 32 - 1;

    static class Node {
        Node zero, one;
        Integer value;

        Node(int val) {
            value = val;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    ", zero=" + zero +
                    ", one=" + one +
                    '}';
        }
    }

    static int[] maxXor(int[] arr, int[] queries) {
        final Node root = buildTrie(arr);

        int[] res = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int q = queries[i];
            int companion = findCompanion(q, root);
            res[i] = companion ^ q;
        }
        return res;
    }

    static int findCompanion(int q, Node root) {
//        System.out.println("q:" + q);
        BitSet bs = BitSet.valueOf(new long[]{q});

        Node curNode = root;
        for (int bitPos = BIT_POS; bitPos >= 0; bitPos--) {
//            System.out.println(">>> bitPos=" + bitPos);
            if (curNode.zero == null && curNode.one == null) {
                throw new IllegalStateException(">>> curNode: " + curNode.value);
            }
            if (bs.get(bitPos)) {
                // 1. We need go to zero.
                if (curNode.zero != null)
                    curNode = curNode.zero;
                else
                    curNode = curNode.one;
            } else {
                if (curNode.one != null)
                    curNode = curNode.one;
                else
                    curNode = curNode.zero;
            }
        }
        return curNode.value;
    }

    static Node buildTrie(int[] arr) {
        final Node root = new Node(-1);
        for (int a : arr) {
            BitSet bs = BitSet.valueOf(new long[]{a});
//            System.out.println(bs.toString());
            Node curNode = root;
            for (int bitPos = BIT_POS; bitPos >= 0; bitPos--) {
                if (bs.get(bitPos)) {
                    // 1
                    if (curNode.one == null) {
                        curNode.one = new Node(bitPos);
                    }
                    curNode = curNode.one;
                } else {
                    //0
                    if (curNode.zero == null) {
                        curNode.zero = new Node(bitPos);
                    }
                    curNode = curNode.zero;
                }
            }
            curNode.value = a;
        }
        return root;
    }

    static class Pair {
        final int pos, value;

        public Pair(int pos, int value) {
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

    static long largestRectangle(int[] h) {
        int[] startPositions = new int[h.length];
        Deque<Pair> stack = new LinkedList<>();
        // Special value. It's never deleted. So, there is no need in checking on empty stack.
        stack.push(new Pair(-1, 0));
        for (int i = 0; i < h.length; i++) {
            fillArray(startPositions, stack, i, h[i]);
        }

        stack.clear();
        stack.addLast(new Pair(h.length, 0));
        int[] endPositions = new int[h.length];
        for (int i = h.length - 1; i >= 0; i--) {
            fillArray(endPositions, stack, i, h[i]);
        }

        long res = 0;
        for (int i = 0; i < h.length; i++) {
            res = Math.max((endPositions[i] - startPositions[i] - 1) * h[i], res);
        }

        return res;
    }

    private static void fillArray(int[] startPositions, Deque<Pair> stack, int i, int cur) {
//        boolean removed = false;
        while (stack.peekLast().value >= cur) {
            stack.removeLast();
//            removed = true;
        }
        startPositions[i] = stack.peekLast().pos;
        stack.push(new Pair(i, cur));
//        if (removed) {
//        } else {
//            stack.push(new Pair(i, cur));
//            startPositions[i] = stack.peekLast().pos;
//        }
    }


    static long largestRectangle_old(int[] h) {
        long res = 0;
        for (int i = 0; i < h.length; i++) {
            int baseHight = h[i];
            int startIndex = findStartIndex(i, h);
            int endIndex = findEndIndex(i, h);
            System.out.println("i:" + i + ", startIdx:" + startIndex + ", endIdx:" + endIndex);
            res = Math.max(res, (endIndex - startIndex + 1) * baseHight);
        }
        return res;
    }

    static int findStartIndex(int initialIndex, int[] h) {
        for (int i = initialIndex - 1; i >= 0; i--) {
            if (h[i] < h[initialIndex]) {
                return i + 1;
            }
        }
        return 0;
    }

    static int findEndIndex(int initialIndex, int[] h) {
        for (int i = initialIndex + 1; i < h.length; i++) {
            if (h[i] < h[initialIndex]) {
                return i - 1;
            }
        }
        return h.length - 1;
    }

    static long candies(int n, int[] arr) {
        n = arr.length;
        int[] dp = new int[n];
//        int curCount = 1;
        dp[0] = 1;
        for (int i = 1; i < n; i++) {
            if (arr[i - 1] < arr[i]) {
                dp[i] = dp[i - 1] + 1;
            } else {
                dp[i] = 1;
            }
        }

        for (int i = n - 2; i >= 0; i--) {
            if (arr[i] > arr[i + 1]) {
                if (!(dp[i] > dp[i + 1])) {
                    dp[i] = dp[i + 1] + 1;
                }
            }
        }

        long res = 0;
        for (int e : dp) {
            res += e;
        }

        return res;
    }

    static long candies_old(int n, int[] arr) {
        long res = 1;
        long candiesAtLastPos = 1;
        int positionOfLastHighest = 0;

        for (int curIndex = 1; curIndex < arr.length; curIndex++) {
            int curElem = arr[curIndex];
            int lastElemPos = curIndex - 1;
            int lastElem = arr[lastElemPos];

            if (lastElem == curElem) {
                res++;
                if (positionOfLastHighest == lastElemPos)
                    positionOfLastHighest = curElem;
                candiesAtLastPos = 1;
            } else if (lastElem > curElem) {
                if (candiesAtLastPos == 1) {
                    res += (curIndex - positionOfLastHighest);
//                    positionOfLastHighest - noop
                    candiesAtLastPos = 1;
                } else {
                    res++;
                    candiesAtLastPos = 1;
                }
            } else {
                positionOfLastHighest = curIndex;
                candiesAtLastPos++;
                res += candiesAtLastPos;
            }

        }
        return res;

    }


    public static void main(String[] args) {
//        maxXor(new int[]{0,1,2}, new int[]{3,7,2});
        System.out.println(candies(1, new int[]{1, 7, 8, 9, 2, 1}));
//        System.out.println(candies(1, new int[]{1,7,2,1}));
    }

}
//
// Node{value=-1,
//         zero=Node{value=3,
//                    zero=Node{value=2,
//                                zero=Node{value=1,
//                                            zero=Node{value=0,zero=null, one=null},
//                                            one=Node{value=1, zero=null, one=null}},
//                                one=Node{value=1,
//                                            zero=Node{value=2, zero=null, one=null},
//                                            one=null}},
//                    one=null},
//         one=null}
