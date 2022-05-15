package org.akruijff.csvpkg.usercases.util;

import java.util.*;

public class CommandUtil {
    public static String getLine(String[] args) {
        StringJoiner joiner = new StringJoiner(",", "\"", "\"");
        for (String arg : args)
            joiner.add(arg);
        return joiner.toString();
    }
}
