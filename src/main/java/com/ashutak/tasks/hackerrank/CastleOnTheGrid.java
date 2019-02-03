package com.ashutak.tasks.hackerrank;

import java.util.*;
import java.util.function.BiFunction;

public class CastleOnTheGrid {
    private static class Grid {
        private final String[] grid;

        Grid(String[] grid) {
            this.grid = grid;
        }

        boolean isAvailiable(int x, int y) {
            return grid[x].charAt(y) == '.';
        }

        boolean isValid(int x) {
            return x >= 0 && x < grid.length;
        }
    }

    private static class Dot {
        final int x, y;

        private Dot(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Dot dot = (Dot) o;
            return x == dot.x &&
                    y == dot.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    private static final String[] spec = new String[]{
                    ".X..X.X..X..X.......XX..XX....X.X...X........X.....XX.X.X...X.X...X..X",
                    "...X.....XX.........XX......X.X.......X......X..XX.X..X..X.....X.X....",
                    "............X.......X........X..X.X......X.......X...X.X.....X.X...X..",
                    ".........X....XX.X.X.X......X..X......X.....X.........X..X.......XX...",
                    ".....X.......X.X.....XX.....X.XXX.........X.....X.X....XX......XX.....",
                    "..X....X..........................X...X.........XX.....X..............",
                    "......X.......X...XX.....X.X....X.......X.............X........X.X....",
                    "...X.X.XX.XX...X............XX...X.....X..X..X....X.........X.........",
                    "X.XX........X..........XX..X.X..X.XX.XXX..X........X..X.....XX......X.",
                    "......X..XXX.......X..XX.XX...........X.....XX..X..X.X......X.X...X...",
                    "X........X.X....X..X..................XX......X..X.......X.....X..X...",
                    "...X......X....XX.......X.....X...........X..X....X.....XXX...X...X.X.",
                    "......X..X....X.XXX.X.....X..X....XX.....X....X.....X...X...........X.",
                    "....X..X.X...XX..X.X.X..X.....X......X..X......X.X.X.X......X......X..",
                    "..X..............X...X.........X........X...........X..X.X......X....X",
                    ".X....X..X......X.........X.....XX....XX............XX..X...X...X.....",
                    "...........X....X.X...XX...X......X...............X....XX..........X..",
                    ".X..X..XX..X...X.....XX...XX...........X.....X..XXX.........XX..X....X",
                    ".XX........XX.XX..........XX............X.........X.XXXX.X.X.........X",
                    ".....X........X......X.............X.......X............X....X...X..X.",
                    "X..X.X..X...........XX..X.....X......X...XXX........XX...........X....",
                    "..X....X.XX.X.....X..X..X...........X......X..........X.....X.......X.",
                    "..........X.X...X.....X....XX.XX.......XX...X.............XXX..X..X..X",
                    "X.....X....XX...X.X...X.X.X..X.X..X....X..X..XXX...X...........X.X.X..",
                    "...XX.X....X...X.....XX...X.....X..XXX.......XX......X....XX......X.XX",
                    "X..X......X.....X......X.X...X............X.X..........X.X.X..X......X",
                    "..X....X..X.X....XX....X.XXX..X.XX.....X........X..X...X...X.X......X.",
                    ".......X...............XX..........X...X......XX...X.X........X.......",
                    "XXX....X.....X..X.....X.X.....XX..X.......X..X.....XX.......X..X.....X",
                    ".......X......X.......X..X.......X.........X...X.........X...X.....XXX",
                    "...X..X....X....X.X..XX......X.......X............X...................",
                    ".X.....X............X...............X.....X.X...X...X.XXX..X....X..XXX",
                    "..........X........X...........XX..........X..............X.....XX.X..",
                    ".X...............XX..X.X......................X..X...X......X.....X...",
                    "XX..X.X..XXXX..X..........XX..XX..X.............X................XX...",
                    "......X.XX..X...............X.X....X....X......X.....X..XX............",
                    "..X.X..X..X......X..X................X......X...X......X.XX...X..X....",
                    ".........X............X......X......XX.X..................X.....X.....",
                    "X..X....X...........X.....XX..X.......XXX.....XXX.......X....X.....X.X",
                    "XX..............XXX....X.X......XX..........X....X.....X......X.......",
                    ".......X.XX.......X......X..XXX.............X.......XX.....X.XX.......",
                    "..X.X.....XXX.X.......X.X.........X..X...X...X..X.....X.....XX.......X",
                    "..XX........XX....X..XX..X...XXX.................X..X...........X.....",
                    "...X........X..X.....X......X.X...XXX..X..XX.X..X...X........X.XXXX...",
                    "...X.X....X.....X.X.......X..............X...X.X.XX...X...XX.X.......X",
                    "......X...........X.......X.....XXXX...........X.X.XX......X...X......",
                    "....X......X......XXX..XX.X.......X.............X.......X.........X..X",
                    "..X..X..X......X.....X............XX....X..........X......X..X..X.X...",
                    "X.........X..X..XX........X.X.X......................X.X....X.....X...",
                    ".....X.X...X.X..X...X...XX...X...X............X..............X...X....",
                    "......................X....X...X....X.X..............XX.....X.........",
                    ".................X..X.....X.....XX......X.......X......X.........X.X..",
                    "...........X..X...X.....X..X.............X............X..X..XX.X......",
                    "X..........X.X..X..X..........X.XX.............X...X.XX........X......",
                    "..XX.XX.....X.....X..X.....X.....X...X...........X..X..X....X.........",
                    "..X.XX...X.X....X.X....X..X.X...X..X.........X..X.....................",
                    "............X...................X....X.X......X.XX..X...X....X..XX.XX.",
                    "...X..X.X....X..............X..X.....X.X.........X..........X.......X.",
                    "..X...........................X......X.X...X........X.................",
                    ".........X..X...............X...........X..X.X......X.................",
                    "..XX.............XX.X.........X....XX........X..X...X........X.....X..",
                    ".............X.X....X.X...X...X.....X...X.....X.....X..X......X......X",
                    ".....X....X.X.X...XXX...X.X.X.X...X...X.X.....X..X...........X.X.X...X",
                    "...XX.X...XX......X..........X.......XX..X.......X...X..X.X......X....",
                    ".......X.......X....................X..........XX.....XXXX..X.X.......",
                    ".....X...XX...X........X..X...X.X...X..........X...X.........X........",
                    "XXX..XX.....X...................X.....X.X.......X.X.X..X..............",
                    "....X.........X.....X.X...XX.....XX......X..........XX..X.XXX...X.X...",
                    "..X...............X.XX.......X....X......X......X.X.X.......X.......X.",
                    "..X.X.......XXX..X....X...X....X.....X.X......X..X......X............."
    };

    static boolean isValid(Dot dot, String[] grid) {
        return dot.x >= 0 && dot.x < grid.length
                && dot.y >= 0 && dot.y < grid.length
                && grid[dot.x].charAt(dot.y) == '.';

    }


    static int minimumMoves(String[] grid, int startX, int startY, int goalX, int goalY) {
        Dot root = new Dot(startX, startY);

        Deque<Dot> q = new LinkedList<>();
        Map<Dot, Integer> dists = new HashMap<>();
        // directions:
        // 1 - x
        // 0 - None
        // -1 - y
        Map<Dot, Integer> directions = new HashMap<>();

        dists.put(root, 0);
        q.addLast(root);
        directions.put(root, -1);


        int[][] possibleMoves = new int[][]{
                {1, 0, 0},
                {-1, 0, 1},
                {0, 1, 2},
                {0, -1, 3}
        };
        while (!q.isEmpty()) {
            Dot dot = q.removeFirst();
            int oldDirection = directions.get(dot);
            int dist = dists.get(dot);

            for (int i = 0; i < 4; i++) {
                Dot newDot = new Dot(dot.x + possibleMoves[i][0], dot.y + possibleMoves[i][1]);
                int newDirection = possibleMoves[i][2];
                int newDist = oldDirection == newDirection ? dist : dist + 1;
                Integer oldDistOfNewDot = dists.get(newDot);
                if (isValid(newDot, grid) && (oldDistOfNewDot == null || newDist <= oldDistOfNewDot)) {
                    q.addLast(newDot);
                    dists.put(newDot, newDist);
                    directions.put(newDot, newDirection);
                }
            }
        }

        return dists.get(new Dot(goalX, goalY));
    }
//
//    private static int minimumMoves0(final Grid grid, int startX, int startY,
//                                     final int goalX, final int goalY, int moves,
//                                     Set<Dot> seenDots, Direction direction) {
//        Deque<Dot> path = new ArrayDeque<>();
//
//        while (true) {
//            if (startX == goalX && startY == goalY)
//                return moves;
//
//            if (!grid.isValid(startX)
//                    || !grid.isValid(startY)
//                    || !grid.isAvailiable(startX, startY)
//                    || seenDots.contains(new Dot(startX, startY))) {
//                return Integer.MAX_VALUE;
//            }
//
//            int movesX = direction == Direction.X ? moves : moves + 1;
//            int movesY = direction == Direction.Y ? moves : moves + 1;
//            Dot dot = new Dot(startX, startY);
//            seenDots.add(dot);
//            int res0 = minimumMoves0(grid, startX + 1, startY, goalX, goalY, movesX, seenDots, Direction.X);
//            int res1 = minimumMoves0(grid, startX - 1, startY, goalX, goalY, movesX, seenDots, Direction.X);
//            int res2 = minimumMoves0(grid, startX, startY + 1, goalX, goalY, movesY, seenDots, Direction.Y);
//            int res3 = minimumMoves0(grid, startX, startY - 1, goalX, goalY, movesY, seenDots, Direction.Y);
//            seenDots.remove(dot);
//
//            return Math.min(Math.min(res0, res1), Math.min(res2, res3));
//        }
//    }
//
//    private static int nextX(int startX, int goalX, int y, Grid grid) {
//        return next(startX, goalX, y, grid, grid::isAvailiable);
//    }
//
//    private static int nextY(int startY, int goalY, int x, Grid grid) {
//        return next(startY, goalY, x, grid, (idx, xx) -> grid.isAvailiable(xx, idx));
//    }
//
//    private static int next(int start, int goal, int constant, Grid grid, BiFunction<Integer, Integer, Boolean> isAvailiable) {
//        if (start == goal) {
//            return goal;
//        }
//        if (start < goal) {
//            int idx = start + 1;
//            while (idx < goal) {
//                if (!isAvailiable.apply(idx, constant)) {
//                    return idx - 1;
//                }
//                idx++;
//            }
//            return goal;
//        } else {
//            int idx = start - 1;
//            while (idx > goal) {
//                if (!isAvailiable.apply(idx, constant)) {
//                    return idx + 1;
//                }
//                idx--;
//            }
//            return goal;
//        }
//    }

}
