# Number of Islands
[LeetCode 200. Number of Islands](https://leetcode.com/problems/number-of-islands/)

[LaiCode 525. Number of Islands](https://app.laicode.io/app/problem/525)

访问过的island点把value直接改成'2'同时就起到了去重的作用，可以不用一个新的boolean矩阵，当然，这样就改变了原输入数组的值了。
```java
class Solution { // TC: O(4*m*n) ==> O(m*n), SC: O(m*n), height of recursion tree
  private static final int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}, };
  public int numIslands(char[][] grid) {
    int rows, cols;
    if (grid == null || (rows = grid.length) == 0 || (cols = grid[0].length) == 0) return 0;

    int count = 0;
    for (int i = 0; i < rows; i++)
      for (int j = 0; j < cols; j++)
        if (grid[i][j] == '1') {
          count++;
          dfs(grid, i, j, rows, cols);
        }

    return count;
  }

  private void dfs(char[][] grid, int i, int j, int rows, int cols) {
    if (i < 0 || j < 0 || i >= rows || j >= cols || grid[i][j] != '1') return;

    grid[i][j] = '2';
    for (int[] dir : dirs)
      dfs(grid, i + dir[0], j + dir[1], rows, cols);
  }
}
```

# Number of Islands II
[LeetCode 305. Number of Islands II](https://leetcode.com/problems/number-of-islands-ii/)

[LaiCode 419. Number of Islands II](https://app.laicode.io/app/problem/419)
## Naive Solution
Will TLE (Time Limit Exceeded) on LeetCode

Use solution 1 k times. (where k is positions.length)
```java
class Solution { // TC: O(k*m*n), SC: O(m*n)
  private static final int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}, };
  public int numIslands(int[][] grid) {
    int rows, cols;
    if (grid == null || (rows = grid.length) == 0 || (cols = grid[0].length) == 0) return 0;

    int count = 0;
    boolean[][] visited = new boolean[rows][cols];
    for (int i = 0; i < rows; i++)
      for (int j = 0; j < cols; j++)
        if (grid[i][j] == 1 && !visited[i][j]) {
          count++;
          dfs(grid, i, j, rows, cols, visited);
        }

    return count;
  }

  private void dfs(int[][] grid, int i, int j, int rows, int cols, boolean[][] visited) {
    if (i < 0 || j < 0 || i >= rows || j >= cols || grid[i][j] != 1 || visited[i][j]) return;

    visited[i][j] = true;
    for (int[] dir : dirs)
      dfs(grid, i + dir[0], j + dir[1], rows, cols, visited);
  }

  public List<Integer> numIslands(int m, int n, int[][] positions) {
    List<Integer> res = new ArrayList<>();
    int[][] grid = new int[m][n];
    for (int[] position : positions) {
      grid[position[0]][position[1]] = 1;
      res.add(numIslands(grid));
    }
    return res;
  }
}
```
## Union Find
```java
class Solution {
  private static final int[][] DIRS = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

  static class UnionFind {
    int[] roots, sizes;
    UnionFind (int size) {
      roots = new int[size];
      sizes = new int[size];
      for (int i = 0; i < size; i++) {
        roots[i] = i;
        sizes[i] = 1;
      }
    }

    public int find(int a) {
    if (roots[a] != a) roots[a] = find(roots[a]);
      return roots[a];
    }

    public boolean union(int a, int b) {
      int ra = find(a);
      int rb = find(b);

      if (ra == rb) return false;

      if (sizes[ra] >= sizes[rb]) {
        roots[rb] = ra;
        sizes[ra] += sizes[rb];
      } else {
        roots[ra] = rb;
        sizes[rb] += sizes[ra];
      }

      return true;
    }
  }
  public List<Integer> numIslands2(int m, int n, int[][] positions) {
    List<Integer> res = new ArrayList<>();

    UnionFind uf = new UnionFind(m * n);

    int[][] grid = new int[m][n];

    int count = 0;
    for (int[] p : positions) {
      int i = p[0], j = p[1];
      if (grid[i][j] == 1) {
        res.add(count);
        continue;
      } // skip duplicate points
      count++;
      grid[i][j] = 1;
      for (int[] dir : DIRS) {
        int i2 = i + dir[0], j2 = j + dir[1];
          if (i2 >= 0 && j2 >= 0 && i2 < m && j2 < n && grid[i2][j2] == 1)
            if (uf.union(i * n + j, i2 * n + j2))
              count--;
      }

      res.add(count);
    }

    return res;
  }
}
```
# 827. Making A Large Island
[LeetCode 827. Making A Large Island](https://leetcode.com/problems/making-a-large-island/)
```java
class Solution { // TC: O(m*n), SC: O(m*n)
    private static final int[][] DIRS = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}, };
    public int largestIsland(int[][] grid) {
        int rows, cols, max = 0;
        if (grid == null || (rows = grid.length) == 0 || (cols = grid[0].length) == 0) return 0;

        Map<Integer, Integer> map = new HashMap<>();
        // 第一遍遍历，数每个岛的面积，用1d index的负数做编号把面积存到map，用负数为了用grid自身来去重
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                if (grid[i][j] == 1) {
                    int idx = -(i * cols + j + 1); // index needs to start from -1, installed of 0, as 0 indicates water on the grid
                    int cur = dfs(grid, i, j, idx, rows, cols);
                    map.put(idx, cur);
                    max = Math.max(max, cur); // update it here too in case no 0 on the whole map.
                }

        //  尝试假装把每个0变成1看最后size会怎么样
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                if (grid[i][j] == 0) {
                    int cur = 1;
                    Set<Integer> set = new HashSet<>(); // de-dup same island from around of each 0
                    for (int[] dir : DIRS) {
                        int i2 = i + dir[0], j2 = j + dir[1];
                        int idx;
                        if (inValid(i2, j2, rows, cols) || set.contains(idx = grid[i2][j2])) continue;
                        cur += map.getOrDefault(idx, 0);
                        set.add(idx);
                    }
                    max = Math.max(max, cur);
                }

        return max;
    }

    private int dfs(int[][]grid, int i, int j, int idx, int rows, int cols) {
        if (inValid(i, j, rows, cols) || grid[i][j] != 1) return 0;

        int count = 1;
        grid[i][j] = idx;

        for (int[] dir : DIRS)
            count += dfs(grid, i + dir[0], j + dir[1], idx, rows, cols);

        return count;
    }

    private boolean inValid(int i, int j, int rows, int cols) {
        return i < 0 || j < 0 || i >= rows || j >= cols;
    }
}
```
# Number of Distinct Islands
[694. Number of Distinct Islands](https://leetcode.com/problems/number-of-distinct-islands/)
```java
class Solution {
    private static final int[][] DIRS = new int[][] {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    public int numDistinctIslands(int[][] grid) {
        int rows, cols;
        if (grid == null ||(rows = grid.length) == 0 || (cols = grid[0].length) == 0) return 0;
        Set<List<List<Integer>>> set = new HashSet<>();
        
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                if (grid[i][j] == 1) {
                    List<List<Integer>> list = new ArrayList<>();
                    dfs(grid, i, j, i, j, rows, cols, list);
                    set.add(list);
                }
        
        return set.size();
    }
    
    private void dfs(int[][] grid, int i, int j, int i0, int j0, int rows, int cols, List<List<Integer>> list) {
        if (i < 0 || j < 0 || i >= rows || j >= cols || grid[i][j] != 1) return;
        
        list.add(Arrays.asList(i - i0, j - j0));
        grid[i][j] = -1;
        
        for (int[] dir : DIRS)
            dfs(grid, i + dir[0], j + dir[1], i0, j0, rows, cols, list);
    }
}
```