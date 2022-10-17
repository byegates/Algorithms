# [207. Course Schedule](https://leetcode.com/problems/course-schedule/)

## Topological Sort (5ms, 85.29%)
TC: O(V + E)

SC: O(V + E)
标准拓扑排序:
1. 建图，数入度(in-degree)
2. in-degree 为0的全部入队
3. 一边poll一边计数一边减in-degree
4. 返回poll的元素数量==总节点数量
```java
class Solution {
    public boolean canFinish(int numCourses, int[][] preqs) {
        List<Integer>[] graph = new List[numCourses];
        int[] indegrees = new int[numCourses];
        
        for (var preq : preqs) {
            int nei = preq[0], cur = preq[1];
            if (graph[cur] == null) graph[cur] = new ArrayList<>();
            graph[cur].add(nei);
            indegrees[nei]++;
        }
        
        Queue<Integer> q = new ArrayDeque<>();
        
        for (int cur = 0; cur < graph.length; cur++)
            if (indegrees[cur] == 0) q.offer(cur);
        
        int count = 0;
        while (!q.isEmpty()) {
            count++;
            int cur = q.poll();
            if (graph[cur] == null) continue;
            for (int nei : graph[cur])
                if (--indegrees[nei] == 0) q.offer(nei);
        }
        
        return count == numCourses;
    }
}
```

## PostOrder DFS (2ms, 100%)
对于每一个节点，如果自身和所有的子节点检查了就不用再看了，没检查过的话，做如下检查:
从这个点开始访问，如果再次访问到任何已经访问过的点，就说明有环，说明完蛋了, 都没有还就能墨迹完所有课程
```java
class Solution {
    public boolean canFinish(int n, int[][] preqs) {
        // create graph
        List<Integer>[] graph = new List[n];
        for (var a : preqs) {
            int c = a[0], p = a[1]; // p: parent, c: child, directed graph
            if (graph[p] == null) graph[p] = new ArrayList<>();
            graph[p].add(c);
        }

        // visit = 2: this node and all of its descends are visited, 1: this node was visited
        int[] visit = new int[n];

        // PostOrder traverse
        for (int cur = 0; cur < n; ++cur)
            if (dfs(cur, visit, graph))
                return false;

        return true;
    }

    // dfs (PostOrder) to check whether starting at certain node there's a cycle
    private boolean dfs(int cur, int[] visit, List<Integer>[] graph) {
        if (visit[cur] == 2) return false;
        if (visit[cur] == 1) return true;

        if (graph[cur] == null) return false;

        visit[cur] = 1;
        boolean res = false;
        for (int next : graph[cur])
            if (res = dfs(next, visit, graph))
                break;

        visit[cur] = 2;
        return res;
    }
}
```