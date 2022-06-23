# [252. Meeting Rooms](https://leetcode.com/problems/meeting-rooms/)
TC: O(nlogn)

SC: O(1)

```java
class Solution {
    public boolean canAttendMeetings(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> (a[0] - b[0]));
        int[] prev = new int[] {0, 0};
        for (var interval : intervals) {
            if (prev[1] > interval[0]) return false;
            prev = interval;
        }
        return true;
    }
}
```

# [253. Meeting Rooms II](https://leetcode.com/problems/meeting-rooms-ii/)
TC: O(n)

SC: O(n)
```java
class Solution {
    public int minMeetingRooms(int[][] intervals) {
        int[] begin = new int[intervals.length];
        int[] end   = new int[intervals.length];
    
        for (int i = 0; i < intervals.length; i++) {
            begin[i] = intervals[i][0];
            end[i]   = intervals[i][1];
        }
        
        Arrays.sort(begin);
        Arrays.sort(end);
        
        int res = 0;
        
        for (int i = 0, j = 0; i < intervals.length; i++)
            if (begin[i] < end[j]) res++;
            else j++;
        
        return res;
    }
}
```

## [1229. Meeting Scheduler](https://leetcode.com/problems/meeting-scheduler/)
TC:

SC:

```java
class Solution {
    public List<Integer> minAvailableDuration(int[][] slots1, int[][] slots2, int duration) {
        PriorityQueue<int[]> q1 = new PriorityQueue<>((a, b) -> (a[0] - b[0]));
        PriorityQueue<int[]> q2 = new PriorityQueue<>((a, b) -> (a[0] - b[0]));
        
        for (var a : slots1) if (a[1] - a[0] >= duration) q1.offer(a);
        for (var a : slots2) if (a[1] - a[0] >= duration) q2.offer(a);
        
        while (!q1.isEmpty() && !q2.isEmpty()) {
            int[] s1 = q1.peek(), s2 = q2.peek();
            int maxBegin = Math.max(s1[0], s2[0]);
            int minEnd   = Math.min(s1[1], s2[1]);
            
            int cur = minEnd - maxBegin;
            if (cur >= duration) return Arrays.asList(maxBegin, maxBegin + duration);
            
            if (s1[1] < s2[1]) q1.poll();
            else q2.poll();
        }
        
        return new ArrayList<>();
    }
}
```