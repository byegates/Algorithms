# [207. Course Schedule](https://leetcode.com/problems/course-schedule/)

## Topological Sort
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

## PostOrder DFS
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
        
        // preparing
        boolean[] checked = new boolean[n]; // this node and all of its descends are checked
        boolean[] visited = new boolean[n]; // single node de-dup/cycle check
        
        // PostOrder traverse
        for (int cur = 0; cur < n; ++cur)
            if (dfs(cur, visited, checked, graph))
                return false;
        
        return true;
    }
    
    // dfs (PostOrder) to check whether starting at certain node there's a cycle
    private boolean dfs(int cur, boolean[] visited, boolean[] checked, List<Integer>[] graph) {
        if (checked[cur]) return false;
        if (visited[cur]) return true;
        
        if (graph[cur] == null) return false;
        
        visited[cur] = true;
        boolean res = false;
        for (int next : graph[cur])
            if (res = dfs(next, visited, checked, graph))
                break;
        visited[cur] = false;
        
        checked[cur] = true;
        return res;
    }
}
```