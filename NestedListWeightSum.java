/*
    Given a nested list of integers represented by a string without blank,
    parse the string and  return the sum of all integers in the list weighted by their depth.

    Each element is either an integer, or a list -- whose elements may also be integers or other lists.

    Example 1:
    Given the list "[[1,1],2,[1,1]]", return 10. (four 1's at depth 2, one 2 at depth 1)

    Example 2:
    Given the list "[1,[4,[6]]]", return 27. (one 1 at depth 1, one 4 at depth 2, and one 6 at depth 3; 1 + 4*2 + 6*3 = 27)
 */
public class NestedListWeightSum {
    public int depthSum(String s) { // TC: O(n), SC:O(1)
        int lvl = 0, sum = 0, curNumber = 0, sign = 1;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '[') lvl++;
            else if (c == ']' || c == ',') {
                sum += curNumber * lvl * sign;
                curNumber = 0; // reset
                sign = 1; // reset
                if (c == ']') lvl--;
            } else if (c == '-') sign = -1;
            else curNumber = curNumber * 10 + c - '0'; // isNumeric
        }
        return sum;
    }
}
