package com.ashutak.tasks.hackerrank;

import java.util.Arrays;

public class CtciConnectedCellInGrid {
    static class DisjointForestSet {
        private final int[] arr;
        private int rootsCount;

        DisjointForestSet(int n) {
            arr = new int[n];
            for (int i = 0; i < n; i++) {
                arr[i] = -1;
            }

            rootsCount = n;
        }

        void merge(int x, int y) {
            mergeRoots(findRoot(x), findRoot(y));
        }

        void mergeRoots(int root1, int root2) {
            if (arr[root1] >= 0 || arr[root2] >= 0) {
                throw new IllegalArgumentException("");
            }
            if (root1 == root2)
                return;

            if (arr[root1] < arr[root2]) {
                // root1 has more childes
                arr[root1] += arr[root2];
                arr[root2] = root1;
            } else {
                arr[root2] += arr[root1];
                arr[root1] = root2;
            }
            rootsCount--;
        }

        int findRoot(int x) {
            if (arr[x] < 0) {
                return x;
            }
            int root = findRoot(arr[x]);
            arr[x] = root;
            return root;
        }

        public int getMaxElementsInArea() {
            return Arrays.stream(arr).filter(el -> el < 0).min().getAsInt() * -1;
        }
    }

    static int maxRegion(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        DisjointForestSet set = new DisjointForestSet(n * m);

        for (int row = 0; row < n; row++) {
            for (int column = 0; column < m; column++) {
                if (grid[row][column] == 1) {
                    int curIndex = row * m + column;

                    processAdjastedCell(grid, set, curIndex, row + 1, column);
                    processAdjastedCell(grid, set, curIndex, row, column + 1);
                    processAdjastedCell(grid, set, curIndex, row + 1, column + 1);
                    processAdjastedCell(grid, set, curIndex, row - 1, column + 1);
                }
            }
        }

        return set.getMaxElementsInArea();
    }

    private static void processAdjastedCell(int[][] grid, DisjointForestSet set, int curIndex, int anotherRow, int anotherColumn) {
        int n = grid.length;
        int m = grid[0].length;

        if (anotherRow >= 0 && anotherRow < n && anotherColumn < m && grid[anotherRow][anotherColumn] == 1) {
            set.merge(curIndex, (anotherRow) * m + anotherColumn);
        }
    }
}
