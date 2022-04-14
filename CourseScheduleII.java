/*
        There are a total of n courses you have to take, labeled from 0 to n - 1.
        Some courses may have prerequisites, for example to take course 0 you have to first take course 1,
        which is expressed as a pair: [0,1]

        Given the total number of courses and a list of prerequisite pairs,
        return the ordering of courses you should take to finish all courses.

        There may be multiple correct orders, you just need to return one of them.
        If it is impossible to finish all courses, return an empty array.

        For example:
        2, [[1,0]]
        There are a total of 2 courses to take. To take course 1 you should have finished course 0.
        So the correct course order is [0,1]
        4, [[1,0],[2,0],[3,1],[3,2]]
        There are a total of 4 courses to take. To take course 3 you should have finished both courses 1 and 2.
        Both courses 1 and 2 should be taken after you finished course 0. So one correct course order is [0,1,2,3].
        Another correct ordering is[0,2,1,3].

        Note:
        The input prerequisites is a graph represented by a list of edges, not adjacency matrices.
        Read more about how a graph is represented.
        You may assume that there are no duplicate edges in the input prerequisites.
*/

import java.util.*;

public class CourseScheduleII {
    public int[] findOrder(int numCourses, int[][] prerequisites) { // TC: O(V+E), SC: O(V+E)
        // Write your solution here
        int[] inDegree = new int[numCourses];
        ArrayList<Integer>[] dependencies = new ArrayList[numCourses];
        for (int i = 0; i < dependencies.length; i++)
            dependencies[i] = new ArrayList<>();
        for (int[] pre : prerequisites) {
            inDegree[pre[0]]++;
            dependencies[pre[1]].add(pre[0]);
        }

        int[] res = new int[numCourses];
        int idx = 0;

        Queue<Integer> q = new ArrayDeque<>();

        for (int i = 0; i < inDegree.length; i++) // index means course #
            if (inDegree[i] == 0)
                q.offer(i);

        while (!q.isEmpty()) {
            int cur = q.poll();
            res[idx++] = cur;
            for (int next : dependencies[cur]) {
                if (--inDegree[next] == 0)
                    q.offer(next);
            }
        }

        return idx == numCourses ? res : new int[]{};
    }
}
