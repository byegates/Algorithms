# Is Graph Bipartite?
[LaiCode 56](https://app.laicode.io/app/problem/56)

[LeetCode 785](https://leetcode.com/problems/is-graph-bipartite/)
## Problem Description
Determine if an undirected graph is bipartite. A bipartite graph is one in which the nodes can be divided into two groups such that no nodes have direct edges to other nodes in the same group.

Examples

<pre>
1  --   2

    /   

3  --   4
</pre>

is bipartite (1, 3 in group 1 and 2, 4 in group 2).
<pre>
 1  --   2

    /   |

 3  --   4
</pre>

is not bipartite.

Assumptions

The graph is represented by a list of nodes, and the list of nodes is not null.

## Analysis
Standard BFS, with de-dup and tag node and its neighbors into different groups.

There can be isolated nodes in graph (List of GraphNodes), so we'll iterate over the list to make sure we don't miss any node.

But of course we check for duplicates before entering a new BFS with any new nodes in list
## Time & Space
TC: O(V+E)

Why is TC V+E? Go through an example.

SC: O(V)

## LaiCode
The input graph is List of graph nodes, this is different from LeetCode
### BFS
```java
class Solution {
  public boolean isBipartite(List<GraphNode> graph) {
    Map<GraphNode, Integer> visited = new HashMap<>();
    for (GraphNode node : graph) {
      if (visited.containsKey(node)) continue; // early termination, why?
      if (!isBipartite(node, visited)) return false;
    }
    return true;
  }

  // standard BFS
  private boolean isBipartite(GraphNode node, Map<GraphNode, Integer> visited) {
    Queue<GraphNode> q = new ArrayDeque<>();
    q.offer(node);
    visited.put(node, 0);

    while (!q.isEmpty()) {
      GraphNode cur = q.poll();
      int curGroup = visited.get(cur);
      for (GraphNode nei : cur.neighbors) {
        Integer neiGroup = visited.get(nei); // reduce map access, avoid using map.containsKey
        if (neiGroup == null) {
          visited.put(nei, 1 - curGroup);
          q.offer(nei);
        } else if (neiGroup == curGroup) return false;
      }
    }

    return true;
  }
}
```

## LeetCode
There is an undirected graph with n nodes, where each node is numbered between 0 and n - 1. You are given a 2D array graph, where graph[u] is an array of nodes that node u is adjacent to. More formally, for each v in graph[u], there is an undirected edge between node u and node v. The graph has the following properties:

There are no self-edges (graph[u] does not contain u).
There are no parallel edges (graph[u] does not contain duplicate values).
If v is in graph[u], then u is in graph[v] (the graph is undirected).
The graph may not be connected, meaning there may be two nodes u and v such that there is no path between them.
A graph is bipartite if the nodes can be partitioned into two independent sets A and B such that every edge in the graph connects a node in set A and a node in set B.

Return true if and only if it is bipartite.

### Example 1

![Graph1](https://assets.leetcode.com/uploads/2020/10/21/bi2.jpg)

Input: graph = [[1,2,3],[0,2],[0,1,3],[0,2]]

Output: false

### Example 2
![Graph2](https://assets.leetcode.com/uploads/2020/10/21/bi1.jpg)

Input: graph = [[1,3],[0,2],[1,3],[0,2]]

Output: true

Explanation: We can partition the nodes into two sets: {0, 2} and {1, 3}.

### Constraints:
<pre>
graph.length == n
1 <= n <= 100
0 <= graph[u].length < n
0 <= graph[u][i] <= n - 1
graph[u] does not contain u.
All the values of graph[u] are unique.
If graph[u] contains v, then graph[v] contains u.
</pre>

### BFS
```java
class Solution {
    public static boolean isBipartite(int[][] graph) { // // TC: O(V+E), SC: O(V)
        int[] group = new int[graph.length];
        for (int node = 0; node < graph.length; node++)
            if (group[node] == 0)
                if (!isBipartite(node, graph, group))
                    return false;
        return true;
    }
    private static boolean isBipartite(int node, int[][] graph, int[] visited) {
        Queue<Integer> q = new ArrayDeque<>();
        q.offer(node);
        visited[node] = 1; // two group name will be 1 vs -1
        while (!q.isEmpty()) {
            int cur = q.poll();
            int curGroup = visited[cur];
            for (int nei : graph[cur]) {
                if (visited[nei] == 0) {
                    visited[nei] = -curGroup;
                    q.offer(nei);
                } else if (visited[nei] == curGroup) return false;
            }
        }
        return true;
    }
}
```
### DFS
```java

```