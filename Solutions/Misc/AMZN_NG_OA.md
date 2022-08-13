# Amazon, New Grad, OA

## 6/29/22
### #1
<pre>
Min start health of game</pre>

```java
import java.util.List;

class Solution {
    public long findMinHealth(List<Integer> power, int armor) {
        int max = 0, sum = 0;
        for (int x : power) {
            if (x > max) max = x;
            sum += x;
        }
        return sum - Math.min(armor, max) + 1;
    }
}
```
### #2
<pre>
Divide list of integers into groups, range with array can't exceed k</pre>
TC: O(nlogn), SC: O(n)
```java
class Solution {
    public int minGroup(List<Integer> list, int k) {
        Collections.sort(list);
        int start = list.get(0), res = 1;
        for (int x : list) {
            if (x - start > k) {
                start = x;
                res++;
            }
        }
        return res;
    }
}
```