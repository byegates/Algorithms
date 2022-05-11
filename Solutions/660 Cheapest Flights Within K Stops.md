# Cheapest Flights Within K Stops
[LeetCode 787](https://leetcode.com/problems/cheapest-flights-within-k-stops/)

[LaiCode 660](https://app.laicode.io/app/problem/660)
## Description
Suppose there are m flights connecting n cities. Flight is represented by an int array int[] where the first element is departure city, the second element is destination city and the third element is the price.

Given a departure city src, a destination city dst, and most # of stops k, return the lowest price of flights can take you from src to dst with at most k stops. If there is no such a route, then return -1.

You can assume that there is no duplicated flights.

Example 1:

Input: n = 3, flights = [[0,1,100],[0,2,1000], [1,2,200]], src = 0, dst = 2, k = 0

Output: 1000

Example 2:

Input: n = 4, flights = [[0,1,100],[1,2,100],[2,0,100],[1,3,600],[2,3,200]], src = 0, dst = 3, k = 1

Output: 700

Example 3:

Input: n = 5, flights = [[4,1,1],[1,2,3],[0,3,2],[0,4,10],[3,1,1],[1,4,3]], src = 2, dst = 1, k = 1

output: -1

# Solution 0: DP ([Bellman Ford](https://en.wikipedia.org/wiki/Bellman%E2%80%93Ford_algorithm))
4ms on LeetCode

## dp definition
We define dp[i][j] as the min cost to take max i flight segments (i - 1 stops) to get to city j, for k stops, we take k + 1 flight segments;

### Example 2 from above
Num of  Cities(n): 4

From    City(src): 0

To      City(dst): 3

Max # of Stops(k): 1

All flights:
[0, 1, 100]
[1, 2, 100]
[2, 0, 100]
[1, 3, 600]
[2, 3, 200]

### Initialization and base case (set dp[0][src] = 0)

|     | 0   | 1   | 2   | 3   |
|-----|-----|-----|-----|-----|
| 0   | 0   | -1  | -1  | -1  |
| 1   | -1  | -1  | -1  | -1  |
| 2   | -1  | -1  | -1  | -1  |


### i = 1 segment(s) (0 stop(s))
flights that don't update below table are ignored

i: 1, flight: [0, 1, 100]

|     | 0   | 1   | 2   | 3   |
|-----|-----|-----|-----|-----|
| 0   | 0   | -1  | -1  | -1  |
| 1   | 0   | 100 | -1  | -1  |
| 2   | -1  | -1  | -1  | -1  |

### i = 2 segment(s) (1 stop(s))
flights that don't update below table are ignored

i: 2, flight: [0, 1, 100]

|     | 0   | 1   | 2   | 3   |
|-----|-----|-----|-----|-----|
| 0   | 0   | -1  | -1  | -1  |
| 1   | 0   | 100 | -1  | -1  |
| 2   | 0   | 100 | -1  | -1  |

i: 2, flight: [1, 2, 100]

|     | 0   | 1   | 2   | 3   |
|-----|-----|-----|-----|-----|
| 0   | 0   | -1  | -1  | -1  |
| 1   | 0   | 100 | -1  | -1  |
| 2   | 0   | 100 | 200 | -1  |

i: 2, flight: [1, 3, 600]

|     | 0   | 1   | 2   | 3   |
|-----|-----|-----|-----|-----|
| 0   | 0   | -1  | -1  | -1  |
| 1   | 0   | 100 | -1  | -1  |
| 2   | 0   | 100 | 200 | 700 |

We return dp[2][dst]: 700.

## Time Complexity
O(k*E), E stands for number of edges in the graph, the upper bound of E would be n^2 as there are n nodes in the graph.

It's also O(k * flights.length)

## Space Complexity
O(k*n) for the new dp matrix we created

```java
import java.util.Arrays;

class Solution {
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {// TC: O(k * EDGE) ==> k*n^2
        int[][] dp = new int[k + 2][n];
        for (int[] a : dp) Arrays.fill(a, -1);
        dp[0][src] = 0;
        for (int i = 1; i <= k + 1; i++) {
            dp[i][src] = 0;
            for (int[] flight : flights)
                if (dp[i - 1][flight[0]] != -1) {
                    int curCost = dp[i - 1][flight[0]] + flight[2];
                    if (dp[i][flight[1]] == -1 || curCost < dp[i][flight[1]])
                        dp[i][flight[1]] = curCost;
                }
        }
        return dp[k + 1][dst];
    }
}
```
### A negative example (Example 3 from above)
Num of  Cities(n): 5

From    City(src): 2

To      City(dst): 1

Max # of Stops(k): 1

All flights:
[4, 1, 1]
[1, 2, 3]
[0, 3, 2]
[0, 4, 10]
[3, 1, 1]
[1, 4, 3]

### Initialization and base case (set dp[0][src] = 0)

|     | 0   | 1   | 2   | 3   | 4   |
|-----|-----|-----|-----|-----|-----|
| 0   | -1  | -1  | 0   | -1  | -1  |
| 1   | -1  | -1  | -1  | -1  | -1  |
| 2   | -1  | -1  | -1  | -1  | -1  |


### i = 1 segment(s) (0 stop(s))
flights that don't update below table are ignored

i: 1, flight: [4, 1, 1]

|     | 0   | 1   | 2   | 3   | 4   |
|-----|-----|-----|-----|-----|-----|
| 0   | -1  | -1  | 0   | -1  | -1  |
| 1   | -1  | -1  | 0   | -1  | -1  |
| 2   | -1  | -1  | -1  | -1  | -1  |

### i = 2 segment(s) (1 stop(s))
flights that don't update below table are ignored

i: 2, flight: [4, 1, 1]

|     | 0   | 1   | 2   | 3   | 4   |
|-----|-----|-----|-----|-----|-----|
| 0   | -1  | -1  | 0   | -1  | -1  |
| 1   | -1  | -1  | 0   | -1  | -1  |
| 2   | -1  | -1  | 0   | -1  | -1  |
What we need to return is dp[k+1][dst] which is dp[2][1] which is -1

## Additional Example

Num of  Cities(n): 6

From    City(src): 0

To      City(dst): 3

Max # of Stops(k): 1

All flights:
[0, 1, 6]
[0, 3, 8]
[0, 4, 27]
[0, 5, 19]
[1, 2, 1]
[1, 3, 2]
[1, 4, 30]
[1, 5, 28]
[2, 3, 7]
[2, 5, 25]
[3, 4, 15]
[3, 5, 23]
[4, 5, 21]

### Initialization and base case (set dp[0][src] = 0)

|     | 0   | 1   | 2   | 3   | 4   | 5   |
|-----|-----|-----|-----|-----|-----|-----|
| 0   | 0   | -1  | -1  | -1  | -1  | -1  |
| 1   | -1  | -1  | -1  | -1  | -1  | -1  |
| 2   | -1  | -1  | -1  | -1  | -1  | -1  |


### i = 1 segment(s) (0 stop(s))
flights that don't update below table are ignored

i: 1, flight: [0, 1, 6]

|     | 0   | 1   | 2   | 3   | 4   | 5   |
|-----|-----|-----|-----|-----|-----|-----|
| 0   | 0   | -1  | -1  | -1  | -1  | -1  |
| 1   | 0   | 6   | -1  | -1  | -1  | -1  |
| 2   | -1  | -1  | -1  | -1  | -1  | -1  |

i: 1, flight: [0, 3, 8]

|     | 0   | 1   | 2   | 3   | 4   | 5   |
|-----|-----|-----|-----|-----|-----|-----|
| 0   | 0   | -1  | -1  | -1  | -1  | -1  |
| 1   | 0   | 6   | -1  | 8   | -1  | -1  |
| 2   | -1  | -1  | -1  | -1  | -1  | -1  |

i: 1, flight: [0, 4, 27]

|     | 0   | 1   | 2   | 3   | 4   | 5   |
|-----|-----|-----|-----|-----|-----|-----|
| 0   | 0   | -1  | -1  | -1  | -1  | -1  |
| 1   | 0   | 6   | -1  | 8   | 27  | -1  |
| 2   | -1  | -1  | -1  | -1  | -1  | -1  |

i: 1, flight: [0, 5, 19]

|     | 0   | 1   | 2   | 3   | 4   | 5   |
|-----|-----|-----|-----|-----|-----|-----|
| 0   | 0   | -1  | -1  | -1  | -1  | -1  |
| 1   | 0   | 6   | -1  | 8   | 27  | 19  |
| 2   | -1  | -1  | -1  | -1  | -1  | -1  |

### i = 2 segment(s) (1 stop(s))
flights that don't update below table are ignored

i: 2, flight: [0, 1, 6]

|     | 0   | 1   | 2   | 3   | 4   | 5   |
|-----|-----|-----|-----|-----|-----|-----|
| 0   | 0   | -1  | -1  | -1  | -1  | -1  |
| 1   | 0   | 6   | -1  | 8   | 27  | 19  |
| 2   | 0   | 6   | -1  | -1  | -1  | -1  |

i: 2, flight: [0, 3, 8]

|     | 0   | 1   | 2   | 3   | 4   | 5   |
|-----|-----|-----|-----|-----|-----|-----|
| 0   | 0   | -1  | -1  | -1  | -1  | -1  |
| 1   | 0   | 6   | -1  | 8   | 27  | 19  |
| 2   | 0   | 6   | -1  | 8   | -1  | -1  |

i: 2, flight: [0, 4, 27]

|     | 0   | 1   | 2   | 3   | 4   | 5   |
|-----|-----|-----|-----|-----|-----|-----|
| 0   | 0   | -1  | -1  | -1  | -1  | -1  |
| 1   | 0   | 6   | -1  | 8   | 27  | 19  |
| 2   | 0   | 6   | -1  | 8   | 27  | -1  |

i: 2, flight: [0, 5, 19]

|     | 0   | 1   | 2   | 3   | 4   | 5   |
|-----|-----|-----|-----|-----|-----|-----|
| 0   | 0   | -1  | -1  | -1  | -1  | -1  |
| 1   | 0   | 6   | -1  | 8   | 27  | 19  |
| 2   | 0   | 6   | -1  | 8   | 27  | 19  |

i: 2, flight: [1, 2, 1]

|     | 0   | 1   | 2   | 3   | 4   | 5   |
|-----|-----|-----|-----|-----|-----|-----|
| 0   | 0   | -1  | -1  | -1  | -1  | -1  |
| 1   | 0   | 6   | -1  | 8   | 27  | 19  |
| 2   | 0   | 6   | 7   | 8   | 27  | 19  |

i: 2, flight: [3, 4, 15]

|     | 0   | 1   | 2   | 3   | 4   | 5   |
|-----|-----|-----|-----|-----|-----|-----|
| 0   | 0   | -1  | -1  | -1  | -1  | -1  |
| 1   | 0   | 6   | -1  | 8   | 27  | 19  |
| 2   | 0   | 6   | 7   | 8   | 23  | 19  |

# Prep for DFS and BFS ()
Visual representation of graph

![Visual representation of graph](https://assets.leetcode.com/uploads/2022/03/18/cheapest-flights-within-k-stops-3drawio.png "Cities as Graph Nodes and cost as weight")

# Solution 1: DFS
## idea
We can represent the graph as a map or an array of list(as map key are int array anyway)
The value of array or map will be edges represented by (adjacent) list of Pair value, each pair represent a destination and the cost of flight from current map key (or array index) to this destination.
Array takes more space, and map runs slightly slower.
So it's going to be something like below:

Below will pass on LaiCode, can't pass on LeetCode (Time Limit Exceeded).
```java
public class Solution {

    static class Pair {
        int dst, cost;
        Pair(int dst, int cost) {
            this.dst = dst;
            this.cost = cost;
        }
    }

    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        List<Pair>[] graph = createGraph(n, flights);
        boolean[] visited = new boolean[n];
        int[] res = new int[]{Integer.MAX_VALUE};
        dfs(src, dst, k + 1, 0, visited, res, graph);
        return res[0] == Integer.MAX_VALUE ? -1 : res[0];
    }

    private void dfs(int src, int dst, int k, int cost, boolean[] visited, int[] res, List<Pair>[] graph) {
        if (src == dst) {
            res[0] = cost;
            return;
        }
        if (k == 0) return;

        List<Pair> neighbors = graph[src];
        if (neighbors == null) return;

        for (Pair pair : neighbors) {
            if (visited[pair.dst]) continue; // standard pruning?
            int curCost = cost + pair.cost;
            // pruning, very important to improve time performance
            if (curCost > res[0]) continue;
            visited[pair.dst] = true; // pruning
            dfs(pair.dst, dst, k - 1, curCost, visited, res, graph);
            visited[pair.dst] = false; //吃了🤮
        }
    }

    private List<Pair>[] createGraph(int n, int[][] flights) {
        List<Pair>[] graph = new List[n];
        for (int[] flight : flights) {
            if (graph[flight[0]] == null)
                graph[flight[0]] = new ArrayList<>();
            graph[flight[0]].add(new Pair(flight[1], flight[2]));
        }
        return graph;
    }


}
```
# Solution 2: BFS with pruning
The pair we save in queue have a different meaning than in the graph.
In the graph the pair means destination index and cost from it's related source.
Pair in queue means the current minimum cost from the single src in input to the current destination index.

```java
public class Solution {

    static class Pair {
        int dst, cost;
        Pair(int dst, int cost) {
            this.dst = dst;
            this.cost = cost;
        }
    }

    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        List<Pair>[] graph = createGraph(n, flights);
        Queue<Pair> q = new ArrayDeque<>();
        int res = Integer.MAX_VALUE;

        q.offer(new Pair(src, 0));

        for (int segments = 0; !q.isEmpty() && segments <= k + 1; segments++) { // k stops will have k + 1 segments of flights
            int size = q.size();
            while (size-- > 0) {
                Pair cur = q.poll();
                if (cur.dst == dst) res = Math.min(res, cur.cost);
                List<Pair> neighbors = graph[cur.dst];
                if (neighbors == null) continue;
                for (Pair nei : neighbors) {
                    int newCost = cur.cost + nei.cost;
                    if (newCost > res) continue; // pruning
                    q.offer(new Pair(nei.dst, newCost));
                }
            }
        }

        return res == Integer.MAX_VALUE ? -1 : res;
    }
    
    private List<Pair>[] createGraph(int n, int[][] flights) {
        List<Pair>[] graph = new List[n];
        for (int[] flight : flights) {
            if (graph[flight[0]] == null)
                graph[flight[0]] = new ArrayList<>();
            graph[flight[0]].add(new Pair(flight[1], flight[2]));
        }
        return graph;
    }

}

```

## NOTE

Another way to update result price, notice the differences.

```java
public class Solution {

    static class Pair {
        int dst, cost;
        Pair(int dst, int cost) {
            this.dst = dst;
            this.cost = cost;
        }
    }

    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        if (src == dst) return 0;
        List<Pair>[] graph = createGraph(n, flights);
        Queue<Pair> q = new ArrayDeque<>();
        int res = Integer.MAX_VALUE;

        q.offer(new Pair(src, 0));

        for (int segments = 0; !q.isEmpty() && segments <= k; segments++) { // k stops will have k + 1 segments of flights
            int size = q.size();
            while (size-- > 0) {
                Pair cur = q.poll();
                List<Pair> neighbors = graph[cur.dst];
                if (neighbors == null) continue;
                for (Pair nei : neighbors) {
                    int newCost = cur.cost + nei.cost;
                    if (newCost > res) continue;
                    q.offer(new Pair(nei.dst, newCost));
                    if (nei.dst == dst) res = Math.min(res, newCost);
                }
            }
        }

        return res == Integer.MAX_VALUE ? -1 : res;
    }

    private List<Pair>[] createGraph(int n, int[][] flights) {
        List<Pair>[] graph = new List[n];
        for (int[] flight : flights) {
            if (graph[flight[0]] == null)
                graph[flight[0]] = new ArrayList<>();
            graph[flight[0]].add(new Pair(flight[1], flight[2]));
        }
        return graph;
    }
    
}
```
# Solution 3： Dijkstra's Algorithm
Would this really work?
```java
public class Solution {

    static class Pair implements Comparable<Pair>{
        int dst, cost, segments;
        Pair(int dst, int cost, int segments) {
            this.dst = dst;
            this.cost = cost;
            this.segments = segments;
        }

        @Override
        public int compareTo(Pair o) {
            return Integer.compare(cost, o.cost);
        }
    }

    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        List<Pair>[] graph = createGraph(n, flights);
        PriorityQueue<Pair> q = new PriorityQueue<>();
        int res = Integer.MAX_VALUE;

        q.offer(new Pair(src, 0, k + 1));

        while (!q.isEmpty()) {
            Pair cur = q.poll();

            if (cur.dst == dst) return cur.cost;
            if (cur.segments == 0) continue;
            List<Pair> neighbors = graph[cur.dst];
            if (neighbors == null) continue;

            for (Pair nei : neighbors)
                q.offer(new Pair(nei.dst, cur.cost + nei.cost, cur.segments - 1));

        }

        return -1;
    }

    private List<Pair>[] createGraph(int n, int[][] flights) {
        List<Pair>[] graph = new List[n];
        for (int[] flight : flights) {
            if (graph[flight[0]] == null)
                graph[flight[0]] = new ArrayList<>();
            graph[flight[0]].add(new Pair(flight[1], flight[2], 0)); // segments mean nothing here, just for the sake of convenience, we use the same Pair class both in priority queue and here
        }
        return graph;
    }
    
}
```