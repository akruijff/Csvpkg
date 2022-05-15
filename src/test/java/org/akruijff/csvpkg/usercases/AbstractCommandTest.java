package org.akruijff.csvpkg.usercases;

import org.akruijff.csvpkg.*;
import org.akruijff.csvpkg.exceptions.IOError;
import org.akruijff.csvpkg.usercases.util.*;
import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.charset.*;

import static org.akruijff.csvpkg.usercases.util.TestUtil.*;
import static org.junit.jupiter.api.Assertions.*;

public class AbstractCommandTest {
    private OutputStream output;
    private InputStream oldIn;
    private PrintStream oldOut;

    public void setup(String input) {
        oldIn = System.in;
        oldOut = System.out;
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        System.setOut(new PrintStream(output = new ByteArrayOutputStream()));
    }

    @AfterEach
    public void teardown() {
        System.setIn(oldIn);
        System.setOut(oldOut);
    }

    @Test
    @DisplayName ("An empty file will return no output")
    public void empty() throws IOException {
        setup("");

        Command command = new DummyCommand();
        command.execute("command");

        String expected = "";
        String actual = output.toString();
        assertEquals(unifyNewLine(expected), unifyNewLine(actual));
    }

    @Test
    @DisplayName ("A malformed file will cause an read error")
    public void malformed() {
        setup("\"A\",  B\",  \"C\", \"D\", \"E\"");

        assertThrows(IOError.class, () -> {
            Command command = new DummyCommand();
            command.execute("command");
        }, "Input/output error: stdin");
    }

    @Test
    @DisplayName ("A number with a comma instead of a dot is still converted in to a double")
    public void comma() throws IOException {
        setup("""
                "A","B","C","D","E"
                "a1","b1","c","d","1"
                "a2","b2","c","d","2,5"
                "a3","B3","c","e","3"
                "a4","B4","c","e","e4"
                """);

        Command command = new DummyCommand();
        command.execute("command");

        String expected = """
                "A","B","C","D","E"
                "a1","b1","c","d","1"
                "a2","b2","c","d","2.5"
                "a3","B3","c","e","3"
                "a4","B4","c","e","e4"
                """;
        String actual = output.toString();
        assertEquals(unifyNewLine(expected), unifyNewLine(actual));
    }
}
