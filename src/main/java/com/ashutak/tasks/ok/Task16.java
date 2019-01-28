package com.ashutak.tasks.ok;

import java.util.*;
import java.util.function.BiFunction;

public class Task16 {
    public static void main(String[] args) {
        int columnsNum = Integer.valueOf(args[0]);
        int rowsNum = Integer.valueOf(args[1]);

        int[][] arr = generateMatrixWithColumnsProperties(columnsNum, rowsNum);

        Arrays.sort(arr, Comparator.comparingInt(o -> o[rowsNum]));

        printMatrix(columnsNum, rowsNum, arr);
    }

    static int activityNotifications(int[] expenditure, int d) {
        int res = 0;
        long medianSum = Arrays.stream(expenditure).limit(d).sum();
        for (int i = d; i < expenditure.length; i++) {
            if (1l * expenditure[i] >= (2 * medianSum / d)) {
                res++;
            }
            medianSum += expenditure[i] - expenditure[i - d];
        }
        return res;
    }


    private static void printMatrix(int columnsNum, int rowsNum, int[][] arr) {
        StringBuilder st = new StringBuilder();
        for (int j = 0; j < rowsNum + 1; j++) {
            for (int i = 0; i < columnsNum; i++) {
                st.append(arr[i][j]).append(' ');
            }
            st.append('\n');
        }

        System.out.println(st.toString());
    }

    private static int[][] generateMatrixWithColumnsProperties(int columnsNum, int rowsNum) {
        int[][] res = new int[columnsNum][rowsNum + 1];

        Random r = new Random();

        int random, property;
        for (int i = 0; i < columnsNum; i++) {
            property = 0;
            for (int j = 0; j < rowsNum; j++) {
                random = r.nextInt();
                res[i][j] = random;
                property += random;
            }

            // Save column's property.
            res[i][rowsNum] = property;
        }

        return res;
    }
}
