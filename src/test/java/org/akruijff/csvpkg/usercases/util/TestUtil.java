package org.akruijff.csvpkg.usercases.util;

import java.io.*;
import java.nio.charset.*;

public class TestUtil {
    public static String unifyNewLine(String input) throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        String line;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (PrintStream printer = new PrintStream(out)) {
            while ((line = reader.readLine()) != null)
                printer.println(line);
        }
        return out.toString();
    }
}
