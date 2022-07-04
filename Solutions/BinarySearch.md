# BinarySearch
## Boundary Discussion
### What causes dead loop?
## Classical
### 14. Classical Binary Search
```java
public class Solution {
  public int binarySearch(int[] a, int t) {
    int l = 0, r = a.length - 1;
    while ( l <= r) {
      int m = l + (r - l) / 2;
      if (a[m] == t) return m;
      else if (a[m] < t) l = m + 1;
      else r = m - 1;
    }
    return -1;
  }
}
```