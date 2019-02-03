package com.ashutak.tasks.hackerrank;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class CrosswordPuzzle {
    static String[] crosswordPuzzle(String[] crossword, String words) {
        char[][] crosswordArr = new char[crossword.length][crossword.length];
        for (int i = 0; i < crossword.length; i++) {
            crosswordArr[i] = crossword[i].toCharArray();
        }
        Crossword finalCrossword = new Crossword(crosswordArr);
        if (!crosswordPuzzle0(finalCrossword, words.split(";"), 0)) {
            throw new IllegalStateException();
        }
        for (int i = 0; i < crosswordArr.length; i++) {
            crossword[i] = finalCrossword.getRowAsString(i);
        }
        return crossword;
    }

    private static class Point {
        final int row, column;

        private Point(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public String toString() {
            return "Point{" +
                    "row=" + row +
                    ", column=" + column +
                    '}';
        }
    }

    private static class Crossword {
        private final Deque<Character>[][] crossword;


        private Crossword(char[][] crossw) {
            //noinspection unchecked
            crossword = new Deque[crossw.length][crossw.length];
            for (int i = 0; i < crossw.length; i++) {
                for (int j = 0; j < crossw.length; j++) {
                    Deque<Character> stack = new ArrayDeque<>();
                    stack.addLast(crossw[i][j]);
                    crossword[i][j] = stack;
                }
            }
        }

        void set(int row, int column, char ch) {
            crossword[row][column].addLast(ch);
        }

        void unsetLast(int row, int column) {
            crossword[row][column].removeLast();
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (Deque<Character>[] row: crossword) {
                for (Deque<Character> stack: row){
                    sb.append(stack.getLast());
                }
            }

            return sb.toString();
        }

        int length() {
            return crossword.length;
        }

        char get(int row, int columm) {
            return crossword[row][columm].getLast();
        }

        boolean isFillable(int row, int column) {
            char c = get(row, column);
            return c != '+' && c != 'X';
        }

        String getRowAsString(int row) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < length(); i++) {
                sb.append(get(row, i));
            }
            return sb.toString();
        }
    }

    private static boolean crosswordPuzzle0(Crossword crossword, String[] words, int wordIndex) {
        if (wordIndex >= words.length) {
            return true;
        }

        String word = words[wordIndex];
        char[] wordArr = word.toCharArray();
        System.out.println(word);

        Point p = findHorizontalRow(crossword, word, new Point(0,0));
        while (p != null) {
//            System.out.println(p);
            fillHorizontalRow(crossword, wordArr, p);
            if (crosswordPuzzle0(crossword, words, wordIndex + 1)) {
                return true;
            }
            for (int letterIdx = 0; letterIdx < wordArr.length; letterIdx++) {
                crossword.unsetLast(p.row, p.column + letterIdx);
            }
            p = findHorizontalRow(crossword, word, new Point(p.row, p.column + 1));
        }

        p = findVerticalColumn(crossword, word, new Point(0,0));
        while (p != null) {
            fillVerticalColumn(crossword, wordArr, p);
            if (crosswordPuzzle0(crossword, words, wordIndex + 1)) {
                return true;
            }
            for (int letterIdx = 0; letterIdx < wordArr.length; letterIdx++) {
                crossword.unsetLast(p.row + letterIdx, p.column);
            }
            p = findVerticalColumn(crossword, word, new Point(p.row + 1, p.column));
        }

        return false;
    }

    private static void fillVerticalColumn(Crossword crossword, char[] word, Point p) {
        for (int letterIdx = 0; letterIdx < word.length; letterIdx++) {
            crossword.set(p.row + letterIdx, p.column, word[letterIdx]);
        }
    }

    private static Point findVerticalColumn(Crossword crossword, String word, Point fromPoint) {
        for (int columnIdx = fromPoint.column; columnIdx < crossword.length(); columnIdx++) {
            int rowIdx = 0;

            if (columnIdx == fromPoint.column)
                rowIdx = fromPoint.row;

            for (; rowIdx < crossword.length() - word.length() + 1; rowIdx++) {
                if (crossword.isFillable(rowIdx, columnIdx)) {
                    Point p = new Point(rowIdx, columnIdx);
                    if (isOkeyVerticalPoint(crossword, word, p)) {
                        return p;
                    }
                }
            }

        }
        return null;
    }

    private static void fillHorizontalRow(Crossword crossword, char[] word, Point p) {
        for (int letterIdx = 0; letterIdx < word.length; letterIdx++) {
            crossword.set(p.row, p.column + letterIdx, word[letterIdx]);
        }
    }

    private static Point findHorizontalRow(Crossword crossword, String word, Point fromPoint) {
        for (int rowIdx = fromPoint.row; rowIdx < crossword.length(); rowIdx++) {
            int columnIdx = 0;

            if (rowIdx == fromPoint.column)
                columnIdx = fromPoint.column;

            for (; columnIdx < crossword.length() - word.length() + 1; columnIdx++) {
                if (crossword.isFillable(rowIdx, columnIdx)) {
                    Point p = new Point(rowIdx, columnIdx);
                    if (isOkeyHorizontalPoint(crossword, word, p)) {
                        return p;
                    }
                }
            }
        }
        return null;
    }

    private static boolean isOkeyHorizontalPoint(Crossword crossword, String word, Point p) {
        for (int letterIdx = 0; letterIdx < word.length(); letterIdx++) {
            char crosswordChar = crossword.get(p.row,p.column + letterIdx);
            boolean isValid = crosswordChar == '-' || crosswordChar == word.charAt(letterIdx);
            if (!isValid) {
                return false;
            }
        }
        return p.column + word.length() >= crossword.length() || !crossword.isFillable(p.row, p.column + word.length());
    }

    private static boolean isOkeyVerticalPoint(Crossword crossword, String word, Point p) {
        for (int letterIdx = 0; letterIdx < word.length(); letterIdx++) {
            char crosswordChar = crossword.get(p.row + letterIdx,p.column);
            boolean isValid = crosswordChar == '-' || crosswordChar == word.charAt(letterIdx);
            if (!isValid) {
                return false;
            }
        }
        return p.row + word.length() >= crossword.length() || !crossword.isFillable(p.row + word.length(), p.column);
    }
}
