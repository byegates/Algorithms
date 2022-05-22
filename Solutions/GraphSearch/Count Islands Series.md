# Number of Islands
[LeetCode 200. Number of Islands](https://leetcode.com/problems/number-of-islands/)

[LaiCode 525. Number of Islands](https://app.laicode.io/app/problem/525)
We change the value on grid to '2' for de-dup, no need for boolean matrix
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
