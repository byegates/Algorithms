# [252. Meeting Rooms](https://leetcode.com/problems/meeting-rooms/)
按照所有会议的开始时间排序，任何一个会议的开始时间跟前一个会议的结束时间冲突的话，就return false。
TC: O(nlogn)

SC: O(1)

```java
class Solution {
    public boolean canAttendMeetings(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> (a[0] - b[0]));
        int[] pre = new int[] {0, 0};
        for (var cur : intervals) {
            if (pre[1] > cur[0]) return false;
            pre = cur;
        }
        return true;
    }
}
```
### You could write it this way too, to save 1 operation...
```java
class Solution {
    public boolean canAttendMeetings(int[][] intervals) {
        if (intervals.length == 0) return true;
        Arrays.sort(intervals, (a, b) -> (a[0] - b[0]));
        int[] pre = intervals[0];
        for (int i = 1; i < intervals.length; i++) {
            if (pre[1] > intervals[i][0]) return false;
            pre = intervals[i];
        }
        return true;
    }
}
```

# [253. Meeting Rooms II](https://leetcode.com/problems/meeting-rooms-ii/)
所有的开始和结束时间分开排序。任何一个开始时间比结束时间早，都要多开一个room；
比当前结束时间晚的开始时间，就用已有的room，就忽略这个结束时间。
TC: O(nlogn)

SC: O(n)
```java
class Solution {
    public int minMeetingRooms(int[][] intervals) {
        int n = intervals.length;
        int[] begin = new int[intervals.length], end = new int[intervals.length];
    
        for (int i = 0; i < intervals.length; i++) {
            begin[i] = intervals[i][0];
              end[i] = intervals[i][1];
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
<pre>
把两个人的schedule分别放到两个minHeap去(按照开始时间排序);
先peek出来各自最早的schedule, 用开始时间大的那个和结束时间小的那个求出overlap duration,
如果>=要求的duration, 结束, 退出。 否则:
结束时间小的那个slot可以扔了，看他的下一个。因为结束时间早的slot的下一个开始时间可能还可以跟另一个slot有overlap。
任何一个minHeap poll完了就没有可用的duration可以返回了。
</pre>
TC: O(nlogn)

SC: O(n)

```java
class Solution {
    public List<Integer> minAvailableDuration(int[][] slots1, int[][] slots2, int duration) {
        PriorityQueue<int[]> q1 = new PriorityQueue<>((a, b) -> (a[0] - b[0]));
        PriorityQueue<int[]> q2 = new PriorityQueue<>((a, b) -> (a[0] - b[0]));

        for (var a : slots1) if (a[1] - a[0] >= duration) q1.offer(a);
        for (var a : slots2) if (a[1] - a[0] >= duration) q2.offer(a);

        while (!q1.isEmpty() && !q2.isEmpty()) {
            int[] a = q1.peek(), b = q2.peek();
            int begin = Math.max(a[0], b[0]);
            int end   = Math.min(a[1], b[1]);

            int cur = end - begin; // cur duration
            if (cur >= duration) return Arrays.asList(begin, begin + duration);

            if (a[1] > b[1]) q2.poll();
            else q1.poll();
        }

        return new ArrayList<>();
    }
}
```

# [LaiCode 599. Meeting Schedule](https://app.laicode.io/app/problem/599)
<pre>
Given a list of meeting time intervals[[s0, e0],[s1, e1]......], return the maximum number of meetings a person could attend. A person could attend two meetings [si, ei] and [sj, ej] only when ei  < sj.

Example:

Input = [[1,2],[2,3],[3,4],[4,5]]

Output = 2

Explanation: The person could attend two meetings either [[1,2], [3,4]] or [[2,3], [4,5].
</pre>
按照结束时间升序排列, 第一个会议一定可以参加。
所有开始时间比上一个结束时间晚的会议都可以参加。这就是你所有可以参加的会议。

TC: O(nlogn)
```java
class Solution {
    public int maximumMeetings(int[][] intervals) {
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] a, int[] b) {
                return a[1] - b[1];
            }
        });
        int res = 1, end = intervals[0][1];
        for (int[] a : intervals) {
            if (a[0] <= end) continue;
            res++;
            end = a[1];
        }
        return res;
    }
}
```
### or
```java
class Solution {
    public int maximumMeetings(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> (a[1] - b[1]));
        int res = 1, end = intervals[0][1];
        for (var a : intervals) {
            if (a[0] <= end) continue;
            res++;
            end = a[1];
        }
        return res;
    }
}
```