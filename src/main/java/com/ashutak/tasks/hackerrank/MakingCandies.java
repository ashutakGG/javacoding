package com.ashutak.tasks.hackerrank;

public class MakingCandies {
    /**
     * @param m the starting number of machines
     * @param w the starting number of workers
     * @param p the cost of a new hire or a new machine
     * @param n the number of candies to produce
     * @return minimum passes
     */
    static long minimumPasses(long m, long w, final long p, final long n) {
        long curCandies = m * w;
        int passes = 1;

        long w0 = w;
        long m0 = m;
        long curCandies0 = curCandies;

        while (curCandies < n) {
            if (curCandies >= p) { // We can buy increase m and w
                // Case with buying
                long numCanBuy = curCandies / p;
                long diff = Math.abs(m - w);

                if (diff <= numCanBuy) {
                    if (m > w) {
                        w0 += numCanBuy;
                    } else {
                        m0 += numCanBuy;
                    }
                } else {
                    long split1 = (numCanBuy - diff) / 2;
                    long split2 = (numCanBuy - diff) - split1;

                    if (m > w) {
                        w0 += diff + split1;
                        m0 += split2;
                    } else {
                        m0 += diff + split1;
                        w0 += split2;
                    }
                }

                curCandies0 = curCandies - numCanBuy * p + m0 * w0;

                curCandies = curCandies0;
                m = m0;
                w = w0;
            } else { // Not able to buy more
                curCandies += m * w;
            }
            passes++;
        }
        return passes;
    }
}
