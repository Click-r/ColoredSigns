package Colored_Signs;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class customSyntax {

    final static String formatChar = "§";
    final static String reset = formatChar + "r";

    static String format(String keycodes, String text) {
        String toReturn = "";

        int n = keycodes.length();
        final int len = text.length();

        for (int k = 0; k < len; k++) {
            String col = formatChar + keycodes.charAt(k % n); // modular arithmetic needed to alternate colours

            toReturn += col + text.charAt(k) + reset;
        }

        return toReturn;
    }

    public static String[] alternate(String[] lines, String pattern) {
        int i = 0;
        Pattern lookfor = Pattern.compile(pattern);

        for (String line: lines) {
            if (!line.isEmpty() && line.length() > 2) {
                int initialLen = line.length();

                Matcher match = lookfor.matcher(line);

                String finalString = "";

                while (match.find()) {
                    int correctionTerm = initialLen - line.length(); // match.start() is relative to the initial size of the string, so we have to correct that
                    String extra = line.substring(0, match.start() - correctionTerm);
                    finalString += extra + reset;

                    line = line.substring(extra.length() + match.group().length());

                    String apply = match.group(1);
                    apply = apply.substring(1,apply.length()-1);

                    String text = match.group(2);

                    finalString += format(apply, text);
                }

                finalString += line + reset;
                lines[i] = finalString;
            }

            i++;
        }

        return lines;
    }
}
