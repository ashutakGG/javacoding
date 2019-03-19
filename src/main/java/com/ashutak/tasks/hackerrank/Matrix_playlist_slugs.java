package com.ashutak.tasks.hackerrank;

import java.util.*;
import java.util.function.IntConsumer;

public class Matrix_playlist_slugs {
    static class DisjointForestSet {
        private final int[] arr;
        private final Set<Integer> red = new HashSet<>();

        DisjointForestSet(int n, int[] red) {
            arr = new int[n];
            for (int i = 0; i < n; i++) {
                arr[i] = -1;
            }
            for (int r: red) {
                this.red.add(r);
            }
        }

        boolean merge(int x, int y) {
            return mergeRoots(findRoot(x), findRoot(y));
        }

        /**
         * @return <code>root</code> if roots have been joined.
         */
        boolean mergeRoots(int root1, int root2) {
            if (arr[root1] >= 0 || arr[root2] >= 0) {
                throw new IllegalArgumentException("");
            }
            if (root1 == root2)
                throw new IllegalArgumentException("same root");

            boolean root1IsRed = red.contains(root1);
            boolean root2IsRed = red.contains(root2);
            if (root1IsRed && root2IsRed)
                return false;

            if (arr[root1] < arr[root2]) {
                // root1 has more childes
                arr[root1] += arr[root2];
                arr[root2] = root1;
            } else {
                arr[root2] += arr[root1];
                arr[root1] = root2;
            }

            if (root1IsRed || root2IsRed) {
                red.add(root1);
                red.add(root2);
            }

            return true;
        }

        int findRoot(int x) {
            if (arr[x] < 0) {
                return x;
            }
            int root = findRoot(arr[x]);
            arr[x] = root;
            return root;
        }
    }

    static int minTime(int[][] roads, int[] machines) {
        Arrays.sort(roads, (r1, r2) -> r2[2] - r1[2]);

        int n = roads.length + 1;
        DisjointForestSet set = new DisjointForestSet(n, machines);

        int res = 0;

        for (int[] road: roads) {
            if (!set.merge(road[0], road[1])) {
                res += road[2];
            }
        }

        return res;
    }
}
