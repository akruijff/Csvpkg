package org.akruijff.csvpkg.usercases.util;

import static java.util.regex.Pattern.*;

public class PatternUtil {
    public static int flags(String args) {
        int flags = 0;
        for (int i = 0, n = args.length(); i < n; ++i)
            flags |= switch (args.charAt(i)) {
                case 'd' -> UNIX_LINES;
                case 'i' -> CASE_INSENSITIVE;
                case 'x' -> COMMENTS;
                case 'm' -> MULTILINE;
                case 's' -> DOTALL;
                case 'u' -> UNICODE_CASE;
                case 'U' -> UNICODE_CHARACTER_CLASS;
                default -> throw new IllegalStateException("Unexpected value: " + args.charAt(i));
            };
        return flags;
    }
}
