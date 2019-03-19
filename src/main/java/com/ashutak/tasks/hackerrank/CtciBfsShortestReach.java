package com.ashutak.tasks.hackerrank;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.*;

public class CtciBfsShortestReach {
    private static class Graph {
        private static final int CONST = 6;
        private final Map<Integer, Node> nodesMap = new HashMap<>();
        private final int n;

        Graph(int n) {
            this.n = n;
            for (int i = 1; i <= n; i++) {
                Node node = new Node(i);
                nodesMap.put(i, node);
            }
        }

        private static class Node {
            private final int value;
            private final Set<Node> adjacent = new HashSet<>();

            private Node(int value) {
                this.value = value;
            }

            @Override
            public String toString() {
                return "Node{" + value + '}';
            }
        }

        void addEdge(int uValue, int vValue) {
            Node u = getNode(uValue);
            Node v = getNode(vValue);
            u.adjacent.add(v);
            v.adjacent.add(u);
        }

        Node getNode (int val) {
            return nodesMap.get(val);
        }

        int[] calculateLengths(int startingV) {
            int[] res = new int[n - 1];

            Deque<Node> nextToProcess = new LinkedList<>();
            Map<Node, Integer> lengthForChiledsMap = new HashMap<>();
            Set<Node> visited = new HashSet<>();
            Node startingNode = getNode(startingV);
            visited.add(startingNode);
            nextToProcess.addLast(startingNode);
            lengthForChiledsMap.put(startingNode, CONST);

            while (!nextToProcess.isEmpty()) {
                Node node = nextToProcess.removeFirst();
                int lengthForChileds = lengthForChiledsMap.get(node);

                for (Node child: node.adjacent) {
                    if (!visited.contains(child)) {
                        visited.add(child);
                        nextToProcess.addLast(child);
                        lengthForChiledsMap.put(child, lengthForChileds + CONST);

                        addToResult(res, startingV, child.value, lengthForChileds);
                    }
                }
            }

            for (int i = 0; i < res.length; i++) {
                if (res[i] == 0) {
                    res[i] = -1;
                }
            }

            return res;
        }

        private void addToResult(int[] res, int startingV, int v, int length) {
            if (v < startingV) {
                res[v - 1] = length;
            } else {
                res[v - 2] = length;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int q = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int qItr = 0; qItr < q; qItr++) {
            String[] n_m = scanner.nextLine().split(" ");

            int n = Integer.parseInt(n_m[0]);

            int m = Integer.parseInt(n_m[1]);

            Graph g = new Graph(n);

            for (int edgeItr = 0; edgeItr < m; edgeItr++) {
                String[] u_v = scanner.nextLine().split(" ");
                g.addEdge(Integer.parseInt(u_v[0]), Integer.parseInt(u_v[1]));
            }

            int s = Integer.parseInt(scanner.nextLine());

            int[] res = g.calculateLengths(s);

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < res.length; i++) {
                sb.append(res[i]);
                if (i != res.length - 1) {
                    sb.append(' ');
                }
            }
            bufferedWriter.write(sb.toString());
            bufferedWriter.newLine();
        }

        bufferedWriter.close();

        scanner.close();
    }
}
