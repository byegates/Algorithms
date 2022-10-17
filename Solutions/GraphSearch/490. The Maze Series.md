# Table of Contents
1. [490. The Maze](#490-The-Maze)
2. [505. The Maze II](#505-The-Maze-II)
3. [499. The Maze III](#499-The-Maze-III)
# [490-The-Maze](https://leetcode.com/problems/the-maze/)

## DFS
```java
class Solution {
    private static final int[][] dirs = new int[][] {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    public boolean hasPath(int[][] maze, int[] start, int[] dest) {
        return dfs(maze, start[0], start[1], maze.length, maze[0].length, dest);
    }
    
    private boolean dfs(int[][] maze, int i, int j, int m, int n, int[] dest) {
        if (!valid(i, j, m, n) || maze[i][j] == -1) return false;
        if (i == dest[0] && j == dest[1]) return true;
        maze[i][j] = -1;
        
        for (var dir : dirs) {
            int i2 = i, j2 = j, di = dir[0], dj = dir[1];
            for (;valid(i2 + di, j2 + dj, m, n) && maze[i2 + di][j2 + dj] != 1; i2+=di, j2+=dj) ;
            if (dfs(maze, i2, j2, m, n, dest)) return true;
        }
        
        return false;
    }
    
    private boolean valid(int i, int j, int m, int n) {
        return i >= 0 && j >= 0 && i < m && j < n;
    }
}
```

# [505-The-Maze-II](https://leetcode.com/problems/the-maze-ii/)

## BFS
```java
class Solution {
    private static final int[][] dirs = new int[][] {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    public int shortestDistance(int[][] maze, int[] start, int[] dest) {
        int m = maze.length, n = maze[0].length;
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(maze[a[0]][a[1]], maze[b[0]][b[1]]));
        pq.offer(start);
        maze[start[0]][start[1]] = 2; // to de-dup and calculate cost at the same time, so cost starting from 2
        
        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int i = cur[0], j = cur[1];
            if (i == dest[0] && j == dest[1]) return maze[i][j] - 2;
            for (var dir : dirs) {
                int i2 = i, j2 = j, di = dir[0], dj = dir[1], count = 0;
                for (; valid(i2+di, j2+dj, m, n) && maze[i2+di][j2+dj] != 1; i2 += di, j2 += dj) count++;
                int newCost = count + maze[i][j];
                if (maze[i2][j2] == 0 || newCost < maze[i2][j2]) {
                    maze[i2][j2] = newCost;
                    pq.offer(new int[] {i2, j2});
                }
                
            }
        }
        
        return -1;
    }

    private boolean valid(int i, int j, int m, int n) {
        return i >= 0 && j >= 0 && i < m && j < n;
    }
}
```

# [499-The-Maze-III](https://leetcode.com/problems/the-maze-iii/)

## Solution 1 (14ms)
```java
class Solution {
    private static final int[][] dirs = new int[][] {{1, 0}, {0, -1}, {0, 1}, {-1, 0}};
    private static final char[]  paths = new char[] {'d', 'l', 'r', 'u'};
    record Cell(int i, int j, int steps, String path) implements Comparable<Cell>{
        @Override
        public int compareTo(Cell other) {
            if (steps == other.steps) return path.compareTo(other.path);
            return steps - other.steps;
        }
    }
    public String findShortestWay(int[][] maze, int[] ball, int[] hole) {
        int m = maze.length, n = maze[0].length;
        PriorityQueue<Cell> q = new PriorityQueue<>();
        q.offer(new Cell(ball[0], ball[1], 0, ""));

        while (!q.isEmpty()) {
            Cell cur = q.poll();
            if (cur.i == hole[0] && cur.j == hole[1]) return cur.path;
            if (maze[cur.i][cur.j] < 0) continue;
            maze[cur.i][cur.j] = -(cur.steps + 1);

            for (int k = 0; k < paths.length; k++) {
                int count = 0, i2 = cur.i, j2 = cur.j, di = dirs[k][0],  dj = dirs[k][1];
                while (valid(i2 + di, j2 + dj, m, n) && maze[i2 + di][j2 + dj] != 1) {
                    i2 += di;
                    j2 += dj;
                    count++;
                    if (i2 == hole[0] && j2 == hole[1]) break;
                }
                q.offer(new Cell(i2, j2, cur.steps + count, cur.path + paths[k]));
            }

        }

        return "impossible";
    }

    public boolean valid(int i, int j, int m, int n) {
        return i >= 0 && j >= 0 && i < m && j < n;
    }
}
```

## Solution 2
Avoid adding redundant cells into pq
```java
class Solution {
    private static final int[][] dirs = new int[][] {{1, 0}, {0, -1}, {0, 1}, {-1, 0}};
    private static final char[]  dirc = new char[] {'d', 'l', 'r', 'u'};
    record Cell(int i, int j, int steps, String path) implements Comparable<Cell>{
        @Override
        public int compareTo(Cell other) {
            if (steps == other.steps) return path.compareTo(other.path);
            return steps - other.steps;
        }
    }
    public String findShortestWay(int[][] maze, int[] ball, int[] hole) {
        int m = maze.length, n = maze[0].length, i0, j0;
        PriorityQueue<Cell> q = new PriorityQueue<>();
        String[][] path = new String[m][n];

        q.offer(new Cell(ball[0], ball[1], 2, ""));
        maze[i0 = ball[0]][j0 = ball[1]] = 2;
        path[i0][j0] = "";

        while (!q.isEmpty()) {
            Cell cur = q.poll();
            if (cur.i == hole[0] && cur.j == hole[1]) return cur.path;

            for (int k = 0; k < dirc.length; k++) {
                int count = 0, i2 = cur.i, j2 = cur.j, di = dirs[k][0],  dj = dirs[k][1];
                while (valid(i2 + di, j2 + dj, m, n) && maze[i2 + di][j2 + dj] != 1) {
                    i2 += di;
                    j2 += dj;
                    count++;
                    if (i2 == hole[0] && j2 == hole[1]) break;
                }
                
                int newSteps = maze[cur.i][cur.j] + count;
                String newPath = path[cur.i][cur.j] + dirc[k];
                if (maze[i2][j2] == 0 || newSteps < maze[i2][j2] || newSteps == maze[i2][j2] && newPath.compareTo(path[i2][j2]) < 0) {
                    maze[i2][j2] = newSteps;
                    path[i2][j2] = newPath;
                    q.offer(new Cell(i2, j2, newSteps, newPath));
                }
            }

        }

        return "impossible";
    }

    public boolean valid(int i, int j, int m, int n) {
        return i >= 0 && j >= 0 && i < m && j < n;
    }
}
```

## Solution 3
No need class and/or PriorityQueue, will need traverse the whole graph in this case
```java
class Solution {

    private static final int[][] dirs = new int[][] {{1, 0}, {0, -1}, {0, 1}, {-1, 0}};
    private static final char[]  dirc = new char[] {'d', 'l', 'r', 'u'};
    public String findShortestWay(int[][] maze, int[] ball, int[] hole) {
        int m = maze.length, n = maze[0].length, i0, j0, iz, jz;
        Queue<int[]> q = new ArrayDeque<>();
        String[][] path = new String[m][n];

        maze[i0 = ball[0]][j0 = ball[1]] = 2;
        path[i0][j0] = "";
        maze[iz = hole[0]][jz = hole[1]] = -1;
        q.offer(new int[]{i0, j0});

        while (!q.isEmpty()) {
            var cur = q.poll();

            for (int k = 0; k < dirc.length; k++) {
                int count = 0, i2 = cur[0], j2 = cur[1], di = dirs[k][0],  dj = dirs[k][1];
                while (valid(i2 + di, j2 + dj, m, n) && maze[i2 + di][j2 + dj] != 1) {
                    i2 += di;
                    j2 += dj;
                    count++;
                    if (i2 == iz && j2 == jz) break;
                }

                int newSteps = maze[cur[0]][cur[1]] + count;
                String newPath = path[cur[0]][cur[1]] + dirc[k];
                if (maze[i2][j2] < 1 || newSteps < maze[i2][j2] || newSteps == maze[i2][j2] && newPath.compareTo(path[i2][j2]) < 0) {
                    maze[i2][j2] = newSteps;
                    path[i2][j2] = newPath;
                    q.offer(new int[]{i2, j2});
                }
            }
        }

        return path[iz][jz] == null ? "impossible" : path[iz][jz];
    }

    public boolean valid(int i, int j, int m, int n) {
        return i >= 0 && j >= 0 && i < m && j < n;
    }
}
```

## Solution4 (13ms)
Still use priorityQueue, but use a Cell matrix to de-dup
```java
class Solution {
    private static final int[][] dirs = new int[][] {{1, 0}, {0, -1}, {0, 1}, {-1, 0}};
    private static final char[]  dirc = new  char[] {'d',    'l',     'r',     'u'};

    record Cell(int i, int j, int steps, String path) implements Comparable<Cell>{
        @Override
        public int compareTo(Cell other) {
            return steps == other.steps ? path.compareTo(other.path) : Integer.compare(steps, other.steps);
        }
    }

    public String findShortestWay(int[][] maze, int[] ball, int[] hole) {
        int m = maze.length, n = maze[0].length, i0 = ball[0], j0 = ball[1], iz = hole[0], jz = hole[1];
        Queue<Cell> q = new PriorityQueue<>();
        Cell[][] cells = new Cell[m][n];

        q.offer(cells[i0][j0] = new Cell(i0, j0, 0, ""));

        while (!q.isEmpty()) {
            Cell cur = q.poll();
            if (cur.i == iz && cur.j == jz) return cur.path;

            for (int k = 0; k < dirc.length; k++) {
                int count = 0, i2 = cur.i, j2 = cur.j, di = dirs[k][0],  dj = dirs[k][1];
                while (valid(i2 + di, j2 + dj, m, n, maze)) {
                    i2 += di; j2 += dj;
                    count++;
                    if (i2 == iz && j2 == jz) break;
                }

                Cell next = new Cell(i2, j2, cur.steps + count, cur.path + dirc[k]);
                if (cells[i2][j2] == null || next.compareTo(cells[i2][j2]) < 0)
                    q.offer(cells[i2][j2] = next);
            }
        }

        return "impossible";
    }

    public boolean valid(int i, int j, int m, int n, int[][] maze) {
        return i >= 0 && j >= 0 && i < m && j < n && maze[i][j] != 1;
    }
}
```