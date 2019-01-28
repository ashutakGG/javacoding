package com.ashutak.tasks.hackerrank;

import java.io.*;
import java.util.*;

import static java.lang.Math.*;

public class Solution {
    static String abbreviation(String a, String b) {
        char[] aChars = a.toCharArray();
        char[] bChars = b.toCharArray();

        return abb1(aChars, bChars) ? "YES" : "NO";
    }


    static boolean abb1(char[] a, char[] b) {
        boolean[][] memory = new boolean[a.length][b.length];

        // fill memory[0][index]
        char firstB = b[0];
        int index = 0;
        boolean foundThatCapital = false;
        while (index < a.length) {
            if ('A' <= a[index] && a[index] <= 'Z') {
                if (a[index] == firstB) {
                    memory[index][0] = !foundThatCapital;
                    foundThatCapital = true;
                } else {
                    while (index < a.length) {
                        memory[index][0] = false;
                        index++;
                    }
                }
            } else {
                memory[index][0] = toCapital(a[index]) == firstB || foundThatCapital;
            }
            index++;
        }
        // fill memory[0][index]
        for (int i = 1; i < b.length; i++) {
            memory[0][i] = false;
        }

        // Main loop.
        for (int idxA = 1; idxA < a.length; idxA++) {
            for (int idxB = 1; idxB < b.length; idxB++) {
                if ('A' <= a[idxA] && a[idxA] <= 'Z') {
                    if (a[idxA] == b[idxB]) {
                        memory[idxA][idxB] = memory[idxA - 1][idxB - 1];
                    } else {
                        memory[idxA][idxB] = false;
                    }
                } else {
                    if (toCapital(a[idxA]) == b[idxB]) {
                        memory[idxA][idxB] = memory[idxA - 1][idxB - 1] || memory[idxA - 1][idxB];
                    } else {
                        memory[idxA][idxB] = memory[idxA - 1][idxB];
                    }
                }
            }
        }
        return memory[a.length - 1][b.length - 1];
    }

    static final int CONST = 'a' - 'A';

    static char toCapital(char ch) {
        return (char) (ch - CONST);
    }

    static long flippingBits(long n) {
        long specNum = 0x00000000FFFFFFFF;
        System.out.println(">>>");
        System.out.println(Long.toBinaryString(n));
        System.out.println(">>>");
        System.out.println(Long.toBinaryString(specNum));
        System.out.println("--------");

        return n ^ specNum;
    }

    static String abbreviation1(String a, String b) {
        char[] aChars = a.toCharArray();
        char[] bChars = b.toCharArray();
        // Find capitals.
        if (findCapitals(aChars, bChars)) return "NO";
        System.out.println(Arrays.toString(aChars));
        System.out.println(Arrays.toString(bChars));
        // Find smalls;
        return findSmalls(aChars, bChars);
    }

    private static String findSmalls(char[] aChars, char[] bChars) {
        int aIdx = 0;
        int bIdx = 0;
        int prevIdx = -1;
        while (bIdx < bChars.length && aIdx < aChars.length) {
            if (bChars[bIdx] < 0) {
                prevIdx = -bChars[bIdx] - 1;
                bIdx++;
            } else {
                if (toCapital(aChars[aIdx]) != bChars[bIdx]) {
                    aIdx++;
                } else {
                    if (aIdx > prevIdx) {
                        aIdx++;
                        bIdx++;
                    } else {
                        aIdx++;
                    }
                }
            }
        }

        if (bIdx < bChars.length) {
            return "NO";
        }

        return "YES";
    }

    private static boolean findCapitals(char[] aChars, char[] bChars) {
        int aIdx = 0;
        int bIdx = 0;
        while (bIdx < bChars.length && aIdx < aChars.length) {
            if (aChars[aIdx] == bChars[bIdx]) {
                aChars[aIdx] = (char) (-bIdx - 1);
                bChars[bIdx] = (char) (-aIdx - 1);
                aIdx++;
                bIdx++;
            } else if ('A' <= aChars[aIdx] && aChars[aIdx] <= 'Z') {

                return true;
            } else {
                aIdx++;
            }
        }
        while (aIdx < aChars.length) {
            if ('A' <= aChars[aIdx] && aChars[aIdx] <= 'Z') {
                return true;
            }
            aIdx++;
        }
        return false;
    }

    static String reverseShuffleMerge(String s) {
        char[] chs = s.toCharArray();
        for (int i = 0; i < chs.length / 2; i++) {
            char tmp = chs[i];
            chs[i] = chs[chs.length - i];
            chs[chs.length - i] = tmp;
        }

        Map<Character, List<Integer>> positions = new HashMap<>();
//        Map<Character, Integer> counts = new HashMap<>();
        for (int i = 0; i < chs.length; i++) {
            int pos = i;
            positions.compute(chs[i], (k, v) -> {
                if (v == null) {
                    List<Integer> res = new ArrayList<>();
                    res.add(pos);
                    return res;
                } else {
                    v.add(pos);
                    return v;
                }
            });
        }

        char[] resArr = new char[s.length()];
        for (Map.Entry<Character, List<Integer>> e : positions.entrySet()) {
            List<Integer> poss = e.getValue();
            char ch = e.getKey();
            for (int i = 0; i < poss.size() / 2; i++) {
                resArr[i] = ch;
            }
        }

        StringBuilder res = new StringBuilder();
        for (int i = 0; i < resArr.length; i++) {
            if (resArr[i] != 0) {
                res.append(resArr[i]);
            }
        }
        return res.toString();
    }

    static Map<Character, Integer> makeIndex(String st) {
        abs(10);
        Map<Character, Integer> res = new HashMap<>();
        for (char ch : st.toCharArray()) {
            res.compute(ch, (k, v) -> {
                if (v == null) {
                    return 1;
                } else {
                    return v + 1;
                }
            });
        }
        return res;
    }


    private static void removeFromSortedList(List<Integer> cache, Integer el) {
        cache.remove(el);
        // ListIterator<Integer> iter = cache.listIterator();
        // while (iter.hasNext()) {
        //     if (iter.next().equals(el)) {

        //     }
        // }
    }

    private static void addToSortedList(List<Integer> cache, Integer el) {
        // System.out.println("Add el=" + el + ", cache=" +cache);
        ListIterator<Integer> iter = cache.listIterator();
        boolean added = false;
        while (iter.hasNext()) {
            Integer next = iter.next();
            if (next >= el) {
                iter.add(el);
                added = true;
                break;
            }
        }
        if (!added) {
            cache.add(el);
        }
        // System.out.println("cache=" +cache);
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static class Pair {
        public final int cost;
        public final int pos;

        public Pair(int cost, int pos) {
            this.cost = cost;
            this.pos = pos;
        }
    }

    static class Graph {
        private final int graphNodes;
        private final int[] graphFrom;
        private final int[] graphTo;
        private final Map<Integer, List<Integer>> adj = new HashMap<>();

        Graph(int graphNodes, int[] graphFrom, int[] graphTo) {
            this.graphNodes = graphNodes;
            this.graphFrom = graphFrom;
            this.graphTo = graphTo;

            for (int i = 0; i < graphFrom.length; i++) {
                int from = graphFrom[i];
                int to = graphTo[i];
                adj.compute(from, (k, v) -> {
                    if (v == null) {
                        v = new ArrayList<>();
                    }
                    v.add(to);
                    return v;
                });
                adj.compute(to, (k, v) -> {
                    if (v == null) {
                        v = new ArrayList<>();
                    }
                    v.add(from);
                    return v;
                });
            }
        }

        List<Integer> adj(int v) {
            return adj.get(v);
        }
    }

//    static int findShortest(int graphNodes, int[] graphFrom, int[] graphTo, long[] ids, int val) {
//        Set<Integer> vertexes = new HashSet<>();
//        for (int i = 0; i < ids.length; i++) {
//            if (ids[i] == ids[val - 1]) {
//                vertexes.add(i + 1);
//            }
//        }
//
//        if (vertexes.size() == 1) {
//            return -1;
//        }
//
//        Graph g = new Graph(graphNodes, graphFrom, graphTo);
//
//        int res = Integer.MAX_VALUE;
////        Set<Integer> globallySeenVertexes = new HashSet<>(vertexes);
//
////        for (int v: vertexes) {
////            System.out.println(">>> v:" + v);
//        int v = val;
//        if (res == 1
////                    || globallySeenVertexes.isEmpty()
//        ) {
//            return res;
//        }
//
//        // BFS
//        Set<Integer> seenVerts = new HashSet<>();
//
//        Queue<Pair> q = new LinkedList<>();
//        q.add(new Pair(v, 1));
//        seenVerts.add(v);
//
//        while (!q.isEmpty()) {
//            Pair pair = q.remove();
//
//            int curV = pair.v;
//            if (pair.depth >= res) {
//                break;
//            }
//
//            for (int adjV : g.adj(curV)) {
//                if (!seenVerts.contains(adjV)) {
//                    System.out.println(">>> adjV:" + adjV);
//                    seenVerts.add(adjV);
//                    q.add(new Pair(adjV, pair.depth + 1));
//
//                    if (vertexes.contains(adjV)) {
//                        res = Math.min(pair.depth, res);
//                        break;
//                    }
//                }
//            }
//        }
//
//
//        return res;
//    }

//    static String isBalanced(String s) {
//        char[] chars = s.toCharArray();
//        LinkedList<Character> stack = new LinkedList<>();
//        Map<Character, Character> pair = new HashMap<>();
//        max(1, 2)
//        pair.put('{', '}');
//        pair.put('[', ']');
//        pair.put('(', ')');
//        pair.put('}', '{');
//        pair.put(']', '[');
//        pair.put(')', '(');
//        for (char ch : chars) {
//            if (ch == '{' || ch == '[' || ch == '(') {
//                stack.addLast(pair.get(ch));
//            } else {
//                Character el = stack.pollLast();
//                if (el == null || !el.equals(ch)) {
//                    return "NO";
//                }
//            }
//
//        }
//        return stack.isEmpty() ? "YES" : "NO";
//    }


//    static long triplets(int[] a, int[] b, int[] c) {
//        Arrays.sort(a);
//        Arrays.sort(b);
//        Arrays.sort(c);
//
//        Arrays.stream(a).
//    }

    public static class MySet {
        // toots have arr[i] < 0, and it = cnt of childs
        // leaf have arr[i] >= 0 and it refers tp parent
        private int[] arr;
        private int cnt;

        public MySet(int n) {
            arr = new int[n];
            for (int i = 0; i < n; i++) {
                arr[i] = -1;
            }
            cnt = n;
        }

        public void put(int x, int y) {
            merge(findRoot(x), findRoot(y));
        }

        public void merge(int root1, int root2) {
            if (arr[root1] < 0 || arr[root2] < 0) {
                throw new IllegalArgumentException();
            }
            if (arr[root1] < arr[root2]) {
                // root1 has more childes
                arr[root1] += arr[root2];
                arr[root2] = root1;
            } else {
                // root2 has more childes
                arr[root2] += arr[root1];
                arr[root1] = root2;
            }
            cnt--;
        }

        public int findRoot(int x) {
            if (arr[x] < 0) {
                return x;
            }
            arr[x] = findRoot(arr[x]);
            return arr[x];
        }

        public int count() {
            return cnt;
        }
    }


    static int calculate(int n, int[][] cities) {
        MySet s = new MySet(n);

        for (int[] ab : cities) {
            s.put(ab[0] - 1, ab[1] - 1);
        }

        return s.count();
    }


    static void whatFlavors(int[] cost, int money) {
        Pair[] pairs = new Pair[cost.length];
        for (int i = 0; i < cost.length; i++) {
            pairs[i] = new Pair(cost[i], i + 1);
        }

        Arrays.sort(pairs, Comparator.comparingInt(o -> o.cost));
        for (Pair pair : pairs) {
            int idx = search0(pairs, money - pair.cost);
            if (idx > 0 && pair.pos != pairs[idx].pos) {
                int min = Math.min(pair.pos, pairs[idx].pos);
                int max = max(pair.pos, pairs[idx].pos);
                System.out.println(min + ' ' + max);
                return;
            }
        }
    }

    private static int search0(Pair[] pairs, int cost) {
        int left = 0;
        int right = pairs.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (pairs[mid].cost == cost) {
                return mid;
            } else if (cost < pairs[mid].cost) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Hello");
        char[] a = new char[2];
        Arrays.fill(a, 'Z');
        String x = Arrays.toString(a);
        System.out.println(x);

//        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));
//
//        String[] nd = scanner.nextLine().split(" ");
//
//        int n = Integer.parseInt(nd[0]);
//
//        int d = Integer.parseInt(nd[1]);
//
//        int[] expenditure = new int[n];
//
//        String[] expenditureItems = scanner.nextLine().split(" ");
//        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
//
//        for (int i = 0; i < n; i++) {
//            int expenditureItem = Integer.parseInt(expenditureItems[i]);
//            expenditure[i] = expenditureItem;
//        }
//
//        int result = activityNotifications(expenditure, d);
//
//        bufferedWriter.write(String.valueOf(result));
//        bufferedWriter.newLine();
//
//        bufferedWriter.close();
//
//        scanner.close();
    }
}
