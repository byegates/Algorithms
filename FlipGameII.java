//        You are playing the following Flip Game with your friend: Given a string that contains only these two characters: + and -,
//        you and your friend take turns to flip two consecutive "++" into "--".
//        The game ends when a person can no longer make a move and therefore the other person will be the winner.
//        Write a function to determine if the starting player can guarantee a win.
//
//        For example, given s = "++++", return true.
//        The starting player can guarantee a win by flipping the middle "++" to become "+--+".

import java.util.ArrayList;
import java.util.List;

public class FlipGameII {
    public boolean canWin(String s) {
        char[] a = s.toCharArray();
        for (int i : validIndices(a)) {
            a[i] = a[i + 1] = '-';
            if (!friendCanWin(a, 1)) return true; // I flip first, if there's one flip to guarantee friend can't win, I win, otherwise I lose
            a[i] = a[i + 1] = '+';
        }
        return false;
    }

    private boolean friendCanWin(char[] a, int hand) { // hand 0 means self and hand 1 means friend
        List<Integer> candidates = validIndices(a);
        if (candidates.size() == 0)
            return hand % 2 == 0;

        for (int i : candidates) {
            a[i] = a[i + 1] = '-';
            boolean friendCanWin = friendCanWin(a, hand + 1);
            a[i] = a[i + 1] = '+'; // need to recover first before return so that I can try another flip in main method
            if (friendCanWin) return true;
        }
        return false;
    }

    private List<Integer> validIndices(char[] a) {
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < a.length - 1; i++)
            if (a[i] == '+' && a[i + 1] == '+')
                res.add(i);
        return res;
    }

    public static void main(String[] args) {
        FlipGameII fg = new FlipGameII();
        for (int i = 1; i < 10; i++)
            System.out.println(fg.canWin("+".repeat(i)));
        System.out.println(fg.canWin("+++----+++--++")); // true
    }
}
