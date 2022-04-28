import java.util.ArrayDeque;
import java.util.Deque;

/*
    Evaluate the value of an arithmetic expression in Reverse Polish Notation.
    Assumption
    Valid operators are +, -, *, /.
    Each operand may be an integer or another expression.
    Examples
    ["2", "1", "+", "3", "*"] -> ((2 + 1) * 3) -> 9
    ["4", "13", "5", "/", "+"] -> (4 + (13 / 5)) -> 6
 */
public class EvaluateReversePolishNotation {
    public int evalRPN(String[] tokens) { // TC: O(n) n is the length of tokens, SC:O(n)
        Deque<Integer> stack = new ArrayDeque<>();
        for (String s : tokens) {
            Integer x = getInt(s);
            if (x != null) stack.offerFirst(x);
            else {
                int x1 = stack.pollFirst();
                int x2 = stack.pollFirst();
                int res = switch (s) {
                    case "+" -> x2 + x1;
                    case "-" -> x2 - x1;
                    case "*" -> x2 * x1;
                    default -> x2 / x1; // "/", assume input is always valid
                };
                stack.offerFirst(res);
            }
        }
        return stack.peekFirst();
    }

    private Integer getInt(String s) {
        Integer res = null;
        try {
            res = Integer.parseInt(s);
        } catch (NumberFormatException ignored) {}
        return res;
    }

    public static void main(String[] args) {
        EvaluateReversePolishNotation erpn = new EvaluateReversePolishNotation();
        System.out.println(erpn.evalRPN(new String[]{"0", "12", "4", "+", "-"}) == -16);
    }

}
