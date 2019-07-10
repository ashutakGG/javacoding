package com.ashutak.tasks.cracking_coding_interview;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Task9_4 {
    public static <T> Set<Set<T>> allSubsets(Set<T> s0) {
        if (s0.isEmpty()) {
            var res =  new HashSet<Set<T>>(new HashSet<>());
            res.add(new HashSet<>());
            return res;
        }

        var s = new HashSet<>(s0);

        var el = s.iterator().next();

        s.remove(el);
        var subsets = allSubsets(s);

        var res = new HashSet<>(subsets);

        for (var subset: subsets) {
            var ss = new HashSet<>(subset);
            ss.add(el);
            res.add(ss);
        }

        return res;
    }

    public static void main(String[] args) {
        var s = Set.of(1,2,3,4,5,6,7,8,9);

        System.out.println(allSubsets(s));

        System.out.println(Set.copyOf(List.of(1,2,3,3,4,5)));
    }
}
