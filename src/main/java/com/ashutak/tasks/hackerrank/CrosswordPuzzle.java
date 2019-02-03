package com.ashutak.tasks.hackerrank;

import java.util.*;

public class CrosswordPuzzle {
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

        boolean set(int row, int column, char ch) {
            char curChar = crossword[row][column].getLast();
            if (curChar == ch || curChar == '-') {
                crossword[row][column].addLast(ch);
                return true;
            }
            return false;
        }

        void unsetLast(int row, int column) {
            crossword[row][column].removeLast();
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (Deque<Character>[] row : crossword) {
                for (Deque<Character> stack : row) {
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

    static String[] crosswordPuzzle(String[] crossword, String words) {
        char[][] crosswordArr = new char[crossword.length][crossword.length];
        for (int i = 0; i < crossword.length; i++) {
            crosswordArr[i] = crossword[i].toCharArray();
        }
        Crossword finalCrossword = new Crossword(crosswordArr);

        Map<Integer, List<Point>> wordLength2StartPointsByHorizontal = Collections.unmodifiableMap(
                createWordLengthIndexInCrosswordByHorizontal(finalCrossword));
        Map<Integer, List<Point>> wordLength2StartPointsByVertical = Collections.unmodifiableMap(
                createWordLengthIndexInCrosswordByVertical(finalCrossword));

        if (!crosswordPuzzle0(finalCrossword, words.split(";"), 0, wordLength2StartPointsByHorizontal,
                wordLength2StartPointsByVertical, new HashSet<>(), new HashSet<>())) {
            throw new IllegalStateException();
        }
        for (int i = 0; i < crosswordArr.length; i++) {
            crossword[i] = finalCrossword.getRowAsString(i);
        }
        return crossword;
    }

    private static Map<Integer, List<Point>> createWordLengthIndexInCrosswordByHorizontal(Crossword crossword) {
        Map<Integer, List<Point>> wordLength2StartPoints = new HashMap<>();
        for (int row = 0; row < crossword.length(); row++) {
            for (int column = 0; column < crossword.length(); column++) {
                if (crossword.isFillable(row, column)) {
                    Point startPoint = new Point(row, column);
                    column++;
                    while (column < crossword.length() && crossword.isFillable(row, column)) {
                        column++;
                    }
                    int wordLength = column - startPoint.column;
                    wordLength2StartPoints.compute(wordLength, (k, v) -> {
                        if (v == null) {
                            v = new ArrayList<>();
                        }
                        v.add(startPoint);
                        return v;
                    });
                }
            }
        }
        return wordLength2StartPoints;
    }

    private static Map<Integer, List<Point>> createWordLengthIndexInCrosswordByVertical(Crossword crossword) {
        Map<Integer, List<Point>> wordLength2StartPoints = new HashMap<>();
        for (int column = 0; column < crossword.length(); column++) {
            for (int row = 0; row < crossword.length(); row++) {
                if (crossword.isFillable(row, column)) {
                    Point startPoint = new Point(row, column);
                    row++;
                    while (row < crossword.length() && crossword.isFillable(row, column)) {
                        row++;
                    }
                    int wordLength = row - startPoint.row;
                    wordLength2StartPoints.compute(wordLength, (k, v) -> {
                        if (v == null) {
                            v = new ArrayList<>();
                        }
                        v.add(startPoint);
                        return v;
                    });
                }
            }
        }
        return wordLength2StartPoints;
    }

    private static boolean crosswordPuzzle0(Crossword crossword, String[] words, int wordIndex,
                                            Map<Integer, List<Point>> wordLength2StartPointsByHorizontal,
                                            Map<Integer, List<Point>> wordLength2StartPointsByVertical,
                                            Set<Point> usedHorizintalPositions,
                                            Set<Point> usedVerticalPositions) {
        if (wordIndex >= words.length) {
            return true;
        }

        String word = words[wordIndex];

        List<Point> points = wordLength2StartPointsByHorizontal.get(word.length());
        if (points != null) {
            for (Point p : points) {
                if (!usedHorizintalPositions.contains(p) && fillWordInCrosswordHorisontally(crossword, word, p)) {
                    usedHorizintalPositions.add(p);
                    if (crosswordPuzzle0(crossword, words, wordIndex + 1,
                            wordLength2StartPointsByHorizontal, wordLength2StartPointsByVertical,
                            usedHorizintalPositions, usedVerticalPositions)) {
                        return true;
                    }
                    usedHorizintalPositions.remove(p);
                    for (int letterIdx = 0; letterIdx < word.length(); letterIdx++) {
                        crossword.unsetLast(p.row, p.column + letterIdx);
                    }
                }
            }
        }
        points = wordLength2StartPointsByVertical.get(word.length());
        if (points != null) {
            for (Point p : points) {
                if (!usedVerticalPositions.contains(p) && fillWordInCrosswordVertically(crossword, word, p)) {
                    usedVerticalPositions.add(p);
                    if (crosswordPuzzle0(crossword, words, wordIndex + 1,
                            wordLength2StartPointsByHorizontal, wordLength2StartPointsByVertical,
                            usedHorizintalPositions, usedVerticalPositions)) {
                        return true;
                    }
                    usedVerticalPositions.remove(p);
                    for (int letterIdx = 0; letterIdx < word.length(); letterIdx++) {
                        crossword.unsetLast(p.row + letterIdx, p.column);
                    }
                }
            }
        }
        return false;
    }

    private static boolean fillWordInCrosswordHorisontally(Crossword crossword, String word, Point p) {
        for (int letterIdx = 0; letterIdx < word.length(); letterIdx++) {
            if (!crossword.set(p.row, p.column + letterIdx, word.charAt(letterIdx))) {
                letterIdx--;
                for (; letterIdx >= 0; letterIdx--) {
                    crossword.unsetLast(p.row, p.column + letterIdx);
                }
                return false;
            }
        }
        return true;
    }

    private static boolean fillWordInCrosswordVertically(Crossword crossword, String word, Point p) {
        for (int letterIdx = 0; letterIdx < word.length(); letterIdx++) {
            if (!crossword.set(p.row + letterIdx, p.column, word.charAt(letterIdx))) {
                letterIdx--;
                for (; letterIdx >= 0; letterIdx--) {
                    crossword.unsetLast(p.row + letterIdx, p.column);
                }
                return false;
            }
        }
        return true;
    }
}
