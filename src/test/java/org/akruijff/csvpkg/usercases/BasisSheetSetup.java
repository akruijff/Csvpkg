package org.akruijff.csvpkg.usercases;

import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.charset.*;

import static org.akruijff.csvpkg.usercases.util.TestUtil.*;
import static org.junit.jupiter.api.Assertions.*;

public class BasisSheetSetup {
    protected OutputStream output;
    protected OutputStream error;
    private InputStream oldIn;
    private PrintStream oldOut;
    private PrintStream oldErr;

    protected void assertOutput(String expected) throws IOException {
        String actual = output.toString();
        assertEquals(unifyNewLine(expected), unifyNewLine(actual));
    }

    protected void assertError(String expected) throws IOException {
        String actual = error.toString();
        assertEquals(unifyNewLine(expected), unifyNewLine(actual));
    }

    @BeforeEach
    public void setup() {
        String input = """
                "A",  "B",  "C", "D", "E"
                "a1", "b1", "c", "d", "1"
                "a2", "b2", "c", "d", "2.5"
                "a3", "B3", "c", "e", "3"
                "a4", "B4", "c", "e", "e4"
                """;
        oldIn = System.in;
        oldOut = System.out;
        oldErr = System.err;
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        System.setOut(new PrintStream(output = new ByteArrayOutputStream()));
        System.setErr(new PrintStream(error = new ByteArrayOutputStream()));
    }

    @AfterEach
    public void teardown() {
        System.setIn(oldIn);
        System.setOut(oldOut);
        System.setErr(oldErr);
    }
}
