package com.ashutak.tasks.ok;

import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.*;

public class Task16Test {
    private static ConcurrentHashMap<Long, Long> map = new ConcurrentHashMap<>();
    @Test
    public void printMatrix() throws Exception {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for(;;) {
                    long v = nextValue();

                    if (map.putIfAbsent(v, v) != null)
                        throw new IllegalStateException(v + "");
                }
            }
        };

        Thread th1 = new Thread(runnable);
        Thread th2 = new Thread(runnable);

        th1.start();
        th2.start();

        th1.join();
        th2.join();

    }

    private static volatile long i = 0;

    public  static long nextValue() {
        return ++i;
    }

}