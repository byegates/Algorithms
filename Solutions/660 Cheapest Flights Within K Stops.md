# Cheapest Flights Within K Stops
LeetCode: https://leetcode.com/problems/cheapest-flights-within-k-stops/

LaiCode : https://app.laicode.io/app/problem/660
## Description
Suppose there are m flights connecting n cities. Flight is represented by an int array int[] where the first element is departure city, the second element is destination city and the third element is the price.

Given a departure city src, a destination city dst, and most # of stops k, return the lowest price of flights can take you from src to dst with at most k stops. If there is no such a route, then return -1.

You can assume that there is no duplicated flights.

Example 1:

Input: n = 3, flights = [[0,1,100],[0,2,1000], [1,2,200]], src = 0, dst = 2, k = 0

Output: 1000

Example 2:

Input: n = 4, flights = [[0,1,100],[1,2,100],[2,0,100],[1,3,600],[2,3,200]], src = 0, dst = 3, k = 1

Output: 700

Example 3:

Input: n = 5, flights = [[4,1,1],[1,2,3],[0,3,2],[0,4,10],[3,1,1],[1,4,3]], src = 2, dst = 1, k = 1

output: -1

# Solution 0: DP (Bellman Ford)
4ms on LeetCode

## idea
We define dp[i][j] as the min cost to take max i times flight to get to city j, and take i flights mean i - 1 stops, so for k stops, we take k + 1 flights;

### Example 3

the dp matrix will start like below:

|     | 0   | 1   | 2   | 3   | 4   |
|-----|-----|-----|-----|-----|-----|
| 0   | +∞  | +∞  | 0   | +∞  | +∞  |
| 1   | +∞  | +∞  | +∞  | +∞  | +∞  |
| 2   | +∞  | +∞  | +∞  | +∞  | +∞  |

After round 1 (going through all flights for i = 1):

|     | 0   | 1   | 2   | 3   | 4   |
|-----|-----|-----|-----|-----|-----|
| 0   | +∞  | +∞  | 0   | +∞  | +∞  |
| 1   | +∞  | +∞  | 0   | +∞  | +∞  |
| 2   | +∞  | +∞  | +∞  | +∞  | +∞  |

After round 2 (going through all flights for i = 2):

|     | 0   | 1   | 2   | 3   | 4   |
|-----|-----|-----|-----|-----|-----|
| 0   | +∞  | +∞  | 0   | +∞  | +∞  |
| 1   | +∞  | +∞  | 0   | +∞  | +∞  |
| 2   | +∞  | +∞  | 0   | +∞  | +∞  |

What we need to return is dp[k+1][dst] which is dp[2][1] which is +∞, so the output is -1

### Example 2

The dp matrix will look like below,

Initialized:

|     | 0   | 1   | 2   | 3   |
|-----|-----|-----|-----|-----|
| 0   | 0   | +∞  | +∞  | +∞  |
| 1   | +∞  | +∞  | +∞  | +∞  |
| 2   | +∞  | +∞  | +∞  | +∞  |

i = 1, iterate through all flights:

|     | 0   | 1   | 2   | 3   |
|-----|-----|-----|-----|-----|
| 0   | 0   | +∞  | +∞  | +∞  |
| 1   | 0   | 100 | +∞  | +∞  |
| 2   | +∞  | +∞  | +∞  | +∞  |

i = 2, iterate through all flights:


|     | 0   | 1   | 2   | 3   |
|-----|-----|-----|-----|-----|
| 0   | 0   | +∞  | +∞  | +∞  |
| 1   | 0   | 100 | +∞  | +∞  |
| 2   | 0   | 100 | 200 | 700 |

We return dp[2][dst] if it has a valid value (not +∞), in this case: 700.

```java
import java.util.Arrays;

class Solution {
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {// TC: O(k * EDGE) ==> k*n^2
        int[][] dp = new int[k + 2][n]; // dp[i][j]: the min cost to take max i times flight to get to city j, and take i flights mean i - 1 stops, so for k stops, we take k + 1 flights
        int noFlight = Integer.MAX_VALUE / 2;
        for (int[] a : dp) Arrays.fill(a, noFlight);
        dp[0][src] = 0;
        for (int i = 1; i <= k + 1; i++) {
            dp[i][src] = 0;
            for (int[] flight : flights)
                //dp[i][flight[1]]: when we take max i-flights, the cost to get to city: flight[i]
                dp[i][flight[1]] = Math.min(dp[i][flight[1]], dp[i - 1][flight[0]] + flight[2]);
        }
        return dp[k + 1][dst] == noFlight ? -1 : dp[k + 1][dst];
    }
}
```

# Solution 1: DFS

# Solution 2: BFS with pruning
