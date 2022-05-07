package util;

import static resources.ConsoleColors.RED;
import static resources.ConsoleColors.RESET;

public class Utils {
    public static String toString(int e, int radix) {
        StringBuilder sb = new StringBuilder();
        sb.append("0".repeat(Integer.numberOfLeadingZeros(e)));
        String s = Integer.toString(e, radix);
        for (int i = 0; i < s.length(); i++)
            if (s.charAt(i) == '1') sb.append(RED).append('1').append(RESET);
            else sb.append('0');
        return sb.toString();
    }
}
