# Table of Content
1. [Kth Smallest Number In Sorted Matrix](#Kth-Smallest-Number-In-Sorted-Matrix)
# Notes
这个系列基本都是用PriorityQueue, 从最小的value开始bfs, more 总结 to come

# Kth-Smallest-Number-In-Sorted-Matrix
[LaiCode 26. Kth Smallest Number In Sorted Matrix](https://app.laicode.io/app/problem/26)
## Description
Given a matrix of size N x M. For each row the elements are sorted in ascending order, and for each column the elements are also sorted in ascending order. Find the Kth-smallest number in it.
<pre>
Assumptions
the matrix is not null, N > 0 and M > 0
K > 0 and K <= N * M
Examples
{
{1,  3,  5,  7},
{2,  4,  8,  9},
{3,  5, 11, 15},
{6,  8, 13, 18} }
the 5th smallest number is 4
the 8th smallest number is 6
</pre>

```java
public class Solution {

  static class MX {
    private int rows, cols;
    private int[][] mx;
    MX (int[][] mx) {
      this.mx = mx;
      rows = mx.length;
      cols = mx[0].length;
    }

    public int idx(Cell c) {
      return c.x * cols + c.y;
    }

    public Cell cell(int x, int y) {
      return new Cell(x, y, mx[x][y]);
    }

    public boolean valid(int x, int y) {
      return x < rows && y < cols;
    }

    public Cell downCell(Cell c) {
      return valid(c.x + 1, c.y) ? cell(c.x + 1, c.y) : null;
    }

    public Cell rightCell(Cell c) {
      return valid(c.x, c.y + 1) ? cell(c.x, c.y + 1) : null;
    }
  }

  static class Cell {
    int x, y, val;
    Cell (int x, int y, int val) {
      this.x = x;
      this.y = y;
      this.val = val;
    }
  }

  public int kthSmallest(int[][] matrix, int k) {
    MX mx = new MX(matrix);
    Set<Integer> visited = new HashSet<>();
    PriorityQueue<Cell> q = new PriorityQueue<>(k, new Comparator<Cell>(){
      @Override
      public int compare(Cell c1, Cell c2) {
        return Integer.compare(c1.val, c2.val);
      }
    });
    
    q.offer(mx.cell(0, 0));
    visited.add(0);

    for (int i = 1; i < k; i++) {
      Cell cur = q.poll();
      Cell c1 = mx.downCell(cur);
      Cell c2 = mx.rightCell(cur);
      if (c1 != null && !visited.contains(mx.idx(c1))) {
        q.offer(c1);
        visited.add(mx.idx(c1));
      }
      if (c2 != null && !visited.contains(mx.idx(c2))) {
        q.offer(c2);
        visited.add(mx.idx(c2));
      }
    }

    return q.peek().val;
  }
}
```
<pre>
</pre>
