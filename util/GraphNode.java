package util;

import java.util.*;

public class GraphNode {
    public int key;
    public HashSet<GraphNode> neighbors;

    public GraphNode(int key) {
        this.key = key;
        this.neighbors = new HashSet<>();
    }

    public static void printListGraph(List<GraphNode> graph) {
        for (GraphNode node : graph) {
            System.out.printf("%d : ", node.key);
            for (GraphNode nei : node.neighbors) System.out.printf("%d, ", nei.key);
            System.out.println();
        }
    }

    public static void printHashMapGraph(HashMap<Integer, GraphNode> graph) {
        for (Map.Entry<Integer, GraphNode> set : graph.entrySet()) {
            System.out.printf("%d : ", set.getKey());
            for (GraphNode nei : set.getValue().neighbors) System.out.printf("%d, ", nei.key);
            System.out.println();
        }
    }

    public static HashMap<Integer, GraphNode> fromMatrix(int[][] matrix) { // n * 2 matrix
        HashMap<Integer, GraphNode> graph = new HashMap<>();
        for (int[] edges : matrix) {
            for (Integer i : edges) if (!graph.containsKey(i)) graph.put(i, new GraphNode(i)); // must check first, otherwise object will be replaced
            for (int i = 0; i < 2; i++) graph.get(edges[i]).neighbors.add(graph.get(edges[1-i]));
        }
        return graph;
    }

    public static List<GraphNode> fromMatrixToList(int[][] matrix) {
        List<GraphNode> graph = new ArrayList<>();
        HashMap<Integer, GraphNode> nodes = fromMatrix(matrix);
        for (Map.Entry<Integer, GraphNode> set : nodes.entrySet()) graph.add(set.getValue());
        return graph;
    }

    public static boolean isBipartite(List<GraphNode> graph) { // // TC: O(V+E), SC: O(V)
        Map<GraphNode, Integer> visited = new HashMap<>();
        for (GraphNode node : graph)
            if (!isBipartite(node, visited)) return false;
        return true;
    }
    private static boolean isBipartite(GraphNode node, Map<GraphNode, Integer> visited) {
        if (visited.containsKey(node)) return true;

        Queue<GraphNode> q = new ArrayDeque<>();
        q.offer(node);
        visited.put(node, 0);
        while (!q.isEmpty()) {
            GraphNode cur = q.poll();
            int curGroup = visited.get(cur);
            int neiGroup = 1 - curGroup;
            for (GraphNode nei : cur.neighbors) {
                Integer assignedGroup = visited.get(nei);
                if (assignedGroup == null) {
                    visited.put(nei, neiGroup);
                    q.offer(nei);
                } else if (assignedGroup != neiGroup) return false;
            }
        }
        return true;
    } // TC: V+E?, SC: E
}
