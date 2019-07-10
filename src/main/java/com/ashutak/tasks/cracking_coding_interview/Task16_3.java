package com.ashutak.tasks.cracking_coding_interview;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Task16_3 {
    private static final int N = 2;
    public static void main (String[] args) {
        IntStream.range(0, 1).forEach(i -> doSimulation());
    }

    private static void doSimulation() {
        var sticks = new AtomicBoolean[N];
        for (int i = 0; i < sticks.length; i++) {
            sticks[i] = new AtomicBoolean();
        }
        var phils = createPhilosophers(sticks);

        var threads = new ArrayList<Thread>();

        for (var p0: phils) {
            final var p = p0;
            var t = new Thread (() -> {
                boolean haveBoth = false;
                while (!haveBoth) {
                    while (! p.takeLeft());
                    if  (! p.takeRight())
                        p.dropLeft();
                    else
                        haveBoth = true;
                }
//                while (! p.takeLeft());
//                while (! p.takeRight());
                System.out.println("Philosopher had dinner: " + p.getId());
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                p.dropLeft();
                p.dropRight();
            });
            threads.add(t);
        }
        threads.forEach(Thread::start);
        System.out.println("Main end.");
    }

    static class Philosopher {
        private final AtomicBoolean[] sticks;
        private final int id;

        Philosopher(int id, AtomicBoolean[] sticks) {
            assert id >= 0 && id < sticks.length;

            this.sticks = sticks;
            this.id = id;
        }

        boolean takeLeft() {
            return getLeft().compareAndSet(false, true);
        }

        private AtomicBoolean getLeft() {
            return sticks[id];
        }
        private AtomicBoolean getRight() {
            return sticks[(id + 1) % (sticks.length - 1)];
        }

        boolean takeRight() {return getRight().compareAndSet(false, true);}
        void dropLeft() {
            getLeft().set(false);
        }

        void dropRight(){
            getRight().set(false);
        }

        public int getId() {
            return id;
        }
    }

    private static Philosopher[] createPhilosophers(final AtomicBoolean[] sticks) {
        var res = new Philosopher[sticks.length];
        for (int i = 0; i < sticks.length; i++) {
            res[i] = new Philosopher(i, sticks);
        }
        return res;
    }

    public static void setZeroRowsAndColumns(int[][] m) {
        if (m == null || m.length == 0)
            return;
        var rowsToZerofy = new BitSet(m.length);
        var columnsToZerofy =  new BitSet(m[0].length);
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++){
                if (m[i][j] == 0) {
                    rowsToZerofy.set(i);
                    columnsToZerofy.set(j);
                }
            }
        }

        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++){
                if (rowsToZerofy.get(i) || columnsToZerofy.get(j)) {
                    m[i][j] = 0;
                }
            }
        }
    }


}
