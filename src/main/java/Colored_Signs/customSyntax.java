package Colored_Signs;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Stack;

public class customSyntax {
    //TODO: make the part of the code responsible for alternating the format into separate function so it'll be easier to solve the issues.
    
    public static String[] alternate(String[] lines, String pattern) {
        int i = 0;
        Pattern lookfor = Pattern.compile(pattern);

        final String formatChar = "§";

        for (String line: lines) {
            if (!line.isEmpty() && line.length() > 2) {
                Matcher match = lookfor.matcher(line);

                String newLine = line;
                newLine += "&r";

                int start = 0;
                boolean change = false;

                Stack<String> sections = new Stack<String>();

                int j = 0;
                String leftover = "";

                while (match.find()) {
                    String found = match.group();
                    int begin = match.start();
                    int finish = match.end();

                    if (j == 0 && begin != start) {
                        leftover = line.substring(0, begin);
                        start = begin;
                    }

                    if (change) {
                        sections.push(line.substring(start,begin));

                        change = false;
                    }

                    if (finish != newLine.length()) {
                        sections.push(found);

                        start = finish;
                        change = true;
                    } else {
                        sections.push(found);
                        sections.push(line.substring(finish+1));
                    }

                    j++;
                }

                if (sections.size() % 2 != 0) {
                    int ind = sections.peek().length()-1;
                    sections.push(line.substring(ind));
                }

                String finalLine = "";

                while (!sections.empty()) {
                    String txt = sections.pop();
                    String pat = sections.pop();

                    if (!txt.isEmpty()) {

                        pat = pat.substring(1, pat.length()-1);
                        txt = txt.substring(1);

                        int n = pat.length();
                        final int len = txt.length();

                        for (int k = 0; k < len; k++) {
                            String col = formatChar + pat.charAt(k % n); // modular arithmetic to alternate colours

                            finalLine += col + txt.charAt(k) + formatChar + "r";
                        }
                    } else {
                        finalLine += formatChar + "r";
                    }
                }
                if (finalLine.isEmpty()) finalLine = line + formatChar + "r";
                lines[i] = leftover + finalLine;
            }

            i++;
        }

        return lines;
    }
}
